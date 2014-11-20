package Test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import cs410.gitInspectorParser;

public class gitInspectorXMLParserTest {

	private static gitInspectorParser GitInspectorParser;
	
	private static int testMaxFileNum = GitInspectorParser.maxFileNum;
	private static int testMaxauthorNum = GitInspectorParser.maxAuthorNum;
			
	static Object[][] testOutputArray = new Object[testMaxFileNum][testMaxauthorNum+1];	
	
	@Test
	// Output array is not null and correctly formatted as 6 x 7 
	public void returnParsedArray_OutputArrayFormat_HappyPath() {
		try {
			testOutputArray = gitInspectorParser.returnParsedArray("C:/Git/cs410/src/Test/TestGitInspectorOutput.xml");
			Assert.assertNotNull(testOutputArray);
			
			int length = testOutputArray.length;
			Assert.assertEquals(testMaxFileNum, length);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void returnParsedArray_GetAuthors_HappyPath() {
		try {
			testOutputArray = gitInspectorParser.returnParsedArray("C:/Git/cs410/src/Test/TestGitInspectorOutput.xml");
			Assert.assertNotNull(testOutputArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void returnParsedArray_GetFileNames_HappyPath() {
		try {
			testOutputArray = gitInspectorParser.returnParsedArray("C:/Git/cs410/src/Test/TestGitInspectorOutput.xml");
			Assert.assertNotNull(testOutputArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
