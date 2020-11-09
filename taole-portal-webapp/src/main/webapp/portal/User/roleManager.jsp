<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><html>
<head>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
	<style type="text/css">
		.btnCls{
			width:100%;
			height:100%;
			color: blue;
			display:block;
			cursor:pointer;
		}
		.delCls{
			width:70px;
			height:25px;
		}
		.btnCls hover{
			color:red;
		}
		#acc_grid-body .x-grid-row,#role_grid-body .x-grid-row{
			vertical-align: middle !important;
		}
		
	</style>
<body>
<script type="text/javascript">

	var roleMap = [];
	var roleData;
	$.ajax({
		type:'GET',
		url:$service.portal+'/fw.System/com.taole.usersystem.domain.Role$Type/enums',
		dataType:'json',
		async:false,
		success:function(data){
			roleData = data;
			if(data && data.length > 0){
				for(var i = 0; i < data.length; i++){
					var item = data[i];
					roleMap[item.name] = item.local;
				}
			}
		},
		failure:function(){
			
		}
	});

	Ext.onReady(function(){
		var entityId = null;
		var entityName = 'us.Role';
		var entityAction = 'create';
		var role_store = null;
		
		//获取权限
		function createPerItem(){
			var roleArray =[];
			$.ajax({
				type:'GET',
				url:$ctx + '/portal/data/permission.json',
				dataType:'json',
				async:false,
				success:function(data){
					var length = data.length;
					for(var i = 0; i < length; i++){
						var roleObj = data[i];
						var children = roleObj.children;
						var columnLayout = [];
						var items = [];
						columnLayout ={
							layout:'column',
							frame:true,
							style:{
								'border':'0px'
							},
							items:[{
								columnWidth:1,
								width:131,
								xtype:'checkbox',
								margin:'-3px 0px -1px 0px',
								boxLabel:'全选'
							}]	
						};
						items.push(columnLayout);
						children = children.reverse();
						for(var j =0; j < children.length; j++){
							var child = children[j];
							if(j%4 == 0){
								columnLayout = {
									layout:'column',
									frame:true,
									style:{
										'border':'0px'
									},
									items:[{
										columnWidth:.25,
										width:131,
										xtype:'checkbox',
										margin:'-3px 0px -1px 0px',
										boxLabel:child.text
									}]
								};
							}else{
								columnLayout.items.push({
									columnWidth:.25,
									width:131,
									xtype:'checkbox',
									margin:'-3px 0px -1px 0px',
									boxLabel:child.text
								});
							}
							if((j+1)%4 == 0 || j == children.length-1){
								items.push(columnLayout);
							}
						}
						roleArray.push({
							xtype:'fieldset',
							collapsible:true,
							margin:'5px',
							frame:true,
							title:roleObj.text,
							layout:'fit',
							items:[items]
						});
					}
				},
				failure:function(){
				
				}
			});
			
			return roleArray;
		}
		//新增角色
		window.showRoleForm = function(id){
			var title ="新增";
			if (id) {
				entityId = id;
				title ="修改";
			} else {
				entityId = null;
			}
			var win = Ext.getCmp("role_win");
			if(!win){
				win = Ext.create("Ext.window.Window",{
					title:title+'角色信息',
					height:300,
					id:'role_win',
					width:600,
					modal:true,
					layout:'fit',
					border:false,
					items:[{
						xtype:'tabpanel',
						layout:'fit',
						items:[{
							xtype:'form',
							title:'基本信息',
							padding:'10px 5px 0xp 5px',
							layout:'anchor',
							id:'role_form',
							border:false,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							frame:true,
							items:[{
								xtype:'textfield',
								fieldLabel:'角色名称',
								name:'name',
								labelAlign:'right',
								labelWidth:80,
								allowBlank:false,
								anchor:'90%'
							},{
								xtype:'combobox',
								fieldLabel:'角色类型',
								name:'type',
								labelAlign:'right',
								labelWidth:80,
								allowBlank:false,
								anchor:'90%',
								displayField: 'local',
							    valueField: 'name',
							    store:Ext.create("Ext.data.Store",{
							    	fields: ['local', 'name'],
							    	data: roleData
							    })
							},{
								xtype:'textarea',
								fieldLabel:'角色描述',
								name:'description',
								labelWidth:80,
								height:100,
								labelAlign:'right',
								allowBlank:false,
								anchor:'90%'
							}]
						},{
							title:'配置权限',
							frame:true,
							xtype:'form',
							id:'add_per_form',
							autoScroll:true,
							border:false,
							style:{
								'border':'0px'
							},
							items:[]
						},{
							title:'赋予用户',
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
									fieldset.add(addUserTo('userList',600,keyword,"",id));
									fieldset.doLayout();
								}
							},{
								xtype:'button',
								text:'重置',
								x:295,
								y:5,
								width:80,
								handler:function(){
									Ext.getCmp("partWord").setValue("");
								}
							},{
								xtype:'fieldset',
								id:'userList',
								title:'用户列表',
								minHeight:200,
								width:550,
								x:10,
								y:35,
								items:[]
							}]
						},{
							title:'赋予用户组',
							id:'add_group_form',
							xtype:'form',
							frame:true,
							style:{
								'border':'0px'
							},
							border:false,
							autoScroll:true,
							layout:'absolute',
							items:[{
								xtype:'textfield',
								fieldLabel:'关键字',
								labelWidth:45,
								labelAlign:'right',
								id:'partWord1',
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
									var keyword = Ext.getCmp('partWord1').getValue();
									var fieldset = Ext.getCmp('groupList');
									fieldset.removeAll();
									fieldset.add(addGroupTo(600,keyword));
									fieldset.doLayout();
								}
							},{
								xtype:'button',
								text:'重置',
								x:295,
								y:5,
								width:80,
								handler:function(){
									Ext.getCmp("partWord1").setValue("");
								}
							},{
								xtype:'fieldset',
								id:'groupList',
								title:'用户列表组',
								minHeight:200,
								width:550,
								x:10,
								y:35,
								items:[]
							}]
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'保存',
						handler : function() {
							var form = Ext.getCmp("role_form");
							if(form.getForm().isValid()){
								var data = Ext.ux.FormUtils.getDataObject(win.down("form"));
								if (entityId) {
									data.id = entityId;
								}
								data.entityName = entityName;
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
								
								var group_form = Ext.getCmp("add_group_form");
								var groupArray = group_form.query("checkbox");
								data.groupList =[];
								for(var i = 0; i < groupArray.length; i++){
									var checkbox = groupArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											data.groupList.push(checkbox.id);
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
								
								var url ="";
								if (id!=undefined && id != '') {
									data.id = id;
									url = $service.portal+'/us.Role/'+id+'/update';
								}else{
									data.id ="";
									url =$service.portal+'/us.Role/collection/create';
								}
								Ext.Ajax.request({
									method: 'post',
									url: url,
									jsonData: data,
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'保存失败：' + r.description);
										} else {
											refresh();
											win.close();
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
							win.destroy();
						}
					}]
				});
			}
			win.show();
			var per_form= Ext.getCmp("add_per_form");
			per_form.add(addPerTo(600));
			per_form.doLayout();
			var user_form= Ext.getCmp("userList");
			user_form.add(addUserTo('userList',600,"","",id));
			user_form.doLayout();

			var group_set= Ext.getCmp("groupList");
			group_set.add(addGroupTo(600,""));
			group_set.doLayout();
			var form = Ext.getCmp("role_form");
			form.getForm().reset();
			var grid = Ext.getCmp("role_grid");
			if(entityId){
				entityAction = 'update';
				var selRow =grid.getSelectionModel().getSelection()[0];
				
				form.getForm().loadRecord(selRow);
				Ext.Ajax.request({
					method: 'post',
					url: getRestAction('retrieve',entityId),
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
									Ext.getCmp(user.id).setValue(true);
								}
							}
							var groupList = data.groupList;
							if(groupList && groupList.length >0){
								for(var i = 0; i < groupList.length; i++){
									var group = groupList[i];
									Ext.getCmp(group.id).setValue(true)
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
			} else {
				win.down('form').getForm().reset();
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
		window.deleteRole = function(){
			var grid = Ext.getCmp("role_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length > 0){
				Ext.Msg.confirm("提示","确定要删除选中的角色记录?",function(btn){
					if(btn == 'yes'){
						
						var ids = [];
						for(var i=0; i<selRows.length; i++){
							ids.push("ids="+selRows[i].data.id);
						}
						//执行删除操作
						Ext.Ajax.request({
							method:'post',
							url: $service.portal + '/' + entityName + '/collection/batchDelete?'+ids.join("&"),
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
				Ext.Msg.alert("提示","请选择删除的角色记录!");
			}
		};
		/****************用户角色设置 开始*********************/
		window.grantRoleToUser = function(type){
			var grid = Ext.getCmp("role_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length > 0){
				var per_win = Ext.getCmp("per_win");
				if(!per_win){
					per_win = Ext.create('Ext.window.Window',{
						title: '赋予用户',
						height:300,
						id:'per_win',
						width:600,
						resizable:false,
						modal:true,
						layout:'fit',
						items:[{
							id:'add_user_form',
							xtype:'form',
							frame:true,
							style:{
								'border':'0px'
							},
							border:false,
							autoScroll:true,
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
									fieldset.add(addUserTo('userList',600,keyword,"",""));
									fieldset.doLayout();
									if(type==''){
										grantRoleUser(type, selRows[0].data.id);
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
								margin:'5px',
								width:550,
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
								var grid = Ext.getCmp("role_grid");
								var selRows = grid.getSelectionModel().getSelection();
								//var roleArray =[];
								//for(var i =0; i<selRows.length; i++){
								//	var row = selRows[i];
								//	roleArray.push("roles="+row.data.id);
								//}
								var url = $service.portal+'/us.Role/'+selRows[0].data.id+'/grantUser?'+userStr.join("&");
								Ext.Ajax.request({
									url:url,
									method:'POST',
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'赋予用户失败：' + r.description);
										} else {
											per_win.destroy();
											Ext.getCmp('role_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'赋予用户失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								per_win.destroy();
							}
						}]
					})
				}
				per_win.show();
				var per_form = Ext.getCmp("userList");
				per_form.add(addUserTo('userList',600,"",'',""));
				per_form.doLayout();
				if(type==''){
					grantRoleUser(type, selRows[0].data.id);
				}
			}else if(selRows.length == 0){
				Ext.Msg.alert("提示","请选择要赋予用户的角色记录!");
			}else{
				Ext.Msg.alert("提示","请选择一条要赋予用户的角色记录!");
			}
		}
		
		function grantRoleUser(type, id){
			if(type==''){
				Ext.Ajax.request({
					method: 'post',
					url: $service.portal+'/us.Role/'+id+'/retrieve',
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
		
		function createUserGrid(){
			Ext.define('userModel',{
				extend:'Ext.data.Model',
				fields:['id','name','description','email','nickName','password','uri','regDate','expired','roles','personId','realName','attributes','group','mobile','status']
			});
			user_store = Ext.create('Ext.data.Store',{
				model: 'userModel',//数据模型
		        remoteSort: true,
		        autoLoad:false,
		        pageSize:20,
		        proxy: {
		            type: 'ajax',
		            url: $service.portal + '/us.User/collection/query',
		            reader: {
		                root: 'items',
		                totalProperty: 'total'
		            },
		            simpleSortMode: true
		        }
			});
			user_store.load({
		    	params:{
		    		start: 0,
	        		limit: 20
	        	}
		    });
		    
			var user_grid = Ext.create('Ext.grid.Panel',{
				store: user_store,
				id:'user_grid',
				border:false,
				region:'center',
				columnLines:true,
				columns:[
					{xtype: 'rownumberer',text: '序号',align:'center',width:40},
					{text:'姓名',width:150,dataIndex:'nickName'},
					{text:'身份证',width:150,dataIndex:''},
					{text:'邮箱',width:100,dataIndex:'email'},
					{text:'手机号',width:100,dataIndex:'mobile'},
					{text:'注册日期',width:80,dataIndex:'regDate',renderer:function(v){
						return v.substr(0,10);
					}},
					{text:'过期日期',width:80,dataIndex:'',renderer:function(v){
						return v.substr(0,10);
					}},
					{text:'用户状态',width:80,dataIndex:'status'}
				],
				selModel: { //选中模型 
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
		        },
				multiSelect: true,//是否允许多选
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: user_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }]
			});
			return user_grid;
		}
		/**********************用户角色设置 结束*********************************/
		
		/**********************资源权限设置 开始*********************************/
		window.grantPermissionToRole = function(type){
			var grid = Ext.getCmp("role_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 1){
				var per_win = Ext.getCmp("per_win");
				if(!per_win){
					per_win = Ext.create('Ext.window.Window',{
						title: '配置权限',
						height:500,
						id:'per_win',
						width:850,
						modal:true,
						layout:'fit',
						items:[{
							id:'add_per_form',
							xtype:'form',
							autoScroll:true,
							frame:true,
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
								var grid = Ext.getCmp("role_grid");
								var selRows = grid.getSelectionModel().getSelection();
								//var roleArray =[];
								//for(var i =0; i<selRows.length; i++){
								//	var row = selRows[i];
								//	roleArray.push("roles="+row.data.id);
								//}
								var url = $service.portal+'/us.Role/'+selRows[0].data.id+'/grantPrivilege?'+ preStr.join("&");
								console.log(url);
								Ext.Ajax.request({
									url:url,
									method:'POST',
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'配置权限失败：' + r.description);
										} else {
											per_win.destroy();
											Ext.getCmp('role_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'配置权限失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								per_win.destroy();
							}
						}]
					})
				}
				per_win.show();
				var pre_form = Ext.getCmp("add_per_form");
				pre_form.add(addPerTo(600,'add'));
				pre_form.doLayout();
				
				if(type==''){
					var roleid = selRows[0].data.id;
					Ext.Ajax.request({
						method: 'post',
						url: $service.portal+'/us.Role/'+roleid+'/retrieve',
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
			}else if(selRows.length == 0){
				Ext.Msg.alert("提示","请选择要授权的角色记录!");
			}else{
				Ext.Msg.alert("提示","请选择一条授权的角色记录!");
			}
		};
		
		
		window.queryForPermission = function(){
			var per_win_query = Ext.getCmp("per_win_query");
			if(!per_win_query){
				per_win_query = Ext.create('Ext.window.Window',{
					title: '权限',
					height:300,
					id:'per_win_query',
					width:600,
					resizable:false,
					modal:true,
					layout:'fit',
					items:[{
						id:'per_form_query',
						xtype:'form',
						autoScroll:true,
						frame:true,
						style:{
							'border':'0px'
						},
						border:false,
						items:[]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'确定',
						handler:function(){
							var pre_form = Ext.getCmp("per_form_query");
							var preArray = pre_form.query("checkbox");
							var preStr =[];
							for(var i = 0; i < preArray.length; i++){
								var checkbox = preArray[i];
								var value = checkbox.getValue();
								if(checkbox.boxLabel !='全选'){
									if(value){
										preStr.push(checkbox.boxLabel+","+checkbox.id);
									}
								}
							}
							
							if(preStr.length == 1){
								Ext.getCmp("prile").setRawValue(preStr[0].split(",")[0]);
								Ext.getCmp("pri").setValue(preStr[0].split(",")[1]);
							}else if(preStr.length > 1){
								Ext.Msg.alert('提示','请选择一条数据');
								return false;
							}
							per_win_query.destroy();
						}
					},{
						text:'取消',
						handler:function(){
							per_win_query.destroy();
						}
					}]
				})
			}
			per_win_query.show();
			var pre_form = Ext.getCmp("per_form_query");
			pre_form.add(addPerTo(600,'add'));
			pre_form.doLayout();
		};
		
		window.grantGroupToRole = function(type){
			var grid = Ext.getCmp("role_grid");
			var width = grid.getWidth();
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 1){
				var group_win = Ext.getCmp("group_win");
				if(!group_win){
					group_win = Ext.create("Ext.window.Window",{
        				title:'赋予用户组',
						id:'group_win',
						height:320,
						width:640,
						modal:true,
						layout:'fit',
						items:[{
							xtype:'form',
							id:'add_group_form',
							frame:true,
							style:{
								'border':'0px'
							},
							layout:'absolute',
							autoScroll:true,
							border:false,
							items:[{
								x:5,
								y:5,
								width:200,
								xtype:'trigger',
								id:'organization',
								fieldLabel:'组织结构',
								labelWidth:55,
								labelAlign:'right',
								onTriggerClick: function(){
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
							},{
								xtype:'textfield',
								fieldLabel:'关键字',
								labelWidth:55,
								labelAlign:'right',
								id:'partWord1',
								x:205,
								y:5,
								width:200
							},{
								xtype:'button',
								text:'查询',
								x:410,
								y:5,
								width:60,
								handler:function(){
									var keyword = Ext.getCmp('partWord1').getValue();
									var fieldset = Ext.getCmp('userList1');
									fieldset.removeAll();
									fieldset.add(addGroupTo(600,keyword));
									fieldset.doLayout();
									if(type==''){
										grantRoleGroup(type, selRows[0].data.id);
									}
								}
							},{
								xtype:'button',
								text:'重置',
								x:480,
								y:5,
								width:60,
								handler:function(){
									Ext.getCmp("partWord1").setValue("");
								}
							},{
								xtype:'fieldset',
								id:'userList1',
								title:'用户列表组',
								margin:'5px',
								width:570,
								x:0,
								y:35,
								items:[]
							}]
						}],
						buttonAlign:'center',
						buttons:[{
							text:'确定',
							handler:function(){
								var form = Ext.getCmp("add_group_form");
								var groupArray = form.query("checkbox");
								var groupList = [];
								for(var i = 0; i < groupArray.length; i++){
									var checkbox = groupArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											groupList.push("groups="+checkbox.id);
										}
									}
								}
								var selRows = Ext.getCmp("role_grid").getSelectionModel().getSelection();
								//var roleList =[];
								//for(var j = 0; j<selRows.length; j++){
								//	var user = selRows[j];
								//	roleList.push("ids="+user.data.id);
								//}
								var url ="";
								if(groupList.length > 0){
									url = $service.portal+'/us.Role/'+selRows[0].data.id+'/grantGroup?'+groupList.join('&');
								}else{
									url = $service.portal+'/us.Role/'+selRows[0].data.id+'/grantGroup';
								}
								console.log(url);
								//执行用户分组操作
								Ext.Ajax.request({
									method:'post',
									url:url,
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'赋予用户组失败：' + r.description);
										} else {
											group_win.destroy();
											Ext.getCmp('role_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'赋予用户组失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								group_win.destroy();
							}
						}]
					})
				}
				group_win.show();
				var group_form = Ext.getCmp("userList1");
				group_form.add(addGroupTo(600,""));
				group_form.doLayout();
				
				if(type==''){
					grantRoleGroup(type, selRows[0].data.id);
				}
			}else if(selRows.length == 0){
				Ext.Msg.alert("提示","请选择要分组的角色记录!");
			}else{
				Ext.Msg.alert("提示","请选择一条要分组的角色记录!");
			}
		}
		
		function grantRoleGroup(type, id){
			if(type==''){
				Ext.Ajax.request({
					method: 'post',
					url: $service.portal+'/us.Role/'+id+'/retrieve',
					dataType:'json',
					async:false,
					success:function(data){
						var response = data.responseText;
						var data = eval("("+response+")");
						if(data){
							var groupList = data.groupList;
							if(groupList && groupList.length >0){
								for(var i = 0; i < groupList.length; i++){
									var group = groupList[i];
									Ext.getCmp(group.id).setValue(true)
								}
							}
						}
					},
					failure:function(){
						
					}
				});
			}
		}
		
		var refresh = function(){
			if (!role_store.isLoading()) {
				role_store.loadPage(role_store.currentPage);
				Ext.getCmp("role_grid").getSelectionModel().clearSelections();//deselectAll
			}
		};
		/**********************资源权限设置 结束*********************************/
		
		
		/**********************设置数据权限开始*********************************/
		window.setDataPrivilege = function(){
			var grid = Ext.getCmp("role_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 0){
				Ext.Msg.alert("提示","请选择要设置数据权限的角色记录!");
				return;
			}
			
			if(selRows.length > 1){
				Ext.Msg.alert("提示","请选择一条角色记录进行操作!");
				return;
			}
			
			var roleId = selRows[0].data.id;
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
			    		bizType: 'ROLE_DATAPRIVILEGE',
			    		bizId: roleId
			    	}).show();
			    }
			});
		}
		/**********************设置数据权限结束*********************************/
		
		//创建用户网格
		function createRoleGrid(){
			if (role_store) {
				return role_store;
			}
			Ext.define('roleModel',{
				extend:'Ext.data.Model',
				fields:['id','name','description','type']
			});
			role_store = Ext.create('Ext.data.Store',{
				model: 'roleModel',//数据模型
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
			role_store.load({
		    	params:{
		    		start: 0,
	        		limit: 20
	        	}
		    });
		    
			var role_grid = Ext.create('Ext.grid.Panel',{
				store: role_store,
				id:'role_grid',
				border:false,
				columnLines:true,
				columns:[
					{xtype: 'rownumberer',text: '序号',align:'center',width:40},
					{text:'角色名称',width:180,dataIndex:'name'},
					{text:'角色类型',width:80,dataIndex:'type',renderer:function(v,cls,rec){
						if(v in roleMap){
							return roleMap[v];
						}else{
							return v;	
						}
					}},
					{text:'角色描述',width:400,dataIndex:'description'},
					{text:'操作',width:320,renderer:function(v,cls,record){
						var roleId = record.data.id;
						if(roleId == "f545a97cd90c46c4b85d0daaf1fd04ac"){
							return '<div id="roleDiv"><input type="button" value="修&nbsp;改" disabled="disabled"  onclick="showRoleForm(\'' + roleId + '\')"  class="enableCls">'+
							'<input type="button" value="删&nbsp;除" disabled="disabled" onclick="deleteRole()"  class="enableCls">'+
							'<input type="button" value="配置权限" disabled="disabled" onclick="grantPermissionToRole(\'\')"  class="enableCls">'+
							'<input type="button" value="赋予用户" onclick="grantRoleToUser(\'\')"  class="enableCls">'+
							'<input type="button" value="赋予用户组" onclick="grantGroupToRole(\'\')"  class="enableCls"></div>';
						}else if(roleId == "aa192ae10bca42b592aca883e9c82bc9"){
							return '<div id="roleDiv"><input type="button" value="修&nbsp;改" disabled="disabled"  onclick="showRoleForm(\'' + roleId + '\')"  class="enableCls">'+
							'<input type="button" value="删&nbsp;除" disabled="disabled" onclick="deleteRole()"  class="enableCls">'+
							'<input type="button" value="配置权限" onclick="grantPermissionToRole(\'\')"  class="enableCls">'+
							'<input type="button" value="赋予用户" disabled="disabled" onclick="grantRoleToUser(\'\')"  class="enableCls">'+
							'<input type="button" value="赋予用户组" disabled="disabled" onclick="grantGroupToRole(\'\')"  class="enableCls"></div>';
						}else{
							return '<div id="roleDiv"><input type="button" value="修&nbsp;改"  onclick="showRoleForm(\'' + roleId + '\')"  class="enableCls">'+
							'<input type="button" value="删&nbsp;除" onclick="deleteRole()"  class="enableCls">'+
							'<input type="button" value="配置权限" onclick="grantPermissionToRole(\'\')"  class="enableCls">'+
							'<input type="button" value="赋予用户" onclick="grantRoleToUser(\'\')"  class="enableCls">'+
							'<input type="button" value="赋予用户组" onclick="grantGroupToRole(\'\')"  class="enableCls"></div>';
						}
					}}
				],
				selModel: { //选中模型 
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
		        },
		        listeners: {  
					'select':function(grid, record){
						var selRows = Ext.getCmp("role_grid").getSelectionModel().getSelection();
						if(selRows.length == 1){
							if(selRows[0].data.id == "f545a97cd90c46c4b85d0daaf1fd04ac"){
								Ext.getCmp("rolePer").disable(true);
								Ext.getCmp("roleDet").disable(true);
								Ext.getCmp("roleUser").enable(true);
								Ext.getCmp("roleGroup").enable(true);
							}else if(selRows[0].data.id == "aa192ae10bca42b592aca883e9c82bc9"){
								Ext.getCmp("roleDet").disable(true);
								Ext.getCmp("rolePer").enable(true);
								Ext.getCmp("roleUser").disable(true);
								Ext.getCmp("roleGroup").disable(true);
							}else{
								Ext.getCmp("roleDet").enable(true);
								Ext.getCmp("roleUser").enable(true);
								Ext.getCmp("roleGroup").enable(true);
								Ext.getCmp("rolePer").enable(true);
							}
						} else{
							Ext.getCmp("roleDet").enable(true);
							Ext.getCmp("roleUser").enable(true);
							Ext.getCmp("roleGroup").enable(true);
							Ext.getCmp("rolePer").enable(true);
						}
					}
		        },
		        tbar:Ext.create('Ext.toolbar.Toolbar', {
		        	id:'roleGridTbar',
		            items: [{
			        	text:'增加',
			        	id:'roleAdd',
			        	iconCls:'',
			        	handler:function(){
			        		showRoleForm();
			        	}
			        },{
			        	text:'删除',
			        	id:'roleDet',
			        	iconCls:'',
			        	handler:function(){
			        		deleteRole();
			        	}
			        },'-',{
			        	text:'配置权限',
			        	id:'rolePer',
			        	iconCls:'',
			        	handler:function(){
			        		grantPermissionToRole('');
			        	}
			        },{
			        	text:'赋予用户',
			        	id:'roleUser',
			        	handler:function(){
			        		grantRoleToUser('');
			        	}
			        },{
			        	text:'赋予用户组',
			        	id:'roleGroup',
			        	handler:function(){
			        		grantGroupToRole('');
			        	}
			        },'-',{
			        	text:'设置数据权限',
			        	handler:function(){
			        		setDataPrivilege();
			        	}
			        }]
		        }),
				multiSelect: true,//是否允许多选
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: role_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }]
			});
			return role_grid;
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
	        		id:'role-search-form',
	        		cls:'x-plain',
	        		layout:'hbox',
	        		items:[{
	        			xtype:'textfield',
	        			fieldLabel:'角色名称',
	        			name:'name',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:200
	        		},{
	        			xtype:'textfield',
	        			fieldLabel:'角色描述',
	        			name:'description',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:400
	        		},{
	        			xtype:'combobox',
	        			labelWidth:70,
	        			name:'type',
	        			labelAlign:'right',
	        			margin:'5px 0px 5px 0px',
	        			width:160,
	        			displayField: 'local',
					    valueField: 'name',
					    store:Ext.create("Ext.data.Store",{
					    	fields: ['local', 'name'],
					    	data: roleData
					    }),
	        			fieldLabel:'角色类型'
	        		},{
	        			xtype:'button',
	        			width:80,
	        			margin:'5px 10px 5px 10px',
	        			text:'查   询',
	        			handler:doQuery
	        		},{
	        			xtype:'button',
	        			width:80,
	        			margin:'5px 10px 5px 10px',
	        			text:'重 置',
	        			handler: function() {
	        				Ext.getCmp('role-search-form').getForm().reset();
	        			}
	        		}]
	        	}]
	        },{
	        	region:'center',
	        	layout:'fit',
	        	items:[createRoleGrid()]
	        }]
		});
	});
	
	doQuery = function(){
		var params = Ext.ux.FormUtils.getDataObject(Ext.getCmp('role-search-form'));
		var store = Ext.getCmp('role_grid').getStore();
		store.getProxy().extraParams = params;
		store.loadPage(1);
	};
</script>
</body>
</html>