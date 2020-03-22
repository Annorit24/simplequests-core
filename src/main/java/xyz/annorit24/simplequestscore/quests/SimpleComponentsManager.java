package xyz.annorit24.simplequestscore.quests;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import xyz.annorit24.simplequestsapi.quest.components.ComponentsManager;
import xyz.annorit24.simplequestsapi.utils.logger.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 23/01/2020
 */
public class SimpleComponentsManager<T> extends ComponentsManager<T> {

    private Map<String, Class<? extends T>> components;
    private GsonBuilder builder;
    private String componentName;
    // TODO: 22/01/2020 Implement ActionManager in ApiImplementation

    public SimpleComponentsManager(String componentName) {
        this.componentName = componentName;
        this.components = new HashMap<>();
        this.builder = new GsonBuilder().setPrettyPrinting();
    }

    public void register(Class<? extends T> clazz, String typeName, TypeAdapter<? extends T> typeAdapter){
        builder.registerTypeAdapter(clazz, typeAdapter);
        components.put(typeName,clazz);
    }

    public Class<? extends T> get(String typeName){
        if(!components.containsKey(typeName)){
            LogUtils.ERROR.log("@ComponentsManager:get Could not get "+componentName+" with the type : "+typeName+". It was not register in the manager");
            return null;
        }
        return components.get(typeName);
    }

    public GsonBuilder getBuilder() {
        return builder;
    }

}
