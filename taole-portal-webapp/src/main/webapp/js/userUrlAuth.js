Ext.onReady(function(){
	function createPanel (){
		var userUrlAuthPanel = Ext.create("Ext.panel.Panel",{
			layout:'fit',
			region:'center',
			id:'userUrlAuthPanel',
			frame:false,
			items:[{
				xtype:'form',
				id:'userUrlAuthForm',
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
					xtype:'textfield',
					fieldLabel:'要请求的URL',
					id:"url",
					allowBlank:false,
					labelWidth:80,
					labelAlign:'right',
					width:800
				},{
					x:5,
					y:35,
					xtype:'textfield',
					fieldLabel:'用户登录账号',
					id:'account',
					allowBlank:false,
					labelWidth:80,
					labelAlign:'right',
					width:300
				},{
					x:310,
					y:35,
					xtype:'combobox',
					fieldLabel:'应用系统',
					id:'appId',
					allowBlank:false,
					labelWidth:80,
					labelAlign:'right',
					width:300,
					displayField:'name',
					valueField:'id',
					store:Ext.create('Ext.data.Store', {
					    fields: ['id', 'name'],
					    autoLoad:true,
						proxy : {
							type : 'ajax',
							url : $service.portal+'/us.App/collection/query',
							reader : {
								type : 'json',
								root : 'items'
							}
						}
					})
				},{
					xtype:'button',
					text:'查询',
					x:650,
					y:35,
					width:80,
					handler:function(){
						var form = Ext.getCmp("userUrlAuthForm").getForm();
						if(form.isValid()){
							var url = Ext.getCmp("url").getValue();
							url = encodeURIComponent(url);
							var account = Ext.getCmp("account").getValue();
							var appId = Ext.getCmp("appId").getValue();
							loadData(url, account, appId);
						}
					}
				},{
					xtype:'button',
					text:'重置',
					x:750,
					y:35,
					width:80,
					handler:function(){
						var form = Ext.getCmp("userUrlAuthForm").getForm();
						form.reset();
						reset();
					}
				},{
					x:70,
					y:70,
					xtype:'label',
					text:'结论：'
				},{
					x:105,
					y:70,
					xtype:'label',
					id:'pass',
					text:''
				},{
					x:180,
					y:70,
					xtype:'label',
					text:'备注：'
				},{
					x:215,
					y:70,
					xtype:'label',
					id:'passDesc',
					text:''
				},{
					x:70,
					y:100,
					xtype:'fieldset',
					title: '结论分析',
					width:570,
					height:120,
					margin:'5px',
					items:[{
						xtype:'gridpanel',
						id:'jielunGrid',
						height:90,
						border:false,
						columnLines: true,//各列之间是否有分割线
						store:Ext.create('Ext.data.Store', {
					        fields:['name', 'value']
			           }),
					   columns:[{
						 	text: "名称",
				            dataIndex: 'name',
				            align:'center',
				            width:300
					   },{
						   text: "值",
				            dataIndex: 'value',
				            align:'center',
				            width:230,
				            renderer:function(val,meta,record){
				            	if(val == 'true'){
				            		return "是";
				            	}else{
				            		return "否";
				            	}
				            }
					   }]
					}]
				},{
					x:70,
					y:230,
					xtype:'label',
					text:'用户信息：'
				},{
					x:130,
					y:230,
					xtype:'label',
					id:'userId'
					//text:''
				},{
					x:70,
					y:260,
					xtype:'label',
					text:'URL信息：'
				},{
					x:130,
					y:260,
					xtype:'label',
					id:'urlId',
					text:''
				},{
					x:70,
					y:290,
					xtype:'fieldset',
					id:'urlFieldSet',
					hidden:true,
					title: 'URL信息',
					width:570,
					margin:'5px',
					items:[{
						xtype:'form',
						frame:true,
						height:280,
						autoScroll:true,
						style:{
							'border':'0px'
						},
						layout:'absolute',
						border:false,
						items: [{
							x:5,
							y:5,
							xtype:'label',
							text:'URL：'
						},{
							x:35,
							y:5,
							xtype:'label',
							id:'urlResource',
							text:''
						},{
							x:5,
							y:30,
							xtype:'label',
							text:'URL资源名称：'
						},{
							x:90,
							y:30,
							xtype:'label',
							id:'urlName',
							text:''
						},{
							x:5,
							y:60,
							xtype:'label',
							text:'功能ID：'
						},{
							x:60,
							y:60,
							xtype:'label',
							id:'funId',
							text:''
						},{
							x:300,
							y:60,
							xtype:'label',
							text:'功能名称：'
						},{
							x:370,
							y:60,
							xtype:'label',
							id:'funName',
							text:''
						},{
							x:5,
							y:90,
							xtype:'label',
							text:'权限ID：'
						},{
							x:60,
							y:90,
							xtype:'label',
							id:'priId',
							text:''
						},{
							x:300,
							y:90,
							xtype:'label',
							text:'权限名称：'
						},{
							x:370,
							y:90,
							xtype:'label',
							id:'priName',
							text:''
						},{
							x:5,
							y:120,
							xtype:'tabpanel',
							layout:'fit',
							items: [{
								title:"所属角色",
								layout:'fit',
								items:[{
									border:false,
									xtype:'gridpanel',
									id:'roleGrid',
									columnLines: true,//各列之间是否有分割线
									store:Ext.create('Ext.data.Store', {
								        fields:['id', 'name']
						           }),
								   columns:[{
									 	text: "角色ID",
							            dataIndex: 'id',
							            align:'center',
							            width:300
								   },{
									   text: "角色名称",
							            dataIndex: 'name',
							            align:'center',
							            width:250
								   }],
							       viewConfig:{  
					                  enableTextSelection:true  
					              }
								}]
							},{
								title:"所属组",
								layout:'fit',
								items:[{
									border:false,
									xtype:'gridpanel',
									id:'groupGrid',
									columnLines: true,//各列之间是否有分割线
									store:Ext.create('Ext.data.Store', {
								        fields:['id', 'name'],
								        autoLoad:true
						           }),
								   columns:[{
									 	text: "组ID",
							            dataIndex: 'id',
							            align:'center',
							            width:150
								   },{
									   text: "组名称",
							            dataIndex: 'name',
							            align:'center',
							            width:150
								   }],
							      viewConfig:{  
					                  enableTextSelection:true  
					              }
								}]
							}]
						}]
					}]
				}]
			}]
		})
		return userUrlAuthPanel;
	}
	
	function loadData(url, account, appId){
		reset();
		showWaitMsg();
		Ext.Ajax.request({
			url:$service.portal+'/us.Resource/collection/userUrlAuth?account='+account+"&url="+url+"&appId="+appId,
			method:'POST',
			dataType:'json',
			success:function(resp){
				hideWaitMsg();
				var text = resp.responseText;
				var data = eval("("+text+")");
				if(data){
					var pass = data.pass;
					if(pass){
						Ext.getCmp("pass").setText("允许访问");
					}else{
						Ext.getCmp("pass").setText("不允许访问");
					}
					Ext.getCmp("passDesc").setText(data.passDesc);
					
					var isEveryOneUrl = data.isEveryOneUrl;
					var isUserUrl = data.isUserUrl;
					var isAuthUrl = data.isAuthUrl;
					loadJieLunGridData(isEveryOneUrl, isUserUrl, isAuthUrl);
					
					var userId = data.userId;
					//Ext.getCmp("userId").setText(userId);
					Ext.getCmp("userId").getEl().setHTML('<a href="#" onclick="userInfo(\''+ userId+'\')">'+userId+'</a>');//\''+ userId+'\'
					
					var resId = data.resId;
					if(resId){
						Ext.getCmp("urlId").setText(resId);
						Ext.getCmp("urlFieldSet").show();
						var resource = data.resource;
						var base = resource.base;
						if(base){
							var uri = base.uri;
							var name = base.name;
							Ext.getCmp("urlResource").setText(uri);
							Ext.getCmp("urlName").setText(name);
						}
						var func = resource.func;
						if(func){
							Ext.getCmp("funId").setText(func.id);
							Ext.getCmp("funName").setText(func.name);
						}
						
						var priv = resource.priv;
						if(priv){
							Ext.getCmp("priId").setText(priv.id);
							Ext.getCmp("priName").setText(priv.name);
						}
						
						var roles = resource.roles;
						if(roles){
							var store = Ext.getCmp("roleGrid").getStore();
							store.removeAll();
							store.add(roles);
						}
						
						var groups = resource.groups;
						if(groups){
							var store = Ext.getCmp("groupGrid").getStore();
							store.removeAll();
							store.add(groups);
						}
					}
				}
			},
			failure:function(resp){
				Ext.Msg.alert('提示','数据加载失败!');
			}
		});
	}
	
	
	function reset(){
		Ext.getCmp("pass").setText("");
		Ext.getCmp("passDesc").setText("");
		Ext.getCmp("userId").setText("");
		Ext.getCmp("urlId").setText("");
		Ext.getCmp("urlResource").setText("");
		Ext.getCmp("urlName").setText("");
		Ext.getCmp("funId").setText("");
		Ext.getCmp("funName").setText("");
		Ext.getCmp("priId").setText("");
		Ext.getCmp("priName").setText("");
		var store = Ext.getCmp("roleGrid").getStore();
		store.removeAll();
		var groupStore = Ext.getCmp("groupGrid").getStore();
		groupStore.removeAll();
		var jieLuntore = Ext.getCmp("jielunGrid").getStore()
		jieLuntore.removeAll();
		Ext.getCmp("urlFieldSet").hide();
	}
	
	function loadJieLunGridData(isEveryOneUrl, isUserUrl, isAuthUrl){
		var jsonData = "[";
		jsonData = jsonData + "{'name':'是否在鉴权名单中','value':'"+isAuthUrl+"'},";
		jsonData = jsonData + "{'name':'URL是否在EveryOne的权限范围内','value':'"+isEveryOneUrl+"'},";
		jsonData = jsonData + "{'name':'是否匹配到用户的URL权限信息','value':'"+isUserUrl+"'}";
		jsonData = jsonData + "]";
		var store = Ext.getCmp("jielunGrid").getStore()
		store.removeAll();
		store.add(eval("("+jsonData+")"));
	}
	
	window.userInfo = function(userId){
		var win = Ext.getCmp("user_win");
		if(!win){
			win = Ext.create("Ext.window.Window",{
				title:'用户信息',
				id:'user_win',
				height:300,
				width:600,
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
					text:'关闭',
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
		var roleArray = role_form.query("checkbox");
		for(var i = 0; i < roleArray.length; i++){
			var checkbox = roleArray[i];
			checkbox.disable(true);
		}
		
		var group_fieldset = Ext.getCmp("groupList");
		group_fieldset.add(addGroupTo(600,""));
		group_fieldset.doLayout();
		group_fieldset.disable(true)
		
		var per_form = Ext.getCmp("add_per_form");
		per_form.add(addPerTo(600));
		per_form.doLayout();
		var perArray = per_form.query("checkbox");
		for(var i = 0; i < perArray.length; i++){
			var checkbox = perArray[i];
			checkbox.disable(true);
		}
		
		$.ajax({
			type:'GET',
			url:$service.portal+'/us.User/'+userId+'/retrieve',
			dataType:'json',
			async:false,
			success:function(data){
				if(data){
					var form = Ext.getCmp('basic_form').getForm();
					for(i in data){
						if(form.findField(i)){
							form.findField(i).setValue(data[i]);
						}
					}
					
					var avatar = data.avatar;
					if(avatar){
						Ext.getCmp('img').el.dom.src="/portal/service/rest/tk.File/"+avatar+"/";
					}
					
					var groupList = data.groups;
					if(groupList && groupList.length >0){
						for(var i = 0; i < groupList.length; i++){
							var group = groupList[i];
							Ext.getCmp(group.id).setValue(true);
						}
					}

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
					
					var accounts = data.accounts;
					var store = Ext.getCmp("acc_grid").getStore();
					store.removeAll();
					store.add(accounts);
				}
			},
			failure:function(){
				Ext.Msg.alert("提示","用户信息获取失败")
			}
		});
	}
	
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
						fieldLabel:'员工编号',
						readOnly: true,
						fieldStyle: 'background-color:#EEEEE0;background-image: none;'
					},{
						xtype:'textfield',
						labelWidth:60,
						labelAlign:'right',
						anchor:'80%',
						name:'cardId',
						fieldLabel:'身份证ID',
						readOnly: true,
						fieldStyle: 'background-color:#EEEEE0;background-image: none;'
					},{
						xtype:'textfield',
						labelWidth:60,
						labelAlign:'right',
						anchor:'80%',
						name:'mobile',
						fieldLabel:'手机号码',
						readOnly: true,
						fieldStyle: 'background-color:#EEEEE0;background-image: none;'
					},{
						xtype:'textfield',
						labelWidth:60,
						labelAlign:'right',
						anchor:'80%',
						name:'nickName',
						fieldLabel:'用户昵称',
						readOnly: true,
						fieldStyle: 'background-color:#EEEEE0;background-image: none;'
					},{
						xtype:'textfield',
						labelWidth:60,
						labelAlign:'right',
						anchor:'80%',
						name:'realName',
						fieldLabel:'真实姓名',
						readOnly: true,
						fieldStyle: 'background-color:#EEEEE0;background-image: none;'
					},{
						xtype:'textfield',
						labelWidth:60,
						labelAlign:'right',
						anchor:'80%',
						name:'email',
						vtype:'email',
						fieldLabel:'联系邮箱',
						readOnly: true,
						fieldStyle: 'background-color:#EEEEE0;background-image: none;'
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
					}]
				}]
			}]
		})
		return user_form;
	}
	
	function createAccountGrid(userId){
		Ext.define('AccountModel',{
			extend:'Ext.data.Model',
			fields:['id','enabled','loginId','effectDate','expireDate','status']
		});
		
		var acc_store = Ext.create('Ext.data.Store',{
			model: 'AccountModel',//数据模型
	        remoteSort: true
		});
		var acc_grid = Ext.create('Ext.grid.Panel',{
			store: acc_store,
			id:'acc_grid',
			title:'账号信息',
			border:false,
			columnLines:true,
			selModel:{
				selType : 'cellmodel'//复选框选择模式Ext.selection.CheckboxModel  
			},
			columns:[
				{xtype: 'rownumberer',text: '序号',width:40,align:'center'},
				{text:'账号',width:120,dataIndex:'loginId',editor: {
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
				}}
			]
        })
        return acc_grid;
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
	    	items:[createPanel()]
	    }]
	});
});
