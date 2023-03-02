@ch02
Feature: Process Change of Director Details - CH02 Form

  CH02 - Corporate Director Change Details

  Scenario: Change detail of Corporate Director
    Given I am logged in as a user in the "RM1" organisational unit
    And I process the start document for form "CH02"
    And I select a "corporate director" to change
    When I change the corporate officer's details
    Then company history information is updated with the accepted CH02 transaction

