/**
 * 退卡充值
 */
Ext.define("Taole.user.userInfoManager.view.CardBackWindow", {
	extend: 'Ext.Window',
	alias : 'widget.cardBackWindow',
	layout: 'border',
	width: 650,
	height: 260,
//	maximized: true,
	modal: true,
	/**
	 * @type Function 
	 * 
	 */
	afterChooseFn: Ext.emptyFn,
	/**
	 * 调用afterChooseFn时的作用域，默认window
	 * @type 
	 */
	scope: window,
	appAdPositionId: null,
	isView: null,
	isRetrieve: null,
	initComponent: function() {
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'center',
			frame: true,
			defaults:{
				xtype: 'panel',
				baseCls:'x-plain',
				frame: true
			},
			items:[{
				//--------------------------------第1行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 80,
					width: 300
				},
				items:[
					{
						fieldLabel: '店面名称',
						xtype: 'combobox',
						name: 'shopId',
						displayField: 'name',
						valueField: 'shopId',
						store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
							autoLoad: true
						}),
					},{
						fieldLabel: '操作人',
						xtype: 'combobox',
						name: 'operator',
						value:getUserName(),
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					},{
						xtype: 'hidden',
						name: 'userCardId',
					}
				]
			},
			{
				//--------------------------------第2行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 80,
					width: 300
				},
				items:[
					{
						fieldLabel: '剩余次数',
						xtype: 'textfield',
						name: 'balanceNum',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					},{
						fieldLabel: '返还金额',
						xtype: 'textfield',
						name: 'consumeMoney',
					}
				]
			},{
				//--------------------------------第2行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 80,
					width: 300
				},
				items:[
					{
						fieldLabel: '支付方式',
						xtype: 'combobox',
						name: 'payType',
						displayField: 'name',
						valueField: 'value',
						store:getDicStore("PAYMODE_CODE",URL_DICTIONARY_MEMBER),
					}
				]
			},{
				//--------------------------------第2行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 80,
					width: 300
				},
				items:[
					{
						fieldLabel: '备注',
						xtype: 'textarea',
						name: 'description',
						width: 600,
						height:60
					}
				]
			}]
		}];
        this.buttons = [
            {
				text: '确定',
				action: 'confirm'
			}
            ,
            {
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});