package POI;

import java.io.Serializable;
import java.util.ArrayList;

public class LoadDataTableProcess implements Serializable{

	/**
	 * @param args
	 */
	private String columnValue, dsplyclorValue, rowIndex,tempcellvalue;

	

	public ArrayList eachLineColumnValueAL = new ArrayList<>();
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public LoadDataTableProcess(String columnValue,String dsplyclorValue,String tempcellvalue){
		this.columnValue=columnValue;
		this.dsplyclorValue=dsplyclorValue;	
		this.tempcellvalue=tempcellvalue;
	}	
	
	public LoadDataTableProcess(String columnValue,String dsplyclorValue, String rowIndex,String tempcellvalue){
		this.columnValue=columnValue;
		this.dsplyclorValue=dsplyclorValue;
		this.rowIndex=rowIndex;
		this.tempcellvalue=tempcellvalue;
	}
	
	public LoadDataTableProcess(ArrayList eachLineColumnValueAL){
		this.eachLineColumnValueAL.addAll(eachLineColumnValueAL);
	}

	public String getDsplyclorValue() {
		return dsplyclorValue;
	}

	public void setDsplyclorValue(String dsplyclorValue) {
		this.dsplyclorValue = dsplyclorValue;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	public ArrayList getEachLineColumnValueAL() {
		return eachLineColumnValueAL;
	}

	public void setEachLineColumnValueAL(ArrayList eachLineColumnValueAL) {
		this.eachLineColumnValueAL = eachLineColumnValueAL;
	}

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
	public String getTempcellvalue() {
		return tempcellvalue;
	}

	public void setTempcellvalue(String tempcellvalue) {
		this.tempcellvalue = tempcellvalue;
	}

}
