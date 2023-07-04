@insolvency @liquidation @liq01
Feature: Liquidation LIQ01 Form Processing

  As a Liquidation 2016 or Insolvency (FES) user
  I want to update company records with a LIQ01
  So that the companies records can reflect when a declaration of solvency is added to a Member's Voluntary liquidation (MVL)case

  LIQ01: A LIQ01 is a 'Notice of statutory declaration of solvency' form

  Notes:
  * The date must a valid date
  * The company can have more than one MVL case on file

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
  Scenario: FES Scanned LIQ01
    Given I am logged in as a user in the "Insolvency (FES)" organisational unit
    And I process a FES "LIQ01" for a "private limited company" registered in "Eng/Wales"
    When I create an insolvency case
    Then company history information is updated with the accepted LIQ01 transaction
    And the company action code should be "Member's Voluntary Liquidation"
