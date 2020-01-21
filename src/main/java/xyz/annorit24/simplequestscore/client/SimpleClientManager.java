package xyz.annorit24.simplequestscore.client;

import org.bukkit.Bukkit;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.client.ClientManager;
import xyz.annorit24.simplequestsapi.database.Database;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.utils.logger.LogUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleClientManager extends ClientManager {

    private Database database;
    private Map<UUID,Client> clients;

    public SimpleClientManager(SimpleQuestsCore simpleQuestsCore) {
        this.database = simpleQuestsCore.getDatabase();
        this.clients = new HashMap<>();
        new SimpleClient();
    }

    @Override
    public Client getClient(UUID uuid) {
        if(!clients.containsKey(uuid)){
            LogUtils.ERROR.log("Could not get client with the uuid "+uuid.toString()+". It must be load !");
            return null;
        }
        return clients.get(uuid);
    }

    @Override
    public void loadClient(UUID uuid) {
        if(database.doesExist(uuid)){
            clients.put(uuid,database.getClient(uuid));
        }else{
            clients.put(uuid,database.createNewClient(Bukkit.getPlayer(uuid)));
        }
    }

    @Override
    public void unloadClient(UUID uuid) {
        if(!clients.containsKey(uuid)){
            LogUtils.ERROR.log("Could not unload client with the uuid "+uuid.toString()+". It must be load !");
            return;
        }
        database.saveClient(clients.get(uuid));
        clients.remove(uuid);
    }
}
