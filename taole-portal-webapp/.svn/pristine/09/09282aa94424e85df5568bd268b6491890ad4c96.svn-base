Ext.define('Taole.strategy.marketingSubject.controller.MarketingSubjectCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.strategy.marketingSubject.view.MarketingSubjectPanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'marketingSubjectPanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'marketingSubjectPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'marketingSubjectPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.load();
    			}
    		},
    		'marketingSubjectPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'marketingSubjectPanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'marketingSubjectPanel>grid button[action=add]': {//新增
    			click: function(){
    				this.edit();
    			}
    		},
    		'marketingSubjectPanel>grid button[action=update]': {//修改
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
    				
    				this.edit(selRows[0].data.id, true);
    			}
    		},
    		'marketingSubjectPanel>grid button[action=remove]': {//删除
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要删除的数据");
    					return;
    				}
    				
    				var ids = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					ids.push("id="+record.data.id);
    				}
    				
    				Ext.Msg.confirm("提示", "确定删除选中的信息吗？", function(bt){
						if(bt=='yes'){
							this.del(ids.join("&"), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		},
    		'marketingSubjectPanel>grid button[action=retrieve]': {//详情
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
    				
    				this.edit(selRows[0].data.id, true, true);
    			}
    		},
    		
    	});
    },
    edit: function(appAdPositionId, isView, isRetrieve){
		Ext.create("Taole.strategy.marketingSubject.controller.AddOrEditPositionCtrl").init();
	    	Ext.create("widget.addOrEditPositionWindow",{
	    		scope: this,
	    		afterChooseFn: function(){
	    			
	    		},
	    		appAdPositionId: appAdPositionId,
	    		isView: isView,
	    		isRetrieve: isRetrieve
    	}).show();
    },
    del: function(appAdpositionIds, successFn, failureFn){
    	Ext.Ajax.request({
			url: APP_ADPOSITION_DELETE+"?"+appAdpositionIds,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					Ext.Msg.alert("提示", "删除成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','删除失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "删除失败，请联系管理员！", failureFn, this);
			},
			scope: this
		});
    }
});