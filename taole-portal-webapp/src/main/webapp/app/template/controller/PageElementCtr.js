var parentPath = $service.portal + "/"
Ext.define('Taole.controller.PageElementCtr', {
    extend: 'Ext.app.Controller',
    stores: ['PageElementStore', 'PageTemplatesStore'],
    views: [
        'ElementDetail', 'Preview', 'PreviewToUse', 'PreviewImg', 'PreviewDown'
    ],

    refs: [
        {
            ref: 'mform',
            selector: 'elementDetail'
        }, {
            ref: 'mwindow',
            selector: 'mPreviewToUse'
        }

    ],
    init: function () {
        this.control({
            'elementDetail': {
                beforerender: function (form1) {
                    //加载数据
                    loadDataById(parentPath + "pgt.PageTemplate/default/toLoadElement", undefined, function (jsondata) {
                        form1.previewurl = jsondata.perview_url;
                        form1.getForm().findField('tId').setValue(jsondata.tempId);
                        form1.tId = jsondata.tempId;
                        Ext.getCmp("helpimg").body.update("<img style='border: 0; margin: 0;padding: 0' width='100%' src='" + jsondata.help_template_back + "'/>"
                        );
                        for (i in jsondata.data) {
                            if (form1.getForm().findField(i)) {
                                var mfiled = form1.getForm().findField(i);
                                mfiled.setValue(jsondata.data[i].value);
                                if (jsondata.data[i].isEdit == contants.isEdit.No) {
                                    mfiled.setReadOnly(true);
                                    mfiled.setFieldStyle("background-color:" + contants.disableClor.one + ";background-image: none;");
                                }
                            }
                        }
                        
                        if(jsondata.tempId == '5da9ad9d9e94469cafb344e232b7afrb'){
	                    	form1.getForm().findField('p_template_login_type').hide();
	                    }else{
	                    	form1.getForm().findField('p_template_login_type').show();
	                    }

                    })


                }

            }, 'mPreviewImg': {
                beforerender: function (window) {
                    // 检索panel中所有子节点id为myCt的组件
                    var mbox = window.query('#mimg_box')[0];
                    mbox.autoEl.src = window.imgUrl;
                }
            },
            'elementDetail #templatecomb': {
                select: function (combo, record, opts) {
                    var form1 = this.getMform();
                    loadDataById(parentPath + "pgt.PageTemplate/" + combo.getValue() + "/toLoadElement", undefined, function (jsondata) {
                        form1.tId = combo.getValue();
                        form1.previewurl = jsondata.perview_url;
                        form1.getForm().findField('tId').setValue(jsondata.tempId);
                        Ext.getCmp("helpimg").body.update("<img style='border: 0; margin: 0;padding: 0' width='100%' src='" + jsondata.help_template_back + "'/>"
                        );
                        for (i in jsondata.data) {
                            if (form1.getForm().findField(i)) {
                                var mfiled = form1.getForm().findField(i);
                                mfiled.setValue(jsondata.data[i].value);
                                if (jsondata.data[i].isEdit == contants.isEdit.No) {
                                    mfiled.setReadOnly(true);
                                    mfiled.setFieldStyle("background-color:" + contants.disableClor.one + ";background-image: none;");
                                }
                            }
                        }

                    });
                    
                    if(combo.getValue() == '5da9ad9d9e94469cafb344e232b7afrb'){
                    	form1.getForm().findField('p_template_login_type').hide();
                    }else{
                    	form1.getForm().findField('p_template_login_type').show();
                    }
                },
                render: function (commp, opt) {
                    Ext.Ajax.request({
                        url: parentPath + 'pgt.PageTemplate/default/getTemplate',
                        params: {
                            id: 1
                        },
                        success: function (response) {
                            var text = response.responseText;
                            var jsonObject = eval("(" + text + ")")
                            commp.value = jsonObject.id;
                        }
                    });
                    //commp.value='5da9ad9d9e94469cafb344e732b7afrb';
                    //console.log(commp)
                }
            },
//            'elementDetail button[action=seave-sub]': {
//                click: function () {
//                    var parentform = this.getMform();
//                    var form = parentform.getForm();
//                    form.url = parentPath + "pgt.PageTemplate/collection/savePageElement";
//                    if (form.isValid()) {
//                        form.submit({
//                            success: function (form, action) {
//                                parentform.previewurl = action.result.data;
//                                Ext.Msg.show({
//                                    title: '提示',
//                                    msg: "操作成功！",
//                                    buttons: Ext.Msg.OK,
//                                    fn: function callback(btn, text) {
//                                        if (btn == "ok") {
//                                            var form1 = parentform;
//                                            loadDataById(parentPath + "pgt.PageTemplate/default/toLoadElement", undefined, function (jsondata) {
//                                                form1.previewurl = jsondata.perview_url;
//                                                form1.getForm().findField('tId').setValue(jsondata.tempId);
//                                                Ext.getCmp("helpimg").body.update("<img style='border: 0; margin: 0;padding: 0' width='100%' src='" + jsondata.help_template_back + "'/>"
//                                                );
//                                                for (i in jsondata.data) {
//                                                    if (form1.getForm().findField(i)) {
//                                                        var mfiled = form1.getForm().findField(i);
//                                                        mfiled.setValue(jsondata.data[i].value);
//                                                        if (jsondata.data[i].isEdit == contants.isEdit.No) {
//                                                            mfiled.setReadOnly(true);
//                                                            mfiled.setFieldStyle("background-color:" + contants.disableClor.one + ";background-image: none;");
//                                                        }
//                                                    }
//                                                }
//                                            })
//
//                                        }
//                                    },
//                                    icon: Ext.Msg.INFO
//                                });
//                            },
//                            waitMsg: '保存数据中，请稍等...',
//                            failure: function (form, action) {
//                                Ext.Msg.show({
//                                    title: '消息',
//                                    msg: action.result.message,
//                                    buttons: Ext.Msg.OK,
//                                    icon: Ext.Msg.ERROR
//                                });
//                            }
//                        });
//                    }
//                }
//            },
            'elementDetail button[action=apply-sub]': {//即刻应用
                click: function () {
                    var parentform = this.getMform();
                    var form = parentform.getForm();
                    form.url = parentPath + "pgt.PageTemplate/collection/perview";
                    form.submit({
                        success: function (form, resp) {
                            var respText = eval("(" + resp.response.responseText + ")")
                            if (respText.success) {
                                Ext.widget('mPreviewToUse', parentform.previewurl, 100, 100)
                            } else {
                                Ext.widget('mPreviewToUse', respText.path + "?message=" + respText.error, 300, 400)
                            }
                        },
                        waitMsg: '请求执行中，请稍等...',
                        failure: function (form, resp) {
                        	Ext.widget('mPreviewToUse', parentform.previewurl, 100, 100);
//                            var respText = eval("(" + resp.response.responseText + ")")
//                            Ext.widget('mPreviewToUse', respText.path + "?message=" + encodeURI(respText.error), 300, 400)
                        }
                    });
                }
            },
            'elementDetail button[action=perview-sub]': {//预览
                click: function () {
                    var parentform = this.getMform();
                    var form = parentform.getForm();
                    form.url = parentPath + "pgt.PageTemplate/collection/perview";
                    if (form.isValid()) {
                        form.submit({
                            success: function (form, resp) {
                                var respText = eval("(" + resp.response.responseText + ")")
                                if (respText.success) {
                                    Ext.widget('myPreview', parentform.previewurl, 100, 100)
                                } else {
                                    Ext.widget('myPreview', respText.path + "?message=" + respText.error, 300, 400)
                                }
                            },
                            waitMsg: '请求执行中，请稍等...',
                            failure: function (form, resp) {
                            	Ext.widget('myPreview', parentform.previewurl, 100, 100);
//                                var respText = eval("(" + resp.response.responseText + ")")
//                                Ext.widget('myPreview', respText.path + "?message=" + encodeURI(respText.error), 300, 400)
                            }
                        });
                    }
                }
            },
//            'elementDetail button[action=ref-sub]': {
//                click: function () {
//                    var form1 = this.getMform();
//                    loadDataById(parentPath + "pgt.PageTemplate/default/toLoadElement", undefined, function (jsondata) {
//                        form1.previewurl = jsondata.perview_url;
//                        form1.getForm().findField('tId').setValue(jsondata.tempId);
//                        Ext.getCmp("helpimg").body.update("<img style='border: 0; margin: 0;padding: 0' width='100%' src='" + jsondata.help_template_back + "'/>"
//                        );
//                        for (i in jsondata.data) {
//                            if (form1.getForm().findField(i)) {
//                                var mfiled = form1.getForm().findField(i);
//                                mfiled.setValue(jsondata.data[i].value);
//                                if (jsondata.data[i].isEdit == contants.isEdit.No) {
//                                    mfiled.setReadOnly(true);
//                                    mfiled.setFieldStyle("background-color:" + contants.disableClor.one + ";background-image: none;");
//                                }
//                            }
//                        }
//                    })
//                }
//            },
            'elementDetail button[action=down-sub]': {//下载
                click: function (btn) {
                    var parentform = this.getMform();
                    var form = parentform.getForm();
                    form.url = parentPath + "pgt.PageTemplate/collection/perview";
                    if (form.isValid()) {
                        form.submit({
                            success: function (form, resp) {
                                var respText = eval("(" + resp.response.responseText + ")")
                                if (respText.success) {
                                    Ext.widget('mPreviewDown', parentform.previewurl, 100, 100)
                                } else {
                                    Ext.widget('mPreviewDown', respText.path + "?message=" + respText.error, 300, 400)
                                }
                            },
                            waitMsg: '正在向服务器请求您要下载的登录界面，请稍后...',
                            failure: function (form, resp) {
                            	Ext.widget('mPreviewDown', parentform.previewurl, 100, 100);
//                                var respText = eval("(" + resp.response.responseText + ")")
//                                Ext.widget('mPreviewDown', respText.path + "?message=" + encodeURI(respText.error), 300, 400)
                            }
                        });
                    }
                }
            },
            "mPreviewToUse button[action=add-sub]": {//即刻应用--应用
                click: function (btn) {
                    mwondow = this.getMwindow()
                    var parentform = this.getMform();
                    var form = parentform.getForm();
                    form.url = parentPath + "pgt.PageTemplate/collection/savePageElement";
                    if (form.isValid()) {
                        form.submit({
                            success: function (form, action) {
                                loadDataById(parentPath + "pgt.PageTemplate/" + parentform.tId + "/useCopy", undefined, function (jsondata) {
                                    Ext.Msg.show({
                                        title: '提示',
                                        msg: "操作成功！",
                                        buttons: Ext.Msg.OK,
                                        fn: function callback(btn, text) {
                                            if (btn == "ok") {
                                                mwondow.close();
                                            }
                                        },
                                        icon: Ext.Msg.INFO
                                    });
                                })
                            },
                            waitMsg: '应用中，请稍后...',
                            failure: function (form, action) {
                            	loadDataById(parentPath + "pgt.PageTemplate/" + parentform.tId + "/useCopy", undefined, function (jsondata) {
                                    Ext.Msg.show({
                                        title: '提示',
                                        msg: "操作成功！",
                                        buttons: Ext.Msg.OK,
                                        fn: function callback(btn, text) {
                                            if (btn == "ok") {
                                                mwondow.close();
                                            }
                                        },
                                        icon: Ext.Msg.INFO
                                    });
                                });
//                                Ext.Msg.show({
//                                    title: '消息',
//                                    msg: action.result.message,
//                                    buttons: Ext.Msg.OK,
//                                    icon: Ext.Msg.ERROR
//                                });
                            }
                        });
                    }


                }
            },
            "mPreviewToUse button[action=cancel-sub]": {//即刻应用--取消
                click: function (btn) {
                    this.cancelFn();
                    this.getMwindow().close();
                }

            },
            "myPreview button[action=add-perview-sub]": {//预览--保存
                click: function (btn) {
                    mwondow = btn.up('window');
                    var parentform = this.getMform();
                    var form = parentform.getForm();
                    form.url = parentPath + "pgt.PageTemplate/collection/savePageElement";
                    if (form.isValid()) {
                        form.submit({
                            success: function (form, action) {
                                Ext.Msg.show({
                                    title: '提示',
                                    msg: "保存成功！",
                                    buttons: Ext.Msg.OK,
                                    fn: function callback(btn, text) {
                                        if (btn == "ok") {
                                            mwondow.close();
                                        }
                                    },
                                    icon: Ext.Msg.INFO
                                });
                            },
                            waitMsg: '应用中，请稍后...',
                            failure: function (form, action) {
                            	Ext.Msg.show({
                                    title: '提示',
                                    msg: "保存成功！",
                                    buttons: Ext.Msg.OK,
                                    fn: function callback(btn, text) {
                                        if (btn == "ok") {
                                            mwondow.close();
                                        }
                                    },
                                    icon: Ext.Msg.INFO
                                });
//                                Ext.Msg.show({
//                                    title: '消息',
//                                    msg: action.result.message,
//                                    buttons: Ext.Msg.OK,
//                                    icon: Ext.Msg.ERROR
//                                });
                            }
                        });
                    }


                }
            },
            "mPreviewDown button[action=down-perview-sub]": {//下载--下载
                click: function (btn) {
                    mwondow = btn.up('window');
                    var parentform = this.getMform();
                    var form = parentform.getForm();
                    var templateType = form.findField("downloadType").getValue();
                    form.url = parentPath + "pgt.PageTemplate/collection/savePageElement";
                    Ext.Msg.confirm('操作提示', '下载需要保存当前预览版面，您是否继续？',
                        function (btn) {
                            if (btn == 'yes') {
                                if (form.isValid()) {
                                    form.submit({
                                        success: function (form, action) {
                                        	window.location.href = parentPath + "pgt.PageTemplate/" + parentform.getForm().findField('tId').getValue() + "/getFile?templateType="+templateType;
                                            mwondow.close();
                                        },
                                        waitMsg: '下载请求中，请稍后...',
                                        failure: function (form, action) {
                                        	window.location.href = parentPath + "pgt.PageTemplate/" + parentform.getForm().findField('tId').getValue() + "/getFile?templateType="+templateType;
                                            mwondow.close();
//                                            Ext.Msg.show({
//                                                title: '消息',
//                                                msg: action.result.message,
//                                                buttons: Ext.Msg.OK,
//                                                icon: Ext.Msg.ERROR
//                                            });
                                        }
                                    });
                                }
                            } else {
                                mwondow.close();
                            }
                        }, this);
                }
            },
            "myPreview button[action=cancel-perview-sub]": {
                click: function (btn) {
                    this.cancelFn()
                    btn.up('window').close();
                }
            },

            "mPreviewImg button[action=cancel-sub]": {
                click: function (btn) {
                    btn.up('window').close();
                }
            },

            "mPreviewDown button[action=cancel-sub]": {
                click: function (btn) {
                    this.cancelFn();
                    btn.up('window').close();
                }

            }
        });
        /**
         * 取消动作
         */
        this.cancelFn = function () {
            url = parentPath + "pgt.PageTemplate/collection/cancelPageElement";
            loadDataById(url, undefined, function (jsondata) {

            })
        }
    }
});

