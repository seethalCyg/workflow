
package beans;

import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class WorkflowDataBean {
	
String documentLink="";
String workflowName1="";
String assignedBy ="";
String nextTeamstr="";
String assigneddatePlanned = "";
String assignedDate = "";
String dueDate = "";
String approvalHistory = "";
String statusVal = "";




///////////////////////////////////

String workflowID="";
String workflowName="";
String documentID="";
String documentName="";
String createdBy ="";
String createdDate="";


String docCreatedby;
String docCreatedDate;
String dueDateColor = "";
int serialNo;
String calendarFrom=""; 
String calendarTo="";


int rowKey;
int rowKeyCount;


String stageTeamstr="";
String stageSDstr="";
String stageEDstr="";
String stageESDstr="";
String stageEEDstr ="";

public String getStageTeamstr() {
	return stageTeamstr;
}

public void setStageTeamstr(String stageTeamstr) {
	this.stageTeamstr = stageTeamstr;
}

public String getStageSDstr() {
	return stageSDstr;
}

public void setStageSDstr(String stageSDstr) {
	this.stageSDstr = stageSDstr;
}

public String getStageEDstr() {
	return stageEDstr;
}

public void setStageEDstr(String stageEDstr) {
	this.stageEDstr = stageEDstr;
}

public String getStageESDstr() {
	return stageESDstr;
}

public void setStageESDstr(String stageESDstr) {
	this.stageESDstr = stageESDstr;
}

public String getStageEEDstr() {
	return stageEEDstr;
}

public void setStageEEDstr(String stageEEDstr) {
	this.stageEEDstr = stageEEDstr;
}

public int getSerialNo() {
	return serialNo;
}

public void setSerialNo(int serialNo) {
	this.serialNo = serialNo;
}





public String getCalendarFrom() {
	return calendarFrom;
}

public void setCalendarFrom(String calendarFrom) {
	this.calendarFrom = calendarFrom;
}

public String getCalendarTo() {
	return calendarTo;
}

public void setCalendarTo(String calendarTo) {
	this.calendarTo = calendarTo;
}
String currentStg= "";
String currentStgUser= "";
String nextTeam= "";
String nextTeamUser = "";
String docType = "";
String nextTeamDetails = "";
String currStgUserSubStr = "";
String nextStgUserSubStr = "";
public WorkflowDataBean(String documentLink,String workflowName1,String assignedBy,String nextTeamstr,String assigneddatePlanned,String assignedDate,String dueDate,String approvalHistory,String statusVal){//code change Jayaramu 27MAR14
	
	this.documentLink = documentLink;
	this.workflowName1 = workflowName1;
	this.assignedBy = assignedBy;
	this.nextTeamstr = nextTeamstr;
	this.assigneddatePlanned = assigneddatePlanned;
	this.assignedDate = assignedDate;
	this.dueDate = dueDate;
	this.approvalHistory = approvalHistory;
	this.statusVal = statusVal;
	
	
	}

public WorkflowDataBean(String documentLink, String workflowName1, String assignedBy, String nextTeamstr, String assigneddatePlanned, String dueDate,String approvalHistory, String statusVal) 
{
this.documentLink = documentLink;
this.workflowName1 = workflowName1;
this.assignedBy = assignedBy;
this.nextTeamstr = nextTeamstr;
this.assigneddatePlanned = assigneddatePlanned;

this.dueDate = dueDate;
this.approvalHistory = approvalHistory;
this.statusVal = statusVal;
}

public WorkflowDataBean(String workflowID, String workflowName,  String documentID, String documentName, String currentStg,
		String currentStgUser, String nextTeam, String nextTeamUser, String dueDate, String docType, String nextTeamDetails,
		String docCreatedby,String docCreatedDate, String dueDateColor) {
	
	this.workflowID = workflowID;
	this.workflowName = workflowName;
	this.documentID = documentID;
	this.documentName = documentName;
	this.currentStg= currentStg;
	this.currentStgUser= currentStgUser;
	this.currStgUserSubStr = currentStgUser;
	if(currentStgUser != null && currentStgUser.length() > 30) {
		this.currStgUserSubStr = currentStgUser.substring(0, 28)+"..";
	}
	this.nextTeam= nextTeam;
	this.nextTeamUser = nextTeamUser;
	this.dueDate = dueDate;
	this.docType = docType;
	this.nextTeamDetails = nextTeamDetails;
	this.nextStgUserSubStr = nextTeamDetails;

	if(nextTeamDetails != null && nextTeamDetails.length() > 30) {
		this.nextStgUserSubStr = nextTeamDetails.substring(0, 28)+"..";
	}
	this.docCreatedby=docCreatedby;
	this.docCreatedDate=docCreatedDate;
	this.dueDateColor = dueDateColor;
	// TODO Auto-generated constructor stub
}






public WorkflowDataBean(String wfLoginHTworkflowName) {
	// TODO Auto-generated constructor stub
	this.workflowName = wfLoginHTworkflowName;
}

public WorkflowDataBean(int rowKeyCount,int serialNo, String calendarFrom, String calendarTo) {
	// TODO Auto-generated constructor stub
	this.rowKeyCount=rowKeyCount;
	this.serialNo = serialNo;
	this.calendarFrom = calendarFrom;
	this.calendarTo = calendarTo;
	
}

public WorkflowDataBean(int rowKey,String stageTeamstr, String stageSDstr, String stageEDstr, String stageESDstr, String stageEEDstr) {
	// TODO Auto-generated constructor stub
	this.rowKey=rowKey;
	this.stageTeamstr=stageTeamstr;
	this.stageSDstr=stageSDstr;
	this.stageEDstr=stageEDstr;
	this.stageESDstr=stageESDstr;
	this.stageEEDstr =stageEEDstr;
}

public String getDocumentLink() {
	return documentLink;
}
public void setDocumentLink(String documentLink) {
	this.documentLink = documentLink;
}
public String getWorkflowName() {
	return workflowName;
}
public void setWorkflowName(String workflowName) {
	this.workflowName = workflowName;
}
public String getAssignedBy() {
	return assignedBy;
}
public void setAssignedBy(String assignedBy) {
	this.assignedBy = assignedBy;
}
public String getNextTeamstr() {
	return nextTeamstr;
}
public void setNextTeamstr(String nextTeamstr) {
	this.nextTeamstr = nextTeamstr;
}
public String getAssigneddatePlanned() {
	return assigneddatePlanned;
}
public void setAssigneddatePlanned(String assigneddatePlanned) {
	this.assigneddatePlanned = assigneddatePlanned;
}
public String getAssignedDate() {
	return assignedDate;
}
public void setAssignedDate(String assignedDate) {
	this.assignedDate = assignedDate;
}
public String getDueDate() {
	return dueDate;
}
public void setDueDate(String dueDate) {
	this.dueDate = dueDate;
}
public String getApprovalHistory() {
	return approvalHistory;
}
public void setApprovalHistory(String approvalHistory) {
	this.approvalHistory = approvalHistory;
}
public String getStatusVal() {
	return statusVal;
}
public void setStatusVal(String statusVal) {
	this.statusVal = statusVal;
}
public String getWorkflowID() {
	return workflowID;
}
public String getWorkflowName1() {
	return workflowName1;
}

public void setWorkflowName1(String workflowName1) {
	this.workflowName1 = workflowName1;
}

public String getDocumentID() {
	return documentID;
}

public void setDocumentID(String documentID) {
	this.documentID = documentID;
}

public String getDocumentName() {
	return documentName;
}

public void setDocumentName(String documentName) {
	this.documentName = documentName;
}

public String getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}

