@change_of_name @connot @nm01 @resolution @res15
Feature: Successfully process change of name by special resolution

  Background:
    Given I am logged in as a user in the "Change of Name Section" organisational unit

  Scenario: Change of company name by special resolution can be successfully executed
    Given I process the start document for form "CONNOT"
    And I complete details giving notice of a change of company name
    When I process the start document for form "RES15"
    And I confirm the change of name details by special resolution
    Then company history information is updated with the accepted "CONNOT" transaction
    And the resolution is displayed and company name changed
