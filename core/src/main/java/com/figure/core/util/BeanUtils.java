package com.figure.core.util;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BeanUtils工具类二次封装
 *
 * @author feather
 * @date 2021-04-06
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * Bean方法名中属性名开始的下标
     */
    private static final int BEAN_METHOD_PROP_INDEX = 3;
    /**
     * 匹配getter方法的正则表达式
     */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");
    /**
     * 匹配setter方法的正则表达式
     */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * 将源对象的属性值复制到目标对象中
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyTo(Object source, Object target) {
        try {
            copyProperties(source, target);
        } catch (Exception ignored) {
        }
    }

    /**
     * 将源对象的属性值复制到目标对象中
     *
     * @param source  源对象
     * @param target  目标对象
     * @param ignores 忽略的属性
     */
    public static void copyTo(Object source, Object target, String... ignores) {
        try {
            copyProperties(source, target, ignores);
        } catch (Exception ignored) {
        }
    }

    /**
     * 将源对象的属性值复制到目标对象中, 忽略值为NULL的属性
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getIgnoreNames(source));
    }

    /**
     * 将源对象的属性值复制到目标对象中, 源对象属性为空时不进行拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyNotNull(Object source, Object target) {
        CopyConverter copyConverter = new CopyConverter(target);
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), true);
        copier.copy(source, target, copyConverter);
    }

    /**
     * 根据Class泛型对象生成一个目标对象，并将源对象的属性值复制到目标对象中
     *
     * @param source 源对象
     * @param target 目标对象class
     * @param <T>    目标对象泛型
     * @return 复制属性值后的目标对象
     */
    public static <T> T copyTo(Object source, Class<T> target) {
        if (source == null || target == null) {
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.getDeclaredConstructor().newInstance();
        } catch (Exception ignored) {
        }
        if (targetObject != null) {
            copyTo(source, targetObject);
        }
        return targetObject;
    }

    /**
     * 获取对象的setter方法。
     *
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj) {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找setter方法
        for (Method method : methods) {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1)) {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     *
     * @param obj 对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj) {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods) {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0)) {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     *
     * @param m1 方法名1
     * @param m2 方法名2
     * @return 属性名一样返回true，否则返回false
     */

    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }

    /**
     * 获取源对象中属性值为NULL的所有属性名称
     *
     * @param source 源对象
     * @return 属性名
     */
    public static String[] getIgnoreNames(Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = beanWrapper.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 复制转换器（源对象属性为空时不进行拷贝）
     *
     * @author feather
     * @date 2021-04-06
     */
    @Data
    public static class CopyConverter implements Converter {

        private Object targetObject;

        @Override
        public Object convert(Object value, Class targetClass, Object context) {
            try {
                if (ObjectUtils.isEmpty(value)) {
                    String name = StringUtils.substring(ObjectUtils.nullSafeToString(context), 3);
                    String fieldName = StringUtils.uncapitalize(name);
                    return FieldUtils.readField(targetObject, fieldName, true);
                }
            } catch (Exception ignored) {
            }
            return value;
        }

        public CopyConverter(Object targetObject) {
            this.targetObject = targetObject;
        }

    }

}
