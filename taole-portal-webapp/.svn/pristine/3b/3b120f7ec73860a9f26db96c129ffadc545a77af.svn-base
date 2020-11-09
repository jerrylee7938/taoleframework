Ext.define('Taole.mobileManager.dict.controller.MobileDictCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.mobileManager.dict.view.MobileDictPanel'
    ],
    refs: [
	    {
	    	 ref: 'panel',
	         selector: 'mobileDictPanel'
	    },
	    {
	    	 ref: 'grid',
	         selector: 'mobileDictPanel>grid'
	    },
	     {
	    	 ref: 'form',
	         selector: 'mobileDictPanel>form'
	    }
    ],
    init: function () {
        this.control({
    		'mobileDictPanel': {
    			afterrender: function(panel){}
    		},
    		'mobileDictPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.load();
    			}
    		},
    		'mobileDictPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'mobileDictPanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'mobileDictPanel>grid button[action=add]': {
    			click: function(){
    				this.edit();
    			}
    		},
    		'mobileDictPanel>grid button[action=update]': {
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要修改的记录信息！");
    					return;
    				}
    				if(selRows.length > 1){
    					Ext.Msg.alert("提示", "请选择一条要修改的记录信息！");
    					return;
    				}
    				this.edit(selRows[0].data.id, true);
    			}
    		},
    		'mobileDictPanel>grid button[action=del]': {
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要删除的记录信息！");
    					return;
    				}
    				var ids = new Array();
    				Ext.each(selRows, function(record){
    					ids.push("id="+record.data.id);
    				});
    				Ext.Msg.confirm("提示", "您确定要删除选择的手机号信息吗？", function(btn){
    					if(btn == "yes"){
    						this.delMobileDict(ids, function(){
    							this.getGrid().store.load();
    						}, null);
    					}
    				}, this);
    			}
    		}
    	})
    },
    edit: function(dictId, isEdit){
    	Ext.create("Taole.mobileManager.dict.controller.AddOrEditMobileDictCtrl").init();
    	Ext.create("widget.addOrEditMobileDictWindow",{
    		dictId: dictId,
    		isEdit: isEdit
    	}).show();
    },
    delMobileDict: function(dictIds, successFn, failureFn){
    	Ext.Ajax.request({
			url: Mobile_DELETE + "?"+dictIds.join("&"),
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					Ext.Msg.alert("提示", "手机号信息删除成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','手机号信息删除失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "手机号信息删除失败", failureFn, this);
			},
			scope: this
		});
    }
});