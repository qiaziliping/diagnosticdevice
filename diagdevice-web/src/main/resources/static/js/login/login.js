//define('login', ['jquery', 'lodash', 'layer', 'store', 'css!ui/css/modules/layer/default/layer'], function ($, _, layer, store) {
//window.storeJS = store;
define('login', ['jquery', 'lodash', 'layer', 'css!/layui/css/modules/layer/default/layer'], function ($, _, layer) {

    var loginObj = function () {
        $('#username').focus();
        this.loginInputKeyboardEvent();   // 用户名、密码 键盘事件，验证有效性
        this.loginBtnClick();             // 登录按钮单击事件，或者回车键
    };

    loginObj.loginLoading = null;          // 登录loading

    loginObj.prototype = {
        constructor: loginObj,
        /** 用户名、密码 键盘事件，验证有效性，以及placeholder **/
        loginInputKeyboardEvent: function () {
            var _this = this,
                $username = $('#username'),
                $password = $('#password');
            var throttleOption = {'trailing': false};
            $username.on('keyup', _.debounce(function () {
                $username.removeClass('error').siblings('.error-info').remove();
                var _theVal = $.trim($(this).val());
                var _result = _this.usernameValidata(_theVal);
                if (_result.status) {
                    return;
                } else {
                    $username.addClass('error').parent().append('<span class="error-info">' + _result.msg + '</span>');
                }
            }, 400));
            $password.on('keyup', _.debounce(function () {
                $password.removeClass('error').siblings('.error-info').remove();
                var _theVal = $.trim($(this).val());
                var _result = _this.passwordValidata(_theVal);
                if (_result.status) {
                    return;
                } else {
                    $password.addClass('error').parent().append('<span class="error-info">' + _result.msg + '</span>');
                }
            }, 400));
        },
        /** 用户名验证 **/
        usernameValidata: function (username) {
            var result = {
                status: false,
                msg: ''
            };
            if (username.length < 2 || username.length > 45) {
                result.msg = '用户名2-45位';
                return result;
            }
            result.status = true;
            return result;
        },
        /** 密码验证 **/
        passwordValidata: function (password) {
            var result = {
                status: false,
                msg: ''
            };
            if (password.length < 6 || password.length > 20) {
                result.msg = '密码6-20位';
                return result;
            }
            result.status = true;
            return result;
        },
        /** 登录按钮单击或者按回车键后执行的操作 **/
        _showValidateResulte: function () {
            var $username = $('#username'),
                $password = $('#password');
            $username.removeClass('error').siblings('.error-info').remove();
            $password.removeClass('error').siblings('.error-info').remove();
            var validataResult = this.loginValidata();
            if (validataResult.code === 0) {
                this._loginSubmit();               // 提交登录
            } else {
                if (!validataResult.username.status) {
                    $username.addClass('error').parent().append('<span class="error-info">' + validataResult.username.msg + '</span>');
                }
                if (!validataResult.password.status) {
                    $password.addClass('error').parent().append('<span class="error-info">' + validataResult.password.msg + '</span>');
                }
                return;
            }
        },
        /** 登录按钮单击事件，或者回车键 **/
        loginBtnClick: function () {
            var _this = this;
            $('#loginBtn').click(function (e) {
                e.preventDefault();
                _this._showValidateResulte();
            });
            $(document).on('keyup', function (e) {
                if (e.keyCode === 13) {
                    _this._showValidateResulte();
                }
            });
        },
        /** 验证用户登陆 **/
        loginValidata: function () {
            var result = {
                username: {
                    status: true,
                    msg: ''
                },
                password: {
                    status: true,
                    msg: ''
                },
                code: 1
            };
            var username = $.trim($('#username').val()),
                password = $.trim($('#password').val());

            if (username.length < 2 || username.length > 45) {
                result.username.status = false;
                result.username.msg = '用户名2-45位';
            }
            if (password.length < 6 || password.length > 20) {
                result.password.status = false;
                result.password.msg = '密码6-20位';
            }
            if (result.username.msg !== '' || result.password.msg !== '') {
                return result;
            }
            result.code = 0;
            return result;
        },
        /** 提交登录 **/
        _loginSubmit: function () {
        	
            var _this = this,
                username = $.trim($('#username').val()),
                password = $.trim($('#password').val());
            var myData = {
            	username: username,
            	password: password
            };
            loginObj.loginLoading = layer.load(3, {shade: false});
            $.ajax({
                data: myData,
                type:'POST',
                url: '/account/login/process',
                dataType:'json',
                success: function (res) {
                    if (loginObj.loginLoading) {
                        layer.close(loginObj.loginLoading);
                        loginObj.loginLoading = null;
                    }
                    if (res.code === 0) {
                        var redirectStr = decodeURIComponent(basic.getUrlString('redirect'));
                        if (redirectStr !== 'null') {
                            window.location.href = redirectStr;
                        } else {
                            location.href = '/view/index.html';
                        }
                    } else {
                        layer.msg('用户名或者密码错误' + res.message, {
                            shadeClose: true,
                            shade: 0.01,
                            time: 5000,
                            icon: 5
                        });
                    }
                },
                error: function (res) {
                	var rep = res.responseJSON;
                	if (rep) {
                		layer.msg('错误:' + rep.message, {
                            shadeClose: true,
                            shade: 0.01,
                            time: 5000,
                            icon: 5
                        });
                	} else {
	                    layer.alert('发生网络错误，用户登录失败。', {
	                        icon: 5,
	                        skin: 'layer-red'
	                    })
                	}
                	// 关闭loading
                	if (loginObj.loginLoading) {
                        layer.close(loginObj.loginLoading);
                        loginObj.loginLoading = null;
                    }
                }
            });
        }
    };
    loginObj.init = function () {
        new this();
    };
    return loginObj;
});
