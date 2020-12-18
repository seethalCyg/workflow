package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class CopyOftestamp {

	/**
	 * @param args
	 */
//	"ROW_WID" NUMBER(10,0) NOT NULL ENABLE, "GL_ACCOUNT_NUM" VARCHAR2(30 CHAR), "GL_ACCOUNT_NAME" VARCHAR2(80 CHAR),"GL_ACCOUNT_DESC" VARCHAR2(255 CHAR), 
//	"GL_ACCOUNT_TEXT" VARCHAR2(255 CHAR), "GL_ACCOUNT_CAT_CODE" VARCHAR2(50 CHAR), "GL_ACCOUNT_CAT_NAME" VARCHAR2(80 CHAR), "FIN_STMT_ITEM_CODE" VARCHAR2(50 CHAR), 
//	"FIN_STMT_ITEM_NAME" VARCHAR2(80 CHAR), "W_FIN_SUB_CODE" VARCHAR2(50 CHAR), "W_FIN_SUB_NAME" VARCHAR2(80 CHAR), "ALT_ACCOUNT_NUM" VARCHAR2(30 CHAR), 
//	"CHART_OF_ACCOUNTS" VARCHAR2(30 CHAR), "PROFIT_CENTER_NUM" VARCHAR2(50 CHAR), "PROFIT_CENTER_DESC" VARCHAR2(255 CHAR), "PROFIT_CENTER_ATTRIB" VARCHAR2(50 CHAR), 
//	"COST_CENTER_NUM" VARCHAR2(50 CHAR), "COST_CENTER_DESC" VARCHAR2(255 CHAR),"COST_CENTER_ATTRIB" VARCHAR2(50 CHAR), "COMPANY_CODE" VARCHAR2(50 CHAR), 
//	"COMPANY_NAME" VARCHAR2(80 CHAR), "ACCOUNT_CLERK_NUM" VARCHAR2(50 CHAR), "ACCOUNT_CLERK_NAME" VARCHAR2(80 CHAR), "ACCOUNT_GROUP_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_GROUP_NAME" VARCHAR2(80 CHAR), "PLAN_GROUP_CODE" VARCHAR2(50 CHAR), "PLAN_GROUP_NAME" VARCHAR2(80 CHAR), "PLAN_LEVEL_NUM" VARCHAR2(50 CHAR), 
//	"PLAN_LEVEL_NAME" VARCHAR2(80 CHAR), "GROUP_ACCOUNT_NUM" VARCHAR2(50 CHAR), "GROUP_ACCOUNT_NAME" VARCHAR2(80 CHAR), "HOUSE_BANK_CODE" VARCHAR2(50 CHAR), 
//	"HOUSE_BANK_NAME" VARCHAR2(80 CHAR), "BANK_ACCOUNT_NUM" VARCHAR2(30 CHAR), "BANK_ACCOUNT_DETAIL" VARCHAR2(30 CHAR), "RECON_TYPE_CODE" VARCHAR2(50 CHAR), 
//	"RECON_TYPE_NAME" VARCHAR2(80 CHAR), "TAX_CAT_CODE" VARCHAR2(50 CHAR), "TAX_CAT_NAME" VARCHAR2(80 CHAR), "CURRENCY_CODE" VARCHAR2(50 CHAR), "CURRENCY_NAME" VARCHAR2(80 CHAR), 
//	"SORTING_SEQ_NUM" VARCHAR2(30 CHAR), "SEARCH_KEY_WORD" VARCHAR2(30 CHAR), "ACTIVE_FLG" CHAR(1 CHAR), "HIER1_WID" NUMBER(10,0), "HIER2_WID" NUMBER(10,0), 
//	"HIER3_WID" NUMBER(10,0), "HIER4_WID" NUMBER(10,0), "HIER5_WID" NUMBER(10,0), "HIER6_WID" NUMBER(10,0), "ACCOUNT_HIER1_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_HIER1_NAME" VARCHAR2(80 CHAR), "ACCOUNT_HIER2_CODE" VARCHAR2(50 CHAR), "ACCOUNT_HIER2_NAME" VARCHAR2(80 CHAR), "ACCOUNT_HIER3_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_HIER3_NAME" VARCHAR2(80 CHAR), "ACCOUNT_HIER4_CODE" VARCHAR2(50 CHAR), "ACCOUNT_HIER4_NAME" VARCHAR2(80 CHAR), "ACCOUNT_HIER5_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_HIER5_NAME" VARCHAR2(80 CHAR), "ACCOUNT_HIER6_CODE" VARCHAR2(50 CHAR), "ACCOUNT_HIER6_NAME" VARCHAR2(80 CHAR), "ACCOUNT_HIER7_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_HIER7_NAME" VARCHAR2(80 CHAR), "ACCOUNT_HIER8_CODE" VARCHAR2(50 CHAR), "ACCOUNT_HIER8_NAME" VARCHAR2(80 CHAR), "ACCOUNT_HIER9_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_HIER9_NAME" VARCHAR2(80 CHAR), "ACCOUNT_HIER10_CODE" VARCHAR2(50 CHAR), "ACCOUNT_HIER10_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG1_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG1_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG1_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG2_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG2_NAME" VARCHAR2(80 CHAR), 
//	"ACCOUNT_SEG2_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG3_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG3_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG3_ATTRIB" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG4_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG4_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG4_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG5_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG5_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG5_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG6_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG6_NAME" VARCHAR2(80 CHAR), 
//	"ACCOUNT_SEG6_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG7_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG7_NAME" VARCHAR2(80 CHAR),"ACCOUNT_SEG7_ATTRIB" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG8_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG8_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG8_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG9_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG9_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG9_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG10_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG10_NAME" VARCHAR2(80 CHAR), 
//	"ACCOUNT_SEG10_ATTRIB" VARCHAR2(50 CHAR),"ACCOUNT_SEG11_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG11_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG11_ATTRIB" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG12_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG12_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG12_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG13_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG13_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG13_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG14_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG14_NAME" VARCHAR2(80 CHAR), 
//	"ACCOUNT_SEG14_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG15_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG15_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG15_ATTRIB" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG16_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG16_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG16_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG17_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG17_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG17_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG18_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG18_NAME" VARCHAR2(80 CHAR), 
//	"ACCOUNT_SEG18_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG19_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG19_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG19_ATTRIB" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG20_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG20_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG20_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG21_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG21_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG21_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG22_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG22_NAME" VARCHAR2(80 CHAR), 
//	"ACCOUNT_SEG22_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG23_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG23_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG23_ATTRIB" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG24_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG24_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG24_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG25_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG25_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG25_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG26_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG26_NAME" VARCHAR2(80 CHAR),
//	"ACCOUNT_SEG26_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG27_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG27_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG27_ATTRIB" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG28_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG28_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG28_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG29_CODE" VARCHAR2(50 CHAR), 
//	"ACCOUNT_SEG29_NAME" VARCHAR2(80 CHAR), "ACCOUNT_SEG29_ATTRIB" VARCHAR2(50 CHAR), "ACCOUNT_SEG30_CODE" VARCHAR2(50 CHAR), "ACCOUNT_SEG30_NAME" VARCHAR2(80 CHAR), 
//	"ACCOUNT_SEG30_ATTRIB" VARCHAR2(50 CHAR), "CREATED_BY_WID" NUMBER(10,0), "CHANGED_BY_WID" NUMBER(10,0), "CREATED_ON_DT" DATE, "CHANGED_ON_DT" DATE, "AUX1_CHANGED_ON_DT" DATE, 
//	"AUX2_CHANGED_ON_DT" DATE, "AUX3_CHANGED_ON_DT" DATE, "AUX4_CHANGED_ON_DT" DATE, "SRC_EFF_FROM_DT" DATE, "SRC_EFF_TO_DT" DATE, "EFFECTIVE_FROM_DT" DATE NOT NULL ENABLE, 
//	"EFFECTIVE_TO_DT" DATE, "DELETE_FLG" CHAR(1 CHAR), "CURRENT_FLG" CHAR(1 CHAR), "W_INSERT_DT" DATE, "W_UPDATE_DT" DATE, "DATASOURCE_NUM_ID" NUMBER(10,0) NOT NULL ENABLE, 
//	"ETL_PROC_WID" NUMBER(10,0) NOT NULL ENABLE, "INTEGRATION_ID" VARCHAR2(240 CHAR) NOT NULL ENABLE, "SET_ID" VARCHAR2(30 CHAR), "TENANT_ID" VARCHAR2(80 CHAR), 
//	"X_CUSTOM" VARCHAR2(10 CHAR), "X_DIV_SEC_UNSEC" VARCHAR2(9 CHAR), "X_DIV_QRS_TRS" VARCHAR2(3 CHAR), "X_ALIAS_NAME" VARCHAR2(30 CHAR), "X_DIVISION_NAME" VARCHAR2(80 CHAR), 
//	"X_TOWER" VARCHAR2(25 CHAR), "X_BUS_SEG_PAR_CODE" VARCHAR2(10 CHAR), "X_BUS_SEG_PAR_NAME" VARCHAR2(80 CHAR), "X_ACCOUNT_PAR_CODE" VARCHAR2(10 CHAR), 
//	"X_ACCOUNT_PAR_NAME" VARCHAR2(80 CHAR), "X_SECURITY_DIVISION" VARCHAR2(50 CHAR), "X_DIV_PAR_CODE" VARCHAR2(10 CHAR), "X_DIV_PAR_NAME" VARCHAR2(80 CHAR), 
//	"X_ACCT_PAR1_CODE" VARCHAR2(150 CHAR), "X_ACCT_PAR1_NAME" VARCHAR2(80 CHAR), "X_ACCT_PAR2_CODE" VARCHAR2(150 CHAR), "X_ACCT_PAR2_NAME" VARCHAR2(80 CHAR), "X_DMV_ORDER" NUMBER(10,0)
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
//		String url = "jdbc:oracle:thin:@" + "system5" + ":" + "1522" + ":" + "orcl";
//
//		System.out.println("Preparing to connect to Database. URL:  " + url);
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		Connection srcConn = DriverManager.getConnection(url, "scott", "tiger");
			String url = "jdbc:oracle:thin:@" + "66.240.205.28" + ":" + "1522" + ":" + "orcl";

			System.out.println("Preparing to connect to Database. URL:  " + url);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection srcConn = DriverManager.getConnection(url, "hierarchy", "hierarchy");
		String insertSQL = "insert into W_GL_ACCOUNT_D (ROW_WID , GL_ACCOUNT_NUM , GL_ACCOUNT_NAME ,GL_ACCOUNT_DESC , GL_ACCOUNT_TEXT , GL_ACCOUNT_CAT_CODE ," +
				" GL_ACCOUNT_CAT_NAME , FIN_STMT_ITEM_CODE , FIN_STMT_ITEM_NAME , W_FIN_SUB_CODE , W_FIN_SUB_NAME , ALT_ACCOUNT_NUM , CHART_OF_ACCOUNTS ," +
				" PROFIT_CENTER_NUM , PROFIT_CENTER_DESC , PROFIT_CENTER_ATTRIB ,COST_CENTER_NUM , COST_CENTER_DESC ,COST_CENTER_ATTRIB , COMPANY_CODE ," +
				" COMPANY_NAME , ACCOUNT_CLERK_NUM , ACCOUNT_CLERK_NAME , ACCOUNT_GROUP_CODE , ACCOUNT_GROUP_NAME , PLAN_GROUP_CODE , PLAN_GROUP_NAME ," +
				" PLAN_LEVEL_NUM , PLAN_LEVEL_NAME , GROUP_ACCOUNT_NUM , GROUP_ACCOUNT_NAME , HOUSE_BANK_CODE , HOUSE_BANK_NAME , BANK_ACCOUNT_NUM ," +
				" BANK_ACCOUNT_DETAIL , RECON_TYPE_CODE , RECON_TYPE_NAME , TAX_CAT_CODE , TAX_CAT_NAME , CURRENCY_CODE , CURRENCY_NAME , SORTING_SEQ_NUM ," +
				" SEARCH_KEY_WORD , ACTIVE_FLG , HIER1_WID , HIER2_WID , HIER3_WID , HIER4_WID , HIER5_WID , HIER6_WID , ACCOUNT_HIER1_CODE , ACCOUNT_HIER1_NAME ," +
				" ACCOUNT_HIER2_CODE , ACCOUNT_HIER2_NAME , ACCOUNT_HIER3_CODE , ACCOUNT_HIER3_NAME , ACCOUNT_HIER4_CODE , ACCOUNT_HIER4_NAME , ACCOUNT_HIER5_CODE ," +
				" ACCOUNT_HIER5_NAME , ACCOUNT_HIER6_CODE , ACCOUNT_HIER6_NAME , ACCOUNT_HIER7_CODE , ACCOUNT_HIER7_NAME , ACCOUNT_HIER8_CODE , ACCOUNT_HIER8_NAME ," +
				" ACCOUNT_HIER9_CODE , ACCOUNT_HIER9_NAME , ACCOUNT_HIER10_CODE , ACCOUNT_HIER10_NAME , ACCOUNT_SEG1_CODE , ACCOUNT_SEG1_NAME , ACCOUNT_SEG1_ATTRIB ," +
				" ACCOUNT_SEG2_CODE , ACCOUNT_SEG2_NAME , ACCOUNT_SEG2_ATTRIB , ACCOUNT_SEG3_CODE , ACCOUNT_SEG3_NAME , ACCOUNT_SEG3_ATTRIB , ACCOUNT_SEG4_CODE ," +
				" ACCOUNT_SEG4_NAME , ACCOUNT_SEG4_ATTRIB , ACCOUNT_SEG5_CODE , ACCOUNT_SEG5_NAME , ACCOUNT_SEG5_ATTRIB , ACCOUNT_SEG6_CODE , ACCOUNT_SEG6_NAME , " +
				" ACCOUNT_SEG6_ATTRIB , ACCOUNT_SEG7_CODE , ACCOUNT_SEG7_NAME ,ACCOUNT_SEG7_ATTRIB , ACCOUNT_SEG8_CODE , ACCOUNT_SEG8_NAME , ACCOUNT_SEG8_ATTRIB ," +
				" ACCOUNT_SEG9_CODE , ACCOUNT_SEG9_NAME , ACCOUNT_SEG9_ATTRIB , ACCOUNT_SEG10_CODE , ACCOUNT_SEG10_NAME , ACCOUNT_SEG10_ATTRIB ,ACCOUNT_SEG11_CODE ," +
				" ACCOUNT_SEG11_NAME , ACCOUNT_SEG11_ATTRIB , ACCOUNT_SEG12_CODE , ACCOUNT_SEG12_NAME , ACCOUNT_SEG12_ATTRIB , ACCOUNT_SEG13_CODE , ACCOUNT_SEG13_NAME ," +
				" ACCOUNT_SEG13_ATTRIB , ACCOUNT_SEG14_CODE , ACCOUNT_SEG14_NAME , ACCOUNT_SEG14_ATTRIB , ACCOUNT_SEG15_CODE , ACCOUNT_SEG15_NAME , ACCOUNT_SEG15_ATTRIB ," +
				" ACCOUNT_SEG16_CODE , ACCOUNT_SEG16_NAME , ACCOUNT_SEG16_ATTRIB , ACCOUNT_SEG17_CODE , ACCOUNT_SEG17_NAME , ACCOUNT_SEG17_ATTRIB , ACCOUNT_SEG18_CODE ," +
				" ACCOUNT_SEG18_NAME , ACCOUNT_SEG18_ATTRIB , ACCOUNT_SEG19_CODE , ACCOUNT_SEG19_NAME , ACCOUNT_SEG19_ATTRIB , ACCOUNT_SEG20_CODE , ACCOUNT_SEG20_NAME ," +
				" ACCOUNT_SEG20_ATTRIB , ACCOUNT_SEG21_CODE , ACCOUNT_SEG21_NAME , ACCOUNT_SEG21_ATTRIB , ACCOUNT_SEG22_CODE , ACCOUNT_SEG22_NAME , ACCOUNT_SEG22_ATTRIB ," +
				" ACCOUNT_SEG23_CODE , ACCOUNT_SEG23_NAME , ACCOUNT_SEG23_ATTRIB , ACCOUNT_SEG24_CODE , ACCOUNT_SEG24_NAME , ACCOUNT_SEG24_ATTRIB , ACCOUNT_SEG25_CODE ," +
				" ACCOUNT_SEG25_NAME , ACCOUNT_SEG25_ATTRIB , ACCOUNT_SEG26_CODE , ACCOUNT_SEG26_NAME , ACCOUNT_SEG26_ATTRIB , ACCOUNT_SEG27_CODE , ACCOUNT_SEG27_NAME ," +
				" ACCOUNT_SEG27_ATTRIB , ACCOUNT_SEG28_CODE , ACCOUNT_SEG28_NAME , ACCOUNT_SEG28_ATTRIB , ACCOUNT_SEG29_CODE , ACCOUNT_SEG29_NAME , ACCOUNT_SEG29_ATTRIB ," +
				" ACCOUNT_SEG30_CODE , ACCOUNT_SEG30_NAME , ACCOUNT_SEG30_ATTRIB , CREATED_BY_WID , CHANGED_BY_WID , CREATED_ON_DT , CHANGED_ON_DT , AUX1_CHANGED_ON_DT ," +
				" AUX2_CHANGED_ON_DT , AUX3_CHANGED_ON_DT , AUX4_CHANGED_ON_DT , SRC_EFF_FROM_DT , SRC_EFF_TO_DT , EFFECTIVE_FROM_DT, EFFECTIVE_TO_DT , DELETE_FLG ," +
				" CURRENT_FLG , W_INSERT_DT , W_UPDATE_DT , DATASOURCE_NUM_ID  , ETL_PROC_WID  , INTEGRATION_ID  , SET_ID , TENANT_ID , X_CUSTOM , X_DIV_SEC_UNSEC ," +
				" X_DIV_QRS_TRS , X_ALIAS_NAME , X_DIVISION_NAME , X_TOWER , X_BUS_SEG_PAR_CODE , X_BUS_SEG_PAR_NAME , X_ACCOUNT_PAR_CODE , X_ACCOUNT_PAR_NAME ," +
				" X_SECURITY_DIVISION , X_DIV_PAR_CODE , X_DIV_PAR_NAME , X_ACCT_PAR1_CODE , X_ACCT_PAR1_NAME , X_ACCT_PAR2_CODE , X_ACCT_PAR2_NAME , X_DMV_ORDER) values " +
				"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
				"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
				"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pre = srcConn.prepareStatement(insertSQL);
		
//		Row_Wid 1 = number running
//				Gl_account_Num 2, GL_account_Name 3, ACCOUNT_SEG2_CODE 73 = var(number incre 10)
//				GL_Account_desc 4, ACCOUNT_SEG2_NAME 74 = Sga Salaries, SGA Bonus, SGA Transportation, SGA Lodging, SGA Recruiting, SGA Training
//				GL_ACCOUNT_TEXT 5 - emp
//				GL_ACCOUNT_CAT_CODE 6 - PL
//				GL_ACCOUNT_CAT_Name 7 - Profit & Loss Account
//				FIN_STMT_ITEM_CODE 8 - OTHERS
//				FIN_STMT_ITEM_NAME 9 - OTHER TRANSACTIONS
//				W_FIN_SUB_CODE 10 - ?
//				W_FIN_SUB_NAME 11 - emp
//				ALT_ACCOUNT_NUM 12 - emp
//				CHART_OF_ACCOUNTS 13 - 101
//				PROFIT_CENTER_NUM 14, ACCOUNT_SEG1_Name 70 - 2,50155
//				PROFIT_CENTER_DESC 15, ACCOUNT_SEG1_Name 71
//				 - REN - American Towers, LLC and ATC Operations LLC
//				PROFIT_CENTER_ATTRIB 16,COST_CENTER_ATTRIB 19, ACCOUNT_SEG1_ATTRIB 72, ACCOUNT_SEG2_ATTRIB 75,ACCOUNT_SEG3_ATTRIB 78, ACCOUNT_SEG4_ATTRIB
//				81,ACCOUNT_SEG5_ATTRIB 84, ACCOUNT_SEG6_ATTRIB 87, ACCOUNT_SEG7_ATTRIB 90, ACCOUNT_SEG8_ATTRIB 93 - 1003964(inc 4 col)
//				COST_CENTER_NUM 17, ACCOUNT_SEG4_CODE 79,ACCOUNT_SEG7_CODE
//				88 - cost center
//				COST_CENTER_DESC 18, ACCOUNT_SEG4_NAME 80, ACCOUNT_SEG7_NAME
//				89 - cost center DESC
//				20 upto 29 empt
//				GROUP_ACCOUNT_NUM 29 - GEN PAYROLL, OTHER MKTG EXP
//				GROUP_ACCOUNT_NAME 30 - GENERAL ADMIN AND OTHER PAYROLL, OTHER MARKETING EXPENSES
//				31 34
//				RECON_TYPE_CODE 35 - E
//				36 - 42
//				ACTIVE_FLG 43 - Y
//				HIER1_WID 44, HIER2_WID 45, HIER3_WID 46,HIER4_WID 47, HIER5_WID 48, HIER6_WID 49, ACCOUNT_SEG6_CODE 85, ACCOUNT_SEG8_CODE 91 - 0
//				50 - 69
//				ACCOUNT_SEG3_CODE 76 - 21
//				ACCOUNT_SEG3_NAME 77 - Rental
//				ACCOUNT_SEG5_CODE 82 - 0 (or) 5
//				ACCOUNT_SEG5_NAME 83 - Default (or) COR Admin / Executive
//				ACCOUNT_SEG6_NAME 86, ACCOUNT_SEG8_NAME 92, TENANT_ID 180 - Default
//				94 - 159 - emp
//				CREATED_BY_WID 160,CHANGED_BY_WID 161 = 7477
//				CREATED_ON_DT 162,CHANGED_ON_DT 163,AUX1_CHANGED_ON_DT 164,AUX2_CHANGED_ON_DT 165,AUX3_CHANGED_ON_DT 166, SRC_EFF_FROM_DT 168, SRC_EFF_TO_DT 169,EFFECTIVE_FROM_DT 170,EFFECTIVE_TO_DT 171,W_INSERT_DT 174,W_UPDATE_DT 175, = date
//				AUX4_CHANGED_ON_DT 167 - emp(date)
//				DELETE_FLG 172,CURRENT_FLG 173 - y (or) n
//				DATASOURCE_NUM_ID 176 - 4
//				ETL_PROC_WID 177 = 22296960
//				INTEGRATION_ID 178 = incr
//				179 - emp
//				X_CUSTOM 181 - emp
//				X_DIV_SEC_UNSEC 182 - Unsecured
//				X_DIV_QRS_TRS 183 - TRS (or) QRS
//				X_ALIAS_NAME 184, X_DIVISION_NAME 185 - emp
//				X_TOWER 186 - CTFO OFC (or) FO FPNA
//				X_BUS_SEG_PAR_CODE 187 - 20
//				X_BUS_SEG_PAR_NAME 188 - Rental - All
//				X_ACCOUNT_PAR_CODE 189 - DRPAY (or) TRVEL (or) TEMRC
//				X_ACCOUNT_PAR_NAME 190 - Direct (or) Travel Related (or) Other Employee Related
//				X_SECURITY_DIVISION 191,X_DIV_PAR_CODE 192  - US (or) Conso (or) RTSRV
//				X_DIV_PAR_NAME 193 - US (all domestic; ATI, SSI, etc.) (or) REIT Services
//				X_ACCT_PAR1_CODE 194 - SGAXS
//				X_ACCT_PAR1_NAME 195 - SG&A Expenses
//				X_ACCT_PAR2_CODE 196 - PAYRS
//				X_ACCT_PAR2_NAME 197 - SGA- Payroll & Related, SGA- Employee & Related
//				X_DMV_ORDER 198 - 30010 (10 incr)
//		SimpleDateFormat reFormat = new SimpleDateFormat("dd-MMM-yy");
//		 Date activityDate = reFormat.parse("24-MAR-13");
//		 java.sql.Date sqlDate = new java.sql.Date(activityDate.getTime());
		 DateFormat casedateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); //code change pandian  05Aprl13
         java.util.Date casedate = new java.util.Date();
         String strDate= casedateFormat.format(casedate);
         java.sql.Date newDataSQL= new java.sql.Date(((Date) casedate).getTime());
         
         System.out.println("---  newDataSQL  "+newDataSQL);
         
         
		int row_wid = 100100;
		int Gl_account_Num = 30000;
		String GL_Account_desc = "";
		String PROFIT_CENTER_DESC = "";
		int PROFIT_CENTER_ATTRIB = 1003964;
		int COST_CENTER_NUM = 100;
		String COST_CENTER_DESC = "";
		String GROUP_ACCOUNT_NUM = "";
		String GROUP_ACCOUNT_NAME = "";
		int ETL_PROC_WID = 22296960;
		int INTEGRATION_ID = 123456;
		for(int i=0;i<300;i++){
			for(int j=1;j<=199;j++){
				if(j==1){
					pre.setInt(j, row_wid);
					row_wid++;
				}else if(j==2 || j==3 || j==74){
					if(i%10==0){
						pre.setString(j, String.valueOf(Gl_account_Num));
						Gl_account_Num = Gl_account_Num+10;
					}else{
						pre.setString(j, String.valueOf(Gl_account_Num));
					}
				}else if(j == 4 || j==75){
					if(i%10==0){
						String val = "Sga Salaries"+String.valueOf(Gl_account_Num-10);
						pre.setString(j, String.valueOf(val));
					}else{
						String val = "Sga Salaries"+String.valueOf(Gl_account_Num);
						pre.setString(j, String.valueOf(val));
					}
					
				}else if(j==6){
					pre.setString(j, "PL");
				}else if(j==7){
					pre.setString(j, "Profit & Loss Account");
				}else if(j==8){
					pre.setString(j, "OTHERS");
				}else if(j==9){
					pre.setString(j, "OTHER TRANSACTIONS");
				}else if(j==10){
					pre.setString(j, "?");
				}else if(j==13){
					pre.setString(j, "101");
				}else if(j==14 || j==71){
					if(i%10<5){
						pre.setString(j, "2");
					}else{
						pre.setString(j, "50155");
					}
				}else if(j==15 || j==72){
					if(i%10<5){
						pre.setString(j, "REN - American Towers, LLC");
					}else{
						pre.setString(j, "ATC Operations LLC");
					}
				}else if(j==16 || j==19 || j==73 || j==76 || j==79 || j==82 || j==85 || j==88 || j==91 || j==94){
					pre.setString(j, String.valueOf(PROFIT_CENTER_ATTRIB++));
				}else if(j==17 || j==80 || j==89){
					if(i%10==0){
						String val = "+"+String.valueOf(COST_CENTER_NUM++);
						pre.setString(j, String.valueOf(val));
					}else{
						String val = "+"+String.valueOf(COST_CENTER_NUM);
						pre.setString(j, String.valueOf(val));
					}
				}else if(j==18 || j==81 || j==90){
//					if(i%10==0){
//						String val = "NAT CTFO Atlanta, GA"+String.valueOf(COST_CENTER_NUM);
//						pre.setString(j, String.valueOf(val));
//					}else{
						String val = "NAT CTFO Atlanta, GA"+String.valueOf(COST_CENTER_NUM);
						pre.setString(j, String.valueOf(val));
//					}
				}else if(j==30){
					if(i%20<10){
						pre.setString(j, "GEN PAYROLL");
					}else{
						pre.setString(j, "OTHER MKTG EXP");
					}
				}else if(j==31){
					if(i%20<10){
						pre.setString(j, "GENERAL ADMIN AND OTHER PAYROLL");
					}else{
						pre.setString(j, "OTHER MARKETING EXPENSES");
					}
				}else if(j==36){
					pre.setString(j, "E");
				}else if(j==44){
					pre.setString(j, "Y");
				}else if(j==45 || j==46 || j==47 || j==48 || j==49 || j==50 || j==86 || j==92){
					pre.setInt(j, 0);
				}else if(j==77){
					pre.setString(j, "21");
				}else if(j==78){
					pre.setString(j, "Rental");
				}else if(j==83){
					if(i%20<10)
					pre.setString(j, "0");
					else
						pre.setString(j, "5");
				}else if(j==84){
					if(i%20<10)
						pre.setString(j, "Default");
					else
						pre.setString(j, "COR Admin / Executive");
					
				}else if(j==87 || j==93 || j==181){
					pre.setString(j, "Default");
				}else if(j==161 || j==162){
					pre.setInt(j, 7477);
				}else if(j==163 || j==164 || j==165 || j==166 || j==167 || j==169 || j==170 || j==171 || j==172 || j==175 || j==176){
					
					pre.setDate(j, newDataSQL);
				}else if(j==168){
					
					pre.setDate(j, null);
				}else if(j==173 || j==174){
						pre.setString(j, "Y");					
				}else if(j==177){
					pre.setInt(j, 4);
				}else if(j==178){
					if(i%100<100){
					pre.setInt(j, ETL_PROC_WID);
					}else{
						pre.setInt(j, ETL_PROC_WID);
						ETL_PROC_WID++;
					}
				}else if(j==179){
					pre.setString(j, String.valueOf(INTEGRATION_ID++));
				}else if(j==183){
					pre.setString(j, "UnSecured");
				}else if(j==184){
					if(i%20<10)
						pre.setString(j, "TRS");
					else
						pre.setString(j, "QRS");
				}else if(j==187){
					if(i%20<10)
					pre.setString(j, "CTFO OFC");
					else
						pre.setString(j, "FO FPNA");
				}else if(j==188){
					pre.setString(j, "20");
				}else if(j==189){
					pre.setString(j, "Rental - All");
				}else if(j==190){
					if(i%30<10){
						pre.setString(j, "DRPAY");
					}else if(i%30>10 && i%30<20){
						pre.setString(j, "TRVEL");
					}else{
						pre.setString(j, "TEMRC");
					}
				}else if(j==191){
					if(i%30<10){
						pre.setString(j, "Direct");
					}else if(i%30>10 && i%30<20){
						pre.setString(j, "Travel Related");
					}else{
						pre.setString(j, "Other Employee Related");
					}
				}else if(j==192 || j == 193){
					if(i%30<10){
						pre.setString(j, "US");
					}else if(i%30>10 && i%30<20){
						pre.setString(j, "Conso");
					}else{
						pre.setString(j, "RTSRV");
					}
				}else if(j==194){
					if(i%20<10){
						pre.setString(j, "US (all domestic; ATI, SSI, etc.)");
					}else{
						pre.setString(j, "REIT Services");
					}
				}else if(j==195){
					pre.setString(j, "SGAXS");
				}else if(j==196){
					pre.setString(j, "SG&A Expenses");
				}else if(j==197){
					pre.setString(j, "PAYRS");
				}else if(j==198){
					if(i%20<10){
						pre.setString(j, "SGA- Payroll & Related");
					}else{
						pre.setString(j, "SGA- Employee & Related");
					}
					
				}else if(j==199){
					if(i%20<10){
						pre.setInt(j, Gl_account_Num-10);
					}else{
						pre.setInt(j, Gl_account_Num);
					}
					
				}else{
					pre.setString(j, "");
				}
			}
			pre.addBatch();
			pre.executeBatch();
		}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
