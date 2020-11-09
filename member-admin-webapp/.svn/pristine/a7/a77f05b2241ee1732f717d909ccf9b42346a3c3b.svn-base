Ext.define('Taole.common.userChoose.model.UserChooseItem', {
    extend: 'Ext.data.Model',
    fields: [
             "createTime",
             "isVerified",
             "birthday",
             "credentialNo",
             "accountId",
             "avatar",
             "entityName",
             "telphone",
             "city",
             "isDelete",
             "id",
             "isDisplay",
             "rank",
             "address",
             "listorder",
             "name",
             "province",
             "gender",
             "credentialType",
             "verifiedTime",
             "UPDATETime",
             "userId"
    ],
    getAccountId: function(){
    	return this.data.accountId;
    },
    getUsername: function(){
    	return this.data.name;
    },
    getUserId: function(){
    	return this.data.userId;
    },
    getUser: function(){
    	return {
    		accountId: this.getAccountId(),
    		name: this.getUsername(),
    		userId:this.getUserId()
    	}
    }
    
});