package cs410;

import java.io.File;
import java.util.Hashtable;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xmlParser {

	static String cMethodClass = null;
	static double crapScore = 0;
	static NodeList cMethodChildren = null;

	static Hashtable<String, Double> classMethodCount = new Hashtable<String, Double>();
	static Hashtable<String, Double> classCrapCount = new Hashtable<String, Double>();

	static double updateClassMethodCount;
	static double updateClassCrapCount;

	public static void main(String[] args) {
		parseXML();
	}

	// parsesXML file containing output from CRAP analyzer will eventually take
	// a file path as an input and will return a 2D array with each row
	// containing class name, number of methods in the class, and the average
	// CRAP score
	// Note: Appropriate content is produced, but heavy refactoring will be
	// needed in sprint 2 to make execution more gracefull and to remove some
	// hard coded values
	public static void parseXML() {

		try {
			// Initialization code modified from tutorial
			// http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
			File crapFile = new File("/home/noah/report.xml");
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
						&& (cNode.getNodeName() == "methods")) {

					cMethods = cNode.getChildNodes();
					System.out.println("getting method list");
				}
			}

			for (int i = 0; i < cMethods.getLength(); i++) {

				Node cMethod = cMethods.item(i);
				if ((cMethod instanceof Element)
						&& (cMethod.getNodeName() == "method")) {
					cMethodChildren = cMethod.getChildNodes();
					cMethodClass = cMethodChildren.item(3).getTextContent()
							.trim();
					crapScore = Double.parseDouble(cMethodChildren.item(11)
							.getTextContent().trim());

					populateTables(cMethodClass);

					/*
					 * Test code System.out.println("classMethodCount:");
					 * System.out.println(classMethodCount.toString());
					 * System.out.println("classCrapCount:");
					 * System.out.println(classCrapCount.toString());
					 */

				}
			}

			// creates a 2d array containing class name, average CRAP score for
			// the class and the number of different methods in that class
			Object[][] outputArray = new Object[cMethods.getLength()][3];
			Set<String> keys = classCrapCount.keySet();
			Object[] keyArray = keys.toArray();
			for (int i = 0; i < keys.size(); i++) {
				outputArray[i][0] = keyArray[i];
				outputArray[i][1] = classMethodCount.get(keyArray[i]);
				outputArray[i][2] = (classCrapCount.get(keyArray[i]))
						/ (classMethodCount.get(keyArray[i]));

			}

			/*
			 * for (int i = 0; i < cMethods.getLength(); i++) { for (int j = 0;
			 * j < 3; j++) { System.out.print(" " + outputArray[i][j] + " "); }
			 * System.out.println(); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// updates hashtables containing number of occurrences of
	// methods from a class, and
	public static void populateTables(String cClass) {

		if (classCrapCount.containsKey(cClass)) {
			updateClassMethodCount = classMethodCount.get(cClass) + 1.00;
			classMethodCount.put(cClass, updateClassMethodCount);
			updateClassCrapCount = classCrapCount.get(cClass) + crapScore;
			classCrapCount.put(cMethodClass, updateClassCrapCount);

		} else {
			classCrapCount.put(cMethodClass, crapScore);
			classMethodCount.put(cMethodClass, 1.00);

		}
	}
}
