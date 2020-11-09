Ext.define('Taole.appManager.appNews.controller.AppNewsCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.appManager.appNews.view.AppNewsPanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'appNewsPanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'appNewsPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'appNewsPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.load();
    			}
    		},
    		'appNewsPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'appNewsPanel>grid': {
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
    		'appNewsPanel>grid button[action=add]': {//新增
    			click: function(){
    				var arr = new Array();
    				arr.push({
    					"appNews": ""
    				});
    				this.getGrid().store.add(arr);
					this.getGrid().getPlugin("rowediting").startEdit(this.getGrid().store.getCount()-1,0);
    			}
    		},
    		'appNewsPanel>grid button[action=remove]': {//删除
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
    				
    				Ext.Msg.confirm("提示", "确定删除选中的APPNews吗？", function(bt){
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
    save: function(appNews, successFn, failureFn, scope){
    	var url = IMEI_CREATE;
    	if(appNews.id){
    		url = Ext.util.Format.format(IMEI_UPDATE, appNews.id);
    	}
    	delete appNews.createTime;
    	delete appNews.updateTime;
		Ext.Ajax.request({
			url: url,
			jsonData: appNews,
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
    del: function(appNewsIds, successFn, failureFn){
    	Ext.Ajax.request({
			url: IMEI_DELTE+"?"+appNewsIds,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					Ext.Msg.alert("提示", "APPNews删除成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','APPNews删除失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "APPNews删除失败", failureFn, this);
			},
			scope: this
		});
    }
});