package xyz.annorit24.simplequestscore.core.trigger;

import org.bukkit.event.Event;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestscore.quests.actions.IncrementStepAction;
import xyz.annorit24.simplequestscore.utils.events.EventsUtils;

import java.util.Map;

/**
 * @author Annorit24
 * Created on 26/01/2020
 */
public class ActionsCall {

    private volatile boolean finish;
    private volatile boolean reprocess;

    private Map<Integer, Action> actions;
    private Map<Integer, Boolean> results;
    private Event event;

    public ActionsCall(Map<Integer, Action> actions, Map<Integer, Boolean> results, Event event) {
        this.actions = actions;
        this.results = results;
        this.event = event;
        this.reprocess = true;
        this.finish = false;
    }

    public void run(){
        /*new Thread(() -> {
            for (Map.Entry<Integer, Action> entry : actions.entrySet()) {
                Action action = entry.getValue();
                if(action.isCustomCall())continue;
                action.call(EventsUtils.getPlayerFromEvent(event),results);
                if(action instanceof IncrementStepAction){
                    reprocess = ((IncrementStepAction)action).isReprocess();
                }
                while (!action.isFinish()){}
                System.out.println(action.getClass().getName());
                System.out.println("b");
                action.setFinish(false);
                System.out.println("1");
            }
            System.out.println("2");
            setFinish(true);
            System.out.println("3");
        }).start();
        System.out.println("a : "+this.finish);*/
    }

    public synchronized boolean isFinish() {
        return finish;
    }

    public synchronized boolean isReprocess() {
        return reprocess;
    }

    public synchronized void setFinish(boolean finish) {
        this.finish = finish;
    }
}
