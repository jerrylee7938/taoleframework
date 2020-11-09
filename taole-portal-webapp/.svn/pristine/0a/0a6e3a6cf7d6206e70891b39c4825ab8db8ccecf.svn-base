/**
 * APP版本管理store
 */
Ext.define('Taole.appManager.appVer.store.AppVerStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.appVer.model.AppVerItem',
    proxy: {
        type: 'ajax',
        api: {
            read: APP_VER_QUERY
        },
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});
