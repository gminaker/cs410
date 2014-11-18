package Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

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

	// ensures that complexity value is set correctly and is persisted
	@Test
	public void testSetandGetNodeComplexity() {
		assertEquals("Test before set", 0, testNode.getComplexity(), 0);
		testNode.setComplexity(20);
		assertEquals("Test after set", 20, testNode.getComplexity(), 0);
	}

	// ensures that color value is set correctly and is persisted
	@Test
	public void testSetandGetNodeColour() {
		int[] beforeColour = { 0, 0, 0 };
		int[] afterColour = { 255, 255, 255 };
		assertArrayEquals(beforeColour, testNode.getColor());
		testNode.setColor(afterColour);
		assertArrayEquals(afterColour, testNode.getColor());
	}

	// tests methods associated with node and graph creation
	@Test
	public void testCreateMethods() {
		Node parentNode = new Node("parent");
		Node childNode = new Node("child", parentNode);
		Node childNode2 = new Node("child2", parentNode);
		parentNode.addEdge(childNode);

		assertNull(parentNode.getParent());

		assertSame("Checking that parent Node of childNode was properly set",
				parentNode, childNode.getParent());

		assertEquals("Checking that the child Node has no children", 0,
				childNode.getNumNodes(), 0);

		assertEquals("Checking that the parent Node has exactly 1 child", 1,
				parentNode.getNumNodes());

		parentNode.addEdge(childNode2);

		assertEquals("Checking that the parent Node has exactly 2 children", 2,
				parentNode.getNumNodes());

	}

}
