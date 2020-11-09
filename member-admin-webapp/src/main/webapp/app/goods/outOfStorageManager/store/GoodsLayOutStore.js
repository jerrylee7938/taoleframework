/**
 * 商品出库管理store
 */
Ext.define('Taole.goods.outOfStorageManager.store.GoodsLayOutStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.goods.outOfStorageManager.model.GoodsLayOutItem',
    proxy: {}
});
