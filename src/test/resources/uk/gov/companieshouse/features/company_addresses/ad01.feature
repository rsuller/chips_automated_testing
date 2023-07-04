@company_addresses @ad01
Feature: AD01 Change of Registered Office Address

  Notes:
  * The form must be submitted within 14 days of the change taking place.
  * A valid UK address must be provided
  * According to filing stats, the most popular method for receiving AD01's are as follows (in order):
  Web Filing, Electronic Filing, Front-End Scan, Single Service, Post

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
  Scenario: FES scanned accepted AD01 form
    Given I am logged in as a user in the "CFS (FES)" organisational unit
    When I process a FES "AD01" for a "private limited company" registered in "Eng/Wales"
    And I complete mandatory details to change a registered office address
    Then company history information is updated with the accepted AD01 transaction

  @paper
  Scenario: Process AD01 with acceptable data
    Given I am logged in as a user in the "RM1" organisational unit
    When I process the start document for form "AD01"
    And I complete mandatory details to change a registered office address
    Then the form is submitted without rules fired
    And company history information is updated with the accepted AD01 transaction