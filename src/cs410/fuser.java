package cs410;

import prefuse.data.Table;

//contains methods for joining 2D arrays and for converting this into a Prefuse table
public class fuser {

	public static void main(String[] args) {
		fuse();
	}

	public static void fuse() {
		Object[][] joinedArray = join();
		Table outputTable = arrayToTable(joinedArray);
	}

	private static Table arrayToTable(Object[][] convertArray) {
		Table table = new Table();

		// creating and labeling table columns
		table.addColumn("className", String.class);
		table.addColumn("methodCount", double.class);
		table.addColumn("averageCRAP", double.class);
		table.addColumn("percentChanged", double.class);
		table.addColumn("changeFlag", int.class);

		// creating table rows
		table.addRows(convertArray.length);

		// populate table with values from fused 2D array
		for (int i = 0; i < convertArray.length; i++) {
			for (int j = 0; j < convertArray[i].length; j++) {
				table.set(i, j, convertArray[i][j]);
			}

		}

		return table;

	}

	// Essentially does a SQL join on two 2D arrays containing the results from
	// the two parsers. Will eventually take these as input but using mock
	// objects for now
	public static Object[][] join() {

		// mock object to imitate the output of the xml parser
		Object[][] xmlArray = new Object[][] {
				{ "BancoAgitarException", 1.0, 1.0 },
				{ "SSNChecks", 2.0, 2.705 }, { "MathUtils", 3.0, 2.0 },
				{ "Branch", 4.0, 1.75 }, { "BancoAgitar", 12.0, 2.0 },
				{ "StringUtil", 2.0, 2.5 },
				{ "CheckingAccount", 19.0, 3.383157894736842 },
				{ "Util", 5.0, 2.0 } };

		// mock object to imitate the output of the html parser
		Object[][] htmlArray = new Object[][] {
				{ "BancoAgitarException", 100, 2 }, { "SSNChecks", 50, 0 },
				{ "MathUtils", 25, 1 }, { "Branch", 33, 1 },
				{ "BancoAgitar", 66, 2 }, { "StringUtil", 77, 0 },
				{ "CheckingAccount", 99, 0 }, { "Util", 20, 1 } };

		Object[][] fusedArray = new Object[xmlArray.length][5];

		// fuse the two arrays together (essentially doing a SQL join)
		// TODO: need to refactor this a bit to remove hard coded values
		for (int i = 0; i < xmlArray.length; i++) {

			for (int j = 0; j < xmlArray.length; j++) {
				if (xmlArray[i][0] == htmlArray[j][0]) {
					fusedArray[i][0] = xmlArray[i][0];
					fusedArray[i][1] = xmlArray[i][1];
					fusedArray[i][2] = xmlArray[i][2];
					fusedArray[i][3] = htmlArray[j][1];
					fusedArray[i][4] = htmlArray[j][2];
				}
			}
		}

		/*
		 * for (int i = 0; i < xmlArray.length; i++) { for (int j = 0; j < 5;
		 * j++) { System.out.print(" " + fusedArray[i][j] + " "); }
		 * System.out.println(); }
		 */

		return fusedArray;
	}

}
