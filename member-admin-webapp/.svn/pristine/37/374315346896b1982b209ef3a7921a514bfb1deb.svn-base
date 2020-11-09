/**
 *月报表管理store
 */
Ext.define('Taole.reportSheet.reportMonthManager.store.ReportMonthStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.reportSheet.reportMonthManager.model.ReportMonthItem',
    proxy: {
        type: 'ajax',
        api: {
            read:URL_MEMBER_REPORT_QUERY+'?circleType=month'
        },
        reader: {
            type: 'json',
            root: 'result.items',
            totalProperty: 'result.total'
        }
    }
});
