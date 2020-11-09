/**
 * 选择用户窗口
 */
Ext.define("Taole.common.userChoose.view.UserChooseWindow", {
	extend: 'Ext.Window',
	alias : 'widget.userChooseWindow',
	layout: 'border',
	title: '请选择用户',
	width: 580,
	height: 500,
	modal: true,
	/**
	 * 是否允许选择多个用户
	 */
	allowMultiSelect: false,
	/**
	 * 用户名称
	 */
	roleName: null,
	/**
	 * 选择用户后调用的函数
	 * @type Function 
	 * 
	 */
	afterChooseFn: Ext.emptyFn,
	/**
	 * 调用afterChooseFn时的作用域，默认window
	 * @type 
	 */
	scope: window,
	initComponent: function() {
		var chooseGoodsStore = Ext.create('Taole.common.userChoose.store.UserChooseStore');
		this.items = [{
			xtype: 'form',
			region: 'north',
			height: 30,
			style: 'margin-top:5px;',
			frame: true,
			defaults:{
				labelAlign: 'right',
				labelWidth: 60
			},
			baseCls:'x-plain',
			layout: 'column',
			items:[
			{fieldLabel: '姓名', xtype: 'textfield', name: 'name', width: 200},
			{fieldLabel: '电话', xtype: 'textfield', name: 'telphone', width: 200},
			{text: '查询', xtype: 'button', action: 'query', width: 60, style:'margin-left:20px;'},
			{text: '重置', xtype: 'button', action: 'reset', width: 60, style:'margin-left:20px;'}
			]
		},{
			xtype: 'grid',
			region:'center',
			style: 'margin-top:3px;',
			store: chooseGoodsStore,
			columns: [
			          {header: '序号',align: 'center',xtype:'rownumberer', width:35},
			          {header: '姓名', dataIndex: 'name',align: 'center', width:100},
			          {header: '手机号', dataIndex: 'telphone',align: 'center',width:100},
			          {header: '创建时间', dataIndex: 'createTime',align: 'center',width:180},
			          ],
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: chooseGoodsStore,
				displayInfo: true
	        },
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
  		}];
        this.buttons = [
            {
				text: '确定',
				action: 'confirm'
			},{
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});