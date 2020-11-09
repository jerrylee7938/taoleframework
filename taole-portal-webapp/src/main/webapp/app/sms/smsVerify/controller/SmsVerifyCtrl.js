Ext.define('Taole.sms.smsVerify.controller.SmsVerifyCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.sms.smsVerify.view.SmsVerifyPanel'
    ],
    refs: [
    {
    	ref: 'form',
    	selector: 'smsVerifyPanel>form'
    },        
    {
    	 ref: 'grid',
         selector: 'smsVerifyPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'smsVerifyPanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
    					appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'smsVerifyPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
    			}
    		},
    		'smsVerifyPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		}
    	});
    }
});