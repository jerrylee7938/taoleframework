Ext.onReady(function(){
	
	window.refreshDesk = function(){
		if(parent&&parent.refreshTab){
			parent.refreshTab("mainpage", true);
		}
		
		if(parent&&parent.removeTab){
			parent.removeTab("f53c8d9a87104996a9a066a493486919");
		}
	}
	
	//消息配置
	function megSet(noticeId){
		var node = Ext.getCmp('notice_tree').getSelectionModel().getSelection()[0];
		var megSet_win = Ext.getCmp('megSet_win');
		if(!megSet_win){
			megSet_win = Ext.create('Ext.window.Window',{
				height:270,
				width:530,
				id:'megSet_win',
				modal:true,
				title: "我的消息配置",
				layout:'fit',
				items:[{
					xtype:'form',
					layout:'anchor',
					id:'notice_form',
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
						allowBlank: false,
					    anchor:'90%',
					    readOnly:true,
					    fieldStyle:"background-color:#EEEEE0;background-image: none;"
					},{xtype:'hidden', name:'father'},
					{
						xtype:'textfield',
						fieldLabel:'<font color=red>*</font>目录名称',
						labelWidth:80,
						name:'name',
						allowBlank:false,
						labelAlign:'right',
						anchor:'90%',
						readOnly:true,
					    fieldStyle:"background-color:#EEEEE0;background-image: none;"
					},{
						xtype:'combobox',
						fieldLabel:'<font color=red>*</font>消息类型',
						labelWidth:80,
						name:'msgType',
						allowBlank:false,
						labelAlign:'right',
						anchor:'90%',
						store: getDicStore(NOTICE_TYPE_CODE),
						displayField: 'name',
   						 valueField: 'value',
   						 readOnly:true,
					    fieldStyle:"background-color:#EEEEE0;background-image: none;",
   						 listeners: {
   						 	select:function(file){
   						 		if(file.getValue() == "1"){
   						 			Ext.getCmp("sendType").hide();
   						 		}else{
   						 			Ext.getCmp("sendType").show();
   						 		}
   						 	}
   						 }
					},{
						xtype:'checkboxgroup',
						fieldLabel:'<font color=red>*</font>分发方式',
						labelWidth:80,
						name:'sendType',
						id: 'sendType',
						labelAlign:'right'
					},
					 {
					    fieldLabel: '备注',
						xtype: 'textarea',
						labelWidth:80,
						anchor:'90%',
						labelAlign:'right',
						name:'description',
						readOnly:true,
					    fieldStyle:"background-color:#EEEEE0;background-image: none;"
					}]
				}],
				buttonAlign:'center',
				buttons:[{
					text:'保存',
					handler:function(){
						if(!Ext.getCmp('notice_form').getForm().isValid())return;
						var jsonData = Ext.getCmp('notice_form').getForm().getValues();
						
						var sendType = jsonData.sendType;
						if(sendType){
							delete jsonData.sendType;
		    				var isArr = sendType instanceof Array;
		    				jsonData.sendType = new Array();
		    				jsonData.sendType = isArr?sendType:sendType.split(',');
						}else{
							if(jsonData.msgType == "2"){
								Ext.Msg.alert("提示", "请选择消息发送方式");
								return false;
							}
						}
						
						var notices = new Array();
						notices.push({
							"noticeId": jsonData.id,
							"sendTypeIds": jsonData.sendType
						});
						
						Ext.Ajax.request({
							url: URL_USER_NOTICE_SET,
							jsonData: {
								"notices": notices,
								"userId": userId
							},
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','消息项保存成功！');
									Ext.getCmp('notice_tree').getStore().load();
									Ext.getCmp('notice_tree').getSelectionModel().deselectAll();
									megSet_win.destroy();
								}else{
									Ext.Msg.alert('提示','消息项保存失败!<br/>'+data.result_code+":"+data.result_desc);
								}
							},
							failure: function(){
								Ext.Msg.alert("提示", "消息项保存失败");
							}
						});
					}
				},{
					text: '移除',
					handler:function(){
						var jsonData = Ext.getCmp('notice_form').getForm().getValues();
						Ext.Ajax.request({
							url: URL_USER_NOTICE_SET_DELETE,
							jsonData: {
								"noticeIds": [jsonData.id],
								"userId": userId
							},
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','消息项移除成功！');
									Ext.getCmp('notice_tree').getStore().load();
									Ext.getCmp('notice_tree').getSelectionModel().deselectAll();
									megSet_win.destroy();
								}else{
									Ext.Msg.alert('提示','消息项移除失败!<br/>'+data.result_code+":"+data.result_desc);
								}
							},
							failure: function(){
								Ext.Msg.alert("提示", "消息项移除失败");
							}
						});
					}
				},{
					text:'取消',
					handler:function(){
						megSet_win.destroy();
					}
				}]
			})
		}
		megSet_win.show();
		var form = Ext.getCmp("notice_form");
		form.getForm().reset();
		if(noticeId){
			Ext.Ajax.request({
				url: Ext.util.Format.format(URL_USER_NOTICE_RETRIEVE,noticeId) + "?userId="+userId,
				success: function(response){
					var data = Ext.decode(response.responseText);
					if(getResp(data)){
						for(i in data){
								if(form.getForm().findField(i)){
									form.getForm().findField(i).setValue(data[i]);
								}
							}
						
						var sendTypeData = data.sendType;
						if(sendTypeData){
							var sendType = Ext.getCmp("sendType");
							var comboboxs = new Array();
							Ext.each(sendTypeData, function(data){				
								comboboxs.push({ name: 'sendType', boxLabel: data.sendTypeName , inputValue: data.sendTypeId, checked: data.checked});
							});
							sendType.add(comboboxs);
						}
						
						if(data.msgType == "1"){
							Ext.getCmp("sendType").hide();
						}
					}else{
						Ext.Msg.alert('提示','获取消息项详情失败!<br/>'+data.result_code+":"+data.result_desc);
					}
				},
				failure: function(){
					Ext.Msg.alert("提示", "获取消息项详情失败");
				}
			});
			var node = Ext.getCmp('notice_tree').getSelectionModel().getSelection()[0];
			var parentNode = node.parentNode;
			form.getForm().findField('parentName').setValue(parentNode.data.text)
		}
		
	}
	
	//右击菜单
	var contextMenu = Ext.create('Ext.menu.Menu',{
        items: [{
        	text: '我的消息配置',			//菜单项文本
        	handler: function(){
        		var node = Ext.getCmp('notice_tree').getSelectionModel().getSelection()[0];
        		megSet(node.data.id);
        	}
        }]
    });	
	
	//创建菜单树
	function createNoticeTree(){
		Ext.define('treeModel', {//树的数据模型
	        extend : 'Ext.data.Model',//继承自Model
	        fields : [//字段
	        	{name : 'id',  type : 'string'},  
	        	{name : 'text', type : 'string'},
	        	{name :'leaf',type : 'boolean'},
	        	{name :'type',type : 'string'},
	        	{name : 'resCode',type: 'string'},
	        	{name : 'url',type: 'string'},
	        	{name : 'checked',type: 'boolean'}
	    	]
	    });
	    
	    var treeStore = Ext.create('Ext.data.TreeStore', {//树的数据源store
			model : treeModel,//数据模型
			proxy : {//代理
	            type : 'ajax',
	            url :URL_USER_NOTICE_QUERY+'?userId='+userId
	        }
	    });
	    
		var noticeTree = Ext.create('Ext.tree.Panel',{//菜单树
			id:'notice_tree',
			minWidth: 210,//最小宽度
			width: '100%',
			border:false,
			store : treeStore,//数据源
			autoHeight : true,//是否自动适应高度
			animate: false,//是否有动画效果
			rootVisible: false,//根节点是否可见
			region:'center',
			frame:true,
			listeners :{
				beforeload : function() {
			     	Ext.MessageBox.show({
						title : '系统提示',
						msg : '正在加载消息数据，请稍候......',
						progressText : 'processing now,please wait...',
						width : 300,
						wait : true,
						waitConfig : {
							interval : 150
						}
					});
			    },  
			    load : function(store, records, data) {
			    	  Ext.MessageBox.hide();
			    	  if(data){
			    	  	if(data.length == 0){
				    	  	Ext.getCmp("cancel").hide();
				    	  }else{
				    	  	Ext.getCmp("cancel").show();
				    	  }
			    	  }
			    },
			    itemcontextmenu:function(tree, record, item, index, e){//注册tree的右键菜单
					e.preventDefault();
					noticeTree.getSelectionModel().select(record);//选择当前树节点
					if(record.data.leaf){
						contextMenu.showAt(e.getXY());
					}
	    		}
			},
			buttonAlign:'center',
			buttons:[{
				text:'关闭',
				id: 'cancel',
				hidden: true,
				handler: function(){
					parent.removeTab("f53c8d9a87104996a9a066a493486919");
				}
			}]
		});
		noticeTree.expandAll();
		return noticeTree;
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
	        	items:[createNoticeTree()]
	        }]
		})
});