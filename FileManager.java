package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileManager {
	
	private String compName;
	
	public FileManager() {}

	public FileManager(String compName) {
		this.compName = compName;
	}
	
	public ArrayList<String> readFile() throws IOException {
		
		FileInputStream file = new FileInputStream(new File("Competitions Participations.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheet(compName);
		DataFormatter dataFormatter = new DataFormatter();
		ArrayList<String> list = new ArrayList<String>();
		
		//adding compititon info
		list.add(sheet.getRow(0).getCell(1).getStringCellValue());
		list.add(sheet.getRow(1).getCell(1).getStringCellValue());
		String date = dataFormatter.formatCellValue(sheet.getRow(2).getCell(1));
		list.add(date);
		
		int rows = sheet.getLastRowNum();
		int cols;
		
		if(isTeam())
			cols = 6;
		else
			cols = 4;
		
		for(int i = 5; i <= rows; i++) {
			
			for(int j = 0; j <= cols; j++) {
				
				String elm = dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
				list.add(elm);
				
			}
			
		}
		
		return list;
	
	}
	
	public void writeComp(String compLink, String compDate, boolean isTeam) throws IOException {
		
		FileInputStream inFile = new FileInputStream("Competitions Participations.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(inFile);
		XSSFSheet sheet = wb.createSheet(compName);
		
		XSSFCellStyle style = wb.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		
		Object[][] compInfo = {
				{"Competition Name", compName},
				{"Competition Link", compLink},
				{"Competition date", compDate}
		};
		
		int compRows = compInfo.length;
		int compCols = compInfo[0].length;
		
		for(int i = 0; i < compRows; i++) {
			
			XSSFRow row = sheet.createRow(i);
			
			for(int j = 0; j < compCols; j++) {
				
				XSSFCell cell = row.createCell(j);
				//cell.setCellStyle(style);
				Object val = compInfo[i][j];
				cell.setCellValue((String) val);
				sheet.autoSizeColumn(j);
				
			}
			
		}
		
		String[] studentInfo = {"", "Student ID", "Student Name", "Major", "Rank"};
		String[] teamInfo = {"", "Student ID", "Student Name", "Major", "team", "Team Name", "Rank"};
		
		if(isTeam) {
			
			XSSFRow rowLabel = sheet.createRow(4);
			
			for(int i = 0; i < teamInfo.length; i++) {
				
				XSSFCell cell = rowLabel.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(teamInfo[i]);
				sheet.autoSizeColumn(i);
				
			}
			
		}
		
		
		
		else {
			
			XSSFRow rowLabel = sheet.createRow(4);
			
			for(int i = 0; i < studentInfo.length; i++) {
				
				XSSFCell cell = rowLabel.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(studentInfo[i]);
				sheet.autoSizeColumn(i);
				
			}
			
		}
		
		FileOutputStream outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.close();
		
	}
	
	public boolean isTeam() throws IOException{
		
		FileInputStream file = new FileInputStream(new File("Competitions Participations.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheet(compName);
		
		if(sheet.getRow(4).getCell(4).getStringCellValue().equals("team"))
			return true;
		
		return false;
		
	}
	
	public boolean isSheetExist() throws IOException{
		
		FileInputStream file = new FileInputStream(new File("Competitions Participations.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			
			if (wb.getSheetName(i).equals(compName)) {
	       	   return true;
	        } 
	           
	    }
		
		return false;
		
	}
	
	public ArrayList<String> getSheets() throws IOException{
		
		FileInputStream file = new FileInputStream(new File("Competitions Participations.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			list.add(wb.getSheetName(i));
	    }
		
		return list;
		
	}
	
	public void writeParticipations(ArrayList<String> list) throws IOException {
		
		FileInputStream inFile = new FileInputStream("Competitions Participations.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(inFile);
		XSSFSheet sheet = wb.getSheet(compName);
		
		XSSFCellStyle style = wb.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		
		int lastRow = sheet.getLastRowNum();
			
		Row row = sheet.createRow(++lastRow);
		Cell index = row.createCell(0);
		index.setCellStyle(style);
		index.setCellValue(lastRow-4);
			
		for(int i = 0; i < list.size(); i++) {
			Cell cell = row.createCell(i+1);
			cell.setCellStyle(style);
			cell.setCellValue(list.get(i));
			sheet.autoSizeColumn(i+1);
		}
		    
		inFile.close();	
		
		FileOutputStream outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		wb.close();
		outFile.close();
		
	}
	
}
