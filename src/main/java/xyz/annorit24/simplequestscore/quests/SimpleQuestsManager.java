package xyz.annorit24.simplequestscore.quests;

import xyz.annorit24.simplequestsapi.quest.Quest;
import xyz.annorit24.simplequestsapi.quest.QuestsManager;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;
import xyz.annorit24.simplequestscore.quests.loader.QuestLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 21/01/2020
 */
public class SimpleQuestsManager extends QuestsManager {

    private Map<String, Quest> quests;
    private QuestLoader questLoader;

    public SimpleQuestsManager() {
        this.quests = new HashMap<>();
        // TODO: 21/01/2020 : Loading quests here using questLoader
    }

    @Override
    public void registerQuest(Quest quest){
        if(quests.containsKey(quest.getQuestId())){
            LogUtils.WARNING.log("Could not load quest :"+quest.getQuestName()+". Quest already registered with this id :"+quest.getQuestId());
            return;
        }
        quests.put(quest.getQuestId(),quest);
    }

    @Override
    public Quest getQuest(String questId){
        if(!quests.containsKey(questId)){
            LogUtils.WARNING.log("Could not get quest with the id : "+questId+". It doesn't exist.");
            return null;
        }
        return quests.get(questId);
    }


}
