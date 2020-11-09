Ext.define('Taole.goods.goodsWarehousing.controller.GoodsWarehousingCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.goods.goodsWarehousing.view.GoodsWarehousingPanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'goodsWarehousingPanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'goodsWarehousingPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'goodsWarehousingPanel>grid button[action=add]': {//新增
    			click: function(){

    				var store = this.getGrid().store;
    				Ext.create("Taole.common.goodsChoose.controller.GoodsChooseCtrl").init();
			    	Ext.create("widget.goodsChooseWindow",{
			    		scope: this,
			    		allowMultiSelect: true,
			    		afterChooseFn: function(chooseData){
			    			var array = new Array();
			    			Ext.each(chooseData, function(record){
			    				if(store.find("id", record.data.id)>-1)return;
			    				array.push(record.data);
			    			});
			    			store.add(array);
			    		}
		    		}).show();
    			
    			}
    		},
    		'goodsWarehousingPanel button[action=confirm]': {//保存
    			click: function(){
    				var form = this.getForm();
    				var grid = this.getGrid();
					if(!form.form.isValid())return;
					var shopGoodsData = form.getValues();
					
					var ids = [];
					grid.store.each(function(record){
						ids.push(record.data.id);
					});
					shopGoodsData.goodsId = ids.join(",");
					
					Ext.Msg.confirm("提示", "确定是否入库？", function(bt){
						if(bt=='yes'){
							this.save(shopGoodsData, function(data){
								
								if(data.code==1){
									Ext.Msg.alert("提示","保存成功！", function(){
										grid.store.removeAll();
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
    save: function(shopGoodsData, successFn, failureFn, scope){
		Ext.Ajax.request({
			url: ORDER_SHOPGOODS_CREATE_URL,
			jsonData: shopGoodsData,
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