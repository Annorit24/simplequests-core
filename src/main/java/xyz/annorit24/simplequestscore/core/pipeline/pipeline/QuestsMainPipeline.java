package xyz.annorit24.simplequestscore.core.pipeline.pipeline;

import xyz.annorit24.simplequestscore.core.pipeline.api.Pipeline;
import xyz.annorit24.simplequestscore.core.pipeline.api.PipelineManager;
import xyz.annorit24.simplequestscore.core.pipeline.runners.ActionsRunner;
import xyz.annorit24.simplequestscore.core.pipeline.runners.ConditionsRunner;

/**
 * @author Annorit24
 * Created on 23/02/2020
 */
public class QuestsMainPipeline extends Pipeline {

    /**
     * Constructor
     *
     * @param pipelineManager pipeline manager
     */
    public QuestsMainPipeline(PipelineManager pipelineManager) {
        super(pipelineManager);
    }

    @Override
    protected void loadDefaultRunners() {
        addLast(new ConditionsRunner(this));
        addLast(new ActionsRunner(this));
    }
}
