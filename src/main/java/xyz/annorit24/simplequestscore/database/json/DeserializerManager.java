package xyz.annorit24.simplequestscore.database.json;

import xyz.annorit24.simplequestsapi.json.Deserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Annorit24 on 13/06/18.
 */
public class DeserializerManager {

    private Map<Class, Deserializer> deserializers;

    public DeserializerManager() {
        this.deserializers = new HashMap<>();
    }

    public void setDeserializer(Class clazz, Deserializer deserializer){
        deserializers.remove(clazz);
        deserializers.put(clazz,deserializer);
    }

    public void setDefaultDeserializers(){

    }

    public Deserializer getDeserializerFor(Class clazz){
        return deserializers.get(clazz);
    }
}
