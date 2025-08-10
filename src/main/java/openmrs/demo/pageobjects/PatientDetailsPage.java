package openmrs.demo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PatientDetailsPage extends BasePage {

    @FindBy(xpath = "//em[text()='Given']//preceding-sibling::span")
    WebElement givenNameEle;

    @FindBy(xpath = "//em[text()='Family Name']//preceding-sibling::span")
    WebElement familyNameEle;

    @FindBy(xpath = "//em[text()='Patient ID']//following-sibling::span")
    WebElement patientIdEle;

    @FindBy(xpath = "//div[contains(text(),'Start Visit')]//ancestor::a")
    WebElement startVisitEle;

    @FindBy(xpath = "//div[@id='quick-visit-creation-dialog']//p[text()='Are you sure you want to start a visit for Test User now?']")
    WebElement startVisitPopupMessageEle;

    @FindBy(id = "start-visit-with-visittype-confirm")
    WebElement startVisitConfirmButtonEle;

    @FindBy(xpath = "//a[contains(@href,'EndVisit')]")
    WebElement endVisitEle;

    @FindBy(xpath = "//a[contains(@href,'attachments')]")
    WebElement attachmentsButtonEle;

    @FindBy(xpath = "//div[contains(text(),'Delete Patient')]//ancestor::a")
    WebElement deletePatientEle;

    @FindBy(xpath = "//div[@id='delete-patient-creation-dialog']//p[contains(text(),'Are you sure you want to DELETE the patient')]")
    WebElement deletePatientPopupMessageEle;

    @FindBy(id = "delete-reason")
    WebElement deleteReasonEle;

    @FindBy(xpath = "//div[@id='delete-patient-creation-dialog']//button[contains(text(),'Confirm')]")
    WebElement deleteConfirmButtonEle;

    public PatientDetailsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getGivenNameEle() {
        return givenNameEle;
    }

    public WebElement getFamilyNameEle() {
        return familyNameEle;
    }

    public WebElement getPatientIdEle() {
        return patientIdEle;
    }

    public WebElement getStartVisitEle() {
        return startVisitEle;
    }

    public WebElement getStartVisitPopupMessageEle() {
        return startVisitPopupMessageEle;
    }

    public WebElement getStartVisitConfirmButtonEle() {
        return startVisitConfirmButtonEle;
    }

    public WebElement getEndVisitEle() {
        return endVisitEle;
    }

    public WebElement getAttachmentsButtonEle() {
        return attachmentsButtonEle;
    }

    public WebElement getDeletePatientEle() {
        return deletePatientEle;
    }

    public WebElement getDeletePatientPopupMessageEle() {
        return deletePatientPopupMessageEle;
    }

    public WebElement getDeleteReasonEle() {
        return deleteReasonEle;
    }

    public WebElement getDeleteConfirmButtonEle() {
        return deleteConfirmButtonEle;
    }

    public String getGivenName() {
        return waitForElementToBeVisible(getGivenNameEle()).getText().trim();
    }

    public String getFamilyName() {
        return waitForElementToBeVisible(getFamilyNameEle()).getText().trim();
    }

    public String getPatientId() {
        return waitForElementToBeVisible(getPatientIdEle()).getText().trim();
    }

    public boolean verifyPatientDetails(String name) {
        String[] nameArr = name.split(",");
        return getGivenName().contains(nameArr[0].trim()) && getFamilyName().contains(nameArr[1].trim());
    }

    public void clickStartVisit() {
        getStartVisitEle().click();
    }

    public boolean verifyStartVisitPopup() {
        return getStartVisitPopupMessageEle().isDisplayed();
    }

    public void clickStartVisitConfirmButton() {
        getStartVisitConfirmButtonEle().click();
    }

    public boolean verifyEndVisitButton() {
        return getEndVisitEle().isDisplayed();
    }

    public void startVisits() {
        clickStartVisit();
        if (verifyStartVisitPopup()) {
            clickStartVisitConfirmButton();
        } else {
            System.out.println("Start Visit popup not displayed");
            System.exit(0);
        }
    }

    public boolean verifyStartVisit() {
        return verifyEndVisitButton();
    }

    public void clickAttachmentsButton() {
        getAttachmentsButtonEle().click();
    }

    public void clickDeletePatientButton() {
        getDeletePatientEle().click();
    }

    public boolean verifyDeletePatientPopup() {
        return getDeletePatientPopupMessageEle().isDisplayed();
    }

    public void setDeleteReason(String reason) {
        getDeleteReasonEle().sendKeys(reason);
    }

    public void clickDeleteConfirmButton() {
        getDeleteConfirmButtonEle().click();
    }

    public void deletePatient(String reason) {
        clickDeletePatientButton();
        if (verifyDeletePatientPopup()) {
            setDeleteReason(reason);
            clickDeleteConfirmButton();
        } else {
            System.out.println("Start Visit popup not displayed");
            System.exit(0);
        }
    }
}
