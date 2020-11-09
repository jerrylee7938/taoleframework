/**
 * 会员卡充值审核管理
 */
Ext.define("Taole.user.tradeeExamine.view.TradeeExaminePanel", {
	extend: 'Ext.Panel',
    alias : 'widget.tradeeExaminePanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.user.tradeeExamine.store.TradeeExamineStore',{
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
					fieldLabel: '会员卡号',
					xtype: 'textfield',
					name:'cardNo',
				},{
					name:'transType',
					xtype:'hidden',
					value:'CZ'
				},{
					fieldLabel: '店面名称',
					xtype: 'combobox',
					name: 'shopId',
					displayField: 'name',
					valueField: 'shopId',
					store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
						autoLoad: true
					}),
				},
				{
					fieldLabel: '状态',
					xtype: 'combobox',
					name: 'status',
					displayField: 'name',
					valueField: 'value',
				    store: getDicStore("c1e3792dfcbb4bc2a87d61ee1ef08287",URL_DICTIONARY_MEMBER),
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
					name:'timeStart',
				},{
					fieldLabel: '结束日期',
					xtype:'datetimefield',
					format:'Y-m-d',	
					name:'timeEnd',
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
			    {header: '会员卡号', dataIndex: 'cardNo', align: 'center', width:150},
			    {header: '卡名称', dataIndex: 'cardName', align: 'center', width:150},
				{header: '会员姓名', dataIndex: 'userName', align: 'center', width:150},
				{header: '店铺名称', dataIndex: 'shopName', align: 'center', width:150},
				{header: '交易类型', dataIndex: 'transTypeName', align: 'center', width:100},
				{header: '交易金额', dataIndex: 'money', align: 'center', width:100,},
				{header: '充值次数', dataIndex: 'chargeNo', align: 'center', width:80,renderer:function(val, meta, record){
					console.log(record.data.cardType)
					if(record.data.cardType == 'QXK'){
						return '--';
					}else {
						return val;
					}
				}},
				{header: '充值张数', dataIndex: 'amount', align: 'center', width:80,renderer:function(val, meta, record){
					if(record.data.cardType == 'JCK'){
						return '--';
					}else {
						return val;
					}
				}},
				{header: '支付方式', dataIndex: 'payTypeName', align: 'center', width:100,},
				{header: '交易时间', dataIndex: 'createTime', align: 'center', width:150},
				{header: '状态', dataIndex: 'statusName', align: 'center', width:100},
				{header: '备注', dataIndex: 'description', align: 'center', width:200,renderer:function(v,rec,record){
					rec.tdAttr = 'qclass="x-tip" data-qtitle="备注：" data-qwidth="200" data-qtip="'+v+ '"';
    				return v;}},
			],
			 viewConfig:{  
	                enableTextSelection:true  
	            }, 
		    tbar : [	
					{text:'审核通过',action:'examine', pressed:true},
					{text:'审核不通过',action:'examineNo', pressed:true},
					{text:'修改到期时间',action:'update', pressed:true},
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