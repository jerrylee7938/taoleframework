/**
 * 账目明细管理
 */
Ext.define("Taole.reportSheet.accountManager.view.AccountPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.accountPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    defaults: {
        split: true
    },
    initComponent: function() {
		var store = Ext.create('Taole.reportSheet.accountManager.store.AccountStore',{
			autoLoad: true
		});	
		var store2 = Ext.create('Taole.reportSheet.accountManager.store.AccountDetailStore',{
			autoLoad: false
		});	
		this.items = [{//-----------------------------------------------表单
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
					width: 230
				},
				items:[{
					fieldLabel: '店名',
					xtype: 'combobox',
					name: 'shopId',
					displayField: 'name',
					valueField: 'shopId',
					store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
						autoLoad: true
					}),	
				},
				{
					fieldLabel: '卡号',
					xtype: 'textfield',
					name:'cardNo',
				},{
					fieldLabel: '顾客姓名',
					xtype: 'textfield',
					name:'name',
				},{
					xtype: 'hidden',
					name:'userId',
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
			},{//--------------------------------第1行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 80,
					width: 230
				},
				items:[
				{
					fieldLabel: '付款方式',
					xtype: 'combobox',
					name: 'payType',
					displayField:'name',
					valueField:'value',
					store: getDicStore("PAYMODE_CODE",URL_DICTIONARY_MEMBER),
				},{
					fieldLabel: '交易类型',
					xtype: 'combobox',
					name: 'actionTypeId',
					displayField:'name',
					valueField:'value',
					store: getDicStore("TRANSACTIONTYPE_CODE",URL_DICTIONARY_MEMBER),
				},
				{
					fieldLabel: '开始日期',
					xtype: 'datetimefield',
					format: 'Y-m-d',
					name:'timeStart',
				},
				{
					fieldLabel: '结束日期',
					xtype: 'datetimefield',
					format: 'Y-m-d',
					name:'timeEnd',
				}]
			}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columns: [
			    {header: '序号',align: 'center',xtype:'rownumberer', width:35},
			    {header: '交易单号', dataIndex: 'userBillNo', align: 'center', width:150},
			    {header: '日期', dataIndex: 'createTime', align: 'center', width:150},
				{header: '店名', dataIndex: 'shopName', align: 'center', width:150},
				{header: '顾客姓名', dataIndex: 'userName', align: 'center', width:100},
				{header: '会员卡号', dataIndex: 'cardNo', align: 'center', width:150,renderer:function(val, meta, record){
					if(record.data.userId == 'SK'){
						return '--';
					}else {
						return val;
					}
				}},
				{header: '卡类型', dataIndex: 'cardType', align: 'center', width:100,renderer:function(val, meta, record){
					if(record.data.userId == 'SK'){
						return '--';
					}else {
						return val;
					}
				}},
				{header: '划卡(次)', dataIndex: 'swipeAmount', align: 'center', width:100,renderer:function(val, meta, record){
					if(record.data.userId == 'SK'){
						return '--';
					}else {
						return val;
					}
				}},
				{header: '交易金额(元)', dataIndex: 'consumeMoney', align: 'center', width:100},
				{header: '交易类型', dataIndex: 'actionTypeName', align: 'center', width:100},
				{header: '付款方式', dataIndex: 'payTypeName', align: 'center', width:100},
				{header: '商品明细', dataIndex: 'rankName', align: 'center', width:100,renderer: function(val, meta, record){
					if(!window.getShopDetail){
						window.getShopDetail = function(userBillId){
							store2.removeAll();
		    				store2.proxy.url = Ext.util.Format.format(URL_MEMBER_USERBILL_GOODSINFO, userBillId);
		    				store2.load();
						}
					}
					
					if(record.data.actionTypeId =='XF'){
						return'<a href="javascript:getShopDetail(\''+record.data.userBillId+'\')">明细</a>';
					}else{return '--';}
					
				}},
				{header: '经办人', dataIndex: 'operator', align: 'center', width:100},	
			],
			 viewConfig:{  
	                enableTextSelection:true  
	            }, 
		    tbar : [
		        	{text:'导出交易流水excel',action:'export', pressed:true},'-',
		        	{text:'导出商品流水excel',action:'export2', pressed:true},
		    ],
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: store,
				displayInfo: true
	        },
		    columnLines: true,
		    selModel:{
		//    	selType : 'checkboxmodel'
		    }
		},{
			enableDragDrop:true,
			collapsible:true,
			xtype: 'panel',
			region: 'south',
			title:'明细',
			frame: true,
			height:200,
			layout: {
		        type: 'border',
		        padding: 5
		    },
			items:[{//---------------------------------------------表格
				xtype: 'grid',
				region:'center',
				store: store2,
				columns: [
				    {header: '序号',align: 'center',xtype:'rownumberer', width:35},
				    {header: '商品编码', dataIndex: 'goodsCode', align: 'center', width:150},
				    {header: '商品名称', dataIndex: 'goodsName', align: 'center', width:150},
					{header: '单价', dataIndex: 'price', align: 'center', width:100},
					{header: '数量', dataIndex: 'amount', align: 'center', width:100},
					{header: '单位', dataIndex: 'unitName', align: 'center', width:100},
					{header: '金额', dataIndex: '', align: 'center', width:100,renderer: function(val, meta, record){		
						return record.data.price*record.data.amount;
					}},
					{header: '备注', dataIndex: 'dcp1', align: 'center', width:200},		
				],
				 viewConfig:{  
		                enableTextSelection:true  
		            }, 
			    columnLines: true,
			    selModel:{
			    //	selType : 'checkboxmodel'
			    }
			}]
		}
		]		
        this.callParent(arguments);
    }
});