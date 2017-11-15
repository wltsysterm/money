package com.count.money.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by 魏霖涛 on 2017/11/15 0015
 */
public class CommonUtil {
    //金额验证
    public static boolean isMoney(String str){
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }else{
            return true;
        }
    }
    //正整数验证
    public static boolean isNumber(String str){
        Pattern pattern=Pattern.compile("^[1-9]\\d*$");
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }else{
            return true;
        }
    }
}
