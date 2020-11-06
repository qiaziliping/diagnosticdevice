// [智能合约规则管理]页面相关js文件
;(function() {
	var ruleObj = function() {
		
		this.addRuleBtnClick(); // 添加按钮单击
		this.rowMonitor();      // 行监听
	};
	
	ruleObj.tableTemp = null; //列表对象
	
	
	ruleObj.prototype = {
		constructor : ruleObj,
		
		
		addRuleBtnClick: function() {
			var _this = this;
            $(document).on('click', '#addRuleBtn', _.throttle(function () {
//                location.href = 'profit_rule_add.html?redirect=' + encodeURIComponent(window.location.href);
                window.location.href = '/view/goAllocationGroupAdd?redirect=' + encodeURIComponent(window.location.href)
            }, 1000, this.throttleOption));
		},
		// 行监听
		rowMonitor: function() {
			var _this = this;
			// 行数据操作，监听行工具事件
			basic.UI.table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
				var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
				if (layEvent === 'detail') { //编辑
					var jobGroupId = data.jobGroupId;
					window.location.href = '/view/goAllocationGroupDetail?jobGroupId='+jobGroupId+'&redirect=' + encodeURIComponent(window.location.href)
				}
				if (layEvent === 'edit') { //编辑
					//_this._editAccountBtnClick(data);
					var jobGroupId = data.jobGroupId;
					window.location.href = '/view/goAllocationGroupEdit?jobGroupId='+jobGroupId+'&redirect=' + encodeURIComponent(window.location.href)
				}
			});
		},
		
		
	};
	
	ruleObj.init = function() {
		new this;
	};
	
	// 初始化页面列表
	loadZnhyRule = function() {
		// 获取合约规则分配租
		var listUrl = basic_url.get_allocation_group_list;
		
		ruleObj.tableTemp = basic.UI.table.render({
			elem : '#ruleList',
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
				{field : 'jobGroupId',width : 60,title : 'ID',hide : true}, 
				{field : 'groupName',title : '规则名称'}, 
				{field : 'createDate',title : '创建时间',width : '40%',minWidth : 50},//minWidth：局部定义当前单元格的最小宽度 
				{fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'} 
				] ]
		});
		
		//监听提交，form表单提交，根据条件搜索
		basic.UI.form.on('submit(formDemo)', function(data) {
			var name = data.field.groupName;
			// 重新渲染数据
			ruleObj.tableTemp.reload({
				where : {
					groupName : name
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
		
		ruleObj.init();
		// 加载列表
		loadZnhyRule();
	});
	
})();


