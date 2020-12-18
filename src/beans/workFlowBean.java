package beans;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import managers.LoginProcessManager;
import managers.RulesManager;
import model.RuleAttributeDataModel;
import model.RuleDataModel;

import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



import com.jniwrapper.e;

import utils.Globals;
import utils.PropUtil;


@SessionScoped
@ManagedBean(name = "workflowbean")
public class workFlowBean {

	public workFlowBean(){
//	loadDefaultLayoutTree();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String heirCode="2";
//		String stageNo="1";
//
//		Hashtable detHT= getStageDetails(heirCode,stageNo);
//		System.out.println(detHT);
//		
//		
//		String aa = "aaaaa (102)";
//		System.out.println(aa.substring(aa.indexOf("(")+1,aa.indexOf(")")));
		
	}
	 private TreeNode root;  
	    private TreeNode root3;
	  
	    String propertiesType = "";
	    String minApproveValue = "";
	    String rootTreeRendered = "false";
	    
	    public String getRootTreeRendered() {
			return rootTreeRendered;
		}
		public void setRootTreeRendered(String rootTreeRendered) {
			this.rootTreeRendered = rootTreeRendered;
		}
		String stageNameValue = "";
	    public String getStageNameValue() {
			return stageNameValue;
		}
		public void setStageNameValue(String stageNameValue) {
			this.stageNameValue = stageNameValue;
		}
		public String getMinApproveValue() {
			return minApproveValue;
		}
		public void setMinApproveValue(String minApproveValue) {
			this.minApproveValue = minApproveValue;
		}
		public String getPropertiesType() {
	    	
			return propertiesType;
		}
		public void setPropertiesType(String propertiesType) {
			
		
			
			
			this.propertiesType = propertiesType;
		}
	

		public TreeNode getRoot3() {
			return root3;
		}

		public void setRoot3(TreeNode root3) {
			this.root3 = root3;
		}

		public TreeNode getRoot4() {
			return root4;
		}

		public void setRoot4(TreeNode root4) {
			this.root4 = root4;
		}

		public TreeNode getRoot5() {
			return root5;
		}

		public void setRoot5(TreeNode root5) {
			this.root5 = root5;
		}

		public TreeNode getRoot6() {
			return root6;
		}

		public void setRoot6(TreeNode root6) {
			this.root6 = root6;
		}


		private TreeNode root4;
	    private TreeNode root5;
	    private TreeNode root6;
	   
	      
	    public void setRoot(TreeNode root) {
			this.root = root;
		}


		private TreeNode root2; 
		private TreeNode node3; 
	      
	    public TreeNode getNode3() {
			return node3;
		}
		public void setNode3(TreeNode node3) {
			this.node3 = node3;
		}
		public void setRoot2(TreeNode root2) {
			this.root2 = root2;
		}


		private TreeNode selectedNode;  
	  
	    private TreeNode selectedNode2;  
	    
	    
	    
	    
	    
	    
	    
	    
	    
	
	public static String addStageintoXML(String heirCode,String stageName,String[] empNames,String[] statusMsg,String[] empEmail,
			String[] empUid,String propName,String minApprovers,String stageNumber,String saveType,String selectedStageRole,
			String parallelType,String selectedMember,Hashtable hashValueTable,Hashtable rejectHT,Hashtable pauseHT,
			boolean autologin,boolean allowupload,boolean enableEsign, boolean externalUserFlag, boolean sendPrimaryFileOnlyFlag, Boolean statusCode4DocsFlag,boolean changeprimarydocFlag,
			ArrayList<RuleDataModel> rulesAL, ArrayList<RuleAttributeDataModel> rulesALTemp, Boolean allowEditUD) throws Exception
	{
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());
		  
		  try{
		PropUtil prop=new PropUtil();
		String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
		String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		
		LoginBean lb = (LoginBean) sessionMap.get("loginBean");
		DashboardBean sss=new DashboardBean();
		String dateFormat = prop.getProperty("DATE_FORMAT");
		Date HIERYDate = new Date();
		DashboardBean dshbn = (DashboardBean) sessionMap.get("dashboardBean");
		String dashboardURL= dshbn.getUrlParameter();
		String dashboardEditURL= dshbn.getEditwrkflowflag();
		String currentLogin="";
		String currentLoginKEY="";
		
		if(dashboardURL.equalsIgnoreCase("true") || dashboardEditURL.equalsIgnoreCase("true")) {
			
			currentLogin=dshbn.getUrlLogin();
			currentLoginKEY=dshbn.getUrlcus_key();
			
		}else {
			
			currentLogin=lb.username;
			currentLoginKEY=lb.getCustomerKey();
		}
		System.out.println(dshbn.getUrlLogin()+"******************dshbn******************"+dshbn.getUrlLogin());
		DateFormat sdf1 = new SimpleDateFormat(dateFormat);
		String StartDate = sdf1.format(HIERYDate);
		Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
		Node heirLevelN=Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", heirCode);
		Element hierEle = (Element)heirLevelN;
		if(hierEle.getAttribute("Hierarchy_Name").isEmpty()) {
			hierEle.setAttribute("Hierarchy_ID", heirCode);
			hierEle.setAttribute("Hierarchy_Name", dshbn.getHierName());
			hierEle.setAttribute("Created_By", currentLogin);
			hierEle.setAttribute("Modified_By", currentLogin);
			hierEle.setAttribute("Created_Date", StartDate);
			hierEle.setAttribute("Modified_Date", StartDate);
//			hierEle.setAttribute("Unique_ID", uniqueid);
		}else {
			hierEle=(Element)Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", heirCode);
			hierEle.setAttribute("Hierarchy_Name", dshbn.getHierName());
			hierEle.setAttribute("Modified_By", currentLogin);
			hierEle.setAttribute("Modified_Date", StartDate);
		}
		hierEle.setAttribute("CustomerKey",currentLoginKEY);
		Node checkifWorkflowisExists=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);
		
		
		int stageNo=0;
		if(saveType.equals("saveStage")){
			stageNo=Integer.parseInt(stageNumber);
		}else{
			stageNo=1;
		}
		

