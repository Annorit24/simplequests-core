package xyz.annorit24.simplequestscore.database.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.database.Database;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.client.SimpleClient;
import xyz.annorit24.simplequestscore.config.FilesManager;
import xyz.annorit24.simplequestscore.config.Parser;
import xyz.annorit24.simplequestscore.utils.logger.LogUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Annorit24
 * Created on 27/12/2019
 */
public class SimpleJsonDatabase extends Database implements Parser {

    private File clientDataDirectory;

    private FilesManager filesManager;
    private Gson gson;

    public SimpleJsonDatabase(SimpleQuestsCore plugin) {
        this.filesManager = plugin.getFilesManager();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(SimpleClient.class,new ClientTypeAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void init() {
        clientDataDirectory = filesManager.getCLIENT_DATA_DIRECTORY();
        if(!clientDataDirectory.isDirectory()){
            LogUtils.ERROR.log("Could not init SimpleJson database, clientDataDirectory ");
        }
    }

    @Override
    public void close() {
        //Nothing to do
    }

    @Override
    public Client getClient(UUID uuid) {
        File clientFile = new File(clientDataDirectory.getPath()+File.separator+uuid.toString()+".json");
        return gson.fromJson(parse(clientFile), SimpleClient.class);
    }

    @Override
    public Client createNewClient(Player player) {
        File clientFile = new File(clientDataDirectory.getPath()+File.separator+player.getUniqueId().toString()+".json");
        clientFile.delete();

        try {
            clientFile.createNewFile();
            Client defaultClient = Client.getDefaultClient(player);
            FileUtils.writeStringToFile(
                    clientFile,
                    gson.toJson(defaultClient,SimpleClient.class),
                    StandardCharsets.UTF_8
            );

            return defaultClient;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveClient(Client client) {
        File clientFile = new File(clientDataDirectory.getPath()+File.separator+client.getUniqueId().toString()+".json");
        try {
            FileUtils.writeStringToFile(
                    clientFile,
                    gson.toJson(client,SimpleClient.class),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean doesExist(UUID uuid) {
        File clientFile = new File(clientDataDirectory.getPath()+File.separator+uuid.toString()+".json");
        return clientFile.exists();
    }

}
