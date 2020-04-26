package xyz.annorit24.simplequestscore.core.pipeline.runners;

import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.pipeline.Pipeline;
import xyz.annorit24.simplequestsapi.pipeline.Runner;
import xyz.annorit24.simplequestsapi.pipeline.Trigger;
import xyz.annorit24.simplequestsapi.quest.Container;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.Revertible;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.pipeline.QuestEventContainer;
import xyz.annorit24.simplequestscore.core.trigger.TriggerManager;
import xyz.annorit24.simplequestscore.utils.Utils;
import xyz.annorit24.simplequestscore.utils.client.ClientUtils;

import java.util.Map;

/**
 * @author Annorit24
 * Created on 29/02/2020
 */
public final class TriggerUpdateRunner extends Runner {

    public TriggerUpdateRunner(Pipeline pipeline) {
        super("RUNNER - SimpleTrigger update", pipeline);
    }

    @Override
    protected void process(Container container) {
        boolean override = false;

        if(container.isCriticalConditionsResult() ||container.isCriticalActionsResult()){
            override = true;
        }

        if(!(container instanceof QuestEventContainer)){
            LogUtils.ERROR.log("Could not process the container for the player with the following uuid"+container.getPlayerUUID()+". Error @ TriggerUpdateRunner receive a container which is not an instance of QuestEventContainer");
            return;
        }

        if(pipeline.isInterrupt()){
            container.setReprocess(false);
        }

        Trigger trigger = ((QuestEventContainer)container).getProcessingTrigger();
        TriggerManager triggerManager = SimpleQuestsCore.getInstance().getTriggerManager();

        if (!container.isReprocess() || override) {
            triggerManager.unregisterTrigger(trigger);

            if(!pipeline.isInterrupt()){
                Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(container.getPlayerUUID());
                QuestInfo questInfo = ClientUtils.getQuestInfoFromQuestId(client, trigger.getQuestInfo().getQuestId());

                if (questInfo != null) Utils.buildTriggers(questInfo, client.getUniqueId());
            }else{
                for (Map.Entry<Integer, Action> entry : container.getActions().entrySet()) {
                    Action action = entry.getValue();

                    if(action instanceof Revertible){
                        ((Revertible)action).revert(container.getPlayerUUID());
                    }
                }
            }


        }

        trigger.setProcessing(false);
        SimpleQuestsCore.getInstance().getBukkitEventsData().removeData(container.getBukkitEventUUID());
        // TODO: 29/03/2020 : Clean quest step data and clean quest data in a cleaner runner
    }
}
