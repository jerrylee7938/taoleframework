package com.taole.member.service.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.dataprivilege.manager.UserDataPrivilegeManager;
import com.taole.framework.annotation.ReturnCodeInfo;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.RestService;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.member.condition.MemberCardCondition;
import com.taole.member.condition.UserBillApplyCondition;
import com.taole.member.condition.UserBillCondition;
import com.taole.member.condition.UserCondition;
import com.taole.member.config.CommonConfig;
import com.taole.member.domain.CardInfo;
import com.taole.member.domain.MemberCard;
import com.taole.member.domain.TradeLock;
import com.taole.member.domain.User;
import com.taole.member.domain.UserBill;
import com.taole.member.domain.UserBillApply;
import com.taole.member.domain.param.MemberCardService.Close;
import com.taole.member.domain.param.MemberCardService.Create;
import com.taole.member.domain.param.MemberCardService.Open;
import com.taole.member.domain.param.MemberCardService.Query;
import com.taole.member.domain.param.MemberCardService.QueryByTel;
import com.taole.member.domain.param.MemberCardService.RetrieveCardIndoByNo;
import com.taole.member.domain.param.MemberCardService.Update;
import com.taole.member.domain.param.MemberCardService.UpdateDeadline;
import com.taole.member.manager.CardInfoManager;
import com.taole.member.manager.MemberCardManager;
import com.taole.member.manager.ShopStoreSetManager;
import com.taole.member.manager.TradeLockManager;
import com.taole.member.manager.UserBillApplyManager;
import com.taole.member.manager.UserBillManager;
import com.taole.member.manager.UserManager;
import com.taole.member.utils.RestClientUtil;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;

@Api(tags={"会员卡管理后台管理"})
@RequestMapping({"/rest/member.MemberCard"})
@Controller
@RestService(name="member.MemberCard")
public class MemberCardService
{
  private Logger logger = LoggerFactory.getLogger(MemberCardService.class);
  private static final String RETURN_CODE_URL = "http://localhost:8180/return-code/member_MemberCard_";
  @Autowired
  private CardInfoManager cardInfoManager;
  @Autowired
  private DictionaryManager dictionaryManager;
  @Autowired
  private ShopStoreSetManager shopStoreSetManager;
  @Autowired
  private UserManager userManager;
  @Autowired
  private UserBillManager userBillManager;
  @Autowired
  private MemberCardManager memberCardManager;
  @Autowired
  private CommonConfig commonConfig;
  @Autowired
  private UserBillApplyManager userBillApplyManager;
  @Autowired
  private TradeLockManager tradeLockManager;
  
