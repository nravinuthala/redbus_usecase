package utils;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ReadData {
	
	public static Object[][] readExcel(String fileName, String sheetName) throws IOException{
		FileInputStream fis = new FileInputStream(fileName); // Read data file
		HSSFWorkbook wb = new HSSFWorkbook(fis); // Create workbook corresponding to the input file
		Sheet sheet = wb.getSheet(sheetName); // Get the sheet from the workbook
		Row row0 = sheet.getRow(0); 
		
		int rowNum = sheet.getPhysicalNumberOfRows(); //Count number of rows
		int colNum = row0.getLastCellNum();
		
		Object[][] data = new Object[rowNum - 1][colNum]; 
		
		System.out.println("num rows :: " + rowNum);
		
		for(int i = 0; i < rowNum - 1; i++) {
			Row row = sheet.getRow(i+1);
			for(int j = 0; j < colNum; j++) {
				if(row == null)
					data[i][j] = "";
				else {
					Cell cell = row.getCell(j);
					if(cell == null)
						data[i][j] = "";
					else data[i][j] = cell.getStringCellValue();
				}
			}
		}
		wb.close();
		return data;
	}
}
