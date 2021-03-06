package com.gordon.main.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gordon
 * @since 2017/3/3
 * do(String (正则或者其他)表达式处理)
 */

public class StringMatcherUtils {
    public static boolean match(String str, String regEx) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    //处理1~16位数字
    public static boolean isMatchNumber(String str) {
        return match(str, "[\\d]*");
    }

    // 匹配是否为手机号码
    public static boolean isMobileNO(String mobiles) {
        // 电信号段：133 139 153 180 181 189 177 170 176
        // 移动号段：138 137 136 135 134 178 188 187 183 182 159 158 157 152 150 147
        // 联通号段：186 185 130 131 132 155 156
        return match(mobiles, "^((13[0-9])|(147)|(15[0-9])|(17[0-9])|(18[0-3,5-9]))\\d{8}$");
    }
    public static boolean isMatchChinese(String string){
        int n = 0;
        for(int i = 0; i < string.length(); i++) {
            n = (int)string.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return false;
            }
        }
        return true;
    }
    // 特殊字符
    public static boolean isSpecialChar(String specialChar) {
        return match(specialChar, "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
    }
}
