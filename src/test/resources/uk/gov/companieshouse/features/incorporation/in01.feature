@incorporation @in01
Feature: IN01 - New Company Incorporation
  As a CHIPS examiner in the relevant organisational unit
  I want to process a company incorporation
  So that the new company will be shown on the register

  IN01: An IN01 is a 'Incorporation of a new private or public company' form

  Notes:
  * According to filing statistics, the vast majority of new Incorporations now come through the electronic route.

  Requirements:
  * The receipt date must a valid date
  * Must select a company type from the drop down menu
  * Must enter a proposed name
  * Rekeyed proposed name must match the proposed name
  * Name ending must match the company type
  * Can select name restrictions, welsh indicator and same day options
  * Must enter at least one valid SIC code
  * Must select a situation of RO option
  * Valid registered office address must match the situation of RO value
  * Must select a model articles option and can select restricted articles
  * Must enter at least one valid director appointment
  * Can enter multiple nationalities for an officer
  * URA barcode (7777788888) only applies to a low volume of paper forms (DSR & London New Incs only)
  * Can enter details of secretaries and PSC's
  * Valid capital must be entered depending on the company type being incorporated
  * Shareholders must be entered for companies with share capital
  * Must enter a guarantor for companies limited by guarantee
  * If no PSC's are provided the PSC statement box must be ticked
  * Can elect to keep certain information on the public register which must match the incorporation details
  * If directors or pscs elect to keep info on public register their full DOB will be available on public record
  * Must enter statement of compliance details delivered by subscribers, agent or both
  * Must enter a valid date of memorandum

  For a Comunity Interest Company:
  * A CIC is a limited company with special additional features
  * For the use of people who want to conduct a business or other activity for community benefit
  * CICs use the standard IN01 form
  * Must select the CIC indicator check box
  * Must provide a CIC name ending e.g. ‘community interest company’, ‘CIC’ or the Welsh equivalent
  * Must select bespoke articles of incorporation

  @electronic
  Scenario: Electronic Filed auto accepted IN01 form successfully processing a new company limited by shares
    Given I am logged in as a user in the "EF New Companies" organisational unit  
    When I process an e-filed "IN01" form for a private limited company
    Then company history information is updated with the accepted NEWINC transaction
    And the new company is displayed correctly