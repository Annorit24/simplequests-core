package xyz.annorit24.simplequestscore.core.trigger;

import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.QuestStep;

public class TriggerFactory {

    private TriggerManager triggerManager;

    public TriggerFactory(TriggerManager triggerManager) {
        this.triggerManager = triggerManager;
    }

    public void buildTrigger(Client client, QuestStep questStep){
        Trigger trigger = createTrigger(client,questStep);
        trigger.addConditions(questStep.getConditions())
                .addActions(questStep.getActions());
        triggerManager.registerTrigger(trigger);
    }

    public void loadPersistedTriggers(){
        // TODO: 21/01/2020 : Make persistence system for trigger
    }

    private Trigger createTrigger(Client client, QuestStep questStep){
        return new Trigger(
                questStep.getEvent(),
                client.getUniqueId(),
                questStep.getQuestStepInfo(),
                questStep.getQuestStepId()
        );
    }
}
