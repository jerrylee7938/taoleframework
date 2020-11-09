Ext.define('Taole.user.userInfoManager.controller.CardApplyCtrl', {
    extend: 'Ext.app.Controller',
    views: [
    	"Taole.user.userInfoManager.view.CardApplyWindow"
    ],
    refs: [
    {
    	 ref: 'window',
         selector: 'cardApplyWindow'
    },
    {
    	 ref: 'form',
         selector: 'cardApplyWindow>form'
    }
    ],
    statics:{
    	isInit: false    
    },
    init: function() {  
    	if(Taole.user.userInfoManager.controller.CardApplyCtrl.isInit)return;

    	Taole.user.userInfoManager.controller.CardApplyCtrl.isInit = true;
    	
    	this.control({
    		'cardApplyWindow': {
    			afterrender: function(win){
    				var cardId = win.cardId;
    				var isView = win.isView;
    				var isRetrieve = win.isRetrieve;
    				var form = this.getForm().form;
    				

					var addBtn= Ext.getCmp("t123");
					addBtn.el.createChild({
			            cls: Ext.baseCSSPrefix + 'form-file-input',
			            tag: 'input',
			            type: 'file',
			            style : 'width:100%;font-size:12px',
			            size: 1
			        }).on('change', function(){
			        	var URL = URL_MEMBER_FILE_UPDATE+"?type=1";
			        	var xhr = new XMLHttpRequest(); // 初始化XMLHttpRequest
			        	xhr.onreadystatechange=state_Change;
			        	xhr.open('post', URL, true);
			        	var fd = new FormData(); // 这里很关键，初始化一个FormData，并将File文件发送到后台
			        	fd.append("file", this.dom.files[0]);
			        	fd.append("type", 1);
			        	xhr.send(fd);   		
			        	function state_Change(){
			    			if (xhr.readyState==4){// 4 = "loaded"
			    			  if (xhr.status==200){// 200 = "OK"
			    			   	 	var data = Ext.decode(xhr.responseText);
			    					if(getResp(data)){
			    						if(data.success==true){
			    							var url = data.url;
			    	 						var image = Ext.getCmp("avatar");
											image.value =data.path;
											image.setSrc(url);
			    						}else{
			    							 Ext.Msg.alert('提示','上传失败！！');
			    						}
			    					}
			    			   } else{
			    			   	 	resultData = xhr.statusText;
			    			   	 Ext.Msg.alert('提示','上传失败！！'+resultData);
			    			   	 	
			    			    }
			    			 }
			    		}
			        });
				
    			}
    		},
    		"cardApplyWindow>form button[action=paizhao]":{
    			click:function(){
    				this.getPricture();
    			}
    		},
    		'cardApplyWindow>form field[name=operatorShopId]':{//店铺
    			change: function(combox){
    				var val = combox.getValue();
    				var cardId = this.getForm().form.findField("cardId");
    				cardId.setValue();
    				var store = cardId.getStore();
    				store.proxy.url = URL_MEMBER_CARD_SHOP_QUERY+'?objectType=1&shopId='+val;
					store.load();
    			}
    		},
    		'cardApplyWindow>form field[id=cardId]':{//卡
    			focus: function(combox){
    				var operatorShopId = this.getForm().form.findField("operatorShopId").getValue();
    				if(operatorShopId){
    					var cardId = this.getForm().form.findField("cardId");
        				var store = cardId.getStore();
        				if(store.getCount()==0){Ext.Msg.alert("提示", "无法识别的店面ID！");}
    					}else{
    						Ext.Msg.alert("提示", "请先选择店铺！");
    					}
    				},
    		},
    		'cardApplyWindow button[action=confirm]':{//确定
    			click: function(){			
    				if(!this.getForm().form.isValid())return;
					var appAdPosition = this.getForm().getValues();
					var main_Image = Ext.getCmp("avatar");
					appAdPosition.avatar = main_Image.value;
					showWaitMsg();
					this.save(appAdPosition, function(data){
						hideWaitMsg();
						if(data.code==1){
							Ext.Msg.alert("提示","办卡成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("userInfoPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
							},this)
						}else{
							Ext.Msg.alert('提示','办卡失败!<br/>'+data.description);
						}
					}, null, this);
    			}
    		},
    		'cardApplyWindow button[action=print]':{//确定
    			click: function(){			
    				if(!this.getForm().form.isValid())return;
					var appAdPosition = this.getForm().getValues();
					showWaitMsg();
					this.save(appAdPosition, function(data){
						hideWaitMsg();
						if(data.code==1){
							Ext.Msg.alert("提示","办卡成功！", function(){
								this.getWindow().destroy();
								var grid =  Ext.ComponentQuery.query("userInfoPanel>grid")[0];
								grid.store.load();
								grid.getSelectionModel().deselectAll();//取消选中行
								var userBillId = data.result;
								console.log(userBillId);
								this.getConsumeDetail(userBillId);	
							},this)
						}else{
							Ext.Msg.alert('提示','办卡失败!<br/>'+data.description);
						}
					}, null, this);
    			}
    		},
    		'cardApplyWindow>form field[name=cardId]':{//卡名称
    	
    			change: function(combox){
    				var value = combox.getValue();
    				console.log(combox)
    				var comboxData = combox.displayTplData[0];
    				var form = this.getForm().form;	
    				console.log(comboxData)
    				if(value){
    					for(i in comboxData){
    						if(form.findField(i)){
    							if(i!='operator'){
    								form.findField(i).setValue(comboxData[i]);
    							}
    						}
    						if(comboxData.cardType =='QXK'){
    							form.findField('totalNum').setValue('--');
    						}else if (comboxData.cardType =='JCK') {
    							form.findField('cardExpireName').setValue('--');
							}
    					}
    					var now = new Date();
    					var day = comboxData.periodOfValidity
    					var endtDay = Ext.Date.add(now, Ext.Date.DAY, day);
    					var endDayFormat = Ext.Date.format(endtDay, 'Y-m-d');
    					form.findField('deadline').setValue(endDayFormat);
    					form.findField('payMoney').setValue(comboxData.money);
    				}
    				
    			}
    		},
    		'cardApplyWindow button[action=cancel]':{//取消
    			click: function(){
    				this.getWindow().close();
    			}
    		},
    		'cardApplyWindow button[action=close]':{//关闭
    			click: function(){
    				this.getWindow().close();
    			}
    		}
    	});
    },
    print:function(data){
    	
    	var result =data.result;
    	var balanceNum =result.balanceNum;
    	if(result.cardType == 'QXK'){balanceNum='--'}
    	var dy_wap = 'style="display: flex;flex-direction: row;font-size: 11px;padding: 2px 0px;margin:0px"';
    	var dy_left ='style="width: 52px;text-align: right;margin:0px;"';
    	var dy_right ='style="padding-left: 5px;flex: 1;margin:0px;"';
    	var tr_td = 'style=" border :1px solid black;font-size:11px;margin:0px;"';
    	var hr_css = 'style="margin-top: 20px; width: 100%; border: 1px dashed black;"';
    	
    	var dayinc = '<p class="title" style="text-align:center;font-size:14px;">'+result.shopName+'</p>';
    //	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>消费单号:</span><span class="dy_right" '+dy_right+'>'+result.userBillNo+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>时间:</span><span class="dy_right" '+dy_right+'>'+result.createTime+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>卡号:</span><span class="dy_right" '+dy_right+'>'+result.cardNo+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>姓名:</span><span class="dy_right" '+dy_right+'>'+result.username+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>充值:</span><span class="dy_right" '+dy_right+'>'+result.consumeMoney+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>卡级别:</span><span class="dy_right" '+dy_right+'>'+result.cardName+'</span></div>';
    	dayinc += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>次数:</span><span class="dy_right" '+dy_right+'>'+balanceNum+'</span></div>';

    	var dayindb =' <div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>有效期到:</span><span class="dy_right" '+dy_right+'></span></div>';
    	dayindb += '<div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>地址:</span><span class="dy_right" '+dy_right+'>'+result.shopAddress+'</span></div>';
    	dayindb +=' <div class="dy_wap" '+dy_wap+'><span class="dy_left" '+dy_left+'>电话:</span><span class="dy_right" '+dy_right+'>'+result.shopTel+'</span></div> <hr '+hr_css+'>';
    	
    	var dayinctn = '<div class="dy_ctn" style="width: 200px;padding: 5px; 0px;margin:0px">'+dayinc+dayindb+'</div>';
    	
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
    	var url= Ext.util.Format.format(URL_MEMBER_USERBILL_APPLY_GOODSINFO, userBillId);;
    	Ext.Ajax.request({
			url: url+"?userCardId="+userBillId,
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
    getPricture:function(){

		var picture_window = Ext.getCmp("picture_window");
		if(!picture_window){       
			picture_window = Ext.create('Ext.window.Window',{
				title: '拍摄照片',
				height:350,
				id:'picture_window',
				width:600,
				resizable:false,
				modal:true,
				layout:'fit',
				html:'<video class="lf" style="float: left;margin-right: 20px;" id="video" width="240" height="240" autoplay></video><canvas class="lf" style="display:block; float: left;margin-right: 20px;" id="canvas" width="240" height="240"></canvas>',
				buttonAlign:'center',
				buttons:[{
					text:'拍摄',
					handler:function(){
						var context = document.getElementById("canvas").getContext("2d");
				        context.drawImage(video, 0, 0, 240, 240);
				        var img = document.getElementById("canvas").toDataURL("image/png");
					}
				},{
					text:'保存',
					handler:function(){

				        var data=document.getElementById("canvas").toDataURL("image/png");
				        data = data.split(',')[1];
				        //数据格式转换
				        data = window.atob(data);
				        var ia = new Uint8Array(data.length);
				        for (var i = 0; i < data.length; i++) {
				            ia[i] = data.charCodeAt(i);
				        }
				        var blob = new Blob([ia], { type: 'image/jpeg' });
				        //2.提交到服务器
				        var fd = new FormData();
				        fd.append('file', blob);
				        fd.append('type', 1);

				        //提交到服务器
				        var xhr = new XMLHttpRequest();
				        xhr.open('post',URL_MEMBER_FILE_UPDATE, true);
				        xhr.onreadystatechange = function () {
			    			if (xhr.readyState==4){// 4 = "loaded"
				    			  if (xhr.status==200){// 200 = "OK"
				    			   	 	var data = Ext.decode(xhr.responseText);
				    					if(getResp(data)){
				    						if(data.success==true){
				    							var url = data.url;
				    	 						var image = Ext.getCmp("avatar");
												image.value =data.path;
												image.setSrc(url);
												picture_window.destroy();
				    						}else{
				    							 Ext.Msg.alert('提示','上传失败！！');
				    						}
				    					}
				    			   } else{
				    			   	 	resultData = xhr.statusText;
				    			   	 Ext.Msg.alert('提示','上传失败！！'+resultData);
				    			    }
				    			 }
				    		}
				        xhr.send(fd);
					}
				},{
					text:'取消',
					handler:function(){
						picture_window.destroy();
					}
				}]
			})
		}
		picture_window.show();
		
//		var mediaStreamTrack;
//		navigator.getUserMedia = navigator.getUserMedia ||
//        navigator.webkitGetUserMedia ||
//        navigator.mozGetUserMedia;
//		if (navigator.getUserMedia) {
//		    navigator.getUserMedia({ audio: true, video: { width: 240, height: 270 } },
//		            function(stream) {
//		                mediaStreamTrack = typeof stream.stop === 'function' ? stream : stream.getTracks()[1];
//		                video.src = (window.URL || window.webkitURL).createObjectURL(stream);
//		            },
//		            function(err) {
//		                console.log("The following error occurred: " + err.name);
//		            }
//		    );
//		} else {
//		    console.log("getUserMedia not supported");
//		}	
		let constraints = {
	            video: {width: 240, height: 240},
	            audio: true
	        };
		
	        //获得video摄像头区域
	        let video = document.getElementById("video");
	        //这里介绍新的方法，返回一个 Promise对象
	        // 这个Promise对象返回成功后的回调函数带一个 MediaStream 对象作为其参数
	        // then()是Promise对象里的方法
	        // then()方法是异步执行，当then()前的方法执行完后再执行then()内部的程序
	        // 避免数据没有获取到
	        let promise = navigator.mediaDevices.getUserMedia(constraints);
	        promise.then(function (MediaStream) {
	            video.srcObject = MediaStream;
	            video.play();
	        });

		
    },
     save: function(appAdPosition, successFn, failureFn, scope){
     	var url = URL_MEMBER_MEMBERCARD_CREATE;
		Ext.Ajax.request({
			url: url,
			jsonData: appAdPosition,
			timeout: 300000,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "办卡失败", failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "办卡失败，请联系管理员", failureFn, this);
			},
			scope: scope||this
		});
    }
});