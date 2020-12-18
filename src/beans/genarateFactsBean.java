
package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import model.ReportTree;
import model.rollupData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import utils.Globals;
import utils.PropUtil;

import com.jniwrapper.Str;
import com.jniwrapper.win32.jexcel.Application;
import com.jniwrapper.win32.jexcel.ExcelException;
import com.jniwrapper.win32.jexcel.H;
import com.jniwrapper.win32.jexcel.Range;
import com.jniwrapper.win32.jexcel.Worksheet;
import com.jniwrapper.win32.jexcel.db;
import com.sun.faces.context.SessionMap;


@ManagedBean(name = "genarateFactsBean")
@ViewScoped

public class genarateFactsBean implements Runnable{

	private String heirID="";
	public genarateFactsBean(String heirID, String userID,String genType){
		this.heirID=heirID;
			setUserID(userID);
			setGenType(genType);
	}
	
	static String userID="";
	String genType="";
	
	

	public String getGenType() {
		return genType;
	}


	public void setGenType(String genType) {
		this.genType = genType;
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}

	

	public  void main(String[] args) throws Exception {
	
	
	
//		generateFacttables("");
		
		
//		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());
//		
//		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());
		
	}
	
	
	
	
	ArrayList<rollupData> joinQueryResAL=new ArrayList<rollupData>();
	

	 public ArrayList<rollupData> getJoinQueryResAL() {
		return joinQueryResAL;
	}


	public void setJoinQueryResAL(ArrayList<rollupData> joinQueryResAL) {
		this.joinQueryResAL = joinQueryResAL;
	}

	
	
	public    void generateFacttables(String genType)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		String dbConSrc="";
		String dbConTarget="";
		
		
		String facttableName = "";
		String srcTabPeriodColumn = "";
		String periodClmn = "";
		String periodClmnInTarTable="";
		String periodClmnValues="";
		String connectionName="";
		String tableLevelstatus="";
		String targetfacttableName = "";
		String rowsAff4DB="";
		
		
//		boolean GenMode=true;
//		String GenMode="GenerateFact";
//		String GenMode="UpdateFact";
//		String GenMode="InsertFact";
		
//		boolean GenMode=false;
		
		Connection srcConn=null;
		Connection targetConn=null;
		
		
		
//		String dimTableName="WC_FLEX_HIERARCHY_D";
		
		
		
		Hashtable srcConnTempTablesHT=new Hashtable<>();
		Hashtable tarConnTempTablesHT=new Hashtable<>();
		
		Hashtable colsHT=new Hashtable<>();
		Hashtable valuesHT=new Hashtable<>();
		
//		String heirCode="328";
		String heirCode=this.heirID;
		
		String dimCodeColumn = "";
		int tableNo=1;
		
		String statusStr="";
		Node rootnode = null;
		try{
			PropUtil prop=new PropUtil();
			String XMLdir=prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyXMLFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String configFile= prop.getProperty("HIERARCHY_CONFIG_FILE");
			String dateFormat= prop.getProperty("DATE_FORMAT");

			Document xmldoc = Globals.openXMLFile(XMLdir, hierarchyXMLFileName);
			Document configDoc=Globals.openXMLFile(XMLdir, configFile);
			rootnode = Globals.getNodeByAttrVal(xmldoc, "Fact_Tables", "ID",heirCode);
//			Node hierNode = Globals.getNodeByAttrVal(xmldoc, "Hierarchy_Level", "ID",heirCode);
			Node hierNode = Globals.getNodeByAttrVal(xmldoc, "Hierarchy_Level", "Hierarchy_ID",heirCode);
			String hierName=Globals.getAttrVal4AttrName(hierNode, "Hierarchy_Name");
			Element factRootEle = (Element)rootnode;
			
			colsHT.put(0, "W_FACT_DETAILS.HIERARCHY_ID");
			valuesHT.put(0, heirCode);
			
			colsHT.put(1, "W_FACT_DETAILS.HIER_NAME");
			valuesHT.put(1,hierName);
			String factGenType = "";
			
			factGenType = Globals.getAttrVal4AttrName(hierNode, "Code_Combination");
			Hashtable joinTypeHT4Incr = new Hashtable<>();
			
			Date startTime=new Date();
			Format formatter1 = new SimpleDateFormat(dateFormat);
			String factstartTime=formatter1.format(startTime);
			
			Format timeStampFORM = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.FF");
			String timeStmp=timeStampFORM.format(startTime);
			
			colsHT.put(2, "W_FACT_DETAILS.FACT_START_TIME");
			valuesHT.put(2,timeStmp);
			
			
			
			System.out.println("**********************************************Generation of Facts Started******=="+factstartTime+"==****************************************");
			
			System.out.println("Getting data from XML..."+genType);
			//	Getting Last Fact Generated Time from XML
			Hashtable rootNattHT=Globals.getAttributeNameandValHT(rootnode);
			
//			String GenMode=(String)rootNattHT.get("Gen_Mode");
			String GenMode=genType;
			if(GenMode==null||GenMode.equalsIgnoreCase(""))
			{
				GenMode="GenerateFact";
			}
			
			
			colsHT.put(3, "W_FACT_DETAILS.GENERATION_MODE");
			valuesHT.put(3,GenMode);
			
			String insertStatus = "";
			
			if(rootnode.getNodeType() == Node.ELEMENT_NODE){
				NodeList child = rootnode.getChildNodes();
				
				for(int r=0;r<child.getLength();r++){
					if(child.item(r).getNodeName().equals("Error_Message")){
						
						if(GenMode.equals("UpdateFact")){
							insertStatus = child.item(r).getTextContent();
						}
					}
				}
			}
		
			
			

//			String GenMode="GenerateFact";
//			String GenMode="UpdateFact";
//			String GenMode="InsertFact";
			
			Hashtable columnProperty = new Hashtable<>();
			
			NodeList dimtabNL=configDoc.getElementsByTagName("Dim_Table_Name");
			Node dimTabN=dimtabNL.item(0);
			
			String dimTableName=dimTabN.getTextContent();
			

			String lastAccestime=(String)rootNattHT.get("Last_Generated_Time");
			if(lastAccestime==null||lastAccestime.equalsIgnoreCase(""))
			{
				Date currentTime=new Date();
				Format formatter = new SimpleDateFormat(dateFormat);
				lastAccestime=formatter.format(currentTime);
			}
			
			System.out.println("Last AccessTime from XML : "+lastAccestime);
			
			//Start Code Change Gokul 03MAY2014
			Date lastAccDT=new Date(lastAccestime);
			String timeStmp1=timeStampFORM.format(lastAccDT);
		
			colsHT.put(4, "W_FACT_DETAILS.LAST_GEN_TIME");
			valuesHT.put(4,timeStmp1);
			//End Code Change Gokul 03MAY2014
			
			
			
			// code change Menaka 21FEB2014
			Hashtable statusHT=new Hashtable<>();
			statusHT.put("result", "InProgress");
			statusHT.put("errorMessage", "InProgress");
			Globals.updateFactStatus(statusHT,heirCode);
			
			
			
			//Getting all Configuration from XML
			if(rootnode != null){
				
				dbConSrc=rootnode.getAttributes().getNamedItem("Connection_Source").getNodeValue();
				dbConTarget=rootnode.getAttributes().getNamedItem("Connection_Target").getNodeValue();
				
				if(rootnode.getNodeType() == Node.ELEMENT_NODE){
					NodeList child = rootnode.getChildNodes();
					
					///////////////// PROCESS EACH SOURCE FACT TABLE  ////////////////////////////////
					for(int r=0;r<child.getLength();r++){
			
						Node tableNode=null;
						Hashtable measureColumnHT=new Hashtable<>();
						int mKey=0;
						Hashtable joinColumnHT=new Hashtable<>();
						int jKey=0;
						
						Hashtable SWjoinColumnHT=new Hashtable<>();
						int SWKey=0;
						Hashtable uniCols4CrtetempTableHT=new Hashtable<>();
						Hashtable uniColsFunc4CrtetempTableHT=new Hashtable<>();
						int uniqueKey=4;
						
						Hashtable incrementLoadHT=new Hashtable<>();
						int incLoadKey=0;
						
						Hashtable updateLoadHT=new Hashtable<>();
						int incUpdateKey=0;
						
						
						if(child.item(r).getNodeName().equals("Table")){
						
							//Start Code Change Gokul 03MAY2014
							Date currentTime=new Date();
							Format formatter = new SimpleDateFormat(dateFormat);
							String lastgendate=formatter.format(currentTime);
							//End Code Change Gokul 03MAY2014
							
						facttableName=child.item(r).getAttributes().getNamedItem("Source_Fact_TableName").getNodeValue();
						srcTabPeriodColumn=child.item(r).getAttributes().getNamedItem("Source_Period_Column").getNodeValue();
						uniCols4CrtetempTableHT.put(0,facttableName+"."+srcTabPeriodColumn );
						uniCols4CrtetempTableHT.put(1,dimTableName+"."+"HIERARCHY_ID");
						uniCols4CrtetempTableHT.put(2,dimTableName+"."+"HIER_CAT_CODE");
						uniCols4CrtetempTableHT.put(3,dimTableName+"."+"NODE_TYPE");
						
//						uniColsFunc4CrtetempTableHT.put(0,facttableName+"."+srcTabPeriodColumn );
//						uniColsFunc4CrtetempTableHT.put(1,dimTableName+"."+"HIERARCHY_ID");
//						uniColsFunc4CrtetempTableHT.put(2,dimTableName+"."+"HIER_CAT_CODE");
//						uniColsFunc4CrtetempTableHT.put(3,dimTableName+"."+"NODE_TYPE");
						
						targetfacttableName=child.item(r).getAttributes().getNamedItem("Target_Fact_TableName").getNodeValue();
						
						tableNode=child.item(r);
						 Element tableE=(Element)tableNode;	// Code Change Gokul 03MAY2014

						if(tableNode!=null)
						{
							if(tableNode.getNodeType() == Node.ELEMENT_NODE){
								NodeList tablechild = tableNode.getChildNodes();
								

								
								for(int j=0;j<tablechild.getLength();j++){
							 if(tablechild.item(j).getNodeName().equals("Measure_Columns")){  // 23FEB14

								NodeList meaNL=tablechild.item(j).getChildNodes();
								for(int h=0;h<meaNL.getLength();h++)
								{
									
									Node n=meaNL.item(h);
									
									if(n.getNodeType()==Node.ELEMENT_NODE)
									{
										Hashtable measureNodedetHT=Globals.getAttributeNameandValHT(n);
										String mapStr=n.getTextContent().toString();
										measureColumnHT.put(mKey, mapStr);
										///////
										String[] Splitter=mapStr.split("=");
										if(Splitter[1].contains(facttableName))
										{
//											String[] colNM=Splitter[1].split("\\.");
											if(!uniCols4CrtetempTableHT.contains(Splitter[1]))
											{
												uniCols4CrtetempTableHT.put(uniqueKey,Splitter[1]);
												
											}
											
											uniqueKey++;
										}
										
										
										if(!uniColsFunc4CrtetempTableHT.contains(Splitter[1])){
											if(String.valueOf(measureNodedetHT.get("Source_Column_Function"))!=null){
												if(!String.valueOf(measureNodedetHT.get("Source_Column_Function")).equals("")){
													uniColsFunc4CrtetempTableHT.put(Splitter[1], String.valueOf(measureNodedetHT.get("Source_Column_Function")));
												}
												
											}
										}
										/////////
										String colProperty=n.getAttributes().getNamedItem("Column_Property").getNodeValue();
										String columnVal=n.getTextContent();
										String[] splitter1=columnVal.split("=");	//code change Vishnu 26Apr2014
										String periodClmn1=splitter1[1];
										String periodClmnInTarTable1=periodClmn1.split("\\.")[1];
										
										columnProperty.put(periodClmnInTarTable1, colProperty);
										
										//Getting Period Column
										if(colProperty.equalsIgnoreCase("period"))
										{
											String[] splitter=columnVal.split("=");
											periodClmn=splitter[1];
											periodClmnInTarTable=splitter[0];
										}
									mKey++;
									}
									
								}
						

							}else if(tablechild.item(j).getNodeName().equals("Join_Columns")){

								NodeList meaNL=tablechild.item(j).getChildNodes();
								dimCodeColumn = Globals.getAttrVal4AttrName(tablechild.item(j), "Dim_Code_Column");
								for(int d=0;d<meaNL.getLength();d++)
								{
									Node n=meaNL.item(d);
									if(n.getNodeType()==Node.ELEMENT_NODE)
									{
										String joinStr=n.getTextContent().toString();
										joinColumnHT.put(jKey, joinStr);
									jKey++;
									
								///////
									
									String[] Splitter=joinStr.split("=");
									if(Splitter[0].contains(facttableName)||Splitter[0].contains(dimTableName))
											{
//												String[] colNM=Splitter[0].split("\\.");
												if(!uniCols4CrtetempTableHT.contains(Splitter[0]))
												{
													uniCols4CrtetempTableHT.put(uniqueKey,Splitter[0]);
//													uniColsFunc4CrtetempTableHT.put(uniqueKey, Splitter[0]);
													uniqueKey++;
												}
												
											}
									if(Splitter[1].contains(facttableName)||Splitter[1].contains(dimTableName))
									{
//										String[] colNM=Splitter[1].split("\\.");
										if(!uniCols4CrtetempTableHT.contains(Splitter[1]))
										{
											uniCols4CrtetempTableHT.put(uniqueKey,Splitter[1]);
											uniqueKey++;
										}
										
									}
									
									
									
									/////////
									}
									
								}


							}else if(tablechild.item(j).getNodeName().equals("Period_Column")){


								periodClmn=tablechild.item(j).getTextContent().toString();
								
							}
							else if(tablechild.item(j).getNodeName().equals("Period_Values")){


								periodClmnValues=tablechild.item(j).getTextContent().toString();
								
								
							}else if(tablechild.item(j).getNodeName().equals("Source_Warehouse_Joins")){
								
								NodeList srsWHJoinNL=tablechild.item(j).getChildNodes();
								
								for(int i=0;i<srsWHJoinNL.getLength();i++)
								{
									Node srsWHJoinN=srsWHJoinNL.item(i);
									if(srsWHJoinN.getNodeType()==Node.ELEMENT_NODE)
									{
									Hashtable joinNodedetHT=Globals.getAttributeNameandValHT(srsWHJoinN);
									
									///////////////
									String Source_Column=(String)joinNodedetHT.get("Source_Column");
									String Target_Column=(String)joinNodedetHT.get("Target_Column");
									
									if(Source_Column.contains(facttableName)||Source_Column.contains(dimTableName))
									{
//										String[] colNM=Source_Column.split("\\.");
										if(!uniCols4CrtetempTableHT.contains(Source_Column))
										{
											uniCols4CrtetempTableHT.put(uniqueKey,Source_Column);
											
										}
										
										uniqueKey++;
										
									}else if(Target_Column.contains(facttableName)||Target_Column.contains(dimTableName))
									{
//										String[] colNM=Target_Column.split("\\.");
										if(!uniCols4CrtetempTableHT.contains(Target_Column))
										{
											uniCols4CrtetempTableHT.put(uniqueKey,Target_Column);
											
										}
										
										uniqueKey++;
									}
//									if(!uniColsFunc4CrtetempTableHT.contains(Source_Column)){
//										if(String.valueOf(joinNodedetHT.get("Source_Column_Function"))!=null){
//											if(!String.valueOf(joinNodedetHT.get("Source_Column_Function")).equals("")){
//												uniColsFunc4CrtetempTableHT.put(Source_Column, String.valueOf(joinNodedetHT.get("Source_Column_Function")));
//											}
//											
//										}
//									}
//									if(!uniColsFunc4CrtetempTableHT.contains(Target_Column)){
//										if(String.valueOf(joinNodedetHT.get("Target_Column_Function"))!=null){
//											if(!String.valueOf(joinNodedetHT.get("Target_Column_Function")).equals("")){
//												
//												uniColsFunc4CrtetempTableHT.put(Target_Column, String.valueOf(joinNodedetHT.get("Target_Column_Function")));
//											}
//											System.out.println("uniColsFunc4CrtetempTableHT===>"+uniColsFunc4CrtetempTableHT);
//										}
//									}
										/////////////
										SWjoinColumnHT.put(SWKey,joinNodedetHT);
										SWKey++;
									}
								}
								
								
							}else if(tablechild.item(j).getNodeName().equals("Incremental_Load")){
								
								NodeList incLoadNL=tablechild.item(j).getChildNodes();
								joinTypeHT4Incr.put("Insert", Globals.getAttrVal4AttrName(tablechild.item(j), "Join_Type4Insert"));
								joinTypeHT4Incr.put("Update", Globals.getAttrVal4AttrName(tablechild.item(j), "Join_Type4Update"));
								for(int i=0;i<incLoadNL.getLength();i++)
								{
									Node incLoadN=incLoadNL.item(i);
									if(incLoadN.getNodeType()==Node.ELEMENT_NODE)
									{
										Hashtable incNodedetHT=Globals.getAttributeNameandValHT(incLoadN);
										///////////////
										String colNM=incLoadN.getTextContent();
										
										
										incNodedetHT.put("Column", colNM);
										incrementLoadHT.put(incLoadKey, incNodedetHT);
										incLoadKey++;
									}
								}
								
							}else if(tablechild.item(j).getNodeName().equals("Incremental_Update")){
								
								NodeList incLoadNL=tablechild.item(j).getChildNodes();
								
								for(int i=0;i<incLoadNL.getLength();i++)
								{
									Node incLoadN=incLoadNL.item(i);
									if(incLoadN.getNodeType()==Node.ELEMENT_NODE)
									{
//										Hashtable updateNodeDetHT=Globals.getAttributeNameandValHT(incLoadN);
										///////////////
										String colNM=incLoadN.getTextContent();
										
										
//										updateNodeDetHT.put("Column", colNM);
										updateLoadHT.put(incUpdateKey, colNM);
										incUpdateKey++;
									}
								}
								
							}
								}
							}

						}
						System.out.println("********Data from XML************");
						System.out.println("Source DB Connection Name : "+dbConSrc);
						System.out.println("Target DB Connection Name : "+dbConTarget);
						System.out.println("Source Fact Table : "+facttableName);
						System.out.println("Source Period Column : "+srcTabPeriodColumn);
						System.out.println("Target Fact Table : "+targetfacttableName);
						System.out.println("Measure Columns : "+measureColumnHT);
						System.out.println("Join Columns : "+joinColumnHT);
						System.out.println("Source and Warehouse joins : "+SWjoinColumnHT);
						System.out.println("Period Column : "+periodClmn);
						System.out.println("Period Column in Target table : "+periodClmnInTarTable);
						System.out.println("Period Column Values : "+periodClmnValues);
						System.out.println("Unique Column HT(fact-dim) : "+uniCols4CrtetempTableHT);
						System.out.println("Increamental Load Details : "+incrementLoadHT);
						System.out.println("Increamental Update Details : "+updateLoadHT);
						System.out.println("***********************************");	
						System.out.println("Unique Column Function HT(fact-dim) : "+uniColsFunc4CrtetempTableHT);
					
//						if(true){
//							return;
//						}
						
						//Checking for null Values And Skipping it
						if(dbConTarget==null||dbConTarget.equalsIgnoreCase("")||dbConTarget.equalsIgnoreCase("null"))
						{	
							tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName,"Target Connection is Empty",String.valueOf(tableNo));
							continue;
						}else if(dbConSrc==null||dbConSrc.equalsIgnoreCase("")||dbConSrc.equalsIgnoreCase("null"))
						{	
							tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName,"Source Connection is Empty",String.valueOf(tableNo));
							continue;
						}else if(facttableName==null||facttableName.equalsIgnoreCase("")||facttableName.equalsIgnoreCase("null"))
						{	
							
							tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName,"Fact table is Empty",String.valueOf(tableNo));
							continue;
						}else if(targetfacttableName==null||targetfacttableName.equalsIgnoreCase("")||targetfacttableName.equalsIgnoreCase("null"))
						{	
							tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName,"Fact target table is Empty",String.valueOf(tableNo));
							continue;
						}else if(periodClmn==null||periodClmn.equalsIgnoreCase("")||periodClmn.equalsIgnoreCase("null"))
						{
							tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName,"Period Column is Empty",String.valueOf(tableNo));
							continue;
							
						}else if(periodClmnValues==null||periodClmnValues.equalsIgnoreCase("")||periodClmnValues.equalsIgnoreCase("null"))
						{
							tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName,"Period Values is Empty",String.valueOf(tableNo));
							continue;
						}else if(joinColumnHT.size()==0)
						{
							tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName,"Join Column is Empty",String.valueOf(tableNo));
							continue;
						}else if(measureColumnHT.size()==0)
						{
							tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName,"Fact Genarated without Measure Column",String.valueOf(tableNo));
							continue;
						}
						
						//////////////////////////
						Hashtable srcConndetHT = Globals.getConnectionString(dbConSrc);
						Hashtable tarConnHT = Globals.getConnectionString(dbConTarget);
						String srcUname=(String)srcConndetHT.get(3);
						String tarUname=(String)tarConnHT.get(3);
						
						srcConn=Globals.getDBConnection(dbConSrc);
						targetConn=Globals.getDBConnection(dbConTarget);
						//Start Code Change Gokul 03MAY2014
						colsHT.put(5, "W_FACT_DETAILS.SOURCE_DB_CONN");
						valuesHT.put(5,dbConSrc);
						
						colsHT.put(6, "W_FACT_DETAILS.TARGET_DB_CONN");
						valuesHT.put(6,dbConTarget);
						//End Code Change Gokul 03MAY2014
						String createdIncTempTable="";
						String orgSrcFactTableNM="";
						
						orgSrcFactTableNM=facttableName;	//code change Vishnu 26Apr2014 
						if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
						{
						//For incremental Load
							//Creating temp source table for Incremental load
						Hashtable createdDetHT=createTemptable4IncrementalLoad(incrementLoadHT, lastAccestime, XMLdir, configFile, facttableName, srcConn, heirCode, tableLevelstatus, targetfacttableName,updateLoadHT,GenMode,String.valueOf(tableNo),joinTypeHT4Incr);
						
						createdIncTempTable=(String)createdDetHT.get(0);
						tableLevelstatus=(String)createdDetHT.get(1);
						String status = "";
						if(createdIncTempTable==null||createdIncTempTable.equalsIgnoreCase(""))
						{
//							statusHT.put("result", "No cofiguration for"+GenMode);
//							statusHT.put("errorMessage", "No cofiguration for"+GenMode);
							if(GenMode.equalsIgnoreCase("InsertFact")){
								status = "Incremental Insert is not configured yet.";
							}else if(GenMode.equalsIgnoreCase("UpdateFact")){
								status = "Incremental Update is not configured yet.";
							}
							
							if(GenMode.equals("UpdateFact")){
								status = insertStatus+" / "+status;
							}
							statusHT.put("result", "Generated");
							statusHT.put("errorMessage", status);
							Globals.updateFactStatus(statusHT,heirCode);
							 updateCurrentFactGenTimeinXML(heirCode,GenMode);
							return;
						}
						
						
						//Assign Incremental Fact  Table to Original Fact table
						//facttableName--Original Fact table name
//						orgSrcFactTableNM=facttableName;
						facttableName=createdIncTempTable;
						
						System.out.println("=============Fact Generating in Incremental Load Mode("+GenMode+")=============");
						System.out.println("Incremental "+GenMode+" table Name :"+createdIncTempTable);
						System.out.println("==========================================================================");
						}
						Connection dataConn = Globals.getDBConnection("DW_Connection");
						//Getting period Wid from Period Table using row wid SQL
						Hashtable resltHT=processingPeriodColumnsAndValues(configDoc, periodClmnValues, targetConn,heirCode,tableLevelstatus,targetfacttableName,String.valueOf(tableNo));
						//If Current table is getting Error Skip it and Proceed next Table 16APR2014 Gokul
						String find=(String)resltHT.get(0);
						tableLevelstatus=(String)resltHT.get(2);
						if(find.equals("Error"))
						{
							continue;
						}
						
						Hashtable rowWid4period=(Hashtable)resltHT.get(1);
						
						
						if(!GenMode.equalsIgnoreCase("InsertFact")&&!GenMode.equalsIgnoreCase("UpdateFact"))
						{
						//Delete Previous SQL data
						long acperiodWd = 0;
						////////////delete previous SQL DATA in All Three Tables
						for(int del=0; del<rowWid4period.size();del++){
							acperiodWd=Long.parseLong((String)rowWid4period.get(del));
							String dataTabledelQuery="DELETE FROM " + targetfacttableName + " WHERE "+periodClmnInTarTable+"="+acperiodWd+" and HIERARCHY_ID="+heirCode; //16MAR14
							PreparedStatement  dataTablePS=targetConn.prepareStatement(dataTabledelQuery);
							dataTablePS.execute();
							dataTablePS.close();
							
						}
						}else
						{
							System.out.println("Skipping delete Old data...");
						}
						
						Hashtable resHT1=createTemptableInDB(XMLdir, configFile, srcUname, tarUname,dataConn,targetConn,dimTableName,heirCode, tableLevelstatus,targetfacttableName,String.valueOf(tableNo));
						//If Current table is getting Error Skip it and Proceed next Table 16APR2014 Gokul
						String crtdTempSrcTable=(String)resHT1.get(0);
						tableLevelstatus=(String)resHT1.get(1);
						if(crtdTempSrcTable==null||crtdTempSrcTable.equalsIgnoreCase(""))
						{
							continue;
						}
						
						
						
