Ext.define('Taole.appManager.appVer.controller.DetailCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.appManager.appVer.view.DetailWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'detailWindow'
    },
    {
    	 ref: 'form',
         selector: 'detailWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.appManager.appVer.controller.DetailCtrl.isInit)return;

    	Taole.appManager.appVer.controller.DetailCtrl.isInit = true;
    	
    	this.control({
    		'detailWindow': {
    			afterrender: function(win){
    				var appVerId = win.appVerId;
    				this.get(appVerId,function(appVer){
						var form = this.getForm().form;
						appVer.publishTime = Ext.Date.format(new Date(appVer.publishTime), "Y-m-d");
						for(i in appVer){
							if(form.findField(i)){
								form.findField(i).setValue(appVer[i]);
							}
						}
						var fileName = appVer.fileName;
						var updatePath = appVer.updatePath;
						if(appVer.systemType == "ios"){
							if(fileName.indexOf(".plist")>-1){
								fileName = fileName.replace("plist", "jpg");
								updatePath = updatePath.replace("plist/"+appVer.fileName, "image/"+fileName);
							}else{
								fileName = fileName.replace("ipa", "jpg");
								updatePath = updatePath.replace(appVer.fileName, "image/"+fileName);
							}
						}else{
							fileName = fileName.replace("apk", "jpg");
							updatePath = updatePath.replace(appVer.fileName, "image/"+fileName);
						}
						
						Ext.getCmp("QRCode").setSrc(updatePath);
					},null,this);
    			}
    		},
    		'detailWindow button[action=cancel]':{//关闭
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
    }
});