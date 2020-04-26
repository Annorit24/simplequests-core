package xyz.annorit24.simplequestscore.quests.actions;

import org.bukkit.event.Listener;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.ActionParameter;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.utils.Callback;

import java.util.List;

/**
 * @author Annorit24
 * Created on 22/03/2020
 */
public class FollowNPCAction extends Action {

    public FollowNPCAction(List<Integer> requireValidConditions, boolean critical, boolean customCall) {
        super(requireValidConditions, critical, customCall);
    }

    @Override
    public Callback<ComponentResult> call(ActionParameter actionParameter) {
        return null;
    }

    @Override
    public Action cloneAction() {
        return null;
    }
}
