Ext.define("Taole.view.PreviewImg", {
    extend: 'Ext.window.Window',
    alias: 'widget.mPreviewImg',
    layout: 'fit',
    title: '图片查看',
    autoShow: true,
    modal: true,
    frame: true,
    width: 450,
    closable:false,
    height: 300,
    imgUrl: undefined,
    constrain: true,
    minWidth: 450,
    minHeight: 300,
    buttonAlign: 'center',
    buttons: [{
        text: '关闭',
        xtype: 'button',
        action: 'cancel-sub',
        style: 'margin-left:10px;',
        width: 80
    }],
    /*    frame: false,  //去除窗体的panel框架*/
    initComponent: function () {
        this.items = [
            {
                xtype: 'box',
                id: "mimg_box",
                autoEl: {
                    tag: 'img',
                    src: 'myphoto.gif'
                }
            }
        ]
        this.callParent(arguments);
    }
});