		Element stagesE=null;
		if(checkifWorkflowisExists==null){
		
		stagesE=xmlDoc.createElement("Workflow");
		stagesE.setAttribute("Workflow_Level","Custom");
		stagesE.setAttribute("Hierarchy_ID",heirCode);
		if(saveType.equals("saveStage")){
		}else{
			stagesE.setAttribute("Total_No_Stages",String.valueOf(stageNo));
			stagesE.setAttribute("Current_Stage_Name",stageName);
			stagesE.setAttribute("Current_Stage_No",String.valueOf(stageNo));
			
		}
		
		
		heirLevelN.appendChild(stagesE);
		}else
		{
			stagesE=(Element)checkifWorkflowisExists;
			
			if(saveType.equals("saveStage")){
				
				
			}else{
				
				stageNo=Integer.parseInt(stagesE.getAttribute("Total_No_Stages"));
				stageNo++;
				stagesE.setAttribute("Total_No_Stages",String.valueOf(stageNo));
				
				
			}
			
		}
		
		Node isStageExists=Globals.getNodeByAttrValUnderParent(xmlDoc, (Node)stagesE, "Stage_No", String.valueOf(stageNo));
		
		if(isStageExists!=null){
			isStageExists.getParentNode().removeChild(isStageExists);
		}
			
		String reject_Return_To =(String) rejectHT.get("Rejected_Return_To");
		String rejected_Set_Status_To_Type =(String) rejectHT.get("Rejected_Set_Status_To_Type");
		String rejected_Set_Status_To =(String) rejectHT.get("Rejected_Set_Status_To");
		String rejected_Notification_To =(String) rejectHT.get("Rejected_Notification_To");
		
		String pass_Notification_To =(String) pauseHT.get("Pass_Notification_To");
		
		System.out.println("reject_Return_To *:"+reject_Return_To +" rejected_Set_Status_To_Type *:"+rejected_Set_Status_To_Type +"rejected_Set_Status_To *:"+rejected_Set_Status_To+"rejected_Notification_To *:"+rejected_Notification_To);
		
		Element stageE=xmlDoc.createElement("Stage");
		stageE.setAttribute("Stage_No", String.valueOf(stageNo));
		stageE.setAttribute("Stage_Name",stageName);
		stageE.setAttribute("Stage_Status",statusMsg.length > 0 ? statusMsg[0] : "YTS");
		
		stageE.setAttribute("Auto_Login",String.valueOf(autologin));
		stageE.setAttribute("Allow_Upload",String.valueOf(allowupload));
		stageE.setAttribute("Enable_Esign",String.valueOf(enableEsign));
		stageE.setAttribute("ExternalUser",String.valueOf(externalUserFlag));
		stageE.setAttribute("SendPrimaryFileOnly",String.valueOf(sendPrimaryFileOnlyFlag));
		stageE.setAttribute("StatusCode4Documents", String.valueOf(statusCode4DocsFlag));
		stageE.setAttribute("Change_PrimaryFileOnly",String.valueOf(changeprimarydocFlag));
		stageE.setAttribute("AllowUltraDocEdit",String.valueOf(allowEditUD));
		
		
		stageE.setAttribute("Rejected_Return_To", reject_Return_To);
		stageE.setAttribute("Rejected_Set_Status_To_Type",rejected_Set_Status_To_Type);
		stageE.setAttribute("Rejected_Set_Status_To",rejected_Set_Status_To);
		stageE.setAttribute("Rejected_Notification_To",rejected_Notification_To);
		stageE.setAttribute("Pass_Notification_To",pass_Notification_To);
		
		
	stageE.setAttribute("Select_Member",selectedMember);
		
		stagesE.appendChild(stageE);
		stageE.setAttribute("Stage_Role","");
		
		Element statusMsgE=xmlDoc.createElement("Status_Codes");
		stageE.appendChild(statusMsgE);

		//code change pandian 21032014
		
		 
			dshbn.setUsercreateStageNumb(String.valueOf(stageNo));
		
		
		//Setting Status Message
		for(int i=0;i<statusMsg.length;i++)
		{
			Element msgE=xmlDoc.createElement("Status_Code");
			if(i==statusMsg.length-1)
			{
				msgE.setAttribute("level","Final");
			}else{
				msgE.setAttribute("level",String.valueOf(i+1));	
			}
			msgE.setTextContent((String)statusMsg[i]);
			statusMsgE.appendChild(msgE);
		}
		
		
		Element empNamesE=xmlDoc.createElement("Employee_Names");
		stageE.appendChild(empNamesE);
		
		
		//code change VIJAY 2018

		String EscalateUniIDs =(String) hashValueTable.get("unidID");
		String EscalateEmailIDs =(String) hashValueTable.get("Email_ID");
		String EscalateuserTextcontents =(String) hashValueTable.get("empNameFrmlaxml");
		
		// String EscalateUniIDs =hashValueTable.get(3);
		
		Element escalateNamesE=xmlDoc.createElement("Escalate_Managers");
		stageE.appendChild(escalateNamesE);

		Element escalatesubN=xmlDoc.createElement("Name");
		
		escalatesubN.setAttribute("Access_Unique_ID",EscalateUniIDs);
		escalatesubN.setAttribute("E-mail",EscalateEmailIDs);
		escalatesubN.setAttribute("UserName",selectedMember);
		escalatesubN.setTextContent(EscalateuserTextcontents);
		escalateNamesE.appendChild(escalatesubN);
		
		
		for(int i=0;i<empNames.length;i++)
		{
			Element nameE=xmlDoc.createElement("Name");
			nameE.setAttribute("E-mail",(String)empEmail[i]);
			nameE.setAttribute("Access_Unique_ID",(String)empUid[i]);
			nameE.setAttribute("User_Status","YTS");
			nameE.setAttribute("User_Role",selectedStageRole);
			nameE.setTextContent((String)empNames[i]);
			empNamesE.appendChild(nameE);
			
		}
		
		
		Element propE=xmlDoc.createElement("Properties");
		if(propName!=null)
		{
			if(propName.equalsIgnoreCase("Parallel")){
				String percent_value = "";
					if(parallelType.equals("Percent")){ //code change Jayaramu 10APR14
						percent_value = minApprovers;
						double percent = Double.parseDouble(minApprovers);
						double totalNumberOfMembers = empNames.length;
					    double minAppr= (percent*totalNumberOfMembers)/100;
					minApprovers = String.valueOf(Math.round(minAppr));
					}
				propE.setAttribute("Min_Approvers", minApprovers);
				propE.setAttribute("Concurrency",propName);
				propE.setAttribute("Parallel_Type", parallelType);
				propE.setAttribute("Min_Approvers_Percent", percent_value);
				propE.setTextContent(propName);	
				}
			else
			{
				propE.setAttribute("Concurrency",propName);
				propE.setTextContent(propName);	
			}
		}
		
