import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;


/**
 * 
 * @author Changtai Xiong
 * @version 0.1
 * 
 * 类名: StateCircleLabel
 * 
 * 继承：JPanel类
 * 
 * 接口：MouseListener, MouseMotionListener
 * 
 * 作用: 此类构造出了状态圆形的具体构型，继承自Label，以响应鼠标的事件。
 *
 * 成员：
 * 		 movingFlag    :int    ==>  记录了该状态圆形是否处于被移动的状态
 *       selectedFlag  :int    ==>  记录了该状态圆形是否处于被选中的状态
 *       canResizeFlag :int    ==>  记录了该状态圆形是否能够进行缩放操作
 *       resizingFlag  :int    ==>  记录了该状态圆形是否正处于被缩放的状态
 *       stPoint       :Point  ==>  当鼠标在该状态内拖动时的状态转移线段起点
 *       enPoint       :Point  ==>  当鼠标在该状态内拖动时的状态转移线段终点
 *       
 * 操作：
 *		（(⊙o⊙)…肚子叫我了，不想写了）
 * 
 * 
 * 摘要：
 * 		重载后效果好多了，元芳，你怎么看？		——2012.12.17
 *       
 * 常数：      
 *       RESIZE_RECT_EDGE  :int                       ==>  用来表示更改大小时边界框上的正方形的边长
 *        
 */   

public class StateCircleLabel extends JLabel implements MouseListener, MouseMotionListener{

	private int index;
	private static DrawPane dp = null;
	private Point point = null;
	private int movingFlag = 0;
	private int selectedFlag = 0;
	private int mouseEnteredFlag = 0;
	private int canResizeFlag = 0;
	private int resizingFlag = 0;
	private static ArrayList<StateCircleLabel> sclArr = null;
	private StateCircle sc = null;
	public StateCircleLabel() {}
	private Point stPoint = null;
	private Point enPoint = null;
	private static int RESIZE_RECT_EDGE = 10;
	private Rectangle [] resizeRects = new Rectangle[4];
	public StateCircleLabel(int index, DrawPane dp, ArrayList<StateCircleLabel> arr)
	{
		this.dp = dp;
		this.index = index;
		this.sc = new StateCircle(new Point(DrawPane.SCL_OFFSET, DrawPane.SCL_OFFSET), getWidth()-2*DrawPane.SCL_OFFSET);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.sclArr = arr;
		Iterator<StateCircleLabel> it = sclArr.iterator();
		while(it.hasNext())
		{
			it.next().setUnselected();
		}
		this.setSelected();
		dp.repaint();
	}
	public void initResizeRects()
	{
		resizeRects[0] = new Rectangle(DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2, DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2,
				RESIZE_RECT_EDGE, RESIZE_RECT_EDGE);

		resizeRects[1] = new Rectangle(this.getWidth() - DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2, DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2,
				RESIZE_RECT_EDGE, RESIZE_RECT_EDGE);
		
		resizeRects[2] = new Rectangle(DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2, this.getWidth() - DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2,
				RESIZE_RECT_EDGE, RESIZE_RECT_EDGE);
		
		resizeRects[3] = new Rectangle(this.getWidth() - DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2, this.getWidth() - DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2,
				RESIZE_RECT_EDGE, RESIZE_RECT_EDGE);
	}
	
	public void updateResizeRects()
	{
		int i;
		for(i = 0 ; i < 4 ; i++)
		{
			resizeRects[i].width = RESIZE_RECT_EDGE;
			resizeRects[i].height = RESIZE_RECT_EDGE;
		}
		resizeRects[0].x = DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
		resizeRects[0].y = DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
		
		resizeRects[1].x = this.getWidth() - DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
		resizeRects[1].y = DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
		
		resizeRects[2].x = DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
		resizeRects[2].y = this.getWidth() - DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
		
		resizeRects[3].x = this.getWidth() - DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
		resizeRects[3].y = this.getWidth() - DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
	}
	
	public void setSelected()
	{
		selectedFlag = 1;
	}
	
	public void setUnselected()
	{
		selectedFlag = 0;
	}
	
	public int isSelected()
	{
		return selectedFlag;
	}
	
