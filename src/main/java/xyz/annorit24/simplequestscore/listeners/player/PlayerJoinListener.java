package xyz.annorit24.simplequestscore.listeners.player;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.packet.SimplePacketReaderManager;
import xyz.annorit24.simplequestscore.core.trigger.runnables.TriggerLoader;
import xyz.annorit24.simplequestscore.mojang.MojangRequest;
import xyz.annorit24.simplequestscore.utils.PacketUtils;

/**
 * @author Annorit24
 * Created on 20/01/2020
 */
public class PlayerJoinListener implements Listener {

    private SimpleQuestsCore plugin;

    public PlayerJoinListener(SimpleQuestsCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        plugin.getQuestNPCManager().asyncSpawnNpc(player);
        ((SimplePacketReaderManager)plugin.getPacketReaderManager()).inject(player);

        Bukkit.getScheduler().runTaskAsynchronously(plugin,() -> {
            plugin.getClientManager().loadClient(player.getUniqueId());
            Client client = plugin.getClientManager().getClient(player.getUniqueId());
            
            if(client == null){
                player.kickPlayer("");// TODO: 21/01/2020 : Set the kick error message
                return;
            }

            new TriggerLoader(client,plugin).run();
        });

    }

}
