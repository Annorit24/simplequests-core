package xyz.annorit24.simplequestscore.core.pipeline;

import xyz.annorit24.simplequestsapi.quest.Container;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.Condition;

import java.util.Map;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 05/03/2020
 */
public class QuestStartContainer extends Container {

    public QuestStartContainer(UUID playerUUID, UUID bukkitEventUUID, Map<Integer, Action> actions, Map<Integer, Condition> conditions) {
        super(playerUUID, bukkitEventUUID, actions, conditions);
    }


}
