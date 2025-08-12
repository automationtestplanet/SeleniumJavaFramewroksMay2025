package openmrs.tdd;

import openmrs.tdd.pageobjects.TestProperties;
import openmrs.tdd.pageobjects.Utils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.Map;

public class OpenMrsHybridDrivenTestCases extends OpenMrsBaseTest {

    String screenshotPath;

    @Test(dataProvider = "HybridDrivenTestData",priority = 0)
    public void registerPatientTest(Map<String, String> testData) {
        Assert.assertTrue(homePage.verifyModuleTile("Register a patient"), "Register a Patient tile is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        homePage.clickModule("Register a patient");
        Assert.assertTrue(registrationPage.verifyModulePage("Register a patient"), "Register patient page is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        registrationPage.enterPatientDetails(testData.get("Name"), testData.get("Gender"), testData.get("DateOfBirth"), testData.get("Address"), testData.get("PhoneNumber"), "Parent,Test User Parent");
        Assert.assertTrue(registrationPage.verifyEnteredDetails(testData.get("Name"), testData.get("Gender"), testData.get("DateOfBirth"), testData.get("PhoneNumber")), "Registered Details are showing incorrect");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        registrationPage.clickConfirmButton();
        Assert.assertTrue(patientDetailsPage.verifyPatientDetails(testData.get("Name")), "Patient Name is not matching");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        TestProperties.setProperty("patient.id", patientDetailsPage.getPatientId());
    }

    @Test(dataProvider = "HybridDrivenTestData",priority = 1)
    public void findPatientTest(Map<String, String> testData) {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record tile ins not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        homePage.clickModule("Find Patient Record");
        Assert.assertTrue(registrationPage.verifyModulePage("Find Patient Record"), "Find Patient Record page is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        findPatientPage.searchPatient(testData.get("Name"));
        Assert.assertTrue(findPatientPage.verifySearchPatientRecord("Name", testData.get("Name")), "Filtered record not matching");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientDetails(testData.get("Name")), "Patient Name is not matching");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
    }

    @Test(dataProvider = "HybridDrivenTestData",priority = 2)
    public void activateVisitsAndAddAttachmentsTest(Map<String, String> testData) {
        homePage.clickModule("Find Patient Record");
        findPatientPage.searchPatient(testData.get("Name"));
        findPatientPage.clickSearchPatientTableFirstRecord();
        patientDetailsPage.startVisits();
        Assert.assertTrue(patientDetailsPage.verifyStartVisit(), "Start Visits failed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        patientDetailsPage.clickAttachmentsButton();
        Assert.assertTrue(attachmentsPage.verifyAttachmentsPage(), "Attachments Page is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        String uploadFilePath = System.getProperty("user.dir") + TestProperties.getProperty("upload.images.path") + testData.get("UploadFileName");
        attachmentsPage.addAttachments(uploadFilePath, testData.get("Caption"));
        Assert.assertTrue(attachmentsPage.verifyUploadFileCaption(testData.get("Caption")), "File Upload is not successful");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
    }

    @Test(dataProvider = "HybridDrivenTestData",priority = 3)
    public void deletePatientTest(Map<String, String> testData) {
        homePage.clickModule("Find Patient Record");
        findPatientPage.searchPatient(testData.get("Name"));
        findPatientPage.clickSearchPatientTableFirstRecord();
        patientDetailsPage.deletePatient(testData.get("Reason"));
        findPatientPage.searchPatient(testData.get("Name"));
        Assert.assertTrue(findPatientPage.verifyNoRecordsFoundMessage(), "Patient record is not deleted");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
    }

}
