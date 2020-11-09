Ext.define('Taole.appManager.appVer.store.AppChanlStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.appVer.model.AppChanlItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: APP_CHANL_QUERY,
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});
