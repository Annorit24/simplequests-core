package xyz.annorit24.simplequestscore.quests.loader;

import xyz.annorit24.simplequestsapi.quest.Quest;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestsapi.quest.QuestStep;
import xyz.annorit24.simplequestsapi.quest.QuestsManager;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 21/01/2020
 */
public class QuestLoader {

    private QuestsManager questsManager;
    private File[] questsDirectory;


    public QuestLoader(SimpleQuestsCore plugin) {
        this.questsManager = plugin.getQuestsManager();
        this.questsDirectory = plugin.getFilesManager().getQUESTS_DIRECTORY().listFiles();
    }

    public void loadQuests(){
        Map<String, Quest> quests = new HashMap<>();

        for (File questDirectory : questsDirectory) {
            Map<QuestInfo, List<QuestStep>> steps = new HashMap<>();
            //StartLoader -> ConditionLoader & ActionLoader
            //EndLoader
            //QuestConfigLoader
            //for pipeline directories -> QuestStepLoader -> ConditionLoader & ActionLoader

            /*
            private UUID id;
            private Class<? extends Event> event;
            private String description;
            private QuestInfo questInfo;
            private Map<Integer,Condition> conditions;
            private Map<Integer, Action> actions;
             */

            //QUESTSTEPS DESERIALIZATION
            File pipelinesDirectory = new File(questDirectory, "pipelines");
            for (File pipelineDir : pipelinesDirectory.listFiles()) {
                String pipelineName = pipelineDir.getName();
                System.out.println(pipelineName);

                for (File questStepDirectory : pipelineDir.listFiles()) {
                    QuestStepLoader loader = new QuestStepLoader(questStepDirectory,pipelineName);
                    QuestStep step = loader.loadQuestStep();
                    QuestInfo info = step.getQuestStepInfo();

                    if(steps.containsKey(info)){
                        List<QuestStep> l = steps.get(info);
                        l.add(step);
                    }else{
                        List<QuestStep> l = new ArrayList<>();
                        l.add(step);
                        steps.put(info, l);
                    }

                }
            }


        }
    }

}
