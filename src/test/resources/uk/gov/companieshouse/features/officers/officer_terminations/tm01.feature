@officers @appointment_terminations @tm01
Feature: TM01 - Termination of a director appointment

  As a Chips Examiner
  I want to update company records with a TM01
  So that the company's records can reflect when a director has been terminated

  TM01: Is the 'termination of appointment of director' form

  Notes:
  * According to filing stats, the most popular method for receiving TM01's are as follows (in order):
  Web Filing, Electronic Filing, Front-End Scan, Post
  * Must select a current appointment from the list
  * Must enter a valid termination date
  * If a matching name cannot be found in list must enter supplied name and select the inconsistency marker

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
  Scenario: FES scanned accepted TM01 forms
    Given I am logged in as a user in the "CFS (FES)" organisational unit
    When I process a FES "TM01" for a "private limited company" registered in "Eng/Wales"
    And I select a current active appointment
    And I complete mandatory details to terminate a director
    Then company history information is updated with the accepted TM01 transaction

  @electronic
  Scenario: Electronic Filed auto accepted TM01 form processing a director termination
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "TM01" form for a private limited company
    Then company history information is updated with the accepted TM01 transaction