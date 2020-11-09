/**
 * 选择app发布渠道
 */
Ext.define("Taole.appManager.appVer.view.ChooseChannelWindow", {
	extend: 'Ext.Window',
	alias : 'widget.chooseChannelWindow',
	layout: 'border',
	width: 800,
	height: 500,
	maximizable: true,
	modal: true,
	title: "选择发布渠道",
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
		var appChanlStore = Ext.create('Taole.appManager.appVer.store.AppChanlStore');
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			split: true,
			frame: true,
			height: 120,
			autoScroll: true,
			defaults:{
				xtype: 'panel',
				baseCls:'x-plain',
				frame: true
			},
			items:[
				{
					xtype: 'hidden',
					name:'appVerId'
				}
			]
		},
		{
			xtype: 'grid',
			region: 'center',
			split: true,
			store: appChanlStore,
			columns: [
		        {header: '渠道名称',  dataIndex: 'channelName', width: 150},
		        {header: '渠道安装包下载地址', dataIndex: 'appDownLoadUrl', width: 400, renderer: function(val){
		        	return '<a href="javascript:void(0);" onclick="window.open(\''+ val+'\')">'+val+'</a>';
		        }},
		        {header: '用户下载地址', dataIndex: 'chnlDownLoadUrl', width: 400,editor:{
					xtype: 'textfield'
		        }},
		        {header: '操作',  width: 100, renderer: function(val, meta, record){
		        	if(!window.download){
						window.download = function(enCodeUrl, chnlDownLoadUrl){
							Ext.create('Taole.appManager.appVer.controller.DownLoadCtrl').init();
							Ext.create("widget.downLoadWindow",{
					    		fileUrl: enCodeUrl,
					    		chnlDownLoadUrl : chnlDownLoadUrl,
					    		afterChooseFn: function(){}
					    	}).show();
						}
					}
					
					
		        	return '<a href="javascript:download(\''+ record.data.enCodeUrl+'\',\''+record.data.chnlDownLoadUrl+'\')">查看二维码</a>';
		        }}
	        ],
	        //grid可复制
			viewConfig:{  
			   enableTextSelection:true  
			},
			 plugins:[{
			    	pluginId: 'rowediting',
			    	ptype: 'rowediting',
			    	clicksToEdit: 2,
			    	autoCancel: false,
			        saveBtnText: '保存',
			        cancelBtnText: '取消'
			    }],
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