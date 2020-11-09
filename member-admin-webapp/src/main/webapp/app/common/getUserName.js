

var dataUserId = getQueryValue('loginUserId');
//getUserName(dataUserId);
var dataUser ={userId:dataUserId};
function getUserName(){
	Ext.Ajax.request({
		url:URL_MANAGER_USER_GETINFO+'?userId='+dataUserId,
		method:'GET',
		 async:false, 
		success:function(resp, opts){
			var r = eval("(" + resp.responseText + ")");
			dataUser.name=r.realName;	
		},						
	})	
	return dataUser.name;
}

