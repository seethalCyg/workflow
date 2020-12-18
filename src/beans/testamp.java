package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testamp {

	/**
	 * @param args
	 */
//	'GL_ACCOUNT_WID' NUMBER(10,0), 	'BUDGT_ORG_WID' NUMBER(10,0),'CUSTOMER_WID' NUMBER(10,0), 'CUSTOMER_FIN_PROFL_WID' NUMBER(10,0), 'TERRITORY_WID' NUMBER(10,0), 
//	'SALES_GROUP_ORG_WID' NUMBER(10,0), 'CUSTOMER_CONTACT_WID' NUMBER(10,0), 'CUSTOMER_SOLD_TO_LOC_WID' NUMBER(10,0), 'CUSTOMER_SHIP_TO_LOC_WID' NUMBER(10,0), 
//	'CUSTOMER_BILL_TO_LOC_WID' NUMBER(10,0), 'CUSTOMER_PAYER_LOC_WID' NUMBER(10,0), 'SUPPLIER_WID' NUMBER(10,0), 'SUPPLIER_ACCOUNT_WID' NUMBER(10,0), 
//	'SALES_REP_WID' NUMBER(10,0), 'SERVICE_REP_WID' NUMBER(10,0), 'ACCOUNT_REP_WID' NUMBER(10,0), 'PURCH_REP_WID' NUMBER(10,0), 'PRODUCT_WID' NUMBER(10,0), 
//	'SALES_PRODUCT_WID' NUMBER(10,0), 'INVENTORY_PRODUCT_WID' NUMBER(10,0), 'SUPPLIER_PRODUCT_WID' NUMBER(10,0), 'COMPANY_LOC_WID' NUMBER(10,0), 'PLANT_LOC_WID' NUMBER(10,0), 
//	'SALES_OFC_LOC_WID' NUMBER(10,0), 'LEDGER_WID' NUMBER(10,0), 'COMPANY_ORG_WID' NUMBER(10,0), 'BUSN_AREA_ORG_WID' NUMBER(10,0), 'CTRL_AREA_ORG_WID' NUMBER(10,0), 
//	'FIN_AREA_ORG_WID' NUMBER(10,0), 'SALES_ORG_WID' NUMBER(10,0), 'PURCH_ORG_WID' NUMBER(10,0), 'ISSUE_ORG_WID' NUMBER(10,0), 'DOC_TYPE_WID' NUMBER(10,0), 'CLRNG_DOC_TYPE_WID' NUMBER(10,0), 
//	'REF_DOC_TYPE_WID' NUMBER(10,0), 'POSTING_TYPE_WID' NUMBER(10,0), 'CLR_POST_TYPE_WID' NUMBER(10,0), 'COST_CENTER_WID' NUMBER(10,0), 'PROFIT_CENTER_WID' NUMBER(10,0), 
//	'DOC_STATUS_WID' NUMBER(10,0), 'BANK_WID' NUMBER(10,0), 'TAX_WID' NUMBER(10,0), 'PAY_TERMS_WID' NUMBER(10,0), 'PAYMENT_METHOD_WID' NUMBER(10,0)
//	'TRANSACTION_DT_WID' NUMBER(15,0), 'TRANSACTION_TM_WID' NUMBER(10,0), 'POSTED_ON_DT_WID' NUMBER(15,0), 'POSTED_ON_TM_WID' NUMBER(10,0), 'ACCT_PERIOD_END_DT_WID' NUMBER(15,0), 
//	'CONVERSION_DT_WID' NUMBER(10,0), 'ORDERED_ON_DT_WID' NUMBER(10,0), 'INVOICED_ON_DT_WID' NUMBER(10,0), 'PURCH_ORDER_DT_WID' NUMBER(10,0), 'SUPPLIER_ORDER_DT_WID' NUMBER(10,0), 
//	'INVOICE_RECEIPT_DT_WID' NUMBER(10,0), 'CLEARED_ON_DT_WID' NUMBER(10,0), 'CLEARING_DOC_DT_WID' NUMBER(10,0), 'BASELINE_DT_WID' NUMBER(10,0), 'PLANNING_DT_WID' NUMBER(10,0), 
//	'PAYMENT_DUE_DT_WID' NUMBER(10,0), 'TREASURY_SYMBOL_WID' NUMBER(10,0), 'MCAL_CAL_WID' NUMBER(10,0), 'OTHER_DOC_AMT' NUMBER(22,7), 'OTHER_LOC_AMT' NUMBER(22,7), 
//	'XACT_QTY' NUMBER(22,7), 'UOM_CODE' VARCHAR2(50 CHAR), 'DB_CR_IND' VARCHAR2(30 CHAR), 'ACCT_DOC_NUM' VARCHAR2(30 CHAR), 'ACCT_DOC_ITEM' NUMBER(15,0), 'ACCT_DOC_SUB_ITEM' NUMBER(15,0), 
//	'CLEARING_DOC_NUM' VARCHAR2(30 CHAR), 'CLEARING_DOC_ITEM' NUMBER(15,0), 'SALES_ORDER_NUM' VARCHAR2(30 CHAR), 'SALES_ORDER_ITEM' NUMBER(15,0), 'SALES_SCH_LINE' NUMBER(15,0), 
//	'SALES_INVOICE_NUM' VARCHAR2(30 CHAR), 'SALES_INVOICE_ITEM' NUMBER(15,0), 'PURCH_ORDER_NUM' VARCHAR2(30 CHAR), 'PURCH_ORDER_ITEM' NUMBER(15,0), 'PURCH_INVOICE_NUM' VARCHAR2(30 CHAR), 
//	'PURCH_INVOICE_ITEM' NUMBER(15,0), 'CUST_PUR_ORD_NUM' VARCHAR2(30 CHAR), 'CUST_PUR_ORD_ITEM' NUMBER(15,0), 'SPLR_ORDER_NUM' VARCHAR2(30 CHAR), 'SPLR_ORDER_ITEM' NUMBER(15,0), 
//	'REF_DOC_NUM' VARCHAR2(30 CHAR), 'REF_DOC_ITEM' NUMBER(15,0), 'DOC_HEADER_TEXT' VARCHAR2(255 CHAR), 'LINE_ITEM_TEXT' VARCHAR2(255 CHAR), 'ALLOCATION_NUM' VARCHAR2(30 CHAR), 
//	'FED_BALANCE_ID' VARCHAR2(320 CHAR), 'BALANCE_ID' VARCHAR2(320 CHAR), 'DOC_CURR_CODE' VARCHAR2(30 CHAR), 'LOC_CURR_CODE' VARCHAR2(30 CHAR), 'LOC_EXCHANGE_RATE' NUMBER(22,7), 
//	'GLOBAL1_EXCHANGE_RATE' NUMBER(22,7), 'GLOBAL2_EXCHANGE_RATE' NUMBER(22,7), 'GLOBAL3_EXCHANGE_RATE' NUMBER(22,7), 'CREATED_BY_WID' NUMBER(10,0), 'CHANGED_BY_WID' NUMBER(10,0), 
//	'CREATED_ON_DT' DATE, 'CHANGED_ON_DT' DATE, 'AUX1_CHANGED_ON_DT' DATE, 'AUX2_CHANGED_ON_DT' DATE, 'AUX3_CHANGED_ON_DT' DATE, 'AUX4_CHANGED_ON_DT' DATE, 
//	'DELETE_FLG' CHAR(1 CHAR), 'W_INSERT_DT' DATE, 'W_UPDATE_DT' DATE, 'DATASOURCE_NUM_ID' NUMBER(10,0) NOT NULL ENABLE, 'ETL_PROC_WID' NUMBER(10,0) NOT NULL ENABLE, 
//	'INTEGRATION_ID' VARCHAR2(80 CHAR) NOT NULL ENABLE, 'TENANT_ID' VARCHAR2(80 CHAR), 'X_CUSTOM' VARCHAR2(10 CHAR), 'GL_RECONCILED_ON_DT' DATE, 
//	'GL_RECONCILED_ON_PROC_WID' NUMBER(10,0), 'X_JE_HEADER_DESCRIPTION' VARCHAR2(240 CHAR), 'X_JE_BATCH_DESCRIPTION' VARCHAR2(240 CHAR), 'X_JE_CATEGORY' VARCHAR2(25 CHAR), 
//	'X_GL_JE_STATUS' VARCHAR2(10 CHAR), 'X_LEASE_WID' NUMBER(10,0), 'X_CUSTOMER_WID' NUMBER(10,0), 'X_REVERSED_JE_HEADER_ID' NUMBER(15,0), 'X_JE_LINE_DESCRIPTION' VARCHAR2(240 CHAR), 
//	'X_REFERENCE_1' VARCHAR2(240 CHAR), 'X_USER_JE_CATEGORY_NAME' VARCHAR2(25 CHAR), 'X_USER_JE_SOURCE_NAME' VARCHAR2(25 CHAR), 'X_TOWER_WID' NUMBER(15,0)
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		String url = "jdbc:oracle:thin:@" + "66.240.205.28" + ":" + "1522" + ":" + "orcl";

		System.out.println("Preparing to connect to Database. URL:  " + url);
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection srcConn = DriverManager.getConnection(url, "hierarchy", "hierarchy");
		String insertSQL = "insert into W_GL_OTHER_F (GL_ACCOUNT_WID,BUDGT_ORG_WID,CUSTOMER_WID, CUSTOMER_FIN_PROFL_WID, TERRITORY_WID," +
				"SALES_GROUP_ORG_WID,CUSTOMER_CONTACT_WID, CUSTOMER_SOLD_TO_LOC_WID, CUSTOMER_SHIP_TO_LOC_WID,CUSTOMER_BILL_TO_LOC_WID" +
				", CUSTOMER_PAYER_LOC_WID, SUPPLIER_WID, SUPPLIER_ACCOUNT_WID,SALES_REP_WID, SERVICE_REP_WID, ACCOUNT_REP_WID, PURCH_REP_WID" +
				", PRODUCT_WID,SALES_PRODUCT_WID, INVENTORY_PRODUCT_WID, SUPPLIER_PRODUCT_WID, COMPANY_LOC_WID, PLANT_LOC_WID,SALES_OFC_LOC_WID" +
				", LEDGER_WID, COMPANY_ORG_WID, BUSN_AREA_ORG_WID, CTRL_AREA_ORG_WID,FIN_AREA_ORG_WID, SALES_ORG_WID, PURCH_ORG_WID" +
				", ISSUE_ORG_WID, DOC_TYPE_WID, CLRNG_DOC_TYPE_WID,REF_DOC_TYPE_WID, POSTING_TYPE_WID, CLR_POST_TYPE_WID, COST_CENTER_WID" +
				", PROFIT_CENTER_WID,DOC_STATUS_WID, BANK_WID, TAX_WID, PAY_TERMS_WID,PAYMENT_METHOD_WID, DATASOURCE_NUM_ID, ETL_PROC_WID,INTEGRATION_ID,TRANSACTION_DT_WID , " +
				"TRANSACTION_TM_WID , POSTED_ON_DT_WID , POSTED_ON_TM_WID , ACCT_PERIOD_END_DT_WID , 	CONVERSION_DT_WID , ORDERED_ON_DT_WID ," +
				" INVOICED_ON_DT_WID , PURCH_ORDER_DT_WID , SUPPLIER_ORDER_DT_WID , INVOICE_RECEIPT_DT_WID , CLEARED_ON_DT_WID , CLEARING_DOC_DT_WID , " +
				"BASELINE_DT_WID , PLANNING_DT_WID , PAYMENT_DUE_DT_WID , TREASURY_SYMBOL_WID , MCAL_CAL_WID , OTHER_DOC_AMT , OTHER_LOC_AMT , XACT_QTY , " +
				"UOM_CODE , DB_CR_IND , ACCT_DOC_NUM , ACCT_DOC_ITEM , ACCT_DOC_SUB_ITEM ,CLEARING_DOC_NUM , CLEARING_DOC_ITEM , SALES_ORDER_NUM , " +
				"SALES_ORDER_ITEM , SALES_SCH_LINE , 	SALES_INVOICE_NUM , SALES_INVOICE_ITEM , PURCH_ORDER_NUM , PURCH_ORDER_ITEM , PURCH_INVOICE_NUM ," +
				"PURCH_INVOICE_ITEM , CUST_PUR_ORD_NUM , CUST_PUR_ORD_ITEM , SPLR_ORDER_NUM , SPLR_ORDER_ITEM , REF_DOC_NUM , REF_DOC_ITEM , DOC_HEADER_TEXT , " +
				"LINE_ITEM_TEXT , ALLOCATION_NUM , FED_BALANCE_ID , BALANCE_ID , DOC_CURR_CODE , LOC_CURR_CODE , LOC_EXCHANGE_RATE , " +
				"GLOBAL1_EXCHANGE_RATE , GLOBAL2_EXCHANGE_RATE , GLOBAL3_EXCHANGE_RATE , CREATED_BY_WID , CHANGED_BY_WID , CREATED_ON_DT , CHANGED_ON_DT " +
				", AUX1_CHANGED_ON_DT , AUX2_CHANGED_ON_DT , AUX3_CHANGED_ON_DT , AUX4_CHANGED_ON_DT , DELETE_FLG , W_INSERT_DT , W_UPDATE_DT ," +
				" TENANT_ID , X_CUSTOM , GL_RECONCILED_ON_DT , GL_RECONCILED_ON_PROC_WID , X_JE_HEADER_DESCRIPTION , " +
				"X_JE_BATCH_DESCRIPTION , X_JE_CATEGORY , X_GL_JE_STATUS , X_LEASE_WID , X_CUSTOMER_WID , X_REVERSED_JE_HEADER_ID , X_JE_LINE_DESCRIPTION ," +
				"X_REFERENCE_1 , X_USER_JE_CATEGORY_NAME , X_USER_JE_SOURCE_NAME , X_TOWER_WID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
				"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		//69,74,76,79,81,83,85,87,92,93,94,95,113,114,117,118,120,124,125
		//106,107,108,109,
		//110,
		int ledgerWid = 1;
		int GlAccountWid = 100000;
		int doc_type = 91,cost_center_wid=89,profit_center_wid=820,dco_status_wid=295;
		int ETL_PROC_WID = 22420900;
		int DATASOURCE_NUM_ID = 4;
		PreparedStatement pre = srcConn.prepareStatement(insertSQL);
		int inte = 7000000;
		int id=1000;
		int posted_year = 2000;
		int posted_mon = 1;
		int Posted_on_tm_wid = 10000;
		int other_doc_amt = 100000;
		int Acct_doc_item =1000; 
		int created_by_wid = 7000;
//		Date dt = new Date();
//		SimpleDateFormat f = new SimpleDateFormat("DD-MMM-YYYY");
//		String date = f.format(new Date());
		//68,73,75,78--sal_in-num,80-pur_or_num,82PURCH_INVOICE_NUM,84CUST_PUR_ORD_NUM,86,91LINE_ITEM_TEXT,92,93,94,112TENANT_ID,113,116,117,119,123,124

		//105,106,107,108,
		//109,
//		Posted_on_dt_wid-50,Transaction_DT_WID-48 - 1002+2010+02+28000
//		Posted_on_tm_wid-51,Transaction_time_WID-49 - 12000
//		Acct_period_end_dt_wid-52 - 1002+year+mon+30000
//		Mcal_cal_wid-65 = 1002(always)
//		other_doc_amt-66,other_loc_amt-67 = 54343/6
//		DB_CR_IND-70=credit (or) debit
//		Acct_doc_num-71,doc_header_text-91 = 28420 Payables 11320365: A 106 (or) Reverses "20-JAN-10 US --> Con
//		Acct_doc_item-72 - 1000
//		Ref_doc_Num-89,X_je_category-119,x_user_je_category_name-126,x_user_je_source_name-127 = payables (or) consolidation (or) adujestment (or) SpreadSheet
//		doc_curr_code-96Loc_curr_code-97 = usd
//		loc_Exchange_rate-98,Gloable1_ex-99,Gloable2_ex-100,Global3_ex-101=1
//		created_by_wid-102,102 = 7000
//		created_on_dt-104,changed_on_dt-105,w_insert_dt-111,w_update_dt-112,Gl_Reconciled_on_dt-115 = date
//		ELT and gl_reconciled-116 are same
		
//		System.out.println("vvv"+pre);
//		ResultSetMetaData rsmd = rs.getMetaData();
//		System.out.println("vvv"+rsmd);
		for(int i=0;i<300;i++){
			
			for(int j=1;j<=128;j++){
//				System.out.println("vvv"+j);
				if(j==1){
//					System.out.println("dsf"+GlAccountWid);
					pre.setInt(j, GlAccountWid);
					GlAccountWid++;
				}else if(j==25){
					if(ledgerWid<=20){
						pre.setInt(j, ledgerWid);
						ledgerWid++;
					}else{
						ledgerWid=1;
					}
				}else if(j==33){
					pre.setInt(j, doc_type);
				}else if(j==38){
					pre.setInt(j, cost_center_wid);
				}else if(j==39){
					pre.setInt(j, profit_center_wid);
				}else if(j==40){
					pre.setInt(j, dco_status_wid);
				}else if(j==46 || j==116){
					pre.setInt(j, ETL_PROC_WID);
				}else if(j==45){
					pre.setInt(j, DATASOURCE_NUM_ID);
					if(i%10==0){
						DATASOURCE_NUM_ID++;
					}
				}else if(j==47){
					String x = String.valueOf(inte++)+"~"+String.valueOf(id++);
					pre.setString(j, x);

				}else if(j==48 || j==50){
					String x = "";
					if(posted_year<=2014){
						if(posted_mon<=12){
							
						}else{
							posted_year++;
							posted_mon =1;
						}
					}else{
						posted_year = 2000;
					}
					if(posted_mon++<10)
						x = "1002"+String.valueOf(posted_year)+"0"+String.valueOf(posted_mon)+"28000";
					else
						x = "1002"+String.valueOf(posted_year)+String.valueOf(posted_mon)+"28000";
					System.out.println(x);
					pre.setDouble(j, Double.valueOf(x));
				}else if(j==49 || j==51){
					pre.setInt(j, Posted_on_tm_wid++);
				}else if(j==52){
					String x = "";
					if(posted_year<=2014){
						if(posted_mon<=12){
							
						}else{
							posted_year++;
							posted_mon =1;
						}
					}else{
						posted_year = 2000;
					}
					if(posted_mon++<10)
						x = "1002"+String.valueOf(posted_year)+"0"+String.valueOf(posted_mon)+"30000";
					else
						x = "1002"+String.valueOf(posted_year)+String.valueOf(posted_mon)+"30000";
					pre.setDouble(j, Double.valueOf(x));

				}else if(j==65){
					pre.setInt(j, 1002);
				}else if(j==66 || j==67){
					pre.setInt(j, (other_doc_amt++/6));
				}else if(j==70){
					if(i%2==0){
						pre.setString(j, "Credit");
					}else{
						pre.setString(j, "Debit");
					}
				}else if(j==71 || j==91){
					if(i%2==0){
						pre.setString(j, "28420Payables11320365:A106");
					}else{
						pre.setString(j, "Reverses20-JAN-10USCon");
					}
				}else if(j==72){
					pre.setInt(j, Acct_doc_item++);
				}else if(j==89 || j==119 || j==126 || j==127){
					if(i%2==0){
						pre.setString(j, "payables");
					}else if(i%3==0){
						pre.setString(j, "consolidation");
					}else if(i%4==0){
						pre.setString(j, "adujestment");
					}else if(i%5==0){
						pre.setString(j, "SpreadSheet");
					}else{
						pre.setString(j, "payables");
					}
				}else if(j==96 || j==97){
					pre.setString(j, "USD");
				}else if(j==98 || j==99 || j==100 || j==101){
					pre.setInt(j, 1);
				}else if(j==102 || j==103){

					pre.setInt(j, created_by_wid++);
				}else if(j==104 || j==105 || j==111 || j==112 || j==115){
					pre.setDate(j, null);
					
				}else if(j==69 || j==74 || j==76 || j==79 || j==81 || j==83 || j==85 || j==87 || j==92 || j==93 || j==94 || j==95 || j==113 || j==114 || j==117 || j==118
						|| j==120 || j==124 || j==125){
					pre.setString(j, null);
				}else if(j==109 || j==106 || j==107 || j==108){
					pre.setDate(j, null);
				}else if(j==110){
					pre.setString(j, null);
				}else{
					pre.setInt(j, 0);
				}
				
			}
			pre.addBatch();
			
			
		}
		pre.executeBatch();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
