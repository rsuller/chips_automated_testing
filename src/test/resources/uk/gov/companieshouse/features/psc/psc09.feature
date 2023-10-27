@psc @psc_statements @psc09
Feature: Process PSC09 form giving notice of an update to a PSC statement

  Notes:
  * The PSC09 form is used to notify Companies House of an update to PSC statements
  * It can also be used to notify cessation of a PSC statement
  * The PSC statements will have previously been applied through the filing of a PSC08 form


  @electronic
  Scenario: Electronic Filed auto accepted PSC09 form processing a cessation of PSC statement.
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "PSC09" form for a private limited company
    Then company history information is updated with the accepted PSC09 transaction