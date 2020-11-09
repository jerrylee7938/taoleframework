/*个人用户增加界面*/
Ext.define("Taole.system.marketing.view.CouponEditV", {
    extend: 'Ext.window.Window',
    alias: 'widget.couponEditV',
    layout: 'fit',
    title: '优惠券维护',
    modal: true,
    frame: true,
    autoShow: false,
    autoScroll: false,
    constrain: true,
    minWidth: 550,
    minHeight: 430,
    initComponent: function () {
        this.items = [{
            xtype: 'form',
            bodyStyle: 'padding:10px',
            border: true,
            height: "100%",
            autoScroll: false,
            buttonAlign: 'center',
            buttons: [{
                text: '保存',
                xtype: 'button',
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
                anchor: '80%,80%',
                /*    baseCls: 'x-plain',*/
                labelAlign: 'right',
                labelWidth: 120,
                frame: true
            },
            defaultType: 'textfield',
            items: [
                {
                    fieldLabel: '优惠券名称',
                    name: 'name',
                    allowBlank: false
                },
                {
                    fieldLabel: '优惠券类别',
                    allowBlank: false,
                    xtype: 'combo',
                    name: 'type',
                    store: Ext.create("Taole.system.marketing.store.LottoryTypeST"),
                    displayField: 'value',
                    valueField: 'key'
                },
                {
                    xtype: "combobox",
                    editable: false,
                    name: 'status',
                    emptyText: '请选择',
                    fieldLabel: '优惠券状态',
                    valueField: 'value',
                    value: 'enable',
                    queryMode: 'local',
                    displayField: 'name',
                    store: Ext.create('Ext.data.Store', {
                        fields: ['name', 'value'],
                        data: [
                            {name: '可用', value: 'enable'},
                            {name: '禁用', value: 'disable'}
                        ]
                    })
                },
                {
                    fieldLabel: '金额',
                    xtype: 'numberfield',
                    name: 'money'
                }, {
                    fieldLabel: '数量',
                    xtype: 'numberfield',
                    value: 0,
                    name: 'number',
                    allowBlank: false
                }, {
                    fieldLabel: '使用下限',
                    xtype: 'numberfield',
                    name: 'lowerLimitMoney',
                },
                {
                    fieldLabel: '截止日期',
                    xtype: 'datetimefield',
                    format: 'Y-m-d H:i:s',
                    name: 'deadLineStr'
                }, {
                    fieldLabel: '有效期',
                    xtype: 'numberfield',
                    name: 'periodOfValidity'
                }, {
                    fieldLabel: 'icon(奖品view标识)',
                    name: 'icon'
                },
                {
                    xtype: 'textareafield',
                    grow: true,
                    name: 'description',
                    fieldLabel: '优惠券描述',
                    height: 100,
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