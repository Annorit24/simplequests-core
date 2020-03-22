package xyz.annorit24.simplequestscore.quests;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.pipeline.Pipeline;
import xyz.annorit24.simplequestsapi.quest.Container;
import xyz.annorit24.simplequestsapi.quest.QuestStarter;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.Condition;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.pipeline.QuestStartContainer;
import xyz.annorit24.simplequestscore.core.pipeline.pipeline.PipelineType;
import xyz.annorit24.simplequestscore.utils.events.EventsUtils;

import javax.print.attribute.IntegerSyntax;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class SimpleQuestStarter extends QuestStarter {

    private Map<Integer, Condition> conditions;
    private Map<Integer, Action> actions;


    public SimpleQuestStarter(Map<Integer, Condition> conditions, Map<Integer, Action> actions) {
        this.conditions = conditions;
        this.actions = actions;
    }

    @Override
    public void start(Event event) {
        Player player = EventsUtils.getPlayerFromEvent(event);
        System.out.println("M");
        if(player == null)return;
        System.out.println("N");
        UUID eventUUID = SimpleQuestsCore.getInstance().getBukkitEventsData().addData(event);
        System.out.println("O");
        Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
        System.out.println("P");

        Container startContainer = new QuestStartContainer(player.getUniqueId(), eventUUID, actions, conditions);
        System.out.println("Q");
        Pipeline pipeline = client.getPipeline(PipelineType.QUESTS_START);
        System.out.println("R");
        pipeline.send(startContainer);
        System.out.println("S");
    }

}
