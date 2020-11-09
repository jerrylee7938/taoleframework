/**
 * 审核
 */
Ext.define("Taole.user.userInfoManager.view.CardOpenWindow", {
	extend: 'Ext.Window',
	alias : 'widget.cardOpenWindow',
	layout: 'border',
	width: 850,
	height: 350,
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
				baseCls: 'x-plain',
				height:'auto',
				defaults:{
					xtype: 'panel',
					baseCls:'x-plain',
					frame: true
				},
				layout:'absolute',
				items:[{
					width:750,
					frame: true,
					baseCls: 'x-plain',
					height:'auto',
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
								fieldLabel: '开卡人',
								xtype: 'combobox',
								name: 'operator',
								fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
								readOnly:true
							},{
								fieldLabel: '店面名称',
								xtype: 'combobox',
								name: 'operatorShopId',
								displayField: 'name',
								valueField: 'shopId',
								store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
									autoLoad: true
								}),
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
								fieldLabel: '会员手机号',
								xtype: 'textfield',
								name: 'telphone',
							},{
								fieldLabel: '会员姓名',
								xtype: 'textfield',
								name: 'userName',
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
								fieldLabel: '性别',
								xtype: 'combobox',
								name: 'gender',
								displayField: 'name',
								valueField: 'value',
								store:getDicStore("33f400dc7b534122b4b757e4e2a824df",URL_DICTIONARY_MEMBER),
							},{
								fieldLabel: '出生日期',
								xtype:'datetimefield',
								format:'Y-m-d',	
								name: 'birthday',
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
								fieldLabel: '卡名称',
								xtype: 'combobox',
								name: 'cardName',
//								displayField: 'cardName',
//								valueField: 'cardId',
//								store: Ext.create('Taole.card.cardManager.store.CardStore',{
//									autoLoad: true
//								}),
								fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
								readOnly:true
							},{
								xtype: 'hidden',
								name: 'cardId',
							
							},{
								fieldLabel: '卡号',
								xtype: 'textfield',
								name: 'cardNo',
								fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
								readOnly:true
							}
						]
					}]
				},{
					x:630,
					width:300,
					height:100,
					frame: true,
					baseCls: 'x-plain',
					id:'imageForm',
					layout:'absolute',
					defaults:{
						labelAlign: 'right',
						labelWidth: 80,
						style: 'margin-top:5px;',
						width: 250
					},
					items:[{
		                width: 90,
		                height: 90,
		                name: 'avatar',
		                id:'avatar',
		                xtype: 'image',
		                fieldLabel: "预览图片",
		                style:{
							'border':'1px solid gray;margin-left:5px;margin-top:5px;'
						}
					},{
						xtype:'button',
						x:110,
						y:10,
				        text : '选择图片',
				        width:80,
				        id:'t123',
						cls : Ext.baseCSSPrefix + 'form-file-wrap',
						preventDefault : false,
						style : 'margin-left:3px'	
					},{
						xtype:'button',
						x:110,
						y:40,
						action:'paizhao',
				        text : '拍照',
				        width:80,
				        id:'paizhao',
					//	preventDefault : false,
						style : 'margin-left:3px'	
					}]
					
				}]
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
					},{
						fieldLabel: '有效期期限',
						xtype: 'textfield',
						name: 'periodOfValidity',
						width: 160,
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					},{
						xtype:'label',
						text:"天",
						width: 20,
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
						fieldLabel: '截止日期',
						xtype: 'textfield',
						name: 'deadline',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					},{
						fieldLabel: '售价',
						xtype: 'textfield',
						name: 'money',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					},{
						fieldLabel: '次数',
						xtype: 'textfield',
						name: 'cardNum',
						width: 200,
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
						fieldLabel: '实付金额',
						xtype: 'textfield',
						name: 'payMoney',
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					},{
						fieldLabel: '支付方式',
						xtype: 'combobox',
						name: 'payType',
						displayField: 'name',
						valueField: 'value',
						store:getDicStore("PAYMODE_CODE",URL_DICTIONARY_MEMBER),
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					}]
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
						width: 800,
						height:60,
						fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
						readOnly:true
					}
				]
			}]
		}];
        this.buttons = [
            {
				text: '确认',
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