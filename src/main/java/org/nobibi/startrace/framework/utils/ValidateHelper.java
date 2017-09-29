package org.nobibi.startrace.framework.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class ValidateHelper {
    
    private static String regex = "";
    
    /**
     * 必填项验证
     * @param value
     * @return boolean
     */
    public static <T> boolean required(T value) {
         if (value != null && StringUtils.isNotBlank(value.toString())) {
             return true;
         } else {
             return false;
         }
        
    }
    
    /**
     * 数字精度验证
     * @param <T>
     * @param value
     * @return boolean
     */
    public static <T> boolean numberDigit(T value, T prifixLength, T suffixLength) {
        regex = "^[-]?\\d{"+ prifixLength +"}([.]\\d{"+ suffixLength +"})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 数字验证
     * @param <T>
     * @param value
     * @return boolean
     */
    public static <T> boolean number(T value) {
        regex= "^[-]?\\d+([.]\\d+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 长度限制
     * @param value
     * @param length
     * @return boolean
     */
    public static <T> boolean lengthLimit(T value, T length) {
        return getBytesLength(value)> (Integer)length ? false : true;
    }
    
    /**
     * 计算value长度方法
     * @param value
     * @return Integer
     */
    public static <T> Integer getBytesLength(T value) {
        Integer byteLen=0;
        if (value != null && value.toString().length() > 0) {
            for(int i = 0; i < value.toString().length(); i++){
                if (value.toString().codePointAt(i)>255) {
                    byteLen += 2;
                } else {
                    byteLen++;
                }
            }
        }
        return byteLen;
    }
    
    /**
     * 中文验证
     * @param value
     * @return boolean
     */
    public static <T> boolean chsNoSymbol(T value) {
        regex = "^[\u4E00-\u9FA5]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } else {
            return false;
        }
        
    }
    
    /**
     * 邮编验证
     * @param value
     * @return boolean
     */
    public static <T> boolean zip(T value) {
        regex = "^\\d{6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 电话验证
     * @param value
     * @return boolean
     */
    public static <T> boolean phone(T value) {
        regex = "^(0\\d{2,3}[-])?(\\d{7,8})([-]\\d{3,5})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 电话号码验证
     * @param value
     * @return boolean
     */
    public static <T> boolean mobile(T value) {
        regex = "^(((13|14|15|18|17)\\d{9}))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true; 
        } else {
            return false;
        }
    }
    
    /**
     * 电话和手机验证
     * @param value
     * @return boolean
     */
    public static <T> boolean tel(T value) {
        regex = "^(0\\d{2,3}[-])?(\\d{7,8})([-]\\d{3,5})?$";
        String regex2 = "(((13|14|15|18|17)\\d{9}))";
        if (value.toString().matches(regex) || value.toString().matches(regex2)) {
            return true;
        } 
        return false;
    }
    
    /**
     * 邮箱验证
     * @param value
     * @return boolean
     */
    public static <T> boolean email(T value) {
        regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } 
        return false;
    }
    
    /**
     * url验证
     * @param value
     * @return boolean
     */
    public static <T> boolean url(T value) {
        regex = "http(s)?|ftp://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        }
        return false;
    }
    
    /**
     * 身份证验证
     * 
     * @param value
     * @return boolean
     */
    public static <T> boolean idCard(T value) {
        regex  = "^(\\d{17}(\\d|x|X)|\\d{15})$";
        Integer[] certArea = new Integer[]{11 ,12 ,13 ,14 ,15 ,21 ,22 ,23 ,
                             31 ,32 ,33 ,34 ,35 ,36 ,37 ,41 ,42 ,
                             43 ,44 ,45 ,46 ,50 ,51 ,52 ,53 ,54 ,
                             61 ,62 ,63 ,64 ,65 ,71 ,81 ,82 ,91 };
               String certnum = value.toString();
               String sBirthday = "";
               int sum = 0;
               Pattern pattern = Pattern.compile(regex);
               Matcher m = pattern.matcher(certnum);
            if (StringUtils.isBlank(certnum) || (certnum.length() != 15 && certnum.length() != 18)) {
                return false;
            } else if (!m.find()) {
                return false;
            } else if (!Arrays.asList(certArea).contains(Integer.valueOf(certnum.substring(0, 2)))) {
                return false;
            } else {
                if (certnum.length() == 15) {
                    certnum = certnum.substring(0, 6) + "19" + certnum.substring(6);
                }
                sBirthday = certnum.substring(6, 10) + "/" + certnum.substring(10, 12) + "/" + certnum.substring(12, 14);
                Date d = new Date(sBirthday);
                String month = ((d.getMonth()+1) < 9)? "0" + (d.getMonth() + 1): String.valueOf(d.getMonth()+1);
                String day = (d.getDate() < 9)? "0" + d.getDate(): String.valueOf(d.getDate());
                if (!sBirthday.equalsIgnoreCase( getYear(d) + "/" + month + "/" + day)) {
                    return false;
                } else if (certnum.length() == 18) {
                    certnum = certnum.replace("/x$/i", "a");
                    for ( int i = 17; i >= 0; i--) {
                        sum += (Math.pow(2, i) % 11) * (Integer.parseInt(String.valueOf(certnum.charAt(17 - i)), 11));
                    }
                    if (sum % 11 != 1) {
                        return false;
                    }
                }
            }
            return true;
    }
    
    /**
     * 非负数字精度验证,限制小数位最小位数和最大位数，可以是整数、0
     * 
     * @param <T>
     * @param minLength,maxLength
     * @return boolean
     */
    public static <T> boolean numberDigitHashZero(T value, T suffixLength, T maxSuffixLength) {
        regex = "^(\\d*)([.]\\d{" + suffixLength + "," + maxSuffixLength + "})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } 
        return false;
    }
    
    /**
     * 非负数字精度验证，限制小数位最小位数和最大位数
     * 
     * @param <T>
     * @param minLength,maxLength
     * @return boolean
     */
    public static <T> boolean numberDigitWithMax(T value, T suffixLength, T maxSuffixLength) {
        regex = "^(\\d*)[.](\\d{" + suffixLength + "," + maxSuffixLength + "})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 正整数、非负整数包括0
     * 
     * @param <T>
     * @param value
     * @return boolean
     */
    public static <T> boolean positiveNumber(T value) {
        regex = "^(\\d*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 传真验证
     * @param <T>
     * @param value
     * @return boolean
     */
    public static <T> boolean fax(T value) {
        regex = "^((([+]\\d{2,3}[-])([0]\\d{2,3}[-]))|([0]\\d{2,3}[-])?)(\\d{7,8})([-]\\d{3,5})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 年份不大于今年，包含今年
     * 
     * @param <T>
     * @param value
     * @return boolean
     */
    public static <T> boolean beforeYear(T value) {
        regex = "^\\d*[1-9]\\d{3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value.toString());
        if (m.find()) {
            Date date = new Date();
            Integer year = getYear(date);
            if (value.getClass().equals(Integer.class)){
                return (Integer)value <= year;
            } else {
                return Integer.valueOf((String) value) <= year;
            }
        } else {
            return false;
        }
    }
    
    /**
     * 获取Date类型的年
     * 
     * @param date
     * return int
     */
    private static int getYear(Date date) {
        Calendar dd = Calendar.getInstance();
        dd.setTime(date);
        return dd.get(Calendar.YEAR);
    }
    
    /**
     * 浮点数精度验证
     * @param value
     * @param maxLength
     * @return
     */
    public static <T> boolean floatPrecision(T value, T maxLength) {
        regex = "^((\\d+(\\.[0-9]*[1-9][0-9]*)?)|(\\d*[1-9]\\d*(\\.\\d+)?))$";
        Pattern pattern = Pattern.compile(regex);
        String strVal = value.toString();
        int intMaxLength = Integer.parseInt(maxLength.toString());
        Matcher m = pattern.matcher(value.toString());
        if (strVal.indexOf('.') > 0) {
            String suffix = strVal.substring(strVal.indexOf('.')+1);
            if(suffix.length() > intMaxLength) {
                return false;
            } else {
                if (m.find()) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (m.find()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
