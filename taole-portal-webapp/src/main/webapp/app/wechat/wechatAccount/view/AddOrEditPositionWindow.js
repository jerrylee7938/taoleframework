/**
 * 新增、修改、查看
 */
Ext.define("Taole.wechat.wechatAccount.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 630,
	height: 275,
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
			items:[
			{
				//--------------------------------第1行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 130,
					width: 300
				},
				items:[
					{
						xtype:'hidden',
						name: 'id'
					},
					{
						fieldLabel: '平台账号',
						xtype: 'textfield',
						name: 'code',
					},
				]
			},
			{//--------------------------------第2行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 130,
					width: 300
				},
				items:[{
					fieldLabel: '平台密码',
					xtype: 'textfield',
					name: 'type',
				},{
					fieldLabel: '再次输入密码',
					xtype: 'textfield',
					name: 'password',
				}
				]	
			},{//--------------------------------第3行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 130,
					width: 300
				},
				items:[{
					fieldLabel: '腾讯微信号appID',
					xtype: 'textfield',
					name: 'password',
				},{
					fieldLabel: '腾讯微信号app_secret',
					xtype: 'textfield',
					name: 'password',
				}
				]	
			},{//--------------------------------第3行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 130,
					width: 300
				},
				items:[{
					fieldLabel: '腾讯微信号ID',
					xtype: 'textfield',
					name: 'password',
				}
				]	
			},
			{//--------------------------------第4行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 130,
					width: 300
				},
				items:[
				   {
					    xtype: 'textarea',
					    name:'description',
						fieldLabel:'腾讯微信号描述',
						width:600,
						height:80
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