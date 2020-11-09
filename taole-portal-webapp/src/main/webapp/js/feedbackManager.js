Ext.onReady(function(){
	Ext.QuickTips.init();
	var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';	
	/**创建查 询表单*/
	function createQueryForm(){

		var query_form = Ext.create("Ext.form.Panel",{
			id:'pro_query_form',
			region:'north',
			height:60,
			layout:'column',
			frame:true,
			items:[{
				columnWidth: 0.3,
				baseCls:'x-plain',
				xtype:'form',
				defaults:{
					labelAlign:'right',
					labelWidth:60
				},
				layout:'anchor',
				items:[{

					y:30,
					xtype:'combobox',
					id:'productName',
					name:'appId',
					fieldLabel:'应用名称',
					anchor:'90%',
					displayField:'name',
					valueField:'id',
					store: Ext.create('Ext.data.Store',{
						autoLoad:true,
						fields:['id','name'],
						proxy: { 
							type: 'ajax', 
							url: $service.portal+'/us.App/collection/query?type=App',
							actionMethods : 'GET', 
							reader: { 
								type: 'json',
								root:'items',
								
							} 
						},
					}),
				
				},{
					xtype:'textfield',
					id:'mobile',
					name:'userMobile',
					fieldLabel:'手机号',
					anchor:'90%'
				}]
			},{
				columnWidth: 0.3,
				baseCls:'x-plain',
				xtype:'form',
				defaults:{
					labelAlign:'right',
					labelWidth:40
				},
				items:[{
					xtype:'textfield',
					id:'userName',
					name:'userName',
					fieldLabel:'姓名',
					anchor:'90%'
				},{
					xtype:'textfield',
					id:'keyWord',
					name:'content',
					fieldLabel:'内容',
					anchor:'90%'
				}]
			},{
				columnWidth: 0.3,
				baseCls:'x-plain',
				xtype:'form',
				defaults:{
					labelAlign:'right',
					labelWidth:60
				},
				layout:'anchor',
				items:[{
					xtype:'button',
					width:80,
					margins:'0 20 0 0',
					text:'查询',
					handler:function(){
						var appId =  Ext.getCmp('productName').getValue();
						var type =  Ext.getCmp('typeName').getValue();
						var userMobile =  Ext.getCmp('mobile').getValue();
						// name = encodeURI(encodeURI(name))
						var userName = Ext.getCmp('userName').getValue();
						var content = Ext.getCmp('keyWord').getValue();
						var projectGrid = Ext.getCmp("project_grid");
						var projectStore = projectGrid.getStore();
					//	projectStore.loadPage(1);
							projectStore.proxy.url = $service.portal+'/us.AppFeedback/collection/query';
					
				//		alert(projectStore.proxy.url);
						projectStore.loadPage(1,{
								params:{ 
									appId:appId,
									userMobile:userMobile,
									userName:userName,
									content:content,
									type:type
							        }
						});
					}
				},{
					xtype:'button',
					width:80,
					x:10,
					margins:'0 0 0 20',
					text:'重置',
					handler:function(){
						var form = Ext.getCmp("pro_query_form").getForm();
						form.reset();
					}
				},{
					xtype:'combobox',
					id:'typeName',
					name:'type',
					fieldLabel:'内容类型',
					anchor:'90%',
					style:'marginTop:5px;',
					displayField:'name',
					valueField:'value',
					store: Ext.create('Ext.data.Store',{
						autoLoad:true,
						fields:['value','name'],
						proxy: { 
							type: 'ajax', 
							url: $service.portal+'/tk.Dictionary/8dc87df4ead24778a214cf1f4de713ff/childAllNodes',
							actionMethods : 'GET', 
							reader: { 
								type: 'json',
							} 
						},
					}),}]
			}]
		});
		
		return query_form;
	}
	
	
	
	/**创建项目网格*/
	function createProjectGrid(){
		
		//定义model
		Ext.define('projectModel', {
	        extend: 'Ext.data.Model',
	        fields:['id', 'userName','typeName','userId','content','createTime','userMobile','appName','isBase64Encode', 'images']
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
	            url:$service.portal+'/us.AppFeedback/collection/query',
	            reader: {
	            
	                root: 'items',
	                totalProperty: 'total'
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
			//grid可复制
			viewConfig:{  
			   enableTextSelection:true  
			},
			columns:[
			         {
	            text: "内容类型",
	            dataIndex: 'typeName',
	            align:'center',
	            width:100
	        },{
	            text: "姓名",
	            dataIndex: 'userName',
	            align:'center',
	            width:100,
	            renderer: function(val, meta, record){
	            	//<a href="javascript:download(\''+ record.data.userId+'\')">查看二维码</a>
	            	if(val)
	            		return val;
	            	//	return "<a href='javascript:void(0);'>"+val+"</a>";
	            }
	           
	        },{
	            text: "手机号",
	            dataIndex: 'userMobile',
	            align:'center',
	            width:150
	           
	        },{
	            text: "应用名称",
	            align:'center',
	            dataIndex: 'appName',
	            width:150
	        },{
	            text: "内容",
	            align:'center',
	            dataIndex: 'content',
	            width:350,
	            renderer: function(val, meta, record){
	    			var words = CryptoJS.enc.Base64.parse(val);
	    			var isBase64Encode = record.data.isBase64Encode;
	    			var content = val;
	    			if(isBase64Encode == 1){
	    				content = words.toString(CryptoJS.enc.Utf8);
	    			}
	    			return content;
	            }
	        },{
	            text: "相关图片",
	            align:'center',
	            width:350,
	            renderer: function(val, meta, record){
	    			var images = record.data.images;
	    			var content = "";
	    			for(var i=0; i<images.length; i++){
	    				content = content + '<a href="'+images[i]+'" target="_blank"><img src="'+images[i]+'"  style="width:70px;height:70px;"/></a>';
	    			}
	    			return content;
	            }
	        },{
	            text: "创建时间",
	            align:'center',
	            dataIndex: 'createTime',
	            width:200,
	            xtype: 'datecolumn',  
	            format:'Y-m-d H:i:s'
	       }],
	        selModel: { //选中模型 
	        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
	        },
	        listeners: {  
				'cellclick':function(grid,rowIndex,columnIndex,e ){
					var selRows = grid.getSelectionModel().getSelection();
					var update_btn = Ext.getCmp('update_btn');
					var delete_btn = Ext.getCmp('delete_btn');
					if(selRows.length == 1){
					/*	var status = selRows[0].data.status;
						if(status == PROJECT_STATUS_UNSTARTED){
							delete_btn.setDisabled(false);
						}else{
							delete_btn.setDisabled(true);
						}
						if(status == PROJECT_STATUS_FINISHED){
							update_btn.setDisabled(true);
						}else{
							update_btn.setDisabled(false);
						}  */
					}else{
						var isDisabled = false;
						for(var i=0; i<selRows.length; i++){
						
					/*		
							var status = selRows[i].data.status;
							if(status != PROJECT_STATUS_UNSTARTED){
								isDisabled = true;
								delete_btn.setDisabled(isDisabled);
								return ;
							}
							*/
						}
						delete_btn.setDisabled(isDisabled);
					}
				}   
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