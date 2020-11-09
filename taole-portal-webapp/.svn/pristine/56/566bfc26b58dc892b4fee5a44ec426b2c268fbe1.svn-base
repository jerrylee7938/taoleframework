/**
 * 新增、修改、查看
 */
Ext.define("Taole.sms.smsApply.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 730,
	height: 320,
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
	taskId: null,
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
					labelWidth: 120,
					width: 350
				},
				items:[
					{
						xtype:'hidden',
						name: 'id'
					},
					{
						fieldLabel: '公司名称',
						xtype: 'textfield',
						readOnly:true,
						fieldStyle:'background-color:#EEEEE0;background-image: none;',
						name: 'companyName',
					},
					{
						fieldLabel: '申请人姓名',
						xtype: 'textfield',
						fieldStyle:'background-color:#EEEEE0;background-image: none;',
						readOnly:true,
						name: 'name',
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
					labelWidth: 120,
					width: 350
				},
				items:[{
					fieldLabel: '申请人电话',
					xtype: 'textfield',
					fieldStyle:'background-color:#EEEEE0;background-image: none;',
					readOnly:true,
					name: 'tel',
				},{
					fieldLabel: '申请人邮箱地址',
					xtype: 'textfield',
					fieldStyle:'background-color:#EEEEE0;background-image: none;',
					readOnly:true,
					name: 'email',
				}
				]	
			},{//--------------------------------第3行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 120,
					width: 350
				},
				items:[{
					fieldLabel: '申请套餐类型',
					xtype: 'textfield',
					fieldStyle:'background-color:#EEEEE0;background-image: none;',
					readOnly:true,
					name: 'typeName',
				},{
					fieldLabel: '发送短信前缀',
					xtype: 'textfield',
					name: 'prefix',
				}
				]	
			},{//--------------------------------第3行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 120,
					width: 350
				},
				items:[{
					fieldLabel: '申请人公司地址',
					xtype: 'textfield',
					name: 'companyAddr',
				},{
					fieldLabel: '产品所属行业',
					xtype: 'textfield',
					name: 'profession',
				}
				]	
			},{//--------------------------------第3行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 120,
					width: 350
				},
				items:[{
					fieldLabel: '申请人公司职务',
					xtype: 'textfield',
					name: 'job',
				},{
					fieldLabel: '申请人微信号',
					xtype: 'textfield',
					name: 'wechat',
				}
				]	
			},{//--------------------------------第3行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 120,
					width: 350
				},
				items:[{
					fieldLabel: '申请人QQ号',
					xtype: 'textfield',
					name: 'qq',
				},{
					fieldLabel: '短信接入方服务器IP',
					xtype: 'textfield',
					name: 'ip',
				}
				]	
			},{//--------------------------------第3行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 120,
					width: 700
				},
				items:[{
					fieldLabel: '短信接口服务地址',
					xtype: 'textfield',
					name: 'hostUrl',
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
					labelWidth: 120,
					width: 350
				},
				items:[
				   {
					    xtype: 'textarea',
					    name:'callbackUrl',
						fieldLabel:'短信到达回调地址',
						width:700,
						height:50
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