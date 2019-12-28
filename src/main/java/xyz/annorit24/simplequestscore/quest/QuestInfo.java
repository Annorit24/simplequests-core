package xyz.annorit24.simplequestscore.quest;

/**
 * Object which contains all information<br>
 * about the current state of a player in a specific quest
 *
 * @author Annorit24
 * Created on 28/12/2019
 */
public class QuestInfo {

    /**
     * Id of the quest
     */
    private String questId;

    /**
     * Name of the pipeline
     */
    private String pipeline;

    /**
     * The step of the quest
     */
    private Integer step;

    /**
     * Constructor
     *
     * @param questId id of the quest
     * @param pipeline pipeline of the quest
     * @param step the current step
     */
    public QuestInfo(String questId, String pipeline, Integer step) {
        this.questId = questId;
        this.pipeline = pipeline;
        this.step = step;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
