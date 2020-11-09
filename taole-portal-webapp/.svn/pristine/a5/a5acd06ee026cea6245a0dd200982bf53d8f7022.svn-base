Ext.onReady(function(){
	
	window.refreshDesk = function(){
		if(parent&&parent.refreshTab){
			parent.refreshTab("mainpage", true);
		}
		
		if(parent&&parent.removeTab){
			parent.removeTab("23085e0ece32439690124838d11e82ea");
		}
	}
	
	//创建菜单树
	function createDestopTree(){
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
	            url :URL_USER_DESKTOP_QUERY+'?userId='+userId
	        }
	    });
	    
		var desktopTree = Ext.create('Ext.tree.Panel',{//菜单树
			id:'desktop_tree',
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
						msg : '正在加载桌面数据，请稍候......',
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
				    	  	Ext.getCmp("save").hide();
				    	  	Ext.getCmp("cancel").hide();
				    	  }else{
				    	  	Ext.getCmp("save").show();
				    	  	Ext.getCmp("cancel").show();
				    	  }
			    	  }
			    },
				checkchange :function(node,checked){
					
					if(checked){
						node.cascade(function(node){
							node.set('checked',checked);
					    });
						var pNode = node.parentNode;
						var isChecked = true;
						pNode.cascade(function(node){
							if(node.data.leaf){
								if(!node.get('checked')){
									isChecked = false;
									return;
								}
							}
					    });
					    pNode.set('checked',isChecked);
					   var parentNode = pNode.parentNode;
					   var pIsChecked = true;
					   parentNode.cascade(function(node){
					   		if(node.data.type == 2){
					   			if(!node.get('checked')){
									pIsChecked = false;
									return;
								}
					   		}
					    });
					    parentNode.set('checked',pIsChecked);
					}else{
						var parentNode = node.parentNode;
						parentNode.set('checked',checked);
						for (; parentNode != null; parentNode = parentNode.parentNode) {
					     	 parentNode.set("checked", checked);
					     }
						node.cascade(function(node){
							node.set('checked',checked);
					    });
					}
				}
			},
			buttonAlign:'center',
			buttons:[{
				text:'保存',
				id: 'save',
				hidden: true,
				handler:function(){
					var nodes =  Ext.getCmp('desktop_tree').getChecked();
					var jsonData = '{';
					var ids = new Array();
					Ext.each(nodes,function(record){
						ids.push(record.data.id);
					});
					jsonData += "'desktopId':"+Ext.encode(ids)+",'userId':'"+userId+"'}";
					Ext.Ajax.request({
						url: URL_USER_DESKTOP_SET,
						jsonData:Ext.decode(jsonData),
						success: function(response){
							var data = Ext.decode(response.responseText);
							if(getResp(data)){
								Ext.Msg.alert('提示','用户桌面配置成功！！');
								Ext.getCmp('desktop_tree').getStore().load();
								refreshDesk();
							}else{
								Ext.Msg.alert('提示','用户桌面配置失败!<br/>'+data.result_code+":"+data.result_desc);
							}
						},
						failure: function(){
							Ext.Msg.alert("提示", "用户桌面配置失败");
						}
					});
				}
			},{
				text:'取消',
				id: 'cancel',
				hidden: true,
				handler: function(){
					parent.removeTab("23085e0ece32439690124838d11e82ea");
				}
			}]
		});
		desktopTree.expandAll();
		return desktopTree;
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
	        	items:[createDestopTree()]
	        }]
		})
});