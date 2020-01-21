package xyz.annorit24.simplequestscore.config;

import xyz.annorit24.simplequestsapi.client.ClientManager;
import xyz.annorit24.simplequestsapi.database.Database;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.client.SimpleClientManager;

import java.util.Date;

/**
 * @author Annorit24
 * Created on 29/12/2019
 */
public class ConfigManager {

    private SimpleQuestsCore javaPlugin;

    private DatabaseConfig databaseConfig;


    public ConfigManager(SimpleQuestsCore javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.databaseConfig = new DatabaseConfig(javaPlugin);
    }

    /**
     * Get the database object which was set in the config<br>
     * This method is only used in the main class to init the database<br>
     * Then use the method {@link }
     *
     * @return instance of a database which is not init
     */
    public Database getDatabase(){
        return databaseConfig.getDatabase();
    }

    /**
     * Get the client manager which can be a basic manager or<br>
     * a manager which use a caching system
     *
     * @return instance of a new client manager
     */
    public ClientManager clientManager(){
        if(databaseConfig.getDatabaseType().equals(DatabaseConfig.DatabaseType.JSON)){
            return new SimpleClientManager(javaPlugin);
        }
        return null;
    }

}
