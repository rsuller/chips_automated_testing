@insolvency @liquidation @liq13_14
Feature: Liquidation LIQ13 and LIQ14 Form Processing

  As an insolvency case user
  I want to update company records with an LIQ13/LIQ14
  So that the companies records can reflect when a company ceases a Members Voluntary Liquidation (MVL)
  or a Creditors Voluntary Liquidation (CVL) case

  LIQ13: A LIQ13 is a 'Notice of final account prior to dissolution in MVL' form
  LIQ14: A LIQ14 is a 'Notice of final account prior to dissolution in CVL' form

  Requirements:
  * The company can have a live Creditors Voluntary Liquidation (CVL)
  or Members Voluntary Liquidation (MVL)case on file
  * Company must have a LRESSP or LRESEX on file
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
  Scenario Outline: FES Scanned <formType>: Notice of final account prior to dissolution in MVL/CVL
    Given I am logged in as a user in the "Insolvency (FES)" organisational unit
    And I process a FES "<formType>" for a "private limited company" registered in "Eng/Wales"
    When I cease the live case
    Then company history information is updated with the accepted <formType> transaction
    And the company action code should be "<actionCodeDescription>"
    @liq13
    Examples: Processing LIQ13
      | formType | actionCodeDescription         |
      | LIQ13    | Return of Final Meeting (MVL) |
    @liq14
    Examples: Processing LIQ14
      | formType | actionCodeDescription         |
      | LIQ14    | Return of Final Meeting (CVL) |

