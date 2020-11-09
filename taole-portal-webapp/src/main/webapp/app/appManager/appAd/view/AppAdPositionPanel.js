/**
 * APP广告位置管理
 */
Ext.define("Taole.appManager.appAd.view.AppAdPositionPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.appAdPositionPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.appManager.appAd.store.AppAdPositionStore',{
			autoLoad: true
		});		
		this.title = 'APP广告管理';
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
						name:'appId',
						store: Ext.create('Taole.appManager.appInfo.store.AppInfoStore'),
						displayField: 'name',
						valueField: 'id'
					},
					{
						fieldLabel: '广告标题',
						xtype: 'textfield',
						name: 'title'
					},
					{
						fieldLabel: '广告位置',
						xtype: 'combobox',
						name: 'code',
						store: getDicStore("APP_AD_POSITION_CODE"),
						displayField: 'name',
						valueField: 'value'
					},
					{
					    xtype: 'combo',
					    name:'environmentId',
						fieldLabel:'所属环境',
						store: getDicStore("e7db2a842e3b49128bbe235cae130d58"),
						displayField:'name',
						valueField:'value'
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
				{header: '应用名称', dataIndex: 'appName',align: 'center', width:150},
				{header: '广告位置', dataIndex: 'codeName',align: 'center'},
				{header: '广告位置标题', dataIndex: 'title', align: 'center', width:250},
				{header: '位置类型', dataIndex: 'typeName', align: 'center'},
				{header: '所属环境', dataIndex: 'environmentName', align: 'center'},
				{header: '创建日期', dataIndex: 'createTime', align: 'center', xtype: 'datecolumn', format: 'Y-m-d H:i:s', width:150},
				{header: '描述', dataIndex: 'description', align: 'center', width:300}
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true},'-',
		    	{text:'详情',action:'retrieve', pressed:true},'-',
		    	{text:'配置广告内容',action:'addBanner', pressed:true}
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