/**
 * 商品入库
 */
Ext.define("Taole.goods.outOfStorageManager.view.GoodsGoInWindow", {
	extend: 'Ext.Window',
	alias : 'widget.goodsGoInWindow',
	layout: 'border',
	width: 850,
	height: 600,
	modal: true,
	maximizable: true,          //是否可以最大化
    minimizable: true,          //是否可以最小化
    closable: true,            //是否可以关闭
	maximized:true,
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
		var store = Ext.create('Taole.goods.outOfStorageManager.store.GoodsGoInStore',{
		});	
		this.items = [
		      		{//-----------------------------------------------表单
		    			xtype: 'form',
		    			region: 'north',
		    			frame: true,
		    			height: 85,
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
		    					width: 250
		    				},
		    				items:[{
		    					fieldLabel: '入库单号',
		    					xtype: 'textfield',
		    					name:'shopBillNo',
		    					fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
								readOnly:true
		    				},{
		    					fieldLabel: '操作人',
		    					xtype: 'textfield',
		    					name:'operator',
		    					value:dataUser.name,
		    					fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
								readOnly:true
		    				},{
		    					fieldLabel: '入库店面',
		    					xtype: 'combobox',
		    					name: 'shopId',
		    					displayField: 'name',
		    					valueField: 'shopId',
		    					store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
		    						autoLoad: true
		    					}),
		    				},
		    				
		    				]
		    			},{
		    				//--------------------------------第1行
		    				layout: 'column',
		    				frame: true,
		    				baseCls: 'x-plain',
		    				style: 'margin-top:5px;',
		    				defaults:{
		    					labelAlign: 'right',
		    					labelWidth: 80,
		    					width: 250
		    				},
		    				items:[{
		    					fieldLabel: '入库备注',
		    					xtype: 'textarea',
		    					name:'description',
		    					width: 750,
		    					height:40
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
		    				{header: '单位', dataIndex: 'unitName', align: 'center', width:150},
		    				{header: '库存数量', dataIndex: 'balance',align: 'center', width:150},
		    				{header: '入库数量', dataIndex: 'amount',editor:{xtype:'numberfield',minValue:1,}, align: 'center', width:150},
		    			],
		    		    tbar : [
		    		    	{text:'选择入库商品',action:'add', pressed:true},'-',
		    		    	{text:'移除',action:'remove', pressed:true}
		    		    ],
		    		    columnLines: true,
		    		    selModel:{
		    		    //	selType : 'checkboxmodel'
		    		    }
		    		}
		    		];
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