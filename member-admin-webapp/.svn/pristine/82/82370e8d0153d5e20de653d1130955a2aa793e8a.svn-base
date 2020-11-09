Ext.define('Taole.common.userCardChoose.store.UserCardChooseStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.common.userCardChoose.model.UserCardChooseItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: URL_MEMBER_MEMBERCARD_TEL_QUERY,
        reader : {
			type : 'json',
			totalProperty: "result.total",
			root: 'result.items'
		}
    }
});