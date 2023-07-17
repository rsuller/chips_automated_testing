@mortgage @mr04
Feature: MR04 mortgage form processing

  As an mortgage user
  I want to update company records with a form MR04
  So that the companies records can reflect when a charge has been satisfied in part or in full

  MR04: An MR04 is Statement of satisfaction in full or in part of a charge

  Notes:
  * Company must have a current mortgage charge against it

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
  Scenario: Successfully submit a FES scanned MR04 form processing a statement of satisfaction
    Given I am logged in as a user in the "Mortgage (FES)" organisational unit
    And I process a FES "MR04" for a "private limited company" registered in "Eng/Wales"
    When I complete mandatory details to process a statement of satisfaction of a charge
    Then company history information is updated with the accepted MR04 transaction

  @electronic
  Scenario: Electronic Filed MR04 form processing a statement of satisfaction
    Given I am logged in as a user in the "EF Mortgage" organisational unit
    And I process an e-filed "MR04" form for a private limited company
    When I allocate and select the work item for processing
    And I complete mandatory details to process a statement of satisfaction of a charge
    Then company history information is updated with the accepted MR04 transaction
