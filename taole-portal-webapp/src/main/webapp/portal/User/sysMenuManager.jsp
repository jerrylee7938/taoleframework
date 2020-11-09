<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%><%@include file="/include/page.jspf" %><html>
<head>
	<jsp:include page="/include/header.jsp"></jsp:include>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
<body>
<script type="text/javascript">
	Ext.onReady(function(){
		
		var entityId = null;
		var entityName = 'us.Menu';
		var entityAction = 'create'; 
		var treeStore = null;
		//执行新增或修改操作
		function addOrUpdateMenuInfo(bool){
			if (bool) {
				var selRows = Ext.getCmp("menu_tree").getSelectionModel().getSelection();
				var id = selRows[0].data.id;
				entityId = id;
			} else {
				entityId = null;
			}
			var form = Ext.getCmp("menu_form");
			var menu_win = Ext.getCmp('menu_win');
			if(form.getForm().isValid()){
				var data = form.getForm().getValues();//Ext.ux.FormUtils.getDataObject(form);
				var url ="";
				if (entityId) {
					data.id = entityId;
					url = $service.portal+'/us.Menu/'+entityId+'/update';
				}else{
					data.id ="";
					url =$service.portal+'/us.Menu/collection/create';
					
				}
				var parentNode = form.getForm().findField("parentNode");
				if(data.enable ==null){
					data.enable = true;
				}
				data.parentNode = menu_win.itemId;
				data.entityName ="us.Menu";
				Ext.Ajax.request({
					url:url,
					method:'post',
					jsonData: data,
					success:function(resp, opts){
						var r = eval("(" + resp.responseText + ")");
						if (r.code != 1) {
							Ext.Msg.alert("提示",'保存失败：' + r.description);
						} else {
							menu_win.hide();
							Ext.getCmp('menu_tree').getStore().load();
						}
					},
					failure:function(resp, opts){
						Ext.Msg.alert("提示",'保存失败：' + resp.responseText);
					}
				})
				
			}
		}
		
		function showMenuAction(){
			var menu_action = Ext.getCmp("menu_action");
			if(!menu_action){
				menu_action = Ext.create('Ext.window.Window',{
					title:'菜单功能选择窗口',
					width:750,
					height:450,
					layout:'border',
					id:'menu_action',
					border:false,
					items:[{
						region:'north',
						xtype:'form',
						layout:'vbox',
						itemId:'',
						id:'menu_select_form',
						height:120,
						frame:true,
						defaults:{
							labelAlign:'right',
							labelWidth:100,
							width:250,
							xtype:'combobox'
						},
						style:{
							'border':'1px 1px 0px 1px'
						},
						items:[{
							name:'appId',
							displayField:'name',
							valueField:'id',
							mode: 'local',
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
							listeners:{
								select:function(combox,rec){
									var form = Ext.getCmp("menu_select_form").getForm();
									form.findField("moduleName").setValue("");
								}
							},
							editable:false,
							fieldLabel:'应用系统'
						},{
							xtype:'trigger',
							name:'moduleName',
							editable:false,
							onTriggerClick:function(){
								var form = Ext.getCmp("menu_select_form").getForm();
								var id = form.findField("appId").getValue();
								var pModule_win = Ext.getCmp("p_m_win");
								if(!pModule_win){
									pModule_win= Ext.create("Ext.window.Window",{
										id:'p_m_win',
										title:'模块选择',
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
													var form = Ext.getCmp("menu_select_form");
													var moduleId = record.data.id;
													form.itemId = moduleId;
													form.getForm().findField("moduleName").setValue(record.data.text);
													pModule_win.destroy();
													var grid = Ext.getCmp("url_grid");
													grid.getStore().removeAll();
													var functionCom = form.getForm().findField("functionName");
													functionCom.setValue("");
													$.ajax({
														type:'GET',
														url:$service.portal+'/us.Function/collection/query?moduleId='+moduleId,
														dataType:'json',
														async:false,
														success:function(data){
															if(data){
																var items = data.items;
																functionCom.clearValue();
																var array =[];
																for(var i = 0; i< items.length; i++){
																	var item = items[i];
																	array.push([item.id,item.name]);
																}
																functionCom.store.loadData(array);
															}
														}
													})
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
													var form = Ext.getCmp("menu_select_form");
													var moduleId = selNode.data.id;
													form.itemId = moduleId;
													form.getForm().findField("moduleName").setValue(selNode.data.text);
													pModule_win.destroy();
													var grid = Ext.getCmp("url_grid");
													grid.getStore().removeAll();
													var functionCom = form.getForm().findField("functionName");
													functionCom.setValue("");
													var functionStore = functionCom.getStore();
													functionStore.proxy.url = $service.portal+'/us.Function/collection/query?moduleId='+moduleId;
													functionStore.load();
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
							fieldLabel:'模块名称'
						},{
							fieldLabel:'功能名称',
							name:'functionName',
							displayField:'name',
							valueField:'id',
							queryMode: 'local',
							triggerAction: 'all',
							store: Ext.create('Ext.data.Store',{
								fields:['id','name'],
								pageSize:1000,
								proxy: {
									type: 'ajax',
									url:'',
									reader: {
										root: 'items',
										totalProperty: 'total'
									},
									simpleSortMode: true
								}
							}),
							listeners:{
								select:function(combox,rec){
									var form = Ext.getCmp("menu_select_form").getForm();
									var functionId = rec[0].data.id;
									var store = Ext.getCmp("url_grid").getStore();
									store.proxy.url = $service.portal+'/us.Resource/collection/query?functionId='+functionId;
									store.load();
								}
							},
							editable:false
						},{
							xtype:'button',
							width:100,
							hidden:true,
							style:{
								'margin':'0px 0px 0px 5px'
							},
							text:'查询',
							handler:function(){
								
							}
						}]
					},{
						xtype:'grid',
						region:'center',
						id:'url_grid',
						style:{
							'border':'0px'
						},
						store: Ext.create('Ext.data.Store',{
							fields:['id','name','functionId','type','uri','entityName','actionName'],
							autoLoad:false,
							pageSize:20,
							proxy: {
								type: 'ajax',
								url:$service.portal + '/us.Resource/collection/query?functionId=',
								reader: {
									root: 'items',
									totalProperty: 'total'
								},
								simpleSortMode: true
							}
						}),
						selModel: { //选中模型 
							selType : 'checkboxmodel',//复选框选择模式Ext.selection.CheckboxModel 
							mode: 'SINGLE'
						},
						columnLines:true,
						columns:[{
							xtype: 'rownumberer',text: '序号',align:'center',width:40
						},{
							text:'URL',width:340,dataIndex:'uri'
						},{
							text:'描述',width:240,dataIndex:'name'
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'确定',
						handler:function(){
							var grid = Ext.getCmp("url_grid");
							var selRows = grid.getSelectionModel().getSelection();
							var uri ="";
							var actionName="";
							if(selRows.length > 0){
								uri = selRows[0].data.id;
								actionName = selRows[0].data.name;
							}
							var form = Ext.getCmp("menu_form").getForm();
							form.findField("action").setValue(uri);
							form.findField("actionName").setValue(actionName);
							menu_action.hide();
						}
					},{
						text:'取消',
						handler:function(){
							menu_action.hide();
						}
					}]
				})
			}
			menu_action.show();
			var form = Ext.getCmp("menu_form");
			var action = form.getForm().findField("action").getValue()||'';
			var menu_form = Ext.getCmp("menu_select_form");
			menu_form.getForm().reset();
			var msgTip;
			if(action!=''){
				var store = Ext.getCmp("url_grid").getStore();
				store.removeAll();
				msgTip = Ext.MessageBox.show({
                   title:'提示',
				   width : 250,
                   msg:'数据在加载中,请稍后......'
                });
				
				Ext.Ajax.request({
					url:$service.portal+'/us.Resource/'+action+'/retrieve',
					method:'POST',
					dataType:'json',
					waitMsg:'数据加载中....',
					success:function(resp){
						var text = resp.responseText;
						var data = eval("("+text+")");
						if(data){
							var functionId = data.functionId||'';
							store.proxy.url = $service.portal+'/us.Resource/collection/query?functionId='+functionId;
							store.load();
							
							var moduleId ,moduleName,appName,appId;
							Ext.Ajax.request({
								url:$service.portal+'/us.Function/'+functionId+'/retrieve',
								method:'POST',
								dataType:'json',
								async:false,
								success:function(resp){
									var text = resp.responseText;
									var data = eval("("+text+")");
									if(data){
										moduleId = data.moduleId;
										var form = Ext.getCmp("menu_select_form");
										form.itemId = moduleId;
										var functionCom = form.getForm().findField("functionName");
										functionCom.setValue("");
										$.ajax({
											type:'GET',
											url:$service.portal+'/us.Function/collection/query?moduleId='+moduleId,
											dataType:'json',
											async:false,
											success:function(data){
												if(data){
													var items = data.items;
													functionCom.clearValue();
													var array =[];
													for(var i = 0; i< items.length; i++){
														var item = items[i];
														array.push([item.id,item.name]);
													}
													functionCom.store.loadData(array);
													functionCom.setValue(functionId);
												}
											}
										})
										$.ajax({
											type:'GET',
											url:$service.portal+'/us.Module/'+moduleId+'/retrieve',
											dataType:'json',
											async:false,
											success:function(data){
												if(data){
													moduleName = data.name;
													var form = Ext.getCmp("menu_select_form");
													form.itemId = moduleId;
													form.getForm().findField("moduleName").setValue(moduleName);
													
													form.getForm().findField("appId").getStore().load();
													appId = data.applicationId;
													form.getForm().findField("appId").setValue(appId);
													msgTip.hide();
												}
											}
										})
									}
								}
							})
						}
					},
					failure:function(resp){
						Ext.Msg.alert('提示','获取菜单列表失败!');
					}
				});
			}
		}
		
		//新增或修改菜单
		function addOrEditSysMenu(id){
			var title = id ==''?'新增':'修改';
			var readonly = id !=''?false:true;
			var menu_win = Ext.getCmp('menu_win');
			
			var parentId,parentName;
			if(id !=''){
				var node = Ext.getCmp("menu_tree").getStore().getNodeById(id).parentNode;
				if(node){
					parentId = node.data.id;
					parentName =node.data.text;
				}
			}else{
				var node = Ext.getCmp("menu_tree").getSelectionModel().getSelection()[0];
				parentId = node.data.id;
				parentName = node.data.text;
			}
			if(!menu_win){
				menu_win = Ext.create('Ext.window.Window',{
					height:370,
					width:530,
					id:'menu_win',
					modal:true,
					itemId:'',
					title:title+'菜单',
					layout:'fit',
					items:[{
						xtype:'form',
						layout:'anchor',
						id:'menu_form',
						itemId:parentId,
						margin:'5px',
						frame:true,
						style:{
							'border':'0px'
						},
						items:[{
							xtype:'combobox',
						    //enumType: 'com.taole.usersystem.domain.Menu$Type',
							fieldLabel:'菜单类型',
							labelWidth:80,
							name:'type',
							value:'Custom',
							labelAlign:'right',
							anchor:'90%',
							store: getDicStore('f9fac24aa4ed41be84a851d65e8dde75'),
							displayField: 'name',
							valueField: 'value'
						},{
							xtype:'treepicker',
							displayField: 'text',
							fieldLabel: '上级菜单',  
							labelAlign:'right',
							name:'parentNode',
							autoScroll:true,
							baseHeight:300,
							labelWidth:80,
							rootVisible:false,
						    forceSelection : true,// 只能选择下拉框里面的内容  
					        editable: false,// 不能编辑  
						    store: Ext.create('Ext.data.TreeStore', {//树的数据源store
								autoLoad:true,
								fields:[{name : 'id',  type : 'string'},  
							        	{name : 'text', type : 'string',mapping:'name'
							        	}],
					        	proxy : {//代理
						            type : 'ajax',
						            url :$service.portal+'/us.Menu/collection/alltree'
						        },
								root: {
							        expanded: true
							    }
						    }),
						    listeners:{
						    	select:function(field,record){
									var menu_win = Ext.getCmp('menu_win');
									menu_win.itemId = record.data.id;
						    	}
						    },
						    anchor:'90%'
						},{
							xtype:'textfield',
							fieldLabel:'菜单名称',
							labelWidth:80,
							name:'name',
							allowBlank:false,
							blankText:'请输入菜单名称',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'textarea',
							fieldLabel:'菜单描述',
							labelWidth:80,
							height:50,
							name:'description',
							labelAlign:'right',
							anchor:'90%'
						},{
					        xtype: 'radiogroup',
					        name: 'resultGroup',
					        fieldLabel: '菜单跳转方式',
					        labelWidth:80,
					        labelAlign:'right',
					        columns: 2,
					        width: 300,
					        items: [
					            { boxLabel: '通过功能跳转', name: 'result', inputValue: 'Y', checked: true},
					            { boxLabel: '通过URL跳转', name: 'result', inputValue: 'N'}
					        ],
					        listeners: {
					            change: function(field){
					            	var result = field.getValue().result;
					            	console.log(result)
					            	if(result == "Y"){
					            		form.findField('actionName').setDisabled(false);
					            		form.findField('action').setDisabled(false);
					            		form.findField("resCode").setDisabled(true);
					            	}else if(result == "N"){
					            		form.findField('actionName').setDisabled(true);
					            		form.findField('action').setDisabled(true);
					            		form.findField("resCode").setDisabled(false);
					            	}
					            }
					        }
					    },{
							xtype:'trigger',
							fieldLabel:'选择菜单动作',
							labelWidth:80,
							name:'actionName',
							editable:false,
							labelAlign:'right',
							anchor:'90%',
							style:{
								'opacity':1
							},
							onTriggerClick:function(){
								showMenuAction();
							}
						},{
							xtype: 'textfield',
							fieldLabel: '跳转URL',
							disabled: true,
							labelWidth: 80,
							name: 'resCode',
							labelAlign: 'right',
							anchor: '90%'
						},{
							xtype:'textfield',
							fieldLabel:'备注',
							labelWidth:80,
							name:'',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'textfield',
							fieldLabel:'图标地址',
							labelWidth:80,
							name:'iconCls',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'textfield',
							hidden:true,
							name:'action'
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'保存',
						handler:function(){
							var _form = menu_win.down('form').getForm();
							if(_form.isValid()){
								var name = _form.findField("name").getValue();
								var root = Ext.getCmp("menu_tree").store.getRootNode();
								var tmp_title = menu_win.title;
								var bool = false;
								if(tmp_title.indexOf('新增')!=-1){
									bool = false;
								}else{
									bool = true;
								}
								var node =  Ext.getCmp("menu_tree").getSelectionModel().getSelection()[0];
								var cur_node =  node.findChild('text',name,true);
								if(cur_node && cur_node.data.id != node.data.id){
									Ext.Msg.alert("提示","该菜单名称已存在,请重新输入!");
									_form.findField("name").setValue(node.data.name);
									return false;
								}
							    addOrUpdateMenuInfo(bool);
							}
						}
					},{
						text:'取消',
						handler:function(){
							menu_win.destroy();
						}
					}]
				})
			}
			menu_win.show();
			menu_win.setTitle(title+'菜单');
			menu_win.itemId = parentId;
			var form = menu_win.down('form').getForm();
			form.reset();
			
			if(id!=''){
				var treeStore = Ext.getCmp('menu_tree').getStore();
				var node = treeStore.getNodeById(id);
				form.loadRecord(node);
				form.findField('name').setValue(node.data.text);	
				form.findField('parentNode').setRawValue(parentName);
				Ext.Ajax.request({
					url:$service.portal+'/us.Menu/'+id+'/retrieve',
					method:'POST',
					dataType:'json',
					success:function(resp){
						var text = resp.responseText;
						var data = eval("("+text+")");
						if(data){
							form.findField("action").setValue(data.action);
							form.findField("actionName").setValue(data.actionName);
							form.findField("resCode").setValue(data.resCode);
							var radios = new Array();
							if(data.resCode){
								radios.push({ boxLabel: '通过功能跳转', name: 'result', inputValue: 'Y'});
								radios.push({ boxLabel: '通过URL跳转', name: 'result', inputValue: 'N', checked: true});
								
								form.findField('actionName').setDisabled(true);
			            		form.findField('action').setDisabled(true);
			            		form.findField("resCode").setDisabled(false);
			            		form.findField("resultGroup").removeAll();
								form.findField("resultGroup").add(radios);
							}else if(data.action){
								radios.push({ boxLabel: '通过功能跳转', name: 'result', inputValue: 'Y', checked: true});
								radios.push({ boxLabel: '通过URL跳转', name: 'result', inputValue: 'N'});
								
								form.findField('actionName').setDisabled(false);
			            		form.findField('action').setDisabled(false);
			            		form.findField("resCode").setDisabled(true);
			            		form.findField("resultGroup").removeAll();
								form.findField("resultGroup").add(radios);
							}
							
						}
					},
					failure:function(resp){
						Ext.Msg.alert('提示','获取物资详情失败!');
					}
				});
			} else {
				form.findField('parentNode').setRawValue(parentName);
			}
		}
		
		function getNodeData(nodeId){
			var data =null;
			Ext.Ajax.request({
				url:$service.portal+'/us.Menu/'+nodeId+'/retrieve',
				method:'post',
				async:false,
				success:function(resp, opts){
					data = eval("("+resp.responseText+")");
				},
				failure:function(resp,opts){
					
				}
			})
			return data;
		}
		//右击菜单
		var contextMenu = Ext.create('Ext.menu.Menu',{
	        items: [{
	        	text: '删除',	
	        	id:'delete',
	        	iconCls: 'saveIcon',
	        	handler:function(){
	        		var selRows = Ext.getCmp("menu_tree").getSelectionModel().getSelection();
	        		if(selRows.length > 0){
	        			Ext.Msg.confirm("提示","确定要删除选中的菜单节点?",function(btn){
	    					if(btn == 'yes'){
	    						var id = selRows[0].data.id;
	    	        			Ext.Ajax.request({
	    	        				url:$service.portal+'/us.Menu/'+id+'/delete',
	    	        				method:'post',
	    	        				success:function(resp, opts){
	    	        					var r = eval("(" + resp.responseText + ")");
	    								if (r.code != 1) {
	    									Ext.Msg.alert("提示",'删除失败：' + r.description);
	    								} else {
	    									Ext.getCmp('menu_tree').getStore().load();
	    								}
	    	        				},
	    	        				failure:function(resp, opts){
	    	        					Ext.Msg.alert("提示",'删除失败：' + resp.responseText);
	    	        				}
	    	        			})
    						}
    					});
	        			
	        		}else{
	        			Ext.Msg.alert("提示","请选择要修改的记录!");
	        		}
	        	}
	        },{
	        	text: '增加',			//菜单项文本
	        	id:'add',
	        	iconCls: 'addIcon',		//菜单项图标
	        	handler: function(){
	        		addOrEditSysMenu('');
	        	}
	        },{
	        	text: '修改',			//菜单项文本
	        	id:'edit',
	        	iconCls: 'addIcon',		//菜单项图标
	        	handler: function(){
	        		var selRows = Ext.getCmp("menu_tree").getSelectionModel().getSelection();
	        		if(selRows.length > 0){
	        			var id = selRows[0].data.id;
	        			addOrEditSysMenu(id);
	        		}else{
	        			Ext.Msg.alert("提示","请选择要修改的记录!");
	        		}
	        	}
	        },{
	        	text:'上升',
	        	id:'up',
	        	handler:function(){
	        		var tree = Ext.getCmp("menu_tree");
	        		var selRows = tree.getSelectionModel().getSelection();
	        		if(selRows.length > 0){
	        			var id = selRows[0].data.id;
	        			Ext.Ajax.request({
	        				url:$service.portal+'/us.Menu/'+id+'/updown?offset=-1',
	        				method:'post',
	        				success:function(resp, opts){
	        					var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'上升失败：' + r.description);
								} else {
									tree.getStore().load();
								}
	        				},
	        				failure:function(resp, opts){
	        					Ext.Msg.alert("提示",'上升失败：' + resp.responseText);
	        				}
	        			})
	        		}else{
	        			Ext.Msg.alert('提示','请选择需要调整的节点!');
	        		}
	        	}
	        },{
	        	text:'下降',
	        	id:'down',
	        	handler:function(){
	        		var tree = Ext.getCmp("menu_tree");
	        		var selRows = tree.getSelectionModel().getSelection();
	        		if(selRows.length > 0){
	        			var id = selRows[0].data.id;
	        			Ext.Ajax.request({
	        				url:$service.portal+'/us.Menu/'+id+'/updown?offset=1',
	        				method:'post',
	        				success:function(resp, opts){
	        					var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'下降失败：' + r.description);
								} else {
									tree.getStore().load();
								}
	        				},
	        				failure:function(resp, opts){
	        					Ext.Msg.alert("提示",'下降失败：' + resp.responseText);
	        				}
	        			})
	        		}else{
	        			Ext.Msg.alert('提示','请选择需要调整的节点!');
	        		}
	        	}
	        },{
	        	text:'重命名',
	        	id:'rename',
	        	handler:function(){
	        		var rename_win = Ext.getCmp("rename_win");
	        		if(!rename_win){
	        			rename_win =Ext.create("Ext.window.Window",{
	        				height:120,
	        				width:280,
	        				title:'重命名',
	        				items:[{
	        					xtype:'textfield',
	        					fieldLabel:'菜单名称',
	        					labelWidth:60,
	        					margin:10,
	        					labelAlign:'right',
	        					id:'menu_name',
	        					width:200
	        				}],
	        				buttons:[{
	        					text:'保存',
	        					handler:function(){
	        						//var tree = Ext.getCmp('tree_menu');
	        						//var node = tree.getSelectionModel().getSelection()[0];
	        						var dataObj = getNodeData(entityId);
	        						//var node = treeStore.getNodeById(entityId);
	        						//var data = node.data;
	        						var name = Ext.getCmp("menu_name").getValue();
	        						dataObj.name = name;
	        						Ext.Ajax.request({
	        							url:$service.portal+'/us.Menu/'+entityId+'/update',
	        							method:'post',
	        							jsonData:dataObj,
	        							success:function(resp, opts){
	        								var r = eval("(" + resp.responseText + ")");
	        								if (r.code != 1) {
	        									Ext.Msg.alert("提示",'重命名失败：' + r.description);
	        								} else {
	        									rename_win.destroy();
	        									Ext.getCmp("menu_tree").getStore().load();
	        								}
	        							},
	        							failure:function(resp, opts){
	        								Ext.Msg.alert("提示",'重命名失败：' + resp.responseText);
	        							}
	        						})
	        						
	        						
	        					}
	        				},{
	        					text:'取消',
	        					handler:function(){
	        						rename_win.destroy();
	        					}
	        				}]
	        			})
	        		}
	        		rename_win.show();
	        		
	        		var tree = Ext.getCmp("menu_tree");
	        		var node = tree.getSelectionModel().getSelection()[0];
	        		Ext.getCmp("menu_name").setValue(node.raw.name);
	        	}
	        }]
	    });	
		
		function setMenuDisableOrEnable(bool){
			Ext.getCmp('delete').setDisabled(bool);
			Ext.getCmp('edit').setDisabled(bool);
			Ext.getCmp('down').setDisabled(bool);
			Ext.getCmp('up').setDisabled(bool);
			Ext.getCmp('rename').setDisabled(bool);
		}
		var rootMenu= Ext.create('Ext.menu.Menu',{
			items:[{
				text:'增加菜单树',
				handler:function(){
					
				}
			}]
		})
		//创建菜单树
		function createMenuTree(){
			Ext.define('treeModel', {//树的数据模型
		        extend : 'Ext.data.Model',//继承自Model
		        fields : [//字段
		        	{name : 'id',  type : 'string'},  
		        	{name : 'text', type : 'string',mapping:'name'},
		        	{name :'nodetype',type : 'string'},
		        	{name :'enable',type : 'string'},
		        	{name : 'action',type: 'string'},
		        	{name : 'description',type: 'string'},
		        	{name : 'iconCls',type: 'string'},
		        	{name : 'attributes',type: 'string'},
		        	{name : 'entityName',type: 'string'},
		        	{name:'type',type:'string'}
		    	],
				proxy : {//代理
		            type : 'ajax',
		            url :$service.portal+'/us.Menu/collection/alltree'
		        }
		    });
			var treeStore = Ext.create('Ext.data.TreeStore', {//树的数据源store
				model : treeModel,//数据模型
				root: {
			        expanded: true,
			        text:'系统菜单',
			        id:''
			    }
		    });
			
			var menuTree = Ext.create('Ext.tree.Panel',{//菜单树
				id:'menu_tree',
				minWidth: 210,//最小宽度
				width: '100%',
				border:false,
				store : treeStore,//数据源
				autoHeight : true,//是否自动适应高度
				animate: false,//是否有动画效果
				rootVisible: false,//根节点是否可见
				viewConfig:{
					plugins: {
	                    ptype: 'treeviewdragdrop',
	                    allowLeafInserts: true
	                },
	                listeners:{
	                	drop:function(node, data, overModel, dropPosition){
	                		var parentNode;
	                		if(dropPosition == 'before' || dropPosition == 'after'){
	                			parentNode = overModel.parentNode;
	                		}else{
	                			parentNode = overModel;
	                		}
	                		var index =0;
	                		var drugNode = data.records[0];
	                		for(var _node = drugNode.previousSibling ; _node !=null ; _node = _node.previousSibling){
	                			console.log(_node);
	                			index++;
	                		}
	                		console.log(index);
	                		var parentId = parentNode.data.id;
	                		Ext.Ajax.request({
    							url:$service.portal+'/us.Menu/'+drugNode.data.id+'/move?parent='+parentId+'&index='+index,
    							method:'post',
    							success:function(resp, opts){
    								var r = eval("(" + resp.responseText + ")");
    								if (r.code != 1) {
    									Ext.Msg.alert("提示",'菜单拖拽失败：' + r.description);
    								} 
    							},
    							failure:function(resp, opts){
    								
    							}
    						})
	                	}
	                }
				},
				listeners: {
		            itemmouseenter:function(view, record, item, index, e){//为tree添加鼠标悬停事件
					},
					itemcontextmenu:function(tree, record, item, index, e){//注册tree的右键菜单
						e.preventDefault();
						menuTree.getSelectionModel().select(record);//选择当前树节点
		    			entityId = record.data.id;
						var root = tree.ownerCt.getRootNode();
						var parentNode = record.parentNode;
						contextMenu.showAt(e.getXY());
						if(record.get('id') == 'SYS_ROOT'){
							setMenuDisableOrEnable(true);
						}else{
							setMenuDisableOrEnable(false);
						}
						//if(root == record){
						//	rootMenu.showAt(e.getXY());
						//}else{
							
						//}
						
		    		}
				}
			});
			menuTree.expandAll();
			return menuTree;
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
	        	region:'center',
	        	layout:'fit',
	        	border:false,
	        	items:[createMenuTree()]
	        }]
		})
	})
	
	function doQuery(){
	}
</script>
</body>
</html>