package com.taole.member.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taole.toolkit.util.DateUtil;
import com.taole.toolkit.util.HttpClientUtils;

/**
 * 订单工具类
 * @author zhangqianlong
 * @date 2016年11月8日下午5:44:27
 */
public class OrderUtil {

	private final static Logger logger = LoggerFactory.getLogger(OrderUtil.class);
    /**
     * 锁对象，可以为任意对象
     */
    private static Object lockObj = "lockerOrder";
    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 0L;
    /**
     * 每毫秒生成订单号数量最大值
     */
    private static int maxPerMSECSize = 1000;

    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     */
    public static String makeOrderNum() {
        // 最终生成的订单号
        StringBuilder finOrderNum = new StringBuilder(20);;
        try {
            synchronized (lockObj) {
                // 取系统当前时间作为订单号变量前半部分，精确到毫秒
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
                if (orderNumCount > maxPerMSECSize) {
                    orderNumCount = 0L;
                }
                // 组装订单号
                
                StringBuilder countStr = new StringBuilder();
                countStr.append(maxPerMSECSize + orderNumCount);
                
                finOrderNum.append(nowLong);
                finOrderNum.append(countStr.substring(1));
                orderNumCount++;
            }
        } catch (Exception e) {
            logger.error("MakeOrderNum Exception", e);
        }
        return finOrderNum.toString();
    }
    
    /**
     * 生成指定位数的随机数
     * @param decimal
     * @return
     */
    public static String getCode(int decimal) {
		String [] array = {"0","1","2","3","4","5","6","7","8","9"};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			String tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < decimal; i++) {
			int index = rand.nextInt(10);
			sb.append(array[index]);
		}
		
		return sb.toString();
	}
    
    //获取最后插入的值
  	public static <K, V> Entry<K, V> getTail(LinkedHashMap<K, V> map) {
  	    Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
  	    Entry<K, V> tail = null;
  	    while (iterator.hasNext()) {
  	        tail = iterator.next();
  	    }
  	    return tail;
  	}
  	
  	/**
	 * 将时间unix转换为int类型
	 * 
	 * @param timeString
	 * @param format
	 * @return
	 */
	public static Long DateToInt(String timeString, String format) {
		long time = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(timeString);
			time = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	public static Date TimeStamp2Date(String timestampString, String formats){    
		  Long timestamp = Long.parseLong(timestampString)*1000;    
		  String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));    
		  return DateUtil.StringToDate(date);    
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		System.out.println(getCode(4));
		String url = "http://111.67.203.172/sms.aspx";
		String param = "userid=78&"
				+ "action=send&"
				+ "account=kaidisi&"
				+ "password=kaidisi123&"
				+ "mobile=18210543036&"
				+ "content=验证码 851884，请在15分钟内完成输入。为了保证您的信息安全，请勿向他人泄露。【蜜爱】&"
				+ "sendTime=";
		String result = HttpClientUtils.getInstance().httpGet(url + "?" + param);
		System.out.println(result);
	}
}
