package model;

import java.util.Vector;

import model.rollupData;

public class rollupData {

	/**
	 * @param args
	 */
	
//	private String rowWidh;
//	private String docAmt;
//	private String parentRowwdh;
//	public String getRowWidh() {
//		return rowWidh;
//	}
//	public void setRowWidh(String rowWidh) {
//		this.rowWidh = rowWidh;
//	}
//	public String getDocAmt() {
//		return docAmt;
//	}
//	public void setDocAmt(String docAmt) {
//		this.docAmt = docAmt;
//	}
//	public String getParentRowwdh() {
//		return parentRowwdh;
//	}
//	public void setParentRowwdh(String parentRowwdh) {
//		this.parentRowwdh = parentRowwdh;
//	}
//	
//	public rollupData(String rowWidh,String docAmt,String parentRowwdh){
//		this.rowWidh=rowWidh;
//		this.docAmt=docAmt;
//		this.parentRowwdh=parentRowwdh;
//	}
	////////
	
	/*ROW_WID	NUMBER(10,0)
	HIER_CAT_CODE	VARCHAR2(4000 BYTE)
	HIERARCHY_ID	VARCHAR2(4000 BYTE)
	HIER20_CODE	VARCHAR2(4000 BYTE)
	HIER20_NAME	VARCHAR2(4000 BYTE)
	SORT_ORDER	NUMBER(10,0)
	DOC_AMT	NUMBER(22,7)
	PERIOD_WID	NUMBER(22,7)
	MTD_AMT	NUMBER(22,7)
	QTD_AMT	NUMBER(22,7)
	YTD_AMT	NUMBER(22,7)
	PARENT_CODE	NUMBER(22,7)
	OTHER_DOC_AMT	VARCHAR2(4000 BYTE)
GL_ACCOUNT_WID	VARCHAR2(4000 BYTE)
ACCT_PERIOD_END_DT_WID	NUMBER*/
	
	/*ROW_WID	NUMBER(10,0)
	HIER_CAT_CODE	VARCHAR2(4000 BYTE)
	HIERARCHY_ID	VARCHAR2(4000 BYTE)
	GL_ACCOUNT_WID	VARCHAR2(4000 BYTE)
	DOC_AMT	VARCHAR2(4000 BYTE)
	PERIOD_WID	VARCHAR2(4000 BYTE)
	SORT_ORDER	NUMBER(10,0)
	ACCOUNT_SEG1_CODE	VARCHAR2(4000 BYTE)
	ACCOUNT_SEG2_CODE	VARCHAR2(4000 BYTE)
	ACCOUNT_SEG3_CODE	VARCHAR2(4000 BYTE)
	ACCOUNT_SEG4_CODE	VARCHAR2(4000 BYTE)
	ACCOUNT_SEG5_CODE	VARCHAR2(4000 BYTE)
	ACCOUNT_SEG6_CODE	VARCHAR2(4000 BYTE)
	ACCOUNT_SEG7_CODE	VARCHAR2(4000 BYTE)*/
	private int ROW_WID;
	private String HIER_CAT_CODE;
	private String HIERARCHY_ID;
	private String GL_ACCOUNT_WID;
//	private double DOC_AMT;
	private double PERIOD_WID;
	private int SORT_ORDER;
	private String ACCOUNT_SEG1_CODE;
	private String ACCOUNT_SEG2_CODE;
	private String ACCOUNT_SEG3_CODE;
	private String ACCOUNT_SEG4_CODE;
	private String ACCOUNT_SEG5_CODE;
	private String ACCOUNT_SEG6_CODE;
	private String ACCOUNT_SEG7_CODE;
	//////////	
	private String HIER20_CODE;
	private String HIER20_NAME;
	private double ACCT_PERIOD_END_DT_WID;
	private String PARENT_ROW_WID;
	private double MTD_AMT;
	private String nodeType;
	///////////
	private String QTD_AMT;
	private String YTD_AMT;
	private int PARENT_CODE;
//	private double LEDGER_WID;
	
	public double getPERIOD_WID() {
		return PERIOD_WID;
	}
	public void setPERIOD_WID(double pERIOD_WID) {
		PERIOD_WID = pERIOD_WID;
	}
	public double getACCT_PERIOD_END_DT_WID() {
		return ACCT_PERIOD_END_DT_WID;
	}
	public void setACCT_PERIOD_END_DT_WID(double aCCT_PERIOD_END_DT_WID) {
		ACCT_PERIOD_END_DT_WID = aCCT_PERIOD_END_DT_WID;
	}

	
	
	public int getROW_WID() {
		return ROW_WID;
	}
	public void setROW_WID(int rOW_WID) {
		ROW_WID = rOW_WID;
	}
//	public double getDOC_AMT() {
//		return DOC_AMT;
//	}
//	public void setDOC_AMT(double dOC_AMT) {
//		DOC_AMT = dOC_AMT;
//	}

