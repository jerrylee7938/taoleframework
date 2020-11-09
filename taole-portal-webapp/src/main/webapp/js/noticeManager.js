Ext.onReady(function(){
	
	//新增或修改菜单
	function addOrEditNotice(noticeId){
		var node = Ext.getCmp('notice_tree').getSelectionModel().getSelection()[0];
		var title = !noticeId?'新增':'修改';
		var menu_win = Ext.getCmp('addorEdidNotice_win');
		if(!menu_win){
			menu_win = Ext.create('Ext.window.Window',{
				height:270,
				width:530,
				id:'addorEdidNotice_win',
				modal:true,
				title:(noticeId?'修改':'新增')+'消息项',
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
						readOnly:true,
						allowBlank: false,
					    anchor:'90%',
					    fieldStyle:"background-color:#EEEEE0;background-image: none;"
					},{xtype:'hidden', name:'father'},
					{
						xtype:'textfield',
						fieldLabel:'<font color=red>*</font>目录名称',
						labelWidth:80,
						name:'name',
						allowBlank:false,
						labelAlign:'right',
						anchor:'90%'
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
   						 value: '2',
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
						name:'description'
					}]
				}],
				buttonAlign:'center',
				buttons:[{
					text:'保存',
					handler:function(){
						if(!Ext.getCmp('notice_form').getForm().isValid())return;
						var jsonData = Ext.getCmp('notice_form').getForm().getValues();
						
						var sendType = jsonData.sendType;
						delete jsonData.sendType;
						var isArr = sendType instanceof Array;
						var sendTypeAry = new Array();
						if(sendType){
							if(isArr){
								for(var i=0; i<sendType.length; i++){
									sendTypeAry.push({
										"sendTypeId": sendType[i]
									});
								}
							}else{
								sendTypeAry.push({
										"sendTypeId": sendType
									});
							}
						}else{
							if(jsonData.msgType == "2"){
								Ext.Msg.alert("提示", "请选择消息发送方式");
								return false;
							}
						}
						jsonData.sendType = sendTypeAry;
						console.log(jsonData);
						var url;
						if(jsonData.id){
							url = Ext.util.Format.format(URL_USER_NOTICE_UPDATE,jsonData.id);
						}else{
							url = URL_USER_NOTICE_CREATE;
						}
						delete jsonData.url;
						Ext.Ajax.request({
							url: url,
							jsonData: jsonData,
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','消息项保存成功！');
									Ext.getCmp('notice_tree').getStore().load();
									Ext.getCmp('notice_tree').getSelectionModel().deselectAll();
									menu_win.destroy();
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
					text:'取消',
					handler:function(){
						menu_win.destroy();
					}
				}]
			})
		}
		menu_win.show();
		var form = Ext.getCmp("notice_form");
		form.getForm().reset();
		if(noticeId){
			Ext.Ajax.request({
				url: Ext.util.Format.format(URL_USER_NOTICE_RETRIEVE,noticeId),
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
								var added = false;//是否已加过
								sendType.items.each(function(item){
									if(item.inputValue == data.sendTypeId){
										added = true;
										item.setValue(true);//已经存在，则选中
										return false;
									}
								});
								if(!added){//没加过
									comboboxs.push({ name: 'sendType', boxLabel: data.sendTypeName , inputValue: data.sendTypeId});
								}
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
		}else{
			var node = Ext.getCmp('notice_tree').getSelectionModel().getSelection()[0];
			form.getForm().findField('parentName').setValue(node.data.text);
			form.getForm().findField('father').setValue(node.data.id);
		}
		
		Ext.Ajax.request({
			url: Ext.util.Format.format(URL_DICTIONARY, "42c5665c1d40453bbafe521a0d72274c"),
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					var sendType = Ext.getCmp("sendType");
					var comboboxs = new Array();
					Ext.each(data, function(sendTypes){
						comboboxs.push({ name: 'sendType', boxLabel: sendTypes.name , inputValue: sendTypes.value});
					});
					sendType.add(comboboxs);
				}else{
					Ext.Msg.alert('提示','获取消息分发方式失败!<br/>'+data.result_code+":"+data.result_desc);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取消息分发方式失败");
			}
		});
	}
	
	//右击菜单
	var contextMenu = Ext.create('Ext.menu.Menu',{
        items: [{
        	text: '删除',
        	id:'desktop_del',
        	handler:function(){
        		Ext.Msg.confirm('提示','确定要删除选择的消息项吗？',function(btn){
        			if(btn=='yes'){
        				var node = Ext.getCmp('notice_tree').getSelectionModel().getSelection()[0];
		        		Ext.Ajax.request({
							url: URL_USER_NOTICE_DELETE + '?id='+ node.data.id,
							success: function(response){
								var data = Ext.decode(response.responseText);
								if(getResp(data)){
									Ext.Msg.alert('提示','消息项删除成功！');
									Ext.getCmp('notice_tree').getStore().load();
									Ext.getCmp('notice_tree').getSelectionModel().deselectAll();
								}else{
									Ext.Msg.alert('提示','消息项删除失败!<br/>'+data.result_code+":"+data.result_desc);
								}
							},
							failure: function(){
								Ext.Msg.alert("提示", "消息项删除失败");
							}
						});
        			}
        		})
        	}
        },{
        	text: '增加',			//菜单项文本
        	id:'desktop_add',
        	handler: function(){
        		addOrEditNotice();
        	}
        },{
        	text: '修改',			//菜单项文本
        	id:'desktop_update',
        	handler: function(){
        		var node = Ext.getCmp('notice_tree').getSelectionModel().getSelection()[0];
        		addOrEditNotice(node.data.id);
        	}
        },{
        	text:'上移',
        	id: 'desktop_up',
        	handler: function(){
        		var node = Ext.getCmp('notice_tree').getSelectionModel().getSelection()[0];
        		Ext.Ajax.request({
					url: Ext.util.Format.format(URL_USER_NOTICE_UP, node.data.id),
					success: function(response){
						var data = Ext.decode(response.responseText);
						if(getResp(data)){
							Ext.getCmp('notice_tree').getStore().load();
							Ext.getCmp('notice_tree').getSelectionModel().deselectAll();
						}else{
							Ext.Msg.alert('提示','消息项上移失败!<br/>'+data.result_code+":"+data.result_desc);
						}
					},
					failure: function(){
						Ext.Msg.alert("提示", "消息项上移失败");
					}
				});
        	}
        },{
        	text:'下移',
        	id: 'desktop_down',
        	handler: function(){
        		var node = Ext.getCmp('notice_tree').getSelectionModel().getSelection()[0];
        		Ext.Ajax.request({
					url: Ext.util.Format.format(URL_USER_NOTICE_DOWN, node.data.id),
					success: function(response){
						var data = Ext.decode(response.responseText);
						if(getResp(data)){
							Ext.getCmp('notice_tree').getStore().load();
							Ext.getCmp('notice_tree').getSelectionModel().deselectAll();
						}else{
							Ext.Msg.alert('提示','消息项下移失败!<br/>'+data.result_code+":"+data.result_desc);
						}
					},
					failure: function(){
						Ext.Msg.alert("提示", "消息项下移失败");
					}
				});
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
	        	{name :'type',type : 'string'}
	    	]
	    });
		var treeStore = Ext.create('Ext.data.TreeStore', {//树的数据源store
			model : treeModel,//数据模型
			proxy : {//代理
	            type : 'ajax',
	            url :URL_USER_NOTICE_QUERY
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
			listeners: {
				itemcontextmenu:function(tree, record, item, index, e){//注册tree的右键菜单
					e.preventDefault();
					noticeTree.getSelectionModel().select(record);//选择当前树节点
					contextMenu.showAt(e.getXY());
					var type = record.data.type;
					if(type == 1){
						Ext.getCmp('desktop_del').setDisabled(true);
						Ext.getCmp('desktop_update').setDisabled(true);
						Ext.getCmp('desktop_down').setDisabled(true);
						Ext.getCmp("desktop_up").setDisabled(true);
					}else{
						Ext.getCmp('desktop_del').setDisabled(false);
						Ext.getCmp('desktop_update').setDisabled(false);
						Ext.getCmp('desktop_down').setDisabled(false);
						Ext.getCmp("desktop_up").setDisabled(false);
					}
	    		},
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
			    load : function() {
			        Ext.MessageBox.hide();
			    }
			}
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