package leo.app.modal;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class Vector {
	public static Point getV(Point start,Point end){//(PQ)
		Point v=new Point(end.x-start.x,end.y-start.y);
		return v;
	}
	public static double innerproduct(Point vector1,Point vector2){
		double inner=vector1.x*vector2.x+vector1.y*vector2.y;
		return inner;
	}
	public static Point add(Point vector1,Point vector2){
		Point add=new Point(vector1.x+vector2.x,vector1.y+vector2.y);
		return add;
	}
	public static Point mutiple(double x,Point vector1){
		Point multiple=new Point(vector1.x*x,vector1.y*x);
		return  multiple;
	}
	public static Point divide(double x,Point vector1){
		Point divide=new Point(vector1.x/x,vector1.y/x);
		return  divide;
	}
	public static double length(Point vector1){
		double length=Math.pow(Math.pow(vector1.x, 2)+Math.pow(vector1.y, 2),0.5);
		return length;
	}
	public static Point perp(Point vector1){//取得的垂直向量皆取y加上附號的，在計算v時，會因為自己的負號而被抵銷
		Point prep=new Point(vector1.y,vector1.x*-1);
		return prep;
	}
	public static Point original_pos(Point p){//轉回圖座標
		p.y=p.y*-1;
//		x=new Point((int)p.x,(int)p.y*-1)
		return p;
//		return ;
	}
	public static Point draw_original_pos(Point p){//圖上顯示而已，轉成int，並且恢復圖座標
		Point x=new Point((int)p.x,(int)p.y*-1);
		return x;
	}
	public static void boundary(Point x,double width,double height){//依然是真實世界座標
		Vector.original_pos(x);//轉回圖座標
		if(x.x<0){
			x.x=0;
		}
		if(x.x>=width){
			x.x=width-1;
		}
		if(x.y<0){
			x.y=0;
		}
		if(x.y>=height){
			x.y=height-1;
		}
//		System.out.println("change="+x);
		//if(x)
		//return new Point(0,0);
	}
	public static double[] bilinear_interpolation(Mat m,Point x,double width,double height){//圖座標嚕~~
		System.out.println("x="+x);
		double color[]=new double[3];
		Point lowxy =new Point((int)x.x,(int)x.y);
		Point topxy =new Point((int)(x.x+1),(int)(x.y+1));
//		System.out.println("x="+x);
//		System.out.println("top="+topxy);
//		System.out.println("down="+lowxy);
		double a= x.x-lowxy.x;//剛好是比例
		double b= x.y-lowxy.y;//剛好是比例
		if(topxy.x>=width-1){
			topxy.x=width-1;
		}
		if(topxy.y>=height-1){
			topxy.y=height-1;
		}
//		System.out.println("a="+a);
//		System.out.println("b="+b);
//		System.out.println("top="+topxy);
//		System.out.println("down="+lowxy);
//		System.out.println(m.size());
		double lefttop[]=m.get((int)lowxy.y, (int)lowxy.x);
		double righttop[]=m.get((int)topxy.y, (int)lowxy.x);
		double leftdown[]=m.get((int)lowxy.y, (int)topxy.x);
		double rightdown[]=m.get((int)topxy.y, (int)topxy.x);
//		System.out.println((int)lowxy.x+","+ (int)lowxy.y);
//		System.out.println((int)lowxy.x+","+ (int)lowxy.y);
//		System.out.println(lefttop[0]+"~"+lefttop[1]+"~"+lefttop[2]);
//		System.out.println(righttop[0]+"~"+righttop[1]+"~"+righttop[2]);
//		System.out.println(leftdown[0]+"~"+leftdown[1]+"~"+leftdown[2]);
//		System.out.println(rightdown[0]+"~"+rightdown[1]+"~"+rightdown[2]);
		for(int i=0;i<3;i++){
			
			color[i]=(1-a)*(1-b)*lefttop[i]+a*(1-b)*righttop[i]+(1-a)*b*leftdown[i]+a*b*rightdown[i];
		}
//		System.out.println(color[0]+"~"+color[1]+"~"+color[2]);
		return color;
	}
}
