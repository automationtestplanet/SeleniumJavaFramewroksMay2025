package openmrs.tdd.pageobjects;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils extends BasePage {

    public static String screenshotsFolderPath = System.getProperty("user.dir") + "//target//screenshots//";

    public Utils(WebDriver driver) {
        super(driver);
    }

    public static String captureScreenshot() {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            String screenshotName = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()).replaceAll("[^0-9]", "") + ".png";
            File scrrenshot = ts.getScreenshotAs(OutputType.FILE);
            String destinationPath = screenshotsFolderPath + screenshotName;
            FileUtils.copyFile(scrrenshot, new File(destinationPath));
            return screenshotName;
        } catch (Exception e) {
            System.out.println("Exception occurred while Capturing the screenshot: " + e.getMessage());
            return null;
        }

    }


}
