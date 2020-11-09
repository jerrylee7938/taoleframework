<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
 <html>
<head>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<style type="text/css">
		.delCls{
			width:70px;
			height:25px;
		}
		#module_panel td {
            padding:2px;
        }
	</style>
</head>
<body>
<script type="text/javascript">
	
	var appMap = [];
	var appStatusData;
	var appTypeData;
	$.ajax({
		type:'GET',
		url:$service.portal+'/fw.System/com.taole.usersystem.domain.Application$Type/enums',
		dataType:'json',
		async:false,
		success:function(data){
			appTypeData = data;
			if(data && data.length > 0){
				for(var i = 0; i < data.length; i++){
					var item = data[i];
					appMap[item.name] = item.local;
				}
			}
		},
		failure:function(){
			
		}
	});
	
	$.ajax({
		type:'GET',
		url:$service.portal+'/fw.System/com.taole.usersystem.domain.Application$Status/enums',
		dataType:'json',
		async:false,
		success:function(data){
			appStatusData = data;
		},
		failure:function(){
			
		}
	});
	
	Ext.onReady(function(){
		var entityId = null;
		var entityName = 'us.App';
		var entityAction = 'create';
		var app_store = null;
		
		//新增应用
		window.addOrUpdateAppInfo = function(id){
			var disabled = false;
			if (id) {
				entityId = id;
				disabled = false;
			} else {
				entityId = null;
				disabled = true;
			}
			var app_win = Ext.getCmp("app_win");
			var title = id==''?'新增':'修改';
			if(!app_win){
				app_win = Ext.create("Ext.window.Window",{
					title:title+'应用信息',
					height:400,
					id:'app_win',
					width:500,
					modal:true,
					layout:'fit',
					border:false,
					resizable:false,
					items:[{
						xtype:'form',
						padding:'5px 5px',
						layout:'anchor',
						id:'app_basic_From',
						border:false,
						style:{
							'border':'0px'
						},
						defaults: { // defaults 将会应用所有的子组件上,而不是父容器
							labelWidth:120,
						},
						frame:true,
						items:[{
							xtype:'combobox',
		        			name:'type',
		        			labelAlign:'right',
		        			margin:'5px 0px 5px 0px',
		        			anchor:'90%',
		        			displayField: 'local',
						    valueField: 'name',
						    store:Ext.create("Ext.data.Store",{
						    	fields: ['local', 'name'],
						    	data: appTypeData
						    }),
		        			value:'Service',
		        			disabled:false,
		        			style:{
								'opacity':1
							},
		        			fieldLabel:'应用类型'
						},{
		        			xtype:'combobox',
		        			name:'status',
		        			labelAlign:'right',
		        			margin:'5px 0px 5px 0px',
		        			anchor:'90%',
		        			value:'Ready',
		        			disabled:disabled,
		        			style:{
								'opacity':1
							},
							displayField: 'local',
						    valueField: 'name',
						    store:Ext.create("Ext.data.Store",{
						    	fields: ['local', 'name'],
						    	data: appStatusData
						    }),
							listeners:{
								expand:function(combo){
									var store = combo.getStore();
									var form = Ext.getCmp("app_basic_From").getForm();
									
									var type = form.findField("type").getValue();
									store.each(function(rec){
										if(type == 'Portal' && rec.data.local == '废弃'){
											store.remove(rec);
										}
									})
								}
							},
		        			fieldLabel:'应用状态'
		        		},{
							xtype:'datefield',
							fieldLabel:'上线日期',
							format:'Y-m-d',
							name:'onlineDate',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'datefield',
							fieldLabel:'下线日期',
							name:'offlineDate',
							format:'Y-m-d',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'textfield',
							fieldLabel:'<font style="color:red">*</font>应用名称',
							name:'name',
							allowBlank:false,
							blankText:'请输入应用名称',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'textarea',
							fieldLabel:'应用描述',
							name:'description',
							labelAlign:'right',
							height:60,
							anchor:'90%'
						},{
							xtype:'textfield',
							fieldLabel:'正式环境域名地址',
							labelAlign:'right',
							id:'website',
							name:'website',
							anchor:'90%'
						},{
							xtype:'textfield',
							fieldLabel:'预发布环境域名地址',
							labelAlign:'right',
							id:'websitePre',
							name:'websitePre',
							anchor:'90%'
						},{
							xtype:'textfield',
							fieldLabel:'测试环境域名地址',
							labelAlign:'right',
							id:'websiteTest',
							name:'websiteTest',
							anchor:'90%'
						},{
							xtype:'textfield',
							fieldLabel:'开发环境域名地址',
							labelAlign:'right',
							id:'websiteDev',
							name:'websiteDev',
							anchor:'90%'
						}]	
					}],
					buttonAlign:'center',
					buttons:[{
						text:'保存',
						handler:function(){
							var form = app_win.down("form");
							if(form.getForm().isValid()){
								var data = Ext.ux.FormUtils.getDataObject(form);
								if (entityId) {
									data.id = entityId;
								}
								data.entityName = "us.Application";
								
								Ext.Ajax.request({
									method: 'post',
									url: getRestAction(entityAction,entityId),
									jsonData: data,
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'保存失败：' + r.description);
										} else {
											app_win.destroy();
											refresh();
										}
									},
									failure:function(resp,opts){
										Ext.Msg.alert("提示",'保存失败：' + resp.responseText);
									}
								});
								
							}
						}
					},{
						text:'取消',
						handler:function(){
							app_win.destroy();
						}
					}]
				});
			}
			app_win.show();
			
			if(entityId){
				entityAction = 'update';
				Ext.Ajax.request({
					method: 'post',
					url: getRestAction('retrieve',entityId),
					success:function(resp, opts){
						var r = eval("(" + resp.responseText + ")");
						if (r) {
							Ext.ux.FormUtils.updateForm(app_win.down('form'), r);
						} else {
							Ext.Msg.alert("提示",'加载失败');
						}
					}
				});
			} else {
				app_win.down('form').getForm().reset();
				entityAction = 'create';
			}
		};
		getRestAction = function(action, resource) {
			if (resource == null || resource == '') {
				resource = 'collection';
			}
			return $service.portal + '/' + entityName + '/' + resource + '/' + action;
		};
		
		//删除
		window.deleteAppInfo = function(){
			var grid = Ext.getCmp("app_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var length = selRows.length;
			if(length >0){
				Ext.Msg.confirm("提示","确定要删除选中的应用记录?",function(btn){
					if(btn == 'yes'){
						//执行删除操作
						var url = '';
						var args ='';
						if(length >1){
							for(var i = 0; i < length; i++){
								args +='ids='+selRows[i].get('id');
								if(i < length -1){
									args +="&";
								}
							}
							url = $service.portal + '/' + entityName + '/collection/batchdelete?'+args;
						}else{
							url = $service.portal + '/' + entityName + '/' + selRows[0].get('id')+ '/delete';
						}
						Ext.Ajax.request({
							method:'post',
							url: url,
							params:{},
							success:function(resp, opts){
								var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'删除失败：' + r.description);
								} else {
									refresh();
								}
							},
							failure:function(resp,opts){
								Ext.Msg.alert("提示",'删除失败：' + resp.responseText);
							}
						});
					}
				});
			}else{
				Ext.Msg.alert("提示","请选择删除的应用记录!");
			}
		}
		var refresh = function(){
			if (!app_store.isLoading()) {
				app_store.loadPage(app_store.currentPage);
			}
		};
		
		//资源管理
		/*
			modified by zyh 2014/02/26 14:49 
			content: 修改应用类型和资源类型
		*/
		window.appResourceManager = function(){
			var app_res_win = Ext.getCmp("app_res_win");
			var grid = Ext.getCmp("app_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var appId = selRows[0].get('id');
			var appName = selRows[0].get('name');
			if(!app_res_win){
				app_res_win = Ext.create('Ext.window.Window',{
					title:'资源权限管理',
					width:880,
					height:500,
					modal:true,
					layout:'border',
					border:false,
					items:[{
						xtype:'form',
						region:'north',
						layout:'hbox',
						id:'res-search-form',
						frame:true,
						cls:'x-plain',
						items:[{
							xtype:'combobox',
							labelAlign:'right',
							labelWidth:60,
							name:'appId',
							displayField:'name',
							valueField:'id',
							mode: 'local',
							style:{'opacity':1},
					        triggerAction: 'all',
							store: Ext.create('Ext.data.Store',{
								fields:['id','name'],
								autoLoad:true,
								proxy:{
									type: 'ajax',
						            url:$service.portal+'/us.App/collection/query',
						            reader: {
						                root: 'items'
						            },
						            simpleSortMode: true
								}
							}),
							margin:'5px 0px 5px 0px',
							width:220,
							value:appId,
							disabled:true,
							editable:false,
							fieldLabel:'应用系统'
						},{
							xtype:'combobox',
							labelAlign:'right',
							displayField:'name',
							valueField:'id',
							mode: 'local',
					        triggerAction: 'all',
					        store: Ext.create('Ext.data.ArrayStore',{
								fields:['id','name'],
								proxy:{
									type: 'ajax',
						            url:$service.portal+'/tk.Dictionary/restype/childNodes'
								}
							}),
							name:'resourceType',
							labelWidth:60,
							margin:'5px 0px 5px 0px',
							width:180,
							fieldLabel:'资源类型'
						},{
							xtype:'textfield',
							labelAlign:'right',
							name:'resourceName',
							labelWidth:80,
							margin:'5px 0px 5px 0px',
							width:250,
							fieldLabel:'资源名称'
						},{
							xtype:'button',
							width:80,
		        			margin:'5px 10px 5px 10px',
		        			text:'查   询',
		        			handler:function(){
		        				var params = Ext.ux.FormUtils.getDataObject(Ext.getCmp('res-search-form'));
								var store = Ext.getCmp('res_grid').getStore();
								store.getProxy().extraParams = params;
								store.loadPage(1);
		        			}
						}]
					},createResourGrid(appId)]
				});
			}
			app_res_win.show();
		};
		
		
		//创建资源网格
		function createResourGrid(appId){
			Ext.define('resModel',{
				extend:'Ext.data.Model',
				fields:['id','name','type','resourceTypeName','uri', 'privresName','privresURL','privresID']
			});
			var res_store = Ext.create('Ext.data.Store',{
				model: 'resModel',//数据模型
		        remoteSort: true,
		        autoLoad:false,
		        pageSize:20,
		        proxy: {
		            type: 'ajax',
		            url:$service.portal+'/us.Resource/collection/query',
		            reader: {
		                root: 'items',
		                totalProperty: 'total'
		            },
		            simpleSortMode: true
		        }
			});
			res_store.load({
		    	params:{
		    		start: 0,
	        		limit: 20,
	        		appId:appId
	        	}
		    });
		    
			var res_grid = Ext.create('Ext.grid.Panel',{
				store: res_store,
				id:'res_grid',
				region:'center',
				margins:'5px 0px',
				columnLines:true,
				columns:[
					{xtype: 'rownumberer',text: '序号',align:'center',width:40},
					{text:'<center>资源名称</center>',width:200,dataIndex:'name'},
					{text:'<center>资源URL</center>',width:140,dataIndex:'uri'},
					{text:'<center>资源类型</center>',width:100,dataIndex:'type'},
					{text:'<center>操作</center>',width:120,renderer:function(v,cls,record){
						var resId =record.data.resourceID;
						return '<div><input type="button" value="修&nbsp;改" onclick="addOrUpdateResource(\''+resId+'\')"  class="enableCls">'+
							'<input type="button" value="删&nbsp;除" onclick="deleteRescouce()"  class="enableCls"></div>'
					}}
				],
				selModel: { //选中模型 
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
		        },
		        tbar:[{
		        	text:'增加',
		        	iconCls:'',
		        	handler:function(){
		        		addOrUpdateResource('');
		        	}
		        },{
		        	text:'删除',
		        	iconCls:'',
		        	handler:function(){
		        		deleteResource();
		        	}
		        }],
				multiSelect: true,//是否允许多选
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: res_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }]
			})
			return res_grid;
			
		}
		//删除资源权限
		function deleteRescouce(){
			var grid = Ext.getCmp("res_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var length = selRows.length;
			if(length > 0){
				Ext.Msg.confirm("提示","确定要删除选中的资源权限记录?",function(btn){
					if(btn == 'yes'){
						var url ='';
						var args='';
						if(length >1){
							for(var i = 0; i < length; i++){
								args +='ids='+selRows[i].get('privresID');
								if(i < length -1){
									args +="&";
								}
							}
							url: $service.portal + '/us.Privilege/collection/batchdelete?'+args;
						}else{
							url: $service.portal + '/us.Privilege/' + selRows[0].get('privresID') + '/delete';
						}
						//执行删除操作
						Ext.Ajax.request({
							method:'post',
							url: url,
							params:{},
							success:function(resp, opts){
								var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'删除失败：' + r.description);
								} else {
									refresh();
								}
							},
							failure:function(resp,opts){
								Ext.Msg.alert("提示",'删除失败：' + resp.responseText);
							}
						});
					}
				});
			}else{
				Ext.Msg.alert("提示","请选择删除的资源权限记录!");
			}
			
		}
		//新增或修改资源权限
		function addOrUpdateResource(id){
			var res_win = Ext.getCmp('res_win');
			if(!res_win){
				var title = id ==''?'新增':'修改';
				res_win = Ext.create("Ext.window.Window",{
					title: title+'资源权限信息',
					height:500,
					id:'res_win',
					width:750,
					itemId:id,
					resizable:false,
					modal:true,
					layout:'fit',
					border:false,
					items:[{
						xtype:'panel',
						layout:'border',
						border:false,
						style:{
							'border':'0px'
						},
						items:[{
							xtype:'form',
							region:'north',
							id:'north-form',
							height:90,
							style:{
								'border-radius':'0px',
								'border-bottom':'0px'
							},
							padding:'5px 5px',
							layout:'column',
							frame:true,
							items:[{
								columnWidth:.5,
								layout:'anchor',
								frame:true,
								style:{
									'border':'0px'
								},
								items:[{
									xtype:'textfield',
									fieldLabel:'资源名称',
									name:'resourceName',
									labelAlign:'right',
									labelWidth:80,
									anchor:'90%'
								},{
									xtype:'combobox',
									fieldLabel:'资源类型',
									labelWidth:80,
									labelAlign:'right',
									displayField:'name',
									valueField:'id',
									mode: 'local',
							        triggerAction: 'all',
							        store: Ext.create('Ext.data.ArrayStore',{
										fields:['id','name','attributes'],
										proxy:{
											type: 'ajax',
								            url:$service.portal+'/tk.Dictionary/restype/childNodes'
										}
									}),
									name:'resourceType',
									anchor:'90%',
									listeners:{
										select:function(combox,records){
											var priv ="", privname="";
											var attriObj = records[0].data.attributes;
											
											for(var i in attriObj){
												priv = i;
												privname = attriObj[priv];
												break;
											}
											
											var resourceType = combox.getValue();
											var form = Ext.getCmp("per_control");
											form.removeAll();
											form.add(createResItem(priv, privname));
										}
									}
									
								}]
							},{
								columnWidth:.5,
								layout:'anchor',
								frame:true,
								style:{
									'border':'0px'
								},
								items:[{
									xtype:'textfield',
									fieldLabel:'资源URL',
									name:'resourceURI',
									labelAlign:'right',
									labelWidth:80,
									anchor:'90%'
								}]
							}]
						},{
							xtype:'form',
							region:'center',
							style:{
								'border-radius':'0px'
							},
							id:'per_control',
							padding:'10px 5px',
							frame:true,
							autoScroll:true,
							items:[]
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'保存',
						handler:function(){
							var fieldsets = Ext.getCmp("per_control").query("fieldset");
							var preCodeArray = [];
							for(var i = 0; i < fieldsets.length; i++){
								var fieldset = fieldsets[i];
								var code = fieldSet.id;
								var checkboxs = fieldset.query('checkbox');
								for(var j = 0; j < checkboxs.length; j++){
									var checkbox = checkboxs[j];
									if(!checkbox.itemId && checkbox.getValue()){
										var id = checkbox.id;
										var url = Ext.getCmp(id+'_url').getValue();
										var name = Ext.getCmp(id+'_label').text;
										preCodeArray.push({code:code,id:checkbox.id,name:name,resoureId:id,resourceUrl:url});
									}
								}
							}
							var form = Ext.getCmp("north-form").getForm();
							var resourceName = form.findField("resourceName").getValue();
							var resourceURI = form.findField("resourceURI").getValue();
							Ext.Ajax.request({
								method:'post',
								url:$service.portal+'/us.Privilege/collection/create',
								jsonData:{privCode:preCodeArray},
								success:function(resp, opts){
									var r = eval("(" + resp.responseText + ")");
									if (r.code != 1) {
										Ext.Msg.alert("提示",'保存失败：' + r.description);
									} else {
										refresh();
										res_win.destroy();
									}
								},
								failure:function(resp,opts){
									Ext.Msg.alert("提示",'保存失败：' +  resp.responseText);
								}
							})
							
						}
					},{
						text:'取消',
						handler:function(){
							res_win.destroy();
						}
					}]
				})
			}
			res_win.show();
			//获取修改资源权限记录，获取资源名称 资源URI 和资源类型值
			var grid = Ext.getCmp("res_grid");
			var form = Ext.getCmp("north-form").getForm();
			var resourceType ='';
			//若新增，重置form表单
			if(id ==''){
				form.reset();
			}else{
				//初始化 资源名称 资源URI 资源类型值
				var selRow = grid.getSelectionModel().getSelection()[0];
				resourceType = selRow.get("resourceType");
				var name_field = form.findField("resourceName");
				name_field.setValue(selRow.get("resourceName"));
				var name_type = form.findField("resourceType");
				name_type.setValue(selRow.get("resourceType"));
				var name_uri = form.findField("resourceURI");
				name_uri.setValue(selRow.get("resourceURI"));
			}
			//根据资源类型值 动态加载权限
			var form = Ext.getCmp("per_control");
			form.removeAll();
			form.add(createResItem(resourceType, ""));
			from.doLayout();
		}
		
		//加载权限列表
		function createResItem(privcode, privname){
			var fieldSetArray =[];
			$.ajax({
				type:'GET',
				url:$service.portal+'/tk.Dictionary/'+privcode+'/childNodes',
				dataType:'json',
				async:false,
				success:function(data){
					var length =0;
					if(data && data.length){
						length = data.length;
					}
					var items = [{
						layout:'column',
						xtype:'form',
						frame:true,
						style:{
							'border':'0px'
						},
						items:[{
							columnWidth:.05,
							xtype:'checkbox',
							fieldLabel:'',
							itemId:privcode,
							listeners:{
								change:function(checkbox, newVal, oldVal, e){
									var fieldSet = Ext.getCmp(checkbox.itemId);
									var checkArray = fieldSet.query('checkbox');
									for(var m = 0; m < checkArray.length; m++){
										var _checkbox = checkArray[m];
										if(_checkbox !=checkbox){
											_checkbox.setValue(newVal);
										}
									}
								}

							}
						},{
							columnWidth:.15,
							xtype:'label',
							text:'全选'
						},{
							columnWidth:.4,
							xtype:'label',
							style:{
								'text-align':'center',
								'margin':'0px 5px 0px 0px'
							},
							text:'资源权限名'
						},{
							columnWidth:.4,
							xtype:'label',
							style:{
								'text-align':'center'
							},
							text:'访问资源Url'
						}]
					}];
					for(var i = 0; i < length; i++){
						var object = data[i];
						var text = object.name;
						var id = object.id;
						var appName = Ext.getCmp("res-search-form").getForm().findField("appId").getRawValue();
						var resourceName = Ext.getCmp("north-form").getForm().findField("resourceName").getValue();
						var display = appName+'_'+resourceName+'_'+text;
						items.push({
							layout:'column',
							xtype:'form',
							frame:true,
							style:{
								'border':'0px',
								'margin':'-10px 0px 1px 0px'
							},
							items:[{
								columnWidth:.05,
								xtype:'checkbox',
								id:id,
								fieldLabel:''
							},{
								columnWidth:.15,
								xtype:'label',
								id:id+'label',
								text:text
							},{
								columnWidth:.4,
								xtype:'displayfield',
								style:{
									'margin':'0px 5px 0px 0px',
									'border':'1px solid',
									'height':'22px'
								},
								labelWidth:0,
								value:display,
								fieldLabel:''
							},{
								columnWidth:.4,
								xtype:'textfield',
								id:id+'_url',
								labelWidth:0,
								fieldLabel:''
							}]
						});
					}
					var fieldSet = {
						xtype:'fieldset',
						id:privcode,
						title:privname,
						collapsible:true,
						items:[items]
					}
					fieldSetArray.push(fieldSet);
				},
				failure:function(){
				
				}
			})
			
			return fieldSetArray;
		}
		
	/****************************************功能管理   begin**************************************************/
		window.functionManager = function(){
			var app_fun_win = Ext.getCmp("app_fun_win");
			var grid = Ext.getCmp("app_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var appId = selRows[0].get('id');
			var appName = selRows[0].get('name');
			
			

			
			
			
			if(!app_fun_win){
				app_fun_win = Ext.create('Ext.window.Window',{
					title:'功能管理',
					width:900,
					height:500,					
					layout:'border',
					border:false,
					//closeAction:'destroy',
					frame:true,
        
						modal:true,
						maximizable: true,          //是否可以最大化
				        minimizable: true,          //是否可以最小化
				        closable: true,            //是否可以关闭
						maximized:true,

				        
					items:[{
						xtype:'form',
						region:'north',
						layout:'hbox',
						id:'fun-search-form',
						frame:true,
						cls:'x-plain',
						items:[{
							xtype:'combobox',
							labelAlign:'right',
							labelWidth:60,
							name:'appId',
							displayField:'name',
							valueField:'id',
							mode: 'local',
							style:{'opacity':1},
					        triggerAction: 'all',
							store: Ext.create('Ext.data.Store',{
								fields:['id','name'],
								autoLoad:true,
								proxy:{
									type: 'ajax',
						            url:$service.portal+'/us.App/collection/query',
						            reader: {
						                root: 'items'
						            },
						            simpleSortMode: true
								}
							}),
							margin:'5px 0px 5px 0px',
							width:220,
							value:appId,
							disabled:false,
							editable:false,
							fieldLabel:'应用系统',
							listeners : {
								select:function(){
									var form = Ext.getCmp("fun-search-form");
									form.getForm().findField("module").reset();
									form.getForm().findField("moduleId").reset();
									form.getForm().findField("name").reset();
								}
							}
						},{
							xtype:'hidden', name:'module'
						},{
							xtype:'trigger',
							labelAlign:'right',
							name:'moduleId',
							labelWidth:60,
							margin:'5px 0px 5px 0px',
							onTriggerClick:function(){
								var form = Ext.getCmp("fun-search-form").getForm();
								var pModule_win = Ext.getCmp("p_m_win");
								if(!pModule_win){
									pModule_win= Ext.create("Ext.window.Window",{
										id:'p_m_win',
										title:'模块名称选择',
										modal:true,
										width:300,
										height:350,
										layout:'fit',
										items:[{
											xtype:"treepanel",
											id:'tree_panel',
											border:false,
											rootVisible:false,
											store:Ext.create('Ext.data.TreeStore', {//树的数据源store
												fields:[{name : 'id',  type : 'string'},  
											        	{name : 'text', type : 'string'}],
									        	proxy : {//代理
										            type : 'ajax',
										            url :$service.portal+'/us.Module/collection/apptree?appId='+
										            		Ext.getCmp('fun-search-form').getForm().findField("appId").getValue()
										        },
												root: {
											        expanded: true
											    }
										    }),
										    listeners:{
										    	itemdblclick:function(tree,record){
										    		if(record.data.leaf){
										    			var form = Ext.getCmp("fun-search-form");
														//form.itemId = record.data.id;
														form.getForm().findField("module").setValue(record.data.id);
														form.getForm().findField("moduleId").setValue(record.data.text);
														pModule_win.destroy();
										    		}
										    	}	
										    }
										}],
										buttonAlign:'center',
										buttons:[{
											text:'确定',
											handler:function(){
												var selNodes = Ext.getCmp("tree_panel").getSelectionModel().getSelection();
												if(selNodes.length >0){
													var selNode = selNodes[0];
													var form = Ext.getCmp("fun-search-form");
													form.getForm().findField("module").setValue(selNode.data.id);
													form.getForm().findField("moduleId").setValue(selNode.data.text);
													pModule_win.destroy();
												}else{
													Ext.Msg.alert('提示','请选择菜单节点!');
												}
											}
										},{
											text:'取消',
											handler:function(){
												pModule_win.destroy();
											}
										}]
									});
								}
								pModule_win.show();
							},
							width:180,
							fieldLabel:'模块名称'
						},{
							xtype:'textfield',
							labelAlign:'right',
							name:'name',
							labelWidth:80,
							margin:'5px 0px 5px 0px',
							width:250,
							fieldLabel:'功能名称'
						},{
							xtype:'button',
							width:80,
		        			margin:'5px 5px 5px 10px',
		        			text:'查   询',
		        			handler:function(){
		        				var form = Ext.getCmp('fun-search-form');
		        				var params = Ext.ux.FormUtils.getDataObject(form);
								var store = Ext.getCmp('fun_grid').getStore();
								//delete params.appId;
								var moduleId = form.getForm().findField("moduleId").getValue();
								if(moduleId == ""){
									params.moduleId = "";
								}else{
									params.moduleId = form.getForm().findField('module').getValue();
								}
								delete params.module;
								store.getProxy().url = $service.portal+'/us.Function/collection/query';
								store.getProxy().extraParams = params;
								store.loadPage(1);
		        			}
						},{
							xtype:'button',
							width:80,
		        			margin:'5px 0px 5px 5px',
		        			text:'重 置',
		        			handler:function(){
		        				var form = Ext.getCmp('fun-search-form');
		        				form.getForm().reset();
		        			}
						}]
					},createFunGrid(appId)]
				});
			}
			app_fun_win.show();
		}	
		
		function createModuleTree1(id){
			Ext.define('treeModel', {//树的数据模型
		        extend : 'Ext.data.Model',//继承自Model
		        fields : [//字段
		        	{name : 'id',  type : 'string'},  
		        	{name : 'text', type : 'string'}
		    	],
				proxy : {//代理
		            type : 'ajax',
		            url :$service.portal+'/us.Module/collection/apptree?appId='+id
		        }
		    });
			var treeStore = Ext.create('Ext.data.TreeStore', {//树的数据源store
				model : treeModel,//数据模型
				root: {
			        expanded: true
			    }
		    });
			
			var _module_Tree = Ext.create('Ext.tree.Panel',{//菜单树
				id:'_module_tree',
				minWidth: 210,//最小宽度
				border:false,
				itemId: id,
				store : treeStore,//数据源
				autoHeight : true,//是否自动适应高度
				animate: false,//是否有动画效果
				rootVisible: false,//根节点是否可见
				listeners: {
					itemdblclick:function(tree,record){
						if(record.data.leaf){
							var moduleId = record.data.id;
							var functionStr=[];
							var grid = Ext.getCmp("fun_grid");
							var selRows = grid.getSelectionModel().getSelection();
							for(var i =0; i<selRows.length; i++){
								var rec = selRows[i];
								functionStr.push("ids="+rec.data.id);
							}
							Ext.Ajax.request({
								method:'post',
								url: $service.portal+'/us.Module/'+moduleId+'/configFunction?'+functionStr.join("&"),
								success:function(resp, opts){
									var r = eval("(" + resp.responseText + ")");
									if (r.code != 1) {
										Ext.Msg.alert("提示",'配置模块：' + r.description);
									} else {
										
										Ext.getCmp("module_win_").destroy();
										var grid = Ext.getCmp("fun_grid");
										grid.getStore().load();
									}
								}
							})
						}
					}
				}
			});
			_module_Tree.expandAll();
			return _module_Tree;
		}
		
		window.configModule = function(){
			var grid = Ext.getCmp("fun_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var length = selRows.length;
			if(length > 0){
				var _window = Ext.getCmp("module_win_");
				var form = Ext.getCmp("fun-search-form").getForm();
				var appId = form.findField("appId").getValue();
				
				if(!_window){
					_window = Ext.create("Ext.window.Window",{
						height:400,
						width:300,
						layout:'fit',
						items:[createModuleTree1(appId)],
						buttons:[{
							text:'保存',
							handler:function(){
								var tree = Ext.getCmp("_module_tree");
								var _selRows = tree.getSelectionModel().getSelection();
								if(_selRows.length >0){
									var moduleId = _selRows[0].data.id;
									var functionStr=[];
									for(var i =0; i<selRows.length; i++){
										var rec = selRows[i];
										functionStr.push("ids="+rec.data.id);
									}
									Ext.Ajax.request({
										method:'post',
										url: $service.portal+'/us.Module/'+moduleId+'/configFunction?'+functionStr.join("&"),
										success:function(resp, opts){
											var r = eval("(" + resp.responseText + ")");
											if (r.code != 1) {
												Ext.Msg.alert("提示",'配置模块：' + r.description);
											} else {
												_window.destroy();
												var grid = Ext.getCmp("fun_grid");
												grid.getStore().load();
											}
										},
										failure:function(resp,opts){
											Ext.Msg.alert("提示",'配置模块：' + resp.responseText);
										}
									})
									
								}else{
									Ext.Msg.alert("提示","请选择模块节点!");
								}
							}
						},{
							text:'取消',
							handler:function(){
								_window.destroy();
							}
						}]
					})
				}
				_window.show();
				
			}else{
				Ext.Msg.alert("提示","请选择要配置模块的功能列表!");
			}
		}
		 
		window.deleteFunction = function(){
			var grid = Ext.getCmp("fun_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var length = selRows.length;
			if(length > 0){
				Ext.Msg.confirm("提示","确定要删除选中的功能?",function(btn){
    				if(btn == 'yes'){
    					//执行删除操作
						var url = '';
						var args ='';
						if(length >1){
							for(var i = 0; i < length; i++){
								args +='ids='+selRows[i].get('id');
								if(i < length -1){
									args +="&";
								}
							}
							url = $service.portal + '/us.Function/collection/batchdelete?'+args;
						}else{
							url = $service.portal + '/us.Function/' + selRows[0].get('id')+ '/delete';
						}
						Ext.Ajax.request({
							method:'post',
							url: url,
							params:{},
							success:function(resp, opts){
								var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'删除失败：' + r.description);
								} else {
									var grid = Ext.getCmp("fun_grid");
									grid.getStore().load();
								}
							},
							failure:function(resp,opts){
								Ext.Msg.alert("提示",'删除失败：' + resp.responseText);
							}
						});
    					
					}
    			})
			}else{
				Ext.Msg.alert('提示','请选择要删除的功能记录!');
			}
		}    
	
		function createFunGrid(appId){
			Ext.define('funModel',{
				extend:'Ext.data.Model',
				fields:['id','privilegeId', 'privilegeName', 'type','name','moduleName','moduleId','appName','appId','description']
			});
			var fun_store = Ext.create('Ext.data.Store',{
				model: 'funModel',//数据模型
		        remoteSort: true,
		        autoLoad:false,
		        pageSize:20,
		        proxy: {
		            type: 'ajax',
		            url:$service.portal+'/us.Function/collection/query?appId='+appId,
		            reader: {
		                root: 'items',
		                totalProperty: 'total'
		            },
		            simpleSortMode: true
		        }
			});
			fun_store.load({
		    	params:{
		    		start: 0,
	        		limit: 20
	        	}
		    });
		    
			var fun_grid = Ext.create('Ext.grid.Panel',{
				store: fun_store,
				id:'fun_grid',
				region:'center',
				margins:'5px 0px',
				columnLines:true,
				columns:[
					{xtype: 'rownumberer',text: '序号',align:'center',width:40},
					{text:'<center>功能名称</center>',width:150,dataIndex:'name'},
					{text:'<center>功能描述</center>',width:250,dataIndex:'description'},
					{text:'<center>模块名称</center>',width:150,dataIndex:'moduleName'},
					{text:'<center>应用名称</center>',width:150,dataIndex:'appName'},
					{text:'关联权限',width:150, dataIndex:'privilegeName'},
					{text:'<center>操作</center>',width:150,renderer:function(v,cls,record){
						var resId =record.data.id;
						return '<div><input type="button" value="修&nbsp;改" style="margin:0px 5px 0px 0px;cursor:pointer;width:auto;" onclick="addOrUpdateFun(\''+resId+'\')"  class="enableCls">'+
							'<input type="button" value="删&nbsp;除" style="margin:0px 5px 0px 0px;cursor:pointer;width:auto;" onclick="deleteFunction()"  class="enableCls"></div>'
					}}
				],
				selModel: { //选中模型 
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
		        },
		        tbar:[{
		        	text:'增加',
		        	iconCls:'',
		        	handler:function(){
		        		addOrUpdateFun('');
		        	}
		        },{
		        	text:'删除',
		        	iconCls:'',
		        	handler:function(){
		        		deleteFunction();
		        	}
		        },{
		        	text:'配置模块',
		        	handler:function(){
		        		configModule();
		        	}
		        }],
		        
				multiSelect: true,//是否允许多选
				

				
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: fun_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }]
			})
			return fun_grid;
		}
		
		//新增|修改功能
		window.addOrUpdateFun = function(id){
			var fun_win = Ext.getCmp("fun_win");
			var title = id == ''? '新增':'修改';
			var funAppId = Ext.getCmp("fun-search-form").getForm().findField("appId").getValue();
			if(!fun_win){
				fun_win = Ext.create('Ext.window.Window',{
					title: title+'功能',
					modal:true,
					width:700,
					id:'fun_win',
					height:350,
					layout:'fit',
					border:false,
					items:[{
						xtype:'tabpanel',
						layout:'fit',
						items:[{
							title:'基本信息',
							xtype:'form',
							id:'res_basic_form',
							itemId:'',
							frame:true,
							style:{
								'border':'0px'
							},
							layout:'anchor',
							items:[{
								xtype:'combobox',
								labelAlign:'right',
								labelWidth:80,
								name:'appId',
								displayField:'name',
								valueField:'id',
								value:funAppId,
								store: Ext.create('Ext.data.Store',{
									fields:['id','name'],
									autoLoad:true,
									proxy:{
										type: 'ajax',
							            url:$service.portal+'/us.App/collection/query',
							            reader: {
							                root: 'items'
							            },
							            simpleSortMode: true
									}
								}),
								anchor:'80%',
								editable:false,
								fieldLabel:'应用系统',
								listeners : {
									select:function(){
										
									}
								}
							},{
								xtype:'trigger',
								labelWidth:80,
								name:'moduleName',
								labelAlign:'right',
								fieldLabel:'模块名称',
								anchor:'80%',
								onTriggerClick:function(){
									var form = Ext.getCmp("res_basic_form").getForm();//Ext.getCmp("fun-search-form").getForm();
									var appId = form.findField("appId").getValue();
									var pModule_win = Ext.getCmp("p_m_win");
									if(!pModule_win){
										pModule_win= Ext.create("Ext.window.Window",{
											id:'p_m_win',
											title:'模块名称选择',
											modal:true,
											width:300,
											height:350,
											layout:'fit',
											items:[{
												xtype:"treepanel",
												id:'tree_panel',
												border:false,
												rootVisible:false,
												store:Ext.create('Ext.data.TreeStore', {//树的数据源store
													fields:[{name : 'id',  type : 'string'},  
												        	{name : 'text', type : 'string'}],
										        	proxy : {//代理
											            type : 'ajax',
											            url :$service.portal+'/us.Module/collection/apptree?appId='+appId
											        },
													root: {
												        expanded: true
												    }
											    }),
											    listeners:{
											    	itemdblclick:function(tree,record){
											    		if(record.data.leaf){
											    			var form = Ext.getCmp("res_basic_form");
															var win = Ext.getCmp("fun_win");
															win.itemId = record.data.id;
															form.getForm().findField("moduleName").setValue(record.data.text);
															pModule_win.destroy();
											    		}
											    	}
											    }
											}],
											buttonAlign:'center',
											buttons:[{
												text:'确定',
												handler:function(){
													var selNodes = Ext.getCmp("tree_panel").getSelectionModel().getSelection();
													if(selNodes.length >0){
														var selNode = selNodes[0];
														var form = Ext.getCmp("res_basic_form");
														var win = Ext.getCmp("fun_win");
														win.itemId = selNode.data.id;
														form.getForm().findField("moduleName").setValue(selNode.data.text);
														pModule_win.destroy();
													}else{
														Ext.Msg.alert('提示','请选择菜单节点!');
													}
												}
											},{
												text:'取消',
												handler:function(){
													pModule_win.destroy();
												}
											}]
										});
									}
									pModule_win.show();
								}
							},{
								xtype:'enum-combobox',
								enumType: 'com.taole.usersystem.domain.Function$Type',
								name:'type',
								labelAlign:'right',
								labelWidth:80,
								value:'Custom',
								fieldLabel:'功能类型',
								anchor:'80%'
							},{
								xtype:'textfield',
								labelWidth:80,
								name:'name',
								labelAlign:'right',
								allowBlank:false,
								blankText:'请输入功能名称',
								fieldLabel:'功能名称',
								anchor:'80%'
							},{
								xtype:'textarea',
								labelWidth:80,
								labelAlign:'right',
								height:80,
								name:'description',
								fieldLabel:'功能描述',
								anchor:'80%'
							}]
						},{
							title:'资源',
							xtype:'gridpanel',
							id:'url_grid',
							store: Ext.create('Ext.data.Store',{
								fields:['id','name','uri','type','typeid','entityName','functionId', 'isMenu', 'typeName'],
								id:'url_store',
						        remoteSort: true,
						        autoLoad:true,
						        pageSize:20,
						        proxy: {
						            type: 'ajax',
						            url: $service.portal + '/us.Function/'+id+'/retrieve',
						            reader: {
						                root: 'resourceList',
						                totalProperty: 'total'
						            }
						        }
							}),
							
							columnLines:true,
							plugins: [new Ext.grid.plugin.CellEditing({
					            clicksToEdit: 1
					        })],
							columns:[
								{xtype: 'rownumberer',text: '序号',align:'center',width:40},
								{text:'URL',width:240,dataIndex:'uri',editor:{}},
								{text:'描述',width:120,dataIndex:'name',editor:{}},
								{text:'可否作为菜单资源',width:120,dataIndex:'isMenu',align:'center',editor:{xtype:'checkbox'},renderer:function(v){
									if(v){
										return "是";
									}else{
										return "否";
									}
								}},
								{text:'资源类型',width:80,dataIndex:'typeName',editor:{
									xtype:'combobox',
				                    typeAhead: true,
				                    displayField:'name',
				                    valueField:'value',
				                    triggerAction: 'all',
					                selectOnTab: true,
					                lazyRender: true,
				                    store: Ext.create('Ext.data.Store',{
				                    	fields:['value','name'],
										autoLoad:true,
										proxy:{
											type: 'ajax',
								            url:$service.portal+'/tk.Dictionary/restype/childNodes',
								            simpleSortMode: true
										}
				                    }),
				                    listeners:{
				                        select:function(combox,records){
				                        	var id = records[0].data.value;
				                        	var name = records[0].data.name;
				                        	console.log(name)
				                        	var selRow = Ext.getCmp("url_grid").getSelectionModel().getSelection()[0];
				                        	selRow.set('type',id);
				                        	selRow.set('typeName',name);
				                        	combox.setValue(name)
				                        }
				                    }
				                }},
				                {text:'资源类型',hidden:true,dataIndex:'type',width:20},
								{text:'操作',width:80,renderer:function(v,cls,record){
									var id =record.data.id;
									return '<div><input type="button" value="删&nbsp;除" style="margin:0px 5px 0px 0px;cursor:pointer; width:60;"  onclick="deleteUrlRecord(\''+id+'\')"  class="enableCls"></div>'
								}}
							],
							tbar:[{
								text:'新增',
								handler:function(){
									var grid = Ext.getCmp('url_grid');
									var store = grid.getStore();
									var count = store.getCount();
									store.insert(count,{});
								}
							}]
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'保存',
						handler:function(){
							var form = Ext.getCmp("res_basic_form");
							var fun_win = Ext.getCmp('fun_win');
							if(form.getForm().isValid()){
								
								var store = Ext.getCmp("url_grid").getStore();
								var urlArray = [];
								var bool = false;
								store.each(function(rec){
									if(rec.data.uri =='' || rec.data.type ==''){
										Ext.Msg.alert("提示","资源记录中url和资源类型不允许为空!");
										bool = true;
									}else{
										if(rec.data.typeid !=''){
											rec.data.type = rec.data.typeid;
										}
										delete rec.data.typeid;
										if(id !=''){
											rec.data.functionId = id;
										}
										rec.data.entityName ="us.Resource";
										urlArray.push(rec.data);
									}
								})
								if(!bool){
									var data = Ext.ux.FormUtils.getDataObject(form);
									var url ="";
									if (id != '') {
										data.id = id;
										url = $service.portal+'/us.Function/'+id+'/update';
									}else{
										data.id ="";
										url =$service.portal+'/us.Function/collection/create';
									}
									data.moduleId = fun_win.itemId;
									data.entityName ="us.Function";
									
									
									data.resourceList = urlArray;
									console.log(data);
									Ext.Ajax.request({
										url:url,
										method:'post',
										jsonData:data,
										success:function(resp, opts){
											var r = eval("(" + resp.responseText + ")");
											if (r.code != 1) {
												Ext.Msg.alert("提示",'保存失败：' + r.description);
											} else {
												fun_win.destroy();
												Ext.getCmp('fun_grid').getStore().load();
											}
										},
										failure:function(resp, opts){
											Ext.Msg.alert("提示",'保存失败：' + resp.responseText);
										}
									})
								}
								
							}
						}
					},{
						text:'取消',
						handler:function(){
							fun_win.destroy();
						}
					}]
				})
			}
			fun_win.show();
			
			var form = Ext.getCmp('res_basic_form').getForm();
			var selRow = Ext.getCmp('fun_grid').getSelectionModel().getSelection()[0];
			if(id!=''){
				fun_win.itemId = selRow.data.moduleId;
				form.loadRecord(selRow);	
			} else {
				form.reset();
				var form = Ext.getCmp("fun-search-form").getForm();
				var appId = form.findField("appId").getValue();
				$.ajax({
					type:'GET',
					url:$service.portal+'/us.Module/collection/apptree?appId='+appId,
					dataType:'json',
					async:false,
					success:function(data){
						if(data){
							var form = Ext.getCmp("res_basic_form");
							var win = Ext.getCmp("fun_win");
							win.itemId = data[0].id;
							form.getForm().findField("moduleName").setValue( data[0].text);
						}
					},
					failure:function(){
						
					}
				});
			}
		}
		
		//删除资源记录
		window.deleteUrlRecord = function(id){
			var grid = Ext.getCmp("url_grid");
			var selRow = grid.getSelectionModel().getSelection();
			grid.getStore().remove(selRow);
		}
	/****************************************功能管理   end**************************************************/	
	
	/***************************************模块管理   begin**************************************************/	
	function configModuleFun(){
		var config_module_win = Ext.getCmp("config_module_win");
		var record = Ext.getCmp("app_grid").getSelectionModel().getSelection()[0];
		var appId = record.data.id;
		var tree = Ext.getCmp("module_tree");
		var node = tree.getSelectionModel().getSelection()[0];
		var moduleId = node.data.id;
		if(!config_module_win){
			config_module_win = Ext.create("Ext.window.Window",{
				height:300,
				id:"config_module_win",
				title:'配置功能',
				width:600,
				itemId:moduleId,
				layout:'fit',
				items:[{
					id:'config_module_form',
					xtype:'form',
					frame:true,
					style:{
						'border':'0px'
					},
					border:false,
					items:[addFunTo(600,appId,moduleId)]
				}],
				buttons:[{
					text:'保存',
					handler:function(){
						var form = Ext.getCmp("config_module_form");
						var funArray = form.query("checkbox");
						var funIds = [];
						for(var i = 0; i < funArray.length; i++){
							var checkbox = funArray[i];
							var value = checkbox.getValue();
							if(checkbox.boxLabel !='全选'){
								if(value){
									funIds.push("ids="+checkbox.id);
								}
							}
						}
						var moduleId = config_module_win.itemId;
						Ext.Ajax.request({
							url:$service.portal+'/us.Module/'+moduleId+'/configFunction?'+funIds.join("&"),
							method:'post',
							success:function(resp, opts){
								var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'保存失败：' + r.description);
								} else {
									config_module_win.destroy();
									Ext.getCmp('module_tree').getStore().load();
								}
							},
							failure:function(resp, opts){
								Ext.Msg.alert("提示",'保存失败：' + resp.responseText);
							}
						})
					}
				},{
					text:'取消',
					handler:function(){
						config_module_win.destroy();
					}
				}]
			})
		}
	}
	
	//新增或修改模块
	function  addOrUpdateModule(type){
		var title = type =='add'?'新增':'修改';
		var readonly = type !='add'?false:true;
		var parentId,parentName;
		var tree = Ext.getCmp("module_tree");
		var node = tree.getSelectionModel().getSelection()[0];
		if(type !='add'){
			var pNode = node.parentNode;
			if(pNode){
				parentId = pNode.data.id;
				parentName =pNode.data.text;
			}
		}else{
			parentId = node.data.id;
			parentName = node.data.text;
		}
		var module_add_win = Ext.getCmp("module_add_win");
		if(!module_add_win){
			module_add_win = Ext.create("Ext.window.Window",{
				title: title+'模块',
				height:400,
				id:'module_add_win',
				width:500,
				itemId:type,
				layout:'fit',
				modal:true,
				items:[{
					xtype:'form',
					layout:'anchor',
					id:'module_form',
					itemId:parentId,
					frame:true,
					margin: '5',
					style:{
						'border':'0px'
					},
					defaults: { // defaults 将会应用所有的子组件上,而不是父容器
						labelWidth:100
					},
					items:[{
						xtype:'trigger',
						fieldLabel:'父级模块',
						name:'parentNode',
						labelAlign:'right',
						anchor:'95%',
						value:parentId,
						style:{
							'opacity':1
						},
						disabled:readonly,
						onTriggerClick:function(){
							var id = Ext.getCmp('module_tree').itemId;
							var pModule_win = Ext.getCmp("p_m_win");
							if(!pModule_win){
								pModule_win= Ext.create("Ext.window.Window",{
									id:'p_m_win',
									title:'父级模块选择',
									modal:true,
									width:300,
									height:350,
									layout:'fit',
									items:[{
										xtype:"treepanel",
										id:'tree_panel',
										border:false,
										rootVisible:false,
										store:Ext.create('Ext.data.TreeStore', {//树的数据源store
											fields:[{name : 'id',  type : 'string'},  
										        	{name : 'text', type : 'string'}],
								        	proxy : {//代理
									            type : 'ajax',
									            url :$service.portal+'/us.Module/collection/apptree?appId='+id
									        },
											root: {
										        expanded: true
										    }
									    }),
									    listeners:{
									    	itemdblclick:function(tree,record){
									    		var form = Ext.getCmp("module_form");
												form.itemId = record.data.id;
												form.getForm().findField("parentNode").setValue(record.data.text);
												pModule_win.destroy();
									    	}
									    }
									}],
									buttonAlign:'center',
									buttons:[{
										text:'确定',
										handler:function(){
											var selNodes = Ext.getCmp("tree_panel").getSelectionModel().getSelection();
											if(selNodes.length >0){
												var selNode = selNodes[0];
												var form = Ext.getCmp("module_form");
												form.itemId = selNode.data.id;
												form.getForm().findField("parentNode").setValue(selNode.data.text);
												pModule_win.destroy();
											}else{
												Ext.Msg.alert('提示','请选择菜单节点!');
											}
										}
									},{
										text:'取消',
										handler:function(){
											pModule_win.destroy();
										}
									}]
								});
							}
							pModule_win.show();
						}
					},{
						xtype:'textfield',
						fieldLabel:'模块名称',
						name:'name',
						labelAlign:'right',
						anchor:'95%'
					},{
						xtype:'textfield',
						fieldLabel:'正式部署路径',
						name:'rootUrl',
						labelAlign:'right',
						anchor:'95%'
					},{
						xtype:'textfield',
						fieldLabel:'预发布部署路径',
						name:'rootUrlPre',
						labelAlign:'right',
						anchor:'95%'
					},{
						xtype:'textfield',
						fieldLabel:'测试部署路径',
						name:'rootUrlTest',
						labelAlign:'right',
						anchor:'95%'
					},{
						xtype:'textfield',
						fieldLabel:'开发部署路径',
						name:'rootUrlDev',
						labelAlign:'right',
						anchor:'95%'
					},{
						xtype:'textarea',
						fieldLabel:'模块描述',
						name:'description',
						height:80,
						labelAlign:'right',
						anchor:'95%'
					}]
				}],
				buttonAlign:'center',
				buttons:[{
					text:'保存',
					handler:function(){
						var form = Ext.getCmp("module_form");
						var module_add_win = Ext.getCmp('module_add_win');
						if(form.getForm().isValid()){
							var data = Ext.ux.FormUtils.getDataObject(form);
							data.parentNode = form.itemId;
							var url ="";
							var type = type||module_add_win.itemId;
							if (type == 'edit') {
								data.id = node.data.id;
								url = $service.portal+'/us.Module/'+node.data.id+'/update';
							}else{
								data.id ="";
								url =$service.portal+'/us.Module/collection/create';
							}
							
							data.entityName ="us.Module";
							Ext.Ajax.request({
								url:url,
								method:'post',
								jsonData:data,
								success:function(resp, opts){
									var r = eval("(" + resp.responseText + ")");
									if (r.code != 1) {
										Ext.Msg.alert("提示",'保存失败：' + r.description);
									} else {
										module_add_win.destroy();
										Ext.getCmp('module_tree').getStore().load();
									}
								},
								failure:function(resp, opts){
									Ext.Msg.alert("提示",'保存失败：' + resp.responseText);
								}
							})
							
						}
					}
				},{
					text:'取消',
					handler:function(){
						module_add_win.destroy();
					}
				}]
			})
		}
		module_add_win.show();
		var form = module_add_win.down('form').getForm();
		if(type!='add'){
			form.loadRecord(node);
			form.findField('name').setValue(node.data.text);	
			form.findField('parentNode').setRawValue(parentName);
			Ext.Ajax.request({
				method:'get',
				url: $service.portal+'/us.Module/'+node.data.id+'/retrieve/',
				dataType:'json',
				async:false,
				success:function(resp){
					var r = eval("(" + resp.responseText + ")");
					if (r.description) {
						form.findField("description").setValue(r.description);
					}
					if (r.rootUrl) {
						form.findField("rootUrl").setValue(r.rootUrl);
					}
					if (r.rootUrlPre) {
						form.findField("rootUrlPre").setValue(r.rootUrlPre);
					}
					if (r.rootUrlTest) {
						form.findField("rootUrlTest").setValue(r.rootUrlTest);
					}
					if (r.rootUrlDev) {
						form.findField("rootUrlDev").setValue(r.rootUrlDev);
					}
				},
				failure:function(){
					
				}
			})
		} else {
			form.reset();
			form.findField('parentNode').setRawValue(parentName);
		}
	}
	
	var module_context = Ext.create('Ext.menu.Menu',{
		items:[{
			text:'删除',
			id:'module_delete',
			handler:function(){
				var tree = Ext.getCmp("module_tree");
				var selRows = tree.getSelectionModel().getSelection();
        		if(selRows.length > 0){
        			Ext.Msg.confirm("提示","确定要删除选中的模块节点?",function(btn){
        				if(btn == 'yes'){
    						var _id = selRows[0].data.id;
    	        			Ext.Ajax.request({
    	        				url:$service.portal+'/us.Module/'+_id+'/delete',
    	        				method:'post',
    	        				success:function(resp, opts){
    	        					var r = eval("(" + resp.responseText + ")");
    								if (r.code != 1) {
    									Ext.Msg.alert("提示",'删除失败：' + r.description);
    								} else {
    									Ext.getCmp('module_tree').getStore().load();
    								}
    	        				},
    	        				failure:function(resp, opts){
    	        					Ext.Msg.alert("提示",'删除失败：' + resp.responseText);
    	        				}
    	        			})
						}
        			})
        		}
			}
		},{
			text:'增加',
			id:'module_add',
			handler:function(){
				addOrUpdateModule('add');
			}
		},{
			text:'修改',
			id:'module_edit',
			handler:function(){
				addOrUpdateModule('edit');
			}
		}]
	})
	
	function setModuleDisableOrEnable(bool){
		Ext.getCmp('module_delete').setDisabled(bool);
		Ext.getCmp('module_edit').setDisabled(bool);
		//Ext.getCmp('module_config').setDisabled(bool);
		//Ext.getCmp('module_add').setDisabled(bool);
	}
	//创建模块树
	function createModuleList(id,width,height){
		var moduleItem =[];
		var children =[];// [{id:'2',name:'库房管理',children:[{id:'5',name:'入库管理',leaf:true},{id:'6',name:'出库管理',leaf:true},{id:'7',name:'库存盘点',leaf:true}]},{id:'3',name:'采购管理',leaf:true},{id:'24',name:'运输管理1',leaf:true},{id:'14',name:'运输管理2',leaf:true},{id:'8',name:'运输管理',leaf:true},{id:'9',name:'运输管理',leaf:true}];
		$.ajax({
			type:'GET',
			url:$service.portal + '/us.Module/collection/alltree',
			dataType:'json',
			async:false,
			success:function(data){
				var length =0;
				if(data && data.length){
					length = data.length;
				} 
				for(var i = 0; i < length; i++){
					var node = data[i];
					if(node['applicationId'] == id){
						children = node['children'];
					}
				}
			},
			failure:function(){
				
			}
		});
		var len = children.length;
		var level = len%5 == 0? len/5:Math.floor(len/5)+1;
		var column = level == 1? len:5;
		var initWidth =200;
		var initHeight =200;
		if(width > 20){
			initWidth = Math.floor((width-20)/column);
		}
		if(height >0){
			initHeight = Math.floor((height-65)/level)-level*2;
		}
		
		for(var j = 0; j < len; j++){
			var node = children[j];
			node.expanded = true;
			var id = node.id;
			var _tree = Ext.create("Ext.tree.Panel",{
				id: id+'_tree',
				store:Ext.create('Ext.data.TreeStore', {//树的数据源store
					fields:[{name:'text',mapping:'name'}],
					proxy : {//代理
			            type : 'ajax',
			            url :$service.portal+'/us.Module/'+id+'/tree'
			        },
					root: {
				        expanded: true,
				        children:[node]
				    }
			    }),
			    rootVisible: false,
			    listeners:{
			    	itemcontextmenu:function(tree, record, item, index, e){//注册tree的右键菜单
						e.preventDefault();
			    		var module_tree = tree.ownerCt;
			    		module_tree.getSelectionModel().select(record);//选择当前树节点
						module_context.showAt(e.getXY());
			    		
						//对于菜单中某些项是否可以用的控制
						//var id = record.data.id;
						//var root = module_tree.getRootNode();
						//var parentNode = record.parentNode;
						//if(parentNode ==  root){
						//	setModuleDisableOrEnable(true);
						//}else{
						//	setModuleDisableOrEnable(false);
						//}
		    		}
			    }
			})
			moduleItem.push(_tree);
		}
		
		var panel =Ext.create('Ext.panel.Panel',{
				xtype:'panel',
				id:'module_panel',
				layout: {
		            type: 'table',
		            columns: column
		        },
		       	border:false,
		        defaults: {frame:true, width:initWidth, height: initHeight},
		        items:[moduleItem]
			});
		return panel;
	}
	
	//单棵树
	function createModuleTree(id){
		Ext.define('treeModel', {//树的数据模型
	        extend : 'Ext.data.Model',//继承自Model
	        fields : [//字段
	        	{name : 'id',  type : 'string'},  
	        	{name : 'text', type : 'string'}
	    	],
			proxy : {//代理
	            type : 'ajax',
	            url :$service.portal+'/us.Module/collection/apptree?appId='+id
	        }
	    });
		var treeStore = Ext.create('Ext.data.TreeStore', {//树的数据源store
			model : treeModel,//数据模型
			root: {
		        expanded: true
		    }
	    });
		
		var module_Tree = Ext.create('Ext.tree.Panel',{//菜单树
			id:'module_tree',
			minWidth: 210,//最小宽度
			border:false,
			itemId: id,
			store : treeStore,//数据源
			autoHeight : true,//是否自动适应高度
			animate: false,//是否有动画效果
			rootVisible: false,//根节点是否可见
			listeners: {
	            itemmouseenter:function(view, record, item, index, e){//为tree添加鼠标悬停事件
				},
				itemcontextmenu:function(tree, record, item, index, e){//注册tree的右键菜单
					e.preventDefault();
					module_Tree.getSelectionModel().select(record);//选择当前树节点
					module_context.showAt(e.getXY());
					//对于菜单中某些项是否可以用的控制
					var id = record.data.id;
					var root = module_Tree.getRootNode();
					var parentNode = record.parentNode;
					if(parentNode ==  root){
						setModuleDisableOrEnable(true);
					}else{
						setModuleDisableOrEnable(false);
					}
	    		}
			}
		});
		module_Tree.expandAll();
		
		return module_Tree;
	}
	//模块管理窗体
	window.ModuleManager = function(id,name){
		var grid = Ext.getCmp("app_grid");
		var width = grid.getWidth();
		var height = grid.ownerCt.ownerCt.getHeight();
		var module_win = Ext.getCmp("module_win");
		if(!module_win){
			module_win = Ext.create('Ext.window.Window',{
				title:name+' 模块管理',
				id: 'module_win',
				itemId:id,
				height:400,
				modal:true,
				maximized:true,
				closeAction:'destroy',
				width:450,
				layout:'fit',
				//items:[createModuleList(id,width,height)],
				items:[createModuleTree(id)],
				buttonAlign:'center',
				buttons:[{
					text:'确定',
					handler:function(){
						module_win.destroy();
					}
				}]
			})
		}
		module_win.show();
	}
	
	/***************************************模块管理   end**************************************************/	
	
	/***************************************数据权限管理   start**************************************************/
	window.dataPrivilegeManager = function(appId){
		commonOpenMenuParameter("5e0d87960f994a399febdf547f18a06b","数据权限管理","http://localhost/taole-portal-webapp/app/dataPrivilege/dataPrivilegeManager/dataPrivilege.jsp","appId="+appId);
	}
	/***************************************数据权限管理   end**************************************************/
		//创建用户网格
		function CreateAppGrid(){
			if (app_store) {
				return app_store;
			}
			Ext.define('appModel',{
				extend:'Ext.data.Model',
				fields:['id','name','status','onlineDate','offlineDate','type','website','description']
			});
			app_store = Ext.create('Ext.data.Store',{
				model: 'appModel',//数据模型
		        remoteSort: true,
		        autoLoad:false,
		        pageSize:20,
		        proxy: {
		            type: 'ajax',
		            url: $service.portal + '/' + entityName + '/collection/query',
		            reader: {
		                root: 'items',
		                totalProperty: 'total'
		            },
		            simpleSortMode: true
		        }
			});
		    
			app_store.load({
		    	params:{
		    		start: 0,
	        		limit: 20
	        	}
		    });
		    
			var app_grid = Ext.create('Ext.grid.Panel',{
				store: app_store,
				id:'app_grid',
				//title:'应用列表',
				border:false,
				//grid可复制
				viewConfig:{  
				   enableTextSelection:true  
				}, 
				columnLines:true,
				columns:[
					{xtype: 'rownumberer',text: '序号',align:'center',width:40},
					{text:'<center>应用ID</center>',width:240,dataIndex:'id'},
					{text:'<center>应用名称</center>',width:240,dataIndex:'name'},
					{text:'<center>应用状态</center>',width:80,dataIndex:'status',renderer:function(v){
						if(v == 'Ready'){
							return '待上线';
						}else if(v == 'Online'){
							return  '上线';
						}else if(v == 'Offline'){
							return  '下线';
						}else if(v == 'Invalid'){
							return  '废弃';
						}else{
							return '待上线';
						}
					}},
					{text:'上线日期',align:'center',hidden:true,width:80,dataIndex:'onlineDate',renderer:function(v){
						return v.substr(0,10);
					}},
					{text:'下线日期',align:'center',hidden:true,width:80,dataIndex:'offlineDate',renderer:function(v){
						return v.substr(0,10);
					}},
					{text:'<center>域名地址</center>',width:160,dataIndex:'website'},
					{text:'<center>类型</center>',width:80,dataIndex:'type',renderer:function(v,rec){
						if(v in appMap){
							return appMap[v];
						}else{
							return v;
						}
					}},
					{text:'<center>操作</center>',width:400,renderer:function(v,cls,record){
						var appId =record.data.id;
						var name = record.data.name;
						return '<div><input type="button" value="修&nbsp;改" style="margin:0px 5px 0px 5px;cursor:pointer;" onclick="addOrUpdateAppInfo(\''+appId+'\')"  class="enableCls">'+
							'<input type="button" value="删&nbsp;除" style="margin:0px 5px 0px 0px;cursor:pointer;"  onclick="deleteAppInfo()"  class="enableCls">'+
							'<input type="button" value="模块管理" style="margin:0px 5px 0px 0px;cursor:pointer;"  onclick="ModuleManager(\''+appId+'\',\''+name+'\')"  class="enableCls">'+
							'<input type="button" value="功能管理" style="margin:0px 5px 0px 0px;cursor:pointer;"  onclick="functionManager()"  class="enableCls">'+
							'<input type="button" value="数据权限管理" style="margin:0px 5px 0px 0px;cursor:pointer;"  onclick="dataPrivilegeManager(\''+appId+'\')"  class="enableCls"></div>'
					}}
				],
				selModel: { //选中模型 
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
		        },
		        tbar:[{
		        	text:'增加',
		        	iconCls:'',
		        	handler:function(){
		        		addOrUpdateAppInfo('');
		        	}
		        },{
		        	text:'删除',
		        	iconCls:'',
		        	handler:function(){
		        		deleteAppInfo();
		        	}
		        },{
		        	text:'刷新系统缓存',
		        	iconCls:'',
		        	handler:function(){
		        		var grid = Ext.getCmp("app_grid");
		    			var selRows = grid.getSelectionModel().getSelection();
		    			if(selRows.length == 0){
		    				Ext.Msg.alert("提示", "请选择要刷新缓存的应用系统！");
		    				return;
		    			}
		    			
		    			if(selRows.length > 1){
		    				Ext.Msg.alert("提示", "请选择一个应用系统刷新缓存！");
		    				return;
		    			}
		    			
		    			if(selRows[0].data.type != "BackEndSrv"){
		    				Ext.Msg.alert("提示", "只能刷新后台服务应用的缓存！");
		    				return;
		    			}
		    			
		    			refreshCache(selRows[0].data.website);
		        	}
		        },{
		        	text:'刷新权限缓存',
		        	iconCls:'',
		        	hidden:true,
		        	handler:function(){
		        		var grid = Ext.getCmp("app_grid");
		    			var selRows = grid.getSelectionModel().getSelection();
		    			if(selRows.length == 0){
		    				Ext.Msg.alert("提示", "请选择要刷新权限缓存的应用系统！");
		    				return;
		    			}
		    			
		    			if(selRows.length > 1){
		    				Ext.Msg.alert("提示", "请选择一个应用系统刷新权限缓存！");
		    				return;
		    			}
		    			
		    			if(selRows[0].data.type != "BackEndSrv"){
		    				Ext.Msg.alert("提示", "只能刷新后台服务应用的权限缓存！");
		    				return;
		    			}
		    			
		    			refreshResource(selRows[0].data.website);
		        	}
		        }],
				multiSelect: true,//是否允许多选
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: app_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }]
			})
			return app_grid;
		}
		
		
		Ext.create("Ext.Viewport",{
			layout: {
	            type: 'border',
	            padding: 3
	        },
	        defaults: {
	            split: true
	        },
	        items:[{
	        	region:'north',
	        	minHeight:55,	//最小高度
	        	maxHeight:55,	//最大高度
	        	height:55,	
        		border:false,	//是否有边框
	        	layout:'fit',	//布局方式
	        	items:[{
	        		xtype:'form',
	        		frame:true,
	        		cls:'x-plain',
	        		id:'app-search-form',
	        		layout:'hbox',
	        		items:[{
	        			xtype:'textfield',
	        			fieldLabel:'应用名称',
	        			name:'name',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:280
	        		},{
	        			xtype:'combobox',
	        			labelWidth:70,
	        			name:'status',
	        			labelAlign:'right',
	        			margin:'5px 0px 5px 0px',
	        			width:160,
	        			displayField: 'local',
					    valueField: 'name',
					    store:Ext.create("Ext.data.Store",{
					    	fields: ['local', 'name'],
					    	data: appStatusData
					    }),
	        			fieldLabel:'应用状态'
	        		},
	        		/*{
	        			xtype:'datefield',
	        			fieldLabel:'上线日期',
	        			name:'onlineDate',
	        			format:'Y-m-d',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:180
	        		},{
	        			xtype:'datefield',
	        			fieldLabel:'下线日期',
	        			format:'Y-m-d',
	        			name:'offlineDate',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:180
	        		},*/{
	        			xtype:'button',
	        			width:80,
	        			margin:'5px 10px 5px 10px',
	        			text:'查   询',
	        			handler:function(){
	        				doQuery();
	        			}
	        		},{
	        			xtype:'button',
	        			width:80,
	        			margin:'5px 10px 5px 10px',
	        			text:'重置',
	        			handler:function(){
	        				reset();
	        			}
	        		},{
	        			xtype:'button',
	        			hidden: true,
	        			width:150,
	        			margin:'5px 10px 5px 10px',
	        			text:'PORTAL系统数据缓存刷新',
	        			handler:function(){
	        				refreshCache();
	        			}
	        		}]
	        	}]
	        },{
	        	region:'center',
	        	layout:'fit',
	        	items:[CreateAppGrid()]
	        }]
		})
	})
	//重置
	function reset(){
		var form = Ext.getCmp("app-search-form").getForm();
		form.reset();
	}
	//查询
	function doQuery(){
		var params = Ext.ux.FormUtils.getDataObject(Ext.getCmp('app-search-form'));
		var store = Ext.getCmp('app_grid').getStore();
		store.getProxy().extraParams = params;
		store.loadPage(1);
	}
	//刷新数据
	function refreshCache(url){
		Ext.Msg.confirm("提示","请确认是否刷新该应用系统的数据缓存",function(btn){
			if(btn == 'yes'){
				showWaitMsg("正在刷新系统缓存数据，请稍后……");
				Ext.Ajax.request({
					method:'post',
					url: url + "/service/rest/tk.Cache/collection/refreshCache",
					success:function(resp, opts){
						hideWaitMsg();
						Ext.Msg.alert("提示",'缓存刷新成功！');
					},
					failure:function(resp,opts){
						hideWaitMsg();
						Ext.Msg.alert("提示",'缓存刷新失败：' + resp.responseText);
					}
				});
			}
		});
	}
	
	//刷新权限
	function refreshResource(url){
		Ext.Msg.confirm("提示","请确认是否刷新该应用系统的权限缓存",function(btn){
			if(btn == 'yes'){
				showWaitMsg("正在刷新系统缓存数据，请稍后……");
				Ext.Ajax.request({
					method:'post',
					url: url + "/service/rest/us.Resource/collection/initUserAuthUrl",
					success:function(resp, opts){
						hideWaitMsg();
						Ext.Msg.alert("提示",'缓存刷新成功！');
					},
					failure:function(resp,opts){
						hideWaitMsg();
						Ext.Msg.alert("提示",'缓存刷新失败：' + resp.responseText);
					}
				});
			}
		});
	}
</script>
</body>
</html>