package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.util.Hashtable;

public class excelReader {

    @DataProvider(name = "demoData")
    public Object[][] getData() {
        return sheetData(System.getProperty("user.dir") + "\\src\\test\\resources\\Sample.xlsx", "Cars");
    }

    public Object[][] sheetData(String fileName,String sheet) {
        Object[][] data = null;
        try {
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet1 = workbook.getSheet(sheet);

            int rowCount = sheet1.getPhysicalNumberOfRows();  // Get physical row count
            int cellCount = sheet1.getRow(0).getPhysicalNumberOfCells();  // Get physical cell count

            // Initialize Object[][] to hold the data

            data = new Object[rowCount - 1][1];
            // Read column headers

            String[] headers = new String[cellCount];
            Row headerRow = sheet1.getRow(0);
            for (int col = 0; col < cellCount; col++) {
                headers[col] = headerRow.getCell(col).getStringCellValue();
            }
            for (int row = 1; row < rowCount; row++) {
                Hashtable<String, String> table = new Hashtable<>();
                Row dataRow = sheet1.getRow(row);
                for (int col = 0; col < cellCount; col++) {
                    Cell cell = dataRow.getCell(col);
                    String value = "";

                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                value = String.valueOf(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            default:
                                value = "";  // If the cell is empty or of an unknown type
                        }
                    }
                    table.put(headers[col], value); // Add column header and cell value to Hashtable
                }
                // Add the Hashtable to the data array
                data[row - 1][0] = table;
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
