@share_capital @sh01
Feature: SH01 - Allotment of shares/Statement of capital

  As a Chips examiner
  I want to be able to update the statement of capital on an SH01 in CHIPS
  So that it reflects the information given on the new form.

  Notes:
  * For the transaction SH01, when the processing date is on or after the legislative date, the statement of capital screen in CHIPS should have the following amendments:
  * The 'amount paid up' & 'amount unpaid' fields should be removed
  * No rules should fire relating to the 'amount paid up' & 'amount unpaid' fields
  In the totals section (at the bottom):
  * The existing 'Total number of shares' & 'Aggregate nominal value of total number of shares' should not automatically calculate.
  * A rule should fire if the totals statement of capital matches the totals (This rule already exists but check it still works)
  * A new numerical field called 'Total Aggregate Amount unpaid' should be present (one for each currency entered).
  * If the company already has a value in this new 'Total Aggregate Amount unpaid' field, the examiner should be able to see it.
  * The legislative date should be configurable (Thought to be April 2016)

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

  @electronic
  Scenario: Electronic Filed auto accepted SH01 form processing a statement of capital
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "SH01" form for a private limited company
    Then company history information is updated with the accepted SH01 transaction
