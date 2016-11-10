@ClientApplicationTest
Feature: Generating Subsets Using a Command Line Interface
  I want to generate subsets of a set (given as sequence of unique strings 
  on a command line) and print them out in random order.
  The number of subsets printed must equal the input length (the number of unique strings).
    
  Background: A Running Client Application
    Given the client application is running

  Scenario: Missing Input
    When I do not provide input
    Then the application should show an error

  Scenario Outline: Creating Subsets
    When I read an input: <inputSet>
    Then the response should contain: <outputCardinality> subsets
    And the subsets should be in random order
    And the subsets should be members of the power set

  Examples:
    | inputSet | outputCardinality |
    | A B      | 2                 |
    | A        | 1                 |    
    | A B C    | 3                 |    