package xyz.annorit24.simplequestscore;

import xyz.annorit24.simplequestsapi.JavaPluginAPI;
import xyz.annorit24.simplequestscore.config.ConfigFilesManager;
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
    private ConfigFilesManager configFilesManager;
    private TriggerManager triggerManager;

    private static SimpleQuestsCore instance;

    @Override
    public void onEnable() {
        LogUtils.appName = "SimpleQuests";
        instance = this;
        this.registerApiImplementation(new ApiImplementation(this));

        EventsUtils.loadEventClasses();

        listenersManager = new ListenersManager(this);
        configFilesManager = new ConfigFilesManager(this);
        triggerManager = new TriggerManager();

        listenersManager.initEventDispatcher();
        listenersManager.registerListeners();
        configFilesManager.createFiles();
    }

    @Override
    public void onDisable() {

    }

    public static SimpleQuestsCore getInstance() {
        return instance;
    }

    public ListenersManager getListenersManager() {
        return listenersManager;
    }

    public ConfigFilesManager getConfigFilesManager() {
        return configFilesManager;
    }

    public TriggerManager getTriggerManager() {
        return triggerManager;
    }
}
