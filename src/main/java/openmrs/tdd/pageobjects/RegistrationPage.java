package openmrs.tdd.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void setName(String name) {
        String[] nameArr = name.split(",");
        driver.findElement(By.name("givenName")).sendKeys(nameArr[0].trim());
        driver.findElement(By.name("familyName")).sendKeys(nameArr[1].trim());
    }

    public void clickNextButton() {
        driver.findElement(By.id("next-button")).click();
    }

    public void selectGender(String gender) {
        WebElement genderDropdownEle = driver.findElement(By.id("gender-field"));
        Select genderDropDown = new Select(genderDropdownEle);
        genderDropDown.selectByVisibleText(gender);
    }

    public void setDateOfBirth(String dateOfBirth) {
        String[] dateOfBirthArr = dateOfBirth.split(",");
        driver.findElement(By.id("birthdateDay-field")).sendKeys(dateOfBirthArr[0].trim());
        WebElement birthMonthDropdownEle = driver.findElement(By.id("birthdateMonth-field"));
        Select birthMonthDropDown = new Select(birthMonthDropdownEle);
        birthMonthDropDown.selectByVisibleText(dateOfBirthArr[1].trim());
        driver.findElement(By.id("birthdateYear-field")).sendKeys(dateOfBirthArr[2].trim());
    }

    public void setAddress(String address) {
        String[] addressArr = address.split(",");
        driver.findElement(By.id("address1")).sendKeys(addressArr[0].trim());
        driver.findElement(By.id("address2")).sendKeys(addressArr[1].trim());
        driver.findElement(By.id("cityVillage")).sendKeys(addressArr[2].trim());
        driver.findElement(By.id("stateProvince")).sendKeys(addressArr[3].trim());
        driver.findElement(By.id("country")).sendKeys(addressArr[4].trim());
        driver.findElement(By.id("postalCode")).sendKeys(addressArr[5].trim());
    }

    public void setPhoneNumber(String phoneNumber) {
        driver.findElement(By.name("phoneNumber")).sendKeys(phoneNumber);
    }

    public void setRelatives(String relatives) {
        String[] relativesArr = relatives.split(",");
        WebElement relationShipDropdownEle = driver.findElement(By.id("relationship_type"));
        Select relationShipDropDown = new Select(relationShipDropdownEle);
        relationShipDropDown.selectByVisibleText(relativesArr[0].trim());
        driver.findElement(By.cssSelector("input[placeholder='Person Name']")).sendKeys(relativesArr[1].trim());
    }

    public boolean verifyEnteredDetails(String expectedName, String expectedGender, String expectedDateOfBirth, String expectedPhoneNumber) {
        String actualName = driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Name')]//parent::p")).getText();
        String actualGender = driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Gender')]//parent::p")).getText();
        String actualBirthDate = driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Birthdate')]//parent::p")).getText();
        String actualPhoneNumber = driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Phone Number')]//parent::p")).getText();
        return actualName.contains(expectedName) && actualGender.contains(expectedGender) && actualBirthDate.contains(expectedDateOfBirth) && actualPhoneNumber.contains(expectedPhoneNumber);
    }

    public void clickConfirmButton() {
        driver.findElement(By.cssSelector("input[value='Confirm']")).click();
    }

    public void clickCancelButton() {
        driver.findElement(By.id("cancelSubmission")).click();
    }

    public void enterPatientDetails(String name, String gender, String dateOfBirth, String address, String phoneNumber, String relatives) {
        setName(name);
        clickNextButton();
        selectGender(gender);
        clickNextButton();
        setDateOfBirth(dateOfBirth);
        clickNextButton();
        setAddress(address);
        clickNextButton();
        setPhoneNumber(phoneNumber);
        clickNextButton();
        setRelatives(relatives);
        clickNextButton();
    }
}
