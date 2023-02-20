@ch01
Feature: CH01 - Change the details of a director

  As a FES or Registrar's Functions user
  I want to update company records with an CH01
  So that the companies records can reflect when a directors details have been changed

  CH01: A CH01 is a 'Change of a natural directors details' form

  Notes:
  * CH01's now follow the FES scanned process for IFD

  Requirements:
  * The date of change must a valid date
  * Must select a director to update from the list of appointments
  * Must have a house name/number, postcode and posttown in the service address (SA)
  * Must have a valid business occupation
  * Must have a valid nationality
  * Must enter a full and partial date of birth
  * Both dates of birth must match
  * Must have a house name/number, postcode and posttown in the residential address (URA)
  * Must enter a nationality (can enter multiple nationalities)
  * URA barcode (7777788888) only applies to a low volume of paper forms (Only in org units Registrar's Functions, DSR, Restoration)

  Scenario: Valid Paper Filed CH01 submission
    Given I am logged in as a user in the "Registrar's Functions" organisational unit
    And I process the start document for form "CH01"
    And I select a current active appointment
    When I change the "middle name" detail of the director
    Then company history information is updated with the accepted CH01 transaction
