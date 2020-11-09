Ext.onReady(function(){
	//将用户编号存放到浏览器本地存储
	setLocalStorage();
	//将用户按钮权限数据存放到浏览器本地存储
	userButtonPrivilegeUrl();
});

Ext.Loader.setConfig({enabled: true});
	Ext.Loader.setPath('Ext.ux', $commonRoot + '/extjs4.2/ux');
	Ext.require([
	    'Ext.form.Panel',
	    'Ext.ux.form.ItemSelector',
	    'Ext.ux.enum.EnumComboBox',
	    'Ext.ux.Format',
	    'Ext.ux.FormUtils',
	    'Ext.ux.TypeUtils',
	    'Ext.tip.QuickTipManager',
	    'Ext.ux.ajax.JsonSimlet',
	    'Ext.ux.ajax.SimManager','*',
	    'Ext.ux.DataTip'
	]);
	
window.addFunTo = function(width,appId,moduleId){
	var columnWidth = 1/Math.floor((width-100)/121);
	var count = Math.floor((width-100)/121);
	var typeMap ={"已配功能":[],"可选功能":[]};
	var configObj = {};
	$.ajax({
		type:'GET',
		url:$service.portal+'/us.Module/'+moduleId+'/getFunction',
		dataType:'json',
		async:false,
		success:function(data){
			if(data){
				var list = data;
				typeMap["已配功能"]  = list;
				for(var i=0; i < list.length; i++){
					var item = list[i];
					configObj[item.id] = item.name;
				}
			}
		},
		failure:function(){
			
		}
	});
	
	var _items = [];
	$.ajax({
		type:'GET',
		url:$service.portal+'/us.Function/collection/query?appId='+appId,
		dataType:'json',
		async:false,
		success:function(data){
			var items = data.items;
			var length = items.length;
			if(length > 0){
				var temp_array =[];
				if("已配功能" in typeMap){
					var temp_items =[];
					for(var i=0; i < length; i++){
						var itemId = items[i].functionId;
						if(!(itemId in configObj)){
							temp_items.push(items[i]);
						}
					}
					items = temp_items;
					length = items.length;
					typeMap["可选功能"] =items;
					
					for(var k in typeMap){
						var children = typeMap[k];
						var columnLayout = [];
						_items =[];
						var bool= k == "已配功能"?true:false;
						columnLayout ={
							layout:'column',
							frame:true,
							style:{
								'border':'0px'
							},
							items:[{
								columnWidth:1,
								width:121,
								itemId:k,
								xtype:'checkbox',
								type:'',
								margin:'-3px 0px -1px 0px',
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
						for(var j =0; j < children.length; j++){
							var child = children[j];
							var name = child.functionName;
							if(j%count == 0){
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
										id:child.functionId,
										checked:bool,
										margin:'-3px 0px -1px 0px',
										boxLabel:name,
										listeners:{
											change:function(check, newValu){
												changeStatus(check, newValu);
											}
										}
									}]
								};
							}else{
								columnLayout.items.push({
									columnWidth:columnWidth,
									width:121,
									id:child.functionId,
									xtype:'checkbox',
									checked:bool,
									margin:'-3px 0px -1px 0px',
									boxLabel:name,
									listeners:{
										change:function(check, newValu){
											changeStatus(check, newValu);
										}
									}
								});
							}
							if((j+1)%count == 0 || j == children.length-1){
								_items.push(columnLayout);
							}
						}
						var fieldSet = Ext.create('Ext.form.FieldSet',{
							xtype:'fieldset',
							collapsible:true,
							margin:'5px',
							frame:true,
							id:k,
							title: k,
							layout:'fit',
							items:[]
						})
						fieldSet.add(_items);
						fieldSet.doLayout();
						temp_array.push(fieldSet);
					}
					_items = temp_array;
				}else{
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
							itemId:'fun',
							type:'',
							margin:'-3px 0px -1px 0px',
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
									id:item.functionId,
									margin:'-3px 0px -1px 0px',
									boxLabel:item.functionName,
									listeners:{
										change:function(check, newValu){
											changeStatus(check, newValu);
										}
									}
								}]
							};
						}else{
							columnLayout.items.push({
								columnWidth:columnWidth,
								width:121,
								id:item.functionId,
								xtype:'checkbox',
								margin:'-3px 0px -1px 0px',
								boxLabel:item.functionName,
								listeners:{
									change:function(check, newValu){
										changeStatus(check, newValu);
									}
								}
							});
						}
						if((i+1)%count == 0 || i == length-1){
							_items.push(columnLayout);
						}
					}
					var fieldSet = Ext.create('Ext.form.FieldSet',{
						xtype:'fieldset',
						collapsible:false,
						frame:true,
						style:style = {
							'border':'0px'
						},
						id:'group',
						layout:'fit',
						items:[]
					})
					fieldSet.add(_items);
					fieldSet.doLayout();
					temp_array.push(fieldSet);
					
				}
				
				_items = temp_array;
			}
		},
		failure:function(){
			
		}
	});
	return _items;
}	
	
