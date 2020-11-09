/**
 * APP版本管理
 */
Ext.define("Taole.appManager.appVer.view.AppVerPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.appVerPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.appManager.appVer.store.AppVerStore',{
			autoLoad: true
		});		
		this.title = 'APP版本管理';
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			height: 40,
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
				items:[
					{
						fieldLabel: '应用名称',
						xtype: 'combo',
						name:'appInfoId',
						store: Ext.create('Taole.appManager.appInfo.store.AppInfoStore'),
						displayField: 'name',
						valueField: 'id'
					},
					{
						fieldLabel: '系统类型',
						xtype: 'combobox',
						name: 'systemType',
						store: Ext.create('Taole.appManager.appVer.store.SystemTypeStore'),
						displayField: 'name',
						valueField: 'id'
					},
					{
						fieldLabel: '版本状态',
						xtype: 'combobox',
						name: 'status',
						store: Ext.create("Ext.data.Store",{
							model: 'Taole.appManager.appVer.model.SystemTypeItem',
						    autoLoad: true,
						    proxy: {
						        type: 'ajax',
						        url: APP_VER_APP_STATUS,
						        reader: {
						            type: 'json'
						        }
						    }
						}),
						displayField: 'name',
						valueField: 'id'
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
			}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columns: [
	          	{header: '版本ID', dataIndex: 'id',align: 'center', width:150},
				{header: '应用名称', dataIndex: 'appInfoName',align: 'center', width:150},
				//{header: '所有者', dataIndex: 'owner',align: 'center', width:150},
				{header: '系统类型', dataIndex: 'systemType',align: 'center', width:150},
		/*		{header: '系统类型', dataIndex: 'fileName',align: 'center', width:150, renderer: function(val, meta, record){
					if(!window.setChannel){
						window.setChannel = function(appInfoId,type){
							Ext.create("Taole.appManager.appVer.controller.ChooseChannelCtrl").init();
		    				Ext.create("Taole.appManager.appVer.view.ChooseChannelWindow", {
		    					appVerId: appInfoId,
		    					type:type
		    				}).show();
						}
					}
					return '<a href="javascript:setChannel(\''+ record.data.id+'\',\''+record.data.systemType+'\')">' + val + '</a>';
				}}, */
				{header: '最新版本号', dataIndex: 'versionCode', align: 'center'},
				{header: '版本名称', dataIndex: 'versionName', align: 'center'},
				{header: 'SVN版本号', dataIndex: 'svnVer', align: 'center'},
				{header: '状态', dataIndex: 'status', align: 'center', renderer: function(val){
					if(val == "TEST") return "测试版";
					if(val == "PRD") return "上线版";
					if(val == "DEV") return "研发版";
					if(val == "PRE") return "预发布版";
				}},
				{header: '测试是否通过', dataIndex: 'testStatus', align: 'center',renderer: function(val){
					if(val == "1") return "通过";
					if(val == "0") return "不通过";
					else return "未测试";
				}},
				{header: '发布日期', dataIndex: 'publishTime', align: 'center', xtype: 'datecolumn', format: 'Y-m-d'},
				{header: '开发者类型', dataIndex: 'developerType', align: 'center',width:150, renderer: function(val){
					if(val){
						if(val == "P1") return "个人账号的开发者证书";
						if(val == "P2") return "个人账号的appStore上线正式证书";
						if(val == "C1") return "公司账号的开发者证书";
						if(val == "C2") return "公司账号的appStore上线正式证书";
						if(val == "E1") return "企业账号的开发者证书";
						if(val == "E2") return "企业账号的上线正式证书";
					}
				}}
			],
			//grid可复制
			viewConfig:{  
			   enableTextSelection:true  
			},
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true},'-',
		    	{text:'详情',action:'retrieve', pressed:true},'-',
		    	{text:'配置渠道',action:'channel', pressed:true},'-',
		    	{text:'下线',action:'Offline', pressed:true},'-',
		    	{text:'AppStore审核通过',action:'iosPass', pressed:true},'-',
		    	{text:'测试通过',action:'pass', pressed:true},'-',
		    	{text:'测试不通过',action:'noPass', pressed:true},
		    	{text:'各渠道下载地址',action:'downloadurl', pressed:true, hidden: true}
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