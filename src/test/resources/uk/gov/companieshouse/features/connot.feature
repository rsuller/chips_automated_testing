@change_of_name @connot @nm01 @resolution @res15
Feature: Successfully process change of name by special resolution

  As an change of name section user
  I want to update company records with form CONNOT and RES15
  So that records can reflect when a company name is changed

  Notes:
  * New company name must not be ‘Same as’ current company names. I.e:
     - Where the only difference to an existing name is punctuation, special characters, a word or character that’s
       similar in appearance or meaning to another from the existing name/ a word or character used commonly in UK
       company names
  * New company name must not be ‘too like’ a name registered before.
  * New company name cannot contain offensive or sensitive words. (Sensitive words need permission to be able to use)
  * A resolution of company officials must be passed and delivered to CH along with the change of name form in order
  to action the company name change.
  * The name change can be actioned using form NM01 (change of name by special resolution) or NM04 (change of name by
  means provided for in the articles)
  * The CONNOT (Change of Name Notice) screen is used to action both of these in Chips.
  * There is a fee of £10 to file on paper, £8 online, or £30 for same day online service.

  Background:
    Given I am logged in as a user in the "Change of Name Section" organisational unit

  Scenario: Change of company name by special resolution (NM01) can be successfully executed
    Given I process the start document for form "CONNOT"
    And I complete details giving notice of a change of company name
    When I process the start document for form "RES15"
    And I confirm the change of name details by special resolution
    Then company history information is updated with the accepted CONNOT transaction
    And the resolution is displayed and company name changed