//						System.out.println("crtdTempSrcTable "+crtdTempSrcTable);
						srcConnTempTablesHT.put(0,crtdTempSrcTable);
						String joinFactandDimtable = "";
						Hashtable tableNameChangeHT = new Hashtable<>();
						//Creation of Temp table in Source Connection Completed
						if(factGenType.equalsIgnoreCase("CreateCodeCombinationInDim")){
							//Join Souce and Dim table with LEFT OUTER JOIN
							Hashtable res2HT=factAndDimTableJoins(XMLdir,configFile,srcConn,rowWid4period,joinColumnHT,facttableName,crtdTempSrcTable,srcTabPeriodColumn,dimTableName,uniCols4CrtetempTableHT,heirCode,
									tableLevelstatus,targetfacttableName,orgSrcFactTableNM,GenMode,String.valueOf(tableNo),xmldoc,columnProperty,configDoc,uniColsFunc4CrtetempTableHT);		//code change Vsihnu 26Apr2014					//If Current table is getting Error Skip it and Proceed next Table 16APR2014
							joinFactandDimtable=(String)res2HT.get(0);
							tableLevelstatus=(String)res2HT.get(1);
							if(joinFactandDimtable==null||joinFactandDimtable.equalsIgnoreCase(""))
							{
								continue;
							}
							
//							
							srcConnTempTablesHT.put(srcConnTempTablesHT.size(),joinFactandDimtable);
						}else if(factGenType.equalsIgnoreCase("CreateCodeCombinationDuringFact")){
							//Join Souce and Dim table with LEFT OUTER JOIN for P&L Hierarchy
							//Genarate sum of Doc amt of Acc number during fact generation
							Hashtable res2HT=factAndDimTableJoinsLater(XMLdir,configFile,srcConn,rowWid4period,joinColumnHT,facttableName,crtdTempSrcTable,srcTabPeriodColumn,
									dimTableName,uniCols4CrtetempTableHT,heirCode,tableLevelstatus,targetfacttableName,orgSrcFactTableNM,GenMode,String.valueOf(tableNo),
									xmldoc,columnProperty,configDoc,measureColumnHT,SWjoinColumnHT,dimCodeColumn,uniColsFunc4CrtetempTableHT,srcConnTempTablesHT);		//code change Vishnu 26Apr2014					//If Current table is getting Error Skip it and Proceed next Table 16APR2014
							joinFactandDimtable=(String)res2HT.get(0);
							tableLevelstatus=(String)res2HT.get(1);
							tableNameChangeHT = (Hashtable)res2HT.get(3);
							if(joinFactandDimtable==null||joinFactandDimtable.equalsIgnoreCase(""))
							{
								continue;
							}
							
//							System.out.println("joinFactandDimtable "+joinFactandDimtable);
							srcConnTempTablesHT.put(srcConnTempTablesHT.size(),joinFactandDimtable);
//							srcConnTempTablesHT.put(srcConnTempTablesHT.size(),(String)res2HT.get(2));
						}
						
						///Joins of Temp and Source table Completed---------
						String tempTableInTarget="";
						if(!srcUname.equalsIgnoreCase(tarUname))
						{
						//Copy temp table from Src Connection to Target Connection
						 Hashtable res3HT=copyTemptableFromConntoConn(joinFactandDimtable, heirCode, srcConn, targetConn,joinFactandDimtable, tableLevelstatus,targetfacttableName,String.valueOf(tableNo));
						//If Current table is getting Error Skip it and Proceed next Table 16APR2014
						 tempTableInTarget=(String)res3HT.get(0);
						 tableLevelstatus=(String)res3HT.get(1);
							if(tempTableInTarget==null||tempTableInTarget.equalsIgnoreCase(""))
							{
								continue;
							}
						
//						System.out.println("tempTableInTarget "+tempTableInTarget);
						tarConnTempTablesHT.put(0,tempTableInTarget);
						
						System.out.println("(Src - Target)Table "+tempTableInTarget+" is Copied Successfully.");
						//Copy the Temp table from Src Connection to Target Connection
						}else
						{
							tempTableInTarget=joinFactandDimtable;
//							srcConnTempTablesHT.put(2,tempTableInTarget);
						}
						
						//Setting Source and Warehouse joins in target connection
						Hashtable res4HT=settingSourceAndWarehouseJoins(tempTableInTarget, SWjoinColumnHT, facttableName, targetfacttableName, measureColumnHT, targetConn,heirCode,tableLevelstatus,orgSrcFactTableNM,GenMode,updateLoadHT,String.valueOf(tableNo),srcTabPeriodColumn,periodClmnInTarTable,xmldoc,joinColumnHT,columnProperty,configDoc,rowWid4period,tableNameChangeHT);	//code change Vishnu 26Apr2014
						//If Current table is getting Error Skip it and Proceed next Table 16APR2014 Gokul
						String rows4DataTable=(String)res4HT.get(0);
						 tableLevelstatus=(String)res4HT.get(1);
						if(rows4DataTable==null||rows4DataTable.equalsIgnoreCase(""))
						{
							continue;
						}
						if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
						{
						srcConnTempTablesHT.put(2, createdIncTempTable);
						}
						
						//Drop Created temp table Created for Fact genaration
						 tableLevelstatus=dropTempTables(srcConnTempTablesHT, tarConnTempTablesHT, srcConn, targetConn,heirCode,tableLevelstatus,targetfacttableName,String.valueOf(tableNo));
						 
						 

						
						if(!GenMode.equalsIgnoreCase("UpdateFact"))
						{
						 System.out.println(rows4DataTable +" row(s) Inserted into " + targetfacttableName + " Table."); //16MAR14
						}else
						{
							System.out.println(rows4DataTable +" row(s) Updated into " + targetfacttableName + " Table."); //16MAR14
						}
						//Start Code Change Gokul 03MAY2014
						rowsAff4DB=rowsAff4DB+"Table_"+tableNo+":"+rows4DataTable+";";
						
						if(rowsAff4DB.length()>3800)
						{
							rowsAff4DB=rowsAff4DB.substring(0,3795)+"...";
						}
						
						colsHT.put(7, "W_FACT_DETAILS.NO_ROWS_AFFECTED");
						valuesHT.put(7,rowsAff4DB);
						//End Code Change Gokul 03MAY2014
							//End New Code change Gokul 05MAR2014 
							Date oneTabendTime=new Date();
							Format ontabendime = new SimpleDateFormat(dateFormat);
							String OneTabendTime=ontabendime.format(oneTabendTime);
//								//Start Code Change Gokul 03MAY2014
							 tableE.setAttribute("Last_Generate_Start_time",lastgendate);
							 tableE.setAttribute("Last_Generate_End_time", OneTabendTime);
								//End Code Change Gokul 03MAY2014
							 System.out.println("==================== Completed Generation of fact into "+targetfacttableName+" Table ====***"+OneTabendTime+"***==============="); //16MAR14
								
							 if(GenMode.equalsIgnoreCase("GenerateFact")){
								 tableLevelstatus=tableLevelstatus+"Table_"+tableNo+": FullLoad. "+targetfacttableName+" is Generated by ("+userID+") with "+rows4DataTable+" row(s)  at ("+OneTabendTime+")  ; "; //16MAR14
							 }else if(GenMode.equalsIgnoreCase("InsertFact")){
								 tableLevelstatus=tableLevelstatus+"Table_"+tableNo+": Incremental Insert. "+targetfacttableName+" is Generated by ("+userID+") with "+rows4DataTable+" row(s)  at ("+OneTabendTime+")  ; "; //16MAR14
							 }else if(GenMode.equalsIgnoreCase("UpdateFact")){
								 tableLevelstatus=tableLevelstatus+"Table_"+tableNo+": Incremental Update. "+targetfacttableName+" is Generated by ("+userID+") with "+rows4DataTable+" row(s)  at ("+OneTabendTime+")  ; "; //16MAR14
							 }
							 
							 tableNo++;
//							 statusHT=new Hashtable<>();
							 if(GenMode.equals("UpdateFact")){
									tableLevelstatus = insertStatus+" / "+tableLevelstatus;
								}
							 statusHT.put("result", "Generated");
							 statusHT.put("errorMessage",tableLevelstatus);
							 Globals.updateFactStatus(statusHT,heirCode);
							 
							 Globals.writeXMLFile(xmldoc, XMLdir,hierarchyXMLFileName);//	// Code Change Gokul 03MAY2014
							
							 
							 
							
						
					}
					
				}//End of Processing Each table by table
//					statusHT=new Hashtable<>();
					if(!tableLevelstatus.contains("Error"))
					{
					statusStr="Generated";	// Code Change Gokul 03MAY2014
					statusHT.put("result", "Generated");
					}else
					{
					statusStr= "Error";	// Code Change Gokul 03MAY2014
					statusHT.put("result", "Error");
					}
					statusHT.put("errorMessage",tableLevelstatus);
					Globals.updateFactStatus(statusHT,heirCode);
					
					Date endTime=new Date();
					Format endimeFormatter = new SimpleDateFormat(dateFormat);
					String factendTime=endimeFormatter.format(endTime);
					System.out.println("**********************************************Generation of Facts Completed******== "+factendTime+" ==****************************************");
					//Start Code Change Gokul 03MAY2014
					String timeStmp2=timeStampFORM.format(endTime);
					colsHT.put(8, "W_FACT_DETAILS.FACT_END_TIME");
					valuesHT.put(8,timeStmp2);
					
					colsHT.put(9, "W_FACT_DETAILS.NO_OF_FACT_TABLES");
					valuesHT.put(9,String.valueOf(tableNo-1));
					
					colsHT.put(10, "W_FACT_DETAILS.FACT_RESULT");
					valuesHT.put(10,statusStr);
					colsHT.put(11, "W_FACT_DETAILS.FACT_ERROR_MSG");
					valuesHT.put(11,tableLevelstatus);
					System.out.println("GEn mode for hierarchy"+GenMode);
					 updateCurrentFactGenTimeinXML(heirCode,GenMode);
					//Insert the Generated Fact Details into DB
//					insertORUpdateFactDetilsintoDB(targetConn, "insert",heirCode, colsHT, valuesHT);
					//End Code Change Gokul 03MAY2014
					
