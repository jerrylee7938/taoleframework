Ext.define('Taole.user.organization.store.ChooseOrganizationStore', {
    extend: 'Ext.data.TreeStore',
    model: 'Taole.user.organization.model.ChooseOrganizationItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: $service.portal + "/us.Organization/ORG_ROOT/treeOne",
        reader : {
			type : 'json'
		}
    },
    listeners : {
        'beforeexpand' : function(node,eOpts){
			if(node.raw.id != 'root'){
				this.proxy.url = $service.portal + "/us.Organization/"+node.raw.id+"/treeOne";
			}
        }
    }
});
