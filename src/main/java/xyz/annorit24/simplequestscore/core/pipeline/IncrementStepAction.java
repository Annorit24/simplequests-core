package xyz.annorit24.simplequestscore.core.pipeline;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.Action;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ComponentResult;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ActionParameter;
import xyz.annorit24.simplequestscore.utils.Callback;
import xyz.annorit24.simplequestscore.utils.client.ClientUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 22/01/2020
 */
public class IncrementStepAction extends Action {

    private String questId;
    private boolean reprocess;

    public IncrementStepAction(List<Integer> validConditions, boolean customCall, String questId) {
        super(validConditions, critical, customCall);
        this.questId = questId;
        this.reprocess = true;
    }

    @Override
    public Callback<ComponentResult> call(ActionParameter parameter) {

        Player player = parameter.getPlayer();
        Map<Integer, Boolean> results = parameter.getConditionsResults();

        if(isConditionsValid(results)) {
            Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
            QuestInfo info = ClientUtils.getQuestInfoFromQuestId(client, questId);
            if (info == null) return () -> {
                player.sendMessage("Could not get quest info for "+player.getName()+" with the quest id : "+questId);
                return ComponentResult.CRITICAL_FAILURE;
            };
            info.setStep(info.getStep() + 1);
            reprocess = false;
        }else{
            reprocess = true;
        }
        finish = true;
        return () -> ComponentResult.SUCCESS;
    }

    private Player getPlayer(Event event){
        try {
            Method m = event.getClass().getMethod("getPlayer");
            return (Player) m.invoke(event);
        } catch (Exception ignored) {
            try {
                Method m = event.getClass().getMethod("getEntity");
                Entity entity = (Entity) m.invoke(event);
                if(entity instanceof Player){
                    return (Player) entity;
                }else{
                    return null;
                }
            } catch (Exception ignored1) {
            }
        }
        return null;
    }



    public boolean isReprocess() {
        return reprocess;
    }

}
