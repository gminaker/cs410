package cs410;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {
	
	
	public static Object[][]complexityAndClassNamesForFilepathsAndFolder(String[] filepaths, String folder) {
		Object[][] returnValue = new Object[filepaths.length][];
		for (int i = 0; i<filepaths.length; i++) {
			returnValue[i] = new Object[] {filepaths[i], complexityForFilepath(filepaths[i])};
		}
		return returnValue;
	}

	
	private static double complexityForFilepath(String filepath) {
		double complexity = 0;
		String htmlFileName = filepath.replaceAll("\\.\\w+", ".html");
		htmlFileName.replace('/', '.');	
		String htmlFilePath;
		
		File f = new File(htmlFileName);
		if (f.exists() && !f.isDirectory()) { 
			try {
				Document doc = Jsoup.parse(f, "UTF-8");
				Elements spans = doc.select(".hidden");
				for (Element e : spans) {
					complexity += Double.parseDouble(e.text());
				}
				complexity /= spans.size();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return complexity;
	}
}
