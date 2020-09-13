Project author: Luana Costa
# Description
This project is automated tests using the B.D.D. 
# Getting started
1. Import the project at Eclipse;
2. Execute the class Search TestRunner in Junit Test.
You can also debug the test to check the results.

# Prerequisites 
-  JDK Compliance version: 1.8;
-  Eclipse: For  development of the project; 
-  Chrome Driver: For execute Selenium WebDriver at Browser Google Chrome; 
-  Cucumber Plugin for eclipse

# Referenced Libraries

| Extension name | Version Used| 
| :---           |    :----:   |  
| cucumber-core  | 1.2.6| 
| cucumber-java  | 1.2.6| 
| cucumber-junit | 1.2.6|
| cucumber-jvm-deps| 1.2.6|
|gherkin| 2.12.2|
|json| 20190722|
|selenium-server-standalone| 3.141.59|

# Structure
- Feature file in Gherkin, contais the steps of the tests;
- BDDSearchIphone class contais the step implementations of the test;
- SearchTestRunner class is a link between feature file and BDDSearchIphone class.