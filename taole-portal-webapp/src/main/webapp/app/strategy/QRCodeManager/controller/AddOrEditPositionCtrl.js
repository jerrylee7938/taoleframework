Ext.define('Taole.strategy.QRCodeManager.controller.AddOrEditPositionCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.strategy.QRCodeManager.view.AddOrEditPositionWindow"
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
    	if(Taole.strategy.QRCodeManager.controller.AddOrEditPositionCtrl.isInit)return;

    	Taole.strategy.QRCodeManager.controller.AddOrEditPositionCtrl.isInit = true;
    	
    	this.control({
    		'addOrEditPositionWindow': {
    			afterrender: function(win){
    				var appAdPositionId = win.appAdPositionId;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				
    				if(isView){			
    					var mainTitle= Ext.getCmp("mainTitle");
    					mainTitle.setReadOnly(true);
    					mainTitle.setFieldStyle("background-color:#EEEEE0;background-image: none;");
    					
    					var tpye= Ext.getCmp("tpye");
    					tpye.setReadOnly(true);
    					tpye.setFieldStyle("background-color:#EEEEE0;background-image: none;");
    					
    					var people= Ext.getCmp("people");
    					people.setReadOnly(true);
    					people.setFieldStyle("background-color:#EEEEE0;background-image: none;");
    					
    					Ext.getCmp("updownQRCp").show();
    					Ext.getCmp("updownQRCpImg").show();
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
    		'addOrEditPositionWindow>form':{
    			afterrender: function(win){
    				var addBtn= Ext.getCmp("updownQR");
    				addBtn.el.createChild({
    		            cls: Ext.baseCSSPrefix + 'form-file-input',
    		            tag: 'input',
    		            type: 'file',
    		            style : 'width:100%;font-size:12px',
    		            size: 1
    		        }).on('change', function(){
    		        	var URL = $service.taole_portal_service +'/us.AppAdContent/collection/upload';
    		        	var xhr = new XMLHttpRequest(); // 初始化XMLHttpRequest
    		        	xhr.onreadystatechange=state_Change;
    		        	xhr.open('post', URL, true);
    		        	var fd = new FormData(); // 这里很关键，初始化一个FormData，并将File文件发送到后台
    		    		fd.append("file", this.dom.files[0]);
    		        	xhr.send(fd);   		
    		        	function state_Change(){
    		    			if (xhr.readyState==4){// 4 = "loaded"
    		    			  if (xhr.status==200){// 200 = "OK"
    		    			   	 	var data = Ext.decode(xhr.responseText);
    		    					if(getResp(data)){		
    		 						var url = data.result_data.img;
    		 						Ext.getCmp("avatarImg").getEl().dom.src=url;
    		 						Ext.getCmp("avatar").setValue(url);				
    		    					}
    		    			   } else{
    		    			   	 	resultData = xhr.statusText;
    		    			   	 Ext.Msg.alert('提示','上传失败！！'+resultData);
    		    			   	 	
    		    			    }
    		    			 }
    		    		}
    		        });	
    			
    			

    				
    			

    				var addBtn= Ext.getCmp("updownImg");
    				addBtn.el.createChild({
    		            cls: Ext.baseCSSPrefix + 'form-file-input',
    		            tag: 'input',
    		            type: 'file',
    		            style : 'width:100%;font-size:12px',
    		            size: 1
    		        }).on('change', function(){
    		        	var URL = $service.taole_portal_service +'/us.AppAdContent/collection/upload';
    		        	var xhr = new XMLHttpRequest(); // 初始化XMLHttpRequest
    		        	xhr.onreadystatechange=state_Change;
    		        	xhr.open('post', URL, true);
    		        	var fd = new FormData(); // 这里很关键，初始化一个FormData，并将File文件发送到后台
    		    		fd.append("file", this.dom.files[0]);
    		        	xhr.send(fd);   		
    		        	function state_Change(){
    		    			if (xhr.readyState==4){// 4 = "loaded"
    		    			  if (xhr.status==200){// 200 = "OK"
    		    			   	 	var data = Ext.decode(xhr.responseText);
    		    					if(getResp(data)){		
    		 						var url = data.result_data.img;
    		 						Ext.getCmp("avatarImg").getEl().dom.src=url;
    		 						Ext.getCmp("avatar").setValue(url);				
    		    					}
    		    			   } else{
    		    			   	 	resultData = xhr.statusText;
    		    			   	 Ext.Msg.alert('提示','上传失败！！'+resultData);
    		    			   	 	
    		    			    }
    		    			 }
    		    		}
    		        });	
    			
    			
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
    		},
    		'addOrEditPositionWindow>form field[name=title]':{
				focus: function(){
		            //获取焦点
					Ext.create("Taole.strategy.referralCode.controller.MarketingNameCtrl").init();
			    	Ext.create("widget.marketingNameWindow",{
			    		scope: this,
			    		afterChooseFn: function(){
			    			
			    		},
			    		appAdPositionId: '',
			    		isView: '',
			    		isRetrieve: ''
		    	}).show();
		        }	
		},
		'addOrEditPositionWindow>form field[name=people]':{
			focus: function(){
	            //获取焦点
				Ext.create("Taole.strategy.referralCode.controller.MarketingPeopleCtrl").init();
		    	Ext.create("widget.marketingPeopleWindow",{
		    		scope: this,
		    		afterChooseFn: function(){
		    			
		    		},
		    		appAdPositionId: '',
		    		isView: '',
		    		isRetrieve: ''
	    	}).show();
	        }
	},
	'addOrEditPositionWindow>form field[name=type]':{
		focus: function(){
            //获取焦点
			Ext.create("Taole.strategy.referralCode.controller.MarketingChanneCtrl").init();
	    	Ext.create("widget.marketingChanneWindow",{
	    		scope: this,
	    		afterChooseFn: function(){
	    			
	    		},
	    		appAdPositionId: '',
	    		isView: '',
	    		isRetrieve: ''
    	}).show();
        }
	},
	'addOrEditPositionWindow>form field[name=wxNews]':{
		change: function(){
			var wxNews = Ext.getCmp('wxNews').getValue();
			console.log(wxNews);
			if(wxNews==1 || wxNews==2){
				Ext.getCmp('newsTitle').hide();
				Ext.getCmp('newsCN').hide();
				Ext.getCmp('newsLogo').hide();
				Ext.getCmp('updownImg').hide();
				Ext.getCmp('newsImg').hide();
			}else if(wxNews==3){
				Ext.getCmp('newsTitle').hide();
				Ext.getCmp('newsCN').hide();
				Ext.getCmp('newsLogo').show();
				Ext.getCmp('updownImg').show();
				Ext.getCmp('newsImg').show();
			}else if(wxNews==4){
				Ext.getCmp('newsTitle').show();
				Ext.getCmp('newsCN').show();
				Ext.getCmp('newsLogo').show();
				Ext.getCmp('updownImg').show();
				Ext.getCmp('newsImg').show();
			}else if(wxNews==5){
				Ext.getCmp('newsTitle').hide();
				Ext.getCmp('newsCN').show();
				Ext.getCmp('newsLogo').hide();
				Ext.getCmp('updownImg').hide();
				Ext.getCmp('newsImg').hide();
			}
			
		}
	},
	'addOrEditPositionWindow>form button[action=updownBg]':{//取消
		
		afterrender: function(){
			var addBtn= Ext.getCmp("updownBg");
			addBtn.el.createChild({
	            cls: Ext.baseCSSPrefix + 'form-file-input',
	            tag: 'input',
	            type: 'file',
	            style : 'width:100%;font-size:12px',
	            size: 1
	        }).on('change', function(){
	        	
	        	var URL = $service.taole_portal_service +'/us.AppAdContent/collection/upload';
	        	var xhr = new XMLHttpRequest(); // 初始化XMLHttpRequest
	        	xhr.onreadystatechange=state_Change;
	        	xhr.open('post', URL, true);
	        	var fd = new FormData(); // 这里很关键，初始化一个FormData，并将File文件发送到后台
	    		fd.append("file", this.dom.files[0]);
	        	xhr.send(fd);   		
	        	function state_Change(){
	    			if (xhr.readyState==4){// 4 = "loaded"
	    			  if (xhr.status==200){// 200 = "OK"
	    			   	 	var data = Ext.decode(xhr.responseText);
	    					if(getResp(data)){		
	 						var url = data.result_data.img;
	 						Ext.getCmp("avatarImg").getEl().dom.src=url;
	 						Ext.getCmp("avatar").setValue(url);				
	    					}
	    			   } else{
	    			   	 	resultData = xhr.statusText;
	    			   	 Ext.Msg.alert('提示','上传失败！！'+resultData);
	    			   	 	
	    			    }
	    			 }
	    		}
	        });	
		}
	},
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
    },
    updownImg:function(){
    	console.log('213')
    }
});