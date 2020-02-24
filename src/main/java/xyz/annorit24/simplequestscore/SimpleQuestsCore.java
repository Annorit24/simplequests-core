package xyz.annorit24.simplequestscore;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.annorit24.simplequestsapi.JavaPluginAPI;
import xyz.annorit24.simplequestsapi.actions.Action;
import xyz.annorit24.simplequestsapi.client.ClientManager;
import xyz.annorit24.simplequestsapi.condition.Condition;
import xyz.annorit24.simplequestsapi.database.Database;
import xyz.annorit24.simplequestsapi.npc.QuestNPCManager;
import xyz.annorit24.simplequestsapi.packet.PacketReaderManager;
import xyz.annorit24.simplequestsapi.quest.*;
import xyz.annorit24.simplequestscore.config.FilesManager;
import xyz.annorit24.simplequestscore.config.ConfigManager;
import xyz.annorit24.simplequestscore.core.packet.SimplePacketReaderManager;
import xyz.annorit24.simplequestscore.core.pipeline.BukkitEventsData;
import xyz.annorit24.simplequestscore.core.trigger.TriggerManager;
import xyz.annorit24.simplequestscore.listeners.ListenersManager;
import xyz.annorit24.simplequestscore.npc.QuestStartNPC;
import xyz.annorit24.simplequestscore.npc.SimpleQuestNPCManager;
import xyz.annorit24.simplequestscore.quests.*;
import xyz.annorit24.simplequestscore.quests.actions.IncrementStepAction;
import xyz.annorit24.simplequestscore.quests.actions.cineamticaction.CinematicAction;
import xyz.annorit24.simplequestscore.quests.actions.messageaction.MessageAction;
import xyz.annorit24.simplequestscore.quests.conditions.BlockCondition;
import xyz.annorit24.simplequestscore.utils.events.EventsUtils;
import xyz.annorit24.simplequestscore.utils.logger.LogUtils;
import xyz.annorit24.simplequestscore.version.VersionManager;

import javax.vecmath.Vector3f;
import java.util.*;

/**
 * @author Annorit24
 * Created on 28/12/2019
 */
public class SimpleQuestsCore extends JavaPluginAPI {

    private ListenersManager listenersManager;
    private FilesManager filesManager;
    private TriggerManager triggerManager;
    private ConfigManager configManager;
    private VersionManager versionManager;

    // TODO: 24/01/2020 Faire en sorte que action ai comme variable de class quesstep permettant de récuperer les différentes actions. Ajouter aussi un paramètre au action du style customcall: false/true permetant de ne pas appeler les actions automatiquement. Creer un event du genre KeyReachEvent pour le faire passer en argument lors du call de l'action durant la cinématique

    private SimpleComponentsManager<Action> actionsManager;
    private SimpleComponentsManager<Condition> conditionsManager;
    private QuestsManager questsManager;
    private Database database;
    private ClientManager clientManager;
    private QuestNPCManager questNPCManager;
    private PacketReaderManager packetReaderManager;

    //WIP
    private BukkitEventsData bukkitEventsData;

    private static SimpleQuestsCore instance;

