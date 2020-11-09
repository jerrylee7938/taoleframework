/**
 * 卡管理
 */
Ext.define("Taole.card.cardManager.view.CardPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.cardPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.card.cardManager.store.CardStore',{
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
					fieldLabel: '卡名称',
					xtype: 'textfield',
					name:'cardName',
				},{
					fieldLabel: '卡有效期类型',
					xtype: 'combobox',
					name: 'chargeType',
					displayField:'name',
					valueField:'value',
				    store: getDicStore("21a0578289544ae5a24d7bfad7130d2d",URL_DICTIONARY_MEMBER),
				},
				{
					fieldLabel: '卡类型',
					xtype: 'combobox',
					name: 'cardType',
					displayField:'name',
					valueField:'value',
				    store: getDicStore("CARDTYPE_CODE",URL_DICTIONARY_MEMBER),
				},
				{
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
			    {header: '序号',align: 'center',xtype:'rownumberer', width:35},
				{header: '编码', dataIndex: 'code',align: 'center', width:150},
				{header: '名称', dataIndex: 'cardName',align: 'center',width:150},
				{header: '售价', dataIndex: 'money',align: 'center',width:100},
				{header: '有效次数', dataIndex: 'totalNum', align: 'center', width:100,renderer: function (val, meta, record) {
					if(record.data.cardType =='QXK'){
						return '--';
					}else {
						return val;
					}
				}},
				{header: '有效天数', dataIndex: 'periodOfValidity', align: 'center', width:100},
				{header: '有效期类型', dataIndex: 'cardExpireName', align: 'center', width:100,renderer: function (val, meta, record) {
					if(record.data.cardType =='JCK'){
						return '--';
					}else {
						return val;
					}
				}},
				{header: '卡类型', dataIndex: 'cardTypeName', align: 'center', width:100},
				{header: '上架日期', dataIndex: 'beginTime', align: 'center', width:150},
				{header: '下架日期', dataIndex: 'endTime', align: 'center', width:150},
				{header: '状态', dataIndex: 'cardStatusName', align: 'center', width:100},
			//	{header: '经办人', dataIndex: 'operator', align: 'center', width:100},
			//	{header: '审核人', dataIndex: 'peoples', align: 'center', width:100},
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true},'-',
		    	{text:'上架',action:'onsale', pressed:true},'-',
		    	{text:'下架',action:'offsale', pressed:true},'-',
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