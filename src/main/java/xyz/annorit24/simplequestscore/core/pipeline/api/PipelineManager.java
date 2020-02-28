package xyz.annorit24.simplequestscore.core.pipeline.api;

import xyz.annorit24.simplequestscore.core.thread.PipelineThreadPoolExecutor;
import xyz.annorit24.simplequestscore.core.thread.ShutdownRejectedExecutionHandler;
import xyz.annorit24.simplequestscore.utils.Utils;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Annorit24
 * Created on 22/02/2020
 */
public abstract class PipelineManager {

    private final PipelineThreadPoolExecutor executorService;

    public PipelineManager() {

        executorService = new PipelineThreadPoolExecutor(
                0,
                100,
                5,
                TimeUnit.MINUTES,
                new SynchronousQueue<>()
        );

        executorService.setRejectedExecutionHandler(new ShutdownRejectedExecutionHandler());

    }

    public void submitPipelineJob(Callable<Void> job, UUID playerUUID){
        executorService.submitPipelineJob(job, playerUUID);
    }



}
