Ext.define("Taole.strategy.referralCode.view.AddOrEditQRcodeWindow", {
    extend: 'Ext.Window',
    alias: 'widget.addOrEditQRcodeWindow',
    layout: 'border',
    modal: true,
    frame: true,
    maximized: true,
    /**
     * @type Function
     *
     */
    afterChooseFn: Ext.emptyFn,
    /**
     * 调用afterChooseFn时的作用域，默认window
     * @type
     */
    scope: window,
    border: 0,
    initComponent: function () {
        this.items = [
            {
                xtype: 'form',
                region: 'center',
                frame: true,
                autoScroll: true,
                defaults: {
                    xtype: 'panel',
                    baseCls: 'x-plain',
                    frame: true,
                    border: 0
                },
                items: [
                    {
                        layout: 'column',
                        style: 'margin-top:5px;',
                        id: "FieldSet_Banner_Img_Container_Main",
                        defaults: {
                            labelAlign: 'right',
                            labelWidth: 80,
                            frame: true,
                            border: 0,
                            xtype: 'panel',
                            baseCls: 'x-plain',
                            width: '98%',
                            style: {
                                marginLeft: '2px'
                            }
                        },
                        items: []
                    }
                ]
            }
        ];
        this.buttons = [
            {
                text: '添加图片',
                action: 'add'
            },
            {
                text: '保存',
                action: 'save'
            },
            {
                text: '取消',
                action: 'cancel'
            }
        ];
        this.callParent(arguments);
    },
    buttonAlign: 'center'
});