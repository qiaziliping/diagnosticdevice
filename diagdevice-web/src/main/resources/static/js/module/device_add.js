// 设备添加页面相关js文件
;(function () {
	
	var OBJECT = function () {
		this.throttleOption = {'leading': true, 'trailing': false};	  // 用于节流函数参数
		this.renderDeviceGroup();		    // 加载设备分组下拉框
		this.getDeviceTypeWithSerialNo();   // 根据 设备序列号 获得 设备类型 事件
		this.addDeviceGroupBtnClick();      // 新建分组按钮单击
		this.addDeviceGroupFormSubmit();              // 新建分组表单提交
		this.closeDialog();                 // 对话框里的关闭按钮单击
		this.initImageUpload();				// 初始化图片上传
		
		this.addDeviceFormSubmit();                   // 添加设备表单提交
		this.returnBtnClick();                        // 返回按钮单击
	};
	
	OBJECT.addDeviceGroupLayer   = null;        // 新建分组对话框
	OBJECT.addDeviceGroupLoading = null;        // 新建分组loading
	OBJECT.addDeviceloading      = null;        // 添加设备 loading
	
	// 原型
	OBJECT.prototype = {
		constructor: OBJECT,
		
		/** 加载设备分组下拉框 **/
		renderDeviceGroup: function () {
			$.ajax({
				url : '/device/selectAllDeviceGroup',
				dataType : 'json',
				type : 'get',
				success : function(result) {
					var data = result.data;
					$.each(data, function(index, item) {
						$('#deviceGroup').append(
								new Option(item.groupName, item.id));// 下拉菜单里添加元素
					})

					basic.UI.form.render();//下拉菜单渲染 把内容加载进去
				}
			});
		},
        /** 根据 设备序列号 获得 设备类型 事件 **/
        getDeviceTypeWithSerialNo: function () {
            var _this = this,
                regExp = /^\d{12}$/g;
            $(document).on('keyup', '#serialNo', _.debounce(function () {
                var serialNo = $.trim($(this).val());
                if (regExp.test(serialNo)) {
                    _this._getDeviceType(serialNo);
                }
            }, 400));
            $(document).on('change', '#serialNo', _.debounce(function () {
                var serialNo = $.trim($(this).val());
                if (regExp.test(serialNo)) {
                    _this._getDeviceType(serialNo);
                }
            }, 400));
        },
        
        /** 新建分组按钮单击 **/
        addDeviceGroupBtnClick: function () {
            var _this = this;
            $(document).on('click', '#addDeviceGroupBtn', _.throttle(function () {
                var addDeviceGroupDialog = $('#addDeviceGroupDialog');
                OBJECT.addDeviceGroupLayer = basic.UI.layer.open({
                    type: 1,
                    title: false,
                    area: 'auto',
                    move: '.h5 .title',
                    maxWidth: 1180,
                    closeBtn: 0,
                    shadeClose: false,
                    content: addDeviceGroupDialog
                });
            }, 1000, _this.throttleOption));
        },
        /** 新建分组表单提交 **/
        addDeviceGroupFormSubmit: function () {
            var _this = this;
            basic.UI.form.on('submit(addDeviceGroup)', function (data) {
            	OBJECT.addDeviceGroupLoading = layer.load(0, {shade: false});
                var groupName = data.field.groupNameA;
                $.ajax({
                    type: 'POST',
                    url: '/device/saveDeviceGroup/' + groupName,
                    success: function (res) {
                    	var res = JSON.parse(res);
                        basic.closeLoading(OBJECT.addDeviceGroupLoading);
                        if (res.code == 0) {                        	
                        	basic.msg('添加成功', null, null, function () {
                        		//_this._closeDialog(OBJECT.addDeviceGroupLayer);
                        		basic.closeDialog(OBJECT.addDeviceGroupLayer);
                        		// 先清空下拉框，再重新加载下拉框
                        		$("#deviceGroup").find("option").not(":first").remove()
                        		_this.renderDeviceGroup();
                        	});
                            /* basic.msg('分组：' + groupName + ' 添加成功', null, null, function () {
                                _this.initDeviceGroupSelect();
                                basic.closeDialog(deviceObj.addDeviceGroupLayer);
                            }); */
                            
                        } else {
                            //basic.alert('分组：' + groupName + ' 添加失败。(' + res.message + ')');
                        	basic.UI.layer.msg('分组：' + groupName + ' 添加失败。(' + res.message + ')');
                        }
                    },
                    error: function () {
                        basic.closeLoading(OBJECT.addDeviceGroupLoading);
                        basic.alert('网络错误，分组：' + groupName + ' 添加失败。');
                    }
                });
                return false;
            });
        },
        /** 对话框里的关闭按钮单击 **/
        closeDialog: function () {
            var _this = this;
            $(document).on('click', '#addDeviceGroupDialog .closeBtn', function () {
                basic.closeDialog(OBJECT.addDeviceGroupLayer)
            });
//             $(document).on('click', '#deviceLocationDialog .closeBtn', function () {
//                 basic.closeDialog(coordinateLayer);
//             });
        },
        
        /** 初始化上传图片 **/
        initImageUpload: function () {
            var _this = this;
            $(document).on('change', '#chooseImgBtn', function () {
                var $this      = $(this),
                    filePath   = $this.val(),
                    fileFormat = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
                if( !fileFormat.match(/.png|.jpg|.jpe?g/) ) {
                	basic.msg('文件格式必须为：png、jpg、jpeg', 5, 6, function () {
                        $this.val('');
                        $('#uploadedImg').html('').parent().hide();
                    });
                    return;
                }
                if (this.files[0].size/1000 >= 1024) {
                	basic.msg('图片最多1M', 5, 6, function () {
                        $this.val('');
                        $('#uploadedImg').html('').parent().hide();
                    });
                    return;
                }
                var src = window.URL.createObjectURL(this.files[0]);
                $('#uploadedImg').html('<img src="'+src+'" />').parent().show();
            });
        },
        // 添加设备时，form表单提交
        addDeviceFormSubmit: function() {
        	var _this = this;
        	//监听提交
			basic.UI.form.on('submit(addDevice)', function(data) {
				// 属性校验
				if (!$('#chooseImgBtn').get(0).files[0]) {
					basic.msg('请上传设备凭证图片', 5, 6);
                    return false;
                }
                if ($.trim(data.field.deviceType) === '') {
                    _this._getDeviceTypeErrMsg('请输入正确的设备序列号。');
                    return false;
                }
                var serialNo = _.escape($.trim(data.field.serialNo)),           // 设备序列号
                    deviceGroupId = data.field.deviceGroupId,                     // 设备分组
                    deviceType =  _.escape($.trim(data.field.deviceType));      // 设备类型
                var myData = new FormData();
                myData.append('upfile', $('#chooseImgBtn').get(0).files[0]);
                myData.append('location', _.escape($.trim(data.field.location)));
                OBJECT.addDeviceloading = layer.load(0, {shade: false});
                
				$.ajax({
                    type: 'POST',
                    data: myData,
                    url: '/device/save/' + deviceGroupId + '/' + deviceType + '/' + serialNo,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        
                        var res = JSON.parse(res);
                        basic.closeLoading(OBJECT.addDeviceloading);
                        if (res.code == 0) {
                            basic.msg('序列号为：' + serialNo + ' 的设备添加成功', null, null, function () {
                            	window.location.href = redirectVal;
                                //window.location.href = decodeURIComponent(basic.getUrlString('redirect'));
                            });
                        } else {
                        	basic.alert('序列号为：' + serialNo + ' 的设备添加失败。(' + res.message + ')');
                        }
                    },
                    error: function () {
                    	basic.closeLoading(OBJECT.addDeviceloading);
                    	basic.alert('发生网络错误，设备添加失败。');
                    }
                });
                return false;
			});
        },
        /** 返回站点按钮单击 **/
        returnBtnClick: function () {
            $(document).on('click', '#returnBtn', function () {
//                 window.location.href = decodeURIComponent(basic.getUrlString('redirect'));
                window.location.href = redirectVal;
            });
        },
// ------------ 以下为私有方法 -------------------        
        /** 根据 设备序列号 获得 设备类型 **/
        _getDeviceType: function (serialNo) {
            var _this = this;
            $.ajax({            	
            	type: 'GET',
                url: '/device/getMycarDeviceInfoBySerialNo/' + serialNo,
                success: function (res) {
                	var res = JSON.parse(res);
                    if (res.code === 0) {
                        $('#serialNo').removeClass('layui-form-danger');
                        $('#deviceType').val(res.data.pdtType);
                    } else {                    	
                        _this._getDeviceTypeErrMsg(res.message);
                    }
                },
                error: function () {
                    _this._getDeviceTypeErrMsg('网络错误，设备类型获取失败。')
                }
            });
        },
        /** 根据 设备序列号 获得 设备类型 错误处理 **/
        _getDeviceTypeErrMsg: function (msg) {
            var $serialNo = $('#serialNo');
            $serialNo.focus().addClass('layui-form-danger');
            basic.msg(msg, 5, 6, function () {
                $serialNo.focus();
                $('#deviceType').val('');
            });
        },
        
	};
	OBJECT.init = function () {
		new this();
	};
    layui.use(['jquery', 'element', 'form'], function () {
        window.$ = layui.$;
        basic.UI.form    = layui.form;
		basic.UI.layer = layui.layer;
		basic.UI.element = layui.element;
		// OBJECT对象初始化 
        OBJECT.init();
    });	
})();