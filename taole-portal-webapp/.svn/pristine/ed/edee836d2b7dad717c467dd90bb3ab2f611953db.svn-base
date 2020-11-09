/**
 * 新增、修改、查看文档信息
 */
Ext.define("Taole.user.organization.view.ChooseOrganizationWindow", {
	extend: 'Ext.Window',
	alias : 'widget.chooseOrganizationWindow',
	layout: 'border',
	height:500,
	width:320,
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
	initComponent: function() {
		var store = Ext.create("Taole.user.organization.store.ChooseOrganizationStore",{
			autoLoad: true
		});
		this.items = [
		{
			xtype: 'treepanel',
			region: 'center',
			store: store,
			rootVisible: true,
			root:{
				text: '组织结构'
			}
		}];
        this.buttons = [
            {
				text: '确定',
				action: 'confirm'
			} ,
            {
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});