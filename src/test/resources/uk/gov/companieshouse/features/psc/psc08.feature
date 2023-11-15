@psc @psc_statements @psc08
Feature: Process a PSC08 giving notice of an additional statement noted in the PSC register

  Notes:
  * The PSC08 form is used to notify Companies House of PSC statements noted in the PSC register.
  * A company must take reasonable steps to find out if there is anyone who is
  a registrable person or a registrable relevant legal entity (RLE), and if so, to
  identify them.
  * Only one statement can be notified using the form. Additional statements need to be notified on additional forms.


  @electronic
  Scenario: Electronic Filed auto accepted PSC08 form processing a notification of PSC statement.
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "PSC08" form for a private limited company
    Then company history information is updated with the accepted PSC08 transaction