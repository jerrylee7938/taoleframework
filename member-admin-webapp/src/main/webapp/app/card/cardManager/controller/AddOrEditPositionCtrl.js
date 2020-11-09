Ext.define('Taole.card.cardManager.controller.AddOrEditPositionCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.card.cardManager.view.AddOrEditPositionWindow"
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
    	if(Taole.card.cardManager.controller.AddOrEditPositionCtrl.isInit)return;

    	Taole.card.cardManager.controller.AddOrEditPositionCtrl.isInit = true;
    	
    	this.control({
    		'addOrEditPositionWindow': {
    			afterrender: function(win){
    				var appAdPositionId = win.appAdPositionId;
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
    			        	
    			        	upload('cardImage',this);
    			        	
    			        });
    					
    					var addBtn= Ext.getCmp("bt123");
    					addBtn.el.createChild({
    			            cls: Ext.baseCSSPrefix + 'form-file-input',
    			            tag: 'input',
    			            type: 'file',
    			            style : 'width:100%;font-size:12px',
    			            size: 1
    			        }).on('change', function(){
    			        	upload('imageBack',this);
    			        	
    			        });
    				}
    				
    				if(isView){
    					win.first =true;
    					this.get(appAdPositionId,function(appAdPosition){
    						if(appAdPosition.code !=1){
    							Ext.Msg.alert(appAdPosition.description);
    						}
    						appAdPosition=appAdPosition.result;
    						var image = Ext.getCmp("cardImage");
							image.setSrc(appAdPosition.cardImage);
							image.value =appAdPosition.cardImage;
							var imageBack = Ext.getCmp("imageBack");
							imageBack.setSrc(appAdPosition.imageBack);
							imageBack.value =appAdPosition.imageBack;
							for(i in appAdPosition){
								if(form.findField(i)){
									form.findField(i).setValue(appAdPosition[i]);
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
					var main_Image = Ext.getCmp("cardImage");
					var imageBack = Ext.getCmp("imageBack");
					appAdPosition.cardImage = main_Image.value;
					appAdPosition.imageBack = imageBack.value;
					this.save(appAdPosition, function(data){
					
						if(data.code==1){
							Ext.Msg.alert("提示","保存成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("cardPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							},this)
						}else{
							Ext.Msg.alert('提示','保存失败!<br/>'+data.description);
						}
					}, null, this);
    			}
    		},
    		'addOrEditPositionWindow>form field[name=cardType]':{//卡类型
    			change: function(combox){ 
    				var val = combox.getValue();
    				var form = this.getForm().form;
    				if(val == 'JCK'){
    					form.findField('chargeType').disable();
    					form.findField('chargeType').setValue();
    					form.findField('totalNum').enable();
    					form.findField('periodOfValidity').enable();
    				}else{
    					form.findField('chargeType').enable();
    					form.findField('totalNum').setValue();
    					form.findField('totalNum').disable();
    					form.findField('periodOfValidity').setValue();
    					form.findField('periodOfValidity').disable();
    				}
    			
    					
					
    				

    			}
    		},
//    		'addOrEditPositionWindow>form field[name=status]':{//卡状态
//    			change: function(combox){ 
//    				var val = combox.getValue();
//    				var form = this.getForm().form;
//    				var day = Ext.Date.format(new Date(), 'Y-m-d');
//    				if(val == 1){
//    					form.findField('startDay').setValue(day);
//    				}else if(val == 0){
//    					form.findField('endDay').setValue(day);
//    				}
//
//    			}
//    		},
    		'addOrEditPositionWindow>form field[name=totalNum]':{//次数
    			blur: function(combox){ 
    				var val = combox.getValue();
    				var form = this.getForm().form;
    				var money = form.findField('money').getValue();
    				var text = money+'元-'+val+'次卡';
    				form.findField('cardName').setValue(text);
    				
    			}
    		},
    		'addOrEditPositionWindow>form field[name=chargeType]':{//卡有效期类型
    			blur: function(combox){ 
    				var val = combox.getRawValue();
    				var form = this.getForm().form;
    				var money = form.findField('money').getValue();
    				var text = money+'元-'+val;
    				form.findField('cardName').setValue(text);
    			}
    		},
    		'addOrEditPositionWindow>form field[name=money]':{//金额
    			blur: function(combox){ 
    				var val = combox.getValue();
    				var form = this.getForm().form;
    				var cardType = form.findField('cardType').getValue();
    				var first = this.getWindow().first;
    				var text = '';
    				if(cardType == 'JCK'){
    					var num =form.findField('totalNum').getValue();
    					text = val +'元-'+num+'次卡';
    				}else{
    					var chargeType =form.findField('chargeType').getRawValue();
    					text = val +'元-'+chargeType;
    				}
    				form.findField('cardName').setValue(text);
					
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
     get: function(cardId, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(URL_MEMBER_CARD_RETRIEVE, cardId);
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
    getCode: function(successFn, failureFn, scope){
    	var url= URL_MEMBER_CARD_CODE;
    	Ext.Ajax.request({
			url: url,
			timeout: 300000,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','获取编码失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取编码失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
     save: function(appAdPosition, successFn, failureFn, scope){
     	var url = URL_MEMBER_CARD_CREATE;
     	if(appAdPosition.cardId){
     		url = Ext.util.Format.format(URL_MEMBER_CARD_UPDATE, appAdPosition.cardId);
     	}
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
function upload(name,_this){
	var URL = URL_MEMBER_FILE_UPDATE+"?type=1";
	showWaitMsg();
	var xhr = new XMLHttpRequest(); // 初始化XMLHttpRequest
	xhr.onreadystatechange=state_Change;
	xhr.open('post', URL, true);
	var fd = new FormData(); // 这里很关键，初始化一个FormData，并将File文件发送到后台
	fd.append("file", _this.dom.files[0]);
	fd.append("type", 1);
	xhr.send(fd);   
	function state_Change(){
		hideWaitMsg();
		if (xhr.readyState==4){// 4 = "loaded"
		  if (xhr.status==200){// 200 = "OK"
		   	 	var data = Ext.decode(xhr.responseText);
				if(getResp(data)){
					if(data.success==true){
						var url = data.url;
 						var image = Ext.getCmp(name);
						image.value =url;
						image.setSrc(url);
 						 Ext.Msg.alert('提示','上传成功！！');
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
};