/**
 * APP广告位置管理
 */
Ext.define("Taole.dataPrivilege.dataPrivilegeManager.view.DataPrivilegePanel", {
	extend: 'Ext.Panel',
    alias : 'widget.dataPrivilegePanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.dataPrivilege.dataPrivilegeManager.store.DataPrivilegeStore');		
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
					width: 250,
					style: 'margin-left:10px;'
				},
				items:[
					{
						fieldLabel: '应用名称',
						xtype: 'combo',
						name:'appId',
						store: Ext.create('Taole.dataPrivilege.dataPrivilegeManager.store.AppInfoStore'),
						displayField: 'name',
						valueField: 'id'
					},
					{
						fieldLabel: '数据权限名称',
						xtype: 'textfield',
						name: 'nameLike'
					},
					{
						fieldLabel: '受控数据编码',
						xtype: 'textfield',
						name: 'keyLike'
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
				{
		            text: "数据权限名称",
		            width:150,
		            align:'center',
		            dataIndex: 'name'
		        },{
		            text: "所属应用",
		            dataIndex: 'appName',
		            width:150,
		            align:'center'
		        },{
		            text: "受控数据编码",
		            dataIndex: 'key',
		            width:150,
		            align:'center'
		        },{
		            text: "获取受控数据的接口地址",
		            dataIndex: 'dataUrl',
		            width:200,
		            align:'center'
		        },{
		            text: "受控数据数据区标识",
		            width:150,
		            dataIndex: 'dataPath',
		            align:'center'
		        },{
		            text: "受控数据id参数",
		            dataIndex: 'dataId',
		            width:150,
		            align:'center'
				},{
		            text: "受控数据名称参数",
		            width:150,
		            dataIndex: 'dataName',
		            align:'center'
		        }
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true}
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