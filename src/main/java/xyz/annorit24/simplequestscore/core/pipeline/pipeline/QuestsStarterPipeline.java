package xyz.annorit24.simplequestscore.core.pipeline.pipeline;

import xyz.annorit24.simplequestsapi.pipeline.Pipeline;
import xyz.annorit24.simplequestsapi.pipeline.PipelineManager;
import xyz.annorit24.simplequestscore.core.pipeline.runners.ActionsRunner;
import xyz.annorit24.simplequestscore.core.pipeline.runners.ConditionsRunner;

/**
 * @author Annorit24
 * Created on 02/03/2020
 */
public class QuestsStarterPipeline extends Pipeline {

    public QuestsStarterPipeline(PipelineManager pipelineManager) {
        super(pipelineManager);
    }

    @Override
    protected void loadDefaultRunners() {
        addLast(new ConditionsRunner(this));
        addLast(new ActionsRunner(this));
    }
}
