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
        if(container.isCriticalConditionsResult())return;

        Map<Integer, ComponentResult> result = new HashMap<>();
        for (Map.Entry<Integer, Action> entry : container.getActions().entrySet()) {
            if(pipeline.isInterrupt()){
                break;
            }

            Action action = entry.getValue();

            if(action instanceof IncrementStepAction){
                container.setReprocess(((IncrementStepAction)action).isReprocess());
            }
            if(action.isCustomCall())continue;

            Integer i = entry.getKey();

            ActionParameter actionParameter = new ActionParameter()
                    .setConditionsResults(container.getConditionsResult())
                    .setPlayer(Bukkit.getPlayer(container.getPlayerUUID()))
                    .setContainer(container);


            Callback<ComponentResult> callback = action.call(actionParameter);
            result.put(i, callback.run());
        }

        container.setActionsResult(result);
    }

}
