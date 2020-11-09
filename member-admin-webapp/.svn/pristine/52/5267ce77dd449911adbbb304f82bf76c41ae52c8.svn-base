Ext.define('Taole.user.userInfoManager.controller.AddOrEditPositionCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.user.userInfoManager.view.AddOrEditPositionWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'addOrEditPositionWindow'
    },
    {
    	 ref: 'form',
         selector: 'addOrEditPositionWindow>form'
    },
    {
   	 ref: 'grid',
        selector: 'addOrEditPositionWindow>grid'
   }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.user.userInfoManager.controller.AddOrEditPositionCtrl.isInit)return;

    	Taole.user.userInfoManager.controller.AddOrEditPositionCtrl.isInit = true;
    	
    	this.control({
    		'addOrEditPositionWindow': {
    			afterrender: function(win){
    				var doctorId = win.doctorId;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				
    				if(!isRetrieve){
    					var addBtn= Ext.getCmp("t123");
    					addBtn.el.createChild({
    			            cls: Ext.baseCSSPrefix + 'form-file-input',
    			            tag: 'input',
    			            type: 'file',
    			            style : 'width:100%;font-size:12px',
    			            size: 1
    			        }).on('change', function(){
    			        	var URL = URL_UPLOAD_FILE+"?type=1";
    			        	var xhr = new XMLHttpRequest(); // 初始化XMLHttpRequest
    			        	xhr.onreadystatechange=state_Change;
    			        	xhr.open('post', URL, true);
    			        	var fd = new FormData(); // 这里很关键，初始化一个FormData，并将File文件发送到后台
    			        	fd.append("file", this.dom.files[0]);
    			        	fd.append("type", 1);
    			        	xhr.send(fd);   		
    			        	function state_Change(){
    			    			if (xhr.readyState==4){// 4 = "loaded"
    			    			  if (xhr.status==200){// 200 = "OK"
    			    			   	 	var data = Ext.decode(xhr.responseText);
    			    					if(getResp(data)){
    			    						if(data.success==true){
    			    							var url = data.url;
    			    	 						var image = Ext.getCmp("avatar");
    											image.value =data.path;
    											image.setSrc(url);
    			    						}else{
    			    							 Ext.Msg.alert('提示','上传失败！！');
    			    						}
    			    					}
    			    			   } else{
    			    			   	 	resultData = xhr.statusText;
    			    			   	 Ext.Msg.alert('提示','上传失败！！'+resultData);
    			    			   	 	
    			    			    }
    			    			 }
    			    		}
    			        });
    				}
    				
    				
    				if(isView){
    					this.get(doctorId,function(doctorData){
    						win.aloadOne=true;
    						doctorInfo=doctorData.result;
    						if(doctorInfo.thirdAccount){
    							Ext.getCmp("regist").disable();
    						}else{
    							Ext.getCmp("regist").enable();
    						}
							for(i in doctorInfo){
								if(i =='avatar'){
									var image = Ext.getCmp("avatar");
									image.value =doctorInfo[i];
									image.setSrc(doctorInfo.path);
								}
								if(form.findField(i)){
									if(i=='birthday'){
										var time = doctorInfo[i];
										var ct = Ext.Date.format(new Date(time),"Y-m-d");	
										form.findField(i).setValue(ct);
									}else{
										form.findField(i).setValue(doctorInfo[i]);
									}
									
								}
							}
						},null,this);
    				}
    				if(isRetrieve){
    					var form = this.getForm().form;
    					var collection = form.getFields();
						collection.each(function(field){
						    field.setReadOnly(true);
						    field.show();
						    field.setFieldStyle("background-color:#EEEEE0;background-image: none;");
						});
    					win.down('button[action=confirm]').hide();
    					win.down('button[action=cancel]').setText("关闭")
    				}
    			}
    		},
    		'addOrEditPositionWindow grid[id=acc_grid] button[action=add]': {//新增
    			click: function(){
    				console.log('11');
    				var arr = new Array();
    				arr.push({
    					"loginId": ""
    				});
    				Ext.getCmp('acc_grid').store.add(arr);
    				Ext.getCmp('acc_grid').getPlugin("rowediting").startEdit(Ext.getCmp('acc_grid').store.getCount()-1,0);
    			}
    		},
    		'addOrEditPositionWindow button[action=confirm]':{//确定
    			click: function(){
    				
    				if(!this.getForm().form.isValid())return;
					var appAdPosition = this.getForm().getValues();
					var main_Image = Ext.getCmp("avatar");
					appAdPosition.avatar = main_Image.value;
					this.save(appAdPosition, function(data){
					
						if(data.code==1){
							Ext.Msg.alert("提示","保存成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("doctorPanel>grid")[0];
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
     get: function(appAdPosition, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(URL_DOCTOR_RETRIEVE, appAdPosition);
    	Ext.Ajax.request({
			url: url,
			timeout: 300000,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code == 1){
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
     	var url = URL_DOCTOR_CREATE;
     	var method ='POST'
     	if(appAdPosition.accountId){
     		url = Ext.util.Format.format(URL_DOCTOR_UPDATE, appAdPosition.accountId);
     		method = 'PUT';
     	//	url= URL_TASK_UPDATE+'?jobId='+appAdPosition.id;
     	}
		Ext.Ajax.request({
			url: url,
			jsonData: appAdPosition,
			timeout: 300000,
			method:method,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code == 1){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "保存失败:"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "保存失败，请联系管理员", failureFn, this);
			},
			scope: scope||this
		});
    }
});