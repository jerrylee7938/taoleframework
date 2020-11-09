/**
 * 微信平台账号管理store
 */
Ext.define('Taole.wechat.wechatAccount.store.WechatAccountStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.wechat.wechatAccount.model.WechatAccountItem',
    proxy: {
        type: 'ajax',
        api: {
            read: APP_ADPOSITION_QUERY
        },
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});
