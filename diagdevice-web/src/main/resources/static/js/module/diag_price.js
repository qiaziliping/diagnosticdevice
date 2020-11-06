// [诊断价格]页面相关js文件
;(function () {
	
	var priceObj  =  function() {
		
		this.searchLoadPdtType();
		this.searchPdtTypeOnchange();
		
		//this.softNameAutoComplete(); // 诊断软件自动完成
		
		this.softNameData = null;    // 用于保存软件名称的数据
		this.pdtTypeIdCache = "";    // 缓存产品类型
		this.isRepeated = false;     // 添加软件价格是否重复，默认false
		this.buyType = 1;            // 购买类型：1单次，2包月，3包季，4包年  ,现在默认传1
		
		this.addSoftBtnClick();      // 添加按钮点击事件
		
		this.pdtTypeOnchange();      // 产品类型改变时
		
		this.addSoftSubmit();      // 添加表单提交事件
		this.myValidata();         // 自定义验证
		this.closeDialogPrice();   // 弹出框关闭
		this.rowMonitor();         // 行监听事件
		this.editSoftSubmit();	   // 编辑表单提交事件
	};
	
	priceObj.addSoftLayer = null;     // 添加软件对话框
	priceObj.editSoftLayer = null;    // 编辑软件对话框
	priceObj.addSoftLoading = null;   // 添加软件加载层
	priceObj.editSoftLoading = null;  // 编辑软件加载层
	priceObj.delSoftLoading =  null;  // 删除软件加载层
	
	priceObj.prototype = {
		constructor: priceObj,
		/** 搜索框加载产品类型 */
		searchLoadPdtType: function() {
	        	
        	$.ajax({
				url : '/diagSoft/getPdtTypeAll',
				dataType : 'json',
				type : 'get',
				success : function(result) {
					var data = result.data;
					$.each(data, function(index, item) {
						$('#pdtTypeName').append(
								new Option(item.pdtType, item.pdtTypeId));// 下拉菜单里添加元素
					})
					basic.UI.form.render("select");//下拉菜单渲染 把内容加载进去
				}
			});
        },
        /** 搜索框产品类型只发生改变时 **/
        searchPdtTypeOnchange: function () {
            var _this = this;
            // 下拉选择框发生改变时
            basic.UI.form.on('select(pdtTypeFilterSearch)', function (data) {
            	var tId = data.value;
            	// 当
            	if (_this.pdtTypeIdCache !== tId) {
            		_this.pdtTypeIdCache = tId;
            		// 切换之前修改
                	$("#softName").val("");
                	$("#softName").empty();
                	$('#softName').append(new Option("请选择诊断软件", ""));
                	basic.UI.form.render("select");
            	    if (tId !== '') {
            	    	_this._softNameSelectSearch(data.value,_this);
            	    }
            	}
            });
            
        },
        /** 加载搜索框软件名称 */
        _softNameSelectSearch: function(pdtTypeId,_this) {
        	var selectorId = "softName";
        	_this._searchSoftNameByPdtType(selectorId,pdtTypeId);
        },
        
        _searchSoftNameByPdtType: function(selectorId,pdtTypeId) {
        	var url = '/diagSoft/getDiagSoftByPdtTypeId/'+pdtTypeId;
        	$.ajax({
				url : url,
				dataType : 'json',
				type : 'get',
				success : function(result) {
					var data = result.data;
					$.each(data, function(index, item) {
						$('#'+selectorId).append(
								new Option(item.softName, item.softId));// 下拉菜单里添加元素
					})
					basic.UI.form.render("select");//下拉菜单渲染 把内容加载进去
				}
			});
        },
        
	        
		/** 添加按钮单击 **/
        addSoftBtnClick: function () {
            var _this = this;
            $(document).on('click', '#addSoftBtn', _.throttle(function () {
                var addSoftDialog = $('#addSoftDialog');
                priceObj.addSoftLayer = basic.UI.layer.open({
                    type: 1,
                    title: false,
                    area: 'auto',
                    move: '.h5 .title',
                    maxWidth: 1180,
                    closeBtn: 0,
                    shadeClose: false,
                    content: addSoftDialog
                });
                addSoftDialog.find('.mainText').html($('#addSoftTemplate').html());
                
                /*$('#softNameA').autocomplete({
                    lookup: _this.softNameData || [],
                    zIndex: 99891015,
                    onSelect: function (suggestion) {
                        $('#softNameA').val(suggestion.softName);
                    },
                });*/
                
                // 加载产品类型
                _this._loadPdtType();
                
                basic.UI.form.render('select');
            }, 1000, _this.throttleOption));
        },
        
        /** 加载产品类型 */ 
        _loadPdtType: function() {
        	
        	$.ajax({
				url : '/diagSoft/getPdtTypeAll',
				dataType : 'json',
				type : 'get',
				success : function(result) {
					var data = result.data;
					$.each(data, function(index, item) {
						$('#pdtTypeNameA').append(
								new Option(item.pdtType, item.pdtTypeId));// 下拉菜单里添加元素
					})

					basic.UI.form.render("select");//下拉菜单渲染 把内容加载进去
				}
			});
        },
        
        /** 产品类型只发生改变时 **/
        pdtTypeOnchange: function () {
            var _this = this;
            basic.UI.form.on('select(pdtTypeFilter)', function (data) {
            	// 切换之前修改
            	$("#softNameA").val("");
            	$("#softNameA").empty();
            	$('#softNameA').append(new Option("请选择诊断软件", ""));
            	basic.UI.form.render("select");
        	    if (data.value !== '') {
        	    	_this._softNameAutoComplete_add(data.value,_this);
        	    }
        	    
            });
            
        },
        
		_softNameAutoComplete_add: function(pdtTypeId,_this) {
			// 根据产品类型查询软件
			var selectorId = "softNameA";
        	_this._searchSoftNameByPdtType(selectorId,pdtTypeId);
		},
		
		softNameAutoComplete: function() { // 诊断软件自动完成
			var _this = this;
			$.ajax({
	            url: '/diagSoft/getDiagSoftAll',
				type : 'GET',
				data:{},
				dataType:'json',
				success: function (res) {
					if (res.code === 0) {
						var dataArr = [];
						for (index in res.data) {
							dataArr.push({
								softId: res.data[index].softId,
								softName: res.data[index].softName,
								softApplicableArea: res.data[index].softApplicableArea,
								value: res.data[index].softName + '(' + res.data[index].softApplicableArea + ')',
							})
						};
						//_this.softNameData = dataArr;
						// autocomplete自动完成需要引用 jquery.autocomplete.js和jquery.autocompleter.css[封装在common.css中]
						$('#softName').autocomplete({
                            lookup: dataArr,
                            onSelect: function (suggestion) {
                                $('#softName').val(suggestion.softName);
                            }
                        });
					} else {
						basic.alert('诊断软件数据获取失败。(' + res.message + ')')
					}
				}
			});
		},
		// 添加软件提交
		addSoftSubmit: function() {
			var _this = this;
			//监听添加软件提交
			basic.UI.form.on('submit(addSoft)', function(data) {
				// 获取诊断软件ID
				var softId = '',$softNameA = $('#softNameA');
	            
				softId = data.field.softName;
				var currency = data.field.currency;
				var price = data.field.price;
				
	            if (!softId) {
	                $softNameA.focus().addClass('layui-form-danger');
	                basic.msg('请输入正确的软件名称', 5, 6, function () {
	                    $softNameA.focus()
	                });
	                return false;
	            }
	            
	            _this.isRepeated = false;
	            _this._verfiyIsRepeated(currency,softId,_this);
	            
	            // 如果重复
	            if (_this.isRepeated) {
	            	
	            	var pdtTypeNameA = $("#pdtTypeNameA").find("option:selected").text();
	            	var softNameA = $("#softNameA").find("option:selected").text();
	            	basic.alert('<span style="color: #f01414;">'+pdtTypeNameA+'</span>产品下的软件<span style="color: #f01414;">'+softNameA+'</span>对应的币种<span style="color: #f01414;">'+currency+'</span>已存在');
	            	
	            	return false;
	            }
	            
	            var saveUrl = basic_url.save_diagsoft_price+"/"+_this.buyType+"/"+currency+"/"+price+"/"+softId;
	            //加载层
	            priceObj.addSoftLoading = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
	            // 提交表单
	        	$.ajax({
	        		url : saveUrl,
	        		dataType : 'json',
	        		type : 'post',
	        		success : function(res) {
	        			basic.closeLoading(priceObj.addSoftLoading);
	        			if (res.code === 0) {
                            basic.msg('软件：' + data.field.softName + ' 的价格添加成功', null, null, function () {
                                basic.closeDialog(priceObj.addSoftLayer);
                                //重新渲染表格
                                tableTemp.reload({
                					where : {}
                				});
                            });
                        } else {
                            basic.alert('软件：' + data.field.softName + ' 的价格添加失败。(' + res.message + ')');
                        }
	        		},
	        		error:function(){
	        			basic.closeLoading(priceObj.addSoftLoading);
                        basic.alert('网络错误：' + data.field.softName + ' 的价格添加失败。');
	        		}
	        	});
				
				
				return false;
			});
			
		},
		
		/** 判断软件价格是否存在 */
		_verfiyIsRepeated: function(currency,softId,_this) {
			var url = "/diagSoft/verifyDiagPriceIsRepeated/"+ _this.buyType+"/"+currency+"/"+softId;
			
			$.ajax({
        		url : url,
        		dataType : 'json',
        		type : 'GET',
        		async : false,
        		success : function(res) {
        			// count大于0，表示数据库已存在软件对应的币种价格
        			if (res.code === 0 && res.count > 0) {
        				_this.isRepeated = true;
                    } 
        		},
        		error:function(XMLHttpRequest, textStatus, errorThrown){
        			basic.closeLoading(priceObj.addSoftLoading);
        		}
        	});
		},
		// 编辑软件价格提交事件
		editSoftSubmit: function() {
			var _this = this;
			
			//监听添加软件提交
			basic.UI.form.on('submit(editSoft)', function(data) {
				var id = data.field.id;
				var price = data.field.price;
				
				priceObj.editSoftLoading = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
				
				var editUrl = basic_url.edit_diagsoft_price+"/"+id+"/"+price
				$.ajax({
	        		url : editUrl,
	        		data: {},
	        		dataType : 'json',
	        		type : 'post',
	        		success : function(res) {
	        			basic.closeLoading(priceObj.editSoftLoading);
	        			if (res.code === 0) {
                            basic.msg('软件：' + data.field.softName + ' 的价格修改成功', null, null, function () {
                                basic.closeDialog(priceObj.editSoftLayer);
                                //重新渲染表格
                                tableTemp.reload({
                					where : {}
                				});
                            });
                        } else {
                            basic.alert('软件：' + data.field.softName + ' 的价格修改失败。(' + res.message + ')');
                        }
	        		},
	        		error:function(XMLHttpRequest, textStatus, errorThrown){
	        			basic.closeLoading(priceObj.addSoftLoading);
                        basic.alert('网络错误：' + data.field.softName + ' 的价格修改失败。');
	        		}
	        	});
				return false;
			});
		},
		// 自定义验证
		myValidata: function() {
			basic.UI.form.verify({
				/** 软件名称 **/
                softName: function (value, item) { //value：表单的值、item：表单的DOM对象
                	
                    var _val = $.trim(value);
                    if (_val.length !== 0 && (_val.length < 2 || _val.length > 50)) {
                        return '软件名称 2-50 位';
                    }
                },
                /** 钱 的验证 **/
                money: function (value, item) {
                    var reg = /^(0|([1-9]\d{0,7}))(\.\d{1,2})?$/g.test(value);
                    if ($.trim(value).length !== 0 && !reg) {
                        return '整数部分1-8位，小数部分最多2位。如：23.74、86，0，21.9';
                    }
                },
			});
		},
		closeDialogPrice: function() {
			$(document).on("click","#addSoftDialog .closeBtn",function(){
				basic.closeDialog(priceObj.addSoftLayer);
			});
			$(document).on("click","#editSoftDialog .closeBtn",function(){
				basic.closeDialog(priceObj.editSoftLayer);
			});
		},
		rowMonitor: function() {
			var _this = this;
			// 行数据操作，监听行工具事件
			basic.UI.table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
				var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
				if (layEvent === 'del') { //删除
					//询问框
					layer.confirm('删除后，软件[<span style="color: #f01414;"> '+data.softName+'</span> ]将不可用，确认删除吗？', {
						btn: ['确定','取消'], //按钮
						skin: 'layer-red'
					}, function() { // 确定
						//layer.msg('的确很重要', {icon: 1});
						_this._delSoftBtnClick(data);
					});
				}
				if (layEvent === 'edit') { //编辑
					_this._editSoftBtnClick(data);
				}
			});
		},
		// 内部函数，编辑按钮弹出框
		_editSoftBtnClick: function(data) {
			var _this = this;		
            var editSoftDialog = $('#editSoftDialog');
            priceObj.editSoftLayer = basic.UI.layer.open({
                type: 1,
                title: false,
                area: 'auto',
                move: '.h5 .title',
                maxWidth: 1180,
                closeBtn: 0,
                shadeClose: false,
                content: editSoftDialog
            });
            
            editSoftDialog.find('.mainText').html($('#editSoftTemplate').html()); 
            // 设置值
            $("#editId").val(data.id);
            $("#editSoftName").html(data.softName);
            $("#editSoftName2").val(data.softName);
            $("#currencyB").html(data.currency);
            $("#pdtTypeB").html(data.pdtType);
            $("#priceE").val(data.price);
		},
		//删除软件点击事件
		_delSoftBtnClick: function(data) {
			var _this = this;
			priceObj.delSoftLoading = layer.load(0, {shade: false});
			var id = data.id;
			var delUrl = basic_url.del_diagsoft_price+"/"+id;
			
			$.ajax({
        		url : delUrl,
        		data: {},
        		dataType : 'json',
        		type : 'post',
        		success : function(res) {
        			basic.closeLoading(priceObj.delSoftLoading);
        			if (res.code === 0) {
                        basic.msg('软件：' + data.softName + ' 的价格删除成功', null, null, function () {
                        	
                        	var softName = $("#softName").val();
                        	var softApplicableArea = $("#softApplicableArea").val();
                            //重新渲染表格
                            tableTemp.reload({
            					where : {
            						softName : softName,
                    				softApplicableArea : softApplicableArea
            					}
            				});
                        });
                    } else {
                        basic.alert('软件：' + data.softName + ' 的价格删除失败。(' + res.message + ')');
                    }
        		},
        		error:function(XMLHttpRequest, textStatus, errorThrown){
        			basic.closeLoading(priceObj.delSoftLoading);
                    basic.alert('网络错误：' + data.softName + ' 的价格删除失败。');
        		}
        	});
		},
		
	};
	
	priceObj.init = function() {
		new this;
	}
	
	
	
	
	layui.use(['jquery','element','table','form','layer'],function() {
		window.$ = layui.$;               // 引入layui内置的jquery对象，赋值给全局$
		basic.UI.table = layui.table;     // 把layui的table模块赋值给common.js中的basic.UI对象
		basic.UI.element = layui.element; // 把layui的element模块赋值给common.js中的basic.UI对象
		basic.UI.form = layui.form;	      // 把layui的form 模块赋值给common.js中的basic.UI对象
		basic.UI.layer = layui.layer;
		
		priceObj.init();
		
		initPrice();
	}); 
})();

