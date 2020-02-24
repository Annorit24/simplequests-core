package xyz.annorit24.simplequestscore.listeners.nmspacket;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import xyz.annorit24.simplequestsapi.npc.QuestNPC;
import xyz.annorit24.simplequestsapi.npc.QuestNPCManager;
import xyz.annorit24.simplequestsapi.packet.IPacketReader;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.utils.PacketUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class NPCPacketReader implements IPacketReader {

    private QuestNPCManager questNPCManager;
    private Map<UUID, Boolean> packetBuffer;

    public NPCPacketReader(QuestNPCManager questNPCManager) {
        this.questNPCManager = questNPCManager;
        this.packetBuffer = new HashMap<>();
    }

    @Override
    public void readPacket(Player player, Object packet) {
        if(packet.getClass().getSimpleName().equals("PacketPlayInUseEntity")) {
            int id = (int) PacketUtils.getField(packet,"a");
            System.out.println(id);

            if((PacketUtils.getField(packet,"action").toString().equalsIgnoreCase("INTERACT_AT")) || (PacketUtils.getField(packet,"action").toString().equalsIgnoreCase("INTERACT_AT"))){

                if(packetBuffer.get(player.getUniqueId()) == null || packetBuffer.get(player.getUniqueId())) {
                    packetBuffer.put(player.getUniqueId(), false);
                    Bukkit.getScheduler().runTaskLater(SimpleQuestsCore.getInstance(),() -> packetBuffer.put(player.getUniqueId(), true),20);
                    QuestNPC questNPC = questNPCManager.getQuestNPC(id);

                    if(questNPC != null){
                    System.out.println(questNPC.getNpcName());
                    questNPC.onInteract(player);
                    }
                }
            }
        }
    }

}