//					Globals.writeXMLFile(xmldoc, XMLdir, "HierachyLevel.xml"); 

					
			}//if(rootnode.getNodeType() == Node.ELEMENT_NODE){
		}//if(rootnode != null){
					
			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());
			
		}catch(Exception e){
			e.printStackTrace();
			Hashtable statusHT=new Hashtable<>();  // code change Menaka 15FEB2014
			String status=e.getMessage();
			
			String exceptionStr = null;
			StringWriter sw = null;
			PrintWriter pw = null;

			String dateFormat="";
			try {
				PropUtil prop=new PropUtil();
				
				try {
					dateFormat = prop.getProperty("DATE_FORMAT");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sw = new StringWriter();
				pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				exceptionStr = sw.toString();
				if (exceptionStr.contains("at ")){
					exceptionStr = exceptionStr.substring(0, exceptionStr.indexOf("at ") - 3);
				}else{
					exceptionStr = exceptionStr;
				}
			
				Date errorT=new Date();
				Format endimeFormatter = new SimpleDateFormat(dateFormat);
				String errorTime=endimeFormatter.format(errorT);
//				System.out.println("exceptionStr---->>>"+exceptionStr);
				statusHT.put("result", "Error");
				statusHT.put("errorMessage", tableLevelstatus+"Table_"+tableNo+": "+targetfacttableName+" is Generated by ("+userID+") contains : "+exceptionStr+" at ("+errorTime+")  ;  ");
				Globals.updateFactStatus(statusHT,heirCode);
				
			} finally {
				//Start Code Change Gokul 03MAY2014
				colsHT.put(0, "W_FACT_DETAILS.FACT_RESULT");
				valuesHT.put(0,statusStr);
				colsHT.put(1, "W_FACT_DETAILS.FACT_ERROR_MSG");
				valuesHT.put(1,tableLevelstatus);
				
				//Update the Generated Fact Details into DB
				//insertORUpdateFactDetilsintoDB(targetConn, "update",heirCode, colsHT, valuesHT);
				//End Code Change Gokul 03MAY2014
				try {
					if (pw != null) {
						pw.close();
					}
					if (sw != null) {
						sw.close();
					}
				} catch (IOException ignore) {
					return;
				}
			}
			
		}
	}
	
	public     String dropTempTables(Hashtable srcConnTempTablesHT,Hashtable tarConnTempTablesHT,Connection srcConn,Connection tarConn,String heirCode,String tableLevelstatus,String targetfacttableName,String tableNo)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		
		try
		{
			String consDroptab4Src="";
			String consDroptab4tar="";
			//Construct Drop Query 4 source Connection
			for(int i=0;i<srcConnTempTablesHT.size();i++)
			{
				consDroptab4Src="DROP TABLE "+(String)srcConnTempTablesHT.get(i);
				System.out.println("Drop table Query 4 Source :"+consDroptab4Src);
				PreparedStatement srcPS=srcConn.prepareStatement(consDroptab4Src);
				srcPS.execute();
								
			}
			
			
			//Construct Drop Query 4 target Connection
			for(int i=0;i<tarConnTempTablesHT.size();i++)
			{
				consDroptab4tar="DROP TABLE "+(String)tarConnTempTablesHT.get(i);
				System.out.println("Drop table Query 4 Target :"+consDroptab4tar);
				PreparedStatement tarPS=tarConn.prepareStatement(consDroptab4tar);
				tarPS.execute();
			}
			
			
			
			
		}catch(Exception e)
		{
			tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
			e.printStackTrace();
			return null;
		}
		
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		return tableLevelstatus;
	}
	
	public     String settingErrorStatusinXML(String heirCode,String tableLevelstatus,String targetfacttableName,String inTableStatus,String tableNo)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		try{
			PropUtil prop=new PropUtil();
			String dateFormat= prop.getProperty("DATE_FORMAT");
			Hashtable statusHT=new Hashtable<>();
			System.out.println("************************Warning : "+inTableStatus+"************************ So Skipping it "+targetfacttableName);
			statusHT.put("result", "Error");
			
			Date dateTime=new Date();
			Format ontabendime = new SimpleDateFormat(dateFormat);
			String errorTime=ontabendime.format(dateTime);
			tableLevelstatus=tableLevelstatus+"Table_"+tableNo+": "+targetfacttableName+" is Generated by ("+userID+") contains : "+inTableStatus+" at ("+errorTime+")  ;  ";
			statusHT.put("errorMessage",tableLevelstatus);
			Globals.updateFactStatus(statusHT,heirCode);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return tableLevelstatus;
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		
		return tableLevelstatus;
	}
	
	public      Hashtable createTemptableInDB(String XmlDir,String configFileNM,String dbConSrc,String dbConTarget,Connection srcConn,Connection targetConn,String dimTableName,String hierCode,String tableLevelstatus,String targetfacttableName,String tableNo)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		String createdTemptable="";
		Hashtable resHT=new Hashtable<>();
		try{
			
			int tempTableDBId=Globals.getMaxID(XmlDir, configFileNM, "DB_Temp_Table_ID", "ID");
			createdTemptable=dimTableName+"_TEMP_"+tempTableDBId;
			
			//Creating a New table in Src system
			String createsql="";
			
			if(dbConSrc.equalsIgnoreCase(dbConTarget))
			{
				createsql="CREATE TABLE "+createdTemptable+" AS SELECT * FROM "+dimTableName+" WHERE HIERARCHY_ID="+hierCode;
				System.out.println("================SOURCE CONNECTION = TARGET CONNECTION================");
				System.out.println("================"+dbConSrc+" = "+dbConTarget+"================");
				System.out.println("Create SQL:"+createsql);
				PreparedStatement tableCreate=srcConn.prepareStatement(createsql);
				tableCreate.execute();
				System.out.println("Temp Table "+createdTemptable+" has Created Successfully.");
				tableCreate.close();
				
			}else
			{
							
				System.out.println("================SOURCE CONNECTION NOT EQUAL TARGET CONNECTION================");
				System.out.println("================"+dbConSrc+" NOT EQUAL "+dbConTarget+"================");
				//copy the temp table from target Connection to Source Connection
				Hashtable resht=copyTemptableFromConntoConn(dimTableName, hierCode, targetConn, srcConn, createdTemptable, tableLevelstatus, targetfacttableName,tableNo);
				//If Current table is getting Error Skip it and Proceed next Table 16APR2014
				createdTemptable=(String)resht.get(0);
				tableLevelstatus=(String)resht.get(1);
				if(createdTemptable==null||createdTemptable.equalsIgnoreCase(""))
				{
					return null;
				}
				
				
			}
			
			//Make Joins between Src and Temp table in SRC DB connection
			
//			xxzx
			
			
			
			
			
			
		}catch(Exception e){
			tableLevelstatus=settingErrorStatusinXML(hierCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
			e.printStackTrace();
			resHT.put(0, "");
			resHT.put(1, tableLevelstatus);
			
			return resHT;
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		resHT.put(0, createdTemptable);
		resHT.put(1, tableLevelstatus);
		
		return resHT;
	}
	
	//Start Code Change Gokul 05MAR2014
	public     Hashtable getDatatypefromDBtable(String tablenameDotColumnName,Connection dbConn,String heirCode,String tableLevelstatus,String targetfacttableName)
	{
		
		String dataType="";
		String size="";
		String decimalDigits="";
		String tableName="";
		String columnName="";
		String[] splitter=null;
		Hashtable resultHT=new Hashtable<>();
		try{
			/////Split TableName and Column Name//////
			splitter=tablenameDotColumnName.split("\\.");
			tableName=splitter[0];
			columnName=splitter[1];
			tableName=tableName.trim();
			columnName=columnName.trim();
			tableName=tableName.toUpperCase();
			columnName=columnName.toUpperCase();
			//////////////////////////////////////////

		DatabaseMetaData dbmD=dbConn.getMetaData();		
		ResultSet columnsRS=dbmD.getColumns(null, null, tableName, columnName);	
		
		System.out.println("tablenameDotColumnName : "+tablenameDotColumnName);
		
		

		
		while(columnsRS.next())
		{
		dataType=columnsRS.getString("TYPE_NAME");
		size=columnsRS.getString("COLUMN_SIZE");
		decimalDigits=columnsRS.getString("DECIMAL_DIGITS");
//		
		System.out.println("dataType : "+dataType);
		System.out.println("size : "+size);
		System.out.println("decimalDigits : "+decimalDigits);
//		data_precision
//		COLUMN_SIZE
		}
		columnsRS.close();
		

		if(dataType==null||dataType.equalsIgnoreCase(""))
		{
			
			System.out.println("***************** Warning : Data Type is Null ********************");
			System.out.println("DataType : "+dataType);
			System.out.println("Size : "+size);
			System.out.println("DecimalDigits : "+decimalDigits);
			System.out.println("TableName : "+tableName);
			System.out.println("ColumnName : "+columnName);
			
			dataType="";
			return null;
		}
		if(size==null||size.equalsIgnoreCase(""))
		{
			size="0";
		}
		if(decimalDigits==null||decimalDigits.equalsIgnoreCase(""))
		{
			decimalDigits="0";
		}
		resultHT.put(1, dataType);
		resultHT.put(2, size);
		resultHT.put(3, decimalDigits);
		resultHT.put(4, columnName);
		resultHT.put(5, tableName);
        
        
		}catch(Exception e){
			
			Hashtable statusHT=new Hashtable<>();
			statusHT.put("result", "Error");
			statusHT.put("errorMessage", tableLevelstatus+targetfacttableName+" contains : "+e.getMessage());
			Globals.updateFactStatus(statusHT,heirCode);
			
			e.printStackTrace();
		}
		
		
		return resultHT;
		
	}
	
	public     Hashtable copyTemptableFromConntoConn(String fromTable,String heirID,Connection fromConnection,Connection toConnection,String newTableName,String tableLevelstatus,String targetfacttableName,String tableNo){
		
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		//Using this As A Common Method Assinging used only for Understanding purpose.
		//////////////
		String dimTableName=fromTable;
		String hierCode=heirID;
		Connection srcConn=toConnection;
		Connection targetConn=fromConnection;
		String createdTemptable=newTableName;
		/////////////
		Hashtable resultHT=new Hashtable<>();
		
		try{
			
		//Getting dim table data from Target Connection
		String selectSQL="SELECT * FROM "+dimTableName+" WHERE HIERARCHY_ID="+hierCode;
		
		System.out.println("Constructed Select SQL:"+selectSQL);
		
		//select Query Prepare STMT
		PreparedStatement getDataPS=targetConn.prepareStatement(selectSQL);
		ResultSet selectRS=getDataPS.executeQuery();
		
		
		
		String targTablesql="SELECT * FROM "+dimTableName + " WHERE ROWNUM = 1";				
		
		java.sql.Statement statement= targetConn.createStatement();  
		
	    ResultSet results = statement.executeQuery(targTablesql);
	    
	      
	     // Get resultset metadata
	      
	    ResultSetMetaData metadata = results.getMetaData();
	     
	    int columnCount = metadata.getColumnCount();
	    
	    // Get the column names; column indices start from 1
	    
	    String consCreateColSTMT="";
	    String consInsStmt4temp="";
	    String consInsValQ="";
	    
	    Hashtable dataTypeHT=new Hashtable();
	      
	     for (int i=1; i<=columnCount; i++) {
	      
	      String columnName = metadata.getColumnName(i);
//	      System.out.println("columnName :"+columnName);
	      Hashtable tableDet=getDatatypefromDBtable(dimTableName+"."+columnName, targetConn, "", "", "");
	      String datatype=(String)tableDet.get(1);
	      String size=(String)tableDet.get(2);
	      String precision=(String)tableDet.get(3);
		
	      
	      if(datatype.contains("VARCHAR")||datatype.contains("CHAR"))
	      {
	    	
	    	  if(i!=columnCount)
	    	  {
	    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+"("+size+"),";
	    	  }else
	    	  {
	    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+"("+size+")";  
	    	  }
	      }else if(datatype.contains("NUMBER"))
	      {
	    	  if(!size.equalsIgnoreCase("0"))
	    	  {
	    	  
	    	  if(i!=columnCount)
	    	  {
	    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+"("+size+","+precision+"),";
	    	  }else
	    	  {
	    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+"("+size+","+precision+")";
	    	  }
	    	  }else
	    	  {
	    		  if(i!=columnCount)
		    	  {
		    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+",";
		    	  }else
		    	  {
		    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype;
		    	  }
	    	  }
	      }else if(datatype.contains("DATE")||datatype.contains("TIMESTAMP"))
	      {
	    	  if(i!=columnCount)
	    	  {
	    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+",";
	    	  }else
	    	  {
	    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype;
	    	  }
	      }
		
	      
	      //Construct Insert SQL and ValData
	      if(i!=columnCount)
    	  {
	      consInsStmt4temp=consInsStmt4temp+columnName+",";
	      consInsValQ=consInsValQ+"?,";
    	  }else
    	  {
    		  consInsStmt4temp=consInsStmt4temp+columnName;
    		  consInsValQ=consInsValQ+"?";
    	  }
	      
	      
	      dataTypeHT.put(i,datatype);
	     }
	     
	    //Create Stmt
	     consCreateColSTMT="CREATE TABLE "+createdTemptable+"("+consCreateColSTMT+")";
	     
	     //Insert STMT
	     consInsStmt4temp="INSERT INTO "+createdTemptable+" ("+consInsStmt4temp+") VALUES("+consInsValQ+")";
		
	    
		System.out.println("Constructed Create Stmt : "+consCreateColSTMT);
		
		System.out.println("createdTemptable : "+createdTemptable);
		
		PreparedStatement createTabPS=srcConn.prepareStatement(consCreateColSTMT);
		createTabPS.execute();
		createTabPS.close();
		
		
		
		
		//Insert Query Prepare STMT
		PreparedStatement insQueryPS=srcConn.prepareStatement(consInsStmt4temp);
		
		
		int key=1;
		while(selectRS.next())
		{
			
			
		    for (int i=1; i<=columnCount; i++) {
		    String tempDType=(String)dataTypeHT.get(i);
		    if(tempDType.contains("VARCHAR")||tempDType.contains("CHAR"))
		    {
		    	
		    	insQueryPS.setString(i, selectRS.getString(i));
		    	
		    }else if(tempDType.contains("NUMBER"))   
		    {
		    	insQueryPS.setDouble(i, selectRS.getDouble(i));
		    }else if(tempDType.contains("DATE"))  
		    {
		    	insQueryPS.setDate(i, selectRS.getDate(i));
		    }else if(tempDType.contains("TIMESTAMP"))  
		    {
		    	insQueryPS.setTimestamp(i, selectRS.getTimestamp(i));
		    }
		   
		  
		    }
		
		    insQueryPS.addBatch();
		    key++;
		    
		}
		insQueryPS.executeBatch();
		
		
		selectRS.close();
		getDataPS.close();
		insQueryPS.close();
		results.close();
		
		System.out.println("Data Inserted into the "+createdTemptable+" table Successfully");
		}
		catch(Exception e)
		{
			tableLevelstatus=settingErrorStatusinXML(hierCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
			e.printStackTrace();
			resultHT.put(0, "");
			resultHT.put(1, tableLevelstatus);
			
			return resultHT;
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		resultHT.put(0, createdTemptable);
		resultHT.put(1, tableLevelstatus);
		
		return resultHT;
	}
	
	
	public     String factAndDimTableJoins(Hashtable joinColumnHT)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		 String consJoinClmn="";
		 String[] col4Select=new String[joinColumnHT.size()];
		 
		 int index=0;
		try{
			
			 for(int k=0;k<joinColumnHT.size();k++)
			 {//Code Change Gokul 16MAR2014
				 String joinColStr=(String)joinColumnHT.get(k);
				 String[] splitter=null;
				 if(joinColStr.contains("="))
				 {
					 splitter=joinColStr.split("=");
					 if(splitter[0].contains(".")){
						 String[] splitter2=splitter[0].split("\\.");
					 col4Select[index]=splitter[0];
					 index++;
					 
				 }
					 }
				 if(k==joinColumnHT.size()-1){
						
					 consJoinClmn=consJoinClmn+joinColumnHT.get(k);    
					 //End Code Change Gokul 16MAR2014
				 }else {
					 consJoinClmn=consJoinClmn+joinColumnHT.get(k)+" AND ";
					 
				 }
				 //Start Code Change Gokul 16MAR2014
				 if(splitter[1].contains(".")){
					 String[] splitter2=splitter[1].split("\\.");
					 
				 }

				 //End Code Change Gokul 16MAR2014				 
				
			 }
//			 consJoinClmn=" LEFT OUTER JOIN "+facttables[0]+" ON "+consJoinClmn;
//			 System.out.println("consJoinClmn "+consJoinClmn);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		return null;
	}
	
	public      Hashtable processingPeriodColumnsAndValues(Document configDoc,String periodClmnValues,Connection dbConn,String heirCode,String tableLevelstatus,String targetfacttableName,String tableNo)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		 String[] periodClmnVals = null;
		 Hashtable rowWid4period=new Hashtable();
		 Hashtable resHT=new Hashtable<>();
		
		try{
			
			NodeList rowidtagNL=configDoc.getElementsByTagName("row_wid_SQL");
			Node rowidN=rowidtagNL.item(0);
			
			 if(periodClmnValues.contains(";"))
			 {
			    periodClmnVals =periodClmnValues.split(";");
			 }else if(periodClmnValues!=null && !periodClmnValues.equals("") && !periodClmnValues.equals("null")){
				 
				 periodClmnVals = new String[1];
				 periodClmnVals[0] =periodClmnValues;
				 
			 }
			 
			 
			String row_wid_SQL=rowidN.getTextContent();
			 
			 
			 ///////////////////////////////////////////Adding Period WID //////////////////////////////
			
				int i=0;
				for (String name: periodClmnVals) {
				
				//SELECT ROW_WID, MCAL_PERIOD_NAME FROM W_MCAL_PERIOD_D WHERE MCAL_CAL_NAME = 'ATC Accounting' and MCAL_PERIOD_NAME in ('NOV-12'); 
				String tempsql=row_wid_SQL+"AND MCAL_PERIOD_NAME in ('"+name+"')";
				System.out.println("SQL for Getting Period Id : "+tempsql);
			
				PreparedStatement ps=dbConn.prepareStatement(tempsql);
				ResultSet rs=ps.executeQuery(tempsql);
				
				
				while(rs.next())
				{
					

				if(!rowWid4period.contains(rs.getString(1))&&!rowWid4period.contains(rs.getString(2)))
				{
				rowWid4period.put(i,rs.getString(1));
				i++;
				}
					
				}
				rs.close();
				ps.close();
				
				}
				System.out.println("Period Id from DB : "+rowWid4period+" for the given "+rowWid4period.size()+" month(s)");
				
			 //////////////////////////////////////////////////////////////////////////////////////////
				
				
				
			
			
			
		}catch(Exception e)
		{
		
			tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
			e.printStackTrace();
			resHT.put(0, "Error");
			resHT.put(1, null);
			resHT.put(2, tableLevelstatus);
			return resHT;
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		resHT.put(0, "success");
		resHT.put(1, rowWid4period);
		resHT.put(2, tableLevelstatus);
		
//		resHT.put(0, rowWid4period);
//		resHT.put(1, tableLevelstatus);
		
		return resHT;
	}
	
public     Hashtable createAtempTablefromExisting(String XmlDir,String configFileNM,Connection dbConn,String tempTableName,String SrctableName,Hashtable uniCols4CrtetempTableHT,String heirCode,String tableLevelstatus,String targetfacttableName,String tableNo){
		
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());
		//Creating a temp table like Same as only the Columns in the Source Table
		
		String CreatedTempTableNM="";
		Integer tempTableDBId=Globals.getMaxID(XmlDir, configFileNM, "DB_Temp_Table_ID", "ID");
		
		CreatedTempTableNM=tempTableName+"_TEMP_"+tempTableDBId;//Dim_Fact_Join_Temp
		
		// 14APR14 -- TEMP Table Name should be less than 28 Chars (Oracle has a limit on this)
		int IDENTIFIER_MAX_LENGTH = 28;
		
		if(CreatedTempTableNM.length()>28)
		{
		int tempTableLength = IDENTIFIER_MAX_LENGTH - tempTableDBId.toString().length() - "_TEMP_".length();
		CreatedTempTableNM = tempTableName.substring(0, tempTableLength) + "_TEMP_" + tempTableDBId; //Dim_Fact_Join_Temp
		}
		Hashtable createdColDType=new Hashtable<>();
		Hashtable resHT=new Hashtable<>();
		
		System.out.println("uniCols4CrtetempTableHT===>"+uniCols4CrtetempTableHT);
		try{
			String consCreateColSTMT="";
			for(int i=0;i<uniCols4CrtetempTableHT.size();i++)
			{
//				System.out.println()
				String tablenameDotColumnName=(String)uniCols4CrtetempTableHT.get(i);
				Hashtable tableDet = new Hashtable<>(); 
				if(tablenameDotColumnName.toLowerCase().contains("wc_flex_hierarchy_d")){
					Connection con = Globals.getDBConnection("DW_Connection");
					tableDet = getDatatypefromDBtable(tablenameDotColumnName, con, "", "", "");
				}else{
					tableDet = getDatatypefromDBtable(tablenameDotColumnName, dbConn, "", "", "");
				}
				
			      String datatype=(String)tableDet.get(1);
			      String size=(String)tableDet.get(2);
			      String precision=(String)tableDet.get(3);
			      String columnName=(String)tableDet.get(4);
			      
			      createdColDType.put(i+1, datatype);//Code Change Gokul 16APR2014
				
			      
			      if(datatype.contains("VARCHAR")||datatype.contains("CHAR"))
			      {
			    	
			    	  if(i!=uniCols4CrtetempTableHT.size()-1)
			    	  {
			    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+"("+size+"),";
			    	  }else
			    	  {
			    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+"("+size+")";  
			    	  }
			      }else if(datatype.contains("NUMBER"))
			      {
			    	  if(!size.equalsIgnoreCase("0"))
			    	  {
			    	  
			    		  if(i!=uniCols4CrtetempTableHT.size()-1)
			    	  {
			    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+"("+size+","+precision+"),";
			    	  }else
			    	  {
			    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+"("+size+","+precision+")";
			    	  }
			    	  }else
			    	  {
			    		  if(i!=uniCols4CrtetempTableHT.size()-1)
				    	  {
				    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+",";
				    	  }else
				    	  {
				    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype;
				    	  }
			    	  }
			      }else if(datatype.contains("DATE")||datatype.contains("TIMESTAMP"))
			      {
			    	  if(i!=uniCols4CrtetempTableHT.size()-1)
			    	  {
			    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype+",";
			    	  }else
			    	  {
			    	  consCreateColSTMT=consCreateColSTMT+columnName+" "+datatype;
			    	  }
			      }
				
			     
				
			}
			
			
				
			
			
			String createSQL="CREATE TABLE "+CreatedTempTableNM+" ("+consCreateColSTMT+")";
			
			System.out.println("Constructed New Create Statement :"+createSQL);
			
			
			
//			String createSQL="CREATE TABLE "+CreatedTempTableNM+" AS (SELECT * FROM "+SrctableName+" WHERE 1=2)";
			PreparedStatement createPS=dbConn.prepareStatement(createSQL);
			ResultSet rs1=createPS.executeQuery();
			createPS.close();
			dbConn.commit();
			
			System.out.println("Created Temp Table is : "+CreatedTempTableNM);
			
		}catch(Exception e)
		{
			tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
			e.printStackTrace();
			resHT.put(0, "");
			resHT.put(1, createdColDType);
			resHT.put(2,tableLevelstatus);
			return resHT;
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		
		resHT.put(0, CreatedTempTableNM);
		resHT.put(1, createdColDType);
		resHT.put(2,tableLevelstatus);
		
		return resHT;
	}
	public     Hashtable getDataTypeSEQ4Table(int columnCount,ResultSetMetaData metadata,String TableName,Connection Conn)
	{
		
		Hashtable dataTypeHT=new Hashtable();
		try{
			  
			     for (int i=1; i<=columnCount; i++) {
			      
			      String columnName =  metadata.getColumnName(i);
//			      System.out.println("columnName :"+columnName);
			      Hashtable tableDet=getDatatypefromDBtable(TableName+"."+columnName, Conn, "", "", "");
			      String datatype=(String)tableDet.get(1);
			      String size=(String)tableDet.get(2);
			      String precision=(String)tableDet.get(3);
				
			      dataTypeHT.put(i,datatype);
			     }
			
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return dataTypeHT;
	}
	
	public     Hashtable factAndDimTableJoins(String XMLdir,String configFile,Connection dbConn,Hashtable rowWid4period,Hashtable joinColumnHT,String facttableName,
			String TempDimtable,String periodClmn,String originalDimTableName,Hashtable uniCols4CrtetempTableHT,String heirCode,String tableLevelstatus,
			String targetfacttableName,String orgSrcFactTableNM,String GenMode,String tableNo,Document xmlDoc,Hashtable columnPropertyHT,Document configDoc,Hashtable colFunc4MeasureHT)	//code change Vishnu 26Apr2014
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		 String consJoinClmn="";
		 String[] srcMapCol=new String[joinColumnHT.size()];
		 String[] tarMapCol=new String[joinColumnHT.size()];
		 
		 int srcindex=0;
		 int tarindex=0;
		 
		 String decodeColmn="";
		 int decodeColID=1;
		 Hashtable resHt=new Hashtable<>();
		try{
			Connection conn = Globals.getDBConnection("DW_Connection");		//code change Vishnu 26Apr2014
			Node hierNd = Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", heirCode);
			NodeList hierNdList = hierNd.getChildNodes();
			Node rootNd = null;
			Hashtable segmentHT = new Hashtable<>();
			
			int segmentCount = 0;
			if(!Globals.getAttrVal4AttrName(hierNd, "Number_of_Segment").equals("")){
				segmentCount=Integer.parseInt(Globals.getAttrVal4AttrName(hierNd, "Number_of_Segment"));
			}
			for(int m=0;m<hierNdList.getLength();m++){
				if(hierNdList.item(m).getNodeType() == Node.ELEMENT_NODE){
					if(hierNdList.item(m).getNodeName().equals("RootLevel")){
					rootNd = hierNdList.item(m);
					}
				}
			}
//			Hashtable segmentHT = new Hashtable<>();
			Hashtable segmentAndCodeCombiColName = new Hashtable<>();
			String codeCombiColumnName = "";
			segmentAndCodeCombiColName = gettingSegmentValueAndCodeCombiColumnName(heirCode, xmlDoc, configDoc);
			codeCombiColumnName  = String.valueOf(segmentAndCodeCombiColName.get("CodeCombination"));
			segmentHT = (Hashtable) segmentAndCodeCombiColName.get("Segment");
			//end code change Vishnu 26Apr2014
			 for(int k=0;k<joinColumnHT.size();k++)
			 {
				 String joinColStr=(String)joinColumnHT.get(k);
				 /////Replacing temp table Name as Original Table name
				 joinColStr=joinColStr.replaceAll(originalDimTableName, TempDimtable);
				 
				 if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
				 {
				 joinColStr=joinColStr.replaceAll(orgSrcFactTableNM, facttableName);//Change Gokul 21APR2014 INCLOAD
				 }
				  /////
				 String[] splitter=null;
				 if(joinColStr.contains("="))
				 {
					 splitter=joinColStr.split("=");
					 if(splitter[0].contains(".")){
						 String[] splitter2=splitter[0].split("\\.");
						 srcMapCol[srcindex]=splitter[0];
						 String[] tempsplit=splitter[1].split("\\.");
						 tarMapCol[tarindex]=tempsplit[1];
						 srcindex++;
						 tarindex++;
						

						 
					 
				 }
					 }
				 if(k==joinColumnHT.size()-1){
						
					 consJoinClmn=consJoinClmn+joinColStr;    
					 decodeColmn=decodeColmn+"DECODE("+splitter[1]+",null,"+splitter[0]+","+splitter[1]+") as COLUMN_"+decodeColID;
					 //DECODE(W_GL_OTHER_F.GL_ACCOUNT_WID, null, WC_FLEX_HIERARCHY_D_TEMP_122.HIER20_NUM,W_GL_OTHER_F.GL_ACCOUNT_WID) as Col1
					 decodeColID++;
					 
					 
				 }else {
					 consJoinClmn=consJoinClmn+joinColStr+" AND ";
					 decodeColmn=decodeColmn+"DECODE("+splitter[1]+",null,"+splitter[0]+","+splitter[1]+") as COLUMN_"+decodeColID+",";
					 decodeColID++;
				 }
						 
//				 System.out.println("joinColumnHT.get(k) "+joinColStr);
				
			 }
			 if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
			 {
			 consJoinClmn=" INNER JOIN "+facttableName+" ON "+consJoinClmn;	 
			 }else{
			 consJoinClmn=" LEFT OUTER JOIN "+facttableName+" ON "+consJoinClmn;
			 }
//			 System.out.println("consJoinClmn "+consJoinClmn);
			 
			  
			   
			   
			   String oldtempDimTable=TempDimtable;
			 
			 TempDimtable=TempDimtable+","+facttableName;//Join Two table Columns As Single
			 
			 Hashtable resHT= createAtempTablefromExisting(XMLdir, configFile, dbConn, facttableName, TempDimtable,uniCols4CrtetempTableHT,heirCode, tableLevelstatus,targetfacttableName,tableNo);
			 
			 TempDimtable=(String)resHT.get(0);
			 tableLevelstatus=(String)resHT.get(2);
				//If Current table is getting Error Skip it and Proceed next Table 16APR2014
				if(TempDimtable==null||TempDimtable.equalsIgnoreCase(""))
				{
					return null;
				}
			 
			
			 
//			 String targTablesql="SELECT * FROM "+TempDimtable + " WHERE ROWNUM = 1";				
				
//				java.sql.Statement statement= dbConn.createStatement();  
				
//			    ResultSet results = statement.executeQuery(targTablesql);
			    
			      
			     // Get resultset metadata
			      
//			    ResultSetMetaData metadata = results.getMetaData();
			     
//			    int columnCount = metadata.getColumnCount();
			    int columnCount = uniCols4CrtetempTableHT.size();//Code Change Gokul 16APR2014
			    
			    String consQ4Ins="";
			    String conColsIns="";
			    String conSelColumn="";
			    
			    
			    for(int i=1;i<=columnCount;i++)
			    {
			    	String tableNamedotColNM=(String)uniCols4CrtetempTableHT.get(i-1);
			    
			    	tableNamedotColNM=tableNamedotColNM.replace(originalDimTableName, oldtempDimTable);
			    	
			    	String[] tableNMcolNM=tableNamedotColNM.split("\\.");
			    	
			    	
			    	if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
			    	{
			    	
			    	tableNamedotColNM=tableNamedotColNM.replace(orgSrcFactTableNM, facttableName);//Change Gokul 21APR2014 INCLOAD
//			    	
			    	}
//			    	
			    	
			    	
			    	if(i!=columnCount)
			    	{
			    		
			    		conSelColumn=conSelColumn+tableNamedotColNM+",";//Code Change Gokul 16APR2014//for Setting tablename.column name
			    		conColsIns=conColsIns+tableNMcolNM[1]+",";
			    		consQ4Ins=consQ4Ins+"?,";
			    	}else
			    	{
			    		conSelColumn=conSelColumn+tableNamedotColNM;
			    		conColsIns=conColsIns+tableNMcolNM[1];
			    		consQ4Ins=consQ4Ins+"?";	
			    	}
			    	for(int j =0;j<colFunc4MeasureHT.size();j++){
				    	
				    }
			    }
			    
			    Enumeration e = colFunc4MeasureHT.keys();
			    while(e.hasMoreElements()){
			    	String nextKey = String.valueOf(e.nextElement());
			    	conSelColumn = conSelColumn.replace(nextKey, String.valueOf(colFunc4MeasureHT.get(nextKey)));
			    }
			    
			    
			    String sqltojoin="SELECT "+conSelColumn+","+decodeColmn+" FROM "+oldtempDimTable+consJoinClmn+" AND "+facttableName+"."+periodClmn+" in (";
			    
			    
				String insSQL4Joinres="INSERT INTO "+TempDimtable+" ("+conColsIns+") VALUES ("+consQ4Ins+")";
				
				//Prepare Statement 4 INS
				PreparedStatement insPS=dbConn.prepareStatement(insSQL4Joinres);
				
//				Hashtable dataTypeHT=getDataTypeSEQ4Table(columnCount, metadata, TempDimtable, dbConn);
				Hashtable dataTypeHT=(Hashtable)resHT.get(1);
				
				System.out.println("Insert Query 4 Join Column : "+insSQL4Joinres);
			 
				int rows4DataTable=0;
				Hashtable nodeHT=new Hashtable<>();	//code change Vishnu 26Apr2014
				Hashtable nodevalueandtypeHT=new Hashtable<>();
				Globals.processUpDowninDB(rootNd, nodeHT, 1, nodevalueandtypeHT);
				for(int j=0;j<rowWid4period.size();j++){
					
					String periodColumn=(String)rowWid4period.get(j)+") ORDER BY SORT_ORDER";
					String JoinSQLQuery=sqltojoin+periodColumn;
				
					System.out.println("Full SQL for Join(JoinSQLQuery)(SQL+Condition) : "+JoinSQLQuery);

					//SELECT * FROM WC_FLEX_HIERARCHY_D_TEMP_56 LEFT OUTER JOIN W_GL_OTHER_F ON WC_FLEX_HIERARCHY_D_TEMP_56.HIER20_NUM=W_GL_OTHER_F.GL_ACCOUNT_WID AND ACCT_PERIOD_END_DT_WID in (100220121031000);
					//SELECT * FROM WC_FLEX_HIERARCHY_D_TEMP_56 LEFT OUTER JOIN W_GL_OTHER_F ON WC_FLEX_HIERARCHY_D_TEMP_56.HIER20_NUM=W_GL_OTHER_F.GL_ACCOUNT_WID AND ACCT_PERIOD_END_DT_WID in (100220121130000) ORDER BY SORT_ORDER
					PreparedStatement ps1=dbConn.prepareStatement(JoinSQLQuery);
					ResultSet joinQueryRS=ps1.executeQuery(JoinSQLQuery);
					
					////////// For Filling Period Column
					
					int periodColFillindex=joinQueryRS.findColumn(periodClmn);
					
					System.out.println("Table Period Column and Index :"+periodClmn+ " :"+periodColFillindex);
					
					ResultSetMetaData resMdata= joinQueryRS.getMetaData();
					
					int key=0;
//					
//					Hashtable joinHT = new Hashtable<>();		//code change Vishnu 26Apr2014
//					String tabname = "";
//					for(int i=0;i<joinColumnHT.size();i++){
//						String srcTableColumn = String.valueOf(joinColumnHT.get(i)).split("=")[1];
//						joinHT.put(i, srcTableColumn.split("\\.")[1]);
//						tabname = srcTableColumn.split("\\.")[0];
//					}
//					
//					
//					Collection c = (Collection)columnPropertyHT.keySet();
//					Iterator itr = c.iterator();
//					Hashtable colPropHT = new Hashtable<>();
//					int l=0;
//					while(itr.hasNext()){
//						String temp = (String)itr.next();
//						colPropHT.put(l, temp);
//						l++;
//					}
//					 String sql = "";
//					boolean createCodeCombination = false;
//					Hashtable segmentDetailHT = new Hashtable<>();
//					PreparedStatement prestat = null;
//					Hashtable htTa = new Hashtable<>();
					while(joinQueryRS.next())
					{
						
						for (int i=1; i<=columnCount; i++) {
						
							for (int k=1; k<=joinColumnHT.size(); k++) {
								int tarIndex=joinQueryRS.findColumn(tarMapCol[k-1]);
								
								if(i==tarIndex)
							    {
							    	int insIndex=joinQueryRS.findColumn("COLUMN_"+k);
//							    	System.out.println("Column index: "+insIndex);
//							    	System.out.println("dt : "+resMdata.getColumnTypeName(insIndex));
//							    	System.out.println("Column : "+resMdata.getColumnName(insIndex));
							    	
							    	String decodeColDtype=resMdata.getColumnTypeName(insIndex);
							    	
							    	if(decodeColDtype.contains("VARCHAR")||decodeColDtype.contains("CHAR"))
								    {
								    	String columnValue = joinQueryRS.getString("COLUMN_"+k);
								    	if(columnValue!=null){
								    	if(columnValue.length()>30){
								    		columnValue = columnValue.substring(0,29);
								    	}}
								    	
								    	insPS.setString(i,columnValue);
								    	
								    }else if(decodeColDtype.contains("NUMBER"))   
								    {
								    	insPS.setDouble(i, joinQueryRS.getDouble("COLUMN_"+k));
								    }else if(decodeColDtype.contains("DATE"))  
								    {
								    	insPS.setDate(i, joinQueryRS.getDate("COLUMN_"+k));
								    }else if(decodeColDtype.contains("TIMESTAMP"))  
								    {
								    	insPS.setTimestamp(i, joinQueryRS.getTimestamp("COLUMN_"+k));
								    }
							    	
							    	
							    }else if(i==periodColFillindex)////////// For Filling Period Column
							    {
//							    	System.out.println(i+ " periodColFillindex"+periodColFillindex+" (String)rowWid4period.get(j) "+(String)rowWid4period.get(j)+" j "+j);
							    	insPS.setString(i, (String)rowWid4period.get(j));
							    }else{
							    	  String tempDType=(String)dataTypeHT.get(i);
							    	if(tempDType.contains("VARCHAR")||tempDType.contains("CHAR"))
								    {

								    	String columnValue = joinQueryRS.getString(i);
								    	if(columnValue!=null){
								    	if(columnValue.length()>30){
								    		columnValue = columnValue.substring(0,29);
								    	}}
								  
								    	insPS.setString(i, columnValue);
								    	
								    }else if(tempDType.contains("NUMBER"))   
								    {
								    	insPS.setDouble(i, joinQueryRS.getDouble(i));
								    }else if(tempDType.contains("DATE"))  
								    {
								    	insPS.setDate(i, joinQueryRS.getDate(i));
								    }else if(tempDType.contains("TIMESTAMP"))  
								    {
								    	insPS.setTimestamp(i, joinQueryRS.getTimestamp(i));
								    }
								   
							    }
							}
							  
							    }
							
						 insPS.addBatch();
							    key++;
					}
//					System.out.println("htTa ======>"+htTa);
//					joinQueryRS=ps1.executeQuery(JoinSQLQuery);
//					boolean createCode = false;
//		    		String dimSql = "";
//					while(joinQueryRS.next()){
//
//						
//						for (int i=1; i<=columnCount; i++) {
//						
//							for (int k=1; k<=joinColumnHT.size(); k++) {
//								int tarIndex=joinQueryRS.findColumn(tarMapCol[k-1]);
//								String tarTableColumn = String.valueOf(joinColumnHT.get(k-1)).split("=")[0];
//						    	String tarjoinColumn = tarTableColumn.split("\\.")[1];
//								String srcTableColumn = String.valueOf(joinColumnHT.get(k-1)).split("=")[1];
//						    	String srcjoinColumn = srcTableColumn.split("\\.")[1];
////							   
//							    if(i==tarIndex)
//							    {
//							    	int insIndex=joinQueryRS.findColumn("COLUMN_"+k);
//							    	
//							    	String decodeColDtype=resMdata.getColumnTypeName(insIndex);
//							    	
//							    	if(decodeColDtype.contains("VARCHAR")||decodeColDtype.contains("CHAR"))
//								    {
//								    	
//								    	insPS.setString(i, joinQueryRS.getString("COLUMN_"+k));
//								    	
//								    }else if(decodeColDtype.contains("NUMBER"))   
//								    {
//								    	insPS.setDouble(i, joinQueryRS.getDouble("COLUMN_"+k));
//								    }else if(decodeColDtype.contains("DATE"))  
//								    {
//								    	insPS.setDate(i, joinQueryRS.getDate("COLUMN_"+k));
//								    }else if(decodeColDtype.contains("TIMESTAMP"))  
//								    {
//								    	insPS.setTimestamp(i, joinQueryRS.getTimestamp("COLUMN_"+k));
//								    }
//							    	
//							    	
//							    }else if(i==periodColFillindex)////////// For Filling Period Column
//							    {
////							    	System.out.println(i+ " periodColFillindex"+periodColFillindex+" (String)rowWid4period.get(j) "+(String)rowWid4period.get(j)+" j "+j);
//							    	insPS.setString(i, (String)rowWid4period.get(j));
//							    }else{
//							    	String tempDType=(String)dataTypeHT.get(i-1);	 
//							    	if(resMdata.getColumnName(i).equalsIgnoreCase(tarjoinColumn)){
////							    		System.out.println("sdsdsdsdsdsds"+joinQueryRS.getString(i));
//							    		
////							    		if(createCode){
//							    			
//							    			
//							    			
////							    		}else{
//							    			if(tempDType.contains("VARCHAR")||tempDType.contains("CHAR"))
//										    {
//									    		insPS.setString(i, joinQueryRS.getString(i));
//										    	
//										    }else if(tempDType.contains("NUMBER"))   
//										    {
//										    	insPS.setDouble(i, joinQueryRS.getDouble(i));
//										    	
//										    }else if(tempDType.contains("DATE"))  
//										    {
//										    	insPS.setDate(i, joinQueryRS.getDate(i));
//										    	
//										    }else if(tempDType.contains("TIMESTAMP"))  
//										    {
//										    	insPS.setTimestamp(i, joinQueryRS.getTimestamp(i));
//										    	
//										    }
////							    		}
//							    	}else{
////							    		System.out.println("other=-=-=-=->");
////							    		if(createCode){
////							    			gettingDim(columnPropertyHT,resMdata,dataTypeHT,i,conn,heirCode,String.valueOf(rowWid4period.get(j)),orgSrcFactTableNM,codeCombiColumnName,periodClmn,
////								    				dbConn,insPS,colPropHT,createCode,srcjoinColumn,dimSql,key);
////							    		}
//							    		if(htTa.size()!=0){ 
//							    			
//							    			
//							    			Hashtable Code = ( Hashtable)htTa.get(joinQueryRS.getString(tarjoinColumn));
//							    			if(Code!=null){
//								    		createCode = Boolean.valueOf(String.valueOf(Code.get("CodeCombinationFlag")));
//								    		dimSql = String.valueOf(Code.get("segmentSql"));
//							    			}else{
//							    				createCode = false;
//							    			}
//							    		}else {
//							    			createCode = false;
//										}
////							    		System.out.println("join=-=-=-=->");
//							    		if(createCode && colPropHT.containsValue(resMdata.getColumnName(i)) 
//												 && String.valueOf(columnPropertyHT.get(resMdata.getColumnName(i))).equalsIgnoreCase("Measure")){
////											 for(int b=0;b<rowWid4period.size();b++){
//											 String measureSQL = "select sum(gl."+resMdata.getColumnName(i)+") from "+orgSrcFactTableNM+" gl, ("+dimSql+") cc where "
//													 +"gl."+srcjoinColumn+"="+"cc."+codeCombiColumnName+" and gl."+periodClmn+" = "+String.valueOf(rowWid4period.get(j))+" group by gl."+periodClmn;
//											 System.out.println("measureSQL---------------"+measureSQL);
//											 PreparedStatement ps = conn.prepareStatement(measureSQL);
//											 ResultSet rs = ps.executeQuery();
//											 
//											 while(rs.next()){
////												 String tempDType=(String)dataTypeHT.get(i-1);
////										    		System.out.println("rs.getString(1)---------------"+rs.getString(1));
//										    if(tempDType.contains("VARCHAR")||tempDType.contains("CHAR"))
//										    {
//										    	
////										    	System.out.println("joinedDataRS.getString(i)---------------"+joinedDataRS.getString(i));
//										    	insPS.setString(i, rs.getString(1));
//										    	
//										    }else if(tempDType.contains("NUMBER"))   
//										    {
//										    	insPS.setDouble(i, rs.getDouble(1));
//										    }else if(tempDType.contains("DATE"))  
//										    {
//										    	insPS.setDate(i, rs.getDate(1));
//										    }else if(tempDType.contains("TIMESTAMP"))  
//										    {
//										    	insPS.setTimestamp(i, rs.getTimestamp(1));
//										    }
//											 }
//											 createCodeCombination = false;
//										 }else{
//							    		
//								    	 
//								    	if(tempDType.contains("VARCHAR")||tempDType.contains("CHAR"))
//									    {
//								    		
//									    	insPS.setString(i, joinQueryRS.getString(i));
//									    	
//									    }else if(tempDType.contains("NUMBER"))   
//									    {
//									    	insPS.setDouble(i, joinQueryRS.getDouble(i));
//									    	
//									    	
//									    }else if(tempDType.contains("DATE"))  
//									    {
//									    	insPS.setDate(i, joinQueryRS.getDate(i));
//									    	
//									    	
//									    }else if(tempDType.contains("TIMESTAMP"))  
//									    {
//									    	insPS.setTimestamp(i, joinQueryRS.getTimestamp(i));
//									    	
//									    	
//									    }
//							    	}
//							    	}
//							    }
//							}
//							  
//							    }
//							
//						 insPS.addBatch();
//							    key++;
//					
//					}
					
					insPS.executeBatch();
					
					
					
				}
				System.out.println("All Join Data are inserted into temp "+TempDimtable+" Table");
			
		}catch(Exception e)
		{
			tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
			e.printStackTrace();
			resHt.put(0, "");
			resHt.put(1, tableLevelstatus);
			
			return resHt;//Returns and Create a temp table after joins FIRST JOIN
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		resHt.put(0, TempDimtable);
		resHt.put(1, tableLevelstatus);
		
		return resHt;//Returns and Create a temp table after joins FIRST JOIN
	}
	
	
	public Hashtable factAndDimTableJoinsLater(String XMLdir,String configFile,Connection dbConn,Hashtable rowWid4period,
			Hashtable joinColumnHT,String facttableName,String TempDimtable,String periodClmn,String originalDimTableName,
			Hashtable uniCols4CrtetempTableHT,String heirCode,String tableLevelstatus,String targetfacttableName,
			String orgSrcFactTableNM,String GenMode,String tableNo,Document xmlDoc,Hashtable columnPropertyHT,
			Document configDoc,Hashtable measureColumnHT, Hashtable SWjoinColumnHT,String dimCodeColumn,Hashtable uniColsFunc4CrtetempTableHT,Hashtable srcConnTempTablesHT)	//code change Vishnu 26Apr2014
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		 String consJoinClmn="";
		 String[] srcMapCol=new String[joinColumnHT.size()];
		 String[] tarMapCol=new String[joinColumnHT.size()];
		 
		 int srcindex=0;
		 int tarindex=0;
		 
		 Hashtable changeTableNmtoTempNameHT = new Hashtable<>();
		 
		 String decodeColmn="";
		 int decodeColID=1;
		 Hashtable resHt=new Hashtable<>();
		 
		 String TempDimtable1="";
		try{
			Connection conn = Globals.getDBConnection("DW_Connection");		//code change Vishnu 26Apr2014
			Node hierNd = Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", heirCode);
			NodeList hierNdList = hierNd.getChildNodes();
			Node rootNd = null;
			Hashtable segmentHT = new Hashtable<>();
			
			int segmentCount = 0;
			if(!Globals.getAttrVal4AttrName(hierNd, "Number_of_Segment").equals("")){
				segmentCount=Integer.parseInt(Globals.getAttrVal4AttrName(hierNd, "Number_of_Segment"));
			}
			for(int m=0;m<hierNdList.getLength();m++){
				if(hierNdList.item(m).getNodeType() == Node.ELEMENT_NODE){
					if(hierNdList.item(m).getNodeName().equals("RootLevel")){
					rootNd = hierNdList.item(m);
					}
				}
			}
//			Hashtable segmentHT = new Hashtable<>();
			Hashtable segmentAndCodeCombiColName = new Hashtable<>();
			String codeCombiColumnName = "";
			segmentAndCodeCombiColName = gettingSegmentValueAndCodeCombiColumnName(heirCode, xmlDoc, configDoc);
			codeCombiColumnName  = String.valueOf(segmentAndCodeCombiColName.get("CodeCombination"));
			segmentHT = (Hashtable) segmentAndCodeCombiColName.get("Segment");
			//end code change Vishnu 26Apr2014
			int t=0;
			String consSel = "";
			String consgroupby = "";
			Hashtable tableNameHT = new Hashtable<>();
//			if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
//			 {
//			 joinColStr=joinColStr.replaceAll(orgSrcFactTableNM, facttableName);//Change Gokul 21APR2014 INCLOAD
//			 }
//			consSel = "WC_FLEX_HIERARCHY_D.HIERARCHY_ID,WC_FLEX_HIERARCHY_D.HIER_CAT_CODE,";
//			consgroupby = "WC_FLEX_HIERARCHY_D.HIERARCHY_ID,WC_FLEX_HIERARCHY_D.HIER_CAT_CODE,";
//			tableNameHT.put(t, "WC_FLEX_HIERARCHY_D");
//			t=1;
			int columnCount1=0;
			
			 Hashtable srcMeasHT = new Hashtable<>();
			 Hashtable srcMeasColHT = new Hashtable<>();
			 
			 Hashtable srcMeasTemp2HT = new Hashtable<>();
			 Hashtable srcMeasColTemp2HT = new Hashtable<>();
			 String decode = "";
			
			  for (int i = 0; i < measureColumnHT.size(); i++) {
				  if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
					 {
					  if(!String.valueOf(measureColumnHT.get(i)).contains(facttableName))
					  measureColumnHT.put(i,String.valueOf(measureColumnHT.get(i)).replaceAll(orgSrcFactTableNM, facttableName));//Change Gokul 21APR2014 INCLOAD
					 }
				  String tableName = String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[0];
				  String columnName = String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[1];
				  if(!String.valueOf(columnPropertyHT.get(columnName)).equalsIgnoreCase("Period")){
				  if(!tableNameHT.containsValue(tableName)){
					  tableNameHT.put(t, tableName);
					  t++;
				  }
				  }
				  
				  if(String.valueOf(columnPropertyHT.get(columnName)).equalsIgnoreCase("Measure")){
					  String measure = String.valueOf(measureColumnHT.get(i)).split("=")[1];
					  if(uniColsFunc4CrtetempTableHT.get(measure)!=null){
						  measure = String.valueOf(uniColsFunc4CrtetempTableHT.get(measure));
					  }
					  consSel = consSel+"SUM("+measure+") AS "+columnName+",";
					  srcMeasHT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1]);
					  srcMeasColHT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[1]);
					  srcMeasTemp2HT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1]);
					  srcMeasColTemp2HT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[1]);
				  }else if(String.valueOf(columnPropertyHT.get(columnName)).equalsIgnoreCase("Period")){
					  String measure = facttableName+"."+periodClmn;
					  System.out.println("Period==>"+measure);
					  if(uniColsFunc4CrtetempTableHT.get(measure)!=null){
						  measure = String.valueOf(uniColsFunc4CrtetempTableHT.get(measure));
						  consSel = consSel+measure+" as "+periodClmn+",";
					  }else{
						  consSel = consSel+measure+",";
					  }
					  System.out.println("Period consSel==>"+consSel);
					  consgroupby = consgroupby+facttableName+"."+periodClmn+",";
					  srcMeasHT.put(i, facttableName+"."+periodClmn);
					  srcMeasColHT.put(i, periodClmn);
					  srcMeasTemp2HT.put(i, facttableName+"."+periodClmn);
					  srcMeasColTemp2HT.put(i, periodClmn);
				  }else if(String.valueOf(columnPropertyHT.get(columnName)).equalsIgnoreCase("Attribute")){
					  String attribute = String.valueOf(measureColumnHT.get(i)).split("=")[1];
					  if(uniColsFunc4CrtetempTableHT.get(attribute)!=null){
						  attribute = String.valueOf(uniColsFunc4CrtetempTableHT.get(attribute));
						  consSel = consSel+attribute+" as "+String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[1]+",";
					  }else{
						  consSel = consSel+attribute+",";
						  
					  }
					  
					  consgroupby = consgroupby+String.valueOf(measureColumnHT.get(i)).split("=")[1]+",";
					  srcMeasHT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1]);
					  srcMeasColHT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[1]);
					  srcMeasTemp2HT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1]);
					  srcMeasColTemp2HT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[1]);
					  
				  }else{
					  String attribute = String.valueOf(measureColumnHT.get(i)).split("=")[1];
					  if(uniColsFunc4CrtetempTableHT.get(attribute)!=null){
						  attribute = String.valueOf(uniColsFunc4CrtetempTableHT.get(attribute));
						  consSel = consSel+attribute+" as "+String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[1]+",";
					  }else{
						  consSel = consSel+attribute+",";
					  }
					  
					  consgroupby = consgroupby+String.valueOf(measureColumnHT.get(i)).split("=")[1]+",";
					  srcMeasHT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1]);
					  srcMeasColHT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[1]);
					  srcMeasTemp2HT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1]);
					  srcMeasColTemp2HT.put(i, String.valueOf(measureColumnHT.get(i)).split("=")[1].split("\\.")[1]);
				  }
				  columnCount1++;
			}
			  System.out.println("srcMeasHT First==>"+srcMeasHT);
			  String joinStr = "";
			  consgroupby = consgroupby.substring(0, consgroupby.length()-1);
			  consSel = consSel.substring(0, consSel.length()-1);
			String tableNames = "";	
			
			
			int c = 0;
			
			Hashtable index2Remove = new Hashtable<>();
			
			  for(int i=0;i<tableNameHT.size();i++){
				  System.out.println("tableNameHT==>"+tableNameHT.get(i));
				  if(facttableName.equals(String.valueOf(tableNameHT.get(i)))){
					  tableNames = tableNames+String.valueOf(tableNameHT.get(i))+",";
				  }else {
					  tableNames = tableNames+String.valueOf(tableNameHT.get(i))+",";
					  System.out.println("SWjoinColumnHT==>"+SWjoinColumnHT);
						System.out.println("SWjoinColumnHT.size()==>"+SWjoinColumnHT.size());
					  for(int l=0;l<SWjoinColumnHT.size();l++){
						  Hashtable tempHT = new Hashtable<>();
						  tempHT = (Hashtable)SWjoinColumnHT.get(l);
						  System.out.println("tempHT==>"+tempHT);
						  System.out.println("conssdel==>"+String.valueOf(tempHT.get("Source_Column")));
						  String srcColumn = String.valueOf(tempHT.get("Source_Column"));
						  String tarColumn = String.valueOf(tempHT.get("Target_Column"));
						  if(tempHT.get("Source_Column_Function")!=null){
							  if(!String.valueOf(tempHT.get("Source_Column_Function")).equals("")){
								  srcColumn = String.valueOf(tempHT.get("Source_Column_Function"));
							  }
						  }
						  if(tempHT.get("Target_Column_Function")!=null){
							  if(!String.valueOf(tempHT.get("Target_Column_Function")).equals("")){
								  tarColumn = String.valueOf(tempHT.get("Target_Column_Function"));
							  }
						  }
						  if(srcColumn.contains(String.valueOf(tableNameHT.get(i))) || 
								  tarColumn.contains(String.valueOf(tableNameHT.get(i)))){
							  if(String.valueOf(tempHT.get("Join_type")).equals("Column")){
								  joinStr = srcColumn+String.valueOf(tempHT.get("Join_Operator"))+
										  tarColumn+" AND ";
							  }else if(String.valueOf(tempHT.get("Join_type")).equals("Value")){
								  if(String.valueOf(tempHT.get("Join_Operator")).equalsIgnoreCase("Between")){
									  joinStr = srcColumn+String.valueOf(tempHT.get("Join_Operator"))+
											  tarColumn.split("#~#")[0]+" AND "+tarColumn.split("#~#")[1]+" AND ";
								  }else{
									  joinStr = srcColumn+String.valueOf(tempHT.get("Join_Operator"))+
											  tarColumn+" AND ";
								  }
							  }
							  if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
								 {
								  if(!joinStr.contains(facttableName))
								  joinStr = joinStr.replaceAll(orgSrcFactTableNM, facttableName);//Change Gokul 21APR2014 INCLOAD
								 }
							  changeTableNmtoTempNameHT.put(c, String.valueOf(tableNameHT.get(i)));
							  index2Remove.put(c, l);
							  c++;
//							  SWjoinColumnHT.remove(l);
//							  if(SWjoinColumnHT.get(l+1)!=null)
//							  SWjoinColumnHT.put(l, SWjoinColumnHT.get(l+1));
						  }
					  }
				  }
			  }
			  if(!joinStr.isEmpty()){
//				  joinStr = joinStr.substring(0, joinStr.length()-4);
			  }
			  tableNames = tableNames.substring(0, tableNames.length()-1);
			   
			  for(int i=0;i<index2Remove.size();i++){
				  SWjoinColumnHT.remove(index2Remove.get(i));
			  }
			  int t1=0;
			  System.out.println("SWjoinColumnHT==>"+SWjoinColumnHT);
			  Enumeration e = SWjoinColumnHT.keys();
			  Hashtable tempHT = new Hashtable<>();
			  while(e.hasMoreElements()){
				  int key = Integer.valueOf(String.valueOf(e.nextElement()));
				  System.out.println("key==>"+key);
				  System.out.println("SWjoinColumnHT==>"+SWjoinColumnHT.get(key));
				  tempHT.put(t1, SWjoinColumnHT.get(key));
				  t1++;
			  }
			  SWjoinColumnHT.clear();
			  for(int i=0;i<tempHT.size();i++){
				  SWjoinColumnHT.put(i, tempHT.get(i));
			  }
			  System.out.println("SWjoinColumnHT First==>"+SWjoinColumnHT);
			String sql = "";
			boolean createCodeCombination = false;
			Hashtable segmentDetailHT = new Hashtable<>();
			PreparedStatement prestat = null;
			Hashtable htTa = new Hashtable<>();
			Hashtable nodeHT=new Hashtable<>();	//code change Vishnu 26Apr2014
			Hashtable nodevalueandtypeHT=new Hashtable<>();
			//getting node info in nodeHT for the Hierarchy
			Globals.processUpDowninDB(rootNd, nodeHT, 1, nodevalueandtypeHT);
			
			
			
			String hierSql = "Select * from WC_FLEX_HIERARCHY_D Where HIERARCHY_ID = "+heirCode;
			
			PreparedStatement ps = conn.prepareStatement(hierSql);
			ResultSet rs = ps.executeQuery();
			String nodeValue = "";
			int m=0;
			String segTableMName = "";
			Hashtable segHT = new Hashtable();
			while (rs.next()) {
				if(!dimCodeColumn.equals("")){
				nodeValue = rs.getString(dimCodeColumn); //03MAY14
				}else{
					nodeValue = rs.getString("Hier20_Code");
				}
				 NodeList rootNdList = rootNd.getChildNodes();
//				 
				   for(int b=0;b<nodeHT.size();b++){
					   Hashtable ht = (Hashtable) nodeHT.get(b);
					   if(!String.valueOf(ht.get("NodeName")).equals("ID") && !String.valueOf(ht.get("NodeName")).equals("RootLevel_Name")){
//							 
							if(String.valueOf(ht.get("Type")).equalsIgnoreCase("Data")){
								if(String.valueOf(ht.get("Data_SubType")).equalsIgnoreCase("Segment") || String.valueOf(ht.get("Data_SubType")).equalsIgnoreCase("null") 
										|| String.valueOf(ht.get("Data_SubType")).equalsIgnoreCase("fromData4DirectSQL")){
								String primarySeg = String.valueOf(ht.get("Primary_Segment"));
								   String segNo = primarySeg.substring(primarySeg.lastIndexOf("_")+1);
//								   
								   if(String.valueOf(ht.get("seg"+segNo+"_Code")).split(";")[0].equals(nodeValue)){
									   createCodeCombination = Boolean.valueOf(String.valueOf(ht.get("Create_Code_Combination4Data")));
									   String segmentValue = String.valueOf(ht.get("seg"+segNo+"_Code")).split(";")[0];
									   
									   if(!String.valueOf(segmentHT.get("Segment_"+segNo)).equals("null")){
										   String temp = String.valueOf(segmentHT.get("Segment_"+segNo));
										   segmentValue = temp+"~"+segmentValue;
									   }
									   segmentHT.put("Segment_"+segNo, segmentValue);
									   
									   break;
								   }
							}else if(String.valueOf(ht.get("Data_SubType")).equalsIgnoreCase("DirectSQL")){
//								isDirectSQL = true;
								String segSQL = String.valueOf(ht.get("Custom_SQL"));
								PreparedStatement ps1 = conn.prepareStatement(segSQL);
								ResultSet rs1 = ps1.executeQuery();
								String segValue = String.valueOf(segmentHT.get(String.valueOf(ht.get("Primary_Segment"))));
//								System.out.println("segValue==>"+segValue);
								while (rs1.next()) {
									if(!segValue.contains(rs1.getString(1))){
									segValue = segValue+"~"+ rs1.getString(1);
									segmentHT.put(String.valueOf(ht.get("Primary_Segment")), segValue);
									}
									
								}
								rs1.close();
								ps1.close();
							}
							}else{
								if(String.valueOf(ht.get("value")).equals(nodeValue)){
									createCodeCombination = false;
							    	segmentDetailHT.put("Result", "false");
									 break;
								}
							}
						}
				   }
				   
			}
			Hashtable codeSqlHT=Globals.Segment(segmentHT, conn, heirCode,true);
			   sql = String.valueOf(codeSqlHT.get(0));
			   if(!consgroupby.contains(String.valueOf(codeSqlHT.get(1))))
			   consgroupby = consgroupby+String.valueOf(codeSqlHT.get(1));
			   if(!consSel.contains(String.valueOf(codeSqlHT.get(1))))
			   consSel = consSel+String.valueOf(codeSqlHT.get(1));
			   segHT = (Hashtable)codeSqlHT.get(3);
			   segTableMName = String.valueOf(codeSqlHT.get(2));
			   
			String segColuName = "";
			for(int i=0;i<segHT.size();i++){
				if(!srcMeasHT.containsValue(segTableMName+"."+String.valueOf(segHT.get(i)))){
				srcMeasHT.put(srcMeasHT.size(), segTableMName+"."+String.valueOf(segHT.get(i)));
				srcMeasColHT.put(srcMeasColHT.size(), String.valueOf(segHT.get(i))); //04MAY14
				
				
				
				//04MAY14
				srcMeasTemp2HT.put(srcMeasTemp2HT.size(), segTableMName+"."+String.valueOf(segHT.get(i)));
				srcMeasColTemp2HT.put(srcMeasColTemp2HT.size(), String.valueOf(segHT.get(i))); 
				}
				segColuName = String.valueOf(segHT.get(i));
			}
			
			//04MAY14
			srcMeasTemp2HT.put(srcMeasTemp2HT.size(), "WC_FLEX_HIERARCHY_D.HIER20_CODE");
			srcMeasTemp2HT.put(srcMeasTemp2HT.size(), "WC_FLEX_HIERARCHY_D.HIERARCHY_ID");
			srcMeasTemp2HT.put(srcMeasTemp2HT.size(), "WC_FLEX_HIERARCHY_D.HIER_CAT_CODE");
			srcMeasTemp2HT.put(srcMeasTemp2HT.size(), "WC_FLEX_HIERARCHY_D.NODE_TYPE");
			
			//04MAY14
			srcMeasColTemp2HT.put(srcMeasColTemp2HT.size(), "HIER20_CODE");
			srcMeasColTemp2HT.put(srcMeasColTemp2HT.size(), "HIERARCHY_ID");
			srcMeasColTemp2HT.put(srcMeasColTemp2HT.size(), "HIER_CAT_CODE");
			srcMeasColTemp2HT.put(srcMeasColTemp2HT.size(), "NODE_TYPE");
			
