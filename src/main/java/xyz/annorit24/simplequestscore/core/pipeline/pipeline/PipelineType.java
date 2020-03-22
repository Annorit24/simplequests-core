package xyz.annorit24.simplequestscore.core.pipeline.pipeline;

/**
 * @author Annorit24
 * Created on 05/03/2020
 */
public enum PipelineType implements xyz.annorit24.simplequestsapi.pipeline.PipelineType {

    QUESTS_MAIN("questsMain"),
    QUESTS_START("questsStart");

    private String name;

    PipelineType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