window.addUserTo = function(_id,width,word,type,id){
	var columnWidth = 1/Math.floor((width-100)/121);
	var count = Math.floor((width-100)/121);
	var url ="";
	var word = word ||"";
	if(word !=""){
		url =$service.portal +'/us.User/collection/query?enabled=true&partWord='+word;
	}else{
		url =$service.portal +'/us.User/collection/query?enabled=true';
	}
	var typeMap ={};
	var configObj ={};
	var id = id ||"";
	if(type=='edit' && id !=""){
		typeMap ={"可分配用户":[],"已分配用户":[]};
		$.ajax({
			type:'GET',
			url:$service.portal+'/us.Group/'+id+'/retrieve',
			dataType:'json',
			async:false,
			success:function(data){
				if(data){
					var list = data.userList;
					typeMap["已分配用户"]  = list;
					for(var i=0; i < list.length; i++){
						var item = list[i];
						configObj[item.id] = item.name;
					}
					
				}
			},
			failure:function(){
				
			}
		});
	}
	var _items = [];
	$.ajax({
		type:'GET',
		url:url,
		dataType:'json',
		async:false,
		success:function(data){
			var items = data.items;
			var length = items.length;
			if(length > 0){
				var temp_array =[];
				if("已分配用户" in typeMap){
					var temp_items =[];
					for(var i=0; i < length; i++){
						var itemId = items[i].id;
						if(!(itemId in configObj)){
							temp_items.push(items[i]);
						}
					}
					items = temp_items;
					length = items.length;
					typeMap["可分配用户"] =items;
					
					for(var k in typeMap){
						var children = typeMap[k];
						var columnLayout = [];
						_items =[];
						var bool= k == "已分配用户"?true:false;
						columnLayout ={
							layout:'column',
							frame:true,
							style:{
								'border':'0px'
							},
							items:[{
								columnWidth:1,
								width:121,
								itemId:k,
								xtype:'checkbox',
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
						for(var j =0; j < children.length; j++){
							var child = children[j];
							var name = bool? child.name:child.realName;
							if(j%count == 0){
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
										id:child.id,
										checked:bool,
										margin:'-3px 0px 1px 0px',
										boxLabel:name,
										listeners:{
											change:function(check, newValu){
												changeStatus(check, newValu);
											}
										}
									}]
								};
							}else{
								columnLayout.items.push({
									columnWidth:columnWidth,
									width:121,
									id:child.id,
									xtype:'checkbox',
									checked:bool,
									margin:'-3px 0px -1px 0px',
									boxLabel:name,
									listeners:{
										change:function(check, newValu){
											changeStatus(check, newValu);
										}
									}
								});
							}
							if((j+1)%count == 0 || j == children.length-1){
								_items.push(columnLayout);
							}
						}
						temp_array.push({
							xtype:'fieldset',
							collapsible:true,
							margin:'5px',
							frame:true,
							id:k,
							title: k,
							layout:'fit',
							items:[_items]
						});
					}
					_items = temp_array;
				}else{
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
									id:item.id,
									margin:'-3px 0px 1px 0px',
									boxLabel:item.realName,
									listeners:{
										change:function(check, newValu){
											changeStatus(check, newValu);
										}
									}
								}]
							};
						}else{
							columnLayout.items.push({
								columnWidth:columnWidth,
								width:121,
								id:item.id,
								xtype:'checkbox',
								margin:'-3px 0px 1px 0px',
								boxLabel:item.realName,
								listeners:{
									change:function(check, newValu){
										changeStatus(check, newValu);
									}
								}
							});
						}
						if((i+1)%count == 0 || i == length-1){
							_items.push(columnLayout);
						}
					}
				}
				
				
			}
		},
		failure:function(){
			
		}
	});
	return _items;
}	
	
window.addGroupTo = function(width,keyword,bool,type){
	var columnWidth = 1/Math.floor((width-100)/121);
	var count = Math.floor((width-100)/121);
	var groupArray =[];
	var keyword = keyword||"";
	var bool = bool||true;
	var url ="";
	if(keyword !=""){
		url =$service.portal +'/us.Group/collection/query?partWord='+keyword+"&page=1&start=0&limit=1000&type="+type;
	}else{
		url =$service.portal +'/us.Group/collection/query?page=1&start=0&limit=1000&type='+type;
	}
	$.ajax({
		type:'GET',
		url:url,
		dataType:'json',
		async:false,
		success:function(data){
			var items = data.items;
			var length = items.length;
			if(length > 0){
				var _items = [];
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
						itemId:'group'+type,
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
								id:item.id,
								margin:'-3px 0px 1px 0px',
								boxLabel:item.name,
								listeners:{
									change:function(check, newValu){
										changeStatus(check, newValu);
									}
								}
							}]
						};
					}else{
						columnLayout.items.push({
							columnWidth:columnWidth,
							width:121,
							id:item.id,
							xtype:'checkbox',
							margin:'-3px 0px 1px 0px',
							boxLabel:item.name,
							listeners:{
								change:function(check, newValu){
									changeStatus(check, newValu);
								}
							}
						});
					}
					if((i+1)%count == 0 || i == length-1){
						_items.push(columnLayout);
					}
				}
				var fieldSet = Ext.create('Ext.form.FieldSet',{
					xtype:'fieldset',
					collapsible:false,
					frame:true,
					style:style = {
						'border':'0px'
					},
					id:'group'+type,
					layout:'fit',
					items:[]
				})
				fieldSet.add(_items);
				fieldSet.doLayout();
				groupArray.push(fieldSet);
			}
		},
		failure:function(){
		
		}
	});
	return groupArray;
}

