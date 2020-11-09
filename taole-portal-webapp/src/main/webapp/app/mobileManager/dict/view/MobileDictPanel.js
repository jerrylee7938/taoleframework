/**
 * 手机号段管理
 */
Ext.define("Taole.mobileManager.dict.view.MobileDictPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.mobileDictPanel',    
    isVerify: null,
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.mobileManager.dict.store.MobileDictStore');	
		this.title = "手机号段管理";
		this.items = [
		{
			//-----------------------------------------------表单
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
					labelWidth: 90,
					width: 250
				},
				items:[
					{
						fieldLabel: '手机号',
						xtype: 'textfield',
						name: 'id'
					},
					{
						fieldLabel: '区号',
						xtype: 'textfield',
						name: 'code'
					},
					{
						fieldLabel: '归属地',
						xtype: 'textfield',
						name: 'desc'
					},
					{
						text: '查询', 
						xtype: 'button', 
						action: 'query', 
						style: 'margin-left:10px;',
						width: 60
					},
					{
						text: '重置', 
						xtype: 'button', 
						action: 'reset',
						style: 'margin-left:10px;',
						width: 60
					}
				]
			}]
		},
		{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			style: 'margin-top:3px;',
			store: store,
			columns: [
				{header: '序号',xtype: 'rownumberer',width: 40,sortable: false,align:'center'},
				{header: '手机号',dataIndex: 'id', align: 'center', width:300},
				{header: '区号',dataIndex: 'code', align: 'center', width:300},
				{header: '归属地',dataIndex: 'desc', align: 'center', width:300}
			],
			  //grid可复制
			viewConfig:{  
			   enableTextSelection:true  
			},
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: store,
				displayInfo: true
	        },
	        tbar : [
		    	{text:'新增',action:'add', pressed:true},
		    	{text:'修改',action:'update', pressed:true},
		    	{text:'删除',action:'del', pressed:true}
		    ],
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
		}
		]		
        this.callParent(arguments);
    }
});