package openmrs.demo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FindPatientPage extends BasePage {

    @FindBy(id = "patient-search")
    WebElement patientSearchFieldEle;

    @FindBy(xpath = "//table[@id='patient-search-results-table']/thead/tr[1]/th/div")
    List<WebElement> searchPatientTableColumnsEle;

    @FindBy(xpath = "//table[@id='patient-search-results-table']/tbody/tr[1]")
    WebElement searchPatientTableFirstRecordEle;

    @FindBy(xpath = "//td[contains(text(),'No matching records found')]")
    WebElement noRecordsFoundMessageEle;

    public FindPatientPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getPatientSearchFieldEle() {
        return patientSearchFieldEle;
    }

    public List<WebElement> getSearchPatientTableColumnsEle() {
        return searchPatientTableColumnsEle;
    }

    public WebElement getSearchPatientTableFirstRecordEle() {
        return searchPatientTableFirstRecordEle;
    }

    public WebElement getNoRecordsFoundMessageEle() {
        return noRecordsFoundMessageEle;
    }

    public void enterValueInPatientSearchField(String searchValue) {
        try {
            getPatientSearchFieldEle().sendKeys(searchValue);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Exception occurred while setting the patient name in search field: " + e.getMessage());
        }
    }

    public Map<String, Integer> getSearchPatientTableColumnNames() {
        Map<String, Integer> searchTableColumnsIndexMap = new LinkedHashMap<>();
        int index = 1;
        for (WebElement eachElement : getSearchPatientTableColumnsEle()) {
            searchTableColumnsIndexMap.put(eachElement.getText().trim(), index);
            index++;
        }
        return searchTableColumnsIndexMap;
    }

    public int getSearchPatientTableColumnIndex(String columnName) {
        return getSearchPatientTableColumnNames().get(columnName);
    }

    public String getSearchPatientTableColumnValue(String columnName) {
        try {
            WebElement element = driver.findElement(By.xpath("//table[@id='patient-search-results-table']/tbody/tr[1]/td[" + getSearchPatientTableColumnIndex(columnName) + "]"));
            waitForElementToBeVisible(element);
            return element.getText().trim();
        } catch (Exception e) {
            System.out.println("Exception occurred while fetching Search Patient Value: " + e.getMessage());
            return null;
        }
    }

    public void clickSearchPatientTableFirstRecord() {
        getSearchPatientTableFirstRecordEle().click();
    }

    public void searchPatient(String patientName) {
        enterValueInPatientSearchField(patientName);
    }

    public boolean verifySearchPatientRecord(String columnName, String expectedColumnValue) {
        return getSearchPatientTableColumnValue(columnName).equalsIgnoreCase(expectedColumnValue);
    }

    public boolean verifyNoRecordsFoundMessage() {
        return getNoRecordsFoundMessageEle().isDisplayed();
    }
}