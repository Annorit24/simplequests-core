package xyz.annorit24.simplequestscore.quests.actions;

import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.ActionParameter;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.utils.Callback;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.utils.client.ClientUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 22/01/2020
 */
public class IncrementStepAction extends Action {

    private String questId;
    private boolean reprocess;

    public IncrementStepAction(List<Integer> requireValidConditions, boolean customCall, String questId) {
        super(requireValidConditions, true, customCall);
        this.questId = questId;
    }

    /*public IncrementStepAction(List<Integer> validConditions, boolean customCall, String questId) {
        super(validConditions, customCall);
        this.questId = questId;
        this.reprocess = true;
    }*/


    /*public void call(Player player, Map<Integer, Boolean> result) {
        if(isConditionsValid(result)) {
            Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
            QuestInfo info = ClientUtils.getQuestInfoFromQuestId(client, questId);
            if (info == null) return;
            info.setStep(info.getStep() + 1);
            reprocess = false;
        }else{
            reprocess = true;
        }
        finish = true;
    }*/



    public boolean isReprocess() {
        return reprocess;
    }

    @Override
    public Callback<ComponentResult> call(ActionParameter actionParameter) {
        Map<Integer, Boolean> result = actionParameter.getConditionsResults();
        Player player = actionParameter.getPlayer();

        if(isConditionsValid(result)) {
            Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
            QuestInfo info = ClientUtils.getQuestInfoFromQuestId(client, questId);

            if (info == null) return () -> {
                player.sendMessage("Could not get quest info for "+player.getName()+" with the quest id : "+questId);
                return ComponentResult.CRITICAL_FAILURE;
            };

            info.setStep(info.getStep() + 1);
            reprocess = false;
            return () -> ComponentResult.SUCCESS;
        }else{
            reprocess = true;
            return () -> ComponentResult.FAILURE;
        }

    }
}
