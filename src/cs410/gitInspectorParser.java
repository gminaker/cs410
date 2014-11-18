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
import javax.xml.xpath.XPathExpressionException;
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

	        Document doc = builder.parse("src/cs410/ElasticSearchGitInspector.xml");
	        
	        XPathFactory xpathfactory = XPathFactory.newInstance();
	        XPath xpath = xpathfactory.newXPath();
	        
	        getAuthors(doc, xpath);
	        getFileNames(doc, xpath);
	        getRows(doc, xpath);
	        
        } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
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
	
    private static List<String> getAuthors(Document doc, XPath xpath) {
        List<String> list = new ArrayList<>();
        try {
        	
	        // 7) Get all writers
	        XPathExpression expr = xpath.compile("/gitinspector/responsibilities/authors/author/name/text()");
	        Object result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodes = (NodeList) result;
	        System.out.println(nodes.getLength());
	        for (int i = 0; i < nodes.getLength(); i++) {
	        	list.add(nodes.item(i).getNodeValue());
	            System.out.println(nodes.item(i).getNodeValue());
	        }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    private static List<String> getFileNames(Document doc, XPath xpath) {
        List<String> list = new ArrayList<>();
        try {
        	
	        // 7) Get all writers
	        XPathExpression expr = xpath.compile("/gitinspector/responsibilities/authors/author/files/file/name/text()");
	        Object result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodes = (NodeList) result;
	        System.out.println(nodes.getLength());
	        for (int i = 0; i < nodes.getLength(); i++) {
	        	list.add(nodes.item(i).getNodeValue());
	            System.out.println(nodes.item(i).getNodeValue());
	        }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    private static List<String> getRows(Document doc, XPath xpath) {
        List<String> list = new ArrayList<>();
        try {
        	
	        // 7) Get all writers
	        XPathExpression expr = xpath.compile("/gitinspector/responsibilities/authors/author/files/file/rows/text()");
	        Object result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodes = (NodeList) result;
	        System.out.println(nodes.getLength());
	        for (int i = 0; i < nodes.getLength(); i++) {
	        	list.add(nodes.item(i).getNodeValue());
	            System.out.println(nodes.item(i).getNodeValue());
	        }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }
}
