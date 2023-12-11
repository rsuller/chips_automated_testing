@officers @appointment_changes @ch04
Feature: CH04 - Change the details of a corporate secretary

  As Chips examiner user
  I want to update company records with an CH04
  So that the company's records can reflect when a corporate secretary's details have been changed

  CH04: A CH04 is a 'Change of a corporate secretary's details' form

  Notes:
  * The date of change must a valid date
  * Must select a secretary to update from the list of appointments
  * The vast majority of CH04 forms come in through the electronic filing route according to filing statistics
  * The corporate secretary is appointed using form AP04

  @electronic
  Scenario: Electronic Filed auto accepted CH04 form processing a change of corporate secretary details
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "CH04" form for a private limited company
    Then company history information is updated with the accepted CH04 transaction