		stageE.appendChild(propE);
		
		Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);
		
		RulesManager.saveRulesInStage(heirCode, stageName, rulesAL, rulesALTemp);
		  }catch (Exception e) {
			e.printStackTrace();
		}
		
	 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());
		  
		
		return null;
		
	}
	
	

//	commended by pandian 18MAR2014
//	public static String setStatusMsg4EMP(String heirCode,String stageNo,String empID,String statusMsg) throws Exception
//	{
//		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());
//		
//		try{
//		PropUtil prop=new PropUtil();
//		String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
//		String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
//		String dateFormat=prop.getProperty("DATE_FORMAT");
//		
//		
//		Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
//		
//		Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);
//		Node stageN=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", stageNo);
////		Node stageN=Globals.getNodeByAttrVal(xmlDoc, "Stage", "Stage_No", stageNo);
//		NodeList msgEnamePropNL=stageN.getChildNodes();
//		for(int i=0;i<msgEnamePropNL.getLength();i++)
//		{
//			Node checkN=msgEnamePropNL.item(i);
//			if(checkN.getNodeType()==Node.ELEMENT_NODE)
//			{
//				if(checkN.getNodeName().equalsIgnoreCase("Employee_Names"))
//				{
//					
//					Node nameN=Globals.getNodeByAttrValUnderParent(xmlDoc, checkN, "Access_Unique_ID", empID);
////					Node nameN=Globals.getNodeByAttrVal(xmlDoc, "Name", "Access_Unique_ID", empID);
//						if(nameN!=null)
//						{
//							Date lastAccDate=new Date();
////							Format formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//							Format formatter = new SimpleDateFormat(dateFormat);
//							
//							String accdate=formatter.format(lastAccDate);
//							
//							Element nameE=(Element)nameN;
//							nameE.setAttribute("User_Status", statusMsg);
//							nameE.setAttribute("Last_Access", accdate);
//							System.out.println("Last Accessed Date : "+accdate);
//							Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);
//						}
//				}
//				
//			}
//			
//			
//		}
//		
//		
//			
//			
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());
//		
//		return null;
//		
//	}
//	
//	
//	public static Hashtable getStageDetails(String heirCode,String stageNo)
//	{
//		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());
//		Hashtable stagedetHT=new Hashtable<>();
//		try{
//		PropUtil prop=new PropUtil();
//		String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
//		String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
//		Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
//		Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);
//		NodeList stageNL=workFlowN.getChildNodes();
//		
//		int key=0,stageKey=0,msgKey=0,empKey=0;
//		
//		for(int i=0;i<stageNL.getLength();i++)
//		{
//			Node stageN=stageNL.item(i);
//			
//			if(stageN.getNodeType()==Node.ELEMENT_NODE)
//			{
//				if(stageN.getNodeName().equalsIgnoreCase("Stage")&&stageN.getAttributes().getNamedItem("Stage_No").getNodeValue().equalsIgnoreCase(stageNo))
//				{
//
//				stagedetHT=Globals.getAttributeNameandValHT(stageN);
//				NodeList msgANDNameNL=stageN.getChildNodes();
//				
//				for(int j=0;j<msgANDNameNL.getLength();j++)
//				{
//					
//					Node nodeCheckN=msgANDNameNL.item(j);
//					
//					if(nodeCheckN.getNodeType()==nodeCheckN.ELEMENT_NODE)
//					{
//						
//						if(nodeCheckN.getNodeName().equalsIgnoreCase("Status_Codes"))
//						{
//						
//							NodeList MsgNL=nodeCheckN.getChildNodes();
//							Hashtable messagedetHT=new Hashtable<>();
//							for(int k=0;k<MsgNL.getLength();k++)
//							{
//								
//						
//								Node msgN=MsgNL.item(k);
//								if(msgN.getNodeType()==Node.ELEMENT_NODE)
//								{
//									String levelNo=msgN.getAttributes().getNamedItem("level").getNodeValue();
//									String msg=msgN.getTextContent();
//									if(levelNo==null){levelNo="";}
//									if(msg==null){msg="";}
//									
//									messagedetHT.put(levelNo,msg);
//									msgKey++;
//								}
//								
//						}
//							stagedetHT.put("MessagedetHT",messagedetHT);
//							
//							
//						}else if(nodeCheckN.getNodeName().equalsIgnoreCase("Employee_Names"))
//						{
//						
//							NodeList empNameNL=nodeCheckN.getChildNodes();
//							Hashtable empNameHT=new Hashtable<>();
//							for(int k=0;k<empNameNL.getLength();k++)
//							{
//								Hashtable nameHT=new Hashtable<>();
//						
//								Node nameN=empNameNL.item(k);
//								if(nameN.getNodeType()==Node.ELEMENT_NODE)
//								{
//
//									nameHT=Globals.getAttributeNameandValHT(nameN);
//									nameHT.put("empName",nameN.getTextContent());
//									empNameHT.put(empKey,nameHT);
//									empKey++;
//								}
//								
//								
//							}
//							
//							stagedetHT.put("EmployeedetHT",empNameHT);
//					
//						}else if(nodeCheckN.getNodeName().equalsIgnoreCase("Properties"))
//						{
//							Hashtable propHT=new Hashtable<>();
//							
//							String properties=nodeCheckN.getTextContent();
//							
//							String Concurrency=nodeCheckN.getAttributes().getNamedItem("Concurrency").getNodeValue();
//							if(!Concurrency.equalsIgnoreCase("Serial"))
//							{
//								String minApprovals=nodeCheckN.getAttributes().getNamedItem("Min_Approvers").getNodeValue();
//								propHT.put("Min_Approvers",minApprovals);
//							}
//							propHT.put("Properties",properties);
//							propHT.put("Concurrency",Concurrency);
//							
//							stagedetHT.put("PropertiesHT",propHT);
//						}
//						
//					}
//				}
//
//			}
//			}
//			
//			
//		}
//		System.out.println("Stage Details HT: "+stagedetHT);	
//		
//		
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());
//		return stagedetHT;
//		
//	}
	
	
	public static String[] getAvailableStages4user(String heirCode, String empID)
	{
		try{
		PropUtil prop=new PropUtil();
		String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
		String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
		Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
		Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);
		String tempnofStages=workFlowN.getAttributes().getNamedItem("Total_No_Stages").getNodeValue();
		int nofStages=Integer.parseInt(tempnofStages);
		
		for(int i=0;i<nofStages;i++)
		{
			
		}
		
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	
	public static String[] getStatusMsgs(String heirCode,String stageNo)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		String[] msgArr=null;
		try{
			
			LoginProcessManager lgn=new LoginProcessManager();
		Hashtable stageDetHT=lgn.getStageDetails(heirCode, stageNo,"");
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
	


public static String deleteNodefromWorkflow(String heirCode,String deleteNodeName,String stageUniqueId,String StageName,String uniqueId)
{
try{
	PropUtil prop=new PropUtil();
	String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
	String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
	Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
	Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);
	if(workFlowN!=null)
	{
		//code change pandian 09APR2014
		Element workFlowNEL=(Element)workFlowN;
		String totalStage=workFlowNEL.getAttribute("Total_No_Stages");
		String currentStageName=workFlowNEL.getAttribute("Current_Stage_Name");
		String currentStageNo=workFlowNEL.getAttribute("Current_Stage_No");
		
		System.out.println("deleteNodefromWorkflow : StageName "+StageName+" currentStageName "+currentStageName);
//		if(StageName.equalsIgnoreCase(currentStageName)){
//			//if current stage equals delete stage
//			// send warning message
//			return "sendWarnningMsgCurrent";
//		}
		
		
						
		
	if(deleteNodeName.equalsIgnoreCase("Stage"))
	{
		Node StageN=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN,"Stage_No", stageUniqueId);
		if(StageN!=null)
		{
			
			//code change pandian 09APR2014
			Element StageNEL=(Element)StageN;  // Stage_Status="Completed"
			String stageStatus=StageNEL.getAttribute("Stage_Status");
			System.out.println("deleteNodefromWorkflow : stageStatus "+stageStatus);
			if(stageStatus.equalsIgnoreCase("Completed")){				
				// send warning message
				return "sendWarnningMsgCompleted";
		
			}
			
			
			workFlowN.removeChild(StageN);
			
			
			if(!StageName.equals("Admin") && !StageName.equals("public")){
				
			//start code change pandian 15MAR2014
			Element workFlowEl=(Element)workFlowN;
			String totalStagenumber=(String)workFlowEl.getAttribute("Total_No_Stages");
			String WFCurrentStageNo=(String)workFlowEl.getAttribute("Current_Stage_No");
			
			
			int totstage=Integer.parseInt(totalStagenumber);
			
			int delstagenumb=Integer.parseInt(stageUniqueId);
			boolean start=false;
			int stagenum=0;
			
			Hashtable stageNameHT=new Hashtable<>();
			
			int updatenumber=delstagenumb;
			for(int j=0;j<totstage;j++){
				stagenum=j+1;
				if(start){
					Node updateStageN=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN,"Stage_No", String.valueOf(stagenum));
					Element updateStageNEL=(Element)updateStageN;
					String stagename=updateStageNEL.getAttribute("Stage_Name");
					updateStageNEL.setAttribute("Stage_No", String.valueOf(updatenumber));	
					
					stageNameHT.put(updatenumber, stagename);
					
					updatenumber=updatenumber+1;
				}
				
				if(stagenum==delstagenumb){ start=true; }
			}
			
			int uptotstage=totstage-1;
			workFlowEl.setAttribute("Total_No_Stages", String.valueOf(uptotstage));
			if(stageUniqueId.equals(WFCurrentStageNo)){
				
				String updatestageName=String.valueOf(stageNameHT.get(delstagenumb));
				workFlowEl.setAttribute("Current_Stage_Name", updatestageName);
				
			}
			//end code change pandian 15MAR2014
			
		} //end of-->if(!StageName.equals("Admin") && !StageName.equals("public"))
			
		}
		
	}else if(deleteNodeName.equalsIgnoreCase("Status_Code"))
	{
		Node StageN=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN,"Stage_No", stageUniqueId);
		if(StageN!=null)
		{
			NodeList msgORempNameNL=StageN.getChildNodes();
			for(int i=0;i<msgORempNameNL.getLength();i++)
			{
				Node checkN=msgORempNameNL.item(i);
				if(checkN.getNodeType()==Node.ELEMENT_NODE)
				{
					if(checkN.getNodeName().equalsIgnoreCase("Status_Codes"))
					{
						Node msgN=Globals.getNodeByAttrValUnderParent(xmlDoc, checkN,"level", uniqueId);
						if(msgN!=null)
						{
							checkN.removeChild(msgN);
						}
					}
				}
				
			}
		
		}
		
	}else if(deleteNodeName.equalsIgnoreCase("Name"))
	{
		
		Node StageN=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN,"Stage_No", stageUniqueId);
		if(StageN!=null)
		{
			NodeList msgORempNameNL=StageN.getChildNodes();
			for(int i=0;i<msgORempNameNL.getLength();i++)
			{
				Node checkN=msgORempNameNL.item(i);
				if(checkN.getNodeType()==Node.ELEMENT_NODE)
				{
					if(checkN.getNodeName().equalsIgnoreCase("Employee_Names"))
					{
						Node empNameN=Globals.getNodeByAttrValUnderParent(xmlDoc, checkN,"Access_Unique_ID", uniqueId);
						if(empNameN!=null)
						{
							checkN.removeChild(empNameN);
						}
					}
				}
				
			}
		
		}
	}else if(deleteNodeName.equalsIgnoreCase("Workflow"))
	{
		workFlowN.getParentNode().removeChild(workFlowN);
	}
	
	Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);
	}
	
}catch(Exception e)
{
	e.printStackTrace();
	}
