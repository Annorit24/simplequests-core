package xyz.annorit24.simplequestscore.core.pipeline.api;

import xyz.annorit24.simplequestscore.core.pipeline.QuestEventContainer;
import xyz.annorit24.simplequestscore.utils.Callback;

/**
 * @author Annorit24
 * Created on 12/02/2020
 */
public abstract class Runner implements IQuestEventReader {

    private final String slug;
    protected Pipeline pipeline;

    public Runner(String slug, Pipeline pipeline) {
        this.slug = slug;
        this.pipeline = pipeline;
    }

    @Override
    public Callback read(QuestEventContainer container) {
        process(container);
        return null;
        // TODO: 22/02/2020 do smthing for the callback to call the next runner, use container index
    }

    protected abstract void process(QuestEventContainer container);

    public String getSlug() {
        return slug;
    }

}
