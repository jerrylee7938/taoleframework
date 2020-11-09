Ext.define("Taole.strategy.referralCode.view.MarketingPeopleWindow", {
    extend: 'Ext.Window',
    alias: 'widget.marketingPeopleWindow',
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
    //	var store = Ext.create('Taole.strategy.referralCode.store.MarketingPeopleStore',{
	//		autoLoad: true
	//	});		
		this.title = '营销推荐码管理';
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
    					fieldLabel: '营销人姓名',
    					xtype: 'textfield',
    					name: 'title'
    					},
    					{
    						fieldLabel: '营销人编号',
    						xtype: 'textfield',
        					name: 'title'
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
    				{header: '营销人姓名', dataIndex: 'appName',align: 'center', width:150},
    				{header: '营销人编号', dataIndex: 'codeName',align: 'center',width:150},
    				{header: '性别', dataIndex: 'sex',align: 'center',width:150},
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