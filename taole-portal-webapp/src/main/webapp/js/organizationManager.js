Ext.onReady(function(){
	
	function addOrEditDict(organizationId, resetName){
		var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
		var title = resetName?"重命名":!organizationId?'新增':'修改';
		var menu_win = Ext.getCmp('addorEdidorganizationDict_win');
		if(!menu_win){
			menu_win = Ext.create('Ext.window.Window',{
				height:200,
				width:400,
				id:'addorEdidorganizationDict_win',
				modal:true,
				title:title+'组织目录',
				layout:'fit',
				items:[{
					xtype:'form',
					layout:'anchor',
					id:'addorEdidorganizationDict_form',
					margin:'5px',
					frame:true,
					style:{
						'border':'0px'
					},
					items:[{
						xtype:'hidden',
						name:'id'
					},{
						xtype:'hidden',
						name:'type',
						value:1
					},{
						xtype:'textfield',
						fieldLabel:'<font color=red>*</font>组织目录名称',
						labelWidth:90,
						name:'name',
						allowBlank:false,
						labelAlign:'right',
						anchor:'90%'
					},{
						xtype:'textfield',
						fieldLabel: '<font color=red>*</font>上级名称',  
						labelAlign:'right',
						name:'parentName',
						baseHeight:300,
						labelWidth:90,
						readOnly:true,
						allowBlank: false,
					    anchor:'90%',
					    hidden:resetName,
					    fieldStyle:"background-color:#EEEEE0;background-image: none;"
					},{xtype:'hidden', name:'parentNode'},
					{
						fieldLabel: '描述',
						xtype: 'textarea',
						labelWidth:90,
						anchor:'90%',
						labelAlign:'right',
						hidden: resetName,
						name:'description'
					}]
				}],
				buttonAlign:'center',
				buttons:[{
					text:'保存',
					handler:function(){
						if(!Ext.getCmp('addorEdidorganizationDict_form').getForm().isValid())return;
						var jsonData = Ext.getCmp('addorEdidorganizationDict_form').getForm().getValues();
						var url = URL_TK_DICTIONARY_CREATE;
						if(jsonData.id){
							url = Ext.util.Format.format(URL_TK_DICTIONARY_UPDATE,jsonData.id);
						}
						Ext.Ajax.request({
							url: url,
							jsonData: jsonData,
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','组织结构目录保存成功！');
									Ext.getCmp('organizationTree').getStore().proxy.url = URL_USER_ORGANIZATION_TREEROOT;
									Ext.getCmp('organizationTree').getStore().load();
									Ext.getCmp('organizationTree').getSelectionModel().deselectAll();
									menu_win.destroy();
								}else{
									Ext.Msg.alert('提示','组织结构目录保存失败!<br/>'+data.result_code+":"+data.result_desc);
								}
							},
							failure: function(){
								Ext.Msg.alert("提示", "组织结构目录保存失败");
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
		if(resetName){
			menu_win.setHeight(110)
		}
		var form = Ext.getCmp("addorEdidorganizationDict_form");
		form.getForm().reset();
		if(organizationId){
			Ext.Ajax.request({
				url: Ext.util.Format.format(URL_TK_DICTIONARY_RETRIEVE,organizationId),
				success: function(response){
					var data = Ext.decode(response.responseText);
					if(getResp(data)){
						for(i in data){
							if(form.getForm().findField(i)){
								form.getForm().findField(i).setValue(data[i]);
							}
						}
						form.getForm().findField('parentNode').setValue(data.parentId);
					}else{
						Ext.Msg.alert('提示','获取组织结构目录详情失败!<br/>'+data.result_code+":"+data.result_desc);
					}
				},
				failure: function(){
					Ext.Msg.alert("提示", "获取组织结构目录详情失败");
				}
			});
			var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
			var parentNode = node.parentNode;
			form.getForm().findField('parentName').setValue(parentNode.data.text)
		}else{
			var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
			form.getForm().findField('parentName').setValue(node.data.text);
			form.getForm().findField('parentNode').setValue(node.data.id);
		}
	}
	
	function addOrEditPost(organizationId, resetName){
		var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
		var title = resetName?"重命名":!organizationId?'新增':'修改';
		var menu_win = Ext.getCmp('addorEdidorganizationPost_win');
		if(!menu_win){
			menu_win = Ext.create('Ext.window.Window',{
				height:200,
				width:400,
				id:'addorEdidorganizationPost_win',
				modal:true,
				title:title+'岗位',
				layout:'fit',
				items:[{
					xtype:'form',
					layout:'anchor',
					id:'addorEdidorganizationPost_form',
					margin:'5px',
					frame:true,
					style:{
						'border':'0px'
					},
					items:[{
						xtype:'hidden',
						name:'id'
					},{
						xtype:'hidden',
						name:'type',
						value:1
					},{
						xtype:'textfield',
						fieldLabel:'<font color=red>*</font>岗位名称',
						labelWidth:80,
						name:'name',
						allowBlank:false,
						labelAlign:'right',
						anchor:'90%'
					},{
						xtype:'textfield',
						fieldLabel: '<font color=red>*</font>上级名称',  
						labelAlign:'right',
						name:'parentName',
						baseHeight:300,
						labelWidth:80,
						readOnly:true,
						allowBlank: false,
						hidden: resetName,
					    anchor:'90%',
					    fieldStyle:"background-color:#EEEEE0;background-image: none;"
					},{xtype:'hidden', name:'fatherId'},
					{
						fieldLabel: '岗位描述',
						xtype: 'textarea',
						labelWidth:80,
						anchor:'90%',
						labelAlign:'right',
						hidden: resetName,
						name:'description'
					}]
				}],
				buttonAlign:'center',
				buttons:[{
					text:'保存',
					handler:function(){
						if(!Ext.getCmp('addorEdidorganizationPost_form').getForm().isValid())return;
						var jsonData = Ext.getCmp('addorEdidorganizationPost_form').getForm().getValues();
						var url = URL_USER_ORGANIZATION_POST_CREATE;
						if(jsonData.id){
							url = Ext.util.Format.format(URL_USER_ORGANIZATION_POST_UPDATE,jsonData.id);
						}
						Ext.Ajax.request({
							url: url,
							jsonData: jsonData,
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','岗位保存成功！');
									Ext.getCmp('organizationTree').getStore().proxy.url = URL_USER_ORGANIZATION_TREEROOT;
									Ext.getCmp('organizationTree').getStore().load();
									Ext.getCmp('organizationTree').getSelectionModel().deselectAll();
									menu_win.destroy();
								}else{
									Ext.Msg.alert('提示','岗位保存失败!<br/>'+data.result_code+":"+data.result_desc);
								}
							},
							failure: function(){
								Ext.Msg.alert("提示", "岗位保存失败");
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
		if(resetName){
			menu_win.setHeight(110);
		}
		var form = Ext.getCmp("addorEdidorganizationPost_form");
		form.getForm().reset();
		if(organizationId){
			Ext.Ajax.request({
				url: Ext.util.Format.format(URL_USER_ORGANIZATION_POST_RETRIEVE,organizationId),
				success: function(response){
					var data = Ext.decode(response.responseText);
					if(getResp(data)){
						for(i in data){
							if(form.getForm().findField(i)){
								form.getForm().findField(i).setValue(data[i]);
							}
						}
					}else{
						Ext.Msg.alert('提示','获取岗位详情失败!<br/>'+data.result_code+":"+data.result_desc);
					}
				},
				failure: function(){
					Ext.Msg.alert("提示", "获取岗位详情失败");
				}
			});
			var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
			var parentNode = node.parentNode;
			form.getForm().findField('parentName').setValue(parentNode.data.text);
		}else{
			var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
			form.getForm().findField('parentName').setValue(node.data.text);
			form.getForm().findField('fatherId').setValue(node.data.id);
		}
	}
	
	//右击菜单
	var contextDictMenu = Ext.create('Ext.menu.Menu',{
        items: [{
        	text: '新增组织目录',
        	handler:function(){
        		addOrEditDict();
        	}
        },{
        	text: '修改组织目录',
        	handler: function(){
        		var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
        		addOrEditDict(node.data.id);
        	}
        },{
        	text: '重命名',
        	handler: function(){
        		var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
        		addOrEditDict(node.data.id, true);
        	}
        },{
        	text: '删除组织目录',	
        	handler: function(){
        		Ext.Msg.confirm('提示','确定要删除选择的组织目录吗？',function(btn){
        			if(btn=='yes'){
        				var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
		        		Ext.Ajax.request({
							url: URL_USER_ORGANIZATIONDICT_DELETE + '?id='+ node.data.id,
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','组织目录删除成功！');
									Ext.getCmp('organizationTree').getStore().proxy.url = URL_USER_ORGANIZATION_TREEROOT;
									Ext.getCmp('organizationTree').getStore().load();
									Ext.getCmp('organizationTree').getSelectionModel().deselectAll();
								}else{
									Ext.Msg.alert('提示','组织目录删除失败!<br/>'+data.result_code+":"+data.result_desc);
								}
							},
							failure: function(){
								Ext.Msg.alert("提示", "组织目录删除失败");
							}
						});
        			}
        		});
        	}
        },{
        	text: '上移',
        	handler: function(){
        		var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
        		Ext.Ajax.request({
					url: Ext.util.Format.format(URL_USER_ORGANIZATION_POST_UPDOWN, node.data.id) + "?offset=-1",
					success: function(response){
						var data = Ext.decode(response.responseText);
						if(getResp(data)){
							Ext.getCmp('organizationTree').getStore().proxy.url = URL_USER_ORGANIZATION_TREEROOT;
							Ext.getCmp('organizationTree').getStore().load();
							Ext.getCmp('organizationTree').getSelectionModel().deselectAll();
						}else{
							Ext.Msg.alert('提示','组织目录上移失败!<br/>'+data.result_code+":"+data.result_desc);
						}
					},
					failure: function(){
						Ext.Msg.alert("提示", "组织目录上移失败");
					}
				});
        	}
        },{
        	text: '下移',
        	handler: function(){
        		var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
        		Ext.Ajax.request({
					url: Ext.util.Format.format(URL_USER_ORGANIZATION_POST_UPDOWN, node.data.id) + "?offset=1",
					success: function(response){
						var data = Ext.decode(response.responseText);
						if(getResp(data)){
							Ext.getCmp('organizationTree').getStore().proxy.url = URL_USER_ORGANIZATION_TREEROOT;
							Ext.getCmp('organizationTree').getStore().load();
							Ext.getCmp('organizationTree').getSelectionModel().deselectAll();
						}else{
							Ext.Msg.alert('提示','组织目录下移失败!<br/>'+data.result_code+":"+data.result_desc);
						}
					},
					failure: function(){
						Ext.Msg.alert("提示", "组织目录下移失败");
					}
				});
        	}
        },{
        	text:'新增岗位',
        	handler: function(){
        		addOrEditPost();
        	}
        }]
    });	
    
    var contextPostMenu = Ext.create('Ext.menu.Menu',{
        items: [{
        	text: '修改岗位信息',
        	handler:function(){
        		var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
        		addOrEditPost(node.data.id);
        	}
        },{
        	text: '重命名',
        	handler:function(){
        		var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
        		addOrEditPost(node.data.id, true);
        	}
        },{
        	text: '删除岗位信息',
        	handler: function(){
        		Ext.Msg.confirm('提示','确定要删除选择的岗位吗？',function(btn){
        			if(btn=='yes'){
        				var node = Ext.getCmp('organizationTree').getSelectionModel().getSelection()[0];
		        		Ext.Ajax.request({
							url: URL_USER_ORGANIZATION_POST_DELETE + '?ids='+ node.data.id,
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','岗位删除成功！');
									Ext.getCmp('organizationTree').getStore().proxy.url = URL_USER_ORGANIZATION_TREEROOT;
									Ext.getCmp('organizationTree').getStore().load();
									Ext.getCmp('organizationTree').getSelectionModel().deselectAll();
								}else{
									Ext.Msg.alert('提示','岗位删除失败!<br/>'+data.result_code+":"+data.result_desc);
								}
							},
							failure: function(){
								Ext.Msg.alert("提示", "岗位删除失败");
							}
						});
        			}
        		});
        	}
        }]
    });	
	
	//创建菜单树
	function createOrganizationTree(){
		Ext.define('treeModel', {//树的数据模型
	        extend : 'Ext.data.Model',//继承自Model
	        fields : [//字段
	        	{name : 'id',  type : 'string'},  
	        	{name : 'text', type : 'string'},
	        	{name :'leaf',type : 'boolean'},
	        	{name :'type',type : 'string'},
	        	{name :'orgNodeType',type: 'string'}
	    	]
	    });
		var treeStore = Ext.create('Ext.data.TreeStore', {//树的数据源store
			model : treeModel,//数据模型
			proxy : {//代理
	            type : 'ajax',
	            url :URL_USER_ORGANIZATION_TREEROOT
	        },
	        listeners : {
	            'beforeexpand' : function(node,eOpts){
					if(node.raw.id != 'root'){
						this.proxy.url = Ext.util.Format.format(URL_USER_ORGANIZATION_TREEONE, node.raw.id);
					}
	            }
	        }
	    });
		
		var organizationTree = Ext.create('Ext.tree.Panel',{//菜单树
			id:'organizationTree',
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
					organizationTree.getSelectionModel().select(record);//选择当前树节点
	    			var orgNodeType = record.data.orgNodeType;
	    			if(orgNodeType == ORGNODE_TYPE_DICT){
	    				contextDictMenu.showAt(e.getXY());
	    			}else if(orgNodeType == ORGNODE_TYPE_POST){
	    				contextPostMenu.showAt(e.getXY());
	    			}else{
	    				contextDictMenu.showAt(e.getXY());
	    			}
//					var type = record.data.type;
//					if(type == 1){
//						Ext.getCmp('desktop_del').setDisabled(true);
//						Ext.getCmp('desktop_update').setDisabled(true);
//						Ext.getCmp('desktop_down').setDisabled(true);
//						Ext.getCmp("desktop_up").setDisabled(true);
//					}else{
//						Ext.getCmp('desktop_del').setDisabled(false);
//						Ext.getCmp('desktop_update').setDisabled(false);
//						Ext.getCmp('desktop_down').setDisabled(false);
//						Ext.getCmp("desktop_up").setDisabled(false);
//					}
	    		},
	    		beforeload : function() {
			       Ext.MessageBox.show({
						title : '系统提示',
						msg : '正在加载组织数据，请稍候......',
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
		return organizationTree;
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
	        	items:[createOrganizationTree()]
	        }]
		})
});