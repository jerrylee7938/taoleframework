/**
 * APP版本管理store
 */
Ext.define('Taole.strategy.marketingType.store.MarketingTypeStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.strategy.marketingType.model.MarketingTypeItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: 'http://api.t.yishengjia1.com/taole-portal-service/service/rest/tk.Dictionary/MARCKETING_SUBJECT_TYPE_CODE/childAllNodes',
        reader: {
            type: 'json',
        }
    }
});