// 数据表格对象
var tableTemp = null;

function initPrice() {
	var url = basic_url.get_diagsoft_price_list;
	
	tableTemp = basic.UI.table.render({
		elem : '#demo',
		// url : '/diagSoft/getDiagSoftPriceList',
		url : url,
		cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		//页码的参数名称，默认：page
		//每页数据量的参数名，默认：limit
		request : {
			pageName : 'pageNum',
			limitName : 'pageSize'
		},
		response : {
			msgName : 'message'
		}, //规定状态信息的字段名称，默认：msg
		page : true, //开启分页
		cols : [ [ 
			{field : 'zizeng',title : '序号',templet : '#zizeng',type : 'numbers'}, 
			{field : 'id',width : 60,title : 'ID',hide : true}, 
			{field : 'pdtType',title : '产品类型'}, 
			{field : 'softName',title : '软件名称'}, 
			{field : 'softApplicableArea',title : '适用区域'}, 
			{field : 'price',title : '单次诊断价格',sort : true}, 
			{field : 'currency',title : '币种'}, //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
			{field : 'createTime',title : '创建时间',width : '20%',minWidth : 50}, //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
			{fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'} 
			] ]
	});

	
	//监听提交，form表单提交，根据条件搜索
	basic.UI.form.on('submit(formDemo)', function(data) {
		var pdtTypeId = data.field.pdtTypeId;
		var softName = data.field.softName;
		var softApplicableArea = data.field.softApplicableArea;
		// 重新渲染数据
		tableTemp.reload({
			where : {
				pdtTypeId : pdtTypeId,
				softId : softName,
				softApplicableArea : softApplicableArea
			}
		})
		return false;
	});
	
		
	//查看详情 
	function detail(id) {
		var index = basic.UI.layer.open({
			title : "设备详情",
			type : 2,
			content : "/view/goDeviceDetail?id=" + id,
			success : function(layero, index) {
				setTimeout(function() {
					basic.UI.layer.tips('点击此处返回',
							'.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
				}, 500);
			}
		});
		basic.UI.layer.full(index);
	}
}