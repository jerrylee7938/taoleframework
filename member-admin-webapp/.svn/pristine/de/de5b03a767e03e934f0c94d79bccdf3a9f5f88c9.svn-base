/**
 *任务管理store
 */
Ext.define('Taole.reportSheet.accountManager.store.AccountStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.reportSheet.accountManager.model.AccountItem',
    proxy: {
        type: 'ajax',
        api: {
            read:URL_MEMBER_USERBILL_QUERY
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.totalCount'
        }
    }
});