	public int isMouseEntered()
	{
		return this.mouseEnteredFlag;
	}
	
	@Override
	public boolean contains(int x, int y) {
		//Point p = sc.getPoint();
		System.out.println(canResizeFlag);
		if(0 == canResizeFlag)
		{
			double dx = x - this.getWidth()/2;
			double dy = y - this.getWidth()/2;
			double dis = Math.sqrt(dx*dx + dy*dy);
			return dis < this.getWidth() / 2 - DrawPane.SCL_OFFSET/2;
		}
		else
		{
			return super.contains(x, y);
		}
			//return dis > sc.getDiameter() / 2;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		Stroke stroke = null;
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		Color oldColor = g.getColor();
		g.setColor(Color.WHITE);
		g.fillOval(DrawPane.SCL_OFFSET, DrawPane.SCL_OFFSET, getWidth()-2*DrawPane.SCL_OFFSET, getHeight()-2*DrawPane.SCL_OFFSET);
		if(selectedFlag == 0)
		{
			if(EditStateList.EDIT_DRAW_LINE == dp.getEditState() && mouseEnteredFlag == 1)
				g.setColor(Color.ORANGE);
			else
			{
				g.setColor(Color.BLACK);
			}
			g.drawOval(DrawPane.SCL_OFFSET, DrawPane.SCL_OFFSET, getWidth()-2*DrawPane.SCL_OFFSET, getHeight()-2*DrawPane.SCL_OFFSET);
			g.setColor(oldColor);
		}
		else 
		{
			g2d.setColor(Color.BLUE);
			stroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
			g2d.drawOval(DrawPane.SCL_OFFSET, DrawPane.SCL_OFFSET, getWidth()-2*DrawPane.SCL_OFFSET, getHeight()-2*DrawPane.SCL_OFFSET);
			if(EditStateList.EDIT_DRAW_CIRCLE == dp.getEditState())
				g2d.setColor(Color.ORANGE);
			else if(EditStateList.EDIT_DRAW_LINE == dp.getEditState())
				g2d.setColor(Color.GREEN);
			int sp = (getWidth() - sc.getPointSize()) / 2 + 1; 
			g2d.fillOval(sp, sp, sc.getPointSize(), sc.getPointSize());
			g2d.setStroke(stroke);
			g2d.setColor(Color.RED);
			if(1 == canResizeFlag)
			{
				stroke = g2d.getStroke();
				g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.drawRect(DrawPane.SCL_OFFSET-1, DrawPane.SCL_OFFSET-1, getWidth()-2*DrawPane.SCL_OFFSET+2, getWidth()-2*DrawPane.SCL_OFFSET+2);
				//int x1 = DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
				//int y1 = DrawPane.SCL_OFFSET - RESIZE_RECT_EDGE/2;
				//g2d.drawRect(x1, y1, RESIZE_RECT_EDGE, RESIZE_RECT_EDGE);
				for(int i = 0 ; i < 3 ; i++)
				{
					g2d.setColor(Color.RED);
					g2d.drawRect(resizeRects[i].x, resizeRects[i].y, resizeRects[i].width, resizeRects[i].height);
					g2d.setColor(Color.YELLOW);
					g2d.fillRect(resizeRects[i].x+1, resizeRects[i].y+1, resizeRects[i].width-1, resizeRects[i].height-1);
					//System.out.println(resizeRects[i].x+", " + resizeRects[i].y);
				}
				g2d.setColor(new Color(0, 238, 118));
				g2d.drawRect(resizeRects[3].x, resizeRects[3].y, resizeRects[3].width, resizeRects[3].height);
				g2d.setColor(new Color(10, 118, 238));
				g2d.fillRect(resizeRects[3].x+1, resizeRects[3].y+1, resizeRects[3].width-1, resizeRects[3].height-1);
				g2d.setColor(oldColor);
			}
			g2d.setColor(oldColor);
		}	
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(2 == e.getClickCount())
		{
			//System.out.println("--v--");
			initResizeRects();
			canResizeFlag = 1 - canResizeFlag;
			repaint();
		}	
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		dp.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		mouseEnteredFlag = 1;
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		dp.setCursor(Cursor.getDefaultCursor());
		mouseEnteredFlag = 0;
		canResizeFlag = 0;
		repaint();
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
		{
			movingFlag = 1;
			point = e.getPoint();
			//this.setComponentZOrder(this, 2);
			dp.setComponentZOrder(this, 0);
			Iterator<StateCircleLabel> it = sclArr.iterator();
			while(it.hasNext())
			{
				it.next().setUnselected();
			}
			this.setSelected();
			dp.repaint();
		}
		switch(dp.getEditState())
		{
			case EditStateList.EDIT_DRAW_CIRCLE:
			{
				
			}
			case EditStateList.EDIT_DRAW_LINE:
			{
				Point curPos = this.getLocation();
				int tmpRadius = this.getWidth() / 2;
				int sp = (getWidth() - sc.getPointSize()) / 2 + 1; 
				if(null == stPoint)
					stPoint = new Point(sp, sp);
				else
				{
					stPoint.x = sp;
					stPoint.y = sp;
				}
				//dp.popEndPoint();
				dp.pushStartPoint(new Point(curPos.x + tmpRadius, curPos.y + tmpRadius));
				dp.setStPoint(curPos.x + tmpRadius, curPos.y + tmpRadius);
			}
			break;
			default:
				System.out.println("Unknow State");
			break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(dp.getEditState())
		{
			case EditStateList.EDIT_DRAW_CIRCLE:
			{
				if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
				{
					movingFlag = 0;
				}
			}
			break;
			case EditStateList.EDIT_DRAW_LINE:
			{
				//System.out.println("en...You released mouse");
				//Here we should add the state transform line
				dp.addStateLine(this);
				dp.clearCurPoint();
			}
			break;
			default:
				System.out.println("Unknow State");
			break;
		}
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(1 == canResizeFlag && resizingFlag > 0)
		{
			int dx, dy, dist = 0, w = this.getWidth();
			dx = e.getPoint().x - resizeRects[3].x;
			dy = e.getPoint().y - resizeRects[3].y;
			dist = (int)(Math.sqrt((dx*dx + dy*dy)/2));
			if(dx < 0 && dy < 0 && w - dist > DrawPane.MIN_DIAMETER)
			{
				this.setSize(w - dist, w - dist);
			}
			else if(dx > 0 && dy > 0 && w + dist < DrawPane.MAX_DIAMETER)
			{
				System.out.println("oh my god" +( w+dist));
				this.setSize(w + dist, w + dist);
			}
			updateResizeRects();
			repaint();
			dp.repaint();
		}
		else
		{
			switch(dp.getEditState())
			{
				case EditStateList.EDIT_DRAW_CIRCLE:
				{
					if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
					{
						int w = this.getWidth();
						int h = this.getHeight();
						
						Point p = this.getLocation();
						if(1 == movingFlag)
							this.setLocation(e.getX() - point.x + p.x /*- w/2*/, e.getY() - point.y + p.y /*- h/2*/);
						dp.repaint();
					}
				}
				break;
				case EditStateList.EDIT_DRAW_LINE:
				{
					System.out.println("en...You are dragging! x:" + e.getX() + ", y:" + e.getY());
					if(null == enPoint)
						enPoint = new Point(e.getPoint());
					else
					{
						enPoint.x = e.getX();
						enPoint.y = e.getY();
					}
					repaint();
					Point curPos = this.getLocation();
					int tmpRadius = this.getWidth() / 2;
					//dp.pushEndPoint(new Point(curPos.x + dx, curPos.y + dy));
					dp.setEnPoint(curPos.x + e.getX(), curPos.y + e.getY());
					dp.repaint();
				}
				break;
				default:
					System.out.println("Unknow State");
				break;
			}
		}
		
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int i, flag = 0;
		/*
		for(i = 0 ; i < 4 ; i++)
		{
			if(resizeRects[i] != null && resizeRects[i].contains(e.getPoint()))
			{
				flag = i+1;
				break;
			}
		}
		*/
		if(resizeRects[3] != null && resizeRects[3].contains(e.getPoint()))
		{
			flag = 4;
		}
		if(flag > 0)
		{
			dp.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			resizingFlag = flag;
		}
		else
		{
			dp.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			resizingFlag = 0;
		}
	}
}
