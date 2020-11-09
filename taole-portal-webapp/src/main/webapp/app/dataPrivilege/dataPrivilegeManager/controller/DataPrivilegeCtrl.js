Ext.define('Taole.dataPrivilege.dataPrivilegeManager.controller.DataPrivilegeCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.dataPrivilege.dataPrivilegeManager.view.DataPrivilegePanel'
    ],
    refs: [
    {
    	 ref: 'panel',
         selector: 'dataPrivilegePanel'
    },
    {
    	 ref: 'form',
         selector: 'dataPrivilegePanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'dataPrivilegePanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'dataPrivilegePanel': {
    			afterrender: function(panel){
    				var appCombo = this.getForm().form.findField("appId");
    				if(panel.appId && panel.appId != 'null'){
    					appCombo.setValue(panel.appId);
    					appCombo.setReadOnly(true);
    				}
    			}
    		},
    		'dataPrivilegePanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.load();
    			}
    		},
    		'dataPrivilegePanel>form button[action=reset]': {//重置
    			click: function(){
    				var appId = this.getPanel().appId;
					this.getForm().form.reset();
					if(appId && appId != 'null'){
						this.getForm().form.findField("appId").setValue(appId);
					}
    			}
    		},
    		'dataPrivilegePanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'dataPrivilegePanel>grid button[action=add]': {//新增
    			click: function(){
    				var panel = this.getPanel();
    				this.edit(panel.appId, null, false);
    			}
    		},
    		'dataPrivilegePanel>grid button[action=update]': {//修改
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要修改的记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录修改");
    					return;
    				}
    				var panel = this.getPanel();
    				this.edit(panel.appId, selRows[0].data, true);
    			}
    		},
    		'dataPrivilegePanel>grid button[action=remove]': {//删除
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要删除的数据");
    					return;
    				}
    				
    				var ids = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					ids.push("id="+record.data.id);
    				}
    				
    				Ext.Msg.confirm("提示", "确定删除选中的数据权限信息吗？", function(bt){
						if(bt=='yes'){
							this.del(ids.join("&"), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		},
    		'dataPrivilegePanel>grid button[action=test]': {
    			click: function(){
    				Ext.create("Taole.dataPrivilege.dataPrivilegeManager.controller.SetDataPrivilegeCtrl").init();
			    	Ext.create("widget.setDataPrivilegeWindow",{
			    		scope: this,
			    		afterChooseFn: function(){
			    			
			    		},
			    		bizType: 'USER_DATAPRIVILEGE',
			    		bizId: 'd319d11ab83a48c69a0375313fa227be'
			    	}).show();
    			}
    		}
    	});
    },
    edit: function(appId, dataPrivilegeData, isView, isRetrieve){
		Ext.create("Taole.dataPrivilege.dataPrivilegeManager.controller.AddOrEditDataPrivilegeCtrl").init();
    	Ext.create("widget.addOrEditDataPrivilegeWindow",{
    		scope: this,
    		title: (isView ? "修改" : "新增") + "数据权限",
    		afterChooseFn: function(){
    			
    		},
    		appId: appId,
    		dataPrivilegeData: dataPrivilegeData,
    		isView: isView,
    		isRetrieve: isRetrieve
    	}).show();
    },
    del: function(dataPrivilegeIds, successFn, failureFn){
    	Ext.Ajax.request({
			url: DATA_PRIVILEGE_DELETE+"?"+dataPrivilegeIds,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					Ext.Msg.alert("提示", "数据权限删除成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','数据权限删除失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "数据权限删除失败，请联系管理员！", failureFn, this);
			},
			scope: this
		});
    }
});