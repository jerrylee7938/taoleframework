Ext.define('Taole.card.cardManager.controller.CardCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.card.cardManager.view.CardPanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'cardPanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'cardPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'cardPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
    			}
    		},
    		'cardPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'cardPanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'cardPanel>grid button[action=add]': {//新增
    			click: function(){
    				this.edit();
    			}
    		},
    		'cardPanel>grid button[action=choose]': {//选择门店
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要操作的记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录");
    					return;
    				}
    				
    				setStoreToCard(selRows[0].data.cardId,1);
    			}
    		},
    		'cardPanel>grid button[action=update]': {//修改
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
    				this.edit(selRows[0].data.cardId, true);
    			}
    		},
    		'cardPanel>grid button[action=remove]': {//删除
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要删除的数据");
    					return;
    				}
    				
    				var ids = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					ids.push(record.data.cardId);
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
    		'cardPanel>grid button[action=onsale]': {//上架
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要上架的卡");
    					return;
    				}
    				
    				var ids = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					ids.push(record.data.cardId);
    				}
    				
    				Ext.Msg.confirm("提示", "确定要上架选中的卡吗？", function(bt){
						if(bt=='yes'){
							this.onSale(ids.join(","), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		},
    		'cardPanel>grid button[action=offsale]': {//下架
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要下架的卡");
    					return;
    				}
    				
    				var ids = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					ids.push(record.data.cardId);
    				}
    				
    				Ext.Msg.confirm("提示", "确定要下架选中的卡吗？", function(bt){
						if(bt=='yes'){
							this.offSale(ids.join(","), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		},
    		'cardPanel>grid button[action=retrieve]': {//详情
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
    				
    				this.edit(selRows[0].data.cardId, true, true);
    			}
    		},
    		
    	});
    },
//    setStoreToCard:function(cardId,type){
//			var user_window = Ext.getCmp("user_window");
//			if(!user_window){       
//				user_window = Ext.create('Ext.window.Window',{
//					title: '设置店面',
//					height:300,
//					id:'user_window',
//					width:600,
//					resizable:false,
//					modal:true,
//					layout:'fit',
//					items:[{
//						id:'add_user_form',
//						xtype:'form',
//						frame:true,
//						style:{
//							'border':'0px'
//						},
//						border:false,
//						autoScroll:true,
//						layout:'absolute',
//						items:[
//						/*       {
//							xtype:'textfield',
//							fieldLabel:'关键字',
//							labelWidth:55,
//							labelAlign:'right',
//							id:'keyword',
//							x:5,
//							y:5,
//							width:200
//						},{
//							xtype:'button',
//							text:'查询',
//							x:210,
//							y:5,
//							width:80,
//							handler:function(){
//								var keyword = Ext.getCmp('keyword').getValue();
//								var fieldset = Ext.getCmp('userList');
//								fieldset.removeAll();
//								fieldset.add(addUserTo('userList',600,keyword,"",""));
//								fieldset.doLayout();
//								
//							}
//						},{
//							xtype:'button',
//							text:'重置',
//							x:295,
//							y:5,
//							width:80,
//							handler:function(){
//								Ext.getCmp('keyword').setValue("");
//							}
//						},
//						*/{
//							xtype:'fieldset',
//							title:'店面列表',
//							id:'userList',
//							width:545,
//							x:20,
//							y:35,
//							items:[]
//						}]
//					}],
//					buttonAlign:'center',
//					buttons:[{
//						text:'保存',
//						handler:function(){
//							var userArray =[];
//							var user_form =  Ext.getCmp("add_user_form");
//							var checkArray = user_form.query("checkbox");
//							for(var j =0; j<checkArray.length; j++){
//								var checkbox = checkArray[j];
//								var value = checkbox.getValue();
//								console.log(value)
//								if(checkbox.boxLabel !='全选'){
//									if(value){
//										userArray.push(checkbox.id);
//									}
//								}
//							}
//							var setData = {'objectId ':cardId,'shopIds':userArray.join(','),'objectType':type};
//							
//							Ext.Ajax.request({
//								url:URL_MEMBER_SHOPSET_CREATE,
//								jsonData:setData,
//								method:'post',
//								success:function(resp, opts){
//									var r = eval("(" + resp.responseText + ")");
//									if (r.code != 1) {
//										Ext.Msg.alert("提示",'保存失败：' + r.description);
//									} else {
//										user_window.destroy();
//										Ext.Msg.alert("提示",'保存成功' );
//									}
//								},
//								failure:function(resp, opts){
//									Ext.Msg.alert("提示",'设置销售店面失败：' + resp.responseText);
//								}
//							})
//						}
//					},{
//						text:'取消',
//						handler:function(){
//							user_window.destroy();
//						}
//					}]
//				})
//			}
//			user_window.show();
//			
//			var user_form = Ext.getCmp("userList");
//			user_form.add(addStore('userList',600,cardId));
//			user_form.doLayout();
//    },
    edit: function(appAdPositionId, isView, isRetrieve){
		Ext.create("Taole.card.cardManager.controller.AddOrEditPositionCtrl").init();
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
			url: URL_MEMBER_CARD_DELETE+'?ids='+appAdpositionIds,
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
    },
    onSale: function(cardId, successFn, failureFn){
    	Ext.Ajax.request({
			url: URL_MEMBER_CARD_ONSALE+'?ids='+cardId,
			timeout: 300000,
			method:'POST',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code ==1){
					Ext.Msg.alert("提示", "上架成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','上架失败!<br/>'+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "上架失败，请联系管理员！", failureFn, this);
			},
			scope: this
		});
    },
    offSale: function(cardId, successFn, failureFn){
    	Ext.Ajax.request({
			url: URL_MEMBER_CARD_OFFSALE+'?ids='+cardId,
			timeout: 300000,
			method:'POST',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code ==1){
					Ext.Msg.alert("提示", "下架成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','下架失败!<br/>'+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "下架失败，请联系管理员！", failureFn, this);
			},
			scope: this
		});
    }
});