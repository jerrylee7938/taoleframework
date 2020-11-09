Ext.define('Taole.common.goodsAllChoose.model.GoodsChooseItem', {
    extend: 'Ext.data.Model',
    fields: [
				"catalogId",
				"createTime",
				"deadline",
				"description",
				"goodsCode",
				"goodsId",
				"goodsType",
				"name",
				"saleMoney",
				"status",
				"unit",
				'unitName',
				"updateTime"
    ],
    getGoodsname: function(){
    	return this.data.name;
    },
    getGoodsId: function(){
    	return this.data.goodsId;
    },
    getUser: function(){
    	return {
    		name: this.getGoodsname(),
    		goodsId:this.getGoodsId()
    	}
    }

});