/**
 * 项目招标物资信息
 */
Ext.define('Taole.wechat.wechatNews.model.WechatNewsConfigureItem', {
    extend: 'Ext.data.Model',
    fields: [
		"id",  //id
		
		"imei", 
		
		"role",
		
		"reMark",
		
		{name: "updateTime",   type: "date" },//更新时间
		
		{name: "createTime",   type: "date" }//创建时间

    ]
});