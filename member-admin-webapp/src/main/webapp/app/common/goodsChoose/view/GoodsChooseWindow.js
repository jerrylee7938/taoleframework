/**
 * 选择商品窗口
 */
Ext.define("Taole.common.goodsChoose.view.GoodsChooseWindow", {
	extend: 'Ext.Window',
	alias : 'widget.goodsChooseWindow',
	layout: 'border',
	title: '请选择商品',
	width: 830,
	height: 500,
	modal: true,
	/**
	 * 是否允许选择多个用户
	 */
	allowMultiSelect: false,
	/**
	 * 用户名称
	 */
	roleName: null,
	/**
	 * 选择用户后调用的函数
	 * @type Function 
	 * 
	 */
	afterChooseFn: Ext.emptyFn,
	/**
	 * 调用afterChooseFn时的作用域，默认window
	 * @type 
	 */
	scope: window,
	initComponent: function() {
		var chooseGoodsStore = Ext.create('Taole.common.goodsChoose.store.GoodsChooseStore');
		this.items = [{
			xtype: 'form',
			region: 'north',
			height: 30,
			style: 'margin-top:5px;',
			frame: true,
			defaults:{
				labelAlign: 'right',
				labelWidth: 80
			},
			baseCls:'x-plain',
			layout: 'column',
			items:[{
				fieldLabel: '商品类型',
				xtype: 'combobox',
				name: 'goodsTypeId',
				displayField: 'name',
				valueField: 'value',
				store: getDicStore("dd85a58e85844fc3b838c6c0566e653b",URL_DICTIONARY_MEMBER),
			},
			{fieldLabel: '商品名称', xtype: 'textfield', name: 'name', width: 250},
			{text: '查询', xtype: 'button', action: 'query', width: 80, style:'margin-left:20px;'},
			{text: '重置', xtype: 'button', action: 'reset', width: 80, style:'margin-left:20px;'}
			]
		},{
			xtype: 'grid',
			region:'center',
			style: 'margin-top:3px;',
			store: chooseGoodsStore,
			columns: [
			          {header: '序号',align: 'center',xtype:'rownumberer', width:35},
			          {header: '商品编号', dataIndex: 'goodsCode',align: 'center', width:150},
			          {header: '商品类型', dataIndex: 'goodsType',align: 'center',width:100},
			          {header: '商品名称', dataIndex: 'name',align: 'center',width:150},
			          {header: '商品规格', dataIndex: 'description', align: 'center', width:100},
			          {header: '单位', dataIndex: 'unitName', align: 'center', width:80},
			          {header: '单价（元）', dataIndex: 'saleMoney', align: 'center', width:80},
			          {header: '库存', dataIndex: 'balance', align: 'center', width:80},
			   ],
			   viewConfig:{  
	                forceFit:true,
	                getRowClass:function(record,index){
	                	var balance = record.get('balance');
	                	if(balance ==0){
	                		return 'getRowClassLock x-grid-row-blue style="disabled:true;"';
	                	}
	                }
	            }, 
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: chooseGoodsStore,
				displayInfo: true
	        },
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel',
		    }
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