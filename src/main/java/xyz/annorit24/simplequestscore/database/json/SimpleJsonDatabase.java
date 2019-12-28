package xyz.annorit24.simplequestscore.database.json;

import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.database.Database;

import java.util.UUID;

/**
 * @author Annorit24
 * Created on 27/12/2019
 */
public class SimpleJsonDatabase extends Database {

    @Override
    public void init() {

    }

    @Override
    public void close() {

    }

    @Override
    public Client getClient(UUID uuid) {
        return null;
    }

    @Override
    public Client createNewClient(Player player) {
        return null;
    }

    @Override
    public void saveClient(Client client) {

    }

    @Override
    public boolean doesExist(UUID uuid) {
        return false;
    }

}