return uniqueId;

}


public synchronized static Node importDefaultWorkFlowTemplate(String heirCode,Document sourceDoc, Document targetDoc,Node targetNode) {

	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
	
	Node impNode = null;
	try {
	
	NodeList reportNL = sourceDoc.getFirstChild().getChildNodes();
	Node node;
	
//	Element rootB = targetDoc.getDocumentElement();
	
	for (int x = 0; x < reportNL.getLength(); x++) {
	node = reportNL.item(x);
	

	if (node.getNodeName().equals("Workflow")) {
		Node isExists=Globals.getNodeByAttrVal(targetDoc, "Workflow", "Hierarchy_ID", heirCode);
		if(isExists==null){
			impNode = targetNode.appendChild(targetDoc.importNode(node, true));
		}else
		{
			
			NodeList list=isExists.getChildNodes();
			
			if(list.getLength()==0){
				//code change remove this code pandian 21Mar2014 11.40pm
				isExists.getParentNode().removeChild(isExists);
				impNode = targetNode.appendChild(targetDoc.importNode(node, true));
			}
			
			
		}
	
	break;
	}
	}
	
	
	
	} catch (Exception e) {
	e.printStackTrace();
	} 
	
	System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

return impNode;

}

public static Hashtable getStatusMsgFromConfig() throws Exception
{
	Hashtable defaultMsgCodesHT=new Hashtable<>();		
	
	PropUtil prop=new PropUtil();
	String heirXMLdir=prop.getProperty("HIERARCHY_XML_DIR");
	String heirConfigXML=prop.getProperty("HIERARCHY_CONFIG_FILE");
	
	Document configDoc=Globals.openXMLFile(heirXMLdir, heirConfigXML);
	
	NodeList defaultStatusCodesNL=configDoc.getElementsByTagName("Default_Status_Codes");
	Node defaultStatusCodeN=defaultStatusCodesNL.item(0);
	
	NodeList defaultMsgCodes=defaultStatusCodeN.getChildNodes();
	int key=0;
	for(int i=0;i<defaultMsgCodes.getLength();i++)
	{
		Node msgCode=defaultMsgCodes.item(i);
		if(msgCode.getNodeType()==Node.ELEMENT_NODE)
		{
			String msgCodes=msgCode.getTextContent();
			if(msgCodes==null){msgCodes="";}
			defaultMsgCodesHT.put(key, msgCodes);
			key++;
		}
		
	}
	
	
	return defaultMsgCodesHT;
}

