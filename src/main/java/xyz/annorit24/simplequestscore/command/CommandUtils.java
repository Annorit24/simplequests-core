package xyz.annorit24.simplequestscore.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class CommandUtils {

    /**
     * Register a bukkit command without using  plugin.yml file
     *
     * @param name name of the command
     * @param command bukkit command object
     */
    public static void registerBukkitCommand(String name, Command command){
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(name, command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
