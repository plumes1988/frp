package com.figure.core.util.copycat.time;

/**
 * 时间处理根接口
 *
 * @author feather
 * @date 2021-04-06
 * @since 1.2.0
 */
public interface TimeProcessor<T> {

    /**
     * 是否支持处理改该 类型(T) 的时间
     *
     * @param clazz 时间类型
     * @return 布尔值
     */
    boolean supports(Class<?> clazz);

    /**
     * 处理时间
     *
     * @param timeStamp 时间戳
     * @param clazz     时间类型
     * @return T 类型
     */
    T handleTime(Long timeStamp, Class<?> clazz);

}
