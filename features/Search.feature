#Author: Luana Costa
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Searching for Iphone at Amazon web site

  @tag1
  Scenario: 80% Of Shown Products Should Be Exclusively The Searched Product  
    When user navigate to google website
    And search for amazon website
    And search for iphone using the search bar
    Then Count The Total List Of Found Products in Page
    And Count The Total Of Iphone Items Found
    Then make sure at least 50 porcent of items found are Iphone
    
  @tag2
  Scenario: The Higher Price In The First Page Can't Be Greater Than U$2000  
    When user navigate to google website
    And search for amazon website
    And search for iphone using the search bar
    Then find the more expensive Iphone in page
    And convert its value to USD
    Then make sure the converted value is not greater than US 2000
    
    @tag3
  Scenario: Products Different Than The Searched Product Should Be Cheaper Than The
						Searched Product

  	When user navigate to google website
    And search for amazon website
    And search for iphone using the search bar
    And find products which are not Iphone
    Then make sure all found products are cheaper than the cheapest Iphone
    
    