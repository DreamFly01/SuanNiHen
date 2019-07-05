package com.fdl.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {
    public static boolean isEmpty(String str) {
        return (str == null) || str.trim().equalsIgnoreCase("null")
                || (str.trim().length() < 1) || str.equals("");
    }

    /**
     * 验证手机号码的合法性 支持+86,0+，170 14
     *
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {

        if (StrUtils.isEmpty(mobile)) {
            return false;
        }
        if (!StrUtils.isNumeric(mobile)) {
            return false;
        }
        if (mobile.length()!=11) {
            return false;
        }

        return true;
    }

    /***
     * 判断是不是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /***
     * 将传入的金额转化为以逗号 3个为分割 保留两位小数
     *
     * @param str
     * @return
     */
    public static String moenyToDH(String str) {

        Double dou = Double.parseDouble(str);
        return new DecimalFormat("###,##0.00").format(dou);

    }

    /***
     * @param remiansDay 秒数
     * @return 0天0小时0分0秒
     */
    public static String getTimeDiff(Long remiansDay) {
        try {
            Long day = remiansDay / 86400000;
            Long hour = (remiansDay % 86400000) / 3600000;
            Long min = (remiansDay % 86400000 % 3600000) / 60000;
            Long second = (remiansDay % 86400000 % 3600000 % 60000 / 1000);
            if (remiansDay <= 0) {
                return "已结束";
            } else {
                return day + "天" + hour + "小时" + min + "分" + second + "秒";
            }
        } catch (Exception e) {

        }
        return "---";

    }

    /***
     * 截断字符串，默认加三个点
     *
     * @param str
     * @return
     */
    public static String cuttOffStr(String str, int length, String suffixs) {
        if (str.length() > length) {
            return str.substring(0, length) + suffixs;
        } else {
            return str;
        }
    }

    /***
     * 是否是中文 不含空格
     *
     * @param str
     * @return
     */
    public static Boolean isChinese(String str) {
        String regex = "^[\\u4e00-\\u9fa5]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 断用户名是否正确
     * @param str
     * @return
     */
    public static Boolean isNUmName(String str){
        String regex= "^[0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    public static Boolean is_(String str){
        String s = str.substring(0,1);
        String w = str.substring(str.length()-1);
        if(s.equals("_")){
            return false;
        }
        if(w.equals("_")){
            return  false;
        }
        return true;
    }


    //校验身份证号码是否合格
    public static Boolean isSfz(String cardId){
        if (cardId.length() == 15 || cardId.length() == 18) {
            if (!cardCodeVerifySimple(cardId)) {
                return false;
            } else {
                if (cardId.length() == 18 && !cardCodeVerify(cardId)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
    private static boolean cardCodeVerifySimple(String cardcode) {
        //第一代身份证正则表达式(15位)
        String isIDCard1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        //第二代身份证正则表达式(18位)
        String isIDCard2 ="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";

        //验证身份证
        if (cardcode.matches(isIDCard1) || cardcode.matches(isIDCard2)) {
            return true;
        }
        return false;
    }
    private static boolean cardCodeVerify(String cardcode) {
        int i = 0;
        String r = "error";
        String lastnumber = "";

        i += Integer.parseInt(cardcode.substring(0, 1)) * 7;
        i += Integer.parseInt(cardcode.substring(1, 2)) * 9;
        i += Integer.parseInt(cardcode.substring(2, 3)) * 10;
        i += Integer.parseInt(cardcode.substring(3, 4)) * 5;
        i += Integer.parseInt(cardcode.substring(4, 5)) * 8;
        i += Integer.parseInt(cardcode.substring(5, 6)) * 4;
        i += Integer.parseInt(cardcode.substring(6, 7)) * 2;
        i += Integer.parseInt(cardcode.substring(7, 8)) * 1;
        i += Integer.parseInt(cardcode.substring(8, 9)) * 6;
        i += Integer.parseInt(cardcode.substring(9, 10)) * 3;
        i += Integer.parseInt(cardcode.substring(10,11)) * 7;
        i += Integer.parseInt(cardcode.substring(11,12)) * 9;
        i += Integer.parseInt(cardcode.substring(12,13)) * 10;
        i += Integer.parseInt(cardcode.substring(13,14)) * 5;
        i += Integer.parseInt(cardcode.substring(14,15)) * 8;
        i += Integer.parseInt(cardcode.substring(15,16)) * 4;
        i += Integer.parseInt(cardcode.substring(16,17)) * 2;
        i = i % 11;
        lastnumber =cardcode.substring(17,18);
        if (i == 0) {
            r = "1";
        }
        if (i == 1) {
            r = "0";
        }
        if (i == 2) {
            r = "x";
        }
        if (i == 3) {
            r = "9";
        }
        if (i == 4) {
            r = "8";
        }
        if (i == 5) {
            r = "7";
        }
        if (i == 6) {
            r = "6";
        }
        if (i == 7) {
            r = "5";
        }
        if (i == 8) {
            r = "4";
        }
        if (i == 9) {
            r = "3";
        }
        if (i == 10) {
            r = "2";
        }
        if (r.equals(lastnumber.toLowerCase())) {
            return true;
        }
        return false;
    }

    //校验邮箱是否合格
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    //验证密码是否合格
    public static boolean isPsw(String password){
        String check = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,20}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(password);
        if(!matcher.matches()){
            return false;
        }
        return true;
    }
    public static String getDistance(String distance){
        if(isEmpty(distance)){
            return "";
        }
       double s=  Double.parseDouble(distance);
       if(s>1000){
           return s/1000+"km";
       }else {
           return distance+"m";
       }
    }
    public static String hideCardNo(String cardNo) {
        if (StrUtils.isEmpty(cardNo)) {
            return cardNo;
        }
        int length = cardNo.length();
        int beforeLength = 0;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i < beforeLength || i >= (length - afterLength)) {
                sb.append(cardNo.charAt(i));
            } else {
                if (i == 4||i==9||i==14) {
                    sb.append(" ");
                } else {
                    sb.append(replaceSymbol);
                }
            }
        }

        return sb.toString();
    }
}
