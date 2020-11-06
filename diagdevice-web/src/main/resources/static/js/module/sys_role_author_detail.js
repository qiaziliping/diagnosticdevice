// 角色授权页面js
;(function () {
	
	var OBJECT = function () {
		
		this.renderTable(); // 渲染表格
		this.returnBtnClick(); //点击返回按钮
	};
	
	
	// 原型
	OBJECT.prototype = {
		constructor: OBJECT,
		
		/** 渲染表格 **/
        renderTable: function () {
            var _this = this;
            
            var retUrl = '/sys/role/getRoleAuthorityByRoleId/'+roleIdVal;
            $.ajax({
                type: 'GET',
                url: retUrl,
                dataType: 'json',
                success: function (res) {
                	
                    if (res.code === 0) {
                    	console.log(res.data);
                        basic.UI.laytpl($('#tableTemplate').html()).render(res.data, function (html) {
                            $('#table').html(html);
                            basic.UI.form.render('checkbox');
                        });
                    } else {
                        basic.UI.laytpl(
                            '<tr class="error-tr"><td><div class="error-msg" style="top: 200px;"><i class="icon-info-sign icon-large"></i> {{d.message }}</div></td></tr>')
                            .render(res, function (html) {
                            $('#table').html(html);
                        });
                    }
                },
                error: function () {
                    $('#table').html(
                        '<tr class="error-tr"><td><div class="error-msg" style="top: 200px;"><i class="icon-info-sign icon-large"></i> 数据获取错误，请刷新后重试...</div></td></tr>');
                }
            });
        },
        
        /** 返回站点按钮单击 **/
        returnBtnClick: function () {
            $(document).on('click', '#returnBtn', function () {
                window.location.href = redirectVal;
            });
            $(document).on('click', '#fanhui', function () {
            	window.location.href = redirectVal;
            });
        },
        
        
	};
	OBJECT.init = function () {
		new this();
	};
    layui.use(['jquery', 'element','layer', 'form','laytpl'], function () {
        window.$ = layui.$;
        basic.UI.form    = layui.form;
		basic.UI.layer = layui.layer;
		basic.UI.element = layui.element;
		basic.UI.laytpl = layui.laytpl;
		// OBJECT对象初始化 
        OBJECT.init();
    });	
})();