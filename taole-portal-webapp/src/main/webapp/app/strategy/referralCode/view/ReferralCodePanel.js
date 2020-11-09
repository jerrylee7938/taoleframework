/**
 * 营销管理
 */
Ext.define("Taole.strategy.referralCode.view.ReferralCodePanel", {
	extend: 'Ext.Panel',
    alias : 'widget.referralCodePanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.strategy.referralCode.store.ReferralCodeStore',{
			autoLoad: true
		});		
		this.title = '营销推荐码管理';
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
					fieldLabel: '营销主体名称',
					xtype: 'textfield',
					name: 'title'
					},
					{
						fieldLabel: '营销渠道',
						xtype: 'combo',
						name:'appId',
						store: Ext.create('Taole.appManager.appInfo.store.AppInfoStore'),
						displayField: 'name',
						valueField: 'id'
					},
					{
						fieldLabel: '营销人员',
						xtype: 'textfield',
						name: 'name'
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
				{header: '营销主体名称', dataIndex: 'appName',align: 'center', width:150},
				{header: '营销主体类型', dataIndex: 'codeName',align: 'center',width:150},
				{header: '营销渠道', dataIndex: 'title', align: 'center', width:150},
				{header: '营销人员', dataIndex: 'typeName', align: 'center', width:150},
				{header: '推荐码', dataIndex: 'description', align: 'center', width:150},
				{header: '创建日期', dataIndex: 'createTime', align: 'center', xtype: 'datecolumn', format: 'Y-m-d H:i:s', width:200},
				
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true},'-',
		    	{text:'二维码管理',action:'addBanner', pressed:true,disabled:true}
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