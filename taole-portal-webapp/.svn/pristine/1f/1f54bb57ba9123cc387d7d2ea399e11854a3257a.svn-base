/**
 * 新增修改手机号
 */
Ext.define("Taole.mobileManager.dict.view.AddOrEditMobileDictWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditMobileDictWindow',
	layout: 'border',
	width: 350,
	height: 180,
	modal: true,
	frame: true,	
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
	initComponent: function() {
		this.items = [
			{
				//-----------------------------------------------表单
				xtype: 'form',
				region: 'center',
				frame: true,
				border: 0,
				defaults:{
					xtype: 'panel',
					baseCls:'x-plain',
					frame: true
				},
				items:[
				{//--------------------------------第2行
					layout: 'anchor',
					frame: true,
					baseCls: 'x-plain',
					style: 'margin-top:5px;',
					defaults:{
						labelAlign: 'right',
						labelWidth: 90,
						anchor: '90%'
					},
					items:[
						{
							fieldLabel: '<font color=red>*</font>手机号',
							xtype: 'textfield',
							name: 'id',
							allowBlank: false
						},
						{
							fieldLabel: '<font color=red>*</font>区号',
							xtype: 'textfield',
							name: 'code',
							allowBlank: false
						},
						{
							fieldLabel: '<font color=red>*</font>归属地',
							xtype: 'textfield',
							name: 'desc',
							allowBlank: false
						}
					]
				}]
			}
		];
        this.buttons = [
            {
				text: '保存',
				action: 'save'
			}
            ,{
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});