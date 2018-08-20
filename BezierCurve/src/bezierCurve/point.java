package bezierCurve;

public class point {
	private double x;
	private double y;
	public point(){
		this.x=-1;
		this.y=-1;
	}
	public point(double x,double y){
		this.x=x;
		this.y=y;
	}
	public static point middlepoint(point a,point b){
		point middle=new point();
		middle.setX((a.getX()+b.getX())/2);
		middle.setY((a.getY()+b.getY())/2);
		return middle;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	
}
