/**
 * 门店管理store
 */
Ext.define('Taole.store.storeManager.store.StoreStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.store.storeManager.model.StoreItem',
    proxy: {
        type: 'ajax',
        api: {
            read:URL_MEMBER_STORE_QUERY
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.total'
        }
    }
});
