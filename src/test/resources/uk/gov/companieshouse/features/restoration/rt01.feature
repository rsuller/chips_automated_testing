@restoration @rt01
Feature: RT01 - Apply for administrative restoration to the register

  As a restoration section user
  I want to process an application for administrative restoration
  So that the dissolved company can be restored

  Requirements:
  * Can only be processed for a dissolved company.
  * The application must be made by a former director/member of the company.
  * The company was struck off the Register under the power of the Registrar to strike off a defunct company.
  * The company was carrying on business or was in operation at the time of strike off.
  * The company can only be restored within 6 years of dissolution.
  * The strike off date is the init_trans_status_date of the latest GAZ2/GAZ2A transaction.
  * All documents must be filed and penalties paid to bring the company up to date.

  @paper
  Scenario: RT01 accepted
    Given I am logged in as a user in the "Restoration Section" organisational unit
    When I process the start document for form "RT01"
    And I complete the mandatory details for an administrative restoration
    Then company history information is updated with the accepted RT01 transaction