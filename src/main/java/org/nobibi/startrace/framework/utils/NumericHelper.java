/**
 * @Title: NumericHelper.java
 * @Package: com.itic.appbase.framework.utils
 * @Description: BigDecimal Calculation
 * @author: idong
 * @date: Jul 22, 2015 10:18:31 AM
 * @version: V1.0
 */

package org.nobibi.startrace.framework.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.nobibi.startrace.framework.plugins.log.Log;

/**
 * 
 * 数字及其运算工具类.
 * <p>
 * Company: itic
 * </p>
 * 利用BigDecimal进行double型数字运算. 常见数字格式化.
 * 
 * @author: idong
 * @date: Jul 22, 2015 10:18:31 AM
 * @version: V1.0
 */
public class NumericHelper {
    /**
     * 字符节大小判断
     */
    private static double       KB              = 1024.0;
    private static double       MB              = 1024.0 * 1024;
    private static double       GB              = 1024.0 * 1024 * 1024;
    private static double       TB              = 1024.0 * 1024 * 1024 * 1024;
    /**
     * 默认除法运算精度
     */
    private static final int    DEF_DIVID_SCALE = 10;
    private static final String SCALE_EXCEPTION = "The scale must be a positive integer or zero, but got {}";

    private NumericHelper() {
        super();
    }

    /**
     * 加法.
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 减法.
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法.
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 除法,精度10,四舍五入.
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double div(double d1, double d2) {
        return div(d1, d2, DEF_DIVID_SCALE);
    }

    /**
     * 除法,四舍五入,自定義精度.
     * 
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double div(double d1, double d2, int scale_input) {
        int scale = scale_input;
        if (scale < 0) {
            Log.error(SCALE_EXCEPTION, scale);
            scale = DEF_DIVID_SCALE;
        }
        return div(d1, d2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 除法.
     * 
     * @param d1
     * @param d2
     * @param scale
     * @param roundingMode
     * @return
     */
    public static double div(double d1, double d2, int scale_input, RoundingMode roundingMode) {
        int scale = scale_input;
        if (scale < 0) {
            Log.error(SCALE_EXCEPTION, scale);
            scale = DEF_DIVID_SCALE;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, roundingMode).doubleValue();
    }

    /**
     * 四舍五入,自定義精度.
     * 
     * @param d1
     * @param scale
     * @return
     */
    public static double round(double d1, int scale_input) {
        int scale = scale_input;
        if (scale < 0) {
            Log.error(SCALE_EXCEPTION, scale);
            scale = DEF_DIVID_SCALE;
        }
        return div(d1, 1.0, scale, RoundingMode.HALF_UP);
    }

    /**
     * 两个中取最大.
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double max(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.max(b2).doubleValue();
    }

    /**
     * 两个中取最小.
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double min(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.min(b2).doubleValue();
    }

    /**
     * 常用格式化
     */
    /**
     * 浮点转双精.
     * 
     * @param f1
     * @return
     */
    public static double float2Double(float f1) {
        BigDecimal b1 = new BigDecimal(Float.toString(f1));
        return b1.doubleValue();
    }

    /**
     * 双精转浮点.
     * 
     * @param d1
     * @return
     */
    public static float double2Float(double d1) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        return b1.floatValue();
    }

    /**
     * 长型装双精.
     * 
     * @param l1
     * @return
     */
    public static double long2Double(long l1) {
        BigDecimal b1 = new BigDecimal(Long.toString(l1));
        return b1.doubleValue();
    }

    /**
     * 双精转长型.
     * 
     * @param d1
     * @return
     */
    public static long double2Long(double d1) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        return b1.longValue();
    }

    /**
     * 双精转整型，小数位丢弃.
     * 
     * @param d1
     * @return
     */
    public static int double2Int(double d1) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        return b1.intValue();
    }

    /**
     * 长型字符节格式化
     * 
     * @param l1
     * @param scale
     * @return
     */
    public static String parseBitSize(long l1, int scale) {
        double value = long2Double(l1);
        String result = "0.0";
        if (value < 1024) {
            result = Double.toString(round(value, scale)) + "B";
        } else if (value < MB) {
            result = Double.toString(div(value, KB, scale)) + "KB";
        } else if (value < GB) {
            result = Double.toString(div(value, MB, scale)) + "MB";
        } else if (value < TB) {
            result = Double.toString(div(value, GB, scale)) + "GB";
        } else {
            result = Double.toString(div(value, TB, scale)) + "TB";
        }
        return result;
    }
}
