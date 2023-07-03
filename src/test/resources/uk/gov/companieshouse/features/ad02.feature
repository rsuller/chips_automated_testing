@ad02 @company_addresses
Feature: AD02 - Change SAIL address details

  As a user in the RM1 organisation team
  I want to be able to change the SAIL address details of an address
  So that I can ensure the address details are correct

  * SAIL = Single Alternative Inspection Location

  Scenario: Change SAIL address details of a company
    Given I am logged in as a user in the "RM1" organisational unit
    And I process the start document for form "AD02"
    When I change the SAIL address details of the company
    Then company history information is updated with the accepted AD02 transaction
