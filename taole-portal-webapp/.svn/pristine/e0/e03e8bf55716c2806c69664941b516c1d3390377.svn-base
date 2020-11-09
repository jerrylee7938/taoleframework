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
		
		window.configFunction = function(){
			
			var grid = Ext.getCmp("per_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 1){
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
							text:'保存',
							handler:function(){
								//var preArray = [];
								//for(var i =0; i< selRows.length; i++){
								//	var record = selRows[i];
								//	preArray.push(record.data.id);
								//} 
								
								var funList =[];
								var fun_form = Ext.getCmp("add_fun_form");
								var funArray = fun_form.query("treepanel");
								for(var i =0; i < funArray.length; i++){
									var tree = funArray[i];
									var rootNode = tree.getRootNode();
									rootNode.cascade(function(rec){
										var type = rec.data.type||"";
										if(type == "function" && rec.data.checked && !rec.data.disable){
											funList.push("funcs="+rec.data.id);
										}
									},true);
								}
								
								Ext.Ajax.request({
									url:$service.portal+'/us.Privilege/'+selRows[0].data.id+'/grantFunction?'+funList.join("&"),
									method:'post',
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'配置功能失败：' + r.description);
										} else {
											fun_window.destroy();
											Ext.getCmp('per_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'配置功能失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								fun_window.destroy();
							}
						}]
					})
				}
				fun_window.show();
				showWaitMsg();
				var id = selRows[0].data.id;
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
			}else if(selRows.length == 0){
				Ext.Msg.alert('提示',"请选择要授予的权限的记录!");
			}else{
				Ext.Msg.alert('提示',"请选择一条要授予的权限的记录!");
			}
			
			
		}
