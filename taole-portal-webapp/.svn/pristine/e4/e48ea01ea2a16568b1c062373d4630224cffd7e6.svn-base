/**
 * APP版本管理store
 */
Ext.define('Taole.appManager.appInfo.store.AppInfoStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.appInfo.model.AppInfoItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: APP_INFO_QUERY+"?type=App",
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});
