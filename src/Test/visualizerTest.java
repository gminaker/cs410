package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import visualization.Node;
import visualization.visualizer;

public class visualizerTest {

	Node parentNode;
	ArrayList<Node> subNodes;
	Node childNode1;
	Node childNode2;
	Node childNode3;
	Node childNode4;
	
	int WIDTH;
	int HEIGHT;
	
    @Before 
    public void initialize() {
    	WIDTH = 750;
    	HEIGHT = 750;
        parentNode = new Node("Parent");
        
        childNode1 = new Node("Child 1");
        childNode2 = new Node("Child 2");
        childNode3 = new Node("Child 3");
        childNode4 = new Node("Child 4");
        
        parentNode.addEdge(childNode1);
        parentNode.addEdge(childNode2);
        parentNode.addEdge(childNode3);
        parentNode.addEdge(childNode4);
        
        subNodes = new ArrayList<Node>();
        subNodes.add(childNode1);
        subNodes.add(childNode2);
        subNodes.add(childNode3);
        subNodes.add(childNode4);
    }
    
	@Test
	public void testCalculateNodeWidth() {
		int actual, expected;
		
		actual = visualizer.calculateNodeWidth(parentNode);
		expected = 25    // base width
				   + (6  // length of node name "parent" 
				   * 7); // multiplier per letter in name
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testGenerateCoordinate(){
		Node expected = new Node("Parent");
		expected.setLat(WIDTH/2);
		expected.setLongt(HEIGHT/2);
		
		visualizer.generateCoordinate(parentNode, WIDTH/2, HEIGHT/2);
		
		float actual_lat = parentNode.getLat();
		float hopeful_lat = expected.getLat();
		
		// check that generateCoordinate is setting lat & long
		// using proper set method
		assertEquals(actual_lat, hopeful_lat, 0);
	
		float actual_long = parentNode.getLongt();
		float hopeful_long = expected.getLongt();
		
		assertEquals(actual_long, hopeful_long, 0);
		
		// check that the actual lat & long is set
		// to the proper values.
		assertEquals(actual_long, HEIGHT/2, 0);
		assertEquals(actual_lat, WIDTH/2, 0);
		
	}
	
	@Test
	public void testGenerateCoordinates(){
		
		// call method on a list of 4 Nodes given the parent node's x and y
		visualizer.generateCoordinates(subNodes, 4, WIDTH/2, HEIGHT/2);
		
		float child1Lat = subNodes.get(0).getLat();
		float child1Long = subNodes.get(0).getLongt();
		
		float child2Lat = subNodes.get(1).getLat();
		float child2Long = subNodes.get(1).getLongt();
		
		float child3Lat = subNodes.get(2).getLat();
		float child3Long = subNodes.get(2).getLongt();
		
		float child4Lat = subNodes.get(3).getLat();
		float child4Long = subNodes.get(3).getLongt();
		
		float expectedChildLat = (float) (visualizer.generateRadius(subNodes.get(0), subNodes.size())
								  * processing.core.PApplet.cos((360 / 4) * 1) // degrees per node
							      + WIDTH/2);
		
		float expectedChild1Lat = 375;
		float expectedChild1Long = 420;
		float expectedChild2Lat = 330;
		float expectedChild2Long = 375;
		float expectedChild3Lat = 375;
		float expectedChild3Long = 330;
		float expectedChild4Lat = 420;
		float expectedChild4Long = 375;
		
		assertEquals(child1Lat, expectedChild1Lat, 1);
		assertEquals(child1Long, expectedChild1Long, 1);
		assertEquals(child2Lat, expectedChild2Lat, 1);
		assertEquals(child2Long, expectedChild2Long, 1);
		assertEquals(child3Lat, expectedChild3Lat, 1);
		assertEquals(child3Long, expectedChild3Long, 1);
		assertEquals(child4Lat, expectedChild4Lat, 1);
		assertEquals(child4Long, expectedChild4Long, 1);
	}
	
	@Test
	public void testGenerateRadius(){
		int expected = 15    // constant
					  + (5   // total nodes from parent
							 // from node.getNumNodes(node) method
					  * 30); // multiplier of each node
		
		int actual = visualizer.generateRadius(parentNode, parentNode.getEdges().size());
		
		assertEquals(expected, actual);
	}

	
}
