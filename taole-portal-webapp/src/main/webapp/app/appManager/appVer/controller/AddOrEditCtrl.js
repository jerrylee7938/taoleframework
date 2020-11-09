Ext.define('Taole.appManager.appVer.controller.AddOrEditCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.appManager.appVer.view.AddOrEditWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'addOrEditWindow'
    },
    {
    	 ref: 'form',
         selector: 'addOrEditWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.appManager.appVer.controller.AddOrEditCtrl.isInit)return;

    	Taole.appManager.appVer.controller.AddOrEditCtrl.isInit = true;
    	
    	this.control({
    		'addOrEditWindow': {
    			afterrender: function(win){
    				var appVerId = win.appVerId;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				var status = "";
    				if(isView){
    					this.get(appVerId,function(appVer){
							appVer.publishTime = Ext.Date.format(new Date(appVer.publishTime), "Y-m-d H:i:s");
							
							for(i in appVer){
								if(form.findField(i)){
									form.findField(i).setValue(appVer[i]);
								}
							}
							
							status = appVer.status;
							var statusStore = form.findField("status").store;
							this.getAppStatus(function(data){
								for (var i = 0; i < data.length; i++) {
						            var status = data[i];
						            if(appVer.status == "DEV"){//研发版
						            	if (status.id == "DEV") {
							            //	statusStore.add(status);
							            }else if(status.id == "TEST"){
							            	statusStore.add(status);
							            }
						            	form.findField("status").setValue('研发版');
						            }else if(appVer.status == "TEST"){//测试版
						            	if (status.id == "DEV") {
						            		statusStore.add(status);
							            }else if(status.id == "PRE" && appVer.testStatus == 1){
							            	statusStore.add(status);
							            }
						            	form.findField("status").setValue('测试版');
						            }else if(appVer.status == "PRE"){//上线版
						            	if (status.id == "TEST" ) {
							            	statusStore.add(status);
							            }
						            	form.findField("status").setValue('预发布版');
						            }else{
						            	statusStore.add(status);
						            }
						        }
								
								
							//	form.findField("status").setValue(appVer.status);
							}, this);
							win.down('button[action=confirm]').setText("保存");
							if(isView&&status == "PRD"){
		    					var collection = form.getFields();
								collection.each(function(field){
									if(field.getName()=="appInfoId"||field.getName()=="systemType"||field.getName()=="developerType"
										||field.getName()=="versionCode"||field.getName()=="status"||field.getName()=="svnVer"){
											field.setReadOnly(true);
								    		field.setFieldStyle("background-color:#EEEEE0;background-image: none;");
										}
								});
		    				}
						},null,this);
    				}else{
    					var statusStore = form.findField("status").store;
    					this.getAppStatus(function(data){
							statusStore.add(data);
						}, this);
    				}
    				if(isRetrieve){
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
    		'addOrEditWindow>form combo[name=systemType]': {//选择系统类型
    			change: function(field){
    				if(field.getValue() == "ios"){
    					this.getForm().form.findField("developerType").show();
    					this.getForm().form.findField("developerType").enable();
    				}else{
    					this.getForm().form.findField("developerType").hide();
    					this.getForm().form.findField("developerType").disable();
    				}
    			},
    			select: function(field){
    				this.getVersionCode();
    			}
    		},
    		'addOrEditWindow>form combo[name=status]': {//选择应用名称
    			expand:function(field){
    			if(field.getValue()=='研发版' && this.getForm().form.findField("testStatus").getValue() != 1){
					Ext.Msg.alert('提示','对不起，当前研发版本测试没有通过，不能修改为测试版本，请联系研发人员确认沟通！');
				}
    		}
    		},
    		'addOrEditWindow>form combo[name=appInfoId]': {//选择应用名称
    			select: function(field){
    				this.getVersionCode();
    			}
    		},
    		'addOrEditWindow>form combo[name=developerType]': {//选择系统类型
    			select: function(field){
    				this.getVersionCode();
    			}
    		},
    		'addOrEditWindow>form combo[name=status]': {//选择版本状态
    			select: function(field){
    				this.getVersionCode();
    			}
    		},
    		'addOrEditWindow>form field[name=updatePathButton]': {//修改安装包
    			change: function(fb, v){
    				var form = this.getForm().form;
    				form.findField("updatePath").setValue(v);
    			}
    		},
    		'addOrEditWindow>form button[name=chooseChannel]': {//选择渠道
    			click: function(){
    				var formpanel = this.getForm();
    				var chooseChannels = formpanel.form.findField("channels").getValue();
    				Ext.create("Taole.appManager.appVer.controller.ChooseChannelCtrl").init();
    				Ext.create("Taole.appManager.appVer.view.ChooseChannelWindow", {
    					chooseChannels: chooseChannels,
			    		afterChooseFn: function(channelsData){
			    			var channels = formpanel.down("checkboxgroup[name=channels]");
	    					var comboboxs = new Array();
	    					channels.removeAll();
							Ext.each(channelsData, function(channel){
								comboboxs.push({ name: 'channel', boxLabel: channel.name , inputValue: channel.id, checked: true});
							});
							channels.add(comboboxs);
							channels.setFieldLabel("已选择渠道");
			    		}
			    	}).show();
    			}
    		},
    		'addOrEditWindow button[action=confirm]':{//确定
    			click: function(){
    				
    				if(!this.getForm().form.isValid())return;
					var appVer = this.getForm().getValues();
					
					if(!appVer.versionDesc && !appVer.bugDesc){
						Ext.Msg.alert("提示", "请输入新功能描述或BUG修复情况！");
						return;
					}
					if(appVer.status == '研发版'){
						appVer.status = 'DEV';
					}else if(appVer.status == '测试版'){
						appVer.status = 'TEST';
					}else if(appVer.status == '预发布版'){
						appVer.status = 'PRE';
					}
					this.save(appVer, function(data){
						if(data.id){
							Ext.Msg.alert("提示","保存成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("appVerPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							},this)
						}
					}, null, this);
    			}
    		},
    		'addOrEditWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'addOrEditWindow button[action=close]':{//关闭
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
     get: function(appVerId, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(APP_VER_RETRIEVE, appVerId);
    	Ext.Ajax.request({
			url: url,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','获取APP版本详情失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取APP版本详情失败", failureFn, this);
			},
			scope: scope
		});
    },
     save: function(appVer, successFn, failureFn, scope){
     	var url = APP_VER_CREATE;
     	if(appVer.id){
     		url = Ext.util.Format.format(APP_VER_UPDATE, appVer.id);
     	}
		Ext.Ajax.request({
			url: url,
			jsonData: appVer,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "版本信息保存失败", failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "版本信息保存失败", failureFn, this);
			},
			scope: scope||this
		});
    },
    getVersionCode: function(){
    	var form = this.getForm().form;
    	var appVerId = this.getWindow().appVerId;
    	var appInfoId = form.findField("appInfoId").getValue();
    	var systemType = form.findField("systemType").getValue();
    	var status = form.findField("status").getValue();
    	if(!appInfoId || !systemType || !status) return false;
    	var developerType = "";
    	if(systemType == "ios"){
    		developerType = form.findField("developerType").getValue();
    		if(!developerType)return false;
    	}
    	Ext.Ajax.request({
			url: APP_VER_GET_VERSION_CODE,
			jsonData: {
				appInfoId: appInfoId,
				systemType: systemType,
				developerType: developerType,
				appVerId: appVerId,
				status: status
			},
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					var resultData = data.result_data;
					form.findField("versionCode").setMinValue(resultData.versionCode);
					form.findField("versionCode").setValue(resultData.versionCode);
				}else{
					Ext.Msg.alert('提示','获取版本编号失败!<br/>'+data.result_code+":"+data.result_desc, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取版本编号失败", this);
			},
			scope: this
		});
    },
    getAppStatus: function(successFn, scope){
    	Ext.Ajax.request({
			url: APP_VER_APP_STATUS,
			success: function(response){
				var data = Ext.decode(response.responseText);
				successFn.call(this, data);
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取版本编号失败", this);
			},
			scope: this
		});
    },
    del: function(appVerIds, successFn, failureFn){
    	Ext.Ajax.request({
			url: APP_VER_DELETE+"?"+appVerIds,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','APP版本失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "APP版本失败", failureFn, this);
			},
			scope: this
		});
    }
});