import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JPanel;

/**
 * 
 * @author Changtai Xiong
 * @version 0.1
 * 
 * 类名: DrawPane
 * 
 * 继承：JPanel类
 * 
 * 接口：MouseListener, MouseMotionListener
 * 
 * 作用: 此类构造出了绘图面板，用于绘制状态图，所以该类是实现图形界面的一个重要部分。
 *
 * 成员：
 *       sclArr     :ArrayList<StateCircleLabel>  ==>  用来存储所有状态圆形（Label）的数组
 *       stlArr     :ArrayList<StateTransLine>    ==>  用来存储所有状态转移线段的数组
 *       stpStk     :Stack<Point>                 ==>  用来记录所有状态转移线段的起点
 *       endStk     :Stack<Point>                 ==>  用来记录所有状态转移线段的终点
 *       tempSc     :StateCircle                  ==>  鼠标当前拖出的状态圆形
 *       stLinePoint:Point                        ==>  鼠标当前拖出的状态转移线段起点
 *       enLinePoint:Point                        ==>  鼠标当前拖出的状态转移线段终点
 *       editState  :int                          ==>  用来表示当前文档的编辑状态：（1:绘制状态; 2:绘制状态转移）
 *       
 * 操作：drawArrow(Graphics2D g2d, StateTransLine li, double size)
 *       >>用来绘制箭头，li表示线段，记录了其起点与终点，size表示线段粗细
 * 
 *       addStateLine(StateCircleLabel s)
 *       >>向stlArr中添加相应线段
 *       
 *       
 * 常数：      
 *       MIN_DIAMETER  :int                       ==>  用来表示状态圆的最小半径
 *       MAX_DIAMETER  :int                       ==>  用来表示状态圆的最大半径
 *       SCL_OFFSET    :int                       ==>  用来表示圆形与按钮之间的边距，防止圆形边界越界
 *       
 */      


public class DrawPane extends JPanel implements MouseListener, MouseMotionListener{
	private ArrayList <StateCircleLabel> sclArr = new ArrayList <StateCircleLabel>();
	private ArrayList <StateTransLine> stlArr = new ArrayList <StateTransLine>(); //store the lines
	private Stack<Point> stpStk = new Stack<Point>(); // store the start point to draw line
	private Stack<Point> enpStk = new Stack<Point>(); // store the end point to draw line
	private Point p = null;
	private StateCircle tempSc = null;
	public static final int MIN_DIAMETER = 100;
	public static final int MAX_DIAMETER = 800;
	public static final int SCL_OFFSET = 20;
	private int editState = 0;

	private int testcnt = 0;
	
