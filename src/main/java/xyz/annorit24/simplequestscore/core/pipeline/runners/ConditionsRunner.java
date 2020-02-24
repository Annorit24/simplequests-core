package xyz.annorit24.simplequestscore.core.pipeline.runners;

import org.bukkit.Bukkit;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.pipeline.QuestEventContainer;
import xyz.annorit24.simplequestscore.core.pipeline.api.Pipeline;
import xyz.annorit24.simplequestscore.core.pipeline.api.Runner;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ComponentResult;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.Condition;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ConditionParameter;
import xyz.annorit24.simplequestscore.utils.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Annorit24
 * Created on 22/02/2020
 */
public final class ConditionsRunner extends Runner {

    public ConditionsRunner(Pipeline pipeline) {
        super("RUNNER - Conditions", pipeline);
    }

    @Override
    protected void process(QuestEventContainer container) {
        Map<Integer, ComponentResult> result = new HashMap<>();

        // TODO: 23/02/2020 : A tester
        AtomicBoolean done = new AtomicBoolean(false);

        for (Map.Entry<Integer, Condition> entry : container.getConditions().entrySet()) {
            if(pipeline.isInterrupt()){
                break;
            }

            Condition condition = entry.getValue();
            Integer i = entry.getKey();

            ConditionParameter parameter = new ConditionParameter()
                    .setBukkitEventId(container.getBukkitEventUUID())
                    .setPlayer(Bukkit.getPlayer(container.getPlayerUUID()));


            Bukkit.getScheduler().runTask(SimpleQuestsCore.getInstance(), () -> {
               Callback<ComponentResult> callback = condition.call(parameter);
               result.put(i,callback.run());
               done.set(true);
            });

            while (true){
                if (done.get()) {
                    done.set(false);
                    break;
                }
                //WAIT
            }

        }

        container.setConditionsResult(result);
    }
}
