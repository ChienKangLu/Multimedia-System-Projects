package bezierCurve;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class paper extends JPanel implements MouseListener, MouseMotionListener{
	Graphics2D g2d;
    int windowsWidth=800;
    int windowsHeight=500;
    double squarelength=5;
    int click=0;
    controlpoint point4[]=new controlpoint[4];
    int level=10;
    boolean AuxiliaryLine=false;//»²§U½u
    public paper(){
    	for(int i=0;i<4;i++){
    		point4[i]=new controlpoint();
    	}
    	AuxString=AuxString_show;
    }
    public void reset(){
    	click=0;
    	point4=new controlpoint[4];
    	for(int i=0;i<4;i++){
    		point4[i]=new controlpoint();
    		
    	}
    	repaint();
    }
    @Override
    public void setBackground(Color bg) {
    	// TODO Auto-generated method stub
    	super.setBackground(Color.WHITE);
    }
    @Override
    public Dimension getPreferredSize() {
        // TODO Auto-generated method stub
        return new Dimension(windowsWidth, windowsHeight);
    }

    double AuxiliaryLineBtnX =10;
    double AuxiliaryLineBtnY =10;
    double AuxiliaryLineBtnWidth=180;
    double AuxiliaryLineBtnHeight=30;
	String AuxString;
	final String AuxString_show="click to show AuxiliaryLine";
	final String AuxString_hide="click to hide AuxiliaryLine";
	
	
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        g2d=(Graphics2D)g;     
        drawpointRect();
        drawrect(AuxiliaryLineBtnX,AuxiliaryLineBtnY,AuxiliaryLineBtnWidth,AuxiliaryLineBtnHeight,AuxString,15);
      //  drawrect(10,50,15,15,"+",10);
        
        if(click==4){
        	RECURSIVE(point4[0],point4[1],point4[2],point4[3],level);

        }
        
//        point a=new point(0,0);
//        point b=new point(100,50.3);
//        drawLine(a,b);
//        point middle=point.middlepoint(a, b);
//        drawLine(middle,new point(100,100));
        
    }
    void RECURSIVE(point A,point B,point C,point D,int level){

    	if(level==0){
    		return;
    	}
    	level--;
    	if(AuxiliaryLine){
	    	drawLine(A,B);
	    	drawLine(B,C);
	    	drawLine(C,D);
    	}
    	point E=point.middlepoint(A, B);
    	point F=point.middlepoint(B, C);
    	point G=point.middlepoint(C, D);
    	point H=point.middlepoint(E, F);
    	point I=point.middlepoint(F, G);
    	point J=point.middlepoint(H, I);
    	if(AuxiliaryLine){
	    	drawLine(E,F);
	    	drawLine(F,G);
	    	drawLine(H,I);
    	}
    	if(level!=0){
	    	RECURSIVE(A, E, H, J,level);
	    	RECURSIVE(J, I, G, D,level);
    	}else{
    		drawLine(A,J);
    	}
    	
    }
    void drawLine(point a,point b){
        g2d.draw(new Line2D.Double(a.getX(),a.getY(),b.getX(),b.getY()));
    }
    void drawrect(double x,double y){
        Path2D square = new Path2D.Double();
        square.moveTo(x,y);
        square.lineTo(x+squarelength, y);
        square.lineTo(x+squarelength, y+squarelength);
        square.lineTo(x,y+squarelength);
        square.closePath();
        g2d.draw(square);
       
    }
    void drawrect(double x,double y,double w,double h,String s,int fontSize){
        Path2D square = new Path2D.Double();
        square.moveTo(x,y);
        square.lineTo(x+w, y);
        square.lineTo(x+w, y+h);
        square.lineTo(x,y+h);
        square.closePath();
        g2d.draw(square);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
        g2d.drawString(s,(int)( x+5),(int)( y+h/1.5)); 

    }
    void drawpointRect(){
    	for(int i=0;i<4;i++){
    		if(point4[i].getX()!=-1&&point4[i].getY()!=-1){
    			drawrect(point4[i].getX(),point4[i].getY());
    			//System.out.println(point4[i].getX()+"~~"+point4[i].getY());
    		}
    	}
    }
    

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	double biasy=30;
	double biasx=8;
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		if(SwingUtilities.isRightMouseButton(e)){
//          System.out.println("right click");
			reset();
		}
		if(SwingUtilities.isLeftMouseButton(e)){
			double x=e.getX()-biasx;
			double y=e.getY()-biasy;
			if (x >= AuxiliaryLineBtnX && x <= AuxiliaryLineBtnX+AuxiliaryLineBtnWidth&&y >= AuxiliaryLineBtnY&& y <= AuxiliaryLineBtnY+AuxiliaryLineBtnHeight){
				//System.out.println("click AuxiliaryLineBtn");
				if(AuxString.equals(AuxString_show)){
			    	AuxString=AuxString_hide;
			    	AuxiliaryLine=true;
			    	repaint();
				}else{
			    	AuxString=AuxString_show;
			    	AuxiliaryLine=false;
			    	repaint();
				}
			}
			if(click<=3){
				
				
				point4[click].setX(x);
				point4[click].setY(y);
			    repaint();
//				System.out.println("click");
//				System.out.println("click"+click+" : "+point4[click].getX()+" "+point4[click].getY());
				click++;
			}else{
				//System.out.println("dragging");
//				System.out.println(mx);
//				System.out.println(point4[0].getX());
//				System.out.println(my);
//				System.out.println(point4[0].getY() );
				for(int i=0;i<4;i++){
					if (x >= point4[i].getX() && x <= point4[i].getX()+squarelength&&y >= point4[i].getY() && y <= point4[i].getY()+squarelength  )
					{
	
						dragging = true;
					    dragging = true;
					    point4[i].setoffset(x, y);
	//				    offsetX  = mx - point4[0].getX();
	//				    offsetY = my - point4[0].getY();
					    point4[i].setMoving(true);
	//					System.out.println("good");
					}else{
	//					System.out.println("bad");
					}
				}
			}
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		dragging = false;
		for (int i = 0; i < 4; i++) {
			if (point4[i].isMoving()) {
			    point4[i].setMoving(false);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	boolean dragging = false;
	
	

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (dragging) {
			double mx = e.getX() - biasx;
			double my = e.getY() - biasy;
			for (int i = 0; i < 4; i++) {
				if (point4[i].isMoving()) {
					point4[i].setX(mx - point4[i].getOffsetX());
					point4[i].setY(my - point4[i].getOffsetY());
					repaint();
				}
			}
		}

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
