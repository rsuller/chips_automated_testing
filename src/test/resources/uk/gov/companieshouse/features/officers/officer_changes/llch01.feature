@officers @appointment_changes @llch01
Feature: LLCH01 - Change details of a member of a LLP

  As a CHIPS examiner
  I want to update company records with an LLCH01
  So that the companies records can reflect when an LLP members details have been changed

  An LLCH01 is to change the details of an individual person who is a member of a limited liability partnership (LLP)

  Notes:
  * LLCH01's now follow the FES scanned process for IFD
  * According to filing stats, the most popular method for receiving LLCH01's is Electronic Filing

  Requirements:
  * The date of change must a valid date
  * Must select a member to update from the list of appointments
  * If a matching name cannot be found in list must enter supplied details and select the inconsistency marker
  * Must have a house name/number, postcode and posttown in the service address (SA)
  * If service address is changed must select no change to URA or update URA
  * Can specify whether the member is designated or non-designated
  * Can enter Country/State usual resident
  * Must have a house name/number, postcode and posttown in the residential address (URA)
  * URA barcode (7777788888) only applies to a low volume of paper forms (Only in org units Registrar's Functions, DSR, Restoration)

  @electronic
  Scenario: Electronic Filed auto accepted LLCH01 form processing a change of LLP member details
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "LLCH01" form for a private limited company
    Then company history information is updated with the accepted LLCH01 transaction