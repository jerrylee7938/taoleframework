/**
 * 卡管理store
 */
Ext.define('Taole.card.cardManager.store.CardStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.card.cardManager.model.CardItem',
    proxy: {
        type: 'ajax',
        api: {
            read:URL_MEMBER_CARD_QUERY
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.total'
        }
    }
});
