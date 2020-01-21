package xyz.annorit24.simplequestscore.config;

import xyz.annorit24.simplequestsapi.database.Database;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.database.json.SimpleJsonDatabase;

/**
 * @author Annorit24
 * Created on 18/01/2020
 */
public class DatabaseConfig extends AbstractConfig{

    public DatabaseConfig(SimpleQuestsCore plugin) {
        super("config/", "database_config.json", plugin);
    }

    public Database getDatabase(){
        switch (getDatabaseType()){
            case JSON:return new SimpleJsonDatabase(SimpleQuestsCore.getInstance());

            case MYSQL:return null;

            case MYSQL_REDIS:return null;
        }
        return null;
    }

    public DatabaseType getDatabaseType(){
        String databaseType = config.get("database_type").getAsString();
        return DatabaseType.valueOf(databaseType.toUpperCase());
    }

    public enum DatabaseType {
        JSON,
        MYSQL,
        MYSQL_REDIS,
    }

}
