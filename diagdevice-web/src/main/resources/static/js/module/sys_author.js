// [系统用户管理]页面相关js文件
;(function() {
	var sysAuthorObj = function() {
		
		this.menuTreeData = null;
		this.addAuthBtnClick(); //添加按钮单击
		this.addAuthSubmit();
		
		this.authTypeRadioChange();
		this.closeDialog();     // 关闭对话框
		this.rowMonitor();      // 行监听
		this.editAuthSubmit();  // 编辑表单提交
		
	};
	
	sysAuthorObj.tableTemp = null;     // 列表
	sysAuthorObj.addAuthLayer = null;  // 添加弹出框
	sysAuthorObj.editAuthLayer = null; // 编辑弹出框
	sysAuthorObj.addAuthLoading = null;
	sysAuthorObj.editAuthLoading = null;
	sysAuthorObj.delAuthLoading = null;
	
	sysAuthorObj.prototype = {
		construction : sysAuthorObj,
		
		/** 添加按钮单击 **/
        addAuthBtnClick: function () {
            var _this = this;
            _this._getMenuTreeApi().then(function (menuData) {
                _this.menuTreeData = menuData;
            });
            $(document).on('click', '#addSoftBtn', _.throttle(function () {
            	
                if (_this.menuTreeData === null) {
                    return false;
                }
                var addAuthDialog = $('#addAuthDialog');
                sysAuthorObj.addAuthLayer = basic.UI.layer.open({
                    type: 1,
                    title: false,
                    area: 'auto',
                    move: '.h5 .title',
                    maxWidth: 1180,
                    closeBtn: 0,
                    shadeClose: false,
                    content: addAuthDialog
                });
                basic.UI.laytpl($('#addAuthTemplate').html()).render(_this.menuTreeData, function (html) {
                    addAuthDialog.find('.mainText').html(html);
                    basic.UI.form.render('select');
                    basic.UI.form.render('radio');
                });
            }, 1000, _this.throttleOption));
        },
        /** 添加表单提交 **/
        addAuthSubmit: function () {
            var _this = this;
            basic.UI.form.on('submit(addAuth)', function (data) {
            	sysAuthorObj.addAuthLoading = layer.load(0, {shade: false});
                var myData = {};
                if (data.field.authType === '5') {
                    myData.resourceUrl = _.escape($.trim(data.field.resourceUrl));
                }
                $.ajax({
                    type: 'POST',
                    data: myData,
                    url: '/sys/authority/save/' + data.field.parentMenu + '/' + $.trim(_.escape(data.field.authKey)) + '/' + $.trim(_.escape(data.field.authName)) + '/' + data.field.authType + '/0' + '/',
                    dataType:'json',
                    success: function (res) {
                        basic.closeLoading(sysAuthorObj.addAuthLoading);
                        if (res.code === 0) {
                            basic.msg('权限：' + data.field.authName + ' 添加成功', null, null, function () {
                                basic.closeDialog(sysAuthorObj.addAuthLayer);
                                
                                sysAuthorObj.tableTemp.reload({
                                	where:{
                                	}
                                });
                            });
                        } else {
                            basic.alert('权限：<span style="color: #f01414">' + data.field.authName + '</span> 添加失败。(' + res.message + ')');
                        }
                    },
                    error: function () {
                        basic.closeLoading(sysAuthorObj.addAuthLoading);
                        basic.alert('网络错误，权限：<span style="color: #f01414">' + data.field.authName + '</span> 添加失败。');
                    }
                });
                return false;
            });
        },
        /** 权限类型改变，切换"接口地址"的显示 **/
        authTypeRadioChange: function () {
            basic.UI.form.on('radio(authType)', function(data) {
                var _html = '';
                _html += '<label class="layui-form-label" id="resourceUrlA"><i class="icon-star not-empty"></i> 接口地址</label>';
                _html += '<div class="layui-input-block">';
                _html +=      '<input class="layui-input" id="resourceUrlA" name="resourceUrl" lay-verify="required|resourceUrl" autocomplete="off" placeholder="2-100位的字母、数字、_、/。备注：见接口文档" />';
                _html += '</div>';
                if (data.value === '5') {
                    $(data.elem).closest('.mainText').find('.resourceUrl').html(_html);
                } else {
                    $(data.elem).closest('.mainText').find('.resourceUrl').html('');
                }
            });
        },
        /** 获得菜单权限树API **/
        _getMenuTreeApi: function () {
            var _this = this,
                dtd = $.Deferred();
            $.ajax({
                url: '/sys/authority/getSysAuthorityMenuTree/1',
                type: 'GET',
                dataType:'json',
                success: function (res) {
                    if (res.code === 0) {
                        dtd.resolve(res.data);
                    } else {
                        dtd.reject('菜单权限树数据获取失败。(' + res.message + ')');
                    }
                },
                error: function () {
                    dtd.reject('网络错误：菜单权限树数据获取失败');
                }
            });
            return dtd.promise();
        },
        
        closeDialog: function() {
        	$(document).on('click','#addAuthDialog .closeBtn',function() {
        		basic.closeDialog(sysAuthorObj.addAuthLayer);
        	});
        	$(document).on('click', '#editAuthDialog .closeBtn', function () {
                basic.closeDialog(sysAuthorObj.editAuthLayer);
            });
        },
        /** 自定义验证 **/
        myValidata: function () {
            basic.UI.form.verify({
                /** 权限key **/
                authKey: function (value, item) {
                    if ($.trim(value).length !== 0 && !new RegExp("^[a-zA-Z_]{2,100}$").test(value)) {
                        return '2-100位的字母、下划线';
                    }
                },
                /** 权限名称 **/
                authName: function (value, item) {
                    var _val = $.trim(value);
                    if (_val.length !== 0 && (_val.length < 2 || _val.length > 30)) {
                        return '权限名称2-30位';
                    }
                },
                /** 接口地址 **/
                resourceUrl: function (value, item) {
                    var _val = $.trim(value);
                    if (_val.length !== 0 && !new RegExp('^[a-zA-Z0-9_/]{2,100}$').test(value)) {
                        return '2-100位的字母、数字、_、/';
                    }
                },
            });
        },
        
        rowMonitor: function() {
        	var _this = this;
			// 行数据操作，监听行工具事件
			basic.UI.table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
				var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
				if (layEvent === 'del') { //删除
					
					_this._delVerification(data);
					
				}
				if (layEvent === 'edit') { //编辑
					_this._editAuthBtnClick(data);
				}
			});
        },
        // 删除验证是否有子节点，有子节点不允许删除
        _delVerification: function(data) {
        	var _this = this;
        	var id = data.id;
        	var authName = data.resourceName;
        	$.ajax({
                type: 'GET',
                url: '/sys/authority/verifyHasChildren/' + id,
                dataType:'json',
                success: function (res) {
                    if (res.code === 0) {
                    	var flag = res.data.hasChildren; // 为true表示有子节点
                    	if (flag) {
                    		basic.alert('权限：' + authName + ' 有子节点，不能删除。');
                    	} else {
                    		//询问框
        					layer.confirm('删除后，权限[<span style="color: #f01414;"> '+authName+'</span> ]将不可用，确认删除吗？', {
        						btn: ['确定','取消'], //按钮
        						skin: 'layer-red'
        					}, function() { // 确定
        						//layer.msg('的确很重要', {icon: 1});
        						_this._delAuthorBtnClick(data);
        					});
                    	}
                    } else {
                    	 basic.alert('网络错误');
                    }
                },
                error: function () {
                    basic.alert('网络错误');
                }
            });
        },
        // 确定删除事件
        _delAuthorBtnClick: function(data) {
        	sysAuthorObj.delAuthLoading = layer.load(0, {shade: false});
        	var id = data.id;
        	var authName = data.resourceName;
        	$.ajax({
                type: 'POST',
                url: '/sys/authority/delete/' + id,
                dataType:'json',
                success: function (res) {
                    basic.closeLoading(sysAuthorObj.delAuthLoading);
                    if (res.code === 0) {
                        basic.msg('权限：<span style="color: #ffc">' + authName + '</span> 删除成功', null, null, function () {
                        	sysAuthorObj.tableTemp.reload({
                            	where:{
                            	}
                            });
                        });
                    } else {
                        basic.alert('权限：' + authName + ' 删除失败。(' + res.message + ')');
                    }
                },
                error: function () {
                    basic.closeLoading(sysAuthorObj.delAuthLoading);
                    basic.alert('网络错误，权限：' + authName + ' 删除失败。');
                }
            });
        },
        /** 表格每一行的编辑按钮单击 **/
        _editAuthBtnClick: function (data) {
            var _this = this;
            var authId = data.id;
            var editAuthDialog = $('#editAuthDialog');
            sysAuthorObj.editAuthLayer = basic.UI.layer.open({
                type: 1,
                title: false,
                area: 'auto',
                move: '.h5 .title',
                maxWidth: 1180,
                closeBtn: 0,
                shadeClose: false,
                content: editAuthDialog
            });
            _this._getAuthDetailApi(authId).then(function (res) {
                basic.UI.laytpl($('#editAuthTemplate').html()).render(res.data, function (html) {
                    editAuthDialog.find('.mainText').html(html);
                    basic.UI.form.render('select');
                });
            }).fail(function (err) {
                editAuthDialog.find('.mainText').html('<div class="error-msg" style="top: 200px;"><i class="icon-info-sign icon-large"></i> ' + err + '</div>');
            });

        },
        /** 添加表单提交 **/
        editAuthSubmit: function () {
            var _this = this;
            basic.UI.form.on('submit(editAuth)', function (data) {
            	sysAuthorObj.editAuthLoading = layer.load(0, {shade: false});
                var myData = {};
                if (data.field.authType === '5') {
                    myData.resourceUrl = _.escape($.trim(data.field.resourceUrl));
                }
                myData.resourceCode = $.trim(_.escape(data.field.authKey));
                myData.resourceName = $.trim(_.escape(data.field.authName))
                
                $.ajax({
                    type: 'POST',
                    data: myData,
                    url: '/sys/authority/update/' + data.field.id,
                    dataType:'json',
                    success: function (res) {
                        basic.closeLoading(sysAuthorObj.editAuthLoading);
                        if (res.code === 0) {
                            basic.msg('权限：' + data.field.authName + ' 修改成功', null, null, function () {
                                basic.closeDialog(sysAuthorObj.editAuthLayer);
                                
                                sysAuthorObj.tableTemp.reload({
                                	where:{
                                	}
                                });
                            });
                        } else {
                            basic.alert('权限：<span style="color: #f01414">' + data.field.authName + '</span> 修改失败。(' + res.message + ')');
                        }
                    },
                    error: function () {
                        basic.closeLoading(sysAuthorObj.editAuthLoading);
                        basic.alert('网络错误，权限：<span style="color: #f01414">' + data.field.authName + '</span> 修改失败。');
                    }
                });
                return false;
            });
        },
        /** 权限详情API **/
        _getAuthDetailApi: function (id) {
            var _this = this,
                dtd = $.Deferred();
            $.ajax({
                type: 'GET',
            	url: '/sys/authority/getSysAuthorityDetail/' + id,
            	dataType:'json',
                success: function (res) {
                    if (res.code === 0) {
                        dtd.resolve(res);
                    } else {
                        dtd.reject('权限详情获取失败：<span style="color: #f01414">' + res.message + '</span>');
                    }
                },
                error: function () {
                    dtd.reject('网络错误：权限详情获取失败');
                }
            });
            return dtd.promise();
        }
        
	};
	
	sysAuthorObj.init = function() {
		new this;
	};
	
	// 初始化页面列表
	loadSysAuthor = function() {
		
		var listUrl = basic_url.get_sys_authority_list;
		
		sysAuthorObj.tableTemp = basic.UI.table.render({
			elem : '#sysAuthorList',
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
				{field : 'id',title : 'ID',hide : true}, 
				{field : 'resourceName',title : '权限名称',width : 150}, 
				{field : 'resourceCode',title : '权限KEY',width : '20%'}, 
				{field : 'parentResourceCode',title : '父菜单权限KEY'}, 
				{field : 'resourceType',title : '权限类型',sort : true,templet:function(d) {
					
					var authType = '';
		            switch(d.resourceType) {
			            case 2: authType = '一级菜单'; break;
			            case 3: authType = '二级菜单'; break;
			            case 5: authType = '权限'; break;
			            default: authType = '';
		            }
		            return authType
				}
				}, 
				{field : 'resourceUrl',title : '接口地址',sort : true}, 
				{fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'} 
				] ]
		});
		
		//监听提交，form表单提交，根据条件搜索
		basic.UI.form.on('submit(formDemo)', function(data) {
			var resourceName = data.field.resourceName;
			var resourceCode = data.field.resourceCode;
			// 重新渲染数据
			sysAuthorObj.tableTemp.reload({
				where : {
					resourceName : resourceName,
					resourceCode : resourceCode
				}
			})
			return false;
		});
	};
	
	layui.use(['jquery','table','form','layer','laytpl'],function() {
		window.$ = layui.$;               // 引入layui内置的jquery对象，赋值给全局$
		basic.UI.table = layui.table;     // 把layui的table模块赋值给common.js中的basic.UI对象
		basic.UI.element = layui.element; // 把layui的element模块赋值给common.js中的basic.UI对象
		basic.UI.form = layui.form;	      // 把layui的form 模块赋值给common.js中的basic.UI对象
		basic.UI.layer = layui.layer;
		basic.UI.laytpl = layui.laytpl;
		
		sysAuthorObj.init();
		// 加载列表
		loadSysAuthor();
	});
	
})();


