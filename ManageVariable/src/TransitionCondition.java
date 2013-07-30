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
 * 作用: 管理转移条件表，并对转移条件的格式进行约束
 *
 * 成员：
 *       list     	:JList<String>				列表控件，列出转移条件表
 *       listModel	:DefaultListModel<String>	管理变量内容
 *       editButton	:JButton					按钮控件，点击以编辑转移条件
 *       addButton	:JButton					按钮控件，点击以添加转移条件
 *       delButton	:JButton					按钮控件，点击以删除转移条件
 *
 *       
 * 操作：private boolean isVariableFormat(String variable)
 * 		检测转移条件是否符合格式要求
 */      


import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

import javax.swing.*;

public class TransitionCondition extends JFrame {

	//维护表
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	//列表控件
	private JList<String> list = new JList<String>(listModel);
	
	private JButton addButton = new JButton("添加");
	private JButton delButton = new JButton("删除");
	private JButton editButton = new JButton("编辑");
	
	public TransitionCondition() {
		//设置选择模式
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayout(new BorderLayout());
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(editButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		setTitle("转移条件表");
		setSize(200, 500);
		setLocationRelativeTo(null);
		
		//添加
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String variable = JOptionPane.showInputDialog("输入新转移条件").trim();
				//检查是否已存在
				if (listModel.contains(variable)) {
					JOptionPane.showMessageDialog(null, "转移条件" + variable + "已存在",
							"转移条件重复", JOptionPane.ERROR_MESSAGE);
				}
				//检查格式是否正确
				else if (!isTransitionConditionFormat(variable)) {
					JOptionPane.showMessageDialog(null, "转移条件" + variable + "格式错误",
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
					JOptionPane.showMessageDialog(null, "未选定任何转移条件",
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
					JOptionPane.showMessageDialog(null, "未选定任何转移条件",
							"编辑错误", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String variable = JOptionPane.showInputDialog(
							"修改转移条件", listModel.get(index)).trim();
					//与原值相等
					if (variable.equals(listModel.get(index))) {}
					//重复
					else if (listModel.contains(variable)) {
						JOptionPane.showMessageDialog(null, "转移条件" + variable + "已存在",
								"转移条件重复", JOptionPane.ERROR_MESSAGE);
					}
					//格式错误
					else if (!isTransitionConditionFormat(variable)) {
						JOptionPane.showMessageDialog(null, "转移条件" + variable + "格式错误",
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

	//检测转移条件的格式是否正确
	private boolean isTransitionConditionFormat(String variable) {
		Pattern pattern = Pattern.compile(
				"(\\d*\\s*<=?\\s*)?\\d*\\*?[a-zA-Z_]+\\w*"
				+ "(\\s*[\\+\\-]\\s*\\d*\\*?[a-zA-Z_]+\\w*)?"
				+ "(\\s*<=?\\s*\\d*)?"); 
		Matcher matcher = pattern.matcher(variable);
		return matcher.matches();
	}

	public static void main(String[] args) {
		TransitionCondition t = new TransitionCondition();
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.setVisible(true);
	}

}
