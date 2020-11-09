package com.taole.toolkit.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class MobNumUtil {

    /**
     * 手机号合法性判断
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobNum(String mobile) {

        // 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        // 联通：130、131、132、152、155、156、185、186
        // 电信：133、153、180、189、（1349卫通）
        // Pattern p =
        // Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        // Pattern p =
        // Pattern.compile("^[1]([3][0-9]{1}|59|58|82|85|87|88|89)[0-9]{8}$");
        // Matcher m = p.matcher(mobile);
        // return m.matches();
        if (mobile.length() != 11 || !mobile.startsWith("1")) {
            return false;
        }

        if (!StringUtil.isNumeric(mobile)) {
            return false;
        }

        return true;
    }

    /**
     * 手机号校验
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        boolean flag = false;
        if (mobile.length() == 0) {
            return false;
        }
        String[] mobiles = mobile.split(",");
        int len = mobiles.length;
        if (len == 1) {
            return Pattern.matches("^((13[0-9])|(14[5,7,9])|(15[^4,\\D])|(17[0,1,3,5-8])|(18[0-9]))\\d{8}$", mobile);
        } else {
            for (int i = 0; i < len; i++) {

                if (isMobile(mobiles[i])) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 隐藏手机号
     *
     * @param mobiles
     * @return
     */
    public static String hideMobNum(String mobile) {
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 7) {
            //mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        }
        return mobile;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(MobNumUtil.hideMobNum("15210543036"));
    }
}
