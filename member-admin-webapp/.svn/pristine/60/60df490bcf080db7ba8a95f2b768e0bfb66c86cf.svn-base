/**
 * 门店管理
 */
Ext.define("Taole.store.storeManager.view.StorePanel", {
	extend: 'Ext.Panel',
    alias : 'widget.storePanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.store.storeManager.store.StoreStore',{
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
					labelWidth: 80,
					width: 250
				},
				items:[{
					fieldLabel: '店面名称',
					xtype: 'textfield',
					name:'name',
				},{
					fieldLabel: '状态',
					xtype: 'combobox',
					name: 'status',
					displayField: 'name',
					valueField: 'value',
				    store: getDicStore("bbbcb9dc0f2b4261aa2ab2b0c0df5675",URL_DICTIONARY_MEMBER),
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
			},{//--------------------------------第1行
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
					fieldLabel:'所属省份',
					name:'province',
					xtype:'combobox',
					displayField:'name',
					valueField:'value',
					store: getDicStore('CN'),
				},{
					fieldLabel: '所属地区',
					xtype: 'combobox',
					name:'city',
					id:'city',
					displayField:'name',
					valueField:'value',
					store: getDicStore(),
				
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
				{header: '所属省份', dataIndex: 'provinceTitle',align: 'center',width:150},
				{header: '所属地区', dataIndex: 'cityTitle',align: 'center',width:150},
				{header: '联系人电话', dataIndex: 'contactTel', align: 'center', width:100},
				{header: '联系人', dataIndex: 'contactPerson', align: 'center', width:100},
				{header: '详细地址', dataIndex: 'address', align: 'center', width:200},
				{header: '状态', dataIndex: 'shopStatusName', align: 'center', width:100}
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
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
		}
		]		
        this.callParent(arguments);
    }
});