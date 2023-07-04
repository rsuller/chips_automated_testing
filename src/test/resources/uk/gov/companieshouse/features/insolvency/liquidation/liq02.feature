@insolvency @liquidation @liq02
Feature: Liquidation LIQ02 Form Processing

  As an insolvency case user
  I want to update company records with an LIQ02
  So that the companies records can reflect when a Creditors Voluntary liquidation (CVL)case is created
  or a Members Voluntary Liquidation (MVL) case is converted into a CVL case

  LIQ02: A LIQ02 is a 'Notice of statement of affairs' form

  Requirements:
  * Must have a LIQ02SOAL or LIQ02SOAD attachment
  * The date must a valid date
  * The company can have an MVL case on file (Converting to CVL case)

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
  Scenario: Converting MVL case to CVL case using LIQ02 with LIQ02SOAL and LIQ02SOC attachments
    Given I am logged in as a user in the "Insolvency (FES)" organisational unit
    And I process a FES "LIQ02" for a "private limited company" registered in "Eng/Wales"
    When I convert a current case from MVL to CVL
    Then company history information is updated with the accepted LIQ02 transaction
    And the company action code should be "Creditors Voluntary Liquidation"
