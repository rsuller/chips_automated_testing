@dissolution @ds01
Feature: DS01 Form

* This form is used to strike off a company from the Register
* DS01 cannot be filed on a dissolved company
* It also cannot be filed if a previous Ds01 has been filed
* There is a fee of £10 for paper forms, £8 for DS01's filed online

  @regression @smoke_test
  Scenario: Process DS01 with acceptable data
    Given I am logged in as a user in the "Dissolution Section" organisational unit
    When I process the start document for form DS01
    And I complete mandatory details to strike off the company from the register
    Then the form is submitted without rules fired
    And company history information is updated with the accepted DS01 transaction
