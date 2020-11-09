/**
 * 选择会员卡窗口
 */
Ext.define("Taole.common.userCardChoose.view.UserCardChooseWindow", {
	extend: 'Ext.Window',
	alias : 'widget.userCardChooseWindow',
	layout: 'border',
	title: '请选择会员卡',
	width: 620,
	height: 400,
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
		var chooseGoodsStore = Ext.create('Taole.common.userCardChoose.store.UserCardChooseStore');
		this.items = [{
			xtype: 'form',
			region: 'north',
			height: 30,
			style: 'margin-top:5px;',
			frame: true,
			defaults:{
				labelAlign: 'right',
				labelWidth: 80
			},
			baseCls:'x-plain',
			layout: 'column',
			items:[
				   	{//--------------------------------第1行
						layout: 'column',
						frame: true,
						baseCls: 'x-plain',
						style: 'margin-top:5px;',
						defaults:{
							labelAlign: 'right',
							labelWidth: 80,
							width: 250
						},
						items:[{
							fieldLabel: '手机号',
							xtype: 'textfield',
							name:'telphone',
						},
						{
							text: '查询', 
							xtype: 'button', 
							action: 'query', 
							style: 'margin-left:10px;',
							width: 80
						},
						{
							text: '重置', 
							xtype: 'button', 
							action: 'reset',
							style: 'margin-left:10px;',
							width: 80
							}
						]
					}]
		},{
			xtype: 'grid',
			region:'center',
			style: 'margin-top:3px;',
			store: chooseGoodsStore,
			columns: [
			          {header: '序号',align: 'center',xtype:'rownumberer', width:35},
			          {header: '姓名', dataIndex: 'userName',align: 'center', width:100},
			          {header: '手机号', dataIndex: 'telphone',align: 'center',width:100},
			          {header: '会员卡号', dataIndex: 'cardNo',align: 'center',width:100},
			          {header: '卡名称', dataIndex: 'cardName',align: 'center',width:100},
			          {header: '开卡时间', dataIndex: 'createTime',align: 'center',width:150},
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