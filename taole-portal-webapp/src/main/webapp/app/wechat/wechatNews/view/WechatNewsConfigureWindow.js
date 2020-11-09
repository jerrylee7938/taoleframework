/**
 * 微信平台消息配置管理
 */
Ext.define("Taole.wechat.wechatNews.view.WechatNewsConfigureWindow", {
	extend: 'Ext.Window',
    alias : 'widget.wechatNewsConfigureWindow',    
    layout:'border',
    modal: true,
    frame: true,
    maximized: true,
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
    border: 0,
    initComponent: function() {
		var store = Ext.create('Taole.wechat.wechatNews.store.WechatNewsConfigureStore');		
		this.title = '微信平台消息配置管理';
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			height: 40,
			defaults:{
				xtype: 'panel',
				baseCls:'x-plain',
				frame: true
			},
			items:[
		   	{//--------------------------------第1行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 120,
					width: 280
				},
				items:[
					{
						fieldLabel: '微信平台账号名称',
						xtype: 'textfield',
						name: 'imei'
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
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columns: [{header: '序号',align: 'center',xtype:'rownumberer', width:35},
				{header: '账号名称', dataIndex: 'imei',align: 'center', width:150, editor:{
					xtype: 'textfield',
		        	allowBlank: false
		        }},
		        {header: '消息类型', dataIndex: 'role', align: 'center', width:150, editor:{
		        	xtype: 'textfield'
		        }},
		        {header: '事件名称', dataIndex: 'reMark',align: 'center', width:150, editor:{
					xtype: 'textfield'
		        }},
		        {header: '事件key', dataIndex: 'reMark',align: 'center', width:150, editor:{
					xtype: 'textfield'
		        }},
		        {header: '业务说明', dataIndex: 'reMark',align: 'center', width:150, editor:{
					xtype: 'textfield'
		        }},
		        {header: '回调url', dataIndex: 'reMark',align: 'center', width:300, editor:{
					xtype: 'textfield'
		        }}
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true}
		    ],
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: store,
				displayInfo: true
	        },
	        plugins:[{
		    	pluginId: 'rowediting',
		    	ptype: 'rowediting',
		    	clicksToEdit: 2,
		    	autoCancel: false,
		        saveBtnText: '保存',
		        cancelBtnText: '取消'
		    }],
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
		}
		];
		 this.buttons = [
		                 {
		                     text: '关闭',
		                     action: 'cancel'
		                 }
		             ];
        this.callParent(arguments);
    },
    buttonAlign: 'center'
});