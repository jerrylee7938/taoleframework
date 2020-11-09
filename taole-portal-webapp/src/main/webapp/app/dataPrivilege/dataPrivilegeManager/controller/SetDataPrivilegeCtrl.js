Ext.define('Taole.dataPrivilege.dataPrivilegeManager.controller.SetDataPrivilegeCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.dataPrivilege.dataPrivilegeManager.view.SetDataPrivilegeWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'setDataPrivilegeWindow'
    },
    {
    	 ref: 'form',
         selector: 'setDataPrivilegeWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.dataPrivilege.dataPrivilegeManager.controller.SetDataPrivilegeCtrl.isInit)return;

    	Taole.dataPrivilege.dataPrivilegeManager.controller.SetDataPrivilegeCtrl.isInit = true;
    	
    	this.control({
    		'setDataPrivilegeWindow': {
    			afterrender: function(win){
    				var bizType = win.bizType;
    				var bizId = win.bizId;
    				this.allDataPrivilege(bizId, bizType, function(allDataPrivilege){
    					console.log(allDataPrivilege.result_data);
    					this.genrDataPrivilegePanel(win, allDataPrivilege.result_data);
    				}, null, this);
    			}
    		},
    		'setDataPrivilegeWindow button[action=confirm]':{//确定
    			click: function(){
    				var bizType = this.getWindow().bizType;
    				var bizId = this.getWindow().bizId;
					var panel = this.getWindow().down("form").down("panel");
					var dataPrivilegeTree = panel.query("treepanel");
					var dataPrivileges = new Array();
					for(var i =0; i < dataPrivilegeTree.length; i++){
						var tree = dataPrivilegeTree[i];
						var dataPrivilegeId = tree.dataPrivilegeId;
						var rootNode = tree.getRootNode();
						
						var dataPrivilege = new Array();
						rootNode.cascade(function(rec){
							if(rec.data.checked && rec.data.leaf){
								var obj = {"id":rec.data.id, "name":rec.data.text};
								dataPrivilege.push(obj);
							}
						},true);
						
						var dpObj = {"dataPrivilegeId": dataPrivilegeId, "dataPrivilege":dataPrivilege};
						dataPrivileges.push(dpObj);
					}
					
					
					var dataKey;
					if(bizType == 'USER_DATAPRIVILEGE'){
			    		dataKey = "userId";
			    	}else if(bizType == 'ROLE_DATAPRIVILEGE'){
			    		dataKey = "roleId";
			    	}else if(bizType == 'GROUP_DATAPRIVILEGE'){
			    		dataKey = "groupId";
			    	}
			    	
			    	var dataPrivilegeStr = "{'"+dataKey+"':'"+bizId+"'}";
			    	var dataPrivilegeData = eval('(' + dataPrivilegeStr + ')');
			    	dataPrivilegeData.dataPriviages = dataPrivileges;
					this.save(bizType, dataPrivilegeData, function(){
						Ext.Msg.alert("提示","保存成功！", function(){
							this.getWindow().destroy();
						},this)
					}, null, this);
    			}
    		},
    		'setDataPrivilegeWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
    allDataPrivilege:function(bizId, bizType, successFn, failureFn, scope){
    	var url;
    	if(bizType == 'USER_DATAPRIVILEGE'){
    		url = USER_DATA_PRIVILEGE_ALL + '?userId=' + bizId;
    	}else if(bizType == 'ROLE_DATAPRIVILEGE'){
    		url = ROLE_DATA_PRIVILEGE_ALL + '?roleId=' + bizId;
    	}else if(bizType == 'GROUP_DATAPRIVILEGE'){
    		url = GROUP_DATA_PRIVILEGE_ALL + '?groupId=' + bizId;
    	}
    	Ext.Ajax.request({
			url: url,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','获取权限数据失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取权限数据失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
    save: function(bizType, userDataPrivilegeData, successFn, failureFn, scope){
    	var url;
    	if(bizType == 'USER_DATAPRIVILEGE'){
    		url = USER_DATA_PRIVILEGE_SAVE;
    	}else if(bizType == 'ROLE_DATAPRIVILEGE'){
    		url = ROLE_DATA_PRIVILEGE_SAVE;
    	}else if(bizType == 'GROUP_DATAPRIVILEGE'){
    		url = GROUP_DATA_PRIVILEGE_SAVE;
    	}
		Ext.Ajax.request({
			url: url,
			jsonData: userDataPrivilegeData,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "数据权限保存失败：" + data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "数据权限保存失败，请联系管理员", failureFn, this);
			},
			scope: scope||this
		});
    },
    genrDataPrivilegePanel: function(win, data){
    	var treePanel = this.getTreePanel(data);
    	win.down("form").add(treePanel);
    },
    getTreePanel: function(treeData){
    	var moduleItem =[];
		var children = treeData;
		var width = document.body.clientWidth;
		var len = children.length;
		var level = len%3 == 0? len/3:Math.floor(len/3)+1;
		var column = level == 1? len:3;
		var initWidth =Math.floor((width-20)/column);
		var initHeight =250;
		
		for(var j = 0; j < len; j++){
			var node = children[j];
			node.expanded = true;
			var fields = [{name : 'id',  type : 'string'},{name: 'text', mapping: 'name'},{name :'leaf',type : 'boolean'},{name : 'checked',type: 'boolean'}];
			var _tree = Ext.create("Ext.tree.Panel",{
				title: node.appName,
				dataPrivilegeId: node.dataPrivilegeId,
				style:{
					'margin':'0px 4px 4px 0px'
				},
				store:Ext.create('Ext.data.TreeStore', {//树的数据源store
					fields:fields,
					root: {
				        expanded: true,
				        children:node.tree
				    }
			    }),
			    listeners :{
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
			    rootVisible: false
			})
			_tree.expandAll();
			moduleItem.push(_tree);
		}
		
		var panel =Ext.create('Ext.panel.Panel',{
			xtype:'panel',
			layout: {
	            type: 'table',
	            columns: column
	        },
	       	border:false,
	        defaults: {frame:false, width:initWidth, height: initHeight},
	        items:[]
		});
		panel.add(moduleItem);
		panel.doLayout();
		return panel;
    }
});