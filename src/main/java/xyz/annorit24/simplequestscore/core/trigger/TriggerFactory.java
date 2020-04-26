package xyz.annorit24.simplequestscore.core.trigger;

import xyz.annorit24.simplequestsapi.pipeline.Trigger;
import xyz.annorit24.simplequestsapi.quest.QuestStep;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TriggerFactory {

    private TriggerManager triggerManager;

    public TriggerFactory(TriggerManager triggerManager) {
        this.triggerManager = triggerManager;
    }

    public void buildTrigger(UUID clientUUID, QuestStep questStep){
        Trigger trigger = createTrigger(clientUUID,questStep);
        trigger.addConditions(questStep.getConditions())
                .addActions(questStep.getActions()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().cloneAction()))
                );
        triggerManager.registerTrigger(trigger);
    }

    private Trigger createTrigger(UUID clientUUID, QuestStep questStep){
        return new SimpleTrigger(
                questStep.getEvent(),
                clientUUID,
                questStep.getQuestStepInfo(),
                questStep.getQuestStepId()
        );
    }
}
