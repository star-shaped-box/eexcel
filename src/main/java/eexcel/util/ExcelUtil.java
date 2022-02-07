package eexcel.util;

import eexcel.bean.ExcelLineBean;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    public static Map<String,ExcelLineBean> readDegreeToExcelLineBeanMap(String filePath) {
        Map<String,ExcelLineBean> degreeToExcelLineBeanMap = new HashMap<>();
        File excelFile = new File(filePath);
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(excelFile);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            for (int sheetNum = 0; sheetNum < xssfWorkbook.getNumberOfSheets(); ++sheetNum) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNum);
                Map<String, Integer> columnNameToIndexMap = parseColumnNameToIndexMap(xssfSheet.getRow(0));
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); ++rowNum) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    String degree = String.valueOf((int)xssfRow.getCell(0).getNumericCellValue());
                    Map<String, Double> noteToFrequencyMap = buildNoteToFrequencyMap(columnNameToIndexMap, xssfRow);
                    degreeToExcelLineBeanMap.put(degree,new ExcelLineBean(degree, noteToFrequencyMap));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(degreeToExcelLineBeanMap.toString());
        return degreeToExcelLineBeanMap;
    }

    private static Map<String, Double> buildNoteToFrequencyMap(Map<String, Integer> columnNameToIndexMap, XSSFRow xssfRow) {
        Map<String, Double> noteToFrequencyMap = new HashMap<>();
        for (Map.Entry<String,Integer> columnNameToIndexEntry :columnNameToIndexMap.entrySet()) {
            String note = columnNameToIndexEntry.getKey();
            int frequencyIndex = columnNameToIndexEntry.getValue();
            double frequency = xssfRow.getCell(frequencyIndex).getNumericCellValue();
            noteToFrequencyMap.put(note,frequency);
        }
        return noteToFrequencyMap;
    }

    public static Map<String, Integer> parseColumnNameToIndexMap(XSSFRow xssfHeader) {
        Map<String, Integer> columnNameToIndexMap = new HashMap<>();
        for (int colNum = 1; colNum < xssfHeader.getLastCellNum(); ++colNum) {
            columnNameToIndexMap.put(xssfHeader.getCell(colNum).getStringCellValue().trim(), colNum);
        }
        return columnNameToIndexMap;
    }
}
