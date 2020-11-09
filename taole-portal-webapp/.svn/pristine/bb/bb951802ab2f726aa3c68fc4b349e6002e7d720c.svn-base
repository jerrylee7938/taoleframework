Ext.define('Taole.appManager.appVer.controller.AppVerCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.appManager.appVer.view.AppVerPanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'appVerPanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'appVerPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'appVerPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.load();
    			}
    		},
    		'appVerPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'appVerPanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		},
    		'appVerPanel>grid button[action=add]': {//新增
    			click: function(){
    				this.edit();
    			}
    		},
    		'appVerPanel>grid button[action=update]': {//修改
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要修改的记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录修改");
    					return;
    				}
    				
    				if(selRows[0].data.status == "PRD"){
    					var publishTime = Ext.Date.format(new Date(selRows[0].data.publishTime), "Y-m-d H:i:s");
						var nowDate = Ext.Date.format(new Date(), "Y-m-d H:i:s");
						if(dateCompare(publishTime, nowDate) == "2" || dateCompare(publishTime, nowDate) == "3"){
							Ext.Msg.alert("提示", "对不起，已经上线且已发布的版本不能修改！");
    						return;
						}
    				}
    				this.edit(selRows[0].data.id, true);
    			}
    		},
    		'appVerPanel>grid button[action=remove]': {//删除
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要删除的数据");
    					return;
    				}
    				
    				var ids = [];
    				var errorIds = [];
    				for(var i=0; i<selRows.length; i++){
    					var record = selRows[i];
    					if(record.data.status == "PRD"){
    						errorIds.push(i+1);
    					}else{
    						ids.push("id="+record.data.id);
    					}
    				}
    				
    				if(errorIds.length > 0){
    					Ext.Msg.alert("提示", "对不起，已经上线的版本不能删除！<br>第"+errorIds.join("，")+"行");
    					return;
    				}
    				
    				Ext.Msg.confirm("提示", "确定删除选中的APP版本吗？", function(bt){
						if(bt=='yes'){
							this.del(ids.join("&"), function(){
		    					this.getGrid().store.load();
		    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    				});
						}
					}, this);  
    			}
    		},
    		'appVerPanel>grid button[action=retrieve]': {//详情
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录");
    					return;
    				}
    				
    				this.edit(selRows[0].data.id, true, true);
