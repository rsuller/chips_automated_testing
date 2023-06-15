@confirmation_statement @cs01
Feature: CS01 - Confirmation Statement.

* This form is the replacement for the old Annual Return.
* It required to be filed annually to ensure company information is kept up to date.
* Only the following information can be updated using CS01:
  - Principal business activities or standard industrial classification (SIC)
  - Statement of capital
  - Trading status of shares and exemption from keeping a register of people with significant control (PSC)
  - Shareholder information
* Any other changes must be done by filing the relevant separate forms.
* A CS01 cannot be processed on a dissolved company
* A CS01 cannot be processed on a closed company
* A fee of £13 must have been paid in order for the form to be processed.
* Business activity must be provided
* Date must be valid
* SIC codes must be valid
* Share class must be valid
* Number of shares must be provided
* Total number of shares must accurate
* Aggregate Nominal value of shares must accurate
* According to filing stats, the most popular method for receiving CS01s are as follows (in order):
  Web Filing, Electronic Filing, Single Service, Front-End Scan, Post
* Electronically filed CS01 forms with no updates are usually auto accepted (there is no need to open
query handling). Therefore in order to verify interaction with the CS01 screens in Chips, a test with a
different filing method is necesssary.

  Scenario: Electronically Filed auto accepted CS01 form
    Given I am logged in as a user in the "EF Registration Team" organisational unit
    When I process a no update e-filed CS01 form for a private limited company
    Then company history information is updated with the accepted CS01 transaction

  @fes_scanned
  Scenario Outline: FES scanned accepted CS01 forms
    Given I am logged in as a user in the "<org_unit>" organisational unit
    And I process a FES "CS01" for a "private limited company" registered in "<country>"
    When I complete mandatory details process a no update confirmation statement
    Then company history information is updated with the accepted CS01 transaction
    Examples:
      | org_unit  | country   |
      | CFS (FES) | Eng/Wales |
    Examples:
      | org_unit                      | country          |
      | Scottish DEB (FES)            | Scotland         |
      | NI Document Examination (FES) | Northern Ireland |

  Scenario: Accepted paper CS01 form
    Given I am logged in as a user in the "RM1" organisational unit
    When I process the start document for form "CS01"
    And I complete mandatory details to file a paper confirmation statement
    Then company history information is updated with the accepted CS01 transaction
