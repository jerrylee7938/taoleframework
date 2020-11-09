Ext.define('Taole.goods.outOfStorageManager.controller.GoodsLayOutCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.goods.outOfStorageManager.view.GoodsLayOutWindow'
    ],
    refs: [{
	   	 ref: 'window',
	     selector: 'goodsLayOutWindow'
	},
    {
    	 ref: 'form',
         selector: 'goodsLayOutWindow>form'
    },
    {
    	 ref: 'grid',
         selector: 'goodsLayOutWindow>grid'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {
    	if(Taole.goods.outOfStorageManager.controller.GoodsLayOutCtrl.isInit)return;

    	Taole.goods.outOfStorageManager.controller.GoodsLayOutCtrl.isInit = true;
    	this.control({
    		'goodsLayOutWindow': {
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
    		"goodsLayOutWindow>form field[name=shopId]":{
    			change:function(cbx){
    				var store = this.getGrid().store.removeAll();
    			}
    		},
    		'goodsLayOutWindow>grid button[action=remove]':{
    			click:function(){
    				var store = this.getGrid().store;
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				Ext.each(selRows, function(record){
    					  store.remove(record);
	    			});
    			}
    		},
    		'goodsLayOutWindow>grid': {
    			beforeedit: function(editor, e){
    				e.grid.columns[6].getEditor().setMaxValue(e.record.data.balance);
    			},			
    			edit: function(editor, e){	
    			},
    		},
    		'goodsLayOutWindow>grid button[action=add]': {//新增
    			click: function(){
    				var form = this.getForm().form;
    				var shopId =form.findField('shopId').getValue();	
    				if(!shopId){
    					Ext.Msg.alert("提示",'请选择出库店面！');
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
			    				if(record.data.balance==0)return;
			    				if(store.find("goodsId", record.data.goodsId)>-1)return;
			    				record.data.amount =1;
			    				array.push(record.data);
			    			});
			    			store.add(array);
			    		}
		    		}).show();
    			
    			}
    		},
    		'goodsLayOutWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'goodsLayOutWindow button[action=confirm]': {//保存
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
					
					Ext.Msg.confirm("提示", "确定是否出库？", function(bt){
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
    	var url= URL_MEMBER_GOODSBILLDETAIL_OUTCODE;
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
			url: URL_MEMBER_GOODSBILLDETAIL_OUTSTORE,
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