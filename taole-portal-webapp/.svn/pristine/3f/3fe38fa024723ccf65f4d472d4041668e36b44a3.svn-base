/**
 * APP版本管理
 */
Ext.define("Taole.appManager.appNews.view.AppNewsPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.appNewsPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.appManager.appNews.store.AppNewsStore');		
		this.title = 'APP资讯内容管理';
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
						fieldLabel: 'APP名称',
						xtype: 'combo',
						name:'appId',
						store: Ext.create('Taole.appManager.appInfo.store.AppInfoStore'),
						displayField: 'name',
						valueField: 'id'
					},
					{
						fieldLabel: '内容展示位',
						xtype: 'combo',
						store: '',
						name: 'role',
						displayField: 'name',
						valueField: 'value'
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
			          {xtype: "rownumberer", header: "序号", align: 'center', width:40 },
				{header: '图标', dataIndex: 'imei',align: 'center', width:300, editor:{
					xtype: 'textfield',
		        	allowBlank: false
		        }},
		        {header: '标题', dataIndex: 'role', align: 'center', width:300, editor:{
					xtype: 'textfield',
		        	allowBlank: false
		        }},
		        {header: '简介', dataIndex: 'reMark',align: 'center', width:300, editor:{
					xtype: 'textfield'
		        }},{
		        	header: '链接', dataIndex: 'reMark',align: 'center', width:300, editor:{
					xtype: 'textfield'
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