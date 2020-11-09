/**
 * Created by ChengJ on 2016/4/11.
 */
Ext.define( 'Taole.store.PageTemplatesStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pageTemplatesStore',
    model: 'Taole.model.PageTemplatesMod',
    storeId:'mypageTemplatesStore',
    proxy: {
        type: 'ajax',
        url: parentPath + "pgt.PageTemplate/sda/loadAllTemplate",
        reader: {
            type: 'json',
            root: 'results'
        }
    }
    , autoLoad: true
});
