Ext.define('Taole.common.userCardChoose.model.UserCardChooseItem', {
    extend: 'Ext.data.Model',
    fields: [
				"createTime",
				"auditor",
				"operateTime",
				"cardID",
				"telphone",
				"cardNo",
				"cardName",
				"approverShopId",
				"operatorShopId",
				"description",
				"auditTime",
				"cardTypeName",
				"userId",
				"money",
				"userCardId",
				"userName",
				"bizNo",
				"cardNum",
				"blanceNum",
				"status",
				"updateTime",
				"approver",
				"cardStatus",
				"operator",
				"approverTime",
				"entityName",
				"cardPwd",
				"deadline"
    ],
    getCardNo: function(){
    	return this.data.cardNo;
    },
    getUsername: function(){
    	return this.data.userName;
    },
    getUserId: function(){
    	return this.data.userId;
    },
    getUser: function(){
    	return {
    		cardNo: this.getCardNo(),
    		name: this.getUsername(),
    		userId:this.getUserId()
    	}
    }
    
});