package com.hujz.framework.orm.mybatis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hujz.framework.orm.mybatis.code.Style;

/**
 * Created by liuzh_3nofxnp on 2015/8/26.
 */
public class StringUtil extends com.hujz.soasoft.util.type.StringUtil{



    /**
     * 根据指定的样式进行转换
     *
     * @param str
     * @param style
     * @return
     */
    public static String convertByStyle(String str, Style style){
        switch (style){
            case camelhump:
                return camelhumpToUnderline(str);
            case uppercase:
                return str.toUpperCase();
            case lowercase:
                return str.toLowerCase();
            case normal:
            default:
                return str;
        }
    }

    /**
     * 将驼峰风格替换为下划线风格
     */
    public static String camelhumpToUnderline(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder(
                (size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isUppercaseAlpha(c)) {
                sb.append('_').append(c);
            } else {
                sb.append(toUpperAscii(c));
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
    }

    /**
     * 将下划线风格替换为驼峰风格
     */
    public static String underlineToCamelhump(String str) {
        Matcher matcher = Pattern.compile("_[a-z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    public static boolean isUppercaseAlpha(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    public static char toUpperAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c -= (char) 0x20;
        }
        return c;
    }
}
