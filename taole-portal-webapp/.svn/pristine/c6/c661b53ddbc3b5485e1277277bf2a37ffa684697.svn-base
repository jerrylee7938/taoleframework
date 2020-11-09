
Ext.define('Taole.wechat.wechatNews.store.WechatNewsConfigureStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.wechat.wechatNews.model.WechatNewsConfigureItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        api: {
            read: IMEI_QUERY
        },
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});
