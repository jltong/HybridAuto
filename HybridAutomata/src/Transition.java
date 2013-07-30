
public class Transition{
	private String id;
	private String name;
	private String srcid;
	private String destid;
	private String srcname;
	private String destname;
	private String initial;
	private String guard;
	private String reset;
	private Bendpoints bendpoints;
	
	public Transition(){
		this.id="";
		this.name="";
		this.srcid="";
		this.destid="";
		this.srcname="";
		this.destname="";
		this.initial="";
		this.guard="";
		this.reset="";
		this.bendpoints=new Bendpoints();
		
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
	public String getSrcname(){
		return this.srcname;
	}
	public void setSrcname(String srcname){
		this.srcname = srcname;
	}
	public String getDestname(){
		return this.destname;
	}
	public void setDestname(String destname){
		this.destname = destname;
	}
	public String  getSrcid(){
		return this.srcid;
	}
	public void setSrcid(String srcid){
		this.srcid = srcid;
	}
	public String getDestid(){
		return this.destid;
	}
	public void setDestid(String destid){
		this.destid = destid;
	}
	public String getInitial(){
		return this.initial;
	}
	public void setInitial(String initial){
		this.initial = initial;
	}
	public String getGurad(){
		return this.guard;
	}
	public void setGuard(String guard){
		this.guard = guard;
	}
	public String getReset(){
		return this.reset;
	}
	public void setReset(String reset){
		this.reset = reset;
	}
	public Bendpoints getBendpoints(){
		return this.bendpoints;
	}
	public void setBendpoints(Bendpoints bendpoints){
		this.bendpoints = bendpoints;
	}
	
}


class Bendpoints{
	Pos[] p;
	int numberOfPoint;
	Bendpoints(){
		this.numberOfPoint = 0;
		
	}
	Bendpoints(Pos[] p,int numberOfPoint){
		this.numberOfPoint = numberOfPoint;
		this.p = new Pos[numberOfPoint];
		for(int i=0;i<numberOfPoint;i++){
			this.p[i]=new Pos(p[i].getX(),p[i].getY());
			
		}
	}
	public int getNumber(){	
		return this.numberOfPoint;
	}
	public void setNumber(int numberOfPoint){
		this.numberOfPoint = numberOfPoint;
	}
	public Pos getPoint(int index){
		return this.p[index];
	}
	
	public void setPoint(int index,Pos p){
		this.p[index].setX(p.getX());
		this.p[index].setY(p.getY());
	}
}

class Pos{
	int x;
	int y;
	Pos(){
		this.x=0;
		this.y=0;
	}
	Pos(int x,int y){
		this.x=x;
		this.y=y;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
}