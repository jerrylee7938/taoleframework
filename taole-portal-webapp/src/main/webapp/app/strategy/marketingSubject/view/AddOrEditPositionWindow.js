/**
 * 新增、修改、查看
 */
Ext.define("Taole.strategy.marketingSubject.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 550,
	height: 245,
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
					labelWidth: 90,
					width: 260
				},
				items:[
					{
						xtype:'hidden',
						name: 'id'
					},
					{
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
					labelWidth: 90,
					width: 260
				},
				items:[{
					fieldLabel: '营销主体URL',
					xtype: 'textfield',
					name: 'type',
					width: 520
				}
				]	
			},{//--------------------------------第3行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 260
				},
				items:[{
					 fieldLabel:'营销主体推广图',
					 xtype: 'filefield',
					 width: 520,
					 name: 'photo',
				     msgTarget: 'side',
				     allowBlank: false,
				     anchor: '100%',
				     buttonText: '选择图片'
				}
				]	
			},
			{//--------------------------------第4行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 90,
					width: 260
				},
				items:[
				   {
					    xtype: 'textarea',
					    name:'description',
						fieldLabel:'说明',
						width:520,
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