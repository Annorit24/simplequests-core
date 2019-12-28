package xyz.annorit24.simplequestscore.database.json;

import xyz.annorit24.simplequestsapi.json.Serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Annorit24 on 17/08/18.
 */
public class SerializerManager {

    private Map<Class, Serializer> serializers;

    public SerializerManager() {
        this.serializers = new HashMap<>();
    }

    public void setSerializer(Class clazz, Serializer serializer){
        serializers.remove(clazz);
        serializers.put(clazz,serializer);
    }

    public void setDefaultSerializers(){

    }

    public Serializer getSerializerFor(Class clazz){
        return serializers.get(clazz);
    }

}
