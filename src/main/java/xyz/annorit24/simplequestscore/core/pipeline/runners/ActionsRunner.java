package xyz.annorit24.simplequestscore.core.pipeline.runners;

import org.bukkit.Bukkit;
import xyz.annorit24.simplequestsapi.pipeline.Pipeline;
import xyz.annorit24.simplequestsapi.pipeline.Runner;
import xyz.annorit24.simplequestsapi.quest.Container;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.ActionParameter;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.utils.Callback;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;
import xyz.annorit24.simplequestscore.quests.actions.IncrementStepAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 13/02/2020
 */
public final class ActionsRunner extends Runner {

    public ActionsRunner(Pipeline pipeline) {
        super("RUNNER - Actions", pipeline);
    }

    @Override
    protected void process(Container container) {
        // TODO: 02/03/2020 Create a ErrorsManageRunner
        if(container.isCriticalConditions())return;

        LogUtils.DEBUG.log("301");
        Map<Integer, ComponentResult> result = new HashMap<>();
        LogUtils.DEBUG.log("302");
        for (Map.Entry<Integer, Action> entry : container.getActions().entrySet()) {
            LogUtils.DEBUG.log("303");
            if(pipeline.isInterrupt()){
                break;
            }
            LogUtils.DEBUG.log("304");
            Action action = entry.getValue();
            LogUtils.DEBUG.log("305");
            if(action instanceof IncrementStepAction){
                LogUtils.DEBUG.log("306");
                container.setReprocess(((IncrementStepAction)action).isReprocess());
                LogUtils.DEBUG.log("307");
            }
            LogUtils.DEBUG.log("308");
            if(action.isCustomCall())continue;
            LogUtils.DEBUG.log("309");

            Integer i = entry.getKey();
            LogUtils.DEBUG.log("310");

            ActionParameter actionParameter = new ActionParameter()
                    .setConditionsResults(container.getConditionsResult())
                    .setPlayer(Bukkit.getPlayer(container.getPlayerUUID()))
                    .setContainer(container);

            LogUtils.DEBUG.log("311");

            Callback<ComponentResult> callback = action.call(actionParameter);
            LogUtils.DEBUG.log("312");
            result.put(i, callback.run());
            LogUtils.DEBUG.log("313");
        }
        LogUtils.DEBUG.log("314");
        container.setActionsResult(result);
        LogUtils.DEBUG.log("315");
    }

}
