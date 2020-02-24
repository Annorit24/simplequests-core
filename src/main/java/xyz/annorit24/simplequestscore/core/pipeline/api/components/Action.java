package xyz.annorit24.simplequestscore.core.pipeline.api.components;

import xyz.annorit24.simplequestsapi.quest.QuestStep;
import xyz.annorit24.simplequestscore.utils.Callback;

import java.util.List;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 21/01/2020
 */
public abstract class Action implements Component<ActionParameter> {

    private List<Integer> requireValidConditions;
    private boolean critical;

    protected QuestStep questStep;
    private boolean customCall;
    protected volatile boolean finish;

    public Action(List<Integer> requireValidConditions, boolean critical, boolean customCall) {
        this.requireValidConditions = requireValidConditions;
        this.critical = critical;
        this.customCall = customCall;
        this.finish = false;
    }

    public abstract Callback<ComponentResult> call(ActionParameter parameter);

    public List<Integer> getRequireValidConditions() {
        return requireValidConditions;
    }

    protected boolean isConditionsValid(Map<Integer, Boolean> results){
        if(results == null)return true;
        for (Map.Entry<Integer, Boolean> entry : results.entrySet()) {
            Integer integer = entry.getKey();
            Boolean aBoolean = entry.getValue();

            if(requireValidConditions.contains(integer) && !aBoolean)return false;
        }
        return true;
    }

    public QuestStep setQuestStep(QuestStep questStep) {
        this.questStep = questStep;
        return questStep;
    }

    public boolean isCustomCall() {
        return customCall;
    }

    public synchronized boolean isFinish() {
        return finish;
    }

    public synchronized void setFinish(boolean finish) {
        this.finish = finish;
    }

    public boolean isCritical() {
        return critical;
    }
}
