package xyz.annorit24.simplequestscore.core.pipeline;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ComponentResult;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.Condition;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ConditionParameter;
import xyz.annorit24.simplequestscore.utils.Callback;

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
    public Callback<ComponentResult> call(ConditionParameter parameter) {
        Event event = parameter.getBukkitEvent();

        if(!(event instanceof BlockEvent))return () -> ComponentResult.FAILURE;
        Block b = ((BlockEvent)event).getBlock();
        if(b.getType().equals(material)){
            return () -> ComponentResult.SUCCESS;
        }else{
            return () -> ComponentResult.FAILURE;
        }
    }
}
