package xyz.annorit24.simplequestscore.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;

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

        Bukkit.getScheduler().runTaskAsynchronously(plugin,() -> {
            plugin.getClientManager().loadClient(player.getUniqueId());

            Client client = plugin.getClientManager().getClient(player.getUniqueId());
            client.addQuestDone("Yooooo");
            for (QuestInfo activesQuest : client.getActivesQuests()) {
                System.out.println(activesQuest.getQuestId());
            }
        });
    }

}
