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
* Electronically filed CS01 forms with no updates are usually auto accepted (there is no need to open query handling).

  Scenario: Electronically Filed auto accepted CS01 form
    Given I am logged in as a user in the "EF Registration Team" organisational unit
    When I process a no update e-filed CS01 form for a private limited company
    Then company history information is updated with the accepted CS01 transaction