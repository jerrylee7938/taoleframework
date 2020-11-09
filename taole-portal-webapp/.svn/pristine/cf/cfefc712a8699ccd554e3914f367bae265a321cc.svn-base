/**
 * 二维码管理store
 */
Ext.define('Taole.strategy.QRCodeManager.store.QRCodeManagerStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.strategy.QRCodeManager.model.QRCodeManagerItem',
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
