/**
 * Created by ChengJ on 2016/7/28.
 */
Ext.define('Taole.marketing.controller.ChannelCtr', {
    extend: 'Ext.app.Controller',
    views: [
        'Taole.marketing.view.ChannelMain', 'Taole.marketing.view.ChanneEditV'
    ],
    refs: [
        {
            ref: 'panel',
            selector: 'channelMain'
        },
        {
            ref: 'form',
            selector: 'channelMain>form'
        },
        {
            ref: 'grid',
            selector: 'channelMain>grid'
        },
        /*-----------------------维护view------------*/
        {
            ref: 'edtiForm',
            selector: 'channeEditV'
        }
    ],
    init: function () {
        this.control({
            'channelMain': {
                afterrender: function (Component, eOpts) {
                    this.getGrid().store.load();
                }

            },
            'channelMain>form button[action=query]': {//查询
                click: function () {
                    this.getGrid().store.load();
                }
            },
            'channelMain>form button[action=reset]': {//取消
                click: function () {
                    this.getForm().form.reset();
                }
            },
            'channelMain>grid': {
                afterrender: function (gridpanel) {
                    gridpanel.store.on("beforeload", function (store) {
                        appendParam(this.getForm(), store);
                    }, this);
                }
            },
            'channelMain>grid button[action=add]': {
                click: function () {
                    Ext.create("widget.channeEditV").show()
                }
            },
            'channelMain>grid button[action=update]': {
                click: function () {
                    var selRows = this.getGrid().getSelectionModel().getSelection();
                    if (selRows.length == 0 || selRows.length > 1) {
                        Ext.Msg.alert("提示", "请选择一条要修改的渠道记录！");
                        return;
                    }
                    Ext.create("widget.channeEditV", {mid: selRows[0].data.id,title:'渠道修改'}).show()
                }
            },
            /*初始化编辑详情*/
            'channeEditV': {
                afterrender: function (mwindow, opts) {
                    var Form = mwindow.down('form')
                    var mmForm = Form.getForm();
                    if (mwindow.mid) {
                        this.loadDataById(mwindow.mid, function (jsondata) {
                            for (i in jsondata) {
                                if (mmForm.findField(i))
                                    if (!mwindow.isEdit) {
                                        mmForm.findField(i).setValue(jsondata[i]);
                                    } else {
                                    	Ext.getCmp('btn').hide();
                                        mmForm.findField(i).setValue(jsondata[i]);
                                        mmForm.findField(i).setReadOnly(true);
                                        mmForm.findField(i).setFieldStyle("background-color:#EEEEE0;background-image: none;;");
                                    }
                            }
                        }, null, this);
                    }
                    
                    if(mwindow.isEdit){
    					var collection = mmForm.getFields();
						collection.each(function(field){
						    field.setReadOnly(true);
						    field.setFieldStyle("background-color:#EEEEE0;background-image: none;");
						});
    				}
                    
                },
                beforedestroy: function (mwindow, opts) {
                    console.log('已注销！')
                }
            },
            'channeEditV>form button[action=create]': {
                click: function (btn) {
                    var maddWindow = btn.up('window');
                    var mform = btn.up('window').down('form');
                    var mgrid = this.getGrid();
                    if (mform.isValid()) {
                        this.save(mform.getValues(), function(data){
                        	maddWindow.close()
                            mgrid.store.load();}, null, this);
                    }
                }
            },
            'channeEditV>form button[action=close]': {
                click: function (btn) {
                    var maddWindow = btn.up('window');
                    maddWindow.close();
                }
            }

        });
    },
    view: function (id) {
        Ext.create("widget.channeEditV", {mid: id, isEdit: true,title:"渠道详情"}).show()
    },
    save: function(channelData, successFn, failureFn, scope){
    	var url = URL_USER_CHANNEL_CREATE;
    	if(channelData.id){
    		url = Ext.util.Format.format(URL_USER_CHANNEL_UPDATE, channelData.id);
    	}
		Ext.Ajax.request({
			url: url,
			jsonData: channelData,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "保存失败<br/>"+data.result_code + "：" + data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "保存失败", failureFn, this);
			},
			scope: scope||this
		});
    },
    loadDataById: function(channelId, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(URL_USER_CHANNEL_GET, channelId);
    	Ext.Ajax.request({
			url: url,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','获取推荐渠道详情失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取推荐渠道详情失败", failureFn, this);
			},
			scope: scope
		});
    }
});