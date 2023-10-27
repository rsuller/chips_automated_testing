@psc @individual_psc @psc01
Feature: Process a PSC01 giving notice of appointment of an individual with significant control

  Notes:
  * The PSC01 form is used to give notice of the appointment of an individual with significant control
  * PSC01's now follow the FES scanned process for IFD
  * The vast majority of PSC01 forms come in through the electronic filing route according to filing statistics


  @electronic
  Scenario: Electronic Filed auto accepted PSC01 form processing an appointment of an individual PSC
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "PSC01" form for a private limited company
    Then company history information is updated with the accepted PSC01 transaction
