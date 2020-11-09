
Ext.define('Taole.appManager.imei.store.StateStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.imei.model.StateItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        api: {
            read: IMEI_STATE_QUERY
        },
        reader: {
            type: 'json'
        }
    }
});