	public int getSORT_ORDER() {
		return SORT_ORDER;
	}
	public void setSORT_ORDER(int sORT_ORDER) {
		SORT_ORDER = sORT_ORDER;
	}

	public double getMTD_AMT() {
		return MTD_AMT;
	}
	public void setMTD_AMT(double mTD_AMT) {
		MTD_AMT = mTD_AMT;
	}
	public int getPARENT_CODE() {
		return PARENT_CODE;
	}
	public void setPARENT_CODE(int pARENT_CODE) {
		PARENT_CODE = pARENT_CODE;
	}
	public String getQTD_AMT() {
		return QTD_AMT;
	}
	public void setQTD_AMT(String qTD_AMT) {
		QTD_AMT = qTD_AMT;
	}
	public String getYTD_AMT() {
		return YTD_AMT;
	}
	public void setYTD_AMT(String yTD_AMT) {
		YTD_AMT = yTD_AMT;
	}
	
	public String getHIER20_CODE() {
		return HIER20_CODE;
	}
	public void setHIER20_CODE(String hIER20_CODE) {
		HIER20_CODE = hIER20_CODE;
	}
	public String getHIER20_NAME() {
		return HIER20_NAME;
	}
	public void setHIER20_NAME(String hIER20_NAME) {
		HIER20_NAME = hIER20_NAME;
	}

	public String getPARENT_ROW_WID() {
		return PARENT_ROW_WID;
	}
	public void setPARENT_ROW_WID(String pARENT_ROW_WID) {
		PARENT_ROW_WID = pARENT_ROW_WID;
	}

	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	public String getHIER_CAT_CODE() {
		return HIER_CAT_CODE;
	}
	public void setHIER_CAT_CODE(String hIER_CAT_CODE) {
		HIER_CAT_CODE = hIER_CAT_CODE;
	}
	public String getHIERARCHY_ID() {
		return HIERARCHY_ID;
	}
	public void setHIERARCHY_ID(String hIERARCHY_ID) {
		HIERARCHY_ID = hIERARCHY_ID;
	}
	public String getGL_ACCOUNT_WID() {
		return GL_ACCOUNT_WID;
	}
	public void setGL_ACCOUNT_WID(String gL_ACCOUNT_WID) {
		GL_ACCOUNT_WID = gL_ACCOUNT_WID;
	}
	
	public String getACCOUNT_SEG1_CODE() {
		return ACCOUNT_SEG1_CODE;
	}
	public void setACCOUNT_SEG1_CODE(String aCCOUNT_SEG1_CODE) {
		ACCOUNT_SEG1_CODE = aCCOUNT_SEG1_CODE;
	}
	public String getACCOUNT_SEG2_CODE() {
		return ACCOUNT_SEG2_CODE;
	}
	public void setACCOUNT_SEG2_CODE(String aCCOUNT_SEG2_CODE) {
		ACCOUNT_SEG2_CODE = aCCOUNT_SEG2_CODE;
	}
	public String getACCOUNT_SEG3_CODE() {
		return ACCOUNT_SEG3_CODE;
	}
	public void setACCOUNT_SEG3_CODE(String aCCOUNT_SEG3_CODE) {
		ACCOUNT_SEG3_CODE = aCCOUNT_SEG3_CODE;
	}
	public String getACCOUNT_SEG4_CODE() {
		return ACCOUNT_SEG4_CODE;
	}
	public void setACCOUNT_SEG4_CODE(String aCCOUNT_SEG4_CODE) {
		ACCOUNT_SEG4_CODE = aCCOUNT_SEG4_CODE;
	}
	public String getACCOUNT_SEG5_CODE() {
		return ACCOUNT_SEG5_CODE;
	}
	public void setACCOUNT_SEG5_CODE(String aCCOUNT_SEG5_CODE) {
		ACCOUNT_SEG5_CODE = aCCOUNT_SEG5_CODE;
	}
	public String getACCOUNT_SEG6_CODE() {
		return ACCOUNT_SEG6_CODE;
	}
	public void setACCOUNT_SEG6_CODE(String aCCOUNT_SEG6_CODE) {
		ACCOUNT_SEG6_CODE = aCCOUNT_SEG6_CODE;
	}
	public String getACCOUNT_SEG7_CODE() {
		return ACCOUNT_SEG7_CODE;
	}
	public void setACCOUNT_SEG7_CODE(String aCCOUNT_SEG7_CODE) {
		ACCOUNT_SEG7_CODE = aCCOUNT_SEG7_CODE;
	}
	
//	public double getLEDGER_WID() {
//		// TODO Auto-generated method stub
//		return LEDGER_WID;
//	}
//	
	private Vector measureColValues=new Vector<>();
	
