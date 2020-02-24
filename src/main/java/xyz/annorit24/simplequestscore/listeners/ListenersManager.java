package xyz.annorit24.simplequestscore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.RegisteredListener;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.events.BukkitEventDispatcher;
import xyz.annorit24.simplequestscore.listeners.player.PlayerJoinListener;
import xyz.annorit24.simplequestscore.listeners.player.PlayerQuitListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Annorit24
 * Created on 28/12/2019
 */
public class ListenersManager {

    private SimpleQuestsCore javaPlugin;

    public ListenersManager(SimpleQuestsCore javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public void initEventDispatcher() {

        RegisteredListener registeredListener = new RegisteredListener(
                new Listener() {},
                (listener, event) -> {
                    List<Class<? extends Event>> ignoredEvents = new ArrayList<>();
                    ignoredEvents.add(ChunkLoadEvent.class);
                    ignoredEvents.add(EntityAirChangeEvent.class);
                    ignoredEvents.add(ChunkUnloadEvent.class);
                    ignoredEvents.add(PlayerMoveEvent.class);
                    ignoredEvents.add(PlayerLoginEvent.class);

                    if (ignoredEvents.contains(event.getClass())) return;
                    if(event instanceof BlockBreakEvent) System.out.println("aaaaaaa : "+((BlockBreakEvent)event).getBlock().getType().name());

                    new BukkitEventDispatcher(event, javaPlugin).setPlayer().dispatch();
                },
                EventPriority.HIGHEST, javaPlugin, true
        );

        HandlerList.getHandlerLists().forEach(handlerList -> handlerList.register(registeredListener));
    }

    public void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(javaPlugin),javaPlugin);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(javaPlugin),javaPlugin);
        Bukkit.getPluginManager().registerEvents(new BreakBlockListener(), javaPlugin);
    }

}
