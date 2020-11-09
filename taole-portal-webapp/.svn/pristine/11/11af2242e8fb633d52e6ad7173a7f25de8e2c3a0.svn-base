/**
 * 新增、修改、查看APP广告位置信息
 */
Ext.define("Taole.dataPrivilege.dataPrivilegeManager.view.AddOrEditDataPrivilegeWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditDataPrivilegeWindow',
	layout: 'border',
	width: 600,
	height: 400,
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
					labelWidth: 120,
					width: 300
				},
				items:[
					{
						xtype:'hidden',
						name: 'id'
					},
					{
						fieldLabel: '<font color=red>*</font>所属名称',
						xtype: 'combo',
						name:'appId',
						store: Ext.create('Taole.dataPrivilege.dataPrivilegeManager.store.AppInfoStore'),
						displayField: 'name',
						valueField: 'id',
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
					labelWidth: 120,
					width: 300
				},
				items:[
				   {
					    xtype: 'textfield',
					    name:'name',
						fieldLabel:'<font color=red>*</font>数据权限名称',
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
					labelWidth: 120,
					width: 300
				},
				items:[
				   {
					    xtype: 'textfield',
					    name:'key',
						fieldLabel:'<font color=red>*</font>受控数据编码',
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
					labelWidth: 120,
					width: 550
				},
				items:[
				   {
					    xtype: 'textarea',
					    name:'dataUrl',
						fieldLabel:'<font color=red>*</font>获取受控数据的接口地址',
						allowBlank: false,
						height: 50
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
					labelWidth: 120,
					width: 550
				},
				items:[
				   {
					    xtype: 'textfield',
					    name:'dataPath',
						fieldLabel:'受控数据数据区标识',
						width: 300
				   },
				   {
				        xtype: 'label',
				        width: 250,
				        text: '（接口返回JSON的受控数据所在标签路径）',
				        margin: '0 0 0 10'
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
					labelWidth: 120,
					width: 550
				},
				items:[
				   {
					    xtype: 'textfield',
					    name:'dataId',
						fieldLabel:'<font color=red>*</font>受控数据ID标识',
						allowBlank: false,
						width: 300
				   },
				   {
				        xtype: 'label',
				        width: 250,
				        text: '（接口返回JSON的受控数据id对应的key）',
				        margin: '0 0 0 10'
				    }
				]	
			},
			{//--------------------------------第7行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 120,
					width: 550
				},
				items:[
				   {
					    xtype: 'textfield',
					    name:'dataName',
						fieldLabel:'<font color=red>*</font>受控数据名称标识',
						allowBlank: false,
						width: 300
				   },
				   {
				        xtype: 'label',
				        width: 250,
				        text: '（接口返回JSON的受控数据名称对应的key）',
				        margin: '0 0 0 10'
				    }
				]	
			},
			{//--------------------------------第8行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 120,
					width: 550
				},
				items:[
				   {
					    xtype: 'textarea',
					    name:'description',
						fieldLabel:'数据权限描述',
						height:50
					}
				]	
			},
			{//--------------------------------第9行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				items:[
				   {
					    xtype: 'label',
					    style: 'margin-top:10px;margin-left:130px;',
				        html: '接口配置样例'
				   },
				   {
					    xtype: 'label',
					    name:'configValidate',
					    style: 'margin-top:10px;margin-left:30px;',
				        html: '<a href="javascript:configValidate()">配置校验</a>'
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
