package xyz.annorit24.simplequestscore.quests;

import xyz.annorit24.simplequestsapi.quest.Quest;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.QuestStarter;
import xyz.annorit24.simplequestsapi.quest.QuestStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 21/01/2020
 */
public class SimpleQuest extends Quest {

    private Map<QuestInfo, List<QuestStep>> questSteps;
    private String questId;
    private String questName;
    private String description;

    private QuestStarter questStarter;
    private Integer maxPlayersNumber;
    private Integer minPlayersNumber;

    public SimpleQuest(Map<QuestInfo, List<QuestStep>> questSteps, String questId, String questName, String description, QuestStarter questStarter, Integer maxPlayersNumber, Integer minPlayersNumber) {
        this.questSteps = questSteps;
        this.questId = questId;
        this.questName = questName;
        this.description = description;
        this.questStarter = questStarter;
        this.maxPlayersNumber = maxPlayersNumber;
        this.minPlayersNumber = minPlayersNumber;
    }

    @Override
    public Map<QuestInfo, List<QuestStep>> getQuestSteps() {
        return questSteps;
    }

    @Override
    public List<QuestStep> getQuestStep(QuestInfo info) {

        List<QuestStep> steps = new ArrayList<>();

        for (Map.Entry<QuestInfo, List<QuestStep>> entry : questSteps.entrySet()) {
            QuestInfo questInfo = entry.getKey();
            List<QuestStep> questSteps = entry.getValue();

            if(info.equals(questInfo)){
                steps.addAll(questSteps);
                break;
            }

        }

        return steps;
    }

    @Override
    public String getQuestId() {
        return questId;
    }

    @Override
    public String getQuestName() {
        return questName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Integer getMinPlayersNumber() {
        return minPlayersNumber;
    }

    @Override
    public Integer getMaxPlayersNumber() {
        return maxPlayersNumber;
    }

    @Override
    public QuestStarter getQuestStarter() {
        return questStarter;
    }
}
