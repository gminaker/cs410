package cs410;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;

public class gitInspectorParser {
	
	public static int maxAuthorNum = 6;
	public static int maxFileNum = 6;
	
	public class Authors {
		public String Name;
		public int TopRowCount;
		public java.util.List<java.util.Map.Entry<String,Integer>> Files;
	}
	
	static Object[][] outputArray = new Object[maxFileNum][maxAuthorNum+1];
	static List<Authors> authorList;
	
	static Hashtable<String, String> responsibilities = new Hashtable<String, String>();
		
    private static TreeMap<String, Double> getAuthors(Document doc, XPath xpath) {
        
    	TreeMap<String, Double> AuthorTable = new TreeMap<String, Double>();
    	
        try {
	        XPathExpression exprAuthor = xpath.compile("/gitinspector/responsibilities/authors/author/name/text()");
	        Object resultAuthor = (NodeList) exprAuthor.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodesAuthor = (NodeList) resultAuthor;
	        
	        XPathExpression exprRow = xpath.compile("/gitinspector/responsibilities/authors/author/files/file/rows/text()");
	        Object resultRow = (NodeList) exprRow.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodesRow = (NodeList) resultRow;
	        
	        for (int i = 0; i < nodesAuthor.getLength(); i++) {
	        	AuthorTable.put(nodesAuthor.item(i).getNodeValue(), Double.valueOf(nodesRow.item(i).getNodeValue()));
	        }
	        
	        for (String key : AuthorTable.keySet()) {
	            //System.out.println(key + ":" + AuthorTable.get(key));
	        }
	        
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        
        return AuthorTable;
    }
    
    
    private static List<String> getFileNames(Document doc, XPath xpath) {
        List<String> list = new ArrayList<>();
        try {
        	
	        // 7) Get all writers
	        XPathExpression expr = xpath.compile("/gitinspector/responsibilities/authors/author/files/file/name/text()");
	        Object result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodes = (NodeList) result;
	        System.out.println(nodes.getLength());
	        for (int i = 0; i < nodes.getLength(); i++) {
	        	list.add(nodes.item(i).getNodeValue());
	            System.out.println(nodes.item(i).getNodeValue());
	        }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
	public static Object[][] giveOutputArray(String filepath) throws Exception {	
		
	       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        DocumentBuilder builder = null;
	        
	        try {
				builder = factory.newDocumentBuilder();

		        Document doc = builder.parse(filepath);
		        
		        XPathFactory xpathfactory = XPathFactory.newInstance();
		        XPath xpath = xpathfactory.newXPath();
		        
		        TreeMap<String, Double> authorTable = new TreeMap<String, Double>();
		        List<String> fileNameTable = getFileNames(doc, xpath);
		        
		        authorTable = getAuthors(doc, xpath);
		        
		       	                
		        
		        
				for (int temp = 0; temp < maxAuthorNum; temp++) {

					outputArray[temp][0] = authorTable.keySet();

					for (int i=1; i < maxFileNum+1; i++)
					{
						String content;
						
						if (fileNameTable != null)
						{
							content = fileNameTable.get(i);
						}
						else
						{
							content = "";
						}
						
						outputArray[temp][i] = content;
					}
				}
				
				
	        } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	        }
	        
			
			//testing
			for(int i = 0; i < outputArray.length; i++)
			{
			    for(int j = 0; j < outputArray[i].length; j++)
			    {
			        System.out.print(outputArray[i][j]);
			        if(j < outputArray[i].length - 1) System.out.print(" ");
			    }
			    System.out.println();
			}
	return outputArray;
	
	}

	public static Object[][] giveElasticSearchOutputArray(String filepath) throws Exception {	
		
	       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        DocumentBuilder builder = null;
	        
	        //temp for debugging fusing
	        Object[][] tempArray = new Object[maxFileNum][maxAuthorNum+1];
	    	
	    	tempArray[0][0] = "kimchy";
	    	tempArray[1][0] = "Martijn van Groningen";
	    	tempArray[2][0] = "Simon Willnauer";
	    	tempArray[3][0] = "Luca Cavanna";
	    	tempArray[4][0] = "uboness";
	    	tempArray[5][0] = "Martijn van Groningen";

	    	tempArray[0][1] = "src/test/java/org/elasticsearch/index/query/SimpleIndexQueryParserTests.java";
	    	tempArray[0][2] = "src/main/java/org/elasticsearch/common/Base64.java";
	    	tempArray[0][3] = "src/main/java/org/elasticsearch/common/xcontent/XContentBuilder.java";
	    	tempArray[0][4] = "src/test/java/org/elasticsearch/transport/AbstractSimpleTransportTests.java";
	    	tempArray[0][5] = "src/main/java/org/elasticsearch/monitor/jvm/JvmStats.java";
	    	tempArray[0][6] = "src/main/java/org/elasticsearch/index/engine/internal/InternalEngine.java";
	    	
	    	tempArray[1][1] = "src/test/java/org/elasticsearch/search/child/SimpleChildQuerySearchTests.java";
	    	tempArray[1][2] = "src/test/java/org/elasticsearch/percolator/PercolatorTests.java";
	    	tempArray[1][3] = "src/test/java/org/elasticsearch/search/aggregations/bucket/TopHitsTests.java";
	    	tempArray[1][4] = "src/test/java/org/elasticsearch/nested/SimpleNestedTests.java";
	    	tempArray[1][5] = "src/test/java/org/elasticsearch/indices/IndicesOptionsIntegrationTests.java";
	    	tempArray[1][6] = "src/main/java/org/elasticsearch/percolator/PercolatorService.java";
	    	
	    	tempArray[2][1] = "src/test/java/org/elasticsearch/test/InternalTestCluster.java";
	    	tempArray[2][2] = "src/main/java/org/elasticsearch/cluster/routing/allocation/allocator/BalancedShardsAllocator.java";
	    	tempArray[2][3] = "src/test/java/org/elasticsearch/index/store/StoreTest.java";
	    	tempArray[2][4] = "src/test/java/org/elasticsearch/search/sort/SimpleSortTests.java";
	    	tempArray[2][5] = "src/test/java/org/elasticsearch/test/ElasticsearchIntegrationTest.java";
	    	tempArray[2][6] = "src/test/java/org/elasticsearch/search/query/MultiMatchQueryTests.java";
	    	
	    	tempArray[3][1] = "src/test/java/org/apache/lucene/search/postingshighlight/XPostingsHighlighterTests.java";
	    	tempArray[3][2] = "src/test/java/org/elasticsearch/search/highlight/HighlighterSearchTests.java";
	    	tempArray[3][3] = "src/test/java/org/elasticsearch/search/query/SimpleQueryTests.java";
	    	tempArray[3][4] = "src/test/java/org/elasticsearch/action/IndicesRequestTests.java";
	    	tempArray[3][5] = "src/test/java/org/elasticsearch/count/query/SimpleQueryTests.java";
	    	tempArray[3][6] = "src/main/java/org/apache/lucene/search/postingshighlight/XPostingsHighlighter.java";

	    	tempArray[4][1] = "src/test/java/org/elasticsearch/search/aggregations/bucket/DateHistogramTests.java";
	    	tempArray[4][2] = "src/test/java/org/elasticsearch/search/aggregations/bucket/StringTermsTests.java";
	    	tempArray[4][3] = "src/test/java/org/elasticsearch/search/aggregations/bucket/DateRangeTests.java";
	    	tempArray[4][4] = "src/test/java/org/elasticsearch/search/aggregations/bucket/LongTermsTests.java";
	    	tempArray[4][5] = "src/test/java/org/elasticsearch/search/aggregations/bucket/DoubleTermsTests.java";
	    	tempArray[4][6] = "src/test/java/org/elasticsearch/search/aggregations/bucket/RangeTests.java";
	    	
	    	tempArray[5][1] = "src/test/java/org/elasticsearch/search/child/SimpleChildQuerySearchTests.java";
	    	tempArray[5][2] = "src/test/java/org/elasticsearch/percolator/PercolatorTests.java";
	    	tempArray[5][3] = "src/test/java/org/elasticsearch/search/aggregations/bucket/TopHitsTests.java";
	    	tempArray[5][4] = "src/test/java/org/elasticsearch/nested/SimpleNestedTests.java";
	    	tempArray[5][5] = "src/test/java/org/elasticsearch/indices/IndicesOptionsIntegrationTests.java";
	    	tempArray[5][6] = "src/main/java/org/elasticsearch/percolator/PercolatorService.java";
	
		return tempArray;
	}
	public static Object[][] giveJenkinsOutputArray(String filepath) throws Exception {	
		
	       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        DocumentBuilder builder = null;
	        
	        //temp for debugging fusing
	        Object[][] tempArray = new Object[maxFileNum][maxAuthorNum+1];
	    	
	    	tempArray[0][0] = "kohsuke";
	    	tempArray[1][0] = "Kohsuke Kawaguchi";
	    	tempArray[2][0] = "Jesse Glick";
	    	tempArray[3][0] = "Stephen Connolly";
	    	tempArray[4][0] = "Nigel Magnay";
	    	tempArray[5][0] = "mindless";

	    	tempArray[0][1] = "core/src/main/java/hudson/FilePath.java";
	    	tempArray[0][2] = "test/src/main/java/org/jvnet/hudson/test/HudsonTestCase.java";
	    	tempArray[0][3] = "core/src/main/java/hudson/util/ProcessTree.java";
	    	tempArray[0][4] = "core/src/main/java/hudson/model/Run.java";
	    	tempArray[0][5] = "core/src/main/java/hudson/model/AbstractProject.java";
	    	tempArray[0][6] = "core/src/main/java/hudson/Functions.java";
	    	
	    	tempArray[1][1] = "core/src/main/java/jenkins/util/AntClassLoader.java";
	    	tempArray[1][2] = "core/src/main/java/hudson/FilePath.java";
	    	tempArray[1][3] = "core/src/main/java/jenkins/model/lazy/AbstractLazyLoadRunMap.java";
	    	tempArray[1][4] = "core/src/main/java/jenkins/model/Jenkins.java";
	    	tempArray[1][5] = "core/src/main/java/jenkins/util/xstream/XStreamDOM.java";
	    	tempArray[1][6] = "core/src/main/java/hudson/model/Queue.java";
	    	
	    	tempArray[2][1] = "core/src/main/java/jenkins/model/lazy/LazyBuildMixIn.java";
	    	tempArray[2][2] = "core/src/main/java/hudson/model/Run.java";
	    	tempArray[2][3] = "test/src/main/java/org/jvnet/hudson/test/JenkinsRule.java";
	    	tempArray[2][4] = "test/src/main/java/org/jvnet/hudson/test/MockFolder.java";
	    	tempArray[2][5] = "core/src/main/java/jenkins/triggers/ReverseBuildTrigger.java";
	    	tempArray[2][6] = "core/src/main/java/jenkins/util/VirtualFile.java";
	    	
	    	tempArray[3][1] = "test/src/main/java/org/jvnet/hudson/test/JenkinsRule.java";
	    	tempArray[3][2] = "test/src/main/java/org/jvnet/hudson/test/JenkinsMatchers.java";
	    	tempArray[3][3] = "core/src/main/java/hudson/slaves/NodeProvisioner.java";
	    	tempArray[3][4] = "core/src/main/java/jenkins/model/IdStrategy.java";
	    	tempArray[3][5] = "core/src/main/java/hudson/model/User.java";
	    	tempArray[3][6] = "core/src/main/java/hudson/model/View.java";

	    	tempArray[4][1] = "core/src/main/java/jenkins/model/Jenkins.java";
	    	tempArray[4][2] = "core/src/main/java/hudson/model/Hudson.java";
	    	tempArray[4][3] = "core/src/main/java/hudson/model/AbstractCIBase.java";
	    	tempArray[4][4] = "core/src/main/java/jenkins/model/Configuration.java";
	    	tempArray[4][5] = "core/src/main/java/hudson/model/AbstractProject.java";
	    	tempArray[4][6] = "core/src/main/java/hudson/Functions.java";
	    	
	    	tempArray[5][1] = "test/src/test/java/lib/form/RepeatableTest.java";
	    	tempArray[5][2] = "core/src/main/java/hudson/diagnosis/OldDataMonitor.java";
	    	tempArray[5][3] = "test/src/test/java/hudson/tasks/BuildTriggerTest.java";
	    	tempArray[5][4] = "core/src/main/java/hudson/Functions.java";
	    	tempArray[5][5] = "core/src/test/java/hudson/util/CopyOnWriteMapTest.java";
	    	tempArray[5][6] = "core/src/main/java/hudson/util/XStream2.java";
	
		return tempArray;
	}
	
}
