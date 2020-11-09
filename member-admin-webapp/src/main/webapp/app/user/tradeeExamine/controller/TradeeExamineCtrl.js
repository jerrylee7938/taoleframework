Ext.define('Taole.user.tradeeExamine.controller.TradeeExamineCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.user.tradeeExamine.view.TradeeExaminePanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'tradeeExaminePanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'tradeeExaminePanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'tradeeExaminePanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
				
    			}
    		},
    		'tradeeExaminePanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'tradeeExaminePanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'tradeeExaminePanel>grid button[action=update]': {//修改
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
    				this.edit(selRows[0].data.userCardId, true);
    			}
    		},
    		'tradeeExaminePanel>grid button[action=examine]': {//审核通过
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要审核的充值记录");
    					return;
    				}
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录审核");
    					return;
    				}
    				if(selRows[0].data.status !=0){
    					Ext.Msg.alert("提示", "请选待审核的记录进行审核");
    					return;
    				}
    				var data={"auditFlag":"y","applyId":selRows[0].data.applyId,"auditorId":dataUser.userId,"auditorName":dataUser.name};

    				Ext.Msg.confirm("提示", "该笔交易将被审核通过，请确认！", function(bt){
						if(bt=='yes'){
							showWaitMsg();
							this.examine(data, function(){
								hideWaitMsg();
								Ext.Msg.alert("提示","审核通过操作成功。", function(){
									this.getGrid().store.load();
			    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
								},this)
		    				});
						}
					}, this);  
    			}
    		},
    		'tradeeExaminePanel>grid button[action=examineNo]': {//审核不通过
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要审核的充值记录");
    					return;
    				}
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录审核");
    					return;
    				}
    				if(selRows[0].data.status !=0){
    					Ext.Msg.alert("提示", "请选待审核的记录进行审核");
    					return;
    				}
    				var data={"auditFlag":"n","applyId":selRows[0].data.applyId,"auditorId":dataUser.userId,"auditorName":dataUser.name};
    				Ext.Msg.confirm("提示", "该笔交易将被拒绝，请确认！", function(bt){
						if(bt=='yes'){
							showWaitMsg();
							this.examine(data, function(){
								hideWaitMsg();
								Ext.Msg.alert("提示","审核不通过操作成功。", function(){
									this.getGrid().store.load();
			    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
								},this)
								
		    					
		    				});
						}
					}, this);  
    			}
    		},
    		
    	});
    },
    edit: function(userCardId, isView, isRetrieve){
		Ext.create("Taole.user.tradeeExamine.controller.AddOrEditPositionCtrl").init();
	    	Ext.create("widget.addOrEditPositionWindow",{
	    		scope: this,
	    		title:'修改会员卡到期时间',
	    		afterChooseFn: function(){
	    			
	    		},
	    		appAdPositionId: userCardId,
	    		isView: isView,
	    		isRetrieve: isRetrieve
    	}).show();
    },
    examine: function(userData, successFn, failureFn){
    	Ext.Ajax.request({
			url:URL_MEMBER_USERBILL_APPLYAUDIT,
			jsonData:userData,
			timeout: 300000,
			method:'post',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code == 1){
					successFn.call(this, data);
				//	Ext.Msg.alert("提示", "充值审核通过，充值成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','审核不通过。<br/>'+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "审核不通过，请联系管理员！", failureFn, this);
			},
			scope: this
		});
    }
});