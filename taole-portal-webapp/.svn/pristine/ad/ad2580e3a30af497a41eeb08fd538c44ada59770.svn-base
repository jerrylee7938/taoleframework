Ext.define('Taole.sms.smsApply.controller.AddOrEditPositionCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.sms.smsApply.view.AddOrEditPositionWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'addOrEditPositionWindow'
    },
    {
    	 ref: 'form',
         selector: 'addOrEditPositionWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.sms.smsApply.controller.AddOrEditPositionCtrl.isInit)return;

    	Taole.sms.smsApply.controller.AddOrEditPositionCtrl.isInit = true;
    	
    	this.control({
    		'addOrEditPositionWindow': {
    			afterrender: function(win){
    				var taskId = win.taskId;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				if(isView){
    					this.get(taskId,function(sms){
    						var smsDetail = sms.result;
							for(i in smsDetail){
								if(form.findField(i)){
									form.findField(i).setValue(smsDetail[i]);
								}
							}
						},null,this);
    				}
    				if(isRetrieve){
    					var form = this.getForm().form;
    					var collection = form.getFields();
						collection.each(function(field){
						    field.setReadOnly(true);
						    field.setFieldStyle("background-color:#EEEEE0;background-image: none;");
						});
    					win.down('button[action=confirm]').hide();
    					win.down('button[action=cancel]').setText("关闭")
    				}
    			}
    		},
    		'addOrEditPositionWindow button[action=confirm]':{//确定
    			click: function(){
    				
    				if(!this.getForm().form.isValid())return;
					var sms = this.getForm().getValues();
					
					this.save(sms, function(data){
						if(data.code == 1){
							Ext.Msg.alert("提示","保存成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("smsApplyPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							},this)
						}else{
							Ext.Msg.alert("提示", "保存失败！<br/>"+data.code+":"+data.description, this);

						}
					}, null, this);
    			}
    		},
    		'addOrEditPositionWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'addOrEditPositionWindow button[action=close]':{//关闭
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
     get: function(id, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(SMS_APPLY_DETAIL, id);
    	Ext.Ajax.request({
			url: url,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','获取详情失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取详情失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
     save: function(smsData, successFn, failureFn, scope){
     	var url = APP_ADPOSITION_CREATE;
     	if(smsData.id){
     		url = Ext.util.Format.format(SMS_APPLY_UPDATE, smsData.id);
     	}
		Ext.Ajax.request({
			url: url,
			jsonData: smsData,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "保存失败！<br/>"+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "保存失败，请联系管理员", failureFn, this);
			},
			scope: scope||this
		});
    }
});