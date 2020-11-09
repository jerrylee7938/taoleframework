Ext.onReady(function(){
		
	window.createTabPanel = function(){
		var centerPanel = Ext.create('Ext.tab.Panel',{//选项卡控件
			id : 'centerpanel',//控件id
			region:'center',//布局区域
			autoDestory : false,//是否自动销毁
			scripts: true, 
			border:0,
			activeTab : 0,//默认激活第一个tab页
			animScroll : true,//使用动画滚动效果
			enableTabScroll : true,//tab标签超宽时自动出现滚动按钮
			tabMargin : 50,			
			tabWidth : 100,
			layout:'fit',
			items:[createDesktopPanel()]
			
		});
		return centerPanel;
	}
	
	window.createDesktopPanel = function(){
		var desktopPanel = Ext.create('Ext.panel.Panel', {
		    title:'&nbsp;我的桌面',
        	baseCls:'x-plain',//
        	id:'mainpage',
        	layout:'fit',
        	autoScroll:true,//是否自动出现滚动条
        	containerScroll: true,
        	columnWidth:1,
        	bodyStyle:'padding:0px 0px 0px 0px',
	        html:'<iframe src="desktop.jsp" width="100%"  height="100%" frameborder="0" scrolling="auto"></iframe>'
        });
        
        return desktopPanel;
	}
	
	window.showPasswordWin = function(){
		var pass_window = Ext.getCmp("pass_win");
		if(!pass_window){
			pass_window = Ext.create('Ext.window.Window',{
				height:200,
				width:300,
				layout:'fit',
				title:'密码修改',
				items:[{
					xtype:'form',
					layout:'anchor',
					id:'edit_pass_form',
					frame:true,
					style:{
						'border':'0px'
					},
					items:[{
						xtype:'textfield',
						fieldLabel:'旧密码',
						margin:'10 0 0 0',
						labelWidth:80,
						allowBlank:false,
						//blankText:'密码至少8位且包含字母和数字',
						//minLengthText:'密码至少8位且包含字母和数字',
						labelAlign:'right',
						inputType:'password',
						id:'oldpassword',
						anchor:'80%'
					},{
						xtype:'textfield',
						fieldLabel:'新密码',
						margin:'10 0 0 0',
						labelWidth:80,
						minLength:8,
						labelAlign:'right',
						inputType:'password',
						allowBlank:false,
						blankText:'密码至少8位且包含字母和数字',
						minLength:6,
						minLengthText:'密码至少8位且包含字母和数字',
						id:'password',
						anchor:'80%'
					},{
						xtype:'textfield',
						fieldLabel:'新密码确认',
						margin:'10 0 0 0',
						labelWidth:80,
						minLength:8,
						allowBlank:false,
						blankText:'密码至少8位且包含字母和数字',
						minLengthText:'密码至少8位且包含字母和数字',
						inputType:'password',
						labelAlign:'right',
						id:'repassword',
						anchor:'80%'
					}]
				}],
				buttonAlign:'center',
				buttons:[{
					text:'保存',
					handler:function(){
						var form = Ext.getCmp("edit_pass_form");
						if(form.getForm().isValid()){
							var password = Ext.getCmp("password").getValue();
							var repassword = Ext.getCmp("repassword").getValue();
							var oldPass = Ext.getCmp("oldpassword").getValue();
							if(password != repassword){
								Ext.Msg.alert("提示","两次输入的密码不一致");
							}else{
								Ext.Ajax.request({
									method:'post',
									url: $service.portal + '/us.User/'+userId+'/updateUserPassword?oldPwd='+oldPass+'&newPwd='+password,
									success:function(resp,opts){
										var r = eval("(" + resp.responseText + ")");
										if (r.code != 1) {
											Ext.Msg.alert("提示",'修改密码失败：' + r.description);
										} else{
											Ext.Msg.alert("提示",'密码修改成功!');
											pass_window.destroy();
										}
									},
									failure: function(resp,opts) { 
										
									}
								})
							}
						}
					}
				},{
					text:'取消',
					handler:function(){
						pass_window.destroy();
					}
				}]
			})
		}
		pass_window.show();
	}	
	var viewPort = Ext.create('Ext.Viewport',{
		layout: {
            type: 'border',
            padding: 3
        },
        defaults: {
            split: true
        },
		items:[{
			region:'center',
			border:0,
			layout:'fit',
			id:'center_panel',
			frame:true,
			items:[createTabPanel()]
		},
		//{
			//region:'north',
			//border:0,
			//html:'',
			//hidden:true,
			//height:60,
			//minHeight:60,
			//maxHeight:60
		//},
		{
			region: 'west',
			width:250,
			minWidth:250,
			maxWidth:250,
			layout:'border',
			border:false,
			collapsible:true,
			items:[{
				region:'north',
				id:'north_panel',
				title:'Hi, 网站管理员',
				collapsible:true,
				height:isAdminOrRoot() ? 115 : 135,
				frame:true,
				html:isAdminOrRoot() ? '<div class="admin" style="display: block;"><div class="image"><img id="currentuser-avatar" src="image/avatar.jpg" class="img-polaroid"></div><ul class="control"><li><span class="icon-cog"></span> <a id="pwd_click"  onclick="showPasswordWin();" href="javascript:void(0);">修改密码</a></li><li><span class="icon-share-alt"></span> <a href="javascript:logout();">退出</a></li></ul><div class="info"><span id="time"></span></div></div>' : '<div class="admin" style="display: block;"><div class="image"><img id="currentuser-avatar" src="image/avatar.jpg" class="img-polaroid"></div><ul class="control"><li style="height:20px;"><span class="icon-user"></span> <a onclick="addTab(\'qxml1\', \'权限目录\', $ctx+\'/portal/User/privilegeList.jsp\');" href="javascript:void(0);">权限目录</a></li><li><span class="icon-cog"></span> <a id="pwd_click"  onclick="showPasswordWin();" href="javascript:void(0);">修改密码</a></li><li><span class="icon-share-alt"></span> <a href="javascript:logout();">退出</a></li></ul><div class="info"><span id="time"></span></div></div>',
				listeners:{
					'afterrender':function(){
						Ext.getCmp("north_panel").setTitle(" Hi,"+userName);
						setInterval(function(){
							var time = document.getElementById("time");
							time.innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());
						},1000);
					}
				}
				
			},{
				region:'center',
				layout:'border',
				border:false,
				items:[{
						region:'north',
						id:'north_panel1',
						margins:'5 0 5 0',
						frame:true,
						xtype:'combobox',
						displayField:'environmentName',
						valueField:'environmentId',
						forceSelection : true,
						editable : false,
						value: $.session.get('defaultSystemType'),
						store: Ext.create('Ext.data.Store',{
							autoLoad:true,
							fields:['environmentId','environmentName','isDefault'],
							proxy: { 
								type: 'ajax', 
								url:$service.portal+'/us.UserEnv/collection/query?userId='+userId,
								actionMethods : 'GET', 
								reader: { 
									type: 'json',
									root:'result_data.items'
									
								} 
							},
							listeners:{
								load:function(store){
									for(var i=0;i<store.getCount();i++){
										var record = store.getAt(i);
										if(record.data.isDefault == '1'){
											console.log($.session.get('defaultSystemType'))
											var combo = Ext.getCmp("north_panel1");
											if(!$.session.get('defaultSystemType')){
												combo.setValue(record.data.environmentId);
											}else {
												combo.setValue($.session.get('defaultSystemType'));
											}
											
											var accordion_panel = Ext.getCmp("accordion_panel");
								         	accordion_panel.removeAll();
								         	removeAllTab(true);
											var panel = LoadMenu(combo.getValue());
											accordion_panel.add(panel);
											accordion_panel.doLayout();
											$.session.set('defaultSystemType', combo.getValue());
										}
									}
								}
							}
						}),
						listeners:{
					         'select': function(combo, record){
					         	var accordion_panel = Ext.getCmp("accordion_panel");
					         	accordion_panel.removeAll();
					         	removeAllTab(true);
					         	var panel = LoadMenu(combo.getValue());
								accordion_panel.add(panel);
								accordion_panel.doLayout();
								$.session.set('defaultSystemType', combo.getValue());
					         }
					    }
					},{
						region:'center',
						collapsible:true,
						title:"系统导航菜单",//标题\
						border:false,
						frame:true,
						id:'accordion_panel',
						layout: 'accordion',
						items:[]
					}  
				]
			
			}
//			,{
//				region:'south',
//				collapsible:true,
//				title:"系统导航菜单",//标题\
//				border:false,
//				frame:true,
//				id:'accordion_panel',
//				layout: 'accordion',
//				items:[]
//			}
			]
		}]
	});
	
	/*
	var panel = LoadMenu();
	var accordion_panel = Ext.getCmp("accordion_panel");
	accordion_panel.add(panel);
	accordion_panel.doLayout();
	*/
});

