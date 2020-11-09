/**
 * 个店面库存管理
 */
Ext.define("Taole.goods.stockManager.view.StockPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.stockPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.goods.stockManager.store.StockStore',{
			autoLoad: true
		});		
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
					fieldLabel: '店面名称',
					xtype: 'combobox',
					name: 'appCode',
					displayField: 'name',
					valueField: 'value',
					store: Ext.create("Ext.data.Store",{
						autoLoad:true,
						fields:['value','name'],
						data:[{"name":"华堂店","value":1},{"name":"双井店","value":2}],
					}),
					
				},{
					fieldLabel: '产品名称',
					xtype: 'textfield',
					name:'name',
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
				{header: '店面名称', dataIndex: 'name',align: 'center', width:150},
				{header: '产品编号', dataIndex: 'no',align: 'center',width:150},
				{header: '产品类型', dataIndex: 'type',align: 'center',width:150},
				{header: '产品名称', dataIndex: 'product', align: 'center', width:250},
				{header: '产品规格', dataIndex: 'yxts', align: 'center', width:100},
				{header: '售价（元）', dataIndex: 'price',align: 'center', width:100},
				{header: '单位', dataIndex: 'site',align: 'center',width:100},
				{header: '当前存量', dataIndex: 'num',align: 'center',width:100},
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