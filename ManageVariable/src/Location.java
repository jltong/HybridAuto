
public class Location {
	private int xOrdinate; //横坐标
	private int yOrdinate; //纵坐标
	
	private int radius; //半径
	
	private Gradient gradient; //变化率集合窗体
	private Constraint constraint; //约束集合窗体
	
	//构造函数
	public Location(int xOrdinate, int yOrdinate, int radius) {
		this.xOrdinate = xOrdinate;
		this.yOrdinate = yOrdinate;
		this.radius = radius;
		
//		gradient = new Gradient();
		constraint = new Constraint();
	}
	
	//修改状态的位置属性
	public void setProperty(int xOrdinate, int yOrdinate, int radius) {
		this.xOrdinate = xOrdinate;
		this.yOrdinate = yOrdinate;
		this.radius = radius;
	}
	
	//显示变化率集合窗体
	public void displayGradientFrame() {
		gradient.setVisible(true);
	}
	
	//显示约束集合窗体
	public void displayConstraintFrame() {
		constraint.setVisible(true);
	}
	
//	//获得变化率数组
//	public Object[] getGradientList() {
//		return gradient.getGradient();
//	}
	
	//获得约束数组
	public Object[] getConstraint() {
		return constraint.getConstrait();
	}
}
