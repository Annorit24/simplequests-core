package xyz.annorit24.simplequestscore.quests.actions;

import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.ActionParameter;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.utils.Callback;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;

import java.util.List;

/**
 * @author Annorit24
 * Created on 03/03/2020
 */
public class StartQuestAction extends Action implements Cloneable{

    private String questId;

    public StartQuestAction(List<Integer> requireValidConditions, String questId) {
        super(requireValidConditions, true, false);
        this.questId = questId;
    }

    @Override
    public Callback<ComponentResult> call(ActionParameter actionParameter) {
        Player player = actionParameter.getPlayer();

        Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
        if(client == null) return () -> {
                player.sendMessage("Could not get client corresponding to the player "+player.getName()+" with the following id : "+player.getUniqueId());
                return ComponentResult.CRITICAL_FAILURE;
        };

        client.addActiveQuests(questId);
        return () -> ComponentResult.SUCCESS;
    }

    @Override
    public Action cloneAction() {
        return new StartQuestAction(getRequireValidConditions(),questId);
    }

    @Override
    protected StartQuestAction clone(){
        return new StartQuestAction(getRequireValidConditions(),questId);
    }
}
