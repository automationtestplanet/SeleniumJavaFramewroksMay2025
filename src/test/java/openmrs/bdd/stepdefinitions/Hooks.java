package openmrs.bdd.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import openmrs.tdd.pageobjects.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Hooks {
    WebDriver driver = Driver.getDriver().getChromeDriver();
    LoginPage loginPage = new LoginPage(driver);
    HomePage homePage = new HomePage(driver);
    int i = 1;

    @Before
    public void beforeMethod() {
        loginPage.loginToOpenMrs(TestProperties.getProperty("user.name"), TestProperties.getProperty("password"), "Registration Desk");
    }

    @After
    public void afterMethod() {
        homePage.clickLogout();
        Assert.assertTrue(loginPage.verifyPageTitle("Login"), "Login Page is not displayed");
    }

    @AfterStep
    public void captureScreenshots(Scenario scenario) {
        byte[] byteArr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(byteArr, "image/png", scenario.getName() + "_Screenshot" + i++);
    }
}
