package com.hujz.framework.orm.mybatis.util;

import com.hujz.framework.orm.mybatis.code.Style;
import com.hujz.framework.orm.util.StringUtils;

public class StringUtil extends StringUtils {



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
    
    public static String toBetween(String item) {
    	if(!isEmpty(item)) {
    		return "'" + item.replace("_", "' and '") + "'";
    	} else {
    		return item;
    	}
    }
}
