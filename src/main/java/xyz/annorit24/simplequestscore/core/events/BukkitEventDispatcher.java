package xyz.annorit24.simplequestscore.core.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.trigger.Trigger;
import xyz.annorit24.simplequestscore.core.trigger.TriggerManager;
import xyz.annorit24.simplequestscore.core.trigger.TriggerProcessing;
import xyz.annorit24.simplequestscore.core.trigger.runnables.LaunchProcessing;
import xyz.annorit24.simplequestscore.utils.Utils;
import xyz.annorit24.simplequestscore.utils.client.ClientUtils;

import java.lang.reflect.Method;
import java.util.List;
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
                }else{
                    player = null;
                }
            } catch (Exception ignored1) {

            }
        }
        return this;
    }

    /**
     * Method use to dispatch events to all triggers registered<br>
     * They will be process owing to {@link xyz.annorit24.simplequestscore.core.trigger.TriggerProcessing}
     */
    public void dispatch(){
        if(player == null)return;
        if(event instanceof BlockBreakEvent) System.out.println("d : "+((BlockBreakEvent)event).getBlock().getType().name());

        List<Trigger> triggers = triggerManager.getTriggers(player.getUniqueId(), event.getClass())
                .stream()
                .filter(trigger -> !trigger.isProcessing())
                .collect(Collectors.toList());

        for (Trigger trigger : triggers) {

            /*Bukkit.getScheduler().runTaskAsynchronously(SimpleQuestsCore.getInstance(),new LaunchProcessing(event));

            Thread t = new Thread(() -> {
                Bukkit.getScheduler().runTask(SimpleQuestsCore.getInstance(),() -> {
                    if(event instanceof BlockBreakEvent) System.out.println("d : "+((BlockBreakEvent)event).getBlock().getType().name());
                });
                if(event instanceof BlockBreakEvent) System.out.println("e : "+((BlockBreakEvent)event).getBlock().getType().name());
                if(event instanceof BlockBreakEvent) System.out.println("e(1) : "+((BlockBreakEvent)getEvent()).getBlock().getType().name());

            });
            t.setPriority(Thread.MAX_PRIORITY);
            t.start();

            Bukkit.getScheduler().runTaskAsynchronously(SimpleQuestsCore.getInstance(), () -> {
                if(event instanceof BlockBreakEvent) System.out.println("f : "+((BlockBreakEvent)event).getBlock().getType().name());
                if(event instanceof BlockBreakEvent) System.out.println("f(1) : "+((BlockBreakEvent)getEvent()).getBlock().getType().name());

            });*/
/*
            new Thread(() -> {
            //Bukkit.getScheduler().runTaskAsynchronously(SimpleQuestsCore.getInstance(), () -> {*/

            trigger.setProcessing(true);
            System.out.println("c");

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

    private synchronized Event getEvent(){
        return event;
    }

}
