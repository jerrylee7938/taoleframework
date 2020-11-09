/**************手机号管理****************************/
var Mobile_QUERY = $service.portal + "/us.DictMobileSeg/collection/query"
var Mobile_CREATE = $service.portal + "/us.DictMobileSeg/collection/create"
var Mobile_DELETE = $service.portal + "/us.DictMobileSeg/collection/delete"
var Mobile_UPDATE = $service.portal + "/us.DictMobileSeg/{0}/update"

/**********app广告位置管理*****************/
var APP_ADPOSITION_QUERY = $service.portal + "/us.AppAdposition/collection/query";
var APP_ADPOSITION_RETRIEVE = $service.portal + "/us.AppAdposition/{0}/retrieve";
var APP_ADPOSITION_CREATE = $service.portal + "/us.AppAdposition/collection/create";
var APP_ADPOSITION_UPDATE = $service.portal + "/us.AppAdposition/{0}/update";
var APP_ADPOSITION_DELETE = $service.portal + "/us.AppAdposition/collection/delete";

var APP_ADCONTENT_CREATE = $service.portal + "/us.AppAdContent/collection/create";
var APP_ADCONTENT_UPLOAD = $service.portal + "/us.AppAdContent/collection/upload";
var APP_ADCONTENT_RETRIEVE = $service.portal + "/us.AppAdContent/{0}/retrieve";
var APP_ADCONTENT_QUERY = $service.portal + "/us.AppAdContent/collection/query";

/**********app版本管理*****************/
var APP_VER_QUERY = $service.portal + "/us.AppVer/collection/query";
var APP_VER_UPLOAD = $service.portal + "/us.AppVer/collection/upload";
var APP_VER_RETRIEVE = $service.portal + "/us.AppVer/{0}/retrieve";
var APP_VER_UPDATE = $service.portal + "/us.AppVer/{0}/update";
var APP_VER_CREATE = $service.portal + "/us.AppVer/collection/create";
var APP_VER_DELETE = $service.portal + "/us.AppVer/collection/delete";
var APP_VER_NEW = $service.portal + "/us.AppVer/collection/queryNew";
var APP_VER_PASS = $service.portal + "/us.AppVer/{0}/updateTestStatus";
var APP_VER_GET_VERSION_CODE = $service.portal + "/us.AppVer/collection/getVersionCode";//获取版本编号

/**********app版本渠道管理*****************/
var APP_CHANL_QUERY = $service.portal + "/us.AppChanl/collection/query";//查询
var APP_VER_CHANNEL_UPDATE = $service.portal + "/us.AppChanl/{0}/update";//更新

var APP_VER_SYSTEM_TYPE = $ctx+"/app/appManager/appVer/data/systemType.json";//app系统类型
var APP_VER_CHANNEL_TYPE = $ctx+"/app/appManager/appVer/data/channelType.json";//app渠道类型
var APP_VER_UPDATE_TYPE = $ctx+"/app/appManager/appVer/data/updateType.json";//app更新类型
var APP_VER_APP_STATUS = $ctx+"/app/appManager/appVer/data/appStatus.json";//app版本状态
var APP_VER_DEVELOPER_TYPE = $ctx+"/app/appManager/appVer/data/developerType.json";//APP开发者账号类型
var APP_VER_CHANNEL_QUERY = $ctx+ "/app/appManager/appVer/data/channel.json";//app出道查询


/*******app信息管理**************/
var APP_INFO_QUERY = $service.portal + "/us.App/collection/query";

/***********app资讯内容管理************************/

/*******IMEI管理**************/
var IMEI_QUERY = $service.portal + "/us.AppImei/collection/query";
var IMEI_CREATE = $service.portal + "/us.AppImei/collection/create";
var IMEI_UPDATE = $service.portal + "/us.AppImei/{0}/update";
var IMEI_DELTE = $service.portal + "/us.AppImei/collection/delete";
var IMEI_ROLE_QUERY = $ctx+"/app/appManager/imei/data/role.json";
var IMEI_STATE_QUERY = $ctx+"/app/appManager/imei/data/state.json";

