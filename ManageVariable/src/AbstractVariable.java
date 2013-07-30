import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public abstract class AbstractVariable extends JFrame {
	
	protected String type;
	
	//维护表
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	//列表控件
	private JList<String> list = new JList<String>(listModel);
	
	private JButton addButton = new JButton("添加");
	private JButton delButton = new JButton("删除");
	private JButton editButton = new JButton("编辑");

	protected abstract boolean isFormat(String variable);
	protected abstract void setType();
	
	public AbstractVariable() {
		init();
	}
	
	private void init() {
		
		setType();
		
		//设置选择模式
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayout(new BorderLayout());
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(editButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		setTitle(type + "表");
		setSize(200, 500);
		setLocationRelativeTo(null);
		
		//添加
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String variable = null;
				variable = JOptionPane.showInputDialog("输入新" + type);
				if (variable == null)
					return;
				variable = variable.trim();
				
				//检查是否已存在
				if (listModel.contains(variable)) {
					JOptionPane.showMessageDialog(null, type + variable + "已存在",
							type + "重复", JOptionPane.ERROR_MESSAGE);
				}
				//检查格式是否正确
				else if (!isFormat(variable)) {
					JOptionPane.showMessageDialog(null, type + variable + "格式错误",
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
					JOptionPane.showMessageDialog(null, "未选定任何" + type,
							"删除错误", JOptionPane.ERROR_MESSAGE);
				}
				else {
					listModel.remove(index);
				}
			}
		});
		
		//编辑
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				//未选中
				if (index < 0) {
					JOptionPane.showMessageDialog(null, "未选定任何" + type,
							"编辑错误", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String variable = null;
					variable = JOptionPane.showInputDialog(
							"修改" + type, listModel.get(index));
					if (variable == null)
						return;
					variable = variable.trim();
					
					//与原值相等
					if (variable.equals(listModel.get(index))) {}
					//重复
					else if (listModel.contains(variable)) {
						JOptionPane.showMessageDialog(null, type + variable + "已存在",
								type + "重复", JOptionPane.ERROR_MESSAGE);
					}
					//格式错误
					else if (!isFormat(variable)) {
						JOptionPane.showMessageDialog(null, type + variable + "格式错误",
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
	
}
