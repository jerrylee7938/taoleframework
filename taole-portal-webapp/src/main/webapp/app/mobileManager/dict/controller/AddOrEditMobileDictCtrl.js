Ext.define('Taole.mobileManager.dict.controller.AddOrEditMobileDictCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.mobileManager.dict.view.AddOrEditMobileDictWindow'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'addOrEditMobileDictWindow>form'
    },
    {
    	 ref: 'window',
         selector: 'addOrEditMobileDictWindow'
    }
    ],
    statics:{
    	isInit: false//是否已初始化，防止事件重复注册监听
    },
    init: function() {
    	if(Taole.mobileManager.dict.controller.AddOrEditMobileDictCtrl.isInit)return;
    	
    	Taole.mobileManager.dict.controller.AddOrEditMobileDictCtrl.isInit = true;
    	this.control({
    		'addOrEditMobileDictWindow': {
    			afterrender: function(win){
    				var form = this.getForm().form;
    				if(win.isEdit){
    					this.getMobileDict(win.dictId, function(data){
    						form.findField('id').setReadOnly(true);
    						form.findField('id').setFieldStyle("background-color:#EEEEE0;background-image: none;");
    						var content = data.items[0];
							for(i in content){
								if(form .findField(i)){
									form .findField(i).setValue(content[i]);
								}
							}
    					}, null, this)
    				}
    			}
    		},
    		'addOrEditMobileDictWindow button[action=save]': {//保存
    			click: function(){
					if(!this.getForm().form.isValid())return;
					var isEdit = this.getWindow().isEdit;
					var dictId =this.getWindow().dictId
					var dictMobileSeg = this.getForm().getValues();
					this.saveMobileDict(dictMobileSeg, isEdit,dictId, function(){
						this.getWindow().close();
						//刷新表格数据
						var grids = Ext.ComponentQuery.query("mobileDictPanel>grid");
						if(grids&&grids.length>0){
							grids[0].store.load();
							grids[0].getSelectionModel().deselectAll()
						}
					}, null, this);
    			}
    		},
    		'addOrEditMobileDictWindow button[action=cancel]': {//取消
    			click: function(){			    	
					this.getWindow().close();
    			}
    		}
    	});
    },
    getMobileDict: function(dictId, successFn, failureFn, scope){
    	Ext.Ajax.request({
			url: Mobile_QUERY+"?id="+dictId,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data.result_object);
				}else{
					Ext.Msg.alert('提示','获取手机号详情失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取手机号地详情失败", failureFn, this);
			},
			scope: this
		});
    },
    saveMobileDict: function(dict, isEdit, dictId, successFn, failureFn, scope){
		var url;
		if(isEdit){//修改
			url = Ext.util.Format.format(Mobile_UPDATE, dictId);
		}else{//新增
			url = Mobile_CREATE;
		}
		Ext.Ajax.request({
			url: url,
			jsonData: dict,
			method:'post',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					Ext.Msg.alert("提示", "手机号信息保存成功", successFn, this);
				}else{
					Ext.Msg.alert('提示','手机号信息保存失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "手机号信息保存失败", failureFn, this);
			},
			scope: scope||this
		});
    },
});