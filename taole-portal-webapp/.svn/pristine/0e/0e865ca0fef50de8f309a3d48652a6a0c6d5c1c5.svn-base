/**
 * 新增、修改、查看
 */
Ext.define("Taole.strategy.QRCodeManager.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 1000,
	height: 560,
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
			items:[
			{
				//--------------------------------第1行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				id:'baseNews',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 240
				},
				items:[
					{
						xtype:'hidden',
						name: 'id'
					},
					{
						fieldLabel: '推荐码',
						xtype: 'textfield',
						id:'codetype',
						readOnly:true,
						fieldStyle:"background-color:#EEEEE0;background-image: none;",
					},
					{
						fieldLabel: '营销主体',
						xtype: 'textfield',
						name:'title',
						id:'mainTitle'
					},{
						fieldLabel: '营销渠道',
						xtype: 'textfield',
						name: 'type',
						id:'tpye'
					},{

						fieldLabel: '营销人员',
						xtype: 'textfield',
						name: 'people',
						id:'people'
					
					}
					
				]
			},{//--------------------------------第2行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 260
				},
				items:[
				   {
					    xtype: 'textarea',
					    name:'description',
						fieldLabel:'说明',
						width:960,
						height:50
					}
				]	
			
			},{//--------------------------------第2行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 260
				},
				items:[
				      {	labelWidth: 150,
				    	 width:220,
					    xtype: 'textfield',
					    name:'description',
						fieldLabel:'微信带参数二维码有效期',
						
					},{
					    xtype: 'label',
					    width:30,
					    style: 'margin-top:3px;margin-left:5px;',
						text:'天',
					},{

						fieldLabel: '关注微信服务号的推送消息',
						xtype: 'combobox',
						name: 'wxNews',
						id:'wxNews',
						width:350,
						labelWidth: 180,
						store: Ext.create('Taole.strategy.QRCodeManager.store.WXNewsStore'),
						displayField: 'name',
						valueField: 'id',
					
					}
				]	
			
			},{//--------------------------------第2行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 260
				},
				items:[
				   {
					    xtype: 'textfield',
					    name:'description',
						fieldLabel:'消息标题',
						width:960,
						id:'newsTitle'
					}
				]	
			
			},{//--------------------------------第2行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 260
				},
				items:[
				   {
					    xtype: 'textarea',
					    name:'description',
						fieldLabel:'消息内容',
						width:960,
						height:50,
						id:'newsCN',
					}
				]	
			
			},{//--------------------------------第2行
				layout: 'absolute',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				height:125,
				id:'newsImg',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 240
				},
				items:[{
					x:0,
                	y:10,
                	width:90,
					xtype: 'label',
					style:'text-align:right',
					text:'图片消息：',
				},{
                    x: 100,
                    y: 2,
                    width: 150,
                    height: 120,
                    name: 'filename',
                    value: '预览图片',
                    xtype: 'image',
                    fieldLabel: "预览图片",
                    style:'border:1px solid #dddddd',
                    src: '',
                    id:'newsLogo'
                },{
                	x:260,
                	y:50,
                	 text:'请上传图片',
 					xtype: 'button',
 					width:100,
 					action: 'newsBTN',
 					cls : Ext.baseCSSPrefix + 'form-file-wrap',
 					preventDefault : false,
 					id:'updownImg'
				},{
					x:370,
                	y:50,
                	labelWidth: 70,
                	width:588,
					fieldLabel: '查看更多',
					xtype: 'textfield',
					name:'moreNews',
				
				}
				]	
			},
			{//--------------------------------第2行
				layout: 'absolute',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;border:1px solid #dddddd;',
				height:160,
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 240
				},
				items:[{
                    x: 100,
                    y: 2,
                    width: 150,
                    height: 120,
                    name: 'filename',
                    value: '预览图片',
                    xtype: 'image',
                    fieldLabel: "预览图片",
                    style:'border:1px solid #dddddd',
                    src: 'http://download.yishengjia1.com/image/product_zlygjy_nv18zlfatj.jpg',
                },{
                	x:30,
                	y:130,
					 fieldLabel:'二维码合成模板',
					 xtype: 'combobox',
					 name: 'code',
					 store: getDicStore("MARCKETING_SUBJECT_TYPE_CODE"),
					 displayField: 'name',
					 valueField: 'value',
				},{
					 x: 330,
	                 y: 2,
	                 width: 150,
	                 height: 120,
	                 name: 'filename',
	                 value: '',
	                 xtype: 'image',
	                 fieldLabel: "预览图片",
	                 style:'border:1px solid #dddddd',
	                 src: '',
				},{
					x: 350,
	                y: 130,
	                text:'上传二维码背景图',
					xtype: 'button',
					width:120,
					id:'updownBg',
					action: 'updownBg',
					preventDefault : false,
					cls : Ext.baseCSSPrefix + 'form-file-wrap',
				},{
					 x: 560,
	                 y: 2,
	                 width: 150,
	                 height: 120,
	                 name: 'filename',
	                 value: '',
	                 xtype: 'image',
	                 fieldLabel: "预览图片",
	                 style:'border:1px solid #dddddd',
	                 src: '',
				},{ 
					x: 580,
	                y: 130,
	                text:'上传原始二维码',
					width: 120,
					xtype: 'button',
					cls : Ext.baseCSSPrefix + 'form-file-wrap',
					preventDefault : false,
					id:'updownQR',
					action: 'updownQR',
					
				},{
					 x: 790,
	                 y: 2,
	                 width: 150,
	                 height: 120,
	                 name: 'filename',
	                 value: '',
	                 xtype: 'image',
	                 fieldLabel: "预览图片",
	                 style:'border:1px solid #dddddd',
	                 src: '',
	                 hidden:true,
	                 id:'updownQRCpImg'
				},{ 
					x: 810,
	                y: 130,
	                text:'上传合成二维码',
					width: 120,
					xtype: 'button',
					cls : Ext.baseCSSPrefix + 'form-file-wrap',
					preventDefault : false,
					hidden:true,
					id:'updownQRCp',
					action: 'updownQRCp',
					
				}
				]	
			}
			]
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