Ext.define('Taole.appManager.appVer.controller.AppChanlCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.appManager.appVer.view.AppChanlWindow'
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'appChanlWindow'
    },
    {
    	 ref: 'grid',
         selector: 'appChanlWindow>grid'
    }
    ],
    init: function() {
    	if(Taole.appManager.appVer.controller.AppChanlCtrl.isInit)return;

    	Taole.appManager.appVer.controller.AppChanlCtrl.isInit = true;
    	this.control({
    		'appChanlWindow': {
    			afterrender: function(win){
    				var appVerId = win.appVerId;
    				this.getGrid().store.on("beforeload", function(store){
			        	store.proxy.extraParams["appVerId"]=appVerId;
    				}, this);
    			}
    		},
    		'appChanlWindow button[action=cancel]': {
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
    view: function(url){
    	
    }
});