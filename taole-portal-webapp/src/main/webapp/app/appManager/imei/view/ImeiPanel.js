/**
 * APP版本管理
 */
Ext.define("Taole.appManager.imei.view.ImeiPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.imeiPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.appManager.imei.store.ImeiStore');		
		var roleStore = Ext.create("Taole.appManager.imei.store.RoleStore");
		var stateStore = Ext.create("Taole.appManager.imei.store.StateStore");
		this.title = 'IMEI管理';
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
						fieldLabel: 'IMEI',
						xtype: 'textfield',
						name: 'imei'
					},
					{
						fieldLabel: '所属角色',
						xtype: 'combo',
						store: roleStore,
						name: 'role',
						displayField: 'name',
						valueField: 'value'
			        },
			        {
						fieldLabel: '备注',
						xtype: 'textfield',
						name: 'reMark'
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
				{header: 'IMEI', dataIndex: 'imei',align: 'center', width:300, editor:{
					xtype: 'textfield',
		        	allowBlank: false
		        }},
		        {header: '所属角色', dataIndex: 'role',renderer: commonRenderer(roleStore), align: 'center', width:300, editor:{
					xtype: 'combo',
					store: roleStore,
					displayField: 'name',
					valueField: 'value',
		        	allowBlank: false
		        }},
		        {header: '状态', dataIndex: 'status',renderer: commonRenderer(stateStore),align: 'center', width:150, editor:{
		        	xtype: 'combo',
					store: stateStore,
					displayField: 'name',
					valueField: 'value',
					allowBlank: false
		        }},
		        {header: '备注', dataIndex: 'reMark',align: 'center', width:300, editor:{
					xtype: 'textfield',
					emptyText:'内容格式:姓名/OS类型/手机品牌型号',
					allowBlank:false,
					blankText:'内容格式:姓名/OS类型/手机品牌型号',
					regex:/^(.*)+\/(.*)+\/(.*)+$/,
					regexText:"内容格式:姓名/OS类型/手机品牌型号",
					
		        }}
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true}
		    ],
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: store,
				displayInfo: true
	        },
	        plugins:[{
		    	pluginId: 'rowediting',
		    	ptype: 'rowediting',
		    	clicksToEdit: 2,
		    	autoCancel: false,
		        saveBtnText: '保存',
		        cancelBtnText: '取消'
		    }],
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
		}
		]		
        this.callParent(arguments);
    }
});