public static Hashtable getAllUsersFromlaXML() throws Exception
{
	Hashtable allUsersHT=new Hashtable<>();		
	
	PropUtil prop=new PropUtil();
	String heirXMLdir=prop.getProperty("HIERARCHY_XML_DIR");
	String heirlaXML=prop.getProperty("LOGIN_XML_FILE");
	
	Document laDoc=Globals.openXMLFile(heirXMLdir, heirlaXML);
	
	NodeList defaultUserssNL=laDoc.getElementsByTagName("Obiee_Users");
	Node defaultusersN=defaultUserssNL.item(0);
	
	NodeList defaultUsernodeNL=defaultusersN.getChildNodes();
	int key=0;
	for(int i=0;i<defaultUsernodeNL.getLength();i++)
	{
		Node userN=defaultUsernodeNL.item(i);
		if(userN.getNodeType()==Node.ELEMENT_NODE)
		{
			Hashtable attHT=Globals.getAttributeNameandValHT(userN);
			String empName=userN.getTextContent();
			if(empName==null){empName="";}
			attHT.put("Employee_Name", empName);
			allUsersHT.put(key,attHT);
			key++;
		}
		
	}
	
	
	return allUsersHT;
}

public static String addStatusMsgToConfig(String statusMsg) throws Exception
{
	Hashtable defaultMsgCodesHT=new Hashtable<>();		
	
	PropUtil prop=new PropUtil();
	String heirXMLdir=prop.getProperty("HIERARCHY_XML_DIR");
	String heirConfigXML=prop.getProperty("HIERARCHY_CONFIG_FILE");
	
	Document configDoc=Globals.openXMLFile(heirXMLdir, heirConfigXML);
	
	NodeList defaultStatusCodesNL=configDoc.getElementsByTagName("Default_Status_Codes");
	Node defaultStatusCodeN=defaultStatusCodesNL.item(0);
	Element defaultStatusCodeE=(Element)defaultStatusCodeN;
	Element msgE=configDoc.createElement("Status_Code");
	msgE.setTextContent(statusMsg);
	defaultStatusCodeE.appendChild(msgE);
	Globals.writeXMLFile(configDoc, heirXMLdir, heirConfigXML);
	
	return null;
}

public static String deleteStatusMsgInConfig(String statusMsg) throws Exception
{
	Hashtable defaultMsgCodesHT=new Hashtable<>();		
	
	PropUtil prop=new PropUtil();
	String heirXMLdir=prop.getProperty("HIERARCHY_XML_DIR");
	String heirConfigXML=prop.getProperty("HIERARCHY_CONFIG_FILE");
	
	Document configDoc=Globals.openXMLFile(heirXMLdir, heirConfigXML);
	
	NodeList defaultStatusCodesNL=configDoc.getElementsByTagName("Default_Status_Codes");
	
	Node defaultStatusCodeN=defaultStatusCodesNL.item(0);
	
	NodeList defaultMsgCodes=defaultStatusCodeN.getChildNodes();
	int key=0;
	for(int i=0;i<defaultMsgCodes.getLength();i++)
	{
		Node msgCode=defaultMsgCodes.item(i);
		if(msgCode.getNodeType()==Node.ELEMENT_NODE)
		{
			String msg=msgCode.getTextContent();
			if(msg.equalsIgnoreCase(statusMsg))
			{
				msgCode.getParentNode().removeChild(msgCode);
				break;
			}
		}
	}
	Globals.writeXMLFile(configDoc, heirXMLdir, heirConfigXML);
	
	return null;
}

