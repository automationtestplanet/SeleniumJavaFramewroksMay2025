package openmrs.bdd.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import openmrs.tdd.pageobjects.Driver;
import openmrs.tdd.pageobjects.FindPatientPage;
import openmrs.tdd.pageobjects.PatientDetailsPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class FindPatientStepDefinition {
    WebDriver driver = Driver.getDriver().getChromeDriver();
    FindPatientPage findPatientPage = new FindPatientPage(driver);
    PatientDetailsPage patientDetailsPage = new PatientDetailsPage(driver);

    @When("the user enters patient name {string} in patient search field")
    public void theUserEntersPatientNameInPatientSearchField(String name) {
        findPatientPage.searchPatient(name);
        Assert.assertTrue(findPatientPage.verifySearchPatientRecord("Name", name), "Filtered record not matching");
    }

    @When("the user clicks on first record from the search results")
    public void theUserClicksOnFirstRecordFromTheSearchResults() {
        findPatientPage.clickSearchPatientTableFirstRecord();
    }

    @Then("searched patient {string} details should be displayed")
    public void searchedPatientDetailsShouldBeDisplayed(String name) {
        Assert.assertTrue(patientDetailsPage.verifyPatientDetails(name), "Patient Name is not matching");
    }
}
