
Ext.define('Taole.marketing.store.ChannelST', {
    extend: 'Ext.data.Store',
    model: 'Taole.marketing.model.ChannelItem',
    proxy: {
        type: 'ajax',
        url: URL_USER_CHANNEL_QUERY,
        reader: {
            type: 'json',
            totalProperty: 'total',
            root: 'items'
        }
    }
});
