package xyz.annorit24.simplequestscore.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.packet.SimplePacketReaderManager;
import xyz.annorit24.simplequestscore.core.pipeline.pipeline.PipelineType;

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
        ((SimplePacketReaderManager)plugin.getPacketReaderManager()).unInject(player);
        plugin.getTriggerManager().unregisterTrigger(player);
        System.out.println(plugin.getTriggerManager().getTriggersByEvents());
        System.out.println(plugin.getTriggerManager().getTriggersByPlayerUUID());

        Client client = plugin.getClientManager().getClient(player.getUniqueId());
        client.getPipeline(PipelineType.QUESTS_MAIN).interruptPipeline();

        Bukkit.getScheduler().runTaskAsynchronously(plugin,() -> {
            plugin.getClientManager().unloadClient(player.getUniqueId());
        });


    }

}