//分配环境
window.addEnvironmentTo = function(width,bool,type){
	var columnWidth = 1/Math.floor((width-100)/121);
	var count = Math.floor((width-100)/121);
	var groupArray =[];
	var url =Ext.util.Format.format(URL_DICTIONARY, '57365de9acca4ec28a009567708c4e61');;
	$.ajax({
		type:'GET',
		url:url,
		dataType:'json',
		async:false,
		success:function(data){
			var length = data.length;
			if(length > 0){
				var _items = [];
				var columnLayout = [];
				for(var i = 0; i < length; i++){
					var item = data[i];
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
								id:item.value,
								margin:'-3px 0px 1px 0px',
								boxLabel:item.name,
								listeners:{
									change:function(check, newValu){
									//	changeStatus(check, newValu);
										changeRadio(check, newValu);
									}
								}
							}]
						};
					}else{
						columnLayout.items.push({
							columnWidth:columnWidth,
							width:121,
							id:item.value,
							xtype:'checkbox',
							margin:'-3px 0px 1px 0px',
							boxLabel:item.name,
							listeners:{
								change:function(check, newValu){
								//	changeStatus(check, newValu);
									changeRadio(check, newValu);
								}
							}
						});
					}
					if((i+1)%count == 0 || i == length-1){
						_items.push(columnLayout);
					}
				}
				var fieldSet = Ext.create('Ext.form.FieldSet',{
					xtype:'fieldset',
					collapsible:false,
					frame:true,
					style:style = {
						'border':'0px'
					},
					id:'group'+type,
					layout:'fit',
					items:[]
				})
				fieldSet.add(_items);
				fieldSet.doLayout();
				groupArray.push(fieldSet);
			}
		},
		failure:function(){
		
		}
	});
	return groupArray;
}

window.addEnvironmentRadio = function(width,bool,type){
	var columnWidth = 1/Math.floor((width-100)/121);
	var count = Math.floor((width-100)/121);
	var groupArray =[];
	var url =Ext.util.Format.format(URL_DICTIONARY, '57365de9acca4ec28a009567708c4e61');;
	$.ajax({
		type:'GET',
		url:url,
		dataType:'json',
		async:false,
		success:function(data){
			var length = data.length;
			if(length > 0){
				var _items = [];
				var columnLayout = [];
				for(var i = 0; i < length; i++){
					var item = data[i];
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
								inputValue :item.value,
								xtype:'radio',
								id:'radio'+item.value,
								margin:'-3px 0px 1px 0px',
								boxLabel:item.name,
								name:'systy',
								hidden:true
							}]
						};
					}else{
						columnLayout.items.push({
							columnWidth:columnWidth,
							width:121,
							inputValue :item.value,
							xtype:'radio',
							id:'radio'+item.value,
							margin:'-3px 0px 1px 0px',
							boxLabel:item.name,
							name:'systy',
							hidden:true
						
						});
					}
					if((i+1)%count == 0 || i == length-1){
						_items.push(columnLayout);
					}
				}
				var fieldSet = Ext.create('Ext.form.FieldSet',{
					xtype:'fieldset',
					collapsible:false,
					frame:true,
					style:style = {
						'border':'0px'
					},
					id:'radiogroup'+type,
					layout:'fit',
					items:[]
				})
				fieldSet.add(_items);
				fieldSet.doLayout();
				groupArray.push(fieldSet);
			}
		},
		failure:function(){
		
		}
	});
	return groupArray;
}

function changeRadio(check, newValu){
	var idValue = check.id;
	if(newValu){
		Ext.getCmp("radio"+idValue).show();
	}else{
		Ext.getCmp("radio"+idValue).hide();
		Ext.getCmp("radio"+idValue).setValue(false);
	}
}

function getResp(data){
	var flag;
	if(data){
		var result_code = data.result_code;
		
		if (result_code == undefined){
			var text = data.responseText;
			var data = eval("("+text+")");
			if (data!=null){
			  result_code = data.result_code;
			}
		}

		if(result_code != '' && result_code != null && result_code != undefined){
			if(data.result_code!=100){
				flag = false;
			}else{
				flag = true;
			}
		}else{
			flag = true;
		}
	}
	return flag;
}


