package xyz.annorit24.simplequestscore.listeners.npc;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.npc.NPCStartManager;
import xyz.annorit24.simplequestsapi.quest.Quest;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.events.StartQuestEvent;

/**
 * @author Annorit24
 * Created on 27/03/2020
 */
public class NPCRightClickListener implements Listener {

    private NPCStartManager npcStartManager;

    public NPCRightClickListener(SimpleQuestsCore plugin) {
        this.npcStartManager = plugin.getNpcStartManager();
    }

    @EventHandler
    public void onNPCRightClickListener(NPCRightClickEvent event){
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        Integer npcId = npc.getId();

        String questId = npcStartManager.getQuestIdByNPCId(npcId);
        if(questId.equals(""))return;

        LogUtils.DEBUG.log("A");
        Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
        LogUtils.DEBUG.log("B");
        Quest quest = SimpleQuestsCore.getInstance().getQuestsManager().getQuest(questId);
        LogUtils.DEBUG.log("C");
        if(quest == null || client == null)return;
        LogUtils.DEBUG.log("D");
        System.out.println(questId);
        quest.getQuestStarter().start(new StartQuestEvent(player, questId, npcId));
    }

}
