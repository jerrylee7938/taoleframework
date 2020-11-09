/**
 * 外部推荐渠道管理
 */
Ext.define("Taole.marketing.view.ChannelMain", {
    extend: 'Ext.Panel',
    alias: 'widget.channelMain',
    layout: {
        type: 'border',
        padding: 5
    },
    isContract: null,
    initComponent: function () {
        var store = Ext.create("Taole.marketing.store.ChannelST");
        this.title = "渠道管理";
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
                                fieldLabel: '渠道名称',
                                xtype: 'textfield',
                                name: 'name'
                            },
                            {
                                fieldLabel: '渠道key',
                                xtype: 'textfield',
                                name: 'key'
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
                    },{//--------------------------------第2行
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
                                fieldLabel: '渠道类型',
                                xtype: 'combobox',
                                name: 'type',
                                editable: false,
                                emptyText: '请选择渠道类型',
                                store: Ext.create('Taole.appManager.appVer.store.ChannelTypeStore'),
            					displayField: 'name',
            					valueField: 'id'
                                
                            },
                            {
                                xtype: "combobox",
                                editable: false,
                                name: 'status',
                                emptyText: '请选择渠道状态',
                                fieldLabel: '渠道状态',
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
                        header: '渠道名称', dataIndex: 'name', align: 'center', width: 140,
                        renderer: function (val, meta, record) {
                            //grid添加链接
                            if (!window.viewUser) {
                                window.viewUser = function (id, isView) {
                                    Ext.create('Taole.marketing.controller.ChannelCtr').view(id);
                                }
                            }
                            return '<a href="javascript:viewUser(\'' + record.data.id + '\')">' + val + '</a>';
                        }
                    },
                    {header: '状态', dataIndex: 'status', align: 'center', width: 120, renderer: function (val, meta, record) {
                    	if(val=='enable'){
                    		return '可用';
                    	}else if(val=='disable'){
                    		return '不可用';
                    	}
                    }},
                    {header: '渠道类型', dataIndex: 'type', align: 'center', width: 120, renderer: function (val, meta, record) {
                    	if(val=='1'){
                    		return 'Andriod';
                    	}else if(val=='2'){
                    		return 'iOS';
                    	}else if(val=='3'){
                    		return 'Andriod&iOS';
                    	}
                    }},
                    {header: '渠道', dataIndex: 'key', align: 'center', width: 120},
                    {header: '是否需要上传APP安装包', dataIndex: 'isUploadPackage', align: 'center', width: 140, renderer: function (val, meta, record) {
                    	if(val=='0'){
                    		return '否';
                    	}else if(val=='1'){
                    		return '是';
                    	}
                    }},
                    {header: '渠道介质类型', dataIndex: 'loadType', align: 'center', width: 120, renderer: function (val, meta, record) {
                    	if(val=='1'){
                    		return 'App';
                    	}else if(val=='2'){
                    		return 'website';
                    	}else if(val =='3'){
                    		return 'App&website';
                    	}
                    }},
                    {header: '上传地址', dataIndex: 'downloadUrl', align: 'center', width: 300},
                    {header: '详情', dataIndex: 'description', align: 'center', width: 340}

                ],
                plugins: [
                    {
                        ptype: 'datatip',
                        tpl: '详情：{description}'
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
                    {text: '增加渠道', action: 'add', pressed: true},
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