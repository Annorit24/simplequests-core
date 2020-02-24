package xyz.annorit24.simplequestscore.quests.actions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.actions.Action;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
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
        super(validConditions, customCall);
        this.questId = questId;
        this.reprocess = true;
    }

    @Override
    public void call(Player player, Map<Integer, Boolean> result) {
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
