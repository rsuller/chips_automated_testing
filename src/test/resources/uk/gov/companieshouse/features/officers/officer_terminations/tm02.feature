@officers @appointment_terminations @tm02
Feature: TM02 - Termination of a secretary appointment

  As a Chips Examiner
  I want to update company records with a TM02
  So that the company's records can reflect when a secretary has been terminated

  TM02: Is the 'termination of appointment of secretary' form

  Notes:
  * According to filing stats, the most popular method for receiving TM02's are as follows (in order):
  Web Filing, Electronic Filing, Front-End Scan, Post
  * Must select a current appointment from the list
  * Must enter a valid termination date


  @electronic
  Scenario: Electronic Filed auto accepted TM02 form processing a secretary termination
    Given I am logged in as a user in the "RM1" organisational unit
    And I process an e-filed "TM02" form for a private limited company
    Then company history information is updated with the accepted TM02 transaction