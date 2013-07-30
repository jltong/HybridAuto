/**
 * 
 * @author ���
 * @version 0.1
 * 
 * ����: Variable
 * 
 * �̳У�JFrame��
 * 
 * 
 * ����: ����Լ��������Լ���ĸ�ʽ����Լ��
 *
 * ��Ա��
 *       list     	:JList<String>				�б�ؼ����г�Լ����
 *       listModel	:DefaultListModel<String>	�����������
 *       editButton	:JButton					��ť�ؼ�������Ա༭Լ��
 *       addButton	:JButton					��ť�ؼ�����������Լ��
 *       delButton	:JButton					��ť�ؼ��������ɾ��Լ��
 *
 *       
 * ������private boolean isVariableFormat(String variable)
 * 		���Լ���Ƿ���ϸ�ʽҪ��
 */      


import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

import javax.swing.*;

public class Constraint extends JFrame {

	//ά����
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	//�б�ؼ�
	private JList<String> list = new JList<String>(listModel);
	
	private JButton addButton = new JButton("���");
	private JButton delButton = new JButton("ɾ��");
	private JButton editButton = new JButton("�༭");
	
	public Constraint() {
		//����ѡ��ģʽ
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayout(new BorderLayout());
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(editButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		setTitle("Լ����");
		setSize(200, 500);
		setLocationRelativeTo(null);
		
		//���
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String variable = JOptionPane.showInputDialog("������Լ��").trim();
				//����Ƿ��Ѵ���
				if (listModel.contains(variable)) {
					JOptionPane.showMessageDialog(null, "Լ��" + variable + "�Ѵ���",
							"Լ���ظ�", JOptionPane.ERROR_MESSAGE);
				}
				//����ʽ�Ƿ���ȷ
				else if (!isConstraintFormat(variable)) {
					JOptionPane.showMessageDialog(null, "Լ��" + variable + "��ʽ����",
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
					JOptionPane.showMessageDialog(null, "δѡ���κ�Լ��",
							"ɾ������", JOptionPane.ERROR_MESSAGE);
				}
				else {
					listModel.remove(index);
				}
			}
		});
		
		//�༭����
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				//δѡ��
				if (index < 0) {
					JOptionPane.showMessageDialog(null, "δѡ���κ�Լ��",
							"�༭����", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String variable = JOptionPane.showInputDialog(
							"�޸�Լ��", listModel.get(index)).trim();
					//��ԭֵ���
					if (variable.equals(listModel.get(index))) {}
					//�ظ�
					else if (listModel.contains(variable)) {
						JOptionPane.showMessageDialog(null, "Լ��" + variable + "�Ѵ���",
								"Լ���ظ�", JOptionPane.ERROR_MESSAGE);
					}
					//��ʽ����
					else if (!isConstraintFormat(variable)) {
						JOptionPane.showMessageDialog(null, "Լ��" + variable + "��ʽ����",
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

	//���Լ���ĸ�ʽ�Ƿ���ȷ
	private boolean isConstraintFormat(String variable) {
		Pattern pattern = Pattern.compile(
				"(\\d*\\s*<=?\\s*)?\\d*\\*?[a-zA-Z_]+\\w*"
				+ "(\\s*[\\+\\-]\\s*\\d*\\*?[a-zA-Z_]+\\w*)?"
				+ "(\\s*<=?\\s*\\d*)?"); 
		Matcher matcher = pattern.matcher(variable);
		return matcher.matches();
	}
	
	public Object[] getConstrait() {
		return listModel.toArray();
	}

	public static void main(String[] args) {
		Constraint c = new Constraint();
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setVisible(true);
	}

}
