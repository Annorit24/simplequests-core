package xyz.annorit24.simplequestscore.quests.actions.cineamticaction;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.quest.components.Action;
import xyz.annorit24.simplequestsapi.quest.components.ActionParameter;
import xyz.annorit24.simplequestsapi.quest.components.ComponentResult;
import xyz.annorit24.simplequestsapi.utils.Callback;
import xyz.annorit24.simplequestscore.utils.maths.Spline3D;

import javax.vecmath.Vector3f;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Annorit24
 * Created on 25/01/2020
 */
public class CinematicAction extends Action {

    private Spline3D spline3D;
    private List<Float> yaws;

    public CinematicAction(List<Integer> requireValidConditions, boolean critical, boolean customCall, List<Vector3f> keysPosition) {
        super(requireValidConditions, critical, customCall);
        this.spline3D = new Spline3D();
        spline3D.addAllPoints(keysPosition);
        spline3D.calcSpline();
    }

    @Override
    public Callback<ComponentResult> call(ActionParameter actionParameter) {
        Map<Integer, Boolean> results = actionParameter.getConditionsResults();
        Player player = actionParameter.getPlayer();

        System.out.println("501");
        if(isConditionsValid(results)) {
            System.out.println("502");
            for (float t = 0f; t < 1; t += 0.0005) {
                System.out.println("503");
                Vector3f vector3f = spline3D.getPoint(t);
                System.out.println("504");
                //getYaw(player, t);
                System.out.println("505");

                Location location = new Location(player.getLocation().getWorld(), vector3f.x, vector3f.y, vector3f.z);
                System.out.println("506");
                player.teleport(location);
                System.out.println("507");

                try {
                    System.out.println("508");
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return () -> ComponentResult.SUCCESS;
        }
        return () -> ComponentResult.FAILURE;
    }

    @Override
    public Action cloneAction() {
        return null;
    }

    /*public CinematicAction(List<Integer> validConditions, boolean customCall, List<Vector3f> keysPosition) {
        super(validConditions, customCall);
        this.spline3D = new Spline3D();
        spline3D.addAllPoints(keysPosition);
        spline3D.calcSpline();
        this.yaws = new ArrayList<>();
        yaws.add(-82F);
        yaws.add(-122.1F);
        yaws.add(162F);
        yaws.add(112.7F);
    }*/

    /*@Override
    public void call(Player player, Map<Integer, Boolean> map) {
        new Thread(() -> {
            for (float t = 0f; t < 1; t += 0.0005) {
                Vector3f vector3f = spline3D.getPoint(t);
                getYaw(player, t);

                Location location = new Location(player.getLocation().getWorld(), vector3f.x, vector3f.y, vector3f.z);
                player.teleport(location);

                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setFinish(true);
        }).start();
    }*/

    private void getYaw(Player player, float t){
        float a = 1/(float) yaws.size();
        int b = (int)(t/a);

        float yawTo =yaws.get(b+1);
        float yaw = player.getLocation().getYaw();

        float diff;

        if(yawTo < 0 || yaw < 0){
            diff = subtractLowerToHigher(yawTo,yaw);
        }

        if(yawTo > 0 || yaw < 0){

        }

        if(yawTo < 0 || yaw > 0){

        }

        if(yawTo > 0 || yaw > 0){

        }

    }

    private float subtractLowerToHigher(float a, float b){
        return Math.max(a, b)- Math.min(a, b);
    }


}
