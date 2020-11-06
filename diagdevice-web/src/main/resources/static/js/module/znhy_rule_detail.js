;(function () {
    var profitObj = function () {
    		// 如果组ID和返回连接都为空
	    	if (!jobGroupIdVal || !redirectVal) {
	            location.href = redirectVal;
	        }
            $('#footer').removeClass('layui-hide');
            this.CACHE = {
                id: 0,
                arr: [],
                selectData: []
            };
            this.renderTable();                                           // 渲染表格
            this.retTurnBtnClick();                                       // 返回上一页按钮单击
    };

    profitObj.prototype = {
        constructor: profitObj,
        /** 渲染表格 **/
        renderTable: function () {
            var _this = this;
            $.when(_this._getNameSelect(), _this._getData()).done(function (selectData, data2) {
                _this.CACHE.selectData = selectData;
                _this.CACHE.id = data2.ruleList.length;
                for (var i=0; i<data2.ruleList.length; i++) {
                    _this.CACHE.arr.push(i);
                }
                data2.selectData = selectData;
                basic.UI.laytpl($('#contentTemplate').html()).render(data2, function (html) {
                    $('#content').html(html);
                    basic.UI.form.render('select');
                });
            }).fail(function (err) {
                basic.alert(err);
            });
        },
        /** 获得待编辑的数据 **/
        _getData: function () {
            var _this = this,
                dtd = $.Deferred();
            $.ajax({
            	url: '/znhy/getAllocationRuleDetail/'+jobGroupIdVal,
                type: 'GET',
                dataType: 'json',
                success: function (res) {
                    if (res.code === 0) {
                        dtd.resolve(res.data);
                    } else {
                        dtd.reject('数据获取失败：' + ( res.message ));
                    }
                },
                error: function () {
                    dtd.reject('发生网络错误，数据获取失败。');
                }
            });
            return dtd.promise();
        },
        /** 获得选择框数据 **/
        _getNameSelect: function (fn) {
            var _this = this,
                dtd = $.Deferred();
            
            var urlAll = basic_url.getall_znhy_account;
            $.ajax({
            	url: urlAll,
                type: 'GET',
                dataType: 'json',
                success: function (res) {
                    if (res.code === 0) {
                        dtd.resolve(res.data);
                    } else {
                        dtd.reject('账户名称选择框数据获取失败：' + ( res.message ));
                    }
                },
                error: function () {
                    dtd.reject('网络错误，账户名称选择框数据获取失败。');
                }
            });
            return dtd.promise();
        },
        /** 返回上一页按钮单击 **/
        retTurnBtnClick: function () {
            $(document).on('click', '#retTurnBtn', function () {
            	window.location.href = redirectVal;
            });
            $(document).on('click', '#returnRuleList', function () {
            	window.location.href = redirectVal;
            });
        },
        
    };
    profitObj.init = function () {
        new this();
    };
    function _waitAuthList () {
         profitObj.init();
    }
    layui.use(['jquery', 'layer', 'form', 'laytpl'], function() {
        window.$ = layui.$;
        basic.UI.layer   = layui.layer;
        basic.UI.form    = layui.form;
        basic.UI.laytpl  = layui.laytpl;
        _waitAuthList();
    });
})();