public void loadDefaultLayoutTree(String workFlowType, String hierCode){
	

	try{
		////Loading Status from Config File
		
		LoginProcessManager lgn=new LoginProcessManager();
        FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
//		HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");
		//DashboardBean dsbn = (DashboardBean) sessionMap.get("dashboardBean");
//		String heirCode=hrybn.getHierarchy_ID();
		
		String selectionType=workFlowType;
		
	//	dsbn.setStageNameValue("Enter_Stage_Name");
	//	dsbn.setStageModel(new DefaultDashboardModel());
		 
		
		
		  Hashtable defaultStatusMsgHT = getStatusMsgFromConfig();
		  root3 = new DefaultTreeNode("Default Status Msg", null);  
		  for(int i=0;i<defaultStatusMsgHT.size();i++){
		  String defaultMsgs = String.valueOf(defaultStatusMsgHT.get(i));
		  TreeNode defaultMsgNode = new DefaultTreeNode(defaultMsgs, root3);  
		  }
		  
		  
		//Loading Users from La.xml file
		  Hashtable allUsersHT = getAllUsersFromlaXML();
		  root4 = new DefaultTreeNode("All Employees Name", null);  
		  for(int i=0;i<allUsersHT.size();i++){
			  Hashtable singleUserHT=(Hashtable)allUsersHT.get(i);
		  String allUsers = String.valueOf(singleUserHT.get("Employee_Name"));
		  allUsers = allUsers +" ("+String.valueOf(singleUserHT.get("Access_Unique_ID"))+")";
		  TreeNode allUsersNode = new DefaultTreeNode(allUsers, root4);  
		  }
		  
		    root2 = new DefaultTreeNode("Work Flow", null);  
		    node3 = new DefaultTreeNode("Work Flow", root2);  
		    
		   
		
		 System.out.println("***********************selectionType***********"+selectionType);
		
		PropUtil prop=new PropUtil();
		String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
		String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
		String workFlowTemplateXML=prop.getProperty("WORKFLOW_TEMPLATE_XML_FILE");
	
	Document sourceDoc=Globals.openXMLFile(heirLeveldir, workFlowTemplateXML);
	Document targetDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
	Node targetNode=Globals.getNodeByAttrVal(targetDoc, "Hierarchy_Level", "Hierarchy_ID", hierCode);
	
	if(selectionType.equalsIgnoreCase("Default")){
		rootTreeRendered = "true";
		
		 System.out.println("********1111***************selectionType***********"+selectionType);
		
		Node impNode=importDefaultWorkFlowTemplate(hierCode, sourceDoc, targetDoc, targetNode);
		if(impNode!=null){
			Element impE=(Element)impNode;
			impE.setAttribute("Hierarchy_ID",hierCode);
		}
		
		Globals.writeXMLFile(targetDoc, heirLeveldir, heirLevelXML);
		
	
//	 dsbn.setTapName("Default");
	
	}else if(selectionType.equalsIgnoreCase("Custom"))
	{ System.out.println("*************222**********selectionType***********"+selectionType);
		rootTreeRendered = "false";
		Node workFlowN=Globals.getNodeByAttrVal(targetDoc, "Workflow", "Hierarchy_ID", hierCode);
		
		//code change remove this code pandian 21Mar2014 11.40pm
		if(workFlowN!=null)
		{
			
	NodeList list=workFlowN.getChildNodes();
			
			if(list.getLength()==0){
				//code change remove this code pandian 21Mar2014 11.40pm
				workFlowN.getParentNode().removeChild(workFlowN);
				Globals.writeXMLFile(targetDoc, heirLeveldir, heirLevelXML);
			}
			
		
			  
		}
		
	//	dsbn.setModel(new DefaultDashboardModel());
	//	dsbn.setPanelAL(new ArrayList<>());
		 
		
	//	dsbn.setCustommodel(new DefaultDashboardModel());
	        DashboardColumn column1 = new DefaultDashboardColumn();
	        DashboardColumn column2 = new DefaultDashboardColumn();
	        DashboardColumn column3 = new DefaultDashboardColumn();
	        
	 //       dsbn.getCustommodel().addColumn(column1);
	 //       dsbn.getCustommodel().addColumn(column2);
	  //      dsbn.getCustommodel().addColumn(column3);
//	       dsbn.setTapName("Custom");
	       
		
	}
	
	
	Node workFlowN=Globals.getNodeByAttrVal(targetDoc, "Workflow", "Hierarchy_ID", hierCode);
	String tempnofStages="";
	if(workFlowN!=null)
	{
	Element workFlowE=(Element)workFlowN;
	tempnofStages=workFlowE.getAttribute("Total_No_Stages");
	}		
	  root = new DefaultTreeNode("Root", null);
	
	  int nOFStages=0;
	  if(tempnofStages!=null&&!tempnofStages.equalsIgnoreCase(""))
	  {
		  nOFStages=Integer.parseInt(tempnofStages);
		  
		  //Loading the XML data in to Tree
		  for(int i=0;i<nOFStages;i++)
		  {
		
			 Hashtable stageDetailsHT =lgn.getStageDetails(hierCode,String.valueOf(i+1),"");
		 String rootName=(String)stageDetailsHT.get("Stage_Name");
		 TreeNode node0 = new DefaultTreeNode(rootName, root);
		 
		 String childofStageNode="";
		 String stageChild = "";
		 Node stageN=Globals.getNodeByAttrValUnderParent(targetDoc, workFlowN, "Stage_No", String.valueOf(i+1));
		 NodeList stageNL=stageN.getChildNodes();
		 for(int j=0;j<stageNL.getLength();j++)
		 {
			 Node childofStage=stageNL.item(j);
			 if(childofStage.getNodeType()==Node.ELEMENT_NODE)
			 {
				 childofStageNode=childofStage.getNodeName();
				 
				 if(!childofStageNode.equalsIgnoreCase("Properties"))
				 {
				 TreeNode node1 = new DefaultTreeNode(childofStageNode, node0);
				 
				
				 NodeList childnode = childofStage.getChildNodes();
				 
				for(int k=0;k<childnode.getLength();k++){
					
					Node chnode = childnode.item(k);
					
					if(chnode.getNodeType()==Node.ELEMENT_NODE)
					 {
						stageChild=chnode.getTextContent();
						 TreeNode node2 = new DefaultTreeNode(stageChild, node1);
						
					 }
				}
				 }
//				else
//				 {
//					 String concurrency=childofStage.getAttributes().getNamedItem("Concurrency").getNodeValue();
//					 if(concurrency==null||concurrency.equalsIgnoreCase("")){concurrency="";}
//					 TreeNode node2 = new DefaultTreeNode(concurrency, node1);
//				 }
				 
				 
			 }
			 
		 }
		  }
		  
		  
	  }else
	  {
		  System.out.println("***********************No of Stages is Null***********");

	  }
	 
	  
	  
	 
	  
	 
	 
	 
	 
	 
	 
	 
	 	  
	  
	  

	  
//	  
//      TreeNode node0 = new DefaultTreeNode("Creator", root);  
//      TreeNode node1 = new DefaultTreeNode("Reviewer", root);  
//      TreeNode node2 = new DefaultTreeNode("Approver", root);  
//      
  
//      TreeNode node4 = new DefaultTreeNode("Approved", root3);  
//      TreeNode node5 = new DefaultTreeNode("Completed", root3);
//      TreeNode node6 = new DefaultTreeNode("YTS", root3);
//      TreeNode node15 = new DefaultTreeNode("Rejected", root3);
//        
//      
//      root4 = new DefaultTreeNode("Root4", null);  
//      TreeNode node7 = new DefaultTreeNode("Raja", root4);  
//      TreeNode node8 = new DefaultTreeNode("Ram", root4);  
//      TreeNode node9 = new DefaultTreeNode("Kumar", root4);
//     
//      root5 = new DefaultTreeNode("Root5", null);  
//      TreeNode node10 = new DefaultTreeNode("Message", root5);  
//      TreeNode node11 = new DefaultTreeNode("Employee", root5);  
//      TreeNode node12 = new DefaultTreeNode("Properties", root5);
//      
//      root6 = new DefaultTreeNode("Root6", null); 
//      TreeNode node13 = new DefaultTreeNode("Serial", root6);  
//      TreeNode node14 = new DefaultTreeNode("Parallel", root6);
//
//      root2 = new DefaultTreeNode("Root2", null);  
//      TreeNode item0 = new DefaultTreeNode("Work Flow", root2);  

	}catch(Exception e)
	{
		e.printStackTrace();
	}
	
}


