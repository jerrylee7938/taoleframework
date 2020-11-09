/**
 * Created by ChengJ on 2016/4/9.
 */
var namespace = "Taole";
/*重写Panle load*/
Ext.override(Ext.Panel, {
    loadData: function (conf) {
        var ct = this;
        //遍历该表单下所有的子项控件，并且为它赋值
        var setByName = function (container, data) {
            var items = container.items;
            if (items != null) {
                for (var i = 0; i < items.getCount(); i++) {
                    var comp = items.get(i);
                    if (comp.items) {
                        setByName(comp, data);
                        continue;
                    }
                    //判断组件的类型，并且根据组件的名称进行json数据的自动匹配
                    var xtype = comp.getXType();
                    try {
                        if (xtype == 'textfield' || xtype == 'textarea' || xtype == 'radio' || xtype == 'checkbox'
                            || xtype == 'datefield' || xtype == 'combo' || xtype == 'hidden' || xtype == 'datetimefield'
                            || xtype == 'htmleditor'
                            || xtype == 'label' || xtype == 'fckeditor'
                        ) {
                            var name = comp.getName();
                            if (name) {
                                if (conf.preName) {
                                    name = name.substring(conf.preName.length + 1);
                                }
                                var val = eval('data.' + name);
                                if (val) {
                                    comp.setValue(val);
                                }
                            }
                        }
                    } catch (e) {
                        //alert(e);
                    }
                }
            }
        };
        if (!ct.loadMask) {
            ct.loadMask = new Ext.LoadMask(Ext.getBody());
            ct.loadMask.show();
        }
        Ext.Ajax.request({
            method: 'POST',
            url: conf.url,
            scope: this,
            success: function (response, options) {
                var json = Ext.util.JSON.decode(response.responseText);
                var data = null;
                if (conf.root) {
                    data = eval('json.' + conf.root);
                } else {
                    data = json;
                }
                setByName(ct, data);
                if (ct.loadMask) {
                    ct.loadMask.hide();
                    ct.loadMask = null;
                }
                if (conf.success) {
                    conf.success.call(ct, response, options);
                }
            },//end of success
            failure: function (response, options) {
                if (ct.loadMask) {
                    ct.loadMask.hide();
                    ct.loadMask = null;
                }
                if (conf.failure) {
                    conf.failure.call(ct, response, options);
                }
            }
        });
    }
});

/**
 * 上色禁用函数
 * @param collection filed集合
 * @param filter 过滤字段
 */
window.meachFun = function (collection, filter) {
    collection.each(function (field) {
        field.setReadOnly(true);
        console.log(field.getName())
        if (field.getName()) {
            try {
                if (field.getName().indexOf('displayfield') < 0)
                    field.setFieldStyle("background-color:#EEEEE0;background-image: none;");
            } catch (e) {
            }
        } else {
            try {
                field.setFieldStyle("background-color:#EEEEE0;background-image: none;");
            } catch (e) {
            }
        }
        if (filter) {
            if (filter instanceof Array)
                for (o = 0; o < filter.length; o++) {
                    if (field.name == filter[o]) {
                        field.setReadOnly(false);
                        field.setFieldStyle('background-color: #FFFFFF;background-image: none;');
                    }
                }
            else {
                if (field.name == filter) {
                    field.setReadOnly(false);
                    field.setFieldStyle('background-color: #FFFFFF;background-image: none;');
                }
            }

        }

    });
}
/**
 * 全局加加载数据公用方法
 * @param url
 * @param id
 * @param successFn
 * @param failureFn
 * @param scope
 */
var loadDataById = function (url, id, successFn, failureFn, scope) {
    UtilFun.showWaitMsg();
    if (id)
        url = Ext.util.Format.format(url, id);
    Ext.Ajax.request({
        url: url,
        success: function (response) {
            console.log(response)
            var data = Ext.decode(response.responseText);
            UtilFun.hideWaitMsg()
            if (getResp(data)) {
                successFn.call(this, data);
            } else {
                Ext.Msg.alert('提示', '获取数据失败!<br/>' + data.code + ":" + data.desc, failureFn, this);
            }
        },
        failure: function () {
            UtilFun.hideWaitMsg()
            Ext.Msg.show({
                title: '提示',
                msg: "获取数据失败，请联系管理员！",
                buttons: Ext.Msg.OK,
                fn: function callback(btn, text) {
                    if (btn == "yes") {
                        failureFn
                    }
                },
                icon: Ext.Msg.ERROR
            });
        },
        scope: scope ? scope : this
    });
}
/***************************************start 》仿类声明函数*************************/
/**
 * 打开窗口类
 * @des 所有打开公共窗口或选择器都可再次调用
 * @type {{openTrackingUserDetailFun: OpenFun.openTrackingUserDetailFun, openTrackingLoanDetailFun: OpenFun.openTrackingLoanDetailFun, openTrackingCompanyAssesDetailFun: OpenFun.openTrackingCompanyAssesDetailFun}}
 */
