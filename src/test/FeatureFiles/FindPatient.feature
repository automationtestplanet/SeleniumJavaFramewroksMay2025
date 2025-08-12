Feature: As a user I want to implement a Find Patient functionality so that the parient details can be verified

  @FindSinglePatient
  Scenario: Register a patient
    Given the user is on the OpenMRS Home Page
    When the user selects "Find Patient Record" module
    And the user enters patient name "Ramesh Babu H" in patient search field
    And the user clicks on first record from the search results
    Then searched patient "Ramesh Babu H" details should be displayed