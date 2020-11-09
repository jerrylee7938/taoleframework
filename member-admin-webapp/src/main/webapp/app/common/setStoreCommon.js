
    function setStoreToCard(cardId,type){
			var user_window = Ext.getCmp("user_window");
			if(!user_window){       
				user_window = Ext.create('Ext.window.Window',{
					title: '设置可售店面',
					height:300,
					id:'user_window',
					width:600,
					resizable:false,
					modal:true,
					layout:'fit',
					items:[{
						id:'add_user_form',
						xtype:'form',
						frame:true,
						style:{
							'border':'0px'
						},
						border:false,
						autoScroll:true,
						layout:'absolute',
						items:[
						/*       {
							xtype:'textfield',
							fieldLabel:'关键字',
							labelWidth:55,
							labelAlign:'right',
							id:'keyword',
							x:5,
							y:5,
							width:200
						},{
							xtype:'button',
							text:'查询',
							x:210,
							y:5,
							width:80,
							handler:function(){
								var keyword = Ext.getCmp('keyword').getValue();
								var fieldset = Ext.getCmp('userList');
								fieldset.removeAll();
								fieldset.add(addUserTo('userList',600,keyword,"",""));
								fieldset.doLayout();
								
							}
						},{
							xtype:'button',
							text:'重置',
							x:295,
							y:5,
							width:80,
							handler:function(){
								Ext.getCmp('keyword').setValue("");
							}
						},
						*/{
							xtype:'fieldset',
							title:'店面列表',
							id:'userList',
							width:545,
							x:20,
							y:5,
							items:[]
						}]
					}],
					buttonAlign:'center',
					buttons:[{
						text:'保存',
						handler:function(){
							var userArray =[];
							var user_form =  Ext.getCmp("add_user_form");
							var checkArray = user_form.query("checkbox");
							for(var j =0; j<checkArray.length; j++){
								var checkbox = checkArray[j];
								var value = checkbox.getValue();
								if(checkbox.boxLabel !='全选'){
									if(value){
										userArray.push(checkbox.id);
									}
								}
							}
							var setData = {'objectId':cardId,'shopIds':userArray.join(','),'objectType':type};
							
							Ext.Ajax.request({
								url:URL_MEMBER_SHOPSET_CREATE,
								jsonData:setData,
								method:'post',
								success:function(resp, opts){
									var r = eval("(" + resp.responseText + ")");
									if (r.code != 1) {
										Ext.Msg.alert("提示",'保存失败：' + r.description);
									} else {
										user_window.destroy();
										Ext.Msg.alert("提示",'保存成功' );
									}
								},
								failure:function(resp, opts){
									Ext.Msg.alert("提示",'设置销售店面失败：' + resp.responseText);
								}
							})
						}
					},{
						text:'取消',
						handler:function(){
							user_window.destroy();
						}
					}]
				})
			}
			user_window.show();
			
			var user_form = Ext.getCmp("userList");
			user_form.add(addStore('userList',600,cardId));
			user_form.doLayout();
    }

window.addStore = function(_id,width,id,type){
	var columnWidth = 1/Math.floor((width-100)/121);
	var count = Math.floor((width-100)/121);
	var url ="";
	var _items = [];
	$.ajax({
		type:'GET',
		url:URL_MEMBER_STORE_SHOPSET+'?objectId='+id,
		dataType:'json',
		async:false,
		success:function(data){
			var items = data.result;
			var length = items.length;
			if(length > 0){
					var columnLayout = [];
					columnLayout ={
						layout:'column',
						frame:true,
						style:{
							'border':'0px'
						},
						items:[{
							columnWidth:1,
							width:121,
							xtype:'checkbox',
							itemId:_id,
							type:'',
							margin:'-3px 0px 1px 0px',
							boxLabel:'全选',
							listeners:{
								change:function(check, newVal, oldVal){
									var id = check.itemId;
									var type = check.type;
									var fieldSet = Ext.getCmp(id);
									var checkArray = fieldSet.query("checkbox");
									for(var i = 0; i < checkArray.length; i++){
										var checkbox = checkArray[i];
										if(check != checkbox && type == ''){
											checkbox.setValue(newVal);
										}
									}
								}
							}
						}]	
					};
					_items.push(columnLayout);
					for(var i = 0; i < length; i++){
						var item = items[i];
						var bool = item.setStatus==1? true:false;
						if(i%count == 0){
							columnLayout = {
								layout:'column',
								frame:true,
								style:{
									'border':'0px'
								},
								items:[{
									columnWidth:columnWidth,
									width:121,
									xtype:'checkbox',
									checked:bool,
									id:item.shopId,
									margin:'-3px 0px 1px 0px',
									boxLabel:item.name,
									listeners:{
										change:function(check, newValu){
											changeStoreStatus(check, newValu);
										}
									}
								}]
							};
						}else{
							columnLayout.items.push({
								columnWidth:columnWidth,
								width:121,
								id:item.shopId,
								checked:bool,
								xtype:'checkbox',
								margin:'-3px 0px 1px 0px',
								boxLabel:item.name,
								listeners:{
									change:function(check, newValu){
										changeStoreStatus(check, newValu);
									}
								}
							});
						}
						if((i+1)%count == 0 || i == length-1){
							_items.push(columnLayout);
						}
					}
				
				
				
			}
		},
	});
	return _items;
}

function changeStoreStatus(check, newValu){
	var fieldSet = check.ownerCt.ownerCt;
	var checkbox = fieldSet.query("checkbox")[0];
	checkbox.type = 'on';
	if(!newValu){
		checkbox.setValue(newValu);
	}
	checkbox.type = '';
}



