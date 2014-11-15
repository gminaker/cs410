package cs410;

import visualization.Node;

//contains methods for joining 2D arrays and for converting this into a Prefuse table
public class fuser {

	// constants
	private int[] AUTH_COLOUR = { 255, 255, 0 };
	private int[] API_COLOUR = { 255, 0, 0 };

	private int[][] LEAF_COLOURS = { { 0, 255, 0 }, { 255, 128, 0 },
			{ 204, 204, 0 }, { 153, 76, 0 }, { 102, 51, 0 } };

	private Object[][] xmlParserOutput;
	private Object[][] htmlParserOutput;

	static Object[][] gitNamesssssss = { { "a", "c1", "c3", "c8" },
			{ "b", "c1", "c6", "c2" }, { "c", "c2", "c6" },
			{ "d", "c2", "c5" }, { "e", "c4", "c1" }, { "f", "c7" },
			{ "g", "c1", "c2", "c3", "c4", "c5", "c6" }, { "h", "c4" },
			{ "i", "c2" }, { "j", "c1" } };

	static Object[][] locTablesssss = { { "c1", 2 }, { "c2", 7 }, { "c3", 5 },
			{ "c4", 2 }, { "c5", 4 }, { "c6", 7 }, { "c7", 3 }, { "c8", 1 },
			{ "c9", 2 }, { "c10", 3 } };

	// public static void main(String[] args) {
	// fuse("api", locTablesssss, gitNamesssssss);
	// }

	public fuser() {
		this.htmlParserOutput = gitNamesssssss;
		this.xmlParserOutput = locTablesssss;

	}

	public Node fuse() {
		return makeAPINode("API", this.xmlParserOutput, this.htmlParserOutput);

	}

	public Node makeAPINode(String apiName, Object[][] locTable,
			Object[][] gitTable) {
		Node apiNode = new Node(apiName);
		for (int i = 0; i < gitTable.length; i++) {
			apiNode.addEdge(makeAuthorNode((String) gitTable[i][0], apiNode,
					locTable, gitTable, i));
		}
		apiNode.setColor(API_COLOUR);
		return apiNode;
	}

	public Node makeAuthorNode(String authName, Node parent,
			Object[][] locTable, Object[][] gitTable, int row) {
		Node authNode = new Node(authName, parent);

		for (int i = 1; i < gitTable[row].length; i++) {
			authNode.addEdge(makeClassNode((String) gitTable[row][i], authNode,
					locTable));
		}
		authNode.setColor(AUTH_COLOUR);
		return authNode;
	}

	public Node makeClassNode(String className, Node parent, Object[][] locTable) {
		Node classNode = new Node(className, parent);
		int tempComplex;

		for (tempComplex = 0; tempComplex < locTable.length; tempComplex++) {
			if (locTable[tempComplex][0].equals(className)) {
				break;
			}

		}

		classNode.setComplexity((int) locTable[tempComplex][1]);

		int tempComplexity = classNode.getComplexity();
		int[] tempColour;

		if (tempComplexity < 5) {
			tempColour = LEAF_COLOURS[0];
		} else if (tempComplexity < 10) {
			tempColour = LEAF_COLOURS[1];
		} else if (tempComplexity < 15) {
			tempColour = LEAF_COLOURS[2];
		} else if (tempComplexity < 20) {
			tempColour = LEAF_COLOURS[3];
		} else {
			tempColour = LEAF_COLOURS[4];
		}

		classNode.setColor(tempColour);

		return classNode;
	}
}
