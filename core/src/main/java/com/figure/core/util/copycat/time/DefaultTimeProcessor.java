package com.figure.core.util.copycat.time;

import com.figure.core.util.copycat.util.TimeUtils;

import java.util.Date;

/**
 * 默认的时间处理器-处理为 {@link Date} 对象
 *
 * @author feather
 * @date 2021-04-06
 * @since 1.2.0
 */
public class DefaultTimeProcessor implements TimeProcessor<Date> {

    @Override
    public boolean supports(Class<?> clazz) {
        return Date.class.equals(clazz);
    }

    @Override
    public Date handleTime(Long timeStamp, Class<?> clazz) {
        return TimeUtils.toTime(timeStamp, clazz);
    }

}
