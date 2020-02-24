package xyz.annorit24.simplequestscore.core.trigger;

import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.actions.Action;
import xyz.annorit24.simplequestsapi.condition.Condition;
import xyz.annorit24.simplequestscore.quests.actions.IncrementStepAction;
import xyz.annorit24.simplequestscore.utils.events.EventsUtils;

import java.util.HashMap;
import java.util.Map;


public class TriggerProcessing {

    private Event event;
    private Map<Integer, Condition> conditions;
    private Map<Integer, Boolean> results;
    private Map<Integer, Action> actions;
    private ActionsCall actionsCall;

    public TriggerProcessing(Event event,Trigger trigger) {
        this.event = event;
        this.conditions = trigger.getConditions();
        this.actions = trigger.getActions();
        this.results = new HashMap<>();
        this.actionsCall = new ActionsCall(actions,results,event);
    }

    public void processTrigger(){
        callConditions();
        // TODO: 22/01/2020 Don't forget to order map https://beginnersbook.com/2013/12/how-to-sort-hashmap-in-java-by-keys-and-values/
        actionsCall.run();
    }

    private void callConditions(){
        System.out.println("Call conditions");
        for (Map.Entry<Integer, Condition> entry : conditions.entrySet()) {
            Integer integer = entry.getKey();
            Condition condition = entry.getValue();

            System.out.println("Condition "+integer+": "+condition.getClass().getName());
            boolean result = condition.call(event);
            System.out.println("result : "+result);
            if(!result)condition.manageInvalidCondition(event);
            results.put(integer, result);
        }
    }

    public Boolean getReprocess() {
        return actionsCall.isReprocess();
    }

    public synchronized Boolean isFinish() {
        return actionsCall.isFinish();
    }
}
