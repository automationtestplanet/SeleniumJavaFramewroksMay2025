package openmrs.demo;

import openmrs.demo.pageobjects.TestProperties;
import openmrs.demo.pageobjects.Utils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class OpenMrsTestCases extends OpenMrsBaseTest {

    String screenshotPath;

    @Test(dataProvider = "DataDrivenTestData")
    public void registerPatientTest(String name, String gender, String dateOfBirth, String address, String phoneNumber) {
        Assert.assertTrue(homePage.verifyModuleTile("Register a patient"), "Register a Patient tile is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        homePage.clickModule("Register a patient");
        Assert.assertTrue(registrationPage.verifyModulePage("Register a patient"), "Register patient page is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        registrationPage.enterPatientDetails(name, gender, dateOfBirth, address, phoneNumber, "Parent,Test User Parent");
        Assert.assertTrue(registrationPage.verifyEnteredDetails(name, gender, dateOfBirth, phoneNumber), "Registered Details are showing incorrect");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        registrationPage.clickConfirmButton();
        Assert.assertTrue(patientDetailsPage.verifyPatientDetails(name), "Patient Name is not matching");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        TestProperties.setProperty("patient.id", patientDetailsPage.getPatientId());
    }

    @Test
    public void findPatientTest() {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record tile ins not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        homePage.clickModule("Find Patient Record");
        Assert.assertTrue(registrationPage.verifyModulePage("Find Patient Record"), "Find Patient Record page is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        findPatientPage.searchPatient("Test User");
        Assert.assertTrue(findPatientPage.verifySearchPatientRecord("Name", "Test User"), "Filtered record not matching");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientDetails("Test, User"), "Patient Name is not matching");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
    }

    @Test
    public void activateVisitsAndAddAttachmentsTest() {
        homePage.clickModule("Find Patient Record");
        findPatientPage.searchPatient("Test User");
        findPatientPage.clickSearchPatientTableFirstRecord();
        patientDetailsPage.startVisits();
        Assert.assertTrue(patientDetailsPage.verifyStartVisit(), "Start Visits failed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        patientDetailsPage.clickAttachmentsButton();
        Assert.assertTrue(attachmentsPage.verifyAttachmentsPage(), "Attachments Page is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        String uploadFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\UploadFiles\\UploadImage.png";
        attachmentsPage.addAttachments(uploadFilePath, "TestCaption");
        Assert.assertTrue(attachmentsPage.verifyUploadFileCaption("TestCaption"), "File Upload is not successful");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
    }

    @Test(dataProvider = "DataDrivenTestData")
    public void deletePatientTest(String name, String deleteReason) {
        homePage.clickModule("Find Patient Record");
        findPatientPage.searchPatient(name);
        findPatientPage.clickSearchPatientTableFirstRecord();
        patientDetailsPage.deletePatient(deleteReason);
        findPatientPage.searchPatient(name);
        Assert.assertTrue(findPatientPage.verifyNoRecordsFoundMessage(), "Patient record is not deleted");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
    }

}
