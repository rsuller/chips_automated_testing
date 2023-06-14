@insolvency @liquidation @600

Feature: Liquidation for 600 Form Processing

  As an insolvency user
  I want to update company records with a form 600
  So that the companies records can reflect when a Members Voluntary liquidation (MVL)
  or Creditors Voluntary Liquidation (CVL) case is created

  600: A 600 is a 'Notice of appointment of liquidator (voluntary)' form

  Requirements:
  * Insolvency Practitioner (IP) must be appointed when creating a case
  * The IP appointment date must a valid date
  * IP does not have to be appointed when adding to a case
  * The company can have an MVL/CVL case on file

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
  Scenario: FES Scanned 600 form creating a CVL or MVL case
    Given I am logged in as a user in the "Insolvency (FES)" organisational unit
    And I process a FES "600" for a "private limited company" registered in "Eng/Wales"
    When I create a voluntary liquidation case using a selected IP
    Then company history information is updated with the accepted 600 transaction