/*----------------推荐渠道管理--------------*/
var URL_USER_CHANNEL_QUERY = $service.portal + "/us.MarketingChannel/collection/query";//查询
var URL_USER_CHANNEL_CREATE = $service.portal + "/us.MarketingChannel/collection/create";//新增
var URL_USER_CHANNEL_UPDATE = $service.portal + "/us.MarketingChannel/{0}/update";//修改
var URL_USER_CHANNEL_GET = $service.portal + "/us.MarketingChannel/{0}/retrieve";//获取详情
var URL_USER_CHANNEL_UPLOAD_APP = $service.portal + "/us.MarketingChannel/collection/uploadApp";//客户端安装包上传


/*-----------------------用户桌面----------------------------------------*/
var URL_USER_DESKTOP_QUERY = $service.portal + '/us.Desktop/collection/tree';//3.3.6 获取桌面项（树形）
var URL_USER_DESKTOP_SET = $service.portal+ '/us.Desktop/collection/set';//3.3.7 配置用户桌面项
var URL_USER_DESKTOP_GET = $service.portal + '/us.Desktop/{0}/retrieve'; //3.3.4 桌面项详情
var URL_USER_DESKTOP_CREATE = $service.portal + '/us.Desktop/collection/create';//3.3.1 添加桌面项
var URL_USER_DESKTOP_UPDATE = $service.portal + '/us.Desktop/{0}/update';//3.3.2 修改桌面项
var URL_USER_DESKTOP_DELETE = $service.portal + '/us.Desktop/collection/delete';// 3.3.3 删除桌面项信息 
var URL_USER_DESKTOP_UP = $service.portal + '/us.Desktop/';// 4.6.8	桌面项上移
var URL_USER_DESKTOP_DOWN = $service.portal + '/us.Desktop/';// 4.6.9	桌面项下移


/*-----------------------用户消息----------------------------------------*/
var URL_USER_NOTICE_QUERY = $service.portal + "/us.Notice/collection/tree";//4.7.6	获取消息项（树形）
var URL_USER_NOTICE_DELETE = $service.portal + "/us.Notice/collection/delete";//4.7.3	删除消息项信息
var URL_USER_NOTICE_UP = $service.portal + '/us.Notice/{0}/upMove';// 4.7.8	消息项上移
var URL_USER_NOTICE_DOWN = $service.portal + '/us.Notice/{0}/downMove';// 4.7.9	消息项下移
var URL_USER_NOTICE_RETRIEVE = $service.portal + '/us.Notice/{0}/retrieve';// 4.7.4	消息项详情
var URL_USER_NOTICE_CREATE = $service.portal + '/us.Notice/collection/create';//4.7.1	添加消息项
var URL_USER_NOTICE_UPDATE = $service.portal + '/us.Notice/{0}/update';//4.7.2	修改消息项

var URL_USER_NOTICE_SET = $service.portal + '/us.Notice/collection/set';// 4.7.7 用户消息项配置 

var URL_USER_NOTICE_SET_DELETE = $service.portal + '/us.Notice/collection/deleteSet';// 4.7.8	用户取消项



/*-----------------------字典---------------------------------------*/
var URL_DICTIONARY =  $service.portal+"/tk.Dictionary/{0}/childNodes";//字典

/*--------------------------------菜单------------------------------*/
var URL_MENU_GET = $service.portal+ '/us.Menu/{0}/getMenu';//获取菜单项字典


