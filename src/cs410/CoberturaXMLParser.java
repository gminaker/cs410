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

public class CoberturaXMLParser {

	String cMethodClass = null;
	NodeList cMethodChildren = null;
	double crapScore;

	Hashtable<String, Double> outputTable;

	double updateClassMethodCount;
	double updateClassCrapCount;

	public CoberturaXMLParser(){
		this.outputTable = new Hashtable<String, Double>();
	}
	
	public Hashtable<String,Double> getHashTable(){
		return this.outputTable;
	}
	
	public Hashtable<String, Double> parseXML() {

		try {
			// Initialization code modified from tutorial
			// http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
			File crapFile = new File("/Documents/eclipse_java/cs410/codebase/elasticSearch/target/site/cobertura/coverage.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
			// creates the DOM object
			Document doc = dBuilder.parse(crapFile);

			Element cElement = doc.getDocumentElement();
			NodeList cNodes = cElement.getChildNodes();
			NodeList cMethods = null;
			for (int i = 0; i < cNodes.getLength(); i++) {

				Node cNode = cNodes.item(i);
				if ((cNode instanceof Element)
						&& (cNode.getNodeName() == "packages")) {

					cMethods = cNode.getChildNodes();
					System.out.println("getting packages nodes");
					processPackageList(cMethods);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.getHashTable();

	}
	
	public void processPackageList(NodeList packageList){
		try{
			
			for(int i = 0; i < packageList.getLength(); i++){
				Node pNode = packageList.item(i);
				if ((pNode instanceof Element)
						&& (pNode.getNodeName() == "package")) {

					System.out.println("getting package nodes");
					processClassesNodes(pNode.getChildNodes());
				}
			}
				
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	public void processClassesNodes(NodeList classesList){
		try{
			
			for(int i = 0; i < classesList.getLength(); i++){
				Node classesNode = classesList.item(i);
				if ((classesNode instanceof Element)
						&& (classesNode.getNodeName() == "classes")) {

					System.out.println("getting classes nodes");
					processClassNodes(classesNode.getChildNodes());
				}
			}
				
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	public void processClassNodes(NodeList classList){
		try{
			
			for(int i = 0; i < classList.getLength(); i++){
				Node classNode = classList.item(i);
				if ((classNode instanceof Element)
						&& (classNode.getNodeName() == "class")) {

					System.out.println("getting class nodes");
					NamedNodeMap classAttributes = classNode.getAttributes();
					addToHash(classAttributes);
				}
			}
				
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	/**
	 * adds classname (key) and class complexity to hashtable
	 * 
	 * @param classAtts
	 */
	public void addToHash(NamedNodeMap classAtts){
		String className = classAtts.getNamedItem("filename").getNodeValue();
		String classComplexityString = classAtts.getNamedItem("complexity").getNodeValue().toString();
		Double classComplexity = Double.parseDouble(classComplexityString);
		
		this.outputTable.put(className, classComplexity);
	}

	
}
