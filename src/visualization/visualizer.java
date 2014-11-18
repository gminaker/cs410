package visualization;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import cs410.HTMLParser;
import cs410.fuser;
import cs410.xmlParser;

//Draws a graph given the parent node
public class visualizer extends PApplet {

	PGraphics pg;
	public static final int SIZE_WIDTH = 1100;
	public static final int SIZE_HEIGHT = 1000;
	public Node graph;
	public PApplet p;
	public static String xmlFilepath;
	public static String htmlFilepath;

	// main method to allow us to run as an application instead of a applet
	public static void main(String args[]) {

		// if (args.length == 2) {
		// xmlFilepath = args[0];
		// htmlFilepath = args[1];
		// } else {
		// System.out
		// .println("Incorrect number of arguments correct number is 2");
		// return;
		// }

		PApplet.main(new String[] { "--present", "visualization.visualizer" });
	}

	/**
	 * Function to get processing going.
	 */
	public void setup() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		pg = createGraphics(SIZE_WIDTH, SIZE_HEIGHT);
		//xmlParser xParser = new xmlParser();
		//String[] filepaths = new String[4];
		//Object[][] htmlParserOutput = HTMLParser.complexityAndClassNamesForFilepaths(filepaths);
		fuser resultFuser = new fuser();
		Node drawGraph = resultFuser.fuse();
		graph = drawGraph;
		frameRate(30);
	}

	/**
	 * Function to get processing going.
	 */
	public void draw() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		PImage img = loadImage("sky.jpg");
		img.resize(SIZE_WIDTH, SIZE_HEIGHT);
		background(img);
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
		
		if(node.getDepth() ==  1){
			
			width = 150;
			PImage img = loadImage("soil.png");
			img.resize(width, width);
			image(img, node.getLat() - (width/2), node.getLongt() - (width/2), width, width);
			fill(250,250,250);
			textSize(26); 
			text(node.getName(), (float) (node.getLat() - (node.getName().length() * 3.8)), node.getLongt() + 5);
			fill(50);
			textSize(14); 
			
			//ellipse(node.getLat(), node.getLongt(), width, 25);
			
			
		}else if(node.getDepth() ==  2){
			width = 100;
			PImage img = loadImage("flower-2.png");
			img.resize(width, width);
			image(img, node.getLat() - (width/2), node.getLongt() - (width/2), width, width);
			//fill(250,250,250);
			//textSize(15); 
			text(node.getName(), (float) (node.getLat() - (node.getName().length() * 3.8)), node.getLongt() + 5);
			//fill(50);
			//textSize(14); 
			
		}else{
			ellipse(node.getLat(), node.getLongt(), width, width);	
			fill(50);
			text(node.getName(), (float) (node.getLat() - (node.getName().length() * 3.8)), node.getLongt() + 5);
		}
		
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
		int[] rgb_white = { 250, 250, 250 };
		int[] rgb_green = { 45, 186, 73 };
		if (node.getParent() != null) {
			
			if(node.getParent().getDepth() == 1){
				
				strokeWeight(10);
				stroke(rgb_green[0], rgb_green[1], rgb_green[2]);
				line(node.getLat(), node.getLongt(), node.getParent().getLat(),
						node.getParent().getLongt());
				strokeWeight(1);
				stroke(rgb_white[0], rgb_white[1], rgb_white[2]);
				
				//place image of leaf
				float x1 = node.getParent().getLat();
				float x2 = node.getLat();
				
				float y1 = node.getParent().getLongt();
				float y2 = node.getLongt();
				
				PImage leaf = loadImage("leaf.png");
				
				float leaf_x = calculateLeafXPosition(x1, x2);
				float leaf_y = calculateLeafYPosition(y1, y2);
				
				image(leaf, leaf_x, leaf_y, 50, 50);
				
			}else{
				fill(rgb_white[0], rgb_white[1], rgb_white[2]);
				strokeWeight(2);
				line(node.getLat(), node.getLongt(), node.getParent().getLat(),
						node.getParent().getLongt());
				strokeWeight(1);
			}
			
			
		}
		drawLines(node.getEdges(), node.getEdges().size());
	}

	private float calculateLeafYPosition( float y1, float y2) {
		return (2* y1 + y2)/3;
	}

	private float calculateLeafXPosition( float x1, float x2) {
		return (2* x1 + x2)/3;
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
		Random rand = new Random();
		float next_x = rand.nextFloat();
		float random_x = (float) x + next_x * 1;
		
		float next_y = rand.nextFloat();
		float random_y = (float) y + next_y * 1;
		
		node.setLat(random_x);
		node.setLongt(random_y);
		
		generateCoordinates(node.getEdges(), node.getEdges().size(), random_x, random_y);
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
	public static void generateCoordinates(ArrayList<Node> nodes, int n,
			float x, float y) {
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
		
		if (node.getParent().getDepth() == 1){
		return 250 + (node.getNumNodes() * 20);
				//+ (siblingCount * 10);
		}
		else{
			return 110;
		}
	}

}