/*-----------------------组织管理-----------------------------------*/
var URL_USER_ORGANIZATION_TREEROOT = $service.portal + "/us.Organization/collection/treeRoot";//4.8.5	获取组织目录结构根节点（树形）
var URL_USER_ORGANIZATION_TREEONE = $service.portal + "/us.Organization/{0}/treeOne";//4.8.6	获取下一层组织目录项（树形）
var URL_TK_DICTIONARY_CREATE = $service.portal + "/tk.Dictionary/collection/create";//4.8.1	添加组织结构目录项
var URL_TK_DICTIONARY_UPDATE = $service.portal + "/tk.Dictionary/{0}/update";//4.8.2	修改组织结构目录项
var URL_TK_DICTIONARY_RETRIEVE = $service.portal + "/tk.Dictionary/{0}/retrieve";//4.8.4	组织结构目录详情
var URL_USER_ORGANIZATIONDICT_DELETE = $service.portal + "/us.Organization/collection/delete";//4.8.3	删除组织目录项信息
var URL_USER_ORGANIZATION_POST_CREATE = $service.portal + "/us.Organization/collection/createPost";//4.8.7	添加岗位
var URL_USER_ORGANIZATION_POST_RETRIEVE = $service.portal + "/us.Group/{0}/retrieve";//详情
var URL_USER_ORGANIZATION_POST_UPDATE = $service.portal + "/us.Group/{0}/update";//修改
var URL_USER_ORGANIZATION_POST_DELETE = $service.portal + "/us.Group/collection/batchDelete";//删除
var URL_USER_ORGANIZATION_POST_UPDOWN = $service.portal + "/tk.Dictionary/{0}/updown";


/*******短信接入管理**************/

var SMS_APPLY_QUERY = $service.portal +"/sms.SmsApply/collection/query";  // 查询
var SMS_APPLY_EXAMINE = $service.portal +"/sms.SmsApply/{0}/audit?status=2";  // 审核
var SMS_APPLY_TEST = $service.portal +"/sms.SmsApply/{0}/testSend";  // 测试发送
var SMS_APPLY_DETAIL = $service.portal +"/sms.SmsApply/{0}/retrieve";  // 详情
var SMS_APPLY_UPDATE = $service.portal +"/sms.SmsApply/{0}/update";  // 跟新




/*******短信验证码查询管理**************/
var SMS_VERIFY_QUERY = $service.portal +"/us.SmsVerify/collection/query";






/*******数据权限管理**************/
var DATA_PRIVILEGE_QUERY = $service.portal +"/us.DataPrivilege/collection/query";  // 查询
var DATA_PRIVILEGE_DELETE = $service.portal +"/us.DataPrivilege/collection/delete";  // 删除
var DATA_PRIVILEGE_CREATE = $service.portal +"/us.DataPrivilege/collection/create";  // 新增
var DATA_PRIVILEGE_UPDATE = $service.portal +"/us.DataPrivilege/{0}/update";  // 修改
var DATA_PRIVILEGE_CONFIGVALIDATE = $service.portal +"/us.DataPrivilege/collection/dataPrivilegeConfigValidate";//配置校验

/*******用户数据权限管理**************/
var USER_DATA_PRIVILEGE_ALL = $service.portal +"/us.UserDataPrivilege/collection/allDataPrivilege";  //所有数据权限
var USER_DATA_PRIVILEGE_SAVE = $service.portal +"/us.UserDataPrivilege/collection/save";  //保存

/*******角色数据权限管理**************/
var ROLE_DATA_PRIVILEGE_ALL = $service.portal +"/us.RoleDataPrivilege/collection/allDataPrivilege";  //所有数据权限
var ROLE_DATA_PRIVILEGE_SAVE = $service.portal +"/us.RoleDataPrivilege/collection/save";  //保存

/*******分组数据权限管理**************/
var GROUP_DATA_PRIVILEGE_ALL = $service.portal +"/us.GroupDataPrivilege/collection/allDataPrivilege";  //所有数据权限
var GROUP_DATA_PRIVILEGE_SAVE = $service.portal +"/us.GroupDataPrivilege/collection/save";  //保存