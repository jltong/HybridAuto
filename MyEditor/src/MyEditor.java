import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;


/**
 * 
 * @author Changtai Xiong
 * @version 0.1
 * 
 * 类名: MyEditor
 * 
 * 继承：JFrame类
 * 
 * 作用: 主窗口
 *
 * 成员：
 *       太多了
 * 
 *
 */


public class MyEditor extends JFrame{
	
	private static Dimension dim = null;
	private static JMenuBar menuBar = null;
	private static JMenu menu1 = null;//文件
	private static JMenuItem mItem1 = null; //新建
	private static JMenuItem mItem2 = null; //打开
	private static JMenuItem mItem3 = null; //保存
	private static JTabbedPane tabbedPane = null;
	private static JToolBar toolBar = null;
	private static ImageButton button1 = null;
	private static ImageButton button2 = null;
	private static ImageButton button3 = null;
	private static ImageButton button4 = null;
	private static String curPath = "";
	private static MyEditor _editor = null;
	private ArrayList <DrawPane> dpArr = new ArrayList<DrawPane>();
	private int editState = 0;
	private int tabNumber = 0;
	private int curTabIndex = -1;
	public MyEditor() {}
	public MyEditor(int width, int height)
	{
		initParameters();
		initFrame(width, height);
		initComponentEvent();

	}
	
	private void initParameters()
	{
		dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		File blankFile = new File("");
		try {
			curPath = blankFile.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initFrame(int w, int h)
	{
		_editor = this;
		this.setBounds((int)(dim.getWidth() - w) / 2, (int)(dim.getHeight() - h) / 2, w, h);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new MyMenuBar();
		menu1 = new JMenu("文件");
		mItem1 = new JMenuItem("新建");
		mItem2 = new JMenuItem("打开");
		mItem3 = new JMenuItem("保存");
		menu1.add(mItem1);
		menu1.add(mItem2);
		menu1.add(mItem3);
		menuBar.add(menu1);
		this.setJMenuBar(menuBar);
		this.getContentPane().setLayout(new BorderLayout());
		toolBar = new JToolBar();
		button1 = new ImageButton(curPath+"\\image\\CreateState.png", 30, 30);
		button2 = new ImageButton(curPath+"\\image\\CreateVar.png", 30, 30);
		button3 = new ImageButton(curPath+"\\image\\CreatePath.png", 30, 30);
		button4 = new ImageButton(curPath+"\\image\\CreateBounds.png", 30, 30);
		toolBar.add(button1);
		toolBar.add(button2);
		toolBar.add(button3);
		toolBar.add(button4);
		toolBar.addSeparator();
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int tabIndex = tabbedPane.getUI().tabForCoordinate(tabbedPane, e.getX(), e.getY());
				if(tabIndex < 0)
					return;
				System.out.println(tabIndex);
				if(tabbedPane.getIconAt(tabIndex) != null)
				{
					curTabIndex = tabIndex;
					Rectangle rect = ((CloseTabIcon)tabbedPane.getIconAt(tabIndex)).getBounds();
					if(rect.contains(e.getX(), e.getY()))
					{
						int res = JOptionPane.showConfirmDialog(null, "是否保存已经修改的内容？");
						if(JOptionPane.YES_OPTION == res)
						{
							//show save dialog;
						}
						else if(JOptionPane.CANCEL_OPTION == res)
						{
							return;
						}
						tabbedPane.removeTabAt(tabIndex);
						curTabIndex--;
					}
				}
				Iterator<DrawPane> it = dpArr.iterator();
				while(it.hasNext())
				{
					it.next().clearComponentOrder(1);
				}
				JScrollPane jp = (JScrollPane)tabbedPane.getSelectedComponent();
				DrawPane curdp = (DrawPane)jp.getViewport().getView();
				//curdp.clearComponentOrder(0);
				curdp.addSth();
				repaint();
			}
			
		});
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		//JScrollPane scrollp2 = new JScrollPane(pane);
		
		//tabbedPane.addTab("Untitled2", scrollp2);
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}
	
	public int getEditState()
	{
		return editState;
	}
	
	private void initComponentEvent()
	{
		mItem1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//JPanel pane = new JPanel();
				DrawPane pane = new DrawPane();
				dpArr.add(pane);
				pane.setPreferredSize(new Dimension(1000, 1000));
				pane.setBackground(Color.WHITE);
				JScrollPane scrollp = new JScrollPane(pane);
				CloseTabIcon tci = new CloseTabIcon();
				tabbedPane.addTab("Untitled"+(++tabNumber), tci ,scrollp);
				tabbedPane.repaint();
				curTabIndex++;
			}
			
		});
		
		mItem2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogType(FileDialog.LOAD);
				/*
				jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {
					
					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "*.xml";
					}
					
					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						if (f.isDirectory())return true;
						return f.getName().endsWith(".xml");
					}
				});
				*/
				int option = jfc.showOpenDialog(null);
				if(JFileChooser.APPROVE_OPTION == option)
				{
					if(jfc.getSelectedFile() != null)
						System.out.println(jfc.getSelectedFile());
					//do the load function...
				}
			}
			
		});
		
		
		mItem3.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogType(FileDialog.SAVE);
				int option = jfc.showSaveDialog(null);
				if(JFileChooser.APPROVE_OPTION == option)
				{
					if(jfc.getSelectedFile() != null)
						System.out.println(jfc.getSelectedFile());
					//do the load function...
				}
			}
			
		});
		
		button1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				editState = 0;
				if(curTabIndex >= 0)
				{
					JScrollPane jp = (JScrollPane)tabbedPane.getSelectedComponent();
					System.out.println("index:"+curTabIndex);
					DrawPane curdp = (DrawPane)jp.getViewport().getView();
					curdp.setEditState(0);
					System.out.println("!");
					repaint();
				}
			}
		});
		
		button2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				editState = 1;
				if(curTabIndex >= 0)
				{
					System.out.println("index:"+curTabIndex);
					JScrollPane jp = (JScrollPane)tabbedPane.getSelectedComponent();
					DrawPane curdp = (DrawPane)jp.getViewport().getView();
					curdp.setEditState(1);
					System.out.println("?");
					repaint();
				}
			}
		});
	}
}
