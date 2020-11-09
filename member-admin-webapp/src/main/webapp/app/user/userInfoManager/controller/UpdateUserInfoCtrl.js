Ext.define('Taole.user.userInfoManager.controller.UpdateUserInfoCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.user.userInfoManager.view.UpdateUserInfoWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'updateUserInfoWindow'
    },
    {
    	 ref: 'form',
         selector: 'updateUserInfoWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.user.userInfoManager.controller.UpdateUserInfoCtrl.isInit)return;

    	Taole.user.userInfoManager.controller.UpdateUserInfoCtrl.isInit = true;
    	this.control({
    		'updateUserInfoWindow': {
    			afterrender: function(win){
    				var cardId = win.cardId;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				
    				var addBtn= Ext.getCmp("t123");
					addBtn.el.createChild({
			            cls: Ext.baseCSSPrefix + 'form-file-input',
			            tag: 'input',
			            type: 'file',
			            style : 'width:100%;font-size:12px',
			            size: 1
			        }).on('change', function(){
			        	var URL = URL_MEMBER_FILE_UPDATE+"?type=1";
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
    				
    				if(isView){
    					this.get(cardId,function(appAdPosition){
    						if(appAdPosition.code !=1){
    							Ext.Msg.alert(appAdPosition.description);
    						}
    						appAdPosition=appAdPosition.result;
							for(i in appAdPosition){
								if(form.findField(i)){
									if(i=='birthday'){
										var time = appAdPosition[i];
										var ct = Ext.Date.format(new Date(time),"Y-m-d");	
										form.findField(i).setValue(ct);
									}else{
										form.findField(i).setValue(appAdPosition[i]);
									}
								}
							}
							var image = Ext.getCmp("avatar");
							image.setSrc(appAdPosition.userAvatar);
							image.value =appAdPosition.avator;
						},null,this);
    				}
    				if(isRetrieve){
    					Ext.getCmp("t123").hide();
    					Ext.getCmp("paizhao").hide();
    					var form = this.getForm().form;
    					var collection = form.getFields();
						collection.each(function(field){
						    field.setReadOnly(true);
						    field.setFieldStyle("background-color:#EEEEE0;background-image: none;");
						});
    				}
    			}
    		},
    		'updateUserInfoWindow button[action=confirm]':{//确定
    			click: function(){
    				if(!this.getForm().form.isValid())return;
					var userData = this.getForm().getValues();
					var main_Image = Ext.getCmp("avatar");
					var data =userData;
					data.name=userData.userName;
					data.id =data.userCardId;
					data.avatar = main_Image.value;
			
					this.save(data,function(data){
						if(data.code==1){
							Ext.Msg.alert("提示","操作成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("userInfoPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							},this)
						}else{
							Ext.Msg.alert('提示','操作失败!<br/>'+data.description);
						}
					}, null, this);
					
					
    			}
    		},
    		"updateUserInfoWindow>form button[action=paizhao]":{
    			click:function(){
    				this.getPricture();
    			}
    		},
    		'updateUserInfoWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'updateUserInfoWindow button[action=close]':{//关闭
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
					Ext.Msg.alert('提示','获取会员详情失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取会员详情失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
    getPricture:function(){

		var picture_window = Ext.getCmp("picture_window");
		if(!picture_window){       
			picture_window = Ext.create('Ext.window.Window',{
				title: '拍摄照片',
				height:350,
				id:'picture_window',
				width:600,
				resizable:false,
				modal:true,
				layout:'fit',
				html:'<video class="lf" style="float: left;margin-right: 20px;" id="video" width="240" height="240" autoplay></video><canvas class="lf" style="display:block; float: left;margin-right: 20px;" id="canvas" width="240" height="240"></canvas>',
				buttonAlign:'center',
				buttons:[{
					text:'拍摄',
					handler:function(){
						var context = document.getElementById("canvas").getContext("2d");
				        context.drawImage(video, 0, 0, 240, 240);
				        var img = document.getElementById("canvas").toDataURL("image/png");
					}
				},{
					text:'保存',
					handler:function(){

				        var data=document.getElementById("canvas").toDataURL("image/png");
				        data = data.split(',')[1];
				        //数据格式转换
				        data = window.atob(data);
				        var ia = new Uint8Array(data.length);
				        for (var i = 0; i < data.length; i++) {
				            ia[i] = data.charCodeAt(i);
				        }
				        var blob = new Blob([ia], { type: 'image/jpeg' });
				        //2.提交到服务器
				        var fd = new FormData();
				        fd.append('file', blob);
				        fd.append('type', 1);

				        //提交到服务器
				        var xhr = new XMLHttpRequest();
				        xhr.open('post',URL_MEMBER_FILE_UPDATE, true);
				        xhr.onreadystatechange = function () {
			    			if (xhr.readyState==4){// 4 = "loaded"
				    			  if (xhr.status==200){// 200 = "OK"
				    			   	 	var data = Ext.decode(xhr.responseText);
				    					if(getResp(data)){
				    						if(data.success==true){
				    							var url = data.url;
				    	 						var image = Ext.getCmp("avatar");
												image.value =data.path;
												image.setSrc(url);
												picture_window.destroy();
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
				        xhr.send(fd);
					}
				},{
					text:'取消',
					handler:function(){
						picture_window.destroy();
					}
				}]
			})
		}
		picture_window.show();
		
		let constraints = {
	            video: {width: 240, height: 240},
	            audio: true
	        };
		
	        //获得video摄像头区域
	        let video = document.getElementById("video");
	        let promise = navigator.mediaDevices.getUserMedia(constraints);
	        promise.then(function (MediaStream) {
	            video.srcObject = MediaStream;
	            video.play();
	        });

		
    },
     save: function(userData, successFn, failureFn, scope){
     	var url =Ext.util.Format.format(URL_MEMBER_MEMBERCARD_UPDATE, userData.id);
		Ext.Ajax.request({
			url: url,
			jsonData:userData,
			timeout: 300000,
			method:'post',
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