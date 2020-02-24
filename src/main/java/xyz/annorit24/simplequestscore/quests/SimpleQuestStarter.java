package xyz.annorit24.simplequestscore.quests;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.actions.Action;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.condition.Condition;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.QuestStarter;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.trigger.ActionsCall;
import xyz.annorit24.simplequestscore.utils.events.EventsUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class SimpleQuestStarter extends QuestStarter {

    private List<Condition> conditions;
    private Map<Integer, Action> actions;
    private String questId;


    public SimpleQuestStarter(List<Condition> conditions, Map<Integer, Action> actions, String questId) {
        this.conditions = conditions;
        this.actions = actions;
        this.questId = questId;
    }

    @Override
    public void start(Event event) {
        if(!isAllConditionValid(event)){
            conditions.forEach(condition -> condition.manageInvalidCondition(event));
            return;
        }

        Player player = EventsUtils.getPlayerFromEvent(event);
        if(player == null)return;
        Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
        client.addActiveQuests(questId);

        // TODO: 24/01/2020 : a tester l'hitorie de la map null, il peut y avoir des erreurs
        new ActionsCall(actions,null,event).run();
    }

    private boolean isAllConditionValid(Event event){
        for (Condition condition : conditions) {
            if(!condition.call(event))return false;
        }
        return true;
    }
}
