package xyz.annorit24.simplequestscore.client.customdata;

import xyz.annorit24.simplequestsapi.client.Client;
import xyz.annorit24.simplequestsapi.client.ClientManager;
import xyz.annorit24.simplequestsapi.customdata.CustomDataManager;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;

/**
 * @author Annorit24
 * Created on 18/04/2020
 */
public class SimpleCustomDataManager extends CustomDataManager {

    private ClientManager clientManager;

    public SimpleCustomDataManager(SimpleQuestsCore simpleQuestsCore) {
        clientManager = simpleQuestsCore.getClientManager();
    }

    @Override
    public void cleanQuestsData(Client client) {
        // TODO: 18/04/2020
    }

    @Override
    public void cleanQuestStepsData(Client client) {
        // TODO: 18/04/2020
    }

}
