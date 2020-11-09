/**
 *日报表管理store
 */
Ext.define('Taole.reportSheet.reportSheetManager.store.ReportSheetStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.reportSheet.reportSheetManager.model.ReportSheetItem',
    proxy: {
        type: 'ajax',
        api: {
            read:URL_MEMBER_REPORT_QUERY+'?circleType=day'
        },
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'total'
        }
    }
});
