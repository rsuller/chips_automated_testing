@confirmation_statement @llcs01
Feature: LLCS01 - Confirmation Statement for an LLP company

* This form is the replacement for the old Annual Return.
* It required to be filed annually to ensure company information is kept up to date.
* Only the following information can be updated using CS01:
- Principal business activities or standard industrial classification (SIC)
- Statement of capital
- Trading status of shares and exemption from keeping a register of people with significant control (PSC)
- Shareholder information
* Any other changes must be done by filing the relevant separate forms.
* An LLCS01 cannot be processed on a dissolved company
* An LLCS01 cannot be processed on a closed company
* An LLCS01 can only processed on a limited liability partnership
* A fee of £13 must have been paid in order for the form to be processed.
* Business activity must be provided
* Date must be valid
* SIC codes must be valid
* Share class must be valid
* Number of shares must be provided
* Total number of shares must accurate
* Aggregate Nominal value of shares must accurate
* According to filing stats, the most popular method for receiving LLCS01s are as follows (in order):
Web Filing, Electronic Filing, Single Service, Front-End Scan, Post
* Electronically filed LLCS01 forms with no updates are usually auto accepted (there is no need to open
query handling). Therefore in order to verify interaction with the LLCS01 screens in Chips, a test with a
different filing method is necesssary.

  @electronic
  Scenario: Electronically Filed auto accepted LLCS01 form
    Given I am logged in as a user in the "EF Registration Team" organisational unit
    When I process an e-filed "LLCS01" form for a private limited company
    Then company history information is updated with the accepted LLCS01 transaction
