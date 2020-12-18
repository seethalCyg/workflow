package beans;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;


import utils.Globals;
import utils.PropUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


@ManagedBean(name = "setupConfigBean")
@ApplicationScoped

public class SetupConfigBean {
	
	
	 String connName_Data="";
	 String hostName_Data="";
	 String portNo_Data="";
	 String dbName_Data="";
	 String userName_Data="";
	 String pass_Data="";
	 String connType_Data="";
	
	 String connName_DW="";
	 String hostName_DW="";
	 String portNo_DW="";
	 String dbName_DW="";
	 String userName_DW="";
	 String pass_DW="";
	 String connType_DW="";
	 
	 int noOfSegments;
	 String segmentSQL="";
	 String segmentTrigerSQL="";
	 String rowWidSQL="";
	 String detailTable="";
	 String factTable="";
	 String dataTable="";
	 
	 String targetDimTable="";
	String sourceFactTableName="";
	String targetFactTableName="";
	ArrayList measureColmnsAL=new ArrayList<>();
	ArrayList joinColmnsAL=new ArrayList<>();			
	String periodColumn="";
	public String periodValues="";
	
	String emailID="";
	String password="";
	
	 ArrayList segSQLTablesAL=new ArrayList<>();
	 ArrayList segSQLColumn1AL=new ArrayList<>();
	 ArrayList segSQLColumn2AL=new ArrayList<>();
	 
	 String statusMsg="";

	 // code change Menaka 26MAR2014
	 String ldapDomainName;
	 String ldapHost;
	int ldapPort;
	int ldapVersion;
	String ldapSearchScope;
	 String ldapSearchBase;
	 String ldapSearchFilter;
	String ldapAttrOnly;
	String ldapAttributes;
	 
	 
	
