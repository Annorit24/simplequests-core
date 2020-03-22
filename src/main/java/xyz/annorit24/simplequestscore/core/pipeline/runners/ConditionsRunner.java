package xyz.annorit24.simplequestscore.core.pipeline.runners;

import org.bukkit.Bukkit;
import xyz.annorit24.simplequestsapi.pipeline.Pipeline;
import xyz.annorit24.simplequestsapi.pipeline.Runner;
import xyz.annorit24.simplequestsapi.quest.Container;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.quest.components.Condition;
import xyz.annorit24.simplequestsapi.quest.components.ConditionParameter;
import xyz.annorit24.simplequestsapi.utils.Callback;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;

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
    protected void process(Container container) {
        LogUtils.DEBUG.log("401");
        Map<Integer, ComponentResult> result = new HashMap<>();
        //LogUtils.DEBUG.log("402");
        // TODO: 23/02/2020 : A tester
        AtomicBoolean done = new AtomicBoolean(false);
        //LogUtils.DEBUG.log("403");
        for (Map.Entry<Integer, Condition> entry : container.getConditions().entrySet()) {
            LogUtils.DEBUG.log("404");
            if(pipeline.isInterrupt()){
                break;
            }

            LogUtils.DEBUG.log("405");
            Condition condition = entry.getValue();
            LogUtils.DEBUG.log("406");
            Integer i = entry.getKey();
            LogUtils.DEBUG.log("407");



            LogUtils.DEBUG.log("408");

            Bukkit.getScheduler().runTask(SimpleQuestsCore.getInstance(), () -> {
                ConditionParameter parameter = new ConditionParameter()
                        .setBukkitEventId(container.getBukkitEventUUID())
                        .setPlayer(Bukkit.getPlayer(container.getPlayerUUID()));

                LogUtils.DEBUG.log("409");
                Callback<ComponentResult> callback = condition.call(parameter);
                LogUtils.DEBUG.log("410");
                result.put(i,callback.run());
                LogUtils.DEBUG.log("411");
                done.set(true);
                LogUtils.DEBUG.log("412");
            });

            while (true){
                if (done.get()) {
                    LogUtils.DEBUG.log("413");
                    done.set(false);
                    LogUtils.DEBUG.log("414");
                    break;
                }
                //WAIT
            }
            LogUtils.DEBUG.log("415");

        }
        //LogUtils.DEBUG.log("416");
        container.setConditionsResult(result);
        LogUtils.DEBUG.log("417");
    }
}
