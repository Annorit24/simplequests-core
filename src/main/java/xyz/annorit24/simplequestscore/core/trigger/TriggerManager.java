package xyz.annorit24.simplequestscore.core.trigger;

import org.bukkit.event.Event;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;

import java.util.*;

/**
 * Class used to manage triggers of different quests<br>
 * Triggers will be get using the method {@link TriggerManager#getTriggers(UUID, Class)} when events will be dispatch<br>
 * Then triggers will be processed
 *
 * @author Annorit24
 * Created on 27/12/2019
 */
public class TriggerManager {

    /**
     * Instance of java plugin
     */
    private SimpleQuestsCore javaPlugin;

    /**
     * Triggers which are sort by the event which call them
     */
    private Map<Class<? extends Event>, List<Trigger>> triggersByEvents;

    /**
     * Triggers which are sort by the uuid of the player target in the trigger
     */
    private Map<UUID, List<Trigger>> triggersByPlayerUUID;

    /**
     * Constructor
     */
    public TriggerManager() {
        triggersByEvents = new HashMap<>();
        triggersByPlayerUUID = new HashMap<>();
    }

    /**
     * Use to register trigger for a quest step
     * @param trigger trigger used to trigger a specific quest step
     */
    public void registerTrigger(Trigger trigger){
        Class<? extends Event> event = trigger.getEvent();
        UUID playerUUID = trigger.getPlayerUniqueId();

        List<Trigger> triggersEvents = triggersByEvents.get(event);
        List<Trigger> triggersPlayerUUID = triggersByPlayerUUID.get(playerUUID);

        if(triggersEvents == null){
            triggersEvents = new ArrayList<>();
            triggersByEvents.put(event, triggersEvents);
        }

        if(triggersPlayerUUID == null){
            triggersPlayerUUID = new ArrayList<>();
            triggersByPlayerUUID.put(playerUUID, triggersPlayerUUID);
        }

        triggersEvents.add(trigger);
        triggersPlayerUUID.add(trigger);
    }

    /**
     * Use to unregister trigger which have been called and where the processing was successful
     *
     * @param trigger useless trigger
     */
    public void unregisterTrigger(Trigger trigger){
        List<Trigger> triggersEvents = triggersByEvents.get(trigger.getEvent());
        List<Trigger> triggersPlayerUUID = triggersByPlayerUUID.get(trigger.getPlayerUniqueId());

        if(triggersEvents == null)return;
        if(triggersPlayerUUID == null)return;

       triggersEvents.remove(trigger);
       triggersPlayerUUID.remove(trigger);
    }

    /**
     * Get trigger by the event and the player unique id
     *
     * @param uuid the uuid of the target player
     * @param event the event used to call the trigger
     *
     * @return list of valid triggers
     */
    public List<Trigger> getTriggers(UUID uuid, Class<? extends Event> event){
        List<Trigger> a = triggersByEvents.get(event);
        List<Trigger> b = triggersByPlayerUUID.get(uuid);

        if(a == null || b == null)return new ArrayList<>();

        List<Trigger> triggers = new ArrayList<>();
        List<Trigger> workList = a.size() > b.size() ? a : b;

        for (Trigger trigger : workList) {
            if(a.contains(trigger) && b.contains(trigger)){
                triggers.add(trigger);
            }
        }

        return triggers;
    }

    public void persistTriggers(){
        // TODO: 21/01/2020 : Make persistence system for trigger
    }
}
