package xyz.annorit24.simplequestscore.utils.client;

import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;

import java.util.List;

/**
 * @author Annorit24
 * Created on 22/01/2020
 */
public class ClientUtils {

    public static QuestInfo getQuestInfoFromQuestId(Client client, String questId){
        List<QuestInfo> activesQuests = client.getActivesQuests();
        for (QuestInfo activesQuest : activesQuests) {
            if(activesQuest.getQuestId().equals(questId)){
                return activesQuest;
            }
        }
        return null;
    }

}
