/**
 *任务管理
 */
Ext.define('Taole.user.consumeManager.model.ConsumeListItem', {
    extend: 'Ext.data.Model',
    fields: [
				"catalogId",
				"description",
				"goodsCode",
				"goodsId",
				"name",
				"saleMoney",
				"unit",
				"unitName",
				"goodsType",
				"amount",
				"consumeMoney"
    ]
})