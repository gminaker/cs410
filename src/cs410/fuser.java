package cs410;

import java.util.Hashtable;

import visualization.Node;

//contains methods for creating the graph to be used by the visualizer using the output from the parsers
public class Fuser {

	// constants
	private int[] AUTH_COLOUR = { 255, 255, 0 };
	private int[] API_COLOUR = { 255, 0, 0 };

	private int[][] LEAF_COLOURS = { { 255, 220, 0 },  //best
									{ 199, 179, 46 },
									{ 169, 155, 59 },
									{ 104, 99, 65 }, 
									{ 82, 81, 69 } }; //worst
	

	private Object[][] xmlParserOutput;
	private Object[][] htmlParserOutput;

	static Object[][] gitNamesssssss = { { "author_aaa", "c1", "class_22222", "class_33333", "class_44444", "class_55555", "class_66666" },
			{ "author_bbb", "c1", "class_22222", "class_33333", "class_44444", "class_55555", "class_66666" }, { "author_ccc", "c1", "class_22222", "class_33333", "class_44444", "class_55555", "class_66666" },
			{ "author_ddd","c1", "class_22222", "class_33333", "class_44444", "class_55555", "class_66666" }, { "author_eee", "c1", "class_22222", "class_33333", "class_44444", "class_55555", "class_66666" }, 
			{ "author_fff", "c1", "class_22222", "class_33333", "class_44444", "class_55555", "class_66666" } };

	static Object[][] locTablesssss = { { "c1", 28 }, { "class_22222", 7 },
			{ "class_33333", 15 }, { "class_44444", 2 }, { "class_55555", 40 }, { "class_66666", 22 }};

	// public static void main(String[] args) {
	// fuse("api", locTablesssss, gitNamesssssss);
	// }


	/**
	 * Test method for using test objects (not to be called for other purposes)
	 * 
	 * @return Node
	 */
//	public Node fuse(String apiName, Object[][] xmlParserOutput, Object[][] htmlParserOutput) {
//		return makeAPINode("API", xmlParserOutput, htmlParserOutput);
//
//	}

	/**
	 * Recursively constructs a DAG to be used by the fuser, the first level
	 * (root) is the codebase name, the second level is the names of the authors
	 * who have worked on the codebase, and the third level is the classes they
	 * have worked on with some associated complexity data
	 * 
	 * @param apiName
	 *            - name of api or codebase being analyzed
	 * @param locTable
	 *            - table containing class names and their complexities
	 * @param gitTable
	 *            - table containing git stats (author name followed by the
	 *            classes they have worked on)
	 * @return - a DAG nodes with the codebase name being the root node
	 */
	public Node makeAPINode(String apiName, Hashtable<String, Double> locTable,
			Object[][] gitTable) {
		Object[][] cleanedTable = cleanTable(gitTable); 
		Node apiNode = new Node(apiName);
		for (int i = 0; i < cleanedTable.length; i++) {
			apiNode.addEdge(makeAuthorNode((String) cleanedTable[i][0], apiNode,
					locTable, cleanedTable, i));
		}
		apiNode.setColor(API_COLOUR);
		apiNode.setDepth(1);
		return apiNode;
	}

	/**
	 * This constructs all the parent nodes it should not be called dirrectly
	 * 
	 * 
	 * @param authName
	 *            - name of author
	 * @param parent
	 *            - the parent node of the node being constructed
	 * @param locTable
	 * @param gitTable
	 * @param row
	 * @return
	 */
	public Node makeAuthorNode(String authName, Node parent,
			Hashtable<String, Double> locTable, Object[][] gitTable, int row) {
		
		Node authNode = new Node(authName, parent);

		for (int i = 1; i < gitTable[row].length; i++) {
			authNode.addEdge(makeClassNode((String) gitTable[row][i], authNode,
					locTable));
		}
		authNode.setColor(AUTH_COLOUR);
		authNode.setDepth(2);
		return authNode;
	}

	/**
	 * Constructs leaf nodes in the DAG
	 * 
	 * @param className
	 *            - name of class
	 * @param parent
	 *            - parent of class (should be an author)
	 * @param locTable
	 *            - table containing classes and their associated compleities
	 * @return - a Node reprenting a class
	 */
	public Node makeClassNode(String className, Node parent, Hashtable<String, Double> locTable) {
		Node classNode = new Node(className, parent);

		double tempComplexity = locTable.get(className);

		classNode.setComplexity(tempComplexity);

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

		classNode.setDepth(3);
		return classNode;
	}
	
	
	/**
	 * strips file path data from file names
	 * @param dirtyTable
	 * @return 
	 */
	public Object[][] cleanTable(Object[][] dirtyTable){
		    String tempClassName;
		    String patternString = "[\\\\/]";
		    String[] result;
		    int resultLen; 
		    for(int i = 0; i < dirtyTable.length; i++){
		    	for(int j = 1; j < dirtyTable[i].length; j++){
		    		result = ((String) dirtyTable[i][j]).split(patternString);
		    		resultLen = result.length - 1;
		    		tempClassName = result[resultLen];
		    		dirtyTable[i][j] = tempClassName;
		    	}
		    }
		    
			return dirtyTable;
			
			
		
	}
}
