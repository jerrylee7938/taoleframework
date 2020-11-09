/**
 * 用户任务管理
 */
Ext.define('Taole.sms.smsVerify.model.SmsVerifyItem', {
    extend: 'Ext.data.Model',
    fields: [
		'id',  // 唯一标识 ,
		'verify',  //  短信验证码 ,
		'telephone',  //  接收手机号,
		'verifyType',  //  验证码类型,
		'isUsed',  //  是否使用 ,
		'isSend',  //  是否发送 ,
		'expiredTime',  // 过期时间,
		'createdTime' //发送时间
    ]
});