Ext.define('Taole.common.storeChoose.store.StoreChooseStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.common.storeChoose.model.StoreChooseItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: URL_MEMBER_STORE_QUERY,
        reader : {
			type : 'json',
			totalProperty: "result.total",
			root: 'result.items'
		}
    }
});