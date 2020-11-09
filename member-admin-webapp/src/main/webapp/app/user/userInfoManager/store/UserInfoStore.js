/**
 *任务管理store
 */
Ext.define('Taole.user.userInfoManager.store.UserInfoStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.user.userInfoManager.model.UserInfoItem',
    proxy: {
        type: 'ajax',
        api: {
            read:URL_MEMBER_MEMBERCARD_QUERY
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.total'
        }
    }
});
