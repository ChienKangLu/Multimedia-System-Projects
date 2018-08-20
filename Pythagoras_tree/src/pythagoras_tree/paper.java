package pythagoras_tree;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;


import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Created by leo on 2016/10/18.
 */
public class paper extends JPanel implements ActionListener,MouseMotionListener,MouseListener{
    int windowsWidth=800;
    int windowsHeight=500;
    int rectWidth=100;
    int rectHeight=100;
    double P1_X=windowsWidth/2-rectWidth/2;
    double P1_Y=windowsHeight;
    double P2_X=P1_X+rectWidth;
    double P2_Y=windowsHeight;
//    double P1_X=350;
//    double P1_Y=500;
//    double P2_X=430;
//    double P2_Y=440;
    double r1=3;
    double r2=4;
    double r3=5;
    int level=1;
    public static Color color;
    double FACTOR=0.8;//0.7

    @Override
    public void setOpaque(boolean isOpaque) {
        super.setOpaque(false);
    }

    @Override
    public void setBackground(Color bg) {

        super.setBackground(Color.black);
    }

    @Override
    public Dimension getPreferredSize() {
        // TODO Auto-generated method stub
        return new Dimension(windowsWidth, windowsHeight);
    }
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;
        //FIRST RECT
        point pointA=new point(P1_X,P1_Y);
        point pointB=new point(P2_X,P2_Y);
        RECURSUVE(g2d,pointA,pointB,level,color);

    }

    void RECURSUVE(Graphics2D g2d,point pointA,point pointB,int level,Color color){
        if(MyClass.mode==1){
            r1=3;
            r2=4;
            r3=5;
        }else{
            r1=5;
            r2=12;
            r3=13;
        }
//            r1=1;
//            r2=1;
//            r3=Math.pow(2,0.5);
//        System.out.println(MyClass.mode);
        if(level==0){
            return;
        }
        level--;
        //FIRST RECT
//        System.out.println("pointA "+pointA.toString());
//        System.out.println("pointB "+pointB.toString());
        //////////////////////
        vector v=new vector(pointA,pointB);
        point v1=v.getV();
        point v2=vector.rotationV(v1, 90);

//        System.out.println("v1 " + v1.toString());
//        System.out.println("v2 " + v2.toString());
        point a=pointA;//撌虫��
        point b=pointB;;//�銝�
        point c=vector.useVmoveP(pointA,v2);//撌虫��
        point d=vector.useVmoveP(pointB,v2);;//�銝�

        Path2D square = new Path2D.Float();
        square.moveTo(a.getX(),a.getY());
        square.lineTo(b.getX(), b.getY());
        square.lineTo(d.getX(), d.getY());
        square.lineTo(c.getX(),c.getY());

//        System.out.println("a " + a.toString());
//        System.out.println("b "+b.toString());
//        System.out.println("c "+c.toString());
//        System.out.println("d "+d.toString());

        square.closePath();
        //////////////////////
        g2d.setColor(color);
        g2d.fill(square);

        // FIND left
        vector L_v=new vector(c,d);
        point L1=L_v.getV();
        point L2=vector.rotationV(L1,Math.round(Math.toDegrees(Math.acos(r2 / r3))));
//        System.out.println(Math.round( Math.toDegrees(Math.acos(r2 / r3))));
        point L3=vector.normalize(L2,L1,r2,r3);
        point w=vector.useVmoveP(c, L3);//撌虫��

//        System.out.println("c "+c.toString());
//        System.out.println("w "+w.toString());

        //color=dark(color);
//        color=color.brighter();
        color=lighter(color);
        RECURSUVE(g2d, c, w, level,color);
        // FIND right
        RECURSUVE(g2d, w, d, level,color);

    }
    Path2D drawrect(point pointA,point pointB){

        vector v=new vector(pointA,pointB);
        point v1=v.getV();
        point v2=vector.rotationV(v1, 90);
        point a=pointA;//撌虫��
        point b=pointB;;//�銝�
        point c=vector.useVmoveP(pointA,v2);//撌虫��
        point d=vector.useVmoveP(pointB,v2);;//�銝�

        Path2D square = new Path2D.Float();
        square.moveTo(a.getX(),a.getY());
        square.lineTo(b.getX(), b.getY());
        square.lineTo(d.getX(), d.getY());
        square.lineTo(c.getX(),c.getY());
        square.closePath();
        return square;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        initial();
        if(SwingUtilities.isRightMouseButton(e)){
//            System.out.println("right click");
            if(level>1) {
                level--;
                repaint();
            }
        }
        if(SwingUtilities.isLeftMouseButton(e)){
//            System.out.println("left click");

            level++;
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public Color dark(Color color){
        double FACTOR=10;
            return new Color(Math.max((int)(color.getRed()-FACTOR), 0),
                    Math.max((int)(color.getGreen()-FACTOR), 0),
                    Math.max((int)(color.getBlue() -FACTOR), 0),
                    color.getAlpha());
    }
    public Color lighter(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();
        int i = (int)(1.0/(1.0-FACTOR));
        if ( r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;

        return new Color(Math.min((int) (r / FACTOR), 255),
                Math.min((int) (g / FACTOR), 255),
                Math.min((int) (b / FACTOR), 255),
                alpha);
    }
    public static void initial(){
        color=new Color(139, 28, 33);
    }
}
