@chips @ad @ad01
Feature: ad01 Form

* This form is used to change the Registered Office Address of a company

  @regression @smoke_test
  Scenario: Process AD01 with acceptable data
    Given I am logged in as a user in the "RM1" organisational unit
    When I process the start document for form AD01
    And I complete mandatory details to change a registered office address
    Then the form is submitted without rules fired