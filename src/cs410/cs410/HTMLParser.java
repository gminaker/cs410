import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {

	static String methodClass = null;
	static double percetangeDifference = 0;
	static double changeStatus = 0;
	
	static Hashtable<String, Double> classCount = new Hashtable<String, Double>();
	
	public static void main(String[] args) {
		parseHTML();
	}
	
	public static void parseHTML() {
		
		File input = new File("/home/tiana/MavenTestChanges.html");
		Document doc = null;
		
		try {
			doc = Jsoup.parse(input, "UTF-8");
		
			// parse percentage difference from iFrame
			Element content = doc.getElementById("classes");
			Elements attributes = content.getElementsByTag("td");
			
			for (Element attribute : attributes) {
				Elements className = attribute.getElementsByIndexEquals(0);
				Elements percentageDifference = attribute.getElementsByIndexEquals(1);
			}
		
			// parse classNames added, modified or removed
			Element classContent = doc.getElementById("By Class");
			Elements classes = content.getElementsByTag("td");
			
			// JDiff markup - bold is new class, strike is removed
			for (Element classe : classes) {
				String newClass = classe.getElementsByTag("b");
				String modifiedClass = classe.getElementsByTag("");
				String removedClass = classe.getElementsByTag("strike");
			}
			
			Object[][] outputArray = new Object[content.count()][3];
			Set<String> keys = classCount.keySet();
			Object[] keyArray = keys.toArray();
			for (int i = 0; i < keys.size(); i++) {
				outputArray[i][0] = keyArray[i];
				outputArray[i][1] = percentageDifference.get(keyArray[i]);
				outputArray[i][2] = (classes.get(keyArray[i]));
			}
		
		catch (IOException e1) {
			e1.printStackTrace();
		};
	}

		