	 public String getLdapDomainName() {
		return ldapDomainName;
	}
	public void setLdapDomainName(String ldapDomainName) {
		this.ldapDomainName = ldapDomainName;
	}
	public String getLdapHost() {
		return ldapHost;
	}
	public void setLdapHost(String ldapHost) {
		this.ldapHost = ldapHost;
	}
	public int getLdapPort() {
		return ldapPort;
	}
	public void setLdapPort(int ldapPort) {
		this.ldapPort = ldapPort;
	}
	public int getLdapVersion() {
		return ldapVersion;
	}
	public void setLdapVersion(int ldapVersion) {
		this.ldapVersion = ldapVersion;
	}
	public String getLdapSearchScope() {
		return ldapSearchScope;
	}
	public void setLdapSearchScope(String ldapSearchScope) {
		this.ldapSearchScope = ldapSearchScope;
	}
	public String getLdapSearchBase() {
		return ldapSearchBase;
	}
	public void setLdapSearchBase(String ldapSearchBase) {
		this.ldapSearchBase = ldapSearchBase;
	}
	public String getLdapSearchFilter() {
		return ldapSearchFilter;
	}
	public void setLdapSearchFilter(String ldapSearchFilter) {
		this.ldapSearchFilter = ldapSearchFilter;
	}
	public String getLdapAttrOnly() {
		return ldapAttrOnly;
	}
	public void setLdapAttrOnly(String ldapAttrOnly) {
		this.ldapAttrOnly = ldapAttrOnly;
	}
	public String getLdapAttributes() {
		return ldapAttributes;
	}
	public void setLdapAttributes(String ldapAttributes) {
		this.ldapAttributes = ldapAttributes;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public ArrayList getSegSQLTablesAL() {
		return segSQLTablesAL;
	}
	public void setSegSQLTablesAL(ArrayList segSQLTablesAL) {
		this.segSQLTablesAL = segSQLTablesAL;
	}
	public ArrayList getSegSQLColumn1AL() {
		return segSQLColumn1AL;
	}
	public void setSegSQLColumn1AL(ArrayList segSQLColumn1AL) {
		this.segSQLColumn1AL = segSQLColumn1AL;
	}
	public ArrayList getSegSQLColumn2AL() {
		return segSQLColumn2AL;
	}
	public void setSegSQLColumn2AL(ArrayList segSQLColumn2AL) {
		this.segSQLColumn2AL = segSQLColumn2AL;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSourceFactTableName() {
		return sourceFactTableName;
	}
	public void setSourceFactTableName(String sourceFactTableName) {
		this.sourceFactTableName = sourceFactTableName;
	}
	public String getTargetFactTableName() {
		return targetFactTableName;
	}
	public void setTargetFactTableName(String targetFactTableName) {
		this.targetFactTableName = targetFactTableName;
	}
	public ArrayList getMeasureColmnsAL() {
		return measureColmnsAL;
	}
	public void setMeasureColmnsAL(ArrayList measureColmnsAL) {
		this.measureColmnsAL = measureColmnsAL;
	}
	public ArrayList getJoinColmnsAL() {
		return joinColmnsAL;
	}
	public void setJoinColmnsAL(ArrayList joinColmnsAL) {
		this.joinColmnsAL = joinColmnsAL;
	}
	public String getPeriodColumn() {
		return periodColumn;
	}
	public void setPeriodColumn(String periodColumn) {
		this.periodColumn = periodColumn;
	}
	public String getPeriodValues() {
		return periodValues;
	}
	public void setPeriodValues(String periodValues) {
		this.periodValues = periodValues;
	}

	ArrayList targetFactTablesAL =new ArrayList<>();

	public ArrayList getTargetFactTablesAL() {
	return targetFactTablesAL;
	}
	public void setTargetFactTablesAL(ArrayList targetFactTablesAL) {
	this.targetFactTablesAL = targetFactTablesAL;
	}
	public String getTargetDimTable() {
		return targetDimTable;
	}
	public void setTargetDimTable(String targetDimTable) {
		this.targetDimTable = targetDimTable;
	}
	public String getSegmentTrigerSQL() {
		return segmentTrigerSQL;
	}
	public void setSegmentTrigerSQL(String segmentTrigerSQL) {
		this.segmentTrigerSQL = segmentTrigerSQL;
	}
	public String getRowWidSQL() {
		return rowWidSQL;
	}
	public void setRowWidSQL(String rowWidSQL) {
		this.rowWidSQL = rowWidSQL;
	}
	public String getDetailTable() {
		return detailTable;
	}
	public void setDetailTable(String detailTable) {
		this.detailTable = detailTable;
	}
	public String getFactTable() {
		return factTable;
	}
	public void setFactTable(String factTable) {
		this.factTable = factTable;
	}
	public String getDataTable() {
		return dataTable;
	}
	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}


	 
	
	public String getSegmentSQL() {
		return segmentSQL;
	}
	public void setSegmentSQL(String segmentSQL) {
		this.segmentSQL = segmentSQL;
	}
	public int getNoOfSegments() {
		return noOfSegments;
	}
	public void setNoOfSegments(int noOfSegments) {
		this.noOfSegments = noOfSegments;
	}
	public String getConnName_Data() {
		System.out.println("connName_Data--------->>>"+connName_Data);
		return connName_Data;
	}
	public void setConnName_Data(String connName_Data) {
		this.connName_Data = connName_Data;
	}
	public String getHostName_Data() {
		return hostName_Data;
	}
	public void setHostName_Data(String hostName_Data) {
		this.hostName_Data = hostName_Data;
	}
	public String getPortNo_Data() {
		return portNo_Data;
	}
	public void setPortNo_Data(String portNo_Data) {
		this.portNo_Data = portNo_Data;
	}
	public String getDbName_Data() {
		return dbName_Data;
	}
	public void setDbName_Data(String dbName_Data) {
		this.dbName_Data = dbName_Data;
	}
	public String getUserName_Data() {
		return userName_Data;
	}
	public void setUserName_Data(String userName_Data) {
		this.userName_Data = userName_Data;
	}
	public String getPass_Data() {
		return pass_Data;
	}
	public void setPass_Data(String pass_Data) {
		this.pass_Data = pass_Data;
	}
	public String getConnType_Data() {
		return connType_Data;
	}
	public void setConnType_Data(String connType_Data) {
		this.connType_Data = connType_Data;
	}
	public String getConnName_DW() {
		return connName_DW;
	}
	public void setConnName_DW(String connName_DW) {
		this.connName_DW = connName_DW;
	}
	public String getHostName_DW() {
		return hostName_DW;
	}
	public void setHostName_DW(String hostName_DW) {
		this.hostName_DW = hostName_DW;
	}
	public String getPortNo_DW() {
		return portNo_DW;
	}
	public void setPortNo_DW(String portNo_DW) {
		this.portNo_DW = portNo_DW;
	}
	public String getDbName_DW() {
		return dbName_DW;
	}
	public void setDbName_DW(String dbName_DW) {
		this.dbName_DW = dbName_DW;
	}
	public String getUserName_DW() {
		return userName_DW;
	}
	public void setUserName_DW(String userName_DW) {
		this.userName_DW = userName_DW;
	}
	public String getPass_DW() {
		return pass_DW;
	}
	public void setPass_DW(String pass_DW) {
		this.pass_DW = pass_DW;
	}
	public String getConnType_DW() {
		return connType_DW;
	}
	public void setConnType_DW(String connType_DW) {
		this.connType_DW = connType_DW;
	}

	public void editConfig(){  // code change Menaka 12MAR2014
		
//		JOptionPane.showMessageDialog(null, "begin------->>>"+connName_Data);
		statusMsg="";
		try{
			PropUtil prop=new PropUtil();
		
		String dir=prop.getProperty("HIERARCHY_XML_DIR");
		String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
		if(!dir.equals("")){
		Document doc=Globals.openXMLFile(dir, configFileName);
		
//		<Data_Connection>	
		NodeList ndlist=doc.getElementsByTagName("Data_Connection");
		Node nd=ndlist.item(0);
		NodeList ndlist1=nd.getChildNodes();
		for(int i=0;i<ndlist1.getLength();i++){
			Node nd1=ndlist1.item(i);
		
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("ConnectionName")){
				
				connName_Data=nd1.getTextContent();	
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("HostName")){
				
				hostName_Data=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("PortName")){
				
				portNo_Data=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("DBName")){
				dbName_Data=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("UserName")){
				userName_Data=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Password")){
				pass_Data=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("ConnectionType")){
				connType_Data=nd1.getTextContent();
			}

		}
		

//		<DW_Connection>
		ndlist=doc.getElementsByTagName("DW_Connection");
		 nd=ndlist.item(0);
		 ndlist1=nd.getChildNodes();
		for(int i=0;i<ndlist1.getLength();i++){
			Node nd1=ndlist1.item(i);
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("ConnectionName")){
				
				connName_DW=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("HostName")){
				
				hostName_DW=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("PortName")){
				
				portNo_DW=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("DBName")){
				dbName_DW=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("UserName")){
				userName_DW=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Password")){
				pass_DW=nd1.getTextContent();
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("ConnectionType")){
				connType_DW=nd1.getTextContent();
			}
		}
		
//		<No_Of_segments>
		ndlist=doc.getElementsByTagName("No_Of_segments");
		Node nd1=ndlist.item(0);
		noOfSegments=Integer.parseInt(nd1.getTextContent());
		
//		<Segment_SQL>
		ndlist=doc.getElementsByTagName("Segment_SQL");
		 nd1=ndlist.item(0);
		segmentSQL=nd1.getTextContent();
	
//		<Segment_Trigger_SQL>
		ndlist=doc.getElementsByTagName("Segment_Trigger_SQL");
		nd=ndlist.item(0);
		 ndlist1=nd.getChildNodes();
	
		 for(int i=0;i<ndlist1.getLength();i++){
			 nd1=ndlist1.item(i);
			 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("SQL")){
				 segmentTrigerSQL=nd1.getTextContent();
				}
		 }
	 
//		 <row_wid_SQL>
		 ndlist=doc.getElementsByTagName("row_wid_SQL");	
		 nd1=ndlist.item(0);
		 rowWidSQL=nd1.getTextContent();

//		 <Fact_Tables>
		 ndlist=doc.getElementsByTagName("Fact_Tables");	
			 nd=ndlist.item(0); 
			 ndlist1=nd.getChildNodes();

			 for(int i=0;i<ndlist1.getLength();i++){
				 nd1=ndlist1.item(i);
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Table")){
					
				 for(int m=0;m<nd1.getAttributes().getLength();m++){
						 System.out.println(" nd1.getAttributes();------>>"+ nd1.getAttributes().item(m).getNodeValue() );
						 if(nd1.getAttributes().item(m).getNodeName().equals("Source_Fact_TableName")){
							 sourceFactTableName= nd1.getAttributes().item(m).getNodeValue();
						 }
						 if(nd1.getAttributes().item(m).getNodeName().equals("Target_Fact_TableName")){
							 targetFactTableName	= nd1.getAttributes().item(m).getNodeValue();
						 }
						 
					 }
					
						NodeList ndlist2= nd1.getChildNodes();
						 NodeList ndlist3;
						 Node nod,nod1;
						 for(int j=0;j<ndlist2.getLength();j++){
							  nod=ndlist2.item(j);
							  
							
							  
							 if(nod.getNodeType()==Node.ELEMENT_NODE  && nod.getNodeName().equalsIgnoreCase("Measure_Columns")){
								  ndlist3= nod.getChildNodes();
								  measureColmnsAL=new ArrayList<>();
								 for(int k=0;k<ndlist3.getLength();k++){
									  nod1=ndlist3.item(k);
									 if(nod1.getNodeType()==Node.ELEMENT_NODE){
										 measureColmnsAL.add(nod.getTextContent());
										}
								 }
								
						
							
								 
								}
							 
							 if(nod.getNodeType()==Node.ELEMENT_NODE  && nod.getNodeName().equalsIgnoreCase("Join_Columns")){
								  ndlist3= nod.getChildNodes();
								  joinColmnsAL=new ArrayList<>();
								  for(int k=0;k<ndlist3.getLength();k++){
									  nod1=ndlist3.item(k);
									 if(nod1.getNodeType()==Node.ELEMENT_NODE){
										 joinColmnsAL.add(nod.getTextContent());
										}
								 }
								
								}
							 
							 if(nod.getNodeType()==Node.ELEMENT_NODE  && nod.getNodeName().equalsIgnoreCase("Period_Column")){
								 periodColumn=nod.getTextContent();
								 System.out.println("periodColumn===========>>>"+periodColumn);
							 }
							 if(nod.getNodeType()==Node.ELEMENT_NODE  && nod.getNodeName().equalsIgnoreCase("Period_Values")){
								 periodValues=nod.getTextContent();
								 System.out.println("periodValues===========>>>"+periodValues);
								 
								 setPeriodValues(periodValues);
							 }
					 
					}
				
			 }
		 
			 }
		 
//		<Target_DIM_Tables>
		 ndlist=doc.getElementsByTagName("Target_DIM_Tables");
			
			
			 nd=ndlist.item(0);
			 ndlist1=nd.getChildNodes();
		
