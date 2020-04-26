package xyz.annorit24.simplequestscore.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Annorit24 on 26/11/17.
 */
public abstract class BukkitSubCommand {

    // TODO: 26/07/2019 Create the javadoc

    private String name;
    private String description;
    private String args;
    private String[][] tabArgs;

    public BukkitSubCommand(String name, String description, String args, String[][] tabArgs) {
        this.name = name;
        this.description = description;
        this.args = args;
        this.tabArgs = tabArgs;
    }

    public abstract void executePlayer(Player pixelPlayer, String[] args);

    public abstract void executeConsole(CommandSender sender, String[]args);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String[] getTabArgs(int i) {
        return tabArgs[i];
    }
}