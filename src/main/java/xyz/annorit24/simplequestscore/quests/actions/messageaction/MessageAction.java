package xyz.annorit24.simplequestscore.quests.actions.messageaction;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.ActionParameter;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.utils.Callback;

import java.util.List;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 21/01/2020
 */
public class MessageAction extends Action {

    private TextComponent textComponent;

    public MessageAction(List<Integer> requireValidConditions, boolean critical, boolean customCall, TextComponent textComponent) {
        super(requireValidConditions, critical, customCall);
        this.textComponent = textComponent;
    }

    /*public MessageAction(List<Integer> validConditions, boolean customCall, TextComponent textComponent) {
        super(validConditions, customCall);
        this.textComponent = textComponent;
    }*/

    /*@Override
    public void call(Player player, Map<Integer, Boolean> results) {
        if(isConditionsValid(results)) {
            player.spigot().sendMessage(textComponent);
        }
        finish = true;
    }*/

    @Override
    public Callback<ComponentResult> call(ActionParameter actionParameter) {
        Map<Integer, Boolean> result = actionParameter.getConditionsResults();
        Player player = actionParameter.getPlayer();

        if(isConditionsValid(result)){
            player.spigot().sendMessage(textComponent);
            return () -> ComponentResult.SUCCESS;
        }
        return () -> ComponentResult.FAILURE;

    }

    @Override
    public Action cloneAction() {
        return new MessageAction(getRequireValidConditions(),isCritical(),isCustomCall(),textComponent);
    }
}
