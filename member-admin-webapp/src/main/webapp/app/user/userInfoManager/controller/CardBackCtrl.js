Ext.define('Taole.user.userInfoManager.controller.CardBackCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.user.userInfoManager.view.CardBackWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'cardBackWindow'
    },
    {
    	 ref: 'form',
         selector: 'cardBackWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.user.userInfoManager.controller.CardBackCtrl.isInit)return;

    	Taole.user.userInfoManager.controller.CardBackCtrl.isInit = true;
    	
    	this.control({
    		'cardBackWindow': {
    			afterrender: function(win){
    				var carddata = win.carddata;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				console.log(carddata)
    				if(carddata.cardTypeName =='期限卡'){carddata.blanceNum ='--'}
    				form.findField('balanceNum').setValue(carddata.blanceNum);
    				form.findField('userCardId').setValue(carddata.userCardId);
    				
    			}
    		},
    		'cardBackWindow button[action=confirm]':{//确定
    			click: function(){
    				
    				if(!this.getForm().form.isValid())return;
					var cardData = this.getForm().getValues();
					if(cardData.balanceNum =='--'){
						cardData.balanceNum =0;
					}
					this.save(cardData, function(data){
					
						if(data.code==1){
							Ext.Msg.alert("提示","退卡成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("userInfoPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							},this)
						}else{
							Ext.Msg.alert('提示','退卡失败!<br/>'+data.description);
						}
					}, null, this);
    			}
    		},
    		'cardBackWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'cardBackWindow button[action=close]':{//关闭
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
     save: function(appAdPosition, successFn, failureFn, scope){
     	var url = URL_MEMBER_MEMBERCARD_CLOSE;
		Ext.Ajax.request({
			url: url,
			jsonData: appAdPosition,
			timeout: 300000,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "退卡失败", failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "退卡失败，请联系管理员", failureFn, this);
			},
			scope: scope||this
		});
    }
});