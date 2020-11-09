/**
 * 短信管理
 */
Ext.define("Taole.sms.smsApply.view.SmsApplyPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.smsApplyPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.sms.smsApply.store.SmsApplyStore',{
			autoLoad: true
		});		
		this.title = '短信接入管理';
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			height: 70,
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
					labelWidth: 90,
					width: 250
				},
				items:[{
					fieldLabel: '申请人公司名称',
					xtype: 'textfield',
					name: 'companyNameLike',
				},
				{
					fieldLabel: '套餐类型',
					xtype: 'combobox',
					name: 'type',
					store: Ext.create("Taole.sms.smsApply.store.TypeStore"),
					displayField: 'name',
					valueField: 'value',
				},{
					fieldLabel: '申请状态',
					xtype: 'combobox',
					name: 'status',
					store: Ext.create("Taole.sms.smsApply.store.StatusStore"),
					displayField: 'name',
					valueField: 'value',
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
			},{//--------------------------------第2行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 250
				},
				items:[
				       {
					fieldLabel: '申请人姓名',
					xtype: 'textfield',
					name:'nameLike',
				},{
					fieldLabel: '申请人电话',
					xtype: 'textfield',
					name: 'telLike',
				}
				]
			}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columns: [
				{header: '序号', xtype: "rownumberer",align: 'center', width:35},
				{header: '接入公司名称', dataIndex: 'companyName', align: 'center', width:200},
				{header: '接入套餐类型名称', dataIndex: 'typeName', align: 'center', width:150},
				{header: '接入公司地址', dataIndex: 'companyAddr', align: 'center', width:250},
				{header: '所属行业', dataIndex: 'profession', align: 'center', width:100},
				{header: '申请人姓名', dataIndex: 'name', align: 'center', width:100},
				{header: '申请人职务', dataIndex: 'job', align: 'center', width:100},
				{header: '申请人电话', dataIndex: 'tel', align: 'center', width:100},
				{header: '申请人邮箱地址', dataIndex: 'email', align: 'center', width:200},
				{header: '申请人微信号', dataIndex: 'wechat', align: 'center', width:150},
				{header: '申请人QQ号', dataIndex: 'qq', align: 'center', width:150},
				{header: '短信接入方服务器IP', dataIndex: 'ip', align: 'center', width:200},
				{header: '回执后URL', dataIndex: 'callbackUrl', align: 'center', width:300},
				{header: '发送短信前缀', dataIndex: 'prefix', align: 'center', width:300},
				{header: '申请状态', dataIndex: 'statusName', align: 'center', width:100},
				{header: '申请时间', dataIndex: 'applyTime', align: 'center', width:150,xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
				{header: '审核时间', dataIndex: 'auditTime', align: 'center', width:150,xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
				{header: '创建时间', dataIndex: 'createTime', align: 'center', width:150,xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
				{header: '修改时间', dataIndex: 'updateTime', align: 'center', width:150,xtype: 'datecolumn', format: 'Y-m-d H:i:s'}
			],
			//grid可复制
			viewConfig:{  
			   enableTextSelection:true  
			},
		    tbar : [
		    	{text:'审核',action:'examine', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'测试发送短信',action:'test', pressed:true}
		    	
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