package xyz.annorit24.simplequestscore.core.trigger.runnables;

import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.QuestsManager;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.trigger.TriggerManager;
import xyz.annorit24.simplequestscore.utils.Utils;

import java.util.List;

/**
 * @author Annorit24
 * Created on 21/01/2020
 */
public class TriggerLoader implements Runnable{

    private Client client;
    private QuestsManager questsManager;
    private TriggerManager triggerManager;

    public TriggerLoader(Client client, SimpleQuestsCore plugin) {
        this.client = client;
        this.questsManager = plugin.getQuestsManager();
        this.triggerManager = plugin.getTriggerManager();
    }

    @Override
    public void run() {
        List<QuestInfo> activeQuests = client.getActivesQuests();

        for (QuestInfo activeQuest : activeQuests) {
            System.out.println(activeQuest.getQuestId());
            System.out.println(activeQuest.getStep());
            Utils.buildTriggers(activeQuest,client);
            /*String questId = activeQuest.getQuestId();
            Quest quest = questsManager.getQuest(questId);

            if(quest == null){
                LogUtils.ERROR.log("@TriggerLoader:run Could not load quest with the questId : "+questId);
                return;
            }

            List<QuestStep> questSteps = quest.getQuestStep(activeQuest);
            TriggerFactory factory = triggerManager.getTriggerFactory();
            for (QuestStep questStep : questSteps) {
                factory.buildTrigger(client,questStep);
            }*/
        }
    }

}
