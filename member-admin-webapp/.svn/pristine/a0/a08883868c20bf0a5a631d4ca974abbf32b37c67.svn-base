/**
 * 商品管理
 */
Ext.define("Taole.goods.goodsManager.view.GoodsPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.goodsPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.goods.goodsManager.store.GoodsStore',{
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
					fieldLabel: '商品类型',
					xtype: 'combobox',
					name: 'catalogId',
					displayField: 'name',
					valueField: 'value',
					store: getDicStore("dd85a58e85844fc3b838c6c0566e653b",URL_DICTIONARY_MEMBER),
					
				},{
					fieldLabel: '商品名称',
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
				{header: '商品编号', dataIndex: 'goodsCode',align: 'center', width:150},
				{header: '商品类型', dataIndex: 'goodsType',align: 'center',width:100},
				{header: '商品名称', dataIndex: 'name',align: 'center',width:150},
				{header: '线上是否可售', dataIndex: 'onlineBuy',align: 'center',width:100,renderer:function(v,rec,record){
					if(v=='1'){
						return '可售';
					}else{
						return '不可售';
					}
    				}},
				{header: '商品规格', dataIndex: 'description', align: 'center', width:250},
				{header: '单位', dataIndex: 'unitName', align: 'center', width:80},
				{header: '单价（元）', dataIndex: 'saleMoney', align: 'center', width:100},
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true},'-',
		    	{text:'可售店面配置',action:'choose', pressed:true}
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