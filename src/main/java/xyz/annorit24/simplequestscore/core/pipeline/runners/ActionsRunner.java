package xyz.annorit24.simplequestscore.core.pipeline.runners;

import org.bukkit.Bukkit;
import xyz.annorit24.simplequestscore.core.pipeline.QuestEventContainer;
import xyz.annorit24.simplequestscore.core.pipeline.api.Pipeline;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.Action;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ActionParameter;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ComponentResult;
import xyz.annorit24.simplequestscore.core.pipeline.api.Runner;
import xyz.annorit24.simplequestscore.utils.Callback;

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
    protected void process(QuestEventContainer container) {
        Map<Integer, ComponentResult> result = new HashMap<>();

        for (Map.Entry<Integer, Action> entry : container.getActions().entrySet()) {
            if(pipeline.isInterrupt()){
                break;
            }

            Action action = entry.getValue();
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
