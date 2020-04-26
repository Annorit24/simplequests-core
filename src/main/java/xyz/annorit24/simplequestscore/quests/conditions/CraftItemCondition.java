package xyz.annorit24.simplequestscore.quests.conditions;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.CraftItemEvent;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.quest.components.Condition;
import xyz.annorit24.simplequestsapi.quest.components.ConditionParameter;
import xyz.annorit24.simplequestsapi.utils.Callback;

/**
 * @author Annorit24
 * Created on 21/03/2020
 */
public class CraftItemCondition extends Condition {

    private String itemName;

    public CraftItemCondition(Material material) {
        this.itemName = material.name().toLowerCase();
    }

    public CraftItemCondition(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public Callback<ComponentResult> call(ConditionParameter conditionParameter) {
        Event event = conditionParameter.getBukkitEvent();
        if(!(event instanceof  CraftItemEvent))return () -> ComponentResult.CRITICAL_FAILURE;
        CraftItemEvent craftItemEvent = (CraftItemEvent) event;

        if(craftItemEvent.getRecipe().getResult().getType().name().toLowerCase().equals(itemName))return () -> ComponentResult.SUCCESS;
        return () -> ComponentResult.FAILURE;
    }
}
