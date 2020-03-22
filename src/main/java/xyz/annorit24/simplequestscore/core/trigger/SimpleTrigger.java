package xyz.annorit24.simplequestscore.core.trigger;

import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.pipeline.Trigger;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.Condition;

import java.util.*;

public class SimpleTrigger extends Trigger {

    private final Class<? extends Event> event;
    private final UUID playerUniqueId;
    private final QuestInfo questInfo;

    private final UUID questStepId;

    private Map<Integer, Condition> conditions;
    private Map<Integer, Action> actions;
    private boolean processing;

    public SimpleTrigger(Class<? extends Event> event, UUID playerUniqueId, QuestInfo questInfo, UUID questStepId) {
        this.event = event;
        this.playerUniqueId = playerUniqueId;
        this.questInfo = questInfo;
        this.questStepId = questStepId;
        this.processing = false;
        this.conditions = new HashMap<>();
        this.actions = new HashMap<>();
    }

    @Override
    public boolean isProcessing() {
        return processing;
    }

    @Override
    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    @Override
    public Class<? extends Event> getEvent() {
        return event;
    }

    @Override
    public UUID getPlayerUniqueId() {
        return playerUniqueId;
    }

    @Override
    public SimpleTrigger addConditions(Map<Integer, Condition> conditions){
        this.conditions.putAll(conditions);
        return this;
    }

    @Override
    public SimpleTrigger addActions(Map<Integer, Action> actions){
        this.actions.putAll(actions);
        return this;
    }

    @Override
    public Map<Integer, Condition> getConditions() {
        return conditions;
    }

    @Override
    public Map<Integer, Action> getActions() {
        return actions;
    }

    @Override
    public QuestInfo getQuestInfo() {
        return questInfo;
    }

    @Override
    public UUID getQuestStepId() {
        return questStepId;
    }
}