//    				Ext.create("Taole.appManager.appVer.controller.DetailCtrl").init();
//			    	Ext.create("widget.detailWindow",{
//			    		scope: this,
//			    		afterChooseFn: function(){
//			    		},
//			    		appVerId: selRows[0].data.id
//		    		}).show();
    			}
    		},
    		'appVerPanel>grid button[action=channel]': {//配置渠道
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录");
    					return;
    				}
    				Ext.create("Taole.appManager.appVer.controller.ChooseChannelCtrl").init();
    				Ext.create("Taole.appManager.appVer.view.ChooseChannelWindow", {
    					appVerId: selRows[0].data.id,
    					type:selRows[0].data.systemType
    				}).show();
    			}
    		},
    		'appVerPanel>grid button[action=Offline]': {//客户端下线
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录");
    					return;
    				}
    				
    				if(selRows[0].data.status == "PRD"){
    					var publishTime = Ext.Date.format(new Date(selRows[0].data.publishTime), "Y-m-d H:i:s");
						var nowDate = Ext.Date.format(new Date(), "Y-m-d H:i:s");
						if(dateCompare(publishTime, nowDate) == "2" || dateCompare(publishTime, nowDate) == "3"){
							var appVer = selRows[0].data;
							appVer.status = "TEST";
							Ext.Msg.confirm("提示", "确定要下线此版本的客户端？", function(btn){
								if(btn == "yes"){
									this.save(appVer, function(data){
										if(data.id){
											Ext.Msg.alert("提示","下线成功！", function(){
												var grid =  Ext.ComponentQuery.query("appVerPanel>grid")[0];
												grid.store.load();
												grid.getSelectionModel().deselectAll();//取消选中行
											},this)
										}
									}, null, this);
								}
							},this);
    						return;
						}
    				}
    				
    				Ext.Msg.alert("提示", "对不起，只能下线已经上线且已发布的版本！");
    			}
    		},
    		'appVerPanel>grid button[action=iosPass]': {//修改
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择上线的安装包");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录操作");
    					return;
    				}
    				
    				if((selRows[0].data.status == "PRD")){
    					var publishTime = Ext.Date.format(new Date(selRows[0].data.publishTime), "Y-m-d H:i:s");
						var nowDate = Ext.Date.format(new Date(), "Y-m-d H:i:s");
						if(dateCompare(publishTime, nowDate) == "2" || dateCompare(publishTime, nowDate) == "3"){
							Ext.Msg.alert("提示", "对不起，已经上线且已发布的版本不能修改！");
    						return;
						}else{
					     	var id= selRows[0].data.id;
					     	var url = Ext.util.Format.format(APP_VER_UPDATE, id);
					     	var data=selRows[0].data
					     	var now=new Date();
					     	if(selRows[0].data.systemType =='ios')
					     		now.setDate(now.getDate()+1);
					     	var nowDate = Ext.Date.format(now, "Y-m-d H:i:s");
					     	data.publishTime =nowDate
							Ext.Ajax.request({
								url: url,
								jsonData: data,
								success: function(response){
									var data = Ext.decode(response.responseText);
									if(getResp(data)){
										if(data.id){
											Ext.Msg.alert("提示","修改成功！", function(){
												var grid =  Ext.ComponentQuery.query("appVerPanel>grid")[0];
												grid.store.load();
												grid.getSelectionModel().deselectAll();//取消选中行
											},this)
										}else{
											Ext.Msg.alert("提示", "版本信息修改失败");
										}
									}else{
										Ext.Msg.alert("提示", "版本信息保存失败");
									}
								},
								failure: function(){
									Ext.Msg.alert("提示", "版本信息保存失败", failureFn, this);
								}
							});
						}
    				}else{
    					Ext.Msg.alert("提示", "请选择上线状态的安装包");
    				}
    		
    			}
    		},
    		'appVerPanel>grid button[action=pass]': {//测试通过
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要操作的数据");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录");
    					return;
    				}
    			
    				
    				var verid = selRows[0].data.id;
    				var dataJson ={
    					"testStatus":"1",
    					"verid":verid,
    				}
    				
    				this.pass(dataJson, function(){
    					this.getGrid().store.load();
    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    	 });
					 
    				
    			}
    		},
    		'appVerPanel>grid button[action=noPass]': {//测试不通过
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择要操作的数据");
    					return;
    				}
    				

    				var verid = selRows[0].data.id;
    				var dataJson ={
    					"testStatus":"0",
    					"verid":verid,
    				}

    				
    				this.pass(dataJson, function(){
    					this.getGrid().store.load();
    					this.getGrid().getSelectionModel().deselectAll();//取消选中行
		    	 });
					 
    				
    			}
    		},
    		'appVerPanel>grid button[action=downloadurl]': {//下载地址
    			click: function(){
    				var selRows = this.getGrid().getSelectionModel().getSelection();
    				if(selRows.length == 0){
    					Ext.Msg.alert("提示", "请选择记录");
    					return;
    				}
    				
    				if(selRows.length >1){
    					Ext.Msg.alert("提示", "请选择一条记录");
    					return;
    				}
    				Ext.create("Taole.appManager.appVer.controller.AppChanlCtrl").init();
    				Ext.create("Taole.appManager.appVer.view.AppChanlWindow", {
    					appVerId: selRows[0].data.id
    				}).show();
    			}
    		}
    	});
    },
    edit: function(appVerId, isView, isRetrieve){
		Ext.create("Taole.appManager.appVer.controller.AddOrEditCtrl").init();
	    	Ext.create("widget.addOrEditWindow",{
	    		scope: this,
	    		afterChooseFn: function(){
	    		},
	    		appVerId: appVerId,
	    		isView: isView,
	    		isRetrieve: isRetrieve
    	}).show();
    },
    save: function(appVer, successFn, failureFn, scope){
     	var url = APP_VER_CREATE;
     	if(appVer.id){
     		url = Ext.util.Format.format(APP_VER_UPDATE, appVer.id);
     	}
		Ext.Ajax.request({
			url: url,
			jsonData: appVer,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "下线失败", failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "下线失败", failureFn, this);
			},
			scope: scope||this
		});
    },
    del: function(appVerIds, successFn, failureFn){
    	Ext.Ajax.request({
			url: APP_VER_DELETE+"?"+appVerIds,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					Ext.Msg.alert("提示", "APP版本删除成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','APP版本失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "APP版本失败", failureFn, this);
			},
			scope: this
		});
    },
    pass: function(appVerIds, successFn, failureFn){
    	var url = Ext.util.Format.format(APP_VER_PASS, appVerIds.verid);
    	Ext.Ajax.request({
			url: url,
			jsonData:appVerIds,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					Ext.Msg.alert("提示", "APP版本测试操作成功！", successFn, this);
				}else{
					Ext.Msg.alert('提示','APP版本测试操作失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "APP版本测试操作失败", failureFn, this);
			},
			scope: this
		});
    }
});