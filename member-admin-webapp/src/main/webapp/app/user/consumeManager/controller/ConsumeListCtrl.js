Ext.define('Taole.user.consumeManager.controller.ConsumeListCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.user.consumeManager.view.ConsumeListWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'consumeListWindow'
    },
    {
    	 ref: 'form',
         selector: 'consumeListWindow>form'
    },
    {
    	 ref: 'grid',
         selector: 'consumeListWindow>grid'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.user.consumeManager.controller.ConsumeListCtrl.isInit)return;
    	Taole.user.consumeManager.controller.ConsumeListCtrl.isInit = true;
    	this.control({
    		'consumeListWindow': {
    			afterrender: function(win){  
    				var consumer = win.consume;
    				var consumeStore = win.consumeStore;
    				var form = this.getForm().form;
    				var store = this.getGrid().store;
    				form.findField('swipeAmount').setMaxValue(consumer.blanceNum);
    				for(i in consumer){
						if(form.findField(i)){
							form.findField(i).setValue(consumer[i]);
						}
					};
					var array =[];
					var consumeMoney =0;
					consumeStore.each(function(record){
						consumeMoney +=record.data.consumeMoney;
						array.push(record.data);
					});
					form.findField('consumeMoney').setValue(consumeMoney);
	    			store.add(array);
    			}
    		},
    		'consumeListWindow>grid': {		
    			edit: function(editor, e){
    				var form = this.getForm().form;
    				var store = this.getGrid().store;
    				var consumeMoney =0;
    				store.each(function(record){
    					consumeMoney +=record.data.consumeMoney;
					});
    				form.findField('consumeMoney').setValue(consumeMoney); 				
    			},
    		},
    		'consumeListWindow button[action=confirm]':{//确认
    			click: function(){
    				var form = this.getForm();
    				var grid = this.getGrid();
					if(!form.form.isValid())return;
					var consumeData = form.getValues();
					
					var goodsValues = [];
					grid.store.each(function(record){
						goodsValues.push({goodsId:record.data.goodsId,amount:record.data.amount});
					});
					consumeData.goodsValues = goodsValues;
					

					this.save(consumeData, function(data){
						if(data.code==1){
							Ext.Msg.alert("提示","消费成功！", function(){
								grid.store.removeAll();
								this.getWindow().destroy();
								var grid_a =  Ext.ComponentQuery.query("consumePanel>grid")[0];
								var form =  Ext.ComponentQuery.query("consumePanel>form")[0].form;
								this.getCode(function(data){
									if(data.code !=1){
										Ext.Msg.alert('提示',data.description);
									}
									var code=data.result;
									form.findField('userBillNo').setValue(code);	
								},null,this);
								Ext.getCmp('menberInfo').form.reset();
								grid_a.store.removeAll();
							},this)
						}else{
							Ext.Msg.alert('提示','保存失败!<br/>'+data.description);
						}
					}, null, this);
				
    			}
    		},
    		'consumeListWindow button[action=print]':{//确认并打印
    			click: function(){
    				var form = this.getForm();
    				var grid = this.getGrid();
					if(!form.form.isValid())return;
					var consumeData = form.getValues();
					
					var goodsValues = [];
					grid.store.each(function(record){
						goodsValues.push({goodsId:record.data.goodsId,amount:record.data.amount});
					});
					consumeData.goodsValues = goodsValues;
					

					this.save(consumeData, function(data){
						if(data.code==1){
							Ext.Msg.alert("提示","消费成功！", function(){
								grid.store.removeAll();
								this.getWindow().destroy();
								var grid_a =  Ext.ComponentQuery.query("consumePanel>grid")[0];
								var form =  Ext.ComponentQuery.query("consumePanel>form")[0].form;
								
								var userBillId = data.result.userBillId;
								this.getConsumeDetail(userBillId);	
								this.getCode(function(data){
									if(data.code !=1){
										Ext.Msg.alert('提示',data.description);
									}
									var code=data.result;
									form.findField('userBillNo').setValue(code);	
								},null,this);
							//	this.print();
								Ext.getCmp('menberInfo').form.reset();
								grid_a.store.removeAll();
							},this)
						}else{
							Ext.Msg.alert('提示','保存失败!<br/>'+data.description);
						}
					}, null, this);
				
    			}
    		},
    		'consumeListWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
    print:function(data){
    	
    	var result =data.result;
    	
    	var dy_wap = 'style="display: flex;flex-direction: row;font-size: 11px;padding: 2px 0px;margin:0px"';
    	var dy_left ='style="width: 52px;text-align: right;margin:0px;"';
    	var dy_right ='style="padding-left: 5px;flex: 1;margin:0px;"';
    	var tr_td = 'style=" border :1px solid black;font-size:11px;margin:0px;"';
    	var hr_css = 'style="margin-top: 20px; width: 100%; border: 1px dashed black;"';
    	
    	var dayinc = '<p class="title" style="text-align:center;font-size:14px;">'+result.shopName+'</p>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>消费单号:</span><span class="dy_right" '+dy_right+'>'+result.userBillNo+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>时间:</span><span class="dy_right" '+dy_right+'>'+result.createTime+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>卡号:</span><span class="dy_right" '+dy_right+'>'+result.cardNo+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>姓名:</span><span class="dy_right" '+dy_right+'>'+result.username+'</span></div>';
    //	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>划卡:</span><span class="dy_right" '+dy_right+'>'+result.createTime+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>现金:</span><span class="dy_right" '+dy_right+'>'+result.consumeMoney+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>划次:</span><span class="dy_right" '+dy_right+'>'+result.swipeAmount+'</span></div>';
    //	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>余额:</span><span class="dy_right" '+dy_right+'>'+result.createTime+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>剩余次数:</span><span class="dy_right" '+dy_right+'>'+result.balanceNum+'</span></div>';
    	
    	
    	var dayintable ='';
    	var goodsList = data.result.goodsList;
    	if(goodsList){
    		var dayinsp =' <caption><col style="width:50%"><col style="width:20%"><col style="width:30%"></caption>';
        	dayinsp +='<thead><tr><th '+tr_td+'>项目</th><th '+tr_td+'>数量</th><th '+tr_td+'>金额</th></tr></thead>'
        	var dayinzj = '<tfoot><tr><td align="center" colspan="2" '+tr_td+'>总 计</td><td align="center" '+tr_td+'>'+result.consumeMoney+'</td></tr></tfoot>';
        	var dayintbody = '<tbody>'
    		for(var i =0;i<goodsList.length;i++){
        		var goods = goodsList[i];
        		var goodsName = goods.goodsName;
        		var amount = goods.amount;
        		var prive = goods.prive;
        		dayintbody+='<tr><td align="center" '+tr_td+'>'+goodsName+'</td><td align="center" '+tr_td+'>'+amount+'</td><td align="center" '+tr_td+'>'+prive+'</td></tr>'
        	};
        	dayintbody +='</tbody>';
        	dayintable ='<table style="width: 180px;border-collapse :collapse ;font-size: 12px;margin:0px;padding:0px;">'+dayinsp+dayintbody+dayinzj+'</table>';

    	}
    	
    	
    	
    //	var dayindb =' <div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>结束时间</span><span class="dy_right" '+dy_right+'>'+result.balanceNum+'</span></div>';
    	var dayindb ='<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>操作员:</span><span class="dy_right" '+dy_right+'>'+result.operator+'</span></div>';
    	dayindb += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>地址:</span><span class="dy_right" '+dy_right+'>'+result.shopAddress+'</span></div>';
    	dayindb +=' <div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>电话:</span><span class="dy_right" '+dy_right+'>'+result.shopTel+'</span></div> <hr '+hr_css+'>';
    	
    	var dayinctn = '<div class="dy_ctn" style="width: 200px;padding: 5px; 0px;margin:0px">'+dayinc+dayintable+dayindb+'</div>';
    	
		var iframe = document.createElement("iframe");
		iframe.setAttribute("id", "iframe");
		iframe.style.padding='0px';
		iframe.style.margin='0px'
		document.body.appendChild(iframe);
		var doc = document.getElementById("iframe").contentDocument || document.frames["iframe"].document;  
		var ii=document.getElementById("iframe")|| document.frames["iframe"];
		doc.body.innerHTML = dayinctn;  
		doc.body.style.padding='0px';
		doc.body.style.margin='0px';
	    ii.focus();
	    ii.contentWindow.print();
    
    },
    getConsumeDetail:function(userBillId,successFn, failureFn, scope){
    	var url= Ext.util.Format.format(URL_MEMBER_USERBILL_GOODSINFO, userBillId);;
    	Ext.Ajax.request({
			url: url,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					this.print(data);
				}else{
					Ext.Msg.alert('提示','打印失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "打印失败，请联系管理员！", failureFn, this);
			},
			scope: scope||this
		});
    
    },
    getCode: function(successFn, failureFn, scope){
    	var url= URL_MEMBER_USERBILL_CONSUME_CODE;
    	Ext.Ajax.request({
			url: url,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','生成单号失败!<br/>'+data.code+":"+data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "生成单号失败，请联系管理员！", failureFn, this);
			},
			scope: scope||this
		});
    },
    save: function(shopGoodsData, successFn, failureFn, scope){
		Ext.Ajax.request({
			url: URL_MEMBER_USERBILL_CONSUME,
			jsonData: shopGoodsData,
			method:'POST',
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "消费失败<br/>"+data.code + "：" + data.description, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "消费失败", failureFn, this);
			},
			scope: scope||this
		});
    }		  
});