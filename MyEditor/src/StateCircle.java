import java.awt.Point;


/**
 * 
 * @author Changtai Xiong
 * @version 0.1
 * 
 * 类名: StateCircle
 * 
 * 作用: 记录了状态圆形的信息（半径，圆心，圆心绘制时的半径大小等）
 *
 * 成员：
 *      （详见代码）
 *       
 */     

public class StateCircle {
	private Point point = null;
	private int pointSize = 10;
	private double dDiameter = 0;
	private int iDiameter = 0;
	
	public StateCircle() {}
	
	public StateCircle(Point p, int d)
	{
		setPoint(p);
		setDiameter(d);
	}
	
	public StateCircle(Point p, double d)
	{
		setPoint(p);
		setDiameter(d);
	}

	public double getDiameterByDouble() {
		return dDiameter;
	}

	public void setDiameter(double dDiameter) {
		this.dDiameter = dDiameter;
	}

	public int getDiameter() {
		return iDiameter;
	}

	public void setDiameter(int iDiameter) {
		this.iDiameter = iDiameter;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getPointSize() {
		return pointSize;
	}

	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
	}
}
