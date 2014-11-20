package Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cs410.gitInspectorParser;

public class gitInspectorXMLParserTest {

	private static gitInspectorParser GitInspectorParser;
	
	private static String filePath = "C:/Git/cs410/src/Test/TestGitInspectorOutput.xml";
	private static int testMaxFileNum = GitInspectorParser.maxFileNum;
	private static int testMaxauthorNum = GitInspectorParser.maxAuthorNum;
	
	private static Document testDoc;
	private static XPath testXPath;
	
	static Object[][] testOutputArray = new Object[testMaxFileNum][testMaxauthorNum+1];	
        
	@Test
	// Output array is not null and correctly formatted as 6 x 7 
	public void returnParsedArray_OutputArrayFormat_HappyPath() {
		try {
			testOutputArray = gitInspectorParser.returnParsedArray(filePath);
			Assert.assertNotNull(testOutputArray);
			
			Assert.assertEquals(testMaxFileNum, testOutputArray.length);

		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Test
	public void returnParsedArray_GetAuthors_HappyPath() throws ParserConfigurationException, SAXException, IOException {
		
		setUpDocument();
		
    	TreeMap<String, Double> AuthorTable = new TreeMap<String, Double>();
    	AuthorTable = gitInspectorParser.getAuthors(testDoc, testXPath);
    	
    	Assert.assertEquals(AuthorTable.size(), 8);
	}
	
	@Test
	public void returnParsedArray_GetFileNames_HappyPath() throws ParserConfigurationException, SAXException, IOException {
		
		setUpDocument();
		
		try {
			testOutputArray = gitInspectorParser.returnParsedArray("C:/Git/cs410/src/Test/TestGitInspectorOutput.xml");
			Assert.assertNotNull(testOutputArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setUpDocument() throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = null;

		builder = factory.newDocumentBuilder();
			
		XPathFactory xpathfactory = XPathFactory.newInstance();

		testDoc = builder.parse(filePath);
		testXPath = xpathfactory.newXPath();
		    
	}
}

