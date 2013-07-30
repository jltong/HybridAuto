
public class AutomataHeader {
	//private String analyzer;
	private String automatavariables;
	private String name;
	
	
	public AutomataHeader(){
		
		this.automatavariables="";
		this.name="";
	}
	public String getAutomatavariables(){
		return this.automatavariables;
	}
	public void setAutomatavariables(String automatavariables){
		this.automatavariables=automatavariables;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
}
