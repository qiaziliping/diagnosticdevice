// [系统用户管理]页面相关js文件
;(function() {
	var sysUserObj = function() {
		this.addClickEvent(); // 添加按钮点击事件
		this.closeDialog();
		this.addUserFormSubmit();
		this.myValidata();
		this.rowMonitor();      // 行监听
		this.updateUserFormSubmit(); // 修改用户表单提交
	};
	
	sysUserObj.tableTemp = null; //列表
	sysUserObj.addSysUserLayer = null; //添加弹出框
	
	sysUserObj.checkUserExitLoading = null; // 添加用户、修改用户对话框里检查用户名是否已经存在loading
	sysUserObj.addUserLoading = null;
	sysUserObj.editUserLoading = null;
	sysUserObj.delUserLoading  = null;
	sysUserObj.editSysUserLayer = null;
	
	
	sysUserObj.prototype = {
		construction : sysUserObj,
		
		// 添加按钮点击事件
		addClickEvent: function() {
			var _this = this;
			
			
			basic.UI.formSelects.render({
                name: 'roleSelect',
            });
			
			$(document).on('click','#addSysUserBtn',_.throttle(function(){
				var addSysUserDialog = $("#addSysUserDialog");
				
				// 清空表单
				$(":input","#addForm")
				.not(":button",":reset","hidden","submit")
				.val("")
				.removeAttr("checked")
				.removeAttr("selected");
				
				sysUserObj.addSysUserLayer = basic.UI.layer.open({
                    type: 1,
                    title: false,
                    area: 'auto',
                    move: '.h5 .title',
                    maxWidth: 1000,
                    closeBtn: 0,
                    shadeClose: false,
                    content: addSysUserDialog
                });
				//addSysUserDialog.find('.mainText').html($('#addAccountTemplate').html());
				 _this._getRoleSelectData($('#roleSelect'), 'roleSelect');
			},1000,_this.throttleOption));
		},
		
		/** 获得新增用户角色选择框的数据 **/
        _getRoleSelectData: function (jqDom, selectName, roleArr) {
        	
            var _this = this;
            
            if (_this.RoleSelectData) {            	
                basic.UI.laytpl($('#roleSelectTemplate').html()).render(_this.RoleSelectData, function (html) {
                    jqDom.html(html);
                    basic.UI.formSelects.render({
                        name: selectName
                    });                    
                    if (roleArr) {                    	
                        basic.UI.formSelects.value('roleSelectU', 'val', roleArr);
                    }
                });
            } else {
                _this.RoleSelectData = [];
                $.ajax({
                    type: 'GET',
                    url: '/sys/role/getAllRoleList',
                    dataType:'json',
                    success: function (res) {
                        if (res.code === 0) {
                            _this.RoleSelectData = res.data;                            
                            basic.UI.laytpl($('#roleSelectTemplate').html()).render(res.data, function (html) {               	

                            	jqDom.html(html);
                                basic.UI.formSelects.render({
                                    name: selectName
                                });
                                if (roleArr) {
                                    basic.UI.formSelects.value('roleSelectU', 'val', roleArr);
                                }
                            });                            
                        } else {
                        	basic.alert('角色选择框数据获取失败。' + res.message);
                        }
                    },
                    error: function () {
                    	basic.alert('发生网络错误，角色选择框数据获取失败。');
                    }
                });
            }
        },
        /** 添加用户检查用户名是否已经存在 **/
        _checkUserNameExit: function (userName, fn) {
            $.ajax({
                type: 'GET',
                url: '/sys/user/verifyUsernameIsexist/' + userName,
                dataType:'json',
                success: function (res) {
                    basic.closeLoading(sysUserObj.checkUserExitLoading);
                    if (res.code === 0) {
                        if (JSON.stringify(res.data) == "{}") {
                            fn();
                        } else {
                            basic.msg('用户：' + userName + ' 已经存在');
                        }
                    } else if (res.code == 3) {
                        window.location.href = 'login.html?redirect=' + encodeURIComponent(window.location.href);
                    } else {
                        basic.alert('检查用户名是否已经存在失败。' +  res.msg)
                    }
                },
                error: function () {
                	basic.closeLoading(sysUserObj.checkUserExitLoading);
                    basic.alert('发生网络错误，检查用户名是否已经存在失败。')
                }
            });
        },
        /** 自定义验证 **/
        myValidata: function () {
            var _this = this;
            basic.UI.form.verify({
                /** 用户名 **/
                userAccount: function (value, item) { //value：表单的值、item：表单的DOM对象
                    var _val = $.trim(value),
                        reg = /^\w{2,45}$/g.test(value);
                    if (_val.length !== 0 && !reg) {
                        return '用户名是 2-45 位的字母、数字、下划线';
                    }
                },
                /** 密码 **/
                password: function (value, item) {
                    var _val = $.trim(value);
                    var reg = /^[a-zA-Z0-9!@#$%^/&.*]{6,60}$/g.test(value);
                    if (_val.length !== 0 && !reg) {
                        return '密码6-60位，不能有空格';
                    }
                },
                /** 确认密码 **/
                passwdConfirm: function (value, item) {
                    var _val = $.trim(value);
                    var passwd = $.trim($('#password').val());
                    if (_val.length !== 0 && _val !== passwd) {
                        return '两次密码输入不一致';
                    }
                },
                /** 修改用户对话框里的确认密码 **/
                passwdConfirmU: function (value, item) {
                    var _val = $.trim(value);
                    var passwd = $.trim($('#passwdU').val());
                    if (_val.length !== 0 && _val !== passwd) {
                        return '两次密码输入不一致';
                    }
                },
                /** 手机 **/
                telphone: function (value, item) {
                    var regTel = /^1[345789]\d{9}$/g.test(value); // 手机号码
                    if ($.trim(value).length !== 0 && !regTel) {
                        return '请填写正确的手机号';
                    }
                },
                /** 邮箱 **/
                email: function (value, item) {
                    var _val = $.trim(value);
                    var regTel = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(_val);
                    if ($.trim(value).length !== 0 && !regTel) {
                        return '请输入正确的邮件地址';
                    }
                },
                /** 角色选择框（多选）必选 **/
                roleRequired: function (value, item) {
                    var roleList = [];
                    var roleSelect = basic.UI.formSelects.value('roleSelect');
                    if (roleSelect && roleSelect.length > 0) {
                        for (var i = 0; i < roleSelect.length; i++) {
                            roleList.push({
                                roleId: roleSelect[i].val
                            });
                        }
                    }
                    if (roleList.length === 0) {
                        return '角色必选';
                    }
                }
            });
        },
        /** 添加用户表单提交 **/
        addUserFormSubmit: function () {
            var _this = this;
            basic.UI.form.on('submit(addSysUser)', function (data) {
            	var username = $.trim(data.field.username);
                _this._checkUserNameExit(username, function () {
                	
                    var password = $.trim(data.field.password),
                    	mobile = $.trim(data.field.mobile),
                        email = $.trim(data.field.email);
                    	remark = $.trim(data.field.remark);
                    	
                	var myData = {
                            "roleIds": []
                        };
                    
                    var roleSelect = basic.UI.formSelects.value('roleSelect');
                    if (roleSelect && roleSelect.length > 0) {
                        for (var i = 0; i < roleSelect.length; i++) {
                        	myData.roleIds.push(roleSelect[i].val);
                        }
                    }
                    myData.roleIds = JSON.stringify(myData.roleIds);
                    
                    sysUserObj.addUserLoading = basic.UI.layer.load(0, {shade: false});
                    
                    var saveUrl = '/sys/user/save/'+email+'/'+mobile+'/'+password+'/'+remark+'/'+username;
                    $.ajax({
                    	type: 'POST',
                    	//contentType: 'application/json;charset=UTF-8',
                        data: myData,
                        url: saveUrl,
                        dataType:'json',
                        success: function (res) {
                            basic.closeLoading(sysUserObj.addUserLoading);
                            if (res.code === 0) {
                                
                                basic.msg('用户：' + username + ' 添加成功', null, null, function () {
                                	basic.closeDialog(sysUserObj.addSysUserLayer);
                                    
                                	// 重新渲染数据
                                    sysUserObj.tableTemp.reload({
                                    	where:{}
                                    });
                                });
                                
                            } else if (res.code == 3) {
                                window.location.href = 'login.html?redirect=' + encodeURIComponent(window.location.href);
                            } else {
                                basic.alert('用户：' + username + ' 添加失败。(' + res.message + ')');
                            }
                        },
                        error: function () {
                            basic.closeLoading(sysUserObj.addUserLoading);
                            basic.alert('发生网络错误，用户：' + username + ' 添加失败。');
                        }
                    });
                });
                return false;
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
					layer.confirm('删除后，用户[<span style="color: #f01414;"> '+data.username+'</span> ]将不可用，确认删除吗？', {
						btn: ['确定','取消'], //按钮
						skin: 'layer-red'
					}, function() { // 确定
						_this._delSysUserClick(data);
					});
				}
				if (layEvent === 'edit') { //编辑
					_this._editSysUserBtnClick(data);
				}
			});
        },
        _delSysUserClick: function(data) {
        	_this = this;
        	sysUserObj.delUserLoading = layer.load(0, {shade: false});
        	var id = data.id;
        	var username = data.username;
        	$.ajax({
                type: 'POST',
                url: '/sys/user/delete/' + id,
                dataType:'json',
                success: function (res) {
                    basic.closeLoading(sysUserObj.delUserLoading);
                    if (res.code === 0) {
                        basic.msg('用户：<span style="color: #ffc">' + username + '</span> 删除成功', null, null, function () {
                        	sysUserObj.tableTemp.reload({
                            	where:{}
                            });
                        });
                    } else {
                        basic.alert('用户：' + username + ' 删除失败。(' + res.message + ')');
                    }
                },
                error: function () {
                    basic.closeLoading(sysUserObj.delUserLoading);
                    basic.alert('网络错误，用户：' + username + ' 删除失败。');
                }
            });
        	
        	
        },
        // 点击修改，弹出框
        _editSysUserBtnClick: function(data) {
        	_this = this;
        	
        	var userId = data.id,
        	pwd = data.password,
        	username = data.username,
        	email = data.email,
        	mobile = data.mobile;
        	remark = data.remark;
        	
        	var roleIds = '';
		    if (data.urVolist.length > 0) {
			    for (var i=0,rsize = data.urVolist.length;i<rsize;i++) {
			    	roleIds += data.urVolist[i].roleId + ',';
				}
		    }
		    roleIds = roleIds.slice(0, -1);
		    
		    var role = roleIds;		    
            
            editSysUserDialog = $('#editSysUserDialog');
        	
	        editSysUserDialog.find('#id').val(userId);
	        editSysUserDialog.find('#myPass').val(pwd);
	        editSysUserDialog.find('#isUpdateRole').val(role);
	        editSysUserDialog.find('#usernameU').val(username);
	        editSysUserDialog.find('#passwdU').val(pwd);
	        editSysUserDialog.find('#passwdConfirmU').val(pwd);
	        editSysUserDialog.find('#telphoneU').val(mobile);
	        editSysUserDialog.find('#emailU').val(email);
	        editSysUserDialog.find('#remarkU').val(remark);
	        
	        sysUserObj.editSysUserLayer = basic.UI.layer.open({
	            type: 1,
	            title: false,
	            area: 'auto',
	            move: '.h5 .title',
	            maxWidth: 1000,
	            closeBtn: 0,
	            shadeClose: false,
	            content: editSysUserDialog
	        });
	        
	        var roleArr = role.toString().split(',');	        
	        _this._getRoleSelectData($('#roleSelectU'), 'roleSelectU', roleArr);
        },
        /** 修改用户表单提交 **/
        updateUserFormSubmit: function () {
            var _this = this;
            basic.UI.form.on('submit(updateUser)', function (data) {
                var userId = $.trim(data.field.id),
                	username = $.trim(data.field.username),
                    passwd = $.trim(data.field.passwdU),
                    telphone = $.trim(data.field.telphoneU),
                    email = $.trim(data.field.emailU),
                    remark = $.trim(data.field.remark);
                var myData = {
                    "id": userId,
                    "username": username,
                    "mobile": telphone,
                    "email": email,
                    "remark": remark,
                    "roleIds": []
                };
                // 判断密码是否需要修改
                if (passwd !== $.trim(data.field.myPass)) {
                    myData.password = passwd;
                }
                // 角色下拉框
                var roleSelect = basic.UI.formSelects.value('roleSelectU');

                if (roleSelect && roleSelect.length) {
                	for (var i = 0; i < roleSelect.length; i++) {
                    	myData.roleIds.push(roleSelect[i].val);
                    }
                }
                
                var _roleArr = [],
                    _roleStr = '';
                
                for (var j = 0; j < myData.roleIds.length; j++) {
                    _roleArr.push(myData.roleIds[j]);
                }
                _roleStr = _roleArr.join(',');
                
                if (_roleStr === $('#isUpdateRole').val()) {
                    myData.isUpdateRole = false;
                } else {
                    myData.isUpdateRole = true;
                }
                
                sysUserObj.editUserLoading = basic.UI.layer.load(0, {
                    shade: false
                });
                
                var updUrl = '/sys/user/update/'+userId;
                myData.roleIds = JSON.stringify(myData.roleIds);
                $.ajax({
                	type:'POST',
                    data: myData,
                    url: updUrl,
                    dataType:'json',
                    success: function (res) {
                        basic.closeLoading(sysUserObj.editUserLoading);
                        if (res.code === 0) {
                            basic.msg('用户：' + username + ' 修改成功', null, null, function () {
                            	basic.closeDialog(sysUserObj.editSysUserLayer);
                            	// 重新渲染数据
                                sysUserObj.tableTemp.reload({
                                	where:{}
                                });
                            });
                            
                        } else if (res.code == 3) {
                            window.location.href = 'login.html?redirect=' + encodeURIComponent(window.location.href);
                        } else {
                            basic.alert('用户：' + username + ' 修改失败。' +res.msg)
                        }
                    },
                    error: function () {
                        basic.closeLoading(sysUserObj.editUserLoading);
                        basic.alert('发生网络错误，用户：' + username + ' 修改失败。')
                    }
                });
                return false;
            });
        },
        
		/** 关闭弹出框 */
		closeDialog: function() {
        	$(document).on('click','#addSysUserDialog .closeBtn',function() {
        		basic.closeDialog(sysUserObj.addSysUserLayer);
        	});
        	$(document).on('click', '#editSysUserDialog .closeBtn', function () {
                basic.closeDialog(sysUserObj.editSysUserLayer);
            });
        },
        
	};
	
	sysUserObj.init = function() {
		new this;
	};
	
	// 初始化页面列表
	loadSysUser = function() {
		
		var listUrl = basic_url.get_sys_user_list;
		
		sysUserObj.tableTemp = basic.UI.table.render({
			elem : '#sysUserList',
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
				{field : 'password',title : 'password',hide : true}, 
				{field : 'username',title : '用户名'}, 
				{field : 'roleList',title : '角色', templet: function(d) {
					var roleStr = '';
				    if (d.urVolist.length > 0) {
					    for (var i=0,rsize = d.urVolist.length;i<rsize;i++) {
							roleStr += d.urVolist[i].roleName + '、';
						}
				    }
					roleStr = roleStr.slice(0, -1);
					return roleStr
			        //return 'ID：'+ d.id +'，标题：<span style="color: #c00;">'+ d.username +'</span>'
			      }
				}, 
				{field : 'mobile',title : '手机'}, 
				{field : 'email',title : '邮箱',sort : true}, 
				{field : 'createTime',title : '创建时间',sort : true}, 
				{fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'} 
				] ]
		});
		
		//监听提交，form表单提交，根据条件搜索
		basic.UI.form.on('submit(formDemo)', function(data) {
			var username = data.field.username;
			// 重新渲染数据
			sysUserObj.tableTemp.reload({
				where : {
					username : username
				}
			})
			return false;
		});
	};
	
	layui.config({
        base: '/layui/lay/modules/'
    }).extend({
        formSelects: 'formSelects-v3'
    });
	
	/*layui.use(['jquery','table','element','form','layer','laytpl','formSelects'],function() {
		window.$ = layui.$;               // 引入layui内置的jquery对象，赋值给全局$
		basic.UI.table = layui.table;     // 把layui的table模块赋值给common.js中的basic.UI对象
		basic.UI.element = layui.element; // 把layui的element模块赋值给common.js中的basic.UI对象
		basic.UI.form = layui.form;	      // 把layui的form 模块赋值给common.js中的basic.UI对象
		basic.UI.layer = layui.layer;
		basic.UI.laytpl = layui.laytpl;
		
		
		
		
	});*/
	
	layui.use(['jquery', 'layer', 'table','form', 'laytpl', 'formSelects'], function () {
        window.$ = layui.$;
        basic.UI.layer = layui.layer;
        basic.UI.table = layui.table; 
        basic.UI.form = layui.form;
        basic.UI.laytpl = layui.laytpl;
        basic.UI.formSelects = layui.formSelects;
        
        sysUserObj.init();
		// 加载列表
		loadSysUser();
    });
	
})();


