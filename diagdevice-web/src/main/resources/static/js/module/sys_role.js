// [系统用户管理]页面相关js文件
;(function() {
	var sysRoleObj = function() {
		
		this.addRoleBtnClick();
		this.myValidata();  		//自定义验证
		this.addRoleSubmit();
		this.editRoleSubmit();
		this.closeDialog();
		this.rowMonitor();      // 行操作
	};
	
	sysRoleObj.tableTemp = null;     // 列表
	sysRoleObj.addRoleLayer = null;  // 添加弹出框
	sysRoleObj.editRoleLayer = null; // 编辑弹出框
	sysRoleObj.addRoleLoading = null;
	sysRoleObj.editRoleLoading = null;
	sysRoleObj.delRoleLoading = null;
	
	sysRoleObj.prototype = {
		construction : sysRoleObj,
		
		/** 添加按钮单击 **/
        addRoleBtnClick: function () {
            var _this = this;
            $(document).on('click', '#addRoleBtn', _.throttle(function () {
            	
                
                var addRoleDialog = $('#addRoleDialog');
                sysRoleObj.addRoleLayer = basic.UI.layer.open({
                    type: 1,
                    title: false,
                    area: 'auto',
                    move: '.h5 .title',
                    maxWidth: 1180,
                    closeBtn: 0,
                    shadeClose: false,
                    content: addRoleDialog
                });
                addRoleDialog.find('.mainText').html($('#addRoleTemplate').html()); 
                
            }, 1000, _this.throttleOption));
        },
        // 添加角色提交
        addRoleSubmit: function() {
			var _this = this;
			//监听添加软件提交
			basic.UI.form.on('submit(addRole)', function(data) {
				var roleCode = data.field.roleCode;
				var roleName = data.field.roleName;
				var remark   = data.field.remark;
				
	            var saveUrl = basic_url.save_sys_role+"/"+remark+"/"+roleCode+"/"+roleName;
	            //加载层
	            sysRoleObj.addRoleLoading = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
	            // 提交表单
	        	$.ajax({
	        		url : saveUrl,
	        		dataType : 'json',
	        		type : 'POST',
	        		success : function(res) {
	        			basic.closeLoading(sysRoleObj.addRoleLoading);
	        			if (res.code === 0) {
                            basic.msg('角色：' + roleName + ' 添加成功', null, null, function () {
                                basic.closeDialog(sysRoleObj.addRoleLayer);
                                //重新渲染表格
                                sysRoleObj.tableTemp.reload({
                					where : {}
                				});
                            });
                        } else {
                            basic.alert('角色：' + roleName + ' 添加失败。(' + res.message + ')');
                        }
	        		},
	        		error:function(){
	        			basic.closeLoading(sysRoleObj.addSoftLoading);
                        basic.alert('网络错误：' + roleName + '添加失败。');
	        		}
	        	});
				
				
				return false;
			});
			
		},
        closeDialog: function() {
        	$(document).on('click','#addRoleDialog .closeBtn',function() {
        		basic.closeDialog(sysRoleObj.addRoleLayer);
        	});
        	$(document).on('click', '#editRoleDialog .closeBtn', function () {
                basic.closeDialog(sysRoleObj.editRoleLayer);
            });
        },
        /** 自定义验证 **/
        myValidata: function () {
            basic.UI.form.verify({
                /** 角色编码 **/
                roleCode: function (value, item) {
                    if ($.trim(value).length !== 0 && !new RegExp("^[a-zA-Z_]{2,100}$").test(value)) {
                        return '2-100位的字母、下划线';
                    }
                },
                /** 角色名称 **/
                roleName: function (value, item) {
                    var _val = $.trim(value);
                    if (_val.length !== 0 && (_val.length < 2 || _val.length > 100)) {
                        return '角色名称 2-100位';
                    }
                },
                /** 描述 **/
                remark: function (value, item) {
                    var _val = $.trim(value);
                    if (_val.length !== 0 && (_val.length < 2 || _val.length > 200)) {
                        return '描述 2-200位';
                    }
                }
            });
        },
        rowMonitor: function() {
			var _this = this;
			// 行数据操作，监听行工具事件
			basic.UI.table.on('tool(roleFilter)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
				var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
				if (layEvent === 'del') { //删除
					//询问框
					layer.confirm('删除后，所有用户的[<span style="color: #f01414;"> '+data.roleName+'</span> ]角色将不可用，确认删除吗？', {
						btn: ['确定','取消'], //按钮
						skin: 'layer-red'
					}, function() { // 确定
						//layer.msg('的确很重要', {icon: 1});
						_this._delSoftBtnClick(data);
					});
				}
				if (layEvent === 'edit') { //编辑
					_this._editRoleBtnClick(data);
				}
				if (layEvent === 'auth') { //授权
					var id = data.id;
					// 跳转到角色授权页面
					window.location.href = '/view/goRoleAuthorEdit?id='+id+'&redirect=' + encodeURIComponent(window.location.href)+"&roleName="+data.roleName;
				}
				if (layEvent === 'getauth') { //查看授权
					var id = data.id;
					window.location.href = '/view/goRoleAuthorDetail?id='+id+'&redirect=' + encodeURIComponent(window.location.href)+"&roleName="+data.roleName;
				}
			});
		},
		// 编辑角色按钮
		_editRoleBtnClick: function(data) {
			var editRoleDialog = $('#editRoleDialog');
            sysRoleObj.editRoleLayer = basic.UI.layer.open({
                type: 1,
                title: false,
                area: 'auto',
                move: '.h5 .title',
                maxWidth: 1180,
                closeBtn: 0,
                shadeClose: false,
                content: editRoleDialog
            });
            
            basic.UI.laytpl($('#editRoleTemplate').html()).render(data,function(html) {
            	editRoleDialog.find('.mainText').html(html); 
            });
		},
		// 编辑提交
		editRoleSubmit: function() {
			var _this = this;
			//监听添加软件提交
			basic.UI.form.on('submit(editRole)', function(data) {
				var roleCode = data.field.roleCode;
				var roleName = data.field.roleName;
				var remark   = data.field.remark;
				var id       = data.field.id;
				
				var editData = new Object();
				editData.roleCode = roleCode;
				editData.roleName = roleName;
				editData.remark   = remark;
				
	            var updUrl = basic_url.update_sys_role+"/"+id;
	            //加载层
	            sysRoleObj.editRoleLoading = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
	            // 提交表单
	        	$.ajax({
	        		url : updUrl,
	        		data: editData,
	        		dataType : 'json',
	        		type : 'POST',
	        		success : function(res) {
	        			basic.closeLoading(sysRoleObj.editRoleLoading);
	        			if (res.code === 0) {
                            basic.msg('角色：' + roleName + ' 修改成功', null, null, function () {
                                basic.closeDialog(sysRoleObj.editRoleLayer);
                                //重新渲染表格
                                sysRoleObj.tableTemp.reload({
                					where : {}
                				});
                            });
                        } else {
                            basic.alert('角色：' + roleName + ' 修改失败。(' + res.message + ')');
                        }
	        		},
	        		error:function(){
	        			basic.closeLoading(sysRoleObj.editRoleLoading);
                        basic.alert('网络错误：' + roleName + '修改失败。');
	        		}
	        	});
				return false;
			});
		},
		_delSoftBtnClick: function(data) {
			var roleName = data.roleName;
			var id       = data.id;
			var delUrl   = basic_url.del_sys_role+"/"+id;
			sysRoleObj.delRoleLoading = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
			// 提交表单
        	$.ajax({
        		url : delUrl,
        		dataType : 'json',
        		type : 'POST',
        		success : function(res) {
        			basic.closeLoading(sysRoleObj.delRoleLoading);
        			if (res.code === 0) {
                        basic.msg('角色：' + roleName + ' 删除成功', null, null, function () {
                            //重新渲染表格
                            sysRoleObj.tableTemp.reload({
            					where : {}
            				});
                        });
                    } else {
                        basic.alert('角色：' + roleName + ' 删除失败。(' + res.message + ')');
                    }
        		},
        		error:function(){
        			basic.closeLoading(sysRoleObj.delRoleLoading);
                    basic.alert('网络错误：' + roleName + '删除失败。');
        		}
        	});
		},
        
	};
	
	sysRoleObj.init = function() {
		new this;
	};
	
	// 初始化页面列表
	loadSysRole = function() {
		
		var listUrl = basic_url.get_sys_role_list;
		
		sysRoleObj.tableTemp = basic.UI.table.render({
			elem : '#sysRoleList',
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
				{field : 'roleName',title : '角色名称'}, 
				{field : 'roleCode',title : '角色编码'}, 
				{field : 'remark',title : '角色描述',sort : true}, 
				{fixed : 'right',title : '操作',width : 265,align : 'center',toolbar : '#barDemo'} 
				] ]
		});
		
		//监听提交，form表单提交，根据条件搜索
		basic.UI.form.on('submit(formDemo)', function(data) {
			var roleName = data.field.roleName;
			var roleCode = data.field.roleCode;
			// 重新渲染数据
			sysRoleObj.tableTemp.reload({
				where : {
					roleName : roleName,
					roleCode : roleCode
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
		
		sysRoleObj.init();
		// 加载列表
		loadSysRole();
	});
	
})();


