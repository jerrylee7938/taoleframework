Ext.define('Taole.common.goodsAllChoose.store.GoodsChooseStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.common.goodsAllChoose.model.GoodsChooseItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: URL_MEMBER_GOODS_QUERY,
        reader : {
			type : 'json',
			totalProperty: "result.total",
			root: 'result.items'
		}
    }
});