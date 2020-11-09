/**
 * 商品管理新增、修改、查看
 */
var required ='<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define("Taole.goods.goodsManager.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 730,
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
				width:550,
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
						width: 270
					},
					items:[
						{
							fieldLabel: '商品编号',
							xtype: 'textfield',
							name: 'goodsCode',
							fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
							readOnly:true
						},{
							fieldLabel: '商品名称',
							xtype: 'textfield',
							name: 'name',
							beforeLabelTextTpl: required,
							allowBlank: false,
						},{
							xtype: 'hidden',
							name: 'goodsId',
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
						width: 270
					},
					items:[
						{
							fieldLabel: '所属类别',
							xtype: 'combobox',
							name: 'catalogId',
							beforeLabelTextTpl: required,
							allowBlank: false,
							displayField: 'name',
							valueField: 'value',
							store: getDicStore("dd85a58e85844fc3b838c6c0566e653b",URL_DICTIONARY_MEMBER),
						},{
							fieldLabel: '线上是否可售',
							xtype: 'combobox',
							name: 'onlineBuy',
							displayField:'name',
							valueField:'value',
							beforeLabelTextTpl: required,
							allowBlank: false,
						    store: Ext.create('Ext.data.Store',{
								autoLoad:true,
								fields:['value','name'],
								data:[{"name":"可售","value":'1'},{"name":"不可售","value":'0'}],
							}),
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
						width: 270
					},
					items:[{
						fieldLabel: '单位',
						xtype: 'combobox',
						name: 'unit',
						beforeLabelTextTpl: required,
						allowBlank: false,
						displayField: 'name',
						valueField: 'value',
						store: getDicStore("45beb4346979480cb954986246035e28",URL_DICTIONARY_MEMBER),
					},{
							fieldLabel: '销售单价（元）',
							xtype: 'numberfield',
							name: 'saleMoney',
							minValue:0,
							beforeLabelTextTpl: required,
							allowBlank: false,
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
						width: 270
					},
					items:[{
						fieldLabel: '原价（元）',
						xtype: 'numberfield',
						name: 'originalCost',
						minValue:0,
						beforeLabelTextTpl: required,
						allowBlank: false,
					},{
							fieldLabel: '限购数量',
							xtype: 'numberfield',
							name: 'maxnumber',
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
						width: 270
					},
					items:[{
						fieldLabel: '限购开始日期',
						name: 'beginDate',
						xtype: 'datefield',
						format: 'Y-m-d',
					},{
							fieldLabel: '限购结束日期',
							xtype: 'datefield',
							format: 'Y-m-d',
							name: 'endDate',
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
						width: 270
					},
					items:[
						{
							fieldLabel: '规格说明',
							xtype: 'textarea',
							name: 'description',
							width: 540,
							height:60
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
						width: 540
					},
					items:[{
						fieldLabel: '选择商品标签',
						xtype: 'checkboxgroup',
						id:'homeTypes',
						layout: 'column',
						hidden:true,
						defaults:{
							width: 150
						},
						items: [
//				            { boxLabel: '甄选好物 ', name: 'homeTypes', id:'homeTypesZXHW', inputValue: 'ZXHW' },	
//							{ boxLabel: '热门推荐 ', name: 'homeTypes',id:'homeTypesRMTJ', inputValue: 'RMTJ' },	          
	       			 ]}
					]
				}]
				},{
					x:555,
					y:0,
					width:190,
					frame: true,
					baseCls: 'x-plain',
					id:'imageForm',
					layout:'absolute',
					items:[{
		                width: 140,
		                height: 90,
		                name: 'image',
		                id:'image',
		                xtype: 'image',
		                fieldLabel: "预览图片",
		                style:{
							'border':'1px solid gray;margin-left:5px;margin-top:5px;'
						}
					},{
						xtype:'button',
						x:20,
						y:100,
				        text : '上传正面图片',
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
				        text : '上传背面图片',
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