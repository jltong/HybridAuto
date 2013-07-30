
public class Location {
	private int xOrdinate; //������
	private int yOrdinate; //������
	
	private int radius; //�뾶
	
	private Gradient gradient; //�仯�ʼ��ϴ���
	private Constraint constraint; //Լ�����ϴ���
	
	//���캯��
	public Location(int xOrdinate, int yOrdinate, int radius) {
		this.xOrdinate = xOrdinate;
		this.yOrdinate = yOrdinate;
		this.radius = radius;
		
//		gradient = new Gradient();
		constraint = new Constraint();
	}
	
	//�޸�״̬��λ������
	public void setProperty(int xOrdinate, int yOrdinate, int radius) {
		this.xOrdinate = xOrdinate;
		this.yOrdinate = yOrdinate;
		this.radius = radius;
	}
	
	//��ʾ�仯�ʼ��ϴ���
	public void displayGradientFrame() {
		gradient.setVisible(true);
	}
	
	//��ʾԼ�����ϴ���
	public void displayConstraintFrame() {
		constraint.setVisible(true);
	}
	
//	//��ñ仯������
//	public Object[] getGradientList() {
//		return gradient.getGradient();
//	}
	
	//���Լ������
	public Object[] getConstraint() {
		return constraint.getConstrait();
	}
}
