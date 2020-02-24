package xyz.annorit24.simplequestscore.quests.loader;

import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.QuestStep;

import java.io.File;

/**
 * @author Annorit24
 * Created on 23/01/2020
 */
public class QuestStepLoader {

    private File questDirectory;
    private String pipelineName;

    public QuestStepLoader(File questDirectory, String pipelineName) {
        this.questDirectory = questDirectory;
        this.pipelineName = pipelineName;
    }

    public QuestStep loadQuestStep(){
        return null;
    }

}
