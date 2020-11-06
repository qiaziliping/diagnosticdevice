;(function () {
    var profitObj = function () {
        /*if (!basic.getUrlString('id') || !basic.getUrlString('redirect')) {
            location.href = 'profit_rule.html';
        }*/
    	if (!jobGroupIdVal || !redirectVal) {
            location.href = redirectVal;
        }
        // this.PROFIT_RULE_MGR_UPDATE    = basic.findAuthWithAuthList('PROFIT_RULE_MGR_UPDATE');

        // if (!this.PROFIT_RULE_MGR_UPDATE) {
        //     basic.UI.laytpl('<tr class="error-tr"><td><div class="error-msg" style="top: 200px;"><i class="icon-info-sign icon-large"></i> 抱歉，你没有 “分账规则管理” 添加权限。</div></td></tr>').render({}, function(html){
        //         $('#content').html(html);
        //     });
        // } else {
            $('#footer').removeClass('layui-hide');
            this.CACHE = {
                id: 0,
                arr: [],
                selectData: []
            };
            this.throttleOption = {'leading': true, 'trailing': false};	  // 用于节流函数参数
            this.renderTable();                                           // 渲染表格
            this.retTurnBtnClick();                                       // 返回上一页按钮单击
            this.myValidata();                                            // 自定义验证
            this.addBtnClick();                                           // 大 + 号按钮单击时，添加预约时间段
            this.cacelBtnClick();                                         // X 按钮单击时
            this.formSubmit();                                            // 表单提交
        // }
    };
    profitObj.submitLoading = null;                      // 提交表单loading
    profitObj.searchLoading = null;                      // 搜索loading

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
               // url: '/znhy/getAllocationRuleDetail/' + basic.getUrlString('id'),
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
        /** 自定义验证 **/
        myValidata: function () {
            basic.UI.form.verify({
                /** 规则名称 **/
                ruleName: function (value, item) {
                    var _val = $.trim(value);
                    if (_val.length !==0 && (_val.length < 2 || _val.length > 50)) {
                        return '规则名称是：2到50位';
                    }
                },
                /** 职位 **/
                position: function (value, item) {
                    var _val = $.trim(value);
                    if (_val.length !==0 && (_val.length < 2 || _val.length > 50)) {
                        return '职位是：2到50位';
                    }
                },
                /** 比例 的验证 **/
                proportion: function (value, item) {
                    var reg = /^\d{1,2}(\.\d+)?$/g.test(value);
                    if ($.trim(value).length !==0 && !reg || $.trim(value).length !==0 && value*1 === 0) {
                        return '0-100之间';
                    }
                }
            });
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
        /** 大 + 号按钮单击时，添加预约时间段 **/
        addBtnClick: function () {
            var _this = this;
            $(document).on('click', '#addBtn', function () {
                var _data = {
                    id: _this.CACHE.id
                };
                basic.UI.laytpl($('#addTemplate').html()).render(_data, function (html) {
                    $('#contentList').append(html);
                    basic.UI.laytpl($('#nameSelectTemplate').html()).render(_this.CACHE.selectData, function (html) {
                        $('#nameSelect' + _this.CACHE.id).html(html);
                        basic.UI.form.render('select');
                    });
                    _this.CACHE.arr.push(_this.CACHE.id);
                    _this.CACHE.id++;
                });
            });
        },
        /** X 按钮单击时 **/
        cacelBtnClick: function () {
            var _this = this;
            $(document).on('click', '#contentList .cacelBtn', function () {
                var layuiFormItem = $(this).closest('.layui-form-item'),
                    index = layuiFormItem.data('index');
                layuiFormItem.remove();
                _.pull(_this.CACHE.arr, index);
            });
        },
        /** 表单提交 **/
        formSubmit: function () {
            var _this = this;
            basic.UI.form.on('submit(profitRuleSave)', function (data) {
                var ruleName   = encodeURIComponent($.trim(data.field.ruleName));
                var _arr = _this.CACHE.arr;
                if (_arr.length > 1) {
                    var _total = 0;
                    for (var i = 0; i < _arr.length; i++) {
                        _total += data.field['proportion[' + _arr[i] + ']'] * 1;
                    }
                    if (_total !== 100) {
                        basic.msg('比例总和必须等于100', 5, 6);
                        return false;
                    }
                } else {
                    basic.msg('最少选择二项', 5, 6);
                    return false;
                }
                var jobList = [];
                for (var i = 0; i < _arr.length; i++) {
                    var _obj = {
                        "job": data.field['position[' + _arr[i] + ']'],
                        "accountId": data.field['nameSelect[' + _arr[i] + ']'],
                        "radios": data.field['proportion[' + _arr[i] + ']'] * 1
                    };
                    jobList.push(_obj);
                }
                var myData = {
                    "ruleListStr": JSON.stringify(jobList)
                };

                profitObj.submitLoading = layer.load(0, {shade: false});
                $.ajax({
                    type: 'POST',
                    data: myData,
                    url: '/znhy/updateAllocationRule/' + jobGroupIdVal + '/' + ruleName,
                    dataType: 'json',
                    success: function (res) {
                        basic.closeLoading(profitObj.submitLoading);
                        if (res.code === 0) {
                            basic.msg('规则：' + decodeURIComponent(ruleName) + ' 修改成功', null, null, function () {
                                window.location.href = redirectVal;
                            });
                        }else {
                            basic.alert('规则：' + decodeURIComponent(ruleName) + ' 修改失败。' + (res.message));
                        }
                    },
                    error: function () {
                        basic.closeLoading(profitObj.submitLoading);
                        basic.alert('发生网络错误，规则：' + decodeURIComponent(ruleName) + ' 修改失败。');
                    }
                });
                return false;
            });
        }
    };
    profitObj.init = function () {
        new this();
    };
    function _waitAuthList () {
        // if (basic.authList == null) {
        //     setTimeout(function () {
        //         _waitAuthList();
        //     }, 100);
        // } else {
            profitObj.init();
        // }
    }
    layui.use(['jquery', 'layer', 'form', 'laytpl'], function() {
        window.$ = layui.$;
        basic.UI.layer   = layui.layer;
        basic.UI.form    = layui.form;
        basic.UI.laytpl  = layui.laytpl;
        _waitAuthList();
    });
})();

