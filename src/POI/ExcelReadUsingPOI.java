package POI;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;


public class ExcelReadUsingPOI {

	/**
	 * @param args
	 */
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File excelFileWthPath=new File("C:\\Users\\Developer\\Desktop\\vijay.xlsx");
		Workbook workbook =ExcelProcess.getWorkbook(excelFileWthPath);
		ArrayList columnNameAL = new ArrayList();
		ArrayList rowValueAL = new ArrayList();
		int limitedRowNumber=8;
		int staringtRow=3;
		readDataFromExcel(workbook, 0,excelFileWthPath.getAbsolutePath(),staringtRow,limitedRowNumber,columnNameAL,rowValueAL);
		
		for(int j=0;j<columnNameAL.size();j++){
			String columnName=(String)columnNameAL.get(j);
			System.out.print(" columnName "+columnName);
		}
		
		
		
		System.out.println(" ");
		for(int j=0;j<rowValueAL.size();j++){
			LoadDataTableProcess colObj=(LoadDataTableProcess)rowValueAL.get(j);
			 ArrayList eachLineColumnValueAL =colObj.eachLineColumnValueAL;
			
       for(int k=0;(eachLineColumnValueAL!=null && k<eachLineColumnValueAL.size());k++){
	
     	LoadDataTableProcess columnObj=(LoadDataTableProcess)eachLineColumnValueAL.get(k);
	
	    System.out.println(" columnValue: "+columnObj.getColumnValue());
				
		}
			
			
//			 ArrayList eachLineColumnValueAL = new ArrayList<>();
//			String colValAry[]=((LoadDataTableProcess)colObj.eachLineColumnValueAL.get(j)).getColumnValue().split("\\$\\^");
//			for(int k=0;(colValAry!=null && k<colValAry.length);k++){
//				
//				System.out.print(" columnValue: "+colValAry[k]);
//			}
//			System.out.println(" ");
			
		}

	}
	
	public static void readDataFromExcel(Workbook workbook, int sheetIndex,
			String excelFileName,int staringtRow,int limitedRowNumber,ArrayList columnNameAL,ArrayList rowValueAL) {

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
			
			 ArrayList eachLineColumnValueAL = new ArrayList<>();
			 LoadDataTableProcess ldTbltPrc = null;
			int rowNumb=1;
			while (rowIterator.hasNext()) {//Row
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				String rowValue=""; 
				while (cellIterator.hasNext()) { //Column
					Cell cell = cellIterator.next();
					Color color =null;

					if (excelFileName.endsWith("xlsx")) {
						XSSFCellStyle style = (XSSFCellStyle) cell
								.getCellStyle();
						XSSFFont font = style.getFont();
						XSSFColor colour = font.getXSSFColor();						
						 //get background colour
						color = style.getFillForegroundColorColor(); 
	                    
					} else if (excelFileName.endsWith("xls")) {
						HSSFCellStyle style = (HSSFCellStyle) cell
								.getCellStyle();
						HSSFFont font = style.getFont(workbook);
						
						 //get background colour
						color = style.getFillForegroundColorColor();  
					}

					String cellValue = ExcelProcess.getCellValue(cell);
				//	 Cell cellValue = row.getCell(0);
					Cell	selcolValue=row.getCell(0);
					
			//	System.out.print("selcolValue :" + selcolValue);
				//	System.out.print("vijay :" + cellValue);
					String tempcell;
					rowValue=rowValue+"$^"+cellValue;
					
					if(cellValue.length()>=10){  
						
						tempcell= cellValue.substring(0, 10);
						
					}else{
						tempcell=cellValue;
					}
					
					if(rowNumb==1){						
						columnNameAL.add(cellValue);
					}else if(rowNumb >=staringtRow){						
						ldTbltPrc = new LoadDataTableProcess(cellValue,"black", String.valueOf(rowNumb),tempcell);
						eachLineColumnValueAL.add(ldTbltPrc);
						
					}
					

				}// end of column
				System.out.println("EEEEEEEEEEE :"+eachLineColumnValueAL.size());
				if(rowValue!=null && !rowValue.equals("")){
					rowValue=rowValue.replaceFirst("\\$\\^", "");	
				}
				if(rowNumb!=1 && (rowNumb>=staringtRow)){		
					//rowValue=rowValue.replaceFirst("\\$\\^", "");
					//System.out.println("rowValue : "+rowValue );
//					rowValueAL.add(rowValue);
					ldTbltPrc = new LoadDataTableProcess(eachLineColumnValueAL);
					rowValueAL.add(ldTbltPrc);
					eachLineColumnValueAL = new ArrayList<>();
				}
				
				
				if(rowNumb==limitedRowNumber){
					break;
				}
				 rowNumb++;
			}// end of row
			
			
			 Date testDate2 = new Date();
             DateFormat formatter2 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
             String dateObj2 = formatter2.format(testDate2);
             System.out.println("read excel End " + dateObj2);;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
