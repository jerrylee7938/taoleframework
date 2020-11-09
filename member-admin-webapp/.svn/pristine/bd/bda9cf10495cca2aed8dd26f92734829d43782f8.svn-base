/**
 * 选择门店窗口
 */
Ext.define("Taole.common.storeChoose.view.StoreChooseWindow", {
	extend: 'Ext.Window',
	alias : 'widget.storeChooseWindow',
	layout: 'border',
	title: '请选择门店',
	width: 750,
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
	 * 参数：
	 * @param Object
	 * {
	 * 	accountId: 用户账户id,
	 * 	name: 用户名称(如果用户名称为空则返回用户手机号)
	 * }
	 */
	afterChooseFn: Ext.emptyFn,
	/**
	 * 调用afterChooseFn时的作用域，默认window
	 * @type 
	 */
	scope: window,
	initComponent: function() {
		var chooseGoodsStore = Ext.create('Taole.common.storeChoose.store.StoreChooseStore');
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
							fieldLabel: '店面名称',
							xtype: 'textfield',
							name:'name',
						},{
							fieldLabel: '状态',
							xtype: 'combobox',
							name: 'status',
							displayField: 'name',
							valueField: 'value',
						    store: getDicStore("bbbcb9dc0f2b4261aa2ab2b0c0df5675",URL_DICTIONARY_MEMBER),
						},
						{
							text: '查询', 
							xtype: 'button', 
							action: 'query', 
							style: 'margin-left:10px;',
							width: 80
						},
						{
							text: '重置', 
							xtype: 'button', 
							action: 'reset',
							style: 'margin-left:10px;',
							width: 80
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
							width: 250
						},
						items:[{
							fieldLabel:'所属省份',
							name:'province',
							xtype:'combobox',
							displayField:'name',
							valueField:'value',
							store: getDicStore('CN'),
						},{
							fieldLabel: '所属地区',
							xtype: 'combobox',
							name:'city',
							id:'city',
							displayField:'name',
							valueField:'value',
							store: getDicStore(),
						
						}
						]
					}]
		},{
			xtype: 'grid',
			region:'center',
			style: 'margin-top:3px;',
			store: chooseGoodsStore,
			columns: [
					    {header: '序号',align: 'center',xtype:'rownumberer', width:35},
						{header: '店面名称', dataIndex: 'name',align: 'center', width:150},
						{header: '所属省份', dataIndex: 'provinceTitle',align: 'center',width:150},
						{header: '所属地区', dataIndex: 'cityTitle',align: 'center',width:150},
						{header: '联系人电话', dataIndex: 'contactTel', align: 'center', width:100},
						{header: '联系人', dataIndex: 'contactPerson', align: 'center', width:100},
						{header: '详细地址', dataIndex: 'address', align: 'center', width:200},
						{header: '状态', dataIndex: 'shopStatusName', align: 'center', width:100}
					],
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: chooseGoodsStore,
				displayInfo: true
	        },
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
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