public String getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(String createdDate) {
	this.createdDate = createdDate;
}

public void setWorkflowID(String workflowID) {
	this.workflowID = workflowID;
}

public String getCurrentStg() {
	return currentStg;
}

public void setCurrentStg(String currentStg) {
	this.currentStg = currentStg;
}

public String getCurrentStgUser() {
	return currentStgUser;
}

public void setCurrentStgUser(String currentStgUser) {
	this.currentStgUser = currentStgUser;
}

public String getNextTeam() {
	return nextTeam;
}

public void setNextTeam(String nextTeam) {
	this.nextTeam = nextTeam;
}

public String getNextTeamUser() {
	return nextTeamUser;
}

public void setNextTeamUser(String nextTeamUser) {
	this.nextTeamUser = nextTeamUser;
}

public String getDocType() {
	return docType;
}

public void setDocType(String docType) {
	this.docType = docType;
}

public String getNextTeamDetails() {
	return nextTeamDetails;
}

public void setNextTeamDetails(String nextTeamDetails) {
	this.nextTeamDetails = nextTeamDetails;
}

public String getCurrStgUserSubStr() {
	return currStgUserSubStr;
}

public void setCurrStgUserSubStr(String currStgUserSubStr) {
	this.currStgUserSubStr = currStgUserSubStr;
}

public String getNextStgUserSubStr() {
	return nextStgUserSubStr;
}

public void setNextStgUserSubStr(String nextStgUserSubStr) {
	this.nextStgUserSubStr = nextStgUserSubStr;
}




public String getDocCreatedby() {
	return docCreatedby;
}

public void setDocCreatedby(String docCreatedby) {
	this.docCreatedby = docCreatedby;
}

public String getDocCreatedDate() {
	return docCreatedDate;
}

public void setDocCreatedDate(String docCreatedDate) {
	this.docCreatedDate = docCreatedDate;
}

public int getRowKeyCount() {
	return rowKeyCount;
}

public void setRowKeyCount(int rowKeyCount) {
	this.rowKeyCount = rowKeyCount;
}

public int getRowKey() {
	return rowKey;
}

public void setRowKey(int rowKey) {
	this.rowKey = rowKey;
}

public String getDueDateColor() {
	return dueDateColor;
}

public void setDueDateColor(String dueDateColor) {
	this.dueDateColor = dueDateColor;
}

}