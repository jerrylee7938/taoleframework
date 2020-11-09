Ext.define('Taole.view.Preview', {
    extend: 'Ext.window.Window',
    constructor: function (url, myHeight, myWidth) {
        if (url.indexOf('?') == -1) {
            url = url + "?ch=" + new Date().getTime()
        }
        this.html = '<iframe frameborder=0 width="100%" height="100%" allowtransparency="true" scrolling=auto src=' + url  + '></iframe>'
        this.callParent(arguments);
    },
    alias: 'widget.myPreview',
    title: '界面预览',
    autoShow: true,
    layout: 'fit',
    modal: true,
    closable:false,
    border: 0,
    maximized: true,
    frame: false,
    buttonAlign: 'center',
    buttons: [{
        text: '保存',
        xtype: 'button',
        action: 'add-perview-sub',
        width: 120
    }, {
        text: '取消',
        xtype: 'button',
        action: 'cancel-perview-sub',
        style: 'margin-left:10px;',
        width: 120,
        scope: this
    }]
});