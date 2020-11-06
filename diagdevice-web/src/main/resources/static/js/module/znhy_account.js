// [智能合约账户管理]页面相关js文件
;(function() {
	var accountObj = function() {
		this.addClickEvent(); // 添加按钮点击事件
		this.closeBtnEvent();      //关闭或返回事件
		this.addAccountSubmit();  // 添加账户提交
		this.editAccountSubmit(); // 修改账户提交
		
		this.accountTypeOld = 0;  // 修改时用到
		
		this.rowMonitor(); 		  //行监听
		this.myValidata(); // 自定义验证
	};
	
	accountObj.tableTemp = null;       //列表
	accountObj.addAccountLayer = null; // 添加账户弹出框
	accountObj.editAccountLayer = null; // 添加账户弹出框
	accountObj.addAccountLoading = null; //提交账户加载
	accountObj.editAccountLoading = null; 
	
	accountObj.prototype = {
		construction : accountObj,
		
		// 添加按钮点击事件
		addClickEvent: function() {
			var _this = this;
			$(document).on('click','#addAccountBtn',_.throttle(function(){
				var addAccountDialog = $("#addAccountDialog");
				
				accountObj.addAccountLayer = basic.UI.layer.open({
                    type: 1,
                    title: false,
                    area: 'auto',
                    move: '.h5 .title',
                    maxWidth: 1180,
                    closeBtn: 0,
                    shadeClose: false,
                    content: addAccountDialog
                });
				addAccountDialog.find('.mainText').html($('#addAccountTemplate').html());
				basic.UI.form.render('select');
			},1000,_this.throttleOption));
		},
		
		// 点击添加账户确定
		addAccountSubmit: function() {
			var _this = this;
			basic.UI.form.on('submit(addAccount)', function(data) {
				// 加载层
				accountObj.addAccountLoading = layer.load(0,{ shade:false });
				
				var accountType = data.field.accountType; //账户类型
				var accountName = data.field.accountName; //账户名
				var name = data.field.userName;     //账户名
				var telephone = data.field.telephone;//手机号
				
				//var accountSaveUrl = basic_url.save_znhy_account+"/"+alipay+"/"+name+"/"+telephone;
				var accountSaveUrl = basic_url.save_znhy_account+"/"+accountName+"/"+accountType+"/"+name+"/"+telephone;
				$.ajax({
					url: accountSaveUrl,
					type : 'post',
					data: {},
					dataType: 'json',
					success: function(rst){
						basic.closeLoading(accountObj.addAccountLoading);
						if (rst.code === 0) {
							 basic.msg('合约账户：' + name + ' 添加成功', null, null, function () {
	                                basic.closeDialog(accountObj.addAccountLayer);
	                                //重新渲染表格
	                                accountObj.tableTemp.reload({
	                					where : {}
	                				});
	                            });
						} else {
							 basic.alert('合约账户：' + name + ' 添加失败。(' + res.message + ')');
						}
					},
					error: function() {
						basic.closeLoading(accountObj.addAccountLoading);
                        basic.alert('网络错误，合约账户：' + name + ' 添加失败。');
					}
				});
			});
		},
		
		// 关闭弹出框
		closeBtnEvent: function() {
			var _this = this;
			$(document).on('click','#addAccountDialog .closeBtn',function() {
				basic.closeDialog(accountObj.addAccountLayer);
			});
			$(document).on('click','#editAccountDialog .closeBtn',function() {
				basic.closeDialog(accountObj.editAccountLayer);
			});
		},
		myValidata: function() {
			basic.UI.form.verify({
				userName:function(value, item) {
					var _val = $.trim(value);
					if (_val.length !== 0 && (_val.length < 2 || _val.length > 30)) {
                        return '姓名是：2到30位';
                    }
				},
				alipay:function(value, item) {
					var regTel = /^1[345789]\d{9}$/g.test(value), // 手机号码
                    regEmail = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(value); // 电子邮箱
	                if ($.trim(value).length !== 0 && !regTel && !regEmail || ($.trim(value).length > 30)) {
	                    return '支付宝账号是：手机号、邮箱(30位以内)';
	                }
				},
				/** 手机号 **/
				telephone: function (value, item) {
                    var regTel = /^1[345789]\d{9}$/g.test(value); // 手机号码
                    if ($.trim(value).length !== 0 && !regTel) {
                        return '请填写正确的手机号';
                    }
                },
			});
		},
		// 行监听
		rowMonitor: function() {
			var _this = this;
			// 行数据操作，监听行工具事件
			basic.UI.table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
				var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
				if (layEvent === 'edit') { //编辑
					_this._editAccountBtnClick(data,_this);
				}
			});
		},
		// 点击编辑弹出框
		_editAccountBtnClick: function(data,_this) {
			var _this = this;		
            var editAccountDialog = $('#editAccountDialog');
            accountObj.editAccountLayer = basic.UI.layer.open({
                type: 1,
                title: false,
                area: 'auto',
                move: '.h5 .title',
                maxWidth: 1180,
                closeBtn: 0,
                shadeClose: false,
                content: editAccountDialog
            });
            
            editAccountDialog.find('.mainText').html($('#editAccountTemplate').html()); 
            
            // 设置值
            $("#editId").val(data.id);
            $("#userNameE").val(data.name);
            $("#accountNameE").val(data.accountName);
            $("#telephoneE").val(data.telephone);
            
            $("#accountTypeE").val(data.accountType);
            
            _this.accountTypeOld = data.accountType;
            
            basic.UI.form.render('select');
		},
		// 点击修改账户确定
		editAccountSubmit: function() {
			var _this = this;
			basic.UI.form.on('submit(editAccount)', function(data) {
				// 加载层
				accountObj.editAccountLoading = layer.load(0,{ shade:false });
				
//				var alipay = data.field.alipay; //支付宝账户
				var accountName = data.field.accountName; //账号
				var accountType = data.field.accountType; //账号类型
				var name = data.field.userName;     //账户名
				var telephone = data.field.telephone;//手机号
				var id = data.field.id;//手机号
				
				var updObject = new Object();
				updObject.id = id;
				updObject.name = name;
				//updObject.alipay = alipay;
				updObject.accountName = accountName;
				updObject.accountType = accountType;
				updObject.accountTypeOld = _this.accountTypeOld;
				
				updObject.telephone = telephone;
				
				var accountEditUrl = basic_url.update_znhy_account;
				$.ajax({
					url: accountEditUrl,
					type : 'post',
					data: updObject,
					dataType: 'json',
					success: function(rst){
						basic.closeLoading(accountObj.editAccountLoading);
						if (rst.code === 0) {
							 basic.msg('合约账户：' + name + ' 修改成功', null, null, function () {
	                                basic.closeDialog(accountObj.editAccountLayer);
	                                
	                                var searchName = $("#searchName").val();
	                                
	                                //重新渲染表格
	                                accountObj.tableTemp.reload({
	                					where : {
	                						name : searchName
	                					}
	                				});
	                            });
						} else {
							 basic.alert('合约账户：' + name + ' 修改失败。(' + res.message + ')');
						}
					},
					error: function() {
						basic.closeLoading(accountObj.addAccountLoading);
                        basic.alert('网络错误，合约账户：' + name + ' 修改失败。');
					}
				});
			});
		},
		
	};
	
	accountObj.init = function() {
		new this;
	};
	
	// 初始化页面列表
	loadZnhyAccount = function() {
		
		var listUrl = basic_url.get_znhy_account_list;
		
		accountObj.tableTemp = basic.UI.table.render({
			elem : '#accountList',
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
				{field : 'name',title : '姓名'}, 
				{field : 'telephone',title : '手机号码'}, 
//				{field : 'alipay',title : '支付宝'}, 
				{field : 'accountType',title : '账户类型',templet: function(d){
					var typename = "",accountType = d.accountType;
					switch (accountType) {
						case 1:
							typename = "支付宝";
							break;
						case 2:
							typename = "微信";
							break;
						case 3:
							typename = "PAYPAL";
							break;
						case 4:
							typename = "银行卡号";
							break;
						default:
							break;
					}
					return typename;
				}}, 
				{field : 'accountName',title : '账户名'}, 
				{field : 'createDate',title : '创建时间',sort : true}, 
				{field : 'updateDate',title : '修改时间',width : '20%',minWidth : 50},//minWidth：局部定义当前单元格的最小宽度 
				{fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'} 
				] ]
		});
		
		//监听提交，form表单提交，根据条件搜索
		basic.UI.form.on('submit(formDemo)', function(data) {
			var name = data.field.name;
			// 重新渲染数据
			accountObj.tableTemp.reload({
				where : {
					name : name
				}
			})
			return false;
		});
	};
	
	layui.use(['jquery','element','table','form','layer'],function() {
		window.$ = layui.$;               // 引入layui内置的jquery对象，赋值给全局$
		basic.UI.table = layui.table;     // 把layui的table模块赋值给common.js中的basic.UI对象
		basic.UI.element = layui.element; // 把layui的element模块赋值给common.js中的basic.UI对象
		basic.UI.form = layui.form;	      // 把layui的form 模块赋值给common.js中的basic.UI对象
		basic.UI.layer = layui.layer;
		
		accountObj.init();
		// 加载列表
		loadZnhyAccount();
	});
	
})();


