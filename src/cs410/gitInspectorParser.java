package cs410;

import java.io.File;
import java.util.Hashtable;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class gitInspectorParser {
	
	static int maxAuthorNum = 6;
	static int maxFileNum = 6;
	
	static Object[][] outputArray = new Object[maxFileNum+1][maxAuthorNum];
	
	static Hashtable<String, String> responsibilities = new Hashtable<String, String>();
	
	public static void main(String[] args) {

		try {
		
			File fXmlFile = new File("src/cs410/GitInspectorOutput.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("author");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					outputArray[temp][0] = eElement.getElementsByTagName("name").item(0).getTextContent();
							
					for (int i=1; i <maxFileNum; i++)
					{
						String content;
						
						if (eElement.getElementsByTagName("file").item(i-1) != null)
						{
							content = eElement.getElementsByTagName("name").item(1).getTextContent();
						}
						else
						{
							content = "";
						}
						outputArray[temp][i] = content;
					}
				}
			}
		    
		}
		catch (Exception e) {
			e.printStackTrace();
	    }
		
		//testing
		for(int i = 0; i < outputArray.length; i++)
		{
		    for(int j = 0; j < outputArray[i].length; j++)
		    {
		        System.out.print(outputArray[i][j]);
		        if(j < outputArray[i].length - 1) System.out.print(" ");
		    }
		    System.out.println();
		}
		
	}	

	public static void parseXML() {
		try {
			
			File fXmlFile = new File("C:/Users/Tiana Im/Desktop/jquery_output.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("author");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					outputArray[temp][0] = eElement.getElementsByTagName("name").item(0).getTextContent();
							
					for (int i=1; i <maxFileNum; i++)
					{
						String content;
						
						if (eElement.getElementsByTagName("file").item(i-1) != null)
						{
							content = eElement.getElementsByTagName("file").item(i-1).getTextContent();
						}
						else
						{
							content = "";
						}
						outputArray[temp][i+1] = content;
					}
				}
			}
		    
		}
		catch (Exception e) {
			e.printStackTrace();
	    }
		
	}
	
	
	protected static Node getNode(String tagName, NodeList nodes) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            return node;
	        }
	    }	 
	    return null;
	}

	protected String getNodeValue( Node node ) {
	    NodeList childNodes = node.getChildNodes();
	    for (int x = 0; x < childNodes.getLength(); x++ ) {
	        Node data = childNodes.item(x);
	        if ( data.getNodeType() == Node.TEXT_NODE )
	            return data.getNodeValue();
	    }
	    return "";
	}
	 
	protected String getNodeValue(String tagName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.TEXT_NODE )
	                    return data.getNodeValue();
	            }
	        }
	    }
	    return "";
	}
	 
	protected String getNodeAttr(String tagName, String attrName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.ATTRIBUTE_NODE ) {
	                    if ( data.getNodeName().equalsIgnoreCase(attrName) )
	                        return data.getNodeValue();
	                }
	            }
	        }
	    }
	 
	    return "";
	}
}
