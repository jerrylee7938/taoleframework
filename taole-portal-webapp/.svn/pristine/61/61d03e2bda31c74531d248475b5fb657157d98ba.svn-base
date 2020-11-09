Ext.define('Taole.dataPrivilege.dataPrivilegeManager.store.DataPrivilegeStore', {
    extend: 'Ext.data.Store',
    model: 'Taole.dataPrivilege.dataPrivilegeManager.model.DataPrivilegeItem',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        api: {
            read: DATA_PRIVILEGE_QUERY
        },
        reader: {
            type: 'json',
            root: 'result_data.items',
            totalProperty: 'result_data.total'
        }
    }
});