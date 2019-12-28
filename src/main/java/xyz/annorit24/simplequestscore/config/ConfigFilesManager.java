package xyz.annorit24.simplequestscore.config;

import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.utils.files.FilesUtils;

import java.io.File;

/**
 * @author Annorit24
 * Created on 27/12/2019
 */
public class ConfigFilesManager {

    private final File DEFAULT_LANG_DIRECTORY;
    private final File QUESTS_DIRECTORY;
    private final File DATA_DIRECTORY;

    private final File GLOBAL_CONFIG_FILE;
    private final File DEFAULT_LANG_FILE;

    public ConfigFilesManager(SimpleQuestsCore javaPlugin) {
        String DATA_FOLDER_NAME = "plugins/" + javaPlugin.getDataFolder().getName() + "/";

        DATA_DIRECTORY = new File(DATA_FOLDER_NAME);
        DEFAULT_LANG_DIRECTORY = new File(DATA_FOLDER_NAME + "translations/");
        QUESTS_DIRECTORY = new File(DATA_FOLDER_NAME + "quests/");

        GLOBAL_CONFIG_FILE = new File(DATA_FOLDER_NAME + "global_config.json");
        DEFAULT_LANG_FILE = new File(DATA_FOLDER_NAME + "translations/english.json");

    }

    public void createFiles(){
        FilesUtils.createDir(DATA_DIRECTORY);
        FilesUtils.createDir(DEFAULT_LANG_DIRECTORY);
        FilesUtils.createDir(QUESTS_DIRECTORY);

        FilesUtils.createFile(GLOBAL_CONFIG_FILE,"global_config.json");
        FilesUtils.createFile(DEFAULT_LANG_FILE,"translations/english.json");
    }
}
