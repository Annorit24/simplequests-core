package xyz.annorit24.simplequestscore.quests.conditions;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.CraftItemEvent;
import xyz.annorit24.simplequestsapi.SimpleQuestsAPI;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.quest.components.Condition;
import xyz.annorit24.simplequestsapi.quest.components.ConditionParameter;
import xyz.annorit24.simplequestsapi.utils.Callback;

/**
 * @author Annorit24
 * Created on 21/03/2020
 */
public class CraftItemCondition extends Condition {

    @Override
    public Callback<ComponentResult> call(ConditionParameter conditionParameter) {
        CraftItemEvent event = ((CraftItemEvent)SimpleQuestsAPI.get().getBukkitEventsData().getData(conditionParameter.getBukkitEventId()));
        System.out.println(event.getRecipe().getResult().getType().name());
        return () -> ComponentResult.FAILURE;
    }
}
