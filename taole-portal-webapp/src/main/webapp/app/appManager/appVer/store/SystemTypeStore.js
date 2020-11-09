Ext.define('Taole.appManager.appVer.store.SystemTypeStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.appVer.model.SystemTypeItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: APP_VER_SYSTEM_TYPE,
        reader: {
            type: 'json'
        }
    }
});