# Test cases

### Overview
JUnit tests were created for each class within our projects for unit testing and regression test after intergration. Test files corresponds to tests are seperated by each components in the architecture diagram. 

In order to run the tests, ensure that the mock files specified are located within the same directory and run the unit test within Eclipse. Mock data for testing are in a 
- sample XML files for CorberturaXMLOutPutParserTest.java and GitInspectorXMLOutputParserTest.java. 
- set up within the code for other test classes.

### 1. GitInspectorXMLOutputParserTest.java
Test cases verified that the output array resulting from the GitInspectorXMLOutputParsers were correctly formatted and contained the correct values. 
- Mock file: TestGitInspectorOutput.xml. Contains known number of authors and file names for testing. 

Test cases:
- returnParsedArray_OutputArrayFormat_HappyPath - verifies that the output array is not null and contains correct dimensions
- returnParsedArray_OutputArrayFormat_ContainsOnlystrings - all contents in output array is convertible to strings
- returnParsedArray_GetAuthors_HappyPath- GetAuthors returns correct number and list of authors in XML
- returnParsedArray_GetTop6Authors_HappyPath - GetTop6Authors correctly grabs all 6 authors 
- returnParsedArray_GetFileNames_HappyPath - GetFileNames method grabs all edited file names 

### 2. CoberturaXMLOutputParserTest.java
- Mock file : test.xml. Test parsed output from Cobertura tool.

Test cases:
- testParseXML() - verifies correct table size & expected complexity value

### 3. FlowerNodeTest.java
This file verifies the number of nodes, value of nodes as well as latitudes of the nodes to be visualized. 
- Mock data: within code.

Test cases: 
- testSetandGetNodeLat - Verify the lat value is set correctly and is persisted
- testSetandGetNodeLong - longt value is set correctly and is persisted
- testSetandGetNodeComplexity - complexity value is set correctly and is persisted
- testSetandGetNodeColour - color value is set correctly and is persisted

### 4. FlowerVisualizerTest.java
This test class verifies that the visualization coordinates and dimensions is correctly displayed.

- Mock data: within code.

Test cases:
- testCalculateNodeWidth - calculated node width is correct
- testGenerateCoordinate - generateCoordinate is setting lat & long using proper values
- testGenerateCoordinates - coordinates of the nodes are set correctly
- testGenerateRadius - radius of the nodes are set correctly

### 5. OutputFuserTest.java
This test class uses test output array and tests that the visulization is processed correctly. 

- Mock data: within code.

Test cases:
- makeAuthorNodeTest - tests that correct number of children are created
- makeClassNodeTest - tests that the value of the class node complexity is correct

### 6. Visualization manual test
There was manual testing done for visualization as it was difficult to automate the vizualization. Using test XML output files containing multiple authors and varying complexity values, we ran the visualizer and verified that
- correct maximum number of authors are disaplyed
- correct number of files are displayed as petals
- more complex files had a darker color displayed 