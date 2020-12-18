
package beans;

import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class HeirarchyDataBean {

	// getter and setter method bean for QA id , date , time
String codevalue="";
String segmentNumber="";
String segments="";
String segmentID="";
String sourceTable = "";
boolean measure;
String joinSourceTable = "";
TreeMap joinTargetTable = new TreeMap<>();
String selectedJoinTargetTable = "";
String measureTargetTableName = "";
String joinTargetTableName = "";
String measureSourceTableName = "";
String joinSourceTableName = "";
String segCodeWithValue = "";
String userLoginID;
String emailID;
String accessStartDate;
String endDate;
String access;
private String actionDisabledStr;

private String attID;
private String attfileName;
private String attfileNameSubStr;
private String attfileType;
private String attfileSize;
private String emptyn;
private String attachStg;
private String attachUser;
private String uploadDetails;
private String uploadDetailsSubStr;
private String showAppRejBtn;
private String actionPerformedBy;
private String actionName;
private String actionDisplayName;
private String actionPerformedOn;
private String uploadedOn;

String passwordID;
boolean newcustomerval;
boolean activeboxval;
boolean disableboxval;


String editUserNamecheckBOXTXT;

public String getEditUserNamecheckBOXTXT() {
	return editUserNamecheckBOXTXT;
}
public void setEditUserNamecheckBOXTXT(String editUserNamecheckBOXTXT) {
	this.editUserNamecheckBOXTXT = editUserNamecheckBOXTXT;
}
String editUserNameTXT;
String edit_firstNameTXT;
String edit_lastNameTXT;
String edit_titleTXT;
String edit_ph1TXT;
String edit_ph2TXT;

String edit_ph3TXT;
String edit_Mail1TXT;
String edit_Mail2TXT;
String edit_addressTXT;
String edit_cityTXT;
String edit_stateTXT;
String edit_countryTXT;
String edit_zipTXT;





private String reSattID;
private String reSattfileName;
private String reSattfileType; 

private String reSattfileSize;
private String reSattachStg;
private String reSattachUser;
private String reSactionPerformedBy; 
private String reSactionName;
private String reSactionPerformedOn;
private String reSuploadeddate;





















public String getReSuploadeddate() {
	return reSuploadeddate;
}
public void setReSuploadeddate(String reSuploadeddate) {
	this.reSuploadeddate = reSuploadeddate;
}
public String getPasswordID() {
	return passwordID;
}
public void setPasswordID(String passwordID) {
	this.passwordID = passwordID;
}
int accessID;

String nodeName = "";
private String userName="";  // code change Menaka 20MAR2014
private String email="";
boolean superAdminPriv;  // code change Menaka 24MAR2014
private String rICRuleNode = "";
private String rICRule = "";
private String ruleNo = "";

public String getRuleNo() {
	return ruleNo;
}
public void setRuleNo(String ruleNo) {
	this.ruleNo = ruleNo;
}
public String getrICRule() {
	return rICRule;
}
public void setrICRule(String rICRule) {
	this.rICRule = rICRule;
}
public String getrICRuleNode() {
	return rICRuleNode;
}
public void setrICRuleNode(String rICRuleNode) {
	this.rICRuleNode = rICRuleNode;
}


public boolean isSuperAdminPriv() {
	return superAdminPriv;
}
public void setSuperAdminPriv(boolean superAdminPriv) {
	this.superAdminPriv = superAdminPriv;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getNodeName() {
	return nodeName;
}
public void setNodeName(String nodeName) {
	this.nodeName = nodeName;
}
public int getAccessID() {
	return accessID;
}
public void setAccessID(int accessID) {
	this.accessID = accessID;
}
public String getUserLoginID() {
	return userLoginID;
}
public void setUserLoginID(String userLoginID) {
	this.userLoginID = userLoginID;
}
public String getEmailID() {
	return emailID;
}
public void setEmailID(String emailID) {
	this.emailID = emailID;
}
public String getAccessStartDate() {
	return accessStartDate;
}
public void setAccessStartDate(String accessStartDate) {
	this.accessStartDate = accessStartDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getAccess() {
	return access;
}
public void setAccess(String access) {
	this.access = access;
}


public String getSegCodeWithValue() {
	return segCodeWithValue;
}
public void setSegCodeWithValue(String segCodeWithValue) {
	this.segCodeWithValue = segCodeWithValue;
}
public String getJoinSourceTableName() {
	return joinSourceTableName;
}
public void setJoinSourceTableName(String joinSourceTableName) {
	this.joinSourceTableName = joinSourceTableName;
}
public String getMeasureSourceTableName() {
	return measureSourceTableName;
}
public void setMeasureSourceTableName(String measureSourceTableName) {
	this.measureSourceTableName = measureSourceTableName;
}
public String getJoinTargetTableName() {
	return joinTargetTableName;
}
public void setJoinTargetTableName(String joinTargetTableName) {
	this.joinTargetTableName = joinTargetTableName;
}
public String getMeasureTargetTableName() {
	return measureTargetTableName;
}
public void setMeasureTargetTableName(String measureTargetTableName) {
	this.measureTargetTableName = measureTargetTableName;
}
public String getSelectedJoinTargetTable() {
	return selectedJoinTargetTable;
}
public void setSelectedJoinTargetTable(String selectedJoinTargetTable) {
	this.selectedJoinTargetTable = selectedJoinTargetTable;
}
public TreeMap getJoinTargetTable() {
	return joinTargetTable;
}
public void setJoinTargetTable(TreeMap joinTargetTable) {
	this.joinTargetTable = joinTargetTable;
}
public String getJoinSourceTable() {
	return joinSourceTable;
}
public void setJoinSourceTable(String joinSourceTable) {
	this.joinSourceTable = joinSourceTable;
}
public boolean isMeasure() {
	return measure;
}
public void setMeasure(boolean measure) {
	this.measure = measure;
}
ArrayList targetTable = new ArrayList<>();
String selectedTargetTable = "";
public String getSelectedTargetTable() {
	return selectedTargetTable;
}
public void setSelectedTargetTable(String selectedTargetTable) {
	this.selectedTargetTable = selectedTargetTable;
}
public ArrayList getTargetTable() {
	return targetTable;
}
public void setTargetTable(ArrayList targetTable) {
	this.targetTable = targetTable;
}


public String getSourceTable() {
	return sourceTable;
}
public void setSourceTable(String sourceTable) {
	this.sourceTable = sourceTable;
}
public String getSegmentID() {
	return segmentID;
}
public void setSegmentID(String segmentID) {
	this.segmentID = segmentID;
}
public String getSegments() {
	return segments;
}
public void setSegments(String segments) {
	this.segments = segments;
}
public String segmentValue="";
public String getSegmentValue() {
	return segmentValue;
}
public void setSegmentValue(String segmentValue) {
	this.segmentValue = segmentValue;
}
int numberOfSegment=0;

public String getSegmentNumber() {
	return segmentNumber;
}
public void setSegmentNumber(String segmentNumber) {
	this.segmentNumber = segmentNumber;
}

public int getNumberOfSegment() {
	return numberOfSegment;
}
public void setNumberOfSegment(int numberOfSegment) {
	this.numberOfSegment = numberOfSegment;
}

	
	public String getCodevalue() {
		return codevalue;
	}
	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}
	String namevalue="";
	public String getNamevalue() {
		return namevalue;
	}
	public void setNamevalue(String namevalue) {
		this.namevalue = namevalue;
	}
	
	TreeMap srcColumTM = new TreeMap<>();
	
	
	public TreeMap getSrcColumTM() {
//		System.out.println("srcColumTM Getter"+srcColumTM);
		return srcColumTM;
	}
	public void setSrcColumTM(TreeMap srcColumTM) {
		this.srcColumTM = srcColumTM;
//		System.out.println("srcColumTM Setter"+srcColumTM);
	}
	public HeirarchyDataBean(){
		
	}
	public HeirarchyDataBean(String codevalue,String namevalue){
		this.codevalue=codevalue;
		this.namevalue=namevalue;
	}
	//Start code change Jayaramu 12FEB14
	String dispalyName4SegPopup = "";
	public String getDispalyName4SegPopup() {
		return dispalyName4SegPopup;
	}
	public void setDispalyName4SegPopup(String dispalyName4SegPopup) {
		this.dispalyName4SegPopup = dispalyName4SegPopup;
	}
	public HeirarchyDataBean(int numberOfSegment,String segmentNumber,String SegmentValue,String dispalyName4SegPopup){
		this.numberOfSegment = numberOfSegment;
		this.segmentNumber = segmentNumber;
		this.segmentValue = SegmentValue;
		this.dispalyName4SegPopup = dispalyName4SegPopup;
	}
	String tableName = "";
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	String columnName = "";
	boolean primaryType;
	public boolean isPrimaryType() {
		return primaryType;
	}
	public void setPrimaryType(boolean primaryType) {
		this.primaryType = primaryType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	boolean overRideCodeCombinationFlag;
	public boolean isOverRideCodeCombinationFlag() {
		return overRideCodeCombinationFlag;
	}
	public void setOverRideCodeCombinationFlag(boolean overRideCodeCombinationFlag) {
		this.overRideCodeCombinationFlag = overRideCodeCombinationFlag;
	}
	String displayName4Disp = "";
	
	public String getDisplayName4Disp() {
		return displayName4Disp;
	}
	public void setDisplayName4Disp(String displayName4Disp) {
		this.displayName4Disp = displayName4Disp;
	}
	public HeirarchyDataBean(String Segment,String codevalue,String namevalue,String segmentID,String segCodeValue,String tableName, String columnName,boolean primarySelection,boolean overRideCodeCombinationFlag,String displayName4Disp){
		this.codevalue = codevalue;
		this.namevalue = namevalue;
		this.segments = Segment;
		this.segmentID = segmentID;
		this.segCodeWithValue = segCodeValue;
		this.tableName = tableName;
		this.columnName = columnName;
		this.primaryType = primarySelection;
		this.overRideCodeCombinationFlag = overRideCodeCombinationFlag;
		this.displayName4Disp = displayName4Disp;
		}
	//End code change Jayaramu 12FEB14
	
	TreeMap propertyTM = new TreeMap<>();
	public TreeMap getPropertyTM() {
		return propertyTM;
	}
	public void setPropertyTM(TreeMap propertTM) {
		this.propertyTM = propertTM;
	}
	String property = "";
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	String selectedSrcTable = "";
	public String getSelectedSrcTable() {
		return selectedSrcTable;
	}
	public void setSelectedSrcTable(String selectedSrcTable) {
		this.selectedSrcTable = selectedSrcTable;
	}
	TreeMap selectedSrcTableTM = new TreeMap<>();
	public TreeMap getSelectedSrcTableTM() {
		return selectedSrcTableTM;
	}
	public void setSelectedSrcTableTM(TreeMap selectedSrcTableTM) {
		this.selectedSrcTableTM = selectedSrcTableTM;
	}
	String selectedTargetColumn = "";
	public String getSelectedTargetColumn() {
		return selectedTargetColumn;
	}
	public void setSelectedTargetColumn(String selectedTargetColumn) {
		this.selectedTargetColumn = selectedTargetColumn;
	}
	TreeMap TargetColumn = new TreeMap<>();
	public TreeMap getTargetColumn() {
		return TargetColumn;
	}
	public void setTargetColumn(TreeMap targetColumn) {
		TargetColumn = targetColumn;
	}
	String additionalFunction4SrcColumn = "";
	public String getAdditionalFunction4SrcColumn() {
		return additionalFunction4SrcColumn;
	}
	public void setAdditionalFunction4SrcColumn(String additionalFunction4SrcColumn) {
		this.additionalFunction4SrcColumn = additionalFunction4SrcColumn;
	}
	String additionalFunction4TarColumn = "";
	public String getAdditionalFunction4TarColumn() {
		return additionalFunction4TarColumn;
	}
	public void setAdditionalFunction4TarColumn(String additionalFunction4TarColumn) {
		this.additionalFunction4TarColumn = additionalFunction4TarColumn;
	}
	String srcImageName4measure = "";
	public String getSrcImageName4measure() {
		return srcImageName4measure;
	}
	public void setSrcImageName4measure(String srcImageName4measure) {
		this.srcImageName4measure = srcImageName4measure;
	}
	String tarImageName4measure = "";
	public String getTarImageName4measure() {
		return tarImageName4measure;
	}
	public void setTarImageName4measure(String tarImageName4measure) {
		this.tarImageName4measure = tarImageName4measure;
	}
	//start code change Jayaramu 06FEB14
	public HeirarchyDataBean(String measureSourceTable,ArrayList measureTargetTable,boolean measure,String selectedTargetTable,
			String measureSourceTableName,String measureTargetTableName,TreeMap srcColumTM,TreeMap propertTM,String propert,
			String selectedSrcTable, TreeMap selectedSrcTableTM, String selectedTargetColumn, TreeMap TargetColumn,
			String additionalFunction4SrcColumn, String additionalFunction4TarColumn,String srcImageName4measure,String tarImageName4measure){
		this.sourceTable = measureSourceTable;
		this.targetTable = measureTargetTable;
		this.measure = measure;
		this.selectedTargetTable = selectedTargetTable;
		this.measureSourceTableName = measureSourceTableName;
		this.measureTargetTableName = measureTargetTableName;
		this.srcColumTM = srcColumTM;
		this.propertyTM =propertTM;
		this.property =propert; 
		this.selectedSrcTable =selectedSrcTable;
		this.selectedSrcTableTM =selectedSrcTableTM;
		this.selectedTargetColumn =selectedTargetColumn;
		this.TargetColumn =TargetColumn;
		this.additionalFunction4SrcColumn = additionalFunction4SrcColumn;
		this.additionalFunction4TarColumn = additionalFunction4TarColumn;
		this.srcImageName4measure = srcImageName4measure;
		this.tarImageName4measure = tarImageName4measure;
	}
	TreeMap joinsrcColumnTM = new TreeMap<>();
	public TreeMap getJoinsrcColumnTM() {
		return joinsrcColumnTM;
	}
	public void setJoinsrcColumnTM(TreeMap joinsrcColumnTM) {
		this.joinsrcColumnTM = joinsrcColumnTM;
	}
	public HeirarchyDataBean(String joinSourceTable,TreeMap joinTargetTable,String selectedJoinTargetTable,String joinSourceTableName,String joinTargetTableName,TreeMap srcColumTM){
		
		this.joinSourceTable = joinSourceTable;
		this.joinTargetTable = joinTargetTable;
		this.selectedJoinTargetTable = selectedJoinTargetTable;
		this.joinSourceTableName = joinSourceTableName;
		this.joinTargetTableName  = joinTargetTableName;
		this.joinsrcColumnTM = srcColumTM;
		}//end code change Jayaramu 06FEB14
	
	
	String segmentid="";
	
	public String getSegmentid() {
		return segmentid;
	}
	public void setSegmentid(String segmentid) {
		segmentid = segmentid;
	}
	String displayName="";
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	String sqltext="";
	public String getSqltext() {
		return sqltext;
	}
	public void setSqltext(String sqltext) {
		this.sqltext = sqltext;
	}
	TreeMap segColumnNameTM = new TreeMap<>();
	
	public TreeMap getSegColumnNameTM() {
		return segColumnNameTM;
	}
	public void setSegColumnNameTM(TreeMap segColumnNameTM) {
		this.segColumnNameTM = segColumnNameTM;
	}
	String segColumnName="";
	
	public String getSegColumnName() {
		return segColumnName;
	}
	public void setSegColumnName(String segColumnName) {
		this.segColumnName = segColumnName;
	}
	public HeirarchyDataBean(String Segmentid,String displayName,String sqltext,TreeMap segColumnNameTM,String segColumnName){
		this.segmentid = Segmentid;
		this.displayName = displayName;
		this.sqltext = sqltext;
		this.segColumnNameTM = segColumnNameTM;
		this.segColumnName=segColumnName;
		}
	
	//start code change Jayaramu 12MAR14
	public HeirarchyDataBean(String userLoginID,String emailID,String accessStartDate,String endDate,String access,int accessID,String nodeName,boolean superAdminPriv,
	String editUserNameTXT,String passwordID,Boolean newcustomerval,Boolean activeboxval,Boolean disableboxval,String edit_firstNameTXT,String edit_lastNameTXT,String edit_titleTXT,String edit_ph1TXT,String edit_ph2TXT
   ,String edit_ph3TXT,String edit_Mail1TXT,String edit_Mail2TXT,String edit_addressTXT,String edit_cityTXT,String edit_stateTXT,String edit_countryTXT,
	String edit_zipTXT){
		
		this.userLoginID = userLoginID;
		this.emailID = emailID;
		this.accessStartDate = accessStartDate;
		this.endDate = endDate;
		this.access = access;
		this.accessID = accessID;
		this.nodeName = nodeName;
		this.superAdminPriv=superAdminPriv;
		
		
		
		
		this.editUserNameTXT=editUserNameTXT;
		this.passwordID=passwordID;
		this.newcustomerval=newcustomerval;
		this.activeboxval=activeboxval;
		this.disableboxval=disableboxval;
		
		this.edit_firstNameTXT=edit_firstNameTXT;
		this.edit_lastNameTXT=edit_lastNameTXT;
		this.edit_titleTXT=edit_titleTXT;
		this.edit_ph1TXT=edit_ph1TXT;
		this.edit_ph2TXT=edit_ph2TXT;

		this.edit_ph3TXT=edit_ph3TXT;
		this.edit_Mail1TXT=edit_Mail1TXT;
		this.edit_Mail2TXT=edit_Mail2TXT;
		this.edit_addressTXT=edit_addressTXT;
		this.edit_cityTXT=edit_cityTXT;
		this.edit_stateTXT=edit_stateTXT;
		this.edit_countryTXT=edit_countryTXT;
		this.edit_zipTXT=edit_zipTXT;
		// this.passwordID = passwordID;
				
	}//End code change Jayaramu 12MAR14
	
public HeirarchyDataBean(String rICRuleNode,String rICRule,String noOfRule){//code change Jayaramu 27MAR14
	
	this.rICRuleNode = rICRuleNode;
	this.rICRule = rICRule;
	this.ruleNo = noOfRule;
	
				
	}

String sourceTableName = "";

String subSourceTableName=""; // code change Menaka 03APR2014



public String getSubSourceTableName() {
	return subSourceTableName;
}
public void setSubSourceTableName(String subSourceTableName) {
	this.subSourceTableName = subSourceTableName;
}
public String getSourceTableName() {
	return sourceTableName;
}
public void setSourceTableName(String sourceTableName) {
	sourceTableName = sourceTableName;
}
String sourcecolumnName = "";
public String getSourcecolumnName() {
	return sourcecolumnName;
}
public void setSourcecolumnName(String sourcecolumnName) {
	this.sourcecolumnName = sourcecolumnName;
}
//TreeMap srcColumTM = new TreeMap<>();
String joinColumnOper = "";
public String getJoinColumnOper() {
	return joinColumnOper;
}
public void setJoinColumnOper(String joinColumnOper) {
	this.joinColumnOper = joinColumnOper;
}
TreeMap joinColumnOperTM = new TreeMap<>();
public TreeMap getJoinColumnOperTM() {
	return joinColumnOperTM;
}
public void setJoinColumnOperTM(TreeMap joinColumnOperTM) {
	this.joinColumnOperTM = joinColumnOperTM;
}
String tarTableName = "";
public String getTarTableName() {
	return tarTableName;
}
public void setTarTableName(String tarTableName) {
	this.tarTableName = tarTableName;
}
String tarColumnName = "";

public String getTarColumnName() {
	return tarColumnName;
}
public void setTarColumnName(String tarColumnName) {
	this.tarColumnName = tarColumnName;
}
TreeMap srcColumnTM = new TreeMap<>();
public TreeMap getSrcColumnTM() {
	return srcColumnTM;
}
public void setSrcColumnTM(TreeMap srcColumnTM) {
	this.srcColumnTM = srcColumnTM;
}
String srcConn = "";
public String getSrcConn() {
	return srcConn;
}
public void setSrcConn(String srcConn) {
	this.srcConn = srcConn;
}
String tarConn = "";
public String getTarConn() {
	return tarConn;
}
public void setTarConn(String tarConn) {
	this.tarConn = tarConn;
}
TreeMap srcTableTM = new TreeMap<>();

public TreeMap getSrcTableTM() {
	return srcTableTM;
}
public void setSrcTableTM(TreeMap srcTableTM) {
	this.srcTableTM = srcTableTM;
}
String additionalFunction4SrcWarColumn = "";
public String getAdditionalFunction4SrcWarColumn() {
	return additionalFunction4SrcWarColumn;
}
public void setAdditionalFunction4SrcWarColumn(
		String additionalFunction4SrcWarColumn) {
	this.additionalFunction4SrcWarColumn = additionalFunction4SrcWarColumn;
}
String additionalFunction4TarWarColumn = "";
public String getAdditionalFunction4TarWarColumn() {
	return additionalFunction4TarWarColumn;
}
public void setAdditionalFunction4TarWarColumn(
		String additionalFunction4TarWarColumn) {
	this.additionalFunction4TarWarColumn = additionalFunction4TarWarColumn;
}
String srcImageName4Whar = "";

public String getSrcImageName4Whar() {
	return srcImageName4Whar;
}
public void setSrcImageName4Whar(String srcImageName4Whar) {
	this.srcImageName4Whar = srcImageName4Whar;
}
String tarImageName4Whar = "";
public String getTarImageName4Whar() {
	return tarImageName4Whar;
}
public void setTarImageName4Whar(String tarImageName4Whar) {
	this.tarImageName4Whar = tarImageName4Whar;
}
public HeirarchyDataBean(String SourceTableName, String sourcecolumnName, String joinColumnOper, TreeMap joinColumnOperTM, 
		String tarTableName, String tarColumnName, TreeMap srcColumnTM, String srcConn, String tarConn,String subSourceTableName, TreeMap srcTableTM,
		String additionalFunction4SrcWarColumn, String additionalFunction4TarWarColumn,String srcImageName4Whar,String tarImageName4Whar){
	this.sourceTableName = SourceTableName;
	this.sourcecolumnName = sourcecolumnName;
	this.joinColumnOper = joinColumnOper;
	this.joinColumnOperTM = joinColumnOperTM;
	this.tarTableName = tarTableName;
	this.tarColumnName = tarColumnName;
	this.srcColumnTM = srcColumnTM;
	this.srcConn = srcConn;
	this.tarConn = tarConn;
	this.subSourceTableName=subSourceTableName;  // code change Menaka 01APR2014
	this.srcTableTM = srcTableTM;
	this.additionalFunction4TarWarColumn = additionalFunction4TarWarColumn;
	this.additionalFunction4SrcWarColumn = additionalFunction4SrcWarColumn;
	this.tarImageName4Whar = tarImageName4Whar;
	this.srcImageName4Whar = srcImageName4Whar;
}
String srcCloumn4incr = "";

public String getSrcCloumn4incr() {
	return srcCloumn4incr;
}
public void setSrcCloumn4incr(String srcCloumn4incr) {
	this.srcCloumn4incr = srcCloumn4incr;
}
TreeMap srcCloumnTM4incr = new TreeMap<>();

public TreeMap getSrcCloumnTM4incr() {
	return srcCloumnTM4incr;
}
public void setSrcCloumnTM4incr(TreeMap srcCloumnTM4incr) {
	this.srcCloumnTM4incr = srcCloumnTM4incr;
}
String incrementalType = "";

public String getIncrementalType() {
	return incrementalType;
}
public void setIncrementalType(String incrementalType) {
	this.incrementalType = incrementalType;
}
TreeMap incrementalTypeTM = new TreeMap<>();

public TreeMap getIncrementalTypeTM() {
	return incrementalTypeTM;
}
public void setIncrementalTypeTM(TreeMap incrementalTypeTM) {
	this.incrementalTypeTM = incrementalTypeTM;
}
String tarCloumn4incr = "";

public String getTarCloumn4incr() {
	return tarCloumn4incr;
}
public void setTarCloumn4incr(String tarCloumn4incr) {
	this.tarCloumn4incr = tarCloumn4incr;
}
TreeMap tarCloumnTM4incr = new TreeMap<>();


public TreeMap getTarCloumnTM4incr() {
	return tarCloumnTM4incr;
}
public void setTarCloumnTM4incr(TreeMap tarCloumnTM4incr) {
	this.tarCloumnTM4incr = tarCloumnTM4incr;
}
public HeirarchyDataBean(String srcCloumn4incr, TreeMap srcCloumnTM4incr, String tarCloumn4incr, TreeMap tarCloumnTM4incr){
	this.srcCloumn4incr= srcCloumn4incr;
	this.srcCloumnTM4incr = srcCloumnTM4incr;
	this.incrementalType = tarCloumn4incr;
	this.incrementalTypeTM = tarCloumnTM4incr;
}
String srcCloumn4incrUpdate = "";
public String getSrcCloumn4incrUpdate() {
	return srcCloumn4incrUpdate;
}
public void setSrcCloumn4incrUpdate(String srcCloumn4incrUpdate) {
	this.srcCloumn4incrUpdate = srcCloumn4incrUpdate;
}
TreeMap srcCloumnTM4incrUpdate = new TreeMap<>();
public TreeMap getSrcCloumnTM4incrUpdate() {
	return srcCloumnTM4incrUpdate;
}
public void setSrcCloumnTM4incrUpdate(TreeMap srcCloumnTM4incrUpdate) {
	this.srcCloumnTM4incrUpdate = srcCloumnTM4incrUpdate;
}

String srcCloumn4uniqUpdate = "";

public String getSrcCloumn4uniqUpdate() {
	return srcCloumn4uniqUpdate;
}
public void setSrcCloumn4uniqUpdate(String srcCloumn4uniqUpdate) {
	this.srcCloumn4uniqUpdate = srcCloumn4uniqUpdate;
}
public HeirarchyDataBean(String srcCloumn4uniqUpdate, TreeMap srcCloumnTM4incrUpdate, TreeMap tarCloumnTM4incr, String tarCloumn4incr){
	this.srcCloumn4uniqUpdate= srcCloumn4uniqUpdate;
	this.srcCloumnTM4incrUpdate = srcCloumnTM4incrUpdate;
	this.tarCloumn4incr = tarCloumn4incr;
	this.tarCloumnTM4incr = tarCloumnTM4incr;
}
public HeirarchyDataBean(String srcCloumn4incrUpdate, TreeMap srcCloumnTM4incrUpdate){
	this.srcCloumn4incrUpdate= srcCloumn4incrUpdate;
	this.srcCloumnTM4incrUpdate = srcCloumnTM4incrUpdate;
}
public HeirarchyDataBean(String attID, String attfileName, String attfileType, String attfileSize,
		String emptyn, String attachStg, String attachUser, String actionPerformedBy, String actionName,
		String actionPerformedOn, String uploadedOn) {
	// TODO Auto-generated constructor stub
	this.attID = attID;
	this.attfileName = attfileName;
	this.attfileNameSubStr = attfileName;
	if(this.attfileNameSubStr.length() > 20) {
		this.attfileNameSubStr = attfileName.substring(0, 18)+"..";
	}
	this.attfileType = attfileType;
	this.attfileSize = attfileSize;
	this.emptyn = emptyn;
	this.attachStg = attachStg;
	this.attachUser = attachUser;
	this.uploadDetails = attachStg+"/"+attachUser;
	this.uploadDetailsSubStr = this.uploadDetails;
	if(this.uploadDetailsSubStr.length() > 20) {
		this.uploadDetailsSubStr = this.uploadDetailsSubStr.substring(0, 18)+"..";
	}
	if(!attachStg.equalsIgnoreCase("Attach")) {
		showAppRejBtn = "true";
	}else {
		showAppRejBtn = "false";
	}
	this.actionName = actionName;
	this.actionPerformedOn = actionPerformedOn;
	this.actionPerformedBy = actionPerformedBy;
	this.uploadedOn = uploadedOn;
	this.actionDisplayName = actionName == null || actionName.trim().isEmpty() ? "Choose" : actionName;
	if(actionDisplayName.equalsIgnoreCase("Choose")) {
		
		this.actionDisabledStr="false";
		
		}else {
		this.actionDisabledStr="true";
	}
}




public HeirarchyDataBean(String reSattID, String reSattfileName, String reSattfileType, String reSattfileSize,
		String reSattachStg, String reSattachUser, String reSactionPerformedBy, String reSactionName,
		String reSactionPerformedOn, String reSuploadeddate) {
	
	this.reSattID=reSattID;
	this.reSattfileName=reSattfileName;
	this.reSattfileType=reSattfileType;
	this.reSattfileSize=reSattfileSize;
	this.reSattachStg=reSattachStg;
	this.reSattachUser=reSattachUser;
	this.reSactionPerformedBy=reSactionPerformedBy;
	this.reSactionName=reSactionName;
	this.reSactionPerformedOn=reSactionPerformedOn;
	this.reSuploadeddate=reSuploadeddate;
	
	
	
}

public String getAttID() {
	return attID;
}
public void setAttID(String attID) {
	this.attID = attID;
}
public String getAttfileName() {
	return attfileName;
}
public void setAttfileName(String attfileName) {
	this.attfileName = attfileName;
}
public String getAttfileType() {
	return attfileType;
}
public void setAttfileType(String attfileType) {
	this.attfileType = attfileType;
}
public String getAttfileSize() {
	return attfileSize;
}
public void setAttfileSize(String attfileSize) {
	this.attfileSize = attfileSize;
}
public String getEmptyn() {
	return emptyn;
}
public void setEmptyn(String emptyn) {
	this.emptyn = emptyn;
}
public boolean isActiveboxval() {
	return activeboxval;
}
public void setActiveboxval(boolean activeboxval) {
	this.activeboxval = activeboxval;
}



public boolean isDisableboxval() {
	return disableboxval;
}
public void setDisableboxval(boolean disableboxval) {
	this.disableboxval = disableboxval;
}

public boolean isNewcustomerval() {
	return newcustomerval;
}
public void setNewcustomerval(boolean newcustomerval) {
	this.newcustomerval = newcustomerval;
}
public String getEditUserNameTXT() {
	return editUserNameTXT;
}
public void setEditUserNameTXT(String editUserNameTXT) {
	this.editUserNameTXT = editUserNameTXT;
}
public String getEdit_firstNameTXT() {
	return edit_firstNameTXT;
}
public void setEdit_firstNameTXT(String edit_firstNameTXT) {
	this.edit_firstNameTXT = edit_firstNameTXT;
}
public String getEdit_lastNameTXT() {
	return edit_lastNameTXT;
}
public void setEdit_lastNameTXT(String edit_lastNameTXT) {
	this.edit_lastNameTXT = edit_lastNameTXT;
}
public String getEdit_titleTXT() {
	return edit_titleTXT;
}
public void setEdit_titleTXT(String edit_titleTXT) {
	this.edit_titleTXT = edit_titleTXT;
}
public String getEdit_ph1TXT() {
	return edit_ph1TXT;
}
public void setEdit_ph1TXT(String edit_ph1TXT) {
	this.edit_ph1TXT = edit_ph1TXT;
}
public String getEdit_ph2TXT() {
	return edit_ph2TXT;
}
public void setEdit_ph2TXT(String edit_ph2TXT) {
	this.edit_ph2TXT = edit_ph2TXT;
}
public String getEdit_ph3TXT() {
	return edit_ph3TXT;
}
public void setEdit_ph3TXT(String edit_ph3TXT) {
	this.edit_ph3TXT = edit_ph3TXT;
}
public String getEdit_Mail1TXT() {
	return edit_Mail1TXT;
}
public void setEdit_Mail1TXT(String edit_Mail1TXT) {
	this.edit_Mail1TXT = edit_Mail1TXT;
}
public String getEdit_Mail2TXT() {
	return edit_Mail2TXT;
}
public void setEdit_Mail2TXT(String edit_Mail2TXT) {
	this.edit_Mail2TXT = edit_Mail2TXT;
}
public String getEdit_addressTXT() {
	return edit_addressTXT;
}
public void setEdit_addressTXT(String edit_addressTXT) {
	this.edit_addressTXT = edit_addressTXT;
}
public String getEdit_cityTXT() {
	return edit_cityTXT;
}
public void setEdit_cityTXT(String edit_cityTXT) {
	this.edit_cityTXT = edit_cityTXT;
}
public String getEdit_stateTXT() {
	return edit_stateTXT;
}
public void setEdit_stateTXT(String edit_stateTXT) {
	this.edit_stateTXT = edit_stateTXT;
}
public String getEdit_countryTXT() {
	return edit_countryTXT;
}
public void setEdit_countryTXT(String edit_countryTXT) {
	this.edit_countryTXT = edit_countryTXT;
}
public String getEdit_zipTXT() {
	return edit_zipTXT;
}
public void setEdit_zipTXT(String edit_zipTXT) {
	this.edit_zipTXT = edit_zipTXT;
}
public String getAttachStg() {
	return attachStg;
}
public void setAttachStg(String attachStg) {
	this.attachStg = attachStg;
}
public String getAttachUser() {
	return attachUser;
}
public void setAttachUser(String attachUser) {
	this.attachUser = attachUser;
}
public String getUploadDetails() {
	return uploadDetails;
}
public void setUploadDetails(String uploadDetails) {
	this.uploadDetails = uploadDetails;
}
public String getShowAppRejBtn() {
	return showAppRejBtn;
}
public void setShowAppRejBtn(String showAppRejBtn) {
	this.showAppRejBtn = showAppRejBtn;
}
public String getAttfileNameSubStr() {
	return attfileNameSubStr;
}
public void setAttfileNameSubStr(String attfileNameSubStr) {
	this.attfileNameSubStr = attfileNameSubStr;
}
public String getUploadDetailsSubStr() {
	return uploadDetailsSubStr;
}
public void setUploadDetailsSubStr(String uploadDetailsSubStr) {
	this.uploadDetailsSubStr = uploadDetailsSubStr;
}
public String getActionPerformedBy() {
	return actionPerformedBy;
}
public void setActionPerformedBy(String actionPerformedBy) {
	this.actionPerformedBy = actionPerformedBy;
}
public String getActionName() {
	return actionName;
}
public void setActionName(String actionName) {
	this.actionName = actionName;
}
public String getActionPerformedOn() {
	return actionPerformedOn;
}
public void setActionPerformedOn(String actionPerformedOn) {
	this.actionPerformedOn = actionPerformedOn;
}
public String getUploadedOn() {
	return uploadedOn;
}
public void setUploadedOn(String uploadedOn) {
	this.uploadedOn = uploadedOn;
}
public String getActionDisplayName() {
	return actionDisplayName;
}
public void setActionDisplayName(String actionDisplayName) {
	this.actionDisplayName = actionDisplayName;
}
public String getActionDisabledStr() {
	return actionDisabledStr;
}
public void setActionDisabledStr(String actionDisabledStr) {
	this.actionDisabledStr = actionDisabledStr;
}







public String getReSattID() {
	return reSattID;
}
public void setReSattID(String reSattID) {
	this.reSattID = reSattID;
}
public String getReSattfileName() {
	return reSattfileName;
}
public void setReSattfileName(String reSattfileName) {
	this.reSattfileName = reSattfileName;
}
public String getReSattfileType() {
	return reSattfileType;
}
public void setReSattfileType(String reSattfileType) {
	this.reSattfileType = reSattfileType;
}
public String getReSattfileSize() {
	return reSattfileSize;
}
public void setReSattfileSize(String reSattfileSize) {
	this.reSattfileSize = reSattfileSize;
}
public String getReSattachStg() {
	return reSattachStg;
}
public void setReSattachStg(String reSattachStg) {
	this.reSattachStg = reSattachStg;
}
public String getReSattachUser() {
	return reSattachUser;
}
public void setReSattachUser(String reSattachUser) {
	this.reSattachUser = reSattachUser;
}
public String getReSactionPerformedBy() {
	return reSactionPerformedBy;
}
public void setReSactionPerformedBy(String reSactionPerformedBy) {
	this.reSactionPerformedBy = reSactionPerformedBy;
}
public String getReSactionName() {
	return reSactionName;
}
public void setReSactionName(String reSactionName) {
	this.reSactionName = reSactionName;
}
public String getReSactionPerformedOn() {
	return reSactionPerformedOn;
}
public void setReSactionPerformedOn(String reSactionPerformedOn) {
	this.reSactionPerformedOn = reSactionPerformedOn;
}

}