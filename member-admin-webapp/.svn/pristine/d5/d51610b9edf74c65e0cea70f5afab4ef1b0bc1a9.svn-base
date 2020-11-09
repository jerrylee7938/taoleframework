/**
 * 用户管理
 */
Ext.define("Taole.user.userCardHistory.view.UserCardHistoryPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.userCardHistoryPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.user.userCardHistory.store.UserCardHistoryStore',{
			autoLoad: true
		});		
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			height: 70,
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
					labelWidth: 100,
					width: 280
				},
				items:[{
					fieldLabel: '卡号',
					xtype: 'textfield',
					name:'nameLike',
				},{
					fieldLabel: '店面名称',
					xtype: 'combobox',
					name: 'appCode',
					displayField: 'name',
					valueField: 'value',
					store: Ext.create("Ext.data.Store",{
						autoLoad:true,
						fields:['value','name'],
						data:[{"name":"双井店","value":1},{"name":"亦庄店","value":2}],
					}),
				},
				{
					fieldLabel: '操作类型',
					xtype: 'combobox',
					name: 'appCode',
					displayField: 'name',
					valueField: 'value',
					store: Ext.create("Ext.data.Store",{
						autoLoad:true,
						fields:['value','name'],
						data:[{"name":"办卡","value":1},{"name":"开卡","value":2}],
					}),
				}
				]
			},{//--------------------------------第1行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 100,
					width: 280
				},
				items:[
				{
					fieldLabel: '起始日期',
					xtype:'datetimefield',
					format:'Y-m-d',	
					name:'telPhone',
				},{
					fieldLabel: '结束日期',
					xtype:'datetimefield',
					format:'Y-m-d',	
					name:'telPhone',
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
			    {header: '序号',align: 'center',xtype:'rownumberer', width:35},
			    {header: '卡号', dataIndex: 'id', align: 'center', width:150},
			    {header: '卡名称', dataIndex: 'peopleID', align: 'center', width:150},
				{header: '店面名称', dataIndex: 'name', align: 'center', width:150},
				{header: '操作人', dataIndex: 'phone', align: 'center', width:150},
				{header: '操作类型', dataIndex: 'time', align: 'center', width:150},
				{header: '次数', dataIndex: 'status', align: 'center', width:100,},
				{header: '剩余次数', dataIndex: 'peopleID', align: 'center', width:150,},
				{header: '截止时间', dataIndex: 'status', align: 'center', width:100},
				{header: '操作时间', dataIndex: 'status', align: 'center', width:100},
				{header: '备注', dataIndex: 'status', align: 'center', width:150},
			],
			 viewConfig:{  
	                enableTextSelection:true  
	            }, 
		    tbar : [],
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