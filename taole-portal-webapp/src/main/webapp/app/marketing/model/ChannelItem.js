/**
 * 标的信息
 */
Ext.define('Taole.marketing.model.ChannelItem', {
    extend: 'Ext.data.Model',
    fields: [
        "id",

        "name",

        "status",

        "key",

        "description",
        "downloadUrl",
        "loadType",
        "isUploadPackage",
        "type"
    ]
})