//			System.out.println("ddddd=>"+srcMeasHT);
			

			String oldtempDimTable=TempDimtable;
			 
			
//			 /////////
				
			 
			    int columnCount = srcMeasHT.size();//Code Change Gokul 16APR2014
			    
			    String consQ4Ins="",conColsIns="";
			    
			    for(int i=1;i<=columnCount;i++)
			    {
			    	String tableNamedotColNM=(String)srcMeasHT.get(i-1);
			    
			    	tableNamedotColNM=tableNamedotColNM.replace(originalDimTableName, oldtempDimTable);
			    	
			    	String[] tabNMColNM=tableNamedotColNM.split("\\.");
			    	
			    	if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
			    	{
			    	
			    	tableNamedotColNM=tableNamedotColNM.replace(orgSrcFactTableNM, facttableName);//Change Gokul 21APR2014 INCLOAD
//			    	aaaaaa
			    	}
			    	
			    	if(i!=columnCount)
			    	{
			    		conColsIns=conColsIns+tabNMColNM[1]+",";
			    		consQ4Ins=consQ4Ins+"?,";
			    	}else
			    	{
			    		conColsIns=conColsIns+tabNMColNM[1];
			    		consQ4Ins=consQ4Ins+"?";	
			    	}
			    }
			    
			    Hashtable joinHT = new Hashtable<>();		//code change Vishnu 26Apr2014
				String tabname = "";
				for(int i=0;i<joinColumnHT.size();i++){
					String srcTableColumn = String.valueOf(joinColumnHT.get(i)).split("=")[1];
					joinHT.put(i, srcTableColumn.split("\\.")[1]);
					tabname = srcTableColumn.split("\\.")[0];
				}    
			    
			   
			    
				 
				 
				 
					//If Current table is getting Error Skip it and Proceed next Table 16APR2014
					if(TempDimtable==null||TempDimtable.equalsIgnoreCase(""))
					{
						return null;
					}
					int columnCount4newTemp = srcMeasTemp2HT.size();
					String consQ4Ins4NewTemp = "",conColsIns1="";
					 for(int i=1;i<=columnCount4newTemp;i++)
					    {
					    	String tableNamedotColNM=(String)srcMeasTemp2HT.get(i-1);
					    
					    	tableNamedotColNM=tableNamedotColNM.replace(originalDimTableName, oldtempDimTable);
					    	
					    	String[] tabNMColNM=tableNamedotColNM.split("\\.");
					    	
					    	if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
					    	{
					    	
					    	tableNamedotColNM=tableNamedotColNM.replace(orgSrcFactTableNM, facttableName);//Change Gokul 21APR2014 INCLOAD

					    	}
					    	
					    	
					    	
					    	if(i!=columnCount4newTemp)
					    	{
					    		
//					    		conColsIns=conColsIns+tableNamedotColNM+",";//Code Change Gokul 16APR2014//for Setting tablename.column name
					    		conColsIns1=conColsIns1+tabNMColNM[1]+",";
					    		consQ4Ins4NewTemp=consQ4Ins4NewTemp+"?,";
					    	}else
					    	{
//					    		conColsIns=conColsIns+tableNamedotColNM;
					    		conColsIns1=conColsIns1+tabNMColNM[1];
					    		consQ4Ins4NewTemp=consQ4Ins4NewTemp+"?";	
					    	}
					    }
					
			String sqltojoin="";
			    String consSelnew = "";
				String newSqltoJoin = "";
				
				//Prepare Statement 4 INS
