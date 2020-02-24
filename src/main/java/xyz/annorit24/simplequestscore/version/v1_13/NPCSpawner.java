package xyz.annorit24.simplequestscore.version.v1_13;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.Player;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.mojang.MojangRequest;
import xyz.annorit24.simplequestscore.utils.PacketUtils;
import xyz.annorit24.simplequestscore.version.AbstractNPCSpawner;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class NPCSpawner extends AbstractNPCSpawner {

    private Integer id;
    private EntityPlayer npc;

    public NPCSpawner( String usernameSkin, Location location, String npcName ) {

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = (WorldServer) PacketUtils.getCraftObject(location.getWorld());
        EntityPlayer npc = new EntityPlayer(server,world, new GameProfile(new MojangRequest().getUuidFromMojang(usernameSkin),npcName), new PlayerInteractManager(world));
        npc.teleportTo(location,false);

        this.id = npc.getId();
        this.npc = npc;
    }

    @Override
    public void spawn(Player player) {

        Bukkit.getScheduler().runTask(SimpleQuestsCore.getInstance(), () -> {
            PlayerConnection connection = (PlayerConnection) PacketUtils.getConnection(player);
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        });

    }

    @Override
    public Integer getId() {
        return id;
    }
}
