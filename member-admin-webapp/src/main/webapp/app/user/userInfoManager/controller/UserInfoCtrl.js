Ext.define('Taole.user.userInfoManager.controller.UserInfoCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.user.userInfoManager.view.UserInfoPanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'userInfoPanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'userInfoPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'userInfoPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
				
    			}
    		},
    		'userInfoPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'userInfoPanel>form field[name=name]': {//用户
    			focus: function(){	
    				var form = this.getForm().form;
    				Ext.create("Taole.common.userChoose.controller.UserChooseCtrl").init();
			    	Ext.create("widget.userChooseWindow",{
			    		scope: this,
			    		allowMultiSelect: false,
			    		afterChooseFn: function(chooseData){
			    			console.log(chooseData);
			    			form.findField('name').setValue(chooseData.name);
			    			form.findField('userId').setValue(chooseData.userId);
			    		}
		    		}).show();
    			}
    		},
    		'userInfoPanel>grid': {
    			afterrender: function(gridpanel){
    				var buttons = gridpanel.query("button");
    				validateButtonPrivilege(buttons);
    				gridpanel.store.on("beforeload", function(store){
    					var cardNo = this.getForm().form.findField("cardNo").getValue();
    					cardNo = cardNo.replace(/\s*/g,"")
    					if(cardNo){
    						store.proxy.extraParams['cardNo'] = cardNo;
    					}else{
    						appendParam(this.getForm(), store);
    					}
    				}, this);
    			}
    		},
    		'userInfoPanel>grid button[action=apply]': {//办卡
    			click: function(){
    				this.apply();
    			}
    		},
    		'userInfoPanel>grid button[action=open]': {//开卡
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择待审核的会员卡进行审核");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条待审核的会员卡进行审核");
    					return;
    				}
    				if(selRows[0].data.status !=='0'){
    					Ext.Msg.alert("提示", "请选择一条待审核的会员卡进行审核");
    					return;
    				}
    				this.open(selRows[0].data.userCardId,true,true);
    			}
    		},
    		'userInfoPanel>grid button[action=recharge]': {//充值
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要充值的会员卡");
    					return;
    				}		
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条会员卡记录进行充值");
    					return;
    				}
    				if(selRows[0].data.status !=='1'){
    					Ext.Msg.alert("提示", "请选择一条已激活的会员卡进行充值");
    					return;
    				}
    				this.recharge(selRows[0].data);
    			}
    		},
    		'userInfoPanel>grid button[action=back]': {//退卡
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要退的会员卡");
    					return;
    				}		
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条会员卡记录进行退卡");
    					return;
    				}
    				this.back(selRows[0].data);
    			}
    		},
    		'userInfoPanel>grid button[action=stop]': {//停用
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择已激活的会员卡");
    					return;
    				}		
    				var userId = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					if(record.data.status =='1'){userId.push(record.data.userCardId);}		
    				}
    				if(userId.length == 0){
    					Ext.Msg.alert("提示", "请选择已激活的会员卡！");
    					return;
    				}
    				Ext.Msg.confirm("提示", "确定要停用会员卡吗？", function(bt){
						if(bt=='yes'){
							this.stop(userId.join(","), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		},
    		'userInfoPanel>grid button[action=start]': {//启用
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择已停用的会员卡！");
    					return;
    				}		
    				var userId = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					if(record.data.status =='4'){userId.push(record.data.userCardId);}		
    				}
    				if(userId.length == 0){
    					Ext.Msg.alert("提示", "请选择已停用的会员卡！");
    					return;
    				}
    				Ext.Msg.confirm("提示", "确定要启用会员吗？", function(bt){
						if(bt=='yes'){
							this.start(userId.join(","), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		},
    		'userInfoPanel>grid button[action=update]': {//修改
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要修改的会员信息");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条会员进行修改");
    					return;
    				}
    				this.update(selRows[0].data.userCardId,true,false);
    			}
    		},
    		'userInfoPanel>grid button[action=remove]': {//删除
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
    		'userInfoPanel>grid button[action=retrieve]': {//详情
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
		Ext.create("Taole.user.userInfoManager.controller.AddOrEditPositionCtrl").init();
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
    recharge:function(data,isView,isRetrieve){
			Ext.create("Taole.user.userInfoManager.controller.CardRechargeCtrl").init();
	    	Ext.create("widget.cardRechargeWindow",{
	    		title:data.cardNo+'卡充值',
	    		scope: this,
	    		afterChooseFn: function(){		
	    		},
	    		carddata: data,
	    		isView: isView,
	    		isRetrieve: isRetrieve
		}).show();
	},
	back:function(data,isView,isRetrieve){
		Ext.create("Taole.user.userInfoManager.controller.CardBackCtrl").init();
		Ext.create("widget.cardBackWindow",{
			title:'卡'+data.cardNo+'退卡',
			scope: this,
			afterChooseFn: function(){	
			},
			carddata: data,
			isView: isView,
			isRetrieve: isRetrieve
	}).show();
	},
	apply:function(cardId,isView,isRetrieve){
		Ext.create("Taole.user.userInfoManager.controller.CardApplyCtrl").init();
		Ext.create("widget.cardApplyWindow",{
			title:'办卡',
			scope: this,
			afterChooseFn: function(){		
			},
			cardId: cardId,
			isView: isView,
			isRetrieve: isRetrieve
	}).show();
	},
	update:function(cardId,isView,isRetrieve){
		Ext.create("Taole.user.userInfoManager.controller.UpdateUserInfoCtrl").init();
		Ext.create("widget.updateUserInfoWindow",{
			title:'修改会员信息',
			scope: this,
			afterChooseFn: function(){
				
			},
			cardId: cardId,
			isView: isView,
			isRetrieve: isRetrieve
	}).show();
	},
	open:function(cardId,isView,isRetrieve){
		Ext.create("Taole.user.userInfoManager.controller.CardOpenCtrl").init();
		Ext.create("widget.cardOpenWindow",{
			title:isRetrieve?'审核':'修改会员信息',
			scope: this,
			afterChooseFn: function(){
				
			},
			cardId: cardId,
			isView: isView,
			isRetrieve: isRetrieve
	}).show();
	},
    stop: function(userId, successFn, failureFn){
    	Ext.Ajax.request({
			url:URL_MEMBER_MEMBERCARD_UNABLE+"?ids="+userId,
			timeout: 300000,
			method:'post',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code == 1){
					Ext.Msg.alert("提示", "操作成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','操作失败!<br/>'+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "操作失败，请联系管理员！", failureFn, this);
			},
			scope: this
		});
    },
    start: function(userId, successFn, failureFn){
    	Ext.Ajax.request({
			url:URL_MEMBER_MEMBERCARD_ENABLE+"?ids="+userId,
			timeout: 300000,
			method:'post',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code == 1){
					Ext.Msg.alert("提示", "操作成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','操作失败!<br/>'+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "操作失败，请联系管理员！", failureFn, this);
			},
			scope: this
		});
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