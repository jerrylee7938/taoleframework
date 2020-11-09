Ext.define('Taole.user.userCardHistory.controller.UserCardHistoryCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.user.userCardHistory.view.UserCardHistoryPanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'userCardHistoryPanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'userCardHistoryPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'userCardHistoryPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
				
    			}
    		},
    		'userCardHistoryPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'userCardHistoryPanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'userCardHistoryPanel>grid button[action=update]': {//修改
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
    				this.edit();
    				//this.edit(selRows[0].data.accountId, true);
    			}
    		},
    		'userCardHistoryPanel>grid button[action=remove]': {//删除
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要删除的数据");
    					return;
    				}
    				
    				var jobId = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					jobId.push(record.data.id);
    				}
    				
    				Ext.Msg.confirm("提示", "确定删除选中的信息吗？", function(bt){
						if(bt=='yes'){
							this.del(jobId.join(","), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		},
    		'userCardHistoryPanel>grid button[action=retrieve]': {//详情
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
    				
    				this.edit(selRows[0].data.accountId, true, true);
    			}
    		},
    		
    	});
    },
    edit: function(doctorId, isView, isRetrieve){
		Ext.create("Taole.user.userCardHistory.controller.AddOrEditPositionCtrl").init();
	    	Ext.create("widget.addOrEditPositionWindow",{
	    		title:isRetrieve?"详情":isView?"修改":"新增",
	    		scope: this,
	    		afterChooseFn: function(){
	    			
	    		},
	    		doctorId: doctorId,
	    		isView: isView,
	    		isRetrieve: isRetrieve
    	}).show();
    },
    del: function(jobId, successFn, failureFn){
    	Ext.Ajax.request({
			url:URL_TASK_DELETE+"?jobIds="+jobId,
			timeout: 300000,
			method:'DELETE',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code == 1){
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