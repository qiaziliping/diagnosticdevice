window.basic = (function() {
	
	this.UI = {};
	
	this.alert = function(msg) {
		this.UI.layer.msg(msg, {
            icon: 5,
            skin: 'layer-red'
        });
	};
	
	this.msg = function(msg, icon, anim, fn) {
		this.UI.layer.msg(msg, {
            shadeClose: true,
            shade: 0.01,
            icon: icon || -1,
            anim: anim || 0
        }, function () {
            (typeof fn === 'function') && fn();
        });
	}
	/** 关闭对话框**/
	this.closeDialog = function (dialog) {
        if (dialog) {
        	this.UI.layer.close(dialog);
            dialog = null;
        }
    }
	/** 关闭加载框 **/
	this.closeLoading = function (loading) {
        if (loading) {
            this.UI.layer.close(loading);
            loading = null;
        }
    };
	
	
	return this;
})();


;(function(){
	var basicObj = function() {
		
		this.eachMenu();      // 切换菜单时，菜单高亮
		this.roleListClick(); // 头部的多角色情况下角色列表单击
		
	};
	
	
	basicObj.prototype = {
		constructor:basicObj,
		
		/** 头部的多角色情况下角色列表单击 **/
        roleListClick: function () {
            var _this = this;
            $(document).on('click', '#headerRoleList .roleItem', function () {
            	var id = $(this).attr("value"); // 获取角色id
            	var children = $(this).children(":first");
            	var roleName = children.html();
            	
            	var currUrl = window.location.href; 
                _this._switchRole(id,roleName,currUrl);
            });
        },
        /** 切换角色 */
        _switchRole: function(roleId,roleName,currUrl) {
        	/*var rData = {
        		roleId:roleId
        	};*/
        	var url = "/account/switchRole/"+roleId+"/"+roleName;
        	$.ajax({
                data: {},
                type:'POST',
                url: url,
                dataType:'json',
                success: function (res) {
                    if (res.code === 0) {
                        window.location.href = currUrl;
                    } else {
                    	basic.alert('服务器内部错误！');
                    }
                },
                error: function (res) {
	                basic.alert('发生网络错误，切换角色失败。');
                }
            });
        	
        },
        
		// 切换菜单时，菜单高亮
		eachMenu : function() {
			var _this = this;
			
			$('.layui-side-scroll a').each(function(){
			    
			    var a_obj = $(this); // 获取标签
				var href = a_obj.attr('href');
				
				var currHref = window.location.href;  // 获取当前页面路径
				currHref = decodeURIComponent(currHref);
				
				if (currHref.indexOf(href) != -1) {   // 判断当前连接
					
					var a_parent = a_obj.parent();    // 得到a标签的上级节点
				    console.log(href);
				    var a_parent_clas = a_parent.attr("class");
				    // 添加layui 展开样式
				    if (a_parent_clas == 'dClass') {
				    	 var top_parent = a_parent.parents(".layui-nav-item");
				    	 top_parent.addClass("layui-nav-itemed");
				    }
				    
				    a_parent.addClass("layui-this");
				}
				
			});
		},
	};
	
	basicObj.init = function() {
		new this;
	};
	
	
	layui.use(['jquery','element','layer','form'],function(){
		window.$ = layui.$;
		basic.UI.form    = layui.form;
		basic.UI.layer = layui.layer;
		basic.UI.element = layui.element;
		basicObj.init();
		
	});
	
	
})();


window.basic_url = (function() {
	
	this.get_device_detail = '/device/getDeviceDetail/'; //获取   设备详情页面
	// 诊断价格
	this.get_diagsoft_price_list = '/diagSoft/getDiagSoftPriceList'; //获取   诊断价格列表
	this.save_diagsoft_price = '/diagSoft/saveDiagSoftPrice'; //新增   诊断价格
	this.edit_diagsoft_price = '/diagSoft/updateDiagSoftPrice'; //修改   诊断价格
	this.del_diagsoft_price  = '/diagSoft/deleteDiagSoftPrice'; //删除   诊断价格
	// 智能合约
	this.get_znhy_account_list  = '/znhy/getZnhyAccountList'; //获取  智能合约账户列表
	this.save_znhy_account  = '/znhy/saveZnhyAccount';        //新增  智能合约账户
	this.update_znhy_account  = '/znhy/updateZnhyAccount';    //修改  智能合约账户
	this.getall_znhy_account  = '/znhy/getZnhyAccountAll';    //获取 所有智能合约账户
	
	this.get_allocation_group_list  = '/znhy/getAllocationGroupList'; //分页获取合约分配组（获取分配规则）
	
	this.get_znhy_allocation_list  = '/znhy/getZnhyAllocationPage'; //分页获取合约分账列表
	this.getall_znhy_allocation_group  = '/znhy/getAllocationGroupAll'; //分页获取合约分账列表
	
	// 系统管理
	this.get_sys_user_list = '/sys/user/getSysUserPage';                   //获取系统用户列表
	this.get_sys_authority_list = '/sys/authority/getSysAuthorityPage';    //获取系统权限列表
	this.get_sys_role_list = '/sys/role/getSysRolePage';              // 获取角色列表
	this.save_sys_role     = '/sys/role/save';                        // 保存系统角色
	this.update_sys_role   = '/sys/role/update';                      // 修改系统角色
	this.del_sys_role      = '/sys/role/delete';                      // 删除系统角色
	
	return this;
})();
