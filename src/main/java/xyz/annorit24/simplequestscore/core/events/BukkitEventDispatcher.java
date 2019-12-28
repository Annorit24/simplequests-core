package xyz.annorit24.simplequestscore.core.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.trigger.Trigger;
import xyz.annorit24.simplequestscore.core.trigger.TriggerManager;

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
    private Event event;

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

        List<Trigger> triggers = triggerManager.getTriggers(player.getUniqueId(), event.getClass())
                .stream()
                .filter(trigger -> !trigger.isProcessing())
                .collect(Collectors.toList());

        for (Trigger trigger : triggers) {
            trigger.setProcessing(true);
            // TODO: 27/12/2019 processing triggers
            System.out.println(trigger.getQuestInfo().getQuestId());
        }
    }

}
