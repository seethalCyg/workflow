package beans;

import java.util.TreeMap;

public class HierarchydataBean {
	
	int hierarchyID = 0;
	String hierarchyName = "";
	String createdDate = "";
	String modifiedDate = "";
	String hierchyCategory="";
	String hierarchyStatus=""; // code change Menaka 15FEB2014
	String factStatus="";
	String errorMsg="";
	String dimStatus = "";
	String comment="";
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDimStatus() {
		return dimStatus;
	}

	public void setDimStatus(String dimStatus) {
		this.dimStatus = dimStatus;
	}

	public String getDimErrorMsg() {
		return dimErrorMsg;
	}

	public void setDimErrorMsg(String dimErrorMsg) {
		this.dimErrorMsg = dimErrorMsg;
	}

	String dimErrorMsg = "";
	String workFlowStage = "";
	
	
	
	public String getWorkFlowStage() {
		return workFlowStage;
	}

	public void setWorkFlowStage(String workFlowStage) {
		this.workFlowStage = workFlowStage;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getFactStatus() {
		return factStatus;
	}

	public void setFactStatus(String factStatus) {
		this.factStatus = factStatus;
	}

	public String getHierarchyStatus() {
		return hierarchyStatus;
	}

	public void setHierarchyStatus(String hierarchyStatus) {
		this.hierarchyStatus = hierarchyStatus;
	}

	public String getHierchyCategory() {
		return hierchyCategory;
	}

	public void setHierchyCategory(String hierchyCategory) {
		this.hierchyCategory = hierchyCategory;
	}

	public int getHierarchyID() {
		return hierarchyID;
	}

	public void setHierarchyID(int hierarchyID) {
		this.hierarchyID = hierarchyID;
	}

	public String getHierarchyName() {
		return hierarchyName;
	}

	public void setHierarchyName(String hierarchyName) {
		this.hierarchyName = hierarchyName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String hierCodeCombination="";
	
	
	
	public String getHierCodeCombination() {
		return hierCodeCombination;
	}

	public void setHierCodeCombination(String hierCodeCombination) {
		this.hierCodeCombination = hierCodeCombination;
	}

	public String srcFactTable = "";
	public String getSrcFactTable() {
		return srcFactTable;
	}

	public void setSrcFactTable(String srcFactTable) {
		this.srcFactTable = srcFactTable;
	}

	public String tarFactTables = "";

	public String getTarFactTables() {
		return tarFactTables;
	}

	public void setTarFactTables(String tarFactTables) {
		this.tarFactTables = tarFactTables;
	}

	String workFlowStageStatus = "";
	
	public String getWorkFlowStageStatus() {
		return workFlowStageStatus;
	}

	public void setWorkFlowStageStatus(String workFlowStageStatus) {
		this.workFlowStageStatus = workFlowStageStatus;
	}
	
	String wfCreatedBy = "";
	public String getWfCreatedBy() {
		return wfCreatedBy;
	}

	public void setWfCreatedBy(String wfCreatedBy) {
		this.wfCreatedBy = wfCreatedBy;
	}

	public String getWfModifiedBy() {
		return wfModifiedBy;
	}

	public void setWfModifiedBy(String wfModifiedBy) {
		this.wfModifiedBy = wfModifiedBy;
	}

	public String getNoOfStages() {
		return noOfStages;
	}

	public void setNoOfStages(String noOfStages) {
		this.noOfStages = noOfStages;
	}

	String wfModifiedBy = "";
	String noOfStages = "";

	HierarchydataBean(int hierarchyID,String hierarchyName,String createdDate,String modifiedDate,String hierchyCategory,String hierCodeCombination,String hierarichyStatus,
			String factStatus,String errorMsg,String dimStatus,String dimErrorMsg,String workFlowStage,String workFlowStageStatus, String wfCreatedBy, String wfModifiedBy, String noOfStages){
		
		this.hierarchyID = hierarchyID;
		this.hierarchyName = hierarchyName;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.hierchyCategory = hierchyCategory;
		this.hierCodeCombination=hierCodeCombination;
		this.hierarchyStatus=hierarichyStatus;  // code change Menaka 15FEB2014
		this.factStatus=factStatus;
		this.errorMsg=errorMsg;
		this.dimStatus = dimStatus;
		this.dimErrorMsg = dimErrorMsg;
		this.workFlowStage = workFlowStage;
		this.workFlowStageStatus = workFlowStageStatus;
		this.wfCreatedBy = wfCreatedBy;
		this.wfModifiedBy = wfModifiedBy;
		this.noOfStages = noOfStages;
	}
	TreeMap periodCols4srcTM = new TreeMap<>();
	public TreeMap getPeriodCols4srcTM() {
		return periodCols4srcTM;
	}

	public void setPeriodCols4srcTM(TreeMap periodCols4srcTM) {
		this.periodCols4srcTM = periodCols4srcTM;
	}

	String periodCol4src = "";
	public String getPeriodCol4src() {
		return periodCol4src;
	}

	public void setPeriodCol4src(String periodCol4src) {
		this.periodCol4src = periodCol4src;
	}

	public HierarchydataBean(String srcFactTable, String tarFactTables, TreeMap periodCols4srcTM, String periodCol4src){
		
		this.srcFactTable=srcFactTable;
		this.tarFactTables=tarFactTables;
		this.periodCols4srcTM = periodCols4srcTM;
		this.periodCol4src = periodCol4src;
	}
public HierarchydataBean(){
		
		
	}
	public String versionNo="";
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String DateCreated="";
	public String getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(String dateCreated) {
		DateCreated = dateCreated;
	}

	public String createdBy="";
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	String codeValue = "";
	
	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public HierarchydataBean(String codeValue){
		this.codeValue = codeValue;
	}
	
	public HierarchydataBean(String versionNo, String DateCreated,String createdBy,String comment){
		
		this.versionNo = versionNo;
		this.createdDate = DateCreated;
		this.createdBy = createdBy;
		this.comment=comment;
	}
	
	String loginID = "";
	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	String dateTime = "";
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	String action = "";
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	String parentID = "";
	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	String beforeVal = "";
	public String getBeforeVal() {
		return beforeVal;
	}

	public void setBeforeVal(String beforeVal) {
		this.beforeVal = beforeVal;
	}

	String afterVal = "";
	public String getAfterVal() {
		return afterVal;
	}

	public void setAfterVal(String afterVal) {
		this.afterVal = afterVal;
	}

	String oldParentID = "";
	public String getOldParentID() {
		return oldParentID;
	}

	public void setOldParentID(String oldParentID) {
		this.oldParentID = oldParentID;
	}

	String oldParentPosition = "";
	public String getOldParentPosition() {
		return oldParentPosition;
	}

	public void setOldParentPosition(String oldParentPosition) {
		this.oldParentPosition = oldParentPosition;
	}

	String newParentID = "";
	public String getNewParentID() {
		return newParentID;
	}

	public void setNewParentID(String newParentID) {
		this.newParentID = newParentID;
	}

	String newParentPosition = "";
	public String getNewParentPosition() {
		return newParentPosition;
	}

	public void setNewParentPosition(String newParentPosition) {
		this.newParentPosition = newParentPosition;
	}
	
	public HierarchydataBean(String loginID, String dateTime, String action, String parentID, String beforeVal, String afterVal,
			String oldParentID, String oldParentPosition, String newParentID, String newParentPosition){
		this.loginID = loginID;
		this.dateTime = dateTime;
		this.action = action;
		this.parentID = parentID;
		this.beforeVal = beforeVal;
		this.afterVal = afterVal;
		this.oldParentID = oldParentID;
		this.oldParentPosition = oldParentPosition;
		this.newParentID = newParentID;
		this.newParentPosition = newParentPosition;
	}
	
	String wfdateTime = "";
	public String getWfdateTime() {
		return wfdateTime;
	}

	public void setWfdateTime(String wFdateTime) {
		this.wfdateTime = wfdateTime;
	}

	String wfActionBy = "";
	

	public String getWfActionBy() {
		return wfActionBy;
	}

	public void setWfActionBy(String wfActionBy) {
		this.wfActionBy = wfActionBy;
	}

	String role = "";
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	String fromStage = "";
	public String getFromStage() {
		return fromStage;
	}

	public void setFromStage(String fromStage) {
		this.fromStage = fromStage;
	}

	String toStage = "";
	public String getToStage() {
		return toStage;
	}

	public void setToStage(String toStage) {
		this.toStage = toStage;
	}

	String fromStatus = "";
	public String getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	String memberStatus = "";
	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	String notes = "";
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	String wfHierarchyID = "";
	

	public String getWfHierarchyID() {
		return wfHierarchyID;
	}

	public void setWfHierarchyID(String wfHierarchyID) {
		this.wfHierarchyID = wfHierarchyID;
	}

	String wfHierarchyName = "";
	

	public String getWfHierarchyName() {
		return wfHierarchyName;
	}

	public void setWfHierarchyName(String wfHierarchyName) {
		this.wfHierarchyName = wfHierarchyName;
	}

	String wfHierarchyType = "";
			
	
	public String getWfHierarchyType() {
		return wfHierarchyType;
	}

	public void setWfHierarchyType(String wfHierarchyType) {
		this.wfHierarchyType = wfHierarchyType;
	}

	public HierarchydataBean(String WFdateTime, String WFActionBy, String Role, String fromStage, String toStage, String fromStatus,
			String memberStatus, String notes, String WFHierarchyID, String WFHierarchyName, String WFHierarchyType){
		this.wfdateTime = WFdateTime;
		this.wfActionBy = WFActionBy;
		this.role = Role;
		this.fromStage = fromStage;
		this.toStage = toStage;
		this.fromStatus = fromStatus;
		this.memberStatus = memberStatus;
		this.notes = notes;
		this.wfHierarchyID = WFHierarchyID;
		this.wfHierarchyName = WFHierarchyName;
		this.wfHierarchyType = WFHierarchyType;
	}
	
	String selectedTableName = "";
	public String getSelectedTableName() {
		return selectedTableName;
	}

	public void setSelectedTableName(String selectedTableName) {
		this.selectedTableName = selectedTableName;
	}

	TreeMap selectedSrcTableNameTM = new TreeMap<>();
	public TreeMap getSelectedSrcTableNameTM() {
		return selectedSrcTableNameTM;
	}

	public void setSelectedSrcTableNameTM(TreeMap selectedSrcTableNameTM) {
		this.selectedSrcTableNameTM = selectedSrcTableNameTM;
	}

	String selectedSrcColumn = "";
	public String getSelectedSrcColumn() {
		return selectedSrcColumn;
	}

	public void setSelectedSrcColumn(String selectedSrcColumn) {
		this.selectedSrcColumn = selectedSrcColumn;
	}

	TreeMap selectedSrcColumnTM = new TreeMap<>();
	public TreeMap getSelectedSrcColumnTM() {
		return selectedSrcColumnTM;
	}

	public void setSelectedSrcColumnTM(TreeMap selectedSrcColumnTM) {
		this.selectedSrcColumnTM = selectedSrcColumnTM;
	}

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

	public HierarchydataBean(String selectedTableName,TreeMap selectedSrcTableNameTM,String selectedSrcColumn,
			TreeMap selectedSrcColumnTM,String joinColumnOper,TreeMap joinColumnOperTM,	String tarTableName,String tarColumnName){
		this.selectedTableName = selectedTableName;
		this.selectedSrcTableNameTM = selectedSrcTableNameTM;
		this.selectedSrcColumn = selectedSrcColumn;
		this.selectedSrcColumnTM = selectedSrcColumnTM;
		this.joinColumnOper = joinColumnOper;
		this.joinColumnOperTM = joinColumnOperTM;
		this.tarTableName = tarTableName;
		this.tarColumnName = tarColumnName;
	}
	String code4DirectSQL = "";
	public String getCode4DirectSQL() {
		return code4DirectSQL;
	}

	public void setCode4DirectSQL(String code4DirectSQL) {
		this.code4DirectSQL = code4DirectSQL;
	}

	String name4DirectSQL = "";
public String getName4DirectSQL() {
		return name4DirectSQL;
	}

	public void setName4DirectSQL(String name4DirectSQL) {
		this.name4DirectSQL = name4DirectSQL;
	}

public HierarchydataBean(String code4DirectSQL, String name4DirectSQL){
		
		this.code4DirectSQL = code4DirectSQL;
		this.name4DirectSQL = name4DirectSQL;
	}

}
