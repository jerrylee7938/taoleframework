Ext.define('Taole.user.userInfoManager.controller.CardRechargeCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.user.userInfoManager.view.CardRechargeWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'cardRechargeWindow'
    },
    {
    	 ref: 'form',
         selector: 'cardRechargeWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.user.userInfoManager.controller.CardRechargeCtrl.isInit)return;

    	Taole.user.userInfoManager.controller.CardRechargeCtrl.isInit = true;
    	
    	this.control({
    		'cardRechargeWindow': {
    			afterrender: function(win){
    				var carddata = win.carddata;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				this.get(carddata.userCardId,function(appAdPosition){
						if(appAdPosition.code !=1){
							Ext.Msg.alert('提示',appAdPosition.description);
							return;
						}
						
						appAdPosition=appAdPosition.result;
						win.cardType=appAdPosition.cardType;
						console.log(appAdPosition)
						form.findField('userCardId').setValue(appAdPosition.userCardId);
	    				form.findField('cardName').setValue(appAdPosition.cardName);
	    				form.findField('cardID').setValue(appAdPosition.cardId);
	    				form.findField('deadline').setValue(appAdPosition.deadline);
	    				form.findField('chargeTypeName').setValue(appAdPosition.chargeTypeName);
	    				form.findField('cardTypeName').setValue(appAdPosition.cardTypeName);
	    				form.findField('periodOfValidity').setValue(appAdPosition.periodOfValidity);
	    				form.findField('money').setValue(appAdPosition.money);
	    				form.findField('chargeNo').setValue(appAdPosition.cardNumber);
	    				if(appAdPosition.cardType =='QXK'){
							form.findField('chargeNo').setValue('--');
						}else if (appAdPosition.cardType =='JCK') {
							form.findField('chargeTypeName').setValue('--');
							form.findField('chargeNo').setReadOnly(false);
							form.findField('chargeNo').setFieldStyle("background-color:#ffffff;");
							form.findField('chargeNo').setMinValue(0);
							form.findField('consumeMoney').setMinValue(0);
							form.findField('chargeNo').allowBlank =false;
							form.findField('consumeMoney').setReadOnly(false);
						}
					},null,this);
				}
			
    		},
    		'cardRechargeWindow>form field[name=cardAmount]':{
    			change:function(btn){
    				var win = this.getWindow();
    				var cardType =win.cardType;
    				if(cardType!='JCK'){
    					var value = btn.getValue();
        				var form = this.getForm().form;
        				var money = form.findField('money').getValue();
        				form.findField('consumeMoney').setValue(money*value);
    				}
    			}
    		},
    		'cardRechargeWindow button[action=confirm]':{//确定
    			click: function(){
    				
    				if(!this.getForm().form.isValid())return;
					var appAdPosition = this.getForm().getValues();
					var win = this.getWindow();
    				var cardType =win.cardType;
    				if(cardType=='QXK'){appAdPosition.chargeNo=null;}
    				showWaitMsg();
					this.save(appAdPosition, function(data){
						hideWaitMsg();
						if(data.code==1){
							Ext.Msg.alert("提示","充值审核提交成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("userInfoPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							},this)
						}else{
							Ext.Msg.alert('提示','充值审核提交失败!<br/>'+data.description);
						}
					}, null, this);
    			}
    		},
    		'cardRechargeWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'cardRechargeWindow button[action=close]':{//关闭
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
    get: function(userCardId, successFn, failureFn, scope){
    	var url= Ext.util.Format.format(URL_MEMBER_MEMBERCARD_RETRIEVE, userCardId);
    	Ext.Ajax.request({
			url: url,
			timeout: 300000,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','获取会员卡详情失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取会员卡详情失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
     save: function(appAdPosition, successFn, failureFn, scope){
		Ext.Ajax.request({
			url: URL_MEMBER_MEMBERCARD_RECHARGE,
			jsonData: appAdPosition,
			timeout: 300000,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "充值审核提交失败", failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "充值审核提交失败，请联系管理员", failureFn, this);
			},
			scope: scope||this
		});
    }
});