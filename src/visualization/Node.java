package visualization;

import java.util.ArrayList;

//a class to enable the drawing of directed graphs
public class Node {

	private String key;
	private ArrayList<Node> edges;
	private int lat;
	private int longt;

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
		edges.add(node);

	}

	public int getLat() {
		return this.lat;
	}

	public int getLongt() {
		return this.longt;
	}

	public void setLat(int newLat) {
		this.lat = newLat;

	}

	public void setLongt(int newLongt) {
		this.longt = newLongt;
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
