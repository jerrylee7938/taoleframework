Ext.define('Taole.user.userInfoManager.controller.ConsumeListCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.user.userInfoManager.view.ConsumeListWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'consumeListWindow'
    },
    {
    	 ref: 'form',
         selector: 'consumeListWindow>form'
    },
    {
    	 ref: 'grid',
         selector: 'consumeListWindow>grid'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.user.userInfoManager.controller.ConsumeListCtrl.isInit)return;

    	Taole.user.userInfoManager.controller.ConsumeListCtrl.isInit = true;
    	
    	this.control({
    		'consumeListWindow': {
    			afterrender: function(win){  				
    			}
    		},
    		'consumeListWindow>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
    			}
    		},
    		'consumeListWindow>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'consumeListWindow>grid': {
    			afterrender: function(gridpanel){
    				var win = this.getWindow();
    				var jobId= win.appAdPositionId;
    				gridpanel.store.on("beforeload", function(store){
    					appendParam(this.getForm(), store);
    					store.proxy.extraParams["jobId"] =jobId;
    				}, this);
    			}
    		},
    		'consumeListWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    }
});