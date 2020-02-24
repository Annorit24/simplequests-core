package xyz.annorit24.simplequestscore.mojang;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import xyz.annorit24.hermes.utils.logger.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Annorit24 on 03/11/17.
 */
public class MojangRequest {

    public UUID getUuidFromMojang(String playerName){
        BufferedReader rd  = null;
        StringBuilder sb = null;
        String line = null;
        try {
            URL url = new URL("https://mcapi.cloudprotected.net/uuid/" + playerName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            connection.connect();
            rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();

            while ((line = rd.readLine()) != null)
            {
                sb.append(line + '\n');
            }

            JsonObject jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();
            JsonArray result = jsonObject.get("result").getAsJsonArray();
            JsonObject info = result.get(0).getAsJsonObject();
            String uuid = info.get("uuid-formatted").getAsString();
            if(uuid == null){
                return null;
            }
            return UUID.fromString(uuid);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getName(String uuid) {
        String url = "https://api.mojang.com/user/profiles/"+uuid.replace("-", "")+"/names";
        try {
            String nameJson = IOUtils.toString(new URL(url));
            JsonArray nameValue = new JsonParser().parse(nameJson).getAsJsonArray();
            JsonObject playerSlot = nameValue.get(nameValue.size()-1).getAsJsonObject();
            return playerSlot.get("name").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String[] getSkinInfoFromName(String name) {
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();

            return new String[] {texture, signature};
        } catch (IOException e) {
            LogUtils.ERROR.log("Could not get skin data from session servers!");
            e.printStackTrace();
            return null;
        }
    }


}