  @RequestMapping(value={"/collection/create"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json;"})
  @ApiOperation(value="会员办卡", httpMethod="POST", response=Create.MemberCardServiceCreateResp.class, notes="会员办卡")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_create.html")})
  @ReturnCodeInfo("create")
  @ResponseBody
  public Object create(HttpServletRequest request, @RequestBody Create.MemberCardServiceCreateReq createReq)
  {
    try
    {
      MemberCardCondition cardCondition = new MemberCardCondition();
      cardCondition.setCardNo(createReq.getCardNo());
      MemberCard memberCard = this.memberCardManager.findByCondition(cardCondition);
      if ((memberCard != null) && (!memberCard.getStatus().equals("6"))) {
        return new ServiceResult(9, "卡号" + createReq.getCardNo() + "已存在！");
      }
      String cardId = createReq.getCardId();
      if (StringUtils.isBlank(cardId)) {
        return new ServiceResult(9, "缺少所办卡id参数");
      }
      CardInfo cardInfo = this.cardInfoManager.getCardInfo(cardId);
      if (cardInfo == null) {
        return new ServiceResult(9, "无效的卡ID");
      }
      UserCondition userCondition = new UserCondition();
      userCondition.setTelphone(createReq.getTelphone());
      
      User newUser = this.userManager.findByCondition(userCondition);
      if (newUser == null)
      {
        newUser = new User();
        if (!this.memberCardManager.isMobileNumber(createReq.getTelphone())) {
          return new ServiceResult(9, "请输入正确手机号！");
        }
        newUser.setTelphone(createReq.getTelphone());
        if (StringUtils.isNotBlank(createReq.getAvatar())) {
          newUser.setAvatar(createReq.getAvatar());
        }
      }
      MemberCard memberCardReq = new MemberCard();
      memberCardReq.setCardNo(createReq.getCardNo());
      memberCardReq.setDescription(createReq.getDescription());
      memberCardReq.setOperatorShopId(createReq.getOperatorShopId());
      memberCardReq.setOperator(createReq.getOperator());
      
      memberCardReq.setUserName(createReq.getName());
      memberCardReq.setUserGender(createReq.getGender());
      memberCardReq.setUserBirthday(createReq.getBirthday());
      
      memberCardReq.setUserAvatar(createReq.getAvatar());
      
      UserBillApply userBillApply = new UserBillApply();
      
      userBillApply.setTransType("BK");
      userBillApply.setMoney(createReq.getPayMoney());
      userBillApply.setOperatorId(createReq.getOperatorId());
      userBillApply.setOperatorName(createReq.getOperator());
      userBillApply.setOperateTime(new Date());
      userBillApply.setPayType(createReq.getPayType());
      userBillApply.setDescription(createReq.getDescription());
      Integer chargeNo = Integer.valueOf(1);
      userBillApply.setChargeNo(chargeNo);
      userBillApply.setStatus("0");
      
      TradeLock tradeLock = this.tradeLockManager.getTradeLock("BkADD" + createReq.getCardNo());
      if (tradeLock != null) {
        return new ServiceResult(-2, "正在操作中，请稍候！");
      }
      TradeLock tradeLock2 = new TradeLock();
      tradeLock2.setCardNo("BkADD" + createReq.getCardNo());
      Date createTime = new Date();
      tradeLock2.setCreateTime(createTime);
      
      String userCardId = this.memberCardManager.applyCard(newUser, cardInfo, userBillApply, memberCardReq, tradeLock2);
      
      return new ServiceResult(1, "办卡成功", userCardId);
    }
    catch (Exception e)
    {
      this.logger.error("member.CardInfoService.create:{}", e);
      return new ServiceResult(9, "办卡失败: " + e.getMessage());
    }
  }
  
  @RequestMapping(value={"/{id}/update"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json;"})
  @ApiOperation(value="修改会员卡信息", httpMethod="POST", response=Update.MemberCardServiceUpdateResp.class, notes="修改会员卡信息")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_updateCategory.html")})
  @ReturnCodeInfo("update")
  @Transactional(readOnly=false, rollbackFor={Exception.class})
  @ResponseBody
  public Object update(HttpServletRequest request, @RequestBody Update.MemberCardServiceUpdateReq updateReq, @ApiParam(required=true, name="id", value="用户会员卡ID") @PathVariable String id)
  {
    try
    {
      if (StringUtils.isEmpty(id)) {
        return new ServiceResult(-2, "用户会员卡id不能为空！");
      }
      if (StringUtils.isBlank(updateReq.getTelphone())) {
        return new ServiceResult(-2, "手机号不能为空！");
      }
      MemberCard memberCard = this.memberCardManager.getMemberCard(id);
      if (memberCard == null) {
        return new ServiceResult(-2, "用户会员卡信息不存在！");
      }
      User user = this.userManager.getUser(memberCard.getUserId());
      
      UserCondition condition = new UserCondition();
      condition.setTelphone(updateReq.getTelphone());
      User userForTel = this.userManager.findByCondition(condition);
      if (userForTel == null)
      {
        User user2 = new User();
        user2.setTelphone(updateReq.getTelphone());
        user2.setAvatar(updateReq.getAvatar());
        user2.setUpdateTime(new Date());
        user2.setGender(updateReq.getGender());
        String userId = this.userManager.createUser(user2);
        memberCard.setUserId(userId);
      }
      else
      {
        userForTel.setTelphone(updateReq.getTelphone());
        
        userForTel.setUpdateTime(new Date());
        userForTel.setGender(updateReq.getGender());
        this.userManager.updateUser(userForTel);
        memberCard.setUserId(userForTel.getId());
      }
      memberCard.setUserName(updateReq.getName());
      memberCard.setUserGender(updateReq.getGender());
      memberCard.setUserBirthday(updateReq.getBirthday());
      memberCard.setDescription(updateReq.getDescription());
      memberCard.setUserAvatar(updateReq.getAvatar());
      this.memberCardManager.updateMemberCard(memberCard);
      
      return new ServiceResult(1, "修改会员卡信息成功");
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.update:{}", e);
      throw new RuntimeException();
    }
  }
  
  @RequestMapping(value={"/{id}/updateDeadLine"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json;"})
  @ApiOperation(value="修改会员卡到期时间信息", httpMethod="POST", response=UpdateDeadline.MemberCardServiceUpdateDeadlineResp.class, notes="修改会员卡信息")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_updateCategory.html")})
  @ReturnCodeInfo("update")
  @ResponseBody
  public Object updateDeadLine(HttpServletRequest request, @RequestBody UpdateDeadline.MemberCardServiceUpdateDeadlineReq updateReq, @ApiParam(required=true, name="id", value="内容id") @PathVariable String id)
  {
    try
    {
      MemberCard memberCard = this.memberCardManager.getMemberCard(id);
      if (memberCard != null)
      {
        memberCard.setDeadline(updateReq.getDeadline());
        memberCard.setUpdateTime(new Date());
        this.memberCardManager.updateMemberCard(memberCard);
      }
      return new ServiceResult(1, "修改会员卡信息成功");
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.update:{}", e);
      return new ServiceResult(9, "修改会员卡信息失败: " + e.getMessage());
    }
  }
  
  @RequestMapping(value={"/collection/query"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json;"})
  @ApiOperation(value="查询会员管理列表", httpMethod="GET", response=Query.MemberCardServiceQueryResp.class, notes="查询会员管理列表")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_query.html")})
  @ReturnCodeInfo("query")
  @ResponseBody
  public Object query(HttpServletRequest request, HttpServletResponse response, @ApiParam(value="分页参数，开始行数从0开始", required=true, defaultValue="0") @RequestParam int start, @ApiParam(value="分页参数，每页显示条数", required=true, defaultValue="25") @RequestParam int limit, @ModelAttribute Query.MemberCardServiceQueryReq queryCondition)
  {
    try
    {
      Range range = new Range(start, limit);
      String createTimeStart = queryCondition.getStartTime();
      String createTimeEnd = queryCondition.getEndTime();
      MemberCardCondition condition = new MemberCardCondition();
      JSONObject userDataObj = UserDataPrivilegeManager.getUserAllDataPrivilege("shop", request, null);
      String[] shopIdArry = this.memberCardManager.getShopIdByUser(userDataObj);
      if (StringUtils.isNotBlank(queryCondition.getCardNo()))
      {
        condition.setCardNo(queryCondition.getCardNo());
      }
      else
      {
        if (StringUtils.isNotBlank(queryCondition.getUserId())) {
          condition.setUserId(queryCondition.getUserId());
        }
        if (StringUtils.isNotBlank(queryCondition.getStatus())) {
          condition.setStatus(queryCondition.getStatus());
        }
        if (StringUtils.isNotBlank(queryCondition.getShopId())) {
          condition.setOperatorShopId(queryCondition.getShopId());
        } else {
          condition.setOperatorShopIds(shopIdArry);
        }
        if (StringUtils.isNotBlank(createTimeStart)) {
          condition.setStartTime(DateUtil.StringToDate(createTimeStart, DateStyle.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(createTimeEnd)) {
          condition.setEndTime(DateUtil.StringToDate(createTimeEnd, DateStyle.YYYY_MM_DD));
        }
      }
      JSONArray jsonArray = (JSONArray)this.memberCardManager.searchToJson(condition, range, new Sorter()
        .desc("createTime"));
      
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("items", jsonArray);
      jsonObject.put("total", Integer.valueOf(this.memberCardManager.count(condition)));
      return new ServiceResult(1, "查询会员卡管理成功", jsonObject);
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.query:{}", e);
      return new ServiceResult(9, "查询会员卡管理失败: " + e.getMessage());
    }
  }
  
  @RequestMapping(value={"/collection/queryByTel"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json;"})
  @ApiOperation(value="通过手机号查询会员卡", httpMethod="GET", response=QueryByTel.MemberCardServiceQueryByTelResp.class, notes="查询会员管理列表")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_queryByTel.html")})
  @ReturnCodeInfo("queryByTel")
  @ResponseBody
  public Object queryByTel(HttpServletRequest request, HttpServletResponse response, @ApiParam(value="分页参数，开始行数从0开始", required=true, defaultValue="0") @RequestParam int start, @ApiParam(value="分页参数，每页显示条数", required=true, defaultValue="25") @RequestParam int limit, @ModelAttribute QueryByTel.MemberCardServiceQueryByTelReq queryCondition)
  {
    try
    {
      Range range = new Range(start, limit);
      MemberCardCondition condition = new MemberCardCondition();
      String[] statuss = { "1", "5" };
      condition.setStatuss(statuss);
      
      UserCondition userCondition = new UserCondition();
      userCondition.setTelphone(queryCondition.getTelphone());
      List<User> list = this.userManager.list(userCondition);
      String[] userIds = new String[list.size()];
      for (int i = 0; i < list.size(); i++) {
        userIds[i] = ((User)list.get(i)).getId();
      }
      if ((null != userIds) && (userIds.length > 0)) {
        condition.setUserIds(userIds);
      }
      JSONArray jsonArray = (JSONArray)this.memberCardManager.searchToJson(condition, range, new Sorter()
        .desc("createTime"));
      
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("items", jsonArray);
      jsonObject.put("total", Integer.valueOf(this.memberCardManager.count(condition)));
      return new ServiceResult(1, "查询会员卡管理成功", jsonObject);
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.query:{}", e);
      return new ServiceResult(9, "查询会员卡管理失败: " + e.getMessage());
    }
  }
  
  @ResponseBody
  @RequestMapping(value={"/{id}/retrieve"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json; charset=utf-8"})
  @ApiOperation(value="获取卡信息详情", httpMethod="GET", response=Query.MemberCardServiceQueryResp.class, notes="获取卡信息详情")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_retrieve.html")})
  @ReturnCodeInfo("Retrieve")
  public Object retrieve(HttpServletRequest request, HttpServletResponse response, @ApiParam(required=true, name="id", value="卡id") @PathVariable String id)
  {
    try
    {
      MemberCard memberCard = this.memberCardManager.getMemberCard(id);
      if (null == memberCard) {
        return new ServiceResult(-1, "没有卡信息！");
      }
      JSONObject object = (JSONObject)SerializableJSONTransformer.transformPojo2Jso(memberCard);
      
      CardInfo cardInfo = this.cardInfoManager.getCardInfo(memberCard.getCardId());
      User user = this.userManager.getUser(memberCard.getUserId());
      UserBillCondition userBillCondition = new UserBillCondition();
      userBillCondition.setUserCardId(memberCard.getUserCardId());
      UserBill userBill = this.userBillManager.findByCondition(userBillCondition);
      if (memberCard.getUserGender() == null) {
        object.put("gender", "");
      } else {
        object.put("gender", String.valueOf(memberCard.getUserGender()));
      }
      if (memberCard.getUserBirthday() == null) {
        object.put("birthday", "");
      } else {
        object.put("birthday", String.valueOf(memberCard.getUserBirthday()));
      }
      if (memberCard.getStatus().equals("0"))
      {
        UserBillApplyCondition condition = new UserBillApplyCondition();
        condition.setStatus("0");
        UserBillApply userBillApply = this.userBillApplyManager.findByCondition(condition);
        
        object.put("blanceNum", userBillApply.getMoney());
      }
      else
      {
        object.put("blanceNum", Integer.valueOf(this.userBillManager.getBalanceNum(memberCard.getUserCardId())));
      }
      UserBillApplyCondition condition = new UserBillApplyCondition();
      condition.setUserCardId(memberCard.getUserCardId());
      UserBillApply userBillApply = this.userBillApplyManager.findByCondition(condition);
      if (userBill == null)
      {
        object.put("payMoney", userBillApply.getMoney());
        object.put("payType", userBillApply.getPayType());
        object.put("payTypeName", this.dictionaryManager
          .getDictName("PAYMODE_CODE", userBillApply.getPayType()));
      }
      else
      {
        object.put("payMoney", userBill.getConsumeMoney());
        object.put("payType", userBill.getPayType());
        object.put("payTypeName", this.dictionaryManager
          .getDictName("PAYMODE_CODE", userBill.getPayType()));
      }
      object.put("cardName", cardInfo.getCardName());
      object.put("cardType", cardInfo.getCardType());
      object.put("cardTypeName", this.dictionaryManager
        .getDictName("CARDTYPE_CODE", cardInfo.getCardType()));
      object.put("chargeType", cardInfo.getChargeType());
      object.put("chargeTypeName", this.dictionaryManager
        .getDictName("21a0578289544ae5a24d7bfad7130d2d", cardInfo.getChargeType()));
      object.put("telphone", user.getTelphone());
      object.put("approver", "");
      object.put("periodOfValidity", cardInfo.getPeriodOfValidity());
      object.put("desc", memberCard.getDescription());
      object.put("cardNumber", cardInfo.getTotalNum());
      if (StringUtils.isNotBlank(memberCard.getUserAvatar()))
      {
        object.put("avator", memberCard.getUserAvatar());
        object.put("userAvatar", this.commonConfig.getGetAvatarUrl() + memberCard.getUserAvatar());
      }
      return new ServiceResult(1, "获取卡信息成功", object);
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.retrieve:{}", e);
      return new ServiceResult(9, "获取卡消息失败", e.getMessage());
    }
  }
  
  @ResponseBody
  @RequestMapping(value={"/collection/retrieveCardInfoByNo"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json; charset=utf-8"})
  @ApiOperation(value="通过卡号获取卡信息详情", httpMethod="GET", response=RetrieveCardIndoByNo.MemberCardServiceRetrieveCardIndoByNoResp.class, notes="获取卡信息详情")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_retrieveCardInfoByNo.html")})
  @ReturnCodeInfo("retrieveCardInfoByNo")
  public Object retrieveCardInfoByNo(HttpServletRequest request, HttpServletResponse response, @ModelAttribute RetrieveCardIndoByNo.MemberCardServiceRetrieveCardIndoByNoReq queryCondition)
  {
    try
    {
      MemberCardCondition cardCondition = new MemberCardCondition();
      cardCondition.setCardNo(queryCondition.getCardNo());
      String[] statuss = { "1", "5" };
      cardCondition.setStatuss(statuss);
      MemberCard memberCard = this.memberCardManager.findByCondition(cardCondition);
      if (null == memberCard) {
        return new ServiceResult(-1, "没有卡信息！");
      }
      JSONObject object = (JSONObject)SerializableJSONTransformer.transformPojo2Jso(memberCard);
      
      CardInfo cardInfo = this.cardInfoManager.getCardInfo(memberCard.getCardId());
      User user = this.userManager.getUser(memberCard.getUserId());
      
      object.put("money", memberCard.getMoney());
      
      object.put("blanceNum", Integer.valueOf(this.userBillManager.getBalanceNum(memberCard.getUserCardId())));
      
      object.put("useTime", Integer.valueOf(this.userBillManager.getUsedCardNum(memberCard.getUserCardId())));
      object.put("cardName", cardInfo.getCardName());
      object.put("days", cardInfo.getPeriodOfValidity());
      object.put("cardType", cardInfo.getCardType());
      object.put("cardTypeName", this.dictionaryManager
        .getDictName("CARDTYPE_CODE", cardInfo.getCardType()));
      object.put("chargeType", cardInfo.getChargeType());
      object.put("chargeTypeName", this.dictionaryManager
        .getDictName("21a0578289544ae5a24d7bfad7130d2d", cardInfo.getChargeType()));
      object.put("userName", memberCard.getUserName());
      object.put("telphone", user.getTelphone());
      object.put("birthday", user.getBirthday());
      object.put("avator", memberCard.getUserAvatar());
      object.put("userAvatar", this.commonConfig.getGetAvatarUrl() + memberCard.getUserAvatar());
      
      return new ServiceResult(1, "获取卡信息成功", object);
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.retrieve:{}", e);
      return new ServiceResult(9, "获取卡消息失败", e.getMessage());
    }
  }
  
  @RequestMapping(value={"/collection/open"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json;"})
  @ApiOperation(value="审核", httpMethod="POST", response=Open.MemberCardServiceOpenResp.class, notes="审核卡")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_open.html")})
  @ReturnCodeInfo("open")
  @ResponseBody
  public Object open(HttpServletRequest request, @RequestBody Open.MemberCardServiceOpenReq req)
  {
    try
    {
      MemberCard memberCard = this.memberCardManager.getMemberCard(req.getUserCardId());
      if (StringUtils.isEmpty(req.getUserCardId())) {
        return new ServiceResult(-2, "卡id不能为空！");
      }
      if (memberCard == null) {
        return new ServiceResult(-2, "卡信息不存在！");
      }
      if ((memberCard.getStatus().equals("1")) || 
        (memberCard.getStatus().equals("5"))) {
        return new ServiceResult(-2, "已有卡号为：" + memberCard
          .getCardNo() + "的会员卡处于可用状态，无法审核通过，请联系管理员");
      }
      UserBillApplyCondition userBillApplyCondition = new UserBillApplyCondition();
      userBillApplyCondition.setUserCardId(req.getUserCardId());
      UserBillApply userBillApply = this.userBillApplyManager.findByCondition(userBillApplyCondition);
      if ("y".equals(req.getAuditFlag()))
      {
        TradeLock tradeLock = this.tradeLockManager.getTradeLock("BkOPEN" + memberCard.getCardNo());
        if (tradeLock != null) {
          return new ServiceResult(-2, "正在操作中，请稍候！");
        }
        TradeLock tradeLock2 = new TradeLock();
        tradeLock2.setCardNo("BkOPEN" + memberCard.getCardNo());
        Date createTime = new Date();
        tradeLock2.setCreateTime(createTime);
        this.tradeLockManager.createTradeLock(tradeLock2);
        
        memberCard.setStatus("5");
        memberCard.setAuditTime(new Date());
        this.memberCardManager.updateMemberCard(memberCard);
        
        UserBill userBill = new UserBill();
        if (StringUtils.isBlank(memberCard.getOperator()))
        {
          String userId = UserDataPrivilegeManager.getUserId(request);
          JSONObject userInfo = RestClientUtil.getUserInfo(userId);
          String operator = userInfo.getString("realName");
          userBill.setOperator(operator);
        }
        String bizNo = this.userBillManager.getUserBillNo("BK");
        userBill.setUserBillId(bizNo);
        userBill.setConsumeMoney(userBillApply.getMoney());
        userBill.setPayType(userBillApply.getPayType());
        userBill.setDescription(memberCard.getDescription());
        userBill.setOperator(memberCard.getOperator());
        String operatorShopId = memberCard.getOperatorShopId();
        if (StringUtils.isBlank(operatorShopId)) {
          return new ServiceResult(9, "必须提交店面参数");
        }
        userBill.setOperatorShopId(operatorShopId);
        userBill.setUserBillNo(bizNo);
        userBill.setShopBillId(null);
        userBill.setUserCardId(req.getUserCardId());
        userBill.setUserId(memberCard.getUserId());
        userBill.setConsumeUnit("YUAN");
        
        CardInfo cardInfo = this.cardInfoManager.getCardInfo(memberCard.getCardId());
        
        userBill.setBalanceNum(cardInfo.getTotalNum());
        userBill.setBalance(Double.valueOf(0.0D));
        userBill.setActionTypeId("BK");
        userBill.setInOutType("WJWC");
        userBill.setOperateTime(new Date());
        userBill.setCreateTime(new Date());
        String userBillID = this.userBillManager.createUserBill(userBill);
        
        userBillApply.setStatus("1");
        this.userBillApplyManager.updateUserBillApply(userBillApply);
      }
      if ("n".equals(req.getAuditFlag()))
      {
        memberCard.setStatus("6");
        memberCard.setAuditTime(new Date());
        this.memberCardManager.updateMemberCard(memberCard);
        userBillApply.setStatus("2");
        this.userBillApplyManager.updateUserBillApply(userBillApply);
      }
      return new ServiceResult(1, "审核卡成功");
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.open:{}", e);
      return new ServiceResult(9, "审核卡失败: " + e.getMessage());
    }
  }
  
  @RequestMapping(value={"/collection/close"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json;"})
  @ApiOperation(value="退卡", httpMethod="POST", response=Close.MemberCardServiceCloseResp.class, notes="退卡")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_close.html")})
  @ReturnCodeInfo("close")
  @ResponseBody
  public Object close(HttpServletRequest request, @RequestBody Close.MemberCardServiceCloseReq closeReq)
  {
    try
    {
      MemberCard memberCard = this.memberCardManager.getMemberCard(closeReq.getUserCardId());
      if (memberCard == null) {
        return new ServiceResult(-2, "卡信息不存在！");
      }
      if (StringUtils.isEmpty(closeReq.getUserCardId())) {
        return new ServiceResult(-2, "卡id不能为空！");
      }
      if (StringUtils.isEmpty(closeReq.getShopId())) {
        return new ServiceResult(-2, "店面不能为空！");
      }
      memberCard.setStatus("2");
      this.memberCardManager.updateMemberCard(memberCard);
      
      UserBill userBill = new UserBill();
      
      MemberCardCondition memberCardCondition = new MemberCardCondition();
      memberCardCondition.setUserCardId(closeReq.getUserCardId());
      MemberCard memberCard2 = new MemberCard();
      memberCard2 = this.memberCardManager.findByCondition(memberCardCondition);
      
      userBill.setUserCardId(memberCard2.getUserCardId());
      userBill.setUserId(memberCard2.getUserId());
      String billNo = this.userBillManager.getUserBillNo("TK");
      userBill.setUserBillNo(billNo);
      userBill.setUserBillId(billNo);
      userBill.setConsumeUnit("元");
      if (closeReq.getConsumeMoney() == null) {
        userBill.setConsumeMoney(Double.valueOf(0.0D));
      }
      userBill.setConsumeMoney(closeReq.getConsumeMoney());
      userBill.setActionTypeId("TK");
      userBill.setInOutType("OUT");
      userBill.setPayType(closeReq.getPayType());
      userBill.setOperateTime(new Date());
      userBill.setDescription(closeReq.getDescription());
      userBill.setPayType(closeReq.getPayType());
      userBill.setOperatorShopId(closeReq.getShopId());
      userBill.setOperator(closeReq.getOperator());
      
      userBill.setCreateTime(new Date());
      this.userBillManager.createUserBill(userBill);
      
      return new ServiceResult(1, "退卡成功");
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.close:{}", e);
      return new ServiceResult(9, "退卡失败: " + e.getMessage());
    }
  }
  
  @RequestMapping(value={"/collection/enable"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json;"})
  @ApiOperation(value="启用", httpMethod="POST", response=Open.MemberCardServiceOpenResp.class, notes="启用")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_enable.html")})
  @ReturnCodeInfo("enable")
  @ResponseBody
  public Object enable(HttpServletRequest request, @ApiParam(required=true, name="ids", value="卡ID，多个以“,”分隔") @RequestParam String ids)
  {
    String[] userCardIds = ids.split(",");
    try
    {
      for (String userCardId : userCardIds)
      {
        MemberCard memberCard = this.memberCardManager.getMemberCard(userCardId);
        if (memberCard == null) {
          return new ServiceResult(-2, "卡信息不存在！");
        }
        if (StringUtils.isEmpty(ids)) {
          return new ServiceResult(-2, "卡id不能为空！");
        }
        memberCard.setStatus("1");
        this.memberCardManager.updateMemberCard(memberCard);
      }
      return new ServiceResult(1, "启用成功");
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.enable:{}", e);
      return new ServiceResult(9, "启用失败: " + e.getMessage());
    }
  }
  
  @RequestMapping(value={"/collection/unable"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json;"})
  @ApiOperation(value="停用", httpMethod="POST", response=Open.MemberCardServiceOpenResp.class, notes="停用")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code=100, message="点击链接查看具体返回码：http://localhost:8180/return-code/member_MemberCard_unable.html")})
  @ReturnCodeInfo("unable")
  @ResponseBody
  public Object unable(HttpServletRequest request, @ApiParam(required=true, name="ids", value="卡ID，多个以“,”分隔") @RequestParam String ids)
  {
    String[] userCardIds = ids.split(",");
    try
    {
      for (String userCardId : userCardIds)
      {
        MemberCard memberCard = this.memberCardManager.getMemberCard(userCardId);
        if (memberCard == null) {
          return new ServiceResult(-2, "卡信息不存在！");
        }
        if (StringUtils.isEmpty(ids)) {
          return new ServiceResult(-2, "卡id不能为空！");
        }
        memberCard.setStatus("4");
        this.memberCardManager.updateMemberCard(memberCard);
      }
      return new ServiceResult(1, "停用成功");
    }
    catch (Exception e)
    {
      this.logger.error("member.MemberCardService.close:{}", e);
      return new ServiceResult(9, "停用失败: " + e.getMessage());
    }
  }
}
