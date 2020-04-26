package xyz.annorit24.simplequestscore;

import xyz.annorit24.simplequestsapi.SimpleQuestsAPI;
import xyz.annorit24.simplequestsapi.client.ClientManager;
import xyz.annorit24.simplequestsapi.npc.NPCStartManager;
import xyz.annorit24.simplequestsapi.npc.QuestNPCManager;
import xyz.annorit24.simplequestsapi.packet.PacketReaderManager;
import xyz.annorit24.simplequestsapi.pipeline.BukkitEventsData;
import xyz.annorit24.simplequestsapi.quest.QuestsManager;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.ComponentsManager;
import xyz.annorit24.simplequestsapi.quest.components.Condition;

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

    @Override
    public BukkitEventsData getBukkitEventsData() {
        return ((SimpleQuestsCore)javaPlugin).getBukkitEventsData();
    }

    @Override
    public NPCStartManager npcStartManager() {
        return ((SimpleQuestsCore)javaPlugin).getNpcStartManager();
    }

}
