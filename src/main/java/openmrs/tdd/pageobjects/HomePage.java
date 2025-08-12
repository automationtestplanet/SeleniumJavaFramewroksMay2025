package openmrs.tdd.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//i[@class='icon-home small']//parent::a")
    WebElement homeIconEle;
    @FindBy(partialLinkText = "Logout")
    WebElement logoutEle;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public WebElement getHomeIconEle() {
        return homeIconEle;
    }

    public WebElement getLogoutEle() {
        return logoutEle;
    }

    public boolean verifyModuleTile(String moduleName) {
        return driver.findElement(By.partialLinkText(moduleName)).isDisplayed();
    }

    public void clickModule(String moduleName) {
        driver.findElement(By.partialLinkText(moduleName)).click();
    }

    public void clickHomeIcon() {
        getHomeIconEle().click();
    }

    public void clickLogout() {
        try {
            Thread.sleep(5000);
            getLogoutEle().click();
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Exception occurred while clicking on logout: " + e.getMessage());
        }
    }
}
