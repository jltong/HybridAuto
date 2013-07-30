
public class State{
	private String id;
	private String name;
	private String initial;
	private String invariant;
	private String changerate;
	private Constraint constraint;
	
	public State(){
		this.id = "";
		this.name="";
		this.initial="";
		this.invariant="";
		this.changerate="";
		this.constraint=new Constraint();
	}
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getInitial(){
		return this.initial;
	}
	public void setInitial(String initial){
		this.initial = initial;
	}
	public String getInvariant(){
		return this.invariant;
	}
	public void setInvariant(String invariant){
		this.invariant = invariant;
	}
	public String getChangerate(){
		return this.changerate;
	}
	public void setChangerate(String changerate){
		this.changerate = changerate;
	}
	public Constraint getConstraint(){
		return this.constraint;
	}
	public void setConstraint(Constraint constraint){
		this.constraint = constraint;
	}
}
class Constraint{
	private int x;
	private int y;
	private int height;
	private int width;
	
	Constraint(){
		this.x=0;
		this.y=0;
		this.height=0;
		this.width=0;
	}
	Constraint(int x,int y,int height,int width){
		this.x = x; 
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getHeight(){
		return this.height;
	}
	public int getWidth(){
		return this.width;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setHeight(int height){
		this.height = height;
	}
	public void setWidth(int width){
		this.width = width;
	}
}