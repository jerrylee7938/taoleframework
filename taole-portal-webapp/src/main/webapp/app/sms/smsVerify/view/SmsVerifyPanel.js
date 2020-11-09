/**
 * 短信验证码查询
 */
Ext.define("Taole.sms.smsVerify.view.SmsVerifyPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.smsVerifyPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.sms.smsVerify.store.SmsVerifyStore',{
			autoLoad: true
		});		
		this.title = '短信验证码查询';
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
					labelWidth: 90,
					width: 250
				},
				items:[{
					fieldLabel: '接收手机号',
					xtype: 'textfield',
					name: 'telephoneLike'
				},
				{
					fieldLabel: '验证码类型',
					xtype: 'combo',
					name: 'verifyType',
					store: Ext.create('Ext.data.Store', {
					    fields: ['value', 'name'],
					    data : [
					        {"value":"1", "name":"注册验证码"},
					        {"value":"2", "name":"找回密码验证码"},
					        {"value":"3", "name":"验证身份类验证"}
					    ]
					}),
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
				{header: '序号', xtype: "rownumberer",align: 'center', width:35},
				{header: '短信验证码', dataIndex: 'verify', align: 'center', width:100},
				{header: '接收手机号', dataIndex: 'telephone', align: 'center', width:150},
				{header: '验证码类型', dataIndex: 'verifyType', align: 'center', width:120, renderer:function(v){
					if(v == '1') return "注册验证码";
					if(v == '2') return "找回密码验证码";
					if(v == '3') return "验证身份类验证";
				}},
				{header: '是否使用', dataIndex: 'isUsed', align: 'center', width:100, renderer:function(v){
					if(v == '1') return "已使用";
					if(v == '0') return "未使用";
				}},
				{header: '是否发送', dataIndex: 'isSend', align: 'center', width:100, renderer:function(v){
					if(v == '1') return "已发送";
					if(v == '0') return "未发送";
				}},
				{header: '过期时间', dataIndex: 'expiredTime', align: 'center', width:150},
				{header: '发送时间', dataIndex: 'createdTime', align: 'center', width:150}
			],
			//grid可复制
			viewConfig:{  
			   enableTextSelection:true  
			},
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