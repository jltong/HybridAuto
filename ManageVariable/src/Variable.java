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
 * ����: ������������Ա����ĸ�ʽ����Լ��
 *
 * ��Ա��
 *       list     	:JList<String>				�б�ؼ����г�������
 *       listModel	:DefaultListModel<String>	�����������
 *       editButton	:JButton					��ť�ؼ�������Ա༭����
 *       addButton	:JButton					��ť�ؼ����������ӱ���
 *       delButton	:JButton					��ť�ؼ��������ɾ������
 *
 *       
 * ������private boolean isVariableFormat(String variable)
 * 		�������Ƿ���ϸ�ʽҪ��
 */      

import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

import javax.swing.*;

public class Variable extends JFrame {

	//ά����
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	//�б�ؼ�
	private JList<String> list = new JList<String>(listModel);
	
	private JButton addButton = new JButton("���");
	private JButton delButton = new JButton("ɾ��");
	private JButton editButton = new JButton("�༭");
	
	public Variable() {
		//����ѡ��ģʽ
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayout(new BorderLayout());
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(editButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		setTitle("������");
		setSize(200, 500);
		setLocationRelativeTo(null);
		
		//���
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String variable = JOptionPane.showInputDialog("�����±���").trim();
				//����Ƿ��Ѵ���
				if (listModel.contains(variable)) {
					JOptionPane.showMessageDialog(null, "����" + variable + "�Ѵ���",
							"�����ظ�", JOptionPane.ERROR_MESSAGE);
				}
				//����ʽ�Ƿ���ȷ
				else if (!isVariableFormat(variable)) {
					JOptionPane.showMessageDialog(null, "����" + variable + "��ʽ����",
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
					JOptionPane.showMessageDialog(null, "δѡ���κα���",
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
					JOptionPane.showMessageDialog(null, "δѡ���κα���",
							"�༭����", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String variable = JOptionPane.showInputDialog(
							"�޸ı���", listModel.get(index)).trim();
					//��ԭֵ���
					if (variable.equals(listModel.get(index))) {}
					//�ظ�
					else if (listModel.contains(variable)) {
						JOptionPane.showMessageDialog(null, "����" + variable + "�Ѵ���",
								"�����ظ�", JOptionPane.ERROR_MESSAGE);
					}
					//��ʽ����
					else if (!isVariableFormat(variable)) {
						JOptionPane.showMessageDialog(null, "����" + variable + "��ʽ����",
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

	//����ʽ�Ƿ���ȷ
	private boolean isVariableFormat(String variable) {
		Pattern pattern = Pattern.compile("[a-zA-Z_]+\\w*");
		Matcher matcher = pattern.matcher(variable);
		return matcher.matches();
	}

	public static void main(String[] args) {
		Variable v = new Variable();
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		v.setVisible(true);
	}

}
