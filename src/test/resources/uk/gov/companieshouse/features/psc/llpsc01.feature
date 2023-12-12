@psc @individual_psc @psc_appointments @llpsc01
Feature: Process an LLPSC01 giving notice of appointment of an individual with significant control against an LLP

  Notes:
  * The LLPSC01 form is used to give notice of the appointment of an individual with significant control for an LLP
  * The vast majority of PSC01 forms come in through the electronic filing route according to filing statistics


  @electronic
  Scenario: Electronic Filed auto accepted LLPSC01 form processing an appointment of an individual PSC
    Given I am logged in as a user in the "EF Registration Team" organisational unit
    And I process an e-filed "LLPSC01" form for a private limited company
    Then company history information is updated with the accepted LLPSC01 transaction
