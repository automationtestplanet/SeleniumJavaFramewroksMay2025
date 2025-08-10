package openmrs.demo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "username")
    WebElement userNameField;

    @FindBy(name = "password")
    WebElement passwordField;

    @FindBy(id = "loginButton")
    WebElement loginButton;

    public WebElement getUserNameField() {
        return userNameField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public void setUserName(String userName) {
        getUserNameField().sendKeys(userName);
    }

    public void setPassword(String password) {
        getPasswordField().sendKeys(password);
    }

    public void selectModule(String moduleName) {
        driver.findElement(By.id(moduleName)).click();
    }

    public void clickLogin() {
        getLoginButton().click();
    }

    public void loginToOpenMrs(String userName, String password, String moduleName) {
        try {
            setUserName(userName);
            setPassword(password);
            selectModule(moduleName);
            clickLogin();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Exception occurred while login to OpenMrs Application: " + e.getMessage());
        }
    }
}
