/**
 * 
 * @author 王璐
 * @version 0.1
 * 
 * 类名: Variable
 * 
 * 继承：JFrame类
 * 
 * 
 * 作用: 管理变量重置表，并对变量重置的格式进行约束
 *
 * 成员：
 *       list     	:JList<String>				列表控件，列出变量重置表
 *       listModel	:DefaultListModel<String>	管理变量内容
 *       editButton	:JButton					按钮控件，点击以编辑变量重置
 *       addButton	:JButton					按钮控件，点击以添加变量重置
 *       delButton	:JButton					按钮控件，点击以删除变量重置
 *
 *       
 * 操作：private boolean isVariableFormat(String variable)
 * 		检测变量是否符合格式要求
 */      

import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

import javax.swing.*;

public class Reset extends JFrame {

	//维护表
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	//列表控件
	private JList<String> list = new JList<String>(listModel);
	
	private JButton addButton = new JButton("添加");
	private JButton delButton = new JButton("删除");
	private JButton editButton = new JButton("编辑");
	
	public Reset() {
		//设置选择模式
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayout(new BorderLayout());
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(editButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		setTitle("变量重置表");
		setSize(200, 500);
		setLocationRelativeTo(null);
		
		//添加
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String variable = JOptionPane.showInputDialog("输入新变量重置").trim();
				//检查是否已存在
				if (listModel.contains(variable)) {
					JOptionPane.showMessageDialog(null, "变量重置" + variable + "已存在",
							"变量重置重复", JOptionPane.ERROR_MESSAGE);
				}
				//检查格式是否正确
				else if (!isResetFormat(variable)) {
					JOptionPane.showMessageDialog(null, "变量重置" + variable + "格式错误",
							"格式错误", JOptionPane.ERROR_MESSAGE);
				}
				else {
					listModel.addElement(variable);
				}
			}
		});
		
		
		//删除
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index < 0) {
					JOptionPane.showMessageDialog(null, "未选定任何变量重置",
							"删除错误", JOptionPane.ERROR_MESSAGE);
				}
				else {
					listModel.remove(index);
				}
			}
		});
		
		//编辑变量
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				//未选中
				if (index < 0) {
					JOptionPane.showMessageDialog(null, "未选定任何变量重置",
							"编辑错误", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String variable = JOptionPane.showInputDialog(
							"修改变量重置", listModel.get(index)).trim();
					//与原值相等
					if (variable.equals(listModel.get(index))) {}
					//重复
					else if (listModel.contains(variable)) {
						JOptionPane.showMessageDialog(null, "变量重置" + variable + "已存在",
								"变量重置重复", JOptionPane.ERROR_MESSAGE);
					}
					//格式错误
					else if (!isResetFormat(variable)) {
						JOptionPane.showMessageDialog(null, "变量重置" + variable + "格式错误",
								"格式错误", JOptionPane.ERROR_MESSAGE);
					}
					//修改
					else {
						listModel.set(index, variable);
					}
				}
			}
		});
	}

	//检测变量重置的格式是否正确
	private boolean isResetFormat(String variable) {
		Pattern pattern = Pattern.compile("[a-zA-Z_]+\\w*\\s*=\\s*\\d+");
		Matcher matcher = pattern.matcher(variable);
		return matcher.matches();
	}

	public static void main(String[] args) {
		Reset r = new Reset();
		r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		r.setVisible(true);
	}

}
