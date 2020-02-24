package xyz.annorit24.simplequestscore.core.pipeline;

import xyz.annorit24.simplequestscore.core.pipeline.api.components.Action;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ComponentResult;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.Condition;

import java.util.Map;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 04/02/2020
 */
public class QuestEventContainer {

    private UUID playerUUID;
    private UUID bukkitEventUUID;

    private Map<Integer, Action> actions;
    private Map<Integer, ComponentResult> actionsResult;

    private Map<Integer, Condition> conditions;
    private Map<Integer, ComponentResult> conditionsResult;

    /**
     * Current position of the packet in the pipeline
     */
    private Integer indexPosition;

    public void setActionsResult(Map<Integer, ComponentResult> actionsResult) {
        this.actionsResult = actionsResult;
    }
    public void setConditionsResult(Map<Integer, ComponentResult> conditionsResult) {
        this.conditionsResult = conditionsResult;
    }

    public Map<Integer, Action> getActions() {
        return actions;
    }
    public Map<Integer, ComponentResult> getActionsResult() {
        return actionsResult;
    }
    public Map<Integer, Condition> getConditions() {
        return conditions;
    }
    public Map<Integer, ComponentResult> getConditionsResult() {
        return conditionsResult;
    }

    public Integer getIndexPosition() {
        return indexPosition;
    }

    public void setIndexPosition(Integer indexPosition) {
        this.indexPosition = indexPosition;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public UUID getBukkitEventUUID() {
        return bukkitEventUUID;
    }
}
