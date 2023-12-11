@officers @appointment_changes @ch03
Feature: CH03 - Change the details of a secretary

  As Chips examiner user
  I want to update company records with an CH03
  So that the company's records can reflect when a secretary's details have been changed

  CH03: A CH03 is a 'Change of a secretary's details' form

  Notes:
  * The date of change must a valid date
  * Must select a secretary to update from the list of appointments
  * The vast majority of CH03 forms come in through the electronic filing route according to filing statistics
  * The secretary is appointed using form AP03

  @electronic
  Scenario: Electronic Filed auto accepted CH03 form processing a change of secretary details
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "CH03" form for a private limited company
    Then company history information is updated with the accepted CH03 transaction
