/**
 * 新增、修改、查看APP广告位置信息
 */
Ext.define("Taole.appManager.appAd.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 650,
	height: 300,
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
					width: 300
				},
				items:[
					{
						xtype:'hidden',
						name: 'id'
					},
					{
						fieldLabel: '<font color=red>*</font>应用名称',
						xtype: 'combo',
						name:'appId',
						store: Ext.create('Taole.appManager.appInfo.store.AppInfoStore'),
						displayField: 'name',
						valueField: 'id',
						allowBlank: false
					},
					{
						fieldLabel: '<font color=red>*</font>广告位编码',
						xtype: 'combobox',
						name: 'code',
						store: getDicStore("APP_AD_POSITION_CODE"),
						displayField: 'name',
						valueField: 'value',
						allowBlank: false
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
					width: 300
				},
				items:[
					{
						fieldLabel: '<font color=red>*</font>类型',
						xtype: 'combobox',
						name: 'type',
						store: getDicStore("APP_AD_PLAY_TYPE_CODE"),
						displayField: 'name',
						valueField: 'value',
						allowBlank: false
					},
				    {
					    xtype: 'combo',
					    name:'environmentId',
						fieldLabel:'<font color=red>*</font>所属环境',
						store: getDicStore("e7db2a842e3b49128bbe235cae130d58"),
						displayField:'name',
						valueField:'value',
						allowBlank: false
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
					    xtype: 'textfield',
					    name:'title',
						fieldLabel:'<font color=red>*</font>标题',
						width:600,
						allowBlank: false
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
					labelWidth: 80,
					width: 250
				},
				items:[
				   {
					    xtype: 'textarea',
					    name:'description',
						fieldLabel:'描述',
						width:600,
						height:125
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