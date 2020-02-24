package xyz.annorit24.simplequestscore.core.pipeline.api.components;

import xyz.annorit24.simplequestscore.utils.Callback;

/**
 * @author Annorit24
 * Created on 22/02/2020
 */
public interface Component<T extends ComponentParameter>{

    Callback<ComponentResult> call(T parameter);

}
