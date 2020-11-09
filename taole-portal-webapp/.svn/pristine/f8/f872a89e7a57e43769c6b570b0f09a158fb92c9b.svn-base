Ext.define("Taole.appManager.appAd.view.BannerImageUploadWindow", {
    extend: 'Ext.Window',
    alias: 'widget.bannerImageUploadWindow',
    layout: 'border',
    title: '图片上传',
    height: 100,
    width: 450,
    modal: true,
    items: {
        xtype: 'form',
        id: 'mBannerImgForm',
        url: APP_ADCONTENT_UPLOAD,
        frame: true,
        defaults: {
            labelAlign: 'right',
            labelWidth: 80,
            width: 400
        },
        baseCls: 'x-plain',
        items: [
            {
                xtype: 'filefield',
                name: 'path',
                fieldLabel: '图片路径路径',
                allowBlank: false,
                blankText: '请选择要上传的图片！',
                buttonText: '选择'
            }
        ]
    },
    buttonAlign: 'center',
    buttons: [
        {
            text: '确定',
            action: 'confirm'
        }, {
            text: '取消',
            action: 'cancel'
        }
    ]
});