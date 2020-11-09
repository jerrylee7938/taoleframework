/**
 * APP版本管理store
 */
Ext.define('Taole.appManager.appAd.store.AppAdPositionStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.appAd.model.AppAdPositionItem',
    proxy: {
        type: 'ajax',
        api: {
            read: APP_ADPOSITION_QUERY
        },
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});
