/**
 * Project:taole-heaframework
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;


import java.net.URLEncoder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since Jul 15, 2011 10:31:56 AM
 * @version $Id: JsoupTest.java 9769 2017-09-06 07:06:07Z chengjun $
 */
@Component
public class JsoupTest {


	public static Site querySite(String keyword) throws Exception {
		//String keyword = "上海市徐汇区人民法院";
		//String keyword = "上海市律师协会";
		keyword = URLEncoder.encode(keyword, "UTF-8");
		
		System.out.println("ready..");
		String url = "http://www.beianchaxun.net/s?keytype=2&q="+keyword;
		
		System.out.println("url:"+url);
		
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("get Done!");
		if(doc==null){
			System.out.print("No Doc!");
			return null;
		}
		Elements trs = doc.select("#show_table tr");
		if(trs.size()==3){
			System.out.print("No Result!");
			return null;
		}
		
		System.out.println("####Site##################################");
		
		
		Site site =null;
		for(int i=1 ;i<trs.size()-2;i++){
			site = tranObj(trs.get(i));
			if(site.state.equalsIgnoreCase("normal")){
				
				return site;
			}
			System.out.println(i);
			System.out.println(site.name);
			System.out.println(site.code);
			System.out.println(site.url);
			System.out.println(site.state);
		}
		
		return site;
	//	System.out.println(table.toString());
	}
	
	public static Site tranObj(Element element){
		Site site =null;
		if(element!=null){
			
			site = new Site();
			String style = element.attr("style");
			System.out.println(style);
			if(style.equalsIgnoreCase("background-color: #EEEEEE;")){
				System.out.println(22);
				site.name=element.select("td").get(5).text();
				site.url=element.select("td").get(6).text();
				site.code=element.select("td").get(2).text();
				site.state="delete";
				
			}else{
				System.out.println(33);
				site.name=element.select("td").get(4).text();
				site.url=element.select("td").get(5).text();
				site.code=element.select("td").get(2).text();
				site.state="normal";
			}
			
			
		}
		return site;
	}
	
	//
	public static void queryAddress(String keyword) throws Exception {
		//String keyword = "上海市徐汇区人民法院";
	//	String keyword = "上海市律师协会";
		//String keyword = "上海市司法局";
		keyword = URLEncoder.encode(keyword, "UTF-8");
		
		System.out.println("ready..");
		String url = "http://ditu.google.cn/maps?hl=zh-CN&newwindow=1&safe=strict&q="+keyword;
		
		System.out.println("url:"+url);
		
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("get Done!");
		if(doc==null){
			System.out.print("No Doc!");
			return;
		}
		
	//	System.out.print(doc);
		System.out.println("###Address###################################");
		
		
		Elements els = doc.select("#panel_A_2");
		if(els==null)return;
		
		Elements el = els.select(".pp-place-title");
		
		Address address = new Address();
		
		address.setName(el.get(0).text());
		
		el = els.select(".pp-headline-address");
		address.setAddress(el.text());
		
		el = els.select(".pp-headline-phone");
		address.setPhone(el.text().replaceAll("()", ""));
		
		System.out.println(address.name);
		System.out.println(address.address);
		System.out.println(address.phone);
		//System.out.println(els);
		return;
		
	}
	
	public static void queryZZJG(String keyword) throws Exception {
		//String keyword = "上海市徐汇区人民法院";
	//	String keyword = "上海市律师协会";
		//String keyword = "上海市司法局";
		keyword = URLEncoder.encode(keyword, "UTF-8");
		
		System.out.println("ready..");
		String url = "https://s.nacao.org.cn/dwr/exec/ServiceForNum.getData.dwr";
		
		System.out.println("url:"+url);
		Connection connection = Jsoup.connect(url);
		
		connection.referrer("https://s.nacao.org.cn/specialResult.html?x=Xifx4kfra5BHsJBVOcDdjiD9nQM=&k=+xCbabRcg/tw1lMAT9JbbcE=&s=r6qJGyf9VNMP9sZ0+XCcNzJh0bl3dENNsF4pXJ/e4uC4SRMbIWwMRDaQQ73ZPoocQcI=&y=uxf92SO4uAJjbqeyYJezsWB0z105Fx8xcqYC2bTLRgqyFq3EbOwQA+DvOA==");
		connection.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) ");

		connection.data("callCount","1");
		connection.data("c0-scriptName","ServiceForNum");
		connection.data("c0-methodName","getData");
		connection.data("c0-id","1629_1358817381259");
		connection.data("c0-e1","string:jgmc='"+keyword+"'");
		connection.data("c0-e2","string:jgmc='"+keyword+"'");
		connection.data("c0-e3","string:"+keyword);
		connection.data("c0-e4","string:2");
		connection.data("c0-e5","string:"+keyword);
		connection.data("c0-e6","string:");
		connection.data("c0-e7","string:alll");
		connection.data("c0-e8","string:");
		connection.data("c0-e9","boolean:false");
		connection.data("c0-e10","boolean:true");
		connection.data("c0-e11","boolean:false");
		connection.data("c0-e12","boolean:false");
		connection.data("c0-e13","boolean:false");
		connection.data("c0-e14","boolean:false");
		connection.data("c0-e15","boolean:false");
		connection.data("c0-e16","boolean:false");
		connection.data("c0-e17","string:");
		connection.data("c0-e18","string:");
		connection.data("c0-e19","string:");
		connection.data("c0-param0","Object:{firststrfind:reference:c0-e1, strfind:reference:c0-e2, key:reference:c0-e3, kind:reference:c0-e4, tit1:reference:c0-e5, selecttags:reference:c0-e6, xzqhName:reference:c0-e7, button:reference:c0-e8, jgdm:reference:c0-e9, jgmc:reference:c0-e10, jgdz:reference:c0-e11, qiye:reference:c0-e12, shiye:reference:c0-e13, shetuan:reference:c0-e14, jiguan:reference:c0-e15, qita:reference:c0-e16, strJgmc:reference:c0-e17, :reference:c0-e18, secondSelectFlag:reference:c0-e19}");
		connection.data("xml","true");
		
		connection.header("Content-ExcuteType", "text/plain");
		
	
		Document doc = null;
		try {
			doc = Jsoup.connect(url).post();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("get Done!");
		if(doc==null){
			System.out.print("No Doc!");
			return;
		}
		
		System.out.print(doc);
		System.out.println("###Address###################################");
		
		
	}
	
	public static void main(String[] args){
		try {
			//String keyword = "上海市徐汇区人民法院";
				//String keyword = "上海市律师协会";
				String keyword = "中华人民共和国最高人民法院";
				queryZZJG(keyword);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static class Site {
		private String name;
		private String code;
		private String url;
		private String date;
		private String state;
		
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		
	}
	
	protected static class Address {
		private String name;
		private String address;
		private String code;
		private String phone;
		
		
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
	
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
		
	}
}
