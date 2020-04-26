package xyz.annorit24.simplequestscore.version;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public abstract class AbstractNPCSpawner {

    public abstract void spawn(Player player);

    public abstract Integer getId();

    public abstract Object getNPC();
}
