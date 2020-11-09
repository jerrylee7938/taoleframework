Ext.define('Taole.reportSheet.accountManager.controller.AccountCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.reportSheet.accountManager.view.AccountPanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'accountPanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'accountPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'accountPanel>form button[action=query]': {//查询
    			click: function(){		
					this.getGrid().store.loadPage(1);
				
    			}
    		},
    		'accountPanel>form button[action=reset]': {//重置
    			click: function(){	
					this.getForm().form.reset();
    			}
    		},
    		'accountPanel>form field[name=name]': {//用户
    			focus: function(){	
    				var form = this.getForm().form;
    				Ext.create("Taole.common.userChoose.controller.UserChooseCtrl").init();
			    	Ext.create("widget.userChooseWindow",{
			    		scope: this,
			    		allowMultiSelect: false,
			    		afterChooseFn: function(chooseData){
			    			console.log(chooseData);
			    			form.findField('name').setValue(chooseData.name);
			    			form.findField('userId').setValue(chooseData.userId);
			    		}
		    		}).show();
    			}
    		},
    		'accountPanel>grid button[action=export]': {//导出交易流水excel
    			click: function(){
    				var appAdPosition = this.getForm().getValues();
    				var str =''
    				for(i in appAdPosition){
    					str+=i+'='+appAdPosition[i]+'&'
    				}
    				Ext.Msg.confirm("提示", "确定要导出交易流水报表数据吗？", function(bt){
						if(bt=='yes'){
	    					window.open(URL_MEMBER_USERBILL_EXPORT+"?"+str,"_self");
						}
					}, this); 
    			}
    		},
    		'accountPanel>grid button[action=export2]': {//导出商品excel
    			click: function(){
    				var appAdPosition = this.getForm().getValues();
    				var str =''
    				for(i in appAdPosition){
    					str+=i+'='+appAdPosition[i]+'&'
    				}
    				Ext.Msg.confirm("提示", "确定要导出商品流水信息吗？", function(bt){
						if(bt=='yes'){
	    					window.open(URL_MEMBER_USERBILL_EXPORT_GOODS+"?"+str,"_self");
						}
					}, this); 
    			}
    		},
    		'accountPanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		}, 		
    	});
    },
});