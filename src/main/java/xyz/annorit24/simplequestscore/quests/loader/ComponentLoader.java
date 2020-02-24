package xyz.annorit24.simplequestscore.quests.loader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import xyz.annorit24.simplequestscore.quests.SimpleComponentsManager;
import xyz.annorit24.simplequestscore.utils.logger.LogUtils;

/**
 * @author Annorit24
 * Created on 23/01/2020
 */
public class ComponentLoader<K> {

    private Gson gson;
    private SimpleComponentsManager<K> manager;

    public ComponentLoader(SimpleComponentsManager<K> manager) {
        this.manager = manager;
        this.gson = manager.getBuilder().create();
    }

    public K load(JsonObject object){
        if(object.has("type")){
            LogUtils.ERROR.log("@ComponentLoader:load Could not load component object, there is no type");
            return null;
        }

        Class<? extends K> actionClazz = manager.get(object.get("type").getAsString());
        if(actionClazz == null)return null;
        K component = gson.fromJson(object,actionClazz);

        if(component == null)LogUtils.ERROR.log("@ActionLoader:loadAction Could not deserialize Action : "+object.get("type").toString()+".");
        return component;
    }

}
