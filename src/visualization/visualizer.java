package visualization;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.Table;

public class visualizer extends PApplet {

	PGraphics pg;
	Table outputTable;
	ArrayList<Node> nodesToDraw = new ArrayList<Node>();
	public static final int SIZE_WIDTH = 1000;
	public static final int SIZE_HEIGHT = 1000;

	public static final Object[][] testArray = new Object[][] {
			{ "Bob", 1.0, 1.0 }, { "Reg", 2.0, 2.705 }, { "stan", 3.0, 2.0 },
			{ "Leon", 4.0, 1.75 }, { "Cat", 12.0, 2.0 },
			{ "Coffee", 2.0, 2.5 }, { "Ethel", 19.0, 3.383157894736842 },
			{ "Tom", 5.0, 2.0 } };

	private static Table arrayToTable(Object[][] convertArray) {
		Table table = new Table();

		table.addColumn("className");
		table.addColumn("methodCount");
		table.addColumn("averageCRAP");
		table.addColumn("percentChanged");
		table.addColumn("changeFlag");

		for (int i = 0; i < convertArray.length; i++) {
			table.addRow();
		}
		// populate table with values from fused 2D array
		for (int i = 0; i < convertArray.length; i++) {
			for (int j = 0; j < convertArray[i].length; j++) {
				if (convertArray[i][j].getClass() == String.class) {
					table.setString(i, j, (String) convertArray[i][j]);
				} else if (convertArray[i][j].getClass() == double.class) {
					table.setDouble(i, j, (double) convertArray[i][j]);
				} else if (convertArray[i][j].getClass() == int.class) {
					table.setInt(i, j, (int) convertArray[i][j]);
				}
			}

		}

		return table;

	}

	public void setup() {
		outputTable = arrayToTable(testArray);
		// makeNodes(outputTable);
		size(SIZE_WIDTH, SIZE_HEIGHT);
		pg = createGraphics(SIZE_WIDTH, SIZE_HEIGHT);
	}

	public void draw() {
		size(500, 500);
		background(153, 76, 0);
		drawNodes();
	}

	public void drawNodes() {
		// algorithm for calculation of x y coordinates found at
		// http://www.mathopenref.com/coordcirclealgorithm.html
		String tempString;
		float locint;
		int centerx = 250;
		int centery = 250;
		fill(255, 255, 0);
		ellipse(250, 250, 25, 25);
		tempString = nodesToDraw.get(0).getName();
		fill(50);
		text(tempString, 250 - 8, 250 + 5);
		float degreesPerNode = 360 / nodesToDraw.size();
		int r = 35;
		for (int i = 1; i < nodesToDraw.size(); i++) {
			float x;
			float y;
			float thetaDegrees = degreesPerNode * i;
			float thetaRads = radians(thetaDegrees);
			x = centerx + r * cos(thetaRads);
			y = centery + r * sin(thetaRads);
			fill(0, 255, 0);
			locint = x;
			ellipse(x, y, 50, 25);
			fill(0);
			tempString = nodesToDraw.get(i).getName();
			text(tempString, x - 5, y + 5);
			nodesToDraw.get(i).setLat(x);
			nodesToDraw.get(i).setLongt(y);
		}

		for (int i = 1; i < nodesToDraw.size(); i++) {
			System.out.println(nodesToDraw.get(i).getName());
			System.out.println(nodesToDraw.get(i).getLat());
			System.out.println(nodesToDraw.get(i).getLongt());
		}

	}

	// public void makeNodes(Table table) {

	// }

}
