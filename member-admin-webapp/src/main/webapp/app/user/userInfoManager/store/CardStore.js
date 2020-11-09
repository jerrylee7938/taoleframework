/**
 *管理store
 */
Ext.define('Taole.user.userInfoManager.store.CardStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.user.userInfoManager.model.CardItem',
    proxy: {
        type: 'ajax',
        api: {
            read:"data/data2.json"
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.totalCount'
        }
    }
});
