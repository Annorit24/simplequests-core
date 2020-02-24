package xyz.annorit24.simplequestscore.utils;

import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.Quest;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.QuestStep;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.trigger.TriggerFactory;
import xyz.annorit24.simplequestscore.utils.logger.LogUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Annorit24
 * Created on 22/01/2020
 */
public class Utils {

    public static QuestInfo getQuestInfoFromQuestId(Client client, String questId){
        List<QuestInfo> activesQuests = client.getActivesQuests();
        for (QuestInfo activesQuest : activesQuests) {
            if(activesQuest.getQuestId().equals(questId)){
                return activesQuest;
            }
        }
        return null;
    }

    public static void buildTriggers(QuestInfo questInfo, Client client){
        String questId = questInfo.getQuestId();
        Quest quest = SimpleQuestsCore.getInstance().getQuestsManager().getQuest(questId);

        if(quest == null){
            LogUtils.ERROR.log("@TriggerLoader:run Could not load quest with the questId : "+questId);
            return;
        }

        List<QuestStep> questSteps = quest.getQuestStep(questInfo);
        TriggerFactory factory = SimpleQuestsCore.getInstance().getTriggerManager().getTriggerFactory();
        for (QuestStep questStep : questSteps) {
            System.out.println(questStep.getQuestStepInfo().getStep());
            System.out.println("Factoring");
            factory.buildTrigger(client,questStep);
        }
    }

    public static ThreadPoolExecutor createThreadPool(int max_pool, long pool_thread_keep_alive, String slug) {
        ThreadPoolExecutor threadPool=new ThreadPoolExecutor(
                0,
                max_pool,
                pool_thread_keep_alive,
                TimeUnit.MINUTES, new SynchronousQueue<>()
        );

        ThreadFactory factory=new ThreadFactory() {
            private final AtomicInteger thread_id=new AtomicInteger(1);

            @Override
            public Thread newThread(final Runnable command) {
                Thread thread = Executors.defaultThreadFactory().newThread(command);
                thread.setName(slug + "- [#" + thread_id.incrementAndGet()+"]");
                return thread;
            }

        };

        threadPool.setThreadFactory(factory);
        threadPool.setRejectedExecutionHandler(new ShutdownRejectedExecutionHandler());

        // TODO: 24/02/2020 : make a thread group for each player and register them in a "QuestThreadManager" in a hash map with the uuid of the player as key https://www.javatpoint.com/threadgroup-in-java
        
        return threadPool;
    }
}
