@psc @individual_psc @psc_changes @llpsc04
Feature: Process an LLPSC04 giving notice of change of details for individual with significant control

  Notes:
  * The LLPSC04 form is used to give notice of a change of details of an individual with significant control
  * It can only be filed against limited liability partnerships which have an individual PSC
  * The individual PSC is appointed to a limited liability partnership using form LLPSC01
  * The vast majority of LLPSC04 forms come in through the electronic filing route according to filing statistics


  @electronic
  Scenario: Electronic Filed auto accepted LLPSC04 form processing a change of individual PSC details for an LLP
    Given I am logged in as a user in the "Limited Liability Partnerships" organisational unit
    And I process an e-filed "LLPSC04" form for a private limited company
    Then company history information is updated with the accepted LLPSC04 transaction