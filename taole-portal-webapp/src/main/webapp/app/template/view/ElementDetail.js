/**
 * Created by ChengJ on 2016/4/8.
 */
window.openImgWindowFn = function (url) {
    var mainUrl = $ctx + "/image/factLogin/" + url;
    Ext.widget("mPreviewImg", {imgUrl: mainUrl});
}

window.downloadTemplate = function(type){
	Ext.getCmp("downloadType").setValue(type);
	Ext.getCmp("down-sub").fireEvent("click");
}
Ext.define('Taole.view.ElementDetail', {
    extend: 'Ext.FormPanel',
    previewurl: '',
    trackResetOnLoad: true,
    loadformurl: '',
    alias: 'widget.elementDetail',
    id: 'TempalteMyTempalteForm',
    region: 'center',
    url: parentPath + "pgt.PageTemplate/collection/savePageElement",
//    layout: 'form',
    frame: true,
    enctype: "multipart/form-data",
    fileUpload: true,
    method: "post",
    defaultType: 'textfield',
    autoScroll: true,
    shadow: true,
    tId: null,
    mystore: [],
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'top',
        defaults: {minWidth: 100},
        items: [
            {
                xtype: "combobox",
                editable: false,// 是否允许输入
                id: 'templatecomb',
                emptyText: '请选择模板',
                labelWidth: 60,
                fieldLabel: '<label style="color: #ffa561">选择模板</label>',
                store: 'PageTemplatesStore',
                queryMode: 'local',
                displayField: 'name',
                valueField: 'id'
            }
        ]
    }],
    /* layout: {
     type: 'border',
     padding: 0
     },*/
    initComponent: function () {
        this.title = '模板管理';
        this.defaults = {
            frame: true,
            labelAlign: 'right',
            baseCls: "x-plain"
        },
            this.items = [

                {
                    xtype: "panel",
                    id: "helpimg",
                    html: "",
                    height: 650,
                    anchor: '99.9%'
                },
                {
                    xtype: 'filefield',
                    name: 'p_template_back_main',
                    id: 'p_template_back_main',
                    fieldLabel: 'BODY背景图片',
                    msgTarget: 'side',
                    anchor: '100%',
                    buttonText: '选择背景图片'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: '已上传文件',
                    name: 'p_template_back_main_str',
                    renderer: function (value, metaData, record) {
                        return "<a style='text-decoration: none'  href=\"javascript:openImgWindowFn('" + value + "');\">" + value + "</a>";
                    }
                },
                {
                    xtype: 'filefield',
                    name: 'p_template_back_logo',
                    id: "p_template_back_logo",
                    fieldLabel: 'LOGO',
                    msgTarget: 'side',
                    anchor: '100%',
                    buttonText: '选择logo图片'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: '已上传文件',
                    name: 'p_template_back_logo_str',
                    renderer: function (value, metaData, record) {
                        return "<a style='text-decoration: none'  href=\"javascript:openImgWindowFn('" + value + "');\">" + value + "</a>";
                    }
                },
                {
                    xtype: 'filefield',
                    name: 'p_template_back_mini',
                    id: 'p_template_back_mini',
                    fieldLabel: '背景主图',
                    msgTarget: 'side',
                    anchor: '100%',
                    buttonText: '选择背景主图'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: '已上传文件',
                    name: 'p_template_back_mini_str',
                    renderer: function (value, metaData, record) {
                        return "<a style='text-decoration: none'  href=\"javascript:openImgWindowFn('" + value + "');\">" + value + "</a>";
                    }
                },
                {
                    xtype: 'filefield',
                    name: 'p_template_back_login',
                    id: 'p_template_back_login',
                    fieldLabel: '登录按钮图片',
                    msgTarget: 'side',
                    anchor: '100%',
                    buttonText: '选择登录按钮',
                    allowBlank: true
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: '已上传文件',
                    name: 'p_template_back_login_str',
                    buttonCfg: {
                        iconCls: 'upload-icon'
                    },
                    renderer: function (value, metaData, record) {
                        return "<a style='text-decoration: none'  href=\"javascript:openImgWindowFn('" + value + "');\">" + value + "</a>";
                    }
                },

                {
                    xtype: 'textfield',
                    name: 'p_template_text_slogan',
                    id: 'p_template_text_slogan',
                    fieldLabel: 'SLOGAN描述',
                    anchor: '100%',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    name: 'p_template_main_title',
                    id: 'p_template_main_title',
                    fieldLabel: '首页title',
                    anchor: '100%',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    name: 'p_template_text_name',
                    id: 'p_template_text_name',
                    fieldLabel: '首页系统名',
                    anchor: '100%',
                    allowBlank: true
                }, {
                    xtype: 'textfield',
                    name: 'p_template_url_register',
                    id: 'p_template_url_register',
                    anchor: '100%',
                    fieldLabel: '注册URL',
                    allowBlank: true
                }, {
                    xtype: 'textfield',
                    name: 'p_template_url_findpsd',
                    id: 'p_template_url_findpsd',
                    fieldLabel: '找回密码URL',
                    anchor: '100%',
                    allowBlank: true
                }, {
                    xtype: 'textfield',
                    name: 'p_template_url_login',
                    id: 'p_template_url_login',
                    fieldLabel: '登录URL',
                    anchor: '100%',
                    allowBlank: false
                }, {
                    xtype: 'combo',
                    name: 'p_template_login_type',
                    id: 'p_template_login_type',
                    fieldLabel: '登录验证方式',
                    anchor: '100%',
                    editable: false,// 是否允许输入
                    store: getDicStore('670e2bd1f8c6490a881bbc2d767a9660'),
                    displayField: 'name',
                	valueField: 'value'
                }, {
                    xtype: 'textfield',
                    name: 'p_template_text_bottpm_des',
                    id: 'p_template_text_bottpm_des',
                    anchor: '100%',
                    fieldLabel: '首页底部信息'
                }, {
                    xtype: 'textfield',
                    name: 'p_template_text_bottpm_copyright',
                    id: 'p_template_text_bottpm_copyright',
                    anchor: '100%',
                    fieldLabel: '版权信息'
                }, {
                    xtype: 'hidden',
                    name: 'tId',
                    value: ""
                }, {
                    xtype: 'hidden',
                    name: 'downloadType',
                    id: 'downloadType',
                    value: ""
                }];
        this.buttons = [/*{
         text: '重置',
         handler: function () {
         this.up('form').getForm().reset();
         }
         }, {
         text: '刷新',
         action: 'ref-sub'
         },*/ /*{
            text: '保存',
            formBind: true,
            disabled: true,
            action: 'seave-sub'
        },*/ {
            text: '即刻应用',
            formBind: true,
            disabled: true,
            action: 'apply-sub'
        }, {
            text: '预览',
            formBind: true,
            disabled: true,
            action: 'perview-sub'
        }, {
            xtype: 'button',
            action: 'down-sub',
            id: 'down-sub',
            hidden: true
        }, {
            text: '下载',
            xtype: 'button',
            formBind: true,
            disabled: true,
            menu: [
				{text: '非CAS版', handler: function(){downloadTemplate('portal');}},
				{text: 'CAS版', handler: function(){downloadTemplate('cas');}}
            ]
        }];
        this.callParent(arguments);
    }
});
