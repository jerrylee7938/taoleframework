<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%><%@include file="/include/page.jspf" %>
 <html>
<head>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=$commonRoot %>/extjs4.2/ux/css/ItemSelector.css" type="text/css"></link>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
</head>
	<style type="text/css">
		.btnCls{
			width:100%;
			height:100%;
			color: blue;
			display:block;
			cursor:pointer;
		}
		
		.buttonCls{
			width:50px;
			margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;
			-moz-border-radius: 5px;
			-webkit-border-radius: 5px;
			background-color: green;
			color:white;
		}
		.disableCls{
			width:50px;
			margin:0px 5px 0px 0px;padding:4px;cursor:pointer;width:auto;
			-moz-border-radius: 5px;
			-webkit-border-radius: 10px;
			-moz-border-radius: 5px;
			-webkit-border-radius: 5px;
			background-color: #e33100;
			
			color:white;
		}
		.btnCls hover{
			color:red;
		}
		#acc_grid-body .x-grid-row,#user_grid-body .x-grid-row{
			vertical-align: middle !important;
		}
		
	</style>

<body>
<script type="text/javascript">
	Ext.onReady(function(){
		var entityId = null;
		var entityName = 'us.User';
		var entityAction = 'create';
		var userPower = {'role':1,'group':1,"organization":1};
		
		Ext.tip.QuickTipManager.init();
		//创建分组面板 
		function createGroupPanel(userId){
			var roleArray = [];
			$.ajax({
				type:'GET',
				url:'../data/group.json',
				dataType:'json',
				async:false,
				success:function(data){
					var length = data.length;
					for(var i = 0; i < length; i++){
						var roleObj = data[i];
						var children = roleObj.children;
						children.push({
							text:'全选'
						})
						var columnLayout = [];
						var items = [];
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
										width:135,
										xtype:'checkbox',
										margin:'-3px 0px -1px 0px',
										boxLabel:child.text
									}]
								}
							}else{
								columnLayout.items.push({
									columnWidth:.25,
									width:135,
									xtype:'checkbox',
									margin:'-3px 0px -1px 0px',
									boxLabel:child.text
								})
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
						})
					}
				},
				failure:function(){
				
				}
			});
			var group_panel = Ext.create('Ext.form.Panel',{
				id:'group_panel',
				frame:true,
				border:false,
				autoScroll:true,
				style:{
					'border':'0px'
				},
				items:[roleArray]
			});
			if(userId != ''){
				//当对单条记录进行分组操作，需要将已有的分组显示出来 Ext.getCmp("item_group").setValue([3,4,5]);
			}
			return group_panel;
		}
		
		//创建权限授予面板
		function createPerPanel(userId){
			var perArray = [];
			$.ajax({
				type:'GET',
				url:'../data/permission.json',
				dataType:'json',
				async:false,
				success:function(data){
					var length = data.length;
					for(var i = 0; i < length; i++){
						var roleObj = data[i];
						var children = roleObj.children;
						children.push({
							text:'全选'
						});
						var columnLayout = [];
						var items = [];
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
										width:135,
										xtype:'checkbox',
										margin:'-3px 0px -1px 0px',
										boxLabel:child.text
									}]
								}
							}else{
								columnLayout.items.push({
									columnWidth:.25,
									width:135,
									xtype:'checkbox',
									margin:'-3px 0px -1px 0px',
									boxLabel:child.text
								})
							}
							if((j+1)%4 == 0 || j == children.length-1){
								items.push(columnLayout);
							}
						}
						perArray.push({
							xtype:'fieldset',
							collapsible:true,
							margin:'5px 10px 5px 5px',
							frame:true,
							title:roleObj.text,
							layout:'fit',
							items:[items]
						})
					}
				},
				failure:function(){
				
				}
			});
				
			var per_panel = Ext.create('Ext.form.Panel',{
				id:'per_panel',
				frame:true,
				border:false,
				autoScroll:true,
				style:{
					'border':'0px'
				},
				items:[perArray]
			});
			if(userId != ''){
				//当对单条记录进行分组操作，需要将已有的分组显示出来 Ext.getCmp("item_group").setValue([3,4,5]);
			}
			return per_panel;
		}
		//创建角色授予面板
		function createRolePanel(userId){
			var roleArray = [];
			$.ajax({
				type:'GET',
				url:$service.portal + '/us.Role/collection/list',
				dataType:'json',
				async:false,
				success:function(data){
					var length = data.length;
					
					var systemRoles = [];
					var customRoles = [];
					
					for (var i = 0; i < length; i++) {
						var role = data[i];
						if (role.type == 'Custom') {
							customRoles.push(role);
						} else if(role.type == 'System') {
							systemRoles.push(role);
						}
					}
					
					var columnLayout = [];
					var items = [];
					for(var j =0; j < systemRoles.length; j++){
						var child = systemRoles[j];
						if(j%4 == 0){
							columnLayout = {
								layout:'column',
								frame:true,
								style:{
									'border':'0px'
								},
								items:[{
									columnWidth:.25,
									width:135,
									xtype:'checkbox',
									value : child.id,
									margin:'-3px 0px -1px 0px',
									boxLabel:child.name
								}]
							};
						}else{
							columnLayout.items.push({
								columnWidth:.25,
								width:135,
								xtype:'checkbox',
								value: child.id,
								margin:'-3px 0px -1px 0px',
								boxLabel:child.name
							});
						}
						if((j+1)%4 == 0 || j == systemRoles.length-1){
							items.push(columnLayout);
						}
					}
					roleArray.push({
						xtype:'fieldset',
						collapsible:true,
						margin:'5px',
						frame:true,
						title:'系统角色',
						layout:'fit',
						items:[items]
					});
					
					var columnLayout = [];
					var items = [];
					for(var j =0; j < customRoles.length; j++){
						var child = customRoles[j];
						if(j%4 == 0){
							columnLayout = {
								layout:'column',
								frame:true,
								style:{
									'border':'0px'
								},
								items:[{
									columnWidth:.25,
									width:135,
									xtype:'checkbox',
									value : child.id,
									margin:'-3px 0px -1px 0px',
									boxLabel:child.name
								}]
							};
						}else{
							columnLayout.items.push({
								columnWidth:.25,
								width:135,
								xtype:'checkbox',
								value: child.id,
								margin:'-3px 0px -1px 0px',
								boxLabel:child.name
							});
						}
						if((j+1)%4 == 0 || j == customRoles.length-1){
							items.push(columnLayout);
						}
					}
					roleArray.push({
						xtype:'fieldset',
						collapsible:true,
						margin:'5px',
						frame:true,
						title:'用户角色',
						layout:'fit',
						items:[items]
					});
					
				},
				failure:function(){
				
				}
			});
				
			var role_panel = Ext.create('Ext.form.Panel',{
				id:'role_panel',
				frame:true,
				border:false,
				autoScroll:true,
				style:{
					'border':'0px'
				},
				items:[roleArray]
			});
			if(userId != ''){
				//当对单条记录进行分组操作，需要将已有的分组显示出来 Ext.getCmp("item_group").setValue([3,4,5]);
			}
			return role_panel;
		}
		
		//上传头像
		function showUploadWin(){
			var upload_win = Ext.getCmp("upload_win");
			if(!upload_win){
				upload_win = Ext.create("Ext.window.Window",{
					id:'upload_win',
					closeAction:'destroy',
					width:400,
					height:160,
					modal:true,
					header: false,
					closable: false,
        			draggable: false,
					layout:'fit',
					items:[{
						xtype:'form',
						id:'upload_file',
						fileUpload:true,
						border:false,
						frame:true,
						style:{'border':'0px','padding':'40px 20px 0px 0px'},
						items:[{
							xtype:'fileuploadfield',
							fieldLabel:'选择图片',
							id:'field',
							name:'field',
							anchor:'100%',
							labelWidth:80,
							buttonText:'浏览',
							labelAlign:'right',
							allowBlank:false,
							emptyText:'请选择头像图片'
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'上传',
						handler:function(){
							var filepath = Ext.getCmp("field").getValue();
							var index = filepath.lastIndexOf(".");
							var type = filepath.substring(index,filepath.length).toUpperCase();
							var form = Ext.getCmp("upload_file").getForm();
							if(type!=".BMP" && type != ".PNG" && type!= ".GIF" && type!=".JPG" && type!=".JPEG"){
								Ext.Msg.alert("提示","图片限于png,gif,jpeg,jpg格式");
								form.reset();
							}else{
								form.submit({
									url:$service.portal+'/tk.File/collection/upload',
									method:'post',
									success:function(resp, opts){
										var r = eval("(" + opts.response.responseText + ")");
										if (r.error != 0) {
											Ext.Msg.alert("提示",'上传失败：' + r.message);
										} else {
											upload_win.destroy();
											var fileid = r.fileId;
											var url = r.url;
											var avatar = Ext.getCmp("avatar");
											avatar.setValue(fileid);
											var img = Ext.getCmp("img");
											img.el.dom.src = url;
											
										}
									},
									failure:function(resp,action){
										var response = action.response.responseText;
										Ext.Msg.alert("提示",'上传失败：' + response);
										upload_win.destroy();
									}
								})
							}
						}
					},{
						text:'取消',
						handler:function(){
							upload_win.destroy();
						}
					}]
				})
			}
			upload_win.show();
		}
		//创建用户基本信息表单
		function createUserForm(){
			var user_form = Ext.create("Ext.form.Panel",{
				title:'基本信息',
				region:'north',
				id:'basic_form',
				frame:true,
				style:{
					'margin-bottom':'5px',
					'border-radius':'0px'
				},
				cls:'x-plain',
				items:[{
					layout: 'column',frame:true,cls:'x-plain',style:{'border':'0px','padding':'10px 10px 10px 20px'},
					items:[{
						columnWidth: 0.5, 
						frame:true,
						style:{'border':'0px'},
						layout:'anchor',
						items:[{
							xtype:'textfield',
							labelWidth:60,
							labelAlign:'right',
							anchor:'80%',
							name:'employeeId',
							fieldLabel:'员工编号'
						},{
							xtype:'textfield',
							labelWidth:60,
							labelAlign:'right',
							anchor:'80%',
							blankText:'请输入身份证ID!',
							name:'cardId',
							fieldLabel:'身份证ID'
						},{
							xtype:'textfield',
							labelWidth:60,
							labelAlign:'right',
							anchor:'80%',
							name:'mobile',
							allowBlank:false,
							fieldLabel:'<font style="color:red">*</font>手机号码'
						},{
							xtype:'textfield',
							labelWidth:60,
							labelAlign:'right',
							anchor:'80%',
							name:'nickName',
							fieldLabel:'用户昵称'
						},{
							xtype:'textfield',
							labelWidth:60,
							labelAlign:'right',
							anchor:'80%',
							name:'realName',
							allowBlank:false,
							blankText:'请输入真实姓名!',
							fieldLabel:'<font style="color:red">*</font>真实姓名'
						},{
							xtype:'textfield',
							labelWidth:60,
							labelAlign:'right',
							anchor:'80%',
							name:'email',
							vtype:'email',
							blankText:'请输入邮箱!',
							fieldLabel:'联系邮箱'
						},{
							xtype:'textfield',
							hidden:true,
							labelWidth:60,
							labelAlign:'right',
							anchor:'80%',
							id:'avatar'
						}]
					},{
						columnWidth: 0.5, 
						frame:true,
						style:{'border':'0px'},
						items:[{
							xtype:'image',
							height:155,
							width:160,
							src:"",
							id:'img'
						},{
							xtype:'button',
							style:{
								'margin':'-15px 0px 0px 20px'
							},
							text:'上传头像',
							handler:function(){
								showUploadWin();//上传头像窗体
							}
						}]
					}]
				}]
			})
			return user_form;
		}
		
		window.delteUser = function(){
			var selRows = Ext.getCmp("user_grid").getSelectionModel().getSelection();
			var length = selRows.length;
			if(length > 0){
				Ext.Msg.confirm('提示','确定要删除该账号信息?',function(btn){
					if(btn == 'yes'){
						var url ="";
						var userArray =[];
						for(var i = 0; i < length; i++){
							var user = selRows[i];
							userArray.push(user.data.id);
						}
						if(length == 1){
							url =$service.portal+'/us.User/'+userArray+'/delete';
						}else{
							url =$service.portal+'/us.User/collection/batchdelete?ids='+userArray.join("&ids=");
						}
						
						Ext.Ajax.request({
							url:url,
							method:'post',
							parmas:{},
							success:function(resp, opts){
								var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'删除失败：' + r.description);
								} else {
									Ext.getCmp('user_grid').getStore().load();
								}
							},
							failure:function(resp, opts){
								Ext.getCmp('user_grid').getStore().load();
							}
						})
					}
				})
			}
		}
		
		window.cancleUser = function(){
			var selRows = Ext.getCmp("user_grid").getSelectionModel().getSelection();
			var length = selRows.length;
			if(length > 0){
				Ext.Msg.confirm('提示','确定要注销该账号信息?',function(btn){
					if(btn == 'yes'){
						var userArray =[];
						for(var i = 0; i < length; i++){
							var user = selRows[i];
							userArray.push(user.data.id);
						}
						
						Ext.Ajax.request({
							url:url =$service.portal+'/us.User/collection/cancle?userIds='+userArray.join(","),
							method:'post',
							parmas:{},
							success:function(resp, opts){
								var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'注销失败：' + r.description);
								} else {
									Ext.getCmp('user_grid').getStore().load();
								}
							},
							failure:function(resp, opts){
								Ext.getCmp('user_grid').getStore().load();
							}
						})
					}
				})
			}else{
				Ext.Msg.alert("提示",'请选择要注销的用户信息！');
			}
		}
		
		//删除账号
		window.deleteUserInfo = function(rowIndex){
			Ext.Msg.confirm('提示','确定要删除该账号信息?',function(btn){
				if(btn == 'yes'){
					var store =  Ext.getCmp("acc_grid").getStore();
					var record =store.getAt(rowIndex);
					var userCode = record.data.id;
					//userCode 为空，代表是新增的账号信息;对于该情况下不需要后台操作，直接前台删除
					if(userCode != ''){
						
						Ext.Ajax.request({
							url:$service.portal+'/us.User/'+userCode+'/delete',
							method:'post',
							parmas:{},
							succes:function(resp, opts){
								var r = eval("(" + resp.responseText + ")");
								if (r.code != 1) {
									Ext.Msg.alert("提示",'删除失败：' + r.description);
								} else {
									Ext.getCmp('user_grid').getStore().load();
								}
							},
							failure:function(resp, opts){
								
							}
						})
					}else{
						store.remove(record);
					}
				}
			})
		}
		
		window.deleteAccountInfo = function(index){
			var grid = Ext.getCmp("acc_grid");
			var store = grid.getStore();
			var i= 0;
			store.each(function(rec){
				if(i == index){
					store.remove(rec);
				}
				i++;
			})
		}
		//账号网格
		function createAccountGrid(userId){
			Ext.define('AccountModel',{
				extend:'Ext.data.Model',
				fields:['id','enabled','loginId','effectDate','expireDate','status']
			});
			
			var acc_store = Ext.create('Ext.data.Store',{
				model: 'AccountModel',//数据模型
		        remoteSort: true/*,
		        autoLoad:true,
		        proxy: {
		            type: 'ajax',
		            url:'getFundUsingList.do?flag=1&enterpriseId=',
		            reader: {
		                root: 'items',
		                totalProperty: 'total'
		            },
		            simpleSortMode: true
		        }*/
			});
			var cellEditing = new Ext.grid.plugin.CellEditing({
	            clicksToEdit: 1
	        });
			var acc_grid = Ext.create('Ext.grid.Panel',{
				store: acc_store,
				id:'acc_grid',
				title:'账号信息',
				border:false,
				columnLines:true,
				tbar:[{
					text:'新增',
					handler:function(){
						var store = Ext.getCmp("acc_grid").getStore();
						var count = store.getCount();
						var record = Ext.create('AccountModel',{enabled:false,effectDate:new Date()});
						store.insert(count,record);
					}
				}],
				selModel:{
					selType : 'cellmodel'//复选框选择模式Ext.selection.CheckboxModel  
				},
			 	plugins: [cellEditing],
				columns:[
					{xtype: 'rownumberer',text: '序号',width:40,align:'center'},
					{text:'账号',width:80,dataIndex:'loginId',editor: {
	                    allowBlank: false
	                }},
					{text:'是否启用',width:60,dataIndex:'enabled',align:'center',editor:{xtype:'checkbox'},renderer:function(v){
						if(v){
							return "是";
						}else{
							return "否";
						}
					}},
					{text:'生效日期',width:100,dataIndex:'effectDate',editor:{xtype:'datefield',format:'Y-m-d'},renderer:function(v){
						if(typeof v ==="object"){
							return Ext.Date.format(v,'Y-m-d');
						}else if(typeof v ==="string"){
							return v.substr(0,10);
						}
					}},
					{text:'过期日期',width:100,dataIndex:'expireDate',editor:{xtype:'datefield',format:'Y-m-d'},renderer:function(v){
						if(typeof v ==="object"){
							return Ext.Date.format(v,'Y-m-d');
						}else if(typeof v ==="string"){
							return v.substr(0,10);
						}
					}},
					{text:'操作',align:'center',width:80,renderer:function(v,cls,rec,rowIndex,columnIndex,store){
						return '<a onclick="deleteAccountInfo('+rowIndex+');" class="btnCls">删&nbsp;&nbsp;&nbsp;除</a>';
					}}
				]
	        })
	        return acc_grid;
		}
		
		
		//新增用户信息
		window.addOrUpdateUserInfo = function(userId){
			var win = Ext.getCmp("user_win");
			var title = userId !=''?'修改':'新增';
			var grid = Ext.getCmp("user_grid");
			var width = grid.getWidth();
			var height = grid.ownerCt.ownerCt.getHeight()-280;
			if(!win){
				win = Ext.create("Ext.window.Window",{
					title:title+'用户信息',
					id:'user_win',
					height:300,
					width:600,
					itemId:userId,
					modal:true,
					resizable:false,
					layout:'fit',
					border:false,
					items:[{
						xtype:'tabpanel',
						layout:'fit',
						items:[createUserForm(),
							createAccountGrid(userId),{
								title:'角色信息',
								xtype:'form',
								id:'add_role_form',
								autoScroll:true,
								frame:true,
								hidden:userPower.role==1?false:true,
								style:{
									'border':'0px'
								},
								border:false,
								items:[]
							},{
								title:'分组信息',
								id:'add_group_form',
								autoScroll:true,
								xtype:'form',
								hidden:userPower.group==1?false:true,
								frame:true,
								style:{
									'border':'0px'
								},
								border:false,
								items:[{
									xtype:'fieldset',
									id:'groupList',
									title:'用户组',
									margin:'5px',
									minHeight:200,
									items:[]
								}]
							},{
								title:'岗位信息',
								id:'add_group1_form',
								autoScroll:true,
								xtype:'form',
								hidden:userPower.organization==1?false:true,
								frame:true,
								style:{
									'border':'0px'
								},
								border:false,
								items:[{
									xtype:'fieldset',
									id:'groupList1',
									title:'用户组',
									margin:'5px',
									minHeight:200,
									items:[]
								}]
							},{
								title:'权限信息',
								xtype:'form',
								id:'add_per_form',
								frame:true,
								autoScroll:true,
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
							var form = Ext.getCmp("basic_form");
							var tmp_win = Ext.getCmp("user_win");
							if(form.getForm().isValid()){
								var data = Ext.ux.FormUtils.getDataObject(form);
								var url ="";
								var userId = tmp_win.itemId;
								if (userId != '') {
									data.id = userId;
									url = $service.portal+'/us.User/'+userId+'/update';
								}else{
									data.id ="";
									url = $service.portal+'/us.User/collection/create?environmentId='+sessionStorage.getItem('defaultSystemType');
								}
								var avatar = Ext.getCmp("avatar").getValue();
								data.avatar = avatar;
								data.entityName ="us.User";
								data.accounts =[];
								var store = Ext.getCmp("acc_grid").getStore();
								store.each(function(rec){
									var account = rec.data;
									account.javaClass = 'com.taole.usersystem.domain.Account';
									data.accounts.push(account);
								});
								data.roles =[];
								var role_form = Ext.getCmp("add_role_form");
								var roleArray = role_form.query("checkbox");
								
								for(var i = 0; i < roleArray.length; i++){
									var checkbox = roleArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											data.roles.push(checkbox.id);
										}
									}
								}
								
								data.groups =[];
								var group_form = Ext.getCmp("add_group_form");
								var groupArray = group_form.query("checkbox");
								for(var i =0; i < groupArray.length; i++){
									var checkbox = groupArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											data.groups.push(checkbox.id);
										}
									}
								}
								
								var group1_form = Ext.getCmp("add_group1_form");
								var group1Array = group1_form.query("checkbox");
								for(var i =0; i < group1Array.length; i++){
									var checkbox = group1Array[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											data.groups.push(checkbox.id);
										}
									}
								}
								
								
								data.privilegeList =[];
								var pre_form = Ext.getCmp("add_per_form");
								var preArray = pre_form.query("checkbox");
								for(var i =0; i < preArray.length; i++){
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
											win.destroy();
											Ext.getCmp('user_grid').getStore().load();
											Ext.getCmp('user_grid').getSelectionModel().deselectAll();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'保存失败：' +resp.responseText );
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
			var role_form = Ext.getCmp("add_role_form");
			role_form.add(addRoleTo(600));
			role_form.doLayout();
			Ext.getCmp("f545a97cd90c46c4b85d0daaf1fd04ac").hide(true);
			Ext.getCmp("aa192ae10bca42b592aca883e9c82bc9").hide(true);
			var group_fieldset = Ext.getCmp("groupList");
			group_fieldset.add(addGroupTo(600,"",'',0));
			group_fieldset.doLayout();
			
			var group1_fieldset = Ext.getCmp("groupList1");
			group1_fieldset.add(addGroupTo(600,"",'',1));
			group1_fieldset.doLayout();
			
			var per_form = Ext.getCmp("add_per_form");
			per_form.add(addPerTo(600));
			per_form.doLayout();
			
			var form = Ext.getCmp('basic_form');
			if(userId !=''){
				var grid = Ext.getCmp("user_grid");
				var selRow = grid.getSelectionModel().getSelection()[0];
				form.getForm().loadRecord(selRow);
				var avatar = selRow.data.avatar;
				if(avatar !=""){
					Ext.getCmp('img').el.dom.src="/portal/service/rest/tk.File/"+avatar+"/";
				}
				
				$.ajax({
					type:'GET',
					url:$service.portal+'/us.User/'+userId+'/retrieve',
					dataType:'json',
					async:false,
					success:function(data){
						if(data){
							
							var accounts = data.accounts;
							var store = Ext.getCmp("acc_grid").getStore();
							if(accounts && accounts.length >0){
								
								store.insert(0,accounts);
							}
							//var groupList = data.groupList;
							var groupList = data.groups;
							if(groupList && groupList.length >0){
								for(var i = 0; i < groupList.length; i++){
									var group = groupList[i];
									Ext.getCmp(group.id).setValue(true);
								}
							}
							//var roleList = data.roleList;
							var roleList = data.roles;
							if(roleList && roleList.length >0){
								for(var i = 0; i < roleList.length; i++){
									var role = roleList[i];
									Ext.getCmp(role.id).setValue(true);
								}
							}
							var privilegeList = data.privilegeList;
							if(privilegeList && privilegeList.length >0){
								for(var i = 0; i < privilegeList.length; i++){
									var pre = privilegeList[i];
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
		//禁用或启用用户
		window.enableOrDisableUser = function(flag){
			var grid = Ext.getCmp("user_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var length = selRows.length;
			var text = flag =='1'?'启用':'禁用';
			if(length >0){
				//循环获取用户编码
				var userIdArray = [];
				for(var i=0; i < length; i++){
					var record = selRows[i];
					userIdArray.push(record.data.id);
				}
				var url = "";
				
				if(length ==1){
					var operate = flag =='1'?'enable':'disable';
					url = $service.portal + '/' + entityName + '/'+userIdArray.toString()+'/'+operate;
				}else{
					var operate = flag =='1'?'batchenable':'batchdisable';
					url =$service.portal + '/' + entityName + '/collection/'+operate+'?ids='+userIdArray.join("&ids=");
				}
				//执行相关操作
				Ext.Ajax.request({
					method:'post',
					url: url,
					success:function(resp,opts){
						var r = eval("(" + resp.responseText + ")");
						if (r.code != 1) {
							Ext.Msg.alert("提示",text+'失败：' + r.description);
						} else {
							Ext.getCmp('user_grid').getStore().load();
						}
					},
					failure: function(resp,opts) { 
						Ext.Msg.alert("提示",text+'失败：' + resp.responseText);
					}
				})
			}else{
				Ext.Msg.alert("提示","请选择"+text+"的用户记录!");
			}
		}
		//重置密码
		window.resetPassword = function(){
			var grid = Ext.getCmp("user_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var length = selRows.length;
			if(length >0){
				var url ="";
				var userArray = [];
				for(var i=0; i < length; i++){
					var record = selRows[i];
					userArray.push(record.data.id);
				}
				if(length >1){
					url = $service.portal + '/us.User/collection/batchSendResetPwdMail?ids='+userArray.join("&ids");
				}else{
					url =$service.portal + '/us.User/'+userArray+'/sendResetPwdMail';
				}
				Ext.Ajax.request({
					method:'post',
					url:url,
					success:function(resp, opts){
						var r = eval("(" + resp.responseText + ")");
						if (r.code != 1) {
							Ext.Msg.alert("提示",'重置密码失败：' + r.description);
						} else {
							Ext.getCmp('user_grid').getStore().load();
						}
					},
					failure:function(resp, opts){
						Ext.Msg.alert("提示",'重置密码失败：' + resp.responseText);
					}
				})
			}else{
				Ext.Msg.alert("提示","请选择重置密码的用户记录!");
			}
		}
		
		//分组（多个用户）
		window.userGroupForUsers = function(type){
			var grid = Ext.getCmp("user_grid");
			var width = grid.getWidth();
			var title = type == 0? '分组':'岗位';
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length > 0){
				var group_users_win = Ext.getCmp("group_users_win");
				if(!group_users_win){
					group_users_win = Ext.create("Ext.window.Window",{
        				title:title+'信息',
						id:'group_users_win',
						height:300,
						width:600,
						modal:true,
						layout:'fit',
						items:[{
							xtype:'form',
							id:'add_group_users_form',
							frame:true,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							layout:'absolute',
							border:false,
							items:[{
								x:5,
								y:5,
								width:200,
								hidden:type ==0? true:false,
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
								labelWidth:55,
								labelAlign:'right',
								id:'keyWord',
								x:type ==0? 5:205,
								y:5,
								width:200
							},{
								xtype:'button',
								text:'查询',
								x:type==0? 210:410,
								y:5,
								width:60,
								handler:function(){
									var keyword = Ext.getCmp('keyWord').getValue();
									var fieldset = Ext.getCmp('userGroupList');
									fieldset.removeAll();
									fieldset.add(addGroupTo(600,keyword,'',type));
									fieldset.doLayout();
								}
							},{
								xtype:'button',
								text:'重置',
								x:type==0? 280:480,
								y:5,
								width:60,
								handler:function(){
									Ext.getCmp("keyWord").setValue("");
								}
							},{
								xtype:'fieldset',
								id:'userGroupList',
								margin:'5px',
								minHeight:100,
								width:570,
								x:0,
								y:35,
								items:[]
							}]
						}],
						buttonAlign:'center',
						buttons:[{
							text:'添加'+title,
							handler:function(){
								var form = Ext.getCmp("add_group_users_form");
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
								var userList =[];
								for(var j = 0; j<selRows.length; j++){
									var user = selRows[j];
									userList.push("ids="+user.data.id);
								}

								if(groupList.length == 0){
									Ext.Msg.alert("提示","请选择要操作的分组数据");
									return false;
								}
								
								//执行用户分组操作
								Ext.Ajax.request({
									method:'post',
									url:$service.portal+'/us.User/collection/batchAddGroup?'+groupList.join('&') + "&" + userList.join("&"),
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'用户分组失败：' + r.description);
										} else {
											group_users_win.destroy();
											Ext.getCmp('user_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'用户分组失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'移除'+title,
							handler:function(){
								var form = Ext.getCmp("add_group_users_form");
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
								var userList =[];
								for(var j = 0; j<selRows.length; j++){
									var user = selRows[j];
									userList.push("ids="+user.data.id);
								}

								if(groupList.length == 0){
									Ext.Msg.alert("提示","请选择要操作的分组数据");
									return false;
								}
								
								//执行用户分组操作
								Ext.Ajax.request({
									method:'post',
									url:$service.portal+'/us.User/collection/batchRemoveGroup?'+groupList.join('&') + "&" + userList.join("&"),
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'移除分组失败：' + r.description);
										} else {
											group_users_win.destroy();
											Ext.getCmp('user_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'移除分组失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								group_users_win.destroy();
							}
						}]
					})
				}
				group_users_win.show();
				var group_form = Ext.getCmp("userGroupList");
				group_form.add(addGroupTo(600,"",'',type));
				group_form.doLayout();
			}else{
				Ext.Msg.alert("提示","请选择要分组的用户记录!");
			}
		}
		
		//分组
		window.userGroup = function(userId,type){
			var grid = Ext.getCmp("user_grid");
			var width = grid.getWidth();
			var title = type == 0? '分组':'岗位';
			var selRows = grid.getSelectionModel().getSelection();
			var groupListAll = [];
			if(selRows.length == 1){
				var group_win = Ext.getCmp("group_win");
				if(!group_win){
					group_win = Ext.create("Ext.window.Window",{
        				title:title+'信息',
						id:'group_win',
						minHeight:200,
						itemId:userId,
						height:300,
						width:600,
						modal:true,
						layout:'fit',
						items:[{
							xtype:'form',
							id:'add_group_form',
							frame:true,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							layout:'absolute',
							border:false,
							items:[{
								x:5,
								y:5,
								hidden:type ==0? true:false,
								width:200,
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
								labelWidth:55,
								labelAlign:'right',
								id:'partWord',
								x:type ==0? 5:205,
								y:5,
								width:200
							},{
								xtype:'button',
								text:'查询',
								x:type==0? 210:410,
								y:5,
								width:60,
								handler:function(){
									var keyword = Ext.getCmp('partWord').getValue();
									var fieldset = Ext.getCmp('userList');
									fieldset.removeAll();
									fieldset.add(addGroupTo(600,keyword,'',type));
									fieldset.doLayout();
								}
							},{
								xtype:'button',
								text:'重置',
								x:type==0? 280:480,
								y:5,
								width:60,
								handler:function(){
									Ext.getCmp("partWord").setValue("");
								}
							},{
								xtype:'fieldset',
								id:'userList',
								margin:'5px',
								minHeight:100,
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
							//	var groupList = [];
							
								for(var i = 0; i < groupArray.length; i++){
									var checkbox = groupArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											groupListAll.push("groups="+checkbox.id);
										}
									}
								}
								var selRows = Ext.getCmp("user_grid").getSelectionModel().getSelection();
								//var userList =[];
								//for(var j = 0; j<selRows.length; j++){
								//	var user = selRows[j];
								//	userList.push("ids="+user.data.id);
								//}
								var url ="";
								if(groupListAll.length > 0){
									url = $service.portal+'/us.User/'+selRows[0].data.id+'/grantGroup?'+groupListAll.join('&');
								}else{
									url = $service.portal+'/us.User/'+selRows[0].data.id+'/grantGroup';
								}
								
								//执行用户分组操作
								Ext.Ajax.request({
									method:'post',
									url:url,
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'用户分组失败：' + r.description);
										} else {
											group_win.destroy();
											Ext.getCmp('user_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'用户分组失败：' + resp.responseText);
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
				var group_form = Ext.getCmp("userList");
				group_form.add(addGroupTo(600,"",'',type));
				group_form.doLayout();
				userId = selRows[0].data.id;
				$.ajax({
					type:'GET',
					url:$service.portal+'/us.User/'+userId+'/retrieve',
					dataType:'json',
					async:false,
					success:function(data){
						var groupList = data.groups;
						if(groupList && groupList.length >0){
							for(var i = 0; i < groupList.length; i++){
								var group = groupList[i];
								if(Ext.getCmp(group.id)){
									Ext.getCmp(group.id).setValue(true);
								}else{
									groupListAll.push("groups="+group.id);
								}
							}
						}
					}
				})
			}else if(selRows.length == 0){
				Ext.Msg.alert("提示","请选择要分组的用户记录!");
			}else{
				Ext.Msg.alert("提示","请选择一条要分组的用户记录!");
			}
		}
		
		//分配环境
		window.userEnvironment = function(type){
			var grid = Ext.getCmp("user_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length > 1){
				Ext.Msg.alert("提示","请选择一条用户记录进行操作!");
			}else if (selRows.length == 1) {
				var users_environment = Ext.getCmp("users_environment");
				if(!users_environment){
					users_environment = Ext.create("Ext.window.Window",{
        				title:'环境信息',
						id:'users_environment',
						minHeight:200,
					//	itemId:userId,
						height:300,
						width:600,
						modal:true,
						layout:'fit',
						items:[{
							xtype:'form',
							id:'add_environment_form',
							frame:true,
							autoScroll:true,
							style:{
								'border':'0px'
							},
							layout:'absolute',
							border:false,
							items:[{
								xtype:'fieldset',
								id:'environmentList',
								margin:'5px',
								minHeight:80,
								width:570,
								x:0,
								y:5,
								title:'分配环境',
								items:[]
							},{
								xtype:'fieldset',
								id:'environmentRadio',
								margin:'5px',
								minHeight:80,
								width:570,
								x:0,
								y:100,
								title:'设置默认环境',
								items:[]
							}]
						}],
						buttonAlign:'center',
						buttons:[{
							text:'确定',
							handler:function(){
								var environmentListAll = [];
					/*			var form = Ext.getCmp("environmentList");
								var groupArray = form.query("checkbox");
								for(var i = 0; i < groupArray.length; i++){
									var checkbox = groupArray[i];
									var value = checkbox.getValue();
									if(value){
										groupListAll.push("groups="+checkbox.id);
									}	
								}  */
								
								var radio = Ext.getCmp("add_environment_form");
								var radioArray = radio.query("radio");
								var isDefaultA = false;
								for(var j = 0; j < radioArray.length; j++){
									var radiobox = radioArray[j];
									var value = radiobox.getValue();
									var inputValue = radiobox.inputValue;
									var name = radiobox.boxLabel
									if(!radiobox.hidden){
										var environment ={'environmentId':inputValue,'isDefault':'2','environmentName':name};
										if(value){
											environment.isDefault='1';
											isDefaultA =true;
										}
										environmentListAll.push(environment);
									}
								}
								if(!isDefaultA){
									Ext.Msg.alert("提示",'默认环境不能为空！');
									return;
								}
								var userId = selRows[0].data.id;
								var url =$service.portal+'/us.UserEnv/collection/update';
								var dataAll ={
										"userId":userId,
										"environments":environmentListAll
									}
								//执行用户分组操作
								Ext.Ajax.request({
									method:'post',
									url:url,
									jsonData:dataAll,
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.result_code == 100) {
											users_environment.destroy();
											Ext.getCmp('user_grid').getStore().load();
										} else {
											Ext.Msg.alert("提示",'用户分组失败：' + r.result_desc);
		
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'用户分组失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								users_environment.destroy();
							}
						}]
					})
				}
				users_environment.show();
				var group_form = Ext.getCmp("environmentList");
				group_form.add(addEnvironmentTo(600,'',1));
				group_form.doLayout();
			    var radio_form = Ext.getCmp("environmentRadio");
			    radio_form.add(addEnvironmentRadio(600,'',1));
				radio_form.doLayout();
				userId = selRows[0].data.id;
			
				$.ajax({
					type:'GET',
					url:$service.portal+'/us.UserEnv/collection/query?userId='+userId,
					dataType:'json',
					async:false,
					success:function(data){
						console.log(data);
						var environmentList = data.result_data.items;
						if(environmentList && environmentList.length >0){
							for(var i = 0; i < environmentList.length; i++){
								var group = environmentList [i];
								var environmentId = group.environmentId;
									Ext.getCmp(environmentId).setValue(true);
									Ext.getCmp('radio'+environmentId).show();
									if(group.isDefault == 1){
										Ext.getCmp('radio'+environmentId).setValue(true);
									}
								
							}
						}
					}
				})
			
			}else {
				Ext.Msg.alert("提示","请选择要分配环境的用户记录!");
			}
			
		}
		
		//设置数据权限
		window.setDataPrivilege = function(){
			var grid = Ext.getCmp("user_grid");
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 0){
				Ext.Msg.alert("提示","请选择要设置数据权限的用户记录!");
				return;
			}
			
			if(selRows.length > 1){
				Ext.Msg.alert("提示","请选择一条用户记录进行操作!");
				return;
			}
			
			var userId = selRows[0].data.id;
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
			    		bizType: 'USER_DATAPRIVILEGE',
			    		bizId: userId
			    	}).show();
			    }
			});
		}
		
		//赋予角色(多个用户)
		window.grantRoleForUsers = function(){
			var grid = Ext.getCmp("user_grid");
			var width = grid.getWidth();
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length > 0){
				var role_win_users = Ext.getCmp("role_win_users");
				if(!role_win_users){
					role_win_users = Ext.create("Ext.window.Window",{
        				title:'角色信息',
						id:'role_win_users',
						height:300,
						width:600,
						modal:true,
						layout:'fit',
						resizable:false,
						items:[{
							xtype:'form',
							id:'add_role_users_form',
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
							text:'赋予角色',
							handler:function(){
								var form = Ext.getCmp("add_role_users_form");
								var roleArray = form.query("checkbox");
								var roleList = [];
								for(var i = 0; i < roleArray.length; i++){
									var checkbox = roleArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											roleList.push("roles="+checkbox.id);
										}
									}
								}
								
								var userList = [];
								for(var j=0; j<selRows.length; j++){
									userList.push("ids="+selRows[j].data.id);
								}
								
								if(roleList.length == 0){
									Ext.Msg.alert("提示","请选择要赋予的角色");
									return false;
								}
								var url = $service.portal+'/us.User/collection/batchAddRole?'+roleList.join("&") + "&"+userList.join("&");
								//执行用户分组操作
								Ext.Ajax.request({
									method:'post',
									url:url,
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'赋予角色失败：' + r.description);
										} else {
											role_win_users.destroy();
											Ext.getCmp('user_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'赋予角色失败：' + resp.responseText);
									}
								})
								
							}
						},{
							text:'移除角色',
							handler:function(){
								var form = Ext.getCmp("add_role_users_form");
								var roleArray = form.query("checkbox");
								var roleList = [];
								for(var i = 0; i < roleArray.length; i++){
									var checkbox = roleArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											roleList.push("roles="+checkbox.id);
										}
									}
								}
								
								var userList = [];
								for(var j=0; j<selRows.length; j++){
									userList.push("ids="+selRows[j].data.id);
								}
								
								if(roleList.length == 0){
									Ext.Msg.alert("提示","请选择要移除的角色");
									return false;
								}
								var url = $service.portal+'/us.User/collection/batchRemoveRole?'+roleList.join("&") + "&"+userList.join("&");
								//执行用户分组操作
								Ext.Ajax.request({
									method:'post',
									url:url,
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'移除角色失败：' + r.description);
										} else {
											role_win_users.destroy();
											Ext.getCmp('user_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'移除角色失败：' + resp.responseText);
									}
								})
								
							}
						},{
							text:'取消',
							handler:function(){
								role_win_users.destroy();
							}
						}]
					})
				}
				role_win_users.show();
				var role_form = Ext.getCmp("add_role_users_form");
				role_form.add(addRoleTo(600));
				role_form.doLayout();
				Ext.getCmp("f545a97cd90c46c4b85d0daaf1fd04ac").hide(true);
				Ext.getCmp("aa192ae10bca42b592aca883e9c82bc9").hide(true);
			}else{
				Ext.Msg.alert("提示","请选择要用户记录！")
			}
		}
		
		//赋予角色
		window.grantRole = function(userId){
			var grid = Ext.getCmp("user_grid");
			var width = grid.getWidth();
			var selRows = grid.getSelectionModel().getSelection();
			if(selRows.length == 1){
				var role_win = Ext.getCmp("role_win");
				if(!role_win){
					role_win = Ext.create("Ext.window.Window",{
        				title:'角色信息',
						id:'role_win',
						minHeight:200,
						maxHeight:400,
						height:300,
						itemId:userId,
						width:600,
						modal:true,
						layout:'fit',
						resizable:false,
						items:[{
							xtype:'form',
							id:'add_role_form',
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
							text:'确定',
							handler:function(){
								var form = Ext.getCmp("add_role_form");
								var roleArray = form.query("checkbox");
								var roleList = [];
								for(var i = 0; i < roleArray.length; i++){
									var checkbox = roleArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											roleList.push("roles="+checkbox.id);
										}
									}
								}
								var selRows = Ext.getCmp("user_grid").getSelectionModel().getSelection();
								//var userList =[];
								//for(var j = 0; j<selRows.length; j++){
								//	var user = selRows[j];
								//	userList.push("ids="+user.data.id);
								//}
								var url ="";
								if(roleList.length > 0){
									url = $service.portal+'/us.User/'+selRows[0].data.id+'/grantRole?'+roleList.join("&");
								}else{
									url = $service.portal+'/us.User/'+selRows[0].data.id+'/grantRole';
								}
								
								//执行用户分组操作
								Ext.Ajax.request({
									method:'post',
									url:url,
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'赋予角色失败：' + r.description);
										} else {
											role_win.destroy();
											Ext.getCmp('user_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'赋予角色失败：' + resp.responseText);
									}
								})
								
							}
						},{
							text:'取消',
							handler:function(){
								role_win.destroy();
							}
						}]
					})
				}
				role_win.show();
				
				var role_form = Ext.getCmp("add_role_form");
				role_form.add(addRoleTo(600));
				role_form.doLayout();
				Ext.getCmp("f545a97cd90c46c4b85d0daaf1fd04ac").hide(true);
				Ext.getCmp("aa192ae10bca42b592aca883e9c82bc9").hide(true);
				userId = selRows[0].data.id
				$.ajax({
					type:'GET',
					url:$service.portal+'/us.User/'+userId+'/retrieve',
					dataType:'json',
					async:false,
					success:function(data){
						var roleList = data.roles;
						if(roleList && roleList.length >0){
							for(var i = 0; i < roleList.length; i++){
								var role = roleList[i];
								Ext.getCmp(role.id).setValue(true);
							}
						}
					}
				});
			}else if(selRows.length == 0){
				Ext.Msg.alert("提示","请选择要赋予角色的用户记录!");
			}else {
				Ext.Msg.alert("提示","请选择一条要赋予角色的用户记录!");
			}
		}
		
		//授予权限(多个用户)
		window.grantPermissionForUsers = function(){
			var grid = Ext.getCmp("user_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var height = grid.ownerCt.ownerCt.getHeight()-40;
			var width = grid.getWidth();
			if(selRows.length > 0){
				var per_win_user = Ext.getCmp("per_win_user");
				if(!per_win_user){
					per_win_user = Ext.create("Ext.window.Window",{
        				title:'权限信息',
						id:'per_win_user',
						height:300,
						width:600,
						modal:true,
						layout:'fit',
						resizable:false,
						items:[{
							id:'add_pre_user_form',
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
							text:'赋予权限',
							handler:function(){
								var form = Ext.getCmp("add_pre_user_form");
								var preArray = form.query("checkbox");
								var preList = [];
								for(var i = 0; i < preArray.length; i++){
									var checkbox = preArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											preList.push("privs="+checkbox.id);
										}
									}
								}
								var userList =[];
								for(var j = 0; j<selRows.length; j++){
									var user = selRows[j];
									userList.push("ids="+user.data.id);
								}
								
								if(preList.length == 0){
									Ext.Msg.alert("提示","请选择要操作的权限数据");
									return false;
								}
								//执行用户赋予权限操作
								Ext.Ajax.request({
									method:'post',
									url: $service.portal+'/us.User/collection/batchAddPrivilege?'+preList.join("&")+"&"+userList.join("&"),
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'赋予权限失败：' + r.description);
										} else {
											per_win_user.destroy();
											Ext.getCmp('user_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'赋予权限失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'移除权限',
							handler:function(){
								var form = Ext.getCmp("add_pre_user_form");
								var preArray = form.query("checkbox");
								var preList = [];
								for(var i = 0; i < preArray.length; i++){
									var checkbox = preArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											preList.push("privs="+checkbox.id);
										}
									}
								}
								var userList =[];
								for(var j = 0; j<selRows.length; j++){
									var user = selRows[j];
									userList.push("ids="+user.data.id);
								}
								
								if(preList.length == 0){
									Ext.Msg.alert("提示","请选择要操作的权限数据");
									return false;
								}
								//执行用户赋予权限操作
								Ext.Ajax.request({
									method:'post',
									url: $service.portal+'/us.User/collection/batchRemovePrivilege?'+preList.join("&")+"&"+userList.join("&"),
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'移除权限失败：' + r.description);
										} else {
											per_win_user.destroy();
											Ext.getCmp('user_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'移除权限失败：' + resp.responseText);
									}
								})
							}
						},{
							text:'取消',
							handler:function(){
								per_win_user.destroy();
							}
						}]
					})
				}
				per_win_user.show();
				
				var pre_form = Ext.getCmp("add_pre_user_form");
				pre_form.add(addPerTo(600));
				pre_form.doLayout();
			}else{
				Ext.Msg.alert("提示","请选择用户记录!");
			}
		}
		
		//授予权限
		window.grantPermission = function(userId){
			var grid = Ext.getCmp("user_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var height = grid.ownerCt.ownerCt.getHeight()-40;
			var width = grid.getWidth();
			if(selRows.length == 1){
				var per_win = Ext.getCmp("per_win");
				if(!per_win){
					per_win = Ext.create("Ext.window.Window",{
        				title:'权限信息',
						id:'per_win',
						height:300,
						width:600,
						itemId:userId,
						modal:true,
						layout:'fit',
						resizable:false,
						items:[{
							id:'add_pre_form',
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
							text:'确定',
							handler:function(){
								var form = Ext.getCmp("add_pre_form");
								var preArray = form.query("checkbox");
								var preList = [];
								for(var i = 0; i < preArray.length; i++){
									var checkbox = preArray[i];
									var value = checkbox.getValue();
									if(checkbox.boxLabel !='全选'){
										if(value){
											preList.push("privs="+checkbox.id);
										}
									}
								}
								var selRows = Ext.getCmp("user_grid").getSelectionModel().getSelection();
								//var userList =[];
								//for(var j = 0; j<selRows.length; j++){
								//	var user = selRows[j];
								//	userList.push("users="+user.data.id);
								//}
								var url ="";
								if(preList.length > 0){
									url = $service.portal+'/us.User/'+selRows[0].data.id+'/grantPrivilege?'+preList.join("&");
								}else{
									url = $service.portal+'/us.User/'+selRows[0].data.id+'/grantPrivilege';
								}
								//执行用户赋予权限操作
								Ext.Ajax.request({
									method:'post',
									url:url,
									params:{},//"传递用户编号 以及 分组编码"
									success:function(resp, opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'赋予权限失败：' + r.description);
										} else {
											per_win.destroy();
											Ext.getCmp('user_grid').getStore().load();
										}
									},
									failure:function(resp, opts){
										Ext.Msg.alert("提示",'赋予权限失败：' + resp.responseText);
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
				
				var pre_form = Ext.getCmp("add_pre_form");
				pre_form.add(addPerTo(600));
				pre_form.doLayout();
				userId =selRows[0].data.id;
				$.ajax({
					type:'GET',
					url:$service.portal+'/us.User/'+userId+'/retrieve',
					dataType:'json',
					async:false,
					success:function(data){
						var privilegeList = data.privilegeList;
						if(privilegeList && privilegeList.length >0){
							for(var i = 0; i < privilegeList.length; i++){
								var role = privilegeList[i];
								if(Ext.getCmp(role.id)){
									Ext.getCmp(role.id).setValue(true);
								}
							}
						}
					}
				});
			}else if(selRows.length == 0){
				Ext.Msg.alert("提示","请选择授予权限的用户记录!");
			}else{
				Ext.Msg.alert("提示","请一条授予权限的用户记录!");
			}
		}
		
		
		function deleteUserInfo(){
			var grid = Ext.getCmp("user_grid");
			var selRows = grid.getSelectionModel().getSelection();
			var length = selRows.length;
			if(length >0){
				var idArray =[];
				for(var i = 0; i < length; i++){
					var row = selRows[i];
					idArray.push(row.data.id);
				}
				var url ="";
				if(length ==1){
					url = $service.portal + '/' + entityName + '/'+idArray.toString()+'/delete';
				}else{
					url = $service.portal + '/' + entityName + '/collection/batchdelete?Ids='+idArray.toString();
				}
				
				Ext.Ajax.request({
					method:'post',
					url:url,
					success:function(resp, opts){
						var r = eval("(" + resp.responseText + ")");
						if (r.code != 1) {
							Ext.Msg.alert("提示",'删除失败：' + r.description);
						} else {
							Ext.getCmp('user_grid').getStore().load();
						}
					},
					failure:function(resp, opts){
						Ext.Msg.alert("提示",'删除失败：' + resp.responseText);
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
		
		//创建用户网格
		function createUserGrid(){
			
			getUserPermission()
			
			var tabPanel = window.parent.Ext.getCmp("center_panel");
			Ext.define('userModel',{
				extend:'Ext.data.Model',
				fields:['cardId','employeeId','enabled','accounts','regDate','avatar','password','id','email','nickName','description','roles','expireDate',
					'personId','realName','attributes','groups','mobile','uri']
			})
			var user_store = Ext.create('Ext.data.Store',{
				model: 'userModel',//数据模型
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
				columnLines:true,
				columns:[
					//{text:'用户ID',width:150,dataIndex:'id'},
					{text:'员工号',width:80,dataIndex:'employeeId'},
					{text:'姓名',width:100,dataIndex:'realName'},
					{text:'身份证',width:150,dataIndex:'cardId'},
					{text:'邮箱',width:120,dataIndex:'email'},
					{text:'手机号',width:120,dataIndex:'mobile'},
					{text:'注册日期',width:100,dataIndex:'regDate',renderer:function(v){
						return v.substr(0,10);
					}},
					{text:'过期日期',width:100,dataIndex:'expireDate',renderer:function(v){
						return v.substr(0,10);
					}},
					{text:'状态',width:60,dataIndex:'enabled',align:'center',renderer:function(v){
						if(v){
							return "<font color='#green'>启用</font>";
						}else{
							return "<font color='#e33100'>禁用</font>";
						}
					}},
					{text:'操作',width:500,align:'center',renderer:function(v, cls, record){
						var userCode = record.data.id;
						var enable = record.data.enabled;
						
						var str ="<div>";
						str+='<input type="button" value="修&nbsp;改" style="" onclick="addOrUpdateUserInfo(\''+userCode+'\')"  class="enableCls">'+
						'<input type="button" value="删&nbsp;除" style="" onclick="delteUser()"  class="enableCls">';
						
						if(enable){
							str+='<input type="button" value="禁用" onclick ="enableOrDisableUser(\'2\')" class="enableCls">';
						}else{
							str+='<input type="button" value="启用" onclick ="enableOrDisableUser(\'1\')"  class="enableCls">';
						}
						str+='<input type="button" value="重置密码" onclick="resetPassword()"  class="enableCls">';
						if(userPower.group == 1){
							str+='<input type="button" value="用户分组" onclick="userGroup(\''+userCode+'\',0)"  class="enableCls">';
						}
						if(userPower.role==1){
							str+='<input type="button" value="赋予角色" onclick="grantRole(\''+userCode+'\')"  class="enableCls">';
						}
						if(userPower.organization==1){
							str+='<input type="button" value="分配岗位" onclick="userGroup(\''+userCode+'\',1)"  class="enableCls">';
						}
						/*
						str+='<input type="button" value="重置密码" onclick="resetPassword()"  class="enableCls">'+
						'<input type="button" value="用户分组" onclick="userGroup(\''+userCode+'\',0)"  class="enableCls">'+
						'<input type="button" value="赋予角色" onclick="grantRole(\''+userCode+'\')"  class="enableCls">'+
						'<input type="button" value="授予权限" onclick="grantPermission(\''+userCode+'\')"  class="enableCls">'+
						'<input type="button" value="分配岗位" onclick="userGroup(\''+userCode+'\',1)"  class="enableCls"></div>';
						*/
						if(userCode == '9d246537919249e6bbb10f5ae777f991' || userCode == "78f1d29ac9ee4e3a82beea454359d76d"){
							if(loginUserId() == '558dba2c445e4550b7f5deae9067e4c2')
								str+='<input type="button" value="授予权限" onclick="grantPermission(\''+userCode+'\')"  class="enableCls"></div>';
							else
								str+='<input type="button" value="授予权限" disabled="disabled" onclick="grantPermission(\''+userCode+'\')"  class="enableCls"></div>';
						}else
							str+='<input type="button" value="授予权限" onclick="grantPermission(\''+userCode+'\')"  class="enableCls"></div>';
						return str;
					}}
				],
				selModel: { //选中模型 
					mode:"MULTI",
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
		        },
		        multiSelect: true,//是否允许多选
		        viewConfig:{  
	                enableTextSelection:true  
	            }, 
	            listeners: {  
					'select':function(grid, record){
						var selRows = Ext.getCmp("user_grid").getSelectionModel().getSelection();
						if(selRows.length == 1){
							if(selRows[0].data.id == "9d246537919249e6bbb10f5ae777f991" || selRows[0].data.id == "78f1d29ac9ee4e3a82beea454359d76d"){
								if(loginUserId() == "558dba2c445e4550b7f5deae9067e4c2")
									Ext.getCmp("grantPermission").enable(true);
								else
									Ext.getCmp("grantPermission").disable(true);
							}else{
								Ext.getCmp("grantPermission").enable(true);
							}
						} else{
							Ext.getCmp("grantPermission").enable(true);
						}
					}
		        },
		        tbar:[{
		        	text:'新增',
		        	iconCls:'',
		        	handler:function(){
		        		addOrUpdateUserInfo('');
		        	}
		        },{
		        	text:'删除',
		        	handler:function(){
		        		delteUser();
		        	}
		        },{
		        	text:'注销',
		        	hidden:true, 
		        	handler:function(){
		        		cancleUser();
		        	}
		        },{
		        	text:'启用用户',
		        	iconCls:'',
		        	handler:function(){
		        		enableOrDisableUser('1');
		        	}
		        },{
		        	text:'禁用用户',
		        	iconCls:'',
		        	handler:function(){
		        		enableOrDisableUser('2');
		        	}
		        },{
		        	text:'重置密码',
		        	iconCls:'',
		        	handler:function(){
		        		resetPassword();
		        	}
		        },{
		        	text:'用户分组',
		        	iconCls:'',
		        	hidden:userPower.group==1?false:true,
		        	handler:function(){
		        		userGroupForUsers(0);
		        	}
		        },{
		        	text:'赋予角色',
		        	iconCls:'',
		        	hidden:userPower.role==1?false:true,
		        	handler:function(){
		        		grantRoleForUsers();
		        	}
		        },{
		        	text:'授予权限',
		        	iconCls:'',
		        	id:'grantPermission',
		        	handler:function(){
		        		var grid = Ext.getCmp("user_grid");
		    			var selRows = grid.getSelectionModel().getSelection();
		    			if(selRows.length == 0){
		    				Ext.msg.alert("提示", "请选择用户信息！");
		    				return ;
		    			}
		    			
		    			if(selRows.length > 1){
		    				Ext.msg.alert("提示", "请选择一条用户信息！");
		    				return ;
		    			}
		        		grantPermission(selRows[0].data.id);
		        	}
		        },{
		        	text:'分配岗位',
		        	iconCls:'',
		        	hidden:userPower.organization==1?false:true,
		        	display:true,
		        	handler:function(){
		        		userGroupForUsers(1);
		        	}
		        },{
		        	text:'分配环境',
		        	iconCls:'',
		        	display:true,
		        	handler:function(){
		        		userEnvironment();
		        	}
		        },{
		        	text:'设置数据权限',
		        	iconCls:'',
		        	display:true,
		        	handler:function(){
		        		setDataPrivilege();
		        	}
		        }],
		        multiSelect: true,//是否允许多选
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: user_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }]
			})
			return user_grid;
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
	        		id:'user_search_form',
	        		layout:'hbox',
	        		items:[{
	        			xtype:'textfield',
	        			fieldLabel:'姓名',
	        			name:'realName',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:40,
	        			width:160
	        		},{
	        			xtype:'textfield',
	        			fieldLabel:'账号',
	        			name:'loginId',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:45,
	        			width:160
	        		},{
	        			xtype:'textfield',
	        			margin:'5px 0px 5px 0px',
	        			fieldLabel:'手机号',
	        			name:'mobile',
	        			labelWidth:55,
	        			width:180,
	        			labelAlign:'right'
	        		},{
	        			xtype:'textfield',
	        			margin:'5px 0px 5px 0px',
	        			fieldLabel:'邮箱',
	        			labelWidth:45,
	        			name:'email',
	        			labelAlign:'right',
	        			width:200
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
	        			text:'重  置',
	        			handler:function(){
	        				Ext.getCmp('user_search_form').getForm().reset();
	        			}
	        		}]
	        	}]
	        },{
	        	region:'center',
	        	layout:'fit',
	        	items:[createUserGrid()]
	        }]
		})
	})
	
	function doQuery(){
		var params = Ext.ux.FormUtils.getDataObject(Ext.getCmp('user_search_form'));
		var store = Ext.getCmp('user_grid').getStore();
		store.getProxy().extraParams = params;
		store.loadPage(1);
	}
</script>
</body>
</html>