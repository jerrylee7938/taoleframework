/**
 * 消费明细
 */
Ext.define("Taole.user.consumeManager.view.ConsumeListWindow", {
	extend: 'Ext.Window',
	alias : 'widget.consumeListWindow',
	layout: 'border',
	width: 630,
	height: 500,
	//maximizable: true,
	modal: true,
	title: "消费明细",
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
		var consumeStore = Ext.create('Taole.user.consumeManager.store.ConsumeListStore');
		this.items = [{//-----------------------------------------------表单
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
					labelWidth: 80,
					width: 200
				},
				items:[{
					xtype:'hidden',
					name:'userBillNo',
				},{
					xtype: 'hidden',
					name:'userType',
				},{
					xtype: 'hidden',
					name:'operator',
				},{
					xtype: 'hidden',
					name:'shopId',
				},{
					xtype: 'hidden',
					name:'userCardId',
				},{
					xtype: 'hidden',
					name:'userId',
				},{
					fieldLabel: '会员卡号',
					xtype: 'textfield',
					name:'userCardNo',
					fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
					readOnly:true
				},
				{
					fieldLabel: '剩余次数',
					xtype: 'textfield',
					name:'blanceNum',
					fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
					readOnly:true
				},{
					fieldLabel: '本次刷卡',
					xtype: 'numberfield',
					name:'swipeAmount',
					minValue:0,
					width: 150,
					fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
					readOnly:true
				},{
					xtype: 'label',
					text:"次",
					width: 20,
					style: 'margin-left:5px;margin-top:3px;',
					
				}]
			},{
				//--------------------------------第1行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 80,
					width: 200
				},
				items:[{
					fieldLabel: '姓名',
					xtype: 'textfield',
					name:'userName',
					fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
					readOnly:true
				},
				{
					fieldLabel: '消费金额',
					xtype: 'textfield',
					name:'consumeMoney',
					fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
					readOnly:true
				},{
					fieldLabel: '支付方式',
					xtype: 'combobox',
					name: 'payType',
					displayField: 'name',
					valueField: 'value',
					store:getDicStore("PAYMODE_CODE",URL_DICTIONARY_MEMBER),
					fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
					readOnly:true
				}]
			
			}]
		},
		{
			xtype: 'grid',
			region: 'center',
			split: true,
			store: consumeStore,
			border:false,
			columnLines:true,
			plugins:[{
		    	pluginId: 'cellediting',
		    	ptype: 'cellediting',
		    	clicksToEdit: 2,
		    	autoCancel: false,
		        saveBtnText: '保存',
		        cancelBtnText: '取消'
		    }],
			columns: [
			     {header: '序号',align: 'center',xtype:'rownumberer', width:35},
		        {header: '商品名称', align: 'center', dataIndex: 'name', width: 150},
				{header: '单价', align: 'center', dataIndex: 'saleMoney', width: 80},
				{header: '数量', align: 'center', dataIndex: 'amount',width: 80},
				{header: '单位', align: 'center', dataIndex: 'unitName', width: 80},
				{header: '总价', align: 'center', dataIndex: 'consumeMoney', width: 80,renderer:function(val, meta, record){
					record.data.consumeMoney =record.data.saleMoney*record.data.amount;
					return record.data.saleMoney*record.data.amount;
				}},
	        ],
	        //grid可复制
			viewConfig:{  
			   enableTextSelection:true  
			},
	        columnLines: true,
		//    selModel: {selType : 'checkboxmodel'}
		}];
        this.buttons = [
            {
				text: '确认',
				action: 'confirm'
			},{
				text: '确认并打印',
				action: 'print'
			},{
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});