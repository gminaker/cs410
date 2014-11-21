package cs410;

import java.io.File;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//parses xml output from cobertura 
public class CoberturaXMLOutputParser {

	String cMethodClass = null;
	NodeList cMethodChildren = null;
	double crapScore;

	Hashtable<String, Double> outputTable;

	double updateClassMethodCount;
	double updateClassCrapCount;

	public CoberturaXMLOutputParser(){
		this.outputTable = new Hashtable<String, Double>();
	}
	
	public Hashtable<String,Double> getHashTable(){
		return this.outputTable;
	}
	
	//returns a hash table containing the names of classes and their associated complexities 
	public Hashtable<String, Double> parseXML(String url) {

		try {
			// Initialization code modified from tutorial
			// http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
			File crapFile = new File(url);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
			// creates the DOM object
			Document doc = dBuilder.parse(crapFile);
			//end initialization 

			Element cElement = doc.getDocumentElement();
			NodeList cNodes = cElement.getChildNodes();
			NodeList cMethods = null;
			for (int i = 0; i < cNodes.getLength(); i++) {

				Node cNode = cNodes.item(i);
				if ((cNode instanceof Element)
						&& (cNode.getNodeName() == "packages")) {

					cMethods = cNode.getChildNodes();
					processPackageList(cMethods);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.getHashTable();

	}
	
	//helper method to process nodes within packages tags
	public void processPackageList(NodeList packageList){
		try{
			
			for(int i = 0; i < packageList.getLength(); i++){
				Node pNode = packageList.item(i);
				if ((pNode instanceof Element)
						&& (pNode.getNodeName() == "package")) {

					processClassesNodes(pNode.getChildNodes());
				}
			}
				
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	
	//helper method to process nodes within package tags
	public void processClassesNodes(NodeList classesList){
		try{
			
			for(int i = 0; i < classesList.getLength(); i++){
				Node classesNode = classesList.item(i);
				if ((classesNode instanceof Element)
						&& (classesNode.getNodeName() == "classes")) {

					processClassNodes(classesNode.getChildNodes());
				}
			}
				
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	//helper method to process nodes within class tags
	public void processClassNodes(NodeList classList){
		try{
			
			for(int i = 0; i < classList.getLength(); i++){
				Node classNode = classList.item(i);
				if ((classNode instanceof Element)
						&& (classNode.getNodeName() == "class")) {

					NamedNodeMap classAttributes = classNode.getAttributes();
					addToHash(classAttributes);
				}
			}
				
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
   //adds classname (key) and class complexity to hashtable
	public void addToHash(NamedNodeMap classAtts){
		
 	    String className = classAtts.getNamedItem("filename").getNodeValue();
		String patternString = "[\\\\/]";
		String[] result = className.split(patternString);
		int resultLen = result.length - 1;
		className = result[resultLen];
		
		//System.out.println(className);

		String classComplexityString = classAtts.getNamedItem("complexity").getNodeValue().toString();
		Double classComplexity = Double.parseDouble(classComplexityString);
		
		this.outputTable.put(className, classComplexity);
	}

	
}
