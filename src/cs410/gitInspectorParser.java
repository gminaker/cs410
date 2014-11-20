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

	public static int maxAuthorNum = 6;
	public static int maxFileNum = 6;

	static Object[][] outputArray = new Object[maxFileNum][maxAuthorNum + 1];

	public static void main(String[] args) {
		try {
			Object[][] testOutput = returnParsedArray("C:/Git/cs410/bin/cs410/GitInspectorElasticSearch.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static TreeMap<String, Double> getAuthors(Document doc, XPath xpath) {

		TreeMap<String, Double> AuthorTable = new TreeMap<String, Double>();

		try {
			XPathExpression exprAuthor = xpath
					.compile("/gitinspector/responsibilities/authors/author/name/text()");
			Object resultAuthor = (NodeList) exprAuthor.evaluate(doc,
					XPathConstants.NODESET);
			NodeList nodesAuthor = (NodeList) resultAuthor;

			XPathExpression exprRow = xpath
					.compile("/gitinspector/responsibilities/authors/author/files/file/rows/text()");
			Object resultRow = (NodeList) exprRow.evaluate(doc,
					XPathConstants.NODESET);
			NodeList nodesRow = (NodeList) resultRow;

			for (int i = 0; i < nodesAuthor.getLength(); i++) {
				AuthorTable.put(nodesAuthor.item(i).getNodeValue(),
						Double.valueOf(nodesRow.item(i).getNodeValue()));
			}

			for (String key : AuthorTable.keySet()) {
				// System.out.println(key + ":" + AuthorTable.get(key));
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return AuthorTable;
	}

	private static ArrayList<String> getTop6Authors(Document doc, XPath xpath) {

		ArrayList<String> top6Authors = new ArrayList<String>();

		TreeMap<Double, String> AuthorTable = new TreeMap<Double, String>();

		try {
			XPathExpression exprAuthor = xpath
					.compile("/gitinspector/blame/authors/author/name/text()");
			Object resultAuthor = (NodeList) exprAuthor.evaluate(doc,
					XPathConstants.NODESET);
			NodeList nodesAuthor = (NodeList) resultAuthor;

			XPathExpression exprRow = xpath
					.compile("/gitinspector/blame/authors/author/rows/text()");
			Object resultRow = (NodeList) exprRow.evaluate(doc,
					XPathConstants.NODESET);
			NodeList nodesRow = (NodeList) resultRow;

			for (int i = 0; i < nodesAuthor.getLength(); i++) {
				AuthorTable.put(
						Double.valueOf(nodesRow.item(i).getNodeValue()),
						nodesAuthor.item(i).getNodeValue());
			}

			/*
			 * System.out.println("..........................."); for (Double
			 * key : AuthorTable.keySet()) { System.out.println(key + ":" +
			 * AuthorTable.get(key)); }
			 */
			int size = AuthorTable.size();
			/*
			 * System.out.println(size); System.out.println((String)
			 * AuthorTable.values().toArray()[size-1]);
			 */

			for (int i = 1; i < 7; i++) {
				top6Authors.add((String) AuthorTable.values().toArray()[size
						- i]);
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return top6Authors;
	}

	private static List<String> getFileNames(Document doc, XPath xpath) {
		List<String> list = new ArrayList<>();
		try {

			XPathExpression expr = xpath
					.compile("/gitinspector/responsibilities/authors/author/files/file/name/text()");
			Object result = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;

			for (int i = 0; i < nodes.getLength(); i++) {
				list.add(nodes.item(i).getNodeValue());
				// System.out.println(nodes.item(i).getNodeValue());
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return list;
	}

	private static List<String> getTop6FileNamesForTopAuthors(Document doc,
			XPath xpath, ArrayList<String> topSixAuthorsList) {

		List<String> list = new ArrayList<>();
		try {

			XPathExpression exprName = xpath
					.compile("/gitinspector/responsibilities/authors/author/name/text()");
			Object resultName = (NodeList) exprName.evaluate(doc,
					XPathConstants.NODESET);
			NodeList nodesName = (NodeList) resultName;

			XPathExpression exprFile = xpath
					.compile("/gitinspector/responsibilities/authors/author/files/file/name/text()");
			Object resultFile = (NodeList) exprFile.evaluate(doc,
					XPathConstants.NODESET);
			NodeList nodesFile = (NodeList) resultFile;

			/*
			 * System.out.println("******************************");
			 * System.out.println(nodesName.getLength());
			 */
			for (int j = 0; j < topSixAuthorsList.size(); j++) {
				for (int i = 0; i < nodesName.getLength(); i++) {
					String ii = nodesName.item(i).getNodeValue();
					String jj = topSixAuthorsList.get(j);

					if (ii.equals(jj)) {
						list.add(ii);
						list.add(nodesFile.item(i).getNodeValue());
						list.add(nodesFile.item(i + 1).getNodeValue());
						list.add(nodesFile.item(i + 2).getNodeValue());
						list.add(nodesFile.item(i + 3).getNodeValue());
						list.add(nodesFile.item(i + 4).getNodeValue());
						list.add(nodesFile.item(i + 5).getNodeValue());
					}
				}
			}

			// for ( String listContent : list)
			// System.out.println(listContent);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static Object[][] returnParsedArray(String filepath)
			throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = null;

		try {
			builder = factory.newDocumentBuilder();

			Document doc = builder.parse(filepath);

			XPathFactory xpathfactory = XPathFactory.newInstance();
			XPath xpath = xpathfactory.newXPath();

			TreeMap<String, Double> authorTable = new TreeMap<String, Double>();
			List<String> fileNameTable = getFileNames(doc, xpath);

			ArrayList<String> top6Authors = getTop6Authors(doc, xpath);
			List<String> authorAndFileList = getTop6FileNamesForTopAuthors(doc,
					xpath, top6Authors);

			int count = 0;

			for (int temp = 0; temp < maxAuthorNum; temp++) {

				for (int i = 0; i < maxFileNum + 1; i++) {
					outputArray[temp][i] = authorAndFileList.get(count);
					count++;
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		// testing
		for (int i = 0; i < outputArray.length; i++) {
			for (int j = 0; j < outputArray[i].length; j++) {
				System.out.print(outputArray[i][j]);
				if (j < outputArray[i].length - 1)
					System.out.print(" ");
			}
			System.out.println();
		}

		return outputArray;
	}
}
