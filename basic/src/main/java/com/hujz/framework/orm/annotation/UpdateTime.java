package com.hujz.framework.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *********************************************** 
 * @Create Date: 2015年9月17日 下午4:20:30
 * @Create Author: jiuzhou.hu
 * @File Name: UpdateTime
 * @Function: 设置更新时间为当前时间
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface UpdateTime {

}
