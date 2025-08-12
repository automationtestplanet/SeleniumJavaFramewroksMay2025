package openmrs.tdd;

import openmrs.tdd.pageobjects.TestProperties;
import openmrs.tdd.pageobjects.Utils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class OpenMrsDataDrivenTestCases extends OpenMrsBaseTest {

    String screenshotPath;

    @Test(dataProvider = "DataDrivenTestData", priority = 0)
    public void registerPatientTest(String name, String gender, String dateOfBirth, String address, String phoneNumber, String relatives) {
        Assert.assertTrue(homePage.verifyModuleTile("Register a patient"), "Register a Patient tile is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        homePage.clickModule("Register a patient");
        Assert.assertTrue(registrationPage.verifyModulePage("Register a patient"), "Register patient page is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        registrationPage.enterPatientDetails(name, gender, dateOfBirth, address, phoneNumber, relatives);
        Assert.assertTrue(registrationPage.verifyEnteredDetails(name, gender, dateOfBirth, phoneNumber), "Registered Details are showing incorrect");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        registrationPage.clickConfirmButton();
        Assert.assertTrue(patientDetailsPage.verifyPatientDetails(name), "Patient Name is not matching");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        TestProperties.setProperty("patient.id", patientDetailsPage.getPatientId());
    }

    @Test(dataProvider = "DataDrivenTestData",priority = 1)
    public void findPatientTest(String name) {
        Assert.assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Find Patient Record tile ins not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        homePage.clickModule("Find Patient Record");
        Assert.assertTrue(registrationPage.verifyModulePage("Find Patient Record"), "Find Patient Record page is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        findPatientPage.searchPatient(name);
        Assert.assertTrue(findPatientPage.verifySearchPatientRecord("Name", name), "Filtered record not matching");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        findPatientPage.clickSearchPatientTableFirstRecord();
        Assert.assertTrue(patientDetailsPage.verifyPatientDetails(name), "Patient Name is not matching");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
    }

    @Test(dataProvider = "DataDrivenTestData",priority = 2)
    public void activateVisitsAndAddAttachmentsTest(String name, String uploadFileName, String caption) {
        homePage.clickModule("Find Patient Record");
        findPatientPage.searchPatient(name);
        findPatientPage.clickSearchPatientTableFirstRecord();
        patientDetailsPage.startVisits();
        Assert.assertTrue(patientDetailsPage.verifyStartVisit(), "Start Visits failed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        patientDetailsPage.clickAttachmentsButton();
        Assert.assertTrue(attachmentsPage.verifyAttachmentsPage(), "Attachments Page is not displayed");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
        String uploadFilePath = System.getProperty("user.dir") + TestProperties.getProperty("upload.images.path")+uploadFileName;
        attachmentsPage.addAttachments(uploadFilePath, caption);
        Assert.assertTrue(attachmentsPage.verifyUploadFileCaption(caption), "File Upload is not successful");
        screenshotPath = Utils.captureScreenshot();
        Reporter.log("<img src=\"" + screenshotPath + "\" />");
    }

    @Test(dataProvider = "DataDrivenTestData",priority = 3)
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
