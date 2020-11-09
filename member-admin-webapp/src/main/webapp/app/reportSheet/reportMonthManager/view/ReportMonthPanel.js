/**
 * 月报表管理
 */
Ext.define('Ext.form.field.Month', {
    extend: 'Ext.form.field.Date',
    alias: 'widget.monthfield',
    requires: ['Ext.picker.Month'],
    alternateClassName: ['Ext.form.MonthField', 'Ext.form.Month'],
    selectMonth: null,
    createPicker: function () {
        var me = this,
            format = Ext.String.format,
            pickerConfig;
        pickerConfig = {
            pickerField: me,
            ownerCmp: me,
            renderTo: document.body,
            floating: true,
            hidden: true,
            focusOnShow: true,
            minDate: me.minValue,
            maxDate: me.maxValue,
            disabledDatesRE: me.disabledDatesRE,
            disabledDatesText: me.disabledDatesText,
            disabledDays: me.disabledDays,
            disabledDaysText: me.disabledDaysText,
            format: me.format,
            showToday: me.showToday,
            startDay: me.startDay,
            minText: format(me.minText, me.formatDate(me.minValue)),
            maxText: format(me.maxText, me.formatDate(me.maxValue)),
            listeners: {
                select: { scope: me, fn: me.onSelect },
                monthdblclick: { scope: me, fn: me.onOKClick },
                yeardblclick: { scope: me, fn: me.onOKClick },
                OkClick: { scope: me, fn: me.onOKClick },
                CancelClick: { scope: me, fn: me.onCancelClick }
            },
            keyNavConfig: {
                esc: function () {
                    me.collapse();
                }
            }
        };
        if (Ext.isChrome) {
            me.originalCollapse = me.collapse;
            pickerConfig.listeners.boxready = {
                fn: function () {
                    this.picker.el.on({
                        mousedown: function () {
                            this.collapse = Ext.emptyFn;
                        },
                        mouseup: function () {
                            this.collapse = this.originalCollapse;
                        },
                        scope: this
                    });
                },
                scope: me,
                single: true
            }
        }
        return Ext.create('Ext.picker.Month', pickerConfig);
    },
    onCancelClick: function () {
        var me = this;
        me.selectMonth = null;
        me.collapse();
    },
    onOKClick: function () {
        var me = this;
        if (me.selectMonth) {
            me.setValue(me.selectMonth);
            me.fireEvent('select', me, me.selectMonth);
        }
        me.collapse();
    },
    onSelect: function (m, d) {
        var me = this;
        me.selectMonth = new Date((d[0] + 1) + '/1/' + d[1]);
    }
});
Ext.define("Taole.reportSheet.reportMonthManager.view.ReportMonthPanel", {
	extend: 'Ext.Panel',
    alias : 'widget.reportMonthPanel',    
    layout: {
        type: 'border',
        padding: 5
    },
    initComponent: function() {
		var store = Ext.create('Taole.reportSheet.reportMonthManager.store.ReportMonthStore',{
		});	
		var firstCkict=true;
		this.items = [{//-----------------------------------------------表单
			xtype: 'form',
			region: 'north',
			frame: true,
			height: 40,
			defaults:{
				xtype: 'panel',
				baseCls:'x-plain',
				frame: true
			},
			items:[
		   	{//--------------------------------第1行
				layout: 'column',
				frame: true,
				baseCls: 'x-plain',
				style: 'margin-top:5px;',
				defaults:{
					labelAlign: 'right',
					labelWidth: 80,
					width: 230
				},
				items:[{
					fieldLabel: '店名',
					xtype: 'combobox',
					name: 'shopId',
					id:'shopId',
					displayField:'name',
					valueField:'shopId',
			        editable:false,
					store:Ext.create('Taole.store.storeManager.store.UserStoreStore',{
						autoLoad: true
					}),
					listeners: { //监听 
						 render : function(combo) {//渲染 
					           combo.getStore().on("load", function(s, r, o) { 
					           combo.setValue(r[0].get('shopId'));//第一个值 
					           var dimens =Ext.getCmp("dimens").getValue();
					           if(dimens && firstCkict){
					        	   firstCkict =false;
					        	   Ext.getCmp("form-query").fireEvent('click');
					           }  
					      }); 
					   } 
					} 	  
				},
				{
					fieldLabel: '日期',
					xtype: 'monthfield',
					format: 'Y-m',
					name:'statDate',
					value:new Date(),
				},{
					fieldLabel: '汇总方式',
					xtype: 'combobox',
					name: 'dimens',
					id:'dimens',
					displayField:'name',
					valueField:'value',
					editable:false,
					store: getDicStore("d6c252cf17d241a7ac232ebc9493b980",URL_DICTIONARY_MEMBER),	
					listeners: { //监听 
					   render : function(combo) {//渲染 
					           combo.getStore().on("load", function(s, r, o) { 
					           combo.setValue(r[0].get('value'));//第一个值 
					           var shop =Ext.getCmp("shopId").getValue();
					           if(shop && firstCkict){
					        	   firstCkict =false;
					        	   Ext.getCmp("form-query").fireEvent('click');
					           }   
					      }); 
					   } 
					} 
				},
				{
					text: '查询', 
					xtype: 'button', 
					action: 'query', 
					style: 'margin-left:10px;',
					id:'form-query',
					width: 60
				},
//				{
//					text: '重置', 
//					xtype: 'button', 
//					action: 'reset',
//					style: 'margin-left:10px;',
//					width: 60
//					}
				]
			}]
		},{//---------------------------------------------表格
			xtype: 'grid',
			region:'center',
			store: store,
			columns: [
			    {header: '序号',align: 'center',xtype:'rownumberer', width:35},
			    {header: '日期', dataIndex: 'statDate', align: 'center', width:150},
			    {header: '店面', dataIndex: 'shopName', align: 'center', width:150},
				{header: '营业总额(元)', dataIndex: 'statMoney', align: 'center', width:150},
				{header: '划卡总次数', dataIndex: 'statAmount', align: 'center', width:150},
				{header: '汇总方式', dataIndex: 'objectName', align: 'center', width:150},		
			],
			 viewConfig:{  
	                enableTextSelection:true  
	            }, 
		    tbar : [
		        	{text:'导出excel',action:'export', pressed:true},'-',
		        	{text:'打印',action:'print', pressed:true}
		    ],
		    bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: store,
				displayInfo: true
	        },
		    columnLines: true,
		    selModel:{
		    	selType : 'checkboxmodel'
		    }
		}]		
        this.callParent(arguments);
    }
});