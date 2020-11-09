/**
 * 营销管理
 */
Ext.define("Taole.strategy.marketingSubject.view.MarketingSubjectPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.marketingSubjectPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.strategy.marketingSubject.store.MarketingSubjectStore',{
			autoLoad: true
		});		
		this.title = '营销主体管理';
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
						fieldLabel: '营销主体类型',
						xtype: 'combo',
						name:'appId',
						store: Ext.create('Taole.strategy.marketingType.store.MarketingTypeStore'),
						displayField: 'name',
						valueField: 'id'
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
				{header: '营销主体url', dataIndex: 'title', align: 'center', width:250},
				{header: '营销背景图', dataIndex: 'typeName', align: 'center', width:150},
				{header: '说明', dataIndex: 'description', align: 'center', width:300},				
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true},'-',
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