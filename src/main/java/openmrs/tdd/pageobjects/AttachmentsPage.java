package openmrs.tdd.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class AttachmentsPage extends BasePage {

    @FindBy(xpath = "//ul[@id='breadcrumbs']/li[3][text()]")
    WebElement attachmentsLabelEle;

    @FindBy(xpath = "//div[text()='Click or drop a file here.']")
    WebElement dropAFileEle;

    @FindBy(xpath = "//h3[text()='Caption']//following-sibling::textarea")
    WebElement captionEle;

    @FindBy(xpath = "//button[contains(text(),'Upload file')]")
    WebElement uploadFileButton;

    public AttachmentsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getAttachmentsLabelEle() {
        return attachmentsLabelEle;
    }

    public boolean verifyAttachmentsPage() {
        return attachmentsLabelEle.isDisplayed();
    }

    public WebElement getDropAFileEle() {
        return dropAFileEle;
    }

    public WebElement getCaptionEle() {
        return captionEle;
    }

    public WebElement getUploadFileButton() {
        return uploadFileButton;
    }

    public void clickDropAFileButton() {
        getDropAFileEle().click();
    }

    public void setFilePathInUploadPopupAndClickEnter(String filePath) {
        try {
            Thread.sleep(5000);
            StringSelection strSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strSelection, null);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(2000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Exception occurred while setting the file path in Upload popup: " + e.getMessage());
        }
    }

    public void setCaption(String caption) {
        getCaptionEle().sendKeys(caption);
    }

    public void clickUploadFileButton() {
        if (getUploadFileButton().isEnabled()) {
            getUploadFileButton().click();
        } else {
            System.out.println("File Upload button is not enabled");
            System.exit(0);
        }
    }

    public boolean verifyUploadFileCaption(String caption) {
        return driver.findElement(By.xpath("//div[contains(@class,'att_thumbnail-caption-section')]//p[contains(text(),'" + caption + "')]")).isDisplayed();
    }

    public void addAttachments(String filePath, String caption){
        clickDropAFileButton();
        setFilePathInUploadPopupAndClickEnter(filePath);
        setCaption(caption);
        clickUploadFileButton();
    }

}
