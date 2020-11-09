/**
 * 消费管理
 */
var required ='<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define("Taole.user.consumeManager.view.ConsumePanel", {
	extend: 'Ext.Panel',
    alias : 'widget.consumePanel',  
    frame: true,
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.user.consumeManager.store.ConsumeStore',{
		});		
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			title:'会员及会员卡信息',
			height:'auto',
			defaults:{
				xtype: 'panel',
				baseCls:'x-plain',
				frame: true
			},
			layout:'absolute',
			items:[
					{
						width:750,
						frame: true,
						baseCls: 'x-plain',
						height:'auto',
						defaults:{
							xtype: 'panel',
							baseCls:'x-plain',
							frame: true
						},
						items:[{//--------------------------------第1行
							layout: 'column',
							frame: true,
							baseCls: 'x-plain',
							defaults:{
								labelAlign: 'right',
								labelWidth: 80,
								style: 'margin-top:5px;',
								width: 250
							},
							items:[{
								fieldLabel: '单号',
								xtype: 'textfield',
								name:'userBillNo',
								fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
								readOnly:true
							},{
								fieldLabel: '经办人',
								xtype: 'textfield',
								name:'operator',
//								value:getUserName(),
								fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
								readOnly:true
							},{
								fieldLabel: '店面',
								xtype: 'combobox',
								name: 'shopId',
								displayField: 'name',
								valueField: 'shopId',
								store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
									autoLoad: true
								}),
								beforeLabelTextTpl: required,
								allowBlank: false,
								
							},{
								fieldLabel: '消费者类型',
								xtype: 'combobox',
								name: 'userType',
								displayField: 'name',
								valueField: 'value',
								value:'HY',
							    store: getDicStore("CUSTOMERTYPE_CODE",URL_DICTIONARY_MEMBER),
							},{
								fieldLabel: '支付方式',
								xtype: 'combobox',
								name: 'payType',
								displayField: 'name',
								valueField: 'value',
								store:getDicStore("PAYMODE_CODE",URL_DICTIONARY_MEMBER),
								beforeLabelTextTpl: required,
								allowBlank: false,
							},{
								fieldLabel: '手机号',
								xtype: 'textfield',
								name:'telphone',
								id:'telphone',
								width:190,
							},{
								text: '查询', 
								xtype: 'button', 
								action: 'readerPhone', 
								style: 'margin-left:10px;margin-top:5px;',
								width: 50
							}
							]
						},{
							layout:'column',
							frame: true,
							baseCls: 'x-plain',
							xtype: 'form',
							defaults:{
								xtype: 'panel',
								baseCls:'x-plain',
								frame: true
							},
							id:'menberInfo',
							items:[
								{
									width:750,
									layout: 'column',
									frame: true,
									baseCls: 'x-plain',
									defaults:{
										labelAlign: 'right',
										labelWidth: 80,
										style: 'margin-top:5px;',
										width: 250
									},
									items:[{
										xtype: 'hidden',
										name:'userCardId',
									},{
										xtype: 'hidden',
										name:'userId',
									},{
										fieldLabel: '会员卡号',
										xtype: 'textfield',
										name:'userCardNo',
										enableKeyEvents: true,
										width: 430
									},{
										text: '读卡', 
										xtype: 'button', 
										action: 'reader', 
										style: 'margin-left:10px;margin-top:5px;',
										width: 60
									},{
										fieldLabel: '姓名',
										xtype: 'textfield',
										name:'userName',
										fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
										readOnly:true
									},
									{
										fieldLabel: '卡类型',
										xtype: 'textfield',
										name:'cardTypeName',
										fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
										readOnly:true
									},{
										fieldLabel: '卡名称',
										xtype: 'textfield',
										name:'cardName',
										fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
										readOnly:true
									},{
										fieldLabel: '购卡金额',
										xtype: 'textfield',
										name:'money',
										fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
										readOnly:true
									}
									,{
										fieldLabel: '剩余天数',
										xtype: 'textfield',
										name:'day',
										fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
										readOnly:true
									},{
										fieldLabel: '剩余次数',
										xtype: 'textfield',
										name:'blanceNum',
										fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
										readOnly:true
									},{
										fieldLabel: '已消费次数',
										xtype: 'textfield',
										name:'useTime',
										fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
										readOnly:true
									},
									{
										fieldLabel: '截止日期',
										xtype: 'textfield',
										name:'deadline',
										fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
										readOnly:true
									},
									{
										fieldLabel: '划卡',
										xtype: 'numberfield',
										name:'swipeAmount',
										minValue:1,
										width: 220
									},{
										xtype:'label',
										text:"次",
										width: 20,
										style: 'margin-left:5px;margin-top:6px;',
									}
									]
									
								}      
							]
						}
					]
						
					},{
						x:780,
						width:300,
						layout: 'column',
						frame: true,
						baseCls: 'x-plain',
						id:'imageForm',
						defaults:{
							labelAlign: 'right',
							labelWidth: 80,
							style: 'margin-top:5px;',
							width: 250
						},
						items:[{
			                width: 120,
			                height: 120,
			                name: 'avatar',
			                id:'avatar',
			                xtype: 'image',
			                fieldLabel: "预览图片",
			                style:{
								'border':'1px solid gray;margin-left:5px;margin-top:5px;'
							}
						}]
						}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columnLines:true,
			plugins:[{
		    	pluginId: 'cellediting',
		    	ptype: 'cellediting',
		    	clicksToEdit: 2,
		    	autoCancel: false,
		        saveBtnText: '保存',
		        cancelBtnText: '取消'
		    }],
			columns: [
				{header: '商品编号', dataIndex: 'goodsCode',align: 'center', width:150},
				{header: '商品类型', dataIndex: 'goodsType',align: 'center',width:150},
				{header: '商品名称', dataIndex: 'name',align: 'center',width:150},
				{header: '商品规格', dataIndex: 'description', align: 'center', width:250},
				{header: '单价(元)', dataIndex: 'saleMoney', align: 'center', width:80},
				{header: '单位', dataIndex: 'unitName', align: 'center', width:80},
				{header: '库存', dataIndex: 'balance', align: 'center', width:80},
				{header: '销售数量', dataIndex: 'amount',editor:{xtype:'numberfield',minValue:1}, align: 'center', width:80,},
				{header: '小计（元）', dataIndex: 'consumeMoney', align: 'center', width:80,renderer: function(val, meta, record){
					record.data.consumeMoney = record.data.saleMoney*record.data.amount;
					return record.data.saleMoney*record.data.amount;
				}},
			],
		    tbar : [
		    	{text:'选择商品',action:'add', pressed:true},'-',
		    	{text:'移除',action:'remove', pressed:true}
		    ],
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
		},
