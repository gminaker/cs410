package testing;

import static org.junit.Assert.assertEquals;

import java.util.Hashtable;

import org.junit.Test;

import cs410.CoberturaXMLOutputParser;


public class CoberturaXMLOutputParserTest {
	
	Hashtable<String, Double> outputTable = new Hashtable<String, Double>();
	String testFilepath = "src/testing/test.xml";
	CoberturaXMLOutputParser outputParser = new CoberturaXMLOutputParser();
	int expectedTableSize = 1;
	String expectedClassName = "CoverageTest.java";
	Double expectedComplexity = 0.0;
	
	@Test
	// Tests output of parser for mock XML file
	public void testParseXML() {
		outputTable = outputParser.parseXML(testFilepath);
		assertEquals(expectedTableSize, outputTable.size());
		assertEquals(expectedComplexity, outputTable.get(expectedClassName));
	}
	

}
