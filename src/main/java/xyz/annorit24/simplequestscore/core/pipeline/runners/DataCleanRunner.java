package xyz.annorit24.simplequestscore.core.pipeline.runners;

import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.client.ClientManager;
import xyz.annorit24.simplequestsapi.customdata.CustomDataManager;
import xyz.annorit24.simplequestsapi.pipeline.Pipeline;
import xyz.annorit24.simplequestsapi.pipeline.Runner;
import xyz.annorit24.simplequestsapi.quest.Container;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.quests.actions.FinishQuestAction;

import java.util.Map;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 18/04/2020
 */
public final class DataCleanRunner extends Runner {

    public DataCleanRunner(Pipeline pipeline) {
        super("RUNNER - Data clean", pipeline);
    }

    @Override
    protected void process(Container container) {
        CustomDataManager customDataManager = SimpleQuestsCore.getInstance().getCustomDataManager();
        ClientManager clientManager = SimpleQuestsCore.getInstance().getClientManager();

        UUID playerUUID = container.getPlayerUUID();
        Client client = clientManager.getClient(playerUUID);

        boolean override = false;
        if(container.isCriticalActionsResult() || container.isCriticalConditionsResult())override = true;

        if(isFinishQuestAction(container) || override){
            customDataManager.cleanQuestsData(client);
        }

        customDataManager.cleanQuestStepsData(client);
    }

    private boolean isFinishQuestAction(Container container){
        for (Map.Entry<Integer, Action> entry : container.getActions().entrySet()) {
            Action action = entry.getValue();

            if(action instanceof FinishQuestAction){
                return true;
            }

        }
        return false;
    }
}
