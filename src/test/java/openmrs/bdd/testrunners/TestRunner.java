package openmrs.bdd.testrunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import openmrs.tdd.pageobjects.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

@CucumberOptions(
        features = "src/test/FeatureFiles",
        glue = "openmrs.bdd.stepdefinitions",
        plugin = "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        dryRun = false,
//        tags = "@RegisterSinglePatient",
//        tags = "@RegisterMultiplePatient",

        tags = "@FindSinglePatient",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class TestRunner extends AbstractTestNGCucumberTests {
    static ExcelUtils excelUtils;
    WebDriver driver;
    LoginPage loginPage;


    @BeforeSuite(alwaysRun = true)
    public void beforeSuit() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + TestProperties.getProperty("driver.file.path"));
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        driver = Driver.getDriver().getChromeDriver();
        loginPage = new LoginPage(driver);
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        loginPage.launchApplication(TestProperties.getProperty("qa.env.url"));
        Assert.assertTrue(loginPage.verifyPageTitle("Login"), "Login Page is not displayed");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        loginPage.closeTheBrowser();
    }

}
