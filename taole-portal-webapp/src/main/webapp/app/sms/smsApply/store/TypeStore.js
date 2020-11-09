/**
 * 短信管理store
 */
Ext.define('Taole.sms.smsApply.store.TypeStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.sms.smsApply.model.TypeItem',
    autoLoad:true,
    proxy: {
        type: 'ajax',
        api: {
            read: Ext.util.Format.format(URL_DICTIONARY, '27fb48b44b7a4adf99f25ac6baa2e57e')
        },
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});