//				PreparedStatement insPS=dbConn.prepareStatement(insSQL4Joinres);
				Hashtable tempTableNameHT = new Hashtable<>();
				if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact")){
					
					TempDimtable1=TempDimtable+","+facttableName;//Join Two table Columns As Single table Column
//					 //////////
					System.out.println("srcMeasHT==>"+srcMeasHT);
					//Creating a temp table from Structure of a Original table
					 Hashtable resHT= createAtempTablefromExisting(XMLdir, configFile, dbConn, facttableName, TempDimtable,srcMeasHT,heirCode, tableLevelstatus,targetfacttableName,tableNo); //04MAY14
//					 
					 TempDimtable1=(String)resHT.get(0);
					 tableLevelstatus=(String)resHT.get(2);
					 Hashtable dataTypeHT=(Hashtable)resHT.get(1);
//					 tempTableNameHT.put(j, TempDimtable1);
//					 System.out.println("Temp Table : "+j+"===>"+TempDimtable1);
						//If Current table is getting Error Skip it and Proceed next Table 16APR2014
						if(TempDimtable==null||TempDimtable.equalsIgnoreCase(""))
						{
							return null;
						}
						 String insSQL4Joinres="INSERT INTO "+TempDimtable1+" ("+conColsIns+") VALUES ("+consQ4Ins+")";
							PreparedStatement insPS=dbConn.prepareStatement(insSQL4Joinres);
							
					String JointableColumn = String.valueOf(joinColumnHT.get(0)).split("=")[1];
					 if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
						 {
						   if(!JointableColumn.contains(facttableName))
							   JointableColumn = JointableColumn.replaceAll(orgSrcFactTableNM, facttableName);//Change Gokul 21APR2014 INCLOAD
						 }
					 joinStr = joinStr.substring(0,joinStr.length()-4);
					sqltojoin = "SELECT "+consSel+" FROM "+tableNames+",("+sql+") cc WHERE "+JointableColumn+
							"=cc."+codeCombiColumnName+" AND "+joinStr+" GROUP BY "+consgroupby;
					System.out.println("Full SQL for Join(JoinSQLQuery)(SQL+Condition) : "+sqltojoin);
					PreparedStatement JoinPS = dbConn.prepareStatement(sqltojoin);
					ResultSet JoinRS = JoinPS.executeQuery();
					ResultSetMetaData resMdata= JoinRS.getMetaData();

					while (JoinRS.next()) {
						for (int i=1; i<=srcMeasHT.size(); i++) { // 04MAY14
							if(!consSelnew.contains(String.valueOf(srcMeasColHT.get(i-1))))
							consSelnew = consSelnew+TempDimtable1+"."+String.valueOf(srcMeasColHT.get(i-1))+",";
								int insIndex=JoinRS.findColumn(String.valueOf(srcMeasColHT.get(i-1)));
								String decodeColDtype=resMdata.getColumnTypeName(insIndex);
								if(decodeColDtype.contains("VARCHAR")||decodeColDtype.contains("CHAR"))
								    {
								    	
								    	insPS.setString(i, JoinRS.getString(insIndex));
								    	
								    }else if(decodeColDtype.contains("NUMBER"))   
								    {
								    	insPS.setDouble(i, JoinRS.getDouble(insIndex));
								    }else if(decodeColDtype.contains("DATE"))  
								    {
								    	insPS.setDate(i, JoinRS.getDate(insIndex));
								    }else if(decodeColDtype.contains("TIMESTAMP"))  
								    {
								    	insPS.setTimestamp(i, JoinRS.getTimestamp(insIndex));
								    }
						}
						insPS.addBatch();
					
				}
					insPS.executeBatch();

				String consSelTemp2 = "";

				for (int i=1; i<=srcMeasColHT.size(); i++) {

					if (srcMeasColHT.get(i-1) != null){
						System.out.println("columnPropertyHT.get(String.valueOf(srcMeasColHT.get(d)))"+columnPropertyHT.get(String.valueOf(srcMeasColHT.get(i-1))));
						
						if(String.valueOf(columnPropertyHT.get(String.valueOf(srcMeasColHT.get(i-1)))).equals("Attribute")){
							
								Hashtable temp = getDatatypefromDBtable(TempDimtable1+"."+String.valueOf(srcMeasColHT.get(i-1)), dbConn, heirCode, tableLevelstatus, targetfacttableName);
										if(String.valueOf(temp.get(1)).contains("VARCHAR")||String.valueOf(temp.get(1)).contains("CHAR"))
									    {
											if(!consSelTemp2.contains("DECODE("+String.valueOf(srcMeasColHT.get(i-1))+",null,HIER20_CODE,"+String.valueOf(srcMeasColHT.get(i-1))+") as "+String.valueOf(srcMeasColHT.get(i-1)))){
											consSelTemp2 = consSelTemp2+ "DECODE("+String.valueOf(srcMeasColHT.get(i-1))+",null,HIER20_CODE,"+String.valueOf(srcMeasColHT.get(i-1))+") as "+String.valueOf(srcMeasColHT.get(i-1))+",";
											}
									    }else if(String.valueOf(temp.get(1)).contains("NUMBER"))   
									    {
									    	if(!consSelTemp2.contains("DECODE("+String.valueOf(srcMeasColHT.get(i-1))+",null,HIER20_CODE,"+String.valueOf(srcMeasColHT.get(i-1))+") as "+String.valueOf(srcMeasColHT.get(i-1)))){
												consSelTemp2 = consSelTemp2+ "DECODE("+String.valueOf(srcMeasColHT.get(i-1))+",null,0,"+String.valueOf(srcMeasColHT.get(i-1))+") as "+String.valueOf(srcMeasColHT.get(i-1))+",";
											}
									    }	
							
						}else{
							if(!consSelTemp2.contains(String.valueOf(srcMeasColHT.get(i-1))))
								consSelTemp2 = consSelTemp2+TempDimtable1+"."+String.valueOf(srcMeasColHT.get(i-1))+",";
						}
					}
					
//					}
				
				}
				
				consSelTemp2 = consSelTemp2.substring(0, consSelTemp2.length() - 1);
					newSqltoJoin = "select "+consSelTemp2+",WC_FLEX_HIERARCHY_D.hier20_code, WC_FLEX_HIERARCHY_D.hierarchy_id,WC_FLEX_HIERARCHY_D.HIER_CAT_CODE,WC_FLEX_HIERARCHY_D.NODE_TYPE"
							+" FROM WC_FLEX_HIERARCHY_D inner join "+TempDimtable1+" on WC_FLEX_HIERARCHY_D.HIER20_CODE = "+TempDimtable1+"."+segColuName+" where WC_FLEX_HIERARCHY_D.node_type='Code' and wc_flex_hierarchy_d.hierarchy_id = "+heirCode;
					System.out.println("Select SQL InsertFact for second Temp table : "+newSqltoJoin);
					Hashtable resHT2= createAtempTablefromExisting(XMLdir, configFile, dbConn, facttableName, TempDimtable,srcMeasTemp2HT,heirCode, tableLevelstatus,targetfacttableName,tableNo); //04MAY14
					 TempDimtable1=(String)resHT2.get(0);
					 tableLevelstatus=(String)resHT2.get(2);
					 Hashtable dataTypeHT1=(Hashtable)resHT2.get(1);
					String newInsertSQL = "";
					tempTableNameHT.put(0, TempDimtable1);
					System.out.println("Temp Table : "+(0)+"===>"+TempDimtable1);
					newInsertSQL = "INSERT INTO "+TempDimtable1+" ("+conColsIns1+") VALUES ("+consQ4Ins4NewTemp+")";
