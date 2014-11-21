# The Blame Flower
### by Oh No, Angel Adoration!


### Project Overview

Our project analyzes a given code base to determine the top six contributors, and the top six classes that they changed. In addition, it computes the cyclomatic complexity for each of the aforementioned classes. Our visualizer then produces flower-like images for each author, with the ‘petals’ representing their most changed classes. The colour of the petals varies from yellow to brown depending on the complexity of the classes. Thus, an observer can get an idea of which authors are producing (potentially needlessly) complex code, and which files might need to be refactored. 


### How to run The Blame Flower

1. Install Maven version 3.0 (instructions available [here](http://maven.apache.org)).

2. Ensure JAVA_HOME is set to JDK version 1.7+

3. Navigate to the root directory of the project, which contains two bash scripts.

4. Ensure there is a working internet connection.

5. Run the bash script corresponding to the codebase to be analyzed.
  * For ElasticSearch, use the command `sh StartElasticSearch.sh`
  * For Jsoup, use the command `sh StartJsoup.sh`
  * The scripts run Cobertura and GitInspector on the specified codebase, and then run our compiled .jar with arguments pointing to the output of the tools.

6. Bask in the glory of the visualization.


### Testing

In order to test our code thoroughly, we created corresponding JUnit for each class within our project. We were able to run these tests to ensure each of our tools was working correctly separately, and to ensure there were no regressions after integration. As it was difficult to automate testing of the visualization itself, we relied on manually running the code to ensure that the output was consistent with our expectations. Our test files are available in `src/testing`, and specific test cases are available in `src/testing/README.md`