Ext.define('Taole.user.consumeManager.controller.ConsumeCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.user.consumeManager.view.ConsumePanel'
    ],
    refs: [
           {
          ref: 'panel',
          selector: 'consumePanel'
     },
    {
    	 ref: 'form',
         selector: 'consumePanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'consumePanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'consumePanel': {
    			afterrender: function(win){
    				var form = this.getForm().form;
    				this.getCode(function(data){
						if(data.code !=1){
							Ext.Msg.alert('提示',data.description);
						}
						var code=data.result;
						form.findField('userBillNo').setValue(code);	
					},null,this);
    				
    				var userName=getUserName();
					form.findField('operator').setValue(userName);	
    				
    			}
    		},
    		'consumePanel>form field[name=userType]':{//用户类型
    			change: function(combox){
    				var val = combox.getValue();
    				if(val == 'HY'){
    					Ext.getCmp('menberInfo').show();
    					Ext.getCmp('imageForm').show();
    					this.getForm().doLayout();
    				}else{
    					Ext.getCmp('menberInfo').hide();
    					Ext.getCmp('imageForm').hide();
    					this.getForm().doLayout();
    					Ext.getCmp("avatar").setSrc();
    					var form =Ext.getCmp('menberInfo').form.reset();
    				}
    			}
    		},
    		'consumePanel>form field[name=userCardNo]':{//卡号
    			change:function(combox,newValue,oldValue){
    				var form =Ext.getCmp('menberInfo').form;
    				var cardNo =combox.getValue();
    				var newLength = newValue.length;
    				if(!oldValue && newLength>2){
    					this.readCard(cardNo,function(card){
    						var cardInfo=card.result;
    						var image = Ext.getCmp("avatar");
							image.setSrc(cardInfo.userAvatar);
							var userState =cardInfo.status
							if(userState ==0){
								Ext.Msg.alert('提示','此卡向未审核,暂不能消费，请先审核！');
							}else if(userState ==2){
								Ext.Msg.alert('提示','此卡已退卡,不能消费！');
							}else if(userState ==3){
								Ext.Msg.alert('提示','此卡已过期,不能消费！');
							}else if(userState ==4){
								Ext.Msg.alert('提示','此卡已停用,不能消费,请重新开通！');
							}
							for(i in cardInfo){	
								if(form.findField(i)){
									form.findField(i).setValue(cardInfo[i]);
								}
							}
							if(cardInfo.cardTypeName =='计次卡'){
    							form.findField('swipeAmount').setMaxValue(cardInfo.blanceNum);
    						}else {
    							form.findField('blanceNum').setValue('--');
    							form.findField('swipeAmount').setMaxValue();
							}
							var date = new Date();
							if(cardInfo.deadline){
								var std = new Date(cardInfo.deadline);
								var day = Math.round((std-date)/86400000);
								form.findField('day').setValue(day);
							}
							
						},null,this);
    				
    				
    				}
    			},
    		},
    		'consumePanel>grid': {
    			beforeedit: function(editor, e){
    				e.grid.columns[7].getEditor().setMaxValue(e.record.data.balance);
    			},			
    			edit: function(editor, e){	
    			},
    		},
    		'consumePanel>form button[action=readerPhone]':{//获取手机号所包含的会员卡
    			click: function(){
    				var telphone = Ext.getCmp('telphone').getValue();	
    				if(telphone){
    					Ext.create("Taole.common.userCardChoose.controller.UserCardChooseCtrl").init();
    			    	Ext.create("widget.userCardChooseWindow",{
    			    		telphone:telphone,
    			    		scope: this,
    			    		allowMultiSelect: false,
    			    		afterChooseFn: function(chooseData){
    			    			Ext.getCmp('menberInfo').form.findField('userCardNo').setValue();
    			    			Ext.getCmp('menberInfo').form.findField('userCardNo').setValue(chooseData.cardNo);
    			    			
    			    		}
    		    		}).show();
    				}else{
    					Ext.Msg.alert('提示','手机号不能为空！');
    				}
    			}
    		},
    		'consumePanel>form button[action=reader]':{//读卡
    			click: function(){
    				var form =Ext.getCmp('menberInfo').form;
    				var cardNo = form.findField('userCardNo').getValue();	
    				if(cardNo){
    					this.readCard(cardNo,function(card){
    						var cardInfo=card.result;
    						var image = Ext.getCmp("avatar");
							image.setSrc(cardInfo.userAvatar);
							var userState =cardInfo.status
							if(userState ==0){
								Ext.Msg.alert('提示','此卡向未审核,暂不能消费，请先审核！');
							}else if(userState ==2){
								Ext.Msg.alert('提示','此卡已退卡,不能消费！');
							}else if(userState ==3){
								Ext.Msg.alert('提示','此卡已过期,不能消费！');
							}else if(userState ==4){
								Ext.Msg.alert('提示','此卡已停用,不能消费,请重新开通！');
							}
							for(i in cardInfo){	
								if(form.findField(i)){
									form.findField(i).setValue(cardInfo[i]);
								}
							}
							if(cardInfo.cardTypeName =='计次卡'){
    							form.findField('swipeAmount').setMaxValue(cardInfo.blanceNum);
    						}else {
    							form.findField('blanceNum').setValue('--');
    							form.findField('swipeAmount').setMaxValue();
							}
							var date = new Date();
							if(cardInfo.deadline){
								var std = new Date(cardInfo.deadline);
								var day = Math.round((std-date)/86400000);
								form.findField('day').setValue(day);
							}
							
						},null,this);
    				}
    			}
    		},
    		'consumePanel>grid button[action=remove]':{
    			click:function(){
    				var store = this.getGrid().store;
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				Ext.each(selRows, function(record){
    					  store.remove(record);
	    			});
    			}
    		},
    		'consumePanel>grid button[action=add]': {//新增
    			click: function(){
    				var form = this.getForm().form;
    				var shopId =form.findField('shopId').getValue();	
    				if(!shopId){
    					Ext.Msg.alert("提示",'请选择消费店面！');
    					return;
    					}
    				var store = this.getGrid().store;
    				Ext.create("Taole.common.goodsChoose.controller.GoodsChooseCtrl").init();
			    	Ext.create("widget.goodsChooseWindow",{
			    		shopId:shopId,
			    		scope: this,
			    		allowMultiSelect: true,
			    		afterChooseFn: function(chooseData){
			    			var array = new Array();
			    			Ext.each(chooseData, function(record){
			    				if(store.find("goodsId", record.data.goodsId)>-1)return;
			    				if(record.data.balance ==0)return;
			    				record.data.amount =1;
			    				array.push(record.data);
			    			});
			    			store.add(array);
			    		}
		    		}).show();
    			
    			}
    		},
    		'consumePanel button[action=preview]': {//预览
    			click: function(){
    				var form = this.getForm();
					if(!form.form.isValid())return;
					var consumeData = form.getValues();
					var store =this.getGrid().store
					var userType =form.form.findField('userType').getValue();
					if(store.data.length ==0 && userType !='HY'){
						Ext.Msg.alert("提示",'请选择消费商品！');
						return;
					}
    				this.preview(consumeData,store);
    			}
    		},
    		
    	});
    },
    readCard: function(cardNo, successFn, failureFn, scope){
    	Ext.Ajax.request({
			url: URL_MEMBER_MEMBERCARD_READER+'?cardNo='+cardNo,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(data.code == 1){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','读卡失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "读卡失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
    getCode: function(successFn, failureFn, scope){
    	var url= URL_MEMBER_USERBILL_CONSUME_CODE;
    	Ext.Ajax.request({
			url: url,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','生成单号失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "生成单号失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
    preview:function(consume,store){

		Ext.create("Taole.user.consumeManager.controller.ConsumeListCtrl").init();
	    	Ext.create("widget.consumeListWindow",{
	    		consume:consume,
	    		consumeStore:store,
	    		title:'消费详情',
	    		scope: this,
	    		afterChooseFn: function(){
	    			
	    		},
    	}).show();
    
    },
});