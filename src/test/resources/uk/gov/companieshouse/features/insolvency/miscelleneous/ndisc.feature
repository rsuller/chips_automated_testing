@insolvency @miscellaneous @ndisc
Feature: NDISC: Give notice of disclaimer under section 178 of the insolvency act form

  As an insolvency case user
  I want to update company records with an NDISC
  So that the companies records can reflect when a Notice of disclaimer form has been logged


  Requirements:
  * NDISCA must be attached in the PSOD screen
  * The company must have a live In Administration/Administrative receiver/Receiver Manager/Compulsory Liquidation/Creditors Voluntary Liquidation(CVL) or Members Voluntary Liquidation(MVL) case on file
  * Form is a miscellaneous logging form

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
  Scenario: FES Scanned NDISC for a company in with action code Receiver Manager
    Given I am logged in as a user in the "Insolvency (FES)" organisational unit
    And I process a FES "NDISC" for a "private limited company" registered in "Eng/Wales"
    When I add the NDISC to the receivership case
    Then company history information is updated with the accepted NDISC transaction
    And the company action code should be "Receiver Manager"
