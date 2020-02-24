package xyz.annorit24.simplequestscore.version;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class VersionManager {

    private String version;
    private static VersionManager instance;

    public VersionManager() {
        this.version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];;
        instance = this;
    }

    public void setup(){

        if(version.contains("v1_13")){
            return;
        }

        if(version.contains("v1_12")){
            return;
        }
    }

    public static VersionManager get(){
        return instance;
    }

    public AbstractNPCSpawner getNpcSpawner(String usernameSkin, Location location, String npcName) {
        if(version.contains("v1_13")){
            return new xyz.annorit24.simplequestscore.version.v1_13.NPCSpawner(usernameSkin, location, npcName);
        }
        return null;
    }
}
