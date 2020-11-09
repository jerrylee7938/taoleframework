Ext.define('Taole.store.storeManager.controller.StoreCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.store.storeManager.view.StorePanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'storePanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'storePanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'storePanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
    			}
    		},
    		'storePanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'storePanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'storePanel>grid button[action=add]': {//新增
    			click: function(){
    				this.edit();
    			}
    		},
    		'storePanel>grid button[action=update]': {//修改
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要修改的记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录修改");
    					return;
    				}
    				this.edit(selRows[0].data.shopId, true);
    			}
    		},
    		'storePanel>form field[name=province]':{//省
    			change: function(combox){
    				var val = combox.getValue();
    				var city = this.getForm().form.findField("city");
    				city.setValue();
    				var store = city.getStore();
    				store.proxy.url = Ext.util.Format.format(URL_DICTIONARY, val);
					store.load();		
    			}
    		},
    		'storePanel>form field[name=city]':{//城市
    			focus: function(combox){
    				var province = this.getForm().form.findField("province").getValue();
    				if(province){
    				}else{
    					Ext.Msg.alert("提示", "请先选择省份！");
    				}
    						
    			}
    		},
    		'storePanel>grid button[action=remove]': {//删除
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要删除的数据");
    					return;
    				}
    				
    				var ids = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					ids.push(record.data.shopId);
    				}
    				
    				Ext.Msg.confirm("提示", "确定删除选中的信息吗？", function(bt){
						if(bt=='yes'){
							this.del(ids.join(","), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		},
    		'storePanel>grid button[action=retrieve]': {//详情
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录");
    					return;
    				}
    				
    				this.edit(selRows[0].data.shopId, true, true);
    			}
    		},
    		
    	});
    },
    edit: function(appAdPositionId, isView, isRetrieve){
		Ext.create("Taole.store.storeManager.controller.AddOrEditPositionCtrl").init();
	    	Ext.create("widget.addOrEditPositionWindow",{
	    		scope: this,
	    		title:isRetrieve? '详情':isView? '修改':'新增',
	    		afterChooseFn: function(){
	    			
	    		},
	    		appAdPositionId: appAdPositionId,
	    		isView: isView,
	    		isRetrieve: isRetrieve
    	}).show();
    },
    del: function(appAdpositionIds, successFn, failureFn){
    	Ext.Ajax.request({
			url: URL_MEMBER_STORE_DELETE+'?ids='+appAdpositionIds,
			timeout: 300000,
			method:'delete',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code ==1){
					Ext.Msg.alert("提示", "删除成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','删除失败!<br/>'+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "删除失败，请联系管理员！", failureFn, this);
			},
			scope: this
		});
    }
});