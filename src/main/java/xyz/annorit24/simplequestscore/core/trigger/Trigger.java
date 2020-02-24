package xyz.annorit24.simplequestscore.core.trigger;

import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.actions.Action;
import xyz.annorit24.simplequestsapi.condition.Condition;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;

import java.util.*;

public class Trigger {

    private final Class<? extends Event> event;
    private final UUID playerUniqueId;
    private final QuestInfo questInfo;

    private final UUID questStepId;

    private Map<Integer, Condition> conditions;
    private Map<Integer, Action> actions;
    private boolean processing;

    public Trigger(Class<? extends Event> event, UUID playerUniqueId, QuestInfo questInfo, UUID questStepId) {
        this.event = event;
        this.playerUniqueId = playerUniqueId;
        this.questInfo = questInfo;
        this.questStepId = questStepId;
        this.processing = false;
        this.conditions = new HashMap<>();
        this.actions = new HashMap<>();
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public Class<? extends Event> getEvent() {
        return event;
    }

    public UUID getPlayerUniqueId() {
        return playerUniqueId;
    }

    public Trigger addConditions(Map<Integer, Condition> conditions){
        this.conditions.putAll(conditions);
        return this;
    }

    public Trigger addActions(Map<Integer, Action> actions){
        this.actions.putAll(actions);
        return this;
    }

    public Map<Integer, Condition> getConditions() {
        return conditions;
    }

    public Map<Integer, Action> getActions() {
        return actions;
    }

    public QuestInfo getQuestInfo() {
        return questInfo;
    }

    public UUID getQuestStepId() {
        return questStepId;
    }
}
