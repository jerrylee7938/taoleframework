Ext.define('Taole.appManager.appVer.store.ChannelTypeStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.appVer.model.ChannelTypeItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: APP_VER_CHANNEL_TYPE,
        reader: {
            type: 'json'
        }
    }
});