/**
 * 门店管理新增、修改、查看
 */
var myHtmlEditor = Ext.create('Ext.ux.form.plugin.HtmlEditor', {
  enableAll: true,
  imageUploadUrl: '', // 处理图片上传的后台
});
var backStyle ='background-color:#EEEEE0;background-image: none;';
var required ='<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
var timeData=[];
for(var i=0;i<=24;i++){
	var t = i>=10?i:'0'+i;
	if(t==24){
		var time =[{time:t+':00'}];
	}else{
		var time =[{time:t+':00'},{time:t+':30'}]
	}
	timeData=timeData.concat(time)
};
Ext.define("Taole.store.storeManager.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
    modal: true,
    frame: true,
    maximized: true,
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
			autoScroll:true,
			layout:'absolute',
			items:[{
				width:600,
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
						width: 300
					},
					items:[
						{
							fieldLabel: '店面名称',
							xtype: 'textfield',
							name: 'name',
							beforeLabelTextTpl: required,
							allowBlank: false,
							
						},{
							xtype: 'hidden',
							name: 'shopId',
						},{
							fieldLabel: '开业状态',
							xtype: 'combobox',
							name: 'status',
							displayField: 'name',
							valueField: 'value',
						    store: getDicStore("bbbcb9dc0f2b4261aa2ab2b0c0df5675",URL_DICTIONARY_MEMBER),
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
						width: 300
					},
					items:[{
							fieldLabel: '联系人',
							xtype: 'textfield',
							name: 'contactPerson',
						},{
							fieldLabel: '联系电话',
							xtype: 'textfield',
							name: 'contactTel',
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
						width: 300
					},
					items:[
					   	{
							fieldLabel: '省份',
							name:'province',
							xtype:'combobox',
							displayField:'name',
							valueField:'value',
							store: getDicStore('CN'),
						},{
							fieldLabel: '地区',
							xtype: 'combobox',
							name:'city',
							displayField:'name',
							valueField:'value',
							store: getDicStore(),
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
						width: 300
					},
					items:[
						{
							fieldLabel: '详细地址',
							xtype: 'textfield',
							name: 'address',
							width: 510,
							beforeLabelTextTpl: required,
							allowBlank: false,
						},{
							xtype:'button',
							id:'achieve',
		                    width: 80,
		                    style: 'margin-left:5px;',
							action:'achieve',
							text:'获取经纬度'
						}
					]		
				},{
					x:0,
					y:280,
					width:565,
					id:"container",
					xtype: 'hidden',
				
				},{
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
							fieldLabel: '经度',
							name:'lng',
							xtype: 'textfield',
							readOnly:true,
							fieldStyle:backStyle
						},{
							fieldLabel: '纬度',
							name:'lat',
							xtype: 'textfield',
							readOnly:true,
							fieldStyle:backStyle
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
						width: 300
					},
					items:[
					   	{
							fieldLabel: '营业开始时间',
							name:'openTime',
							xtype: 'combobox',
							displayField:'time',
							valueField:'time',
							store:Ext.create('Ext.data.Store',{
								autoLoad:true,
								fields:['time'],
								data:timeData,
							}),
							
						},{
							fieldLabel: '营业结束时间',
							name:'closeTime',
							xtype:'combobox',
							displayField:'time',
							valueField:'time',
							store:Ext.create('Ext.data.Store',{
								autoLoad:true,
								fields:['time'],
								data:timeData,
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
						width: 300
					},
					items:[
					   	{
							fieldLabel: '推荐值',
							name:'sort',
							xtype: 'textfield',
							hidden:true
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
						width: 300
					},
					items:[
						{
							fieldLabel: '备注',
							xtype: 'textarea',
							name: 'description',
							width: 600,
							height:50
						}
					]		
				},{
					//--------------------------------第3行
					layout: 'column',
					frame: true,
					baseCls: 'x-plain',
					style: 'margin-top:5px;',
					defaults:{
						labelAlign: 'right',
						labelWidth: 100,
						width: 600
					},
					items:[
						{
							fieldLabel: '描述',
							xtype:'htmleditor',
							plugins: [myHtmlEditor],
							name:'introduce',
							height:470
						}
					]
				
				}]
			},{
				x:620,
				y:0,
				width:400,
				frame: true,
				baseCls: 'x-plain',
				id:'imageForm',
				layout:'absolute',
				items:[{
	                width: 150,
	                height: 150,
	                name: 'shopLogo',
	                id:'shopLogo',
	                xtype: 'image',
	                fieldLabel: "预览图片",
	                style:{
						'border':'1px solid gray;margin-left:5px;margin-top:5px;'
					}
				},{
					xtype:'button',
					x:15,
					y:160,
			        text : '上传店铺LOGO',
			        width:120,
			        id:'t123',
					cls : Ext.baseCSSPrefix + 'form-file-wrap',
					preventDefault : false,
					style : 'margin-left:3px'	
				},{
					y:190,
	                width: 300,
	                height: 150,
	                name: 'shopDescImage',
	                id:'shopDescImage',
	                xtype: 'image',
	                fieldLabel: "预览图片",
	                style:{
						'border':'1px solid gray;margin-left:5px;margin-top:5px;'
					}
				},{
					xtype:'button',
					x:80,
					y:350,
			        text : '上传店铺详情图片',
			        width:120,
			        id:'t1234',
					cls : Ext.baseCSSPrefix + 'form-file-wrap',
					preventDefault : false,
					style : 'margin-left:3px'	
				},{
					y:380,
	                width: 300,
	                height: 150,
	                name: 'shopListImage',
	                id:'shopListImage',
	                xtype: 'image',
	                fieldLabel: "预览图片",
	                style:{
						'border':'1px solid gray;margin-left:5px;margin-top:5px;'
					}
				},{
					xtype:'button',
					x:80,
					y:550,
			        text : '上传店铺列表图片',
			        width:120,
			        id:'t12345',
					cls : Ext.baseCSSPrefix + 'form-file-wrap',
					preventDefault : false,
					style : 'margin-left:3px'	
				}]
			}],
			
		}];
        this.buttons = [
            {
				text: '确定',
				action: 'confirm'
			},{
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});