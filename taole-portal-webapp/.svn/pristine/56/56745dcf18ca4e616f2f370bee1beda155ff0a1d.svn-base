<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%><%@include file="/include/page.jspf" %><html>
<head>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
	<style type="text/css">
		.delCls{
			width:70px;
			height:25px;
		}
		
		#per_grid-body .x-grid-row{
			vertical-align: middle !important;
		}
	</style>
<body>
<script type="text/javascript">
	Ext.onReady(function(){
		Ext.QuickTips.init();
		Ext.Loader.setConfig({
		    enabled: true
		});
		Ext.tip.QuickTipManager.init();
		//新增角色
		window.addOrUpdateGroup= function(id){
			var grid =Ext.getCmp("group_grid");
			var width = grid.getWidth();
			var win = Ext.getCmp("group_win");
			var height = grid.ownerCt.ownerCt.getHeight()-230;
			var title = id ==''?'新增':'修改';
			var type = id == ''?'add':'edit';
			if(!win){
				win = Ext.create("Ext.window.Window",{
					title:title+'用户组信息',
					height:300,
					id:'group_win',
					width:600,
					itemId:id,
					modal:true,
					layout:'fit',
					border:false,
					resizable:false,
					items:[{
						xtype:'tabpanel',
						border:false,
						items:[{
							xtype:'form',
							region:'north',
							title:'基本信息',
							id:'group_basic_form',
							layout:'anchor',
							border:false,
							style:{
								'margin-bottom':'5px',
								'border-radius':'0px'
							},
							frame:true,
							autoScroll:true,
							items:[{
								xtype:'textfield',
								fieldLabel:'名称',
								name:'name',
								labelAlign:'right',
								labelWidth:80,
								anchor:'80%'
							},{
								xtype:'textarea',
								fieldLabel:'描述',
								labelWidth:80,
								height:100,
								name:'description',
								labelAlign:'right',
								anchor:'80%'
							},{
								xtype:'textfield',
								fieldLabel:'type',
								name:'type',
								value:0,
								hidden:true,
							}]	
						},{
							title:'分配用户',
							id:'add_user_form',
							xtype:'form',
							frame:true,
							style:{
								'border':'0px'
							},
							border:false,
							layout:'absolute',
							autoScroll:true,
							items:[{
								xtype:'textfield',
								fieldLabel:'关键字',
								labelWidth:45,
								labelAlign:'right',
								id:'partWord',
								x:5,
								y:5,
								width:200
							},{
								xtype:'button',
								text:'查询',
								x:210,
								y:5,
								width:80,
								handler:function(){
									var keyword = Ext.getCmp('partWord').getValue();
									var fieldset = Ext.getCmp('userList');
									fieldset.removeAll();
									fieldset.add(addUserTo('userList',600,keyword,"",""));
									fieldset.doLayout();
								}
							},{
								xtype:'fieldset',
								id:'userList',
								title:'用户列表',
								width:555,
								x:10,
								y:35,
								items:[]
							}]
						},{
							title:'赋予角色',
							xtype:'form',
							id:'add_role_form',
							frame:true,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							border:false,
							items:[]
						},{
							title:'赋予权限',
							xtype:'form',
							id:'add_per_form',
							autoScroll:true,
							frame:true,
							style:{
								'border':'0px'
							},
							border:false,
							items:[]
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'保存',
						handler:function(){
							var form = Ext.getCmp("group_basic_form");
							var group_win = Ext.getCmp('group_win');
							if(form.getForm().isValid()){
								var data = Ext.ux.FormUtils.getDataObject(form);
								var url ="";
								var id = id||group_win.itemId;
								if (id != '') {
									var selRow = grid.getSelectionModel().getSelection()[0];
									data.id = selRow.data.id;
									url = $service.portal+'/us.Group/'+selRow.data.id+'/update';
								}else{
									data.id ="";
									url =$service.portal+'/us.Group/collection/create';
								}
								
								data.entityName ="us.Group";
								
								data.userList =[];
								
								var user_form = Ext.getCmp("add_user_form");
								var userArray = user_form.query("checkbox");
								for(var i = 0; i < userArray.length; i++){
									var checkbox = userArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											data.userList.push(checkbox.id);
										}
									}
								}
								
								var role_form = Ext.getCmp("add_role_form");
								var roleArray = role_form.query("checkbox");
								data.roleList =[];
								for(var i = 0; i < roleArray.length; i++){
									var checkbox = roleArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											data.roleList.push(checkbox.id);
										}
									}
								}
								
								data.privilegeList =[];
								var pre_form = Ext.getCmp("add_per_form");
								var preArray = pre_form.query("checkbox");
								for(var i = 0; i < preArray.length; i++){
									var checkbox = preArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											data.privilegeList.push(checkbox.id);
										}
									}
								}
								
								
								Ext.Ajax.request({
									url:url,
									method:'post',
									jsonData:data,
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'保存失败：' + r.description);
										} else {
											group_win.destroy();
											Ext.getCmp('group_grid').getStore().load();
											Ext.getCmp('group_grid').getSelectionModel().deselectAll();
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
							win.destroy();
						}
					}]
				})
			}
			win.show();
			var role_form= Ext.getCmp("add_role_form");
			role_form.add(addRoleTo(600,"",""));
			role_form.doLayout();
			Ext.getCmp("f545a97cd90c46c4b85d0daaf1fd04ac").hide(true);
			Ext.getCmp("aa192ae10bca42b592aca883e9c82bc9").hide(true);
			var user_form= Ext.getCmp("userList");
			user_form.add(addUserTo('userList',600,"","",""));
			user_form.doLayout();

			var per_form= Ext.getCmp("add_per_form");
			per_form.add(addPerTo(600,""));
			per_form.doLayout();
			var form = Ext.getCmp("group_basic_form");
			var grid = Ext.getCmp("group_grid");
			if(id!=''){
				var selRow =grid.getSelectionModel().getSelection()[0];
				form.getForm().loadRecord(selRow);
				var id = selRow.data.id;
				Ext.Ajax.request({
					method: 'GET',
					url: $service.portal+'/us.Group/'+id+'/retrieve',
					dataType:'json',
					async:false,
					success:function(data){
						var text = data.responseText;
						var data = eval("("+text+")");
						if(data){
							var userList = data.userList;
							if(userList && userList.length >0){
								for(var i = 0; i < userList.length; i++){
									var user = userList[i];
									Ext.getCmp(user.id).setValue(true);
								}
							}
							var roleList = data.roleList;
							if(roleList && roleList.length >0){
								for(var i = 0; i < roleList.length; i++){
									var role = roleList[i];
									Ext.getCmp(role.id).setValue(true);
								}
							}
							
							var preList = data.privilegeList;
							if(preList && preList.length >0){
								for(var i = 0; i < preList.length; i++){
									var pre = preList[i];
									Ext.getCmp(pre.id).setValue(true);
								}
							}
							
						}
					},
					failure:function(){
						
					}
				});
				
			}else{
				form.getForm().reset();
			}
		}
		//删除
		deleteGroup = function (){
			var grid = Ext.getCmp("group_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length >0){
				Ext.Msg.confirm("提示","确定要删除选中的用户组?",function(btn){
					if(btn == 'yes'){
						var ids = [];
						for(var i=0; i<selRows.length; i++){
							ids.push("ids="+selRows[0].data.id);
						}
						
						//执行删除操作
						Ext.Ajax.request({
							method:'post',
							url: $service.portal+'/us.Group/collection/batchDelete?'+ids.join("&"),
							success:function(resp, opts){
								var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'删除失败：' + r.description);
								} else {
									Ext.getCmp('group_grid').getStore().load();
								}
							},
							failure:function(resp, opts){
								Ext.Msg.alert("提示",'删除失败：' + resp.responseText);
							}
						})
					}
				})
			}else{
				Ext.Msg.alert("提示","请选择删除的用户组!");
			}
		}
		
		window.addPerToGroup = function(type){
			var grid = Ext.getCmp("group_grid");
			var width = grid.getWidth();
			var height = grid.ownerCt.ownerCt.getHeight();
			var selRows = grid.getSelectionModel().getSelection();
			var length = selRows.length;
			var id = "";
			if(length == 1){
				if(length == 1){
					id = selRows[0].data.id;
				}
				var per_window = Ext.getCmp("per_window");
				if(!per_window){
					per_window = Ext.create('Ext.window.Window',{
						title: '授予权限',
						height:300,
						id:'per_window',
						width:600,
						resizable:false,
						modal:true,
						layout:'fit',
						items:[{
							id:'add_per_form',
							xtype:'form',
							frame:true,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							border:false,
							items:[]
						}],
						buttonAlign:'center',
						buttons:[{
							text:'保存',
							handler:function(){
								var per_form = Ext.getCmp("add_per_form");
								var preArray = per_form.query("checkbox");
								var preStr =[];
								for(var i = 0; i < preArray.length; i++){
									var checkbox = preArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											preStr.push("privs="+checkbox.id);
										}
									}
								}
								var grid = Ext.getCmp("group_grid");
								var selRows = grid.getSelectionModel().getSelection();
								//var groupArray =[];
								//for(var i =0; i<selRows.length; i++){
								//	var row = selRows[i];
								//	groupArray.push("groups="+row.data.id);
								//}
								var url = $service.portal+'/us.Group/'+selRows[0].data.id+'/grantPrivilege?'+preStr.join("&");
								Ext.Ajax.request({
									url:url,
									method:'POST',
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'授予权限失败：' + r.description);
										} else {
											per_window.destroy();
											Ext.getCmp('group_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								per_window.destroy();
							}
						}]
					})
				}
				per_window.show();
				var per_form = Ext.getCmp("add_per_form");
				per_form.add(addPerTo(600,'add'));
				per_form.doLayout();
				
				if(type==''){
					var groupid = selRows[0].data.id;
					Ext.Ajax.request({
						method: 'post',
						url: $service.portal+'/us.Group/'+groupid+'/retrieve',
						dataType:'json',
						async:false,
						success:function(data){
							var response = data.responseText;
							var data = eval("("+response+")");
							if(data){
								var preList = data.privilegeList;
								if(preList && preList.length >0){
									for(var i = 0; i < preList.length; i++){
										var pre = preList[i];
										Ext.getCmp(pre.id).setValue(true);
									}
								}
							}
						},
						failure:function(){
							
						}
					});
				}
			}else if(length == 0){
				Ext.Msg.alert('提示',"请选择要授予权限的用户组!");
			}else{
				Ext.Msg.alert('提示',"请选择一条要授予权限的用户组!");
			}
		}
		window.addUserToGroup = function(type){
			var grid = Ext.getCmp("group_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var width = grid.getWidth();
			var length = selRows.length;
			var id = "";
			if(length == 1){
				if(length == 1){
					id = selRows[0].data.id;
				}
				var user_window = Ext.getCmp("user_window");
				if(!user_window){
					user_window = Ext.create('Ext.window.Window',{
						title: '分配用户',
						height:300,
						id:'user_window',
						width:600,
						resizable:false,
						modal:true,
						layout:'fit',
						items:[{
							id:'add_user_form',
							xtype:'form',
							frame:true,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							border:false,
							layout:'absolute',
							items:[{
								xtype:'textfield',
								fieldLabel:'关键字',
								labelWidth:55,
								labelAlign:'right',
								id:'partWord',
								x:5,
								y:5,
								width:200
							},{
								xtype:'button',
								text:'查询',
								x:210,
								y:5,
								width:80,
								handler:function(){
									var keyword = Ext.getCmp('partWord').getValue();
									var fieldset = Ext.getCmp('userList');
									fieldset.removeAll();
							//		fieldset.add(addUserTo('userList',600,keyword,'edit',id));
									fieldset.add(addUserTo('userList',600,keyword,'',id));
									fieldset.doLayout();
									if(type ==''){
										checkToUser(type, selRows[0].data.id);
									}
								}
							},{
								xtype:'button',
								text:'重置',
								x:295,
								y:5,
								width:80,
								handler:function(){
									Ext.getCmp('partWord').setValue("");
								}
							},{
								xtype:'fieldset',
								id:'userList',
								title:'用户列表',
								x:5,
								y:35,
								items:[]
							}]
						}],
						buttonAlign:'center',
						buttons:[{
							text:'保存', 
							handler:function(){
								var user_form = Ext.getCmp("userList");
								var userArray = user_form.query("checkbox");
								var userStr =[];
								for(var i = 0; i < userArray.length; i++){
									var checkbox = userArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											userStr.push("users="+checkbox.id);
										}
									}
								}
								var grid = Ext.getCmp("group_grid");
								var selRows = grid.getSelectionModel().getSelection();
								//var groupArray =[];
								//for(var i =0; i<selRows.length; i++){
								//	var row = selRows[i];
								//	groupArray.push("groups="+row.data.id);
								//}
								var url = $service.portal+'/us.Group/'+selRows[0].data.id+'/grantUser?'+userStr.join("&");
								Ext.Ajax.request({
									url:url,
									method:'POST',
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'分配用户失败：' + r.description);
										} else {
											user_window.destroy();
											Ext.getCmp('group_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'分配用户失败：' +resp.responseText);
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								user_window.destroy();
							}
						}]
					})
				}
				user_window.show();
				var user_form = Ext.getCmp("userList");
				user_form.add(addUserTo('userList',600,"",'',""));
				user_form.doLayout();
				
				if(type ==''){
					checkToUser(type, selRows[0].data.id);
				}
				
			}else if(length == 0){
				Ext.Msg.alert('提示',"请选择要分配用户的用户组!");
			}else{
				Ext.Msg.alert('提示',"请选择一条要分配用户的用户组!");
			}
		}
		function checkToUser(type, groupid){
			if(type==''){
				Ext.Ajax.request({
					method: 'post',
					url: $service.portal+'/us.Group/'+groupid+'/retrieve',
					dataType:'json',
					async:false,
					success:function(data){
						var response = data.responseText;
						var data = eval("("+response+")");
						if(data){
							var userList = data.userList;
							if(userList && userList.length >0){
								for(var i = 0; i < userList.length; i++){
									var user = userList[i];
									if(Ext.getCmp(user.id)){
										Ext.getCmp(user.id).setValue(true);
									}
								}
							}
						}
					},
					failure:function(){
						
					}
				});
			}
		}
		window.addRoleToGroup = function(type){
			var grid = Ext.getCmp("group_grid");
			var width = grid.getWidth();
			var selRows = grid.getSelectionModel().getSelection();
			
			var length = selRows.length;
			var id = "";
			if(length >0){
				if(length == 1){
					id = selRows[0].data.id;
				}
				var role_window = Ext.getCmp("role_window");
				if(!role_window){
					role_window = Ext.create('Ext.window.Window',{
						title: '赋予角色',
						height:300,
						id:'role_window',
						width:600,
						resizable:false,
						modal:true,
						layout:'fit',
						items:[{
							id:'add_role_form',
							xtype:'form',
							frame:true,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							border:false,
							items:[]
						}],
						buttonAlign:'center',
						buttons:[{
							text:'保存',
							handler:function(){
								var role_form = Ext.getCmp("add_role_form");
								var roleArray = role_form.query("checkbox");
								var roleStr =[];
								for(var i = 0; i < roleArray.length; i++){
									var checkbox = roleArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											roleStr.push("roles="+checkbox.id);
										}
									}
								}
								var grid = Ext.getCmp("group_grid");
								var selRows = grid.getSelectionModel().getSelection();
								//var groupArray =[];
								//for(var i =0; i<selRows.length; i++){
								//	var row = selRows[i];
								//	groupArray.push("groups="+row.data.id);
								//}
								var url = $service.portal+'/us.Group/'+selRows[0].data.id+'/grantRole?'+roleStr.join("&");
								Ext.Ajax.request({
									url:url,
									method:'POST',
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'授予角色失败：' + r.description);
										} else {
											role_window.destroy();
											Ext.getCmp('group_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'授予角色失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								role_window.destroy();
							}
						}]
					})
				}
				role_window.show();
				var role_form = Ext.getCmp("add_role_form");
				role_form.add(addRoleTo(600,"",id));
				role_form.doLayout();
				Ext.getCmp("f545a97cd90c46c4b85d0daaf1fd04ac").hide(true);
				Ext.getCmp("aa192ae10bca42b592aca883e9c82bc9").hide(true);
				if(type==''){
					var groupid = selRows[0].data.id;
					Ext.Ajax.request({
						method: 'post',
						url: $service.portal+'/us.Group/'+groupid+'/retrieve',
						dataType:'json',
						async:false,
						success:function(data){
							var response = data.responseText;
							var data = eval("("+response+")");
							if(data){
								var roleList = data.roleList;
								if(roleList && roleList.length >0){
									for(var i = 0; i < roleList.length; i++){
										var role = roleList[i];
										Ext.getCmp(role.id).setValue(true);
									}
								}
							}
						},
						failure:function(){
							
						}
					});
				}
			}else if(length == 0){
				Ext.Msg.alert('提示',"请选择要赋予角色的用户组!");
			}else{
				Ext.Msg.alert('提示',"请选择一条要赋予角色的用户组!");
			}
		}
		
		
		//设置数据权限
		window.setDataPrivilege = function(){
			var grid = Ext.getCmp("group_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 0){
				Ext.Msg.alert("提示","请选择要设置数据权限的分组记录!");
				return;
			}
			
			if(selRows.length > 1){
				Ext.Msg.alert("提示","请选择一条分组记录进行操作!");
				return;
			}
			
			var groupId = selRows[0].data.id;
			Ext.application({
		   		name: 'Taole',
		   		appFolder: '<%=request.getContextPath()%>/app',
		   		controllers: [
		       		'Taole.dataPrivilege.dataPrivilegeManager.controller.SetDataPrivilegeCtrl'
		   		],
			    launch: function() {
			    	Ext.create("widget.setDataPrivilegeWindow",{
			    		scope: this,
			    		afterChooseFn: function(){
			    			
			    		},
			    		bizType: 'GROUP_DATAPRIVILEGE',
			    		bizId: groupId
			    	}).show();
			    }
			});
		}
		
		//创建用户网格
		function createGroupTreeGrid(){
			Ext.define('groupModel',{
				extend:'Ext.data.Model',
				fields:['id','name','description','entityName','roleList','userList','privilegeList','orgPathName']
			})
			var group_store = Ext.create('Ext.data.Store',{
				model: 'groupModel',//数据模型
				proxy: {
		            type: 'ajax',
		            url: $service.portal+'/us.Group/collection/query?type=0',
		            reader: {
		                root: 'items',
		                totalProperty: 'total'
		            },
		            simpleSortMode: true
		        }
			});
			group_store.load({
		    	params:{
		    		start: 0,
	        		limit: 20
	        	}
		    });
		    
			var group_grid = Ext.create('Ext.grid.Panel',{
				store: group_store,
				id:'group_grid',
				//title:'分组列表',
				border:false,
				columnLines:true,
				collapsible: false,//是否可收缩
				columns:[
					{xtype: 'rownumberer',text: '序号',align:'center',width:40},
					{text:'名称',width:150,dataIndex:'name'},
					{text:'组织结构路径',width:300,dataIndex:'orgPathName'},
					{text:'描述',width:180,dataIndex:'description'},
					{text:'操作',width:350,dataIndex:'',align:'center',renderer:function(v,rec){
						var groupId =1;//= rec.data.id;
						return '<div> <input type="button"  value="修 改" onclick ="addOrUpdateGroup(\''+groupId+'\')"  class="enableCls">&nbsp;&nbsp;<input type="button" value="删除" onclick ="deleteGroup()" class="enableCls">'+
						' <input type="button" value="分配用户" onclick="addUserToGroup(\'\')"  class="enableCls"><input type="button" value="分配角色" onclick ="addRoleToGroup(\'\')"  class="enableCls">&nbsp;&nbsp;<input type="button" value="授予权限"  onclick ="addPerToGroup(\'\')" class="enableCls"></div>';
					}}
				],
				plugins: [
			        {
			            ptype: 'datatip',
			            tpl: '组织结构路径：{orgPathName}'
			        }
			    ],
				selModel: { //选中模型 
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
		        },
		        multiSelect: true,//是否允许多选
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: group_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }],
		        tbar:[{
		        	text:'增加',
		        	iconCls:'',
		        	handler:function(){
		        		addOrUpdateGroup('');
		        	}
		        },{
		        	text:'删除',
		        	iconCls:'',
		        	handler:function(){
		        		deleteGroup();
		        	}
		        },'-',{
		        	text:'赋予角色',
		        	handler:function(){
		        		addRoleToGroup('');
		        	}
		        },{
		        	text:'授予权限',
		        	iconCls:'',
		        	handler:function(){
		        		addPerToGroup('');
		        	}
		        },{
		        	text:'分配用户',
		        	handler:function(){
		        		addUserToGroup('');
		        	}
		        },{
		        	text:'设置数据权限',
		        	handler:function(){
		        		setDataPrivilege();
		        	}
		        }]
			})
			return group_grid;
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
	        	minHeight:40,	//最小高度
	        	maxHeight:55,	//最大高度
	        	height:40,	
        		border:false,	//是否有边框
	        	layout:'fit',	//布局方式
	        	items:[{
	        		xtype:'form',
	        		frame:true,
	        		id:'group_search_from',
	        		cls:'x-plain',
	        		layout:'hbox',
	        		items:[
	        	/*	       {
						width:200,
						xtype:'trigger',
						fieldLabel:'组织结构',
						id:'organization',
						labelWidth:55,
						labelAlign:'right',
						margin:'5px 0px 5px 0px',
						onTriggerClick: function(field){
							Ext.application({
						   		name: 'Taole',
						   		appFolder: $ctx+'/app',
						   		controllers: [
						       		'Taole.user.organization.controller.ChooseOrganizationWinCtrl'
						   		],
							    launch: function() {
							    	Ext.create("widget.chooseOrganizationWindow",{
							    		scope: this,
							    		afterChooseFn: function(node){
							    			Ext.getCmp("organization").setValue(node.text);
							    		}
							    	}).show();
							    }
							});
						}
	        		},
	        		*/{
	        			xtype:'textfield',
	        			fieldLabel:'名称',
	        			name:'name',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:200
	        		},{
	        			xtype:'textfield',
	        			fieldLabel:'描述',
	        			name:'description',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:400
	        		},{
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
	        			text:'重 置',
	        			handler:function(){
	        				var form = Ext.getCmp("group_search_from").getForm();
	        				form.reset();
	        			}
	        		}]
	        	}]
	        },{
	        	region:'center',
	        	layout:'fit',
	        	items:[createGroupTreeGrid()]
	        }]
		})
	})
	
	function doQuery(){
		var params = Ext.ux.FormUtils.getDataObject(Ext.getCmp("group_search_from"));
		var store = Ext.getCmp('group_grid').getStore();
		store.getProxy().extraParams = params;
		store.loadPage(1);
	}
</script>
</body>
</html>