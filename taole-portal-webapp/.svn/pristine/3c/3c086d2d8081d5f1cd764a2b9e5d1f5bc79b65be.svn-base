Ext.onReady(function(){
	
	function showMenuAction(){
		var menu_action = Ext.getCmp("menu_action");
		if(!menu_action){
			menu_action = Ext.create('Ext.window.Window',{
				title:'功能选择窗口',
				width:750,
				height:450,
				layout:'border',
				id:'menu_action',
				border:false,
				items:[{
					region:'north',
					xtype:'form',
					layout:'absolute',
					baseCls:'x-plain',
					itemId:'',
					id:'menu_select_form',
					height:90,
					frame:true,
					defaults:{
						labelAlign:'right',
						labelWidth:100,
						width:250,
						xtype:'combobox'
					},
					margins:'5 0 0 0',
					items:[{
						name:'appId',
						x:0,
						y: 5,
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
						x:0,
						y: 30,
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
						x:253,
						y: 30,
						xtype: 'panel', 
						frame: true, 
						style: 'margin-left:5px;margin-top:5px;font-size:12px;', 
						baseCls: 'x-plain',
						html: '<font color=red>注意：只能选择"桌面快捷管理"模块</font>'
					},{
						fieldLabel:'功能名称',
						name:'functionName',
						displayField:'name',
						valueField:'id',
						queryMode: 'local',
						triggerAction: 'all',
						x:0,
						y: 55,
						store: Ext.create('Ext.data.Store',{
							fields:['id','name'],
							data:[]
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
						selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
					},
					multiSelect: false,//是否允许多选
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
						if(selRows.length==0){
							Ext.Msg.alert('提示','请选择桌面项地址！');
							return;
						}
						if(selRows.length>1){
							Ext.Msg.alert('提示','只能选择一个桌面项地址！');
							return;
						}
						
						var form = Ext.getCmp("desktop_form").getForm();
						form.findField("urlName").setValue(selRows[0].data.name);
						form.findField("resCode").setValue(selRows[0].data.id);
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
		var form = Ext.getCmp("desktop_form");
		var action = form.getForm().findField('resCode').getValue();
		var menu_form = Ext.getCmp("menu_select_form");
		menu_form.getForm().reset();
		var msgTip;
		if(action){
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
	function addOrEditDesktop(desktopId){
		var disabled = true;
		var node = Ext.getCmp('desktop_tree').getSelectionModel().getSelection()[0];
		if(!desktopId){
			if(node.data.type == 3){
				Ext.Msg.alert('提示','桌面项仅可维护3层，不能继续建立子桌面项!');
				return;
			}
			if(node.data.type == 2){
				disabled = false;
			}
			if(node.data.type == 1){
				disabled = true;
			}
		}else{
			if(node.data.type == 3){
				disabled = false;
			}else{
				disabled = true;
			}
		}
		var title = !desktopId?'新增':'修改';
		var menu_win = Ext.getCmp('addorEdidDesktop_win');
		if(!menu_win){
			menu_win = Ext.create('Ext.window.Window',{
				height:180,
				width:530,
				id:'addorEdidDesktop_win',
				modal:true,
				title:(desktopId?'修改':'新增')+'桌面项',
				layout:'fit',
				items:[{
					xtype:'form',
					layout:'anchor',
					id:'desktop_form',
					margin:'5px',
					frame:true,
					style:{
						'border':'0px'
					},
					items:[{
						xtype:'hidden',
						name:'id'
					},{
						xtype:'textfield',
						fieldLabel: '<font color=red>*</font>上级名称',  
						labelAlign:'right',
						name:'parentName',
						baseHeight:300,
						labelWidth:80,
						readOnly:true,
						allowBlank: false,
					    anchor:'90%',
					    fieldStyle:"background-color:#EEEEE0;background-image: none;"
					},{xtype:'hidden', name:'father'},
					{
						xtype:'textfield',
						fieldLabel:'<font color=red>*</font>桌面项名称',
						labelWidth:80,
						name:'name',
						allowBlank:false,
						labelAlign:'right',
						anchor:'90%'
					},{
						xtype:'trigger',
						fieldLabel:'<font color=red>*</font>桌面功能',
						labelWidth:80,
						name:'urlName',
						editable:false,
						allowBlank: false,
						disabled: disabled,
						labelAlign:'right',
						anchor:'90%',
						style:{
							'opacity':1
						},
						onTriggerClick:function(){
							showMenuAction();
						}
					},{
						xtype:'hidden',
						name:'resCode'
					}]
				}],
				buttonAlign:'center',
				buttons:[{
					text:'保存',
					handler:function(){
						if(!Ext.getCmp('desktop_form').getForm().isValid())return;
						var jsonData = Ext.getCmp('desktop_form').getForm().getValues();
						var url;
						if(jsonData.id){
							url = Ext.util.Format.format(URL_USER_DESKTOP_UPDATE,jsonData.id);
						}else{
							url = URL_USER_DESKTOP_CREATE;
						}
						delete jsonData.url;
						Ext.Ajax.request({
							url: url,
							jsonData: jsonData,
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','桌面项保存成功！');
									Ext.getCmp('desktop_tree').getStore().load();
									Ext.getCmp('desktop_tree').getSelectionModel().deselectAll();
									menu_win.destroy();
								}else{
									Ext.Msg.alert('提示','桌面项保存失败!<br/>'+data.result_code+":"+data.result_desc);
								}
							},
							failure: function(){
								Ext.Msg.alert("提示", "桌面项保存失败");
							}
						});
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
		var form = Ext.getCmp("desktop_form");
		form.getForm().reset();
		if(desktopId){
			Ext.Ajax.request({
				url: Ext.util.Format.format(URL_USER_DESKTOP_GET,desktopId),
				success: function(response){
					var data = Ext.decode(response.responseText);
					if(getResp(data)){
						for(i in data){
							if(form.getForm().findField(i)){
								form.getForm().findField(i).setValue(data[i]);
							}
						}
					}else{
						Ext.Msg.alert('提示','获取桌面项详情失败!<br/>'+data.result_code+":"+data.result_desc);
					}
				},
				failure: function(){
					Ext.Msg.alert("提示", "获取桌面项详情失败");
				}
			});
			var node = Ext.getCmp('desktop_tree').getSelectionModel().getSelection()[0];
			var parentNode = node.parentNode;
			form.getForm().findField('parentName').setValue(parentNode.data.text)
		}else{
			var node = Ext.getCmp('desktop_tree').getSelectionModel().getSelection()[0];
			form.getForm().findField('parentName').setValue(node.data.text);
			form.getForm().findField('father').setValue(node.data.id);
		}
	}
	
	//右击菜单
	var contextMenu = Ext.create('Ext.menu.Menu',{
        items: [{
        	text: '删除',
        	id:'desktop_del',
        	handler:function(){
        		Ext.Msg.confirm('提示','确定要删除选择的桌面项吗？',function(btn){
        			if(btn=='yes'){
        				var node = Ext.getCmp('desktop_tree').getSelectionModel().getSelection()[0];
		        		Ext.Ajax.request({
							url: URL_USER_DESKTOP_DELETE + '?id='+ node.data.id,
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','桌面项删除成功！');
									Ext.getCmp('desktop_tree').getStore().load();
									Ext.getCmp('desktop_tree').getSelectionModel().deselectAll();
								}else{
									Ext.Msg.alert('提示','桌面项删除失败!<br/>'+data.result_code+":"+data.result_desc);
								}
							},
							failure: function(){
								Ext.Msg.alert("提示", "桌面项删除失败");
							}
						});
        			}
        		})
        	}
        },{
        	text: '增加',			//菜单项文本
        	id:'desktop_add',
        	handler: function(){
        		addOrEditDesktop();
        	}
        },{
        	text: '修改',			//菜单项文本
        	id:'desktop_update',
        	handler: function(){
        		var node = Ext.getCmp('desktop_tree').getSelectionModel().getSelection()[0];
        		addOrEditDesktop(node.data.id);
        	}
        },{
        	text:'上移',
        	id: 'desktop_up',
        	handler: function(){
        		var node = Ext.getCmp('desktop_tree').getSelectionModel().getSelection()[0];
        		Ext.Ajax.request({
					url: URL_USER_DESKTOP_UP + node.data.id + "/upMove",
					success: function(response){
						var data = Ext.decode(response.responseText);
						if(getResp(data)){
							Ext.getCmp('desktop_tree').getStore().load();
							Ext.getCmp('desktop_tree').getSelectionModel().deselectAll();
						}else{
							Ext.Msg.alert('提示','桌面项上移失败!<br/>'+data.result_code+":"+data.result_desc);
						}
					},
					failure: function(){
						Ext.Msg.alert("提示", "桌面项上移失败");
					}
				});
        	}
        },{
        	text:'下移',
        	id: 'desktop_down',
        	handler: function(){
        		var node = Ext.getCmp('desktop_tree').getSelectionModel().getSelection()[0];
        		Ext.Ajax.request({
					url: URL_USER_DESKTOP_DOWN + node.data.id + "/downMove",
					success: function(response){
						var data = Ext.decode(response.responseText);
						if(getResp(data)){
							Ext.getCmp('desktop_tree').getStore().load();
							Ext.getCmp('desktop_tree').getSelectionModel().deselectAll();
						}else{
							Ext.Msg.alert('提示','桌面项下移失败!<br/>'+data.result_code+":"+data.result_desc);
						}
					},
					failure: function(){
						Ext.Msg.alert("提示", "桌面项下移失败");
					}
				});
        	}
        }]
    });	
	
	//创建菜单树
	function createDestopTree(){
		Ext.define('treeModel', {//树的数据模型
	        extend : 'Ext.data.Model',//继承自Model
	        fields : [//字段
	        	{name : 'id',  type : 'string'},  
	        	{name : 'text', type : 'string'},
	        	{name :'leaf',type : 'boolean'},
	        	{name :'type',type : 'string'},
	        	{name : 'resCode',type: 'string'},
	        	{name : 'url',type: 'string'}
	    	]
	    });
		var treeStore = Ext.create('Ext.data.TreeStore', {//树的数据源store
			model : treeModel,//数据模型
			proxy : {//代理
	            type : 'ajax',
	            url :URL_USER_DESKTOP_QUERY
	        }
	    });
		
		var desktopTree = Ext.create('Ext.tree.Panel',{//菜单树
			id:'desktop_tree',
			minWidth: 210,//最小宽度
			width: '100%',
			border:false,
			store : treeStore,//数据源
			autoHeight : true,//是否自动适应高度
			animate: false,//是否有动画效果
			rootVisible: false,//根节点是否可见
			listeners: {
				itemcontextmenu:function(tree, record, item, index, e){//注册tree的右键菜单
					e.preventDefault();
					desktopTree.getSelectionModel().select(record);//选择当前树节点
	    			desktopId = record.data.id;
					contextMenu.showAt(e.getXY());
					var type = record.data.type;
					if(type == 1){
						Ext.getCmp('desktop_del').setDisabled(true);
						Ext.getCmp('desktop_update').setDisabled(true);
						Ext.getCmp('desktop_down').setDisabled(true);
						Ext.getCmp("desktop_up").setDisabled(true);
					}else{
						Ext.getCmp('desktop_del').setDisabled(false);
						Ext.getCmp('desktop_update').setDisabled(false);
						Ext.getCmp('desktop_down').setDisabled(false);
						Ext.getCmp("desktop_up").setDisabled(false);
					}
	    		},
	    		beforeload : function() {
			       Ext.MessageBox.show({
						title : '系统提示',
						msg : '正在加载桌面数据，请稍候......',
						progressText : 'processing now,please wait...',
						width : 300,
						wait : true,
						waitConfig : {
							interval : 150
						}
					});
			    },  
			    load : function() {
			        Ext.MessageBox.hide();
			    }
			}
		});
		desktopTree.expandAll();
		return desktopTree;
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
	        	items:[createDestopTree()]
	        }]
		})
});