function changeStatus(check, newValu){
	var fieldSet = check.ownerCt.ownerCt;
	var checkbox = fieldSet.query("checkbox")[0];
	checkbox.type = 'on';
	if(!newValu){
		checkbox.setValue(newValu);
	}
	checkbox.type = '';
}
//授权角色
window.addRoleTo= function(width,type,id){
	var columnWidth = 1/Math.floor((width-100)/121);
	var count = Math.floor((width-100)/121);
	var roleArray =[];
	var typeMap = {};
	var configObj ={};
	var id = id ||"";
	if(type == "edit" && id!=""){
		typeMap ={"可赋予角色":[],"已赋予角色":[]};
		$.ajax({
			type:'GET',
			url:$service.portal+'/us.Group/'+id+'/retrieve',
			dataType:'json',
			async:false,
			success:function(data){
				if(data){
					var list = data.roleList;
					typeMap["已赋予角色"]  = list;
					for(var i=0; i < list.length; i++){
						var item = list[i];
						configObj[item.id] = item.name;
					}
					
				}
			},
			failure:function(){
				
			}
		});
	}else{
		$.ajax({
			type:'GET',
			url:$service.portal+'/fw.System/com.taole.usersystem.domain.Role$Type/enums',
			dataType:'json',
			async:false,
			success:function(data){
				if(data && data.length > 0){
					for(var i = 0; i < data.length; i++){
						var item = data[i];
						typeMap[item.name] = item.local;
					}
				}
			},
			failure:function(){
				
			}
		});
	}
	
	$.ajax({
		type:'GET',
		url:$service.portal +'/us.Role/collection/query?page=1&start=0&limit=1000',
		dataType:'json',
		async:false,
		success:function(data){
			var items = data.items;
			var temp_items = [];
			var length = items.length;
			if("已赋予角色" in typeMap){
				for(var i=0; i < length; i++){
					var itemId = items[i].id;
					if(!(itemId in configObj)){
						temp_items.push(items[i]);
					}
				}
				items = temp_items;
				length = items.length;
				typeMap["可赋予角色"] =items;
				for(var k in typeMap){
					var children = typeMap[k];
					var columnLayout = [];
					var _items = [];
					var bool= k == "已赋予角色"?true:false;
					columnLayout ={
						layout:'column',
						frame:true,
						style:{
							'border':'0px'
						},
						items:[{
							columnWidth:1,
							width:121,
							itemId:k,
							xtype:'checkbox',
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
					for(var j =0; j < children.length; j++){
						var child = children[j];
						if(j%count == 0){
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
									id:child.id,
									checked:bool,
									margin:'-3px 0px 1px 0px',
									boxLabel:child.name,
									listeners:{
										change:function(check, newValu){
											changeStatus(check, newValu);
										}
									}
								}]
							};
						}else{
							columnLayout.items.push({
								columnWidth:columnWidth,
								width:121,
								id:child.id,
								xtype:'checkbox',
								checked:bool,
								margin:'-3px 0px 1px 0px',
								boxLabel:child.name,
								listeners:{
									change:function(check, newValu){
										changeStatus(check, newValu);
									}
								}
							});
						}
						if((j+1)%count == 0 || j == children.length-1){
							_items.push(columnLayout);
						}
					}
					var fieldSet = Ext.create('Ext.form.FieldSet',{
						xtype:'fieldset',
						collapsible:true,
						margin:'5px',
						frame:true,
						id:k,
						title: k,
						layout:'fit',
						items:[]
					})
					fieldSet.add(_items);
					fieldSet.doLayout();
					roleArray.push(fieldSet);
				}
			}else{
				var obj = {};
				for(var i = 0; i < length; i++){
					var item = items[i];
					var type = item["type"];
					if(type in obj){
						obj[type].push(item);
					}else{
						obj[type]=[item];
					}
				}
				
				for(var k in obj){
					var children = obj[k];
					var columnLayout = [];
					var _items = [];
					columnLayout ={
						layout:'column',
						frame:true,
						style:{
							'border':'0px'
						},
						items:[{
							columnWidth:1,
							width:121,
							itemId:k,
							xtype:'checkbox',
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
					for(var j =0; j < children.length; j++){
						var child = children[j];
						if(j%count == 0){
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
									id:child.id,
									margin:'-3px 0px 1px 0px',
									boxLabel:child.name,
									listeners:{
										change:function(check, newValu){
											changeStatus(check, newValu);
										}
									}
								}]
							};
						}else{
							columnLayout.items.push({
								columnWidth:columnWidth,
								width:121,
								id:child.id,
								xtype:'checkbox',
								margin:'-3px 0px 1px 0px',
								boxLabel:child.name,
								listeners:{
									change:function(check, newValu){
										changeStatus(check, newValu);
									}
								}
							});
						}
						if((j+1)%count == 0 || j == children.length-1){
							_items.push(columnLayout);
						}
					}
					var name ="";
					if(k in typeMap){
						name = typeMap[k];
					}else{
						name = k;
					}
					var fieldSet = Ext.create('Ext.form.FieldSet',{
						xtype:'fieldset',
						collapsible:true,
						margin:'5px',
						frame:true,
						id:k,
						title: name,
						layout:'fit',
						items:[]
					})
					fieldSet.add(_items);
					fieldSet.doLayout();
					roleArray.push(fieldSet);
				}
			}
			
		},
		failure:function(){
		
		}
	});
	return roleArray;
}

