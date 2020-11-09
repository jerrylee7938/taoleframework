Ext.define('Taole.user.tradeeExamine.controller.AddOrEditPositionCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.user.tradeeExamine.view.AddOrEditPositionWindow"
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
    	if(Taole.user.tradeeExamine.controller.AddOrEditPositionCtrl.isInit)return;

    	Taole.user.tradeeExamine.controller.AddOrEditPositionCtrl.isInit = true;
    	
    	this.control({
    		'addOrEditPositionWindow': {
    			afterrender: function(win){
    				var appAdPositionId = win.appAdPositionId;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				if(isView){
    					win.first =true;
    					this.get(appAdPositionId,function(appAdPosition){
    						if(appAdPosition.code !=1){
    							Ext.Msg.alert(appAdPosition.description);
    						}
    						appAdPosition=appAdPosition.result;
							for(i in appAdPosition){
								if(form.findField(i)){
									if(i =='deadline'){			
										var time = appAdPosition[i];
										var ct = Ext.Date.format(new Date(time),"Y-m-d");
										form.findField('deadline').setValue(ct);
									}else{
										form.findField(i).setValue(appAdPosition[i]);
									}
									
								}
							}
						},null,this);
    				}else{
    					this.getCode(function(data){
    						if(data.code !=1){
    							Ext.Msg.alert(data.description);
    						}
    						var code=data.result;
							form.findField('code').setValue(code);	
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
					
						if(data.code==1){
							Ext.Msg.alert("提示","保存成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("tradeeExaminePanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							},this)
						}else{
							Ext.Msg.alert('提示','保存失败!<br/>'+data.description);
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
     get: function(userCardId, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(URL_MEMBER_MEMBERCARD_RETRIEVE, userCardId);
    	Ext.Ajax.request({
			url: url,
			timeout: 300000,
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
     save: function(appAdPosition, successFn, failureFn, scope){
     	var url = Ext.util.Format.format(URL_MEMBER_MEMBERCARD_UPDATA_DEAD, appAdPosition.userCardId);
		Ext.Ajax.request({
			url: url,
			jsonData: appAdPosition,
			timeout: 300000,
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