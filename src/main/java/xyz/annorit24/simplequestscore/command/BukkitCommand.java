package xyz.annorit24.simplequestscore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Annorit24 on 26/10/17.
 */
public abstract class BukkitCommand extends Command {

    /**
     * The minimum number of args in the command
     */
    private int minimumArgs;

    /**
     * The args after the command
     * Ex : {@literal <arg1> <arg2>}
     */
    private String args;

    /**
     * The description of the command
     */
    private String description;

    /**
     * Arg for the tab completer
     */
    private String[][] tabArgs;

    /**
     * Optional subCommands
     */
    private List<BukkitSubCommand> subCommands;

    /**
     * The sender of the command
     */
    private CommandSender sender;

    /**
     * Create the bukkit command , use this constructor for sub commands
     *
     * @param name name of the command
     * @param aliases aliases of the command
     * @param minimumArgs the minimum of args for the command
     */
    public BukkitCommand(String name, String[] aliases, int minimumArgs) {
        super(name, "NULL", "NetworkCommand", new ArrayList<>());
        this.setAliases(Arrays.asList(aliases));
        this.subCommands = new ArrayList<>();
        this.minimumArgs = minimumArgs;
        this.tabArgs = new String[][]{{"SUBCOMMANDS"}};
        this.args = "NULL";
        this.description = "NULL";
        CommandUtils.registerBukkitCommand(name,this);
    }

    /**
     * Create Bukkit Command
     * @param name name of the command
     * @param aliases aliases of the command
     * @param minimumArgs the minimum of args for the command
     * @param tabArgs args for the tab completer
     * @param description the description of the command
     * @param args args after the command
     */
    public BukkitCommand(String name, String[] aliases, int minimumArgs, String[][] tabArgs, String description, String args) {
        super(name, description, "Network Command", new ArrayList<>());
        this.setAliases(Arrays.asList(aliases));
        this.subCommands = new ArrayList<>();
        this.minimumArgs = minimumArgs;
        this.args = args;
        this.tabArgs = tabArgs;
        this.description = description;
        CommandUtils.registerBukkitCommand(name,this);
    }

    /**
     * Execute the command for Player
     * @param player Player who execute the command
     * @param args args of the command
     */
    public abstract void executePlayer(Player player, String[] args);

    /**
     * Execute the command for Console
     * @param commandSender the sender of the command
     * @param args args of the command
     */
    public abstract void executeConsole(CommandSender commandSender, String[] args);

    /**
     * Register a subCommand for this command
     * @param subCommand the sub command
     */
    public void registerSubCommand(BukkitSubCommand subCommand){
        subCommands.add(subCommand);
    }

    /**
     * Get all sub command names
     * @return the lis of sub commands
     */
    public List<String> getSubCommandsName(){
        List<String > l = new ArrayList<>();
        subCommands.forEach(subCommand -> l.add(subCommand.getName()));
        return l;
    }

    /**
     * The method invoke the first when command is send
     * @param commandSender the sender of the command
     * @param cmd the command name
     * @param args args of the command
     * @return boolean
     */
    @Override
    public boolean execute(CommandSender commandSender, String cmd, String[] args) {
        this.sender = commandSender;
        if(args.length >= minimumArgs) {

            if (!getSubCommands().isEmpty() && !getSubCommandsName().contains(args[0])) {
                showHelp();
                return true;
            }

            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;

                boolean canExecute = true;

                if (canExecute) {
                    executePlayer(player, args);
                }else{
                    // TODO: 26/07/2019 Send do not have permissions message using message manager
                }

            } else {
                executeConsole(commandSender, args);
            }

        }else{
            showHelp();
        }
        return true;
    }

    /**
     * Method invoke when player want to use tab for a command
     *
     * @param sender the target sender
     * @param alias alias of the command
     * @param args args of the command
     * @return list of possible values for the tab completer
     * @throws IllegalArgumentException exception
     */
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

        if ( args.length > tabArgs.length || args.length == 0 ) {
            return new ArrayList<>();
        }

        List<String> matches = new ArrayList<>();

        String search = args[args.length - 1];
        String[] possibleArgs = tabArgs[args.length - 1];

        if(args.length == 1 && possibleArgs[0].equalsIgnoreCase("SUBCOMMANDS")){
            for(BukkitSubCommand subCommand : subCommands){
                if(subCommand.getName().toLowerCase().startsWith( search )){
                    matches.add(subCommand.getName());
                }
            }
        }

        if(args.length >= 2 && !subCommands.isEmpty()){
            String[] possibleSubArgs = (String[]) subCommands.stream().map(bukkitSubCommand -> bukkitSubCommand.getTabArgs(args.length -1)).toArray();
            matches.addAll(getMatches(possibleSubArgs,search));
        }

        if(args.length >= 2 && subCommands.isEmpty()){
            matches.addAll(getMatches(possibleArgs,search));
        }

        return matches;
    }

    /**
     * Get possible args which matches with the search string
     *
     * @param possibleTabArgs the possible tab args register in the command
     * @param search the beginning of the arg
     *
     * @return possible matches
     */
    private List<String> getMatches(String[] possibleTabArgs, String search){
        List<String> matches = new ArrayList<>();

        for (String arg : possibleTabArgs) {
            if(arg.equalsIgnoreCase("PLAYER"))matches.addAll(getPossiblePlayer(search));
            if (arg.toLowerCase().startsWith(search)) {
                matches.add(arg);
            }
        }

        return matches;
    }

    /**
     * Get possible player name with the beginning of the arg
     *
     * @param search beginning of the arg
     *
     * @return possible players name which start with the search
     */
    private List<String> getPossiblePlayer(String search){
        List<String> matches = new ArrayList<>();

        /*for(Player player : AltheaAPI.get().getJavaPlugin().getServer().getOnlinePlayers()){
            if ( player.getName().toLowerCase().startsWith( search ) ) {
                matches.add( player.getName() );
            }
        }*/

        return matches;
    }

    /**
     * Get the list of sub commands
     * @return the list of sub commands
     */
    public List<BukkitSubCommand> getSubCommands() {
        return subCommands;
    }

    /**
     * Show help of the command
     */
    public void showHelp(){
        //sender.sendMessage(MessageUtils.BAR);
        if(!subCommands.isEmpty()) {
            subCommands.forEach(subCommand -> {
                sender.sendMessage("§9Usage : §3/" + getName() + " " + subCommand.getName() + " " + subCommand.getArgs());
                sender.sendMessage("§7➜ " + subCommand.getDescription());
            });
        }else{
            sender.sendMessage("§9Usage : §3/" + getName() + " " + args);
            sender.sendMessage("§7➜ " + description);
        }
        sender.sendMessage(" \n§9Aliases : §3"+getAliasesAsString());
        //sender.sendMessage(MessageUtils.BAR);
    }

    /**
     * get list of alias as string
     * @return alias as string
     */
    public String getAliasesAsString(){
        String aliases = "";
        for (String s : getAliases()) {
            aliases += s + ", ";
        }
        if(aliases.equals(""))return "";
        aliases = aliases.substring(0,aliases.length() - 2);
        return aliases;
    }
}
