define('basic', function () {
    return {
        development: false,          // 开发环境
        /** IE小于9 **/
        isIE: function () {
            return !+'\v1';
        },
        /** 获取URL的参数 **/
        getUrlString: function(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)
                return unescape(r[2]);
            return null;
        },
        /** 封装ajax **/
        request: function (param, successFn, errorFn) {
            var _this = this;
            $.ajax({
                type       : param.type     || 'POST',
                url        : _this.development ? '/getRepair' + param.url : param.url,
                contentType: param.contentType === undefined ? ((param.type && param.type.toLowerCase() === 'get') ? 'application/json' : 'application/x-www-form-urlencoded') : param.contentType,
                processData: (param.processData === undefined) ? true : param.processData,
                dataType   : param.dataType || 'json',
                data       : param.data     || '',
                success    : typeof param.success === 'function' && param.success,
                error      : typeof param.error === 'function' && param.error,
            });
        }
    }
});

require.config({
    baseUrl: './',
    paths: {
        jquery: '/js/jquery-1.12.4.min',
        lodash: '/js/lodash/lodash.min',
        layer: '/layui/lay/modules/layer',
        css: '/js/require/css.min',
        login: '/js/login/login'
    },
/*    paths: {
    	jquery: 'library/jquery/jquery-1.12.4.min',
    	lodash: 'library/lodash/lodash.min',
    	layer: 'ui/lay/modules/layer',
    	css: 'library/require/css.min',
    	store: 'library/store/store.legacy.min',
    	login: 'js/login/login'
    },*/
    skim: {
        layer: {
            deps: ['jquery'],
            exports: 'layer'
        }
    }
});

require(['basic', 'login'], function (basic, login) {
    window.basic = basic;
    if (!+'\v1') {
        layer.alert('为使您有更好的用户体验，请不要使用 IE8 浏览器。', {
            icon: 5,
            skin: 'layer-red'
        })
    }
    login.init();
});
