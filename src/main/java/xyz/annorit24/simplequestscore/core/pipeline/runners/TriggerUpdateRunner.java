package xyz.annorit24.simplequestscore.core.pipeline.runners;

import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.pipeline.Pipeline;
import xyz.annorit24.simplequestsapi.pipeline.Runner;
import xyz.annorit24.simplequestsapi.pipeline.Trigger;
import xyz.annorit24.simplequestsapi.quest.Container;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.pipeline.QuestEventContainer;
import xyz.annorit24.simplequestscore.core.trigger.TriggerManager;
import xyz.annorit24.simplequestscore.utils.Utils;
import xyz.annorit24.simplequestscore.utils.client.ClientUtils;

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
        LogUtils.DEBUG.log("501");
        if(!(container instanceof QuestEventContainer)){
            LogUtils.ERROR.log("Could not process the container for the player with the following uuid"+container.getPlayerUUID()+". Error @ TriggerUpdateRunner receive a container which is not an instance of QuestEventContainer");
            return;
        }

        if(pipeline.isInterrupt()){
            return;
        }
        LogUtils.DEBUG.log("502");

        Trigger trigger = ((QuestEventContainer)container).getProcessingTrigger();
        LogUtils.DEBUG.log("503");
        TriggerManager triggerManager = SimpleQuestsCore.getInstance().getTriggerManager();
        LogUtils.DEBUG.log("503");

        if (!container.isReprocess()) {
            LogUtils.DEBUG.log("503");
            triggerManager.unregisterTrigger(trigger);
            LogUtils.DEBUG.log("504");
            Client client = SimpleQuestsCore.getInstance().getClientManager().getClient(container.getPlayerUUID());
            LogUtils.DEBUG.log("505");
            QuestInfo questInfo = ClientUtils.getQuestInfoFromQuestId(client, trigger.getQuestInfo().getQuestId());
            LogUtils.DEBUG.log("506");
            if (questInfo != null) Utils.buildTriggers(questInfo, client);
            LogUtils.DEBUG.log("507");
        }
        LogUtils.DEBUG.log("508");
        trigger.setProcessing(false);
        LogUtils.DEBUG.log("509");
        SimpleQuestsCore.getInstance().getBukkitEventsData().removeData(container.getBukkitEventUUID());
        LogUtils.DEBUG.log("509");
    }
}
