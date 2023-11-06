@psc @psc_cessation @psc07
Feature: Process a PSC07 giving notice of ceasing to be a person with significant control

  Notes:
  * The PSC07 form is used to give notice of ceasing to be a person of significant control
  * It can only be filed against companies which have a person of significant control appointed and can be used to
  cease all the PSC appointment types against limited companies: individual person (PSC), relevant legal entity (RLE),
  or other registrable person (ORP)
  * PSC's are appointed using form PSC01, PSC02 or PSC03


  @electronic
  Scenario: Electronic Filed auto accepted PSC07 form processing a cessation of an individual PSC
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "PSC07" form for a private limited company
    Then company history information is updated with the accepted PSC07 transaction