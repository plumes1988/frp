package com.figure.op.videoplay.utils;

import java.util.concurrent.ScheduledFuture;

/**
 * @author sdt
 * @version 1.0
 * @description:cheduledFuture的包装类。ScheduledFuture是ScheduledExecutorService定时任务线程池的执行结果。
 * @date 2023/9/14 9:49
 */
public final class ScheduledTask {
    public volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