window.addPerTo = function(width,type,id){
	var columnWidth = 1/Math.floor((width-100)/121);
	var count = Math.floor((width-100)/121);
	var typeMap = {};
	var perArray =[];
	var configObj ={};
	var id = id ||"";
	if(type='edit' && id !=""){
		typeMap ={"可授予权限":[],"已授予权限":[]};
		$.ajax({
			type:'GET',
			url:$service.portal+'/us.Group/'+id+'/retrieve',
			dataType:'json',
			async:false,
			success:function(data){
				if(data){
					var list = data.privilegeList;
					typeMap["已授予权限"]  = list;
					for(var i=0; i < list.length; i++){
						var item = list[i];
						configObj[item.id] = item.name;
					}
					
				}
			},
			failure:function(){
				
			}
		});
	}else{
		$.ajax({
			type:'GET',
			url:$service.portal+'/fw.System/com.taole.usersystem.domain.Privilege$Type/enums',
			dataType:'json',
			async:false,
			success:function(data){
				if(data && data.length > 0){
					for(var i = 0; i < data.length; i++){
						var item = data[i];
						typeMap[item.name] = item.local;
					}
				}
			},
			failure:function(){
				
			}
		});
	}
	
	$.ajax({
		type:'GET',
		url:$service.portal + '/us.Privilege/collection/query?page=1&start=0&limit=1000',
		dataType:'json',
		async:false,
		success:function(data){
			var items = data.items;
			var temp_items = [];
			var length = items.length;
			if("已授予权限" in typeMap){
				for(var i=0; i < length; i++){
					var itemId = items[i].id;
					if(!(itemId in configObj)){
						temp_items.push(items[i]);
					}
				}
				items = temp_items;
				length = items.length;
				typeMap["可授予权限"] =items;
				for(var k in typeMap){
					var children = typeMap[k];
					var columnLayout = [];
					var _items = [];
					var bool= k == "已授予权限"?true:false;
					columnLayout ={
						layout:'column',
						frame:true,
						style:{
							'border':'0px'
						},
						items:[{
							columnWidth:1,
							width:121,
							itemId:k,
							xtype:'checkbox',
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
					for(var j =0; j < children.length; j++){
						var child = children[j];
						if(j%count == 0){
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
									id:child.id,
									checked:bool,
									margin:'-3px 0px 1px 0px',
									boxLabel:child.name,
									listeners:{
										change:function(check, newValu){
											changeStatus(check, newValu);
										}
									}
								}]
							};
						}else{
							columnLayout.items.push({
								columnWidth:columnWidth,
								width:121,
								id:child.id,
								xtype:'checkbox',
								checked:bool,
								margin:'-3px 0px -1px 0px',
								boxLabel:child.name,
								listeners:{
									change:function(check, newValu){
										changeStatus(check, newValu);
									}
								}
							});
						}
						if((j+1)%count == 0 || j == children.length-1){
							_items.push(columnLayout);
						}
					}
					var fieldSet = Ext.create('Ext.form.FieldSet',{
						xtype:'fieldset',
						collapsible:true,
						margin:'5px',
						frame:true,
						id:k,
						title: k,
						layout:'fit',
						items:[]
					})
					fieldSet.add(_items);
					fieldSet.doLayout();
					perArray.push(fieldSet);
					
				}
			}else{
				var obj = {};
				for(var i = 0; i < length; i++){
					var item = items[i];
					var type = item["type"];
					if(type in obj){
						obj[type].push(item);
					}else{
						obj[type]=[item];
					}
				}
				
				for(var k in obj){
					var children = obj[k];
					var columnLayout = [];
					var _items = [];
					columnLayout ={
						layout:'column',
						frame:true,
						style:{
							'border':'0px'
						},
						items:[{
							columnWidth:1,
							width:121,
							itemId:k,
							xtype:'checkbox',
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
					for(var j =0; j < children.length; j++){
						var child = children[j];
						if(j%count == 0){
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
									id:child.id,
									margin:'-3px 0px 1px 0px',
									boxLabel:child.name,
									listeners:{
										change:function(check, newValu){
											changeStatus(check, newValu);
										}
									}
								}]
							};
						}else{
							columnLayout.items.push({
								columnWidth:columnWidth,
								width:121,
								id:child.id,
								xtype:'checkbox',
								margin:'-3px 0px -1px 0px',
								boxLabel:child.name,
								listeners:{
									change:function(check, newValu){
										changeStatus(check, newValu);
									}
								}
							});
						}
						if((j+1)%count == 0 || j == children.length-1){
							_items.push(columnLayout);
						}
					}
					var name ="";
					if(k in typeMap){
						name = typeMap[k];
					}else{
						name = k;
					}  
					var fieldSet = Ext.create('Ext.form.FieldSet',{
						xtype:'fieldset',
						collapsible:true,
						margin:'5px',
						frame:true,
						id:k,
						title: name,
						layout:'fit',
						items:[]
					})
					fieldSet.add(_items);
					fieldSet.doLayout();
					perArray.push(fieldSet);
				}
			}
			
			
		},
		failure:function(){
			
		}
	});
	return perArray;
}

