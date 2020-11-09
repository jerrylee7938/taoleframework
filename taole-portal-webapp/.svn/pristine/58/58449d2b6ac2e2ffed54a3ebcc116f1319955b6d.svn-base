/**
 * 外部推荐渠道管理
 */
Ext.define("Taole.system.marketing.view.CouponMain", {
    extend: 'Ext.Panel',
    alias: 'widget.couponMain',
    layout: {
        type: 'border',
        padding: 5
    },
    isContract: null,
    initComponent: function () {
        var store = Ext.create("Taole.system.marketing.store.CouponST");
        this.title = "优惠券管理";
        this.items = [
            {
                //-----------------------------------------------表单
                xtype: 'form',
                region: 'north',
                frame: true,
                defaults: {
                    xtype: 'panel',
                    baseCls: 'x-plain',
                    frame: true
                },
                items: [
                    {//--------------------------------第1行
                        layout: 'column',
                        frame: true,
                        baseCls: 'x-plain',
                        style: 'margin-top:5px;',
                        defaults: {
                            labelAlign: 'right',
                            labelWidth: 80,
                            width: 260
                        },
                        items: [
                            {
                                fieldLabel: '优惠券名称',
                                xtype: 'textfield',
                                name: 'name'
                            },
                            {
                                fieldLabel: '优惠券类别',
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
                                queryMode: 'local',
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
                                text: '查询',
                                xtype: 'button',
                                action: 'query',
                                style: 'margin-left:10px;',
                                width: 80
                            },
                            {
                                text: '重置',
                                xtype: 'button',
                                action: 'reset',
                                style: 'margin-left:10px;',
                                width: 80
                            }
                        ]
                    }

                ]
            },
            {//---------------------------------------------表格
                xtype: 'grid',
                region: 'center',
                style: 'margin-top:3px;',
                store: store,
                columns: [
                    {header: '序号', xtype: 'rownumberer', width: 40, sortable: false, align: 'center'},
                    {
                        header: '优惠券名称', dataIndex: 'name', align: 'center', width: 140,
                        renderer: function (val, meta, record) {
                            //grid添加链接
                            if (!window.viewUser) {
                                window.viewUser = function (id, isView) {
                                    Ext.create('Taole.system.marketing.controller.CouponCtr').view(id);
                                }
                            }
                            return '<a href="javascript:viewUser(\'' + record.data.id + '\')">' + val + '</a>';
                        }
                    },
                    {header: '优惠券类型', dataIndex: 'typeStr', align: 'center', width: 120},
                    {header: '状态', dataIndex: 'statusStr', align: 'center', width: 120},
                    {header: '金额(物品)', dataIndex: 'money', align: 'center', width: 120},
                    {header: '使用下限', dataIndex: 'lowerLimitMoney', align: 'center', width: 120},
                    {header: '有效期', dataIndex: 'periodOfValidity', align: 'center', width: 120},
                    {header: '数量', dataIndex: 'number', align: 'center', width: 120},
                    {header: '截止日期', dataIndex: 'deadLineStr', align: 'center', width: 120},
                    {header: '奖品view标识', dataIndex: 'icon', align: 'center', width: 120},
                    {header: '优惠券描述', dataIndex: 'description', align: 'center', width: 120},

                ],
                plugins: [
                    {
                        ptype: 'datatip',
                        tpl: '优惠券描述：{description}'
                    }
                ],
                listeners: {
                    beforeshowtip: function (grid, tip, data) {
                        var cellNode = tip.triggerEvent.getTarget(tip.view.getCellSelector());
                        if (cellNode) {
                            data.colName = tip.view.headerCt.columnManager.getHeaderAtIndex(cellNode.cellIndex).text;
                        }
                    }
                },
                tbar: [
                    {text: '添加优惠券', action: 'add', pressed: true},
                    {text: '编辑', action: 'update', pressed: true}
                ],
                bbar: {
                    xtype: 'pagingtoolbar',
                    store: store,
                    displayInfo: true
                },
                columnLines: true,
                selModel: {
                    selType: 'checkboxmodel'
                }
            }
        ]
        this.callParent(arguments);
    }
});