var
    OpenFun = {
        openTrackingContentFun: function (value, title) {
            contetwindow = Ext.create('Ext.window.Window', {
                autoShow: true,
                title: title ? title : '内容详情',
                bodyStyle: ' background: #FFFFFF;',
                autoShow: true,
                frame: true,
                width: 300,
                modal: true,
                height: 300,
                constrain: true,
                minWidth: 300,
                minHeight: 300,
                items: [
                    {
                        xtype: 'displayfield',
                        grow: true,
                        value: value
                    }
                ]
            });
            contetwindow.show()
        },
        /**
         * 打开用户详情窗口
         * @param value
         * @param khmc
         */
        openTrackingUserDetailFun: function (type, value, khmc) {
            var muser = {id: value, name: khmc}
            if (type && type == credit_code.customer.person.base_info.CUSTOM_TYPE.PERSON) {
                SysUserManager.PersonUser.Detail(muser)
            } else {
                SysUserManager.CompanyUser.Detail(muser)
            }
        }
        ,

        /**
         * 打开贷款信息详情窗口
         * @param value
         */
        openTrackingLoanDetailFun: function (value) {
            SysLoanManager.LoanDetail(value);
        }
        ,


        /**
         * 打开企业财务评估
         * @param value
         */
        openTrackingCompanyAssesDetailFun: function (value) {
            SysUserManager.CompanyUser.AssessPGForm(value + "的评估信息");
            var mforms = Ext.ComponentQuery.query('#CommonAssessPGForm243642742fdsd>form');
            for (var i = 0; i < mforms.length; i++) {
                var collection = mforms[i].getForm().getFields()
                window.meachFun(collection);
                contetwindow.show();
            }
        }
        ,
        /**
         * 公共字典store
         * @param code
         * @returns {Ext.data.Store}
         */
        commonStore: function (code) {
            var murl = Ext.util.Format.format(credit_Interface.common.URL_DICT, code)
            var store = Ext.create('Ext.data.Store', {
                autoLoad: false,
                fields: ['id', 'name'],
                proxy: {
                    url: murl,
                    type: 'ajax',
                    reader: {
                        type: 'json',
                        root: 'result_object.items'
                    }
                }
            });
            store.load();
            return store;
        }
    }

/**
 * 普通工具类函数
 * @des 在应用中用到的公共函数都可在此调用
 * @type {{isNotEmpty: UtilFun.isNotEmpty, isCardNo: UtilFun.isCardNo, discriCard: UtilFun.discriCard}}
 */
var UtilFun = {
    /**
     * 为空判定
     * @param text
     * @returns {boolean}
     */
    isNotEmpty: function (text) {
        var reg = /^\s*$/g;
        if (reg.test(text)) {
            return false
        } else {
            return true;
        }
    },
    /***
     * 身份证是否正确
     * @param card
     * @returns {boolean}
     */
    isCardNo: function (card) {
        // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (reg.test(card) === false)
            return false;
        else
            return true;

    },
    /**
     * 根据身份证获取性别级出生年月日
     * @param UUserCard
     * @returns {{sex: {}}|*}
     */
    discriCard: function (UUserCard) {
        mUser = {
            sex: {}
        }
        //获取出生日期
        mUser.birthday = UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
        //性别
        if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
            mUser.sex.disname = '男'
            mUser.sex.value = '0'
        } else {
            mUser.sex.disname = '女'
            mUser.sex.value = '1'
        }
        //获取年龄
        var myDate = new Date();
        var month = myDate.getMonth() + 1;
        var day = myDate.getDate();
        var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
        if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
            age++;
        }
        mUser.age = age;
        return mUser;
    },

    /**
     * 获取约定的json格式对象
     * @param jsonObjectText
     */
    convertJsonBuNameSpaces: function (jsonObjectText) {
        if (jsonObjectText) {
            return jsonObjectText.result_object;
        } else {
            return false
        }
    },
    /**
     * 显示请求等待进度条窗口
     * @param msg
     */
    showWaitMsg: function (title, msg) {
        Ext.MessageBox.show({
            title: title ? title : '系统提示',
            msg: msg == null ? '正在处理数据,请稍候...' : msg,
            progressText: 'processing now,please wait...',
            width: 300,
            wait: true,
            waitConfig: {
                interval: 150
            }
        });
    },

    /**
     * 隐藏请求等待进度条窗口
     */
    hideWaitMsg: function () {
        Ext.MessageBox.hide();
    },
    /**
     * 给store添加查询条件
     * @param {} f
     * @param {} store
     */
    appendParam: function (f, store) {
        if (f.isDisabled())return;

        if (f.items) {
            f.items.each(function (field) {
                appendParam(field, store);
            });
        } else if (f instanceof Ext.form.DateField) {//&&f.getValue()
            store.proxy.extraParams[f.getName()] = Ext.Date.format(f.getValue(), f.format);
        } else if (f instanceof Ext.form.Radio) {
            store.proxy.extraParams[f.getName()] = f.getGroupValue();
        } else if (f instanceof Ext.form.Field) {
            store.proxy.extraParams[f.getName()] = f.getValue();
        }
    }
}
/*常亮配置*/
var contants = {
    isEdit: {
        Yes: 0, No: 1
    },
    /*禁用颜色*/
    disableClor: {
        one: "#999999"
    }
}
/***************************************start 》仿类声明函数*************************/

