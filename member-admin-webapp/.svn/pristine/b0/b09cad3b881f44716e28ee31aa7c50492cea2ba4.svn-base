/**
 * 账目明细管理
 */
Ext.define("Taole.reportSheet.reportSheetManager.view.ReportSheetPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.reportSheetPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.reportSheet.reportSheetManager.store.ReportSheetStore',{
		});	
		var firstCkict=true;
		this.items = [{//-----------------------------------------------表单
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
					fieldLabel: '店名',
					xtype: 'combobox',
					name: 'shopId',
					id:'shopId',
					displayField:'name',
					valueField:'shopId',
			        editable:false,
					store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
						autoLoad: true
					}),
					listeners: { //监听 
						 render : function(combo) {//渲染 
					           combo.getStore().on("load", function(s, r, o) { 
					           combo.setValue(r[0].get('shopId'));//第一个值 
					           var dimens =Ext.getCmp("dimens").getValue();
					           if(dimens && firstCkict){
					        	   firstCkict =false;
					        	   Ext.getCmp("form-query").fireEvent('click');
					           }  
					      }); 
					   } 
					} 	  
				},
				{
					fieldLabel: '日期',
					xtype: 'datefield',
					format: 'Y-m-d',
					name:'statDate',
					value:new Date(),
				},{
					fieldLabel: '汇总方式',
					xtype: 'combobox',
					name: 'dimens',
					id:'dimens',
					displayField:'name',
					valueField:'value',
					editable:false,
					store: getDicStore("d6c252cf17d241a7ac232ebc9493b980",URL_DICTIONARY_MEMBER),	
					listeners: { //监听 
					   render : function(combo) {//渲染 
					           combo.getStore().on("load", function(s, r, o) { 
					           combo.setValue(r[0].get('value'));//第一个值 
					           var shop =Ext.getCmp("shopId").getValue();
					           if(shop && firstCkict){
					        	   firstCkict =false;
					        	   Ext.getCmp("form-query").fireEvent('click');
					           }   
					      }); 
					   } 
					} 
				},
				{
					text: '查询', 
					xtype: 'button', 
					action: 'query', 
					style: 'margin-left:10px;',
					id:'form-query',
					width: 60
				},
//				{
//					text: '重置', 
//					xtype: 'button', 
//					action: 'reset',
//					style: 'margin-left:10px;',
//					width: 60
//					}
				]
			}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columns: [
			    {header: '序号',align: 'center',xtype:'rownumberer', width:35},
			    {header: '日期', dataIndex: 'statDate', align: 'center', width:150},
			    {header: '店面', dataIndex: 'shopName', align: 'center', width:150},
				{header: '营业总额(元)', dataIndex: 'statMoney', align: 'center', width:150},
				{header: '划卡总次数', dataIndex: 'statAmount', align: 'center', width:150},
				{header: '汇总方式', dataIndex: 'objectName', align: 'center', width:150},		
			],
			 viewConfig:{  
	                enableTextSelection:true  
	            }, 
		    tbar : [
		        	{text:'导出excel',action:'export', pressed:true},'-',
		        	{text:'打印',action:'print', pressed:true}
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
		}]		
        this.callParent(arguments);
    }
});