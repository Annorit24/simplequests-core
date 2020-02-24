package xyz.annorit24.simplequestscore.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.npc.QuestNPC;
import xyz.annorit24.simplequestsapi.quest.Quest;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.events.StartQuestEvent;
import xyz.annorit24.simplequestscore.version.AbstractNPCSpawner;
import xyz.annorit24.simplequestscore.version.VersionManager;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class QuestStartNPC extends QuestNPC {

    private AbstractNPCSpawner npcSpawner;

    public QuestStartNPC(Location location, String questId, String npcName, String skinName) {
        super(location, questId, npcName, skinName);
        this.npcSpawner = VersionManager.get().getNpcSpawner(skinName,location,npcName);
        this.id = npcSpawner.getId();
    }

    @Override
    public void spawnNPC(Player player) {
        npcSpawner.spawn(player);
    }

    @Override
    public void onInteract(Player player) {
        Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(player.getUniqueId());
        Quest quest = SimpleQuestsCore.getInstance().getQuestsManager().getQuest(getQuestId());

        if(quest == null || client == null)return;

        quest.getQuestStarter().start(new StartQuestEvent(player, getQuestId()));
    }
}
