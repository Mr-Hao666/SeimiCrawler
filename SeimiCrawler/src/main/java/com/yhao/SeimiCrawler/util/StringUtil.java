package com.yhao.SeimiCrawler.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理
 *
 * @author long.hua
 * @version 1.0.0
 * @since 2015-12-10 01:11
 */
public class StringUtil {

    public static byte[] getBytes(String str) {

        if (str != null) {
            try {
                return str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException ignored) {
            }
        }

        return null;

    }

    public static String toString(byte[] bytes) {

        if (bytes != null) {
            try {
                return new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException ignored) {
            }
        }

        return null;

    }

    /**
     * 移除字符串中所有指定子字符串
     *
     * @param target 源字符串
     * @param trim   需要被移除的子字符串
     * @return 移除后的字符串
     */
    public static String trimAll(String target, String trim) {
        if (target == null || target.isEmpty() || trim == null || trim.isEmpty()) {
            return target;
        }
        StringBuilder trimStrSB = new StringBuilder(target);

        int idx = trimStrSB.indexOf(trim);
        while (idx >= 0) {
            trimStrSB.delete(idx, idx + trim.length());
            idx = trimStrSB.indexOf(trim, idx + trim.length());
        }

        return trimStrSB.toString();
    }

    /**
     * 移除字符串中所有指定字符
     *
     * @param target
     * @param trim   需要被移除的子字符
     * @return 移除后的字符串
     */
    public static String trimAll(String target, char trim) {
        if (target == null || target.isEmpty()) {
            return target;
        }
        StringBuilder trimCharSB = new StringBuilder(target);

        int idx = 0;
        while (true) {
            char c = trimCharSB.charAt(idx);
            if (trim == c) {
                trimCharSB.deleteCharAt(idx);
                idx--;
            }

            idx++;

            if (idx == trimCharSB.length()) {
                break;
            }
        }

        return trimCharSB.toString();
    }

    public static String unescape(String json) {
        if (json == null || json.isEmpty()) {
            return json;
        }
        StringBuilder trimEscapeSB = new StringBuilder(json);

        int i = 0;
        while (true) {

            char f = trimEscapeSB.charAt(i);
            if (f == '\\') {
                if (i + 1 < trimEscapeSB.length()) {
                    boolean escape = false;

                    char s = trimEscapeSB.charAt(i + 1);
                    switch (s) {
                        case '"':
                            escape = true;
                            break;
                        case '\\':
                            escape = true;
                            break;
                        case '\b':
                            escape = true;
                            break;
                        case '\f':
                            escape = true;
                            break;
                        case '\n':
                            escape = true;
                            break;
                        case '\r':
                            escape = true;
                            break;
                        case '\t':
                            escape = true;
                            break;
                        case '/':
                            escape = true;
                            break;
                        default:
                            escape = false;
                    }

                    if (escape) {
                        trimEscapeSB.deleteCharAt(i);
                        i--;
                    }
                }
            }

            i++;

            if (i == trimEscapeSB.length()) {
                break;
            }
        }

        return trimEscapeSB.toString();
    }

    public static String trimLeft(String target, String trim) {
        StringBuilder sb = new StringBuilder(target);

        int idx = sb.indexOf(trim, 0);
        while (idx == 0) {
            sb.delete(idx, trim.length());
            idx = sb.indexOf(trim, 0);
        }

        return sb.toString();
    }

    public static String trimRight(String target, String trim) {
        StringBuilder sb = new StringBuilder(target);

        int idx = sb.lastIndexOf(trim, sb.length());
        while (idx > -1 && idx + trim.length() == sb.length()) {
            sb.delete(idx, sb.length());
            idx = sb.lastIndexOf(trim, sb.length());
        }

        return sb.toString();
    }


    public static <T> String join(T... t) {
        if (t == null || t.length == 0) {
            return null;
        }

        return join(Colls.array2List(t));
    }

    public static <T> String join(List<T> list) {
        return join(list, ", ");
    }

    public static <T> String join(List<T> list, String split) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (T obj : list) {
            sb.append(obj.toString()).append(split);
        }

        sb.delete(sb.length() - split.length(), sb.length());

        return sb.toString();
    }

    public static String getParentPath(String parentPath, int userId) {
        if (null == parentPath || "".equals(parentPath.trim())) {
            return String.valueOf(userId);
        }

        return (parentPath + "-" + userId);
    }

    /**
     * 补齐不足长度
     *
     * @param length 长度
     * @param number 数字
     * @return
     */
    public static String lpad(int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }

    /**
     * 创建指定数量的随机字符串
     *
     * @param isNumber 是否是数字
     * @param length
     * @return
     */
    public static String randomStr(boolean isNumber, int length) {
        String retStr = "";
        String strTable = isNumber ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    public static boolean endWith(StringBuilder builder, String flag) {
        if (builder != null && flag != null) {
            return builder.lastIndexOf(flag) + flag.length() == builder.length();
        }

        return false;
    }

    public static void trimRight(StringBuilder builder, String flag) {
        if (endWith(builder, flag)) {
            int idx = builder.lastIndexOf(flag);
            builder.delete(idx, builder.length());
        }
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static boolean isBlank(String str) {
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static String substring(String rawString, String beginFlag, String endFlag) {
        if (notEmpty(rawString)) {
            int bi = rawString.indexOf(beginFlag, -1);
            if (bi > -1) {
                int ei = rawString.indexOf(endFlag, beginFlag.length() + bi);
                if (ei > bi) {
                    return rawString.substring(bi + beginFlag.length(), ei);
                }
            }
        }
        return null;
    }

    public static String substring(String rawString, int beginFlag, int endFlag) {
        if (notEmpty(rawString)) {
            if (beginFlag > -1) {
                if (endFlag > beginFlag) {
                    return rawString.substring(beginFlag, endFlag);
                }
            }
        }
        return null;
    }

    public static String urlEncode(String string) {

        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (Throwable ignored) {
        }

        return "";

    }

    public static String urlDecode(String string) {

        try {
            return URLDecoder.decode(string, "UTF-8");
        } catch (Throwable ignored) {
        }

        return "";

    }

    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
       /* StringBuilder sb = new StringBuilder("123bbcc");
        trimRight(sb, "cc");
        System.out.println(sb);*/
        String status = " ";
//        System.out.println(notBlank(status));
        System.out.println(isContainChinese("123456456456"));
    }

}
