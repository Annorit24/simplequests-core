package xyz.annorit24.simplequestscore.client;

import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.quest.QuestInfo;
import xyz.annorit24.simplequestscore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 29/12/2019
 */
public class SimpleClient extends Client {

    /**
     * Unique id of the player
     */
    private final UUID UNIQUE_ID;

    /**
     * The real name of the player
     */
    private String name;

    /**
     * The name display on the server
     */
    private String displayName;

    /**
     * The quests started by the player
     */
    private List<QuestInfo> activeQuests;

    /**
     * Quests which are finished
     * List of quest's id
     */
    private List<String> questsDone;

    public SimpleClient() {
        super();
        System.out.println("Test");
        UNIQUE_ID=null;
    }

    public SimpleClient(UUID UNIQUE_ID, String name, String displayName, List<QuestInfo> activeQuests, List<String> questsDone) {
        super();
        this.UNIQUE_ID = UNIQUE_ID;
        this.name = name;
        this.displayName = displayName;
        this.activeQuests = activeQuests;
        this.questsDone = questsDone;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public UUID getUniqueId() {
        return UNIQUE_ID;
    }

    @Override
    public List<QuestInfo> getActivesQuests() {
        return activeQuests;
    }

    @Override
    public List<String> getFinishedQuests() {
        return questsDone;
    }

    @Override
    public void addActiveQuests(String... questsId) {

        for (String s : questsId) {
            QuestInfo questInfo = new QuestInfo(s,"default",0,0);
            activeQuests.add(questInfo);
            Utils.buildTriggers(questInfo,this);
        }

    }

    @Override
    public void removeActiveQuests(String... strings) {

    }

    @Override
    public void addQuestDone(String s) {
        questsDone.add(s);
    }

    @Override
    public void removeQuestDone(String s) {

    }

    @Override
    public Client createClient(Player player) {
        return new SimpleClient(player.getUniqueId(), player.getName(), player.getDisplayName(), new ArrayList<>(), new ArrayList<>());
    }

}
