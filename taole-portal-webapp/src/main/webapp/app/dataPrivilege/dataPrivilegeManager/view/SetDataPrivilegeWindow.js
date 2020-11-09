/**
 * 新增、修改、查看APP广告位置信息
 */
Ext.define("Taole.dataPrivilege.dataPrivilegeManager.view.SetDataPrivilegeWindow", {
	extend: 'Ext.Window',
	alias : 'widget.setDataPrivilegeWindow',
	layout: 'fit',
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
	title:'数据权限设置',
	initComponent: function() {
		this.items = [{
			xtype:'form',
			autoScroll:true,
			frame:true,
			border:false,
			autoScroll:true,
			style:{
				'border':'0px'
			},
			items:[]
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