@officers @appointment_terminations @lltm01
Feature: LLTM01 - Terminate the appointment of a member of an LLP

  Notes:
  * The LLTM01 form is used to give notice about the termination of a members appointment
  * Must enter a valid termination date
  * According to stats, the most popular method of filing LLTM01's is electronic
  * Must select a current member appointment
  * If a matching name cannot be found in list must enter supplied name and select the inconsistency marker
  * URA barcode (7777788888) only applies to a low volume of paper forms (Only in org units Registrar's Functions, DSR, Restoration)

  @electronic
  Scenario: Electronic Filed auto accepted LLTM01 form processing an LLP member termination
    Given I am logged in as a user in the "EF Registration Team" organisational unit
    And I process an e-filed "LLTM01" form for a private limited company
    Then company history information is updated with the accepted LLTM01 transaction