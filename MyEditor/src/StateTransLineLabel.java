import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class StateTransLineLabel extends JLabel implements MouseListener, MouseMotionListener{

	private StateTransLine li = null;
	
	public StateTransLineLabel() {}
	
	public StateTransLineLabel(StateTransLine li)
	{
		this.li = li;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void drawArrow(Graphics2D g2d, StateTransLine li, double size)
	{
		Point sp = li.getStPoint();
		Point ep = li.getEnPoint();
		
		//+-->>改变标签的大小以配合箭头的移动
		int width = Math.abs(sp.x - ep.x);
		int height = Math.abs(sp.y - ep.y);
		//this.setSize(width, height);
		int sX = sp.x < ep.x ? sp.x : ep.x;
		int sY = sp.y < ep.y ? sp.y : ep.y;
		if(height > 1 && width > 1)
			this.setBounds(sX, sY, width, height);
		//*<<
		
		double c1 = Math.cos(Math.PI/6);
		double s1 = Math.sin(Math.PI/6);
		int ax = (sp.x - ep.x > 0) ? 1 : -1;
		int ay = (sp.y - ep.y > 0) ? 1 : -1;
		double dx = sp.x - ep.x;
		double dy = sp.y - ep.y;
		double dis = Math.sqrt(dx * dx + dy * dy);
		double sk = Math.abs(dy) / dis;
		double ck = Math.abs(dx) / dis;
		double x1 = ep.x + size * ax * (c1*ck - s1*sk);
		double y1 = ep.y + size * ay * (s1*ck + c1*sk);
		double x2 = ep.x + size * ax * (c1*ck + s1*sk);
		double y2 = ep.y + size * ay * (c1*sk - s1*ck);
		int [] x = new int[3];
		int [] y = new int[3];
		x[0] = (int) x1; x[1] = (int) x2; x[2] = ep.x;
		y[0] = (int) y1; y[1] = (int) y2; y[2] = ep.y;
		g2d.fillPolygon(x, y, 3);
		System.out.println(li.getStPoint().x + "," + li.getEnPoint().x + "," +
				li.getStPoint().y + "," + li.getEnPoint().y);
		System.out.println(this.getX());
		System.out.println(this.getY());
		g2d.drawLine(li.getStPoint().x - this.getX(), li.getStPoint().y - this.getY(), 
				li.getEnPoint().x - this.getX(), li.getEnPoint().y - this.getY());
	}
	
	@Override
	public boolean contains(int x, int y) {
		return super.contains(x, y);
	}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		Color oldColor = g2d.getColor();
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(oldColor);
		drawArrow(g2d, li, 15);
		g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
}
