package visualization;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import cs410.fuser;

//Draws a graph given the parent node
public class visualizer extends PApplet {

	PGraphics pg;
	public static final int SIZE_WIDTH = 1000;
	public static final int SIZE_HEIGHT = 1000;
	public Node graph;
	public PApplet p;
	public static String xmlFilepath;
	public static String htmlFilepath;

	// main method to allow us to run as an application instead of a applet
	public static void main(String args[]) {
//		if (args.length == 2) {
//			xmlFilepath = args[0];
//			htmlFilepath = args[1];
//		} else {
//			System.out
//					.println("Incorrect number of arguments correct number is 2");
//			return;
//		}
		PApplet.main(new String[] { "--present", "visualization.visualizer" });
	}

	/**
	 * Function to get processing going.
	 */
	public void setup() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		pg = createGraphics(SIZE_WIDTH, SIZE_HEIGHT);
		fuser resultFuser = new fuser();
		Node drawGraph = resultFuser.fuse();
		graph = drawGraph;
	}

	/**
	 * Function to get processing going.
	 */
	public void draw() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		background(250, 250, 250);
		drawTestGraph();
	}

	/**
	 * Coordinates the drawing of the entire graph, including the generation of
	 * node coordinates, drawing of nodes, and drawing of lines.
	 * 
	 */
	public void drawTestGraph() {
		generateCoordinate(graph, SIZE_WIDTH / 2, SIZE_HEIGHT / 2);
		drawLine(graph);
		drawNode(graph);
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
		text(node.getName(), node.getLat() - 7, node.getLongt() + 5);
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
	public static int calculateNodeWidth(Node node) {
		return 25 + (node.getName().length() * 7);
	}

	/**
	 * 
	 * 
	 * @param node
	 */
	public void drawLine(Node node) {
		int[] rgb = { 0, 0, 0 };
		if (node.getParent() != null) {
			fill(rgb[0], rgb[1], rgb[2]);
			line(node.getLat(), node.getLongt(), node.getParent().getLat(),
					node.getParent().getLongt());
		}
		drawLines(node.getEdges(), node.getEdges().size());
	}

	/**
	 * Given a parent node, draws the lines between it and its children.
	 * recurses through nodes until end.
	 * 
	 * @param testGraph
	 */
	public void drawLines(ArrayList edges, int n) {
		if (n == 0) {
			return;
		} else {
			drawLine((Node) edges.get(n - 1));
			drawLines(edges, n - 1);
		}

	}

	/**
	 * Mutually recursive with generateCoordinates to traverse nodes and
	 * generate coordinates at each one.
	 */
	public static void generateCoordinate(Node node, float x, float y) {
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
	public static void generateCoordinates(ArrayList<Node> nodes, int n, float x,
			float y) {
		if (n == 0) {
			return;
		} else {
			float x_new;
			float y_new;

			float radius = generateRadius(nodes.get(n - 1), nodes.size());
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
	public static int generateRadius(Node node, int siblingCount) {
		return 10 
			  + (node.getNumNodes() * 15)
			  + (node.getNumNodes() * siblingCount * 5);
	}

}
