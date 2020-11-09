Ext.define('Taole.store.storeManager.controller.AddOrEditPositionCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.store.storeManager.view.AddOrEditPositionWindow"
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
    	if(Taole.store.storeManager.controller.AddOrEditPositionCtrl.isInit)return;

    	Taole.store.storeManager.controller.AddOrEditPositionCtrl.isInit = true;
    	
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
    			        	upload('shopLogo',this);
    			        });
    					var addBtn= Ext.getCmp("t1234");
    					addBtn.el.createChild({
    			            cls: Ext.baseCSSPrefix + 'form-file-input',
    			            tag: 'input',
    			            type: 'file',
    			            style : 'width:100%;font-size:12px',
    			            size: 1
    			        }).on('change', function(){
    			        	upload('shopDescImage',this);
    			        });
    					var addBtn= Ext.getCmp("t12345");
    					addBtn.el.createChild({
    			            cls: Ext.baseCSSPrefix + 'form-file-input',
    			            tag: 'input',
    			            type: 'file',
    			            style : 'width:100%;font-size:12px',
    			            size: 1
    			        }).on('change', function(){
    			        	upload('shopListImage',this);
    			        });
    				}
    				if(isView){
    					win.aloadOne = true;
    					this.get(appAdPositionId,function(appAdPosition){
    						if(appAdPosition.code !=1){
    							Ext.Msg.alert(appAdPosition.description);
    						}
    						appAdPosition=appAdPosition.result;
    						var shopLogo = Ext.getCmp("shopLogo");
    						shopLogo.setSrc(appAdPosition.shopLogo);
    						shopLogo.value =appAdPosition.shopLogo;
    						var shopDescImage = Ext.getCmp("shopDescImage");
    						shopDescImage.setSrc(appAdPosition.shopDescImage);
    						shopDescImage.value =appAdPosition.shopDescImage;
    						var shopListImage = Ext.getCmp("shopListImage");
    						shopListImage.setSrc(appAdPosition.shopListImage);
    						shopListImage.value =appAdPosition.shopListImage;
    						
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
    		'addOrEditPositionWindow>form field[name=province]':{//省
    			change: function(combox){
    				var val = combox.getValue();
    				var city = this.getForm().form.findField("city");
    				var aloadOne = this.getWindow().aloadOne;
					if(aloadOne){
						this.getWindow().aloadOne=false;
					}else{
						city.setValue();
					}
    				var store = city.getStore();
    				store.proxy.url = Ext.util.Format.format(URL_DICTIONARY, val);
					store.load();
					
					
    			}
    		},
    		'addOrEditPositionWindow>form button[action=achieve]':{ //获取经纬度
    			click: function(){
					    var map = new BMap.Map("container");
					    var localSearch = new BMap.LocalSearch(map);
					    map.clearOverlays();//清空原来的标注
					    var form = this.getForm().form;
					    var keyword = form.findField('address').getValue();
					    localSearch.setSearchCompleteCallback(function (searchResult) {
					        var poi = searchResult.getPoi(0);
					
					        form.findField("lng").setValue(poi.point.lng);
					        form.findField("lat").setValue(poi.point.lat);
					    });
					    localSearch.search(keyword);
					
				
    			}
    		},
    		'addOrEditPositionWindow>form field[name=city]':{//城市
    			focus: function(combox){
    				var province = this.getForm().form.findField("province").getValue();
    				console.log(province)
    				if(province){
    				}else{
    					Ext.Msg.alert("提示", "请先选择省份！");
    				}
    						
    			}
    		},
    		'addOrEditPositionWindow button[action=confirm]':{//确定
    			click: function(){
    				
    				if(!this.getForm().form.isValid())return;
					var appAdPosition = this.getForm().getValues();
					var shopLogo = Ext.getCmp("shopLogo");
					appAdPosition.shopLogo = shopLogo.value;
					var shopDescImage = Ext.getCmp("shopDescImage");
					appAdPosition.shopDescImage = shopDescImage.value;
					var shopListImage = Ext.getCmp("shopListImage");
					appAdPosition.shopListImage = shopListImage.value;
					this.save(appAdPosition, function(data){
					
						if(data.code==1){
							Ext.Msg.alert("提示","保存成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("storePanel>grid")[0];
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
     get: function(shopId, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(URL_MEMBER_STORE_RETRIEVE, shopId);
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
     	var url = URL_MEMBER_STORE_CREATE;
     	if(appAdPosition.shopId){
     		url = Ext.util.Format.format(URL_MEMBER_STORE_UPDATE, appAdPosition.shopId);
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
    },
});

function upload(name,_this){
	var URL = URL_MEMBER_FILE_UPDATE+"?type=1";
	var xhr = new XMLHttpRequest(); // 初始化XMLHttpRequest
	xhr.onreadystatechange=state_Change;
	xhr.open('post', URL, true);
	var fd = new FormData(); // 这里很关键，初始化一个FormData，并将File文件发送到后台
	fd.append("file", _this.dom.files[0]);
	fd.append("type", 1);
	xhr.send(fd);   		
	function state_Change(){
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