@psc @individual_psc @psc_changes @psc04
Feature: Process a PSC04 giving notice of change of details for individual with significant control

  Notes:
  * The PSC04 form is used to give notice of a change of details of an individual with significant control
  * It can only be filed against companies which have an individual PSC
  * The individual PSC is appointed using form PSC01
  * The vast majority of PSC04 forms come in through the electronic filing route according to filing statistics


  @electronic
  Scenario: Electronic Filed auto accepted PSC04 form processing a change of individual PSC details
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "PSC04" form for a private limited company
    Then company history information is updated with the accepted PSC04 transaction