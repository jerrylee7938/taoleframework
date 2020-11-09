Ext.define('Taole.view.PreviewToUse', {
    extend: 'Ext.window.Window',
    constructor: function (url, myHeight, myWidth) {
        if (url.indexOf('?') == -1) {
            url = url + "?ch=" + new Date().getTime()
        }
        this.html = '<iframe frameborder=0 width="100%" height="100%" allowtransparency="true" scrolling=auto src=' + url + '></iframe>'
        this.callParent(arguments);
    },
    alias: 'widget.mPreviewToUse',
    title: '界面预览',
    autoShow: true,
    layout: 'fit',    //设置布局模式为fit，能让frame自适应窗体大小
    modal: true,    //打开遮罩层
    border: 0,    //无边框
    closable: false,
    maximized: true,
    frame: false   //去除窗体的panel框架
    ,
    buttonAlign: 'center',
    buttons: [{
        text: '应用',
        xtype: 'button',
        action: 'add-sub',
        width: 120
    }, {
        text: '取消',
        xtype: 'button',
        action: 'cancel-sub',
        style: 'margin-left:10px;',
        width: 120,
        scope: this
    }]
});