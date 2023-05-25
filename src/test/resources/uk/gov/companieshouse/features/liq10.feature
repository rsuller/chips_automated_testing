@insolvency @liquidation @liq10
Feature: Liquidation LIQ10 form Processing

  As an insolvency case user
  I want to update company records with an LIQ10
  So that the companies records can reflect when a liquidator is removed by court
  on a Members Voluntary Liquidation (MVL) or Creditors Voluntary Liquidation (CVL) case

  LIQ10: A LIQ10 is a 'Notice of removal of liquidator by court in MVL and CVL' form

  Requirements:
  * Case must have an Insolvency Practitioner (IP)on file
  * The company can have a live Creditors Voluntary Liquidation (CVL)
  or Members Voluntary Liquidation (MVL)case on file
  * Date must be a valid date

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
  Scenario: FES Scanned LIQ10: Notice of removal of liquidator by court in cvl OR MVL
    Given I am logged in as a user in the "Insolvency (FES)" organisational unit
    And I process a FES "LIQ10" for a "private limited company" registered in "Eng/Wales"
    When I cease an Insolvency Practitioner
    Then company history information is updated with the accepted LIQ10 transaction
