Ext.define('Taole.appManager.appVer.store.AppStatusStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.appManager.appVer.model.SystemTypeItem',
//    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: '',
        reader: {
            type: 'json'
        }
    }
});