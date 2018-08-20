package pythagoras_tree;

/**
 * vector A to B
 * Created by leo on 2016/10/18.
 */
public class vector {
    private point A;
    private point B;

    public vector() {
    }

    public vector(point a, point b) {
        A = a;
        B = b;
    }

    public point getA() {
        return A;
    }

    public point getB() {
        return B;
    }
    public double length(){
        return Math.pow(Math.pow(A.getX()-B.getX(),2)+Math.pow(A.getY()-B.getY(),2),0.5);
    }
    public point getV(){//暺�����
        point Vpoint=new point();
        Vpoint.setX(B.getX() - A.getX());
        Vpoint.setY(B.getY() - A.getY());
        return Vpoint;
    }
    public static point rotationV(point Vin,double angle){//���� 嚗�������祕摨扳��(�����)嚗�敺����������漣璅�
        point V=new point();
        double Radians=Math.toRadians(angle);
        V.setX((Math.cos(Radians) * Vin.getX() + (-Math.sin(Radians)) * -Vin.getY()));
        V.setY(-(Math.sin(Radians)*Vin.getX()+(Math.cos(Radians))* -Vin.getY()));
        return V;
    }
    public static point useVmoveP(point p,point v){
        point np=new point();
        np.setX(p.getX() + v.getX());
        np.setY(p.getY()+v.getY());
        return np;
    }
    public static point normalize(point v,point v2,double t1,double t2){//4,5
        point np=new point();
        double v2l=v2.length();
        double v1l=v.length();
        double t=v2l*t1/t2;
        np.setX(t * v.getX() / v1l);
        np.setY(t*v.getY()/v1l);
        return np;
    }


}
