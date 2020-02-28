package xyz.annorit24.simplequestscore.core.thread;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Annorit24
 * Created on 25/02/2020
 */
public class PipelineThreadFactory implements ThreadFactory {

    private final UUID playerUUID;
    private final AtomicInteger threadId;
    private final String prefix = "PLAYER_PIPELINE_JOB";

    private static Map<UUID, ThreadGroup> playersThreadGroup = new HashMap<>();

    public PipelineThreadFactory(UUID playerUUID, AtomicInteger threadId) {
        this.playerUUID = playerUUID;
        this.threadId = threadId;
    }

    @Override
    public Thread newThread(Runnable command) {
        if(command == null){
            // TODO: 27/02/2020 log command is null
        }

        return new Thread(
                getPlayerThreadGroup(playerUUID),
                command,
                prefix + "{"+playerUUID.toString()+"} - [#" + threadId.incrementAndGet()+"]"
        );
    }

    public ThreadGroup getPlayerThreadGroup(UUID playerUUID){
        if(playersThreadGroup.containsKey(playerUUID))return playersThreadGroup.get(playerUUID);
        return playersThreadGroup.put(playerUUID, new ThreadGroup("PipelineThreadGroup#"+playerUUID.toString()));
    }

}