public void addtoNode(){
	
	if(stageNameValue!= null && !stageNameValue.equals("")){
		
		root2 = new DefaultTreeNode("Work Flow", null);  
	    node3 = new DefaultTreeNode("Work Flow", root2);
	TreeNode node4 = new DefaultTreeNode(stageNameValue, node3); 
	TreeNode node5 = new DefaultTreeNode("Status_Codes", node4);  
	node5 = new DefaultTreeNode("Employee_Names", node4); 
	}
}

public TreeNode getRoot() {  
    return root;  
}  

public TreeNode getRoot2() {  
    return root2;  
}  
  
public TreeNode getSelectedNode() {  
    return selectedNode;  
}  
public void setSelectedNode(TreeNode selectedNode) {  
    this.selectedNode = selectedNode;  
}  

public TreeNode getSelectedNode2() {  
    return selectedNode2;  
}  
public void setSelectedNode2(TreeNode selectedNode2) {  
    this.selectedNode2 = selectedNode2;  
}  


public void onDragDrop(TreeDragDropEvent event) {  
	
	
	
    TreeNode dragNode = event.getDragNode();  
    TreeNode dropNode = event.getDropNode();  
    
//    if(dropNode.getData().equals("Work Flow")){
//    	
//    	
//    	root2.getChildren().remove(0);
//    	
//    }
    
    int dropIndex = event.getDropIndex();  
    System.out.println("dragNode.getData()"+ dragNode.getData());
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dragged " + dragNode.getData(), "Dropped on " + dropNode.getData() + " at " + dropIndex);  
    FacesContext.getCurrentInstance().addMessage(null, message);  
    
    for(int i=0;i<root2.getChildCount();i++){
   System.out.println("Child Node"+ root2.getChildren().get(i));
   System.out.println("Child Node1"+ root2.getChildCount());
//   System.out.println("Child Node1"+ root2.);
    }
}  

