@psc @corporate_psc @psc_changes @psc05
Feature: Process a PSC05 giving notice of change of details for relevant legal entity with significant control

  Notes:
  * The PSC05 form is used to give notice of a change of details of relevant legal entity (RLE) with significant control
  * It can only be filed against companies which have a RLE
  * The RLE is appointed using form PSC02
  * The vast majority of PSC05 forms come in through the electronic filing route according to filing statistics


  @electronic
  Scenario: Electronic Filed auto accepted PSC05 form processing a change of RLE PSC details
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "PSC05" form for a private limited company
    Then company history information is updated with the accepted PSC05 transaction