import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class dataDrivenExcel {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Sample.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Cars");

        int rowCount = sheet.getPhysicalNumberOfRows();  // Get total rows
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();  // Get total columns

        System.out.println("Rows: " + rowCount);
        System.out.println("Columns: " + colCount);

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i); // Get the row

            if (row == null) continue;  // **Skip if row is null**

            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j); // Get the cell

                String value = "";
                if (cell != null) { // Ensure the cell is not null
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
                            value = "";  // Handle empty cells
                    }
                }
                System.out.print(value + "     ");
            }
            System.out.println(); // Move to the next row
        }

        workbook.close();
        fis.close();
    }
}
