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
 * ����: ����ת������������ת�������ĸ�ʽ����Լ��
 *
 * ��Ա��
 *       list     	:JList<String>				�б�ؼ����г�ת��������
 *       listModel	:DefaultListModel<String>	�����������
 *       editButton	:JButton					��ť�ؼ�������Ա༭ת������
 *       addButton	:JButton					��ť�ؼ�����������ת������
 *       delButton	:JButton					��ť�ؼ��������ɾ��ת������
 *
 *       
 * ������private boolean isVariableFormat(String variable)
 * 		���ת�������Ƿ���ϸ�ʽҪ��
 */      


import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

import javax.swing.*;

public class TransitionCondition extends JFrame {

	//ά����
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	//�б�ؼ�
	private JList<String> list = new JList<String>(listModel);
	
	private JButton addButton = new JButton("���");
	private JButton delButton = new JButton("ɾ��");
	private JButton editButton = new JButton("�༭");
	
	public TransitionCondition() {
		//����ѡ��ģʽ
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayout(new BorderLayout());
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(editButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		setTitle("ת��������");
		setSize(200, 500);
		setLocationRelativeTo(null);
		
		//���
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String variable = JOptionPane.showInputDialog("������ת������").trim();
				//����Ƿ��Ѵ���
				if (listModel.contains(variable)) {
					JOptionPane.showMessageDialog(null, "ת������" + variable + "�Ѵ���",
							"ת�������ظ�", JOptionPane.ERROR_MESSAGE);
				}
				//����ʽ�Ƿ���ȷ
				else if (!isTransitionConditionFormat(variable)) {
					JOptionPane.showMessageDialog(null, "ת������" + variable + "��ʽ����",
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
					JOptionPane.showMessageDialog(null, "δѡ���κ�ת������",
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
					JOptionPane.showMessageDialog(null, "δѡ���κ�ת������",
							"�༭����", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String variable = JOptionPane.showInputDialog(
							"�޸�ת������", listModel.get(index)).trim();
					//��ԭֵ���
					if (variable.equals(listModel.get(index))) {}
					//�ظ�
					else if (listModel.contains(variable)) {
						JOptionPane.showMessageDialog(null, "ת������" + variable + "�Ѵ���",
								"ת�������ظ�", JOptionPane.ERROR_MESSAGE);
					}
					//��ʽ����
					else if (!isTransitionConditionFormat(variable)) {
						JOptionPane.showMessageDialog(null, "ת������" + variable + "��ʽ����",
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

	//���ת�������ĸ�ʽ�Ƿ���ȷ
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
