package xyz.annorit24.simplequestscore.core.thread;

import xyz.annorit24.simplequestscore.utils.logger.LogUtils;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Annorit24
 * Created on 12/02/2020
 */
public class ShutdownRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        LogUtils.DEBUG.log("Thread rejected : "+r.getClass().getName());    
    }
}