	private Point stLinePoint = null;
	private Point enLinePoint = null;
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Color oldColor = g.getColor();
		g.setColor(Color.BLACK);
		int px, py;
		if(tempSc != null)
		{
			px = tempSc.getPoint().x - tempSc.getDiameter()/2;
			py = tempSc.getPoint().y - tempSc.getDiameter()/2;
			g.drawOval(px, py, tempSc.getDiameter(), tempSc.getDiameter());
			
			//绘制圆心（橘色圆心）
			g.setColor(Color.ORANGE);
			px = tempSc.getPoint().x - tempSc.getPointSize()/2;
			py = tempSc.getPoint().y - tempSc.getPointSize()/2;
			g.fillOval(px, py, tempSc.getPointSize(), tempSc.getPointSize());
			g.setColor(oldColor);
		}
		if(stLinePoint != null && enLinePoint != null)
		{
			g.drawLine(stLinePoint.x, stLinePoint.y, enLinePoint.x, enLinePoint.y);
		}
		/*
		g.setColor(Color.RED);
		while(it.hasNext())
		{
			StateCircle sc = it.next();
			px = sc.getPoint().x - sc.getDiameter()/2;
			py = sc.getPoint().y - sc.getDiameter()/2;
			g.drawOval(px, py, sc.getDiameter(), sc.getDiameter());
		}
		*/
		if(stlArr != null)
		{
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			Iterator <StateTransLine> it = stlArr.iterator();
			while(it.hasNext())
			{
				StateTransLine li = it.next();
				//System.out.println("num:" + testcnt++);
				//this.add(stll);
				drawArrow(g2d, li, 15);
			}
		}
		g.setColor(oldColor);
	}
	
	public void drawArrow(Graphics2D g2d, StateTransLine li, double size)
	{
		Point sp = li.getStPoint();
		Point ep = li.getEnPoint();
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
		g2d.drawLine(li.getStPoint().x, li.getStPoint().y, li.getEnPoint().x, li.getEnPoint().y);
	}
	
	public  DrawPane() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setLayout(null);
		/*
		scl = new StateCircleLabel(this);
		scl2 = new StateCircleLabel(this);
		scl.setPreferredSize(new Dimension(30, 30));
		scl.setBounds(0, 0, 30, 30);
		scl2.setPreferredSize(new Dimension(30, 30));
		scl2.setBounds(10, 10, 30, 30);
		this.add(scl);
		this.add(scl2);
		*/
	}

	public int getEditState()
	{
		return editState;
	}
	
	public void setEditState(int es)
	{
		this.editState = es;
	}
	
	public void setStPoint(int x, int y)
	{
		if(null == stLinePoint)
		{
			stLinePoint = new Point(x, y);
		}
		else
		{
			stLinePoint.x = x;
			stLinePoint.y = y;
		}
	}
	
	public void setEnPoint(int x, int y)
	{
		if(null == enLinePoint)
		{
			enLinePoint = new Point(x, y);
		}
		else
		{
			enLinePoint.x = x;
			enLinePoint.y = y;
		}
	}
	
	public void clearCurPoint()
	{
		stLinePoint = null;
		enLinePoint = null;
		repaint();
	}
	
	public void addStateLine(StateCircleLabel s)
	{
		System.out.println("add State Line" + stpStk.isEmpty() + enpStk.isEmpty());
		Point tmpPoint = new Point();
		Iterator <StateCircleLabel> it = sclArr.iterator();
		while(it.hasNext())
		{
			StateCircleLabel scl = it.next();
			if(1 == scl.isMouseEntered())
			{
				tmpPoint = scl.getLocation();
				int width = scl.getWidth();
				StateTransLine stl = new StateTransLine(s, scl);
				stlArr.add(stl);
				Point p1 = stl.getStPoint(), p2 = stl.getEnPoint();
				int sw = Math.abs(p1.x - p2.x), sh = Math.abs(p1.y - p2.y);
				int stX = p1.x < p2.x ? p1.x : p2.x;
				int stY = p1.y < p2.y ? p1.y : p2.y;
				StateTransLineLabel stll = new StateTransLineLabel(stl);
				stll.setSize(sw, sh);
				stll.setBounds(stX, stY, sw, sh);
				//this.add(stll);
				break;
			}
		}
		if(!stpStk.isEmpty())
			stpStk.clear();
		//if(!stpStk.isEmpty() && !enpStk.isEmpty())
		//{
		//	System.out.println("加线啦");
		//	StateTransLine stl = new StateTransLine(stpStk.pop(), enpStk.pop());
		//	this.stlArr.add(stl);
		//}
		//if(!enpStk.isEmpty())
		//	enpStk.clear();
		//if(!stpStk.isEmpty())
		//	stpStk.pop();
	}
	
	public void pushStartPoint(Point p)
	{
		stpStk.push(p);
	}
	
	public void pushEndPoint(Point p)
	{
		System.out.println("加了个终点");
		enpStk.push(p);
	}

	public void popStartPoint()
	{
		if(!stpStk.isEmpty())
			stpStk.pop();
	}
	
	public void popEndPoint()
	{
		System.out.println("为何删了个终点");
		if(!enpStk.isEmpty())
			enpStk.pop();
	}
	
	public void clearComponentOrder(int index)
	{
		int i;
		for(i = 0 ; i < this.getComponentCount() ; i++)
		{
			this.setComponentZOrder(this.getComponent(i), index);
		}
		//this.remove(tmpBtn);
	}
	
	public void addSth()
	{
		StateCircleLabel scl = new StateCircleLabel(sclArr.size(), this, sclArr);
		//this.add(scl);
	}
	
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(editState)
		{
			case EditStateList.EDIT_DRAW_CIRCLE:
			{
				if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
				{
					double dx = e.getX() - p.getX();
					double dy = e.getY() - p.getY();
					int diameter = 2 * (int) Math.sqrt(dx * dx + dy * dy);
					System.out.println(diameter);
					if(null == tempSc)
						tempSc = new StateCircle(p, diameter);
					tempSc.setDiameter(diameter);
					//scl.setSize(diameter, diameter);
					repaint();
				}
			}
			break;
			case EditStateList.EDIT_DRAW_LINE:
			{

			}
			break;
			default:
				break;
		}
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(editState)
		{
			case EditStateList.EDIT_DRAW_CIRCLE:
			{
				
			}
			break;
			case EditStateList.EDIT_DRAW_LINE:
			{
				
			}
			break;
			default:
				
			break;
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Iterator<StateCircleLabel> it = sclArr.iterator();
		System.out.println("label number:" + sclArr.size());
		System.out.println("==================//////?");
		while(it.hasNext())
		{
			StateCircleLabel tmpScl = it.next();
			tmpScl.setUnselected();
		}
		System.out.println("==================//////?");
		repaint();
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		clearComponentOrder(0);
		addSth();
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("editstate: "+editState);
		switch(editState)
		{
			case EditStateList.EDIT_DRAW_CIRCLE:
			{
				if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
				{
					p = new Point(e.getX(), e.getY());
					if(null == tempSc)
						tempSc = new StateCircle(p, 1);
				}
			}
			break;
			case EditStateList.EDIT_DRAW_LINE:
			{
				/*
				Iterator<StateCircleLabel> it = sclArr.iterator();
				System.out.println("==================//////?");
				while(it.hasNext())
				{
					StateCircleLabel tmpScl = it.next();
					if(tmpScl.isSelected() == 1)
					{
						Point curPos = tmpScl.getLocation();
						int tmpRadius = tmpScl.getWidth() / 2;
						stLinePoint = new Point(curPos.x + tmpRadius, curPos.y + tmpRadius);
					}
				}
				*/
			}
			break;
			default:
				System.out.println("UnKnown State.");
			break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(EditStateList.EDIT_DRAW_CIRCLE == editState)
		{
			if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
			{
				tempSc = null;
				double dx = e.getX() - p.getX();
				double dy = e.getY() - p.getY();
				int diameter = 2 * (int) Math.sqrt(dx * dx + dy * dy) + SCL_OFFSET;
				if(diameter >= MIN_DIAMETER)
				{
					StateCircleLabel scl = new StateCircleLabel(sclArr.size(), this, sclArr);
					scl.setSize(diameter, diameter);
					scl.setBounds(p.x - diameter/2, p.y - diameter/2, diameter, diameter);
					this.add(scl);//添加Label
					sclArr.add(scl);//也向数组中添加该Label
				}
				repaint();
			}
		}
	}
	
}
