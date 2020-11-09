/**
 * 审核
 */
Ext.define("Taole.user.userInfoManager.view.UpdateUserInfoWindow", {
	extend: 'Ext.Window',
	alias : 'widget.updateUserInfoWindow',
	layout: 'border',
	width: 560,
	height: 200,
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
					width:300,
					frame: true,
					baseCls: 'x-plain',
					height:'auto',
					defaults:{
						xtype: 'panel',
						baseCls:'x-plain',
						frame: true
					},
					items:[
					{
						//--------------------------------第2行
						layout: 'column',
						frame: true,
						baseCls: 'x-plain',
						defaults:{
							labelAlign: 'right',
							labelWidth: 80,
							width: 300,
							style: 'margin-top:5px;',
						},
						items:[
							{
								fieldLabel: '会员手机号',
								xtype: 'textfield',
								name: 'telphone',
							},{
								fieldLabel: '宝宝姓名',
								xtype: 'textfield',
								name: 'userName',
							},{
								xtype:'hidden',
								name:'userCardId',
							}
						]
					},{
						//--------------------------------第2行
						layout: 'column',
						frame: true,
						baseCls: 'x-plain',
						defaults:{
							labelAlign: 'right',
							labelWidth: 80,
							width: 300,
							style: 'margin-top:5px;',
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
					}]
				},{
					x:320,
					width:300,
					height:120,
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
		                width: 100,
		                height: 100,
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
						style : 'margin-left:3px'	
					}]
					
				}]
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