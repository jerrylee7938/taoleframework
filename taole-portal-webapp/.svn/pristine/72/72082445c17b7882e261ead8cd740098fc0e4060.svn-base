/**
 * APP版本管理
 */
Ext.define("Taole.appManager.appVer.view.AppDownLoadPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.appDownLoadPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.appManager.appVer.store.AppVerStore');		
		this.title = 'APP下载';
		this.items = [
		{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			viewConfig:{  
                enableTextSelection:true  
            }, 
			columns: [
				{header: '应用名称', align: 'center', width:120, renderer:function(){
					return "沃贷宝理财";
				}},
				{header: '系统类型' ,align: 'center', width:120, renderer:function(val){
					return "安卓";
				}},
				{header: '下载地址',align: 'center', width:700, renderer:function(){
					return $service.rest+"<br>/app.AppVer/collection/downLoad?appId=00357124345e4972af358e4551941a84&sysId=1&channel=1";
				}},
				{header: '详情', align: 'center', renderer: function(val, meta, record){
					
					if(!window.download){
						window.download = function(){
							Ext.create('Taole.appManager.appVer.controller.DownLoadCtrl').init();
							Ext.create("widget.downLoadWindow",{
					    		fileUrl: null,
					    		afterChooseFn: function(){}
					    	}).show();
						}
					}
					return '<a href="javascript:download()">详情</a>';
				}}
			],
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: store,
				displayInfo: true
	        },
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
		}
		]		
        this.callParent(arguments);
    }
});