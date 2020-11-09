/**
 *任务管理store
 */
Ext.define('Taole.user.userInfoManager.store.ConsumeListStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.user.userInfoManager.model.ConsumeListItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        api: {
            read:URL_DOCTOR_QUERY
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.count'
        }
    }
});
