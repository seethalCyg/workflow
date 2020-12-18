package beans;

import javax.faces.bean.ManagedBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import managers.LoginProcessManager;
import managers.RulesManager;
import managers.WorkflowManager;
import model.RuleAttributeDataModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Globals;
import utils.PropUtil;

@ManagedBean(name = "memberStatusBean")
@SessionScoped
public class MemberStatusBean {

	/**
	 * @param args
	 */

	String selectedStatus="";
	String comingFrom = "";
	HeirarchyDataBean selectedAttachDocData = null;
	private String dbData="";
	String message="";
	String messageSubStr="";
	private boolean reasonFlag;
	LinkedHashMap<String,String> statusHM=new LinkedHashMap<String,String>();
	private String selectedAccessType="";
	private String reasonText="";
	private String reasonText4admin="";
	private boolean dispMsgStatusPopup=false;  // code change Menaka 15APR2014

	public boolean isDispMsgStatusPopup() {
		return dispMsgStatusPopup;
	}
	public void setDispMsgStatusPopup(boolean dispMsgStatusPopup) {
		this.dispMsgStatusPopup = dispMsgStatusPopup;
	}
	public String getReasonText4admin() {
		return reasonText4admin;
	}
	public void setReasonText4admin(String reasonText4admin) {
		this.reasonText4admin = reasonText4admin;
	}
	public String getReasonText() {
		return reasonText;
	}
	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}
	public String getSelectedAccessType() {
		return selectedAccessType;
	}
	public void setSelectedAccessType(String selectedAccessType) {
		this.selectedAccessType = selectedAccessType;
	}
	public LinkedHashMap<String, String> getStatusHM() {
		return statusHM;
	}
	public void setStatusHM(LinkedHashMap<String, String> statusHM) {
		this.statusHM = statusHM;
	}
	public boolean isReasonFlag() {
		return reasonFlag;
	}
	public void setReasonFlag(boolean reasonFlag) {
		this.reasonFlag = reasonFlag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDbData() {
		message=""; 
		return dbData;
	}
	public void setDbData(String dbData) {
		this.dbData = dbData;
	}
	public String getSelectedStatus() {
		return selectedStatus;
	}
	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}
	//////////code change pandian 04Mar2014

	private String hierarchyID="";
	private String hierarchyName="";
	private String currentStageName="";
	private String currentStageStatus="";
	private String stageDbData="";
	private String adminSelectStage="";
	private String selectedStatusAdmin="";
	private LinkedHashMap<String,String> adminStagestatusHT=new LinkedHashMap<String,String>();
	//	private TreeMap adminStagestatusTM=new TreeMap();  //code change commented by pandian 19APR2014
	//	public TreeMap getAdminStagestatusTM() {
	//		return adminStagestatusTM;
	//	}
	//	public void setAdminStagestatusTM(TreeMap adminStagestatusTM) {
	//		this.adminStagestatusTM = adminStagestatusTM;
	//	}

	private LinkedHashMap<String,String> statusadminHT=new LinkedHashMap<String,String>();
	private Hashtable allstatusadminHT=new Hashtable();

	public Hashtable getAllstatusadminHT() {
		return allstatusadminHT;
	}
	public void setAllstatusadminHT(Hashtable allstatusadminHT) {
		this.allstatusadminHT = allstatusadminHT;
	}
	public String getHierarchyID() {
		return hierarchyID;
	}
	public void setHierarchyID(String hierarchyID) {
		this.hierarchyID = hierarchyID;
	}
	public String getHierarchyName() {
		return hierarchyName;
	}
	public void setHierarchyName(String hierarchyName) {
		this.hierarchyName = hierarchyName;
	}
	public String getCurrentStageName() {
		return currentStageName;
	}
	public void setCurrentStageName(String currentStageName) {
		this.currentStageName = currentStageName;
	}
	public String getCurrentStageStatus() {
		return currentStageStatus;
	}
	public void setCurrentStageStatus(String currentStageStatus) {
		this.currentStageStatus = currentStageStatus;
	}

	public String getAdminSelectStage() {
		return adminSelectStage;
	}
	public void setAdminSelectStage(String adminSelectStage) {
		this.adminSelectStage = adminSelectStage;
	}
	public String getSelectedStatusAdmin() {
		return selectedStatusAdmin;
	}
	public void setSelectedStatusAdmin(String selectedStatusAdmin) {
		this.selectedStatusAdmin = selectedStatusAdmin;
	}
	public LinkedHashMap<String, String> getAdminStagestatusHT() {
		return adminStagestatusHT;
	}
	public void setAdminStagestatusHT(
			LinkedHashMap<String, String> adminStagestatusHT) {
		this.adminStagestatusHT = adminStagestatusHT;
	}
	public LinkedHashMap<String, String> getStatusadminHT() {
		return statusadminHT;
	}
	public void setStatusadminHT(LinkedHashMap<String, String> statusadminHT) {
		this.statusadminHT = statusadminHT;
	}
	String hierarchyName4SubStr = "";
	public String getHierarchyName4SubStr() {
		return hierarchyName4SubStr;
	}
	public void setHierarchyName4SubStr(String hierarchyName4SubStr) {
		this.hierarchyName4SubStr = hierarchyName4SubStr;
	}
	public void getStageDbDatas() {
		try{
			adminStagestatusHT=new LinkedHashMap<String,String>();
			//	 adminStagestatusTM = new TreeMap<>();
			allstatusadminHT=new Hashtable();
			message="";
			messageSubStr = "";
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
			this.hierarchyID=hryb.getHierarchy_ID();
			this.hierarchyName=hryb.getHierarchyName();
			hierarchyName4SubStr = hierarchyName;
			if(this.hierarchyName.length()>6){
				hierarchyName4SubStr = hierarchyName.substring(0, 6);
			}
			PropUtil prop = new PropUtil();
			String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc = Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node workFlowN = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			if(workFlowN==null){
				message=" Work Flow did not Created yet so you cannot modify";
				return;
			}else{
				Hashtable getHT=Globals.getAttributeNameandValHT(workFlowN);
				this.currentStageName=(String)getHT.get("Current_Stage_Name");
				Node curstageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_Name", currentStageName);
				Hashtable stagegetHT=Globals.getAttributeNameandValHT(curstageN);
				this.currentStageStatus=(String)stagegetHT.get("Stage_Status");
			}




			LoginProcessManager log=new LoginProcessManager();
			this.allstatusadminHT=log.getallStageStatus(hierarchyID);
			System.out.println("allstatusadminHT------>"+allstatusadminHT);
			int totalStagelevel=(int)allstatusadminHT.get("Total_No_Stages");
			int stagelevel=1;
			for(int process=0; process<totalStagelevel; process++){
				String stage=(String)allstatusadminHT.get(stagelevel);
				adminStagestatusHT.put(stage, stage);
				stagelevel++;
			}



			//Enumeration en=allstatusadminHT.keys();
			//while (en.hasMoreElements()){
			//	String stage=(String)en.nextElement();
			//	adminStagestatusHT.put(stage, stage);
			////	adminStagestatusTM.put(stage, stage);
			//}

			statusadminHT=new LinkedHashMap<String,String>();
			String selectedStatus=currentStageName;
			adminSelectStage=selectedStatus;
			System.out.println(selectedStatus+"   adminSelectStage------>"+adminSelectStage);
			Hashtable finalStatusHT=(Hashtable)allstatusadminHT.get(selectedStatus);
			System.out.println("finalStatusHT------>"+finalStatusHT);

			int statusnum=0;
			Hashtable allmsgHT=new Hashtable<>();
			for(int i=0;i<finalStatusHT.size();i++){
				statusnum=i+1;
				String stmsg="";
				//	   if(statusnum==finalStatusHT.size()){
				//		   stmsg=(String)finalStatusHT.get("Final");
				//	   }else{
				//		   stmsg=(String)finalStatusHT.get(String.valueOf(statusnum));
				//	   }	
				stmsg=(String)finalStatusHT.get(i);
				System.out.println("stmsg------>"+stmsg);

				statusadminHT.put(stmsg, stmsg);
			}


			System.out.println("statusadminHT------>"+statusadminHT);


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}




		//		return stageDbData;
	}
	public void setStageDbData(String stageDbData) {
		this.stageDbData = stageDbData;
	}

	public void changingStageStatusAdmin() {
		try{
			message="";
			messageSubStr="";
			statusadminHT=new LinkedHashMap<String,String>();
			String selectedStatus=adminSelectStage;
			Hashtable finalStatusHT=(Hashtable)allstatusadminHT.get(selectedStatus);
			int statusnum=0;
			Hashtable allmsgHT=new Hashtable<>();
			for(int i=0;i<finalStatusHT.size();i++){
				//				   statusnum=i+1;
				String stmsg="";
				//				   if(statusnum==finalStatusHT.size()){
				//					   stmsg=(String)finalStatusHT.get("Final");
				//				   }else{
				//					   stmsg=(String)finalStatusHT.get(String.valueOf(statusnum));
				//				   }		

				stmsg=(String)finalStatusHT.get(i);

				statusadminHT.put(stmsg, stmsg);
			}


			System.out.println("statusadminHT------>"+statusadminHT);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void updatingStatusFromAdmininXMl() {
		try{

			message="";
			messageSubStr="";
			if(selectedStatusAdmin.equals("Rejected")){
				if(reasonText4admin.equals("")){
					message="For Rejection, reason (Notes) is mandatory.";
					messageSubStr = "For Rejection, reason (Notes) is mandatory.";
					System.out.println("************************************* message "+message);
					return;
				}else{
					System.out.println("************************************* message "+message);
				}
			}
			//Start code change Jayaramu 10APR14
			PropUtil prop=new PropUtil();
			String HIERARCHY_XML_DIR=prop.getProperty("HIERARCHY_XML_DIR");
			String HIERARCHY_XML_FILE=prop.getProperty("HIERARCHY_XML_FILE");
			Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, HIERARCHY_XML_FILE);
			insertWorkFlowActivity2DB(doc,"updatingStatusFromAdmininXMl");
			//End code change Jayaramu 10APR14
			String adminMail="";
			String adminAccesID="";
			String adminRole="";
			String adminUsername="";
			workFlowBean wfbn=new workFlowBean();
			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");
			HierarchyBean hryb = (HierarchyBean) sessionMap1.get("hierarchyBean");
			String adminusername=lgnbn.username;
			System.out.println("*************************************");
			System.out.println("adminSelectStage  "+adminSelectStage);
			System.out.println("selectedStatusAdmin  "+selectedStatusAdmin);
			String adminStatusmsg=selectedStatusAdmin;
			Hashtable allUsersHT=wfbn.getAllUsersFromlaXML();
			for(int member=0;member<allUsersHT.size();member++){
				Hashtable memberHT=(Hashtable)allUsersHT.get(member);
				String username=(String)memberHT.get("Login_ID");
				if(adminusername.equals(username)){
					adminMail=(String)memberHT.get("Email_ID");
					adminAccesID=(String)memberHT.get("Access_Unique_ID");
					adminRole="Admin";
					adminUsername=username;
					break;
				}

			}
			LoginProcessManager  log=new LoginProcessManager();						
			log.setAdminStage(hierarchyID,hierarchyName,adminSelectStage,adminMail,adminAccesID,selectedStatusAdmin,adminRole,adminUsername,reasonText4admin);
			hryb.setStageMessageinFront(hierarchyID);//coe change Jayaramu 23APR14
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	public void changingOfStatus() {

		try{


			message="";
			messageSubStr="";
			reasonText="";
			if(selectedStatus.equals("Rejected")){
				reasonFlag=true;
				System.out.println("Rejected==>"+reasonFlag);
			}else{
				reasonFlag=false;
				System.out.println("selectedsegment==>"+reasonFlag);
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public void changingOfStatustextAreaAdmin() {

		try{


			message="";
			reasonText4admin="";
			messageSubStr="";
			if(selectedStatusAdmin.equals("Rejected")){
				reasonFlag=true;
				System.out.println("Rejected==>"+reasonFlag);
			}else{
				reasonFlag=false;
				System.out.println("selectedsegment==>"+reasonFlag);
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public void updatingStatusinXMl(String documentId,String docName) {
		try{
			System.out.println("documentId" +documentId +"docName :"+docName);
			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");
			HierarchyBean hryb = (HierarchyBean) sessionMap1.get("hierarchyBean");
			WorkflowTabBean wtb = (WorkflowTabBean) sessionMap1.get("workflowTabBean");
			String userName=lgnbn.getUsername();
			String notes= this.reasonText;
			if(comingFrom.equalsIgnoreCase("Primary")) {
				String actionName="";
				String docName1=docName;
				String documentId1=documentId;

				if(selectedStatus != null) {

					if(selectedStatus.equalsIgnoreCase("Approved")) {

						actionName="Approve";
						message="Approved Successfully";
						messageSubStr = "Approved Successfully";
					}else if(selectedStatus.equalsIgnoreCase("Completed")) {

						actionName="Completed";
						message="Completed Successfully";
						messageSubStr = "Completed Successfully";
					}else if(selectedStatus.equalsIgnoreCase("Rejected")) {

						actionName="Reject";
						message="Rejected Successfully";
						messageSubStr = "Rejected Successfully";
					}
					else if(selectedStatus.equalsIgnoreCase("WIP")) {

						actionName="WIP";
					}else if(selectedStatus.equalsIgnoreCase("Acknowledged")) {
						actionName="Acknowledged";
						message="Acknowledged Successfully";
						messageSubStr = "Acknowledged Successfully";
					}


				}
				String attrValue = "";
				for(RuleAttributeDataModel radm : wtb.getRulesAttrDetailsAL()) {
					String value = radm.getAttrValue();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					if(radm.getAttrDataType().equalsIgnoreCase("date")) {
						value = radm.getDateType().equalsIgnoreCase("currentdate") ? "CurrentDate" : sdf.format(radm.getDateValue());
					}
					System.out.println("-=-=-=-=-dfd=-=-=-="+value);
					attrValue = attrValue.isEmpty() ? value : attrValue +"#~#"+ value;
				}
				System.out.println("-=-=-=-=-attrValue=-=-=-="+attrValue);
				PropUtil prop = new PropUtil();
				String heirLevelXML=prop.getProperty("HIERARCHY_XML_DIR");
				String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
				Document docXmlDoc=Globals.openXMLFile(heirLevelXML, docXmlFileName);
				Node workFlowN=null;
				Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
				Element docEle = (Element)docNd;
				workFlowN = docEle.getElementsByTagName("Workflow").item(0);
				((Element) workFlowN).setAttribute("RuleAttributeValue", attrValue);
				String currentStg = ((Element) workFlowN).getAttribute("Current_Stage_No");
				if(actionName.equalsIgnoreCase("WIP")) {
					
					Node stgNd = Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", currentStg);
					Element stgEle = (Element) stgNd;
					stgEle.setAttribute("Stage_Status", "WIP");
					NodeList empNdList = stgEle.getElementsByTagName("Employee_Names").item(0).getChildNodes();
					for(int i=0;i<empNdList.getLength();i++) {
						if(empNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
							Element empEle = (Element) empNdList.item(i);
							empEle.setAttribute("User_Status", "WIP");
						}
					}
				}
				Globals.writeXMLFile(docXmlDoc, heirLevelXML, docXmlFileName);
				if(!actionName.equalsIgnoreCase("WIP"))
					WorkflowManager.performingAction(userName, notes, actionName, docName1, documentId1, "JavaWF","","","");
				else {

				}
				docXmlDoc=Globals.openXMLFile(heirLevelXML, docXmlFileName);
				docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
				docEle = (Element)docNd;
				Element wfEle = (Element)docEle.getElementsByTagName("Workflow").item(0);
				String wfId = docEle.getAttribute("WorkflowID");
				String wfName = docEle.getAttribute("WorkflowName");
				String currStgNo = wfEle.getAttribute("Current_Stage_No");
				String totalStage = wfEle.getAttribute("Total_No_Stages");
				//		insertWorkFlowActivity2DB(doc,"updatingStatusinXMl");
				//End code change Jayaramu 10APR14
				LoginProcessManager lpm = new LoginProcessManager();

				
				Hashtable LoginDetailsHT=lpm.getLoginDetails(userName, docEle.getAttribute("CustomerKey"));
				String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
				message="";
				messageSubStr="";
				if(selectedStatus.equals("Rejected")){
					if(reasonText.equals("")){
						message="For Rejection, reason (Notes) is mandatory.";
						messageSubStr= "For Rejection, reason (Notes) is mandatory.";
					}else{
						System.out.println("Saved rejected");

						LoginProcessManager  log=new LoginProcessManager();
						Hashtable NextProcessHT=WorkflowManager.setStatusMsg4EMP(wfId, documentId, wfName, currStgNo, AccessUniqID, "", "", AccessUniqID, true, userName);
						if(NextProcessHT.get("ERROR")!=null){
							String msg=(String)NextProcessHT.get("ERROR");
							message=msg;
						}else{
							Hashtable testHT = WorkflowManager.rejectNextUsers(docXmlDoc, workFlowN, currentStg, userName);
							String nxtStgMsg = "";
							if(testHT != null) {
								ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>() : (ArrayList<String>)testHT.get("Users");
								String stage = testHT.get("Stage") == null ? "" : (String)testHT.get("Stage");
								nxtStgMsg = stage;
								for (int i=0;i<testAL.size();i++) {
									 nxtStgMsg = nxtStgMsg+(i == 0 ? "(":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
								}
							}
							System.out.println("-=-=-=-=-nxtStgMsg=-=-rej=-=-member=-="+nxtStgMsg);
							if(nxtStgMsg.trim().isEmpty())
								message="The status is updated successfully. This document is forwarded to previous team.";
							else
								message="The status is updated successfully. This document is forwarded to "+nxtStgMsg+".";
						}
						messageSubStr = message;
						if(messageSubStr!=null && messageSubStr.length()>80) {
							messageSubStr = messageSubStr.substring(0, 78)+"..";
						}
						if(currStgNo.equals("Completed") || currStgNo.equalsIgnoreCase("Cancel") || currStgNo.equalsIgnoreCase("Rules Failed")){

							hryb.renderstatus4Approved="false"; 
							hryb.renderstatus4compltd="false"; 
							hryb.renderstatus4Rejected="false"; 
							hryb.renderstatus4Wip="false";  
							hryb.setStageStatus("Completed");   //..

						}else{
							int tottalst=Integer.parseInt(totalStage);
							int curst=Integer.parseInt(currStgNo);

							String isstatusAvaible=WorkflowManager.getFinalMsg(wfEle,selectedStatus);		
							System.out.println(isstatusAvaible+ " isstatusAvaible tottalst"+tottalst+" curst"+curst);
							if(tottalst==curst && isstatusAvaible.equalsIgnoreCase("true")){  //....
								System.out.println("   (tottalst==curst)  tottalst"+tottalst+" curst"+curst);
								hryb.renderstatus4Approved="false"; 
								hryb.renderstatus4compltd="false"; 
								hryb.renderstatus4Rejected="false"; 
								hryb.renderstatus4Wip="false";
								hryb.setStageStatus("Completed");  //..
							}else {
								System.out.println("Redirect the Page ");
								System.out.println("   (tottalst!=curst)  tottalst"+tottalst+" curst"+curst);
								// code change pandian 28Mar2013						
								hryb.setStageMessageinFrontWF(wfId, documentId);
							}

						}





					}
				}else{

					LoginProcessManager  log=new LoginProcessManager();
					if(currStgNo.equals("Completed")){
						message="The status is updated successfully.";
					}else {
						Hashtable NextProcessHT=WorkflowManager.setStatusMsg4EMP(wfId, documentId, wfName, currStgNo, AccessUniqID, "", "", AccessUniqID, true, userName);
						if(NextProcessHT.get("ERROR")!=null){
							String msg=(String)NextProcessHT.get("ERROR");
							message=msg;
						}else{
							Hashtable testHT = WorkflowManager.getNextDocumentUsers(docXmlDoc, workFlowN, currentStg, userName);
//							Element wfEle = (Element)workFlowN;
			        		String wfType = wfEle.getAttribute("Workflow_Type");
							String nxtStgMsg = "";
							if(testHT != null) {
								ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>() : (ArrayList<String>)testHT.get("Users");
								String stage = testHT.get("Stage") == null ? "" : (String)testHT.get("Stage");
								nxtStgMsg = stage;
								String users = "";
								for (int i=0;i<testAL.size();i++) {
									 nxtStgMsg = nxtStgMsg+(i == 0 ? "(":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
									 users = testAL.get(i)+(i < testAL.size()-1 ? ", " : "");
								}
								if(wfType.equalsIgnoreCase("Simple")) {
									nxtStgMsg = users;
								}
							}
							System.out.println("-=-=-=-=-nxtStgMsg=-=-app=-=-member=-="+nxtStgMsg);
							if(nxtStgMsg.trim().isEmpty())
								message="The status is updated successfully. This document is forwarded to next team.";
							else if(nxtStgMsg.trim().equalsIgnoreCase("Rules Failed"))
								message="This document will not be forwarded to next stage because of rules failure.";
							else
								if(Integer.valueOf(currentStg)==Integer.valueOf(totalStage)) 
									message="The status is updated successfully. Workflow is completed.";
								else
									message="The status is updated successfully. This document is forwarded to "+nxtStgMsg+".";
						}
					}
					
					messageSubStr = message;
					if(messageSubStr!=null && messageSubStr.length()>80) {
						messageSubStr = messageSubStr.substring(0, 78)+"..";
					}
					System.out.println("Saved "+selectedStatus);

					if(currStgNo.equals("Completed") || currStgNo.equals("Cancel") || currStgNo.equals("Rules Failed")){

						hryb.renderstatus4Approved="false"; 
						hryb.renderstatus4compltd="false"; 
						hryb.renderstatus4Rejected="false"; 
						hryb.renderstatus4Wip="false"; 
						hryb.setStageStatus(currStgNo);


					}else{
						int tottalst=Integer.parseInt(totalStage);
						int curst=Integer.parseInt(currStgNo);

						String isstatusAvaible=WorkflowManager.getFinalMsg(wfEle,selectedStatus);		
						System.out.println(isstatusAvaible+" isstatusAvaible tottalst"+tottalst+" curst"+curst);
						if(tottalst==curst && isstatusAvaible.equalsIgnoreCase("true")){  //....
							System.out.println("   (tottalst==curst)  tottalst"+tottalst+" curst"+curst);
							hryb.renderstatus4Approved="false"; 
							hryb.renderstatus4compltd="false"; 
							hryb.renderstatus4Rejected="false"; 
							hryb.renderstatus4Wip="false";
							hryb.setStageStatus("Completed");  //..

						}else {
							System.out.println("Redirect the Page ");
							System.out.println("   (tottalst!=curst)  tottalst"+tottalst+" curst"+curst);
							// code change pandian 28Mar2013						
							hryb.setStageMessageinFrontWF(wfId, documentId);
						}

					}
				}
			}else {
				System.out.println(selectedAttachDocData.getAttachUser()+"-=-=-=-=attachUser-=-=-=-="+selectedAttachDocData.getUploadDetails());
				if(selectedStatus != null) {
					RulesManager.performActionOnAttachmentDocument(selectedStatus, selectedAttachDocData, documentId, userName, notes);
//					if(selectedStatus.equals("Rejected")){
//						message="The status is updated successfully. This document is forwarded to previous team.";
//					}else{
					message="The status updated successfully. An e mail will be sent to the user who uploaded this document.";
//					}
					messageSubStr = message;
					/*if(messageSubStr!=null && messageSubStr.length()>50) {
						messageSubStr = messageSubStr.substring(0, 48)+"..";
					}*/
					hryb.attachmentTabDetailsAL1.clear();
					if(hryb.getSendPrimaryFileOnly().equalsIgnoreCase("true")) {
//						String userName=lgnbn.getUsername();
						hryb.attachmentTabDetailsAL1 = WorkflowManager.loadAttachmentByStageAndUser(documentId, hryb.getCurrentStatgeName(), userName);
					}else {
						hryb.attachmentTabDetailsAL1 = WorkflowManager.loadAttachmentTable(documentId);
					}
				}
			}

			//		UpdateStatusAndNotes2DB();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void displayingStatus(String docId,String currentstageNo,String userStatus, String comingFrom) {
		try{

			reasonText = "";
			this.comingFrom = comingFrom;
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
			RIConstrainsBean ricb = (RIConstrainsBean) sessionMap.get("rIConstrainsBean");
			LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
			//		String currentstageNo=hryb.getCurrentStageNumb();

			//code change pandian 19MAR2014

			/////////////////// code change Menaka 15APR2014
			String Hierarchy_ID=docId;

			//		System.out.println("Hierarchy_ID==============>>>"+Hierarchy_ID);	
			String totalStage=hryb.getTotalStageNum();
			//		System.out.println("totalStage==============>>>"+totalStage);	

			int tottalst1=Integer.parseInt(totalStage);

			//	System.out.println("tottalst1==============>>>"+tottalst1);	
			//	System.out.println("curst1==============>>>"+curst1);	
			LoginProcessManager  log=new LoginProcessManager();
			PropUtil prop=new PropUtil();
			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
			Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId); 
			//	insertWorkFlowActivity2DB(doc,"updatingStatusinXMl");
			Node wfNd = ((Element) docNd).getElementsByTagName("Workflow").item(0);
			//	String isstatusAvaible1=WorkflowManager.getFinalMsg(wfNd,userStatus);	
			currentstageNo = ((Element) wfNd).getAttribute("Current_Stage_No");
			int curst1=Integer.parseInt(currentstageNo);
			//	System.out.println("selectedStatus====MM==========>>>"+userStatus);	


			//	System.out.println("isstatusAvaible1==============>>>"+isstatusAvaible1);	


			//	System.out.println(isstatusAvaible1+" isstatusAvaible tottalst"+tottalst1+" curst"+curst1);

			Hashtable stageDetailsHT=log.retriveStageDetailsFromXML(wfNd, currentstageNo,"");
			Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
			/////////////////////////

			selectedStatus="";
			message="";
			messageSubStr = "";
			statusHM=new LinkedHashMap<String,String>();
			Iterator<Entry> it = mssgeDetailsHT.entrySet().iterator();
			while(it.hasNext()) {
				String val = it.next().getValue().toString();
				statusHM.put(val, val);
			}
			selectedStatus = userStatus;
			dispMsgStatusPopup=true;

			
			System.out.println("rIValidationFailure==============>>>"+ricb.rIValidationFailure);

			System.out.println("dispMsgStatusPopup==============>>>"+dispMsgStatusPopup);
			//		ricb.rIValidationFailure=false;

//			if(hryb.getSendPrimaryFileOnly().equalsIgnoreCase("true")) {
//				String userName=lgnbn.getUsername();
//				hryb.attachmentTabDetailsAL1 = WorkflowManager.loadAttachmentByStageAndUser(docId, hryb.getCurrentStatgeName(), userName);
//			}else {
//				hryb.attachmentTabDetailsAL1 = WorkflowManager.loadAttachmentTable(docId);
//			}
			//		String status[]=getStatusMsgs(hireID, currentstageNo);
			//		for(int i=0;i<status.length;i++){
			//			System.out.println("status==>"+status[i]);
			//			statusHM.put(status[i], status[i]);
			//		}


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void displayingStatus4Attachment(String docId,String currentstageNo,String userStatus, HeirarchyDataBean selectedData, String comingFrom) {
		try{

			this.reasonText = "";
			this.comingFrom = comingFrom;
			this.selectedAttachDocData = selectedData;
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
			RIConstrainsBean ricb = (RIConstrainsBean) sessionMap.get("rIConstrainsBean");
			WorkflowTabBean wtb = (WorkflowTabBean) sessionMap.get("workflowTabBean");
			LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
			//		String currentstageNo=hryb.getCurrentStageNumb();

			//code change pandian 19MAR2014

			/////////////////// code change Menaka 15APR2014
			String Hierarchy_ID=docId;

			//		System.out.println("Hierarchy_ID==============>>>"+Hierarchy_ID);	
			String totalStage=hryb.getTotalStageNum();
			//		System.out.println("totalStage==============>>>"+totalStage);	

			int tottalst1=Integer.parseInt(totalStage);

			//	System.out.println("tottalst1==============>>>"+tottalst1);	
			//	System.out.println("curst1==============>>>"+curst1);	
			LoginProcessManager  log=new LoginProcessManager();
			PropUtil prop=new PropUtil();
			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
			Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId); 
			//	insertWorkFlowActivity2DB(doc,"updatingStatusinXMl");
			Node wfNd = ((Element) docNd).getElementsByTagName("Workflow").item(0);
			//	String isstatusAvaible1=WorkflowManager.getFinalMsg(wfNd,userStatus);	
			currentstageNo = ((Element) wfNd).getAttribute("Current_Stage_No");
			int curst1=Integer.parseInt(currentstageNo);
			//	System.out.println("selectedStatus====MM==========>>>"+userStatus);	


			//	System.out.println("isstatusAvaible1==============>>>"+isstatusAvaible1);	


			//	System.out.println(isstatusAvaible1+" isstatusAvaible tottalst"+tottalst1+" curst"+curst1);

			Hashtable stageDetailsHT=log.retriveStageDetailsFromXML(wfNd, currentstageNo,"");
			Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
			/////////////////////////

			selectedStatus="";
			message="";
			messageSubStr = "";
			statusHM=new LinkedHashMap<String,String>();
			Iterator<Entry> it = mssgeDetailsHT.entrySet().iterator();
			while(it.hasNext()) {
				String val = it.next().getValue().toString();
				statusHM.put(val, val);
			}
			selectedStatus = userStatus;
			dispMsgStatusPopup=true;

			wtb.getAllAttributesFromWorkflow4Stage(docId);
			System.out.println("rIValidationFailure==============>>>"+ricb.rIValidationFailure);

			System.out.println("dispMsgStatusPopup==============>>>"+dispMsgStatusPopup);
			//		ricb.rIValidationFailure=false;
			

			//		String status[]=getStatusMsgs(hireID, currentstageNo);
			//		for(int i=0;i<status.length;i++){
			//			System.out.println("status==>"+status[i]);
			//			statusHM.put(status[i], status[i]);
			//		}


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public static String[] getStatusMsgs(String heirCode,String stageNo)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		String[] msgArr=null;
		try{

			Hashtable stageDetHT=getStageDetails(heirCode, stageNo);
			Hashtable msgHT=(Hashtable)stageDetHT.get("MessagedetHT");
			msgArr=new String[msgHT.size()];


			System.out.println(msgHT);

			for(int i=0;i<msgHT.size();i++)
			{
				String tempMsg="";
				if(i==msgHT.size()-1)
				{
					tempMsg=(String)msgHT.get("Final");
				}else
				{
					tempMsg=(String)msgHT.get(String.valueOf(i+1));	
				}

				msgArr[i]=tempMsg;
				System.out.println(msgArr[i]);
			}


		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());


		return msgArr;
	}



	public static Hashtable getStageDetails(String heirCode,String stageNo)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Hashtable stagedetHT=new Hashtable<>();
		try{
			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);
			if(workFlowN==null){
				return stagedetHT;
			}
			NodeList stageNL=workFlowN.getChildNodes();

			int key=0,stageKey=0,msgKey=0,empKey=0;

			for(int i=0;i<stageNL.getLength();i++)
			{
				Node stageN=stageNL.item(i);

				if(stageN.getNodeType()==Node.ELEMENT_NODE)
				{
					if(stageN.getNodeName().equalsIgnoreCase("Stage")&&stageN.getAttributes().getNamedItem("Stage_No").getNodeValue().equalsIgnoreCase(stageNo))
					{

						stagedetHT=Globals.getAttributeNameandValHT(stageN);
						NodeList msgANDNameNL=stageN.getChildNodes();

						for(int j=0;j<msgANDNameNL.getLength();j++)
						{

							Node nodeCheckN=msgANDNameNL.item(j);

							if(nodeCheckN.getNodeType()==nodeCheckN.ELEMENT_NODE)
							{

								if(nodeCheckN.getNodeName().equalsIgnoreCase("Status_Codes"))
								{

									NodeList MsgNL=nodeCheckN.getChildNodes();
									Hashtable messagedetHT=new Hashtable<>();
									for(int k=0;k<MsgNL.getLength();k++)
									{


										Node msgN=MsgNL.item(k);
										if(msgN.getNodeType()==Node.ELEMENT_NODE)
										{
											String levelNo=msgN.getAttributes().getNamedItem("level").getNodeValue();
											String msg=msgN.getTextContent();
											if(levelNo==null){levelNo="";}
											if(msg==null){msg="";}

											messagedetHT.put(levelNo,msg);
											msgKey++;
										}

									}
									stagedetHT.put("MessagedetHT",messagedetHT);


								}else if(nodeCheckN.getNodeName().equalsIgnoreCase("Employee_Names"))
								{

									NodeList empNameNL=nodeCheckN.getChildNodes();
									Hashtable empNameHT=new Hashtable<>();
									for(int k=0;k<empNameNL.getLength();k++)
									{
										Hashtable nameHT=new Hashtable<>();

										Node nameN=empNameNL.item(k);
										if(nameN.getNodeType()==Node.ELEMENT_NODE)
										{

											nameHT=Globals.getAttributeNameandValHT(nameN);
											nameHT.put("empName",nameN.getTextContent());
											empNameHT.put(empKey,nameHT);
											empKey++;
										}


									}

									stagedetHT.put("EmployeedetHT",empNameHT);

								}else if(nodeCheckN.getNodeName().equalsIgnoreCase("Properties"))
								{
									Hashtable propHT=new Hashtable<>();

									String properties=nodeCheckN.getTextContent();

									String Concurrency=nodeCheckN.getAttributes().getNamedItem("Concurrency").getNodeValue();
									if(!Concurrency.equalsIgnoreCase("Serial"))
									{
										String minApprovals=nodeCheckN.getAttributes().getNamedItem("Min_Approvers").getNodeValue();
										propHT.put("Min_Approvers",minApprovals);
									}
									propHT.put("Properties",properties);
									propHT.put("Concurrency",Concurrency);

									stagedetHT.put("PropertiesHT",propHT);
								}

							}
						}

					}
				}


			}
			System.out.println("Stage Details HT: "+stagedetHT);	


		}catch(Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return stagedetHT;

	}


	//code change vishnu getLogin Detaails  
	public static Hashtable<String,String> getLoginDetails(String loginID) {
		Hashtable<String,String> attrHT = new Hashtable<>();
		String HierarchyDir="";
		String HierarchyloginXml="";
		try{
			PropUtil prop=new PropUtil();
			HierarchyDir=prop.getProperty("HIERARCHY_XML_DIR");
			HierarchyloginXml=prop.getProperty("LOGIN_XML_FILE");

			Document doc=Globals.openXMLFile(HierarchyDir, HierarchyloginXml);
			NodeList ndList1=doc.getElementsByTagName("Obiee_Users");
			NodeList ndList=ndList1.item(0).getChildNodes();
			for(int i=0;i<ndList.getLength();i++){
				if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE && ndList.item(i).getNodeName().contains("User_")){
					if(ndList.item(i).getTextContent().equals(loginID)){
						attrHT = Globals.getAttributeNameandValHT(ndList.item(i));   
						attrHT.put("Node_Name", ndList.item(i).getNodeName());
						attrHT.put("Login_ID", ndList.item(i).getTextContent());
					}
				}
			}		   

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return attrHT;
	}

	public void UpdateStatusAndNotes2DB(){ //code change Jayaramu 31MAR14
		try{
			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");


			Connection connection = Globals.getDBConnection("DW_Connection");
			String insertSql = "UPDATE WORK_FLOW_ACTIVITY SET TO_STATUS='"+selectedStatus+"',NOTES='"+reasonText+"' WHERE LOGIN_ID= '"+lgnbn.getUniqueID()+"'";
			System.out.println("User Login Details For UPDATE DB TABLE : insert SQL "+insertSql);
			PreparedStatement ps=connection.prepareStatement(insertSql);
			ps.execute();
			ps.close();
			connection.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public void insertWorkFlowActivity2DB(Document doc,String updateFrom){ //Start code change Jayaramu 10APR14
		try{
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."

		     + new Exception().getStackTrace()[0].getMethodName());

			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");
			HierarchyBean hryb = (HierarchyBean) sessionMap1.get("hierarchyBean");
			LoginProcessManager  log=new LoginProcessManager();
			Hashtable LoginDetailsHT=log.getLoginDetails(lgnbn.getUsername());
			String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
			//		  System.out.println("AccessUniqID====>>>"+AccessUniqID);

			String userRole = "";
			String from_Status = "";
			String member_Mail_ID = "";
			String from_Stage = "";
			String to_Stage = "";
			String to_Status = "";
			String currentStageNo = "";
			String hierarchy_Name = "";
			String hierarchy_Type = "";
			int totalStage = 0;
			int curStage = 0;
			PropUtil prop=new PropUtil();
			String xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			Node nd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID",hryb.getHierarchy_ID());
			Hashtable WFNodeDetailsHT=Globals.getWFNodeDetails(hryb.getHierarchy_ID(), "",true,AccessUniqID);
			Hashtable UserDetailsHT=(Hashtable)WFNodeDetailsHT.get("UserDetailsHT");

			if(UserDetailsHT != null){		   
				userRole =  (String)UserDetailsHT.get("User_Role");
				member_Mail_ID = (String)UserDetailsHT.get("E-mail");
			}
			if(WFNodeDetailsHT != null){
				from_Stage = (String)WFNodeDetailsHT.get("Current_Stage_Name");
				currentStageNo=(String)WFNodeDetailsHT.get("Current_Stage_No"); 
				String totalnoOfstage=(String)WFNodeDetailsHT.get("Total_No_Stages");
				totalStage = Integer.parseInt(totalnoOfstage);
				if(currentStageNo.equals("Completed")){
					currentStageNo=totalnoOfstage;
				}
				curStage = Integer.parseInt(currentStageNo);
			}

			int hierID = Integer.parseInt(hryb.getHierarchy_ID());
			int toStageNo =0;
			if(totalStage > curStage){
				toStageNo = curStage+1;
			}else  if(totalStage==curStage){
				toStageNo = curStage;
			}
			Node wrkFlowNode=Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hryb.getHierarchy_ID());
			Node stageNode=Globals.getNodeByAttrValUnderParent(doc, wrkFlowNode, "Stage_No", String.valueOf(toStageNo));
			Node curstageNode=Globals.getNodeByAttrValUnderParent(doc, wrkFlowNode, "Stage_No", currentStageNo);
			Node stgNode = stageNode.getAttributes().getNamedItem("Stage_Name");
			Node stgstatus = stageNode.getAttributes().getNamedItem("Stage_Status");
			if(curstageNode != null){
				Element curStageNd = (Element)curstageNode;
				from_Status=curStageNd.getAttribute("Stage_Status");
			}
			if(stgNode != null){
				to_Stage = stgNode.getNodeValue();
				to_Status = stageNode.getAttributes().getNamedItem("Stage_Status").getNodeValue();
			}

			if(nd != null){
				hierarchy_Name = nd.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue();
				hierarchy_Type = nd.getAttributes().getNamedItem("RI_Hierarchy_Type").getNodeValue();
			}

			//		  System.out.println("to_Stage====>>>"+to_Stage);
			//		  System.out.println("currentStageNo====>>>"+currentStageNo);
			//		  System.out.println("userRole====>>>"+userRole);
			//		  System.out.println("memberstatuss====>>>"+from_Status);
			//		  System.out.println("stageRole====>>>"+from_Stage);
			//		  System.out.println("to_Status====>>>"+to_Status);

			System.out.println("User Login Details For UPDATE DB TABLE : to_Stage "+to_Stage+" currentStageNo "+currentStageNo+" userRole "+userRole+
					" from_Status "+from_Status+" from_Stage "+from_Stage+" to_Status "+to_Status);

			Date access_Date = new Date();
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
			String userAccessDate = sdf.format(access_Date);
			Date userDate = new Date(userAccessDate);
			java.sql.Timestamp useraccsDate = new java.sql.Timestamp(userDate.getTime()); 
			String uniqID = String.valueOf(hryb.getWfUniqueID());

			boolean isLoginIDisExists = false;
			ArrayList WorkFlowActivityAL = new ArrayList<>();
			Hashtable WorkFlowActivityHT = new Hashtable<>();
			String status = "";
			String notesText = "";
			Connection connection = Globals.getDBConnection("DW_Connection");

			String isLoginIDExists = "select * from WORK_FLOW_ACTIVITY where WF_UNIQUE_ID="+hryb.getWfUniqueID()+" AND HIERARCHY_ID="+hryb.getHierarchy_ID()+"";
			System.out.println("User Login isExists : isLoginIDExists  :"+isLoginIDExists);
			Statement statment=connection.createStatement();
			ResultSet results = statment.executeQuery(isLoginIDExists);
			System.out.println("results  :"+results);
			if(results.next()){
				isLoginIDisExists = true;
				WorkFlowActivityHT.put("ROLE",userRole);
				WorkFlowActivityHT.put("FROM_STAGE",from_Stage);
				if(updateFrom.equalsIgnoreCase("updatingStatusFromAdmininXMl")){
					WorkFlowActivityHT.put("MEMBER_STATUS",selectedStatusAdmin);
					WorkFlowActivityHT.put("NOTES",reasonText4admin);
					if(!currentStageName.equals(adminSelectStage)){
						to_Stage =  adminSelectStage;
					}
				}else if(updateFrom.equalsIgnoreCase("updatingStatusinXMl")){
					WorkFlowActivityHT.put("MEMBER_STATUS",selectedStatus);
					WorkFlowActivityHT.put("NOTES",reasonText);
				}
				WorkFlowActivityHT.put("TO_STAGE",to_Stage);
				WorkFlowActivityHT.put("FROM_STATUS",from_Status);
				WorkFlowActivityHT.put("HIERARCHY_NAME",hierarchy_Name);
				WorkFlowActivityHT.put("HIERARCHY_TYPE",hierarchy_Type);
				WorkFlowActivityHT.put("MAIL_NOTIFICATION","");
				WorkFlowActivityHT.put("REJECTED_RETURN_TYPE","");
				WorkFlowActivityHT.put("REJECTED_SET_STATUS","");
				WorkFlowActivityHT.put("REJECTED_NOTIFICATION_TYPE","");
				WorkFlowActivityHT.put("REJECTED_RETURN_TO_MEMBER","");
			}else{
				WorkFlowActivityAL.add(0,uniqID);
				WorkFlowActivityAL.add(1,lgnbn.getUseraccsDate());
				WorkFlowActivityAL.add(2,lgnbn.getUsername());
				WorkFlowActivityAL.add(3,userRole);
				WorkFlowActivityAL.add(4,member_Mail_ID);
				WorkFlowActivityAL.add(5,from_Stage);

				if(updateFrom.equalsIgnoreCase("updatingStatusFromAdmininXMl")){ //code change Jayaramu 10APR14
					if(!currentStageName.equals(adminSelectStage)){
						to_Stage =  adminSelectStage;
					}
					status = this.selectedStatusAdmin;
					notesText = this.reasonText4admin;

				}else if(updateFrom.equalsIgnoreCase("updatingStatusinXMl")){
					status = this.selectedStatus;
					notesText = this.reasonText;
				}
				WorkFlowActivityAL.add(6,to_Stage);
				WorkFlowActivityAL.add(7,from_Status);
				WorkFlowActivityAL.add(8,status);
				WorkFlowActivityAL.add(9,notesText);
				WorkFlowActivityAL.add(10,hierID);
				WorkFlowActivityAL.add(11,hierarchy_Name);
				WorkFlowActivityAL.add(12,hierarchy_Type);
				WorkFlowActivityAL.add(13,"");
				WorkFlowActivityAL.add(14,"");
				WorkFlowActivityAL.add(15,"");
				WorkFlowActivityAL.add(16,"");
				WorkFlowActivityAL.add(17,"");
			}
			hryb.updateWFActivityInDB(connection,WorkFlowActivityAL,hryb.getWfUniqueID(),lgnbn.getUsername(),WorkFlowActivityHT,isLoginIDisExists);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	//End code change Jayaramu 10APR14
	public String getMessageSubStr() {
		return messageSubStr;
	}
	public void setMessageSubStr(String messageSubStr) {
		this.messageSubStr = messageSubStr;
	}
	public String getComingFrom() {
		return comingFrom;
	}
	public void setComingFrom(String comingFrom) {
		this.comingFrom = comingFrom;
	}

}