//					System.out.println("Second Temp Insert====>"+newInsertSQL);
					PreparedStatement newinsPS=dbConn.prepareStatement(newInsertSQL);
				
				
	PreparedStatement newPS4Join = dbConn.prepareStatement(newSqltoJoin);
	ResultSet newRS = newPS4Join.executeQuery();
	ResultSetMetaData newresMdata = newRS.getMetaData();
	int l=0;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	System.out.println("start insert===>"+sdf.format(new Date()));
	while (newRS.next()) {
		
		for (int i=1; i<=srcMeasColTemp2HT.size(); i++) {

			if(String.valueOf(srcMeasColTemp2HT.get(i-1)).equalsIgnoreCase("HIERARCHY_ID")){
				newinsPS.setDouble(i, newRS.getDouble(i));
			}else if(String.valueOf(srcMeasColTemp2HT.get(i-1)).equalsIgnoreCase("HIER_CAT_CODE")){
				newinsPS.setString(i, newRS.getString(i));
			}else if(String.valueOf(srcMeasColTemp2HT.get(i-1)).equalsIgnoreCase("NODE_TYPE")){
				newinsPS.setString(i, newRS.getString(i));
			}
//			else if(String.valueOf(srcMeasColTemp2HT.get(i-1)).equalsIgnoreCase(periodClmn)){
//				newinsPS.setString(i, String.valueOf(rowWid4period.get(j)));
//			}
			else{

				int insIndex=newRS.findColumn(String.valueOf(srcMeasColTemp2HT.get(i-1)));
				String decodeColDtype=newresMdata.getColumnTypeName(insIndex);
				if(decodeColDtype.contains("VARCHAR")||decodeColDtype.contains("CHAR"))
				    {
				    	
					newinsPS.setString(i, newRS.getString(insIndex));
				    	
				    }else if(decodeColDtype.contains("NUMBER"))   
				    {
				    	newinsPS.setDouble(i, newRS.getDouble(insIndex));
				    }else if(decodeColDtype.contains("DATE"))  
				    {
				    	newinsPS.setDate(i, newRS.getDate(insIndex));
				    }else if(decodeColDtype.contains("TIMESTAMP"))  
				    {
				    	newinsPS.setTimestamp(i, newRS.getTimestamp(insIndex));
				    }
			}
		}
		newinsPS.addBatch();
		l++;
		if(l%1000==0){
			System.out.println("----Insert Batch------");
			newinsPS.executeBatch();
			l=0;
		}
					}
	System.out.println("End insert===>"+sdf.format(new Date()));
	newinsPS.executeBatch();
				}else{
					for(int j=0;j<rowWid4period.size();j++){
					TempDimtable1=TempDimtable+","+facttableName;//Join Two table Columns As Single table Column
//					 //////////
					System.out.println("srcMeasHT==>"+srcMeasHT);
					//Creating a temp table from Structure of a Original table
					 Hashtable resHT= createAtempTablefromExisting(XMLdir, configFile, dbConn, facttableName, TempDimtable,srcMeasHT,heirCode, tableLevelstatus,targetfacttableName,tableNo); //04MAY14
//					 
					 TempDimtable1=(String)resHT.get(0);
					 tableLevelstatus=(String)resHT.get(2);
					 Hashtable dataTypeHT=(Hashtable)resHT.get(1);
					 srcConnTempTablesHT.put(srcConnTempTablesHT.size(),TempDimtable1);
//					 tempTableNameHT.put(j, TempDimtable1);
//					 System.out.println("Temp Table : "+j+"===>"+TempDimtable1);
						//If Current table is getting Error Skip it and Proceed next Table 16APR2014
						if(TempDimtable==null||TempDimtable.equalsIgnoreCase(""))
						{
							return null;
						}
						 String insSQL4Joinres="INSERT INTO "+TempDimtable1+" ("+conColsIns+") VALUES ("+consQ4Ins+")";
							PreparedStatement insPS=dbConn.prepareStatement(insSQL4Joinres);
							System.out.println("First Temp Table Insert : "+insSQL4Joinres);
						 String JointableColumn = String.valueOf(joinColumnHT.get(0)).split("=")[1];
						 if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
							 {
							   if(!JointableColumn.contains(facttableName))
								   JointableColumn = JointableColumn.replaceAll(orgSrcFactTableNM, facttableName);//Change Gokul 21APR2014 INCLOAD
							 }
						sqltojoin = "SELECT "+consSel+" FROM "+tableNames+",("+sql+") cc WHERE "+JointableColumn+
								"=cc."+codeCombiColumnName+" AND "+joinStr+facttableName+"."+periodClmn+"="+String.valueOf(rowWid4period.get(j))+""+" GROUP BY "+consgroupby;
						System.out.println("Full SQL for Join(JoinSQLQuery)(SQL+Condition) : "+sqltojoin);
						PreparedStatement JoinPS = dbConn.prepareStatement(sqltojoin);
						ResultSet JoinRS = JoinPS.executeQuery();
						ResultSetMetaData resMdata= JoinRS.getMetaData();

						while (JoinRS.next()) {
							for (int i=1; i<=srcMeasHT.size(); i++) { // 04MAY14
								if(!consSelnew.contains(String.valueOf(srcMeasColHT.get(i-1))))
								consSelnew = consSelnew+TempDimtable1+"."+String.valueOf(srcMeasColHT.get(i-1))+",";
									int insIndex=JoinRS.findColumn(String.valueOf(srcMeasColHT.get(i-1)));
									String decodeColDtype=resMdata.getColumnTypeName(insIndex);
									if(decodeColDtype.contains("VARCHAR")||decodeColDtype.contains("CHAR"))
									    {
									    	
									    	insPS.setString(i, JoinRS.getString(insIndex));
									    	
									    }else if(decodeColDtype.contains("NUMBER"))   
									    {
									    	insPS.setDouble(i, JoinRS.getDouble(insIndex));
									    }else if(decodeColDtype.contains("DATE"))  
									    {
									    	insPS.setDate(i, JoinRS.getDate(insIndex));
									    }else if(decodeColDtype.contains("TIMESTAMP"))  
									    {
									    	insPS.setTimestamp(i, JoinRS.getTimestamp(insIndex));
									    }
							}
							insPS.addBatch();
						
					}
						insPS.executeBatch();

					String consSelTemp2 = "";

					for (int i=1; i<=srcMeasColHT.size(); i++) {

						if (srcMeasColHT.get(i-1) != null){
							System.out.println("columnPropertyHT.get(String.valueOf(srcMeasColHT.get(d)))"+columnPropertyHT.get(String.valueOf(srcMeasColHT.get(i-1))));
							
							if(String.valueOf(columnPropertyHT.get(String.valueOf(srcMeasColHT.get(i-1)))).equals("Attribute")){
								
									Hashtable temp = getDatatypefromDBtable(TempDimtable1+"."+String.valueOf(srcMeasColHT.get(i-1)), dbConn, heirCode, tableLevelstatus, targetfacttableName);
											if(String.valueOf(temp.get(1)).contains("VARCHAR")||String.valueOf(temp.get(1)).contains("CHAR"))
										    {
												if(!consSelTemp2.contains("DECODE("+String.valueOf(srcMeasColHT.get(i-1))+",null,HIER20_CODE,"+String.valueOf(srcMeasColHT.get(i-1))+") as "+String.valueOf(srcMeasColHT.get(i-1)))){
												consSelTemp2 = consSelTemp2+ "DECODE("+String.valueOf(srcMeasColHT.get(i-1))+",null,HIER20_CODE,"+String.valueOf(srcMeasColHT.get(i-1))+") as "+String.valueOf(srcMeasColHT.get(i-1))+",";
												}
										    }else if(String.valueOf(temp.get(1)).contains("NUMBER"))   
										    {
										    	if(!consSelTemp2.contains("DECODE("+String.valueOf(srcMeasColHT.get(i-1))+",null,HIER20_CODE,"+String.valueOf(srcMeasColHT.get(i-1))+") as "+String.valueOf(srcMeasColHT.get(i-1)))){
													consSelTemp2 = consSelTemp2+ "DECODE("+String.valueOf(srcMeasColHT.get(i-1))+",null,0,"+String.valueOf(srcMeasColHT.get(i-1))+") as "+String.valueOf(srcMeasColHT.get(i-1))+",";
												}
										    }	
								
							}else{
								if(!consSelTemp2.contains(String.valueOf(srcMeasColHT.get(i-1))))
									consSelTemp2 = consSelTemp2+TempDimtable1+"."+String.valueOf(srcMeasColHT.get(i-1))+",";
							}
						}
						
//						}
					
					}
					
					consSelTemp2 = consSelTemp2.substring(0, consSelTemp2.length() - 1);
					if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact")){
						newSqltoJoin = "select "+consSelTemp2+",WC_FLEX_HIERARCHY_D.hier20_code, WC_FLEX_HIERARCHY_D.hierarchy_id,WC_FLEX_HIERARCHY_D.HIER_CAT_CODE,WC_FLEX_HIERARCHY_D.NODE_TYPE"
								+" FROM WC_FLEX_HIERARCHY_D inner join "+TempDimtable1+" on WC_FLEX_HIERARCHY_D.HIER20_CODE = "+TempDimtable1+"."+segColuName+" where WC_FLEX_HIERARCHY_D.node_type='Code' and wc_flex_hierarchy_d.hierarchy_id = "+heirCode;
						System.out.println("Select SQL InsertFact for second Temp table : "+newSqltoJoin);
					}else{
						newSqltoJoin = "select "+consSelTemp2+",WC_FLEX_HIERARCHY_D.hier20_code, WC_FLEX_HIERARCHY_D.hierarchy_id,WC_FLEX_HIERARCHY_D.HIER_CAT_CODE,WC_FLEX_HIERARCHY_D.NODE_TYPE"
								+" FROM WC_FLEX_HIERARCHY_D left outer join "+TempDimtable1+" on WC_FLEX_HIERARCHY_D.HIER20_CODE = "+TempDimtable1+"."+segColuName+" where WC_FLEX_HIERARCHY_D.node_type='Code' and wc_flex_hierarchy_d.hierarchy_id = "+heirCode;
						System.out.println("Select SQL for second Temp table : "+newSqltoJoin);
					}
					Hashtable resHT2= createAtempTablefromExisting(XMLdir, configFile, dbConn, facttableName, TempDimtable,srcMeasTemp2HT,heirCode, tableLevelstatus,targetfacttableName,tableNo); //04MAY14
					 TempDimtable1=(String)resHT2.get(0);
					 tableLevelstatus=(String)resHT2.get(2);
					 Hashtable dataTypeHT1=(Hashtable)resHT2.get(1);
					String newInsertSQL = "";
					tempTableNameHT.put(j, TempDimtable1);
					System.out.println("Temp Table : "+(j+1)+"===>"+TempDimtable1);
					newInsertSQL = "INSERT INTO "+TempDimtable1+" ("+conColsIns1+") VALUES ("+consQ4Ins4NewTemp+")";
					srcConnTempTablesHT.put(srcConnTempTablesHT.size(),TempDimtable1);
//					System.out.println("Second Temp Insert====>"+newInsertSQL);
					PreparedStatement newinsPS=dbConn.prepareStatement(newInsertSQL);
					
					
		PreparedStatement newPS4Join = dbConn.prepareStatement(newSqltoJoin);
		ResultSet newRS = newPS4Join.executeQuery();
		ResultSetMetaData newresMdata = newRS.getMetaData();
		int l=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println("start insert===>"+sdf.format(new Date()));
		while (newRS.next()) {
			
			for (int i=1; i<=srcMeasColTemp2HT.size(); i++) {

				if(String.valueOf(srcMeasColTemp2HT.get(i-1)).equalsIgnoreCase("HIERARCHY_ID")){
					newinsPS.setDouble(i, newRS.getDouble(i));
				}else if(String.valueOf(srcMeasColTemp2HT.get(i-1)).equalsIgnoreCase("HIER_CAT_CODE")){
					newinsPS.setString(i, newRS.getString(i));
				}else if(String.valueOf(srcMeasColTemp2HT.get(i-1)).equalsIgnoreCase("NODE_TYPE")){
					newinsPS.setString(i, newRS.getString(i));
				}
				else if(String.valueOf(srcMeasColTemp2HT.get(i-1)).equalsIgnoreCase(periodClmn)){
					newinsPS.setString(i, String.valueOf(rowWid4period.get(j)));
				}
				else{

					int insIndex=newRS.findColumn(String.valueOf(srcMeasColTemp2HT.get(i-1)));
					String decodeColDtype=newresMdata.getColumnTypeName(insIndex);
					if(decodeColDtype.contains("VARCHAR")||decodeColDtype.contains("CHAR"))
					    {
					    	
						newinsPS.setString(i, newRS.getString(insIndex));
					    	
					    }else if(decodeColDtype.contains("NUMBER"))   
					    {
					    	newinsPS.setDouble(i, newRS.getDouble(insIndex));
					    }else if(decodeColDtype.contains("DATE"))  
					    {
					    	newinsPS.setDate(i, newRS.getDate(insIndex));
					    }else if(decodeColDtype.contains("TIMESTAMP"))  
					    {
					    	newinsPS.setTimestamp(i, newRS.getTimestamp(insIndex));
					    }
				}
			}
			newinsPS.addBatch();
			l++;
			if(l%1000==0){
				System.out.println("----Insert Batch------");
				newinsPS.executeBatch();
				l=0;
			}
						}
		
		System.out.println("End insert===>"+sdf.format(new Date()));
		newinsPS.executeBatch();
//		newinsPS.close();
//		newRS.close();
					}
					}
				
				TempDimtable=TempDimtable+","+facttableName;//Join Two table Columns As Single table Column
//				//Creating a temp table from Structure of a Original table
				 Hashtable resHT1= createAtempTablefromExisting(XMLdir, configFile, dbConn, facttableName, TempDimtable,srcMeasTemp2HT,heirCode, tableLevelstatus,targetfacttableName,tableNo);
//				 
				 TempDimtable=(String)resHT1.get(0);
				 tableLevelstatus=(String)resHT1.get(2);
				 
				 for(int i=0;i<tempTableNameHT.size();i++){
					 String sql1 = "insert into "+TempDimtable+" select * from "+String.valueOf(tempTableNameHT.get(i));
					 PreparedStatement newPS4Join = dbConn.prepareStatement(sql1);
					 System.out.println("SQL for last insert in temp: "+sql1);
					 newPS4Join.execute();
				 }
//				 tempTableNameHT.put(tempTableNameHT.size(), TempDimtable);
//				 System.out.println("Temp Table to delete: "+tempTableNameHT);
				
				System.out.println("All Join Data are inserted into temp "+TempDimtable+" Table");
			
