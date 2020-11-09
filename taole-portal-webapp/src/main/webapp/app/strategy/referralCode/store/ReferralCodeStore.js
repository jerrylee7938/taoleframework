/**
 * APP营销管理store
 */
Ext.define('Taole.strategy.referralCode.store.ReferralCodeStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.strategy.referralCode.model.ReferralCodeItem',
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
