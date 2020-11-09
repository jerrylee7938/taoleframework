/**
 * Created by ChengJ on 2016/4/8.
 */
Ext.define('Taole.store.PageElementStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pageElementStore',
      model: 'Taole.model.PageElementMod',
    proxy: {
        type: 'ajax',
        url: parentPath + "pgt.PageTemplate/5da9ad9d9e94469cafb344e732b7afrb/toLoadElement",
        reader: {
            type: 'json',
            root: 'results'
        }
    }
});