@example
Feature: An example test to access CHIPS

  Scenario: The example of accessing CHIPS
    Given I am logged in as a user in the "RM1" organisational unit
    Then I will be able to search for company "00006400"
