package xyz.annorit24.simplequestscore.utils.maths;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

public class Spline3D extends BasicSpline{
    private Vector<Vector3f> points;

    //TEST
    //private Vector<View> views;

    private Vector<Cubic> xCubics;
    private Vector<Cubic> yCubics;
    private Vector<Cubic> zCubics;

    //TEST
    //private Vector<Cubic> yawCubics;
    //private Vector<Cubic> pitchCubics;

    private static final String vector3DgetXMethodName = "getX";
    private static final String vector3DgetYMethodName = "getY";
    private static final String vector3DgetZMethodName = "getZ";

    //TEST
    //private static final String viewGetYawMethodName = "getYaw";
    //private static final String viewGetPitchMethodName = "getPitch";

    private Method vector2DgetXMethod;
    private Method vector2DgetYMethod;
    private Method vector2DgetZMethod;

    //TEST
    //private Method viewGetYawMethod;
    //private Method viewGetPitchMethod;

    private static final Object[] EMPTYOBJ = new Object[] { };

    public Spline3D() {
        this.points = new Vector<Vector3f>();
        //TEST
        //this.views = new Vector<>();

        this.xCubics = new Vector<Cubic>();
        this.yCubics = new Vector<Cubic>();
        this.zCubics = new Vector<Cubic>();
        //TEST
        //this.yawCubics = new Vector<>();
        //this.pitchCubics = new Vector<>();


        try {
            vector2DgetXMethod = Tuple3f.class.getDeclaredMethod(vector3DgetXMethodName);
            vector2DgetYMethod = Tuple3f.class.getDeclaredMethod(vector3DgetYMethodName);
            vector2DgetZMethod = Tuple3f.class.getDeclaredMethod(vector3DgetZMethodName);
            //TEST
            //viewGetPitchMethod = View.class.getDeclaredMethod(viewGetPitchMethodName);
            //viewGetYawMethod = View.class.getDeclaredMethod(viewGetYawMethodName);
        } catch (SecurityException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void addPoint(Vector3f point) {
        this.points.add(point);
    }

    public void addAllPoints(List<Vector3f> points){
        this.points.addAll(points);
    }

    /*public void addView(View view) {
        this.views.add(view);
    }*/

    public Vector<Vector3f> getPoints() {
        return points;
    }

    public void calcSpline() {
        try {
            calcNaturalCubic(points, vector2DgetXMethod, xCubics);
            calcNaturalCubic(points, vector2DgetYMethod, yCubics);
            calcNaturalCubic(points, vector2DgetZMethod, zCubics);
            //calcNaturalCubic(views, viewGetPitchMethod, pitchCubics);
            //calcNaturalCubic(views, viewGetYawMethod, yawCubics);
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Vector3f getPoint(float position) {
        position = position * xCubics.size();
        int      cubicNum = (int) position;
        float   cubicPos = (position - cubicNum);

        return new Vector3f(xCubics.get(cubicNum).eval(cubicPos),
                yCubics.get(cubicNum).eval(cubicPos),
                zCubics.get(cubicNum).eval(cubicPos));
    }

    /*public View getView(float position) {
        position = position * xCubics.size();
        int      cubicNum = (int) position;
        float   cubicPos = (position - cubicNum);

        return new View(
                yawCubics.get(cubicNum).eval(cubicPos),
                pitchCubics.get(cubicNum).eval(cubicPos)
                );
    }*/
}
