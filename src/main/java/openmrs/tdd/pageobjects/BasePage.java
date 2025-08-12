package openmrs.tdd.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BasePage {

    //    WebDriver driver = new ChromeDriver();
    static WebDriver driver;

    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void launchApplication(String url) {
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public boolean verifyPageTitle(String pageName) {
        return driver.getTitle().trim().equalsIgnoreCase(pageName);
    }

    public boolean verifyModulePage(String moduleName) {
        return driver.findElement(By.xpath("//h2[contains(text(),'" + moduleName + "')]")).isDisplayed();
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeStale(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.stalenessOf(element));
    }

    public WebElement waitAndIgnoreException(long waitTIme, TimeUnit timeUnit, Exception e, By locator) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.withTimeout(waitTIme, timeUnit).pollingEvery(2, TimeUnit.SECONDS).ignoring(e.getClass());

//        WebElement givenNameElement = wait.until(new Function<WebDriver,WebElement>() {
//            @Override
//            public WebElement apply(WebDriver driver) {
//                return getGivenNameEle();
//            }
//        });
        return wait.until(driver -> driver.findElement(locator));
    }

    public void closeTheBrowser(){
        driver.close();
    }
}
