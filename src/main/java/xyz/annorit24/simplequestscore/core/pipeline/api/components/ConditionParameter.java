package xyz.annorit24.simplequestscore.core.pipeline.api.components;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.pipeline.api.components.ComponentParameter;

import java.util.UUID;

/**
 * @author Annorit24
 * Created on 22/02/2020
 */
public final class ConditionParameter implements ComponentParameter {

    private UUID bukkitEventId;
    private Player player;

    public ConditionParameter setBukkitEventId(UUID bukkitEventId) {
        this.bukkitEventId = bukkitEventId;
        return this;
    }

    public ConditionParameter setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public Event getBukkitEvent() {
        return SimpleQuestsCore.getInstance().getBukkitEventsData().getData(bukkitEventId);
    }

    public Player getPlayer() {
        return player;
    }
}
