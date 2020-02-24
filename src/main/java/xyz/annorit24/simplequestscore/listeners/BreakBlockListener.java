package xyz.annorit24.simplequestscore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author Annorit24
 * Created on 22/01/2020
 */
public class BreakBlockListener implements Listener {

    @EventHandler
    public void onBreakBlockEvent(BlockBreakEvent event){
        System.out.println(event.getBlock().getType().name());
    }

}
