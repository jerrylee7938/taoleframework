/**
 * APP营销管理store
 */
Ext.define('Taole.strategy.referralCode.store.MarketingNameStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.strategy.referralCode.model.MarketingNameItem',
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
