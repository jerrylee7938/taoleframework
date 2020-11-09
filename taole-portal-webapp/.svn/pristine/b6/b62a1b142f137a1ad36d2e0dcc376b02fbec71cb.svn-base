/**
 * 新增、修改、查看文档信息
 */
Ext.define("Taole.dataPrivilege.dataPrivilegeManager.view.ConfigValidateWindow", {
	extend: 'Ext.Window',
	alias : 'widget.configValidateWindow',
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
	isDict: null,
	isOrganization: null,
	initComponent: function() {
		this.items = [
		];
        this.buttons = [
            {
				text: '确认',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});