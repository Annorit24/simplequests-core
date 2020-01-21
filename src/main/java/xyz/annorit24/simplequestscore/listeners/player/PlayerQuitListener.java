package xyz.annorit24.simplequestscore.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;

/**
 * @author Annorit24
 * Created on 20/01/2020
 */
public class PlayerQuitListener implements Listener {

    private SimpleQuestsCore plugin;

    public PlayerQuitListener(SimpleQuestsCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(plugin,() -> {
            plugin.getClientManager().unloadClient(player.getUniqueId());
        });
    }

}
