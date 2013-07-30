import java.awt.Point;

import javax.swing.JOptionPane;


/**
 * 
 * @author Changtai Xiong
 * @version 0.1
 * 
 * 类名: StateYransLine
 * 
 * 继承：JPanel类
 * 
 * 接口：MouseListener, MouseMotionListener
 * 
 * 作用: 此类是状态转移线段的抽象模型，即记录了线段的起点与终点等。
 *
 *        
 */   

public class StateTransLine {
	
	private StateCircleLabel scl1 = null;
	private StateCircleLabel scl2 = null;
	private double xk = 0;
	private double yk = 0;
	private double k = 0;
	public StateTransLine()
	{
		
	}
	public StateTransLine(StateCircleLabel s1, StateCircleLabel s2)
	{
		this.scl1 = s1;
		this.scl2 = s2;
	}
	private void updateOffset()
	{
		Point st = new Point(scl1.getLocation().x + scl1.getWidth()/2, scl1.getLocation().y + scl1.getWidth()/2);
		Point en = new Point(scl2.getLocation().x + scl2.getWidth()/2, scl2.getLocation().y + scl2.getWidth()/2);
		double dx = st.x - en.x;
		double dy = st.y - en.y;
		k = 0;
		//JOptionPane.showConfirmDialog(null, "dx: " + dx);
		if(Math.abs(dx) > 10E-8)
		{
			k = dy / dx;
			xk = Math.sqrt(1 / (k*k+1));
			System.out.println("k: " + k);
			yk = Math.abs(xk * k);
			xk *= (dx < 0 ? 1 : -1);
			yk *= (dy < 0 ? 1 : -1);
		}
	}
	public Point getStPoint()
	{
		updateOffset();
		double r = scl1.getWidth() / 2 - DrawPane.SCL_OFFSET;
		return new Point(scl1.getLocation().x + scl1.getWidth()/2 + (int)(r * xk),
			scl1.getLocation().y + scl1.getWidth()/2 + (int)(r * yk));
	}
	public Point getEnPoint()
	{
		updateOffset();
		double r = scl2.getWidth() / 2 - DrawPane.SCL_OFFSET;
		return new Point(scl2.getLocation().x + scl2.getWidth()/2 - (int)(r * xk), 
			scl2.getLocation().y + scl2.getWidth()/2 - (int)(r * yk));
	}
}
