/**
 * 用户管理
 */
Ext.define("Taole.user.userInfoManager.view.UserInfoPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.userInfoPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.user.userInfoManager.store.UserInfoStore');		
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			minheight: 70,
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
					width: 240
				},
				items:[{
					fieldLabel: '会员姓名',
					xtype: 'textfield',
					name:'name',
				},{
					xtype:'hidden',
					name:'userId'
				},
				{
					fieldLabel: '卡号',
					xtype: 'textfield',
					name:'cardNo',
				},
				{
					fieldLabel: '会员卡状态',
					xtype: 'combobox',
					name: 'status',
					displayField: 'name',
					valueField: 'value',
				    store: getDicStore("78110cde012d44cc81988fb7ba116460",URL_DICTIONARY_MEMBER),
				},{
					text: '查询', 
					xtype: 'button', 
					action: 'query', 
					style: 'margin-left:20px;',
					width: 70
				}
				]
			},{//--------------------------------第1行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 240
				},
				items:[{
					fieldLabel: '开卡店',
					xtype: 'combobox',
					name: 'shopId',
					displayField: 'name',
					valueField: 'shopId',
					store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
						autoLoad: true
					}),
				},
				{
					fieldLabel: '开卡起始日期',
					xtype:'datefield',
					format:'Y-m-d',	
					name:'startTime',
				},{
					fieldLabel: '开卡结束日期',
					xtype:'datefield',
					format:'Y-m-d',	
					name:'endTime',
				},
				{
					text: '重置', 
					xtype: 'button', 
					action: 'reset',
					style: 'margin-left:20px;',
					width: 70
					}
				]
			}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columns: [
			    {header: '序号',align: 'center',xtype:'rownumberer', width:35},
			    {header: '卡号', dataIndex: 'cardNo', align: 'center', width:150},
			    {header: '卡名称', dataIndex: 'cardName', align: 'center', width:150},
				{header: '卡类型', dataIndex: 'cardTypeName', align: 'center', width:100},
				{header: '剩余次数', dataIndex: 'blanceNum', align: 'center', width:100,renderer:function(v,c,r){
					if(r.data.cardTypeName == '期限卡'){
						return '--';
					}else {
						return v;
					}
				}},
				{header: '截止日期', dataIndex: 'deadline', align: 'center', width:150},
				{header: '宝宝姓名', dataIndex: 'userName', align: 'center', width:100,},
				{header: '卡联系手机号', dataIndex: 'telphone', align: 'center', width:100,},
				{header: '状态', dataIndex: 'cardStatus', align: 'center', width:100},
				{header: '开卡时间', dataIndex: 'approverTime', align: 'center', width:150},
				{header: '审核时间', dataIndex: 'auditTime', align: 'center', width:150},
				{header: '开卡人', dataIndex: 'operator', align: 'center', width:100},
				{header: '店名称', dataIndex: 'shopName', align: 'center', width:100},
				{header: '备注', dataIndex: 'description', align: 'center', width:200,renderer:function(v,rec,record){
					rec.tdAttr = 'qclass="x-tip" data-qtitle="备注：" data-qwidth="200" data-qtip="'+v+ '"';
    				return v;}}
			],
			 viewConfig:{  
	                enableTextSelection:true  
	            }, 
		    tbar : [
		        	{text:'办卡',action:'apply', pressed:true,privilegeUrl:URL_MEMBER_MEMBERCARD_CREATE},
		        	{text:'修改会员信息',action:'update', pressed:true,privilegeUrl:URL_MEMBER_MEMBERCARD_UPDATE},
					{text:'办卡审核',action:'open', pressed:true,privilegeUrl:URL_MEMBER_MEMBERCARD_OPEN},
					{text:'退卡',action:'back', pressed:true},
					{text:'充值',action:'recharge', pressed:true,privilegeUrl:URL_MEMBER_MEMBERCARD_RECHARGE},
					{text:'停用',action:'stop', pressed:true},
					{text:'启用',action:'start', pressed:true}
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