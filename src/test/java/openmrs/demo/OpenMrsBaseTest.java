package openmrs.demo;

import openmrs.demo.pageobjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class OpenMrsBaseTest {

    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    RegistrationPage registrationPage;
    PatientDetailsPage patientDetailsPage;
    FindPatientPage findPatientPage;
    AttachmentsPage attachmentsPage;
    Utils utils;
    static ExcelUtils excelUtils;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuit() {
        System.out.println(System.getProperty("user.dir") + TestProperties.getProperty("driver.file.path"));
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + TestProperties.getProperty("driver.file.path"));
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        registrationPage = new RegistrationPage(driver);
        patientDetailsPage = new PatientDetailsPage(driver);
        findPatientPage = new FindPatientPage(driver);
        attachmentsPage = new AttachmentsPage(driver);
        utils = new Utils(driver);
        excelUtils = new ExcelUtils();
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        loginPage.launchApplication(TestProperties.getProperty("qa.env.url"));
        Assert.assertTrue(loginPage.verifyPageTitle("Login"), "Login Page is not displayed");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        loginPage.loginToOpenMrs(TestProperties.getProperty("user.name"), TestProperties.getProperty("password"), "Registration Desk");
        Assert.assertTrue(homePage.verifyPageTitle("Home"), "Hope Page is not displayed");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        homePage.clickLogout();
        Assert.assertTrue(loginPage.verifyPageTitle("Login"), "Login Page is not displayed");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        loginPage.closeTheBrowser();
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        driver = null;
        loginPage = null;
        homePage = null;
        registrationPage = null;
        patientDetailsPage = null;
        findPatientPage = null;
        attachmentsPage = null;
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuit() {
        System.out.println("Test Suit execution completed");
    }

    @DataProvider(name = "DataDrivenTestData")
    public Iterator<String[]> dataDrivenDataProvider(Method method){
        String excelFilePath = System.getProperty("user.dir")+TestProperties.getProperty("data.driven.test.data.path");
        String sheetName = method.getName().trim();
        List<String[]> listOfStrings = excelUtils.readDataDrivenTestDataFromExcel(excelFilePath,sheetName);;
        return listOfStrings.iterator();
    }


}
