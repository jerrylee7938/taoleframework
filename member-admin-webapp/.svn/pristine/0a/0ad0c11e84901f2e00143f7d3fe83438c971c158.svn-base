/**
 * 商品库管理
 */
Ext.define("Taole.goods.outOfStorageManager.view.OutOfStoragePanel", {
	extend: 'Ext.Panel',
    alias : 'widget.outOfStoragePanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.goods.outOfStorageManager.store.OutOfStorageStore',{
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
					width: 230
				},
				items:[{
					fieldLabel: '店面名称',
					xtype: 'combobox',
					name: 'shopId',
					displayField: 'name',
					valueField: 'shopId',
					store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
						autoLoad: true
					}),	
				},{
					fieldLabel: '商品类型',
					xtype: 'combobox',
					name: 'catalogId',
					displayField: 'name',
					valueField: 'value',
					store: getDicStore("dd85a58e85844fc3b838c6c0566e653b",URL_DICTIONARY_MEMBER),
				},{
					fieldLabel: '商品名称',
					xtype: 'textfield',
					name: 'name',	
				},{
					xtype:'hidden',
					name:'goodsId'
				},{
					text: '查询', 
					xtype: 'button', 
					action: 'query', 
					style: 'margin-left:10px;',
					width: 70
				},
				{
					text: '重置', 
					xtype: 'button', 
					action: 'reset',
					style: 'margin-left:10px;',
					width: 70
					}
				]
			}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columns: [
			    {header: '单据号', dataIndex: 'shopBillNo', align: 'center', width:100},
				{header: '店面名称', dataIndex: 'shopName',align: 'center', width:150},
				{header: '商品类型', dataIndex: 'catalogName',align: 'center',width:100},
				{header: '商品名称', dataIndex: 'name', align: 'center', width:150},
				{header: '商品编号', dataIndex: 'goodsCode',align: 'center',width:150},
				{header: '单据类型', dataIndex: 'shopBillTypeName',align: 'center',width:100,renderer: function(val, meta, record){
					if(record.data.inOutType == 'CK'){
						return '<span style="color:red">'+val+'<span>';
					}else if (record.data.inOutType == 'RK') {
						return '<span style="color:green">'+val+'<span>';
					}
				}},
				{header: '操作数量', dataIndex: 'amount',align: 'center',width:80,renderer: function(val, meta, record){
					if(record.data.inOutType == 'CK'){
						return '<span style="color:red">-'+val+'<span>';
					}else if (record.data.inOutType == 'RK') {
						return '<span style="color:green">+'+val+'<span>';
					}
				}},
				{header: '库存量', dataIndex: 'balance', align: 'center', width:80},
				{header: '单位', dataIndex: 'unitName', align: 'center', width:80},
				{header: '操作人', dataIndex: 'operator', align: 'center', width:100},
				{header: '操作时间', dataIndex: 'operatorTime', align: 'center', width:150},
			],
			 tbar : [
				    	{text:'商品出库',action:'delivery', pressed:true},'-',
				    	{text:'商品入库',action:'goIn', pressed:true}
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