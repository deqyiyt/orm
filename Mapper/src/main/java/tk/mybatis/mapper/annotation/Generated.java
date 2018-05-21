package tk.mybatis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.TemporalType;

import tk.mybatis.mapper.code.GenerationTime;

/**
 * 持久化时是否更新时间
 * @date: 2018年1月4日 下午6:48:43
 * @author: jiuzhou.hu
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Generated {
    GenerationTime value();
    
    TemporalType type() default TemporalType.DATE;
    
    String format() default "yyyy-MM-dd HH:mm:ss:SS";
}
