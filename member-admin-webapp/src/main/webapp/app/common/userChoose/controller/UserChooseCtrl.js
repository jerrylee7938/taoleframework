/**
 * 选择用户ctrl
 */
Ext.define('Taole.common.userChoose.controller.UserChooseCtrl', {
    extend: 'Ext.app.Controller',
    views: ['Taole.common.userChoose.view.UserChooseWindow'],
    refs: [
    {
    	 ref: 'window',
         selector: 'userChooseWindow'
    },
    {
    	 ref: 'form',
         selector: 'userChooseWindow > form'
    },
    {
    	 ref: 'grid',
         selector: 'userChooseWindow > grid'
    }
    ],
    statics:{
    	isInit: false//是否已初始化，防止事件重复注册监听
    },
    init: function() {
    	if(Taole.common.userChoose.controller.UserChooseCtrl.isInit)return;
    	
    	Taole.common.userChoose.controller.UserChooseCtrl.isInit = true;
    	
    	this.control({
    		'userChooseWindow>form': {
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
    		'userChooseWindow>form button[action=query]': {//查询
    			click: function(){
    			//	this.query();
    				this.getGrid().store.loadPage(1);
    			}
    		},
    		'userChooseWindow>form button[action=reset]': {//重置
    			click: function(){
    				this.getForm().getForm().reset();
    			}
    		},
    		'userChooseWindow>grid': {
    			afterrender: function(gridpanel){
    				var win = this.getWindow();
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			},
    			itemdblclick: function(view, record,item, index, e,eOpts ){
    				this.getGrid().getSelectionModel().deselectAll();
    				this.getGrid().getSelectionModel().select(record);
    				this.confirm();
    			}
    		},
    		'userChooseWindow button[action=confirm]': {//确定
    			click: function(){
    				this.confirm();
    			}
    		},
    		'userChooseWindow button[action=cancel]': {//取消
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
			Ext.Msg.alert("提示", "请选择用户");
		}else if(chooseGoodsWin.allowMultiSelect){//允许选择多个用户
			var goods = new Array();
			Ext.each(selRows, function(record){
				goods.push(record.getUser());
			});
			if(chooseGoodsWin.afterChooseFn.call(chooseGoodsWin.scope,goods)!==false){//回调函数没有返回false
				chooseGoodsWin.close();
			}
		}else if(selRows.length>1){
			Ext.Msg.alert("提示", "只能选择一个用户");
		}else{
			if(chooseGoodsWin.afterChooseFn.call(chooseGoodsWin.scope, selRows[0].getUser())!==false){//回调函数没有返回false
				chooseGoodsWin.close();
			}
		}
    }
});