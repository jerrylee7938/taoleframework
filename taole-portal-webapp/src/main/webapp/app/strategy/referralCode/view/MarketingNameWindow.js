Ext.define("Taole.strategy.referralCode.view.MarketingNameWindow", {
    extend: 'Ext.Window',
    alias: 'widget.marketingNameWindow',
    layout: 'border',
    modal: true,
    frame: true,
    maximized: true,
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
    border: 0,
    initComponent: function () {
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
    				items:[{
    					fieldLabel: '营销主体名称',
    					xtype: 'textfield',
    					name: 'title'
    					},
    					{
    						fieldLabel: '营销主体类型',
    						xtype: 'combo',
    						name:'appId',
    						store: Ext.create('Taole.appManager.appInfo.store.AppInfoStore'),
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
    				{header: '营销主体名称', dataIndex: 'appName',align: 'center', width:150},
    				{header: '营销主体类型', dataIndex: 'codeName',align: 'center',width:150},
    				{header: '创建日期', dataIndex: 'createTime', align: 'center', xtype: 'datecolumn', format: 'Y-m-d H:i:s', width:200},
    				
    			],
    		    tbar : [
    		    	
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
        ];
        this.buttons = [
            {
                text: '确定',
                action: 'confirm'
            },
            {
                text: '取消',
                action: 'cancel'
            }
        ];
        this.callParent(arguments);
    },
    buttonAlign: 'center'
});