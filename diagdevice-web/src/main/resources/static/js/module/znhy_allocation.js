// [智能合约分账管理]页面相关js文件
;(function() {
	var allocationObj = function() {
		
		this.rowMonitor();      // 行监听
		this.getRuleSelectData();  // 获得分账规则选择框的数据
		this.addProfitBtnClick(); // 添加按钮单击
		this.addProfitSubmit(); // 添加表单提交
		this.editProfitSubmit();  // 编辑不需要了，修改详情
		this.closeDialog();
		this.myValidata();
		
		this.addRuleOnchange();  //添加时，规则发生改变时
		this.editRuleOnchange();  //修改时，规则发生改变时
	};
	
	allocationObj.tableTemp = null; //列表对象
	allocationObj.addProfitLayer = null; //添加弹出框
	allocationObj.editProfitLayer = null; //编辑弹出框
	allocationObj.detailProfitLayer = null; //详情弹出框
	allocationObj.addProfitLoading = null;
	allocationObj.editProfitLoading = null;
	
	allocationObj.delProfitLoading = null;
	
	allocationObj.prototype = {
		constructor : allocationObj,
		 /** 获得分账规则选择框的数据 **/
		getRuleSelectData: function() {
			var _this = this;
			var urlAll = basic_url.getall_znhy_allocation_group;
			$.ajax({
                //url: '/znhy/getAllocationGroupAll',
				url: urlAll,
				type: 'GET',
				dataType:'json',
                success: function (res) {
                    if (res.code === 0) {
                        basic.UI.laytpl($('#ruleSelectTemplate').html()).render(res.data, function (html) {
                            $('#ruleSelect').html(html);
                            _this.ruleSelectData = res.data;
                            basic.UI.form.render('select');
                        });
                    } else {
                        basic.alert('分账规则选择框数据获取失败。(' + res.message + ')');
                    }
                },
                error: function (err) {
                    basic.alert('分账规则选择框数据获取失败。(' + err.statusText + ')');
                }
            });
		},
		
		/** 添加时：规则发生改变时 **/
        addRuleOnchange: function () {
            var _this = this;
            basic.UI.form.on('select(ruleAFilter)', function (data) {
            	// 切换之前修改
            	$("#creatorIdA").val("");
            	$("#creatorIdA").empty();
            	$('#creatorIdA').append(new Option("请选择拥有者", ""));
            	basic.UI.form.render("select");
        	    if (data.value !== '') {
        	    	_this._loadCreatorId(data.value,"creatorIdA");
        	    }
        	    
            });
            
        },
        /** 修改时：规则发生改变时 **/
        editRuleOnchange: function () {
        	var _this = this;
        	basic.UI.form.on('select(ruleEFilter)', function (data) {
        		// 切换之前修改
        		$("#creatorIdE").val("");
        		$("#creatorIdE").empty();
        		$('#creatorIdE').append(new Option("请选择拥有者", ""));
        		basic.UI.form.render("select");
        		if (data.value !== '') {
        			_this._loadCreatorId(data.value,"creatorIdE");
        		}
        		
        	});
        	
        },
        /** groupRuleId:规则组ID,selectorId 选择器ID */
        _loadCreatorId: function(groupId,selectorId) {
        	var url = '/znhy/getZnhyAccountByGroupId/'+groupId;
        	$.ajax({
				url : url,
				dataType : 'json',
				type : 'get',
				async: false, 
				success : function(result) {
					var data = result.data;
					$.each(data, function(index, item) {
						$('#'+selectorId).append(
								new Option(item.name, item.accountId));// 下拉菜单里添加元素
					})
					basic.UI.form.render("select");//下拉菜单渲染 把内容加载进去
				}
			});
        },
        
		
		/** 添加按钮单击 **/
        addProfitBtnClick: function () {
            var _this = this;
            $(document).on('click', '#addAllocationBtn', _.throttle(function () {
                var addProfitDialog = $('#addProfitDialog');
                allocationObj.addProfitLayer = basic.UI.layer.open({
                    type: 1,
                    title: false,
                    area: 'auto',
                    move: '.h5 .title',
                    maxWidth: 1180,
                    closeBtn: 0,
                    shadeClose: false,
                    content: addProfitDialog
                });
                basic.UI.laytpl($('#ruleSelectTemplate').html()).render(_this.ruleSelectData, function (html) {
                	addProfitDialog.find('.mainText').html($('#addProfitTemplate').html());
                    $('#ruleA').html(html);
                    basic.UI.form.render('select');
                });
            }, 1000, _this.throttleOption));
        },
        
        
        
        /* 添加按钮提交 */
        addProfitSubmit: function() {
        	var _this = this;
            basic.UI.form.on('submit(addProfit)', function (data) {
            	allocationObj.addProfitLoading = layer.load(0, {shade: false}); // 加载阴影
            	var creatorId = data.field.creatorId;
                $.ajax({
                    type: 'POST',
                    url: '/znhy/saveZnhyAllocation/'+creatorId+"/" + data.field.rule + '/' + data.field.serial,
                    dataType:'json',
                    success: function (res) {
                        basic.closeLoading(allocationObj.addProfitLoading);
                        if (res.code === 0) {
                            basic.msg('添加'+data.field.serial + ' 应用分账成功', null, null, function () {
                                basic.closeDialog(allocationObj.addProfitLayer);
                                //重新渲染表格
                                allocationObj.tableTemp.reload({
                					where : {}
                				});
                            });
                        } else {
                            basic.alert('添加<span style="color: #f01414;">' + data.field.serial + '</span> 应用分账失败。(' + res
                                .message + ')');
                        }
                    },
                    error: function () {
                        basic.closeLoading(allocationObj.addProfitLoading);
                        basic.alert('网络错误：<span style="color: #f01414;">' + data.field.serial + '</span> 应用分账失败。');
                    }
                });
                return false;
            });
        },
		// 行监听
		rowMonitor: function() {
			var _this = this;
			// 行数据操作，监听行工具事件
			basic.UI.table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
				var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
				if (layEvent === 'detail') { //详情
					_this._detailProfitBtnClick(data);
				}
				if (layEvent === 'edit') { //编辑
					_this._editProfitBtnClick(data);
				}
				/*if (layEvent === 'discard') {
					//询问框
					console.log(data);
					layer.confirm('废弃后，设备[<span style="color: #f01414;"> '+data.name+'</span> ]将不可分账，确认废弃吗？', {
						btn: ['确定','取消'], //按钮
						skin: 'layer-red'
					}, function() { // 确定
						_this._delProfitBtnClick(data);
					});
				}*/
			});
		},
		//废弃分配，实为修改
		_delProfitBtnClick: function(data) {
			var _this = this;
			allocationObj.delProfitLoading = layer.load(0, {shade: false});
			var id = data.id;
			$.ajax({
				type: 'POST',
                url: '/znhy/updateZnhyAllocation/' + id,
        		data: {},
        		dataType : 'json',
        		success : function(res) {
        			basic.closeLoading(allocationObj.delProfitLoading);
        			if (res.code === 0) {
                        basic.msg('序列号：' + data.name + ' 分配废弃成功', null, null, function () {
                        	
                            var name = $("#searchName").val();
                			var jobGroupId = $("#ruleSelect").val();
                			// 重新渲染数据
                			allocationObj.tableTemp.reload({
                				where : {
                					name : name,
                					jobGroupId:jobGroupId
                				}
                			})
                        });
                    } else {
                        basic.alert('序列号：' + data.name + ' 分配废弃失败。(' + res.message + ')');
                    }
        		},
        		error:function(XMLHttpRequest, textStatus, errorThrown){
        			basic.closeLoading(allocationObj.delProfitLoading);
                    basic.alert('网络错误：' + data.name + ' 分配废弃失败。');
        		}
        	});
		},
		
		/** 编辑按钮单击 **/
		_detailProfitBtnClick: function (data) {
            var _this = this;
            var detailProfitDialog = $('#detailProfitDialog');
            allocationObj.detailProfitLayer = basic.UI.layer.open({
                type: 1,
                title: false,
                area: 'auto',
                move: '.h5 .title',
                maxWidth: 1180,
                closeBtn: 0,
                shadeClose: false,
                content: detailProfitDialog
            });
            basic.UI.laytpl($('#ruleSelectTemplate').html()).render(_this.ruleSelectData, function (selectHtml) {
            	var tplData = {
            		id: data.id,
            		jobGroupId: data.jobGroupId,
            		name: data.name
            	};
            	
            	basic.UI.laytpl($('#detailProfitTemplate').html()).render(tplData, function (html) {
            		 detailProfitDialog.find('.mainText').html(html);
                     $('#ruleD').html(selectHtml).val(tplData.jobGroupId);
                     $('#creatorIdD').append(
								new Option(data.accountName, data.creatorId));// 下拉菜单里添加元素
                     $('#creatorIdD').val(data.creatorId);
                     basic.UI.form.render('select');
                 });
            });
            
        },
        /** 编辑按钮单击 **/
		_editProfitBtnClick: function (data) {
            var _this = this;
            var editProfitDialog = $('#editProfitDialog');
            allocationObj.editProfitLayer = basic.UI.layer.open({
                type: 1,
                title: false,
                area: 'auto',
                move: '.h5 .title',
                maxWidth: 1180,
                closeBtn: 0,
                shadeClose: false,
                content: editProfitDialog
            });
            basic.UI.laytpl($('#ruleSelectTemplate').html()).render(_this.ruleSelectData, function (selectHtml) {
            	var tplData = {
            		id: data.id,
            		jobGroupId: data.jobGroupId,
            		name: data.name
            	};
            	
            	basic.UI.laytpl($('#editProfitTemplate').html()).render(tplData, function (html) {
            		 editProfitDialog.find('.mainText').html(html);
                     $('#ruleE').html(selectHtml).val(tplData.jobGroupId);
                     _this._loadCreatorId(tplData.jobGroupId,"creatorIdE");
                     // _loadCreatorId设为同步，返回成功之后才执行下面val（），否则数据设置不进来
                     $('#creatorIdE').val(data.creatorId);
                     basic.UI.form.render('select');
                 });
            });
            
        },
        /** 编辑表单提交 **/
        editProfitSubmit: function () {
            var _this = this;
            basic.UI.form.on('submit(editProfit)', function (data) {
            	var creatorId ="",serial = "";
            	creatorId = data.field.creatorId;
            	serial    = data.field.serial;
            	
            	allocationObj.editProfitLoading = layer.load(0, {shade: false});
                $.ajax({
                    type: 'POST',
                    url: '/znhy/updateZnhyAllocation/' +creatorId+'/'+ data.field.id + '/' + data.field.rule + '/' + serial,
                    dataType: 'json',
                    success: function (res) {
                        basic.closeLoading(allocationObj.editProfitLoading);
                        if (res.code === 0) {
                            basic.msg(data.field.serial + ' 修改分账成功', null, null, function () {
                                basic.closeDialog(allocationObj.editProfitLayer);
                                allocationObj.tableTemp.reload({
                					where : {}
                				});
                            });
                        } else {
                            basic.alert('<span style="color: #f01414;">' + data.field.serial + '</span> 修改分账失败。(' + res
                                .message + ')');
                        }
                    },
                    error: function () {
                        basic.closeLoading(allocationObj.editProfitLoading);
                        basic.alert('网络错误：<span style="color: #f01414;">' + data.field.serial + '</span> 修改分账失败。');
                    }
                });
                return false;
            });
        },
		/** 对话框里的关闭按钮单击 **/
        closeDialog: function () {
            var _this = this;
            $(document).on('click', '#addProfitDialog .closeBtn', function () {
                basic.closeDialog(allocationObj.addProfitLayer);
            });
            $(document).on('click', '#editProfitDialog .closeBtn', function () {
                basic.closeDialog(allocationObj.editProfitLayer);
            });
            $(document).on('click', '#detailProfitDialog .closeBtn', function () {
            	basic.closeDialog(allocationObj.detailProfitLayer);
            });
        },
        /** 自定义验证 **/
        myValidata: function () {
            basic.UI.form.verify({
                /** 设备序列号 **/
                serialNo: function (value, item) { //value：表单的值、item：表单的DOM对象
                    //if ($.trim(value).length !== 0 && !/^98495(\d{7})$/g.test(value)) {
                	if ($.trim(value).length !== 0 && !/^\d{12}$/.test(value)) {
                        //return '设备序列号是98495开头的12位数字';
                		return '设备序列号是12位数字';
                    }
                }
            });
        },
		
	};
	
	allocationObj.init = function() {
		new this;
	};
	
	// 初始化页面列表
	loadZnhyAllocation = function() {
		// 获取合约规则分配租
		var listUrl = basic_url.get_znhy_allocation_list;
		
		allocationObj.tableTemp = basic.UI.table.render({
			elem : '#allocationList',
			url : listUrl,
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
				{field : 'allocationId',title : '分配表链上ID'}, 
				{field : 'name',title : '设备序列号'}, 
				{field : 'groupName',title : '设备分组'}, 
				{field : 'createDate',title : '创建时间',width : '15%',minWidth : 50},//minWidth：局部定义当前单元格的最小宽度 
				{fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'} 
				] ]
		});
		
		//监听提交，form表单提交，根据条件搜索
		basic.UI.form.on('submit(formDemo)', function(data) {
			var name = data.field.name;
			var jobGroupId = data.field.jobGroupId;
			// 重新渲染数据
			allocationObj.tableTemp.reload({
				where : {
					name : name,
					jobGroupId:jobGroupId
				}
			})
			return false;
		});
	};
	
	layui.use(['jquery','element','table','form','layer','laytpl'],function() {
		window.$ = layui.$;               // 引入layui内置的jquery对象，赋值给全局$
		basic.UI.table = layui.table;     // 把layui的table模块赋值给common.js中的basic.UI对象
		//basic.UI.element = layui.element; // 把layui的element模块赋值给common.js中的basic.UI对象
		basic.UI.form = layui.form;	      // 把layui的form 模块赋值给common.js中的basic.UI对象
		basic.UI.layer = layui.layer;
		basic.UI.laytpl = layui.laytpl;
		
		allocationObj.init();
		// 加载列表
		loadZnhyAllocation();
	});
	
})();


