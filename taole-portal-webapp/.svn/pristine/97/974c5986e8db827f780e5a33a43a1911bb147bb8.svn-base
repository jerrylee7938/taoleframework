Ext.onReady(function(){
	Ext.QuickTips.init();
	var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
	
	/**创建查 询表单*/
	function createQueryForm(){

		var query_form = Ext.create("Ext.form.Panel",{
			id:'pro_query_form',
			region:'north',
			height:70,
			layout:'column',
			frame:true,
			items:[{
				columnWidth: 0.20,
				baseCls:'x-plain',
				xtype:'form',
				defaults:{
					labelAlign:'right',
					labelWidth:80
				},
				items:[{
					xtype:'textfield',
					id:'mobileNum',
					name:'mobileNum',
					fieldLabel:'手机号',
					anchor:'100%'
				},{
					xtype:'textfield',	
					id:'Content',
					name:'Content',
					fieldLabel:'短信内容',
					anchor:'100%'
				}]
			},{
				columnWidth: 0.2,
				baseCls:'x-plain',
				xtype:'form',
				defaults:{
					labelAlign:'right',
					labelWidth:80
				},
				items:[{					
					id:'Status',
					name:'Status',
					fieldLabel:'发送状态',
					anchor:'100%',
					xtype:'combobox',
					displayField:'name',
					valueField:'value',
					store: Ext.create('Ext.data.Store',{
						autoLoad:true,
						fields:['value','name'],
						proxy: { 
							type: 'ajax', 
							url:$service.portal+'/tk.Dictionary/SMS_STATUS_CODE/childAllNodes',						
							actionMethods : 'GET', 
							reader: { 
								 type: 'json',
								 
								} 
							},
					})
				},{					
					id:'mtType',
					name:'mtType',
					fieldLabel:'短信类型',
					anchor:'100%',
					xtype:'combobox',
					displayField:'name',
					valueField:'value',
					store: Ext.create('Ext.data.Store',{
						autoLoad:true,
						fields:['value','name'],
						proxy: { 
							type: 'ajax', 
							url: $service.portal+'/tk.Dictionary/MT_TYPE_CODE/childNodes',						
							actionMethods : 'GET', 
							reader: { 
								 type: 'json',
								 root: 'childNodes',
								} 
							},
					})
				}]
			},{

				columnWidth: 0.2,
				baseCls:'x-plain',
				xtype:'form',
				defaults:{
					labelAlign:'right',
					labelWidth:80
				},
				items:[{					
					fieldLabel:'发送时间从',
					anchor:'100%',
					xtype:'datetimefield',
					id:'sendTimeStart',
					name:'sendTimeStart',
					format:'Y-m-d H:i:s'
				},{
					id:'Type',
					name:'type',
					fieldLabel:'发送方式',
					anchor:'100%',
					xtype:'combobox',
					displayField:'name',
					valueField:'value',
					store: Ext.create('Ext.data.Store',{
						autoLoad:true,
						fields:['value','name'],
						data:[{"name":"正式发送","value":'1'},{"name":"模拟发送","value":'2'}],
					})
				}]
			
			},{
				columnWidth: 0.15,
				baseCls:'x-plain',
				xtype:'form',
				defaults:{
					labelAlign:'right',
					labelWidth:20
				},
				items:[{					
					fieldLabel:'至',
					anchor:'90%',
					xtype:'datetimefield',
					name:"sendTimeEnd",
					id:'sendTimeEnd',
					format:'Y-m-d H:i:s'
				}]
			
			},{
				columnWidth: 0.25,
				baseCls:'x-plain',			
				items:[{
					xtype:'button',
					width:60,
					x:10,
					text:'查询',
					handler:function(){
						var Type = Ext.getCmp('Type').getValue();
						var Status = Ext.getCmp('Status').getValue();
						var Content = Ext.getCmp('Content').getValue();
						var mobileNum = Ext.getCmp("mobileNum").getValue();
						var mtType = Ext.getCmp("mtType").getValue();
						var sendTimeStart = Ext.getCmp('sendTimeStart').getValue();
						var sendTimeEnd = Ext.getCmp('sendTimeEnd').getValue();
						if(sendTimeStart){
							sendTimeStart = Ext.Date.format(new Date(Ext.getCmp("sendTimeStart").getValue()), "Y-m-d H:i:s");
						}
						if(sendTimeEnd){
							sendTimeEnd = Ext.Date.format(new Date(Ext.getCmp("sendTimeEnd").getValue()), "Y-m-d H:i:s");
						}
						
						var projectGrid = Ext.getCmp("project_grid");
						var projectStore = projectGrid.getStore();
					//	projectStore.loadPage(1);
						projectStore.proxy.url = $service.portal+'/sms.SMMT/collection/query';
						projectStore.loadPage(1,{
								params:{ 
									content:Content,
									mobileNum:mobileNum,
									type:Type,
									status:Status,
								mtType:mtType,
								sendTimeStart:sendTimeStart,
								sendTimeEnd:sendTimeEnd
							        }
						});
					}
				},{				
					xtype:'button',
					width:60,
					x:20,
					text:'重置',
					handler:function(){
						var form = Ext.getCmp("pro_query_form").getForm();
						form.reset();
					}
				}]
			}]
		});
		
		return query_form;
	}	
			
	/**创建项目网格*/
	function createProjectGrid(){
		//定义model
		Ext.define('projectModel', {
	        extend: 'Ext.data.Model',
	        fields:['smsId','mobileNum','sendNum','content','statusName','type',"senderName","sendTime",'createTime','channel','mtTypeName','description']
	    });
		//定义store: 菜单表格的数据源
		var gridStore = Ext.create('Ext.data.Store', {
	        model: 'projectModel',//数据模型
	        remoteSort: true,
	        autoLoad:false,
			pageSize:20,
		   proxy: {
	            type: 'ajax',
	            actionMethods : 'post', 
	            url:$service.portal+'/sms.SMMT/collection/query',
	            reader: {
	            
	                root: 'result.items',
	                totalProperty: 'result.total'
	            },
	            simpleSortMode: true
	        },
	        listeners:{
	        	beforeload:function(){
	        		var form = Ext.getCmp("pro_query_form");
	        		appendParam(form, gridStore);
		        }
	        }
	    });
		gridStore.load({
			params:{
				start:0,
				limit:20
			},
		});
	    Ext.ClassManager.setAlias('Ext.selection.CheckboxModel','selection.checkboxmodel'); 
		//定义grid
		var projectGrid = Ext.create('Ext.grid.Panel',{
			region:	'center',
			id:'project_grid',
			store:	gridStore,
			columnLines: true,//各列之间是否有分割线
			multiSelect: true,//是否允许多选
			columns:[{
	            text: "SMSID",
	            dataIndex: 'smsId',
	            align:'center',
	            width:100
	        },{
	            text: "手机号码",
	            dataIndex: 'mobileNum',
	            align:'center',
	            width:100
	           
	        },{
	            text: "短信内容",
	            align:'center',
	            dataIndex: 'content',
	            width:350
	        },{
	            text: "发送方式",
	            align:'center',
	            dataIndex: 'type',
	            width:100,
	            renderer: function(v,p,r){
	            	if(v=="1"){
	            		return "正式发送";
	            	}else if(v=="2"){
	            		return "模拟发送";
	            	} 	
	           	}
	        },{
	            text: "短信类型",
	            align:'center',
	            dataIndex: 'mtTypeName',
	            width:100
	        
	        },{
	            text: "发送状态",
	            align:'center',
	            dataIndex: 'statusName',
	            width:100,	           
	        },{
	            text: "发送通道",
	            align:'center',
	            dataIndex: 'channel',
	            width:100
	        },{
	            text: "应用名称",
	            align:'center',
	            dataIndex: 'senderName',
	            width:100
	        
	        },{
	            text: "发送时间",
	            align:'center',
	            dataIndex: 'sendTime',
	            width:150,
	            xtype: 'datecolumn',  
	            format:'Y-m-d H:i:s'
	        },{
	            text: "描述",
	            align:'center',
	            dataIndex: 'description',
	            width:150
	        }],
	        selModel: { //选中模型 
	        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
	        },
	        viewConfig:{  
                enableTextSelection:true  
            }, 
		    //增删改查
	        tbar: [],			
	        bbar:{
	        	xtype: 'pagingtoolbar',
	        	store: gridStore,
				displayInfo: true
	        }
		});
		
		return projectGrid;
	}
	
	
	Ext.create('Ext.Viewport', {
        layout: {
            type: 'border',
            padding: 5
        },
        defaults: {
            split: true
        },
        items: [createQueryForm(),createProjectGrid()]
    });
});