//		{//-----------------------------------------------表单
//			xtype: 'panel',
//			region: 'south',
//			frame: true,
//			height:40,
//			border:false,
//			defaults:{
//				xtype: 'panel',
//				baseCls:'x-plain',
//				frame: true
//			},
//			items:[
//		   	{//--------------------------------第1行
//				layout: 'column',
//				frame: true,
//				baseCls: 'x-plain',
//				defaults:{
//					labelAlign: 'right',
//					labelWidth: 80,
//					style: 'margin-top:5px;',
//					width: 250
//				},
//				items:[{
//					fieldLabel: '总价',
//					xtype: 'textfield',
//					id:'priceAll',
//					name:'priceAll',
//					fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
//					readOnly:true
//				},{
//					fieldLabel: '支付方式',
//					xtype: 'combobox',
//					name: 'appCode',
//					displayField: 'name',
//					valueField: 'value',
//					store: Ext.create("Ext.data.Store",{
//						autoLoad:true,
//						fields:['value','name'],
//						data:[{"name":"微信","value":1},{"name":"支付宝","value":2}],
//					}),
//				
//				}]
//			}]
//		
//			
//		}	
		]	
		  this.buttons = [
		                  {
		      				text: '确认',
		      				action: 'preview',
		      				width:100,
		      				style:"background-color:#219DED;background-image:none;",
		      			}];
        this.callParent(arguments);
    },
    buttonAlign: 'center'
});