/**
 * 执行列表
 */
Ext.define("Taole.user.userInfoManager.view.ConsumeListWindow", {
	extend: 'Ext.Window',
	alias : 'widget.consumeListWindow',
	layout: 'border',
	width: 830,
	height: 500,
	//maximizable: true,
	modal: true,
	title: "历史执行记录",
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
	initComponent: function() {
		var chooseDoctorStore = Ext.create('Taole.user.userInfoManager.store.ConsumeListStore');
		this.items = [{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			height: 0,
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
					labelWidth: 60,
					width: 200
				},
				items:[]
			}]
		},
		{
			xtype: 'grid',
			region: 'center',
			split: true,
			store: chooseDoctorStore,
			columns: [
			     {header: '序号',align: 'center',xtype:'rownumberer', width:35},
		        {header: '执行开始时间', align: 'center', dataIndex: 'jobExeStarttime', width: 150},
				{header: '执行结束时间', align: 'center', dataIndex: 'jobExeEndtime', width: 150},
				{header: '执行结果', align: 'center', dataIndex: 'jobExeResult', width: 250},
				{header: '执行结果说明', align: 'center', dataIndex: 'jobResultDesc', width: 250},

	        ],
	        //grid可复制
			viewConfig:{  
			   enableTextSelection:true  
			},
	        bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: chooseDoctorStore,
				pageSize:20,
				displayInfo: true
	        },
	        columnLines: true,
		    selModel: {selType : 'checkboxmodel'}
		}];
        this.buttons = [
            {
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});