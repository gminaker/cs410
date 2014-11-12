package visualization;

import java.util.ArrayList;

//a class to enable the drawing of directed graphs
public class Node {

	private String key;
	private ArrayList<Node> edges;
	private float lat;
	private float longt;
	private int complexity;

	// node constructor
	public Node(String name) {
		key = name;
		edges = new ArrayList<Node>();

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
		this.edges.add(node);

	}

	public float getLat() {
		return this.lat;
	}

	public float getLongt() {
		return this.longt;
	}

	public void setLat(float newLat) {
		this.lat = newLat;

	}

	public void setLongt(float newLongt) {
		this.longt = newLongt;
	}

	public void setComplexity(int complex) {
		this.complexity = complex;

	}

	public int getComplexity() {
		return this.complexity;
	}

	// returns the number of children that a node and its sub nodes have
	// including its self
	public int getNumNodes() {
		int accumulator = 0;
		int tempNum;

		if (this.edges.size() == 0) {
			return 1;
		} else {
			for (int i = 0; i < this.edges.size(); i++) {
				tempNum = this.edges.get(i).getNumNodes();
				accumulator = accumulator + tempNum;
			}
			return accumulator + 1;
		}
	}

}
