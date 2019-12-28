package xyz.annorit24.simplequestscore.core.trigger;

import org.bukkit.event.Event;
import xyz.annorit24.simplequestscore.quest.QuestInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Condition;

public class Trigger {

    private final Class<? extends Event> event;
    private final UUID playerUniqueId;
    private final QuestInfo questInfo;

    private List<Condition> conditions;
    private boolean processing;

    public Trigger(Class<? extends Event> event, UUID playerUniqueId, QuestInfo questInfo) {
        this.event = event;
        this.playerUniqueId = playerUniqueId;
        this.questInfo = questInfo;
        this.processing = false;
        this.conditions = new ArrayList<>();
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

    public Trigger addCondition(Condition... conditions){
        this.conditions.addAll(Arrays.asList(conditions));
        return this;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public QuestInfo getQuestInfo() {
        return questInfo;
    }
}
