package xyz.annorit24.simplequestscore.quests.conditions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import xyz.annorit24.simplequestsapi.SimpleQuestsAPI;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.quest.components.Condition;
import xyz.annorit24.simplequestsapi.quest.components.ConditionParameter;
import xyz.annorit24.simplequestsapi.utils.Callback;

import java.util.UUID;

/**
 * @author Annorit24
 * Created on 21/01/2020
 */
public class BlockCondition extends Condition {

    private Material material;

    public BlockCondition(Material material) {
        this.material = material;
    }

    @Override
    public Callback<ComponentResult> call(ConditionParameter conditionParameter) {
        Event event = SimpleQuestsAPI.get().getBukkitEventsData().getData(conditionParameter.getBukkitEventId());

        System.out.println(((BlockBreakEvent)SimpleQuestsAPI.get().getBukkitEventsData().getData(conditionParameter.getBukkitEventId())).getBlock().getType().toString());
        ((BlockBreakEvent)event).getPlayer().sendMessage("yooo");

        if(!(event instanceof BlockEvent))return () -> ComponentResult.FAILURE;
        Block b = ((BlockEvent)event).getBlock();
        System.out.println("NEEDED : "+this.material.name());
        System.out.println("HAVE : "+b.getType().name());
        if(b.getType().equals(material)){
            return () -> ComponentResult.SUCCESS;
        }return () -> ComponentResult.FAILURE;
    }
}
