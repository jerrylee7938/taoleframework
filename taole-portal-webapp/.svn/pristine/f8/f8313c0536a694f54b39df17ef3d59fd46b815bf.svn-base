<%@ page language="java" contentType="text/html; charset=UTF-8" 
 pageEncoding="UTF-8"%><html>
<head>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
	<style type="text/css">
		.delCls{
			width:70px;
			height:25px;
		}
		.enableCls{
			width:70px;
			height:25px;
		}
		#per_grid-body .x-grid-row{
			vertical-align: middle !important;
		}
		.app-color-red{
			color: blue !important;
		}
		.module-color-blue{
			color: red !important
		}
		.fun-color-green{
			color: green !important
		}
		.Diy-mask {  
			opacity: 0.5;  
			-moz-opacity: 0.5;
			filter: alpha(opacity=50);
			z-index: 100;  
		} 
		
		.x-grid-row-blue .x-grid-cell{
		    background-color:#EEEEE0;
		}
		.x-grid-row-selected .x-grid-cell-special{
		background-image:none;
		}
	</style>
<body>
<script type="text/javascript">

	var preMap = [];
	var permissionData;
	
	$.ajax({
		type:'GET',
		url:$service.portal+'/fw.System/com.taole.usersystem.domain.Privilege$Type/enums',
		dataType:'json',
		async:false,
		success:function(data){
			permissionData = data;
			if(data && data.length > 0){
				for(var i = 0; i < data.length; i++){
					var item = data[i];
					preMap[item.name] = item.local;
				}
			}
		},
		failure:function(){
			
		}
	});
	Ext.onReady(function(){
		var userPower = {'role':1,'group':1,"organization":1};
		//获取权限
		function createPerItem(){
			var fieldSetArray =[];
			$.ajax({
				type:'GET',
				url:'../data/permission.json',
				dataType:'json',
				async:false,
				success:function(data){
					var length = data.length;
					for(var i = 0; i < length; i++){
						var object = data[i];
						var children = object.children;
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
								fieldLabel:''
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
						for(var j = 0; j < children.length; j++){
							var child = children[j];
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
									fieldLabel:''
								},{
									columnWidth:.15,
									xtype:'label',
									text:child.text
								},{
									columnWidth:.4,
									xtype:'textfield',
									style:{
										'margin':'0px 5px 0px 0px'
									},
									labelWidth:0,
									fieldLabel:''
								},{
									columnWidth:.4,
									xtype:'textfield',
									labelWidth:0,
									fieldLabel:''
								}]
							})
						}
						var fieldSet = {
							xtype:'fieldset',
							title:object.text,
							collapsible:true,
							items:[items]
						}
						fieldSetArray.push(fieldSet);
					}
				},
				failure:function(){
				
				}
			})
			
			return fieldSetArray;
		}
		
		window.getCheckbox = function(tree,node){  
			var td=tree.getView().getNode(node).firstChild.firstChild;  
			var checkbox=td.getElementsByTagName('input')[0];  
			return checkbox;  
		}   
		window.setNode = function(tree,node,value){  
			var checkbox=getCheckbox(tree,node);  
			checkbox.disabled=value;  
			if(value==true)  
				checkbox.className=checkbox.className.replace('Diy-mask','')+' Diy-mask';  
			else  
				checkbox.className=checkbox.className.replace('Diy-mask','');  
		}  
		
		window.functionWin = function(){
			var functionWin = Ext.getCmp("functionWin");
			if(!functionWin){
				functionWin = Ext.create('Ext.window.Window',{
					title: '配置功能',
					height:300,
					id:'functionWin',
					width:600,
					resizable:false,
					modal:true,
					layout:'fit',
					items:[{
						xtype:'form',
						id:'query_fun_form',
						autoScroll:true,
						frame:true,
						style:{
							'border':'0px'
						},
						border:false,
						autoScroll:true,
						items:[]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'确定',
						handler:function(){
							var funList =[];
							var fun_form = Ext.getCmp("query_fun_form");
							var funArray = fun_form.query("treepanel");
							for(var i =0; i < funArray.length; i++){
								var tree = funArray[i];
								var rootNode = tree.getRootNode();
								rootNode.cascade(function(rec){
									var type = rec.data.type||"";
									if(type == "function" && rec.data.checked && !rec.data.disable){
										funList.push(rec.data.text +","+rec.data.id);
									}
								},true);
							}
							if(funList.length == 1){
								Ext.getCmp("funcName").setValue(funList[0].split(",")[0]);
								Ext.getCmp("funcId").setValue(funList[0].split(",")[1]);
							}else if(funList.length > 1){
								Ext.Msg.alert("提示","请选择一条功能数据");
								return false;
							}
							functionWin.destroy();
						}
					},{
						text:'取消',
						handler:function(){
							functionWin.destroy();
						}
					}]
				})
			}
			functionWin.show();
			var pre_form = Ext.getCmp("query_fun_form");
			pre_form.add(addPerTo1(600,300,true,'us.Function'));
			pre_form.doLayout();
		}
		
		window.configFunction = function(id){
			
				var fun_window = Ext.getCmp("fun_window");
				if(!fun_window){
					fun_window = Ext.create('Ext.window.Window',{
						title: '配置功能',
						height:300,
						id:'fun_window',
						width:600,
						resizable:false,
						modal:true,
						layout:'fit',
						items:[{
							xtype:'form',
							id:'add_fun_form',
							autoScroll:true,
							frame:true,
							style:{
								'border':'0px'
							},
							border:false,
							autoScroll:true,
							items:[]
						}],
						buttonAlign:'center',
						buttons:[{
							text:'取消',
							handler:function(){
								fun_window.destroy();
							}
						}]
					})
				}
				fun_window.show();
				showWaitMsg();
				var pre_form = Ext.getCmp("add_fun_form");
				pre_form.add();
				pre_form.doLayout();
				
				Ext.Ajax.request({
					url:$service.portal+'/us.Privilege/'+id+'/retrieve',
					method:'post',
					success:function(resp, opts){
						var pre_form = Ext.getCmp("add_fun_form");
						pre_form.add(addPerTo1(600,300,true,'us.Function'));
						pre_form.doLayout();
						var trees = fun_window.query("treepanel");
						for(var i=0; i<trees.length; i++){
							var rootNode = trees[i].getRootNode();
							rootNode.cascadeBy(function(rec){
								var type = rec.data.type;
								if(type =="application"){
									rec.set("cls","app-color-red");
								}else if(type == "module"){
									rec.set("cls","module-color-blue");
								}else if(type == "function"){
									rec.set("cls","fun-color-green");
								}
								if(rec.data.privilegeId == id){
									rec.set('checked',true);
								}else if(rec.data.privilegeId!=''){
									rec.set('checked',true);
									rec.set('disable',true);
									setNode(trees[i],rec,true);
								}
							},true);
						}
						hideWaitMsg();
						var data = eval("(" + resp.responseText + ")");
						if(data){
							var funList = data.functionList;
							if(funList && funList.length >0){
								for(var i = 0; i < funList.length; i++){
									var fun = funList[i];
									var form = Ext.getCmp("add_fun_form");
									var treeArray = form.query("treepanel");
									for(var j = 0; j<treeArray.length; j++){
										var tree = treeArray[j];
										var node = tree.getStore().getNodeById(fun.id);
										if(node){
											node.set("checked",true);
										}
									}
								}
							}
						}
					},
					failure:function(resp, opts){
						Ext.Msg.alert("提示",'配置功能失败：' + resp.responseText);
					}
				})
			
			
			
		}

		
		//新增角色
		window.addPermission1 = function(type){
			var per_win = Ext.getCmp("per_win");
			var readOnly = type =='add'?false:true;
			var title = type == 'add'?'新增':'修改';
			if(!per_win){
				per_win = Ext.create("Ext.window.Window",{
					title: title+'资源权限信息',
					height:500,
					id:'role_win',
					width:600,
					itemId:type,
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
							height:90,
							collapsible:true,
							title:'<center>基本信息</center>',
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
									fieldLabel:'资源编码',
									readOnly:readOnly,
									id:'',
									name:'',
									labelAlign:'right',
									labelWidth:80,
									anchor:'90%'
								},{
									xtype:'combobox',
									fieldLabel:'资源类型',
									id:'',
									name:'',
									labelAlign:'right',
									labelWidth:80,
									displayField:'label',
									valueField:'value',
									store: new Ext.data.Store({  
							            proxy: {  
							                type: 'ajax',
							                url : '' ,
							                reader: {
									  			data:'totalCount',  
									  			type: 'json',  
									  			root: 'result'   
											}
							            },
							            fields:['label','value']
					            	}),
									anchor:'90%'
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
									fieldLabel:'资源名称',
									id:'',
									name:'',
									labelAlign:'right',
									labelWidth:80,
									anchor:'90%'
								},{
									xtype:'combobox',
									fieldLabel:'应用系统',
									id:'',
									name:'',
									displayField:'label',
									valueField:'value',
									store: new Ext.data.Store({  
							            proxy: {  
							                type: 'ajax',
							                url : '' ,
							                reader: {
									  			data:'totalCount',  
									  			type: 'json',  
									  			root: 'result'   
											}
							            },
							            fields:['label','value']
					            	}),
									labelAlign:'right',
									labelWidth:80,
									anchor:'90%'	
								}]
							}]
						},{
							xtype:'form',
							title:'<center>权限控制设置</center>',
							region:'center',
							style:{
								'border-radius':'0px'
							},
							id:'per_control',
							padding:'10px 5px',
							frame:true,
							autoScroll:true,
							items:[createPerItem()]
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'确定'
					},{
						text:'取消',
						handler:function(){
							per_win.destroy();
						}
					}]
				})
			}
			per_win.show();
		}
		
		//资源权限设置
		window.showPermWindow = function(reCode){
			var per_window = Ext.getCmp("per_win");
			if(!per_window){
				per_window = Ext.create('Ext.window.Window',{
					
				})
			}
			per_window.show();
		}
		
		window.addPerToRole = function(type,id){
			var grid = Ext.getCmp("per_grid");
			var width = grid.getWidth();
			var selRows = grid.getSelectionModel().getSelection();
				var role_window = Ext.getCmp("role_window");
				if(!role_window){
					role_window = Ext.create('Ext.window.Window',{
						title: '授权角色',
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
						buttons:[{
							text:'取消',
							handler:function(){
								role_window.destroy();
							}
						}],
						buttonAlign:'center'
					
					})
				}
				role_window.show();
				var role_form = Ext.getCmp("add_role_form");
				role_form.add(addRoleToList(600));
				role_form.doLayout();
	/*			Ext.getCmp("f545a97cd90c46c4b85d0daaf1fd04ac").hide(true);
				Ext.getCmp("aa192ae10bca42b592aca883e9c82bc9").hide(true);
				Ext.getCmp("f32835dd1469477fa6bcbe17ed6c29b0").hide(true);
				Ext.getCmp("267b2df4f6614987872fcebfe824ccac").hide(true);
				Ext.getCmp("1dc99ac8475f44519bbcd6194221c7e1").hide(true);
				Ext.getCmp("d50ec54ea0274c88a99fb6d434506163").hide(true);    */
				if(type !=""){
					
					$.ajax({
						type:'GET',
						url:$service.portal+'/us.Privilege/'+id+'/retrieve',
						dataType:'json',
						async:false,
						success:function(data){
							if(data){
								var roleList = data.roleList;
								if(roleList && roleList.length >0){
									for(var i = 0; i < roleList.length; i++){
										var role = roleList[i];
										Ext.getCmp(role.id).show(true);
										Ext.getCmp(role.id).setValue(true);
										
									}
								}
							}
						}
					})
				}
			
		}
		
		window.addRoleToList= function(width,type,id){
			var columnWidth = 1/Math.floor((width-100)/121);
			var count = Math.floor((width-100)/121);
			var roleArray =[];
			var typeMap = {};
			var configObj ={};
			var id = id ||"";
				$.ajax({
					type:'GET',
					url:$service.portal+'/fw.System/com.taole.usersystem.domain.Role$Type/enums',
					dataType:'json',
					async:false,
					success:function(data){
						if(data && data.length > 0){
							for(var i = 0; i < data.length; i++){
								var item = data[i];
								typeMap[item.name] = item.local;
							}
						}
					},
					failure:function(){
						
					}
				});
			
			
			$.ajax({
				type:'GET',
				url:$service.portal +'/us.Role/collection/query?page=1&start=0&limit=1000',
				dataType:'json',
				async:false,
				success:function(data){
					var items = data.items;
					var temp_items = [];
					var length = items.length;
					if("已赋予角色" in typeMap){
						
					}else{
						var obj = {};
						for(var i = 0; i < length; i++){
							var item = items[i];
							var type = item["type"];
							if(type in obj){
								obj[type].push(item);
							}else{
								obj[type]=[item];
							}
						}		
						for(var k in obj){
							var children = obj[k];
							var columnLayout = [];
							var _items = [];
							columnLayout ={
								layout:'column',
								frame:true,
								style:{
									'border':'0px'
								},
								items:[]	
							};
							_items.push(columnLayout);
							for(var j =0; j < children.length; j++){
								var child = children[j];
								if(j%count == 0){
									columnLayout = {
										layout:'column',
										frame:true,
										style:{
											'border':'0px'
										},
										items:[{
											columnWidth:columnWidth,
											width:121,
											xtype:'checkbox',
											id:child.id,
											margin:'-3px 0px 1px 0px',
											boxLabel:child.name,
											hidden:true,
											listeners:{
												change:function(check, newValu){
													changeStatus(check, newValu);
												}
											}
										}]
									};
								}else{
									columnLayout.items.push({
										columnWidth:columnWidth,
										width:121,
										id:child.id,
										xtype:'checkbox',
										margin:'-3px 0px 1px 0px',
										boxLabel:child.name,
										hidden:true,
										listeners:{
											change:function(check, newValu){
												changeStatus(check, newValu);
											}
										}
									});
								}
								if((j+1)%count == 0 || j == children.length-1){
									_items.push(columnLayout);
								}
							}
							var name ="";
							if(k in typeMap){
								name = typeMap[k];
							}else{
								name = k;
							}
							var fieldSet = Ext.create('Ext.form.FieldSet',{
								xtype:'fieldset',
								collapsible:true,
								margin:'5px',
								frame:true,
								id:k,
								title: name,
								layout:'fit',
								items:[]
							})
							fieldSet.add(_items);
							fieldSet.doLayout();
							roleArray.push(fieldSet);
						}
					}
					
				},
				failure:function(){
				
				}
			});
			return roleArray;
		}
		
		//相关用户
		
		window.relatedUsers = function(id){
			var grid = Ext.getCmp("per_grid");
			var width = grid.getWidth();
				var user_window = Ext.getCmp("user_window");
				if(!user_window){
					user_window = Ext.create('Ext.window.Window',{
						title: '已授权用户',
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
							style:{
								'border':'0px'
							},
							border:false,
							autoScroll:true,
							layout:'absolute',
							items:[{
								xtype:'fieldset',
								title:'用户列表',
								id:'userList',
								width:545,
								x:20,
								y:5,
								items:[]
							}]
						}],
						buttonAlign:'center',
						buttons:[{
							text:'取消',
							handler:function(){
								user_window.destroy();
							}
						}]
					})
				}
				user_window.show();
				var user_form = Ext.getCmp("userList");
				user_form.add(addRelatedUserTo('userList',600,"",id));
				user_form.doLayout();
				
			
		}
		
		window.addRelatedUserTo = function(_id,width,type,id){
			var columnWidth = 1/Math.floor((width-100)/121);
			var count = Math.floor((width-100)/121);
			var url =$service.portal+'/us.Privilege/'+id+'/retrieve';
		
			var id = id ||"";
			var _items = [];
			$.ajax({
				type:'GET',
				url:url,
				dataType:'json',
				async:false,
				success:function(data){
					var items = data.userList;
					var length = items.length;
					if(length > 0){
						var temp_array =[];
						
							var columnLayout = [];
							columnLayout ={
								layout:'column',
								frame:true,
								style:{
									'border':'0px'
								},
								items:[]	
							};
							_items.push(columnLayout);
							for(var i = 0; i < length; i++){
								var item = items[i];
								if(i%count == 0){
									columnLayout = {
										layout:'column',
										frame:true,
										style:{
											'border':'0px'
										},
										items:[{
											columnWidth:columnWidth,
											width:121,
											xtype:'checkbox',
											id:item.id,
											margin:'-3px 0px 1px 0px',
											boxLabel:item.name,
											checked:true,
										}]
									};
								}else{
									columnLayout.items.push({
										columnWidth:columnWidth,
										width:121,
										id:item.id,
										xtype:'checkbox',
										margin:'-3px 0px 1px 0px',
										boxLabel:item.name,
										checked:true,
									});
								}
								if((i+1)%count == 0 || i == length-1){
									_items.push(columnLayout);
								}
							}
					}
				},
				failure:function(){
					
				}
			});
			return _items;
		}	
		
		
		
		
		//相关岗位
		
		window.addPerToUser = function(type,id){
			var grid = Ext.getCmp("per_grid");
			var width = grid.getWidth();
				var post_window = Ext.getCmp("post_window");
				if(!post_window){
					post_window = Ext.create('Ext.window.Window',{
						title: '授权用户相关岗位',
						height:300,
						id:'post_window',
						width:600,
						resizable:false,
						modal:true,
						layout:'fit',
						items:[{
							id:'add_group_form',
							xtype:'form',
							frame:true,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							border:false,
							layout:'absolute',
							items:[{
								xtype:'fieldset',
								id:'groupList',
								title:'用户岗位列表',
								minHeight:220,
								width:545,
								x:10,
								items:[]
							}]
						}],
						buttons:[{
							text:'取消',
							handler:function(){
								post_window.destroy();
							}
						}],
						buttonAlign:'center'
					})
				}
				post_window.show();
				var group_form = Ext.getCmp("groupList");
				group_form.add(addGroupToList(600,"",1));
				group_form.doLayout();
				if(type!=""){
					checkToGroup(type,id);
				}
			
		}
		
		function checkToUser(type, id){
			if(type !=''){
				$.ajax({
					type:'GET',
					url:$service.portal+'/us.Privilege/'+id+'/retrieve',
					dataType:'json',
					async:false,
					success:function(data){
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
					}
				})
			}
		}
		// 相关分组
		window.addPerToGroup = function(type,id){
			var grid = Ext.getCmp("per_grid");
			var width = grid.getWidth();
			var selRows = grid.getSelectionModel().getSelection();
				var group_window = Ext.getCmp("group_window");
				if(!group_window){
					group_window = Ext.create('Ext.window.Window',{
						title: '授权用户组',
						height:300,
						id:'group_window',
						width:600,
						resizable:false,
						modal:true,
						layout:'fit',
						items:[{
							id:'add_group_form',
							xtype:'form',
							frame:true,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							border:false,
							layout:'absolute',
							items:[{
								xtype:'fieldset',
								id:'groupList',
								title:'用户组列表',
								minHeight:210,
								width:545,
								x:10,
								y:5,
								items:[]
							}]
						}],
						buttons:[{
							text:'取消',
							handler:function(){
								group_window.destroy();
							}
						}],
						buttonAlign:'center'
					})
				}
				group_window.show();
				var group_form = Ext.getCmp("groupList");
				group_form.add(addGroupToList(600,"",0));
				group_form.doLayout();
				if(type!=""){
					checkToGroup(type,id);
				}
			
		}
		window.addGroupToList = function(width,keyword,type){
			var columnWidth = 1/Math.floor((width-100)/150);
			var count = Math.floor((width-100)/150);
			var groupArray =[];
			var keyword = keyword||"";
			var bool = bool||true;
			var url ="";
			if(keyword !=""){
				url =$service.portal +'/us.Group/collection/query?partWord='+keyword+"&page=1&start=0&limit=1000&type="+type;
			}else{
				url =$service.portal +'/us.Group/collection/query?page=1&start=0&limit=1000&type='+type;
			}
			$.ajax({
				type:'GET',
				url:url,
				dataType:'json',
				async:false,
				success:function(data){
					var items = data.items;
					var length = items.length;
					if(length > 0){
						var _items = [];
						var columnLayout = [];
						columnLayout ={
							layout:'column',
							frame:true,
							style:{
								'border':'0px'
							},
							items:[]	
						};
						_items.push(columnLayout);
						for(var i = 0; i < length; i++){
							var item = items[i];
							if(i%count == 0){
								columnLayout = {
									layout:'column',
									frame:true,
									style:{
										'border':'0px'
									},
									items:[{
										columnWidth:columnWidth,
										width:140,
										xtype:'checkbox',
										id:item.id,
										margin:'-3px 0px 1px 0px',
										boxLabel:item.name,
										hidden:true,
										listeners:{
											change:function(check, newValu){
												changeStatus(check, newValu);
											}
										}
									}]
								};
							}else{
								columnLayout.items.push({
									columnWidth:columnWidth,
									width:140,
									id:item.id,
									xtype:'checkbox',
									margin:'-3px 0px 1px 0px',
									boxLabel:item.name,
									hidden:true,
									listeners:{
										change:function(check, newValu){
											changeStatus(check, newValu);
										}
									}
								});
							}
							if((i+1)%count == 0 || i == length-1){
								_items.push(columnLayout);
							}
						}
						var fieldSet = Ext.create('Ext.form.FieldSet',{
							xtype:'fieldset',
							collapsible:false,
							frame:true,
							style:style = {
								'border':'0px'
							},
							id:'group',
							layout:'fit',
							items:[]
						})
						fieldSet.add(_items);
						fieldSet.doLayout();
						groupArray.push(fieldSet);
					}
				},
				failure:function(){
				
				}
			});
			return groupArray;
		}
		function checkToGroup(type, id){
			if(type!=""){
				$.ajax({
					type:'GET',
					url:$service.portal+'/us.Privilege/'+id+'/retrieve',
					dataType:'json',
					async:false,
					success:function(data){
						if(data){
							var groupList = data.groupList;
							if(groupList && groupList.length >0){
								for(var i = 0; i < groupList.length; i++){
									var group = groupList[i];
									if(Ext.getCmp(group.id)){
										Ext.getCmp(group.id).setValue(true);
										Ext.getCmp(group.id).show(true);
									}
								}
							}else{
								
							}
						}
					}
				})
			}
		}
		//用户可以操作模块
		function getUserPermission() {
			var url = Ext.util.Format.format(URL_DICTIONARY,'fc952770af074a7981afc5cd1ce35616');
		//	url = 'http://portal.51taole.cn/taole-portal-service/service/rest/tk.Dictionary/fc952770af074a7981afc5cd1ce35616/childNodes'
				$.ajax({
					type:'GET',
					url:url,
					dataType:'json',
					async:false,
					success:function(data){
						if(data){
							for(var i=0;i<data.length;i++){
								var usreList =data[i];
								var name =usreList.name;
								var value =usreList.value;
								userPower[name] = value;
							}
						}
					},
					failure:function(){
						Ext.Msg.alert("提示",'获取信息失败：');
					}
				});

		}
		//创建权限网格
		function createPermissionGrid(){
			getUserPermission();
			Ext.define('perModel',{
				extend:'Ext.data.Model',
				fields:['id','name','type','description','entityName','roleList','groupList','functionList','userList','isGrant']
			})
			var per_store = Ext.create('Ext.data.Store',{
				model: 'perModel',//数据模型
		        remoteSort: true,
		        autoLoad:false,
		        pageSize:20,
		        proxy: {
		            type: 'ajax',
		            url:$service.portal+'/us.User/collection/queryPrivilege?userId='+userId,
		            reader: {
		                root: 'items',
		                totalProperty: 'total'
		            },
		            simpleSortMode: true
		        },
	        listeners: {
		            load: function (me, records, success, opts) {
		            	 var selModel = per_grid.getSelectionModel();
		            	for(var i=0;i<records.length;i++){
		            		var num =records[i].index;
		            		if(records[i].data.isGrant){
		            			selModel.select(i, true,true); 
		            		}else{
		            			selModel.deselect(i,false); 
		            		}
		            	}
		               
		            }
		        },
			});
			per_store.load({
		    	params:{
		    		start: 0,
	        		limit: 20
	        	}
		    });
			
			var per_grid = Ext.create('Ext.grid.Panel',{
				store: per_store,
				id:'per_grid',
				border:false,
				columnLines:true,
				columns:[
					{xtype: 'rownumberer',text: '序号',align:'center',width:40},
					{text:'权限名称',width:200,dataIndex:'name'},
					{text:'权限描述',width:200,dataIndex:'description'},
					{text:'权限类型',width:80,dataIndex:'type',renderer:function(v,cls,rec){
						if(v in preMap){
							return preMap[v];
						}else{
							return v;	
						}
					}},
					{text:'<center>操作</center>',width:450,align:'center',dataIndex:'',renderer:function(v,cls,record){
						
						
						var str ='<div><input type="button" value="相关用户" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="relatedUsers(\''+record.data.id+'\')"  class="enableCls">'+
						'<input type="button" value="相关功能" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="configFunction(\''+record.data.id+'\')"  class="enableCls">';
						
						if(userPower.group == 1){
							str+='<input type="button" value="相关分组" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToGroup(\'edit\',\''+record.data.id+'\')"  class="enableCls">';
						}
						if(userPower.role==1){
							str+='<input type="button" value="相关角色" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToRole(\'edit\',\''+record.data.id+'\')"  class="enableCls">';
						}
						if(userPower.organization==1){
							str+='<input type="button" value="相关岗位" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToUser(\'edit\',\''+record.data.id+'\')"  class="enableCls">';
						}
						str +='</div>';
						
						return str;
						/*
						return '<div><input type="button" value="相关用户" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="relatedUsers(\''+record.data.id+'\')"  class="enableCls">'+
						'<input type="button" value="相关功能" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="configFunction(\''+record.data.id+'\')"  class="enableCls">'+
						'<input type="button" value="相关岗位" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToUser(\'edit\',\''+record.data.id+'\')"  class="enableCls">'+
						'<input type="button" value="相关角色" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToRole(\'edit\',\''+record.data.id+'\')"  class="enableCls">'+
						'<input type="button" value="相关分组" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToGroup(\'edit\',\''+record.data.id+'\')"  class="enableCls"></div>'
						*/
					}}
				],
				selModel: { //选中模型 
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel 
		        //	checkOnly:true,
		       // 	hearder:'&quot;&quot;',
		        },
		        listeners:{
			       cellclick:function(sm,td,cellIndex,record,tr,rowIndex,keepExisting){  
			    	   console.log('2');
			                per_grid.getSelectionModel().select(rowIndex, true,true);
			         },
			      beforedeselect:function(sm,record,rowIndex,keepExisting){
			        	if(record.data.isGrant){
			        		return false;
			        	}
	                } 
	            },
		        viewConfig: { 
			        stripeRows: false, 
			        getRowClass: function(record,v,s,l) { 
			        	if(record.data.isGrant){
			        		return 'x-grid-row-blue style="disabled:true;"';
			        	}
			        }
			    },
		        multiSelect: true,//是否允许多选
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: per_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }],
			    tbar:[{
		        	text:'权限申请流程',
		        	iconCls:'',
		        	handler:function(){
		        		var url = '../../image/ablum/qxsqlc.jpg';
			    		var flowPicWithFlag = Ext.create("Ext.window.Window",{
							title:'权限申请流程图',
							height:'100%',
		                    modal:true,
		                    width:'100%',
		                    autoScroll:true,
							maximizable: true,         
					        minimizable: true,        
		                    html:"<img src='"+url+"' style='width:100%'></img>",
		                    buttonAlign:'center',
							buttons:[{
								text:'关闭',
								handler:function(){
									flowPicWithFlag.destroy();
								}
							}]
						});
						flowPicWithFlag.show();
			    				
		        	}
		        },'-',{
		        	text:'申请权限',
		        	iconCls:'',
		        	handler:function(){
		        		var grid=Ext.getCmp("per_grid");
		        		var selRows = grid.getSelectionModel().getSelection();
		        		var dataID =[];
		        		for(var i = 0, length = selRows.length; i < length; i++){
							var record = selRows[i];
							if(!record.data.isGrant){
								dataID.push(record.data.id);
							}
						}
		        		if(dataID.length ==0){
		        			Ext.Msg.alert("提示",'请选择你要申请的权限！');
		        		}else{
		        			var ids = dataID.toString();
		        			var _url=$service.portal+'/us.User/'+userId+'/exportApplyPrivilege?privilegeIds='+ids
		        			window.open(_url,'_self');
		        		}
		        	}
		        }]
		        
			})
		
			return per_grid;
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
	        		cls:'x-plain',
	        		id:'pre_search_form',
	        		layout:'hbox',
	        		items:[{
	        			xtype:'combobox',
						name:'type',
						labelAlign:'right',
						fieldLabel:'类型',
						anchor:'70%',
						displayField: 'local',
					    valueField: 'name',
					    store:Ext.create("Ext.data.Store",{
					    	fields: ['local', 'name'],
					    	data: permissionData
					    }),
						margin:'5px 0px 5px 0px',
	        			labelWidth:50,
	        			width:200
	        		},{
	        			xtype:'textfield',
	        			fieldLabel:'名称',
	        			name:'name',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:50,
	        			width:200
	        		},{
	        			xtype:'textfield',
	        			fieldLabel:'描述',
	        			name:'description',
	        			labelWidth:50,
	        			labelAlign:'right',
	        			margin:'5px 0px 5px 0px',
	        			width:300
	        		},{
	        			xtype:'hidden',
	        			name:'funcId',
	        			id:'funcId'
	        		},{
	        			xtype:'combobox',
	        			id:'funcName',
	        			labelWidth:70,
	        			labelAlign:'right',
	        			margin:'5px 0px 5px 0px',
	        			width:230,
	        			fieldLabel:'功能',
	        			onTriggerClick:function(){
	        				functionWin();
	        			}
	        		},{
	        			xtype:'button',
	        			width:60,
	        			margin:'5px 10px 5px 10px',
	        			text:'查   询',
	        			handler:function(){
	        				doQuery();
	        			}
	        		},{
	        			xtype:'button',
	        			width:60,
	        			margin:'5px 0px 5px 0px',
	        			text:'重 置',
	        			handler:function(){
	        				var form = Ext.getCmp("pre_search_form").getForm();
	        				form.reset();
	        			}
	        		}]
	        	}]
	        },{
	        	region:'center',
	        	layout:'fit',
	        	items:[createPermissionGrid()]
	        }]
		})
	})
	
	function doQuery(){
		var params = Ext.ux.FormUtils.getDataObject(Ext.getCmp('pre_search_form'));
		var store = Ext.getCmp('per_grid').getStore();
		store.getProxy().extraParams = params;
		store.loadPage(1);
	}
</script>
</body>
</html>