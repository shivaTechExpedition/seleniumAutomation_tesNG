package com.training.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.training.base.BaseTest;




public class ExcelRead extends BaseTest{
	 //public static String dirpath = System.getProperty("user.dir") + "/resources/xldata1.xlsx";
	 
	public Object[][] readAllDataSheet(String dirPath, String sheetName) {
		File file = new File(dirPath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = wb.getSheet(sheetName);
		
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		Object[][] array = new Object[10][2];
		for(int i = firstRowNum; i <= lastRowNum; i++) {
			array[i][0] = sheet.getRow(i).getCell(0).getStringCellValue();
			array[i][1] = sheet.getRow(i).getCell(1).getStringCellValue();
		}
		for(Object[] row: array) {
			for(Object col : row) {
				System.out.print(col + " ");
			}
			System.out.println();
		}
		return array;
	}
	
	public XSSFCell readSingleCellData(String dirPath, String sheetName, int rowNo, int cellNo) {
		File file = new File(dirPath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = wb.getSheet(sheetName);
		return sheet.getRow(rowNo).getCell(cellNo);
		
	}
	
	
	public void writeDataToCell(String dirPath, String sheetName, int rowNo, int cellNo, String value) throws IOException {
		File file = new File(dirPath);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb  = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet(sheetName);
		Row row = sheet.createRow(rowNo);
		Cell cell = row.createCell(cellNo);
		cell.setCellValue(value);
		fis.close();
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		fos.close();
		wb.close();
		
		
	}
	
	public void createXLAndWrite(String filePath, String sheetName, int rowNo, int cellNo, String value) throws IOException {
		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet newSheet = wb.createSheet();
		wb.setSheetName(wb.getSheetIndex(newSheet), sheetName);
		newSheet.createRow(rowNo).createCell(cellNo).setCellValue(value);
		Cell cell = wb.getSheet(sheetName).getRow(rowNo).getCell(cellNo);
		XSSFCellStyle style = wb.createCellStyle();
		
		style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		style.setFillPattern(FillPatternType.BRICKS);
		cell.setCellStyle(style);
		//mylog.info("Background color of the cell set successfully");
		wb.write(fos);
		wb.close();
		fos.close();
		
		
	}
	
//	public void setHyperLink(String dirPath, String sheetName, int rowNo, int cellNo) throws IOException {
//		File file = new File(dirPath);
//		XSSFWorkbook wb = new XSSFWorkbook();
//		XSSFSheet sh = wb.getSheet(sheetName);
//		
//		XSSFCellStyle style = wb.createCellStyle();
//		style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
//		style.setFillPattern(FillPatternType.BIG_SPOTS);
//		cell.setCellStyle(style);
////		if(cell.getCellStyle() == null) {
////			XSSFCellStyle style = (XSSFCellStyle) cell.getSheet().getWorkbook().createCellStyle();
////		
////		style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
////		}
//		FileOutputStream fos = new FileOutputStream(file);
//		wb.write(fos);
//		wb.close();
//		fos.close();
//	   
//	}

//	public static void main(String[] args) throws IOException {
//		
//		ExcelRead xl = new ExcelRead();
//		// xl.writeDataToCell(dirpath, "sep2024", 5, 5, "Shivakumari");
//		//xl.createXLAndWrite(System.getProperty("user.dir")+ "/resources/xlData.xlsx", "sep", 0, 0, "google");
//
//		//XSSFCell cell= xl.readSingleCellData(System.getProperty("user.dir")+ "/resources/xlData.xlsx", "sep", 0, 0);
//		
//		//xl.setHyperLink(System.getProperty("user.dir")+ "/resources/xlData.xlsx","sep", 0, 0);
//		
//		// mylog.info(cell.getStringCellValue());
//		//Object[][] result = xl.readAllDataSheet("C:\\Users\\shiva\\OneDrive\\Desktop\\JavaTraining\\TestNGProject\\src\\test\\resources\\loginCredentials.xlsx", "loginDetails");
//	}

}
