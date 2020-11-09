/**
 *充值审核管理store
 */
Ext.define('Taole.user.tradeeExamine.store.TradeeExamineStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.user.tradeeExamine.model.TradeeExamineItem',
    proxy: {
        type: 'ajax',
        api: {
            read:URL_MEMBER_USERBILLAPPLY_QUERY
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.totalCount'
        }
    }
});