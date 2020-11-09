Ext.define('Taole.goods.outOfStorageManager.controller.GoodsGoInCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.goods.outOfStorageManager.view.GoodsGoInWindow'
    ],
    refs: [ {
	   	 ref: 'window',
	     selector: 'goodsGoInWindow'
	},
    {
    	 ref: 'form',
         selector: 'goodsGoInWindow>form'
    },
    {
    	 ref: 'grid',
         selector: 'goodsGoInWindow>grid'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {
    	if(Taole.goods.outOfStorageManager.controller.GoodsGoInCtrl.isInit)return;

    	Taole.goods.outOfStorageManager.controller.GoodsGoInCtrl.isInit = true;
    	this.control({
    		'goodsGoInWindow': {
    			afterrender: function(win){
    				var form = this.getForm().form;
    				this.getCode(function(data){
						if(data.code !=1){
							Ext.Msg.alert('提示',data.description);
						}
						var code=data.result;
						form.findField('shopBillNo').setValue(code);	
					},null,this);
    			}
    		},
    		"goodsGoInWindow>form field[name=shopId]":{
    			change:function(cbx){
    				var store = this.getGrid().store.removeAll();
    			}
    		},
    		'goodsGoInWindow>grid button[action=remove]':{
    			click:function(){
    				var store = this.getGrid().store;
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				Ext.each(selRows, function(record){
    					  store.remove(record);
	    			});
    			}
    		},
    		'goodsGoInWindow>grid button[action=add]': {//新增
    			click: function(){
    				var form = this.getForm().form;
    				var shopId =form.findField('shopId').getValue();	
    				if(!shopId){
    					Ext.Msg.alert("提示",'请选择入库店面！');
    					return;
    					}
    				var store = this.getGrid().store;
    				Ext.create("Taole.common.goodsGoInChoose.controller.GoodsChooseCtrl").init();
			    	Ext.create("widget.goodsChooseWindow",{
			    		shopId:shopId,
			    		scope: this,
			    		allowMultiSelect: true,
			    		afterChooseFn: function(chooseData){
			    			var array = new Array();
			    			Ext.each(chooseData, function(record){
			    				if(store.find("goodsId", record.data.goodsId)>-1)return;
			    				record.data.amount =1;
			    				array.push(record.data);
			    			});
			    			store.add(array);
			    		}
		    		}).show();
    			
    			}
    		},
    		'goodsGoInWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'goodsGoInWindow button[action=confirm]': {//保存
    			click: function(){
    				var form = this.getForm();
    				var grid = this.getGrid();
					if(!form.form.isValid())return;
					var shopGoodsData = form.getValues();
					
					var goodsValues = [];
					grid.store.each(function(record){
						goodsValues.push({goodsId:record.data.goodsId,amount:record.data.amount});
					});
					shopGoodsData.goodsValues = goodsValues;
					
					Ext.Msg.confirm("提示", "确定是否入库？", function(bt){
						if(bt=='yes'){
							this.save(shopGoodsData, function(data){
								
								if(data.code==1){
									Ext.Msg.alert("提示","保存成功！", function(){
										grid.store.removeAll();
										this.getWindow().destroy();
										var grid_a =  Ext.ComponentQuery.query("outOfStoragePanel>grid")[0];
										grid_a.store.load();
									},this)
								}else{
									Ext.Msg.alert('提示','保存失败!<br/>'+data.description);
								}
							}, null, this);
						}
					}, this); 	
    			}
    		},
    		
    	});
    },
    getCode: function(successFn, failureFn, scope){
    	var url= URL_MEMBER_GOODSBILLDETAIL_INCODE;
    	Ext.Ajax.request({
			url: url,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','生成编码失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "生成编码失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
    save: function(shopGoodsData, successFn, failureFn, scope){
		Ext.Ajax.request({
			url: URL_MEMBER_GOODSBILLDETAIL_INSTORE,
			jsonData: shopGoodsData,
			method:'POST',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "保存失败<br/>"+data.code + "：" + data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "保存失败", failureFn, this);
			},
			scope: scope||this
		});
    }
});