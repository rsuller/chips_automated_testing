@psc @corporate_psc @psc_appointments @psc02
Feature: Process a PSC02 giving notice of appointment of a relevant legal entity with significant control

  Notes:
  * The PSC02 form is used to give notice of appointment of a relevant legal entity (RLE) with significant control
  * This is sometimes known as a 'corporate person' of significant control
  * PSC Details must be completed including the date of notification
  * All fields within the Corporate Info Tab must be completed
  * Nature of control must be completed
  * Register Date needs to be completed
  * The vast majority of PSC02 forms come in through the electronic filing route according to filing statistics


  @electronic
  Scenario: Electronic Filed auto accepted PSC02 form processing an appointment of an corporate PSC (Relevant Legal Entity/RLE)
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "PSC02" form for a private limited company
    Then company history information is updated with the accepted PSC02 transaction
