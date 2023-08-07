@new-contact
Feature: REST - Add New Contact Feature

  Scenario Outline: Validate that a user may add a contact
      When the user adds a new contact with "<name>" and surname "<surname>"
      Then the user is in the list of contacts

    Examples:
      | name           | surname        |
      | auto-generated | auto-generated |





