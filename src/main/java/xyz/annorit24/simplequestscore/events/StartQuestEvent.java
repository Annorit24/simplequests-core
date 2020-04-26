package xyz.annorit24.simplequestscore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class StartQuestEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private String questId;
    private Integer npcId;

    public StartQuestEvent(Player who, String questId, Integer npcId) {
        super(who);
        this.questId = questId;
        this.npcId = npcId;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public String getQuestId() {
        return questId;
    }

    public Integer getNpcId() {
        return npcId;
    }
}
