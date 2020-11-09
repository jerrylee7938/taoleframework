/**
 * 新增、修改、查看文档信息
 */
Ext.define("Taole.appManager.appVer.view.DetailWindow", {
	extend: 'Ext.Window',
	alias : 'widget.detailWindow',
	layout: 'border',
	width: 750,
	height: 500,
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
	appVerId: null,
	initComponent: function() {
		this.items = [
		{//-----------------------------------------------表单
			xtype: 'form',
			region: 'center',
			frame: true,
			layout:'absolute',
			defaults:{
				xtype:'textfield',
				labelAlign:'right',
				style:{
					'margin':'10px 0px 10px 0px'
				},
				fieldStyle:"background-color:#EEEEE0;background-image: none;",
				readOnly:true,
				labelWidth:80
			},
			items:[
				{
					x:0,
					y:0,
					fieldLabel: '<font color=red>*</font>应用名称',
					xtype: 'combo',
					name:'appInfoId',
					store: Ext.create('Taole.appManager.appInfo.store.AppInfoStore'),
					displayField: 'name',
					valueField: 'id',
					allowBlank: false
				},
				{
					x:250,
					y:0,
					fieldLabel: '<font color=red>*</font>系统类型',
					xtype: 'combobox',
					name: 'systemType',
					store: Ext.create('Taole.appManager.appVer.store.SystemTypeStore'),
					displayField: 'name',
					valueField: 'id',
					allowBlank: false
				},
				{
					x:520,
					y:0,
					xtype:'image',
					style:{
						'border':'1px solid gray'
					},
					labelWidth:0,
					id:'QRCode',
					height:150,
					width:150
				},
				{
					x:0,
					y:30,
					fieldLabel: '<font color=red>*</font>版本名称',
					xtype: 'textfield',
					name:'versionName',
					allowBlank: false
				},{
					x:250,
					y:30,
					fieldLabel: '<font color=red>*</font>版本编号',
					xtype: 'numberfield',
					name:'versionCode',
					allowBlank: false
				},
				{
					x:0,
					y:60,
					fieldLabel: '<font color=red>*</font>版本状态',
					xtype: 'combobox',
					name: 'status',
					store: Ext.create('Taole.appManager.appVer.store.AppStatusStore'),
					displayField: 'name',
					valueField: 'id',
					allowBlank: false
				},{
					x:250,
					y:60,
					fieldLabel: '<font color=red>*</font>SVN版本号',
					xtype: 'numberfield',
					name:'svnVer',
					allowBlank: false
				},
				{
					x:0,
					y:90,
					fieldLabel: '<font color=red>*</font>强制更新',
					xtype: 'combobox',
					name: 'isOptional',
					store: Ext.create('Taole.appManager.appVer.store.UpdateTypeStore'),
					displayField: 'name',
					valueField: 'id',
					allowBlank: false
				},{
					x:250,
					y:90,
					fieldLabel: '<font color=red>*</font>发布时间',
					xtype: 'datefield',
					name:'publishTime',
					format: 'Y-m-d',
					allowBlank: false
				},
				{
					x:0,
					y:120,
				    xtype: 'textfield',
				    name:'updatePath',
					fieldLabel:'<font color=red>*</font>下载地址',
					width:480,
					allowBlank: false
				},
				{
					x:0,
					y:150,
				    xtype: 'textarea',
				    name:'versionDesc',
					fieldLabel:'新功能描述',
					width:710,
					height:125
				},
				{
					x:0,
					y:280,
				    xtype: 'textarea',
				    name:'bugDesc',
					fieldLabel:'BUG修复描述',
					buttonText: '选择',
					width:710,
					height:125
				}
			]
		}];
        this.buttons = [
            {
				text: '关闭',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});