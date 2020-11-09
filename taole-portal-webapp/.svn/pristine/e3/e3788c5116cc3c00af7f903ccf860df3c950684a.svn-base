Ext.onReady(function(){
		Ext.QuickTips.init();
		Ext.Loader.setConfig({
		    enabled: true
		});
		Ext.tip.QuickTipManager.init();
		Ext.MessageBox.show({
			title : '系统提示',
			msg : '正在加载桌面数据，请稍候......',
			progressText : 'processing now,please wait...',
			width : 300,
			wait : true,
			waitConfig : {
				interval : 150
			}
		});
		Ext.Ajax.request({
			url: $service.portal+'/us.Desktop/collection/tree?userId='+userId,
			method:'post',
			dataType:'json',
			success:function(resp){
				Ext.MessageBox.hide();
				if(!getResp(resp)){
					var text = resp.responseText;
					var data = eval("("+text+")");
					Ext.Msg.alert('提示','获取桌面数据失败:'+data.result_desc);
				}else{
					var text = resp.responseText;
					var data = eval("("+text+")");
					function getDesktopPanel(){
						var desktopPanel = Ext.getCmp('desktopPanel');
						if(!desktopPanel){
							desktopPanel = Ext.create('Ext.tab.Panel', {
								id:'desktopPanel',
								region:'center',
							    width: 600,
							    height: 120,
							    enableTabScroll: true,              //选项卡过多时，允许滚动
							    defaults: { autoScroll: true },
							    items: []
							});
						}
						getTabPanel(data);
						return desktopPanel;
					}
					Ext.create('Ext.Viewport',{
				        layout: {
				            type: 'border',
				            padding: 5
				        },
				        defaults: {
				            split: true
				        },
				        items:[getDesktopPanel()]
				    });
				}
			},
			failure:function(resp){
				Ext.Msg.alert('提示','获取桌面数据失败!');
			}
		});
		
		function getTabPanel(data){
			var tabPanel = Ext.getCmp('desktopPanel');
			if(tabPanel){
				var children = data[0].children;
				for(var i=0; i<children.length; i++){
					var record = children[i];
					var tabId = record.id;
					var tab = tabPanel.getComponent(tabId);
					if(!tab){
						tab = tabPanel.add(new Ext.Panel({
							id:record.id,
							title:record.text,
							layout: 'absolute'
						}));
					}
					
					getTabGrid(record.children,record.id);
				}
			}
			
		}
		
		function getTabGrid(children,fatherId){
			if(!children)return;
			var tab = Ext.getCmp(fatherId);
			if(tab){
				var x = 20;
				var y = 15;
				var forNum = 0;
				var grid;
				var gridAry = new Array();
				for(var i=0; i<children.length; i++){
					var record = children[i];
					if(record.checked){
						gridAry.push(record);
					}
				}
				
				for(var i=0; i<gridAry.length; i++){
						var record = gridAry[i];
						forNum = i + 1;
						grid = tab.add(
						new Ext.grid.Panel({
							x:x,
							y:y,
							width:510,
							id:record.id,
							region:'center',
							columnLines: true,//各列之间是否有分割线
							height:200,
							store:Ext.create('Ext.data.Store', {
						        fields:['text','url','id','menuId','menuName'],
						        autoLoad:true,
						        proxy: {
						            type: 'ajax',
						            url:record.url,
						            reader: {
						                root: 'items',
						                totalProperty: 'total'
						            },
						            simpleSortMode: true
						        }
				           }),
				           columns:[{
				        	   text:record.text,
							   dataIndex:'text',
							   align:'left',
							   width:508,
							   sortable: false,
							   menuDisabled:true,
							   renderer:function(v,meta,record){
							   		if(record.data.url){
							   			return '<a href="javascript:void(0);" onclick="">'+v+'</a>';
							   		}else{
							   			return v;
							   		}
					            }
						   },{
							   dataIndex:'url',
							   hidden:true
						   },{
							   dataIndex:'id',
							   hidden:true
						   }],
						   plugins: [
						        {
						            ptype: 'datatip',
						            tpl: '操作说明：{text}'
						        }
						    ],
						   listeners: {  
								'cellclick':function(grid,rowIndex,columnIndex,e ){
									var selRow = grid.getSelectionModel().getSelection()[0];
									if(selRow.data.url){
										TaskMeg(selRow.data.url,selRow.data.menuId,selRow.data.menuName);
									}
								}   
						   }
						})
					);
					if((forNum%2)==0){
						y = y+210;
						x = 20;
					}else{
						x = x+530;
					}
					
				}
			}
		}
		window.TaskMeg = function(url,menuId,menuName){
			commonOpenMenu(url, menuId,menuName);
//			var taskMeg_win = Ext.getCmp("taskMeg_win");
//			if(!taskMeg_win){
//				taskMeg_win = Ext.create("Ext.window.Window",{
//					title:title,
//					id:'taskMeg_win',
//					maximized:true,
//					modal:true,
//					maximizable: true,          //是否可以最大化
//			        minimizable: true,          //是否可以最小化
//			        closable: true,            //是否可以关闭
//					html:'<iframe src='+url+' width="100%"  height="100%" frameborder="0" scrolling="auto"></iframe>'
//				});
//			}
//			taskMeg_win.show();
		}
});