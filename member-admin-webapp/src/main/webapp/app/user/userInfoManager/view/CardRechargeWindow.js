/**
 * 卡充值
 */
var required ='<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define("Taole.user.userInfoManager.view.CardRechargeWindow", {
	extend: 'Ext.Window',
	alias : 'widget.cardRechargeWindow',
	layout: 'border',
	width: 650,
	height: 300,
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
						beforeLabelTextTpl: required,
						allowBlank: false,
					},{
						fieldLabel: '操作人',
						xtype: 'textfield',
						name: 'operator',
						value:getUserName(),
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					},{
						xtype: 'hidden',
						name: 'userCardId',
					},{
						xtype: 'hidden',
						name: 'operatorId',
						value:dataUser.userId,
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
					{   xtype: 'hidden',
						name: 'cardID',
						},{
						fieldLabel: '选择卡',
						xtype: 'textfield',
						name: 'cardName',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					},{
						fieldLabel: '支付方式',
						xtype: 'combobox',
						name: 'payType',
						displayField: 'name',
						valueField: 'value',
						store:getDicStore("PAYMODE_CODE",URL_DICTIONARY_MEMBER),
						beforeLabelTextTpl: required,
						allowBlank: false,
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
						fieldLabel: '卡类型',
						xtype: 'textfield',
						name: 'cardTypeName',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					
					},{
						fieldLabel: '有效期类型',
						xtype: 'textfield',
						name: 'chargeTypeName',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
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
						fieldLabel: '有效期限',
						xtype: 'textfield',
						name: 'periodOfValidity',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true,
						width:275
					},{
						xtype: 'label',
						text:'天',
						width:20,
						style: 'margin-left:5px;margin-top:3px;',
					},{
						fieldLabel: '截止日期',
						xtype: 'textfield',
						name: 'deadline',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
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
						fieldLabel: '售价',
						xtype: 'textfield',
						name: 'money',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true,
						width:275
					},{
						xtype: 'label',
						text:'元',
						width:20,
						style: 'margin-left:5px;margin-top:3px;',
					},{
						fieldLabel: '次数',
						xtype: 'numberfield',
						name: 'chargeNo',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
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
						fieldLabel: '张数',
						xtype: 'numberfield',
						name: 'cardAmount',
						minValue:1,
						allowBlank: false,
					},{
						fieldLabel: '充值金额',
						xtype: 'numberfield',
						name: 'consumeMoney',
						readOnly:true,
						width:275,
						beforeLabelTextTpl: required,
						allowBlank: false,
					},{
						xtype: 'label',
						text:'元',
						width:20,
						style: 'margin-left:5px;margin-top:3px;',
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