function LoadMenu(systemType){
	var panelItem = [];
	$.ajax({
		type:'GET',
		url: $service.portal + '/us.Menu/'+userId+'/getPortalMenu?systemType=' + systemType,
		//url:$ctx+"/portal/data/menu.json",
		dataType:'json',
		async:false,
		success:function(data){
			if('responseText' in data){
				data = eval("("+data.responseText+")");
			}else{
				data = data;
			}
			if(data){
				var data = data[0].children;
				var length = data.length;
			  //var panel = Ext.getCmp("accordion_panel");
				for(var i = 0; i < length;i++){
					var object = data[i];
					var children = object.children;
					var _panel = Ext.create('Ext.tree.Panel',{
						xtype:'treepanel',
						store:Ext.create('Ext.data.TreeStore', {//数据源
							fields:["id",{name: 'text', mapping: 'name'},{name: 'url', mapping: 'action'}],
					        root: {
					            children:children,
					            expanded: true
					        }
					    }),
						title:object.name,
				        width:'100%',//宽度
				        border:false,
				        height:'98%',//高度
				        autoScroll:true, //是否自动出现滚动条
				        animate:false, //是否有动画效果
				        rootVisible: false,
				        listeners:{
				        	itemclick:function(view,record,index,item,e){
				  				var center_panel = Ext.getCmp('centerpanel');
				  				var id = record.data.id;
								var n = center_panel.getComponent(id);
								var url = record.data.url;
								if(!n && url !='' && url!="null"){
					    			n = center_panel.add({   
					                	id: id,   
					               	 	title:'&nbsp;'+record.data.text,  
					                	closeAction : 'close',
					                	closable:true, 
					                	html:'<iframe src="'+url+'?loginUserId='+userId+'" width="100%"  height="100%" frameborder="0" scrolling="auto"></iframe>'
					    			});
					    		}
								center_panel.setActiveTab(n);
				        	}
				        }
				    });
				    panelItem.push(_panel);
				}
			}
		},
		error:function(xhr, status, error){
			alert("获取用户菜单数据失败，请重新登陆！");
		}
	});
	return panelItem;
}

