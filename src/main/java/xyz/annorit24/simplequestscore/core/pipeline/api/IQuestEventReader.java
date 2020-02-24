package xyz.annorit24.simplequestscore.core.pipeline.api;

import xyz.annorit24.simplequestscore.core.pipeline.QuestEventContainer;
import xyz.annorit24.simplequestscore.utils.Callback;

/**
 * @author Annorit24
 * Created on 08/02/2020
 */
public interface IQuestEventReader {

    Callback read(QuestEventContainer container);

}
