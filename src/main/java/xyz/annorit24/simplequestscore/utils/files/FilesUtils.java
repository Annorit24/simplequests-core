package xyz.annorit24.simplequestscore.utils.files;

import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.utils.logger.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 *
 *
 * @author Annorit24
 * Created on 28/12/2019
 */
public class FilesUtils {

    public static void createDir(File dir){
        if(dir.exists())return;
        if(!dir.mkdir()){
            LogUtils.ERROR.log("Could not create directory : "+dir.getPath());
        }
    }

    /**
     * Create a file
     * @param file the File to create
     * @param defaultResourcePath the path of the default file which is located in the resources folder
     */
    public static void createFile(File file, String defaultResourcePath){
        if(file.exists())return;
        if(defaultResourcePath == null){
            try {
                if(file.createNewFile()){
                    LogUtils.INFO.log("File created successfully : "+file.getPath());
                }else{
                    LogUtils.ERROR.log("Could not create file : "+file.getPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                SimpleQuestsCore.getInstance().saveResource(defaultResourcePath, false);
                LogUtils.INFO.log("Default file created successfully : "+defaultResourcePath);
            } catch (Exception e) {
                LogUtils.ERROR.log("Could not create Default file : "+defaultResourcePath);
                e.printStackTrace();
            }
        }
    }
}
