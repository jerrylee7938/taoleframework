/**
 * 短信验证码查询
 */
Ext.define('Taole.sms.smsVerify.store.SmsVerifyStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.sms.smsVerify.model.SmsVerifyItem',
    autoLoad:true,
    proxy: {
        type: 'ajax',
        api: {
            read: SMS_VERIFY_QUERY
        },
        reader: {
            type: 'json',
            root: 'result_data.items',
            totalProperty: 'result_data.total'
        }
    }
});
