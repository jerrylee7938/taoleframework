/*----------------------------------项目----------------------------*/

/*----字典------ */
var URL_DICTIONARY = $service.portal+'/tk.Dictionary/{0}/childAllNodes';//portal字典
var URL_DICTIONARY_MEMBER = $service.member+'/tk.Dictionary/{0}/childAllNodes';//业务字典
/*--------------------------------菜单------------------------------*/
var URL_MENU_GET = $service.portal+ '/us.Menu/{0}/getMenu';//获取菜单项字典

/*--------------------------------卡管理------------------------------*/
var URL_MEMBER_CARD_QUERY = $service.member+"/member.CardInfo/collection/query";//查询卡列表
var URL_MEMBER_CARD_DELETE = $service.member+"/member.CardInfo/collection/delete";//删除卡
var URL_MEMBER_CARD_CREATE = $service.member+"/member.CardInfo/collection/create";//创建卡信息
var URL_MEMBER_CARD_UPDATE = $service.member+"/member.CardInfo/{0}/update";//修改卡信息
var URL_MEMBER_CARD_RETRIEVE = $service.member+"/member.CardInfo/{0}/retrieve";//店面详情信息
var URL_MEMBER_CARD_ONSALE = $service.member+"/member.CardInfo/collection/onSale";//卡上信息
var URL_MEMBER_CARD_OFFSALE = $service.member+"/member.CardInfo/collection/offSale";//卡下信息
var URL_MEMBER_CARD_CODE = $service.member+"/member.CardInfo/collection/generateCardCode";//生成卡编码
var URL_MEMBER_CARD_SHOP_QUERY = $service.member+"/member.CardInfo/collection/queryCard4Shop";//查询可售店面卡列表

/*--------------------------------店面管理------------------------------*/
var URL_MEMBER_STORE_QUERY = $service.member+"/member.ShopInfo/collection/query";//查询店面列表
var URL_MEMBER_STORE_DELETE = $service.member+"/member.ShopInfo/collection/delete";//删除店面
var URL_MEMBER_STORE_CREATE = $service.member+"/member.ShopInfo/collection/create";//创建店面信息
var URL_MEMBER_STORE_UPDATE = $service.member+"/member.ShopInfo/{0}/update";//修改店面信息
var URL_MEMBER_STORE_RETRIEVE = $service.member+"/member.ShopInfo/{0}/retrieve";//店面详情信息
var URL_MEMBER_STORE_SHOPSET = $service.member+"/member.ShopInfo/collection/queryForShopSet";//可售店面设置查询
var URL_MEMBER_SHOPSET_CREATE = $service.member+"/member.ShopStoreSet/collection/create";//可售店面设置保存
var URL_MEMBER_SHOPSET_FORSALE = $service.member+"/member.ShopStoreSet/collection/queryForSale";//查询店面可售商品
var URL_MEMBER_STORE_QUERYUSER = $service.member+"/member.ShopInfo/collection/queryByUser";//查询用户授权店面列表

/*--------------------------------商品信息管理------------------------------*/
var URL_MEMBER_GOODS_QUERY = $service.member+"/member.GoodsInfo/collection/query";//查询商品列表
var URL_MEMBER_GOODS_DELETE = $service.member+"/member.GoodsInfo/collection/delete";//删除商品
var URL_MEMBER_GOODS_CREATE = $service.member+"/member.GoodsInfo/collection/create";//创建商品信息
var URL_MEMBER_GOODS_UPDATE = $service.member+"/member.GoodsInfo/{0}/update";//修改商品信息
var URL_MEMBER_GOODS_RETRIEVE = $service.member+"/member.GoodsInfo/{0}/retrieve";//商品详情信息
var URL_MEMBER_GOODS_CODE = $service.member+"/member.GoodsInfo/collection/generateGoodsCode";//商品编码

