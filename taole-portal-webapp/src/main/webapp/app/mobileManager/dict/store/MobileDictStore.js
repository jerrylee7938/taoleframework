
Ext.define('Taole.mobileManager.dict.store.MobileDictStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.mobileManager.dict.model.MobileDictItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: Mobile_QUERY,
        reader: {
            type: 'json',
            totalProperty: 'result_object.total',
            root: 'result_object.items'
        }
    }
});
