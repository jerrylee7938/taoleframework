package com.taole.member.utils;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taole.member.ProjectConfig;
import com.taole.member.config.MemberConfig;
import com.taole.member.manager.ExecutorCreateSmsPortal;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;
import com.taole.toolkit.util.HttpClientUtils;

@Component
public class SmsSenderUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SmsSenderUtil.class);

	@Autowired
	private MemberConfig memberConfig;
	
	//发送短信接口地址
	private static String smsSendUrl;
	
	@PostConstruct
	public void init() {
		smsSendUrl = memberConfig.getSmsSendUrl();
	}
	
	/**
	 * 发送短信
	 * @param content 短信内容
	 * @param mobileNum 接收短信手机号
	 */
	public static String send(String content, String mobileNum){
		try {
			SmsUtil smsUtil = new SmsUtil();
			JSONObject sendSmsParams = new JSONObject();
			sendSmsParams.put("content", "【KIDS】"+content);
			sendSmsParams.put("destId", mobileNum);
			sendSmsParams.put("token", smsUtil.getTaoleSmsToken());
			sendSmsParams.put("type", "1");
			
			logger.info(ProjectConfig.PREFIX + "sendSms4Taole:{}", smsSendUrl);
			logger.info(ProjectConfig.PREFIX + "sendSms4Taole param{}", sendSmsParams.toString());
			
			String smsSendRresult = HttpClientUtils.getInstance().httpPost(smsSendUrl, sendSmsParams);
			JSONObject smsSendObject = new JSONObject(smsSendRresult);
			
			logger.info(ProjectConfig.PREFIX + "sendSms4Taole:{}", smsSendRresult);
			
			String result = "100";
			if(!smsSendObject.getString("result_code").equals("100")){
				logger.info("短信发送失败："+smsSendObject.getString("result_desc"));
				result = smsSendObject.getString("result_desc");
			}
			
			ExecutorCreateSmsPortal executor = new ExecutorCreateSmsPortal();
			executor.asynTask(mobileNum, content, "7d4ef0ca9a9843b4a4cb5db924f6a31f", 
					DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS), "100".equals(result) ? "2" : "0", "1", "1", result);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * 验证手机号码的正确性
	 * 
	 * @param mobileNumber
	 * @return
	 */
	public static boolean isMobileNumber(String mobileNumber) {
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16([6]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (mobileNumber.length() != 11) {
			return false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(mobileNumber);
			return m.matches();
		}
	}
	
	/**
	 * 验证手机号码的正确性
	 * 
	 * @param mobileNumber
	 * @return
	 */
	public static boolean isVerifyCode(String verifyCode) {
		String regex = "^\\d{6}$";
		if (verifyCode.length() != 6) {
			return false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(verifyCode);
			return m.matches();
		}
	}
	
	/**
	 * 生成6位短信验证码
	 * @return
	 */
	public static String getCode() {
		String [] array = {"0","1","2","3","4","5","6","7","8","9"};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			String tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < 6; i++) {
			int index = rand.nextInt(10);
			sb.append(array[index]);
		}
		
		return sb.toString();
	}
}
