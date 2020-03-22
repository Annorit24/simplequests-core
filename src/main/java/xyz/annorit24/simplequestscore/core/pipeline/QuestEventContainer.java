package xyz.annorit24.simplequestscore.core.pipeline;

import xyz.annorit24.simplequestsapi.pipeline.Trigger;
import xyz.annorit24.simplequestsapi.quest.Container;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.quest.components.Condition;

import java.util.Map;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 04/02/2020
 */
public final class QuestEventContainer extends Container {

    private Trigger processingTrigger;

    public QuestEventContainer(UUID playerUUID, UUID bukkitEventUUID, Trigger processingTrigger) {
        super(playerUUID, bukkitEventUUID, processingTrigger.getActions(), processingTrigger.getConditions());
        this.processingTrigger = processingTrigger;
    }

    public Trigger getProcessingTrigger() {
        return processingTrigger;
    }

    public void setProcessingTrigger(Trigger processingTrigger) {
        this.processingTrigger = processingTrigger;
    }
}
