package xyz.annorit24.simplequestscore.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Annorit24
 * Created on 20/01/2020
 */
public interface Parser {

    default JsonObject parse(File file){
        JsonParser parser = new JsonParser();
        try {
            JsonElement element = parser.parse(new FileReader(file));
            return element.getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
