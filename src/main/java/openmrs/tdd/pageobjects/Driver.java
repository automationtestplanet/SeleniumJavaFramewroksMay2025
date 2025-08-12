package openmrs.tdd.pageobjects;


import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Getter
public class Driver {
    @Getter
    private static final Driver driver = new Driver();

    private final WebDriver chromeDriver;

    private Driver(){
        chromeDriver = new ChromeDriver();
    }

}
