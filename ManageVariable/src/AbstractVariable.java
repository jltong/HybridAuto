import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public abstract class AbstractVariable extends JFrame {
	
	protected String type;
	
	//ά����
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	//�б�ؼ�
	private JList<String> list = new JList<String>(listModel);
	
	private JButton addButton = new JButton("���");
	private JButton delButton = new JButton("ɾ��");
	private JButton editButton = new JButton("�༭");

	protected abstract boolean isFormat(String variable);
	protected abstract void setType();
	
	public AbstractVariable() {
		init();
	}
	
	private void init() {
		
		setType();
		
		//����ѡ��ģʽ
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayout(new BorderLayout());
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(editButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		setTitle(type + "��");
		setSize(200, 500);
		setLocationRelativeTo(null);
		
		//���
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String variable = null;
				variable = JOptionPane.showInputDialog("������" + type);
				if (variable == null)
					return;
				variable = variable.trim();
				
				//����Ƿ��Ѵ���
				if (listModel.contains(variable)) {
					JOptionPane.showMessageDialog(null, type + variable + "�Ѵ���",
							type + "�ظ�", JOptionPane.ERROR_MESSAGE);
				}
				//����ʽ�Ƿ���ȷ
				else if (!isFormat(variable)) {
					JOptionPane.showMessageDialog(null, type + variable + "��ʽ����",
							"��ʽ����", JOptionPane.ERROR_MESSAGE);
				}
				else {
					listModel.addElement(variable);
				}
			}
		});
		
		
		//ɾ��
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index < 0) {
					JOptionPane.showMessageDialog(null, "δѡ���κ�" + type,
							"ɾ������", JOptionPane.ERROR_MESSAGE);
				}
				else {
					listModel.remove(index);
				}
			}
		});
		
		//�༭
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				//δѡ��
				if (index < 0) {
					JOptionPane.showMessageDialog(null, "δѡ���κ�" + type,
							"�༭����", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String variable = null;
					variable = JOptionPane.showInputDialog(
							"�޸�" + type, listModel.get(index));
					if (variable == null)
						return;
					variable = variable.trim();
					
					//��ԭֵ���
					if (variable.equals(listModel.get(index))) {}
					//�ظ�
					else if (listModel.contains(variable)) {
						JOptionPane.showMessageDialog(null, type + variable + "�Ѵ���",
								type + "�ظ�", JOptionPane.ERROR_MESSAGE);
					}
					//��ʽ����
					else if (!isFormat(variable)) {
						JOptionPane.showMessageDialog(null, type + variable + "��ʽ����",
								"��ʽ����", JOptionPane.ERROR_MESSAGE);
					}
					//�޸�
					else {
						listModel.set(index, variable);
					}
				}
			}
		});
	}
	
}
