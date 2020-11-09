/**
 * 新增、修改、查看文档信息
 */
Ext.define("Taole.appManager.appVer.view.AppChanlWindow", {
	extend: 'Ext.Window',
	alias : 'widget.appChanlWindow',
	layout: 'border',
	width: 750,
	height: 400,
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
	appVerId: null,
	initComponent: function() {
		var appChanlStore = Ext.create('Taole.appManager.appVer.store.AppChanlStore');
		this.items = [
		{
			xtype: 'grid',
			region: 'center',
			store: appChanlStore,
			columns: [
		        {header: '渠道名称',  dataIndex: 'channelName', width: 150},
		        {header: '下载地址', dataIndex: 'appDownLoadUrl', width: 400},
		        {header: '操作',  width: 100, renderer: function(val, meta, record){
		        	if(!window.download){
						window.download = function(url){
							Ext.create('Taole.appManager.appVer.controller.DownLoadCtrl').init();
							Ext.create("widget.downLoadWindow",{
					    		fileUrl: url,
					    		afterChooseFn: function(){}
					    	}).show();
						}
					}
		        	return '<a href="javascript:download(\''+ record.data.enCodeUrl+'\')">查看二维码</a>';
		        }}
	        ],
	        //grid可复制
			viewConfig:{  
			   enableTextSelection:true  
			},
	        bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: appChanlStore,
				pageSize:20,
				displayInfo: true
	        },
	        columnLines: true,
		    selModel: {selType : 'checkboxmodel', mode: 'SINGLE'}
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