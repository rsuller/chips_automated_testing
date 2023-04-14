Feature: Process a PSC01 giving notice of appointment of an individual with significant control

  Notes:
  * The PSC01 form is used to give notice of the appointment of an individual with significant control
  * PSC01's now follow the FES scanned process for IFD
  * URA barcode (2619020208) only applies to a low volume of paper forms (Only in org units Registrar's Functions, DSR, Restoration)
  * FATF update allows multiple nationalities to be added to a PSC
  * FES = Front End Scanned

  Scenario: Successfully submit a paper PSC01
    Given I am logged in as a user in the "Registrar's Functions" organisational unit
    And a "private limited company" that has not previously filed a PSC
    When I complete the mandatory details for form "PSC01"
    Then the form is submitted without rules fired
    And company history information is updated with the accepted PCS01 transaction
