/**
 * 选择用户ctrl
 */
Ext.define('Taole.common.userCardChoose.controller.UserCardChooseCtrl', {
    extend: 'Ext.app.Controller',
    views: ['Taole.common.userCardChoose.view.UserCardChooseWindow'],
    refs: [
    {
    	 ref: 'window',
         selector: 'userCardChooseWindow'
    },
    {
    	 ref: 'form',
         selector: 'userCardChooseWindow > form'
    },
    {
    	 ref: 'grid',
         selector: 'userCardChooseWindow > grid'
    }
    ],
    statics:{
    	isInit: false//是否已初始化，防止事件重复注册监听
    },
    init: function() {
    	if(Taole.common.userCardChoose.controller.UserCardChooseCtrl.isInit)return;
    	
    	Taole.common.userCardChoose.controller.UserCardChooseCtrl.isInit = true;
    	
    	this.control({
    		'userCardChooseWindow>form': {
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
    		'userCardChooseWindow>form button[action=query]': {//查询
    			click: function(){
    			//	this.query();
    				this.getGrid().store.loadPage(1);
    			}
    		},
    		'userCardChooseWindow>form button[action=reset]': {//重置
    			click: function(){
    				this.getForm().getForm().reset();
    			}
    		},
    		'userCardChooseWindow>grid': {
    			afterrender: function(gridpanel){
    				var win = this.getWindow();
    				var telphone =win.telphone;
    				this.getForm().form.findField("telphone").setValue(telphone);
    				
    				gridpanel.store.on("beforeload", function(store){
    				//	store.proxy.extraParams["telphone"] = telphone;
    					appendParam(this.getForm(), store);
    				}, this);
    			},
    			itemdblclick: function(view, record,item, index, e,eOpts ){
    				this.getGrid().getSelectionModel().deselectAll();
    				this.getGrid().getSelectionModel().select(record);
    				this.confirm();
    			}
    		},
    		'userCardChooseWindow button[action=confirm]': {//确定
    			click: function(){
    				this.confirm();
    			}
    		},
    		'userCardChooseWindow button[action=cancel]': {//取消
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
			Ext.Msg.alert("提示", "请选择会员卡");
		}else if(chooseGoodsWin.allowMultiSelect){//允许选择多个用户
			var goods = new Array();
			Ext.each(selRows, function(record){
				goods.push(record.getUser());
			});
			if(chooseGoodsWin.afterChooseFn.call(chooseGoodsWin.scope,goods)!==false){//回调函数没有返回false
				chooseGoodsWin.close();
			}
		}else if(selRows.length>1){
			Ext.Msg.alert("提示", "只能选择一个会员卡号");
		}else{
			if(chooseGoodsWin.afterChooseFn.call(chooseGoodsWin.scope, selRows[0].getUser())!==false){//回调函数没有返回false
				chooseGoodsWin.close();
			}
		}
    }
});