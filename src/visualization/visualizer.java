package visualization;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import cs410.fuser;

//Test
public class visualizer extends PApplet {

	PGraphics pg;
	public static final int SIZE_WIDTH = 500;
	public static final int SIZE_HEIGHT = 500;

	/**
	 * Creates a dummy graph by making an initial node, adding 6 author nodes,
	 * and each author node has 4 class nodes.
	 * 
	 * @return
	 */
	public Node createDummyGraph() {
		int[] projectColor = { 79, 67, 255 };
		Node tempNode = new Node("Project");
		tempNode.setColor(projectColor);

		int[] authorColor = { 255, 255, 0 };
		int[] classColor = { 0, 255, 43 };

		// setup the 6 author nodes
		createDummySubNodes(tempNode, 6, "Author", authorColor);

		// for each author node, setup 4 class nodes.
		for (int i = 0; i < tempNode.getEdges().size(); i++) {
			createDummySubNodes(tempNode.getNode(i), 4, "Class", classColor);
		}

		return tempNode;
	}

	/**
	 * generates n number of subnodes, each with the same name, color, and
	 * parent node.
	 * 
	 * @param parent
	 * @param n
	 * @param name
	 * @param rgb
	 */
	public void createDummySubNodes(Node parent, int n, String name, int[] rgb) {

		for (int i = 0; i < n; i++) {
			Node temp = new Node(name);
			temp.setParent(parent);
			temp.setColor(rgb);
			parent.addEdge(temp);
		}
	}

	/**
	 * Function to get processing going.
	 */
	public void setup() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		pg = createGraphics(SIZE_WIDTH, SIZE_HEIGHT);
	}

	/**
	 * Function to get processing going.
	 */
	public void draw() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		background(153, 76, 0);
		drawTestGraph();
	}

	/**
	 * Coordinates the drawing of the entire graph, including the generation of
	 * node coordinates, drawing of nodes, and drawing of lines.
	 * 
	 */
	public void drawTestGraph() {
		fuser f = new fuser();
		Node testGraph = f.fuse();
		generateCoordinate(testGraph, SIZE_WIDTH / 2, SIZE_HEIGHT / 2);
		drawLine(testGraph);
		drawNode(testGraph);
	}

	/**
	 * Draws an individual node on the screen as an ellipse, then recurses
	 * through its children to draw sub-nodes.
	 * 
	 * @param node
	 */
	public void drawNode(Node node) {
		int rgb[] = node.getColor();
		int width = calculateNodeWidth(node);

		fill(rgb[0], rgb[1], rgb[2]);
		ellipse(node.getLat(), node.getLongt(), width, 25);
		fill(50);
		text(node.getName(), node.getLat() - 17, node.getLongt() + 5);
		drawNodes(node.getEdges(), node.getEdges().size());
	}

	/**
	 * recurses through a list of nodes ("edges") n length long, to draw each
	 * node and their sub-nodes.
	 * 
	 * @param edges
	 * @param n
	 */
	public void drawNodes(ArrayList edges, int n) {
		if (n == 0) {
			return;
		} else {
			drawNode((Node) edges.get(n - 1));
			drawNodes(edges, n - 1);
		}
	}

	/**
	 * determines the width of a node ellipse, considering the length of the
	 * node's name.
	 * 
	 * @param node
	 * @return
	 */
	private int calculateNodeWidth(Node node) {
		return 25 + (node.getName().length() * 7);
	}

	/**
	 * Given a parent node, draws the lines between it and its children.
	 * recurses through nodes until end.
	 * 
	 * @param testGraph
	 */
	public void drawLine(Node testGraph) {
		// TODO Auto-generated method stub

	}

	/**
	 * Mutually recursive with generateCoordinates to traverse nodes and
	 * generate coordinates at each one.
	 */
	public void generateCoordinate(Node node, float x, float y) {
		node.setLat(x);
		node.setLongt(y);
		generateCoordinates(node.getEdges(), node.getEdges().size(), x, y);
	}

	/**
	 * Recurses through a list of Nodes, and generates x and y positions for
	 * each node, adding those positions to each Node's lat (x) and long (y)
	 * fields.
	 * 
	 * @param nodes
	 * @param n
	 *            - the length of the array of nodes
	 */
	public void generateCoordinates(ArrayList<Node> nodes, int n, float x,
			float y) {
		if (n == 0) {
			return;
		} else {
			float x_new;
			float y_new;

			int radius = generateRadius(nodes.get(n - 1));
			float degreesPerNode = 360 / nodes.size();
			float thetaDegrees = degreesPerNode * n;
			float thetaRads = radians(thetaDegrees);

			x_new = x + radius * cos(thetaRads);
			y_new = y + radius * sin(thetaRads);

			generateCoordinate(nodes.get(n - 1), x_new, y_new);
			generateCoordinates(nodes, (n - 1), x, y);
		}
	}

	/**
	 * Given a parent node, this algorithm determines how far away a sub-node
	 * should sit, taking into account the number of children of that sub-node.
	 * 
	 * @param node
	 * @return distance
	 */
	private int generateRadius(Node node) {
		return 15 + (node.getNumNodes() * 30);
	}

	/**
	 * Deprecated.
	 */
	// public void drawNodes() {
	// // algorithm for calculation of x y coordinates found at
	// // http://www.mathopenref.com/coordcirclealgorithm.html
	// String tempString;
	// float locint;
	// int centerx = 250;
	// int centery = 250;
	// fill(255, 255, 0);
	// ellipse(250, 250, 25, 25);
	// tempString = nodesToDraw.get(0).getName();
	// fill(50);
	// text(tempString, 250 - 8, 250 + 5);
	// float degreesPerNode = 360 / nodesToDraw.size();
	// int r = 35;
	// for (int i = 1; i < nodesToDraw.size(); i++) {
	// float x;
	// float y;
	// float thetaDegrees = degreesPerNode * i;
	// float thetaRads = radians(thetaDegrees);
	// x = centerx + r * cos(thetaRads);
	// y = centery + r * sin(thetaRads);
	// fill(0, 255, 0);
	// locint = x;
	// ellipse(x, y, 50, 25);
	// fill(0);
	// tempString = nodesToDraw.get(i).getName();
	// text(tempString, x - 5, y + 5);
	// nodesToDraw.get(i).setLat(x);
	// nodesToDraw.get(i).setLongt(y);
	// }
	//
	// for (int i = 1; i < nodesToDraw.size(); i++) {
	// System.out.println(nodesToDraw.get(i).getName());
	// System.out.println(nodesToDraw.get(i).getLat());
	// System.out.println(nodesToDraw.get(i).getLongt());
	// }
	//
	// }

}
