/**
 * 短信管理store
 */
Ext.define('Taole.sms.smsApply.store.SmsApplyStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.sms.smsApply.model.SmsApplyItem',
    autoLoad:true,
    proxy: {
        type: 'ajax',
        api: {
            read: SMS_APPLY_QUERY
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.total'
        }
    }
});
