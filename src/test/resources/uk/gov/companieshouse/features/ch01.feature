@ch01
Feature: CH01 - Change the details of a director

  As a FES or Registrar's Functions user
  I want to update company records with an CH01
  So that the companies records can reflect when a directors details have been changed

  CH01: A CH01 is a 'Change of a natural directors details' form

  Notes:
  * CH01's now follow the FES scanned process for IFD
  * According to filing stats, the most popular method for receiving CH01's are as follows (in order):
  Web Filing, Electronic Filing, Front-End Scan, Post

  Requirements:
  * The date of change must a valid date
  * Must select a director to update from the list of appointments
  * Must have a house name/number, postcode and posttown in the service address (SA)
  * Must have a valid business occupation
  * Must have a valid nationality
  * Must enter a full and partial date of birth
  * Both dates of birth must match
  * Must have a house name/number, postcode and posttown in the residential address (URA)
  * Must enter a nationality (can enter multiple nationalities)
  * URA barcode (7777788888) only applies to a low volume of paper forms (Only in org units Registrar's Functions, DSR, Restoration)

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

  Scenario: FES Scanned CH01
    Given I am logged in as a user in the "CFS (FES)" organisational unit
    And I process a FES "CH01" for a "private limited company" registered in "Eng/Wales"
    When I select a current active appointment
    And I change the "middle name" detail of the director
    Then company history information is updated with the accepted CH01 transaction
