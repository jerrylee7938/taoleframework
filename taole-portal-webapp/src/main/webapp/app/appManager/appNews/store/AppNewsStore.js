
Ext.define('Taole.appManager.appNews.store.AppNewsStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.appNews.model.AppNewsItem',
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
