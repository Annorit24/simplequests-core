package xyz.annorit24.simplequestscore.version.v1_13;

import net.minecraft.server.v1_13_R2.EntityCreature;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.EnumMoveType;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;

/**
 * @author Annorit24
 * Created on 23/03/2020
 */
public class NpcMove {

    private EntityPlayer npc;
    private int x;
    private int y;
    private  int z;

    public NpcMove(EntityPlayer npc, int x, int y, int z) {
        this.npc = npc;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void move(){
        CraftEntity ce = npc.getBukkitEntity(); // e is an entity
        EntityCreature ec = (EntityCreature) ce.getHandle();
    }


}
