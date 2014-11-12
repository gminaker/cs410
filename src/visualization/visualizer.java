package visualization;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.Table;

//Test
public class visualizer extends PApplet {

	PGraphics pg;
	public static final int SIZE_WIDTH = 500;
	public static final int SIZE_HEIGHT = 500;

	/**
	 * Creates a dummy graph by making an initial node, 
	 * adding 6 author nodes, and each author node has 4 
	 * class nodes. 
	 * 
	 * @return
	 */
	public Node createDummyGraph(){
		Node tempNode = new Node("Project");
		int[] authorColor = {1,2,3};
		int[] classColor = {1,2,3};
		
		// setup the 6 author nodes
		createDummySubNodes(tempNode, 6, "Author", authorColor);
		
		// for each author node, setup 4 class nodes. 
		for(int i=0; i<tempNode.getEdges().size(); i++){
			createDummySubNodes(tempNode.getNode(i), 4, "Class", classColor);
		}
		
		return tempNode;
	}

	public void createDummySubNodes(Node parent, int n, String name, int[] rgb){
		
		for(int i=0; i<n; i++){
			parent.addEdge(new Node("Author"));
		}
	}
	
	public void setup() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		pg = createGraphics(SIZE_WIDTH, SIZE_HEIGHT);
	}

	public void draw() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		background(153, 76, 0);
		drawTestGraph();
	}

	public void drawTestGraph() {
		Node testGraph = createDummyGraph();
		generateCoordinate(testGraph, SIZE_WIDTH/2, SIZE_HEIGHT/2);
		drawLine(testGraph);
		drawNode(testGraph);
	}
	
	
	public void drawNode(Node node){
		//draw this node
		drawNodes(node.getEdges(), node.getEdges().size());
	}
	
	public void drawNodes(ArrayList edges, int n) {
		if(n == 0){
			return;
		}else{
			drawNode(edges.get(n-1));
			drawNodes(edges, n-1);
		}
		
	}

	public void drawLine(Node testGraph) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Mutually recursive with generateCoordinates to 
	 * traverse nodes and generate coordinates at each one. 
	 */
	public void generateCoordinate(Node node, float x, float y){
		node.setLat(x);
		node.setLongt(y);
		generateCoordinates(node.getEdges(), node.getEdges().size(), x, y);
	}

	/**
	 * Recurses through a list of Nodes, and generates coordi
	 * 
	 * @param nodes
	 * @param n - the length of the array of nodes
	 */
	public void generateCoordinates(ArrayList<Node> nodes, int n, float x, float y){
		if(n == 0){
			return;
		}else{
			int r = 35;
			float x_new;
			float y_new;
			float degreesPerNode = 360 / nodes.size();
			float thetaDegrees = degreesPerNode * n;
			float thetaRads = radians(thetaDegrees);
			x_new = x + r * cos(thetaRads);
			y_new = y + r * sin(thetaRads);
			
			generateCoordinate(nodes.get(n - 1), x_new, y_new);
			generateCoordinates(nodes, (n - 1), x, y);
		}	
	}
	
	
	/**
	 * Deprecated.
	 */
//	public void drawNodes() {
//		// algorithm for calculation of x y coordinates found at
//		// http://www.mathopenref.com/coordcirclealgorithm.html
//		String tempString;
//		float locint;
//		int centerx = 250;
//		int centery = 250;
//		fill(255, 255, 0);
//		ellipse(250, 250, 25, 25);
//		tempString = nodesToDraw.get(0).getName();
//		fill(50);
//		text(tempString, 250 - 8, 250 + 5);
//		float degreesPerNode = 360 / nodesToDraw.size();
//		int r = 35;
//		for (int i = 1; i < nodesToDraw.size(); i++) {
//			float x;
//			float y;
//			float thetaDegrees = degreesPerNode * i;
//			float thetaRads = radians(thetaDegrees);
//			x = centerx + r * cos(thetaRads);
//			y = centery + r * sin(thetaRads);
//			fill(0, 255, 0);
//			locint = x;
//			ellipse(x, y, 50, 25);
//			fill(0);
//			tempString = nodesToDraw.get(i).getName();
//			text(tempString, x - 5, y + 5);
//			nodesToDraw.get(i).setLat(x);
//			nodesToDraw.get(i).setLongt(y);
//		}
//
//		for (int i = 1; i < nodesToDraw.size(); i++) {
//			System.out.println(nodesToDraw.get(i).getName());
//			System.out.println(nodesToDraw.get(i).getLat());
//			System.out.println(nodesToDraw.get(i).getLongt());
//		}
//
//	}

}