//
//public void saveWorkFlowToXml(){ //code change Jayaramu 15MAR14
//	
//	try{
//	List<TreeNode> rootChlid = null;
//	List<TreeNode> sub1rootChlid = null;
//	List<TreeNode> sub2rootChlid = null;
//	List<TreeNode> sub3rootChlid = null;
//	TreeNode subchild1 = null;
//	TreeNode subchild2 = null;
//	TreeNode subchild3 = null;
//	TreeNode subchild4 = null;
//	
//	String stageName = "";
//	String empNames[] = null;
//	String statusMsg[] = null;
//	String empEmail[]=null;
//	String empUid[] = null;
//	String propName;
//	String minApprovers = null;
//	FacesContext ctx = FacesContext.getCurrentInstance();
//	ExternalContext extContext = ctx.getExternalContext();
//	Map sessionMap = extContext.getSessionMap();
//	HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");
//	String heirCode = hrybn.getHierarchy_ID();
//	propName = propertiesType;
//	PropUtil prop=new PropUtil();
//	String usersXmlFileName=prop.getProperty("LOGIN_XML_FILE");
//	String xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
//	Document doc = Globals.openXMLFile(xmlDir, usersXmlFileName);
//NodeList obiee_usersList = doc.getElementsByTagName("Obiee_Users");
//Node obieeUserparentNode = obiee_usersList.item(0);
//NodeList usersNode = obieeUserparentNode.getChildNodes();
//
//
//
//
//	if(propName.equals("Serial") || minApproveValue ==  null || minApproveValue.equals("")){
//		minApprovers = "0";	
//	}else{
//		minApprovers = minApproveValue;
//	}
//	
//	int keyempUid=0;
//	int keyempEmail=0;
//	int keyempNames=0;
//	int keystatusMsg=0;
//	int setSize = 0;
//	int statusSize = 0;
//	for(int n=0;n<root2.getChildCount();n++){                                       
////		Work flow-->>>creator --->>> Status----->>>YTS,WIP,Cr: creato---->>EMP_NAME 
//	
//		
//		
//		if(root2 != null){
//		 rootChlid = root2.getChildren();
//		
//		for(int p=0;p<rootChlid.size();p++){ 
//			
//			subchild1 = rootChlid.get(p);
//			
//			
////			System.out.println("subchild1===>>"+subchild1);
//			
//			
//			sub1rootChlid = subchild1.getChildren(); 
////			System.out.println("sub1rootChlid===>>>"+sub1rootChlid);
//			
//			if(sub1rootChlid != null){
//				
//				for(int k=0;k<sub1rootChlid.size();k++){
//					
//					subchild2 = sub1rootChlid.get(k); 
////					System.out.println("subchild2====>>>"+subchild2);
//					stageName = (String.valueOf(subchild2));
//					sub2rootChlid = subchild2.getChildren();
////					System.out.println("sub2rootChlid====>>>"+sub2rootChlid);
//					if(sub2rootChlid != null){
//						
//						for(int m=0;m<sub2rootChlid.size();m++){
//							
//							subchild3 = sub2rootChlid.get(m); 
////							System.out.println("subchild3=====>>>>"+subchild3);
//							
//							sub3rootChlid = subchild3.getChildren();
//							
//							if(sub3rootChlid != null){
//							if(setSize == 0 && String.valueOf(subchild3).equals("Employee_Names")){
//								setSize++;
//								empUid = new String[sub3rootChlid.size()];
//								empNames = new String[sub3rootChlid.size()];
//								
//								empEmail = new String[sub3rootChlid.size()];
//							}else if(statusSize == 0 && String.valueOf(subchild3).equals("Status_Codes")){
//								statusSize++;
//								statusMsg = new String[sub3rootChlid.size()];
//								
//							}
//								for(int n1=0;n1<sub3rootChlid.size();n1++){
//									
//									subchild4 = sub3rootChlid.get(n1);
//									
//									if (String.valueOf(subchild3).equals("Employee_Names")){
////										System.out.println("subchild4 eeeee =====>>>>"+subchild4);
//										String empNameWithID = String.valueOf(subchild4);
//										
//										empUid[keyempUid] = empNameWithID.substring(empNameWithID.indexOf("(")+1,empNameWithID.indexOf(")"));
//										keyempUid++;
//										for(int i=0;i<usersNode.getLength();i++){
//											
//											if(usersNode.item(i).getNodeType() == Node.ELEMENT_NODE)
//											{
////												System.out.println("usersNode.item(i) =====>>>>"+usersNode.item(i));
//											String unidID = usersNode.item(i).getAttributes().getNamedItem("Access_Unique_ID").getNodeValue();
//										if(empUid[n1].equals(unidID)){
//											empEmail[keyempEmail] = usersNode.item(i).getAttributes().getNamedItem("Email_ID").getNodeValue();
//											keyempEmail++;
//										}}
//										}
//										
//										empNames[keyempNames] = empNameWithID.substring(0,empNameWithID.indexOf("("));
//										keyempNames++;
//									}else if(String.valueOf(subchild3).equals("Status_Codes")){
////										System.out.println("subchild4 ssssss=====>>>>"+subchild4);
////										System.out.println("String.valueOf(subchild4)=====>>>>"+String.valueOf(subchild4));
//										statusMsg[keystatusMsg] = String.valueOf(subchild4);
////										System.out.println("subchild41111=====>>>>"+statusMsg[keystatusMsg]);
//										keystatusMsg++;
//
//									}
////									System.out.println("subchild4=====>>>>"+subchild4);
//									
//								}
//								
//							}
//							
//							
//						}
//					}
//					
//				}
//				
//			}
//		
//			
//		}
//		
//		}
//	}
//	
//	
//	
//	if(selectionType.equals("Custom")){
//		
//		rootTreeRendered = "true";
//		root = new DefaultTreeNode("Root", null);
//		TreeNode rootnode = new DefaultTreeNode(stageName, root);
//		TreeNode sub1node = new DefaultTreeNode("Status_Codes", rootnode);
//		
//		for(int j=0;j<statusMsg.length;j++){
//			
//			TreeNode sub2node = new DefaultTreeNode(statusMsg[j], sub1node);
//			
//			System.out.println("statusMsg=====>>>>>"+statusMsg[j]);
//			}
//		
//		TreeNode sub10node = new DefaultTreeNode("Employee_Names", rootnode);
//		for(int i=0;i<empNames.length;i++){
//			
//			TreeNode sub3node = new DefaultTreeNode(empNames[i], sub10node);
//			
//			System.out.println("stageName=====>>>>>"+empNames[i]);
//		}
//	}
//	
//	System.out.println("heirCode=====>>>>>"+heirCode);
//	System.out.println("stageName=====>>>>>"+stageName);
//	System.out.println("statusMsg 000=====>>>>>"+statusMsg[0]);
//	
//	
//
//	
//for(int i=0;i<empEmail.length;i++){
//	
//	System.out.println("empEmail=====>>>>>"+empEmail[i]);
//	}
//for(int i=0;i<empUid.length;i++){
//	
//	System.out.println("empUid=====>>>>>"+empUid[i]);
//	}
//	
//	
//	
//	
//	System.out.println("propName=====>>>>>"+propName);
//	System.out.println("minApprovers=====>>>>>"+minApprovers);
//	addStageintoXML(heirCode, stageName, empNames, statusMsg, empEmail, empUid, propName, minApprovers);
//	DashboardBean db = new DashboardBean();
//	db.DashboardStage();
//}catch(Exception e){
//e.printStackTrace();
//}
//}
}
