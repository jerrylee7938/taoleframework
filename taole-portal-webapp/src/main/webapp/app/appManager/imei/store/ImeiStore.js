
Ext.define('Taole.appManager.imei.store.ImeiStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.imei.model.ImeiItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        api: {
            read: IMEI_QUERY
        },
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});
