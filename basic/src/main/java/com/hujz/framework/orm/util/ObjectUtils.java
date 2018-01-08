package com.hujz.framework.orm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.util.Assert;

public class ObjectUtils extends org.springframework.util.ObjectUtils {
	/**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 */
	public static Object getFieldValue(Object object,
			String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("never happend exception!", e);
		}
		return result;
	}
	
	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 */
	public static void setFieldValue(final Object object,
			final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("never happend exception!", e);
		}
	}
	
	/**
	 * 循环向上转型,获取类的DeclaredField.
	 */
	protected static Field getDeclaredField(final Class<?> clazz,
			final String fieldName) {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}
	
	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 */
	protected static Field getDeclaredField(final Object object,
			final String fieldName) {
		Assert.notNull(object);
		return getDeclaredField(object.getClass(), fieldName);
	}
	
	/**
	 * 强制转换fileld可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}
	
	/**
     * 获取不含有包名称的类名称
     * @param className 带包名的类名称
     * @return 类名
     */
    public static String getBaseClassName(String className) {
        return StringUtils.getLastSuffix(className, ".");
    }
    
    /**
     *获取对象的属性
     *
     * @param object 对象
     * @param sProperty 对象的Bean属性
     * @return  对象的属性方法
     */
    public static Object getProperty(Object object, String sProperty) {
        if (null == object || null == sProperty) {
            return null;
        }
        try {
            Method method = object.getClass().getMethod("get" + StringUtils.upperFirst(sProperty));
            return method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
