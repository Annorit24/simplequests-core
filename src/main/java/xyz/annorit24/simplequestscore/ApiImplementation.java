package xyz.annorit24.simplequestscore;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.annorit24.simplequestsapi.SimpleQuestsAPI;
import xyz.annorit24.simplequestsapi.actions.Action;
import xyz.annorit24.simplequestsapi.client.ClientManager;
import xyz.annorit24.simplequestsapi.condition.Condition;
import xyz.annorit24.simplequestsapi.npc.QuestNPCManager;
import xyz.annorit24.simplequestsapi.packet.PacketReaderManager;
import xyz.annorit24.simplequestsapi.quest.ComponentsManager;
import xyz.annorit24.simplequestsapi.quest.QuestsManager;

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

    @Override
    public QuestsManager questsManager() {
        return ((SimpleQuestsCore)javaPlugin).getQuestsManager();
    }

    @Override
    public ComponentsManager<Action> getActionsManager() {
        return ((SimpleQuestsCore)javaPlugin).getActionsManager();
    }

    @Override
    public ComponentsManager<Condition> getConditionsManager() {
        return ((SimpleQuestsCore)javaPlugin).getConditionsManager();
    }

    @Override
    public PacketReaderManager getPacketReaderManager() {
        return ((SimpleQuestsCore)javaPlugin).getPacketReaderManager();
    }

    @Override
    public QuestNPCManager questNPCManager() {
        return ((SimpleQuestsCore)javaPlugin).getQuestNPCManager();
    }

}
