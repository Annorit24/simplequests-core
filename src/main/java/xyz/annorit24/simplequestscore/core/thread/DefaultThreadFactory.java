package xyz.annorit24.simplequestscore.core.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Annorit24
 * Created on 27/02/2020
 */
public class DefaultThreadFactory implements ThreadFactory {

    private final AtomicInteger threadId;
    private final String prefix = "DEAULT_PIPELINE_JOB";

    public DefaultThreadFactory(AtomicInteger threadId) {
        this.threadId = threadId;
    }

    @Override
    public Thread newThread(Runnable command) {
        Thread thread = Executors.defaultThreadFactory().newThread(command);
        thread.setName(prefix + "- [#" + threadId.incrementAndGet()+"]");
        return thread;
    }
}
