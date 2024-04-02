package com.figure.core.util.copycat.time;

import com.figure.core.util.copycat.util.TimeUtils;

import java.time.LocalDateTime;

/**
 * 默认的时间处理器-处理为 {@link LocalDateTime} 对象
 *
 * @author feather
 * @date 2021-04-06
 * @since 1.2.0
 */
public class LocalDateTimeProcessor implements TimeProcessor<LocalDateTime> {

    @Override
    public boolean supports(Class<?> clazz) {
        return LocalDateTime.class.equals(clazz);
    }

    @Override
    public LocalDateTime handleTime(Long timeStamp, Class<?> clazz) {
        return TimeUtils.timestampToLocalDateTime(timeStamp);
    }

}
