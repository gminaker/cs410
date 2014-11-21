package testing;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cs410.CoberturaXMLOutputParser;

public class CoberturaXMLOutputParserTest {
	
	Document testDoc;
	Element testElement;
	NodeList testNodes;
	NodeList testMethods;
	
	@Before
	public void initialization() {
		
//		CoberturaXMLOutputParser.outputTable = new Hashtable<String, Double>();
		
		File testFile = new File("test/test.xml");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = docFactory.newDocumentBuilder();
			testDoc = dBuilder.parse(testFile);
			
			testElement = testDoc.getDocumentElement();
			testNodes = testElement.getChildNodes();
			testMethods = null;
			for (int i = 0; i < testNodes.getLength(); i++) {

				Node cNode = testNodes.item(i);
				if ((cNode instanceof Element)
						&& (cNode.getNodeName() == "packages")) {

					testMethods = cNode.getChildNodes();
					System.out.println("getting packages nodes");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddToHash() {
//		NamedNodeMap input = new NamedNodeMap();
	}

}
