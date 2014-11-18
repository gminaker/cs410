package cs410;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;

public class gitInspectorParser {
	
	static int maxAuthorNum = 6;
	static int maxFileNum = 6;
	
	public class Authors {
		public String Name;
		public int TopRowCount;
		public java.util.List<java.util.Map.Entry<String,Integer>> Files;
	}
	
	static Object[][] outputArray = new Object[maxFileNum][maxAuthorNum+1];
	static List<Authors> authorList;
	
	static Hashtable<String, String> responsibilities = new Hashtable<String, String>();
	
	public static void main(String[] args) throws Exception {
		giveOutputArray();   
	}	

	
    private static TreeMap<String, Double> getAuthors(Document doc, XPath xpath) {
        
    	TreeMap<String, Double> AuthorTable = new TreeMap<String, Double>();
    	
        try {
	        XPathExpression exprAuthor = xpath.compile("/gitinspector/responsibilities/authors/author/name/text()");
	        Object resultAuthor = (NodeList) exprAuthor.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodesAuthor = (NodeList) resultAuthor;
	        
	        XPathExpression exprRow = xpath.compile("/gitinspector/responsibilities/authors/author/files/file/rows/text()");
	        Object resultRow = (NodeList) exprRow.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodesRow = (NodeList) resultRow;
	        
	        for (int i = 0; i < nodesAuthor.getLength(); i++) {
	        	AuthorTable.put(nodesAuthor.item(i).getNodeValue(), Double.valueOf(nodesRow.item(i).getNodeValue()));
	        }
	        
	        for (String key : AuthorTable.keySet()) {
	            System.out.println(key + ":" + AuthorTable.get(key));
	        }
	        
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        
        return AuthorTable;
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
    
    
	public static Object[][] giveOutputArray() throws Exception {	
		
	       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        DocumentBuilder builder = null;
	        
	        try {
				builder = factory.newDocumentBuilder();

		        Document doc = builder.parse("src/cs410/ElasticSearchGitInspector.xml");
		        
		        XPathFactory xpathfactory = XPathFactory.newInstance();
		        XPath xpath = xpathfactory.newXPath();
		        
		        TreeMap<String, Double> authorTable = new TreeMap<String, Double>();
		        List<String> fileNameTable = getFileNames(doc, xpath);
		        
		        authorTable = getAuthors(doc, xpath);
		        
				for (int temp = 0; temp < maxAuthorNum; temp++) {

					//outputArray[temp][0] = authorTable.keySet();

					//temp
					outputArray[temp][0] = "Adrien Grand";
					outputArray[temp][0] = "Bogdan Dumitrescu";
					outputArray[temp][0] = "Leonardo Menezes";
					outputArray[temp][0] = "Robert Muir";
					outputArray[temp][0] = "tstibbs";
					outputArray[temp][0] = "uboness";
					
					for (int i=1; i < maxFileNum+1; i++)
					{
						String content;
						
						if (fileNameTable != null)
						{
							content = fileNameTable.get(i);
						}
						else
						{
							content = "";
						}
						
						outputArray[temp][i] = content;
					}
				}
				
	        } catch (ParserConfigurationException | SAXException | IOException e) {
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
			
		return outputArray;
	}
}
