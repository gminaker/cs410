package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import visualization.Node;
import cs410.fuser;

public class fuserTest {

	private static fuser testFuser;
	private Object[][] xmlParserOutput;
	private Object[][] htmlParserOutput;

	public Object[][] gitNamessssss = { { "a", "c1", "c3", "c8" },
			{ "b", "c1", "c6", "c2" }, { "c", "c2", "c6" },
			{ "d", "c2", "c5" }, { "e", "c4", "c1" }, { "f", "c7" },
			{ "g", "c1", "c2", "c3", "c4", "c5", "c6" }, { "h", "c4" },
			{ "i", "c2" }, { "j", "c1" } };

	public Object[][] locTablessss = { { "c1", 2 }, { "c2", 7 }, { "c3", 5 },
			{ "c4", 2 }, { "c5", 4 }, { "c6", 7 }, { "c7", 3 }, { "c8", 1 },
			{ "c9", 2 }, { "c10", 3 } };

	// create test objects
	@BeforeClass
	public static void setup() {
		testFuser = new fuser();

	}

	@Test
	public void makeAPINodeTest() {
		fail("Not yet implemented");
	}

	@Test
	public void makeAuthorNodeTest() {
		Node compareAuthorNode = new Node("b");
		Node child1 = new Node("c1", compareAuthorNode);
		Node child2 = new Node("c6", compareAuthorNode);
		Node child3 = new Node("c2", compareAuthorNode);
		compareAuthorNode.addEdge(child1);
		compareAuthorNode.addEdge(child2);
		compareAuthorNode.addEdge(child3);

		Node testParentNode = new Node("api");
		Node testAuthorNode = testFuser.makeAuthorNode("b", testParentNode,
				locTablessss, gitNamessssss, 1);
		assertEquals("Ensuring correct number of children are created",
				compareAuthorNode.getEdges().size(), testAuthorNode.getEdges()
						.size(), 0);

		Node testChildNode;
		Node compareChildNode;

		testChildNode = (Node) testAuthorNode.getEdges().get(1);
		compareChildNode = (Node) compareAuthorNode.getEdges().get(1);

	}

	@Test
	public void makeClassNodeTest() {
		Node parentNode = new Node("b");
		Node compareClassNode = new Node("c4");
		compareClassNode.setComplexity(2);
		Node testClassNode = testFuser.makeClassNode("c4", parentNode,
				locTablessss);
		assertEquals(compareClassNode.getName(), testClassNode.getName());
		assertEquals("Comparing class node complexity values with test node",
				compareClassNode.getComplexity(),
				testClassNode.getComplexity(), 0);
	}

}
