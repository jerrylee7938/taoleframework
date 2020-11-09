
Ext.define('Taole.appManager.imei.store.RoleStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.imei.model.RoleItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        api: {
            read: IMEI_ROLE_QUERY
        },
        reader: {
            type: 'json'
        }
    }
});