window.addPerTo1 = function(width,height,isCheck,entityName,id,type){
	var moduleItem =[];
	var children = [];
	/*if(entityName == 'us.Module'){
		children =[{id:'2',name:'库房管理',children:[{id:'5',name:'入库管理',leaf:true},{id:'6',name:'出库管理',leaf:true},{id:'7',name:'库存盘点',leaf:true}]},{id:'3',name:'采购管理',leaf:true},{id:'24',name:'运输管理1',leaf:true},{id:'14',name:'运输管理2',leaf:true},{id:'8',name:'运输管理',leaf:true},{id:'9',name:'运输管理',leaf:true}];
	}else{
		children =[{"id":"84b1b3326331445eb7b8dff8b0d927e1","name":"顶戴国颗地会会人大","leaf":true,"type":"application"},
		           {"id":"23427ab8a296488682acfc8e40e912c1","name":"工程项目管理系统","leaf":true,"type":"application"},
		           {"id":"e9c51265cd4445f5b58f7f604dc46ad6","name":"rerewrewrewr",
		        	   "children":[{"id":"ca8ebd860633449a80d65120d2b5da1b","name":"rerewrewrewr","leaf":true,"type":"module"}],"type":"application"},
		          {"id":"0","name":"建筑工程施工企业信息管理系统（CMIS）",
		        		   "children":[{"id":"8","name":"CMIS系统",
		        			   	"children":[{"id":"3","name":"采购管理","leaf":true,"type":"module"},{"id":"4","name":"运输管理","leaf":true,"type":"module"}],"type":"module"}],"type":"application"},
		         {"id":"1d93dab1fb1848749bec76407dfb3c47","name":"测试应用出发增加默认模块",
		        	"children":[{"id":"fa2e687877df40a49e25fe52574e7c00","name":"测试应用出发增加默认模块","leaf":true,"type":"module"}],"type":"application"},
		         {"id":"38a39f975f6f4290bd8d2273ffe5ca02","name":"project","leaf":true,"type":"application"},
		         {"id":"08f84de9a6b94a1f841cbb08020f7560","name":"12343243432545",
		        	 "children":[{"id":"f52f2578dbd5478d836c68f5f4276f9f","name":"12343243432545","leaf":true,"type":"module"}],"type":"application"},
		          {"id":"0ab4e57412d443158fb5130ed4c82548","name":" 物资管理系统",
		        		 "children":[{"id":"1","name":" 物资管理系统",
		        			   "children":[{"id":"2","name":"库房管理",
		        				    "children":[{"id":"5","name":"入库管理111",
		        				    	 "children":[{"id":"1","name":"功能122222","leaf":true,"type":"function"}],"type":"module"},
		        				    	 {"id":"6","name":"出库管理","leaf":true,"type":"module"},
		        				    	 {"id":"7","name":"库存盘点",
		        				    		 "children":[{"id":"4","name":"23423","leaf":true,"type":"function"}],"type":"module"}],"type":"module"}],"type":"module"}],"type":"application"}];
	}
	*/
	$.ajax({
		type:'GET',
		url:$service.portal + '/'+entityName+'/collection/alltree',
		dataType:'json',
		async:false,
		beforeSend: function(){
		},
		success:function(data){
			var length =0;
			if(data && data.length){
				length = data.length;
			} 
			children = data;
			/*for(var i = 0; i < length; i++){
				var node = data[i];
				if(node['applicationId'] == id){
					children = node['children'];
				}
			}*/
		},
		failure:function(){
			
		}
	});
	var len = children.length;
	var level = len%3 == 0? len/3:Math.floor(len/3)+1;
	var column = level == 1? len:3;
	var initWidth =200;
	var initHeight =200;
	if(width > 20){
		initWidth = Math.floor((width-20)/column);
	}
	
	function configColor(v,record){
		var type = record.data.type;
		if(type =="application"){
			record.set("cls","app-color-red");
		}else if(type == "module"){
		record.set("cls","module-color-blue");
		}else if(type == "function"){
			record.set("cls","fun-color-green");
		}
		return v;
	}
	
	for(var j = 0; j < len; j++){
		var node = children[j];
		node.expanded = true;
		var id = node.id;
		var text = node.name;
		var fields = [{name:'text',mapping:'name'},{name:'type',convert:configColor},{name:'privilegeId'},{name:'disable',type:'boolean',defaultValue: false}];
		if(isCheck){
			fields.push({name:'checked',type:'boolean',defaultValue: false},{name:'expanded',type:'boolean',defaultValue: true});
		}
		var _tree = Ext.create("Ext.tree.Panel",{
			id: id+'_tree',
			style:{
				'margin':'0px 4px 4px 0px'
			},
			store:Ext.create('Ext.data.TreeStore', {//树的数据源store
				fields:fields,
				autoLoad:true,
				proxy : {//代理
		            type : 'ajax',
		            url :$service.portal+'/us.Module/'+id+'/tree'
		        },
				root: {
			        expanded: true,
			        children:[node]
			    }
		    }),
		    rootVisible: false,
		    listeners:{
		    	itemcontextmenu:function(tree, record, item, index, e){//注册tree的右键菜单
					e.preventDefault();
	    		},
			    checkchange:function(node,checked){
		    		node.cascade(function(rec){
		    			var isDisble = rec.get('disable');
		    			if(!isDisble){
		    				rec.set('checked',checked);
		    			}
		    		});
		    	}
		    }
		})
		moduleItem.push(_tree);
	}
	
	var panel =Ext.create('Ext.panel.Panel',{
			xtype:'panel',
			id:'module_panel',
			layout: {
	            type: 'table',
	            columns: column
	        },
	       	border:false,
	        defaults: {frame:true, width:initWidth, height: initHeight},
	        items:[]
		});
	panel.add(moduleItem);
	panel.doLayout();
	return panel;
}

