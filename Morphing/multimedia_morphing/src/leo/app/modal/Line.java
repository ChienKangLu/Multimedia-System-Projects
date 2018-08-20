package leo.app.modal;

import org.opencv.core.Point;

public class Line {
	private Point P_first;//P
	private Point Q_second;//Q
	private Point Middle;//線段中點
	private double degree;
	private double length;
	
	public Line(Point first, Point second) {
		this.P_first = first;
		this.Q_second = second;
	
		length=Vector.length(Vector.getV(P_first, Q_second));
		
	}
	public Line(){
		
	}
	public Point getMiddle() {
		return Middle;
	}
	public void setMiddle(Point middle) {
		Middle = middle;
	}
	public double getDegree() {
		return degree;
	}
	public void setDegree(double degree) {
		this.degree = degree;
	}
	public Point getP_First() {
		return P_first;
	}
	public void setP_First(Point first) {
		this.P_first = first;
	}
	public Point getQ_Second() {
		return Q_second;
	}
	public void setQ_Second(Point second) {
		this.Q_second = second;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+P_first.toString()+","+Q_second.toString()+"]";
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	
	/*void Line::PQtoMLD()
{
	//CvPoint2D32f tmpP=cvPointTo32f(P);
	//CvPoint2D32f tmpQ=cvPointTo32f(Q);
	M.x=(P.x+Q.x)/2;
	M.y=(P.y+Q.y)/2;
	
	float tmpx = Q.x - P.x;
	float tmpy = Q.y - P.y;
	
	len=sqrt( tmpx*tmpx + tmpy*tmpy );
	degree=atan2(tmpy,tmpx);
	return;
}*/
	public void PQ2MiddleDegreeLength(){
		this.Middle=new Point((this.P_first.x+this.Q_second.x)/2,(this.P_first.y+this.Q_second.y)/2);
		double tmpx = Q_second.x - P_first.x;
		double tmpy = Q_second.y - P_first.y;
		this.degree=Math.atan2(tmpy,tmpx);
		this.length=Vector.length(Vector.getV(this.P_first, this.Q_second));
//		System.out.println("radius="+degree);
//		System.out.println("degree="+Math.toDegrees(degree));
//		System.out.println("length="+length);
		
	}
	/*void Line::MLDtoPQ()
{
	float tmpx=0.5*len*cos(degree);
	float tmpy=0.5*len*sin(degree);

	CvPoint2D32f tmpP;
	CvPoint2D32f tmpQ;
	tmpP.x = M.x - tmpx;
	tmpP.y = M.y - tmpy;
	tmpQ.x = M.x + tmpx;
	tmpQ.y = M.y + tmpy;
	
	P = tmpP ;
	Q = tmpQ ;
	return;
}*/
	public void MiddleDegreeLength2PQ(){
		double tmpx=0.5*length*Math.cos(degree);
		double tmpy=0.5*length*Math.sin(degree);
		Point P=new Point(this.Middle.x-tmpx,this.Middle.y-tmpy);
		Point Q=new Point(this.Middle.x+tmpx,this.Middle.y+tmpy);
		this.P_first=P;
		this.Q_second=Q;
	}
	
}
