package xyz.annorit24.simplequestscore.npc;

import xyz.annorit24.simplequestsapi.npc.NPCStartManager;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 27/03/2020
 */
public class SimpleNPCStartManager extends NPCStartManager {

    /**
     * Map with id of npcs as key and the id of the quest to start as value
     */
    private Map<Integer, String> startNpcs;

    /**
     * Constructor
     */
    public SimpleNPCStartManager() {
        this.startNpcs = new HashMap<>();
    }

    /**
     * Register a start npc
     *
     * @param npcId the npc id
     * @param questId the starting quest id
     */
    @Override
    public void registerNPCStart(Integer npcId, String questId){
        if(startNpcs.containsKey(npcId)){
            LogUtils.ERROR.log("@SimpleNPCStartManager:registerNPCStart Could not register start npc for the quest "+questId+", a npc is already register with the id : "+npcId);
            return;
        }

        startNpcs.put(npcId, questId);
    }

    /**
     * Get the quest id to start by the id of the start npc
     *
     * @param npcId the start npc id
     * @return the corresponding quest id or an empty string if there is no corresponding questId
     */
    @Override
    public String getQuestIdByNPCId(Integer npcId){
        if(!startNpcs.containsKey(npcId)){
            LogUtils.ERROR.log("@SimpleNPCStartManager:getQuestIdByNPCId Could not get quest id for the npc with the id "+npcId);
            return "";
        }

        return startNpcs.get(npcId);
    }

}
