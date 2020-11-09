Ext.define('Taole.appManager.appVer.controller.ChooseChannelCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.appManager.appVer.view.ChooseChannelWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'chooseChannelWindow'
    },
    {
    	 ref: 'form',
         selector: 'chooseChannelWindow>form'
    },
    {
    	 ref: 'grid',
         selector: 'chooseChannelWindow>grid'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.appManager.appVer.controller.ChooseChannelCtrl.isInit)return;

    	Taole.appManager.appVer.controller.ChooseChannelCtrl.isInit = true;
    	
    	this.control({
    		'chooseChannelWindow': {
    			afterrender: function(win){
    				var formpanel = this.getForm();
    				var appVerId = win.appVerId;
    				var type = win.type;
    				if(type =='ios'){
    					type='2&type=3';
    				}else if(type == 'android'){
    					type = '1&type=3';
    				}else{
    					type ="3";
    				}
    				
    				this.getGrid().store.on("beforeload", function(store){
			        	store.proxy.extraParams["appVerId"]=appVerId;
			        	store.proxy.extraParams["status"]='enable';
    				}, this);
    				
    				formpanel.form.findField("appVerId").setValue(appVerId);
    				this.get(function(data){
    					Ext.each(data, function(record){
    						var itemPanel = Ext.create('Ext.panel.Panel', {
		                        layout: 'anchor',
								frame: true,
								baseCls: 'x-plain',
								style: 'margin-top:5px;',
								defaults:{
									labelAlign: 'right',
									labelWidth: 80,
									anchor: '90%'
								},
		                        items: [
									{
									    xtype: 'filefield',
									    fieldLabel: record.name,
									    name:record.key,
									    width: 500,
										buttonText: '选择安装包',
										style:{
										  'margin-left':'5px'
										}
									}
								]
		                    });
		                    formpanel.add(itemPanel)
    					})
    				}, null, type,this);
    			}
    		},
    		'chooseChannelWindow>grid':{
    			edit: function(editor, e){
    				this.save(e.record.data, function(data){
    					this.getGrid().store.load();
    					this.getGrid().getSelectionModel().deselectAll();
    				}, null, this);
    			},
    			canceledit: function(editor, e){
    				var record = e.record;
    				if(!record.data.id){
    					this.getGrid().store.remove(record);
    				}
    			}
    		},
    		'chooseChannelWindow button[action=confirm]':{//确定
    			click: function(){
    				var formpanel = this.getForm().form;
    				this.getForm().submit({
						url:URL_USER_CHANNEL_UPLOAD_APP,
						method:'post',
						timeout: 3600000,
						success:function(form, action){
							if (action.result.success === false) {
								Ext.Msg.alert('提示','上传失败:'+action.result.message);
							} else {
								this.getWindow().close();
								var grid =  Ext.ComponentQuery.query("appVerPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							}
						},
						failure:function(resp){
							Ext.Msg.alert('提示','保存失败！！');
						},
						scope: this
					});
    			}
    		},
    		'chooseChannelWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
    save: function(appNews, successFn, failureFn, scope){
    	var url = APP_VER_CHANNEL_UPDATE;
    	if(appNews.id){
    		url = Ext.util.Format.format(APP_VER_CHANNEL_UPDATE, appNews.id);
    	}
		Ext.Ajax.request({
			url: url,
			jsonData: appNews,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "保存失败<br/>"+data.result_code + "：" + data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "保存失败", failureFn, this);
			},
			scope: scope||this
		});
    },
    get: function(successFn, failureFn, type,scope){
    	Ext.Ajax.request({
			url: URL_USER_CHANNEL_QUERY+'?type='+type+'&status=enable',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data.items);
				}else{
					Ext.Msg.alert('提示','获取发布渠道信息失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取发布渠道信息失败", failureFn, this);
			},
			scope: scope
		});
    }
});