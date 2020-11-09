/**
 * 中奖明细
 */
Ext.define("Taole.system.marketing.view.LottoryDrawMain", {
    extend: 'Ext.Panel',
    alias: 'widget.lottoryDrawMain',
    layout: {
        type: 'border',
        padding: 5
    },
    isContract: null,
    initComponent: function () {
        var store = Ext.create("Taole.system.marketing.store.LottoryDrawST");
        this.title = "中奖明细管理";
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
                                fieldLabel: '奖品编号',
                                xtype: 'textfield',
                                name: 'couponId'
                            },
                            {
                                fieldLabel: '奖品名称',
                                xtype: 'textfield',
                                name: 'couponName'
                            },
                            {
                                fieldLabel: '奖品类别',
                                xtype: 'combo',
                                name: 'couponType',
                                store: Ext.create("Taole.system.marketing.store.LottoryTypeST"),
                                displayField: 'value',
                                valueField: 'key'
                            },
                            {
                                xtype: "combobox",
                                editable: false,
                                emptyText: '请选择',
                                name: 'isWinner',
                                fieldLabel: '是否中奖',
                                displayField: 'name',
                                valueField: 'value',
                                queryMode: 'local',
                                store: Ext.create('Ext.data.Store', {
                                    fields: ['name', 'value'],
                                    data: [
                                        {name: '是', value: 'yes'},
                                        {name: '否', value: 'no'}
                                    ]
                                })

                            }

                        ]
                    },{
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
                                fieldLabel: '抽奖时间',
                                xtype:'datetimefield',
                                format: 'Y-m-d H:i:s',
                                name: 'timeStart'
                            },
                            {
                                fieldLabel: '至',
                                labelWidth: 20,
                                xtype:'datetimefield',
                                format: 'Y-m-d H:i:s',
                                width: 210,
                                name: 'timeEnd'
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
                        header: '用户名称', dataIndex: 'realName', align: 'center', width: 140,
                        renderer: function (val, meta, record) {
                            //grid添加链接
                            if (!window.viewUser) {
                                window.viewUser = function (userId, isView) {
                                    Ext.create('Taole.loan.invest.controller.InvestUserCtrl').view(userId, isView);
                                }
                            }
                            return '<a href="javascript:viewUser(\'' + record.data.userId + '\',true)">' + val + '</a>';
                        }
                    },
                    {header: '手机号', dataIndex: 'mobNum', align: 'center', width: 120},
                    {header: '奖品名称', dataIndex: 'couponName', align: 'center', width: 120},
                    {header: '奖品类别', dataIndex: 'couponType', align: 'center', width: 140},
                    {header: '是否中奖', dataIndex: 'statusCoupon', align: 'center', width: 140},
                    {header: '抽奖时间', dataIndex: 'lottoryDateStr', align: 'center', width: 140},
                    {header: '奖品描述', dataIndex: 'description', align: 'center', width: 140},
                    {header: '奖品编号', dataIndex: 'couponId', align: 'center', width: 120}

                ],
                plugins: [
                    {
                        ptype: 'datatip',
                        tpl: '奖品描述：{description}'
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
                {text: '导出', action: 'export', pressed: true}
                    /*				{text: '新增', action: 'add', pressed: true},
                     {text: '修改', action: 'update', pressed: true},
                     {text: '删除', action: 'delete', pressed: true}*/
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