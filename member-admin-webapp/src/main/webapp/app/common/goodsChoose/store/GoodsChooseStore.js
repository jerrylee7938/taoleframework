Ext.define('Taole.common.goodsChoose.store.GoodsChooseStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.common.goodsChoose.model.GoodsChooseItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: URL_MEMBER_SHOPSET_FORSALE,
        reader : {
			type : 'json',
			totalProperty: "result.total",
			root: 'result.items'
		}
    }
});