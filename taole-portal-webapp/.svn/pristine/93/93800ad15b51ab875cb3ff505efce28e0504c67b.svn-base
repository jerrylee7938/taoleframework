/**
 * 新增、修改、查看文档信息
 */
Ext.define("Taole.appManager.appVer.view.DownLoadWindow", {
	extend: 'Ext.Window',
	alias : 'widget.downLoadWindow',
	layout: 'border',
	width: 275,
	height: 330,
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
	fileUrl: null,
	chnlDownLoadUrl: null,
	initComponent: function() {
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'center',
			frame: true,
			layout:'absolute',
			id: 'imageForm',
			defaults:{
				xtype:'textfield',
				labelAlign:'right',
				style:{
					'margin':'10px 0px 10px 0px'
				},
				fieldStyle:"background-color:#EEEEE0;background-image: none;",
				readOnly:true,
				labelWidth:80
			}
		}];
        this.buttons = [
            {
				text: '关闭',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});