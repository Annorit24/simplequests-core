package xyz.annorit24.simplequestscore.database.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestscore.client.SimpleClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 20/01/2020
 */
public class ClientTypeAdapter extends TypeAdapter<SimpleClient> {

    @Override
    public void write(JsonWriter writer, SimpleClient simpleClient) throws IOException {
        writer.beginObject();
        writer.name("uniqueId").value(simpleClient.getUniqueId().toString());
        writer.name("name").value(simpleClient.getName());
        writer.name("displayName").value(simpleClient.getDisplayName());

        writer.name("activesQuests").beginArray();
        for (QuestInfo activesQuest : simpleClient.getActivesQuests()) {
            writer.beginObject();
            writer.name("questId").value(activesQuest.getQuestId());
            writer.name("pipeline").value(activesQuest.getPipeline());
            writer.name("step").value(activesQuest.getStep());
            writer.name("subStep").value(activesQuest.getSubStep());
            writer.endObject();
        }
        writer.endArray();

        writer.name("questsDone").beginArray();
        for (String finishedQuest : simpleClient.getFinishedQuests()) {
            writer.value(finishedQuest);
        }
        writer.endArray();

        writer.endObject();

    }

    @Override
    public SimpleClient read(JsonReader reader) throws IOException {
        reader.beginObject();
        UUID uuid = null;
        String name = "";
        String displayName = "";
        List<QuestInfo> activeQuests;
        activeQuests = new ArrayList<>();

        List<String> questsDone;
        questsDone = new ArrayList<>();

        QuestInfo questInfo;
        String questId="";
        String pipeline="default";
        int step=0;
        int subStep=0;


        while (reader.hasNext()){
            switch (reader.nextName()){
                case "uniqueId":
                    uuid = UUID.fromString(reader.nextString());
                    break;

                case "name":
                    name = reader.nextString();
                    break;

                case "displayName":
                    displayName = reader.nextString();
                    break;

                case "activesQuests":
                    reader.beginArray();
                    while (reader.hasNext()){
                        reader.beginObject();
                        while (reader.hasNext()){
                            switch (reader.nextName()){
                                case "questId":questId = reader.nextString();
                                    break;
                                case "pipeline":pipeline = reader.nextString();
                                    break;
                                case "step":step = reader.nextInt();
                                    break;
                                case "subStep":subStep = reader.nextInt();
                                    break;
                            }
                        }
                        reader.endObject();
                        questInfo = new QuestInfo(questId,pipeline,step,subStep);
                        activeQuests.add(questInfo);
                    }
                    reader.endArray();
                    break;
                case "questsDone":
                    reader.beginArray();
                    while (reader.hasNext()){
                        questsDone.add(reader.nextString());
                    }
                    reader.endArray();
                    break;
            }
        }

        reader.endObject();
        return new SimpleClient(uuid,name,displayName,activeQuests,questsDone);
    }
}
