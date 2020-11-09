/**
 * 各店面库存管理store
 */
Ext.define('Taole.goods.stockManager.store.StockStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.goods.stockManager.model.StockItem',
    proxy: {
        type: 'ajax',
        api: {
            read:'data/data.json'
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.totalCount'
        }
    }
});
