Ext.define('Taole.appManager.appVer.controller.AppDownLoadCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.appManager.appVer.view.AppDownLoadPanel'
    ],
    refs: [
    {
    	 ref: 'grid',
         selector: 'appDownLoadPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'appDownLoadPanel': {
    			afterrender: function(panel){
    				var dataAry = new Array();
    				dataAry.push({
    					"appInfoName": "沃贷宝理财",
    					"systemType":"安卓"
    				});
    				this.getGrid().store.add(dataAry);
    			}
    		}
    	});
    }
});