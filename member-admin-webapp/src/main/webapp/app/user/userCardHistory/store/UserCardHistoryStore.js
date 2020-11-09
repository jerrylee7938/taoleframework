/**
 *任务管理store
 */
Ext.define('Taole.user.userCardHistory.store.UserCardHistoryStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.user.userCardHistory.model.UserCardHistoryItem',
    proxy: {
        type: 'ajax',
        api: {
            read:"data/data.json"
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.totalCount'
        }
    }
});
