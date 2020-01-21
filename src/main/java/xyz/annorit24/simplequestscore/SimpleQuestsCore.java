package xyz.annorit24.simplequestscore;

import xyz.annorit24.simplequestsapi.JavaPluginAPI;
import xyz.annorit24.simplequestsapi.client.ClientManager;
import xyz.annorit24.simplequestsapi.database.Database;
import xyz.annorit24.simplequestscore.config.FilesManager;
import xyz.annorit24.simplequestscore.config.ConfigManager;
import xyz.annorit24.simplequestscore.core.trigger.TriggerManager;
import xyz.annorit24.simplequestscore.listeners.ListenersManager;
import xyz.annorit24.simplequestscore.utils.events.EventsUtils;
import xyz.annorit24.simplequestscore.utils.logger.LogUtils;

/**
 * @author Annorit24
 * Created on 28/12/2019
 */
public class SimpleQuestsCore extends JavaPluginAPI {

    private ListenersManager listenersManager;
    private FilesManager filesManager;
    private TriggerManager triggerManager;
    private ConfigManager configManager;

    private Database database;
    private ClientManager clientManager;

    private static SimpleQuestsCore instance;

    @Override
    public void onEnable() {
        instance = this;

        //Basic setup for the plugin
        LogUtils.appName = "SimpleQuests";
        EventsUtils.loadEventClasses();

        //Register the implementation of the simplequests api
        this.registerApiImplementation(new ApiImplementation(this));

        //Setup config directory tree and config files
        //If files or directories are missing they will be created using a default template
        filesManager = new FilesManager(this);
        filesManager.createFiles();

        //Use config manager to set object which depending of config
        configManager = new ConfigManager(this);
            //Setup database can be Sql or Json
            database = configManager.getDatabase();
            database.init();
            //Setup client manager which can change if u use a caching system or not
            clientManager = configManager.clientManager();

        listenersManager = new ListenersManager(this);
        listenersManager.initEventDispatcher();
        listenersManager.registerListeners();

        triggerManager = new TriggerManager();

    }

    @Override
    public void onDisable() {

    }

    //Getter for managers
    public static SimpleQuestsCore getInstance() {
        return instance;
    }
    public ListenersManager getListenersManager() {
        return listenersManager;
    }
    public FilesManager getFilesManager() {
        return filesManager;
    }
    public TriggerManager getTriggerManager() {
        return triggerManager;
    }
    public ClientManager getClientManager() {
        return clientManager;
    }
    public Database getDatabase() {
        return database;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
}
