<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%><%@include file="/include/page.jspf" %><html>
<head>
	<jsp:include page="../include/header.jsp"></jsp:include>
	<style type="text/css">
		.delCls{
			width:70px;
			height:25px;
		}
		table{width:100% !important;}
	</style>
<body>
<script type="text/javascript">
	Ext.onReady(function(){
		
		var entityId = null;
		var entityName = 'tk.Dictionary';
		var entityAction = 'create';
		var treeStore = null;
		//执行新增或修改操作
		function addOrUpdateMenuInfo(id){
			
			if (id) {
				var selRows = Ext.getCmp("dict_tree").getSelectionModel().getSelection();
				var id = selRows[0].data.id;
				entityId = id;
			} else {
				entityId = null;
			}
			var form = Ext.getCmp("dict_form");
			var menu_win = Ext.getCmp('dict_win');
			if(form.getForm().isValid()){
				var data = Ext.ux.FormUtils.getDataObject(form);
				var url ="";
				if (entityId) {
					data.id = entityId;
					url = $service.portal+'/tk.Dictionary/'+entityId+'/update';
				}else{
					data.id ="";
					url =$service.portal+'/tk.Dictionary/collection/create';
				}
				
				data.entityName ="tk.Dictionary";
				Ext.Ajax.request({
					url:url,
					method:'post',
					async: false,
					jsonData:data,
					success:function(resp, opts){
						var r = eval("(" + resp.responseText + ")");
						if (r.code != 1) {
							Ext.Msg.alert("提示",'保存失败：' + r.description);
						} else {
							menu_win.destroy();
							Ext.getCmp('dict_tree').getStore().load();
							Ext.getCmp('dict_grid').getStore().load();
						}
					},  
					failure:function(resp, opts){
						
					}
				})
			}
		}
		
		//新增或修改菜单
		function addOrEditSysMenu(id){
			var title = id ==''?'新增':'修改';
			var readonly = id !=''?false:true;
			var dict_win = Ext.getCmp('dict_win');
			
			var parentId,parentName;
			if(id !=''){
				var node = Ext.getCmp('dict_tree').getStore().getNodeById(id).parentNode;
				if(node){
					parentId = node.data.id;
					parentName =node.data.text;
				}
			}else{
				var node = Ext.getCmp("dict_tree").getSelectionModel().getSelection()[0];
				parentId = node.data.id;
				parentName = node.data.text;
			}
			if(!dict_win){
				dict_win = Ext.create('Ext.window.Window',{
					height:270,
					width:530,
					id:'dict_win',
					modal:true,
					title:title+'词条',
					layout:'fit',
					items:[{
						xtype:'form',
						layout:'anchor',
						id:'dict_form',
						margin:'5px',
						frame:true,
						style:{
							'border':'0px'
						},
						items:[{
							xtype:'treepicker',
							displayField: 'text',
							fieldLabel: '父级词条',  
							id:'tree_picker',
							labelAlign:'right',
							autoScroll:true,
							name:'parentNode',
							baseHeight:300,
							labelWidth:80,
							rootVisible:false,
							minPickerHeight:200,
							autoScroll:true,
						    forceSelection : true,// 只能选择下拉框里面的内容  
					        editable: false,// 不能编辑  
						    store: Ext.create('Ext.data.TreeStore', {//树的数据源store
								autoLoad:true,
								fields:[{name : 'id',  type : 'string'},  
							        	{name : 'text', type : 'string'
							        	}],
					        	proxy : {//代理
						            type : 'ajax',
						            url :$service.portal+'/tk.Dictionary/DICT_ROOT/tree'
						        },
								root: {
							        expanded: true,
							        text:'数据字典'
							    }
						    }),
						    listeners:{
						    	select:function(field,record){
						    		//var form = Ext.getCmp("dict_form");
									//form.itemId = record.data.id;
									//form.getForm().findField("parentNode").setValue(record.data.text);
						    	}
						    },
						    anchor:'90%'
						},{
							xtype:'textfield',
							fieldLabel:'词条名称',
							labelWidth:80,
							name:'name',
							allowBlank:false,
							blankText:'请输入词条名称',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'textfield',
							fieldLabel:'词条编码',
							labelWidth:80,
							name:'value',
							allowBlank:false,
							blankText:'请输入词条编码',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'textarea',
							fieldLabel:'词条描述',
							labelWidth:80,
							height:50,
							name:'description',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'checkbox',
							fieldLabel:'是否缺省',
							labelWidth:80,
							name:'isDefault',
							labelAlign:'right',
							anchor:'90%'
						},{
							xtype:'checkbox',
							fieldLabel:'是否系统',
							labelWidth:80,
							name:'system',
							labelAlign:'right',
							anchor:'90%'
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'保存',
						handler:function(){
							var _form = dict_win.down('form').getForm();
							if(_form.isValid()){
								var name = _form.findField("name").getValue();
								var code = _form.findField("value").getValue();
								var root = Ext.getCmp("dict_tree").store.getRootNode();
								if(id==''){
									/**
										var cur_node = root.findChild('text',name,true);
										if(cur_node){
											Ext.Msg.alert("提示","该词条名称已存在,请重新输入!");
											_form.findField("name").setValue("");
											return false;
										}
									**/
								}else{
									/**没必要这样进行判断，这样判断并不准确，且会误判，因此注释掉 by kwq 20140715
									var node =  Ext.getCmp("dict_tree").getSelectionModel().getSelection()[0];
									var cur_node =  root.findChild('text',name,true);
									if(cur_node && cur_node != node){
										Ext.Msg.alert("提示","该词条名称已存在,请重新输入!");
										_form.findField("name").setValue(node.data.text);
										return false;
									}
									*/
								}
							    addOrUpdateMenuInfo(id);
							}
							
						}
					},{
						text:'取消',
						handler:function(){
							dict_win.destroy();
						}
					}]
				})
			}
			dict_win.show();
			var form = dict_win.down('form');
			form.getForm().reset();
			if(id!=''){
				dict_win.setTitle("修改词条");
				Ext.Ajax.request({
					url:$service.portal+'/tk.Dictionary/'+id+'/retrieve',
					method:'get',
					success:function(r){
						var text = r.responseText;
						var data = eval("("+text+")");
						for(var i in data){
							var field  = form.getForm().findField(i);
							var val = data[i];
							if(field){
								field.setValue(val);
							}
						}
					}
				})
				form.getForm().findField('parentNode').setValue(parentId);
			} else {
				dict_win.setTitle("新增词条");
				dict_win.down('form').getForm().reset();
				form.getForm().findField('parentNode').setValue(parentId);
			}
			
		}
		
		function getNodeData(nodeId){
			var data =null;
			Ext.Ajax.request({
				url:$service.portal+'/tk.Dictionary/'+nodeId+'/retrieve',
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
	        		var selRows = Ext.getCmp("dict_tree").getSelectionModel().getSelection();
	        		if(selRows.length > 0){
	        			Ext.Msg.confirm("提示","确定要删除选中的词条节点?",function(btn){
	    					if(btn == 'yes'){
	    						var id = selRows[0].data.id;
	    	        			Ext.Ajax.request({
	    	        				url:$service.portal+'/tk.Dictionary/'+id+'/delete',
	    	        				method:'post',
	    	        				success:function(resp, opts){
	    	        					var r = eval("(" + resp.responseText + ")");
	    								if (r.code != 1) {
	    									Ext.Msg.alert("提示",'删除失败：' + r.description);
	    								} else {
	    									Ext.getCmp('dict_tree').getStore().load();
	    									Ext.getCmp("dict_grid").getStore().load();
	    								}
	    	        				},
	    	        				failure:function(resp, opts){
	    	        				
	    	        				}
	    	        			})
    						}
    					});
	        			
	        		}else{
	        			Ext.Msg.alert("提示","请选择要修改的词条记录!");
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
	        		var selRows = Ext.getCmp("dict_tree").getSelectionModel().getSelection();
	        		if(selRows.length > 0){
	        			var id = selRows[0].data.id;
	        			addOrEditSysMenu(id);
	        		}else{
	        			Ext.Msg.alert("提示","请选择要修改的词条记录!");
	        		}
	        	}
	        },{
	        	text:'上升',
	        	id:'up',
	        	handler:function(){
	        		var tree = Ext.getCmp("dict_tree");
	        		var selRows = tree.getSelectionModel().getSelection();
	        		if(selRows.length > 0){
	        			var id = selRows[0].data.id;
	        			Ext.Ajax.request({
	        				url:$service.portal+'/tk.Dictionary/'+id+'/updown?offset=-1',
	        				method:'post',
	        				success:function(resp, opts){
	        					var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'上升失败：' + r.description);
								} else {
									tree.getStore().load();
									Ext.getCmp('dict_grid').getStore().load();
								}
	        				},
	        				failure:function(resp, opts){
	        				
	        				}
	        			})
	        		}else{
	        			Ext.Msg.alert('提示','请选择需要调整的词条节点!');
	        		}
	        	}
	        },{
	        	text:'下降',
	        	id:'down',
	        	handler:function(){
	        		var tree = Ext.getCmp("dict_tree");
	        		var selRows = tree.getSelectionModel().getSelection();
	        		if(selRows.length > 0){
	        			var id = selRows[0].data.id;
	        			Ext.Ajax.request({
	        				url:$service.portal+'/tk.Dictionary/'+id+'/updown?offset=1',
	        				method:'post',
	        				success:function(resp, opts){
	        					var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'下降失败：' + r.description);
								} else {
									tree.getStore().load();
									Ext.getCmp('dict_grid').getStore().load();
								}
	        				},
	        				failure:function(resp, opts){
	        				
	        				}
	        				
	        			})
	        		}else{
	        			Ext.Msg.alert('提示','请选择需要调整的词条节点!');
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
	        					fieldLabel:'词条名称',
	        					labelWidth:60,
	        					margin:10,
	        					labelAlign:'right',
	        					id:'dict_name',
	        					width:200
	        				}],
	        				buttons:[{
	        					text:'保存',
	        					handler:function(){
	        						var dataObj = getNodeData(entityId);
	        						var name = Ext.getCmp("dict_name").getValue();
	        						dataObj.name = name;
	        						Ext.Ajax.request({
	        							url:$service.portal+'/tk.Dictionary/'+entityId+'/update',
	        							method:'post',
	        							jsonData:dataObj,
	        							success:function(resp, opts){
	        								var r = eval("(" + resp.responseText + ")");
	        								if (r.code != 1) {
	        									Ext.Msg.alert("提示",'重命名失败：' + r.description);
	        								} else {
	        									rename_win.destroy();
	        									Ext.getCmp("dict_tree").getStore().load();
	        									Ext.getCmp('dict_grid').getStore().load();
	        								}
	        							},
	        							failure:function(resp, opts){
	        								
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
	        		var node = Ext.getCmp("dict_tree").getSelectionModel().getSelection()[0];
	        		Ext.getCmp("dict_name").setValue(node.raw.text);
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
		
		//创建菜单树
		function createDataDictTree(){
			Ext.define('treeModel', {//树的数据模型
		        extend : 'Ext.data.Model',//继承自Model
		        fields : [//字段
		        	{name : 'id',  type : 'string'},  
		        	{name : 'text', type : 'string'},
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
		            url :''//$service.portal+'/tk.Dictionary/DICT_ROOT/tree'
		        }
		    });
			var treeStore = Ext.create('Ext.data.TreeStore', {//树的数据源store
				model : treeModel,//数据模型
				root: {
			        expanded: true,
			        text:'数据字典',
			        id:'root'
			    }
		    });
			
			var dictTree = Ext.create('Ext.tree.Panel',{//菜单树
				id:'dict_tree',
				minWidth: 210,//最小宽度
				border:false,
				width:'100%',
				store : treeStore,//数据源
				autoHeight : true,//是否自动适应高度
				animate: false,//是否有动画效果
				rootVisible: false,//根节点是否可见
				tbar:[{
					text:'收缩',
					pressed:true,
					handler:function(){
						var tree = Ext.getCmp("dict_tree");
						 tree.collapseAll();
					}
				},{
					text:'展开',
					pressed:true,
					handler:function(){
						var tree = Ext.getCmp("dict_tree");
						tree.expandAll();
					}
				}],
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
	                			index++;
	                		}
	                		var parentId = parentNode.data.id;
	                		Ext.Ajax.request({
    							url:$service.portal+'/tk.Dictionary/'+drugNode.data.id+'/move?parent='+parentId+'&index='+index,
    							method:'post',
    							success:function(resp, opts){
    								var r = eval("(" + resp.responseText + ")");
    								if (r.code != 1) {
    									Ext.Msg.alert("提示",'数据字典节点拖拽失败：' + r.description);
    									Ext.getCmp("dict_tree").getStore().load();
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
						dictTree.getSelectionModel().select(record);//选择当前树节点
		    			entityId = record.data.id;
						var root = tree.ownerCt.getRootNode();
						var parentNode = record.parentNode;
						contextMenu.showAt(e.getXY());
						if(record.get('id') == 'DICT_ROOT'){
							setMenuDisableOrEnable(true);
						}else{
							setMenuDisableOrEnable(false);
						}
						
		    		}
				}
			});
			return dictTree;
		}
		
		
		function createWestTreeGrid(){
			Ext.define('dictModel', {//树的数据模型
		        extend : 'Ext.data.Model',//继承自Model
		        fields : [//字段
		        	{name : 'id',  type : 'string'},  
		        	{name : 'name', type : 'string'},
		        	{name :'nodetype',type : 'string'},
		        	{name :'enable',type : 'string'},
		        	{name : 'action',type: 'string'},
		        	{name : 'description',type: 'string'},
		        	{name : 'iconCls',type: 'string'},
		        	{name : 'attributes',type: 'string'},
		        	{name : 'entityName',type: 'string'},
		        	{name:'type',type:'string'}
		    	]
		    });
			var gridStore = Ext.create('Ext.data.Store', {
				model: 'dictModel',//数据模型
				remoteSort: true,
				autoLoad:true,
				proxy: {
					type: 'ajax',
					url:$service.portal+'/tk.Dictionary/DICT_ROOT/getDictionary',
					reader: {
						root: 'items',
						totalProperty: 'total'
					},
					simpleSortMode: true
				}
			});
			var goodsGrid = Ext.create('Ext.grid.Panel',{
				id:'dict_grid',
				store:	gridStore,
				columnLines: true,//各列之间是否有分割线
				multiSelect: true,//是否允许多选
				columns:[{
					xtype:'rownumberer'
				},{
					text: "词条名称",
					dataIndex: 'name',
					align:'left',
					flex:1
				}],
				tbar:[{
						xtype:'textfield',
						fieldLabel:'关键字',
						id:'keyword',
						labelWidth:50,
						width:'65%',
						labelAlign:'right'
					},'->',{
						text:'查找',
						flex:1,
						pressed:true,
						handler:function(){
							var store = Ext.getCmp("dict_grid").getStore();
							var  keyword = Ext.getCmp("keyword").getValue();
							store.clearFilter();
							if(keyword !=''){
								var regex = eval('/^.*'+keyword+'.*$/');
								store.filterBy(function(rec){
									return regex.test(rec.data.name);
								})
							}
						}
					}],
				listeners:{
					itemclick:function(view,rec){
						var tree = Ext.getCmp("dict_tree");
						var treeStore = tree.getStore();
						var id = rec.data.id;
						treeStore.proxy.url = $service.portal+'/tk.Dictionary/'+id+'/tree';
						treeStore.load();
					}
				}
			});
			
			return goodsGrid;
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
				region:'west',
				width:280,
				layout:'fit',
				border:false,
				items:[createWestTreeGrid()]
			},{
	        	region:'center',
	        	layout:'fit',
	        	border:false,
	        	items:[createDataDictTree()]
	        }]
		})
	})
	
	function doQuery(){
	}
</script>
</body>
</html>