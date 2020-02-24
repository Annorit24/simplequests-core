package xyz.annorit24.simplequestscore.quests.actions.messageaction;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.actions.Action;
import xyz.annorit24.simplequestscore.utils.events.EventsUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 21/01/2020
 */
public class MessageAction extends Action {

    private TextComponent textComponent;

    public MessageAction(List<Integer> validConditions, boolean customCall, TextComponent textComponent) {
        super(validConditions, customCall);
        this.textComponent = textComponent;
    }

    @Override
    public void call(Player player, Map<Integer, Boolean> results) {
        if(isConditionsValid(results)) {
            player.spigot().sendMessage(textComponent);
        }
        finish = true;
    }
}
