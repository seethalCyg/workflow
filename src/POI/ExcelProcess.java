package POI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		File inputFile = new File(
//				"C:\\Users\\sys1\\Desktop\\temp\\tempExcelxls.xls");
//		File outputFile = new File("C:\\Users\\sys1\\Desktop\\temp\\amp.xlsx");
//		saveExcelFile4NatvFrmt(inputFile, outputFile);
		
		File excelFileWthPath=new File("C:\\Users\\sys1\\Desktop\\temp\\2laks.xlsx");
		Workbook workbook =getWorkbook(excelFileWthPath);
		readDataFromExcel(workbook, 0,excelFileWthPath.getAbsolutePath());
	}

	public static Map WBStaticPOIList = new Hashtable();
	
	public static void saveExcelFile4NatvFrmt(File inputFile, File outputFile) {
		Workbook excelWK = null;
		InputStream ip = null;
		FileOutputStream out = null;
		try {
			ip = new FileInputStream(inputFile);
			excelWK = WorkbookFactory.create(ip);
			out = new FileOutputStream(outputFile);
			excelWK.write(out);
			System.out.println("Input File :" + inputFile.getAbsolutePath()
					+ " Saved As : " + outputFile.getAbsolutePath());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				if (out != null) {
					out.close();
				}
				if (ip != null) {
					ip.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public static Workbook getWorkbook(File excelFileWthPath,String key) {
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(excelFileWthPath);
			String excelFilePath = excelFileWthPath.getAbsolutePath();

			if (excelFilePath.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (excelFilePath.endsWith("xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else {
				throw new IllegalArgumentException(
						"The specified file is not Excel file");
			}
			
			
			WBStaticPOIList.put(key, workbook);
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return workbook;
	}
	
	public String findWBList() {
		int countLst=0;
		try{
			
			int staticLstSz=WBStaticPOIList.size();
			if(staticLstSz==0){
				countLst=0;
			}else{
				countLst=staticLstSz;
			}
						
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return String.valueOf(countLst);
	}

	public static Workbook getWorkbook(File excelFileWthPath) {
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(excelFileWthPath);
			String excelFilePath = excelFileWthPath.getAbsolutePath();

			if (excelFilePath.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (excelFilePath.endsWith("xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else {
				throw new IllegalArgumentException(
						"The specified file is not Excel file");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return workbook;
	}

	public static String getCellValue(Cell cell) {
		String cellValue = "";
		try {
//			CellType.
			switch (cell.getCellTypeEnum()) {
			case NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			case STRING:
				cellValue = String.valueOf(cell.getStringCellValue());
				break;
			case BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case ERROR:
				cellValue = String.valueOf(cell.getErrorCellValue());
				break;
			case FORMULA:
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			case BLANK:
				cellValue = "";
				break;
			default:
				cellValue = String.valueOf(cell.getDateCellValue());
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return cellValue;
	}

	public static void readDataFromExcel(Workbook workbook, int sheetIndex,
			String excelFileName) {

		try {
			Iterator<Row> rowIterator = null;
			
			 
              Date testDate1 = new Date();
              DateFormat formatter1 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
              String dateObj1 = formatter1.format(testDate1);
              System.out.println("read excel start " + dateObj1);;
			 

			if (excelFileName.endsWith("xlsx")) {
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(sheetIndex);
				rowIterator = sheet.iterator();
			} else if (excelFileName.endsWith("xls")) {
				HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(sheetIndex);
				rowIterator = sheet.iterator();
			}

			while (rowIterator.hasNext()) {//Row
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) { //Column
					Cell cell = cellIterator.next();
					Color color =null;

					if (excelFileName.endsWith("xlsx")) {
						XSSFCellStyle style = (XSSFCellStyle) cell
								.getCellStyle();
						XSSFFont font = style.getFont();
						XSSFColor colour = font.getXSSFColor();
						
//						 System.out.println("colour.getARGBHex() :"+colour.getARGBHex());;// font colour code
//						 System.out.println("font.getFontName() :"+font.getFontName());;// font Name Calibri
//						 System.out.println("font.getBold() :"+font.getBold());;// Bold
//						 System.out.println("font.getBoldweight() :"+font.getBoldweight());;// Bold
//						 System.out.println("font.getItalic() :"+font.getItalic());;// italic
//						 System.out.println("font.getStrikeout() :"+font.getStrikeout());;// strikeout
//						 System.out.println("font.getUnderline() :"+font.getUnderline());;// underline
//						 System.out.println("font.getFontHeight() :"+font.getFontHeight());;
//						 System.out.println("font.getFontHeightInPoints() :"+font.getFontHeightInPoints());;
						
						 //get background colour
						color = style.getFillForegroundColorColor();                  
//	                    System.out.println("color " + color);
//	                    System.out.println("style.getFillBackgroundColor() " + style.getFillBackgroundColor());
//	                    System.out.println("style.getFillBackgroundColorColor() " + style.getFillBackgroundColorColor());
//	                    System.out.println("style.getFillForegroundColorColor() " + style.getFillForegroundColorColor());
	                    
					} else if (excelFileName.endsWith("xls")) {
						HSSFCellStyle style = (HSSFCellStyle) cell
								.getCellStyle();
						HSSFFont font = style.getFont(workbook);
//						System.out.println("font.getFontName() :"+font.getFontName());;// font Name Calibri
//						System.out.println("font.getBoldweight() :"+font.getBoldweight());;// Bold
//						System.out.println("font.getItalic() :"+font.getItalic());;// italic
//						System.out.println("font.getStrikeout() :"+font.getStrikeout());;// strikeout
//						System.out.println("font.getUnderline() :"+font.getUnderline());;// underline
//						System.out.println("font.getFontHeight() :"+font.getFontHeight());;// FontHeight or FontSize
//						System.out.println("font.getFontHeightInPoints() :"+font.getFontHeightInPoints());;
						
						 //get background colour
						color = style.getFillForegroundColorColor();                  
//						System.out.println("color " + color);
//	                    System.out.println("style.getFillBackgroundColor() " + style.getFillBackgroundColor());
//	                    System.out.println("style.getFillBackgroundColorColor() " + style.getFillBackgroundColorColor());
//	                    System.out.println("style.getFillForegroundColorColor() " + style.getFillForegroundColorColor());
					}

					String cellValue = getCellValue(cell);
//					System.out.println("cellValue " + cellValue);

				}
//				System.out.println("");
			}
			
			
			 Date testDate2 = new Date();
             DateFormat formatter2 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
             String dateObj2 = formatter2.format(testDate2);
             System.out.println("read excel End " + dateObj2);;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
