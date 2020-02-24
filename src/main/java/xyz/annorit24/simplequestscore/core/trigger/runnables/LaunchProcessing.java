package xyz.annorit24.simplequestscore.core.trigger.runnables;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author Annorit24
 * Created on 22/01/2020
 */
public class LaunchProcessing implements Runnable{

    private final Event event;

    public LaunchProcessing(Event event) {
        this.event = event;
    }

    @Override
    public void run() {
        if(event instanceof BlockBreakEvent) System.out.println("c : "+((BlockBreakEvent)event).getBlock().getType().name());
    }
}
