Ext.define('Taole.goods.goodsManager.controller.AddOrEditPositionCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.goods.goodsManager.view.AddOrEditPositionWindow"
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
    	if(Taole.goods.goodsManager.controller.AddOrEditPositionCtrl.isInit)return;

    	Taole.goods.goodsManager.controller.AddOrEditPositionCtrl.isInit = true;
    	
    	this.control({
    		'addOrEditPositionWindow': {
    			afterrender: function(win){
    				this.getdiction(function(data){
    					var homeTypes = data;
    					var homeTypescheck=[];
        				if(homeTypes.length>0){
        					for(var k=0;k<homeTypes.length;k++){
            					var checkdata =homeTypes[k];
        						var typescheck ={
    									checked:false,
    									boxLabel:checkdata.name,
    									name:'homeTypes',
    									inputValue:checkdata.value,
    									id:'homeTypes'+checkdata.value
    									}
        						homeTypescheck.push(typescheck);
            				}
        					Ext.getCmp("homeTypes").add(homeTypescheck);
        				}
    				},this)

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
    			        	upload('image',this);
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
    					this.get(appAdPositionId,function(appAdPosition){
    						if(appAdPosition.code !=1){
    							Ext.Msg.alert(appAdPosition.description);
    						}
    						appAdPosition=appAdPosition.result;
    						var image = Ext.getCmp("image");
							image.setSrc(appAdPosition.image);
							image.value =appAdPosition.image;
							var imageBack = Ext.getCmp("imageBack");
							imageBack.setSrc(appAdPosition.imageBack);
							imageBack.value =appAdPosition.imageBack;
							for(i in appAdPosition){			
								if(form.findField(i)){
									if(i=='beginDate' || i=='endDate'){
										var time = appAdPosition[i];
										var ct = Ext.Date.format(new Date(time),"Y-m-d");	
										form.findField(i).setValue(ct);
									}else{
										form.findField(i).setValue(appAdPosition[i]);
									}
								}
							}
							if(appAdPosition.catalogId =='SPL'){
								var homeTypes= appAdPosition.homeTypes;
								Ext.getCmp('homeTypes').show();
								for(var j=0;j<homeTypes.length;j++){
									var homeTypesvalue =homeTypes[j];
									Ext.getCmp('homeTypes' + homeTypesvalue).setValue(true);
								}
							}
						},null,this);
    				}else{
    					this.getCode(function(data){
    						if(data.code !=1){
    							Ext.Msg.alert(data.description);
    						}
    						var code=data.result;
							form.findField('goodsCode').setValue(code);	
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
    		'addOrEditPositionWindow >form field[name=catalogId]':{//确定
    			change:function(conbox){
    				var val =conbox.value;
    				if(val=='SPL'){
    					Ext.getCmp('homeTypes').show();
    				}else{
    					Ext.getCmp('homeTypes').hide();
    					
    				}
    			}
    		},
    		'addOrEditPositionWindow button[action=confirm]':{//确定
    			click: function(){
    				
    				if(!this.getForm().form.isValid())return;
					var appAdPosition = this.getForm().getValues();
					if(appAdPosition.catalogId=="SPL" && appAdPosition.homeTypes){
						homeTypes =appAdPosition.homeTypes;
						if(!(homeTypes instanceof Array)){
							var homeTypesarry=[];
							homeTypesarry.push(homeTypes)
							appAdPosition.homeTypes=homeTypesarry;
						}
						
					}else{
						appAdPosition.homeTypes=[];
					}
					var main_Image = Ext.getCmp("image");
					appAdPosition.image = main_Image.value;
					var imageBack = Ext.getCmp("imageBack");
					appAdPosition.imageBack = imageBack.value;
					this.save(appAdPosition, function(data){
						if(data.code==1){
							Ext.Msg.alert("提示","保存成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("goodsPanel>grid")[0];
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
    getdiction:function(successFn,scope){
    	var url= Ext.util.Format.format(URL_DICTIONARY_MEMBER,'af00bc5686ef4fa4915c967f71f83f58');
    	Ext.Ajax.request({
			url: url,
			timeout: 300000,
			success: function(response){
				var data = Ext.decode(response.responseText);
					successFn.call(this, data);
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取详情失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
     get: function(appAdPosition, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(URL_MEMBER_GOODS_RETRIEVE, appAdPosition);
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
    	var url= URL_MEMBER_GOODS_CODE;
    	Ext.Ajax.request({
			url: url,
			timeout: 300000,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','获取商品编码失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取商品编码失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
     save: function(appAdPosition, successFn, failureFn, scope){
     	var url = URL_MEMBER_GOODS_CREATE;
     	if(appAdPosition.goodsId){
     		url = Ext.util.Format.format(URL_MEMBER_GOODS_UPDATE, appAdPosition.goodsId);
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