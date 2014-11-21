package visualization;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import cs410.CoberturaXMLOutputParser;
import cs410.OutputFuser;
import cs410.GitInspectorXMLOutputParser;

//Draws a graph given the parent node
public class FlowerVisualizer extends PApplet {

	PGraphics pg;
	public static final int SIZE_WIDTH = 1100;
	public static final int SIZE_HEIGHT = 1000;
	public FlowerNode graph;
	public PApplet p;
	public static String codeBasename;
	public static String coberturaFilePath;
	public static String gitInspectorFilePath;
	
//	public static String gitInspectorElasticSearchFilepath = "cs410/GitInspectorElasticSearch.xml";
//	public static String gitInspectorJenkinsFilepath = "cs410/GitInspectorJenkins.xml";
//	public static String coberturaElasticSearchFilepath = "codebase/elasticSearch/target/site/cobertura";
//	public static String coberturaJenkinsFilepath = "codebase/jenkins/core/target/site/cobertura";

	public static String gitInspectorElasticSearchFilepath = "gitinspectorOutput/elasticsearchtest2.xml";
	public static String coberturaElasticSearchFilepath = "codebase/elasticSearch/target/site/cobertura/coverage.xml";

	
	public static void main(String[] args) {
		codeBasename = args[0];
		coberturaFilePath = args[1];
		gitInspectorFilePath = args[2];
		PApplet.main(new String[] { "--present", "visualization.FlowerVisualizer" });
		
	}

	/**
	 * Function to get processing going.
	 */
	public void setup() {
		System.out.println(codeBasename);
		System.out.println(coberturaFilePath);
		System.out.println(gitInspectorFilePath);
			
		size(SIZE_WIDTH, SIZE_HEIGHT);
		pg = createGraphics(SIZE_WIDTH, SIZE_HEIGHT);
		try {
			
			

			
			//GitInspector
			GitInspectorXMLOutputParser gitParser = new GitInspectorXMLOutputParser();
		    Object[][] gitParserOutput = gitParser.returnParsedArray(gitInspectorFilePath);
		    
		    //Cobertura
		    CoberturaXMLOutputParser cobParser = new CoberturaXMLOutputParser();
		    //cobParser.parseXML();
		    Hashtable<String, Double> coberturaParseOutput = cobParser.parseXML(coberturaFilePath);
		    
		    //Fuser
			OutputFuser resultFuser = new OutputFuser();
			FlowerNode drawGraph = resultFuser.makeAPINode(codeBasename, coberturaParseOutput, gitParserOutput);
			graph = drawGraph;

			//Visualization
			frameRate(30);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to get processing going.
	 */
	public void draw() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		translate(100,100);
		scale((float) 0.8);
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
	public void drawNode(FlowerNode node) {
		int rgb[] = node.getColor();
		int width = calculateNodeWidth(node);
		String truncatedName = truncateName(node.getName());
		
		fill(rgb[0], rgb[1], rgb[2]);
		textAlign(CENTER, CENTER);
		
		if(node.getDepth() ==  1){
			// Draw the very center for the project
			width = 150;
			PImage img = loadImage("soil.png");
			img.resize(width, width);
			image(img, node.getLat() - (width/2), node.getLongt() - (width/2), width, width);
			fill(250,250,250);
			textSize(26); 
			//text(truncatedName, (float) (node.getLat() - (truncatedName.length() * 3.8)),  node.getLongt() + 5, 40, 40);
			text(truncatedName, node.getLat() - 50,  node.getLongt() - 60 + 5, 100, 100);
			
			fill(50);
			textSize(14); 
			
		}else if(node.getDepth() ==  2){
			// draw center of flower
			width = 100;
			PImage img = loadImage("flower-2.png");
			img.resize(width, width);
			image(img, node.getLat() - (width/2), node.getLongt() - (width/2), width, width);
			text(truncatedName, node.getLat() - 40,  node.getLongt() - 50 + 5, 80, 80);
			//text(truncatedName, (float) (node.getLat() - (truncatedName.length() * 3.8)), node.getLongt() + 5, 40, 40);
			
		}else{
			// draw the petals
			truncatedName = truncateClassName(node.getName());
			ellipse(node.getLat(), node.getLongt(), width, width);	
			fill(50);
			
			text(truncatedName, node.getLat() - 40,  node.getLongt() - 50 + 5, 80, 80);
			//text(truncatedName, (float) (node.getLat() - (truncatedName.length() * 3.8)), node.getLongt() + 5, 40, 40);
		}
		
		drawNodes(node.getEdges(), node.getEdges().size());
	}

	
	/**
	 * truncates the name of the node to fit within bounds
	 * of the visualizer. truncates if name length > 10
	 * 
	 * @param name
	 * @return truncated name
	 */
	private String truncateClassName(String name) {
		
		String classNameWithoutJavaSuffix = name.substring(0, name.length() - 5);
		return truncateName(classNameWithoutJavaSuffix);
	}

	/**
	 * truncates the name of the node to fit within bounds
	 * of the visualizer. truncates if name length > 10
	 * 
	 * @param name
	 * @return truncated name
	 */
	private String truncateName(String name) {
		
		if(name.length() <= 40){
			return name;
		}else{
			return name.substring(0, 15) + "..." + name.substring(name.length()-15, name.length());
		}
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
			drawNode((FlowerNode) edges.get(n - 1));
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
	public static int calculateNodeWidth(FlowerNode node) {
		return 100;
	}

	/**
	 * 
	 * 
	 * @param node
	 */
	public void drawLine(FlowerNode node) {
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
			drawLine((FlowerNode) edges.get(n - 1));
			drawLines(edges, n - 1);
		}

	}

	/**
	 * Mutually recursive with generateCoordinates to traverse nodes and
	 * generate coordinates at each one.
	 */
	public static void generateCoordinate(FlowerNode node, float x, float y) {
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
	public static void generateCoordinates(ArrayList<FlowerNode> nodes, int n,
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
	public static int generateRadius(FlowerNode node, int siblingCount) {
		
		if (node.getParent().getDepth() == 1){
		return 250 + (node.getNumNodes() * 20);
				//+ (siblingCount * 10);
		}
		else{
			return 110;
		}
	}

}
