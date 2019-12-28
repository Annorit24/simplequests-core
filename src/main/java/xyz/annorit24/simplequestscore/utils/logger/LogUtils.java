package xyz.annorit24.simplequestscore.utils.logger;

/**
 * Created by Annorit24 on 19/11/17.
 */
public enum LogUtils {

    /**
     * GREEN message and INFO Prefix
     * Use to log info of the plugin
     */
    INFO(ConsoleColor.GREEN.getFormatCode()+"[&&PREFIX][INFO] "),
    /**
     * RED message and ERROR Prefix
     * Use to log error of the plugin
     */
    ERROR(ConsoleColor.RED.getFormatCode()+"[&&PREFIX][ERROR] "),
    /**
     * RED message and WARNING Prefix
     * Use to log warnings of the plugin
     */
    WARNING(ConsoleColor.RED.getFormatCode()+"[&&PREFIX][WARNING] "),

    /**
     * MAGENTA message and DEBUG Prefix
     * Use to log some debug in the console
     */
    DEBUG(ConsoleColor.MAGENTA.getFormatCode()+"[&&PREFIX][DEBUG] ");

    /**
     * The prefix of the message
     */
    String prefix;

    /**
     * The application name
     */
    public static String appName = "";

    /**
     * Prefix of the message
     * @param prefix prefix
     */
    LogUtils(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Log a message with the prefix
     * @param message message
     */
    public void log(String message){
        for (ConsoleColor consoleColor : ConsoleColor.values()) {
            message = message.replace(consoleColor.getSymbol(), consoleColor.getFormatCode());
        }
        String prefix1 = prefix.replace("&&PREFIX", appName);
        System.out.println(prefix1 + message + "\033[0;37m");
    }

    /**
     * Just log a message in the console
     * @param message message
     */
    public static void simpleLog(String message){
        for (ConsoleColor consoleColor : ConsoleColor.values()) {
            message = message.replace(consoleColor.getSymbol(), consoleColor.getFormatCode());
        }
        System.out.println(message+"\033[0;37m");
    }
}
