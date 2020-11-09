/**
 * 商品管理store
 */
Ext.define('Taole.goods.goodsManager.store.GoodsStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.goods.goodsManager.model.GoodsItem',
    proxy: {
        type: 'ajax',
        api: {
            read:URL_MEMBER_GOODS_QUERY
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.total'
        }
    }
});
