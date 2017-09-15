package org.nobibi.startrace.framework.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extened Apache Common String Utils for project
 * 
 * @author crespo
 * 
 * 
 */
public class StringHelper
        extends org.apache.commons.lang3.StringUtils {
    private static final Logger  LOGGER               = LoggerFactory.getLogger(StringHelper.class);
    private static final String  EMPTY                = "";
    private static final String  STRINGEXCEPTION      = "StringHelper Exception";
    private static final String  REGEXP_FORMAT_STRING = "(\\{\\d\\})";
    private static final Pattern pattern              = Pattern.compile(REGEXP_FORMAT_STRING, Pattern.CASE_INSENSITIVE);

    private StringHelper() {
    }

    public static int toInt(String str) {
        return toInt(str, -1);
    }

    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            LOGGER.error(STRINGEXCEPTION, ex);
        }
        return defaultValue;
    }

    public static long toLong(String str) {
        return toLong(str, -1);
    }

    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception ex) {
            LOGGER.error(STRINGEXCEPTION, ex);
        }
        return defaultValue;
    }

    /**
     * 字符串连接实玄17
     * 
     * @param args
     * @return
     */
    public static String concat(Object... args) {
        if (args == null || args.length == 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        return stringBuilder.toString();
    }

    public static String concat(String split, Object... args) {
        if (args == null || args.length == 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object arg : args) {
            stringBuilder.append(split).append(arg);
        }

        if (stringBuilder.length() > 0 && !StringHelper.isBlank(split)) {
            return stringBuilder.toString().substring(split.length());
        }
        return stringBuilder.toString();
    }

    /**
     * 仿c语言的sprintf实现
     * 
     * @param format
     *            字符串输出格弄17
     * @param args
     * @return
     */
    public static String sprintf(String format, Object... args) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        writer.printf(format, args);
        writer.close();
        return stringWriter.toString();
    }

    /**
     * 
     * @param format
     *            aaaa{0}hello world{1}, welcome {0}
     * @param args
     * @return
     */

    public static String buildString(String format, Object... args) {
        Matcher matcher = pattern.matcher(format);
        String result = format;
        if (args == null) {
            return result;
        }
        while (matcher.find()) {
            String token = matcher.group();
            int idx = Integer.parseInt(token.substring(1, token.length() - 1));
            result = result.replace(token, args[idx] == null ? "" : args[idx].toString());
        }
        return result;
    }

    // 随机生成Word文档的名称
    public static String generateDocFileName() {
        Random random = new Random();
        return "file" + random.nextInt() + ".doc";
    }

    // 随机生成Excel文档的名称
    public static String generateExcelFileName() {
        Random random = new Random();
        return "file" + random.nextInt() + ".xls";
    }

    // 随机生成导出统计图的文件名
    public static String generateImageFileName() {
        Random random = new Random();
        return "file" + random.nextInt();
    }

    /**
     * 将字符串的第一个字母变成大写
     */
    public static String toUpper(String str) {
        char[] chars = str.toCharArray();
        if (chars.length <= 0) {
            return str;
        }
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * 将一个长度不为length的字符串变为长度为length的字符串
     */
    public static String fillZero(String str, int length) {
        StringBuilder sb = new StringBuilder();
        if (str != null && str.length() <= length) {
            for (int i = 0; i < length - str.length(); i++) {
                sb.append("0");
            }
            sb.append(str);
            return sb.toString();
        } else {
            return str;
        }
    }

    /**
     * 
     * @param input
     * @return
     * @throws
     */
    public static String getCleanString(String in) {
        String out = in;
        if (in != null) {
            out = in.trim();
            if (EMPTY.equals(out)) {
                out = null;
            }
        }
        return out;
    }
}
