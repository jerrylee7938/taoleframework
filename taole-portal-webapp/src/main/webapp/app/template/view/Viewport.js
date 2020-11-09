/*Ext.define('Taole.template.view.Viewport', {
 extend: 'Ext.window.Window',
 alias: 'widget.viewmain',
 title: 'Hello',
 layout: 'fit',
 autoShow:true,
 items: {  // Let's put an empty grid in just to illustrate fit layout
 xtype: 'grid',
 border: false,
 columns: [{header: 'World'}],                 // 仅仅用来显示一个头部。没有数据，
 store: Ext.create('Ext.data.ArrayStore', {}) // 一个假的空的数据存储
 }

 });*/

//创建window
Ext.define('Taole.template.view.Viewport', {
    extend: 'Ext.window.Window',
    id: "myWin",
    alias: 'widget.viewmain',
    title: "示例窗口",
    layout: "fit",
    items: [
        {
            xtype: "elementDetail"

        }
    ],
    buttons: [
        {
            xtype: "button", text: "确定", handler: function () {
            this.up("window").close();
        }
        },
        {
            xtype: "button", text: "取消", handler: function () {
            this.up("window").close();
        }
        }
    ]
});
