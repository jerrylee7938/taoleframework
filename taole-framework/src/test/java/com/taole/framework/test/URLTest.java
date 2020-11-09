/**
 * Project:taole-heaframework
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import java.net.URLDecoder;

import org.junit.Test;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since Jul 15, 2011 10:31:56 AM
 * @version $Id: URLTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class URLTest {

	@Test
	public void testParse() throws Exception {
		String url = "http://passport.lawyers.org.cn/openid/server?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.claimed_id=http%3A%2F%2Fpassport.lawyers.org.cn%2Fopenid%2F&openid.identity=http%3A%2F%2Fpassport.lawyers.org.cn%2Fopenid%2F&openid.return_to=http%3A%2F%2Flocalhost%3A8080%2Fsbai%2Fj_spring_openid_security_check&openid.realm=http%3A%2F%2Flocalhost%3A8080%2F&openid.assoc_handle=1310270683258-854&openid.mode=checkid_setup&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0&openid.ext1.mode=fetch_request&openid.ext1.type.firstName=http%3A%2F%2Fschema.openid.net%2FnamePerson%2Ffirst&openid.ext1.type.lastName=http%3A%2F%2Fschema.openid.net%2FnamePerson%2Flast&openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail&openid.ext1.type.language=http%3A%2F%2Fschema.openid.net%2Flanguage%2Fpref&openid.ext1.type.birthDate=http%3A%2F%2Fschema.openid.net%2FbirthDate&openid.ext1.type.namePerson=http%3A%2F%2Fschema.openid.net%2FnamePerson&openid.ext1.type.nickname=http%3A%2F%2Fschema.openid.net%2FnamePerson%2Ffriendly&openid.ext1.type.country=http%3A%2F%2Fschema.openid.net%2Fcontact%2Fcountry%2Fhome&openid.ext1.if_available=firstName%2ClastName%2Clanguage%2CbirthDate%2Ccountry&openid.ext1.type.email2=http%3A%2F%2Faxschema.org%2Fcontact%2Femail&openid.ext1.type.namePerson2=http%3A%2F%2Faxschema.org%2FnamePerson&openid.ext1.type.userId=http%3A%2F%2Fschema.openid.net%2Ftaole%2Fuser%2Fuser-id&openid.ext1.type.personId=http%3A%2F%2Fschema.openid.net%2Fhomolo%2Fuser%2Fperson-id&openid.ext1.type.userName=http%3A%2F%2Fschema.openid.net%2Fhomolo%2Fuser%2Fuser-name&openid.ext1.type.nickName=http%3A%2F%2Fschema.openid.net%2Fhomolo%2Fuser%2Fuser-nick&openid.ext1.required=email%2CnamePerson%2Cnickname%2Cemail2%2CnamePerson2%2CuserId%2CpersonId%2CuserName%2CnickName";
		assertEquals("http://passport.lawyers.org.cn/openid/server?openid.ns=http://specs.openid.net/auth/2.0&openid.claimed_id=http://passport.lawyers.org.cn/openid/&openid.identity=http://passport.lawyers.org.cn/openid/&openid.return_to=http://localhost:8080/sbai/j_spring_openid_security_check&openid.realm=http://localhost:8080/&openid.assoc_handle=1310270683258-854&openid.mode=checkid_setup&openid.ns.ext1=http://openid.net/srv/ax/1.0&openid.ext1.mode=fetch_request&openid.ext1.type.firstName=http://schema.openid.net/namePerson/first&openid.ext1.type.lastName=http://schema.openid.net/namePerson/last&openid.ext1.type.email=http://schema.openid.net/contact/email&openid.ext1.type.language=http://schema.openid.net/language/pref&openid.ext1.type.birthDate=http://schema.openid.net/birthDate&openid.ext1.type.namePerson=http://schema.openid.net/namePerson&openid.ext1.type.nickname=http://schema.openid.net/namePerson/friendly&openid.ext1.type.country=http://schema.openid.net/contact/country/home&openid.ext1.if_available=firstName,lastName,language,birthDate,country&openid.ext1.type.email2=http://axschema.org/contact/email&openid.ext1.type.namePerson2=http://axschema.org/namePerson&openid.ext1.type.userId=http://schema.openid.net/taole/user/user-id&openid.ext1.type.personId=http://schema.openid.net/homolo/user/person-id&openid.ext1.type.userName=http://schema.openid.net/homolo/user/user-name&openid.ext1.type.nickName=http://schema.openid.net/homolo/user/user-nick&openid.ext1.required=email,namePerson,nickname,email2,namePerson2,userId,personId,userName,nickName",URLDecoder.decode(url, "UTF-8"));
		

		String url2 = "http://passport.lawyers.org.cn/openid/server?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.return_to=http%3A%2F%2Fmc.lawyers.org.cn%2Fj_spring_openid_security_check&openid.realm=http%3A%2F%2Fmc.lawyers.org.cn%3A80%2F&openid.assoc_handle=1310270683258-852&openid.mode=checkid_setup&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0-draft4&openid.ext1.mode=fetch_request&openid.ext1.type.userId=http%3A%2F%2Fschema.openid.net%2Ftaole%2Fuser%2Fuser-id&openid.ext1.required=userId&openid.ext1.type.personId=http%3A%2F%2Fschema.openid.net%2Fhomolo%2Fuser%2Fperson-id&openid.ext1.type.userName=http%3A%2F%2Fschema.openid.net%2Fhomolo%2Fuser%2Fuser-name&openid.ext1.type.nickName=http%3A%2F%2Fschema.openid.net%2Fhomolo%2Fuser%2Fuser-nick&openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail&openid.ext1.if_available=personId%2CuserName%2CnickName%2Cemail";
		assertEquals("http://passport.lawyers.org.cn/openid/server?openid.ns=http://specs.openid.net/auth/2.0&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&openid.identity=http://specs.openid.net/auth/2.0/identifier_select&openid.return_to=http://mc.lawyers.org.cn/j_spring_openid_security_check&openid.realm=http://mc.lawyers.org.cn:80/&openid.assoc_handle=1310270683258-852&openid.mode=checkid_setup&openid.ns.ext1=http://openid.net/srv/ax/1.0-draft4&openid.ext1.mode=fetch_request&openid.ext1.type.userId=http://schema.openid.net/taole/user/user-id&openid.ext1.required=userId&openid.ext1.type.personId=http://schema.openid.net/homolo/user/person-id&openid.ext1.type.userName=http://schema.openid.net/homolo/user/user-name&openid.ext1.type.nickName=http://schema.openid.net/homolo/user/user-nick&openid.ext1.type.email=http://schema.openid.net/contact/email&openid.ext1.if_available=personId,userName,nickName,email", URLDecoder.decode(url2, "UTF-8"));
	}
	
}
