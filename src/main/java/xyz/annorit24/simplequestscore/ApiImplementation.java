package xyz.annorit24.simplequestscore;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.annorit24.simplequestsapi.SimpleQuestsAPI;
import xyz.annorit24.simplequestsapi.client.ClientManager;

/**
 * @author Annorit24
 * Created on 28/12/2019
 */
public class ApiImplementation extends SimpleQuestsAPI {

    public ApiImplementation(SimpleQuestsCore javaPlugin) {
        super(javaPlugin);
    }

    @Override
    public ClientManager getClientManager() {
        return ((SimpleQuestsCore)javaPlugin).getClientManager();
    }

}
