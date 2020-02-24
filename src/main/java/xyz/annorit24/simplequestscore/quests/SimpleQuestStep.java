package xyz.annorit24.simplequestscore.quests;

import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.actions.Action;
import xyz.annorit24.simplequestsapi.condition.Condition;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.QuestStep;

import java.util.Map;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 21/01/2020
 */
public class SimpleQuestStep extends QuestStep {

    private UUID id;
    private Class<? extends Event> event;
    private String description;
    private QuestInfo questInfo;
    private Map<Integer,Condition> conditions;
    private Map<Integer, Action> actions;

    public SimpleQuestStep(
            UUID id,
            Class<? extends Event> event,
            String description,
            QuestInfo questInfo,
            Map<Integer, Condition> conditions,
            Map<Integer, Action> actions
    ) {
        this.id = id;
        this.event = event;
        this.description = description;
        this.questInfo = questInfo;
        this.conditions = conditions;
        this.actions = actions;
    }

    @Override
    public UUID getQuestStepId() {
        return id;
    }

    @Override
    public Class<? extends Event> getEvent() {
        return event;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public QuestInfo getQuestStepInfo() {
        return questInfo;
    }

    @Override
    public Map<Integer, Condition> getConditions() {
        return conditions;
    }

    @Override
    public Map<Integer, Action> getActions() {
        return actions;
    }

}
