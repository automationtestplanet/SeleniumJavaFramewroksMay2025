Feature: As a user I want to implement a Register functionality so that the parient details can be stored
  for the future reference purpose.

  @RegisterSinglePatient
  Scenario: Register a patient
    Given the user is on the OpenMRS Home Page
    When the user selects "Register a patient" module
    And the user enters patient details "Ramesh Babu, H", "Male", "26, December, 2000",	"Near HDFC bank, S R Nagar,Hyderabad, Telanagana, India, 500033", "9876543210",	"Parent, Ramesh Babu Parent"
    Then the user verifies patient details "Ramesh Babu, H", "Male", "26, December, 2000", "9876543210" to confirm
    And the user verifies registered patient name "Ramesh Babu, H"
    And the user captures the patient Id

  @RegisterMultiplePatient
  Scenario Outline: Register multiple patients
    Given the user is on the OpenMRS Home Page
    When the user selects "Register a patient" module
    And the user enters patient details "<Name>", "<Gender>", "<DateOfBirth>",	"<Address>", "<PhoneNumber>",	"<Relatives>"
    Then the user verifies patient details "<Name>", "<Gender>", "<DateOfBirth>", "<PhoneNumber>" to confirm
    And the user verifies registered patient name "<Name>"
    And the user captures the patient Id

    Examples:
      | Name             | Gender | DateOfBirth        | Address                                                     | PhoneNumber | Relatives                    |
      | Ramesh Babu, H   | Male   | 26, December, 2000 | Near HDFC bank,S R Nagar,Hyderabad,Telangana,India,500033   | 9876543210  | Parent, Ramesh Babu Parent   |
      | Kishore Kumar, M | Male   | 10, November, 1998 | Near Icici bank,HitechCity,Hyderabad,Telangana,India,500033 | 9876543211  | Parent, Kishore Kumar Parent |
      | Kumar Raju, Ch   | Male   | 17, July, 2002     | Near SBI bank,Kukatpally,Hyderabad,Telangana,India,500033   | 9876543212  | Parent, Kumar Raju Parent    |
