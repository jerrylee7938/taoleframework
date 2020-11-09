Ext.define('Taole.appManager.appVer.controller.DownLoadCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.appManager.appVer.view.DownLoadWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'downLoadWindow'
    },
    {
    	 ref: 'form',
         selector: 'downLoadWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.appManager.appVer.controller.DownLoadCtrl.isInit)return;

    	Taole.appManager.appVer.controller.DownLoadCtrl.isInit = true;
    	
    	this.control({
    		'downLoadWindow': {
    			afterrender: function(win){
    				var url = win.fileUrl;
    				var chnlDownLoadUrl = win.chnlDownLoadUrl;
    				if(chnlDownLoadUrl){
    					var qrcode = new QRCode('imageForm-innerCt', { 
						  text: chnlDownLoadUrl, 
						  width: 256, 
						  height: 256, 
						  colorDark : '#000000', 
						  colorLight : '#ffffff'
						});
    				}else{
    					var changingImage = Ext.create('Ext.Img', {
						    src: url
						});
						
						this.getForm().add(changingImage);
    				}
    			}
    		},
    		'downLoadWindow button[action=cancel]':{//关闭
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    }
});