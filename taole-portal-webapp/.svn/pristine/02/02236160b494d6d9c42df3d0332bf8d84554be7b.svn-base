/**
 * APP营销管理store
 */
Ext.define('Taole.strategy.marketingSubject.store.MarketingSubjectStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.strategy.marketingSubject.model.MarketingSubjectItem',
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
