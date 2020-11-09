/**
 * 微信平台账号管理
 */
Ext.define("Taole.wechat.wechatAccount.view.WechatAccountPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.wechatAccountPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.wechat.wechatAccount.store.WechatAccountStore',{
			autoLoad: true
		});		
		this.title = '微信平台账号管理';
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
					labelWidth: 80,
					width: 250
				},
				items:[{
					fieldLabel: '平台账号',
					xtype: 'textfield',
					name: 'title'
					},
					{
						fieldLabel: '微信账号',
						xtype: 'textfield',
						name:'appId',
					},{
						fieldLabel: '微信号id',
						xtype: 'textfield',
						name:'accountId',
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
			columns: [
				{header: '平台账号', dataIndex: 'appName',align: 'center', width:150},
				{header: '微信微信', dataIndex: 'codeName',align: 'center',width:150},
				{header: '服务号id', dataIndex: 'title', align: 'center', width:250},
				{header: '说明', dataIndex: 'typeName', align: 'center', width:150},
				{header: '创建时间', dataIndex: 'description', align: 'center', width:300},				
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true},'-',
		    	{text:'消息配置',action:'configure', pressed:true},'-',
		    	{text:'关注用户查询',action:'userfocus', pressed:true},
		    ],
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: store,
				displayInfo: true
	        },
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
		}
		]		
        this.callParent(arguments);
    }
});