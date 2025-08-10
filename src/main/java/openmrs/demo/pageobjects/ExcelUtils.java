package openmrs.demo.pageobjects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    public static List<String[]> readDataDrivenTestDataFromExcel(String excelFilePath, String sheetName) {
        try {
            List<String[]> dataDrivenTestDataList = new ArrayList<>();
            FileInputStream fis = new FileInputStream(new File(excelFilePath));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> itr = sheet.rowIterator();

//            while(itr.hasNext()){
//               Row eachRow = itr.next();
//
//                Iterator<Cell> allCells = eachRow.cellIterator();
//
//                while (allCells.hasNext()) {
//                    Cell eachCell = allCells.next();
//                    switch (eachCell.getCellType()) {
//                        case STRING -> System.out.println(eachCell.getStringCellValue());
//                        case NUMERIC -> System.out.println(String.valueOf(eachCell.getNumericCellValue()));
//                        case BOOLEAN -> System.out.println(String.valueOf(eachCell.getBooleanCellValue()));
//                        case BLANK -> {
//                            break;
//                        }
//                    }
//
//                }
//            }

            for (int i = 1; i <= 3; i++) {
                Row eachRow = sheet.getRow(i);
                Iterator<Cell> allCells = eachRow.cellIterator();
                int index = 0;
                String[] eachRowDataStrArr = new String[2];
                while (allCells.hasNext()) {
                    Cell eachCell = allCells.next();
                    switch (eachCell.getCellType()) {
                        case STRING -> eachRowDataStrArr[index] = eachCell.getStringCellValue();
                        case NUMERIC -> eachRowDataStrArr[index] = String.valueOf(eachCell.getNumericCellValue());
                        case BOOLEAN -> eachRowDataStrArr[index] = String.valueOf(eachCell.getBooleanCellValue());
                        case BLANK -> {break;}
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
}
