package openmrs.bdd.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import openmrs.tdd.pageobjects.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class RegisterPatientStepDefinition {
    WebDriver driver = Driver.getDriver().getChromeDriver();
    HomePage homePage = new HomePage(driver);
    RegistrationPage registrationPage = new RegistrationPage(driver);
    PatientDetailsPage patientDetailsPage = new PatientDetailsPage(driver);

    @Given("the user is on the OpenMRS Home Page")
    public void theUserIsOnTheOpenMRSHomePage() {
        Assert.assertTrue(homePage.verifyPageTitle("Home"), "Hope Page is not displayed");
    }

    @When("the user selects {string} module")
    public void theUserSelectsModule(String moduleName) {
        Assert.assertTrue(homePage.verifyModuleTile(moduleName), moduleName+" tile is not displayed");
        homePage.clickModule(moduleName);
        Assert.assertTrue(registrationPage.verifyModulePage(moduleName), moduleName+" page is not displayed");
    }

    @When("the user enters patient details {string}, {string}, {string},	{string}, {string},	{string}")
    public void theUserEntersPatientDetails(String name, String gender, String dateOfBirth, String address, String phoneNumber, String relatives) {
        registrationPage.enterPatientDetails(name, gender, dateOfBirth, address, phoneNumber, relatives);
    }

    @Then("the user verifies patient details {string}, {string}, {string}, {string} to confirm")
    public void theUserVerifiesPatientDetailsToConfirm(String name, String gender, String dateOfBirth, String phoneNumber) {
        Assert.assertTrue(registrationPage.verifyEnteredDetails(name, gender, dateOfBirth, phoneNumber), "Registered Details are showing incorrect");
        registrationPage.clickConfirmButton();
    }

    @Then("the user verifies registered patient name {string}")
    public void theUserVerifiesRegisteredPatientName(String name) {
        Assert.assertTrue(patientDetailsPage.verifyPatientDetails(name), "Patient Name is not matching");
    }

    @Then("the user captures the patient Id")
    public void theUserCapturesThePatientId() {
        TestProperties.setProperty("patient.id", patientDetailsPage.getPatientId());
    }


}
