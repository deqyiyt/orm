package com.hujz.framework.orm.mybatis.util;

import com.hujz.framework.orm.mybatis.entity.Example;

/**
 * OGNL静态方法
 */
public abstract class OGNL {

    /**
     * 是否包含自定义查询列
     * @param parameter
     * @return
     */
    public static boolean hasSelectColumns(Object parameter){
        if(parameter != null && parameter instanceof Example){
            Example example = (Example)parameter;
            if(example.getSelectColumns() != null && example.getSelectColumns().size() > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 不包含自定义查询列
     * @param parameter
     * @return
     */
    public static boolean hasNoSelectColumns(Object parameter){
        return !hasSelectColumns(parameter);
    }
}