window.showWaitMsg = function(msg) {
	Ext.MessageBox.show({
		title : '系统提示',
		msg : msg == null ? '正在处理数据,请稍候...' : msg,
		progressText : 'processing now,please wait...',
		width : 300,
		wait : true,
		waitConfig : {
			interval : 150
		}
	});
}

window.hideWaitMsg = function () {
	Ext.MessageBox.hide();
}

function getDicStore(code){
	return Ext.create("Ext.data.Store", {
		autoLoad: true,
		fields: ['value', 'name'],
		proxy: {
	        type: 'ajax',
	        url: Ext.util.Format.format(URL_DICTIONARY, code),
	        reader: {
	            type: 'json'
	        }
	    }		
	}); 
}


function commonOpenMenu(url,menuId,menuName){
	if(parent&&parent.addTab){
		if(menuId){
			commonGetMenu(menuId, function(menu){
				parent.addTab(menu.id, menu.name, url);
			});
		}else{
			parent.addTab("", menuName, url);
		}
		
	}
}

function commonOpenMenuParameter(menuId,menuName,url,parameter){
	if(parent&&parent.addTab){
		if(menuId){
			commonGetMenu(menuId, function(menu){
				var menuUrl = menu.url;
				if(menuUrl.indexOf("http") == -1 || menuUrl.indexOf("http") == -1){
					menuUrl = $ctx + menuUrl;
				}
				parent.addTab(menu.id, menu.name, menuUrl+(menuUrl.indexOf("?")>-1?"&":"?")+parameter);
			});
		}else{
			parent.addTab("", menuName, url+(url.indexOf("?")>-1?"&":"?")+parameter);
		}
		
	}
}

function commonOpenMenuParameterForAddress(menuId,parameter,address){
	if(parent&&parent.addTab){
		commonGetMenu(menuId, function(menu){
			if(menu.url.indexOf("http")>-1){
				parent.addTab(menu.id, menu.name, menu.url+(menu.url.indexOf("?")>-1?"&":"?")+parameter);
			}else{
				parent.addTab(menu.id, menu.name, address+"/"+menu.url+(menu.url.indexOf("?")>-1?"&":"?")+parameter);
			}
		});
	}
}

function commonGetMenu(menuId, successFn){
	Ext.Ajax.request({
		url: Ext.util.Format.format(URL_MENU_GET, menuId),
		success: function(response){
			var data = Ext.decode(response.responseText);
			if(getResp(data)){
				successFn.call(this,data);
			}else{
				Ext.Msg.alert('提示','获取菜单信息失败!<br/>'+data.result_code+":"+data.result_desc);
			}
		},
		failure: function(){
			Ext.Msg.alert("提示", "获取菜单信息失败");
		}
	});	
}

/**
 * 给store添加查询条件
 * @param {} f
 * @param {} store
 */
function appendParam(f, store) {
	if (f.isDisabled())return;
	if (f.items) {
    	f.items.each(function(field){
    		appendParam(field, store);
    	});
    }else if(f instanceof Ext.form.DateField){//&&f.getValue()
    	store.proxy.extraParams[f.getName()] = Ext.Date.format(f.getValue(),f.format);
    }else if(f instanceof Ext.form.Radio){
		store.proxy.extraParams[f.getName()] = f.getGroupValue();
	}else if(f instanceof Ext.form.Field){
    	store.proxy.extraParams[f.getName()] = f.getValue();
    }
};

