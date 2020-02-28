package xyz.annorit24.simplequestscore.core.thread;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Annorit24
 * Created on 25/02/2020
 */
public class PipelineThreadPoolExecutor extends ThreadPoolExecutor {

    /**
     * atomic id of thread which will increase on a creation of a new thread<br>
     * It will be use for the {@link PipelineThreadFactory}
     */
    private final AtomicInteger threadId = new AtomicInteger(1);

    // TODO: 27/02/2020 : log parameters in console e.g. max pool size

    //constructors matching super
    public PipelineThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
    public PipelineThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }
    public PipelineThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }
    public PipelineThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * Submit a new job to the thread pool executor in order to process the pipeline job in a new thread
     *
     * @param job the job to process
     * @param playerUniqueId the id of the player to whom the pipeline belongs
     */
    public synchronized void submitPipelineJob(Callable<Void> job, UUID playerUniqueId){
        PipelineThreadFactory factory = new PipelineThreadFactory(playerUniqueId,threadId);
        this.setThreadFactory(factory);
        submit(job);
        this.setThreadFactory(new DefaultThreadFactory(threadId));
    }
}
