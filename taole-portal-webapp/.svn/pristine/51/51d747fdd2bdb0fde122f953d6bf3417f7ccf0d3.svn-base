Ext.define('Taole.dataPrivilege.dataPrivilegeManager.controller.AddOrEditDataPrivilegeCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.dataPrivilege.dataPrivilegeManager.view.AddOrEditDataPrivilegeWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'addOrEditDataPrivilegeWindow'
    },
    {
    	 ref: 'form',
         selector: 'addOrEditDataPrivilegeWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.dataPrivilege.dataPrivilegeManager.controller.AddOrEditDataPrivilegeCtrl.isInit)return;

    	Taole.dataPrivilege.dataPrivilegeManager.controller.AddOrEditDataPrivilegeCtrl.isInit = true;
    	
    	this.control({
    		'addOrEditDataPrivilegeWindow': {
    			afterrender: function(win){
    				var dataPrivilegeData = win.dataPrivilegeData;
    				var isView = win.isView;
    				var appId = win.appId;
    				var form = this.getForm().form;
    				if(isView){
    					for(i in dataPrivilegeData){
							if(form.findField(i)){
								form.findField(i).setValue(dataPrivilegeData[i]);
							}
						}
    				}else{
    					if(appId && appId != 'null'){
	    					form.findField("appId").setValue(appId);
	    				}
    				}
    			}
    		},
    		'addOrEditDataPrivilegeWindow button[action=confirm]':{//确定
    			click: function(){
    				
    				if(!this.getForm().form.isValid())return;
					var dataPrivilegeData = this.getForm().getValues();
					
					this.save(dataPrivilegeData, function(data){
						Ext.Msg.alert("提示","保存成功！", function(){
							this.getWindow().destroy();
							var grid =  Ext.ComponentQuery.query("dataPrivilegePanel>grid")[0];
							grid.store.load();
							grid.getSelectionModel().deselectAll();//取消选中行
						},this)
					}, null, this);
    			}
    		},
    		'addOrEditDataPrivilegeWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'addOrEditDataPrivilegeWindow button[action=close]':{//关闭
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
     save: function(dataPrivilegeData, successFn, failureFn, scope){
     	var url = DATA_PRIVILEGE_CREATE;
     	if(dataPrivilegeData.id){
     		url = Ext.util.Format.format(DATA_PRIVILEGE_UPDATE, dataPrivilegeData.id);
     	}
		Ext.Ajax.request({
			url: url,
			jsonData: dataPrivilegeData,
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
    validate:function(dataPrivilegeData, successFn, failureFn, scope){
    	showWaitMsg("配置信息校验中，请稍后…………");
    	Ext.Ajax.request({
			url: DATA_PRIVILEGE_CONFIGVALIDATE,
			jsonData: dataPrivilegeData,
			success: function(response){
				hideWaitMsg();
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					failureFn.call(this, data);
					//Ext.Msg.alert("提示", "配置校验失败：" + data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				hideWaitMsg();
				Ext.Msg.alert("提示", "数配置校验失败，请联系管理员", failureFn, this);
			},
			scope: scope||this
		});
    },
    configValidate: function(){
     	var window = this.getWindow();
     	var dataPrivilegeData = this.getForm().getValues();
     	
     	this.validate(dataPrivilegeData, function(data){
     		Ext.create("Taole.dataPrivilege.dataPrivilegeManager.controller.ConfigValidateWinCtrl").init();
	    	Ext.create("widget.configValidateWindow",{
	    		scope: this,
	    		title: "校验数据权限",
	    		afterChooseFn: function(){
	    		},
	    		dataPrivilegeData: data.result_data
	    	}).show();
     	}, function(data){
     		Ext.Msg.alert("提示", data.result_code + "：" + data.result_desc);
     	}, this);
     	
    }
});