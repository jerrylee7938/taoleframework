/**
 * 卡管理新增、修改、查看
 */
var required ='<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define("Taole.card.cardManager.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 700,
	height: 320,
//	maximized: true,
	modal: true,
	/**
	 * @type Function 
	 * 
	 */
	afterChooseFn: Ext.emptyFn,
	/**
	 * 调用afterChooseFn时的作用域，默认window
	 * @type 
	 */
	scope: window,
	appAdPositionId: null,
	isView: null,
	isRetrieve: null,
	initComponent: function() {
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'center',
			frame: true,
			defaults:{
				xtype: 'panel',
				baseCls:'x-plain',
				frame: true
			},
			layout:'absolute',
			items:[{
				width:510,
				frame: true,
				baseCls: 'x-plain',
				height:'auto',
				defaults:{
					xtype: 'panel',
					baseCls:'x-plain',
					frame: true
				},
				items:[{
					//--------------------------------第1行
					layout: 'column',
					frame: true,
					baseCls: 'x-plain',
					style: 'margin-top:5px;',
					defaults:{
						labelAlign: 'right',
						labelWidth: 100,
						width: 250
					},
					items:[
					       {
					    	   xtype:'hidden',
					    	   name:'cardId'
					       },
						{
							fieldLabel: '编码',
							xtype: 'textfield',
							name: 'code',
							fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
							readOnly:true
						},{
							fieldLabel: '名称',
							xtype: 'textfield',
							name: 'cardName',
							fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
							readOnly:true
						}
					]
				},{
					//--------------------------------第2行
					layout: 'column',
					frame: true,
					baseCls: 'x-plain',
					style: 'margin-top:5px;',
					defaults:{
						labelAlign: 'right',
						labelWidth: 100,
						width: 250
					},
					items:[{
							fieldLabel: '卡类型',
							xtype: 'combobox',
							name: 'cardType',
							displayField:'name',
							valueField:'value',
						    store: getDicStore("CARDTYPE_CODE",URL_DICTIONARY_MEMBER),
						    beforeLabelTextTpl: required,
							allowBlank: false,
						},{
							fieldLabel: '有效期类型',
							xtype: 'combobox',
							name: 'chargeType',
							displayField:'name',
							valueField:'value',
						    store: getDicStore("21a0578289544ae5a24d7bfad7130d2d",URL_DICTIONARY_MEMBER),
						
						}
					]
				},
				{
					//--------------------------------第2行
					layout: 'column',
					frame: true,
					baseCls: 'x-plain',
					style: 'margin-top:5px;',
					defaults:{
						labelAlign: 'right',
						labelWidth: 100,
						width: 250
					},
					items:[
						{
							fieldLabel: '有效次数',
							xtype: 'numberfield',
							name: 'totalNum',
							minValue:0,
						},{
							fieldLabel: '有效天数',
							xtype: 'numberfield',
							name: 'periodOfValidity',
							minValue:0,
						}
					]
				},{
					//--------------------------------第2行
					layout: 'column',
					frame: true,
					baseCls: 'x-plain',
					style: 'margin-top:5px;',
					defaults:{
						labelAlign: 'right',
						labelWidth: 100,
						width: 250
					},
					items:[
						{
							fieldLabel: '售价（元）',
							xtype: 'textfield',
							name: 'money',
							beforeLabelTextTpl: required,
							allowBlank: false,
						},{
							fieldLabel: '状态',
							xtype: 'combobox',
							name: 'status',
							displayField: 'name',
							valueField: 'value',
						    store: getDicStore("a4146cc08ae54c97983708774a303884",URL_DICTIONARY_MEMBER),

						}
					]		
				},
				{
					//--------------------------------第2行
					layout: 'column',
					frame: true,
					baseCls: 'x-plain',
					style: 'margin-top:5px;',
					defaults:{
						labelAlign: 'right',
						labelWidth: 100,
						width: 250
					},
					items:[
						{
							fieldLabel: '上架日期',
							xtype:'textfield',
							name: 'beginTime',
							fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
							readOnly:true
							
						},{
							fieldLabel: '下架日期',
							xtype:'textfield',
							name: 'endTime',
							fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
							readOnly:true
						}
					]
				},{
					//--------------------------------第2行
					layout: 'column',
					frame: true,
					baseCls: 'x-plain',
					style: 'margin-top:5px;',
					defaults:{
						labelAlign: 'right',
						labelWidth: 100,
						width: 250
					},
					items:[{
							fieldLabel: '线上是否可售',
							xtype: 'combobox',
							name: 'onlineShow',
							displayField:'name',
							valueField:'value',
						    store: Ext.create('Ext.data.Store',{
								autoLoad:true,
								fields:['value','name'],
								data:[{"name":"可以","value":'1'},{"name":"不可以","value":'0'}],
							}),
						},{
							fieldLabel: '线上是否可充值',
							xtype: 'combobox',
							name: 'onlineRecharge',
							displayField:'name',
							valueField:'value',
						    store:Ext.create('Ext.data.Store',{
								autoLoad:true,
								fields:['value','name'],
								data:[{"name":"可以","value":'1'},{"name":"不可以","value":'0'}],
							})
							}
					]
				}
		/*		,{
					//--------------------------------第2行
					layout: 'column',
					frame: true,
					baseCls: 'x-plain',
					style: 'margin-top:5px;',
					defaults:{
						labelAlign: 'right',
						labelWidth: 100,
						width: 300
					},
					items:[
						{
							fieldLabel: '经办人',
							xtype: 'textfield',
							name: 'operator',
							value:dataUser.name,
							fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
							readOnly:true
						},{
							fieldLabel: '审核人',
							xtype: 'textfield',
							name: 'name',
							value:dataUser.name,
							fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
							readOnly:true
						}
					]		
				} */
				]
			},{
				x:515,
				y:0,
				width:250,
				frame: true,
				baseCls: 'x-plain',
				id:'imageForm',
				layout:'absolute',
				items:[{
	                width: 140,
	                height: 90,
	                name: 'cardImage',
	                id:'cardImage',
	                xtype: 'image',
	                fieldLabel: "预览图片",
	                style:{
						'border':'1px solid gray;margin-left:5px;margin-top:5px;'
					}
				},{
					xtype:'button',
					x:20,
					y:100,
			        text : '上传卡正面图片',
			        width:100,
			        id:'t123',
					cls : Ext.baseCSSPrefix + 'form-file-wrap',
					preventDefault : false,
					style : 'margin-left:3px'	
				},{	
					y:125,
	                width: 140,
	                height: 90,
	                name: 'imageBack',
	                id:'imageBack',
	                xtype: 'image',
	                fieldLabel: "预览图片",
	                style:{
						'border':'1px solid gray;margin-left:5px;margin-top:5px;'
					}
				},{
					xtype:'button',
					x:20,
					y:225,
			        text : '上传卡背面图片',
			        width:100,
			        id:'bt123',
					cls : Ext.baseCSSPrefix + 'form-file-wrap',
					preventDefault : false,
					style : 'margin-left:3px'	
				}]
			}]
		}];
        this.buttons = [
            {
				text: '确定',
				action: 'confirm'
			}
            ,
            {
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});