			 for(int i=0;i<ndlist1.getLength();i++){
				 nd1=ndlist1.item(i);
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Dim_Table_Name")){
					 targetDimTable =nd1.getTextContent();
					}
			 }
		 
		
//		 <Target_Fact_Tables>
		 ndlist=doc.getElementsByTagName("Target_Fact_Tables");
			
			 nd=ndlist.item(0);
			 ndlist1=nd.getChildNodes();
		
			 targetFactTablesAL=new ArrayList<>();
			 for(int i=0;i<ndlist1.getLength();i++){
				 nd1=ndlist1.item(i);
				 if(nd1.getNodeType()==Node.ELEMENT_NODE){
					 targetFactTablesAL.add(nd1.getTextContent());
					
					}
				
			 }
			 
//			 <Mail_Configuration>
			 ndlist=doc.getElementsByTagName("Mail_Configuration");
			 nd=ndlist.item(0);
			 ndlist1=nd.getChildNodes();
		
			 for(int i=0;i<ndlist1.getLength();i++){
				 nd1=ndlist1.item(i);
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_ID_Send_by")){
					 emailID =nd1.getTextContent();
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Password")){
					 password =nd1.getTextContent();
					}
			 }
			 
			 
			 
//			 <Segment_SQLS>
			 ndlist=doc.getElementsByTagName("Segment_SQLS");
			 nd=ndlist.item(0);
			 ndlist1=nd.getChildNodes(); 
			  segSQLTablesAL=new ArrayList<>();
			  segSQLColumn1AL=new ArrayList<>();
			  segSQLColumn2AL=new ArrayList<>();
			
			 for(int i=0;i<ndlist1.getLength();i++){
				 nd1=ndlist1.item(i);
				 
				 if(nd1.getNodeType()==Node.ELEMENT_NODE){
					
					 for(int j=0;j<nd1.getAttributes().getLength();j++){
						 if(nd1.getAttributes().item(j).getNodeName().equals("Column_Name1")){
							 segSQLColumn1AL.add(nd1.getAttributes().item(j).getNodeValue());
						 }else  if(nd1.getAttributes().item(j).getNodeName().equals("Column_Name2")){
							 segSQLColumn2AL.add(nd1.getAttributes().item(j).getNodeValue());
						 }else if(nd1.getAttributes().item(j).getNodeName().equals("Table_Name")){
							 segSQLTablesAL.add(nd1.getAttributes().item(j).getNodeValue());
						 }
						
					 }
					 
					}
				 
			 }
			 
