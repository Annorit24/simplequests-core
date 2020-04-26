package xyz.annorit24.simplequestscore.command;

import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntity;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_13_R2.PlayerConnection;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.SimpleQuestsAPI;
import xyz.annorit24.simplequestsapi.npc.QuestNPCManager;
import xyz.annorit24.simplequestscore.npc.QuestStartNPC;
import xyz.annorit24.simplequestscore.npc.SimpleQuestNPCManager;
import xyz.annorit24.simplequestscore.version.AbstractNPCSpawner;
import xyz.annorit24.simplequestscore.version.v1_13.NPCSpawner;
import xyz.annorit24.simplequestscore.version.v1_13.NpcMove;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * @author Annorit24
 * Created on 23/03/2020
 */
public class TestCommand extends BukkitCommand {

    public TestCommand() {
        super("test", new String[]{}, 0,  new String[][]{}, "test command", "");
    }

    @Override
    public void executePlayer(Player player, String[] args) {
        Integer entityID = SimpleQuestNPCManager.ID;
        AbstractNPCSpawner npcSpawner = ((QuestStartNPC)SimpleQuestsAPI.get().questNPCManager().getQuestNPC(entityID)).getNpcSpawner();
        //NpcMove move = new NpcMove((EntityPlayer) npcSpawner.getNPC(),21,68,54);
        //move.move();


        System.out.println("Move entity : "+entityID);
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutEntity.PacketPlayOutRelEntityMove packet = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(entityID, 25, 78, 50, true);
        PacketPlayOutEntityTeleport packet1 = new PacketPlayOutEntityTeleport();
        try {
            Field entityId = packet1.getClass().getDeclaredField("a");
            entityId.setAccessible(true);
            entityId.set(packet1,entityID);

            Field x = packet1.getClass().getDeclaredField("b");
            x.setAccessible(true);
            x.set(packet1,21);

            Field y = packet1.getClass().getDeclaredField("c");
            y.setAccessible(true);
            y.set(packet1,68);

            Field z = packet1.getClass().getDeclaredField("d");
            z.setAccessible(true);
            z.set(packet1,50);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            int i = 0;

            while (i < 50){
                connection.sendPacket(packet);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }).start();
        //connection.sendPacket(packet1);
    }

    @Override
    public void executeConsole(CommandSender commandSender, String[] args) {

    }
}
