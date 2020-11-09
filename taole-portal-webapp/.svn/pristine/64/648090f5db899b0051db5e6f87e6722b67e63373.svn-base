Ext.define('Taole.wechat.wechatAccount.controller.AddOrEditPositionCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.wechat.wechatAccount.view.AddOrEditPositionWindow"
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
    	if(Taole.wechat.wechatAccount.controller.AddOrEditPositionCtrl.isInit)return;

    	Taole.wechat.wechatAccount.controller.AddOrEditPositionCtrl.isInit = true;
    	
    	this.control({
    		'addOrEditPositionWindow': {
    			afterrender: function(win){
    				var appAdPositionId = win.appAdPositionId;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				if(isView){
    					this.get(appAdPositionId,function(appAdPosition){
							for(i in appAdPosition){
								if(form.findField(i)){
									form.findField(i).setValue(appAdPosition[i]);
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
					var appAdPosition = this.getForm().getValues();
					
					this.save(appAdPosition, function(data){
						if(data.id){
							Ext.Msg.alert("提示","保存成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("appAdPositionPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							},this)
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
     get: function(appAdPosition, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(APP_ADPOSITION_RETRIEVE, appAdPosition);
    	Ext.Ajax.request({
			url: url,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','获取详情失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取详情失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
     save: function(appAdPosition, successFn, failureFn, scope){
     	var url = APP_ADPOSITION_CREATE;
     	if(appAdPosition.id){
     		url = Ext.util.Format.format(APP_ADPOSITION_UPDATE, appAdPosition.id);
     	}
		Ext.Ajax.request({
			url: url,
			jsonData: appAdPosition,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "保存失败", failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "保存失败，请联系管理员", failureFn, this);
			},
			scope: scope||this
		});
    }
});