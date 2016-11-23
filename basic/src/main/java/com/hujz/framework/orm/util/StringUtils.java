package com.hujz.framework.orm.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {

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
    
    public static String toBetween(String item) {
    	if(!isEmpty(item)) {
    		return "'" + item.replace("_", "' and '") + "'";
    	} else {
    		return item;
    	}
    }
    
    /**
     * 判断字符串是否内容相同
     * @param s1  第1个输入字符串
     * @param s2  第2个输入字符串
     * @return 布尔值=true：两个字符串相等
     *                =false:两个字符串不相等
     */
    public static boolean equals(String s1, String s2) {
        if (null == s1) {
            return false;
        }
        return s1.equals(s2);
    }
    
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static String createUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 生成无“-”的UUID
	 * 
	 * @return
	 */
	public static String createSystemDataPrimaryKey() {
		return StringUtils.replace(createUUID(), "-", "");
	}
	
	/**
     * 根据指定的delima标志获取输入字符串的后缀
     * @param str 输入字符串
     * @param delima 指定的标志,一般是一个字符，如“.”
     * @return 输入字符串子的后缀
     */
    public static String getLastSuffix(String str, String delima) {
        if (isEmpty(delima)) {
            return str;
        }

        String suffix = "";
        if (isNotEmpty(str)) {
            int index = str.lastIndexOf(delima);
            if (index >= 0) {
                suffix = str.substring(index + delima.length());
            } else {
                suffix = str;
            }
        }
        return suffix;
    }
    
    /**
     * 把字符串的第一个字符变为大写
     * @param s 输入字符串
     * @return 返回第一个字符是大写的字符串
     */
    public static String upperFirst(String s) {
        String str = null;
        if (null != s) {
            if (s.length() == 1) {
                str = s.toUpperCase();
            } else {
                str = s.substring(0, 1).toUpperCase() + s.substring(1);
            }
        }
        return str;
    }
}
