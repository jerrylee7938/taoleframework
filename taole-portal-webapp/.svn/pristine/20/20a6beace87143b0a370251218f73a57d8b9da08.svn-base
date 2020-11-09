/**
 * 短信管理store
 */
Ext.define('Taole.sms.smsApply.store.StatusStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.sms.smsApply.model.StatusItem',
    autoLoad:true,
    proxy: {
        type: 'ajax',
        api: {
            read: Ext.util.Format.format(URL_DICTIONARY, 'dcd52693d4354f45952d22dedfcd1c19')
        },
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});
