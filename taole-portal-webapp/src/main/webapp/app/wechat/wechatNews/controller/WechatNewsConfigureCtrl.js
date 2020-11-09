Ext.define('Taole.wechat.wechatNews.controller.WechatNewsConfigureCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.wechat.wechatNews.view.WechatNewsConfigureWindow'
    ],
    refs: [{
        ref: 'window',
        selector: 'wechatNewsConfigureWindow'
    },
    {
    	 ref: 'form',
         selector: 'wechatNewsConfigureWindow>form'
    },
    {
    	 ref: 'grid',
         selector: 'wechatNewsConfigureWindow>grid'
    }
    ],
    init: function() {
    	if(Taole.wechat.wechatNews.controller.WechatNewsConfigureCtrl.isInit)return;

    	Taole.wechat.wechatNews.controller.WechatNewsConfigureCtrl.isInit = true;
    	this.control({
    		'wechatNewsConfigureWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'wechatNewsConfigureWindow>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.load();
    			}
    		},
    		'wechatNewsConfigureWindow>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'wechatNewsConfigureWindow>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			},
    			edit: function(editor, e){
    				this.save(e.record.data, function(data){
    					this.getGrid().store.load();
    					this.getGrid().getSelectionModel().deselectAll();
    				}, null, this);
    			},
    			canceledit: function(editor, e){
    				var record = e.record;
    				if(!record.data.id){
    					this.getGrid().store.remove(record);
    				}
    			}
    		},
    		'wechatNewsConfigureWindow>grid button[action=add]': {//新增
    			click: function(){
    				var arr = new Array();
    				arr.push({
    					"imei": ""
    				});
    				this.getGrid().store.add(arr);
					this.getGrid().getPlugin("rowediting").startEdit(this.getGrid().store.getCount()-1,0);
    			}
    		},
    		'wechatNewsConfigureWindow>grid button[action=update]': {//修改
    			click: function(grid){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要修改的记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录修改");
    					return;
    				}
					this.getGrid().getPlugin("rowediting").startEdit(this.getGrid().getStore().indexOf(selRows[0]),0);	
    			}
    		},
    		'wechatNewsConfigureWindow>grid button[action=remove]': {//删除
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				var store = this.getGrid().store;
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要删除的数据");
    					return;
    				}
    				
    				var ids = [];
    				Ext.each(selRows, function(record){
    					ids.push("id="+record.data.id);
    				});
    				
    				Ext.Msg.confirm("提示", "确定删除选中的消息吗？", function(bt){
						if(bt=='yes'){
							this.del(ids.join("&"), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		}
    	});
    },
    save: function(imei, successFn, failureFn, scope){
    	var url = IMEI_CREATE;
    	if(imei.id){
    		url = Ext.util.Format.format(IMEI_UPDATE, imei.id);
    	}
    	delete imei.createTime;
    	delete imei.updateTime;
		Ext.Ajax.request({
			url: url,
			jsonData: imei,
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
    del: function(imeiIds, successFn, failureFn){
    	Ext.Ajax.request({
			url: IMEI_DELTE+"?"+imeiIds,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					Ext.Msg.alert("提示", "删除成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','删除失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "删除失败", failureFn, this);
			},
			scope: this
		});
    }
});