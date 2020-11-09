/**
 * 新增、修改、查看APP广告位置信息
 */
Ext.define("Taole.strategy.referralCode.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 780,
	height: 240,
//	maximized: true,
	modal: true,
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
	appAdPositionId: null,
	isView: null,
	isRetrieve: null,
	initComponent: function() {
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'center',
			frame: true,
			defaults:{
				xtype: 'panel',
				baseCls:'x-plain',
				frame: true
			},
			items:[
			{
				//--------------------------------第1行
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
						xtype:'hidden',
						name: 'id'
					},{
						fieldLabel: '营销主体类型',
						xtype: 'combobox',
						name: 'code',
						store: getDicStore("MARCKETING_SUBJECT_TYPE_CODE"),
						displayField: 'name',
						valueField: 'value',
					},
					{
						fieldLabel: '营销主体名称',
						xtype: 'textfield',
						name:'title',
						
					},	
					{
						fieldLabel: '营销渠道',
						xtype: 'textfield',
						name: 'type',
					}
				]
			},
			{//--------------------------------第2行
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
					    xtype: 'textfield',
					    name:'people',
						fieldLabel:'营销人员',
					},{
					    xtype: 'textfield',
					    name:'number',
						fieldLabel:'推荐码',
						readOnly:true,
						fieldStyle:"text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;",	
					}
				]	
			},
			{//--------------------------------第3行
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
					    xtype: 'textarea',
					    name:'description',
						fieldLabel:'描述',
						width:750,
						height:80
					}
				]	
			}]
		}];
        this.buttons = [
            {
				text: '确定',
				action: 'confirm'
			}
            ,
            {
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});