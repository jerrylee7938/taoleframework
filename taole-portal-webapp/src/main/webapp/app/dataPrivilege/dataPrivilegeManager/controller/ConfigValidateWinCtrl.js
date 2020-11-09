Ext.define('Taole.dataPrivilege.dataPrivilegeManager.controller.ConfigValidateWinCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.dataPrivilege.dataPrivilegeManager.view.ConfigValidateWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'configValidateWindow'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.dataPrivilege.dataPrivilegeManager.controller.ConfigValidateWinCtrl.isInit)return;

    	Taole.dataPrivilege.dataPrivilegeManager.controller.ConfigValidateWinCtrl.isInit = true;
    	
    	this.control({
    		'configValidateWindow': {
    			afterrender: function(win){
    				var dataPrivilegeData = win.dataPrivilegeData;
    				var fields = [{name : 'id'},{name: 'text', mapping: 'name'},{name :'leaf',type : 'boolean'}];
					var _tree = Ext.create("Ext.tree.Panel",{
						region: 'center',
						store:Ext.create('Ext.data.TreeStore', {//树的数据源store
							fields:fields,
							root: {
						        expanded: true,
						        children:dataPrivilegeData
						    }
					    }),
					    rootVisible: false
					});
					_tree.expandAll();
					win.add(_tree);
    			}
    		},
    		'configValidateWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    }
});