function gridRowEditing() {
	Ext.override(Ext.grid.RowEditor, {
		addFieldsForColumn : function(column, initial) {
			var me = this, i, length, field;
			if (Ext.isArray(column)) {
				for (i = 0, length = column.length; i < length; i++) {
					me.addFieldsForColumn(column[i], initial);
				}
				return;
			}
			if (column.getEditor) {
				field = column.getEditor(null, {
							xtype : 'displayfield',
							getModelData : function() {
								return null;
							}
						});
				if (column.align === 'right') {
					field.fieldStyle = 'text-align:right';
				}
				if (column.xtype === 'actioncolumn') {
					field.fieldCls += ' ' + Ext.baseCSSPrefix
							+ 'form-action-col-field';
				}
				if (me.isVisible() && me.context) {
					if (field.is('displayfield')) {
						me.renderColumnData(field, me.context.record, column);
					} else {
						field.suspendEvents();
						field.setValue(me.context.record.get(column.dataIndex));
						field.resumeEvents();
					}
				}
				if (column.hidden) {
					me.onColumnHide(column);
				} else if (column.rendered && !initial) {
					me.onColumnShow(column);
				}

				// -- start edit
				me.mon(field, 'change', me.onFieldChange, me);
				// -- end edit
			}
		}
	});
}

/**
 * 共用的从字典store获取字典名称的renderer
 * @param {} store
 * @param {} dicValue
 * @return {}
 */
function commonRenderer(store){
	return function(val){
		for(var i=0;i<store.getCount();i++){
			var record = store.getAt(i);
			if(record.data.value == val){
				return record.data.name;
			}
		}
		return "";
	}
}

//比较两个日期大小，如果date1>date2返回1,如果相等返回2，如果小于date2返回3
function dateCompare(date1,date2){
	date1 = date1.replace(/\-/gi,"/");
	date2 = date2.replace(/\-/gi,"/");
	var time1 = new Date(date1).getTime();
	var time2 = new Date(date2).getTime();
	if(time1 > time2){
		return 1;
	}else if(time1 == time2){
		return 2;
	}else{
		return 3;
	}
}

function setLocalStorage(){
	var loginUserId = getQueryValue('loginUserId');
	localStorage.setItem("loginUserId", loginUserId);
}

function getQueryValue(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r != null) return decodeURI(r[2]);
    return null;
}

function loginUserId (){
	var loginUserId = localStorage.getItem("loginUserId");
	return loginUserId;
}
//获取用户按钮权限接口
function userButtonPrivilegeUrl(){

	var userButtonIsHave = sessionStorage.getItem('userButtonIsHave');
	//console.log(userButtonIsHave);
	if(userButtonIsHave){return;}
	$.ajax({
	    "method": "get",
	    "url": $service.portal + "/us.User/collection/userButtonPrivilege?userId="+getQueryValue('loginUserId'),
	    "success": function (r) {
	        if (r.code == 1) {
	        	var userButtonPrivilegeUrl = JSON.stringify(r.result);
	        		sessionStorage.setItem("userButtonIsHave", true);
	        	localStorage.setItem("userButtonPrivilegeUrl", userButtonPrivilegeUrl);
	        } else {
	      	  console.log(r.description);
	        }
	    },
	    "Error": function (text) {
	    	console.log(text);
	    }
	});
}

function validateButtonPrivilege(buttons){
	var userButtonPrivilegeUrlAry = JSON.parse(localStorage.getItem("userButtonPrivilegeUrl"));
	var systemType = sessionStorage.getItem('defaultSystemType');
	
	//console.log(userButtonPrivilegeUrlAry);
	Ext.each(buttons, function(btn){
		var btnHref = btn.privilegeUrl;
		if(btnHref){
			//如果按钮配置url参数则校验此按钮权限
			if(userButtonPrivilegeUrlAry){
				Ext.each(userButtonPrivilegeUrlAry, function(url){
					if(btnHref == url){
						btn.show();
						return false;
					}else {
						btn.hide();
					}
				});
			}else {
				btn.hide();
			}
			
		}
	});
}

hookAjax({
    //拦截回调
    onreadystatechange:function(xhr){
        //console.log("onreadystatechange called: %O",xhr)
    },
    onload:function(xhr){
        //console.log("onload called: %O",xhr)
    },
    //拦截函数
    open:function(arg){
    	var loginUserId = localStorage.getItem("loginUserId");
    	if(loginUserId != null && loginUserId != undefined){
    		var url = arg[1];
    		if(url.indexOf("?") > -1)
    			arg[1]+="&loginUserId=" + loginUserId;
    		else 
    			arg[1]+="?loginUserId=" + loginUserId;
		}
     	
    }
});

