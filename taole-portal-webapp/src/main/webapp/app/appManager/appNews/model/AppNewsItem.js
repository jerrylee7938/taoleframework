/**
 * app广告位置管理信息
 */
Ext.define('Taole.appManager.appNews.model.AppNewsItem', {
    extend: 'Ext.data.Model',
    fields: [
		"id",  //id
		"title",//广告位标题名称
		"codeName",
		"typeName",
		"description",// 广告位描述
		"appId",//广告位宿主（哪个App的广告位）
		"appName",//广告位宿主（哪个App的广告位）
		{name: "updateTime",   type: "date" },//更新时间
		{name: "createTime",   type: "date" }//创建时间
    ]
})