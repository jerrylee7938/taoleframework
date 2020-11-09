Ext.define('Taole.appManager.appAd.store.CodeStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.appAd.model.CodeItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: 'data/code.json',
        reader: {
            type: 'json'
        }
    }
});