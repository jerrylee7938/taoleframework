Ext.define('Taole.user.organization.controller.ChooseOrganizationWinCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.user.organization.view.ChooseOrganizationWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'chooseOrganizationWindow'
    },
    {
    	 ref: 'treepanel',
         selector: 'chooseOrganizationWindow>treepanel'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.user.organization.controller.ChooseOrganizationWinCtrl.isInit)return;

    	Taole.user.organization.controller.ChooseOrganizationWinCtrl.isInit = true;
    	
    	this.control({
    		'chooseOrganizationWindow': {
    			afterrender: function(win){
    			}
    		},
    		'chooseOrganizationWindow button[action=confirm]':{//确定
    			click: function(){
    				var treepanel = this.getWindow().down("treepanel");
    				var selNodes = treepanel.getSelectionModel().getSelection();
    				var win = this.getWindow();
    				if(win.afterChooseFn.call(win.scope, selNodes[0].data)!==false){//回调函数没有返回false
						win.close();
					}
    			}
    		},
    		'chooseOrganizationWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    }
});