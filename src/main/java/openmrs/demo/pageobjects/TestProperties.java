package openmrs.demo.pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class TestProperties {
    public static Properties properties;
    public static File inputFile;
    static FileInputStream fis;

    static {
        try {
            System.out.println("Static block is executed");
            properties = new Properties();
            inputFile = new File(System.getProperty("user.dir") + "//OpenMrsTest.properties");
            fis = new FileInputStream(inputFile);
            properties.load(fis);
        } catch (Exception e) {
            System.out.println("Exception occurred while reading properties: " + e.getMessage());
        }
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName).trim();
    }

    public static void setProperty(String propertyName, String propertyValue) {
        try {
            properties.setProperty(propertyName, propertyValue);
            FileOutputStream fos = new FileOutputStream(inputFile);
            properties.store(fos, "updated by Test User");
        } catch (Exception e) {
            System.out.println("Exception occurred while Setting the property: " + e.getMessage());
        }
    }

}
