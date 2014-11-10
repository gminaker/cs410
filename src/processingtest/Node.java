package processingtest;

import java.util.ArrayList;

//a class to enable the drawing of directed graphs
public class Node {

	private static String key;
	private ArrayList edges;

	// node constructor
	public Node(String name) {
		key = name;

	}

	// getter method for the node key (in our case the name of the developer)
	public String getName() {
		return this.key;

	}

	// returns the edges associated with the node
	public ArrayList getEdges() {
		return this.edges;

	}

	// adds an edge to the node
	public void addEdge(Node node) {
		edges.add(node);

	}

}
