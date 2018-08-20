package bezierCurve;

public class controlpoint extends point{

	private boolean isMoving=false;
	private double  offsetX;
	private double  offsetY;

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	public void setoffset(double x,double y){
		offsetX  = x-this.getX() ;
	    offsetY = y-this.getY();
	}
	public double getOffsetX() {
		return offsetX;
	}
	public double getOffsetY() {
		return offsetY;
	}
	public boolean isMoving() {
		return isMoving;
	}
	
	
}
