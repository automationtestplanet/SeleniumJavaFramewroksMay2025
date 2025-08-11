package openmrs.demo.pageobjects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ExcelUtils {

    public static List<String[]> readDataDrivenTestDataFromExcel(String excelFilePath, String sheetName) {
        try {
            List<String[]> dataDrivenTestDataList = new ArrayList<>();
            FileInputStream fis = new FileInputStream(new File(excelFilePath));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum();
            for (int i = 1; i <= rowCount; i++) {
                Row eachRow = sheet.getRow(i);
                Iterator<Cell> allCells = eachRow.cellIterator();
                int index = 0;
                String[] eachRowDataStrArr = new String[eachRow.getLastCellNum()];
                while (allCells.hasNext()) {
                    Cell eachCell = allCells.next();
                    switch (eachCell.getCellType()) {
                        case STRING -> eachRowDataStrArr[index] = eachCell.getStringCellValue();
                        case NUMERIC -> eachRowDataStrArr[index] = String.valueOf(eachCell.getNumericCellValue());
                        case BOOLEAN -> eachRowDataStrArr[index] = String.valueOf(eachCell.getBooleanCellValue());
                        case BLANK -> {
                            break;
                        }
                    }
                    index++;
                }
                dataDrivenTestDataList.add(eachRowDataStrArr);
            }
            fis.close();
            return dataDrivenTestDataList;
        } catch (Exception e) {
            System.out.println("Exception occurred while Reading the Data Driven test data from excel: " + e.getMessage());
            return null;
        }
    }

    public Iterator<Object[]> readHybridDrivenTestDataFromExcel(String excelFilePath, String sheetName, String testCaseName) {
        List<Map<String, String>> testData = null;
        try {
            FileInputStream fis = new FileInputStream(new File(excelFilePath));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            testData = getTestDataByTestName(sheet, testCaseName);
            fis.close();
            return convertTestDataToIteratorOfObjetArray(testData);
        } catch (Exception e) {
            System.out.println("Exception occurred while Reading the Data Driven test data from excel: " + e.getMessage());
            return null;
        }
    }

    public int getStartRowNo(XSSFSheet sheet, String testCaseName) {
        try {
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK) != null) {
                    if (!sheet.getRow(i).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue().trim().equals("")) {
                        if (sheet.getRow(i).getCell(0).getStringCellValue().trim().equals(testCaseName))
                            return i;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while fetching the Start Row of " + testCaseName + " :" + e.getMessage());
        }
        return 0;
    }

    public int getLastRowNo(XSSFSheet sheet, String testCaseName, int startRowNo) {
        try {
            for (int i = startRowNo + 1; i <= sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK) != null) {
                    if (!sheet.getRow(i).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue().trim().isEmpty()) {
                        if (sheet.getRow(i).getCell(0).getStringCellValue().trim().equals(testCaseName))
                            return i;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while fetching the End Row of " + testCaseName + " :" + e.getMessage());
        }
        return startRowNo;
    }

    public List<String> getTestDataColumnHeaders(XSSFSheet sheet, int startRowNo) {
        List<String> headersList = new ArrayList<>();
        try {
            Row headersRow = sheet.getRow(startRowNo + 1);
            for (int i = 1; i < headersRow.getLastCellNum(); i++) {
                Cell eachHaderCell = headersRow.getCell(i, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                if (eachHaderCell == null) {
                    headersList.add("CELL NOT FOUND");
                } else if (eachHaderCell.getStringCellValue().trim().isEmpty()) {
                    headersList.add("CELL DATA NOT FOUND");
                } else {
                    eachHaderCell.setCellType(CellType.STRING);
                    headersList.add(eachHaderCell.getStringCellValue().trim());
                }
            }
            return headersList;
        } catch (Exception e) {
            System.out.println("Exception occurred while fetching the headers :" + e.getMessage());
            return null;
        }
    }

    public List<Map<String, String>> getTestDataByTestName(XSSFSheet sheet, String testCaseName) {
        List<Map<String, String>> allDataMapsList = new ArrayList<>();
        try {
            int startRowNo = getStartRowNo(sheet, testCaseName);
            int lastRowNo = getLastRowNo(sheet, testCaseName, startRowNo);
            List<String> allHeaders = getTestDataColumnHeaders(sheet, startRowNo);
            for (int i = startRowNo + 2; i < lastRowNo; i++) {
                Map<String, String> eachRowDataMap = new LinkedHashMap<>();
                Row eachDataRow = sheet.getRow(i);
                Cell cellData;
                for (int j = 0; j < allHeaders.size(); j++) {
                    if (!allHeaders.get(j).equalsIgnoreCase("CELL NOT FOUND") && !allHeaders.get(j).equalsIgnoreCase("CELL DATA NOT FOUND")) {
                        cellData = eachDataRow.getCell(j + 1, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                        if (cellData != null) {
                            cellData.setCellType(CellType.STRING);
                        }
                        if (cellData != null && !cellData.getStringCellValue().trim().isEmpty()) {
                            eachRowDataMap.put(allHeaders.get(j), cellData.getStringCellValue().trim());
                        }
                    }
                }
                allDataMapsList.add(eachRowDataMap);
            }
            return allDataMapsList;
        } catch (Exception e) {
            System.out.println("Exception occurred while fetching the " + testCaseName + " test Data: " + e.getMessage());
            return null;
        }
    }

    public Iterator<Object[]> convertTestDataToIteratorOfObjetArray(List<Map<String, String>> allDataMapsList) {
        try {
            List<Object[]> objArrList = new ArrayList<>();
            for (Map<String, String> eachDataMap : allDataMapsList) {
                objArrList.add(new Object[]{eachDataMap});
            }
            return objArrList.iterator();
        } catch (Exception e) {
            System.out.println("Exception occurred while converting the Test Data map to Iterator of Objects Array: " + e.getMessage());
            return null;
        }
    }
}