/*******************************************新增|修改权限**************************************************/
		window.addPermission = function(type){
			var grid = Ext.getCmp('per_grid');
			var width = grid.getWidth();
			var height = grid.ownerCt.ownerCt.getHeight()-230;
			var title = type == 'add'?'新增':'修改';
			var per_win = Ext.getCmp("per_win");
			if(!per_win){
				per_win = Ext.create('Ext.window.Window',{
					title: title+'权限信息',
					height:300,
					id:'per_win',
					width:600,
					itemId:type,
					resizable:false,
					modal:true,
					layout:'fit',
					border:false,
					items:[{
						xtype:'tabpanel',
						border:false,
						items:[{
							title:'权限信息',
							xtype:'form',
							region:'north',
							id:'per_bais_form',
							frame:true,
							style:{
								'margin-bottom':'10px',
								'border-radius':'0px'
							},
							layout:'anchor',
							items:[{
								xtype:'combobox',
								name:'type',
								labelAlign:'right',
								labelWidth:60,
								fieldLabel:'类型',
								anchor:'70%',
								displayField: 'local',
							    valueField: 'name',
							    store:Ext.create("Ext.data.Store",{
							    	fields: ['local', 'name'],
							    	data: permissionData
							    })
							},{
								xtype:'textfield',
								fieldLabel:'名称',
								labelWidth:60,
								name:'name',
								labelAlign:'right',
								anchor:'70%'
							},{
								xtype:'textarea',
								fieldLabel:'描述',
								labelWidth:60,
								height:80,
								name:'description',
								labelAlign:'right',
								anchor:'90%'
							}]
						},{
							title:'授权用户',
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
									fieldset.add(addUserTo('userList',600,keyword));
									fieldset.doLayout();
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
								title:'用户列表',
								id:'userList',
								width:545,
								x:20,
								y:35,
								items:[]
							}]
						},{
							title:'授权角色',
							xtype:'form',
							id:'add_role_form',
							hidden:userPower.role==1?false:true,
							autoScroll:true,
							frame:true,
							style:{
								'border':'0px'
							},
							border:false,
							items:[]
						},{
							title:'授权用户组',
							xtype:'form',
							id:'add_group_form',
							hidden:userPower.group==1?false:true,
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
								labelWidth:55,
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
								width:60,
								handler:function(){
									var keyword = Ext.getCmp('partWord1').getValue();
									var fieldset = Ext.getCmp('userList1');
									fieldset.removeAll();
									fieldset.add(addGroupTo(600,keyword,'',0));
									fieldset.doLayout();
								}
							},{
								xtype:'button',
								text:'重置',
								x:280,
								y:5,
								width:60,
								handler:function(){
									Ext.getCmp("partWord1").setValue("");
								}
							},{
								xtype:'fieldset',
								id:'userList1',
								title:'用户组列表',
								width:545,
								x:20,
								y:35,
								items:[]
							}]
						},{
							title:'授权岗位',
							xtype:'form',
							id:'add_group2_form',
							hidden:userPower.organization==1?false:true,
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
								labelWidth:55,
								labelAlign:'right',
								id:'partWord2',
								x:5,
								y:5,
								width:200
							},{
								xtype:'button',
								text:'查询',
								x:210,
								y:5,
								width:60,
								handler:function(){
									var keyword = Ext.getCmp('partWord2').getValue();
									var fieldset = Ext.getCmp('userList2');
									fieldset.removeAll();
									fieldset.add(addGroupTo(600,keyword,'',1));
									fieldset.doLayout();
								}
							},{
								xtype:'button',
								text:'重置',
								x:280,
								y:5,
								width:60,
								handler:function(){
									Ext.getCmp("partWord2").setValue("");
								}
							},{
								xtype:'fieldset',
								id:'userList2',
								title:'岗位列表',
								width:545,
								x:20,
								y:35,
								items:[]
							}]
						},{
							title:'配置功能',
							xtype:'form',
							id:'add_fun_form',
							autoScroll:true,
							itemId:'unload',
							frame:true,
							style:{
								'border':'0px'
							},
							border:false,
							autoScroll:true,
							items:[],
							listeners: {
								beforeactivate: function(){
									showWaitMsg();
								},
			                    activate: function(tab){
			                        setTimeout(function() {
			                        	var trees = per_win.query("treepanel");
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
			                        }, 1);
			                    }
			                }
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'保存',
						handler:function(){
							var form = Ext.getCmp("per_bais_form");
							var per_win = Ext.getCmp('per_win');
							var type = per_win.itemId;
							if(form.getForm().isValid()){
								var data = Ext.ux.FormUtils.getDataObject(form);
								var url ="";
								if (type == 'edit') {
									var selRow = Ext.getCmp("per_grid").getSelectionModel().getSelection()[0];
									data.id = selRow.data.id;
									url = $service.portal+'/us.Privilege/'+id+'/update';
								}else{
									data.id ="";
									url =$service.portal+'/us.Privilege/collection/create';
								}
								
								data.entityName ="us.Privilege";
								
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
								
								var group2_form = Ext.getCmp("add_group2_form");
								var group2Array = group2_form.query("checkbox");
								for(var i = 0; i < group2Array.length; i++){
									var checkbox = group2Array[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											data.groupList.push(checkbox.id);
										}
									}
								}
								
								data.functionList =[];
								var fun_form = Ext.getCmp("add_fun_form");
								var funArray = fun_form.query("treepanel");
								for(var i =0; i < funArray.length; i++){
									var tree = funArray[i];
									var rootNode = tree.getRootNode();
									rootNode.cascade(function(rec){
										var type = rec.data.type||"";
										if(type == "function" && rec.data.checked && !rec.data.disable ){
											data.functionList.push(rec.data.id);
										}
									},true);
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
											per_win.destroy();
											Ext.getCmp('per_grid').getStore().load();
											Ext.getCmp("per_grid").getSelectionModel().deselectAll();
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
							per_win.destroy();
						}
					}]
				})
			}
			per_win.show();
			var user_fieldset= Ext.getCmp("userList");
			user_fieldset.add(addUserTo('userList',600,""));
			user_fieldset.doLayout();
			var role_form= Ext.getCmp("add_role_form");
			role_form.add(addRoleTo(600));
			role_form.doLayout();
			Ext.getCmp("f545a97cd90c46c4b85d0daaf1fd04ac").hide(true);
			Ext.getCmp("aa192ae10bca42b592aca883e9c82bc9").hide(true);
			var fun_form= Ext.getCmp("add_fun_form");
			fun_form.add(addPerTo1(600,300,true,'us.Function'));
			fun_form.doLayout();
			
			var group_set= Ext.getCmp("userList1");
			group_set.add(addGroupTo(600,'','',0));
			group_set.doLayout();
			
			var group2_set= Ext.getCmp("userList2");
			group2_set.add(addGroupTo(600,'','',1));
			group2_set.doLayout();
			
			var grid = Ext.getCmp("per_grid");
			var form = Ext.getCmp("per_bais_form").getForm();
			if(type == 'edit'){
				var selRow = grid.getSelectionModel().getSelection()[0];
				form.loadRecord(selRow);
				var id = selRow.data.id;
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
									Ext.getCmp(user.id).setValue(true);
								}
							}
							var groupList = data.groupList;
							if(groupList && groupList.length >0){
								for(var i = 0; i < groupList.length; i++){
									var group = groupList[i];
									Ext.getCmp(group.id).setValue(true);
								}
							}
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
				
			}else{
				form.reset();	
			}
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
		//删除
		window.deletePermission = function(){
			var grid = Ext.getCmp("per_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length >0){
				Ext.Msg.confirm("提示","确定要删除选中的权限记录?",function(btn){
					if(btn == 'yes'){
						//执行删除操作
						var perIdArray = [];
						for(var i = 0, length = selRows.length; i < length; i++){
							var record = selRows[i];
							perIdArray.push(record.data.id);
						}
						var url;
						if(selRows.length >1){
							url = $service.portal+'/us.Privilege/collection/batchDelete?ids='+perIdArray.toString();
						}else{
							url =$service.portal+'/us.Privilege/'+perIdArray.toString()+'/delete';
						}
						//执行删除操作
						Ext.Ajax.request({
							method:'post',
							url:url,
							success:function(resp, opts){
								var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'删除失败：' + r.description);
								} else {
									Ext.getCmp('per_grid').getStore().load();
								}
							},
							failure:function(resp, opts){
								Ext.Msg.alert("提示",'删除失败：' + resp.responseText);
							}
						})
					}
				})
			}else{
				Ext.Msg.alert("提示","请选择删除的权限记录!");
			}
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
		
		window.addPerToRole = function(type){
			var grid = Ext.getCmp("per_grid");
			var width = grid.getWidth();
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 1){
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
						buttonAlign:'center',
						buttons:[{
							text:'保存',
							handler:function(){
								var preArray = [];
								for(var i =0; i< selRows.length; i++){
									var record = selRows[i];
									preArray.push("ids="+record.data.id);
								} 
								var roleArray =[];
								var role_form =  Ext.getCmp("add_role_form");
								var checkArray = role_form.query("checkbox");
								for(var j =0; j<checkArray.length; j++){
									var checkbox = checkArray[j];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											roleArray.push("roles="+checkbox.id);
										}
									}
								}
								
								Ext.Ajax.request({
									url:$service.portal+'/us.Privilege/'+selRows[0].data.id+'/grantRole?'+roleArray.join("&"),
									method:'post',
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'授权角色失败：' + r.description);
										} else {
											role_window.destroy();
											Ext.getCmp('per_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'授权角色失败：' + resp.responseText);
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
				role_form.add(addRoleTo(600));
				role_form.doLayout();
				Ext.getCmp("f545a97cd90c46c4b85d0daaf1fd04ac").hide(true);
				Ext.getCmp("aa192ae10bca42b592aca883e9c82bc9").hide(true);
				if(type !=""){
					var id = selRows[0].data.id;
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
										Ext.getCmp(role.id).setValue(true);
									}
								}
							}
						}
					})
				}
			}else if(selRows.length == 0){
				Ext.Msg.alert('提示',"请选择要授权角色的记录!");
			}else{
				Ext.Msg.alert('提示',"请选择一条要授权角色的记录!");
			}
		}
		window.addPerToUser = function(type){
			var grid = Ext.getCmp("per_grid");
			var width = grid.getWidth();
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 1){
				var user_window = Ext.getCmp("user_window");
				if(!user_window){
					user_window = Ext.create('Ext.window.Window',{
						title: '授权用户',
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
								xtype:'textfield',
								fieldLabel:'关键字',
								labelWidth:55,
								labelAlign:'right',
								id:'keyword',
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
									var keyword = Ext.getCmp('keyword').getValue();
									var fieldset = Ext.getCmp('userList');
									fieldset.removeAll();
									fieldset.add(addUserTo('userList',600,keyword,"",""));
									fieldset.doLayout();
									if(type !=''){
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
									Ext.getCmp('keyword').setValue("");
								}
							},{
								xtype:'fieldset',
								title:'用户列表',
								id:'userList',
								width:545,
								x:20,
								y:35,
								items:[]
							}]
						}],
						buttonAlign:'center',
						buttons:[{
							text:'保存',
							handler:function(){
								//var preArray = [];
								//for(var i =0; i< selRows.length; i++){
								//	var record = selRows[i];
								//	preArray.push("ids="+record.data.id);
								//} 
								var userArray =[];
								var user_form =  Ext.getCmp("add_user_form");
								var checkArray = user_form.query("checkbox");
								for(var j =0; j<checkArray.length; j++){
									var checkbox = checkArray[j];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											userArray.push("users="+checkbox.id);
										}
									}
								}
								
								Ext.Ajax.request({
									url:$service.portal+'/us.Privilege/'+selRows[0].data.id+'/grantUser?'+userArray.join("&"),
									method:'post',
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'授权用户失败：' + r.description);
										} else {
											user_window.destroy();
											Ext.getCmp('per_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'授权用户失败：' + resp.responseText);
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
				user_form.add(addUserTo('userList',600,""));
				user_form.doLayout();
				
				if(type !=''){
					checkToUser(type, selRows[0].data.id);
				}
				
			}else if(selRows.length == 0){
				Ext.Msg.alert('提示',"请选择要授予的权限的记录!");
			}else{
				Ext.Msg.alert('提示',"请选择一条要授予的权限的记录!");
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
		
		window.addPerToGroup = function(type,typeId){
			var grid = Ext.getCmp("per_grid");
			var width = grid.getWidth();
			var title = typeId == 0? '用户组':'岗位';
			var groupListAll = [];
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 1){
				var group_window = Ext.getCmp("group_window");
				if(!group_window){
					group_window = Ext.create('Ext.window.Window',{
						title: '授权'+title,
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
								x:5,
								y:5,
								width:200,
								hidden:typeId ==0? true:false,
								xtype:'trigger',
								fieldLabel:'组织结构',
								id:'organization',
								labelWidth:55,
								labelAlign:'right',
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
							},{
								xtype:'textfield',
								fieldLabel:'关键字',
								labelWidth:45,
								labelAlign:'right',
								id:'partWord1',
								x:typeId ==0? 5:205,
								y:5,
								width:200
							},{
								xtype:'button',
								text:'查询',
								x:typeId==0? 210:410,
								y:5,
								width:60,
								handler:function(){
									var keyword = Ext.getCmp('partWord1').getValue();
									var fieldset = Ext.getCmp('groupList');
									fieldset.removeAll();
									fieldset.add(addGroupTo(600,keyword,'',typeId));
									fieldset.doLayout();
									if(type!=""){
										checkToGroup(type, selRows[0].data.id);
									}
								}
							},{
								xtype:'button',
								text:'重置',
								x:typeId==0? 285:485,
								y:5,
								width:60,
								handler:function(){
									Ext.getCmp("partWord1").setValue("");
								}
							},{
								xtype:'fieldset',
								id:'groupList',
								title:title+'列表',
								minHeight:200,
								width:545,
								x:10,
								y:35,
								items:[]
							}]
						}],
						buttons:[{
							text:'保存',
							handler:function(){
								var preArray = [];
								for(var i =0; i< selRows.length; i++){
									var record = selRows[i];
									preArray.push("ids="+record.data.id);
								} 
							//	var groupArray =[];
								var group_form =  Ext.getCmp("add_group_form");
								var checkArray = group_form.query("checkbox");
								for(var j =0; j<checkArray.length; j++){
									var checkbox = checkArray[j];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											groupListAll.push("groups="+checkbox.id);
										}
									}
								}
								Ext.Ajax.request({
									url:$service.portal+'/us.Privilege/'+selRows[0].data.id+'/grantGroup?'+groupListAll.join("&"),
									method:'post',
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'授权用户组失败：' + r.description);
										} else {
											group_window.destroy();
											Ext.getCmp('per_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'授权用户组失败：' + resp.responseText);
									}
								});
							}
						},{
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
				group_form.add(addGroupTo(600,"",'',typeId));
				group_form.doLayout();
				if(type!=""){
				groupListAll = checkToGroup(type, selRows[0].data.id);
				}
			}else if(selRows[0].data.id){
				Ext.Msg.alert('提示',"请选择要授权用户组的记录!");
			}else{
				Ext.Msg.alert('提示',"请选择一条要授权用户组的记录!");
			}
		}
		
		function checkToGroup(type, id){
			if(type!=""){
				var groupAll=[];
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
									}else{
										groupAll.push("groups="+group.id);
									}
								}
							}
						}
					}
				})
				return groupAll;
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
				fields:['id','name','type','description','entityName','roleList','groupList','functionList','userList']
			})
			var per_store = Ext.create('Ext.data.Store',{
				model: 'perModel',//数据模型
		        remoteSort: true,
		        autoLoad:false,
		        pageSize:20,
		        proxy: {
		            type: 'ajax',
		            url:$service.portal+'/us.Privilege/collection/queryV2',
		            reader: {
		                root: 'items',
		                totalProperty: 'total'
		            },
		            simpleSortMode: true
		        }
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
					{text:'<center>操作</center>',width:490,align:'center',dataIndex:'',renderer:function(v,cls,record){
						
						var str ='<div><input type="button" value="删&nbsp;除" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="deletePermission()"  class="enableCls">'+
						'<input type="button" value="修&nbsp;改" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPermission(\'edit\')"  class="enableCls">'+
						'<input type="button" value="配置功能" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="configFunction()"  class="enableCls">'+
						'<input type="button" value="授权用户" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToUser(\'edit\')"  class="enableCls">';
						
						if(userPower.group == 1){
							str+='<input type="button" value="授权用户组" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToGroup(\'edit\',0)"  class="enableCls">';
						}
						if(userPower.role==1){
							str+='<input type="button" value="授权角色" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToRole(\'edit\')"  class="enableCls">';
						}
						if(userPower.organization==1){
							str+='<input type="button" value="分配岗位" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToGroup(\'edit\',1)"  class="enableCls">';
						}
						str +='</div>';
						return str;
				/*		return '<div><input type="button" value="删&nbsp;除" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="deletePermission()"  class="enableCls">'+
						'<input type="button" value="修&nbsp;改" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPermission(\'edit\')"  class="enableCls">'+
						'<input type="button" value="配置功能" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="configFunction()"  class="enableCls">'+
						'<input type="button" value="授权用户" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToUser(\'edit\')"  class="enableCls">'+
						'<input type="button" value="授权角色" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToRole(\'edit\')"  class="enableCls">'+
						'<input type="button" value="授权用户组" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToGroup(\'edit\',0)"  class="enableCls">'+
						'<input type="button" value="分配岗位" style="margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;"  onclick="addPerToGroup(\'edit\',1)"  class="enableCls"></div>'
						*/
					}}
				],
				selModel: { //选中模型 
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
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
		        	text:'增加',
		        	iconCls:'',
		        	handler:function(){
		        		addPermission('add');
		        	}
		        },{
		        	text:'删除',
		        	iconCls:'',
		        	handler:function(){
		        		deletePermission();
		        	}
		        },'-',{
		        	text:'授权用户',
		        	iconCls:'',
		        	handler:function(){
		        		addPerToUser('edit');
		        	}
		        },{
		        	text:'授权角色',
		        	iconCls:'',
		        	hidden:userPower.role==1?false:true,
		        	handler:function(){
		        		addPerToRole('edit');
		        	}
		        },{
		        	text:'授权用户组',
		        	hidden:userPower.group==1?false:true,
		        	iconCls:'',
		        	handler:function(){
		        		addPerToGroup('edit',0);
		        	}
		        },,{
		        	text:'分配岗位',
		        	iconCls:'',
		        	hidden:userPower.organization==1?false:true,
		        	handler:function(){
		        		addPerToGroup('edit',1);
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
	        	minHeight:55,	//最小高度
	        	maxHeight:55,	//最大高度
	        	height:55,	
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