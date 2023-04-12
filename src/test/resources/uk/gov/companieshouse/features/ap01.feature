@company_appointments @ap01
Feature: AP01 - Appoint a director

  As a FES or Registrar's Functions user
  I want to update company records with an AP01
  So that the company's records can reflect when a new director has been appointed

  AP01: Is the 'Appointment of a natural director' form

  Note:
  * SE company type uses org unit SE Companies FES for an AP01 and has two member types as follows: Member of
  Administrative Organ and Member of Management Organ
  * The AP01 IFD generated image can be corrected in the RM Customer Support org unit

  Validation notes:
  * The appointment date must a valid date
  * Must enter a forename and surname
  * Must enter a house name/number, postcode and posttown in the service address (SA)
  * Must enter a valid business occupation
  * Must enter a valid nationality (can enter multiple nationalities)
  * Must enter a full and partial date of birth
  * Both dates of birth must match
  * Must enter a house name/number, postcode and posttown in the residential address (URA)
  * URA barcode (7777788888) only applies to a low volume of paper forms (Only in org units Registrar's Functions,
  DSR, Restoration)

  Front End Scanning (FES) notes:
  * Forms in FES with a status of 5(ready for chips) generate a JSON message as below and post it to CHIPS over HTTP
  on the exposed REST URL /rest/efiling/postScannedDoc
  * This is simulated through running tests tagged @fes_scanned within this project, which POST submissions
  directly to the REST URL for the particular environment from env.conf
  * A new barcode must be used everytime
  * Scanned location sends the work item to different queues. scannedLocation - 1 is Eng/Wales, scannedLocation - 2
  is Scotland, scannedLocation - 3 is Northern Ireland
  * Forms can be submitted without formImageId and coverLetterImageId however no image icons will appear in the
  CHIPS TCL.
  * A valid formImageId can be found by connecting to the associated FES database and using an IMAGE_ID from the
  IMAGE table
  * There is no need to change the batchName, batchNumber, envelopeId or paymentInd between tests.
  * Submissions appear in the relevant work queues, just as they would for FES'd forms in the real world.


  @fes_scanned
  Scenario Outline: FES scanned accepted AP01 forms
    Given I am logged in as a user in the "<org_unit>" organisational unit
    When I process a FES "AP01" for a "private limited company" registered in "<country>"
    And I complete mandatory details to appoint a FES'd director
    Then company history information is updated with the accepted AP01 transaction
    Examples:
      | org_unit  | country   |
      | CFS (FES) | Eng/Wales |
    Examples:
      | org_unit                      | country          |
      | Scottish DEB (FES)            | Scotland         |
      | NI Document Examination (FES) | Northern Ireland |