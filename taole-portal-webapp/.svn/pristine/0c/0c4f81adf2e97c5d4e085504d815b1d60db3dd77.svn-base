/**
 * 新增、修改、查看文档信息
 */
Ext.define("Taole.appManager.appVer.view.AddOrEditWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditWindow',
	layout: 'border',
	width: 780,
	height: 500,
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
	appVerId: null,
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
					},
					{
						fieldLabel: '<font color=red>*</font>应用名称',
						xtype: 'combo',
						name:'appInfoId',
						store: Ext.create('Taole.appManager.appInfo.store.AppInfoStore'),
//						store: Ext.create("Ext.data.Store",{
//							fields: ['id', 'name'],
//							data: [{"id":"1", "name":"Portal系统"}]
//						}),
						displayField: 'name',
						valueField: 'id',
						allowBlank: false
					},
					{
						fieldLabel: '<font color=red>*</font>系统类型',
						xtype: 'combobox',
						name: 'systemType',
						store: Ext.create('Taole.appManager.appVer.store.SystemTypeStore'),
						displayField: 'name',
						valueField: 'id',
						allowBlank: false
					},
					{
						fieldLabel: '<font color=red>*</font>开发者类型',
						xtype: 'combobox',
						name: 'developerType',
						store: Ext.create('Taole.appManager.appVer.store.DeveloperTypeStore'),
						displayField: 'name',
						valueField: 'id',
						allowBlank: false,
						hidden: true,
						disabled: true
					}
				]
			},
			{
				//--------------------------------第2行
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
						fieldLabel: '<font color=red>*</font>版本名称',
						xtype: 'textfield',
						name:'versionName',
						allowBlank: false
					},{
						fieldLabel: '<font color=red>*</font>版本编号',
						xtype: 'numberfield',
						name:'versionCode',
						allowBlank: false
					},
					{
						fieldLabel: '<font color=red>*</font>版本状态',
						xtype: 'combobox',
						name: 'status',
						editable: false, 
						store: Ext.create('Taole.appManager.appVer.store.AppStatusStore'),
						displayField: 'name',
						valueField: 'id',
						allowBlank: false,
						
					}
				]
			},
			{
				//--------------------------------第2行
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
						fieldLabel: '<font color=red>*</font>SVN版本号',
						xtype: 'numberfield',
						name:'svnVer',
						allowBlank: false
					},{
						fieldLabel: '<font color=red>*</font>强制更新',
						xtype: 'combobox',
						name: 'isOptional',
						store: Ext.create('Taole.appManager.appVer.store.UpdateTypeStore'),
						displayField: 'name',
						valueField: 'id',
						allowBlank: false
					},{
						fieldLabel: '<font color=red>*</font>发布时间',
						xtype: 'datetimefield',
						name:'publishTime',
						format: 'Y-m-d H:i:s',
						allowBlank: false,
						minValue: new Date()
					}
				]
			},
			{
				//--------------------------------第3行
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
						fieldLabel: "测试是否通过",
					    xtype: 'combo',
						name:'testStatus',
						store: Ext.create("Ext.data.Store",{
							fields: ['id', 'name'],
							data: [{"id":"1", "name":"通过"},{"id":"0", "name":"不通过"},{"id":"2", "name":"未测试"}]
					}),
						displayField: 'name',
						valueField: 'id',
						value:'2',
					}
				]
			},
//			{
//				//--------------------------------第3行
//				layout: 'column',
//				frame: true,
//				baseCls: 'x-plain',
//				style: 'margin-top:5px;',
//				defaults:{
//					labelAlign: 'right',
//					labelWidth: 80,
//					width: 250
//				},
//				items:[
//					{
//					    xtype: 'label',
//					    width:200,
//					    margin: '0 0 0 30',
//					    text: "发布渠道"
//					},{
//						xtype:'button',
//						text:'选择渠道',
//						width:80,
//						margin: '0 0 0 -130',
//						name:'chooseChannel'
//					},{
//					    xtype: 'checkboxgroup',
//				        name: 'channels',
//				        width:750,
//				        columns: 5
//					}
//				]
//			},
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
					    xtype: 'filefield',
					    name:'path',
						fieldLabel:'<font color=red>*</font>上传安装包',
						buttonText: '选择安装包',
						width:750,
						disabled: true,
						hidden: true,
						allowBlank: false
					},
					{
					    xtype: 'textfield',
					    name:'updatePath',
						fieldLabel:'<font color=red>*</font>上传安装包',
						width:650,
						disabled: true,
						hidden:true
					},
					{
					    xtype: 'textfield',
					    name:'updatePath1',
					    disabled: true,
						hidden:true
					},
				   {
					    xtype: 'filefield',
					    name:'updatePathButton',
						buttonText: '重新上传',
						buttonOnly: true,
        				hideLabel: true,
        				width: 55,
						hidden:true,
						disabled: true,
						style: 'margin-left:10px;'
					}
				]	
			},
			{//--------------------------------第5行
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
					    name:'versionDesc',
						fieldLabel:'新功能描述',
						width:750,
						height:125
					}
				]	
			},
			{//--------------------------------第6行
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
					    name:'bugDesc',
						fieldLabel:'BUG修复描述',
						buttonText: '选择',
						width:750,
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