package xyz.annorit24.simplequestscore.core.pipeline.api;

import org.apache.commons.collections4.map.ListOrderedMap;
import xyz.annorit24.simplequestscore.core.pipeline.QuestEventContainer;
import xyz.annorit24.simplequestscore.utils.logger.LogUtils;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author Annorit24
 * Created on 04/02/2020
 */
public abstract class Pipeline {

    /**
     * List of the runner which will read events send to the pipeline
     * Key is the slug of the runner
     */
    private volatile ListOrderedMap<String, Runner> runners;

    /**
     * the id of the pipeline
     */
    private UUID pipelineId;

    /**
     * The pipeline manager use to create new threads for the pipeline
     */
    private PipelineManager pipelineManager;

    /**
     * State of the pipeline<br>
     * if it is interrupt manage interruption of the pipeline
     */
    private volatile boolean interrupt;

    /**
     * Constructor
     * @param pipelineManager pipeline manager
     */
    public Pipeline(PipelineManager pipelineManager) {
        this.pipelineManager = pipelineManager;
        this.runners = new ListOrderedMap<>();
        pipelineId = UUID.randomUUID();
        loadDefaultRunners();
    }

    /**
     * Add a new runner before a specified runner already added
     *
     * @param slug slug of the target runner
     * @param runner the new runner to register
     *
     * @return if the runner was successfully added
     */
    public synchronized boolean addBefore(String slug, Runner runner){
        if(!runners.containsKey(slug)){
            LogUtils.ERROR.log("Could not add before the runner : "+runner.getSlug()+", the target runner :"+slug+" doesn't exist");
            return false;
        }

        int index = runners.indexOf(slug);
        int newIndex = index == 0 ? index : index-1;

        runners.put(newIndex,runner.getSlug(),runner);

        return true;
    }

    /**
     * Add a new runner after a specified runner already added
     *
     * @param slug slug of the target runner
     * @param runner the new runner to register
     *
     * @return if the runner was successfully added
     */
    public synchronized boolean addAfter(String slug, Runner runner){
        if(!runners.containsKey(slug)){
            LogUtils.ERROR.log("Could not add after the runner : "+runner.getSlug()+", the target runner :"+slug+" doesn't exist");
            return false;
        }

        int index = runners.indexOf(slug);
        int newIndex = index+1;

        runners.put(newIndex,runner.getSlug(),runner);

        return true;
    }

    /**
     * Add a runner which will be call at the beginning
     *
     * @param runner new runner to add
     */
    public synchronized void addFirst(Runner runner){
        runners.put(0, runner.getSlug(), runner);
    }

    /**
     * Add a runner which will be call at the end
     *
     * @param runner new runner to add
     */
    public synchronized void addLast(Runner runner){
        runners.put(runners.size()-1, runner.getSlug(), runner);
    }

    /**
     * Method call in the constructor of the pipeline<br>
     * It is use to load default runners which will be in the pipeline
     */
    protected abstract void loadDefaultRunners();

    /**
     * Get the unique id of the pipeline
     *
     * @return a uuid
     */
    public UUID getPipelineId() {
        return pipelineId;
    }

    /**
     * Entry point of the pipeline<br>
     * Method use to start processing the quest event
     *
     * @param container container of the quest event
     */
    public synchronized void send(QuestEventContainer container){

        pipelineManager.submitPipelineJob((Callable<Void>) () -> {
            runners.getValue(0).read(container);
            return null;
        });

    }

    public synchronized void interruptPipeline(){
        // TODO: 23/02/2020 handle interruption of the pipeline
        // TODO: 23/02/2020 handle player disconnect event
    }

    public boolean isInterrupt() {
        return interrupt;
    }
}