    @Override
    public void onEnable() {
        instance = this;

        //Basic setup for the plugin
        LogUtils.appName = "SimpleQuests";
        EventsUtils.loadEventClasses();

        //Register the implementation of the simplequests api
        this.registerApiImplementation(new ApiImplementation(this));

        //Setup version manager
        versionManager = new VersionManager();
        versionManager.setup();

        packetReaderManager = new SimplePacketReaderManager();

        questNPCManager = new SimpleQuestNPCManager(this);

        //Setup config directory tree and config files
        //If files or directories are missing they will be created using a default template
        filesManager = new FilesManager(this);
        filesManager.createFiles();

        //Use config manager to set object which depending of config
        configManager = new ConfigManager(this);
            //Setup database can be Sql or Json
            database = configManager.getDatabase();
            database.init();
            //Setup client manager which can change if u use a caching system or not
            clientManager = configManager.clientManager();

        listenersManager = new ListenersManager(this);
        listenersManager.initEventDispatcher();
        listenersManager.registerListeners();

        triggerManager = new TriggerManager();

        questsManager = new SimpleQuestsManager();

        actionsManager = new SimpleComponentsManager<Action>("Action");
        conditionsManager = new SimpleComponentsManager<Condition>("Condition");

        questNPCManager.registerNpc(new QuestStartNPC(
                new Location(Bukkit.getWorld("world"),21,78,43),
                "start",
                "Annorit24",
                "Garde"
        ));

        Vector3f v1 = new Vector3f(16, 79, 46);
        Vector3f v2 = new Vector3f(25, 87, 67);
        Vector3f v3 = new Vector3f(50, 85, 69);
        Vector3f v4 = new Vector3f(74, 78, 53);

        List<Vector3f> points = new ArrayList<>();
        points.addAll(Arrays.asList(v1,v2,v3,v4));
        CinematicAction cinematicAction = new CinematicAction(Collections.singletonList(1),false,points);


        List<QuestStep> steps = new ArrayList<>();
        QuestInfo questInfo = new QuestInfo("start","default",0,0);
        Map<Integer, Condition> conditions = new HashMap<>();
        conditions.put(1,new BlockCondition(Material.STONE));
        Map<Integer, Action> actions = new HashMap<>();
        actions.put(1,new MessageAction(Collections.singletonList(1),false ,new TextComponent("Good job you broke a stone block")));
        actions.put(2,new MessageAction(Collections.singletonList(1),false , new TextComponent("Now you need to break a birch leaves block")));
        actions.put(4,new IncrementStepAction(Collections.singletonList(1),false , "start"));
        QuestStep questStep = new SimpleQuestStep(UUID.randomUUID(), BlockBreakEvent.class,"desc",questInfo,conditions, actions);
        steps.add(questStep);

        List<QuestStep> steps1 = new ArrayList<>();
        QuestInfo questInfo1 = new QuestInfo("start","default",1,0);
        Map<Integer, Condition> conditions1 = new HashMap<>();
        conditions1.put(1,new BlockCondition(Material.EMERALD_BLOCK));
        Map<Integer, Action> actions1 = new HashMap<>();
        actions1.put(1,new MessageAction(Collections.singletonList(1),false , new TextComponent("Good job you broke a birch leaves block")));
        actions1.put(2,new IncrementStepAction(Collections.singletonList(1),false , "start"));
        QuestStep questStep1 = new SimpleQuestStep(UUID.randomUUID(), BlockBreakEvent.class,"desc",questInfo1,conditions1, actions1);
        steps1.add(questStep1);

        Map<QuestInfo,List<QuestStep>> questSteps = new HashMap<>();
        questSteps.put(questInfo, steps);
        questSteps.put(questInfo1, steps1);

        Map<Integer, Action> starterActions = new HashMap<>();
        starterActions.put(1,cinematicAction);
        QuestStarter starter = new SimpleQuestStarter(new ArrayList<>(), starterActions, "start");


        Quest test = new SimpleQuest(questSteps,"start","The start","starting quests", starter,1,1);
        questsManager.registerQuest(test);
    }

    @Override
    public void onDisable() {

    }

    //Getter for managers
    public static SimpleQuestsCore getInstance() {
        return instance;
    }
    public ListenersManager getListenersManager() {
        return listenersManager;
    }
    public FilesManager getFilesManager() {
        return filesManager;
    }
    public TriggerManager getTriggerManager() {
        return triggerManager;
    }
    public ClientManager getClientManager() {
        return clientManager;
    }
    public Database getDatabase() {
        return database;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public QuestsManager getQuestsManager() {
        return questsManager;
    }
    public SimpleComponentsManager<Action> getActionsManager() {
        return actionsManager;
    }
    public SimpleComponentsManager<Condition> getConditionsManager() {
        return conditionsManager;
    }
    public QuestNPCManager getQuestNPCManager() {
        return questNPCManager;
    }
    public PacketReaderManager getPacketReaderManager() {
        return packetReaderManager;
    }
    public BukkitEventsData getBukkitEventsData() {
        return bukkitEventsData;
    }
}
