package xyz.annorit24.simplequestscore.core.pipeline.api;

import xyz.annorit24.simplequestscore.utils.Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author Annorit24
 * Created on 22/02/2020
 */
public abstract class PipelineManager {

    private final ExecutorService executorService;

    public PipelineManager() {
        // TODO: 12/02/2020 : Let user change the max_pool and pool_thread_keep_alive in json config @Annorit24
        executorService = Utils.createThreadPool(100,10,"PLAYERS - PIPELINES");
    }

    public void submitPipelineJob(Callable job){
        executorService.submit(job);
    }



}
