package xyz.annorit24.simplequestscore.quests.conditions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import xyz.annorit24.simplequestsapi.condition.Condition;

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
    public boolean call(Event event) {
        if(!(event instanceof BlockEvent))return false;
        Block b = ((BlockEvent)event).getBlock();
        return b.getType().equals(material);
    }

    @Override
    public void manageInvalidCondition(Event event) {

    }
}
