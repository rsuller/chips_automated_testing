@example
Feature: An example test to access CHIPS

  Scenario: The example of accessing CHIPS
    Given I am an authorised user
    And I am a user of the "EF ROE" department
    When I access CHIPS
    Then I will be able to file for the correct department
    And I will be able to search for company "00006400"