//			 <LDAP_SERVER>
			 ndlist=doc.getElementsByTagName("LDAP_SERVER");
			 nd=ndlist.item(0);
			 ndlist1=nd.getChildNodes();
		
			 for(int i=0;i<ndlist1.getLength();i++){
				 nd1=ndlist1.item(i);
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Domain_Name")){
				String	 ldapDomainNam =nd1.getTextContent();
				ldapDomainName=ldapDomainNam.substring(0,ldapDomainNam.length()-1);
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Ldap_Host")){
					 ldapHost =nd1.getTextContent();
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Ldap_Port")){
					 ldapPort =Integer.parseInt(nd1.getTextContent());
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Ldap_Version")){
					 ldapVersion =Integer.parseInt(nd1.getTextContent());
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Search_Scope")){
					 ldapSearchScope =nd1.getTextContent();
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Search_Filter")){
					 ldapSearchFilter =nd1.getTextContent();
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Attribute_Only")){
					 ldapAttrOnly =nd1.getTextContent();
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Attributes")){
					 ldapAttributes =nd1.getTextContent();
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Search_Base")){
					 ldapSearchBase =nd1.getTextContent();
					}
				 
			 }
		
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void updateConfig(){
		
		System.out.println("Update Method**************************************");
		statusMsg="";
		String validation="Valid";
		String dir="";
		String configFileName="";
		Document doc = null;
		try{
			PropUtil prop=new PropUtil();
		
		 dir=prop.getProperty("HIERARCHY_XML_DIR");
		 configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
		if(!dir.equals("")){
		 doc=Globals.openXMLFile(dir, configFileName);
		
		
//		<Data_Connection>	
		NodeList ndlist=doc.getElementsByTagName("Data_Connection");
		Node nd=ndlist.item(0);
		NodeList ndlist1=nd.getChildNodes();
		Node nd1;
		for(int i=0;i<ndlist1.getLength();i++){
			 nd1=ndlist1.item(i);
		
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("ConnectionName")){
				
				if(connName_Data==null||connName_Data.equals("")){
					validation="Invalid";
					statusMsg="Enter the Connection Name in DB Connection (Segment) section";
					return;
				}else{
					nd1.setTextContent(connName_Data);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("HostName")){
					
				if(hostName_Data==null||hostName_Data.equals("")){
					validation="Invalid";
					statusMsg="Enter the Host Name in DB Connection (Segment) section";
					return;
				}else{
					nd1.setTextContent(hostName_Data);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("PortName")){
				
			if(portNo_Data==null||portNo_Data.equals("")){
					validation="Invalid";
					statusMsg="Enter the Port Number in DB Connection (Segment) section";
					return;
				}else{
					nd1.setTextContent(portNo_Data);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("DBName")){
				
				if(dbName_Data==null||dbName_Data.equals("")){
					validation="Invalid";
					statusMsg="Enter the DB Name in DB Connection (Segment) section";
					return;
				}else{
					nd1.setTextContent(dbName_Data);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("UserName")){
				
				if(userName_Data==null||userName_Data.equals("")){
					validation="Invalid";
					statusMsg="Enter the User Name in DB Connection (Segment) section";
					return;
				}else{
					nd1.setTextContent(userName_Data);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Password")){
				
				if(pass_Data==null||pass_Data.equals("")){
					validation="Invalid";
					statusMsg="Enter the Password in DB Connection (Segment) section";
					return;
				}else{
					nd1.setTextContent(pass_Data);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("ConnectionType")){
				
				if(connType_Data==null||connType_Data.equals("")){
					validation="Invalid";
					statusMsg="Enter the Connection Type in DB Connection (Segment) section";
					return;
				}else{
					nd1.setTextContent(connType_Data);
				}
			}

		}
		

//		<DW_Connection>
		ndlist=doc.getElementsByTagName("DW_Connection");
		 nd=ndlist.item(0);
		 ndlist1=nd.getChildNodes();
		for(int i=0;i<ndlist1.getLength();i++){
			 nd1=ndlist1.item(i);
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("ConnectionName")){
				
				if(connName_DW==null||connName_DW.equals("")){
					validation="Invalid";
					statusMsg="Enter the Connection Name in DB Connection (Data Warehouse)";
					return;
				}else{
					nd1.setTextContent(connName_DW);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("HostName")){
				
				if(hostName_DW==null||hostName_DW.equals("")){
					validation="Invalid";
					statusMsg="Enter the Host Name in DB Connection (Data Warehouse)";
					return;
				}else{
					nd1.setTextContent(hostName_DW);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("PortName")){
				
				if(portNo_DW==null||portNo_DW.equals("")){
					validation="Invalid";
					statusMsg="Enter the Port Number in DB Connection (Data Warehouse)";
					return;
				}else{
					nd1.setTextContent(portNo_DW);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("DBName")){
				
				if(dbName_DW==null||dbName_DW.equals("")){
					validation="Invalid";
					statusMsg="Enter the DB Name in DB Connection (Data Warehouse)";
					return;
				}else{
					nd1.setTextContent(dbName_DW);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("UserName")){
			
				if(userName_DW==null||userName_DW.equals("")){
					validation="Invalid";
					statusMsg="Enter the User Name in DB Connection (Data Warehouse)";
					return;
				}else{
					nd1.setTextContent(userName_DW);
				}
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Password")){
				
				if(pass_DW==null||pass_DW.equals("")){
					validation="Invalid";
					statusMsg="enter the Password in DB Connection (Data Warehouse)";
					return;
				}else{
					nd1.setTextContent(pass_DW);
				}
				
			}
			if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("ConnectionType")){
				
				if(connType_DW==null||connType_DW.equals("")){
					validation="Invalid";
					statusMsg="Enter the Connection Type in DB Connection (Data Warehouse)";
					return;
				}else{
					nd1.setTextContent(connType_DW);
				}
			}
		}
		
		
	
		
//		<No_Of_segments>
		if(noOfSegments<=0||String.valueOf(noOfSegments).equals("")){
			validation="Invalid";
			statusMsg="Enter the No. of Segments in Segment section.";
			return;
		}else{
			ndlist=doc.getElementsByTagName("No_Of_segments");
			 nd1=ndlist.item(0);
			nd1.setTextContent(String.valueOf(noOfSegments));
		}
		
	
		
//		<Segment_SQL>
		if(segmentSQL==null||segmentSQL.equals("")){
			validation="Invalid";
			statusMsg="Enter the Segment SQL in Segment section.";
			return;
		}else{
		ndlist=doc.getElementsByTagName("Segment_SQL");
		 nd1=ndlist.item(0);
		nd1.setTextContent(segmentSQL);
		}
	
//		<Segment_Trigger_SQL>
		if(segmentTrigerSQL==null||segmentTrigerSQL.equals("")){
			validation="Invalid";
			statusMsg="Enter the Segment Trigger SQL in Segment section.";
			return;
		}else{
		ndlist=doc.getElementsByTagName("Segment_Trigger_SQL");
		nd=ndlist.item(0);
		 ndlist1=nd.getChildNodes();
	
		 for(int i=0;i<ndlist1.getLength();i++){
			 nd1=ndlist1.item(i);
			 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("SQL")){
				 nd1.setTextContent(segmentTrigerSQL);
				}
		 }
		}
//		 <row_wid_SQL>
		if(rowWidSQL==null||rowWidSQL.equals("")){
			validation="Invalid";
			statusMsg="Enter the List of Period SQL  in Segment section.";  //Enter the row wid SQL in segment
			return;
		}else{
		 ndlist=doc.getElementsByTagName("row_wid_SQL");	
		 nd1=ndlist.item(0);
		 nd1.setTextContent(rowWidSQL);
		}
		 
		
		 

//		 <Fact_Tables>
		 ndlist=doc.getElementsByTagName("Fact_Tables");	
			 nd=ndlist.item(0); 
			 ndlist1=nd.getChildNodes();

			 for(int i=0;i<ndlist1.getLength();i++){
				 nd1=ndlist1.item(i);
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Table")){
					
				 for(int m=0;m<nd1.getAttributes().getLength();m++){
//						 System.out.println(" nd1.getAttributes();------>>"+ nd1.getAttributes().item(m).getNodeValue() );
						 if(nd1.getAttributes().item(m).getNodeName().equals("Source_Fact_TableName")){
							 if(sourceFactTableName==null||sourceFactTableName.equals("")){
									validation="Invalid";
									statusMsg="Enter the Source fact table name in Default Fact Configuration section.";
									return;
								}else{
							 nd1.getAttributes().item(m).setNodeValue(sourceFactTableName);
								}
							
						 }
						 if(nd1.getAttributes().item(m).getNodeName().equals("Target_Fact_TableName")){
							 
							 if(targetFactTableName==null||targetFactTableName.equals("")){
									validation="Invalid";
									statusMsg="Enter the Target fact table name in Default Fact Configuration section.";
									return;
								}else{
							 nd1.getAttributes().item(m).setNodeValue(targetFactTableName);
								}
							 
						 }
						 
					 }
					
						NodeList ndlist2= nd1.getChildNodes();
						 NodeList ndlist3;
						 Node nod,nod1;
						 for(int j=0;j<ndlist2.getLength();j++){
							  nod=ndlist2.item(j);
							 if(nod.getNodeType()==Node.ELEMENT_NODE  && nod.getNodeName().equalsIgnoreCase("Measure_Columns")){
								  ndlist3= nod.getChildNodes();
//								  measureColmnsAL=new ArrayList<>();
								 for(int k=0,m=0;k<ndlist3.getLength();k++){
									  nod1=ndlist3.item(k);
									 if(nod1.getNodeType()==Node.ELEMENT_NODE){
										 nod1.setTextContent((String) measureColmnsAL.get(m));
										 m++;
									 }
								 }
								
								}
							 
							 if(nod.getNodeType()==Node.ELEMENT_NODE  && nod.getNodeName().equalsIgnoreCase("Join_Columns")){
								  ndlist3= nod.getChildNodes();
//								  joinColmnsAL=new ArrayList<>();
								  for(int k=0,l=0;k<ndlist3.getLength();k++){
									  nod1=ndlist3.item(k);
									 if(nod1.getNodeType()==Node.ELEMENT_NODE){

										 nod1.setTextContent((String) joinColmnsAL.get(l));
										 l++;
										}
								 }
								
								}
							 
							 if(nod.getNodeType()==Node.ELEMENT_NODE  && nod.getNodeName().equalsIgnoreCase("Period_Column")){
								
								 if(periodColumn==null||periodColumn.equals("")){
										validation="Invalid";
										statusMsg="Enter the Period column in Default Fact Configuration section.";
										return;
									}else{
										nod.setTextContent(periodColumn);
									}
							 }
							 if(nod.getNodeType()==Node.ELEMENT_NODE  && nod.getNodeName().equalsIgnoreCase("Period_Values")){
								 
								 if(periodValues==null||periodValues.equals("")){
										validation="Invalid";
										statusMsg="Enter the Period values in Default Fact Configuration section.";
										return;
									}else{
										nod.setTextContent(getPeriodValues());
									}
								 
							 }
					 
					}
				
			 }
		 
			 }
			 
			 
//				<Target_DIM_Tables>
			 
			 if(targetDimTable==null||targetDimTable.equals("")){
					validation="Invalid";
					statusMsg="Enter the Target Dim table in Target Dim Tables section.";
					return;
				}else{
					 ndlist=doc.getElementsByTagName("Target_DIM_Tables");
					 nd=ndlist.item(0);
					 ndlist1=nd.getChildNodes();
				
					 for(int i=0;i<ndlist1.getLength();i++){
						 nd1=ndlist1.item(i);
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Dim_Table_Name")){
							 nd1.setTextContent(targetDimTable);
							}
					 }
					
				}
			 
				
				 
				
//				 <Target_Fact_Tables>
			 if(targetFactTablesAL==null||targetFactTablesAL.equals("")){
					validation="Invalid";
					statusMsg="Enter the Target Fact table";
					return;
				}else{
					
					
					
				 ndlist=doc.getElementsByTagName("Target_Fact_Tables");
					
					 nd=ndlist.item(0);
					 
					 ndlist1=nd.getChildNodes();
				
//					 targetFactTablesAL=new ArrayList<>();
					 for(int i=0,j=0;i<ndlist1.getLength();i++){
						 nd1=ndlist1.item(i);
						 if(nd1.getNodeType()==Node.ELEMENT_NODE){
							 nd1.setTextContent((String) targetFactTablesAL.get(j));
							 j++;
							}
						
					 }
				}
			 
//			 <Mail_Configuration>
	 
			 ndlist=doc.getElementsByTagName("Mail_Configuration");
			 nd=ndlist.item(0);
			 ndlist1=nd.getChildNodes();
		
			 for(int i=0;i<ndlist1.getLength();i++){
				 nd1=ndlist1.item(i);
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_ID_Send_by")){
					
					 if(emailID==null||emailID.equals("")){
							validation="Invalid";
							statusMsg="Enter the EMail ID in sender E-Mail ID section";
							return;
						}else{
							nd1.setTextContent(emailID);
						}
					}
				 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Password")){
					
					 if(password==null||password.equals("")){
							validation="Invalid";
							statusMsg="Enter the Pasword in sender E-Mail ID section";
							return;
						}else{
							nd1.setTextContent(password);
						} 
					}
			 }
					 
					 
					 
//					 <Segment_SQLS>
					 ndlist=doc.getElementsByTagName("Segment_SQLS");
					 nd=ndlist.item(0);
					 ndlist1=nd.getChildNodes(); 
//					  segSQLTablesAL=new ArrayList<>();
//					  segSQLColumn1AL=new ArrayList<>();
//					  segSQLColumn2AL=new ArrayList<>();
					 
					 System.out.println("segSQLTablesAL==========>>>"+segSQLTablesAL);
					 System.out.println("segSQLColumn1AL==========>>>"+segSQLColumn1AL);
					 System.out.println("segSQLColumn2AL==========>>>"+segSQLColumn2AL);
					
					 for(int i=0,mm=0;i<ndlist1.getLength();i++){
						 nd1=ndlist1.item(i);
						 
						 if(nd1.getNodeType()==Node.ELEMENT_NODE){
							
							 for(int j=0;j<nd1.getAttributes().getLength();j++){
								 if(nd1.getAttributes().item(j).getNodeName().equals("Column_Name1")){
								
									 nd1.getAttributes().item(j).setNodeValue((String) segSQLColumn1AL.get(mm));
								 }else  if(nd1.getAttributes().item(j).getNodeName().equals("Column_Name2")){
								
									 nd1.getAttributes().item(j).setNodeValue((String) segSQLColumn2AL.get(mm));
								 }else if(nd1.getAttributes().item(j).getNodeName().equals("Table_Name")){
							
									 
									 nd1.getAttributes().item(j).setNodeValue((String) segSQLTablesAL.get(mm));
								 }
								 
							 }
							 
							
							 mm++;
							}
						
					 }
					 
					
					 
//					 <LDAP_SERVER>
					 
					 if(ldapDomainName==null||ldapDomainName.equals("")){
						 validation="Invalid";
							statusMsg="Enter the Domain Name in LDAP Server Information section.";
							return;
					 }
					 if(ldapHost==null||ldapHost.equals("")){
						 validation="Invalid";
							statusMsg="Enter the LDAP Host in LDAP Server Information section.";
							return;
					 }
					 if(ldapSearchScope==null||ldapSearchScope.equals("")){
						 validation="Invalid";
							statusMsg="Enter the Search Scope in LDAP Server Information section.";
							return;
					 }
					 if(ldapSearchFilter==null||ldapSearchFilter.equals("")){
						 validation="Invalid";
							statusMsg="Enter the Search Filter in LDAP Server Information section.";
							return;
					 }
					 if(ldapAttrOnly==null||ldapAttrOnly.equals("")){
						 validation="Invalid";
							statusMsg="Enter the Attribute only in LDAP Server Information section.";
							return;
					 }
					 if(ldapAttributes==null||ldapAttributes.equals("")){
						 validation="Invalid";
							statusMsg="Enter the Attributes in LDAP Server Information section.";
							return;
					 }
					 if(ldapSearchBase==null||ldapSearchBase.equals("")){
						 validation="Invalid";
							statusMsg="Enter the Search Base in LDAP Server Information section.";
							return;
					 }
					
					 
					 
					 
					 ndlist=doc.getElementsByTagName("LDAP_SERVER");
					 nd=ndlist.item(0);
					 ndlist1=nd.getChildNodes();
				
					 for(int i=0;i<ndlist1.getLength();i++){
						 nd1=ndlist1.item(i);
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Domain_Name")){
							  nd1.setTextContent(ldapDomainName.concat("\\"));
							}
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Ldap_Host")){
							 nd1.setTextContent(ldapHost);
							}
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Ldap_Port")){
							 nd1.setTextContent(String.valueOf(ldapPort));
							}
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Ldap_Version")){
							 nd1.setTextContent(String.valueOf(ldapVersion));
							}
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Search_Scope")){
							 nd1.setTextContent(ldapSearchScope);
							}
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Search_Filter")){
							 nd1.setTextContent(ldapSearchFilter);
							}
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Attribute_Only")){
							 nd1.setTextContent(ldapAttrOnly);
							}
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Attributes")){
							 nd1.setTextContent(ldapAttributes);
							}
						 if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Search_Base")){
							 nd1.setTextContent(ldapSearchBase);
							}
					 }
					
					 System.out.println("***** Updated LDAP_SERVER *****"); 
				
		System.out.println("validation========>>"+validation);
		
		if(validation.equals("Valid")){
			Globals.writeXMLFile(doc, dir, configFileName);
			statusMsg="System Configuration updated successfully.";
			System.out.println("***********System Configuration updated successfully.");
		}
		
		
		}	}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	public void factTableListFromField(ValueChangeEvent e){

	//	config:factTables::4:j_idt62
		String[] indexS=e.getComponent().getClientId().split(":");
		int index=Integer.parseInt(indexS[3]);
		targetFactTablesAL.set(index, e.getNewValue().toString());
	
	}
	
	public void joinColsFromField(ValueChangeEvent e){

		//	config:factTables::4:j_idt62
			String[] indexS=e.getComponent().getClientId().split(":");
			int index=Integer.parseInt(indexS[3]);
			joinColmnsAL.set(index, e.getNewValue().toString().trim());
			System.out.println("joinColmnsAL===========>>>"+joinColmnsAL);
		}
	
	public void measureColsFromField(ValueChangeEvent e){

		//	config:factTables::4:j_idt62
			String[] indexS=e.getComponent().getClientId().split(":");
			int index=Integer.parseInt(indexS[3]);
			measureColmnsAL.set(index, e.getNewValue().toString().trim());
			System.out.println("measureColmnsAL===========>>>"+measureColmnsAL);
		
		}
	
	public void segSQLTableFromField(ValueChangeEvent e){

		//	config:factTables::4:j_idt62
			String[] indexS=e.getComponent().getClientId().split(":");
			int index=Integer.parseInt(indexS[3]);
			segSQLTablesAL.set(index, e.getNewValue().toString());
			
			System.out.println("segSQLTablesAL===========>>>"+segSQLTablesAL);
			
			
		}
	
	public void segSQLColumn1FromField(ValueChangeEvent e){

		//	config:factTables::4:j_idt62
			String[] indexS=e.getComponent().getClientId().split(":");
			int index=Integer.parseInt(indexS[3]);
			segSQLColumn1AL.set(index, e.getNewValue().toString());
			
			System.out.println("segSQLColumn1AL===========>>>"+segSQLColumn1AL);
		
		}
	 
	public void segSQLColumn2FromField(ValueChangeEvent e){

		//	config:factTables::4:j_idt62
			String[] indexS=e.getComponent().getClientId().split(":");
			int index=Integer.parseInt(indexS[3]);
			segSQLColumn2AL.set(index, e.getNewValue().toString());
			
			System.out.println("segSQLColumn2AL===========>>>"+segSQLColumn2AL);
		
		}
	 
	 

}
