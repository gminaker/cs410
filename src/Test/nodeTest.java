package Test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import visualization.Node;

public class nodeTest {
	private Node testNode;

	@Before
	public void createTestNode() {
		testNode = new Node("testNode");
	}

	@After
	public void destroyTestNode() {
		testNode = null;
	}

	// ensures that lat value is set correctly and is persisted
	@Test
	public void testSetandGetNodeLat() {
		assertEquals("Test before set", 0, testNode.getLat(), 0);
		testNode.setLat(35);
		assertEquals("Test after set", 35, testNode.getLat(), 0);
	}

	// ensures that longt value is set correctly and is persisted
	@Test
	public void testSetandGetNodeLong() {
		assertEquals("Test before set", 0, testNode.getLongt(), 0);
		testNode.setLongt(35);
		assertEquals("Test after set", 35, testNode.getLongt(), 0);
	}

}
