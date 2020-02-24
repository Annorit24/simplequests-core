package xyz.annorit24.simplequestscore.config;

import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.utils.files.FilesUtils;

import java.io.File;

/**
 * @author Annorit24
 * Created on 27/12/2019
 */
public class FilesManager {

    private  final File DATABASE_CONFIG_FILE;
    private final File DEFAULT_LANG_DIRECTORY;
    private final File QUESTS_DIRECTORY;
    private final File DATA_DIRECTORY;
    private final File CONFIG_DIRECTORY;
    private final File CLIENT_DATA_DIRECTORY;

    private final File GLOBAL_CONFIG_FILE;
    private final File DEFAULT_LANG_FILE;

    public FilesManager(SimpleQuestsCore javaPlugin) {
        String DATA_FOLDER_NAME = "plugins/" + javaPlugin.getDataFolder().getName() + "/";

        DATA_DIRECTORY = new File(DATA_FOLDER_NAME);
        DEFAULT_LANG_DIRECTORY = new File(DATA_FOLDER_NAME + "translations/");
        QUESTS_DIRECTORY = new File(DATA_FOLDER_NAME + "quests/");
        CONFIG_DIRECTORY = new File(DATA_FOLDER_NAME + "config/");
        CLIENT_DATA_DIRECTORY = new File(DATA_FOLDER_NAME+"client_data/");

        GLOBAL_CONFIG_FILE = new File(DATA_FOLDER_NAME + "config/global_config.json");
        DATABASE_CONFIG_FILE = new File(DATA_FOLDER_NAME+"config/database_config.json");
        DEFAULT_LANG_FILE = new File(DATA_FOLDER_NAME + "translations/english.json");

    }

    public void createFiles(){
        FilesUtils.createDir(DATA_DIRECTORY);
        FilesUtils.createDir(DEFAULT_LANG_DIRECTORY);
        FilesUtils.createDir(QUESTS_DIRECTORY);
        FilesUtils.createDir(CONFIG_DIRECTORY);
        FilesUtils.createDir(CLIENT_DATA_DIRECTORY);

        FilesUtils.createFile(GLOBAL_CONFIG_FILE,"config/global_config.json");
        FilesUtils.createFile(DEFAULT_LANG_FILE,"translations/english.json");
        FilesUtils.createFile(DATABASE_CONFIG_FILE,"config/database_config.json");
    }

    public File getDATA_DIRECTORY() {
        return DATA_DIRECTORY;
    }

    public File getCLIENT_DATA_DIRECTORY() {
        return CLIENT_DATA_DIRECTORY;
    }

    public File getQUESTS_DIRECTORY() {
        return QUESTS_DIRECTORY;
    }
}
