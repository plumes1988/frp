package com.figure.core.util.copycat.hook;

import com.figure.core.util.copycat.advisor.CriteriaAnnotationProcessorAdvisor;
import com.figure.core.util.copycat.time.TimeProcessorContainer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CopycatShutdownHook
 *
 * @author feather
 * @date 2020/09/29
 * @see CriteriaAnnotationProcessorAdvisor
 * @see TimeProcessorContainer
 * @since 1.3.0
 */
public class CopycatShutdownHook extends Thread {

    private final AtomicBoolean destroyed = new AtomicBoolean(false);

    static {
        Runtime.getRuntime().addShutdownHook(getInstance());
    }

    private CopycatShutdownHook(final String name) {
        super(name);
    }

    private static class CopycatShutdownHookFactory {
        private static final CopycatShutdownHook INSTANCE = new CopycatShutdownHook("copycat-shutdown-hook");
    }

    /**
     * Gets CopycatShutdownHook instance.
     *
     * @return {@link CopycatShutdownHook}
     */
    public static CopycatShutdownHook getInstance() {
        return CopycatShutdownHookFactory.INSTANCE;
    }

    @Override
    public void run() {
        closeAll();
    }

    private void closeAll() {
        if (!destroyed.compareAndSet(false, true)) {
            return;
        }
        // 清理-缓存
        CriteriaAnnotationProcessorAdvisor.destroyProcessorCache();
        TimeProcessorContainer.destroyProcessorCache();
    }

}
