# Test cases

### Overview
JUnit tests were created for each class within our projects for unit test and also as a regression test method after intergration. Test files are seperated by each components in the architecture diagram.

### 1. GitInspectorXMLOutputParserTest.java
Test casese verified that the output array resulting from the GitInspectorXMLOutputParsers were correctly formatted and contained the correct values. TestGitInspectorOutput.xml file with known number of authors, file names were created for testing. Test cases verified that :
- the output array is not null and contains correct dimensions
- all contents in output array is convertible to strings
- GetAuthors returns correct number and list of authors in XML
- GetTop6Authors correctly grabs all 6 authors 
- GetFileNames method grabs all edited file names 

### 2. CoberturaXMLOutputParserTest.java

### 3. FlowerNodeTest.java

### 4. FlowerVisualizerTest.java

### 5. OutputFuserTest.java

### 6. Visualization manual test
There was manual testing done for visualization as it was difficult to automate the vizualization. Using test XML output files containing multiple authors and varying complexity values, we ran the visualizer and verified that
1. correct maximum number of authors are disaplyed
2. correct number of files are displayed as petals
3. more complex files had a darker color displayed 