package leo.app.modal;

import java.util.ArrayList;

import org.opencv.core.Point;

import leo.app.controller;

public class Line_pair {
	private Line LineS;
	private Line LineD;
	private ArrayList<Line> wrapline=new ArrayList<>();
	public Line_pair(Line lineS, Line lineD) {
		LineS = lineS;
		LineD = lineD;
	}
	
	public ArrayList<Line> getWrapline() {
		return wrapline;
	}

	public Line getLineS() {
		return LineS;
	}
	public void setLineS(Line lineS) {
		LineS = lineS;
	}
	public Line getLineD() {
		return LineD;
	}
	public void setLineD(Line lineD) {
		LineD = lineD;
	}
	public double calculate_u(Point x,int version,int frame){
		Line now=null;
		if(version==0){
			now=wrapline.get(frame);
		}else{
//			now=LineS;
			now=wrapline.get(frame);
		}
		Point PQvecter=Vector.getV(now.getP_First(), now.getQ_Second());
		Point PXvecter=Vector.getV(now.getP_First(), x);
		double inner=Vector.innerproduct(PQvecter, PXvecter);
//		System.out.println("inner_"+inner);
//		System.out.println(Math.pow(Vector.length(PQvecter),2));
		double u=inner/Math.pow(Vector.length(PQvecter),2);
		return u;
	}
	public double calculate_v(Point x,int version,int frame){
		Line now=null;
		if(version==0){
//			now=LineD;
			now=wrapline.get(frame);
		}else{
//			now=LineS;
			now=wrapline.get(frame);
		}
		Point PXvecter=Vector.getV(now.getP_First(), x);
		Point PQvecter=Vector.getV(now.getP_First(), now.getQ_Second());
		double inner =Vector.innerproduct(PXvecter, Vector.perp(PQvecter));
		double v=inner/Vector.length(PQvecter);
		return v;
	}
	public Point getNewX(double u,double v,int version){
		Line now=null;
		if(version==0){
			now=LineS;
		}else{
			now=LineD;// pairs[i].rightLine
		}
		Point PQvecter=Vector.getV(now.getP_First(), now.getQ_Second());
//		System.out.println("PQvecter="+PQvecter);
		Point a=Vector.add(now.getP_First(), Vector.mutiple(u, PQvecter));
//		System.out.println("LineS.getP_First()="+LineS.getP_First());
//		System.out.println("Vector.mutiple(u, PQvecter)="+Vector.mutiple(u, PQvecter));
//		System.out.println("a="+a);
		Point b=Vector.divide(Vector.length(PQvecter),Vector.mutiple(v, Vector.perp(PQvecter)));
//		System.out.println("b="+b);
		Point newX=Vector.add(a,b); 
		return newX;
//		return a;
	}
	
	/*
	 * (1).如果0 < u <1 ,distance的值就是abs(v)
     * (2).如果 u < 0 ,distance的值就是點X’到P’的距離
     * (3).如果 u > 1 ,distance的值就是點X’到Q’的距離
     * 
     * */
	public double getWeight(Point x,double a,double b,double p,int version,int frame){
			Line now=null;
			if(version==0){
//				now=LineS;			
				now=wrapline.get(frame);
			}else{
//				now=LineD;			
				now=wrapline.get(frame);
			}
		
		   double d = 0.0;
		   double u = calculate_u(x,version,frame);//用u來判斷
		   if(u > 1.0 )
		       d = Vector.length(Vector.getV(x, now.getQ_Second()));
		   else if(u < 0)
		       d = Vector.length(Vector.getV(x, now.getP_First()));
		   else
		       d = calculate_v(x,version,frame);
		   
		   double weight = Math.pow( Math.pow(now.getLength(),p)/(a +  d) , b);
		return weight;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+LineS.toString()+","+LineD.toString()+"]";
	}

	public void calculateWrapLine(){
		while(LineS.getDegree()-LineD.getDegree()>3.14159265)
			LineD.setDegree(LineD.getDegree()+3.141596);
		while(LineD.getDegree()-LineS.getDegree()>3.14159265)
			LineS.setDegree(LineS.getDegree()+3.141596);
		for(int i=0;i<controller.frame_number;i++)
		{
			double ratio=(double)(i+1)/(controller.frame_number+1);
			Line curLine=new Line();
			
			curLine.setMiddle(new Point((1-ratio)*LineS.getMiddle().x + ratio*LineD.getMiddle().x,(1-ratio)*LineS.getMiddle().y + ratio*LineD.getMiddle().y));
			curLine.setLength((1-ratio)*LineS.getLength() + ratio*LineD.getLength());
			curLine.setDegree((1-ratio)*LineS.getDegree() + ratio*LineD.getDegree());
			curLine.MiddleDegreeLength2PQ();
			wrapline.add(curLine);
//			System.out.println("a"+i+"="+curLine);
		}
//		System.out.println("calculateWrapLine");
	}
	
	
	
}
