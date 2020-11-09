package com.taole.toolkit.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CodeUtil {

	
    public String generateCode(String prefix){
    	String code = prefix + getTimestamp();
    	return code;
    }
    
    public synchronized String getTimestamp(){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	return formatter.format(new Date());
    }
}
