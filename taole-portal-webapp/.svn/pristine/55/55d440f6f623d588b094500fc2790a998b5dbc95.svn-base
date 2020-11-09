Ext.define('Taole.sms.smsApply.controller.SmsApplyCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.sms.smsApply.view.SmsApplyPanel'
    ],
    refs: [
    {
    	ref: 'form',
    	selector: 'smsApplyPanel>form'
    },        
    {
    	 ref: 'grid',
         selector: 'smsApplyPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'smsApplyPanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
    					appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'smsApplyPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
    			}
    		},
    		'smsApplyPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'smsApplyPanel>grid button[action=update]': {//修改
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
    		
    		'smsApplyPanel>grid button[action=examine]': {//审核
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				var store = this.getGrid().store;
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要审核的记录");
    					return;
    				}
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录审核");
    					return;
    				}
    				
    				this.examine(selRows[0].data.id, function(){
    					var grid =  Ext.ComponentQuery.query("smsApplyPanel>grid")[0];
						grid.store.load();
						grid.getSelectionModel().deselectAll();//取消选中行
    				})
    				
    			}
    		},
    		'smsApplyPanel>grid button[action=test]': {//测试发送
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				var store = this.getGrid().store;
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要测试发送短信的记录");
    					return;
    				}
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录测试发送短信");
    					return;
    				}
    				
    				Ext.Msg.confirm("提示", "请确认是否需要测试发送短信？", function(bt){
						if(bt=='yes'){
							this.publish(selRows[0].data.id, function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		}
    	});
    },
    edit: function(taskId, isView, isRetrieve){
		Ext.create("Taole.sms.smsApply.controller.AddOrEditPositionCtrl").init();
	    	Ext.create("widget.addOrEditPositionWindow",{
	    		scope: this,
	    		title:taskId? '编辑任务 ':'新建任务',
	    		afterChooseFn: function(){
	    		},
	    		taskId: taskId,
	    		isView: isView,
	    		isRetrieve: isRetrieve
    	}).show();
    },
    
    examine:function(id,successFn){
			var pro_win = Ext.create("Ext.window.Window",{
				scope: this,
				title:'审核',
				id:'pro_win',
				width:450,
				modal:true,
				height:200,
				layout:'fit',
				items:[{
					xtype:'form',
					layout:'absolute',
					id:'prj_basic_form',
					baseCls:'x-plain',
					margins:'20 0 0 0',
					frame:true,
					defaults:{
						labelAlign:'right',
						labelWidth:90,
						xtype:'textfield',
						width:320
					},
					items:[{
						x:20,
						y:0,
						id:'avatar',
						name:'account',						
						fieldLabel:'登录账号',
					},{
						x:20,
						y:30,
						id:'password',
						name:'password',						
						fieldLabel:'登录密码'
					},{
						x:20,
						y:60,
						id:'appKey',
						name:'appKey',
						fieldLabel:'平台编号',
					
					}]
				}],
				buttonAlign:'center',				
				buttons:[{
					text:'保存',
					handler:function(){
						var form = Ext.getCmp("prj_basic_form");
						if(form.getForm().isValid()){
							var data = Ext.ux.FormUtils.getDataObject(form); 
							var datal = '&account='+data.account+'&password='+data.password+'&appKey='+data.appKey
							var url =Ext.util.Format.format(SMS_APPLY_EXAMINE+datal, id);
							Ext.Ajax.request({
								url:url,
								method:'get',
								jsonData:data,
								success:function(resp){
									if(!getResp(resp)){
										var text = resp.responseText;
										var data = eval("("+text+")");
										Ext.Msg.alert('提示','审核失败:'+data.description);
									}else{
										var text = resp.responseText;
										var obj = Ext.JSON.decode(text);
										if(obj.code==1){
											pro_win.destroy();
											Ext.Msg.alert('提示','审核成功:',successFn,this);
										}else{
											Ext.Msg.alert('提示','审核失败:'+obj.description);
										}
										
									}
								},
								failure:function(resp){
									Ext.Msg.alert('提示','审核失败！！');
								},
								scope: this
							});
						}
					}
				},{
					text:'取消',
					handler:function(){
						pro_win.destroy();
					}
				}]
			});
		
		pro_win.show();
    },
    publish: function(id, successFn, failureFn){
    	var url= Ext.util.Format.format(SMS_APPLY_TEST, id);
    	Ext.Ajax.request({
			url: url,
			method:'GET',
//			jsonData:{"status":"2"},
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code == '1'){
					Ext.Msg.alert("提示", "测试发送短信成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','测试发送短信!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "测试发送短信失败", failureFn, this);
			},
			scope: this
		});
    }
});