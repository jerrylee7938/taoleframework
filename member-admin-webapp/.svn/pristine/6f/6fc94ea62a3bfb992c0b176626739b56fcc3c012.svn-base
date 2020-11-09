Ext.define('Taole.goods.outOfStorageManager.controller.OutOfStorageCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.goods.outOfStorageManager.view.OutOfStoragePanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'outOfStoragePanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'outOfStoragePanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'outOfStoragePanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
    			}
    		},
    		'outOfStoragePanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'outOfStoragePanel>form field[name=name]': {//商品名称
    			focus: function(){			    	
    				var form = this.getForm().form;
    				Ext.create("Taole.common.goodsAllChoose.controller.GoodsChooseCtrl").init();
			    	Ext.create("widget.goodsChooseWindow",{
			    		scope: this,
			    		allowMultiSelect: false,
			    		afterChooseFn: function(chooseData){
			    			form.findField('name').setValue(chooseData.name);
			    			form.findField('goodsId').setValue(chooseData.goodsId);
			    		}
		    		}).show();
    			}
    		},
    		'outOfStoragePanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'outOfStoragePanel>grid button[action=delivery]': {//商品出库
    			click: function(){
    				this.delivery();
    			}
    		},
    		'outOfStoragePanel>grid button[action=goIn]': {//商品入库
    			click: function(){
    				this.goIn();
    			}
    		},		
    	});
    },
    delivery:function(){
		Ext.create("Taole.goods.outOfStorageManager.controller.GoodsLayOutCtrl").init();
    	Ext.create("widget.goodsLayOutWindow",{
    		scope: this,
    		afterChooseFn: function(){
    			
    		},
    	}).show();
    },
    goIn:function(){
		Ext.create("Taole.goods.outOfStorageManager.controller.GoodsGoInCtrl").init();
    	Ext.create("widget.goodsGoInWindow",{
    		scope: this,
    		afterChooseFn: function(){
    			
    		},
    	}).show();
    }
});