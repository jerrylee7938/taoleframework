/**
 * 库存管理store
 */
Ext.define('Taole.goods.outOfStorageManager.store.OutOfStorageStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.goods.outOfStorageManager.model.OutOfStorageItem',
    proxy: {
        type: 'ajax',
        api: {
            read:URL_MEMBER_GOODSBILLDETAIL_QUERY
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.total'
        }
    }
});