//		}
		}
				catch(Exception e)
		{
			tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
			e.printStackTrace();
			resHt.put(0, "");
			resHt.put(1, tableLevelstatus);
			
			return resHt;//Returns and Create a temp table after joins FIRST JOIN
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		resHt.put(0, TempDimtable);
		resHt.put(1, tableLevelstatus);
		resHt.put(2, TempDimtable1);
		resHt.put(3, changeTableNmtoTempNameHT);
		
		return resHt;//Returns and Create a temp table after joins FIRST JOIN
	}
	
	//code change Vishnu 26Apr2014
	public Hashtable checkingCreateCodeCombination(ResultSetMetaData resMdata, String srcjoinColumn,Node rootNd,Hashtable nodeHT,
			String nodeValue, Hashtable segmentHT, Connection conn,String heirCode,int i,int k) {
		boolean createCodeCombination = false;
		Hashtable segmentDetailHT = new Hashtable<>();
		try{
			String sql = "";
//			System.out.println("colName----------.-----"+resMdata.getColumnName(i));
			if(resMdata.getColumnName(i).contains(srcjoinColumn)){
//				 srcjoinColumn = String.valueOf(joinHT.get(k));
//				 System.out.println("colName---------------"+resMdata.getColumnName(i));
				
//		    		System.out.println("tempDType---------------"+tempDType);
				 
		   
		   NodeList rootNdList = rootNd.getChildNodes();
		   for(int b=0;b<nodeHT.size();b++){
			   Hashtable ht = (Hashtable) nodeHT.get(b);
			   if(!String.valueOf(ht.get("NodeName")).equals("ID") && !String.valueOf(ht.get("NodeName")).equals("RootLevel_Name")){
//					 System.out.println("aa---------------"+ht);
					if(String.valueOf(ht.get("Type")).equalsIgnoreCase("Data")){
//						System.out.println("bb---------------"+nodeValue);
						String primarySeg = String.valueOf(ht.get("Primary_Segment"));
						   String segNo = primarySeg.substring(primarySeg.lastIndexOf("_")+1);
//						   System.out.println("segNo---------------"+segNo);
						   if(String.valueOf(ht.get("seg"+segNo+"_Code")).split(";")[0].equals(nodeValue)){
							   createCodeCombination = Boolean.valueOf(String.valueOf(ht.get("Create_Code_Combination4Data")));
							   String segmentValue = String.valueOf(ht.get("seg"+segNo+"_Code")).split(";")[0];
							    
							   segmentHT.put("Segment_"+segNo, segmentValue);
							   Hashtable codeSqlHT=Globals.Segment(segmentHT, conn, heirCode,true);
							   sql = String.valueOf(codeSqlHT.get(0));
							    if(String.valueOf(ht.get("OverWrite_Code_Combination")).equalsIgnoreCase("true")){
							    	createCodeCombination = false;
							    	segmentDetailHT.put("Result", "false");
							    }else{
							    	 segmentDetailHT.put("Result", "true");
									   segmentDetailHT.put("CodeCombinationFlag", createCodeCombination);
									   segmentDetailHT.put("segmentHT", segmentHT);
									   segmentDetailHT.put("segmentSql", sql);
							    }
							   
//							   System.out.println("segements===>"+segmentHT);
//							   System.out.println("sql===>"+sql);
//							   System.out.println("createCodeCombination111---------------"+createCodeCombination);
							  
							   break;
						   }
					}else{
						if(String.valueOf(ht.get("value")).equals(nodeValue)){
							 break;
						}
					}
				}
		   }
		    }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return segmentDetailHT;
	}
	
	public Hashtable gettingSegmentValueAndCodeCombiColumnName(String heirCode, Document xmldoc, Document configDoc) {
		Hashtable segmentAndCodeCombiColName = new Hashtable<>();
		try{
			
			Node segTrigSqlNd = null; //Segment_Trigger_SQL
			
			Node hierNd = Globals.getNodeByAttrVal(xmldoc, "Hierarchy_Level", "Hierarchy_ID", heirCode);
			NodeList hierNdList = hierNd.getChildNodes();
			Node rootNd = null;
			Hashtable segmentHT = new Hashtable<>();
			Hashtable segmentKeyHT = new Hashtable<>();
			int p=0,q=0,missedcount=0;
			int segmentCount = 0;
			if(!Globals.getAttrVal4AttrName(hierNd, "Number_of_Segment").equals("")){
				segmentCount=Integer.parseInt(Globals.getAttrVal4AttrName(hierNd, "Number_of_Segment"));
			}
			for(int m=0;m<hierNdList.getLength();m++){
				if(hierNdList.item(m).getNodeType() == Node.ELEMENT_NODE){
					if(hierNdList.item(m).getNodeName().equals("RootLevel")){
					rootNd = hierNdList.item(m);
					}else if(hierNdList.item(m).getNodeName().equals("Segments")){
						
//						NodeList hierarchyNdlist=hierNdList.item(m).getChildNodes();
//						
////						for(int i=0;i<hierarchyNdlist.getLength();i++){
//							Node segmentnd=hierarchyNdlist.item(m);
//							if(segmentnd.getNodeType()==Node.ELEMENT_NODE && segmentnd.getNodeName().equals("Segments")){
								NodeList segmentndlist=hierNdList.item(m).getChildNodes();
								for(int j=0;j<segmentndlist.getLength();j++){
									if(segmentndlist.item(j).getNodeType()==Node.ELEMENT_NODE){
											String[] segmentcodewithvalue=segmentndlist.item(j).getTextContent().split("~");
											  String original="";
											  int samp=0;
											  if(segmentndlist.item(j).getTextContent().equals("")){
												  samp=0;
											  }else{
												  samp = segmentcodewithvalue.length;
											  }
											  for(int r=0;r<samp;r++){
											   String temp=segmentcodewithvalue[r];
											   original=original+temp.substring(0, temp.indexOf("#$#"))+"~";
											  }
											  segmentHT.put(segmentndlist.item(j).getNodeName(), original);
												segmentKeyHT.put(q, segmentndlist.item(j).getNodeName());
												q++;
											p++;

									}else{
										p++;
									}
								}
//								}
//							}
					Hashtable ht=new Hashtable<>();
					missedcount=0;
					int count1=0;
					missedcount=0;
					for(int i=1;i<=segmentCount;i++){
						ht=new Hashtable<>();
						for(int k=0;k<segmentKeyHT.size();k++){
							if(segmentKeyHT.get(k).equals("Segment_"+i)){
								ht.put(missedcount, "false");
								missedcount++;
							}else{
								ht.put(missedcount, "true");
								missedcount++;
								
							}
						}

							if(ht.contains("false")){
								
							}else{
								
									
									segmentHT.put("Segment_"+i, "");
									count1++;
							}
					}
					}else if(hierNdList.item(m).getNodeName().equals("Segment_Trigger_SQL")){
						segTrigSqlNd = hierNdList.item(m);
					}
				}
			}
			if(segTrigSqlNd==null){
				NodeList segTrigdList = configDoc.getElementsByTagName("Segment_Trigger_SQL");
				segTrigSqlNd = segTrigdList.item(0);
			}
			String codeCombiColumnName = "";
			NodeList segtrigList = segTrigSqlNd.getChildNodes();
			for(int i=0;i<segtrigList.getLength();i++){
				if(segtrigList.item(i).getNodeType() == Node.ELEMENT_NODE && segtrigList.item(i).getNodeName().equals("COLUMN_NAME")){
					codeCombiColumnName = segtrigList.item(i).getTextContent();
				}
			}
			segmentAndCodeCombiColName.put("Segment", segmentHT);
			segmentAndCodeCombiColName.put("CodeCombination", codeCombiColumnName);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return segmentAndCodeCombiColName;
	}
	//end code change Vishnu 26Apr2014
	public Hashtable settingSourceAndWarehouseJoins(String tempTableinTarget,Hashtable SWjoinColumnHT,String srcTableNM,String targetfacttableName,Hashtable measureColumnHT,Connection targetConn,String heirCode,String tableLevelstatus,String orgSrcFactTableNM,String GenMode,Hashtable updateLoadHT,String tableNo,String srcTabPeriodColumn,String periodClmnInTarTable,Document xmldoc,Hashtable joinColumnHT,Hashtable columnPropertyHT,Document configDoc,Hashtable rowWid4period,Hashtable tableNameChangeHT)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		String rowsInserted="";
		measureColumnHT.put(measureColumnHT.size(), targetfacttableName+".HIERARCHY_ID="+srcTableNM+".HIERARCHY_ID");
		measureColumnHT.put(measureColumnHT.size(), targetfacttableName+".HIER_CAT_CODE="+srcTableNM+".HIER_CAT_CODE");
//		measureColumnHT.put(measureColumnHT.size(), targetfacttableName+".GL_ACCOUNT_NUM="+srcTableNM+".HIER20_CODE"); //04MAY14
		
		Hashtable resHT=new Hashtable<>();
		try{
			String consSELstmt="";
			String uniqTableNM="";
			String conInsStmt="";
			String conInsValQmark="";
			
			String conUpdateStmt="";
			String conUniqColStmt="";
			String conNonUniqColStmt="";
			
			Hashtable uniqueTabHT=new Hashtable<>();
			
			Hashtable selectCols=new Hashtable<>();
			Hashtable insCols=new Hashtable<>();
			Hashtable dataTypeHT=new Hashtable<>();
			
			Hashtable nonUniqueColHT=new Hashtable<>();
			Hashtable uniqueConsColHT=new Hashtable<>();
			Hashtable uniqueTarConsColHT=new Hashtable<>();
			Hashtable uniquecols4UpdateHT=new Hashtable<>();
			Hashtable uniquecols4UpdateTarHT=new Hashtable<>();
			
			Hashtable mappedCols4UpdateHT=new Hashtable<>(); 
			
			
			
			int uTabkey=0; 
			int selKey=0;
			int insKey=0;
			int nonUQkey=0;
			int UQkey=0;
			int ukey=0;
			
		
			if(GenMode.equalsIgnoreCase("UpdateFact"))
			{
			for(int j=0;j<updateLoadHT.size();j++)
			{
				String uniqTabdotColNM=(String)updateLoadHT.get(j);
//				String[] Splitter=uniqTabdotColNM.split("\\.");
//				uniquecols4UpdateHT.put(ukey,Splitter[1]);
				String[] Splitter=uniqTabdotColNM.split("=");
				String[] srcSplitter=Splitter[1].split("\\.");
				String[] tarSplitter=Splitter[0].split("\\.");
				uniquecols4UpdateHT.put(ukey,srcSplitter[1]);
				uniquecols4UpdateTarHT.put(ukey, tarSplitter[1]);
				ukey++;
				
			}
			}
			
			
			//Getting table Columns and Tables Names form Measure Columns
			
//			System.out.println("measureColumnHT :"+measureColumnHT.size());
			for(int i=0;i<measureColumnHT.size();i++)	
			{
				String mapcolumnStr=(String)measureColumnHT.get(i);
//				System.out.println("ORG :"+mapcolumnStr);
				
			
				mapcolumnStr=mapcolumnStr.replaceAll(srcTableNM, tempTableinTarget);
				
				
				if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
				{
//for INC LOAD  
					System.out.println("!mapcolumnStr.contains(tempTableinTarget)==>"+!mapcolumnStr.contains(tempTableinTarget));
				if(!mapcolumnStr.contains(tempTableinTarget))
				{
					
//					System.out.println("mapcolumnStr :"+mapcolumnStr);
				mapcolumnStr=mapcolumnStr.replace(orgSrcFactTableNM, tempTableinTarget);//Change Gokul 21APR2014 INCLOAD
				}else{
					
				}
				
//				dddddddddd
				}
				
				for(int h=0;h<tableNameChangeHT.size();h++){
					if(mapcolumnStr.contains(String.valueOf(tableNameChangeHT.get(h)))){
						mapcolumnStr=mapcolumnStr.replace(String.valueOf(tableNameChangeHT.get(h)), tempTableinTarget);
					}
				}
				
//				System.out.println("mapcolumnStr :"+mapcolumnStr);

				//Splitting map Column 
				String[] selCol=mapcolumnStr.split("=");
				
				String[] colSplit=selCol[0].split("\\.");//0 tableName  1 Column name
				
				String aliasColumnNM=colSplit[1]+"_"+i;
				
				//Cons SELECT Stmt and Ins Stmt
				if(i!=measureColumnHT.size()-1)
				{
//				consSELstmt=consSELstmt+selCol[1]+",";
				consSELstmt=consSELstmt+selCol[1]+" AS "+aliasColumnNM+" ,";
				conInsStmt=conInsStmt+colSplit[1]+",";
				conInsValQmark=conInsValQmark+"?,";
				}else{
//				consSELstmt=consSELstmt+selCol[1];
				consSELstmt=consSELstmt+selCol[1]+" AS "+aliasColumnNM;
				conInsStmt=conInsStmt+colSplit[1];
				conInsValQmark=conInsValQmark+"?";
				}
				selectCols.put(selKey, selCol[1]);
				insCols.put(insKey, colSplit[1]);
				Hashtable datadetHT=getDatatypefromDBtable(targetfacttableName+"."+colSplit[1], targetConn, "", "", "");
				String datatype=(String)datadetHT.get(1);
				dataTypeHT.put(insKey, datatype);
				insKey++;
				selKey++;
				
				//Getting Unique Table Name
				
				String[] uniqTable=selCol[1].split("\\.");
				
				
				if(!uniqueTabHT.contains(uniqTable[0]))//19APR2014
				{
					uniqueTabHT.put(uTabkey, uniqTable[0]);
					uTabkey++;
				}
				
			
		 if(GenMode.equalsIgnoreCase("UpdateFact"))
			{
				
				//for Update 
				String[] colSPlitt=selCol[1].split("\\.");
				String colNM=colSPlitt[1];
				
			
//				System.out.println("uniquecols4UpdateHT   "+uniquecols4UpdateHT+" =   ");
				
				for(int j=0;j<uniquecols4UpdateHT.size();j++)
				{
//					String uniqTabdotColNM=(String)updateLoadHT.get(j);
//					String[] Splitter=uniqTabdotColNM.split("\\.");

//					System.out.println("colNM   "+colNM+" =   ");
					//////////////////////////////
					
					if(uniquecols4UpdateHT.contains(srcTabPeriodColumn))
					{
						String[] periodSplit=periodClmnInTarTable.split("\\.");
						if(!uniqueConsColHT.contains(periodSplit[1])&&colSplit[1].equalsIgnoreCase(periodSplit[1]))
						{
						uniqueConsColHT.put(UQkey,periodSplit[1]);
						mappedCols4UpdateHT.put(periodSplit[1], aliasColumnNM);
						UQkey++;
						}
					}
					//////////////////////////////
					
					
					if(!uniquecols4UpdateHT.contains(colNM))
					{
						
						if(!nonUniqueColHT.contains(colSplit[1])&&!uniqueConsColHT.contains(colSplit[1]))
						{
//						System.out.println("colNM   "+colNM+" =   "+Splitter[1]);
//						System.out.println("inside >>>"+colSplit[1]);
							nonUniqueColHT.put(nonUQkey,colSplit[1]);
							mappedCols4UpdateHT.put(colSplit[1],aliasColumnNM);
							nonUQkey++;
							break;
						}
					}else
					{
						if(!uniqueConsColHT.contains(colSplit[1]))
						{
						uniqueConsColHT.put(UQkey,colSplit[1]);
						mappedCols4UpdateHT.put(colSplit[1], aliasColumnNM);
						UQkey++;
						break;
						}
					}
				}
	
			}
				
				
			}
			System.out.println("uniqueTarConsColHT :"+uniqueTarConsColHT);
			System.out.println("uniqueConsColHT :"+uniqueConsColHT);
			System.out.println("nonUniqueColHT :"+nonUniqueColHT);
			if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("GenerateFact"))
			{
			conInsStmt="INSERT INTO "+targetfacttableName+" ("+conInsStmt+") VALUES ("+conInsValQmark+")";
			}else
			{
				
				System.out.println("nonUniqueColHT "+nonUniqueColHT);
				System.out.println("uniqueConsColHT "+uniqueConsColHT);
				
				for(int i=0;i<nonUniqueColHT.size();i++)
				{
					String nonUniqueCols=(String)nonUniqueColHT.get(i);
					
					if(i!=nonUniqueColHT.size()-1)
					{
						conNonUniqColStmt=conNonUniqColStmt+nonUniqueCols+" = ? ,";
					}else{
						conNonUniqColStmt=conNonUniqColStmt+nonUniqueCols+" = ? ";
					}
					
				}
				//Start Code Change Gokul 03MAY2014
				for(int i=0;i<uniqueConsColHT.size();i++)
//				for(int i=0;i<uniquecols4UpdateTarHT.size();i++)
				{
					String UniqueCols=(String)uniqueConsColHT.get(i);
//					String UniqueCols=(String)uniquecols4UpdateTarHT.get(i);
					
					if(i!=uniqueConsColHT.size()-1)
//					if(i!=uniquecols4UpdateTarHT.size()-1)
					{
						conUniqColStmt=conUniqColStmt+UniqueCols+" = ? AND ";
					}else{
						conUniqColStmt=conUniqColStmt+UniqueCols+" = ? ";
					}
					
				}	//end Code Change Gokul 03MAY2014
				
				
				
				conInsStmt="UPDATE "+targetfacttableName+" SET "+conNonUniqColStmt+" WHERE "+ conUniqColStmt;
				
				
//				System.out.println("Constructed Update Statement : ======>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("Constructed Update Statement : "+conInsStmt);
//				System.out.println("Constructed Update Statement : ======<<<<<<<<<<<<<<<<<<<<<<<<<");
			}
			

			
			System.out.println("Constructed Select for Join:"+consSELstmt);//W_MCAL_PERIOD_D.ROW_WID,W_GL_OTHER_F_TEMP_79.OTHER_LOC_AMT,W_GL_OTHER_F_TEMP_79.OTHER_DOC_AMT,W_GL_OTHER_F_TEMP_79.LEDGER_WID
		
			
//			System.out.println("srcTableNM "+srcTableNM);
//			System.out.println("tempTableinTarget "+tempTableinTarget);
			
			
			///////////
			//Getting table Name from SWjoinColumn 
			
			String consJoinDATA="";
			System.out.println("SWjoinColumnHT====>"+SWjoinColumnHT);
			for(int i=0;i<SWjoinColumnHT.size();i++)	
			{
				System.out.println(i+"<====SWjoinColumnHT====>"+SWjoinColumnHT.get(i));
//				if((Hashtable)SWjoinColumnHT.get(i)==null){
//					continue;
//				}
				Hashtable joincolHT=(Hashtable)SWjoinColumnHT.get(i);
				String tarColumn=(String)joincolHT.get("Target_Column");
				String srcColumn=(String)joincolHT.get("Source_Column");
				String Join_type=(String)joincolHT.get("Join_type");
				String Join_Operator=(String)joincolHT.get("Join_Operator");
				String Target_Connection=(String)joincolHT.get("Target_Connection");
				String Source_Connection=(String)joincolHT.get("Source_Connection");
				//Start code change Jayaramu 05MAY14
				Character firstChar = '\0';
				Character lastChar = '\0';
				if(tarColumn.length()>0){
				firstChar = tarColumn.charAt(0);
				lastChar = tarColumn.charAt(tarColumn.length()-1);
				
				}//End code change Jayaramu 05MAY14
				
				
				
				
				tarColumn=tarColumn.replaceAll(srcTableNM, tempTableinTarget);
				srcColumn=srcColumn.replaceAll(srcTableNM, tempTableinTarget);
				
//				sdawdas
				
				if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
				{
				
				//for INC LOAD  
				if(!tarColumn.contains(tempTableinTarget))
				{
				
					tarColumn=tarColumn.replace(orgSrcFactTableNM, tempTableinTarget);//Change Gokul 21APR2014 INCLOAD
				}
				
				//for INC LOAD  
				if(!srcColumn.contains(tempTableinTarget))
				{
				
					srcColumn=srcColumn.replace(orgSrcFactTableNM, tempTableinTarget);//Change Gokul 21APR2014 INCLOAD
				}
				
				
				}
				
				
				String[] tarColSplitter=tarColumn.split("\\.");
				String[] srcColSplitter=srcColumn.split("\\.");
				
				
				if(!Join_type.equalsIgnoreCase("Value"))
				{
//				if(!uniqueTabHT.contains(tarColSplitter[0])&&!tempTableinTarget.equalsIgnoreCase(tarColSplitter[0]))
//				{
				if(!uniqueTabHT.contains(tarColSplitter[0]))//Code change Gokul 19APR2014 
				{
					
					uniqueTabHT.put(uTabkey, tarColSplitter[0]);
					uTabkey++;
				}
				}
				
//				if(!uniqueTabHT.contains(srcColSplitter[0])&&!tempTableinTarget.equalsIgnoreCase(srcColSplitter[0]))
//				{
				if(!uniqueTabHT.contains(srcColSplitter[0]))//Code change Gokul 19APR2014
				{
					
					uniqueTabHT.put(uTabkey, srcColSplitter[0]);
					uTabkey++;
				}
//				System.out.println("uniqueTabHT:"+uniqueTabHT);
				if(String.valueOf(joincolHT.get("Target_Column_Function"))!=null){
					if(!String.valueOf(joincolHT.get("Target_Column_Function")).equals("")){
						tarColumn = String.valueOf(joincolHT.get("Target_Column_Function"));
						tarColumn=tarColumn.replaceAll(srcTableNM, tempTableinTarget);
						if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact"))
						{
						
						//for INC LOAD  
						if(!tarColumn.contains(tempTableinTarget))
						{
						
							tarColumn=tarColumn.replace(orgSrcFactTableNM, tempTableinTarget);//Change Gokul 21APR2014 INCLOAD
						}
						}
					}
				}
				if(String.valueOf(joincolHT.get("Source_Column_Function"))!=null){
					if(!String.valueOf(joincolHT.get("Source_Column_Function")).equals("")){
						srcColumn = String.valueOf(joincolHT.get("Source_Column_Function"));
						srcColumn=srcColumn.replaceAll(srcTableNM, tempTableinTarget);
						if(GenMode.equalsIgnoreCase("InsertFact")||GenMode.equalsIgnoreCase("UpdateFact")){
						if(!srcColumn.contains(tempTableinTarget))
						{
						
							srcColumn=srcColumn.replace(orgSrcFactTableNM, tempTableinTarget);//Change Gokul 21APR2014 INCLOAD
						}
						
						
						}
					}
				}
				
				
				
				
				//for INC LOAD  
				
				if(Join_type.equalsIgnoreCase("Column"))
				{
					if(i!=SWjoinColumnHT.size()-1)
					{
						if(Join_Operator.equals("IN")){
							
							if(firstChar.equals('(') && lastChar.equals(')')){
								consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+tarColumn+" AND ";
							}else{
						consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" ("+tarColumn+") AND ";
							}
						}else{
					consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+tarColumn+" AND ";
						}
					}else
					{
						if(Join_Operator.equals("IN")){
							if(firstChar.equals('(') && lastChar.equals(')')){
								consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+tarColumn;	
							}else{
						consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" ("+tarColumn+")";	
							}
						}else{
					consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+tarColumn;	
					}
					}
					
					
				}else if(Join_type.equalsIgnoreCase("Value"))
				{
					if(Join_Operator.equalsIgnoreCase("Between")||Join_Operator.equalsIgnoreCase("<>"))
					{
						String[] betwvals=tarColumn.split("#-#");
						
						if(i!=SWjoinColumnHT.size()-1)
						{
						consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+betwvals[0]+" AND "+betwvals[1]+" AND ";
						}else
						{
							consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+betwvals[0]+" AND "+betwvals[1]+"";
						}
						
					}else
					{
						if(i!=SWjoinColumnHT.size()-1)
						{
							if(Join_Operator.equals("IN")){
								if(firstChar.equals('(') && lastChar.equals(')')){
									consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+tarColumn+" AND ";
								}else{
								consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" ("+tarColumn+") AND ";
								}
							}else{
						consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+tarColumn+" AND ";
//							consJoinDATA=consJoinDATA+tarColumn+" "+Join_Operator+" "+srcColumn+" AND ";
						}}else
						{
							if(Join_Operator.equals("IN")){
								if(firstChar.equals('(') && lastChar.equals(')')){
									consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+tarColumn+"";
								}else{
								consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" ("+tarColumn+")";
								}
							}else{
						consJoinDATA=consJoinDATA+srcColumn+" "+Join_Operator+" "+tarColumn;
							}
//							consJoinDATA=consJoinDATA+tarColumn+" "+Join_Operator+" "+srcColumn;
						}
					}
				}
				
				
				
			}
			
			
			for(int i=0;i<uniqueTabHT.size();i++)
			{
				
				
				if(i!=uniqueTabHT.size()-1)
				{
					if(tableNameChangeHT.containsValue((String)uniqueTabHT.get(i))){
//						uniqTableNM=uniqTableNM+tempTableinTarget+",";
					}else{
						uniqTableNM=uniqTableNM+(String)uniqueTabHT.get(i)+",";
					}
					
					
				}else
				{	
					if(tableNameChangeHT.containsValue((String)uniqueTabHT.get(i))){
//						uniqTableNM=uniqTableNM+tempTableinTarget;
					}else{
						uniqTableNM=uniqTableNM+(String)uniqueTabHT.get(i);
					}
					
					
				}
				
				
			}
			
//			System.out.println("Constructed Unique Table Name for Join:"+uniqTableNM);//W_MCAL_PERIOD_D,W_GL_OTHER_F_TEMP_79,W_LEDGER_D
			
			//Construction of SELECT Query
//			System.out.println("Constructed JoinDATA:"+consJoinDATA);
			
//			String selectQuery="SELECT "+consSELstmt+","+tempTableinTarget+".NODE_TYPE FROM "+tempTableinTarget+" LEFT OUTER JOIN "+uniqTableNM+" ON "+consJoinDATA;//sql with Join 
			
//			String selectQuery="SELECT "+consSELstmt+","+tempTableinTarget+".NODE_TYPE FROM "+tempTableinTarget+" , "+uniqTableNM+" WHERE "+consJoinDATA;
			
//			String selectQuery="SELECT "+consSELstmt+","+tempTableinTarget+".NODE_TYPE FROM "+uniqTableNM+" WHERE "+consJoinDATA;//sql with out join Code Change  Gokul 19APR2014 
			
			String selectQuery="";
			
			if(SWjoinColumnHT!=null&&SWjoinColumnHT.size()!=0)
			{
			
			 selectQuery="SELECT "+consSELstmt+","+tempTableinTarget+".NODE_TYPE FROM "+uniqTableNM+" WHERE "+consJoinDATA;//sql with out join Code Change  Gokul 19APR2014
			}else
			{
			 selectQuery="SELECT "+consSELstmt+","+tempTableinTarget+".NODE_TYPE FROM "+tempTableinTarget;//sql with out join Code Change  Gokul 19APR2014
			}
			
			
			
			
			
			
			//SELECT W_GL_OTHER_F.GL_ACCOUNT_WID,W_GL_OTHER_F_TEMP_79.OTHER_DOC_AMT,W_GL_OTHER_F_TEMP_79.LEDGER_WID FROM W_GL_OTHER_F_TEMP_79 LEFT OUTER JOIN W_GL_OTHER_F  ON W_GL_OTHER_F_TEMP_79.HIER20_NUM = W_GL_OTHER_F.GL_ACCOUNT_WID;
			//SELECT W_MCAL_PERIOD_D.ROW_WID,W_GL_OTHER_F_TEMP_79.OTHER_LOC_AMT,W_GL_OTHER_F_TEMP_79.OTHER_DOC_AMT,W_GL_OTHER_F_TEMP_79.LEDGER_WID FROM W_MCAL_PERIOD_D,W_GL_OTHER_F_TEMP_79 LEFT OUTER JOIN W_MCAL_PERIOD_D ON W_GL_OTHER_F_TEMP_79.ACCT_PERIOD_END_DT_WID = W_MCAL_PERIOD_D.ROW_WID
			
			System.out.println("Constructed selectQuery:"+selectQuery);
			System.out.println("Constructed Insert Query :"+conInsStmt);
			
//			System.out.println("insCols---------------"+insCols);
			
			PreparedStatement selectJoinData=targetConn.prepareStatement(selectQuery);
			ResultSet joinedDataRS=selectJoinData.executeQuery();
			
			PreparedStatement insJoinDataPS=targetConn.prepareStatement(conInsStmt);
			int rows=0;
			int index=1;
			
			if(!GenMode.equalsIgnoreCase("UpdateFact"))
			{
			
			
			while(joinedDataRS.next()) // 03MAY14 Insert into Target Fact
			{
				
				String nodetype=joinedDataRS.getString("NODE_TYPE");
				if(nodetype!=null&&nodetype.equalsIgnoreCase("code")) //??15APR14
				{
				 for (int i=1; i<=insCols.size(); i++) {
					  
//					 System.out.println("colName---------------"+colName);
//					    	Hashtable dataTypeHT=getDatatypefromDBtable(targetfacttableName+"."+colName, targetConn, "", "", "");
					    		String tempDType=(String)dataTypeHT.get(i-1);
//					    		System.out.println("tempDType---------------"+tempDType);
					    if(tempDType.contains("VARCHAR")||tempDType.contains("CHAR"))
					    {
					    	
					    	insJoinDataPS.setString(i, joinedDataRS.getString(i));
					    	
					    }else if(tempDType.contains("NUMBER"))   
					    {
					    	insJoinDataPS.setDouble(i, joinedDataRS.getDouble(i));
					    }else if(tempDType.contains("DATE"))  
					    {
					    	insJoinDataPS.setDate(i, joinedDataRS.getDate(i));
					    }else if(tempDType.contains("TIMESTAMP"))  
					    {
					    	insJoinDataPS.setTimestamp(i, joinedDataRS.getTimestamp(i));
					    }
					   
					    index++;
					    }
					
				 insJoinDataPS.addBatch();
				 rows++;
				}
					    
			}
			}else
			{
//				conInsStmt="UPDATE "+targetfacttableName+" SET "+conNonUniqColStmt+" WHERE "+ conUniqColStmt;
//
//nonUniqueColHT
//
//uniqueConsColHT
				
				int noOFindex=nonUniqueColHT.size()+uniqueConsColHT.size();
				
//				System.out.println("dataTypeHT   "+dataTypeHT);
//				System.out.println("mappedCols4UpdateHT   "+mappedCols4UpdateHT);
//				
//
				while(joinedDataRS.next())
				{
					
					String nodetype=joinedDataRS.getString("NODE_TYPE");
					if(nodetype!=null&&nodetype.equalsIgnoreCase("code")) //??15APR14
					{
						int indexNonUQ=0,indexUQ=0;
						
					 for (int i=1; i<=noOFindex; i++) {
						 String updateColNM="";
						 int updateColindex=0;
						 if(i<nonUniqueColHT.size()+1)
						 {
//							 System.out.println(">>>>>>>> nonUniqueColHT "+nonUniqueColHT.get(indexNonUQ));
							 updateColNM=(String)nonUniqueColHT.get(indexNonUQ);
							 indexNonUQ++;
						 }else 
						 {
//							 System.out.println(">>>>>>>> uniqueConsColHT "+uniqueConsColHT.get(indexUQ));
							 updateColNM=(String)uniqueConsColHT.get(indexUQ);
							 indexUQ++;
							 
						 }
						 String corresMapColumn=(String)mappedCols4UpdateHT.get(updateColNM);
						 
						 
						 updateColindex=joinedDataRS.findColumn(corresMapColumn);
//						 System.out.println(">>>>>>>> corresMapColumn "+corresMapColumn+" updateColindex "+updateColindex);
//						 dfgdfgdfgfgfgf
						 String tempDType=(String)dataTypeHT.get(updateColindex-1);
						 
//						 System.out.println(">>>>>>>> updateColNM "+updateColNM+" Dtype =>"+tempDType);
						 
						   if(tempDType.contains("VARCHAR")||tempDType.contains("CHAR"))
						    {
						    	
						    	insJoinDataPS.setString(i, joinedDataRS.getString(updateColindex));
//						    	System.out.println("index value  : "+i);
						    	
						    }else if(tempDType.contains("NUMBER"))   
						    {
						    	insJoinDataPS.setDouble(i, joinedDataRS.getDouble(updateColindex));
//						    	System.out.println("index value  : "+i);
						    }else if(tempDType.contains("DATE"))  
						    {
						    	insJoinDataPS.setDate(i, joinedDataRS.getDate(updateColindex));
//						    	System.out.println("index value  : "+i);
						    }else if(tempDType.contains("TIMESTAMP"))  
						    {
						    	insJoinDataPS.setTimestamp(i, joinedDataRS.getTimestamp(updateColindex));
//						    	System.out.println("index value  : "+i);
						    }
						   
						    index++;
						 
					}
					 insJoinDataPS.addBatch();
					 rows++;	    
				}
				
				
				}
				
			}
			insJoinDataPS.executeBatch();
			
//			System.out.println("All Join Data are inserted into temp "+targetfacttableName+" Table");
//			System.out.println(rows+" row(s) inserted into "+targetfacttableName+" Table");
			
			rowsInserted=String.valueOf(rows);
			
		}catch(Exception e)
		{
			tableLevelstatus=settingErrorStatusinXML(heirCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
			e.printStackTrace();
			resHT.put(0, "");
			resHT.put(1, tableLevelstatus);
			
			return resHT;
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		resHT.put(0, rowsInserted);
		resHT.put(1, tableLevelstatus);
		
		return resHT;
	}
	
	//code change Vishnu 26Apr2014
	public void gettingDim(Hashtable columnPropertyHT,ResultSetMetaData rsmd,Hashtable dataTypeHT,int i, Connection conn,String heirCode,
			String rowWid4period,String orgSrcFactTableNM, String codeCombiColumnName, String srcTabPeriodColumn,
			Connection targetConn,PreparedStatement insJoinDataPS, Hashtable colPropHT, boolean createCodeCombination, 
			String joinColumn, String sql, int key) {
		try{
			
			
			
			
			 if(createCodeCombination && colPropHT.containsValue(rsmd.getColumnName(i)) 
					 && String.valueOf(columnPropertyHT.get(rsmd.getColumnName(i))).equalsIgnoreCase("Measure")){
//				 for(int b=0;b<rowWid4period.size();b++){
				 String measureSQL = "select sum(gl."+rsmd.getColumnName(i)+") from "+orgSrcFactTableNM+" gl, ("+sql+") cc where "
						 +"gl."+joinColumn+"="+"cc."+codeCombiColumnName+" and gl."+srcTabPeriodColumn+" = "+rowWid4period+" group by gl."+srcTabPeriodColumn;
				PreparedStatement ps = targetConn.prepareStatement(measureSQL);
				 ResultSet rs = ps.executeQuery();
				 
				 while(rs.next()){
					 String tempDType=(String)dataTypeHT.get(i-1);
			    		
			    if(tempDType.contains("VARCHAR")||tempDType.contains("CHAR"))
			    {
			    	
//			    	System.out.println("joinedDataRS.getString(i)---------------"+joinedDataRS.getString(i));
			    	insJoinDataPS.setString(i, rs.getString(1));
			    	
			    }else if(tempDType.contains("NUMBER"))   
			    {
			    	insJoinDataPS.setDouble(i, rs.getDouble(1));
			    }else if(tempDType.contains("DATE"))  
			    {
			    	insJoinDataPS.setDate(i, rs.getDate(1));
			    }else if(tempDType.contains("TIMESTAMP"))  
			    {
			    	insJoinDataPS.setTimestamp(i, rs.getTimestamp(1));
			    }
//			    insJoinDataPS.addBatch();
//			    key++;
				 }
//				 System.out.println("measureSQL---------------"+measureSQL);
				 createCodeCombination = false;
			 }
//			 }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//end code change Vishnu 26Apr2014

	public    String preferredDataType4SQLDataType(Hashtable dataTypeDetHT)  //16MAR14
	{
		
		String dataType=(String)dataTypeDetHT.get(1);
		String size=(String)dataTypeDetHT.get(2);
		String decimalDigits=(String)dataTypeDetHT.get(3);
		String preferredDtype="";
		try{
		if(dataType!=null&&!dataType.equalsIgnoreCase(""))
		{
			
			if(dataType.contains("VARCHAR")||dataType.contains("CHAR"))
			{
				preferredDtype="String";
			}else if(dataType.contains("DATE"))
			{
				preferredDtype="Date";
					
			}else if(dataType.contains("TIMESTAMP"))
			{
				preferredDtype="Timestamp";
					
			} else if (dataType.contains("INT")) {
				preferredDtype="int";
				
			} else if (dataType.contains("LONG")) {
				preferredDtype="long";
				
			} else if(dataType.contains("NUMBER") || dataType.contains("DECIMAL")||dataType.contains("DOUBLE")) { //16MAR14
				preferredDtype="double";
			}
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return preferredDtype;
	}
	//End Code Change Gokul 05MAR2014
	
	
	public  void updateCurrentFactGenTimeinXML(String heirID, String GenMode){   
		
		try{
			
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			String dateFormat=prop.getProperty("DATE_FORMAT");
			Document doc = Globals.openXMLFile(dir, "HierachyLevel.xml");
			Node rootnode = Globals.getNodeByAttrVal(doc, "Fact_Tables", "ID",heirID);
			Element factTableE=(Element)rootnode;
			
			Date currentTime=new Date();
			Format formatter = new SimpleDateFormat(dateFormat);
			String lastgendate=formatter.format(currentTime);
			
			factTableE.setAttribute("Last_Generated_Time",lastgendate);
			factTableE.setAttribute("Previous_Gen_Type", GenMode);
			
			Globals.writeXMLFile(doc, dir, "HierachyLevel.xml");
		System.out.println("Fact Genarated Time Updated in XML");	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public  Hashtable createTemptable4IncrementalLoad(Hashtable incrementLoadHT,String lastGentime,String XmlDir,String configFileNM,String srcFacttableName,Connection srcConn,String hierCode,String tableLevelstatus,String targetfacttableName,Hashtable incUpdateHT,String GenMode,String tableNo,Hashtable joinTypeHT4Incr)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		String createdTemptable="";
		Hashtable resHT=new Hashtable<>();
		try{
			
				String parseDATE ="";
				PropUtil prop=new PropUtil();
				String dateFormatDF= prop.getProperty("DATE_FORMAT");

				
//				SELECT SERIAL,W_INSERT_DT,W_UPDATE_DT FROM W_GL_OTHER_F WHERE W_INSERT_DT >sysdate OR W_UPDATE_DT > sysdate;
			
				Hashtable insertLoadHT=new Hashtable<>();
				int inskey=0;
				Hashtable updateLoadHT=new Hashtable<>();
				int upkey=0;
				
				String consSQLstmt="";
				
				
				for(int i=0;i<incrementLoadHT.size();i++)
				{
					Hashtable loadDetHT=(Hashtable)incrementLoadHT.get(i);
					String loadType=(String)loadDetHT.get("Type");
					String colNM=(String)loadDetHT.get("Column");
				
					if(loadType.equalsIgnoreCase("Insert"))
					{
						insertLoadHT.put(inskey, colNM);
						inskey++;
				
				
					}else if(loadType.equalsIgnoreCase("Update"))
					{
						updateLoadHT.put(upkey, colNM);
						upkey++;
					}
				}
				
				if(GenMode.equalsIgnoreCase("InsertFact"))
				{
					if(insertLoadHT.size()<=0){
					      resHT.put(0, "");
					      resHT.put(1, "");
					      return resHT;
					     }
				for(int i=0;i<insertLoadHT.size();i++)
				{
						String colNM=(String)insertLoadHT.get(i);
										
						if(i!=insertLoadHT.size()-1)
						{
//						consSQLstmt=consSQLstmt+"to_char("+colNM+",'"+dateFormatDF+"') > '"+lastGentime +"' OR ";
//							consSQLstmt=consSQLstmt+colNM+" > "+"TO_TIMESTAMP('"+lastGentime+"','yyyy-MM-dd HH24:mi:ss.FF')"+"' OR ";
							consSQLstmt=consSQLstmt+colNM+" > "+"TO_TIMESTAMP('"+lastGentime+"','yyyy-MM-dd HH24:mi:ss.FF')"+" "+String.valueOf(joinTypeHT4Incr.get("Insert"))+" ";
						}else
						{
//							consSQLstmt=consSQLstmt+"to_char("+colNM+",'"+dateFormatDF+"') > '"+lastGentime+"'";
							consSQLstmt=consSQLstmt+colNM+" > "+"TO_TIMESTAMP('"+lastGentime+"','yyyy-MM-dd HH24:mi:ss.FF')";
						}
						
						
				}
				
			
				int tempTableDBId=Globals.getMaxID(XmlDir, configFileNM, "DB_Temp_Table_ID", "ID");
				createdTemptable=srcFacttableName+"_INC_LOAD_TEMP_";
				
				if(createdTemptable.length()>15)
				{
					createdTemptable=createdTemptable.substring(0,15);
					
				}
				createdTemptable=createdTemptable+tempTableDBId;
				//Creating a New table in Src Connection
				String createsql="";
			
							
				createsql="CREATE TABLE "+createdTemptable+" AS SELECT * FROM "+srcFacttableName+" WHERE "+consSQLstmt;
				
	
				System.out.println("Create SQL:"+createsql);
			
				PreparedStatement tableCreate=srcConn.prepareStatement(createsql);
				tableCreate.execute();
				System.out.println("Temp Table "+createdTemptable+" has Created Successfully.");
				tableCreate.close();
				}else if(GenMode.equalsIgnoreCase("UpdateFact"))
				{
					if(updateLoadHT.size()<=0){
					      resHT.put(0, "");
					      resHT.put(1, "");
					      return resHT;
					     }
					for(int i=0;i<updateLoadHT.size();i++)
					{
							String colNM=(String)updateLoadHT.get(i);
											
							if(i!=updateLoadHT.size()-1)
							{
//							consSQLstmt=consSQLstmt+"to_char("+colNM+",'"+dateFormatDF+"') > '"+lastGentime +"' OR ";
//							consSQLstmt=consSQLstmt+colNM+" > "+"TO_TIMESTAMP('"+lastGentime+"','yyyy-MM-dd HH24:mi:ss.FF')" +" OR ";
								consSQLstmt=consSQLstmt+colNM+" > "+"TO_TIMESTAMP('"+lastGentime+"','yyyy-MM-dd HH24:mi:ss.FF')" +" "+String.valueOf(joinTypeHT4Incr.get("Update"))+" ";
							}else
							{
//								consSQLstmt=consSQLstmt+"to_char("+colNM+",'"+dateFormatDF+"') > '"+lastGentime+"'";
								consSQLstmt=consSQLstmt+colNM+" > "+"TO_TIMESTAMP('"+lastGentime+"','yyyy-MM-dd HH24:mi:ss.FF')";
								

							}
							
							
					}
					
				
					int tempTableDBId=Globals.getMaxID(XmlDir, configFileNM, "DB_Temp_Table_ID", "ID");
					createdTemptable=srcFacttableName+"_INC_UPDATE_TEMP_";
				
					//Creating a New table in Src connection
					String createsql="";
					
					if(createdTemptable.length()>28)
					{
						createdTemptable=createdTemptable.substring(0,27);
						createdTemptable=createdTemptable+tempTableDBId;
					}else
					{
						createdTemptable=createdTemptable+tempTableDBId;
					}
				
								
					createsql="CREATE TABLE "+createdTemptable+" AS SELECT * FROM "+srcFacttableName+" WHERE "+consSQLstmt;
					
		
					System.out.println("Create SQL:"+createsql);
				
					PreparedStatement tableCreate=srcConn.prepareStatement(createsql);
					tableCreate.execute();
					System.out.println("Temp Table "+createdTemptable+" has Created Successfully.");
					tableCreate.close();
					
			
					
				}
				
			
			
			
		}catch(Exception e){
			tableLevelstatus=settingErrorStatusinXML(hierCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		resHT.put(0, createdTemptable);
		resHT.put(1, tableLevelstatus);
		
		return resHT;
	
	}
	//Start Code Change Gokul 03MAY2014
	public void insertORUpdateFactDetilsintoDB(Connection conn,String mode,String hierCode,Hashtable colsHT,Hashtable valuesHT)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
	try{
		
		String consColsins="";
		String consQmarks="";
		String consColUP="";
		
		Hashtable dTypeHT=new Hashtable<>();
		
		for(int i=0;i<colsHT.size();i++)
		{
			String tablenMdotColNM=(String)colsHT.get(i);
			
			String[] splitter=tablenMdotColNM.split("\\.");
			
			Hashtable resHT=getDatatypefromDBtable(tablenMdotColNM, conn, hierCode, "", "");
			
			String dType=(String)resHT.get(1);
			
			dTypeHT.put(i, dType);
			
			if(i==colsHT.size()-1)
			{
				consColsins=consColsins+splitter[1];
				consQmarks=consQmarks+"?";
				consColUP=consColUP+splitter[1]+"= ?";
			}else
			{
				consColsins=consColsins+splitter[1]+",";
				consQmarks=consQmarks+"?,";
				consColUP=consColUP+splitter[1]+"= ?,";
			}
		}
		
		System.out.println("Constructed Column INSERT Statement : "+consColsins);
		System.out.println("Constructed Column UPDATE Statement : "+consColUP);
		
		String sql="";
		PreparedStatement queryPS=null;
		if(mode.equalsIgnoreCase("insert"))
		{
			sql="INSERT INTO W_FACT_DETAILS ("+consColsins+") VALUES ("+consQmarks+")";
			
		}else
		{
			sql="UPDATE W_FACT_DETAILS SET "+consColUP+" WHERE HIERARCHY_ID="+hierCode;
		}
			
		queryPS=conn.prepareStatement(sql);
		
		for(int i=0;i<colsHT.size();i++)
		{
//			System.out.println("index "+i);
			String dType=(String)dTypeHT.get(i);
//			System.out.println("dType "+dType);
			 if(dType.contains("VARCHAR")||dType.contains("CHAR"))
			    {
			    	
				 queryPS.setString(i+1, (String)valuesHT.get(i));
			    }else if(dType.contains("NUMBER"))   
			    {
			    	queryPS.setDouble(i+1, Double.valueOf((String)valuesHT.get(i)));
			    }else if(dType.contains("DATE"))  
			    {
			      	queryPS.setDate(i+1, java.sql.Date.valueOf((String)valuesHT.get(i)));
			    }else if(dType.contains("TIMESTAMP"))  
			    {
			    	queryPS.setTimestamp(i+1, Timestamp.valueOf((String)valuesHT.get(i)));
			    }
			
			
		}
		queryPS.execute();
		System.out.println(" FACT DETAILS UPDATED INTO W_FACT_DETAILS TABLE ");
		
	}catch(Exception e){
//		tableLevelstatus=settingErrorStatusinXML(hierCode, tableLevelstatus, targetfacttableName, e.getMessage(),String.valueOf(tableNo));
		e.printStackTrace();
	}
	System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());
	
	}
	//End Code Change Gokul 03MAY2014
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("============THREAD STARTED TO GENARATE FACTS===========");
		String genType=this.genType;
		if(genType.equalsIgnoreCase("InsertFact"))
		{
		generateFacttables(genType);
		generateFacttables("UpdateFact");
		}
		else
		{
		generateFacttables("GenerateFact");
		}
	}
	
	
	
	
	
	
	

}

