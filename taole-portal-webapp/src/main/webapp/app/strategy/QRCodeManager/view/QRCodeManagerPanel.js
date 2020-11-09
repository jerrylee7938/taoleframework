/**
 * 二维码管理
 */
Ext.define("Taole.strategy.QRCodeManager.view.QRCodeManagerPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.qrCodeManagerPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.strategy.QRCodeManager.store.QRCodeManagerStore',{
			autoLoad: true
		});		
		this.title = '二维码管理';
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			height: 65,
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
					fieldLabel: '营销主体类型',
					xtype: 'combo',
					name: 'title',
					store: Ext.create('Taole.strategy.marketingType.store.MarketingTypeStore'),
					displayField: 'name',
					valueField: 'id'
					},
					{
						fieldLabel: '营销渠道',
						xtype: 'combo',
						name:'appId',
						store: Ext.create('Taole.strategy.marketingType.store.MarketingTypeStore'),
						displayField: 'name',
						valueField: 'id'
					},{
						fieldLabel: '二维码类型',
						xtype: 'combo',
						name:'appId',
						store: '',
						displayField: 'name',
						valueField: 'id'	
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
				items:[{
					fieldLabel: '营销主体',
					xtype: 'combo',
					name: 'title'
					},
					{
						fieldLabel: '营销人员',
						xtype: 'combo',
						name:'appId',
						store: '',
						displayField: 'name',
						valueField: 'id'
					},
					{
						text: '查询', 
						xtype: 'button', 
						action: 'query', 
						style: 'margin-left:30px;',
						width: 90
					},
					{
						text: '重置', 
						xtype: 'button', 
						action: 'reset',
						style: 'margin-left:30px;',
						width: 90
					}
				]
			}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columns: [
			    {xtype: "rownumberer", header: "序号", align: 'center', width:40 },
				{header: '营销主体', dataIndex: 'appName',align: 'center', width:150},
				{header: '营销渠道', dataIndex: 'codeName',align: 'center',width:150},
				{header: '营销人员', dataIndex: 'codeName',align: 'center',width:100},
				{header: '营销主体背景图', dataIndex: 'title', align: 'center', width:100},
				{header: '二维码', dataIndex: 'typeName', align: 'center', width:100,
					renderer:function(v,rec,record){
            		if(v){
            			return '<img src="http://download.yishengjia1.com/image/product_zlygjy_nv18zlfatj.jpg"  style="width:70px;"/>';
            		}else{
            			return '暂无照片';
            		}
	        		
        	}},
				{header: '二维码合成图', dataIndex: 'description', align: 'center', width:100},
				{header: '二维码类型', dataIndex: 'description', align: 'center', width:100},
				{header: '状态', dataIndex: 'description', align: 'center', width:100},
			],
		    tbar : [
		    	{text:'新增',action:'add', pressed:true},'-',
		    	{text:'修改',action:'update', pressed:true},'-',
		    	{text:'删除',action:'remove', pressed:true},'-',
		    	{text:'详情',action:'retrieve', pressed:true},
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