function logout(){
	window.location.href = "logout_center.jsp";
}

/**
 * 添加标签页
 * @param {} id
 * @param {} title
 * @param {} url
 */
function addTab(id, title, url){
	var center_panel = Ext.getCmp('centerpanel');
	var n = center_panel.getComponent(id);
	if(n){
		center_panel.remove(n);
	}
	if(url !='' && url!="null"){
		n = center_panel.add({   
	    	id: id,   
	   	 	title:'&nbsp;'+title,
	    	closeAction : 'close',
	    	closable:true, 
	    	html:'<iframe src='+url+' width="100%"  height="100%" frameborder="0" scrolling="auto"></iframe>'
		});
	}
	center_panel.setActiveTab(n);
}



/**
 * 刷新tab
 * @param {} id
 * @param {} title
 * @param {} url
 */
function refreshTab(id, isSwitchTab){
	var center_panel = Ext.getCmp('centerpanel');
	var panel = center_panel.getComponent(id);
	if(panel){
		if(isSwitchTab){
			center_panel.setActiveTab(panel);
		}
		//panel.update('<iframe src='+url+' width="100%"  height="100%" frameborder="0" scrolling="auto"></iframe>');
		center_panel.el.select("iframe").first().dom.contentWindow.location.reload();
	}
}

function removeTab(id){
	var center_panel = Ext.getCmp('centerpanel');
	var panel = center_panel.getComponent(id);
	if(panel){
		center_panel.remove(panel);
	}
}

function removeAllTab(isAddCenter){
	var center_panel = Ext.getCmp('centerpanel');
	center_panel.removeAll();
	if(isAddCenter){
		center_panel.add(createDesktopPanel());
	}
}

function onLogoutClick(){
	var loadMask = new Ext.LoadMask(document.body, {msg : '正在向服务器发送请求...'});
	loadMask.show();
	Ext.Ajax.request({
		url:$service.portal + '/us.User/collection/logout',
		method:'get',
		success:function(resp){
			loadMask.hide();
			var r = eval("(" + resp.responseText + ")");
			if (r.code == 1) {
				window.location.href="login.jsp";
			}
		},
		failure:function(){
			loadMask.hide();
		}
	})
}

function isAdminOrRoot(){
	var loginUserId = userId;
	if(loginUserId == '558dba2c445e4550b7f5deae9067e4c2' 
		|| loginUserId == '9d246537919249e6bbb10f5ae777f991'
			|| loginUserId == '78f1d29ac9ee4e3a82beea454359d76d'){
		return true;
	}
	return false;
}

