package xyz.annorit24.simplequestscore.core.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.pipeline.Pipeline;
import xyz.annorit24.simplequestsapi.pipeline.Trigger;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.pipeline.QuestEventContainer;
import xyz.annorit24.simplequestscore.core.pipeline.pipeline.PipelineType;
import xyz.annorit24.simplequestscore.core.trigger.TriggerManager;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Class use to dispatch bukkit events listened by the {@link xyz.annorit24.simplequestscore.listeners.ListenersManager}<br>
 * Events will be dispatch to triggers registered for a player in the {@link TriggerManager}<br>
 * Then the trigger will be processed
 *
 * @author Annorit24
 * Created on 27/12/2019
 */
public class BukkitEventDispatcher {

    /**
     * Event to dispatch
     */
    private volatile Event event;

    /**
     * The player object target by the event<br>
     * It will bet set using the method {@link BukkitEventDispatcher#setPlayer()}
     */
    private Player player;

    /**
     * Triggers Manager
     */
    private TriggerManager triggerManager;

    /**
     * Constructor
     *
     * @param event event to dispatch
     * @param simpleQuestsCore instance of the Main java plugin
     */
    public BukkitEventDispatcher(Event event, SimpleQuestsCore simpleQuestsCore) {
        this.event = event;
        triggerManager = simpleQuestsCore.getTriggerManager();
    }

    /**
     * Method use to extract player object from the event<br>
     * If it is not a player event, player will be set to null
     *
     * @return instance of BukkitEventDispatcher
     */
    public BukkitEventDispatcher setPlayer(){
        try {
            Method m = event.getClass().getMethod("getPlayer");
            player = (Player) m.invoke(event);
        } catch (Exception ignored) {
            try {
                Method m = event.getClass().getMethod("getEntity");
                Entity entity = (Entity) m.invoke(event);
                if(entity instanceof Player){
                    player = (Player) entity;
                }
            } catch (Exception ignored1) {
                try {
                    Method m = event.getClass().getMethod("getWhoClicked");
                    HumanEntity entity = (HumanEntity) m .invoke(event);
                    if(entity instanceof Player){
                        player = (Player)entity;
                    }else {
                        player = null;
                    }
                } catch (Exception ignored2){
                    player = null;
                }
            }
        }
        return this;
    }

    /**
     * Method use to dispatch events to all triggers registered<br>
     * They will be process owing to {@link Pipeline}<br>
     * <br>
     * Quest steps events will be process by the {@link xyz.annorit24.simplequestscore.core.pipeline.pipeline.QuestsMainPipeline}
     * Static Quest steps events will be process by the todo: link l'autre pipeline
     */
    public void dispatch(){
        if(player == null)return;
        if(event instanceof BlockBreakEvent) System.out.println("d : "+((BlockBreakEvent)event).getBlock().getType().name());
        if(event instanceof CraftItemEvent) System.out.println(" x : "+((CraftItemEvent)event).getRecipe().getResult().getType().name());

        List<Trigger> triggers = triggerManager.getTriggers(player.getUniqueId(), event.getClass())
                .stream()
                .filter(trigger -> !trigger.isProcessing())
                .collect(Collectors.toList());

        for (Trigger trigger : triggers) {
            LogUtils.DEBUG.log("101");
            trigger.setProcessing(true);
            LogUtils.DEBUG.log("102");

            UUID eventUUID = SimpleQuestsCore.getInstance().getBukkitEventsData().addData(event);
            LogUtils.DEBUG.log("103 | eventUUID : "+eventUUID);
            System.out.println(Thread.currentThread().getName());
            /*System.out.println(
                    ((BlockBreakEvent)SimpleQuestsCore.getInstance().getBukkitEventsData().getData(eventUUID)).getBlock().getType().name()
            );*/

            Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
            LogUtils.DEBUG.log("104");

            QuestEventContainer container = new QuestEventContainer(player.getUniqueId(), eventUUID, trigger);
            LogUtils.DEBUG.log("105");
            Pipeline pipeline = client.getPipeline(PipelineType.QUESTS_MAIN);
            LogUtils.DEBUG.log("106");

            pipeline.send(container);
            LogUtils.DEBUG.log("107");
            /*System.out.println("c");

            TriggerProcessing triggerProcessing = new TriggerProcessing(getEvent(), trigger);
            System.out.println("d");

            triggerProcessing.processTrigger();
            System.out.println("e");


            new Thread(() -> {
                System.out.println("f");
                while (!triggerProcessing.isFinish()) {
                }
                System.out.println("g");
                if (!triggerProcessing.getReprocess()) {
                    triggerManager.unregisterTrigger(trigger);

                    Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
                    QuestInfo questInfo = ClientUtils.getQuestInfoFromQuestId(client, trigger.getQuestInfo().getQuestId());
                    if (questInfo != null) Utils.buildTriggers(questInfo, client);
                }
                trigger.setProcessing(false);

                System.out.println(trigger.getQuestInfo().getQuestId());
                System.out.println(trigger.getQuestStepId());

            }).start();

            //});
            /*}).start();*/
        }

    }

}
