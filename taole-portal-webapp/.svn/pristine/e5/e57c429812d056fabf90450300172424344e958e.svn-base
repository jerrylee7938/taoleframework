/*个人用户增加界面*/
Ext.define("Taole.marketing.view.ChanneEditV", {
    extend: 'Ext.window.Window',
    alias: 'widget.channeEditV',
    layout: 'fit',
    title: '渠道维护',
    modal: true,
    frame: true,
    autoShow: false,
    width: 500,
    height: 380,
    constrain: true,
    minWidth: 500,
    minHeight: 380,
    initComponent: function () {
        this.items = [{
            xtype: 'form',
            bodyStyle: 'padding:10px',
            border: true,
            autoScroll: true,
            buttonAlign: 'center',
            buttons: [{
                text: '保存',
                xtype: 'button',
                id:'btn',
                action: 'create',
                style: 'margin-left:10px;',
                width: 80,
                scope: this
            }, {
                text: '取消',
                xtype: 'button',
                action: 'close',
                style: 'margin-left:10px;',
                width: 80
            }],
            defaults: {
                anchor: '95%,95%',
                /*    baseCls: 'x-plain',*/
                labelAlign: 'right',
                labelWidth: 140,
                frame: true
            },
            defaultType: 'textfield',
            items: [
                {
                	xtype: "combobox",
                    fieldLabel: '渠道类型',
                    name: 'type',
                    emptyText: '请选择渠道类型',
                    allowBlank: false,
                    editable: false,
                    store: Ext.create('Taole.appManager.appVer.store.ChannelTypeStore'),
					displayField: 'name',
					valueField: 'id'
                },
                {
                    fieldLabel: '渠道名称',
                    name: 'name',
                    allowBlank: false
                },
                {
                    xtype: "combobox",
                    editable: false,
                    name: 'status',
                    allowBlank: false,
                    emptyText: '请选择渠道状态',
                    fieldLabel: '渠道状态',
                    valueField: 'value',
                    queryMode: 'local',
                    value: 'enable',
                    displayField: 'name',
                    store: Ext.create('Ext.data.Store', {
                        fields: ['name', 'value'],
                        data: [
                            {name: '可用', value: 'enable'},
                            {name: '不可用', value: 'disable'}
                        ]
                    })
                },
                {
                    fieldLabel: '渠道key',
                    name: 'key',
                    allowBlank: false
                },{
                    xtype: "combobox",
                    editable: false,
                    name: 'isUploadPackage',
                    allowBlank: false,
                    emptyText: '请选择',
                    fieldLabel: '是否需要上传APP安装包',
                    valueField: 'value',
                    displayField: 'name',
                    store: Ext.create('Ext.data.Store', {
                        fields: ['name', 'value'],
                        data: [
                            {name: '否', value: '0'},
                            {name: '是', value: '1'}
                        ]
                    }),
                    listeners:{
						change:function(combox){
							var val = combox.getValue();
							if(val == 0){
								Ext.getCmp("downloadUrl").hide();
							}else if(val == 1){
								Ext.getCmp("downloadUrl").show();
							}
							
						},
					}
                
                },
                {
                    xtype: "combobox",
                    editable: false,
                    name: 'loadType',
                    emptyText: '请选择渠道介质类型',
                    fieldLabel: '渠道介质类型',
                    valueField: 'value',
                    displayField: 'name',
                    store: Ext.create('Ext.data.Store', {
                        fields: ['name', 'value'],
                        data: [
                            {name: 'App', value: '1'},
                            {name: 'website', value: '2'},
                            {name: 'App&website', value: '3'}
                        ]
                    })
                
                },
                {
                	xtype: 'textareafield',
                    grow: true,
                    id:'downloadUrl',
                    fieldLabel: '上传地址',
                    name: 'downloadUrl',
                    height: 50
                },
                {
                    xtype: 'textareafield',
                    grow: true,
                    name: 'description',
                    fieldLabel: '备注',
                    height: 70
                },
                {
                    xtype: 'hiddenfield',
                    name: 'id'
                }
            ]
        }]
        this.callParent(arguments);
    }
});