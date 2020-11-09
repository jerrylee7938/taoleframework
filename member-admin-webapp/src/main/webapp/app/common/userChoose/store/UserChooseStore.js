Ext.define('Taole.common.userChoose.store.UserChooseStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.common.userChoose.model.UserChooseItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: URL_MEMBER_USER_QUERY_OPERATOR,
        reader : {
			type : 'json',
			totalProperty: "result.totalCount",
			root: 'result.items'
		}
    }
});