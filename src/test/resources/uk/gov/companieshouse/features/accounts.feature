@accounts @aa
Feature: Accounts Form

  Notes:
  * This form (AA) is for annual accounts or 'statutory accounts'
  * Companies must send their statutory accounts to companies house annually at the end of their financial year
  * Statutory accounts must include:
  - a ‘balance sheet’, which shows the value of everything the company owns, owes and is owed on the last day of the
  financial year
  - Statement of capital
  - a ‘profit and loss account’, which shows the company’s sales, running costs and the profit or loss it has made
  over the financial year
  - notes about the accounts
  - a director’s report (unless they a ‘micro-entity’)
  * Statutory accounts must meet either:
  - International Financial Reporting Standards
  - New UK Generally Accepted Accounting Practice
  * PLC's and Limited companies can only file certain types of accounts
  * Made up date (MUD) must be after received date
  * MUD entered on the form must also be on or after the next expected MUD date
  * Duplicate submissions for the same MUD will fire a rule.
  * According to filing stats, the most popular method for receiving accounts are as follows (in order):
  Electronic Filing, Web Filing, Front-End Scan, Single Service, Post
  * Only certain types of accounts can be filed using the different electronic methods.

  Background:
    Given I am logged in as a user in the "CFS (FES)" organisational unit


  @fes_scanned
  Scenario: Annual accounts form processing accepted for a LTD company registered in England/Wales, selecting an account
  type from the list shown at random.
    Given I process a FES "AA" for a "private limited company" registered in "Eng/Wales"
    When I complete mandatory details to file accounts for a company
      | Full                  |
      | Small                 |
      | Medium                |
      | Group                 |
      | Dormant               |
      | Total Exemption Full  |
      | Total Exemption Small |
      | Partial Exemption     |
      | Micro Entity          |
      | Audited Abridged      |
      | Unaudited Abridged    |
    Then company history information is updated with the accepted AA transaction

  @fes_scanned
  Scenario: Annual accounts form processing accepted for a PLC registered in England/Wales, selecting an account
  type from the list shown at random.
    Given I process a FES "AA" for a "PLC" registered in "Eng/Wales"
    When I complete mandatory details to file accounts for a company
      | Interim |
      | Initial |
    Then company history information is updated with the accepted AA transaction