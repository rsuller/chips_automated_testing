@chips @insolvency @ins @liq @lresex
Feature: Liquidation LRESEX Form Processing

  As an insolvency case user
  I want to update company records with a LRESEX
  So that the companies records can reflect when a CVL (Creditors Voluntary Liquidation) case has been started

  LRESEX Extraordinary resolution to wind up

  *  Must enter a valid Case start date
  *  Can have an existing CVL case on file (add to case option)

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
  Scenario: Creating a Liquidation case with FES Scanned LRESEX form
    Given I am logged in as a user in the "Insolvency (FES)" organisational unit
    When I process a FES "LRESEX" for a "private limited company" registered in "Eng/Wales"
    And I create an insolvency case
    Then company history information is updated with the accepted LRESEX transaction
    Then the company action code remains "Creditors Voluntary Liquidation"

