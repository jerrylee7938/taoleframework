/**
 * 商品出库管理
 */
Ext.define("Taole.goods.goodsDelivery.view.GoodsDeliveryPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.goodsDeliveryPanel',  
    frame: true,
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.goods.goodsDelivery.store.GoodsDeliveryStore',{
		});		
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			height: 85,
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
					fieldLabel: '出库单号',
					xtype: 'textfield',
					name:'name',
				},
				{
					fieldLabel: '出库类型',
					xtype: 'combobox',
					name: 'appCode',
					displayField: 'name',
					valueField: 'value',
					store: Ext.create("Ext.data.Store",{
						autoLoad:true,
						fields:['value','name'],
						data:[{"name":"调拨出库","value":2}],
					}),
				},{
					fieldLabel: '操作人',
					xtype: 'textfield',
					name:'name',
				},{
					fieldLabel: '出库店面',
					xtype: 'combobox',
					name: 'appCode',
					displayField: 'name',
					valueField: 'value',
					store: Ext.create("Ext.data.Store",{
						autoLoad:true,
						fields:['value','name'],
						data:[{"name":"亦庄店","value":1},{"name":"双井店","value":2}],
					}),
				},
				
				]
			},{
				//--------------------------------第1行
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
					fieldLabel: '出库备注',
					xtype: 'textarea',
					name:'name',
					width: 1000,
					height:40
				}]
			}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columnLines:true,
			plugins:[{
		    	pluginId: 'rowediting',
		    	ptype: 'rowediting',
		    	clicksToEdit: 2,
		    	autoCancel: false,
		        saveBtnText: '保存',
		        cancelBtnText: '取消'
		    }],
			columns: [
			    {header: '序号',align: 'center',xtype:'rownumberer', width:35},
				{header: '产品编号', dataIndex: 'no',align: 'center', width:150},
				{header: '产品类型', dataIndex: 'name',align: 'center',width:150},
				{header: '产品名称', dataIndex: 'price',align: 'center',width:150},
				{header: '产品规格', dataIndex: 'yxcs', align: 'center', width:250},
				{header: '单位', dataIndex: 'yxts', align: 'center', width:150},
				{header: '数量', dataIndex: 'num',editor:{xtype:'numberfield'}, align: 'center', width:150},
			],
		    tbar : [
		    	{text:'选择出库产品',action:'add', pressed:true}
		    ],
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
		}
		];
		this.buttons = [
		                {
		    				text: '确定',
		    				action: 'confirm'
		    			}
		            ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});