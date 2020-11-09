/**
 * 项目招标物资信息
 */
Ext.define('Taole.appManager.imei.model.ImeiItem', {
    extend: 'Ext.data.Model',
    fields: [
		"id",  //id
		
		"imei", 
		
		"role",
		
		"reMark",
		"status",
		
		{name: "updateTime",   type: "date" },//更新时间
		
		{name: "createTime",   type: "date" }//创建时间

    ]
});