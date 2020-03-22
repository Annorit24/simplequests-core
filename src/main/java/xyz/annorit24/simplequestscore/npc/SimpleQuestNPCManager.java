package xyz.annorit24.simplequestscore.npc;

import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.npc.QuestNPC;
import xyz.annorit24.simplequestsapi.npc.QuestNPCManager;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.listeners.nmspacket.NPCPacketReader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class SimpleQuestNPCManager extends QuestNPCManager {

    private Map<Integer, QuestNPC> npcs;

    public SimpleQuestNPCManager(SimpleQuestsCore plugin) {
        this.npcs = new HashMap<>();
        plugin.getPacketReaderManager().registerPacketReader(new NPCPacketReader(this));
    }

    @Override
    public void registerNpc(QuestNPC questNPC){
        if(npcs.containsKey(questNPC.getId())){
            LogUtils.ERROR.log("Quest npc with the id "+questNPC.getId()+" is already register");
            return;
        }
        System.out.println("ID : "+questNPC.getId());
        npcs.put(questNPC.getId(), questNPC);
    }

    @Override
    public void asyncSpawnNpc(Player player){
        new Thread(() -> npcs.forEach((integer, questNPC) -> questNPC.spawnNPC(player))).start();
    }

    @Override
    public QuestNPC getQuestNPC(Integer id) {
        if(!npcs.containsKey(id)) return null;
        return npcs.get(id);
    }

}
