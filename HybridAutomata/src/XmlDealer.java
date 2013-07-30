/**
 * 
 * @author 蒋林桐
 * @version 0.1
 * 
 * 类名: XmlDealer
 * 
 * 继承：
 * 
 * 
 * 作用: process the xml file
 *
 * 成员：
 * 		StateNum : int    number of state 
 * 		TransitionNum : int    number of transition
 *       
 *
 *       
 * 操作：
 * 
 */
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import java.io.*;
import java.util.Formatter;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class XmlDealer {
	private int StateNum;
	private int TransitionNum;
	
	public XmlDealer(){
		StateNum = 0;
		TransitionNum = 0;
	}
	
	public int getStateNum(){
		return this.StateNum;
	}
	public void setStateNum(String FileName) throws ParserConfigurationException, SAXException, IOException, NumberFormatException, XPathExpressionException{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		File file = new File(FileName);
		Document document = docBuilder.parse(file);
		XPathFactory xpFactory = XPathFactory.newInstance();
		XPath xPath = xpFactory.newXPath();
		int numberOfState = Integer.parseInt(xPath.evaluate("count(/automata/state)", document));
		this.StateNum=numberOfState;
	}
	public int getTransitionNum(){
		return this.TransitionNum;
	}
	public void setTransitionNum(String FileName) throws ParserConfigurationException, SAXException, IOException, NumberFormatException, XPathExpressionException{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		File file = new File(FileName);
		Document document = docBuilder.parse(file);
		XPathFactory xpFactory = XPathFactory.newInstance();
		XPath xPath = xpFactory.newXPath();
		int numberOfTransition = Integer.parseInt(xPath.evaluate("count(/automata/transition)", document));
		this.TransitionNum=numberOfTransition;
	}
	
	public void readXmlFile(String FileName,State state[],Transition transition[],AutomataHeader ah)throws 
	ParserConfigurationException, IOException, SAXException,XPathExpressionException{
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		File file = new File(FileName);
		Document document = docBuilder.parse(file);
		
		XPathFactory xpFactory = XPathFactory.newInstance();
		XPath xPath = xpFactory.newXPath();
		
		int numberOfState = Integer.parseInt(xPath.evaluate("count(/automata/state)", document));
		int numberOfTransition = Integer.parseInt(xPath.evaluate("count(/automata/transition)", document));
		
		//ah.setAnalyzer(xPath.evaluate("/automata/@analyzer", document));
		ah.setAutomatavariables(xPath.evaluate("/automata/@automatavariables", document));
		ah.setName(xPath.evaluate("/automata/@name", document));
		//ah.setStatesize(xPath.evaluate("/automata/@statesize", document));
		
		System.out.println("analyzer: "+xPath.evaluate("/automata/@analyzer", document));
		System.out.println("automatavariables: "+xPath.evaluate("/automata/@automatavariables", document));
		System.out.println("name: "+xPath.evaluate("/automata/@name", document));
		System.out.println("statesize: "+xPath.evaluate("/automata/@statesize", document));
		
		for(int i=1;i<=numberOfState;i++){
			System.out.print("state: ");
			
			state[i-1].setId(xPath.evaluate("/automata/state[" + i + "]/@ID", document));
			System.out.println("\tID: "+xPath.evaluate("/automata/state[" + i + "]/@ID", document));
			
			state[i-1].setName(xPath.evaluate("/automata/state[" + i + "]/name", document));
			System.out.println("\tname: "+xPath.evaluate("/automata/state[" + i + "]/name", document));
			
			state[i-1].setInitial(xPath.evaluate("/automata/state[" + i + "]/initial", document));
			System.out.println("\tinitial: "+xPath.evaluate("/automata/state[" + i + "]/initial", document));
			
			state[i-1].setChangerate(xPath.evaluate("/automata/state[" + i + "]/changerate", document));
			System.out.println("\tchangerate: "+xPath.evaluate("/automata/state[" + i + "]/changerate", document));
			
			int x=Integer.parseInt(xPath.evaluate("/automata/state[" + i + "]/constraint/@x", document));
			int y=Integer.parseInt(xPath.evaluate("/automata/state[" + i + "]/constraint/@y", document));
			int height=Integer.parseInt(xPath.evaluate("/automata/state[" + i + "]/constraint/height", document));
			int width=Integer.parseInt(xPath.evaluate("/automata/state[" + i + "]/constraint/width", document));
			Constraint cons = new Constraint(x,y,height,width);
			state[i-1].setConstraint(cons);
			System.out.println("\tconstraint: "+x+" "+y+'\n'+'\t'+height+" "+width);
			
			//Constraint cons = new Constraint(x,y,height,weight);
			//state[i].setConstraint(cons);
		}
		
		
		for(int i=1;i<=numberOfTransition;i++){
			
			System.out.println("transition: "+(i-1));
			
			transition[i-1].setName(xPath.evaluate("/automata/transition[" + i + "]/name", document));
			System.out.println("\tname: "+xPath.evaluate("/automata/transition[" + i + "]/name", document));
			
			transition[i-1].setSrcid(xPath.evaluate("/automata/transition[" + i + "]/sourceid", document));
			System.out.println("\tsourceid: "+xPath.evaluate("/automata/transition[" + i + "]/sourceid", document));
			
			transition[i-1].setSrcname(xPath.evaluate("/automata/transition[" + i + "]/sourcename", document));
			System.out.println("\tsourcename: "+xPath.evaluate("/automata/transition[" + i + "]/sourcename", document));
			
			transition[i-1].setDestid(xPath.evaluate("/automata/transition[" + i + "]/targetid", document));
			System.out.println("\ttargetid: "+xPath.evaluate("/automata/transition[" + i + "]/targetid", document));
			
			transition[i-1].setInitial(xPath.evaluate("/automata/transition[" + i + "]/initial", document));
			System.out.println("\tinitial: "+xPath.evaluate("/automata/transition[" + i + "]/initial", document));
			
			transition[i-1].setGuard(xPath.evaluate("/automata/transition[" + i + "]/guard", document));
			System.out.println("\tguard: "+xPath.evaluate("/automata/transition[" + i + "]/guard", document));
			
			transition[i-1].setReset(xPath.evaluate("/automata/transition[" + i + "]/reset", document));
			System.out.println("\treset: "+xPath.evaluate("/automata/transition[" + i + "]/reset", document));
			
			int numberOfPoint = Integer.parseInt(xPath.evaluate("count(/automata/transition[" + i + "]/bendpoints/point)",document));
			Pos[] p = new Pos[numberOfPoint];
			
			for(int j=1;j<=numberOfPoint;j++){
				int x =  Integer.parseInt(xPath.evaluate("/automata/transition[" + i + "]/bendpoints/point[" + j + "]/@x", document));
				int y =  Integer.parseInt(xPath.evaluate("/automata/transition[" + i + "]/bendpoints/point[" + j + "]/@y", document));
				System.out.println("\t"+x+" "+y);
				p[j-1] = new Pos(x,y);
			}
			
			Bendpoints bendpoints = new Bendpoints(p,numberOfPoint);
			transition[i-1].setBendpoints(bendpoints);
			
		}
		
	}
	
	
	public void writeXmlFile(String FileName,State state[],Transition transition[],AutomataHeader ah) throws ParserConfigurationException, FileNotFoundException{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		Document document = docBuilder.newDocument();
		//Create a comment
		Comment comment = document.createComment("XML document for HybridAutomata");
		document.appendChild(comment);
		//Create the <automata> as the root
		Element automataElement = document.createElement("automata");
		//automataElement.setAttribute("analyzer",ah.getAnalyzer());
		automataElement.setAttribute("automatavariables", ah.getAutomatavariables());
		automataElement.setAttribute("name",ah.getName());
		//automataElement.setAttribute("statesize",ah.getStatesize());
		document.appendChild(automataElement);
		
		
		//Create the state
		for(int i=1;i<=state.length;i++){
			Element stateElement = document.createElement("state");
			String id = Integer.toString(i-1);
			stateElement.setAttribute("ID", id);
			
			Element nameElement = document.createElement("name");
			Text nameText = document.createTextNode(state[i-1].getName());
			Element initialElement = document.createElement("initial");
			Text initialText = document.createTextNode(state[i-1].getInitial());
			Element invariantElement = document.createElement("invariant");
			Text invariantText = document.createTextNode(state[i-1].getInvariant());
			Element changerateElement = document.createElement("changerate");
			Text changerateText = document.createTextNode(state[i-1].getChangerate());
			Element constraintElement = document.createElement("constraint");
			constraintElement.setAttribute("x",Integer.toString(state[i-1].getConstraint().getX()));
			constraintElement.setAttribute("y",Integer.toString(state[i-1].getConstraint().getY()));
			
			Element heightElement = document.createElement("height");
			Text heightText = document.createTextNode(Integer.toString(state[i-1].getConstraint().getHeight()));
			Element widthElement = document.createElement("width");
			Text widthText = document.createTextNode(Integer.toString(state[i-1].getConstraint().getWidth()));
			
			automataElement.appendChild(stateElement);
			stateElement.appendChild(nameElement);
			nameElement.appendChild(nameText);
			stateElement.appendChild(initialElement);
			initialElement.appendChild(initialText);
			stateElement.appendChild(invariantElement);
			invariantElement.appendChild(invariantText);
			stateElement.appendChild(changerateElement);
			changerateElement.appendChild(changerateText);
			stateElement.appendChild(constraintElement);
			constraintElement.appendChild(heightElement);
			heightElement.appendChild(heightText);
			constraintElement.appendChild(widthElement);
			widthElement.appendChild(widthText);
			
			
		}
		
		for(int i=1;i<=transition.length;i++){
			Element transitionElement = document.createElement("transition");
			
			Element nameElement = document.createElement("name");
			Text nameText = document.createTextNode(transition[i-1].getName());
			
			Element srcidElement = document.createElement("sourceid");
			Text srcidText = document.createTextNode(transition[i-1].getSrcid());
			
			Element srcnameElement = document.createElement("sourcename");
			Text srcnameText = document.createTextNode(transition[i-1].getSrcname());
			
			Element destidElement = document.createElement("targetid");
			Text destidText = document.createTextNode(transition[i-1].getDestid());
			
			Element destnameElement = document.createElement("targetname");
			Text destnameText = document.createTextNode(transition[i-1].getDestname());
			
			Element initialElement = document.createElement("initial");
			Text initialText = document.createTextNode(transition[i-1].getInitial());
			
			Element guardElement = document.createElement("guard");
			Text guardText = document.createTextNode(transition[i-1].getGurad());
			
			Element resetElement = document.createElement("reset");
			Text resetText = document.createTextNode(transition[i-1].getReset());
			
			Element bendpointsElement = document.createElement("bendpoints");
			int numberOfPoint = transition[i-1].getBendpoints().getNumber();
			Element[] pointElement = new Element[numberOfPoint];
			for(int j=0;j<numberOfPoint;j++){
				pointElement[j] = document.createElement("point");
				pointElement[j].setAttribute("x", Integer.toString(transition[i-1].getBendpoints().getPoint(j).getX()));
				pointElement[j].setAttribute("y", Integer.toString(transition[i-1].getBendpoints().getPoint(j).getY()));
			}
			
			automataElement.appendChild(transitionElement);
			
			transitionElement.appendChild(nameElement);
			nameElement.appendChild(nameText);
			transitionElement.appendChild(srcidElement);
			srcidElement.appendChild(srcidText);
			srcnameElement.appendChild(srcnameText);
			transitionElement.appendChild(destidElement);
			destidElement.appendChild(destidText);
			transitionElement.appendChild(destnameElement);
			destnameElement.appendChild(destnameText);
			transitionElement.appendChild(initialElement);
			initialElement.appendChild(initialText);
			transitionElement.appendChild(guardElement);
			guardElement.appendChild(guardText);
			transitionElement.appendChild(resetElement);
			resetElement.appendChild(resetText);
			
			transitionElement.appendChild(bendpointsElement);
			for(int j=0;j<numberOfPoint;j++){
				bendpointsElement.appendChild(pointElement[j]);
			}
		}
		
		DOMImplementation impl = document.getImplementation();
		DOMImplementationLS implLS =(DOMImplementationLS)impl.getFeature("LS", "3.0");
		LSSerializer serializer = implLS.createLSSerializer();
		String out = serializer.writeToString(document);
		System.out.println(out);
		
		// Write XML to a text file
		Formatter output = new Formatter(FileName);
		output.format("%s", out);
		output.close();
	}
	public static void main(String arg[]) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException{
		
		String filename = "sample.xml";
		XmlDealer xd = new XmlDealer();
		xd.setStateNum(filename);
		xd.setTransitionNum(filename);
		State[] state = new State[xd.getStateNum()];
		for(int i=0;i<state.length;i++)
			state[i]=new State();
		Transition[] transition = new Transition[xd.getTransitionNum()];
		for(int i=0;i<transition.length;i++)
			transition[i]=new Transition();
		AutomataHeader ah = new AutomataHeader();
		xd.readXmlFile(filename,state,transition,ah);
		
		xd.writeXmlFile("temp.xml", state, transition, ah);
	}
	
}
