/**
 * Project:taole-heaframework
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taole.framework.util.StringUtils;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since Jul 6, 2011 3:09:50 PM
 * @version $Id: StringUtilsTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class StringUtilsTest {

	@Test
	public void testDisplayLeft() throws Exception {
		String str3 = "对“《刑法修正案（八）》的业务讲座”进行";
		String str4 = "关于印发《关于开展迎接建党90周年纪念";
		
		String str5 = "盈科（上海）律师事tester务《所利用各种平台充分重视青";
		String str6 = "黄浦区盛沃律师事务所创x..。sd新发展求突破，主动服务";
		
		String str5_long = "盈科（上海）律师事tester务《所利用各种平台充分重视青年律师";
		String str6_long = "黄浦区盛沃律师事务所创x..。sd新发展求突破，主动服务国企解";
		
		assertEquals(40, str3.getBytes("GBK").length);
		assertEquals(20, str3.length());
		
		
		assertEquals(36, str4.getBytes("GBK").length);
		assertEquals(19, str4.length());
		
		assertEquals(52, str5.getBytes("GBK").length);
		assertEquals(51, str6.getBytes("GBK").length);
		
		assertEquals(str5, StringUtils.displayLeft(str5_long, 52));
		assertEquals(str6, StringUtils.displayLeft(str6_long, 52));
	}
	
	@Test
	public void testLength() throws Exception {
		System.out.println("市律协国资国企专业委员会召开第一次全体委员会议暨“律师从事国资国企法律服务新产品开发”第一次头脑风暴活动".getBytes("GBK").length);
	}
}
