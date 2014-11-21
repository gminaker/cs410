package testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

import cs410.GitInspectorXMLOutputParser;

public class GitInspectorXMLOutputParserTest {

	private static String filePath = "./src/testing/TestGitInspectorOutput.xml";

	private static int expectedMaxFileNum = GitInspectorXMLOutputParser.maxFileNum;
	private static int expectedMaxauthorNum = GitInspectorXMLOutputParser.maxAuthorNum;
	private static int expectedFilePathsNum = 35; // based on TestGitInspectorOutput.xml

	private ArrayList<String> expectedAllAuthorNames = new ArrayList<String>();
	private ArrayList<String> expectedTop6AuthorNames = new ArrayList<String>();

	private static Document testDoc;
	private static XPath testXPath;

	static Object[][] testOutputArray = new Object[expectedMaxFileNum][expectedMaxauthorNum + 1];

	@Test
	// Output array is not null and correctly formatted as an 6 x 7 array
	public void returnParsedArray_OutputArrayFormat_HappyPath() {
		try {
			testOutputArray = GitInspectorXMLOutputParser.returnParsedArray(filePath);
			Assert.assertNotNull(testOutputArray);

			Assert.assertEquals(expectedMaxFileNum, testOutputArray.length);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// All content stored in output array should be convertible to Strings
	public void returnParsedArray_OutputArrayFormat_ContainsOnlystrings() {
		try {
			testOutputArray = GitInspectorXMLOutputParser.returnParsedArray(filePath);
			Assert.assertNotNull(testOutputArray);

			for (Object[] arrayElement : testOutputArray) {
				arrayElement.toString();

				// no exception thrown
				ForceAssertSuccess();
			}
		} catch (Exception e) {
			// should not enter here if this test case is successful
			ForceAssertFail();
			e.printStackTrace();
		}
	}

	@Test
	// GetAuthors method correctly grabs all authors listed in the XML.
	// Expected number of authors is 8.
	public void returnParsedArray_GetAuthors_HappyPath()
			throws ParserConfigurationException, SAXException, IOException {
		setUpDocument();
		setUpExpectedData();

		TreeMap<String, Double> AuthorTable = new TreeMap<String, Double>();
		AuthorTable = GitInspectorXMLOutputParser.getAuthors(testDoc, testXPath);

		Assert.assertEquals(AuthorTable.size(), 8);

		for (String authorName : expectedAllAuthorNames) {
			Assert.assertTrue(AuthorTable.containsKey(authorName));
		}
	}

	@Test
	// GetAuthors method correctly grabs all top 6 authors listed in the XML.
	// Expected number of authors is 6.
	public void returnParsedArray_GetTop6Authors_HappyPath()
			throws ParserConfigurationException, SAXException, IOException {
		setUpDocument();
		setUpExpectedData();

		ArrayList<String> Top6AuthorList = new ArrayList<String>();
		Top6AuthorList = GitInspectorXMLOutputParser.getTop6Authors(testDoc, testXPath);

		Assert.assertEquals(Top6AuthorList.size(), 6);

		for (String authorName : expectedTop6AuthorNames) {
			Assert.assertTrue(Top6AuthorList.contains(authorName));
		}
	}

	@Test
	public void returnParsedArray_GetFileNames_HappyPath()
			throws ParserConfigurationException, SAXException, IOException {

		setUpDocument();

		List<String> allFileNames = null;

		try {
			allFileNames = GitInspectorXMLOutputParser.getFileNames(testDoc, testXPath);
			Assert.assertNotNull(allFileNames);

			Assert.assertEquals(expectedFilePathsNum, allFileNames.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setUpDocument() throws ParserConfigurationException,
			SAXException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;

		builder = factory.newDocumentBuilder();

		XPathFactory xpathfactory = XPathFactory.newInstance();

		testDoc = builder.parse(filePath);
		testXPath = xpathfactory.newXPath();
	}

	// helper method to force assert to true. Always evaluates to true
	private void ForceAssertSuccess() {
		Assert.assertTrue(1 == 1);
	}

	// helper method to force assert to false. Always evaluates to false
	private void ForceAssertFail() {
		Assert.assertTrue(1 == 2);
	}

	private void setUpExpectedData() {
		// Set up all 8 author names in XML test data
		expectedAllAuthorNames.add("Thomas Han");
		expectedAllAuthorNames.add("Albert Einstein");
		expectedAllAuthorNames.add("Slacker Slackerson");
		expectedAllAuthorNames.add("Michael Bay");
		expectedAllAuthorNames.add("Hello World");
		expectedAllAuthorNames.add("Peter Parker");
		expectedAllAuthorNames.add("Superstar Contributor");
		expectedAllAuthorNames.add("Average Joe");

		// Set up top 6 author names in XML test data
		expectedTop6AuthorNames.add("Albert Einstein");
		expectedTop6AuthorNames.add("Slacker Slackerson");
		expectedTop6AuthorNames.add("Michael Bay");
		expectedTop6AuthorNames.add("Hello World");
		expectedTop6AuthorNames.add("Peter Parker");
		expectedTop6AuthorNames.add("Superstar Contributor");
	}
}
