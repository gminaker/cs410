package testing;

import static org.junit.Assert.assertEquals;

import java.util.Hashtable;

import org.junit.BeforeClass;
import org.junit.Test;

import visualization.FlowerNode;
import cs410.OutputFuser;

public class OutputFuserTest {

	private static OutputFuser testFuser;

	// test objects representing sample output from the parsers
	public Object[][] gitNamessssss = { { "a", "c1", "c3", "c8" },
			{ "b", "c1", "c6", "c2" }, { "c", "c2", "c6" },
			{ "d", "c2", "c5" }, { "e", "c4", "c1" }, { "f", "c7" },
			{ "g", "c1", "c2", "c3", "c4", "c5", "c6" }, { "h", "c4" },
			{ "i", "c2" }, { "j", "c1" } };

	public Object[][] locTablessss = { { "c1", 2 }, { "c2", 7 }, { "c3", 5 },
			{ "c4", 2 }, { "c5", 4 }, { "c6", 7 }, { "c7", 3 }, { "c8", 1 },
			{ "c9", 2 }, { "c10", 3 } };
	
	public static Hashtable<String, Double> testHash;

	// create test objects
	@BeforeClass
	public static void setup() {
		testFuser = new OutputFuser();
		testHash = new Hashtable<String, Double>();
		testHash.put("c1", 2.0);
		testHash.put("c2", 7.0);
		testHash.put("c3", 5.0);
		testHash.put("c4", 2.0);
		testHash.put("c5", 4.0);
		testHash.put("c6", 7.0);
		testHash.put("c7", 3.0);
		testHash.put("c8", 1.0);
		testHash.put("c9", 2.0);
		testHash.put("c10", 3.0);
		

	}


	// tests the makeAuthorNode method
	@Test
	public void makeAuthorNodeTest() {
		FlowerNode compareAuthorNode = new FlowerNode("b");
		FlowerNode child1 = new FlowerNode("c1", compareAuthorNode);
		FlowerNode child2 = new FlowerNode("c6", compareAuthorNode);
		FlowerNode child3 = new FlowerNode("c2", compareAuthorNode);
		compareAuthorNode.addEdge(child1);
		compareAuthorNode.addEdge(child2);
		compareAuthorNode.addEdge(child3);

		FlowerNode testParentNode = new FlowerNode("api");
		FlowerNode testAuthorNode = testFuser.makeAuthorNode("b", testParentNode,
				testHash, gitNamessssss, 1);
		assertEquals("Ensuring correct number of children are created",
				compareAuthorNode.getEdges().size(), testAuthorNode.getEdges()
						.size(), 0);

	}

	// tests the makeClassNode method
	@Test
	public void makeClassNodeTest() {
		FlowerNode parentNode = new FlowerNode("b");
		FlowerNode compareClassNode = new FlowerNode("c4");
		compareClassNode.setComplexity(2);
		FlowerNode testClassNode = testFuser.makeClassNode("c4", parentNode,
				testHash);
		assertEquals(compareClassNode.getName(), testClassNode.getName());
		assertEquals("Comparing class node complexity values with test node",
				compareClassNode.getComplexity(),
				testClassNode.getComplexity(), 0);
	}

}