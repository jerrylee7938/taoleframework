/**
 *任务管理store
 */
Ext.define('Taole.reportSheet.accountManager.store.AccountDetailStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.reportSheet.accountManager.model.AccountDetailItem',
    proxy: {
        type: 'ajax',
        api: {
            read:''
        },
        reader: {
            type: 'json',
            root: 'result.goodsList',
            totalProperty: 'result.total'
        }
    }
});
