Ext.define('Taole.reportSheet.reportSheetManager.controller.ReportSheetCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	'Taole.reportSheet.reportSheetManager.view.ReportSheetPanel'
    ],
    refs: [
    {
    	 ref: 'form',
         selector: 'reportSheetPanel>form'
    },
    {
    	 ref: 'grid',
         selector: 'reportSheetPanel>grid'
    }
    ],
    init: function() {
    	this.control({
    		'reportSheetPanel>form button[action=query]': {//查询
    			click: function(){			    	
					this.getGrid().store.loadPage(1);
					var val = this.getForm().form.findField('dimens').getRawValue();
    				if(val){
    					var grid= Ext.ComponentQuery.query("reportSheetPanel")[0].down('grid');
    					grid.columns[5].setText(val);
    					if(val =='交易类型'){
    						grid.columns[4].setText('交易笔数');
    					}else if(val == '商品明细'){
    						grid.columns[4].setText('销售数量');
						}else {
							grid.columns[4].setText('支付笔数');
						}
    				}
    			}
    		},
    		'reportSheetPanel>form button[action=reset]': {//重置
    			click: function(){			    	
					this.getForm().form.reset();
    			}
    		},
    		'reportSheetPanel>grid': {
    			afterrender: function(gridpanel){
    				gridpanel.store.on("beforeload", function(store){
			        	appendParam(this.getForm(), store);
    				}, this);
    			}
    		}, 	
    		'reportSheetPanel>grid button[action=print]': {//打印
    			click: function(){	
    				var formData = this.getForm().getValues();
    				
    				this.getQuery(formData, function(data){
						if(data.result.total>0){
							//1版
//							var result =data.items;
//					    	var objectName ='商品明细';
//					    	var statAmount ='销售数量';
//					    	if(formData.dimens == 'goods'){
//					    		objectName ='商品明细';
//						    	statAmount ='销售数量';
//					    	}else if (formData.dimens == 'tradeType') {
//					    		objectName ='交易类型';
//						    	statAmount ='交易笔数';
//							}else if (formData.dimens == 'payType') {
//					    		objectName ='支付类型';
//						    	statAmount ='支付笔数';
//							}
//					    	var dy_ctn_cs = 'style="padding: 5px 5px;text-align: center;"';
//					    	var dy_title_cs = 'style="font-size:16px;  font-weight: 600;  padding-bottom: 10px;  margin: 0px; text-align: center"';
//					    	var dy_table_cs = 'style="border-collapse :collapse ;  width: 100%; margin: 0px;"';
//					    	var dy_tr_cs = 'style="border :1px solid black;  margin: 0px; text-align: center;font-size:12px"';
//					    	if(result){
//					    		var tablecaption ='<caption><col style="width:5%"><col style="width:20%"><col style="width:25%"><col style="width:15%"><col style="width:15%"><col style="width:20%"></caption>';
//					    		var tableTitle = '<thead><tr><th '+dy_tr_cs+'>序号</th><th '+dy_tr_cs+'>日期</th><th '+dy_tr_cs+'>店面</th><th '+dy_tr_cs+'>营业总额（元）</th><th '+dy_tr_cs+'>'+statAmount+'</th><th '+dy_tr_cs+'>'+objectName+'</th></tr></thead>';
//					    		var tableBody = '<tbody>';
//					    		for(var i =0;i<result.length;i++){
//					        		var contentList = result[i];
//					        		var num = i+1;
//					        		tableBody += '<tr><td '+dy_tr_cs+'>'+num+'</td><td '+dy_tr_cs+'>'+contentList.statDate+'</td><td '+dy_tr_cs+'>'+contentList.shopName+'</td><td '+dy_tr_cs+'>'+contentList.statMoney+'</td><td '+dy_tr_cs+'>'+contentList.statAmount+'</td><td '+dy_tr_cs+'>'+contentList.objectName+'</td></tr>'
//					        	};
//					        	tableBody +='</tbody>';
//					    	}
//					    	var title = '<p '+dy_title_cs+'>'+formData.statDate+' 日流水报表</p>';
//					    	var table = '<table '+dy_table_cs+'>'+tablecaption+tableTitle+tableBody+'</table">'
//					    	var dayinctn = '<div '+dy_ctn_cs+'>'+title+table+'</div>';
							
							
							//2版
							var result =data.result; //改版
					    	var dy_ctn_cs = 'style="padding: 5px 0px;text-align: center;"';
					    	var dy_title_cs = 'style="font-size:12px;  font-weight: 500;  padding-bottom: 3px;  margin: 0px; text-align: center"';
					    	var dy_table_cs = 'style="border-collapse :collapse ;  width: 100%; margin: 0px;"';
					    	var dy_tr_cs = 'style="border :1px solid black;  margin: 0px;font-size:10px"';
					    	var dy_tr_title_cs = 'style="border :1px solid black; font-weight: 600; margin: 0px;font-size:10px"';
					    	if(result){
					    		var tablecaption ='<caption><col style="width:50%"><col style="width:50%"></caption>';
					    		var tableTitle = '<thead><tr><th '+dy_tr_cs+'>项目</th><th '+dy_tr_cs+'>金额（元）</th></tr></thead>';
					    		var tableBody = '<tbody>';
					    		tableBody +='<tr><td '+dy_tr_title_cs+'>消费总额</td><td '+dy_tr_cs+'>'+result.total+'元</td></tr>'
					    		var demins= result.demins;
					    		for(var i =0;i<demins.length;i++){
					        		var contentList = demins[i];
					        		tableBody += '<tr><td '+dy_tr_title_cs+'>'+contentList.name+'</td><td '+dy_tr_cs+'></td></tr>';
					        		var printItems =contentList.items;
					        		for(var j =0;j<printItems.length;j++){
					        			var list =printItems[j]
					        			tableBody += '<tr><td '+dy_tr_cs+'>'+list.name+'</td><td '+dy_tr_cs+'>'+list.money+'元</td></tr>';
					        		}
					        	};
					        	tableBody +='</tbody>';
					        	var tableFoot = '<tfoot><tr><th '+dy_tr_cs+' colspan="2" align="center">统计日期：'+result.reportDate+'</th></tr></tfoot>';
					    	}
					    	var title = '<p '+dy_title_cs+'>'+result.shopName+'</p><p '+dy_title_cs+'>'+result.reportName+'</p><p '+dy_title_cs+'>打印时间：'+result.createTime+'</p>';
					    	var table = '<table '+dy_table_cs+'>'+tablecaption+tableTitle+tableBody+tableFoot+'</table">';
					    	var dayinctn = '<div '+dy_ctn_cs+'>'+title+table+'</div>';
					    	
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
							
						}else{
							Ext.Msg.alert("提示", "暂无日运营数报表据！");
						}
					}, null, this);
		
    			}
    		},
    		'reportSheetPanel>grid button[action=export]': {//导出
    			click: function(){
    				var appAdPosition = this.getForm().getValues();
    				var str =''
    				for(i in appAdPosition){
    					str+=i+'='+appAdPosition[i]+'&'
    				}
    				Ext.Msg.confirm("提示", "确定要导出报表数据吗？", function(bt){
						if(bt=='yes'){
	    					window.open(URL_MEMBER_REPORT_EXPORT+"?circleType=day&"+str,"_self");
						}
					}, this); 
    			}
    		},
    		'reportSheetPanel>form field[name=dimens]': {//汇总方式
    			change: function(combox){ 
    				
    			}
    		},  		
    	});
    },
    getQuery: function(billdata,successFn, failureFn, scope){
    	var url= URL_MEMBER_REPORT_PRINT+'?circleType=day'+'&statDate='+billdata.statDate+'&shopId='+billdata.shopId;
    	Ext.Ajax.request({
			url: url,
			timeout: 300000,
			success: function(response){
				var data = Ext.decode(response.responseText);
				successFn.call(this, data);
			},
			failure: function(){
				Ext.Msg.alert("提示", "打印失败，请联系管理员！", failureFn, this);
			},
			scope: scope
		});
    },
});