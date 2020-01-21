package xyz.annorit24.simplequestscore.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.utils.logger.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Annorit24
 * Created on 18/01/2020
 */
public abstract class AbstractConfig implements Parser{

    protected JsonObject config;

    public AbstractConfig(String configDirectory, String configName, SimpleQuestsCore plugin){
        FilesManager filesManager = plugin.getFilesManager();

        File directory = new File(filesManager.getDATA_DIRECTORY()+File.separator+configDirectory);
        File config = new File(filesManager.getDATA_DIRECTORY()+File.separator+configDirectory+configName);


        if(!directory.exists() || !config.exists()){
            LogUtils.ERROR.log("Could not load "+configName+" in this directory : "+directory.getPath());
            return;
        }


        this.config = this.parse(config);
    }

}
