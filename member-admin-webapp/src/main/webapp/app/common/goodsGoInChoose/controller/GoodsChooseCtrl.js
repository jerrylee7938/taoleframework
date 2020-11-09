/**
 * 选择用户ctrl
 */
Ext.define('Taole.common.goodsGoInChoose.controller.GoodsChooseCtrl', {
    extend: 'Ext.app.Controller',
    views: ['Taole.common.goodsGoInChoose.view.GoodsChooseWindow'],
    refs: [
    {
    	 ref: 'window',
         selector: 'goodsChooseWindow'
    },
    {
    	 ref: 'form',
         selector: 'goodsChooseWindow > form'
    },
    {
    	 ref: 'grid',
         selector: 'goodsChooseWindow > grid'
    }
    ],
    statics:{
    	isInit: false//是否已初始化，防止事件重复注册监听
    },
    init: function() {
    	if(Taole.common.goodsGoInChoose.controller.GoodsChooseCtrl.isInit)return;
    	
    	Taole.common.goodsGoInChoose.controller.GoodsChooseCtrl.isInit = true;
    	
    	this.control({
    		'goodsChooseWindow>form': {
    			beforerender: function(formpanel){
    				    				
    			},
	   			afterrender: function(){
	//    				this.getGrid().store.on("beforeload", function(store){
	//    					var name = this.getForm().form.findField("nameLike").getValue();
	//    					store.proxy.extraParams["nameLike"] = name;
	//    					var telPhone = this.getForm().form.findField("telPhone").getValue();
	//    					store.proxy.extraParams["telPhone"] = telPhone;
	//    				}, this);
	//    				this.query();
	   			}
    		},
    		'goodsChooseWindow>form button[action=query]': {//查询
    			click: function(){
    			//	this.query();
    				this.getGrid().store.loadPage(1);
    			}
    		},
    		'goodsChooseWindow>form button[action=reset]': {//重置
    			click: function(){
    				this.getForm().getForm().reset();
    			}
    		},
    		'goodsChooseWindow>grid': {
    			afterrender: function(gridpanel){
    				var win = this.getWindow();
    				var shopId= win.shopId;
    				gridpanel.store.on("beforeload", function(store){
    					store.proxy.extraParams["shopId"] = shopId;
			        	appendParam(this.getForm(), store);
    				}, this);
    			},
    			itemdblclick: function(view, record,item, index, e,eOpts ){
    				this.getGrid().getSelectionModel().deselectAll();
    				this.getGrid().getSelectionModel().select(record);
    				this.confirm();
    			}
    		},
    		'goodsChooseWindow button[action=confirm]': {//确定
    			click: function(){
    				this.confirm();
    			}
    		},
    		'goodsChooseWindow button[action=cancel]': {//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	})
    },
    /**
     * 查询用户
     */
    query: function(){
		this.getGrid().store.load().loadPage(1);
    },
    /**
     * 确定
     */
    confirm: function(){
    	var chooseGoodsWin = this.getWindow();
    	var selRows = this.getGrid().getSelectionModel().getSelection();
		if(selRows.length==0){
			Ext.Msg.alert("提示", "请选择商品");
		}else if(chooseGoodsWin.allowMultiSelect){//允许选择多个用户
			var goods = new Array();
			Ext.each(selRows, function(record){
				goods.push(record);
			});
			if(chooseGoodsWin.afterChooseFn.call(chooseGoodsWin.scope,goods)!==false){//回调函数没有返回false
				chooseGoodsWin.close();
			}
		}else if(selRows.length>1){
			Ext.Msg.alert("提示", "只能选择一个用户");
		}else{
			if(chooseGoodsWin.afterChooseFn.call(chooseGoodsWin.scope, selRows[0])!==false){//回调函数没有返回false
				chooseGoodsWin.close();
			}
		}
    }
});