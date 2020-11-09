	Ext.define('Taole.appManager.appAd.controller.AddOrEditBannerCtrl', {
    extend: 'Ext.app.Controller',
    views: [
        "Taole.appManager.appAd.view.AddOrEditBannerWindow",
        "Taole.appManager.appAd.view.BannerImageUploadWindow"
    ],
    refs: [
        {
            ref: 'window',
            selector: 'addOrEditBannerWindow'
        },
        {
            ref: 'window',
            selector: 'bannerImageUploadWindow'
        },
        {
            ref: 'form',
            selector: 'addOrEditBannerWindow>form'
        },
        {
            ref: 'grid',
            selector: 'bannerPanel>grid'
        }
    ],
    statics: {
        isInit: false
    },
    init: function () {
        if (Taole.appManager.appAd.controller.AddOrEditBannerCtrl.isInit)return;

        Taole.appManager.appAd.controller.AddOrEditBannerCtrl.isInit = true;
        this.control({
            'addOrEditBannerWindow': {
                afterrender: function (win) {
                	this.get(win.bannerId, function(data){
                		var appAdContents = data.items;
                		for(var i=0; i<appAdContents.length; i++){
                			var appAdContent = appAdContents[i];
                			appAdContent.startTime = Ext.Date.format(new Date(appAdContent.startTime), "Y-m-d H:i:s");
                			appAdContent.endTime = Ext.Date.format(new Date(appAdContent.endTime), "Y-m-d H:i:s");
                			this.addAppAdFieldSet(appAdContents[i]);
                		}
                	}, function(){}, this);
                }
            },
            'addOrEditBannerWindow button[action=add]': {//添加图片
                click: function () {
                	  uploadPicture(APP_ADCONTENT_UPLOAD, function (data) {
                          for (var i = 0; i < data.length; i++) {
                        	  this.addAppAdFieldSet(data[i]);
                          }
                      }, this, false);
                }
            },
            'addOrEditBannerWindow button[action=save]': {//保存
                click: function (btn) {
                	var win = this.getWindow();
                	var bannerId = win.bannerId;
                	//获取存放所有banner信息的form
                	var FieldSet_Banner_Img_Container_Main = this.getForm().query("#FieldSet_Banner_Img_Container_Main")[0];
                	//获取存放banner信息的panel
                    var dataItems = FieldSet_Banner_Img_Container_Main.items.items;
                    //收集所有数据信息
                    var jsonData = new Array()
                    for (i = 0; i < dataItems.length; i++) {
                    	//获取单个存放banner的form
                        var bannerForm = dataItems[i].items.items[0];
                        if(!bannerForm.isValid())
                        	return;
                        var bannerValues = bannerForm.getValues();
                        bannerValues.posId = bannerId;
                        //获取form的组件数组
                        var formField = bannerForm.items.items;
                        for (j = 0; j < formField.length; j++) {
                        	//获取图片名称
                        	if(formField[j].name == "filename"){
                        		if(formField[j].value.startWith("/")){
                        			bannerValues.img = formField[j].value;
                        		}else{
                        			bannerValues.img = "/"+formField[j].value;
                        		}
                        	}
                        }
                        jsonData.push(bannerValues);
                    }
                    
                    var adData = new Object();
                    adData.appAdContent = jsonData;
                    adData.posId = bannerId;
                    this.save(adData, function(){
                    	Ext.Msg.alert("提示", "保存成功！", function(){
                    		win.close();
                    	}, this);
                    }, null, this);
                    
                }
            },
            'addOrEditBannerWindow button[action=cancel]': {//取消
                click: function () {
                    this.getWindow().close();
                }
            },
            'bannerImageUploadWindow button[action=confirm]': {//确定
                click: function (btn) {
                	var imageWindow = btn.up("window");
                	var imageForm = imageWindow.down("form").getForm();
                	imageForm.submit({
                		scope: this,
                		success:function(form, action){
                			if(imageWindow.isUpdate){
                				var image = Ext.getCmp("banner_id_"+imageWindow.imgIndex);
                				image.value = action.result.result_data.filename;
                				image.setSrc(action.result.result_data.img);
                				var form = image.up("form").getForm();
                				form.findField("imgType").setValue(action.result.result_data.imgType);
                			}else{
                				this.addAppAdFieldSet(action.result.result_data);
                			}
                			
                            imageWindow.close();
                		},
            			failure: function(){
            				Ext.Msg.alert("提示", "信息保存失败，请联系管理员！");
            			}
                	});
                }
            },
            'bannerImageUploadWindow button[action=cancel]': {//取消
                click: function (btn) {
                    btn.up("window").close();
                }
            }
        });
    },
    
    addAppAdFieldSet: function(data){
    	var bannerForm = Ext.ComponentQuery.query("addOrEditBannerWindow>form")[0];
        var bannerFieldSet = bannerForm.query("#FieldSet_Banner_Img_Container_Main")[0];
        var filedArray = bannerFieldSet.items.items;
        var filedSetArray = new Array();
        i = 0;
        for (i; i < filedArray.length; i++) {
            if (filedArray[i].componentCls == 'x-fieldset')filedSetArray.push(filedArray[i])
        }
        var mlength = filedSetArray.length++;
        var fieldSet = new Ext.create("Ext.form.FieldSet", {
            style: {
                fontSize: '10px;'
            },
            id: 'FieldSet_Banner_Img_Container_' + mlength,
            order_number: mlength,
            items: [{
                xtype: 'form',
                layout: 'absolute',
                frame: true,
                defaults: {
                    labelWidth: 60,
                    xtype: 'textfield',
                    labelAlign: 'right',
                    width: 200
                },
                items: [
                    {
                        x: 0,
                        y: 10,
                        width: 450,
                        name: 'uri',
                        value: data.uri,
                        fieldLabel: '链接'
                    },
                    {
                        x: 0,
                        y: 40,
                        xtype: 'combo',
                        name: 'device',
                        value: data.device,
                        fieldLabel: '<font color=red>*</font>适配类型',
                        store: getDicStore("APP_AD_CONTENT_DEVICE_CODE"),
						displayField: 'name',
						valueField: 'value',
                        allowBlank: false
                    },
                    {
                        x: 250,
                        y: 40,
                        name: 'imgType',
                        value: data.imgType,
                        fieldLabel: '<font color=red>*</font>图片类型',
                        readOnly: true,
                        allowBlank: false
                    },
                    {
                        x: 0,
                        y: 70,
                        xtype: 'datetimefield',
                        name: 'startTime',
                        value: data.startTime,
                        format: 'Y-m-d H:i:s',
                        fieldLabel: '<font color=red>*</font>开始时间',
                        allowBlank: false
                    },
                    {
                        x: 250,
                        y: 70,
                        xtype: 'datetimefield',
                        name: 'endTime',
                        value: data.endTime,
                        format: 'Y-m-d H:i:s',
                        fieldLabel: '<font color=red>*</font>结束时间',
                        allowBlank: false
                    },{
                        x: 0,
                        y: 100,
                        name: 'title',
                        value: data.title,
                        width:450,
                        fieldLabel: '标题',
                    },{
                        x: 0,
                        y: 130,
                        name: 'serviceCode',
                        width:450,
                        value: data.serviceCode,
                        fieldLabel: '服务编码',
                    },{
                        x: 0,
                        y: 160,
                        name: 'description',
                        xtype: 'textarea',
                        width:450,
						height:40,
                        value: data.description,
                        fieldLabel: '简介',
                    },
                    {
                        x: 480,
                        y: 15,
                        width: 300,
                        height: 150,
                        name: 'filename',
                        value: data.filename,
                        xtype: 'image',
                        fieldLabel: "预览图片",
                        src: data.img,
                        id:'banner_id_' + mlength
//                        autoEl: {
//                            tag: 'img',
//                            
//                        }
                    },
                    {
                        x: 805,
                        y: 35,
                        xtype: 'button',
                        text: '删除',
                        width: 50,
                        handler: function (btn) {
                        	bannerFieldSet.remove(fieldSet);
                        }
                    },
                    {
                        x: 865,
                        y: 35,
                        xtype: 'button',
                        width: 50,
                        text: '换图',
                        handler: function (btn) {
                        	uploadPicture(APP_ADCONTENT_UPLOAD, function (data) {
                        		var image = Ext.getCmp("banner_id_"+mlength);
                				image.value = data[0].filename;
                				image.setSrc(data[0].img);
                				var form = image.up("form").getForm();
                				form.findField("imgType").setValue(data[0].imgType);
                            }, this, true);
                        }
                    }, {
                        x: 805,
                        y: 65,
                        xtype: 'button',
                        width: 50,
                        text: '下移',
                        handler: function (btn) {
                        	myfieldset = btn.up('fieldset')
                            order_number = myfieldset.order_number;
                            var mmFieldSet = bannerForm.query("#FieldSet_Banner_Img_Container_" + order_number)[0]
                            var pasint = parseInt(order_number) + 1;
                            //下一个set的容器
                            var mmFieldSetmax = bannerForm.query("#FieldSet_Banner_Img_Container_" + pasint)[0]
                            //   alert(mlength)
                            var mindex = order_number + 1;
                            var filedArray1 = bannerFieldSet.items.items;
                            var filedSetArray1 = new Array();
                            i = 0;
                            for (i; i < filedArray1.length; i++) {
                                if (filedArray1[i].componentCls == 'x-fieldset') {
                                    filedSetArray1.push(filedArray1[i])
                                }
                            }
                            if (mindex <= filedSetArray1.length) {
                                var tempField;
                                for (j = 0; j < filedSetArray1.length; j++) {
                                    //根据索引找到下一个fieldset
                                    if (filedSetArray1[j].id == "FieldSet_Banner_Img_Container_" + mindex) {
                                        tempField = filedSetArray1[j];
                                        filedSetArray1[j] = bannerForm.query("#FieldSet_Banner_Img_Container_" + order_number)[0]
                                        for (l = 0; l < filedSetArray1.length; l++) {
                                            if (filedSetArray1[l].id == "FieldSet_Banner_Img_Container_" + order_number) {
                                                filedSetArray1[l] = tempField;
                                                //容器id重新赋值
                                                var mlast = mmFieldSetmax.order_number;
                                                var mmmmm = mlast - 1;
                                                btn.up('fieldset').order_number += 1;
                                                mmFieldSetmax.order_number = mmmmm;
                                                //给容器改变id
                                                var mmmmFieldSetmax = mmFieldSetmax;
                                                var mmfiledttop = btn.up('fieldset')
                                                mmmmFieldSetmax.id = 'FieldSet_Banner_Img_Container_999999999'
                                                mmfiledttop.id = 'FieldSet_Banner_Img_Container_' + mmfiledttop.order_number
                                                mmFieldSet.doLayout()
                                                mmmmFieldSetmax.id = 'FieldSet_Banner_Img_Container_' + mmmmm
                                                mmmmFieldSetmax.order_number = mmmmm;
                                                break
                                            }
                                        }
                                        break;
                                    }
                                }
                                bannerFieldSet.items.items = filedSetArray1;
                            }
                            mmFieldSet.doLayout()
                            bannerForm.doLayout()
                        }
                    }, {
                        x: 865,
                        y: 65,
                        xtype: 'button',
                        width: 50,
                        text: '上移',
                        handler: function (btn) {
                        	myfieldset = btn.up('fieldset')
                            order_number = myfieldset.order_number;
                            var mmFieldSet = bannerForm.query("#FieldSet_Banner_Img_Container_" + order_number)[0]
                            var pasint = parseInt(order_number) - 1;
                            //上一个set的容器
                            var mmFieldSetmax = bannerForm.query("#FieldSet_Banner_Img_Container_" + pasint)[0]
                            var mindex = order_number - 1;
                            var filedArray1 = bannerFieldSet.items.items;
                            var filedSetArray1 = new Array();
                            i = 0;
                            for (i; i < filedArray1.length; i++) {
                                if (filedArray1[i].componentCls == 'x-fieldset')filedSetArray1.push(filedArray1[i])
                            }
                            if (order_number > 0) {
                                var dest;
                                var source;
                                for (j = 0; j < filedSetArray1.length; j++) {
                                    //根据索引找到需要替换的fieldset
                                    if (filedSetArray1[j].id == "FieldSet_Banner_Img_Container_" + mindex) {
                                        dest = filedSetArray1[j];
                                    }
                                    if (filedSetArray1[j].id == "FieldSet_Banner_Img_Container_" + order_number) {
                                        source = filedSetArray1[j];
                                    }
                                }

                                for (l = 0; l < filedSetArray1.length; l++) {
                                    if (filedSetArray1[l].id == "FieldSet_Banner_Img_Container_" + order_number) {
                                        filedSetArray1[l] = dest;
                                    }
                                }
                                for (l = 0; l < filedSetArray1.length; l++) {
                                    if (filedSetArray1[l].id == "FieldSet_Banner_Img_Container_" + mindex) {
                                        filedSetArray1[l] = source;
                                        //容器id、排序属性重新赋值
                                        dest.order_number = order_number
                                        source.order_number = mindex;

                                        //给容器改变id
                                        dest.id = 'FieldSet_Banner_Img_Container_999999999'
                                        source.id = 'FieldSet_Banner_Img_Container_' + source.order_number
                                        mmFieldSet.doLayout()
                                        dest.id = 'FieldSet_Banner_Img_Container_' + dest.order_number
                                        break
                                    }
                                }
                                bannerFieldSet.items.items = filedSetArray1;
                            }
                            mmFieldSet.doLayout()
                            bannerForm.doLayout()
                        }
                    },
//                    {
//                        x: 50,
//                        y: 80,
//                        name: 'isOutSite',
//                        xtype: 'radiogroup',
//                        allowBlank: false,
//                        fieldLabel: '是否外链',
//                        columns: 2,
//                        items: [
//                            {boxLabel: '是', name: 'isOutSite', inputValue: 'Y'},
//                            {boxLabel: '否', checked: true, name: 'isOutSite', inputValue: 'N'}
//                        ]
//                    },
                    {
                        x: 0,
                        y: 200,
                        name: 'isShow',
                        xtype: 'radiogroup',
                        allowBlank: false,
                        fieldLabel: '是否显示',
                        columns: 2,
                        items: [
                            {boxLabel: '是', checked: data.isShow?(data.isShow=="1"?true:false):true, name: 'isShow', inputValue: '1'},
                            {boxLabel: '否', checked: data.isShow?(data.isShow=="0"?true:false):false, name: 'isShow', inputValue: '0'}
                        ]
                    }
                ]
            }]
        });
        bannerFieldSet.add(fieldSet);
    },
    save: function(banners, successFn, failureFn, scope){
		Ext.Ajax.request({
			url: APP_ADCONTENT_CREATE,
			jsonData: banners,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert("提示", "信息保存失败："+data.result_code+"："+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "信息保存失败，请联系管理员！", failureFn, this);
			},
			scope: scope||this
		});
    },
    get: function(posId, successFn, failureFn, scope){
    	Ext.Ajax.request({
			url: APP_ADCONTENT_QUERY + "?posId="+posId,
			success: function(response){
				var data = Ext.decode(response.responseText);
				if(getResp(data)){
					successFn.call(this, data);
				}else{
					Ext.Msg.alert('提示','获取APP版本详情失败!<br/>'+data.result_code+":"+data.result_desc, failureFn, this);
				}
			},
			failure: function(){
				Ext.Msg.alert("提示", "获取APP版本详情失败", failureFn, this);
			},
			scope: scope
		});
    }
});


String.prototype.startWith=function(str){
  var reg=new RegExp("^"+str);     
  return reg.test(this);        
}  