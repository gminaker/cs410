package visualization;

import java.util.ArrayList;

//a class to enable the drawing of directed graphs
public class FlowerNode {

	private String key;
	private FlowerNode parent;
	private ArrayList<FlowerNode> edges;
	private int[] rgb = new int[3];
	private float lat;
	private float longt;
	private double complexity;
	private int depth;

	// FlowerNode constructor for constructing a parentless FlowerNode
	public FlowerNode(String name) {
		this.parent = null;
		this.key = name;
		this.edges = new ArrayList<FlowerNode>();

	}

	// constructor for constructing a FlowerNode with a parent
	public FlowerNode(String name, FlowerNode nodeParent) {
		this.key = name;
		this.edges = new ArrayList<FlowerNode>();
		this.parent = nodeParent;

	}

	public int[] getColor() {
		return rgb;
	}

	public void setColor(int[] c) {
		this.rgb = c;
	}

	public FlowerNode getParent() {
		return parent;
	}

	public void setParent(FlowerNode parent) {
		this.parent = parent;
	}

	// getter method for the node key (in our case the name of the developer)
	public String getName() {
		return this.key;
	}

	// gets the FlowerNode at index i in the arraylist of edges
	public FlowerNode getFlowerNode(int i) {
		return edges.get(i);

	}

	// returns the edges associated with the node
	public ArrayList<FlowerNode> getEdges() {
		return this.edges;

	}

	// adds an edge to the node
	public void addEdge(FlowerNode node) {
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

	public void setComplexity(double complex) {
		this.complexity = complex;

	}

	public double getComplexity() {
		return this.complexity;
	}

	public int getDepth() {
		return this.depth;
	}

	public void setDepth(int newDepth) {
		this.depth = newDepth;
	}

	// returns the number of children that a node and its sub nodes have
	// including its self
	public int getNumNodes() {
		int accumulator = 0;
		int tempNum;

		if (this.edges.size() == 0) {
			return 0;
		} else {
			for (int i = 0; i < this.edges.size(); i++) {
				tempNum = this.edges.get(i).getNumNodes();
				accumulator = accumulator + tempNum + 1;
			}
			return accumulator;
		}
	}

}