	private Vector defaultColumnvalues=new Vector<>();
	
//	rollupData roll=new rollupData(ROW_WID,HIER_CAT_CODE, HIERARCHY_ID, GL_ACCOUNT_WID, DOC_AMT,PERIOD_WID,SORT_ORDER,
//			 ACCOUNT_SEG1_CODE, ACCOUNT_SEG2_CODE, ACCOUNT_SEG3_CODE, ACCOUNT_SEG4_CODE, ACCOUNT_SEG5_CODE,
//			 ACCOUNT_SEG6_CODE,ACCOUNT_SEG7_CODE, HIER20_CODE,HIER20_NAME,ACCT_PERIOD_END_DT_WID,
//				PARENT_ROW_WID,MTD_AMT,nodeType, QTD_AMT, YTD_AMT, PARENT_CODE){
//		
//		
//	}
	
public Vector getDefaultColumnvalues() {
		return defaultColumnvalues;
	}
	public void setDefaultColumnvalues(Vector defaultColumnvalues) {
		this.defaultColumnvalues = defaultColumnvalues;
	}
public Vector getMeasureColValues() {
		return measureColValues;
	}
	public void setMeasureColValues(Vector measureColValues) {
		this.measureColValues = measureColValues;
	}
		//	public rollupData(int ROW_WID,String HIER_CAT_CODE, String HIERARCHY_ID, String GL_ACCOUNT_WID, double DOC_AMT,double LEDGER_WID, long PERIOD_WID,int SORT_ORDER,
//			 String ACCOUNT_SEG1_CODE, String ACCOUNT_SEG2_CODE, String ACCOUNT_SEG3_CODE, String ACCOUNT_SEG4_CODE, String ACCOUNT_SEG5_CODE,
//			 String ACCOUNT_SEG6_CODE,String ACCOUNT_SEG7_CODE,	String HIER20_CODE,String HIER20_NAME,long ACCT_PERIOD_END_DT_WID,
//	String PARENT_ROW_WID,double MTD_AMT,String nodeType,String QTD_AMT,String YTD_AMT,int PARENT_CODE){
		public rollupData(int ROW_WID,String HIER_CAT_CODE, String HIERARCHY_ID, String GL_ACCOUNT_WID,Vector defaultColvalues, Vector measureColumnvalues,double PERIOD_WID,int SORT_ORDER,
				 String ACCOUNT_SEG1_CODE, String ACCOUNT_SEG2_CODE, String ACCOUNT_SEG3_CODE, String ACCOUNT_SEG4_CODE, String ACCOUNT_SEG5_CODE,
				 String ACCOUNT_SEG6_CODE,String ACCOUNT_SEG7_CODE,	String HIER20_CODE,String HIER20_NAME,double ACCT_PERIOD_END_DT_WID,
		String PARENT_ROW_WID,String nodeType,String QTD_AMT,String YTD_AMT,int PARENT_CODE){
		
		this.ROW_WID=ROW_WID;
		this.HIER_CAT_CODE=HIER_CAT_CODE;
		this.HIERARCHY_ID=HIERARCHY_ID;
		this.GL_ACCOUNT_WID=GL_ACCOUNT_WID;
//		this.DOC_AMT=DOC_AMT;//code Comment Gokul 06MAR2014
		this.measureColValues=measureColumnvalues;
		this.defaultColumnvalues=defaultColvalues;
		this.PERIOD_WID=PERIOD_WID;
//		this.PERIOD_WID=PERIOD_WID;
//		this.LEDGER_WID=LEDGER_WID; //23FEB14	//code Comment Gokul 06MAR2014	
		this.SORT_ORDER=SORT_ORDER;
		this.ACCOUNT_SEG1_CODE=ACCOUNT_SEG1_CODE;
		this.ACCOUNT_SEG2_CODE=ACCOUNT_SEG2_CODE;
		this.ACCOUNT_SEG3_CODE=ACCOUNT_SEG3_CODE;
		this.ACCOUNT_SEG4_CODE=ACCOUNT_SEG4_CODE;
		this.ACCOUNT_SEG5_CODE=ACCOUNT_SEG5_CODE;		
		this.ACCOUNT_SEG6_CODE=ACCOUNT_SEG6_CODE;
		this.ACCOUNT_SEG7_CODE=ACCOUNT_SEG7_CODE;
		this.HIER20_CODE=HIER20_CODE;
		this.HIER20_NAME=HIER20_NAME;
		this.ACCT_PERIOD_END_DT_WID=ACCT_PERIOD_END_DT_WID;		
		this.PARENT_ROW_WID=PARENT_ROW_WID;
//		this.MTD_AMT=MTD_AMT;//code Comment Gokul 06MAR2014
		this.nodeType=nodeType;
		this.QTD_AMT=QTD_AMT;
		this.YTD_AMT=YTD_AMT;
		this.PARENT_CODE=PARENT_CODE;
		
		
	}
	
	 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

}