/*--------------------------------会员信息管理------------------------------*/
var URL_MEMBER_MEMBERCARD_QUERY = $service.member+"/member.MemberCard/collection/query";//查询会员卡列表
var URL_MEMBER_MEMBERCARD_ENABLE = $service.member+"/member.MemberCard/collection/enable";//启用会员
var URL_MEMBER_MEMBERCARD_UNABLE = $service.member+"/member.MemberCard/collection/unable";//停用会员
var URL_MEMBER_MEMBERCARD_CREATE = $service.member+"/member.MemberCard/collection/create";//办卡
var URL_MEMBER_MEMBERCARD_CLOSE = $service.member+"/member.MemberCard/collection/close";//退卡
var URL_MEMBER_MEMBERCARD_OPEN = $service.member+"/member.MemberCard/collection/open";//审核卡
var URL_MEMBER_MEMBERCARD_UPDATE = $service.member+"/member.MemberCard/{0}/update";//修改会员信息
var URL_MEMBER_MEMBERCARD_RETRIEVE = $service.member+"/member.MemberCard/{0}/retrieve";//会员卡详情
var URL_MEMBER_MEMBERCARD_RECHARGE = $service.member+"/member.UserBillApplyService/collection/charge";//会员卡充值提交审核
var URL_MEMBER_MEMBERCARD_READER = $service.member+"/member.MemberCard/collection/retrieveCardInfoByNo";//读卡
var URL_MEMBER_USERBILLAPPLY_QUERY = $service.member+"/member.UserBillApplyService/collection/query";//会员卡充值审核查询
var URL_MEMBER_USERBILL_APPLYAUDIT = $service.member+"/member.UserBill/collection/applyAudit";//会员卡充值审核是否通过
var URL_MEMBER_MEMBERCARD_TEL_QUERY = $service.member+"/member.MemberCard/collection/queryByTel";//通过手机查询会员卡列表
var URL_MEMBER_MEMBERCARD_UPDATA_DEAD = $service.member+"/member.MemberCard/{0}/updateDeadLine";//更新到期时间



/*--------------------------------商品库存管理------------------------------*/
var URL_MEMBER_GOODSBILLDETAIL_QUERY = $service.member+"/member.GoodsBillDetail/collection/query";//查询商品库存列表
var URL_MEMBER_GOODSBILLDETAIL_INCODE = $service.member+"/member.GoodsBillDetail/collection/generateShopBillCode";//入库编码
var URL_MEMBER_GOODSBILLDETAIL_OUTCODE = $service.member+"/member.GoodsBillDetail/collection/generateShopBillOutCode";//出库编码
var URL_MEMBER_GOODSBILLDETAIL_INSTORE = $service.member+"/member.GoodsBillDetail/collection/inStore";//商品入库
var URL_MEMBER_GOODSBILLDETAIL_OUTSTORE = $service.member+"/member.GoodsBillDetail/collection/outStore";//商品出库

/*--------------------------------会员消费管理------------------------------*/
var URL_MEMBER_USERBILL_CONSUME = $service.member+"/member.UserBill/collection/consume";//用户消费
var URL_MEMBER_USERBILL_CONSUME_CODE = $service.member+"/member.UserBill/collection/generateConsumeBillCode";//单号

/*--------------------------------报表管理------------------------------*/
var URL_MEMBER_REPORT_QUERY = $service.member+"/member.Report/collection/query";//报表查询
var URL_MEMBER_REPORT_PRINT = $service.member+"/member.Report/collection/printReport";//报表打印
var URL_MEMBER_USERBILL_QUERY = $service.member+"/member.UserBill/collection/query";//查询流水账明细
var URL_MEMBER_USERBILL_GOODSINFO = $service.member+"/member.UserBill/{0}/retrieve";//查询流水账明细消费商品列表
var URL_MEMBER_REPORT_EXPORT = $service.member+"/member.Report/collection/exportExcel";//导出流水日、月报表
var URL_MEMBER_USERBILL_EXPORT = $service.member+"/member.UserBill/collection/exportExcel";//导出流水明细
var URL_MEMBER_USERBILL_EXPORT_GOODS = $service.member+"/member.UserBill/collection/exportGoodsExcel";//导出商品流水明细
var URL_MEMBER_USERBILL_APPLY_GOODSINFO = $service.member+"/member.UserBillApplyService/{0}/retrieve";//办卡打印


/*--------------------------------用户管理------------------------------*/
var URL_MEMBER_USER_QUERY = $service.member+"/member.User/collection/query";//用户
var URL_MEMBER_FILE_UPDATE = $service.rest+"/tk.File/collection/upload"//上传头像
var URL_MANAGER_USER_GETINFO = $service.portal+"/us.User/collection/getUserInfo";//获取登录用户信息
var URL_MEMBER_USER_QUERY_OPERATOR = $service.member+"/member.User/collection/query4Operator";//用户





