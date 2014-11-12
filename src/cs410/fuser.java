package cs410;

import visualization.Node;

//contains methods for joining 2D arrays and for converting this into a Prefuse table
public class fuser {

	static Object[][] gitNamesssssss = { { "a", "c1", "c3", "c8" },
			{ "b", "c1", "c6", "c2" }, { "c", "c2", "c6" },
			{ "d", "c2", "c5" }, { "e", "c4", "c1" }, { "f", "c7" },
			{ "g", "c1", "c2", "c3", "c4", "c5", "c6" }, { "h", "c4" },
			{ "i", "c2" }, { "j", "c1" } };

	static Object[][] locTablesssss = { { "c1", 2 }, { "c2", 7 }, { "c3", 5 },
			{ "c4", 2 }, { "c5", 4 }, { "c6", 7 }, { "c7", 3 }, { "c8", 1 },
			{ "c9", 2 }, { "c10", 3 } };

	public static void main(String[] args) {
		fuse("api", locTablesssss, gitNamesssssss);
	}

	public static void fuse(String apiName, Object[][] locTable,
			Object[][] gitTable) {
		makeAPINode(apiName, locTable, gitTable);

	}

	public static Node makeAPINode(String apiName, Object[][] locTable,
			Object[][] gitTable) {
		Node apiNode = new Node(apiName);
		for (int i = 0; i < gitTable.length; i++) {
			apiNode.addEdge(makeAuthorNode((String) gitTable[i][0], locTable,
					gitTable, i));
		}
		return apiNode;
	}

	public static Node makeAuthorNode(String authName, Object[][] locTable,
			Object[][] gitTable, int row) {
		Node authNode = new Node(authName);

		for (int i = 1; i < gitTable[row].length; i++) {
			authNode.addEdge(makeClassNode((String) gitTable[row][i], locTable));
		}

		return authNode;
	}

	public static Node makeClassNode(String className, Object[][] locTable) {
		Node classNode = new Node(className);
		int tempComplex;

		for (tempComplex = 0; tempComplex < locTable.length; tempComplex++) {
			if (locTable[tempComplex][0].equals(className)) {
				break;
			}

		}

		classNode.setComplexity((int) locTable[tempComplex][1]);

		return classNode;
	}
}
