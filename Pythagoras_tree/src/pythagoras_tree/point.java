package pythagoras_tree;

/**
 * Created by leo on 2016/10/18.
 */
public class point {
    private double x;
    private double y;
    public point() {
    }
    public point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+getX()+","+getY()+")";
    }
    public double length(){
        return Math.pow(Math.pow(getX(),2)+Math.pow(getY(),2),0.5);
    }
}
