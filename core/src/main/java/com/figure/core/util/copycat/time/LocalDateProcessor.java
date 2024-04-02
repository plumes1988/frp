package com.figure.core.util.copycat.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 默认的时间处理器-处理为 {@link LocalDate} 对象
 *
 * @author feather
 * @date 2021-04-06
 * @since 1.2.0
 */
public class LocalDateProcessor implements TimeProcessor<LocalDate> {

    @Override
    public boolean supports(Class<?> clazz) {
        return LocalDate.class.equals(clazz);
    }

    @Override
    public LocalDate handleTime(Long timeStamp, Class<?> clazz) {
        Instant instant = Instant.ofEpochMilli(timeStamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

}
