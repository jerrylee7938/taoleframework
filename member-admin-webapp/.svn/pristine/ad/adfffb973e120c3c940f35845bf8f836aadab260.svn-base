/**
 * 新增、修改、查看会员管理
 */
var required ='<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define("Taole.user.userInfoManager.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 1100,
	height: 420,
	maximized: true,
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
		var store = Ext.create('Taole.user.userInfoManager.store.CardStore',{
			autoLoad: true
		});	
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'center',
			frame: true,
			baseCls:'x-plain',
			layout:'fit',
			modal:true,
			defaults:{
			//	xtype: 'panel',
			//	xtype:'textfield',
				frame: true,
				labelAlign: 'right',
//				labelWidth: 100,
//				width: 350
			},
			items:[{
				 xtype:'tabpanel',
					layout:'fit',
					items:[{
						title:'会员信息',
						xtype:'panel',
						region:'north',
						id:'basic_form',
						frame:true,
						style:{
							'margin-bottom':'5px',
							'border-radius':'0px'
						},
						cls:'x-plain',
						items:[{
							layout: 'column',
							frame:true,
							cls:'x-plain',
							style:{'border':'0px'},
							items:[{
								columnWidth: 0.7, 
								frame:true,
								style:{'border':'0px'},
								layout:'column',
								defaults:{
										xtype:'textfield',
										frame: true,
										labelAlign: 'right',
										labelWidth: 100,
										style:{'margin-bottom':'5px'},
										width: 350
									},
								items:[{
									name:'employeeId',
									fieldLabel:'姓名',
									allowBlank: false,
									afterLabelTextTpl: required,
								},{
									name:'mobile',
									fieldLabel:'性别',
									xtype:'combobox',
									displayField:'name',
									valueField:'value',
									editable: false,
									store: Ext.create('Ext.data.Store',{
										autoLoad:true,
										fields:['value','name'],
										data:[{"name":"男","value":'1'},{"name":"女","value":'2'},{"name":"未知","value":'0'}],
									})
								},{
									name:'mobile',
									fieldLabel:'出生日期',
									xtype:'datetimefield',
									format:'Y-m-d',	
								},{
									name:'mobile',
									fieldLabel:'手机号码',
									allowBlank: false,
									afterLabelTextTpl: required,
								},{
									name:'mobile',
									fieldLabel:'会员级别',
									xtype:'combobox',
									displayField:'name',
									valueField:'value',
									editable: false,
									store: Ext.create('Ext.data.Store',{
										autoLoad:true,
										fields:['value','name'],
										data:[{"name":"VIP会员","value":'1'},{"name":"普通会员","value":'2'}],
									})
								},{
									xtype: 'textarea',
								    name:'description',
									fieldLabel:'备注',
									width:700,
									height:80
								},{
									xtype:'hidden',
									name:'avatar'
								}]
							},{
								columnWidth: 0.25, 
								frame:true,
								style:{'border':'0px',},
								layout:'absolute',
								items:[{
//									x: 10,
//					                y: 10,
					                width: 180,
					                height: 200,
					                name: 'avatar',
					                id:'avatar',
					                xtype: 'image',
					                fieldLabel: "预览图片",
					                style:{
										'border':'1px solid gray'
									}
								},{
									xtype:'button',
									x:0,
									y:210,
							        text : '选择图片',
							        width:80,
							        id:'t123',
									cls : Ext.baseCSSPrefix + 'form-file-wrap',
									preventDefault : false,
									style : 'margin-left:3px'	
								},{
									xtype:'button',
									x:90,
									y:210,
							        text : '拍照',
							        width:80,
							        id:'t1234',
									cls : Ext.baseCSSPrefix + 'form-file-wrap',
									preventDefault : false,
									style : 'margin-left:3px'	
								
								}]
							}]
						}]
					},{
						xtype: 'grid',
						store: store,
						id:'acc_grid',
						title:'会员卡信息',
						border:false,
						columnLines:true,
						tbar:[
							{text:'新增',action:'add', pressed:true},
							{text:'删除',action:'delete', pressed:true}
						
						],
						selModel:{
							selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
						},
						 plugins:[{
						    	pluginId: 'rowediting',
						    	ptype: 'rowediting',
						    	clicksToEdit: 2,
						    	autoCancel: false,
						        saveBtnText: '保存',
						        cancelBtnText: '取消'
						    }],
						columns:[
							{text:'单号',width:80,dataIndex:'id',editor: {
			                  
			                }},
			                {text:'店名',width:80,dataIndex:'name',editor: {
			                	xtype: 'textfield',
			                }},
			                {text:'会员卡号',width:80,dataIndex:'name1',editor: {
			                	xtype: 'textfield',
			                }},
							{text:'卡类型',width:60,dataIndex:'name2',align:'center',editor:{
								xtype: 'textfield',
					        	}
			                },
							{text:'卡名称',width:80,dataIndex:'name3',align:'center',editor:{xtype: 'textfield',}},
							{text:'购卡金额',width:80,dataIndex:'name4',align:'center',editor:{xtype: 'textfield',}},
							{text:'付款方式',width:80,dataIndex:'name5',align:'center',editor:{xtype: 'textfield',}},
							{text:'次数',width:60,dataIndex:'name6',align:'center',editor:{xtype: 'textfield',}},
							{text:'有效期',width:100,dataIndex:'name7',align:'center',editor:{xtype:'datefield',format:'Y-m-d'},renderer:function(v){
								if(typeof v ==="object"){
									return Ext.Date.format(v,'Y-m-d');
								}else if(typeof v ==="string"){
									return v.substr(0,10);
								}
							}},
							{text:'办卡人',width:80,dataIndex:'name8',align:'center',editor:{xtype: 'textfield',}},
							{text:'办卡日期',width:100,dataIndex:'name9',align:'center',editor:{xtype:'datefield',format:'Y-m-d'},renderer:function(v){
								if(typeof v ==="object"){
									return Ext.Date.format(v,'Y-m-d');
								}else if(typeof v ==="string"){
									return v.substr(0,10);
								}
							}},
							{text:'开卡人',width:80,dataIndex:'name10',align:'center',editor:{xtype: 'textfield',}},
							{text:'开卡日期',width:100,dataIndex:'name11',align:'center',editor:{xtype:'datefield',format:'Y-m-d'},renderer:function(v){
								if(typeof v ==="object"){
									return Ext.Date.format(v,'Y-m-d');
								}else if(typeof v ==="string"){
									return v.substr(0,10);
								}
							}},
							
						]
			        }]
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