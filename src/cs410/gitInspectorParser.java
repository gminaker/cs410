package cs410;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class gitInspectorParser {
	
	static int maxAuthorNum = 6;
	static int maxFileNum = 6;
	
	static Object[][] outputArray = new Object[maxFileNum][maxAuthorNum+1];
	static List allList = new ArrayList<Object>();
	
	static Hashtable<String, String> responsibilities = new Hashtable<String, String>();
	
	public static void main(String[] args) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Document doc = builder.parse("src/cs410/ElasticSearchGitInspector.xml");
        
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        
        System.out.println("Get all writers");
        // 7) Get all writers
        XPathExpression expr = xpath.compile("/gitinspector/responsibilities/authors/author/name/text()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        nodes = (NodeList) result;
        System.out.println(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
	}	

	
	public static Object[][] parseXML() {
/*
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("src/cs410/ElasticSearchGitInspector.xml");
        
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        
        System.out.println("n//7) Get all writers");
        // 7) Get all writers
        XPathExpression expr = xpath.compile("//gitinspector/responsibilities/author/name()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

		try {
			
			//File fXmlFile = new File("src/cs410/GitInspectorOutput.xml"); 
			File fXmlFile = new File("src/cs410/ElasticSearchGitInspector.xml"); 
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("author");

			for (int temp = 0; temp < maxAuthorNum; temp++) {
				 
				Node nNode = nList.item(temp);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;

					outputArray[temp][0] = eElement.getElementsByTagName("name").item(0).getTextContent();
							
					for (int i=1; i < maxFileNum+1; i++)
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
		
		*/		
		return outputArray;

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
