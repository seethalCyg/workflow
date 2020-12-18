package managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.json.JSONException;
import org.json.XML;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.quartz.Job;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.api.services.drive.Drive;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.novell.ldap.LDAPConnection;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;

import beans.HeirarchyDataBean;
import beans.HierarchyBean;
import beans.HierarchydataBean;
import beans.LoginBean;
import beans.RandomString;
import beans.workFlowBean;
import utils.Globals;
import utils.Inventory;
import utils.PropUtil;

public class WorkflowManager {
//	private String hierarchyXmlFileName="HierachyLevel.xml";
//	private String hierarchyConfigFile="Hierarchy_Config.xml";
	public static String checkLogin(String username, String password, Hashtable HT) {
		String status = "failure";
		try {
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String xmlFileName=prop.getProperty("LOGIN_XML_FILE");

			NodeList ndlist=Globals.getNodeList(HIERARCHY_XML_DIR, xmlFileName, "Obiee_Users");
			Hashtable UserIDHT=new Hashtable<>();
			Hashtable UserDetailsHT=new Hashtable<>();
			Hashtable UserIDNDetailsHT=new Hashtable<>();
			String attrName="",arrtValue="";
			NodeList ndlist2=ndlist.item(0).getChildNodes();
			int k=0;
			for(int i=0;i<ndlist2.getLength();i++){
				Node nd=ndlist2.item(i);

				UserDetailsHT=new Hashtable<>();
				if(nd.getNodeType()==Node.ELEMENT_NODE){

					UserIDHT.put(k, nd.getTextContent());
					k++;
					for(int j=0;j<nd.getAttributes().getLength();j++){
						attrName=nd.getAttributes().item(j).getNodeName();
						arrtValue=nd.getAttributes().item(j).getNodeValue();
						UserDetailsHT.put(attrName, arrtValue);
					}

					UserIDNDetailsHT.put(nd.getTextContent(), UserDetailsHT);
				}

			}

			
			
			String serverDeployed="";


			String HIERARCHY_CONFIG_FILE=prop.getProperty("HIERARCHY_CONFIG_FILE");
			NodeList nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Ldap_Host");
			String hostName=String.valueOf(nodeList.item(0).getTextContent());
			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Domain_Name");
			String domainName=String.valueOf(nodeList.item(0).getTextContent());

			System.out.println("-=-=-=-=-=-UserIDHT=-=-=-=-="+UserIDHT);
			System.out.println("-=-=-=-=-=-username=-=-=-=-="+username);
			System.out.println("-=-=-=-=-=-UserIDNDetailsHT=-=-=-=-="+UserIDNDetailsHT);
			for (int x = 0; x < UserIDHT.size(); x++) {
				System.out.println(String.valueOf(UserIDHT.get(x))+"-=-=-=-=x-=-=-=-=-="+username+"-=-=-=-=-=-=-=-="+(String.valueOf(UserIDHT.get(x)).equalsIgnoreCase(username)));
				if (String.valueOf(UserIDHT.get(x)).equalsIgnoreCase(username)){
					String loginDN=domainName.concat(username);
					HT.putAll((Hashtable) UserIDNDetailsHT.get(username));
					System.out.println(loginDN+"-=-=-=-=dd-=-=-="+checkPassword(HT, password, hostName, loginDN, "local"));
					if(checkPassword(HT, password, hostName, loginDN, "local").equalsIgnoreCase("Success")) {
						String disableFlag = String.valueOf(HT.get("Disable"));
						status = !disableFlag.equalsIgnoreCase("true") ? "success" : "Error: Login failed – Your Login has disabled. Please contact your Administrator.";
						break;
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return status;
	}
	public static String checkPassword(Hashtable usersHT, String password, String ldapHost, String loginDN, String type) throws Exception {
		String status = "success";
		if(type.equalsIgnoreCase("local")) {
			String pass = Inventory.retrieve(String.valueOf(usersHT.get("User_Password")));
			
			System.out.println(pass+": 90909090909090 :"+": &*&*&*&*&*&*&*&* :"+pass.equalsIgnoreCase(password));
			status = pass.equalsIgnoreCase(password) ? "success" : "failure";
		}else {
			status = ldapLogon(ldapHost, loginDN, password);
		}
		return status;
	}


	public static String ldapLogon(String ldapHost, String loginDN, String password) //Logon Testing with LDAP Server : Code Change Bharath 20-MAR-2014
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		String value="";

		int ldapPort;
		int searchScope;
		int ldapVersion;
		boolean attributeOnly;


		String searchBase ="";
		String searchFilter = "";

		PropUtil prop=new PropUtil();

		String HIERARCHY_CONFIG_FILE="",HIERARCHY_XML_DIR="";

		LDAPConnection lc = new LDAPConnection();

		try {


			// code change Menaka 21MAR2014
			HIERARCHY_CONFIG_FILE=prop.getProperty("HIERARCHY_CONFIG_FILE");
			HIERARCHY_XML_DIR=prop.getProperty("HIERARCHY_XML_DIR");
			NodeList nodeList=null;

			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Ldap_Port");
			ldapPort=Integer.parseInt(nodeList.item(0).getTextContent());			

			//						nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Search_Scope");
			//						searchScope=Integer.parseInt(nodeList.item(0).getTextContent());		

			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Ldap_Version");
			ldapVersion=Integer.parseInt(nodeList.item(0).getTextContent());

			//						nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Attribute_Only");
			//						attributeOnly=Boolean.parseBoolean(String.valueOf(nodeList.item(0).getTextContent()));
			//					
			//						nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Attributes");
			//						String attrs[]={String.valueOf(nodeList.item(0).getTextContent())};
			//						
			//						nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Search_Base");
			//						searchBase=String.valueOf(nodeList.item(0).getTextContent());
			//						
			//						nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Search_Filter");
			//						searchFilter=String.valueOf(nodeList.item(0).getTextContent());

			// connect to the server
			lc.connect(ldapHost, ldapPort); // 3268
			System.out.println("LDAP Connection Success");

			//loginDN = "cn=mahadevan.muthusamy, dc=americantower, dc=com";  //"cn=admin,dc=ggd543,dc=com";

			// bind to the server
			lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));
			System.out.println("Bind Success");

			value="Success";  // code change Menaka 20MAR2014
			//			System.out.println("login Status ===>>>"+value);


		}
		catch (Exception e) {
			e.printStackTrace();
			value="Success";

		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		System.out.println("login Status ===>>>"+value);
		return "Success";
	}
	
	
	
	
	
	public static Boolean checkEsignconfigureORnot(String docId) throws Exception {
		boolean checkEsignFlag = false;
	
		
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		Element docEle = (Element) Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
		if(docEle == null)
			return checkEsignFlag;
		Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
		String currStageNo = wfEle.getAttribute("Current_Stage_No");
		
		if(currStageNo.equalsIgnoreCase("Completed") || currStageNo.equalsIgnoreCase("Cancel") || currStageNo.equalsIgnoreCase("Rules Failed")) 
			return checkEsignFlag;
		
		Element stageEle = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, wfEle, "Stage_No", currStageNo);
		
		String checkES=stageEle.getAttribute("IsESignConfigured")==null ||stageEle.getAttribute("IsESignConfigured").equalsIgnoreCase("") ? "false" :stageEle.getAttribute("IsESignConfigured");
		
		System.out.println("checkES***************:"+checkES);
		
	if(checkES.toLowerCase().trim().equalsIgnoreCase("true"))
	{
		checkEsignFlag = true;
	}else {
		
		checkEsignFlag = false;
	}
	
		
		return checkEsignFlag;
	}
	
	
	
	
	
	
	public static Hashtable listWorkflowForCustomer(String customerKey) throws Exception {
//		try {
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
			Hashtable<String, String> hierarchyAttr = new Hashtable<String, String>();
//			
			Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);
//			doc.getDocumentElement().normalize();
			ArrayList HierarchyAttrAL=new ArrayList();
			NodeList HRYNodesAL = Globals.getNodeList(HIERARCHY_XML_DIR, hierarchyXmlFileName, "Hierarchy_Level");
			String hierarchyID="";
			int hierarchyIDInt = 1;
			String hierarchyName="";
			String hierarchyCreatedDate="";
			String hierarchyModifiedDate="";
			String hierchyCategory="";
			String hierarchyStatus="";  // code change Menaka 15FEB2014
			String hierCodeCombination;
			String factStatus="";
			String errorMsg=""; // code change Menaka 21FEB2014
			String dimStatus = "";
			String dimErrorMsg = "";
			//		String workFlowStage = "";
			//		String workFlowStageStatus = "";



			//start code change Menaka 21MAR2014

			/*String loginXMLfile=prop.getProperty("LOGIN_XML_FILE");
			Document doc1=Globals.openXMLFile(HIERARCHY_XML_DIR, loginXMLfile);


			NodeList ndlist=Globals.getNodeList(HIERARCHY_XML_DIR, loginXMLfile, "User");

			

			String hierIDS = "";

			NodeList nodlist=Globals.getNodeList(HIERARCHY_XML_DIR, loginXMLfile, "User");

			String defaultUserID=nodlist.item(0).getTextContent();

			System.out.println("defaultUserID=====>>"+defaultUserID);

			Node nod=Globals.getNodeByAttrVal(doc1, "User", "Login_ID", username);


			Node attr=null;
			if(nod!=null){  // code change Menaka 24MAR2014
				if(nod.getNodeType()==Node.ELEMENT_NODE){

					for(int m=0;m<nod.getAttributes().getLength();m++){
						attr=nod.getAttributes().item(m);
						if(attr.getNodeName().equals("Allowed_Hierarchies")){
							hierIDS=attr.getNodeValue();
						}
					}

				}
			}


			String hierID4users[]=hierIDS.split(","); 

			ArrayList hierIDList=new ArrayList<>();

			for(int ii=0;ii<hierID4users.length;ii++){
				hierIDList.add(hierID4users[ii]);
			}*/

			//		System.out.println("hierIDList==========>>>"+hierIDList);
			Hashtable wfHT = new Hashtable<>();
			int wfCount = 0;
			for (int hry = 0; hry < HRYNodesAL.getLength(); hry++) {
				Node node = HRYNodesAL.item(hry);

				Hashtable<String, String> HRYAttrHt = Globals.getAttributeNameandValHT(node);


				hierarchyID = HRYAttrHt.get("Hierarchy_ID");

				/*if(!username.equals(defaultUserID)){
					if(hierIDList.contains(hierarchyID)){  // code change Menaka 21MAR2014

					}else{
						continue;
					}
				}*/
				if(String.valueOf(HRYAttrHt.get("CustomerKey")) == null || String.valueOf(HRYAttrHt.get("CustomerKey")).equals("null") || String.valueOf(HRYAttrHt.get("CustomerKey")).isEmpty() || 
						!String.valueOf(HRYAttrHt.get("CustomerKey")).equalsIgnoreCase(customerKey)) {
					continue;
				}



				hierarchyIDInt = Integer.parseInt(hierarchyID);
				hierarchyName = HRYAttrHt.get("Hierarchy_Name");
				hierarchyCreatedDate = HRYAttrHt.get("Created_Date");
				String createdBy = HRYAttrHt.get("Created_By");
				hierarchyModifiedDate = HRYAttrHt.get("Modified_Date");
				hierchyCategory =  HRYAttrHt.get("Hierarchy_Category");
				hierCodeCombination =  String.valueOf(HRYAttrHt.get("Code_Combination"));
				dimStatus = HRYAttrHt.get("Dim_Status");
				dimErrorMsg = HRYAttrHt.get("Dim_Status_Details");
				hierarchyStatus=HRYAttrHt.get("RI_Hierarchy_Type");   // code change Menaka 15FEB2014 // code change Menaka 31MAR2014
				Node workFlowNode = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierarchyID);
				String workFlowStage = "";
				String workFlowStageStatus = "";
				if(workFlowNode != null){
					Hashtable curHT=Globals.getAttributeNameandValHT(workFlowNode);
					String currentStageNo="";

					String stageno=(String)curHT.get("Current_Stage_No");
					if(stageno.equalsIgnoreCase("Completed") || stageno.equalsIgnoreCase("Cancel") || stageno.equalsIgnoreCase("Rules Failed")){
						currentStageNo=(String)curHT.get("Total_No_Stages");
					}else{
						currentStageNo=(String)curHT.get("Current_Stage_No");
					}
					Node currentStageNode=Globals.getNodeByAttrValUnderParent(doc, workFlowNode, "Stage_No", currentStageNo);
					Hashtable curStageHT=Globals.getAttributeNameandValHT(currentStageNode);
					workFlowStage=(String)curStageHT.get("Stage_Name");
					String stagests=(String)curStageHT.get("Stage_Status");
					if(stagests.equalsIgnoreCase("Completed")){
						LoginProcessManager lgnpro=new LoginProcessManager();
						Hashtable stageDetHT=lgnpro.retriveStageDetailsFromXML(workFlowNode, currentStageNo,"");
						Hashtable msgHT=(Hashtable)stageDetHT.get("MessagedetHT");
						String finalmsg=(String)msgHT.get("Final");
						workFlowStageStatus=finalmsg;		
					}else{
						workFlowStageStatus=(String)curStageHT.get("Stage_Status");
					}

					
					wfCount++;
					Hashtable detailsHT = new Hashtable<>();
					detailsHT.put("WorkflowName", hierarchyName);
					detailsHT.put("CreatedDate", hierarchyCreatedDate);
					detailsHT.put("CreatedBy", createdBy);
					detailsHT.put("WorkflowID", hierarchyID);
					Hashtable usersHT = new Hashtable<>();
					detailsHT.put("Users", usersHT);
					ArrayList<String> stageNameAL = new ArrayList<>();
					Element wfEle = (Element) workFlowNode;
					NodeList stageNdList = wfEle.getElementsByTagName("Stage");
					for(int l=0;l<stageNdList.getLength();l++) {
						if(stageNdList.item(l).getNodeType() == Node.ELEMENT_NODE) {
							Element stageEle = (Element) stageNdList.item(l);
							String stageNo = stageEle.getAttribute("Stage_No");
							NodeList userNdList = stageEle.getElementsByTagName("Employee_Names");
							if(userNdList.getLength() > 0) {
								if(userNdList.item(0).getChildNodes().getLength() > 0 && Integer.valueOf(stageNo) > 0) {
									if(stageNameAL.isEmpty())
										stageNameAL.add(stageEle.getAttribute("Stage_Name"));
									else {
										if(Integer.valueOf(stageNo)-1 > stageNameAL.size())
											stageNameAL.add(stageEle.getAttribute("Stage_Name"));
										else
											stageNameAL.add(Integer.valueOf(stageNo)-1, stageEle.getAttribute("Stage_Name"));
									}
									ArrayList<String> userAL = new ArrayList<>();
									usersHT.put(stageEle.getAttribute("Stage_Name"), userAL);
									NodeList empsNdList =  userNdList.item(0).getChildNodes();
									for(int k=0;k<empsNdList.getLength();k++) {
										if(empsNdList.item(k).getNodeType() == Node.ELEMENT_NODE) {
											userAL.add(empsNdList.item(k).getTextContent());
										}
									}
								}
							}
						}
					}
					detailsHT.put("StageName", stageNameAL);
					wfHT.put("R"+String.valueOf(wfCount), detailsHT);
				}
				wfHT.put("WorkflowCount", String.valueOf(wfCount));
				System.out.println("hierarchyID---->>>"+hierarchyID);


				

				
				

			}
			
			
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
			return wfHT;
	}
	public static String checkDocumentAttached2WF(String documentName, String docId, String userEmailID) throws Exception {
		String str = "false";
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		Element docEle = (Element) Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
		if(docEle == null)
			return str;
		Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
		String currStageNo = wfEle.getAttribute("Current_Stage_No");
		if(currStageNo.equalsIgnoreCase("Completed") || currStageNo.equalsIgnoreCase("Cancel") || currStageNo.equalsIgnoreCase("Rules Failed")) 
			return str;
		Element stageEle = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, wfEle, "Stage_No", currStageNo);
		Node empNd = stageEle.getElementsByTagName("Employee_Names").item(0);
		LoginProcessManager  log=new LoginProcessManager();
		Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(wfEle, currStageNo,"");
		Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
		Hashtable mssgeDetailsHT=(Hashtable)currentstageDetailsHT.get("MessagedetHT");
		String finalmsg=(String)mssgeDetailsHT.get("Final");
		for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
			Hashtable ApproversHT=new Hashtable();
			ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
			String member=(String)ApproversHT.get("empName");
			String userStatus=(String)ApproversHT.get("User_Status");
			if(member.equalsIgnoreCase(userEmailID) && !userStatus.equalsIgnoreCase(finalmsg)) {
				str = "true";
				Element userNd = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, empNd, "E-mail", member);
				if(userNd != null) {
					userNd.setAttribute("isWorkStarted", "true");
					Globals.writeXMLFile(docXmlDoc, HIERARCHY_XML_DIR, docXmlFileName);
				}
				break;
			}
		}
		return str;
	}
	
	public static String getDocumentIDAttached2WF(String documentName) throws Exception {
		String str = "false";
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		Element docEle = (Element) Globals.getNodeByAttrVal(docXmlDoc, "Document", "DocumentName", documentName);
		return docEle == null ? "#false" : docEle.getAttribute("Document_ID");
	}
	
	public static Hashtable getAttachDocumentDetailsFromXML(String documentName, String documentId) throws Exception {
		Hashtable detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		Element docEle = (Element) Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
		if(docEle == null)
			return detailsHT;
//		{"WorkflowName":"H1","WorkflowID":"314","CustomerKey":"xbstjb1ciumjt3hu","CreatedBy":"Raghul","CreatedDate":"06-05-2018","DocumentName":"Finance.docx","ChooseTeamName":"",
//			"ChooseMember":"","Sendnotes":"Attached Finance Document at 06-05-2018","Timeline":"True","Showhours":"False","ExecludeWeekEnd":"False","Eachteam":"True","ExecludeHoliday":"False",
//			"SendAlertBefore":"True","SendAlertBeforeDay":"1","SendAlertAfter":"False","SendAlertAfterCount":"","SendAlertAfterDay":"","SendAlertDayHourchooser":"Day","Tablevalue":
//			{"TableLength":"3","R0":{"tblCol_Team":"Create","tblCol_StartDay":"0","tblCol_EndDay":"2","tblCol_EffStartDate":"03-05-2018","tblCol_EffEndDate":"05-05-2018"},"R1":
//			{"tblCol_Team":"Review","tblCol_StartDay":"1","tblCol_EndDay":"1","tblCol_EffStartDate":"06-05-2018","tblCol_EffEndDate":"07-05-2018"},"R2":
//			{"tblCol_Team":"Approve","tblCol_StartDay":"1","tblCol_EndDay":"0","tblCol_EffStartDate":"08-05-2018","tblCol_EffEndDate":"08-05-2018"}}}
		detailsHT.put("isEditMode", "true");
		detailsHT.put("WorkflowName", docEle.getAttribute("WorkflowName"));
		detailsHT.put("WorkflowID", docEle.getAttribute("WorkflowID"));
		detailsHT.put("CustomerKey", docEle.getAttribute("CustomerKey"));
		detailsHT.put("CreatedBy", docEle.getAttribute("Created_By"));
		detailsHT.put("CreatedDate", docEle.getAttribute("Created_Date"));
		detailsHT.put("DocumentName", docEle.getAttribute("DocumentName"));
		detailsHT.put("DocumentID", docEle.getAttribute("Document_ID"));
		detailsHT.put("ChooseTeamName", docEle.getAttribute("ChooseTeamName"));
		detailsHT.put("ChooseMember", docEle.getAttribute("ChooseMember"));
		detailsHT.put("Sendnotes", docEle.getAttribute("AttachmentNotes"));
		detailsHT.put("Timeline", docEle.getAttribute("AssignTimeline"));
		detailsHT.put("Showhours", docEle.getAttribute("ShowHours"));
		detailsHT.put("ExecludeWeekEnd", docEle.getAttribute("ExecludeWeekEnd"));
		detailsHT.put("Eachteam", docEle.getAttribute("TimelineEachteam"));
		detailsHT.put("ExecludeHoliday", docEle.getAttribute("ExecludeHoliday"));
		detailsHT.put("SendAlertBefore", docEle.getAttribute("BeforeDeadlineAlertFlag"));
		detailsHT.put("SendAlertBeforeDay", docEle.getAttribute("BeforeDeadlineAlert"));
		detailsHT.put("SendAlertAfter", docEle.getAttribute("AfterDeadlineAlertFlag"));
		detailsHT.put("SendAlertAfterCount", docEle.getAttribute("AfterDeadlineAlertCount"));
		detailsHT.put("SendAlertAfterDay", docEle.getAttribute("AfterDeadlineAlert"));
		detailsHT.put("SendAlertDayHourchooser", docEle.getAttribute("AfterDeadlineAlertRange"));
		
		Hashtable tableValueHT = new Hashtable<>();
		detailsHT.put("Tablevalue", tableValueHT);
		if(docEle.getAttribute("TimelineEachteam").equalsIgnoreCase("false")) {
			tableValueHT.put("TableLength", "1");
			Hashtable rowHT = new Hashtable<>();
			tableValueHT.put("R0", rowHT);
			rowHT.put("tblCol_Team", docEle.getAttribute("WorkflowName"));
			rowHT.put("tblCol_StartDay", docEle.getAttribute("StartDayCount"));
			rowHT.put("tblCol_EndDay", docEle.getAttribute("EndDayCount"));
			rowHT.put("tblCol_EffStartDate", docEle.getAttribute("EffectiveStarDate"));
			rowHT.put("tblCol_EffEndDate", docEle.getAttribute("EffectiveEndDate"));
		}else {
			Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
			int stageCount = Integer.valueOf(wfEle.getAttribute("Total_No_Stages"));
			tableValueHT.put("TableLength", String.valueOf(stageCount));
			for(int i=0;i<stageCount;i++) {
				Element stgEle = (Element)Globals.getNodeByAttrValUnderParent(docXmlDoc, wfEle, "Stage_No", String.valueOf(i+1));
				Hashtable rowHT = new Hashtable<>();
				tableValueHT.put("R"+i, rowHT);
				rowHT.put("tblCol_Team", stgEle.getAttribute("Stage_Name"));
				rowHT.put("tblCol_StartDay", stgEle.getAttribute("StartDayCount"));
				rowHT.put("tblCol_EndDay", stgEle.getAttribute("EndDayCount"));
				rowHT.put("tblCol_EffStartDate", stgEle.getAttribute("EffectiveStarDate"));
				rowHT.put("tblCol_EffEndDate", stgEle.getAttribute("EffectiveEndDate"));
			}
		}
		return detailsHT;
	}
	
	public static Hashtable attachDocument2TheWF(String jsonStr, String actionType) throws Exception {
		//boolean attachFlag = false;
		
		Hashtable detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String hierarchyXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		String configFileName = prop.getProperty("HIERARCHY_CONFIG_FILE"); 
		Document wfDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);
		Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		
		String uniqueid=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, configFileName,"Document_Unique_ID", "ID"));
		System.out.println("Json Str ----- :"+jsonStr);
		JSONObject jObj = (JSONObject) new JSONParser().parse(jsonStr);
		String primary_Attach_File = (String) jObj.get("Primary_FileName");
		String template_name = (String) jObj.get("Template_Name");
		String template_descriptor = (String) jObj.get("Template_Descriptor");
		String template_FolderName = (String) jObj.get("Template_FolderName");
		String primary_FilePath = (String) jObj.get("Primary_FilePath");
		String primary_FileConn = (String) jObj.get("Primary_FileConn");
		String workFlowName = (String) jObj.get("WorkflowName");
		String workFlowId = (String) jObj.get("WorkflowID");
		String customerKey = (String) jObj.get("CustomerKey");
		String attachedBy = (String) jObj.get("CreatedBy");
		String documentName = (String) jObj.get("DocumentName");
		String documentId = (String) jObj.get("DocumentID");
		String attachedDate = (String) jObj.get("CreatedDate");
		String chooseTeamName = (String) jObj.get("ChooseTeamName");
		String chooseMember = (String) jObj.get("ChooseMember");
		String notes = (String) jObj.get("Sendnotes");
		String timeline = (String) jObj.get("Timeline");
		String showhours = (String) jObj.get("Showhours");
		String execludeWeekEnd = (String) jObj.get("ExecludeWeekEnd");
		String excludeHoliday = (String) jObj.get("ExecludeHoliday");
		String eachteam = (String) jObj.get("Eachteam");
		String sendAlertBefore = (String) jObj.get("SendAlertBefore");
		String sendAlertBeforeDay = (String) jObj.get("SendAlertBeforeDay");
		String sendAlertAfter = (String) jObj.get("SendAlertAfter");
		String sendAlertAfterCount = (String) jObj.get("SendAlertAfterCount");
		String sendAlertAfterDay = (String) jObj.get("SendAlertAfterDay");
		String sendAlertDayHourchooser = (String) jObj.get("SendAlertDayHourchooser");
		String ruleAttrValue = (String) jObj.get("RuleAttributeValue");
		String esignConfig = (String) jObj.get("Esign_configure");
		String revisionType = jObj.get("RevisionType") == null ? "Offline" : (String) jObj.get("RevisionType");
		
		String oneDriveId = jObj.get("OneDriveId") ==null ? "" : (String) jObj.get("OneDriveId");
		String oneDriveName = jObj.get("OneDriveName") == null ? "" : (String) jObj.get("OneDriveName");
		String oneDriveUrl = jObj.get("OneDriveWebUrl") == null ? "" : (String) jObj.get("OneDriveWebUrl");
		String oneDriveDownloadUrl = jObj.get("OneDriveDownloadUrl") == null ? "" : (String) jObj.get("OneDriveDownloadUrl");
		JSONObject teamDetails = (JSONObject) jObj.get("Tablevalue");
		String stageCount = (String) teamDetails.get("TableLength");
		int stgCount = stageCount == null || stageCount.trim().isEmpty() || !Globals.checkStringIsNumber(stageCount) ? 0 : Integer.parseInt(stageCount);
		
	
		System.out.println("Stage Count :"+stgCount);
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
		Element docEle = null;
		if(documentId == null || documentId.trim().isEmpty() || docNd == null) {
			docEle = docXmlDoc.createElement("Document");
			docXmlDoc.getFirstChild().appendChild(docEle);
			docEle.setAttribute("Document_ID", uniqueid);
			docEle.setAttribute("Created_By", attachedBy);
			docEle.setAttribute("Created_Date", attachedDate);
		}else {
			docEle = (Element) docNd;
			uniqueid = docEle.getAttribute("Document_ID");
		}
		
		Node atthmentsNd = Globals.getChildNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId,"Attachments");
		Element attFile_Ele=null;
		if(atthmentsNd == null) {
			attFile_Ele=docXmlDoc.createElement("Attachments");
			docEle.appendChild(attFile_Ele);
		}else {
			attFile_Ele = (Element) atthmentsNd;
		}
		 
		
		
		
		docEle.setAttribute("WorkflowID", workFlowId);
		docEle.setAttribute("Template_Name", template_name);
		docEle.setAttribute("Template_Descriptor", template_descriptor);
		docEle.setAttribute("Template_FolderName", template_FolderName);
		docEle.setAttribute("WorkflowName", workFlowName);
		docEle.setAttribute("Primary_FileName", primary_Attach_File);
		docEle.setAttribute("Primary_FilePath", primary_FilePath);
		docEle.setAttribute("Primary_FileConn", primary_FileConn);
		docEle.setAttribute("CustomerKey", customerKey);
		docEle.setAttribute("DocumentName", documentName);
		docEle.setAttribute("ChooseTeamName", chooseTeamName);
		docEle.setAttribute("ChooseMember", chooseMember);
		docEle.setAttribute("AttachmentNotes", notes);
		docEle.setAttribute("AssignTimeline", timeline);
		docEle.setAttribute("ShowHours", showhours);
		docEle.setAttribute("ExecludeWeekEnd", execludeWeekEnd);
		docEle.setAttribute("ExecludeHoliday", excludeHoliday);
		docEle.setAttribute("TimelineEachteam", eachteam);
		docEle.setAttribute("BeforeDeadlineAlertFlag", sendAlertBefore);
		docEle.setAttribute("BeforeDeadlineAlert", sendAlertBeforeDay);
		docEle.setAttribute("AfterDeadlineAlertFlag", sendAlertAfter);
		docEle.setAttribute("AfterDeadlineAlertCount", sendAlertAfterCount);
		docEle.setAttribute("AfterDeadlineAlert", sendAlertAfterDay);
		docEle.setAttribute("AfterDeadlineAlertRange", sendAlertDayHourchooser);
		docEle.setAttribute("Esign_configure", esignConfig);
		docEle.setAttribute("OneDriveId", oneDriveId);
		docEle.setAttribute("OneDriveName", oneDriveName);
		docEle.setAttribute("OneDriveWebUrl", oneDriveUrl);
		docEle.setAttribute("OneDriveDownloadUrl", oneDriveDownloadUrl);
		docEle.setAttribute("RevisionType", revisionType);
		System.out.println("======esignConfig========>> "+esignConfig);
		
		System.out.println("==============>> "+workFlowId);
		
		Node wfNd = Globals.getNodeByAttrVal(wfDoc, "Workflow", "Hierarchy_ID", workFlowId);
		if(wfNd == null) {
			detailsHT.put("Status", "Failure");
			detailsHT.put("Message", "Document is not attached to the workflow.");
			return detailsHT;
		}
		String toStage = "";
		String externalUserFlag = "";
		String autoLoginFlag = "";
		ArrayList<String> emailList = new ArrayList<>();
		LoginProcessManager  log=new LoginProcessManager();
		Node wfDocXmlNd = null;
		Element wfEle = (Element) wfNd;
		if(docEle.getElementsByTagName("Workflow").getLength() <= 0) {
			wfDocXmlNd = docXmlDoc.importNode(wfNd, true);
			docEle.appendChild(wfDocXmlNd);
		}else{
			wfDocXmlNd = docEle.getElementsByTagName("Workflow").item(0);
		}
		NodeList nList=attFile_Ele.getChildNodes();
		for (int i = nList.getLength()-1; i >0; i--) {
			attFile_Ele.removeChild( nList.item( i ) );
		}
		
		
		
		JSONObject esignStageno = (JSONObject) jObj.get("EsignConfiguration");
		System.out.println("esignStageno:::::: "+esignStageno);
		JSONObject attachFileDetails = (JSONObject) jObj.get("AttachMent_Files");
		if(!attachFileDetails.isEmpty()) {
					
		String attachFilesCount = (String) attachFileDetails.get("TableLength");
		int noOf_Sec_AttachFiles = attachFilesCount == null || attachFilesCount.trim().isEmpty() || !Globals.checkStringIsNumber(attachFilesCount) ? 0 : Integer.parseInt(attachFilesCount);
		for(int j=0;j<noOf_Sec_AttachFiles;j++) {
			String key = "R"+j;
			int id=j+1;
			String keyVal="";
			if(id > 9) {
				keyVal="0"+id;
			}else {
				keyVal="00"+id;
			}
			JSONObject team = (JSONObject)attachFileDetails.get(key);
			String fileName = String.valueOf(team.get("FileName"));
			String fileType = String.valueOf(team.get("FileType"));
			String fileSize = String.valueOf(team.get("FileSize"));
			String fileaddDate = String.valueOf(team.get("File_AddDate"));
			String oneDriveId1 = team.get("OneDriveId") ==null ? "" : (String) team.get("OneDriveId");
			String oneDriveName1 = team.get("OneDriveName") == null ? "" : (String) team.get("OneDriveName");
			String oneDriveUrl1 = team.get("OneDriveWebUrl") == null ? "" : (String) team.get("OneDriveWebUrl");
			String oneDriveDownloadUrl1 = team.get("OneDriveDownloadUrl") == null ? "" : (String) team.get("OneDriveDownloadUrl");
			String source_types = String.valueOf(team.get("Source_types"));
			String connection_Type = String.valueOf(team.get("Connection_Type"));
			String srcLink = String.valueOf(team.get("SrcLink"));
			String filePath = String.valueOf(team.get("FilePath"));
			System.out.println(fileName+"<===fileName===>"+filePath);
			String attachStgName = team.get("AttachedStage") == null ? "Attach" : team.get("AttachedStage").toString();
			String userCustomerName = team.get("AttachedUser") == null ? "Attach" : team.get("AttachedUser").toString();
			String uploadedDate = team.get("UploadedDate") == null ? "" : team.get("UploadedDate").toString();
			Element each_attFile_Ele=docXmlDoc.createElement("Attachment");
			each_attFile_Ele.setAttribute("FileName", fileName);
			each_attFile_Ele.setAttribute("FileType", fileType);
			each_attFile_Ele.setAttribute("FileSize", fileSize);
			each_attFile_Ele.setAttribute("File_AddDate", fileaddDate);
			each_attFile_Ele.setAttribute("AttachMentFile_ID", keyVal);

			each_attFile_Ele.setAttribute("Source_types", source_types);
			each_attFile_Ele.setAttribute("Connection_Type", connection_Type);
			each_attFile_Ele.setAttribute("SrcLink", srcLink);
			each_attFile_Ele.setAttribute("FilePath", filePath);
			each_attFile_Ele.setAttribute("AttachedStage", attachStgName);
			each_attFile_Ele.setAttribute("AttachedUser", userCustomerName);
			each_attFile_Ele.setAttribute("UploadedDate", uploadedDate);
			each_attFile_Ele.setAttribute("OneDriveId", oneDriveId1);
			each_attFile_Ele.setAttribute("OneDriveName", oneDriveName1);
			each_attFile_Ele.setAttribute("OneDriveWebUrl", oneDriveUrl1);
			each_attFile_Ele.setAttribute("OneDriveDownloadUrl", oneDriveDownloadUrl1);
			attFile_Ele.appendChild(each_attFile_Ele);

		}
		}
		
		NodeList stageNdList = wfDocXmlNd.getChildNodes();
		for(int i=0;i<stageNdList.getLength();i++) {
			if(stageNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element stageEle = (Element) stageNdList.item(i);
				String stageName = stageEle.getAttribute("Stage_Name");
				for(int j=0;j<stgCount;j++) {
					String key = "R"+j;
					JSONObject team = (JSONObject)teamDetails.get(key);
					System.out.println("Stage Name From XML :"+stageName);
					System.out.println("Stage Name From Json :"+String.valueOf(team.get("tblCol_Team")));
					if(String.valueOf(team.get("tblCol_Team")).equalsIgnoreCase(stageName)) {
						String startDayCount = String.valueOf(team.get("tblCol_StartDay"));
						String endDayCount = String.valueOf(team.get("tblCol_EndDay"));
						String effStarDate = String.valueOf(team.get("tblCol_EffStartDate"));
						String effEndDate = String.valueOf(team.get("tblCol_EffEndDate"));
						if(eachteam.equalsIgnoreCase("true")) {
							stageEle.setAttribute("StartDayCount", startDayCount);
							stageEle.setAttribute("EndDayCount", endDayCount);
							stageEle.setAttribute("EffectiveStarDate", effStarDate);
							stageEle.setAttribute("EffectiveEndDate", effEndDate);
						}else {
							docEle.setAttribute("StartDayCount", startDayCount);
							docEle.setAttribute("EndDayCount", endDayCount);
							docEle.setAttribute("EffectiveStarDate", effStarDate);
							docEle.setAttribute("EffectiveEndDate", effEndDate);
						}
						break;
					}
				}
				String stageNo = stageEle.getAttribute("Stage_No");
				System.out.println("EEEEEEEEEEEEE*****stageNo*******EEEEEEEEEEEEE :"+stageNo);
				if(esignStageno!=null) {
					if(esignStageno.get(stageNo) != null)
					{
						stageEle.setAttribute("IsESignConfigured", esignStageno.get(stageNo).toString());
					}else {
						stageEle.setAttribute("IsESignConfigured", "false");
					}
					if(esignStageno.get("All") != null) {
						stageEle.setAttribute("IsESignConfigured", esignStageno.get("All").toString());
					}
			      }
				if(stageNo.equalsIgnoreCase("1")) {
					System.out.println("EEEEEEEEEEEEE*****stageNo**111111111111111*****EEEEEEEEEEEEE :"+stageNo);
					Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(wfNd, stageNo,"");
					Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
					Hashtable mssgeDetailsHT=(Hashtable)currentstageDetailsHT.get("MessagedetHT");
					Hashtable curentPropertiesHT=(Hashtable)currentstageDetailsHT.get("PropertiesHT");
					String Properties=(String)curentPropertiesHT.get("Properties");
					toStage = (String) currentstageDetailsHT.get("Stage_Name");
					externalUserFlag = currentstageDetailsHT.get("ExternalUser") == null ? "" : currentstageDetailsHT.get("ExternalUser").toString();
					autoLoginFlag = currentstageDetailsHT.get("Auto_Login") == null ? "" : currentstageDetailsHT.get("Auto_Login").toString();
					for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
						Hashtable ApproversHT=new Hashtable();
						ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
						String member=(String)ApproversHT.get("empName");
						System.out.println(Properties+"::EEEEEEEEEEEEE*****member*******EEEEEEEEEEEEE :"+member);
						if(Properties.equalsIgnoreCase("Parallel")) {
							String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
							Hashtable<String, String> loginDetailsHT = log.getLoginDetails(member, customerKey);
						
							String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(loginDetailsHT.get("Disable")).isEmpty() ? "true" : String.valueOf(loginDetailsHT.get("Disable"));
							if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
								continue;
							emailList.add(member);
						}else {
							String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
							Hashtable<String, String> loginDetailsHT = log.getLoginDetails(member, customerKey);
							String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(loginDetailsHT.get("Disable")).isEmpty() ? "true" : String.valueOf(loginDetailsHT.get("Disable"));
							System.out.println(disableFlag+"::EEEEEEEEEEEEE*****disableFlag*******EEEEEEEEEEEEE :"+activeFlag);
							System.out.println(disableFlag+"::EEEEEEEEEEEEE*****disableFlag11111*******EEEEEEEEEEEEE :"+loginDetailsHT.get("Disable"));
							if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
								continue;
							if(emailList.isEmpty())
							emailList.add(member);
						}
						if(member.equalsIgnoreCase(attachedBy)) {
							ArrayList<String> userCodesAl = new ArrayList<>(mssgeDetailsHT.values());
							detailsHT.put("UserCodes", userCodesAl);
//							break;
						}
					}
					
				}
			}
		}
		String docIDs = wfEle.getAttribute("DependentObjectID");
		docIDs = docIDs.trim().isEmpty() ? uniqueid : Arrays.asList(docIDs.split(",")).contains(uniqueid) ? docIDs : docIDs+","+uniqueid;
		wfEle.setAttribute("DependentObjectID", docIDs);
		
		Element wfDocXmlEle = (Element) wfDocXmlNd;
		wfDocXmlEle.setAttribute("RuleAttributeValue", ruleAttrValue);
		checkUsersAreActive((Element) wfDocXmlNd); 
		String documentPath = createDocumentVersionForWF(docEle.getAttribute("DocumentName"), uniqueid, wfEle.getAttribute("Storage"), wfEle,primary_FileConn);
		System.out.println("EEEEEEEEEEEEE*****documentPath*******EEEEEEEEEEEEE :"+documentPath);
		addHistory(docXmlDoc, uniqueid, workFlowId, "Attach Document", notes, "1", attachedBy, documentPath,wfNd);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
	//	addLog(docXmlDoc,uniqueid,"Started",attachedBy,(String)dateFormat.format(cal.getTime()),"NA");
		Globals.writeXMLFile(docXmlDoc, HIERARCHY_XML_DIR, docXmlFileName);
		Globals.writeXMLFile(wfDoc, HIERARCHY_XML_DIR, hierarchyXmlFileName);
		docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "DocumentName", documentName);
		docEle = (Element) docNd;
//		wfNd = Globals.getNodeByAttrVal(docXmlDoc, "Workflow", "Hierarchy_ID", workFlowId);
		if(actionType.equalsIgnoreCase("Save")) {
			System.out.println("EEEEEEEEEEEEE*****actionType*******EEEEEEEEEEEEE :"+actionType);
			String actionName = "Attach";
			if(externalUserFlag.equalsIgnoreCase("true"))
				actionName = "ExternalUser";
			Hashtable mailDtailsHT=log.getMailDetailsFromConfig(docXmlDoc,wfDocXmlNd, "1", attachedBy, actionName, "Next", false , "");
			String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			String bobyofMail=mailMessage;
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			String[] mailList = emailList.toArray(new String[emailList.size()]);	//check Vishnu - for inactive user
			String subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
					"Workflow Name / Document Name : "+workFlowName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
			bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$Notes$", notes);
			//bobyofMail=bobyofMail.replace("$userName$", mailList[0]);
			subject = subject.replace("$ToStage$", toStage);
			log.postMail(mailList, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
		}		
		String nxtStgMsg = "";
		Hashtable stageDetHT=log.retriveStageDetailsFromXML(wfDocXmlNd, "1", "");
		Hashtable currentemplyeeDetailsHT=(Hashtable)stageDetHT.get("EmployeedetHT");
		Hashtable curentPropertiesHT=(Hashtable)stageDetHT.get("PropertiesHT");
		String Properties=(String)curentPropertiesHT.get("Properties");
		String stageName = (String) stageDetHT.get("Stage_Name");
		String members = ""; 
		String wfType = wfEle.getAttribute("Workflow_Type");
		for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
			Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(i);
			if(Properties.equalsIgnoreCase("Serial")) {
				members = (String)empHt.get("empName");
				break;
			}else {
				members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
			}
			
		}
		nxtStgMsg = stageName+" ("+members+")";
		if(wfType.equalsIgnoreCase("Simple")) {
			nxtStgMsg = members;
		}
//		Hashtable testHT = WorkflowManager.getNextDocumentUsers(docXmlDoc, wfEle, "1", attachedBy);
//		//String nxtStgMsg = "";
//		if(testHT != null) {
//			ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>() : (ArrayList<String>)testHT.get("Users");
//			String stage = testHT.get("Stage") == null ? "" : (String)testHT.get("Stage");
//			nxtStgMsg = stage;
//			for (int i=0;i<testAL.size();i++) {
//				 nxtStgMsg = nxtStgMsg+(i == 0 ? " (":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
//			}
//		}
		System.out.println("-=-=-=-=-=-nxtStgMsg=-=-=-="+nxtStgMsg);
		detailsHT.put("Status", "Success");
		detailsHT.put("Message", "Document is attached to the workflow.");
		detailsHT.put("DocumentID", uniqueid);
		detailsHT.put("DocumentName", documentPath.split("###")[1].equalsIgnoreCase("Server") ? documentPath.split("###")[0]+"\\"+documentName : documentPath.split("###")[0]+"\\"+documentName);
		detailsHT.put("NextStageUserDetails", nxtStgMsg);
		return detailsHT;
	}
	
	public static void addLog(Document docXmlDoc, String docID,String action,String action_by,String action_date,String esign_info ,String to,String fromStage) throws Exception {
		System.out.println("inside add Log====="+docXmlDoc+"======"+docID);
		LoginProcessManager  log=new LoginProcessManager();
		PropUtil prop = new PropUtil();
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		Element docEle = (Element)docNd;
	
		//docEle.appendChild(docEl1e);
		//workFlowN=wfNd;
	//	System.out.println(documentPath+":::::::::documentPath:::::::"+"workFlowID:: "+wfID+" -=-=-=-=-=-=workFlowN-=-=-="+workFlowN);
		Element workFlowEle = (Element) docNd;
		NodeList historyNdList = docEle.getElementsByTagName("Logs");
		Element historyEle = null;
		if(historyNdList == null || historyNdList.getLength()<=0) {
			historyEle = docXmlDoc.createElement("Logs");
			docEle.appendChild(historyEle);
		}else {
			historyEle = (Element)historyNdList.item(0);
		}
		
		
		
		Element actEle = docXmlDoc.createElement("log");
		String	DateFormat=prop.getProperty("DATE_FORMAT");

		Date lastAccDate = new Date();
		Format formatter = new SimpleDateFormat(DateFormat);
		String accdate = formatter.format(lastAccDate);
		historyEle.appendChild(actEle);
		actEle.setAttribute("Action_by", action_by);
		actEle.setAttribute("Action_type", action);
		actEle.setAttribute("Action_date", action_date);
		actEle.setAttribute("Esign_info", esign_info);
		actEle.setAttribute("To", to);
		actEle.setAttribute("FromStage", fromStage);
		
		System.out.println("look==================================="+docID+"=="+action+"=="+action_by+"=="+action_date+"=="+esign_info);
	}
	private static void checkUsersAreActive(Element wfEle) {
		
		NodeList stagesNdList = wfEle.getElementsByTagName("Stage");
		for(int i=0;i<stagesNdList.getLength();i++) {
			if(stagesNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element stageEle =  (Element) stagesNdList.item(i);
				Node empNd = stageEle.getElementsByTagName("Employee_Names").item(0);
				NodeList usersNdList = empNd.getChildNodes();
				setActiveAttr2User(usersNdList);
				
				Node escNd = stageEle.getElementsByTagName("Escalate_Managers").item(0);
				if(escNd != null) {
					NodeList escManagersNdList = escNd.getChildNodes();
					setActiveAttr2User(escManagersNdList);
				}
				
			}
		}
	}
	private static void setActiveAttr2User(NodeList usersNdList) {
		LoginProcessManager  log=new LoginProcessManager();
		for(int j=0;j<usersNdList.getLength();j++) {
			if(usersNdList.item(j).getNodeType() == Node.ELEMENT_NODE) {
				Element userEle = (Element) usersNdList.item(j);
				String userAccessId = userEle.getAttribute("Access_Unique_ID"); 
				String username = userEle.getAttribute("E-mail");
				Hashtable LoginDetailsHT = log.getLoginDetailsById(username, userAccessId);
				if(LoginDetailsHT != null && !LoginDetailsHT.isEmpty()) {
					String activeFlag = (String) LoginDetailsHT.get("Active");
					if(activeFlag != null && activeFlag.equalsIgnoreCase("false")) {
						userEle.setAttribute("Active", "false");
					}
				}
			}
		}
	}
	public static Hashtable listofTaskForUser(String customerKey, String username) throws Exception {
//		try {
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String loginXMLfile=prop.getProperty("LOGIN_XML_FILE");
			String hierarchyXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
			Hashtable<String, String> hierarchyAttr = new Hashtable<String, String>();
//			
			Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);
//			doc.getDocumentElement().normalize();
			ArrayList HierarchyAttrAL=new ArrayList();
			NodeList HRYNodesAL = Globals.getNodeList(HIERARCHY_XML_DIR, hierarchyXmlFileName, "Hierarchy_Level");
			String hierarchyID="";
			int hierarchyIDInt = 1;
			String hierarchyName="";
			String hierarchyCreatedDate="";
			String hierarchyModifiedDate="";
			String hierchyCategory="";
			String hierarchyStatus="";  // code change Menaka 15FEB2014
			String hierCodeCombination;
			String factStatus="";
			String errorMsg=""; // code change Menaka 21FEB2014
			String dimStatus = "";
			String dimErrorMsg = "";
			//		String workFlowStage = "";
			//		String workFlowStageStatus = "";



			//start code change Menaka 21MAR2014

			
			Document doc1=Globals.openXMLFile(HIERARCHY_XML_DIR, loginXMLfile);


			NodeList ndlist=Globals.getNodeList(HIERARCHY_XML_DIR, loginXMLfile, "User");

			

			String hierIDS = "";

			NodeList nodlist=Globals.getNodeList(HIERARCHY_XML_DIR, loginXMLfile, "User");

			String defaultUserID=nodlist.item(0).getTextContent();

			System.out.println("defaultUserID=====>>"+defaultUserID);

			Node nod=Globals.getNodeByAttrVal(doc1, "User", "Login_ID", username);


			Node attr=null;
			if(nod!=null){  // code change Menaka 24MAR2014
				if(nod.getNodeType()==Node.ELEMENT_NODE){

					for(int m=0;m<nod.getAttributes().getLength();m++){
						attr=nod.getAttributes().item(m);
						if(attr.getNodeName().equals("Allowed_Hierarchies")){
							hierIDS=attr.getNodeValue();
						}
					}

				}
			}


			String hierID4users[]=hierIDS.split(","); 

			ArrayList hierIDList=new ArrayList<>();

			for(int ii=0;ii<hierID4users.length;ii++){
				hierIDList.add(hierID4users[ii]);
			}

			//		System.out.println("hierIDList==========>>>"+hierIDList);
			Hashtable wfHT = new Hashtable<>();
			int wfCount = 0;
			for (int hry = 0; hry < HRYNodesAL.getLength(); hry++) {
				Node node = HRYNodesAL.item(hry);

				Hashtable<String, String> HRYAttrHt = Globals.getAttributeNameandValHT(node);


				hierarchyID = HRYAttrHt.get("Hierarchy_ID");

				if(!username.equals(defaultUserID)){
					if(hierIDList.contains(hierarchyID)){  // code change Menaka 21MAR2014

					}else{
						continue;
					}
				}
				if(String.valueOf(HRYAttrHt.get("CustomerKey")) == null || String.valueOf(HRYAttrHt.get("CustomerKey")).equals("null") || String.valueOf(HRYAttrHt.get("CustomerKey")).isEmpty() || 
						!String.valueOf(HRYAttrHt.get("CustomerKey")).equalsIgnoreCase(customerKey)) {
					continue;
				}



				hierarchyIDInt = Integer.parseInt(hierarchyID);
				hierarchyName = HRYAttrHt.get("Hierarchy_Name");
				hierarchyCreatedDate = HRYAttrHt.get("Created_Date");
				String createdBy = HRYAttrHt.get("Created_By");
				hierarchyModifiedDate = HRYAttrHt.get("Modified_Date");
				hierchyCategory =  HRYAttrHt.get("Hierarchy_Category");
				hierCodeCombination =  String.valueOf(HRYAttrHt.get("Code_Combination"));
				dimStatus = HRYAttrHt.get("Dim_Status");
				dimErrorMsg = HRYAttrHt.get("Dim_Status_Details");
				hierarchyStatus=HRYAttrHt.get("RI_Hierarchy_Type");   // code change Menaka 15FEB2014 // code change Menaka 31MAR2014
				Node workFlowNode = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierarchyID);
				String workFlowStage = "";
				String workFlowStageStatus = "";
				if(workFlowNode != null){
					Hashtable curHT=Globals.getAttributeNameandValHT(workFlowNode);
					String currentStageNo="";

					String stageno=(String)curHT.get("Current_Stage_No");
					if(stageno.equalsIgnoreCase("Completed") || stageno.equalsIgnoreCase("Cancel") || stageno.equalsIgnoreCase("Rules Failed")){
						currentStageNo=(String)curHT.get("Total_No_Stages");
					}else{
						currentStageNo=(String)curHT.get("Current_Stage_No");
					}
					Node currentStageNode=Globals.getNodeByAttrValUnderParent(doc, workFlowNode, "Stage_No", currentStageNo);
					Hashtable curStageHT=Globals.getAttributeNameandValHT(currentStageNode);
					workFlowStage=(String)curStageHT.get("Stage_Name");
					String stagests=(String)curStageHT.get("Stage_Status");
					if(stagests.equalsIgnoreCase("Completed")){
						LoginProcessManager lgnpro=new LoginProcessManager();
						Hashtable stageDetHT=lgnpro.retriveStageDetailsFromXML(workFlowNode, currentStageNo,"");
						Hashtable msgHT=(Hashtable)stageDetHT.get("MessagedetHT");
						String finalmsg=(String)msgHT.get("Final");
						workFlowStageStatus=finalmsg;		
					}else{
						workFlowStageStatus=(String)curStageHT.get("Stage_Status");
					}

					
					wfCount++;
					Hashtable detailsHT = new Hashtable<>();
					detailsHT.put("WorkflowName", hierarchyName);
					detailsHT.put("CreatedDate", hierarchyCreatedDate);
					detailsHT.put("CreatedBy", createdBy);
					detailsHT.put("WorkflowID", hierarchyID);
					ArrayList<String> stageNameAL = new ArrayList<>();
					Element wfEle = (Element) workFlowNode;
					NodeList stageNdList = wfEle.getElementsByTagName("Stage");
					for(int l=0;l<stageNdList.getLength();l++) {
						if(stageNdList.item(l).getNodeType() == Node.ELEMENT_NODE) {
							Element stageEle = (Element) stageNdList.item(l);
							NodeList userNdList = stageEle.getElementsByTagName("Employee_Names");
							if(userNdList.getLength() > 0) {
								stageNameAL.add(stageEle.getAttribute("Stage_Name"));
							}
						}
					}
					detailsHT.put("StageName", stageNameAL);
					wfHT.put(String.valueOf(wfCount), detailsHT);
				}
				wfHT.put("WorkflowCount", String.valueOf(wfCount));
				System.out.println("hierarchyID---->>>"+hierarchyID);


				

				
				

			}
			
			
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
			return wfHT;
	}
	
	public static Hashtable actionsWaitingForUser(String userName) throws Exception {
		LoginProcessManager  log=new LoginProcessManager();
		Hashtable LoginDetailsHT=log.getLoginDetails(userName);
		String hierarchyIDs = (String) LoginDetailsHT.get("Allowed_Hierarchies");
		String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
		String[] hierIds = hierarchyIDs == null ? "".split(",") : hierarchyIDs.split(",");
		System.out.println("-=-=-=-hierarchyIDs=-=-=-="+hierarchyIDs);
		
		PropUtil prop = new PropUtil();
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String hierLevelXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");
		Document doc=Globals.openXMLFile(hierDir, hierLevelXmlFileName);
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
		Hashtable tasksHT = new Hashtable<>();
		Hashtable adminTasksHT = new Hashtable<>();
		Hashtable userTasksHT = new Hashtable<>();
		Hashtable userAwaitingTaskHT = new Hashtable<>();
		tasksHT.put("AdminTasks", adminTasksHT);
		tasksHT.put("UserTasks", userTasksHT);
		tasksHT.put("UserAwaitingTasks", userAwaitingTaskHT);
//		if(hierarchyIDs.trim().isEmpty()) {
//			return tasksHT;
//		}
		for(int i=0;i<hierIds.length;i++) {
			String hierID = hierIds[i];
			
			
			Node wfNd=Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierID);
			if(wfNd == null)
				continue;
			Element wfEle = (Element) wfNd;
			String docIds = wfEle.getAttribute("DependentObjectID");
			if(docIds.trim().isEmpty()) {
				continue;
			}
			String[] docs = docIds.split(",");
			for(int j=0;j<docs.length;j++) {
				System.out.println("Document ID :"+docs[j]);
				Hashtable WFNodeDetailsHT=Globals.getWFNodeDetails(hierID, docs[j],true,AccessUniqID);
				if(WFNodeDetailsHT.isEmpty())
					continue;
				String currStageNo = (String)WFNodeDetailsHT.get("Current_Stage_No");
				if(currStageNo.equalsIgnoreCase("Completed") || currStageNo.equalsIgnoreCase("Rules Failed") || currStageNo.equalsIgnoreCase("Cancel")) {
					currStageNo = WFNodeDetailsHT.get("Total_No_Stages") == null ? "" : (String)WFNodeDetailsHT.get("Total_No_Stages");
					System.out.println(docs[j]+"-=-=-=-=-=-=-=-docs[j]=-=-=-=-=-="+currStageNo);
					if(currStageNo.trim().isEmpty())
						continue;
					Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docs[j]);
					Node workflowNode = docNd == null ? null : ((Element) docNd).getElementsByTagName("Workflow") == null ? null : ((Element) docNd).getElementsByTagName("Workflow").item(0);
					System.out.println(((Element) docNd).getElementsByTagName("Workflow").item(0)+": workflowNode :"+workflowNode);
					if(workflowNode == null)	continue;
					int currStgNo = Integer.valueOf(currStageNo);
					for(int l=1;l<=currStgNo;l++) {
						Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workflowNode, String.valueOf(l),"");
						Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
						Hashtable mssgeDetailsHT=(Hashtable)currentstageDetailsHT.get("MessagedetHT");
						String finalMsg=(String)mssgeDetailsHT.get("Final");
						String userStatus="";
						boolean flag = false;
						for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
							Hashtable ApproversHT=new Hashtable();
							ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
							userStatus=(String)ApproversHT.get("User_Status");	
							String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
							// get current user name and his Role and Status
							String member=(String)ApproversHT.get("empName");
							Hashtable<String, String> loginDetailsHT = log.getLoginDetails(member);
							String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
							System.out.println(userStatus+"-=-=-=-=-=-=-finalMsg=-=-=-=-=-="+finalMsg);
							System.out.println(member+"-=-=-=-=-=-=-=userName-=-=-=-=-="+userName);
							flag = member.equalsIgnoreCase(userName) && userStatus.equalsIgnoreCase(finalMsg) && !activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true");
							System.out.println(activeFlag+"-=-=-=-=-"+disableFlag+"=-=-=flag-=-=-=-=-="+flag);
							if(flag)	
								break;
						}
						if(flag) {
							ArrayList<String> userCodesAl = new ArrayList<>(mssgeDetailsHT.values());
							if(userStatus.equalsIgnoreCase(finalMsg)) {
								userCodesAl.remove("Rejected");
								userCodesAl.remove("Approved");
								userCodesAl.remove("Initiate");
							}else {
								userCodesAl.remove("Escalate");
								userCodesAl.remove("Remind");
							}
							WFNodeDetailsHT.put("UserCodes", userCodesAl);
							String count = String.valueOf(userAwaitingTaskHT.size());
							userAwaitingTaskHT.put("R"+count, WFNodeDetailsHT);
							break;
						}
					}
					continue;
				}
				String currentMember = (String) WFNodeDetailsHT.get("currentprocessMember");
				if(currentMember == null)
					continue;
				String currentMemberRole = (String) WFNodeDetailsHT.get("currentprocessMemberRole");
				String type = (String) WFNodeDetailsHT.get("wfmemberAcessType");
				Hashtable userDetailsHT = (Hashtable) WFNodeDetailsHT.get("UserDetailsHT");
				String userRole = userDetailsHT == null || userDetailsHT.get("User_Role") == null ? "" : (String) userDetailsHT.get("User_Role");
				String activeFlag = userDetailsHT == null || userDetailsHT.get("Active") == null || String.valueOf(userDetailsHT.get("Active")).isEmpty() ? "true" : String.valueOf(userDetailsHT.get("Active"));
				System.out.println(userName+": currentMember :"+currentMember);
				System.out.println("currentMemberRole :"+currentMemberRole);
				if(userRole.equalsIgnoreCase("Admin")) {
					if(!WFNodeDetailsHT.isEmpty() && !activeFlag.equalsIgnoreCase("false")) {
						String count = String.valueOf(adminTasksHT.size());
						adminTasksHT.put("R"+count, WFNodeDetailsHT);
					}
				}
				if(!WFNodeDetailsHT.isEmpty() && type.equalsIgnoreCase("Serial") && currentMember.equalsIgnoreCase(userName) && !activeFlag.equalsIgnoreCase("false")) {
					String count = String.valueOf(userTasksHT.size());
					userTasksHT.put("R"+count, WFNodeDetailsHT);
				}else if(!WFNodeDetailsHT.isEmpty() && type.equalsIgnoreCase("Parallel")) {
					String count = String.valueOf(userTasksHT.size());
//					String currStageNo = (String)WFNodeDetailsHT.get("Current_Stage_No");
					Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docs[j]);
					Node workflowNode = docNd == null ? null : ((Element) docNd).getElementsByTagName("Workflow") == null ? null : ((Element) docNd).getElementsByTagName("Workflow").item(0);
					System.out.println(((Element) docNd).getElementsByTagName("Workflow").item(0)+": workflowNode :"+workflowNode);
					if(workflowNode == null)	continue;
					Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workflowNode, currStageNo,"");
					Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
					Hashtable mssgeDetailsHT=(Hashtable)currentstageDetailsHT.get("MessagedetHT");
					String finalMsg=(String)mssgeDetailsHT.get("Final");
					String userStatus="";
					boolean flag = false;
					for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
						Hashtable ApproversHT=new Hashtable();
						ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
						userStatus=(String)ApproversHT.get("User_Status");	
						String member=(String)ApproversHT.get("empName");
						activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
						Hashtable<String, String> loginDetailsHT = log.getLoginDetails(member);
						String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
						if(member.equalsIgnoreCase(userName) && !userStatus.equalsIgnoreCase(finalMsg) && !activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true")) {
							userTasksHT.put("R"+count, WFNodeDetailsHT);
							break;
						}
					}
					
				}
				
//					String currStageNo = (String)WFNodeDetailsHT.get("Current_Stage_No");
					if(currStageNo.equalsIgnoreCase("Completed") || currStageNo.equalsIgnoreCase("Cancel") || currStageNo.equalsIgnoreCase("Rules Failed")) {
						currStageNo = WFNodeDetailsHT.get("Total_No_Stages") == null ? "" : (String)WFNodeDetailsHT.get("Total_No_Stages");
						System.out.println(docs[j]+"-=-=-=-=-=-=-=-docs[j]=-=-=-=-=-="+currStageNo);
						if(currStageNo.trim().isEmpty())
							continue;
					}
//					else {
						Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docs[j]);
						Node workflowNode = docNd == null ? null : ((Element) docNd).getElementsByTagName("Workflow") == null ? null : ((Element) docNd).getElementsByTagName("Workflow").item(0);
						System.out.println(((Element) docNd).getElementsByTagName("Workflow").item(0)+": workflowNode :"+workflowNode);
						if(workflowNode == null)	continue;
						int currStgNo = Integer.valueOf(currStageNo);
						for(int l=1;l<=currStgNo;l++) {
							Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workflowNode, String.valueOf(l),"");
							Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
							Hashtable mssgeDetailsHT=(Hashtable)currentstageDetailsHT.get("MessagedetHT");
							String finalMsg=(String)mssgeDetailsHT.get("Final");
							String userStatus="";
							boolean flag = false;
							for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
								Hashtable ApproversHT=new Hashtable();
								ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
								userStatus=(String)ApproversHT.get("User_Status");	
								activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
								// get current user name and his Role and Status
								String member=(String)ApproversHT.get("empName");
								Hashtable<String, String> loginDetailsHT = log.getLoginDetails(member);
								String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
								System.out.println(userStatus+"-=-=-=-=-=-=-finalMsg=-=-=-=-=-="+finalMsg);
								System.out.println(member+"-=-=-=-=-=-=-=userName-=-=-=-=-="+userName);
								flag = member.equalsIgnoreCase(userName) && userStatus.equalsIgnoreCase(finalMsg) && !activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true");
								System.out.println(activeFlag+"-=-=-=-=-"+disableFlag+"=-=-=flag-=-=-=-=-="+flag);
								if(flag)	
									break;
							}
							if(flag) {
								ArrayList<String> userCodesAl = new ArrayList<>(mssgeDetailsHT.values());
								if(userStatus.equalsIgnoreCase(finalMsg)) {
									userCodesAl.remove("Rejected");
									userCodesAl.remove("Approved");
									userCodesAl.remove("Initiate");
								}else {
									userCodesAl.remove("Escalate");
									userCodesAl.remove("Remind");
								}
								WFNodeDetailsHT.put("UserCodes", userCodesAl);
								String count = String.valueOf(userAwaitingTaskHT.size());
								userAwaitingTaskHT.put("R"+count, WFNodeDetailsHT);
								break;
							}
						}
//					}
				
				/*Hashtable WFNodeDetailsHT1=Globals.getWFNodeDetails(hierID, docs[j],true,AccessUniqID, true);
				System.out.println("WFNodeDetailsHT1 :"+WFNodeDetailsHT1);
				if(WFNodeDetailsHT1.isEmpty())
					continue;
				String lastMember = (String) WFNodeDetailsHT1.get("LastMemberName");
				System.out.println(userName+": lastMember :"+lastMember);
				if(lastMember != null && lastMember.equalsIgnoreCase(userName))
					userAwaitingTaskHT.put(String.valueOf(userAwaitingTaskHT.size()), WFNodeDetailsHT1);*/
				System.out.println("Hierarchy ID :"+hierID);
				System.out.println("Workflow Details for the User :"+WFNodeDetailsHT);
			}
		}
		String count = String.valueOf(adminTasksHT.size());
		System.out.println("-=-=-=-=-=-=-="+count);
		adminTasksHT.put("TableLength", count);
		count = String.valueOf(userAwaitingTaskHT.size());
		userAwaitingTaskHT.put("TableLength", count);
		count = String.valueOf(userTasksHT.size());
		userTasksHT.put("TableLength", count);
		JSONObject jobj = new JSONObject(tasksHT);
		System.out.println("tasksHT::::"+jobj.toJSONString());
		return tasksHT;
	}
	
	public static Hashtable getNotificationDetails4TheAction(String userName, String notes, String actionName, String docName, String documentId) throws Exception {
		Hashtable detailsHT = new Hashtable<>();
		LoginProcessManager  log=new LoginProcessManager();
		String statusMsg = actionName;
		PropUtil prop = new PropUtil();
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
		
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
		System.out.println(actionName+" :: "+userName+" : : "+docNd+"-=-=-=-=-=-getNotificationDetails4TheAction=-=-=-=-="+documentId);
		if(docNd == null) {
			throw new Exception("Document is not attached to Workflow.");
//			return detailsHT;
		}
		Element docEle = (Element)docNd;
		String paused = docEle.getAttribute("Paused");
		String hierarchyName = Globals.getAttrVal4AttrName(docNd, "WorkflowName");
		Node workFlowN =  docEle.getElementsByTagName("Workflow").item(0);
		Element wfEle = (Element) workFlowN;
		String currStageNo = wfEle.getAttribute("Current_Stage_No");
		String stageName = wfEle.getAttribute("LastAccessStageName");
		String totalStage = wfEle.getAttribute("Total_No_Stages");
		String bobyofMail = "";
		String subject = "";
		ArrayList<String> membersAL = new ArrayList<>();
		if(actionName.equalsIgnoreCase("Approve") || actionName.equalsIgnoreCase("Completed") || actionName.equalsIgnoreCase("Initiate")) {
			Hashtable testHT = getNextDocumentUsers(docXmlDoc, workFlowN, currStageNo, userName);
			ArrayList<String> testAL = (ArrayList<String>)testHT.get("Users");
			String stage = (String)testHT.get("Stage");
			bobyofMail = "Workflow Name / Document Name : "+hierarchyName+" / "+ docName;
			bobyofMail = bobyofMail+"\n"+"Approved by "+userName;
			if(Integer.valueOf(currStageNo)==Integer.valueOf(totalStage)) {
				bobyofMail = bobyofMail+"\n"+"Workflow will be completed ,";
			}else if(stage.equalsIgnoreCase("Rules Failes")){
				bobyofMail = bobyofMail+"\n"+"Rules has failed so, the document will not go any further.";
			}else {
				bobyofMail = bobyofMail+"\n"+"Document will be moved to "+stage+" stage and to following Member(s),";
			}
			for(String user : testAL) {
				if(!bobyofMail.contains(user))
					bobyofMail = bobyofMail+"\n\t"+user;
			}
		}else if(actionName.equalsIgnoreCase("Reject")) {
			Hashtable testHT = rejectNextUsers(docXmlDoc, workFlowN, currStageNo, userName);
			ArrayList<String> testAL = (ArrayList<String>)testHT.get("Users");
			String stage = (String)testHT.get("Stage");
			bobyofMail = "Workflow Name / Document Name : "+hierarchyName+" / "+ docName;
			bobyofMail = bobyofMail+"\n"+"Rejected by "+userName;
			bobyofMail = bobyofMail+"\n"+"Document will be moved to "+stage+" stage and to following Member(s),";
			for(String user : testAL) {
				bobyofMail = bobyofMail+"\n\t"+user;
			}
		}else if(actionName.equalsIgnoreCase("Escalate") || actionName.equalsIgnoreCase("Remind")) {
			subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ docName;
			/*Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currentStgNo,"");
			Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
			for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
				Hashtable ApproversHT=new Hashtable();
				ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
				String emailID = (String) ApproversHT.get("empName");
				
			}*/
			Hashtable mailDtailsHT=log.getMailDetailsFromConfig(docXmlDoc,workFlowN,currStageNo,userName, actionName, "Next", false , "");
			String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			bobyofMail=mailMessage;
			
			Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currStageNo,"");
			String temp = actionName.equalsIgnoreCase("Remind") ? "Reminded" : "Escalated";
			Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
			if(actionName.equalsIgnoreCase("Escalate"))
				currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EscalateManagersHT");
			subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
					subject : String.valueOf(mailDtailsHT.get("Subject"));
			
			String members = ""; 
			for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
				Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(i);
				members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
				membersAL.add((String)empHt.get("empName"));
			}
			bobyofMail = "Workflow Name / Document Name : "+hierarchyName+" / "+ docName;
			bobyofMail = bobyofMail+"\n"+temp+" by "+userName;
			bobyofMail = bobyofMail+"\n"+temp+" to following Member(s),";
			for(String user : membersAL) {
				bobyofMail = bobyofMail+"\n\t"+user;
			}
		}else if(actionName.equalsIgnoreCase("Cancel")) {
			
			Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currStageNo,"");

			Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
			Hashtable curentPropertiesHT=(Hashtable)currentstageDetailsHT.get("PropertiesHT");
			String Properties=(String)curentPropertiesHT.get("Properties");
			
			String members = ""; 
			for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
				Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(i);
				members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
			}
			String toStage = (String)currentstageDetailsHT.get("Stage_Name");
			String dueDate = (String)currentstageDetailsHT.get("EffectiveEndDate");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date due = sdf.parse(dueDate);
			long diff = new Date().getTime() - due.getTime();
			int diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
			System.out.println(dueDate+"-=-=-=-=-=-=-=-=-=-="+stageName);
			if(Properties.equalsIgnoreCase("Serial")) {
				Hashtable empHT = (Hashtable)currentemplyeeDetailsHT.get(0);
				membersAL.add((String)empHT.get("empName"));
			}else {
//				mailIds = new String[currentemplyeeDetailsHT.size()+1];
				for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
					Hashtable empHT = (Hashtable)currentemplyeeDetailsHT.get(i);
					membersAL.add(String.valueOf(empHT.get("empName")));
				}
//				mailIds[currentemplyeeDetailsHT.size()] = userName;
			}
			bobyofMail = "Workflow Name / Document Name : "+hierarchyName+" / "+ docName;
			bobyofMail = bobyofMail+"\n"+"Canceled by "+userName;
			bobyofMail = bobyofMail+"\n"+"Notification will be sent to following Member(s),";
			for(String user : membersAL) {
				bobyofMail = bobyofMail+"\n\t"+user;
			}
		}else if(actionName.equalsIgnoreCase("Pause")) {
			Element stageN = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", currStageNo);
			String sendMailStatus = stageN.getAttribute("Pass_Notification_To");
			if(sendMailStatus.equalsIgnoreCase("Affected_Member")) {
				membersAL.add(userName);
//				log.postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail);
			}else if(sendMailStatus.equalsIgnoreCase("All_Members_in_Stage")) {
				Hashtable sendMailStageHT = log.retriveStageDetailsFromXML(wfEle,String.valueOf(currStageNo),"");
				Hashtable sendmailempNMHT =(Hashtable)sendMailStageHT.get("EmployeedetHT");
				for (int mail = 0; mail < sendmailempNMHT.size(); mail++) {
					Hashtable mailApproversHT=new Hashtable();
					mailApproversHT=(Hashtable)sendmailempNMHT.get(mail);
					System.out.println("-=-=-=-mailApproversHT=-=-="+mailApproversHT);
//					String usermailID=(String)mailApproversHT.get("E-mail");	
					String usrname=(String)mailApproversHT.get("empName");	
					membersAL.add(userName);
				}
				
			}else if(sendMailStatus.equalsIgnoreCase("All_Members_Before_Stage")) {
				
				for(int i=1;i<=Integer.valueOf(currStageNo);i++)
				{
					Hashtable stageDetHT= log.retriveStageDetailsFromXML(workFlowN, String.valueOf(i),"");
					Hashtable memdetHT=(Hashtable)stageDetHT.get("EmployeedetHT");
					for (int mail = 0; mail < memdetHT.size(); mail++) {
						Hashtable mailApproversHT=new Hashtable();
						mailApproversHT=(Hashtable)memdetHT.get(mail);
						System.out.println("-=-=-=-mailApproversHT=-=-="+mailApproversHT);
//						String usermailID=(String)mailApproversHT.get("E-mail");	
						String usrname=(String)mailApproversHT.get("empName");	
						membersAL.add(userName);
					}
				}
				
			}else if(sendMailStatus.equalsIgnoreCase("All_Members_in_WF")) {
				int totalStages=Integer.parseInt(workFlowN.getAttributes().getNamedItem("Total_No_Stages").getNodeValue());

				for(int i=1;i<=totalStages;i++)
				{
					Hashtable stageDetHT= log.retriveStageDetailsFromXML(workFlowN, String.valueOf(i),"");
					Hashtable memdetHT=(Hashtable)stageDetHT.get("EmployeedetHT");
					for (int mail = 0; mail < memdetHT.size(); mail++) {
						Hashtable mailApproversHT=new Hashtable();
						mailApproversHT=(Hashtable)memdetHT.get(mail);
						System.out.println("-=-=-=-mailApproversHT=-=-="+mailApproversHT);
//						String usermailID=(String)mailApproversHT.get("E-mail");	
						String usrname=(String)mailApproversHT.get("empName");	
						membersAL.add(userName);
					}
				}
			}
			bobyofMail = "Workflow Name / Document Name : "+hierarchyName+" / "+ docName;
			bobyofMail = bobyofMail+"\n"+"Pasued by "+userName;
			bobyofMail = bobyofMail+"\n"+"Notification will be sent to following Member(s),";
			for(String user : membersAL) {
				bobyofMail = bobyofMail+"\n\t"+user;
			}
		}
		detailsHT.put("Message", bobyofMail);
		detailsHT.put("Subject", subject);
		return detailsHT;
	}
	
	
	
	public static Hashtable performingAction(String userName, String notes, String actionName, String docName, String documentId, String comingFrom,String attchID, String attchUser, String attchFileName) throws Exception {
		Hashtable detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
		String statusMsg = actionName;
		
		//		try {
		LoginProcessManager  log=new LoginProcessManager();
		
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
		if(docNd == null) {
			detailsHT.put("UserCodes", new ArrayList<>());
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", "Document is not attached to Workflow.");
			return detailsHT; 
		}
		Element docEle = (Element)docNd;
		String paused = docEle.getAttribute("Paused");
		String docID = docEle.getAttribute("Document_ID");
		String wfID = docEle.getAttribute("WorkflowID");
		String custKey = docEle.getAttribute("CustomerKey");
		String primaryFileConn=docEle.getAttribute("Primary_FileConn");
		String hierarchyName = Globals.getAttrVal4AttrName(docNd, "WorkflowName");
		String documentName = docEle.getAttribute("DocumentName");;
		Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
		String currStageNo = wfEle.getAttribute("Current_Stage_No");
		String wfType = wfEle.getAttribute("Workflow_Type");
		String documentPath = "";
		Hashtable LoginDetailsHT=log.getLoginDetails(userName, custKey);
		String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
		System.out.println(AccessUniqID+"************"+custKey+"***********actionName*******************"+actionName);
		String nxtStgMsg = "";
		if(!actionName.equalsIgnoreCase("Reject") && !actionName.equalsIgnoreCase("Rejected")) {
			Hashtable testHT = WorkflowManager.getNextDocumentUsers(docXmlDoc, wfEle, currStageNo, userName);

			if(testHT != null) {
				ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>() : (ArrayList<String>)testHT.get("Users");
				String stage = testHT.get("Stage") == null ? "" : (String)testHT.get("Stage");
				nxtStgMsg = stage;
				String users = "";
				for (int i=0;i<testAL.size();i++) {
					nxtStgMsg = nxtStgMsg+(i == 0 ? " (":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
					users = testAL.get(i)+(i < testAL.size()-1 ? ", " : "");
				}
				if(wfType.equalsIgnoreCase("Simple")) {
					nxtStgMsg = users;
				}
			}
		}else {
			Hashtable testHT = rejectNextUsers(docXmlDoc, wfEle, currStageNo, userName);
			if(testHT != null) {
				ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>() : (ArrayList<String>)testHT.get("Users");
				String stage = testHT.get("Stage") == null ? "" : (String)testHT.get("Stage");
				nxtStgMsg = stage;
				for (int i=0;i<testAL.size();i++) {
					if(wfType.equalsIgnoreCase("simple")) {
						
						 nxtStgMsg = (i == 0 ? "(":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
					}else{
						
						 nxtStgMsg = nxtStgMsg+(i == 0 ? "(":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
					}
					
				}
			}
		}
		if(actionName.equalsIgnoreCase("Approve") || actionName.equalsIgnoreCase("Approved")) {
			statusMsg = "Approved";
			setStatusMsg4EMP(wfID, docID, hierarchyName, currStageNo, AccessUniqID, notes, statusMsg, AccessUniqID, false, userName);
			if(comingFrom.equalsIgnoreCase("MSO")) {
				documentPath = createDocumentVersionForWF(docEle.getAttribute("DocumentName"), docID, wfEle.getAttribute("Storage"), wfEle,primaryFileConn);
				detailsHT.put("DocumentName", documentPath.split("###")[1].equalsIgnoreCase("Server") ? documentPath.split("###")[0]+"\\"+documentName : documentPath.split("###")[0]+"\\"+documentName);
			}
			
		}else if(actionName.equalsIgnoreCase("Completed")) {
			statusMsg = "Completed";
			setStatusMsg4EMP(wfID, docID, hierarchyName, currStageNo, AccessUniqID, notes, statusMsg, AccessUniqID, false, userName);
			if(comingFrom.equalsIgnoreCase("MSO")) {
			documentPath = createDocumentVersionForWF(docEle.getAttribute("DocumentName"), docID, wfEle.getAttribute("Storage"), wfEle,primaryFileConn);
			detailsHT.put("DocumentName", documentPath.split("###")[1].equalsIgnoreCase("Server") ? documentPath.split("###")[0]+"\\"+documentName : documentPath.split("###")[0]+"\\"+documentName);
			}
		}else if(actionName.equalsIgnoreCase("Acknowledged")) {
			statusMsg = "Acknowledged";
			setStatusMsg4EMP(wfID, docID, hierarchyName, currStageNo, AccessUniqID, notes, statusMsg, AccessUniqID, false, userName);
			if(comingFrom.equalsIgnoreCase("MSO")) {
			documentPath = createDocumentVersionForWF(docEle.getAttribute("DocumentName"), docID, wfEle.getAttribute("Storage"), wfEle,primaryFileConn);
			detailsHT.put("DocumentName", documentPath.split("###")[1].equalsIgnoreCase("Server") ? documentPath.split("###")[0]+"\\"+documentName : documentPath.split("###")[0]+"\\"+documentName);
			}
		}else if(actionName.equalsIgnoreCase("Reject") || actionName.equalsIgnoreCase("Rejected")) {
			statusMsg = "Rejected";
			setStatusMsg4EMP(wfID, docID, hierarchyName, currStageNo, AccessUniqID, notes, statusMsg, AccessUniqID, false, userName);
			if(comingFrom.equalsIgnoreCase("MSO")) {
			documentPath = createDocumentVersionForWF(docEle.getAttribute("DocumentName"), docID, wfEle.getAttribute("Storage"), wfEle,primaryFileConn);
			detailsHT.put("DocumentName", documentPath.split("###")[1].equalsIgnoreCase("Server") ? documentPath.split("###")[0]+"\\"+documentName : documentPath.split("###")[0]+"\\"+documentName);
			}
		}else if(actionName.equalsIgnoreCase("Resubmit")) {
			
			statusMsg = "Resubmit";
			System.out.println("-=-=-=-statusMsg=-=-="+statusMsg);
			setStatusMsg4RESUBMIT(wfID, docID, hierarchyName, currStageNo, AccessUniqID, notes, statusMsg, AccessUniqID, false, userName, attchID, attchUser, attchFileName);
			
			if(comingFrom.equalsIgnoreCase("MSO")) {
			documentPath = createDocumentVersionForWF(docEle.getAttribute("DocumentName"), docID, wfEle.getAttribute("Storage"), wfEle,primaryFileConn);
			detailsHT.put("DocumentName", documentPath.split("###")[1].equalsIgnoreCase("Server") ? documentPath.split("###")[0]+"\\"+documentName : documentPath.split("###")[0]+"\\"+documentName);
			}
			
			
		}
		else if(actionName.equalsIgnoreCase("Initiate")) {
			statusMsg = "Initiate";
			setStatusMsg4EMP(wfID, docID, hierarchyName, currStageNo, AccessUniqID, notes, statusMsg, AccessUniqID, false, userName);
			if(comingFrom.equalsIgnoreCase("MSO")) {
			documentPath = createDocumentVersionForWF(docEle.getAttribute("DocumentName"), docID, wfEle.getAttribute("Storage"), wfEle,primaryFileConn);
			detailsHT.put("DocumentName", documentPath.split("###")[1].equalsIgnoreCase("Server") ? documentPath.split("###")[0]+"\\"+documentName : documentPath.split("###")[0]+"\\"+documentName);
			}
		}
		else if(actionName.equalsIgnoreCase("Escalate")) {
			remindMembers(docID, wfID, userName, actionName);
			int prevStageno = Integer.valueOf(currStageNo) - 1;
			boolean flag = false;
			for(int i=prevStageno;i>=1;i--) {
				Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(wfEle, String.valueOf(prevStageno),"");
				Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
				for(int j=0;j<currentemplyeeDetailsHT.size();j++) {
					Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(j);
					String empName = (String)empHt.get("empName");
					if(userName.equalsIgnoreCase(empName)) {
						currStageNo = String.valueOf(i);
						flag = true;
						break;
					}
				}
				if(flag)
					break;
			}
		}else if(actionName.equalsIgnoreCase("Remind")) {
			remindMembers(docID, wfID, userName, actionName);
			int prevStageno = Integer.valueOf(currStageNo) - 1;
			boolean flag = false;
			for(int i=prevStageno;i>=1;i--) {
				Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(wfEle, String.valueOf(prevStageno),"");
				Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
				for(int j=0;j<currentemplyeeDetailsHT.size();j++) {
					Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(j);
					String empName = (String)empHt.get("empName");
					if(userName.equalsIgnoreCase(empName)) {
						currStageNo = String.valueOf(i);
						flag = true;
						break;
					}
				}
				if(flag)
					break;
			}
		}else if(actionName.equalsIgnoreCase("Cancel")) {
			boolean cancelFlag = cancelApproveRequest(docID, wfID, hierarchyName, userName, currStageNo);
			detailsHT.put("CancelMessage", cancelFlag ? "Cancellation for document is cancelled successfully." : "Cancellation for document is failed.");
		}else if(actionName.equalsIgnoreCase("WorkflowCancel")) {
			
			boolean cancelFlag = workflowcancelRequest(docID, wfID, hierarchyName, userName, currStageNo);
			detailsHT.put("CancelMessage", cancelFlag ? "Cancellation for document is cancelled successfully." : "Cancellation for document is failed.");
		}
		else if(actionName.equalsIgnoreCase("Pause")) {
			pauseWorkflow(docID, wfID, hierarchyName, userName, currStageNo, paused);
		}
		
		
		ArrayList<String> userCodesAL = new ArrayList<>();
		docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
		docEle = (Element)Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
		wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
		String stageNo = wfEle.getAttribute("LastAccessStage");
		String stageName = wfEle.getAttribute("LastAccessStageName");
		System.out.println("-=-=-=-=stageNo-=-=-="+stageNo);
		if(!stageNo.trim().isEmpty()) {
			Hashtable stageDetailsHT =log.retriveStageDetailsFromXML(wfEle,stageNo,"");
			Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
			userCodesAL.addAll(messagesHT.values());
			Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
			String finalmsg=(String)messagesHT.get("Final");
			String status = "";
			for(int i=0;i<empNMHT.size();i++) {
				Hashtable empHT = (Hashtable) empNMHT.get(i);
				String member=(String)empHT.get("empName");
				String userStatus=(String)empHT.get("User_Status");
				if(member.equalsIgnoreCase(userName)) {
					status = userStatus.equalsIgnoreCase(finalmsg) ? "Completed" : "WIP";
					break;
				}
			}
			if(status.equalsIgnoreCase("Completed")) {
				userCodesAL.remove("Rejected");
				userCodesAL.remove("Approved");
				userCodesAL.remove("Initiate");
			}else {
				userCodesAL.remove("Escalate");
				userCodesAL.remove("Remind");
			}
		}
		addHistory(docXmlDoc, docID, wfID, statusMsg, notes, currStageNo, userName, documentPath,null);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
	//	addLog(docXmlDoc, documentId, statusMsg, userName, (String)dateFormat.format(cal.getTime()), notes);
	
	Globals.writeXMLFile(docXmlDoc, hierDir, docXmlFileName);
		
		detailsHT.put("NextStageUserDetails", nxtStgMsg);
		if(actionName.equalsIgnoreCase("Approve") || actionName.equalsIgnoreCase("Completed") || actionName.equalsIgnoreCase("Initiate") || actionName.equalsIgnoreCase("Reject")) {
			Document configdoc=Globals.openXMLFile(hierDir, configFileName);
			Element downLinkEle = (Element)configdoc.getElementsByTagName("EsignExecuteURL_Link").item(0);
			String downLink = downLinkEle.getAttribute("URL").trim().isEmpty() ? "" : downLinkEle.getAttribute("URL").trim();
			
			docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
			docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
			docEle = (Element)docNd;
			String customerKey = docEle.getAttribute("CustomerKey");
			wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
			currStageNo = wfEle.getAttribute("Current_Stage_No");
			Hashtable stgDetailsHT = log.retriveStageDetailsFromXML(wfEle, currStageNo, comingFrom);
			String enableEsignFlag = stgDetailsHT.get("Enable_Esign") == null ? "false" : stgDetailsHT.get("Enable_Esign").toString();
			System.out.println("-=-=-=-=-=enableEsignFlag-=-=-=-="+enableEsignFlag);
			if(enableEsignFlag.equalsIgnoreCase("true")) {
				Hashtable tempHT = FormsManager.getTemplateNdFromDocId(docID);
				Node templateNd = tempHT == null ? null : (Node)tempHT.get("TemplateNode");
				if(templateNd != null) {
					Element templateEle = (Element) templateNd;
					String templateName = templateEle.getAttribute("Name");
					String urlStr = downLink.replace("$templatename$", templateName).replace("$username$", userName).replace("$customerKey$", customerKey);//customerKey"http://localhost:38391/Service.svc/executeESignTemplate?templateName="+templateName+"&username="+userName;
					System.out.println("-=-=-=-=-=urlStr-=-=-=-="+urlStr);
					URL url = new URL(urlStr);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Accept", "application/json");

					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
								+ conn.getResponseCode());
					}

					BufferedReader br = new BufferedReader(new InputStreamReader(
							(conn.getInputStream())));

					String output;
					System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						System.out.println(output);
					}

					conn.disconnect();
				}
			}
		}
		detailsHT.put("UserCodes", userCodesAL);
		detailsHT.put("DocumentID", documentId);
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Success");
		detailsHT.put("RevisionType", docEle.getAttribute("RevisionType"));
		return detailsHT;
	}
	
	
	public static void sendMailResubmit(Document levelDoc, Node WfNode,String heirCode,String currentStageNo,String curUserId,
			Hashtable stageDetailsHT,Hashtable messagesHT,Hashtable empNMHT,Hashtable propHT,String currentuserAccUID,String hierarchyName,String attchID, String attchUser, String attchFileName)
	
	{
		
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {
		
			String[] userMailAddress = {attchUser};
			LoginProcessManager lg=new LoginProcessManager();
			String stagefinalStatus=(String)messagesHT.get("Final");
			String Rejected_Return_To =(String)stageDetailsHT.get("Rejected_Return_To");
			String Rejected_Set_Status_To =(String)stageDetailsHT.get("Rejected_Set_Status_To");
			String Rejected_Notification_To =(String)stageDetailsHT.get("Rejected_Notification_To");
			String concurrency =(String)propHT.get("Concurrency");	
			String documentName = Globals.getAttrVal4AttrName(WfNode.getParentNode(), "DocumentName");
			PropUtil prop = new PropUtil();
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			
			Hashtable mailDtailsHT=lg.getMailDetailsFromConfig(levelDoc,WfNode,currentStageNo,curUserId, "Resubmited", "Next", false, attchFileName);
			Element docEle = (Element)WfNode.getParentNode();
			String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			String bobyofMail=mailMessage;
			String subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
					"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
			//bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
			lg.postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
		
		}catch(Exception e)
		{
		
			e.printStackTrace();
			
		}
		
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
		
	}
	
	public static Hashtable setStatusMsg4RESUBMIT(String hierarchyID,String docID, String hierarchyName, String stageNo, String empID, String reasonText,String statusMsg,
			String accessUniqIDcmnFrmLogin,Boolean cmngFromLogin, String adminUsername,String attchID, String attchUser, String attchFileName) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."

					+ new Exception().getStackTrace()[0].getMethodName());

		Hashtable NextProcessHT=new Hashtable<>();

		boolean issendback=false;
		updateLastAccessDetails(hierarchyID, docID, stageNo, adminUsername, statusMsg);

		PropUtil prop = new PropUtil();
		//			String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
		String currentUserName="";

		Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		String documentName = Globals.getAttrVal4AttrName(docNd, "DocumentName");
		String custKey = Globals.getAttrVal4AttrName(docNd, "CustomerKey");
		Node workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", hierarchyID);
		LoginProcessManager lgn=new LoginProcessManager();
		if(!cmngFromLogin){

			Node stageN = Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", stageNo);
			NodeList msgEnamePropNL = stageN.getChildNodes();
			for (int i = 0; i < msgEnamePropNL.getLength(); i++) {
				Node checkN = msgEnamePropNL.item(i);
				if (checkN.getNodeType() == Node.ELEMENT_NODE) {
					if (checkN.getNodeName().equalsIgnoreCase("Employee_Names")) {

						Node nameN = Globals.getNodeByAttrValUnderParent(docXmlDoc, checkN, "Access_Unique_ID", empID);
						if (nameN != null) {

							String	DateFormat=prop.getProperty("DATE_FORMAT");

							Date lastAccDate = new Date();
							Format formatter = new SimpleDateFormat(DateFormat);
							String accdate = formatter.format(lastAccDate);

							Element nameE = (Element) nameN;
							nameE.setAttribute("User_Status", statusMsg);
							nameE.setAttribute("Last_Access", accdate);
							nameE.setAttribute("Notes", reasonText);
							currentUserName=nameN.getTextContent();

							Element stageNELL=(Element)stageN;
							stageNELL.setAttribute("Stage_Status", "WIP");

							String isstatusAvaible=getFinalMsg(workFlowN,statusMsg);



							 if(statusMsg.equals("Resubmit")){

								issendback=true;

								Hashtable stageDetailsHT =lgn.retriveStageDetailsFromXML(workFlowN,stageNo,"");
								Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
								Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
								Hashtable propHT =(Hashtable)stageDetailsHT.get("PropertiesHT");
								sendMailResubmit(docXmlDoc, workFlowN, hierarchyID, stageNo, currentUserName, stageDetailsHT, messagesHT, empNMHT, propHT, empID, hierarchyName,attchID,attchUser,attchFileName);
								


							}

							else if(isstatusAvaible.equals("true")){


								// code change pandian 28Mar2013

								//							send mail to next stage Reviewer
								//..
								String mailTo = statusMsg.equalsIgnoreCase("Initiate") ? "Initiator" : "Next";
								Hashtable mailDtailsHT=lgn.getMailDetailsFromConfig(docXmlDoc,workFlowN,stageNo,adminUsername, statusMsg, mailTo, false , "");
								String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
								String mailPassword=(String)mailDtailsHT.get("Mail_Password");
								String mailMessage=(String)mailDtailsHT.get("Mail_Message");
								String bobyofMail=mailMessage;
								//							String bobyofMail="Please Login to the Heiarchy Genarator to View the Progress.This is an Auto Genarated Email Don't reply to this Mail-ID";
								String subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
										"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
								Hashtable currentstageDetailsHT=lgn.retriveStageDetailsFromXML(workFlowN, String.valueOf(stageNo),"");
								Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
								Hashtable curentPropertiesHT=(Hashtable)currentstageDetailsHT.get("PropertiesHT");
								String Properties=(String)curentPropertiesHT.get("Properties");
								String externalUserFlag = currentstageDetailsHT.get("ExternalUser") == null ? "" : currentstageDetailsHT.get("ExternalUser").toString();
								String autoLoginFlag = currentstageDetailsHT.get("Auto_Login") == null ? "" : currentstageDetailsHT.get("Auto_Login").toString();
								if(Properties.equals("Parallel") && !externalUserFlag.equalsIgnoreCase("true")){  // send all the person
									String members = ""; 
									for(int t=0;t<currentemplyeeDetailsHT.size();t++) {
										Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(t);
										members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
									}
									String toStage = (String)currentstageDetailsHT.get("Stage_Name");
									String dueDate = (String)currentstageDetailsHT.get("EffectiveEndDate");
									SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
									int diffInDays = 0;
									if(dueDate != null && !dueDate.trim().isEmpty()) {
										Date due = sdf.parse(dueDate);
										long diff = new Date().getTime() - due.getTime();
										diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
									}else
										dueDate = "";
									bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
									subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
									String userMailAddress[] = new String[currentemplyeeDetailsHT.size()];
									int mailsend=0;
									Hashtable EmailHT=new Hashtable<>();
									String updateMail2DB = "";
									for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
										Hashtable mailApproversHT=new Hashtable();
										mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
										String usermailID=(String)mailApproversHT.get("E-mail");
										String username=(String)mailApproversHT.get("empName");
										String AccessUNiqID=(String)mailApproversHT.get("Access_Unique_ID");
										String UserStatus=(String)mailApproversHT.get("User_Status");
										String isstatusAvaiblee=getFinalMsg(workFlowN,UserStatus);
										String activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
										Hashtable<String, String> loginDetailsHT = lgn.getLoginDetails(username, custKey);
										String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
										if(AccessUNiqID.equals(empID) || isstatusAvaiblee.equals("true") || activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true")){


										}else{
											EmailHT.put(mailsend, usermailID);										
											mailsend++;

										}

									}

									userMailAddress = new String[EmailHT.size()];
									for (int mail = 0; mail < EmailHT.size(); mail++) {
										String usermailID=(String)EmailHT.get(mail);
										userMailAddress[mail]=usermailID;
										if(mail == 0){
											updateMail2DB = usermailID;
										}else{
											updateMail2DB = usermailID+","+updateMail2DB;
										}
									}
									if(userMailAddress.length>0){
										lgn.postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, custKey);

									}	
									if(statusMsg.equalsIgnoreCase("Initiate")) {

										mailDtailsHT=lgn.getMailDetailsFromConfig(docXmlDoc,workFlowN,stageNo,adminUsername, statusMsg, "Next", false , "");
										mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
										mailPassword=(String)mailDtailsHT.get("Mail_Password");
										mailMessage=(String)mailDtailsHT.get("Mail_Message");
										bobyofMail=mailMessage;
										String[] mailList = {empID};	//check Vishnu - for inactive user
										subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
												"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
										bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
										subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
										lgn.postMail(mailList, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, custKey);
									
									}
								}

								//							else{ // send only one person to next stage who is available on first (Serial)
								//								
								//								int mailsend=0;
								//								Hashtable EmailHT=new Hashtable<>();
								//								for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
								//									Hashtable mailApproversHT=new Hashtable();
								//									mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
								//									String usermailID=(String)mailApproversHT.get("E-mail");	
								//									String AccessUNiqID=(String)mailApproversHT.get("Access_Unique_ID");
								//									String UserStatus=(String)mailApproversHT.get("User_Status");
								//									if(AccessUNiqID.equals(empID) || UserStatus.equals("Completed") || UserStatus.equals("Approved")){
								//										
								//									}else{
								//										EmailHT.put(mailsend, usermailID);										
								//										mailsend++;
								//										break;
								//									}
								//									
								//								}
								//								
								//								 String userMailAddress[] = new String[EmailHT.size()];
								//								for (int mail = 0; mail < EmailHT.size(); mail++) {
								//									String usermailID=(String)EmailHT.get(mail);
								//									userMailAddress[mail]=usermailID;
								//								}
								//							
								//								
								//							
								//									
								//								postMail(userMailAddress, subject, bobyofMail, "accelbi@cygnussoftwares.com","acbi$2014", null, hierlink4Mail);
								//								
								//								
								//							}

							}

							System.out.println("update Last Accessed Date : " + accdate);
							Globals.writeXMLFile(docXmlDoc, hierLeveldir, docXmlFileName);


						} else{
							NextProcessHT.put("ERROR", "Employee is not available in This Stage");
							return NextProcessHT;
						}
					}

				}

			}
		}

		if(!issendback){
			docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
			docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
			workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", hierarchyID);
			NextProcessHT=lgn.workFlowNCheckAccessUser(docXmlDoc, workFlowN, hierarchyID,hierarchyName,stageNo,cmngFromLogin,accessUniqIDcmnFrmLogin,hierLeveldir, docXmlFileName, adminUsername, statusMsg);
			System.out.println("current Stage Processing  HT :"+NextProcessHT);
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return NextProcessHT;

	}
	
	
	
	
	public static void pauseWorkflow(String docID, String wfID, String hierarchyName, String userName, String currStageNo, String paused) throws Exception {
		LoginProcessManager log=new LoginProcessManager();
		PropUtil prop = new PropUtil();
		//			String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
		Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		Element docEle = (Element) docNd;
		String documentName = Globals.getAttrVal4AttrName(docNd, "DocumentName");
		Node workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", wfID);
		Element wfEle = (Element) workFlowN;
		String stageNo = wfEle.getAttribute("LastAccessStage");
		String stageName = wfEle.getAttribute("LastAccessStageName");
		String adminUsername = wfEle.getAttribute("LastAccessMember");
		String action = wfEle.getAttribute("LastAccessAction");
		Element stageN = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", currStageNo);
		String sendMailStatus = stageN.getAttribute("Pass_Notification_To");
		
		Hashtable mailDtailsHT=log.getMailDetailsFromConfig(docXmlDoc,workFlowN,currStageNo,userName, paused.isEmpty() || paused.equalsIgnoreCase("false") ? "Pause" : "Resume", "Next", false , "");
		String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
		String mailPassword=(String)mailDtailsHT.get("Mail_Password");
		String mailMessage=(String)mailDtailsHT.get("Mail_Message");
		String bobyofMail=mailMessage;
		String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
		subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
				subject : String.valueOf(mailDtailsHT.get("Subject"));
		
		if(sendMailStatus.equalsIgnoreCase("Affected_Member")) {
			String[] userMailAddress = {adminUsername};
			log.postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
		}else if(sendMailStatus.equalsIgnoreCase("All_Members_in_Stage")) {
			Hashtable sendMailStageHT = log.retriveStageDetailsFromXML(wfEle,String.valueOf(currStageNo),"");
			Hashtable sendmailempNMHT =(Hashtable)sendMailStageHT.get("EmployeedetHT");

			if(sendmailempNMHT!=null&&sendmailempNMHT.size()!=0)
			{
				log.sendmailFromAdmin(sendmailempNMHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
			}
		}else if(sendMailStatus.equalsIgnoreCase("All_Members_Before_Stage")) {
			
			for(int i=1;i<=Integer.valueOf(currStageNo);i++)
			{
				Hashtable stageDetHT= log.retriveStageDetailsFromXML(workFlowN, String.valueOf(i),"");
				Hashtable memdetHT=(Hashtable)stageDetHT.get("EmployeedetHT");
				if(memdetHT!=null&&memdetHT.size()!=0){
					log.sendmailFromAdmin(memdetHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
				}
			}
			
		}else if(sendMailStatus.equalsIgnoreCase("All_Members_in_WF")) {
			int totalStages=Integer.parseInt(workFlowN.getAttributes().getNamedItem("Total_No_Stages").getNodeValue());

			for(int i=1;i<=totalStages;i++)
			{
				Hashtable stageDetHT= log.retriveStageDetailsFromXML(workFlowN, String.valueOf(i),"");
				Hashtable memdetHT=(Hashtable)stageDetHT.get("EmployeedetHT");
				if(memdetHT!=null&&memdetHT.size()!=0){
					log.sendmailFromAdmin(memdetHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
				}
			}
		}
		Node empNd = stageN.getElementsByTagName("Employee_Names").item(0);
		Element userEle = (Element)Globals.getNodeByAttrValUnderParent(docXmlDoc, empNd, "E-mail", userName);
		if(paused.isEmpty() || paused.equalsIgnoreCase("false")) {
			userEle.setAttribute("Prev_User_Status", userEle.getAttribute("User_Status"));
			userEle.setAttribute("User_Status", "Pause");
		}else {
			userEle.setAttribute("User_Status", userEle.getAttribute("Prev_User_Status"));
		}
		
		docEle.setAttribute("Paused", paused.isEmpty() || paused.equalsIgnoreCase("false") ? "true" : "false");
		docEle.setAttribute("PausedBy", userName);
		docEle.setAttribute("PauseStageNo", currStageNo);
		Globals.writeXMLFile(docXmlDoc, hierLeveldir, docXmlFileName);
	}
	
	
	
	
	
	public static boolean workflowcancelRequest(String docID, String wfID, String hierarchyName, String userName, String currStageNo) throws Exception {
		boolean workflowcancelFlag = true;
		try {
			
			
			System.out.println("docID*************** :"+docID);
			System.out.println("wfID*************** :"+wfID);
			System.out.println("hierarchyName*************** :"+hierarchyName);
			System.out.println("userName*************** :"+userName);
			System.out.println("currStageNo*************** :"+currStageNo);
			ArrayList<String> emailList = new ArrayList<>();
			Hashtable detailsHT = new Hashtable<>();
			PropUtil prop = new PropUtil();
			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
			String currentUserName="";
			if(currStageNo.equalsIgnoreCase("Completed") || currStageNo.equalsIgnoreCase("Rules Failed")) {
				throw new Exception("Workflow is already completed you cannot cancel it now.");
				//			return;
			}
			if(currStageNo.equalsIgnoreCase("Cancel")) {
				throw new Exception("Workflow is already Cancel you cannot cancel it now.");
				//			return;
			}
			Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
			Element docNdEle = (Element) docNd;
			String documentName = Globals.getAttrVal4AttrName(docNd, "DocumentName");
			Node workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", wfID);
			Element wfEle = (Element) workFlowN;
			String createBy= docNdEle.getAttribute("Created_By");
			String stageNo = wfEle.getAttribute("LastAccessStage");
			String stageName = wfEle.getAttribute("LastAccessStageName");
			String adminUsername = wfEle.getAttribute("LastAccessMember");
			String action = wfEle.getAttribute("LastAccessAction");
			LoginProcessManager log=new LoginProcessManager();
			
		
				Element stageN = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", currStageNo);
				Hashtable stageDetailsHT =log.retriveStageDetailsFromXML(workFlowN,currStageNo,"");
				Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
				Hashtable currentemplyeeDetailsHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
				String finalmsg=(String)messagesHT.get("Final");
				Hashtable curentPropertiesHT=(Hashtable)stageDetailsHT.get("PropertiesHT");
				String Properties=(String)curentPropertiesHT.get("Properties");
			
				for (int m = 0; m < currentemplyeeDetailsHT.size(); m++) {
					Hashtable ApproversHT=new Hashtable();
					ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(m);
					String member=(String)ApproversHT.get("empName");
					System.out.println(Properties+"::EEEEEEEEEEEEE*****member*******EEEEEEEEEEEEE :"+member);
					emailList.add(member);
				
				}
				
				Hashtable mailDtailsHT=log.getMailDetailsFromConfig(docXmlDoc,workFlowN,currStageNo,userName, "Cancelled", "Success", false , "");
				String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
				String mailPassword=(String)mailDtailsHT.get("Mail_Password");
				String mailMessage=(String)mailDtailsHT.get("Mail_Message");
				String bobyofMail=mailMessage;
				
				System.out.println("::EEEEEEEEEEEEE*****bobyofMail*******EEEEEEEEEEEEE :"+bobyofMail);
				//Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currStageNo,"");

				
				String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
				subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
						subject : String.valueOf(mailDtailsHT.get("Subject"));
				String members = ""; 
				for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
					Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(i);
					members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
				}
				String toStage = (String)stageDetailsHT.get("Stage_Name");
				String dueDate = (String)stageDetailsHT.get("EffectiveEndDate");
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				int diffInDays = 0;
				if(dueDate != null && !dueDate.trim().isEmpty()) {
					Date due = sdf.parse(dueDate);
					long diff = new Date().getTime() - due.getTime();
					diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
				}else
					dueDate = "";
				bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members).replace("$CancelStage$", stageName);
				subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members).replace("$CancelStage$", stageName);
				String[] userMailAddress = 	emailList.toArray(new String[emailList.size()]);
				log.postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, docNdEle.getAttribute("CustomerKey"));
			
				 workflowcancelFlag = true;
			
				 currStageNo = "1";
					
				 addHistory(docXmlDoc, docID, wfID, "Cancel", "", currStageNo, userName, "",workFlowN);
				// Globals.writeXMLFile(docXmlDoc, hierLeveldir, docXmlFileName);
			
			
			
				
		}finally {
			updateLastCancelAction(wfID, docID, currStageNo, userName, "Cancel");
		}
		
		
		return workflowcancelFlag;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static boolean cancelApproveRequest(String docID, String wfID, String hierarchyName, String userName, String currStageNo) throws Exception {
		boolean cancelFlag = false;
		try {
		
		PropUtil prop = new PropUtil();
		//			String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
		String currentUserName="";
		if(currStageNo.equalsIgnoreCase("Completed") || currStageNo.equalsIgnoreCase("Cancel") || currStageNo.equalsIgnoreCase("Rules Failed")) {
			throw new Exception("Workflow is already completed you cannot cancel it now.");
			//			return;
		}
		Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		Element docEle = (Element)docNd;
		String documentName = Globals.getAttrVal4AttrName(docNd, "DocumentName");
		Node workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", wfID);
		Element wfEle = (Element) workFlowN;
		String stageNo = wfEle.getAttribute("LastAccessStage");
		String stageName = wfEle.getAttribute("LastAccessStageName");
		String adminUsername = wfEle.getAttribute("LastAccessMember");
		String action = wfEle.getAttribute("LastAccessAction");
		LoginProcessManager log=new LoginProcessManager();
		
		if(userName.equalsIgnoreCase(adminUsername)) {
			//			return cancelFlag;

			
			Element stageN = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", currStageNo);
			//Element empEle = (Element) s
			Hashtable stageDetailsHT =log.retriveStageDetailsFromXML(workFlowN,currStageNo,"");
			Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
			Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
			String finalmsg=(String)messagesHT.get("Final");
			Hashtable curentPropertiesHT=(Hashtable)stageDetailsHT.get("PropertiesHT");
			String Properties=(String)curentPropertiesHT.get("Properties");
			cancelFlag = true;
			boolean stageFlag = stageNo.equalsIgnoreCase(currStageNo);
			for(int i=0;i<empNMHT.size();i++) {
				Hashtable empHT = (Hashtable) empNMHT.get(i);
				String member=(String)empHT.get("empName");
				String userStatus=(String)empHT.get("User_Status");
				if(stageFlag) {
					if(Properties.equalsIgnoreCase("Serial")) {
						boolean flag = member.equalsIgnoreCase(userName);
						if(flag) {
							Hashtable nextEmpHT = (Hashtable) empNMHT.get(i+1);
							if(nextEmpHT != null && !nextEmpHT.isEmpty()) {
								userStatus=(String)nextEmpHT.get("User_Status");
								String isWorkStarted = nextEmpHT.get("isWorkStarted") == null || String.valueOf(nextEmpHT.get("isWorkStarted")).isEmpty() ? "false" : String.valueOf(nextEmpHT.get("isWorkStarted"));
								cancelFlag = userStatus.equalsIgnoreCase(finalmsg) || isWorkStarted.equalsIgnoreCase("true") ? false : cancelFlag;
								break;
							}
						}
					}else {
						cancelFlag = false;
						break;
					}
				}else {
					if(Properties.equalsIgnoreCase("Serial")) {
						String isWorkStarted = empHT.get("isWorkStarted") == null || String.valueOf(empHT.get("isWorkStarted")).isEmpty() ? "false" : String.valueOf(empHT.get("isWorkStarted"));
						if(i==0) {
							cancelFlag = userStatus.equalsIgnoreCase(finalmsg) || isWorkStarted.equalsIgnoreCase("true") ? false : cancelFlag;
							break;
						}
					}else {
						String isWorkStarted = empHT.get("isWorkStarted") == null || String.valueOf(empHT.get("isWorkStarted")).isEmpty() ? "false" : String.valueOf(empHT.get("isWorkStarted"));
						if(userStatus.equalsIgnoreCase(finalmsg) || isWorkStarted.equalsIgnoreCase("true")) {
							cancelFlag = false;
							break;
						}
					}
				}
			}
			
			if(cancelFlag) {
				Hashtable mailDtailsHT=log.getMailDetailsFromConfig(docXmlDoc,workFlowN,currStageNo,userName, "Cancel", "Success", false , "");
				String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
				String mailPassword=(String)mailDtailsHT.get("Mail_Password");
				String mailMessage=(String)mailDtailsHT.get("Mail_Message");
				String bobyofMail=mailMessage;
				wfEle.setAttribute("Current_Stage_Name", stageName);
				wfEle.setAttribute("Current_Stage_No", stageNo);
				Element stageEle = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", stageNo);
				stageEle.setAttribute("Stage_Status", "WIP");
				Element empEle = (Element) stageEle.getElementsByTagName("Employee_Names").item(0);
				System.out.println(adminUsername+"-=-=-=-=-=-=-=-=-=-="+empEle.getNodeName()+"-=-=-=-=-=-=-=-=-="+empEle.getChildNodes().getLength());
				Element usrEle = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, empEle, "E-mail", adminUsername);
				System.out.println(adminUsername+"-=-=-=-=-=-=-=-=-=-="+usrEle.getNodeName());
				usrEle.setAttribute("User_Status", "YTS");
				Globals.writeXMLFile(docXmlDoc, hierLeveldir, docXmlFileName);
				Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currStageNo,"");

				Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
				String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
				subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
						subject : String.valueOf(mailDtailsHT.get("Subject"));
				String members = ""; 
				for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
					Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(i);
					members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
				}
				String toStage = (String)currentstageDetailsHT.get("Stage_Name");
				String dueDate = (String)currentstageDetailsHT.get("EffectiveEndDate");
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				int diffInDays = 0;
				if(dueDate != null && !dueDate.trim().isEmpty()) {
					Date due = sdf.parse(dueDate);
					long diff = new Date().getTime() - due.getTime();
					diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
				}else
					dueDate = "";
				System.out.println(dueDate+"-=-=-=-=-=-=-=-=-=-="+stageName);
				bobyofMail = bobyofMail.replace("$ToStage$", stageName).replace("$DueDate$", dueDate).replace("$CancelStage$", stageName);
				subject = subject.replace("$ToStage$", stageName).replace("$DueDate$", dueDate).replace("$CancelStage$", stageName);
				String[] mailIds = null;
				if(Properties.equalsIgnoreCase("Serial")) {
					if(currStageNo.equalsIgnoreCase(stageNo)) {
						boolean flag = false;
						ArrayList<String> tempAL = new ArrayList<>();
						for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
							Hashtable tempHT = (Hashtable)currentemplyeeDetailsHT.get(i);
							String empUsername = String.valueOf(tempHT.get("empName"));
							flag = !flag ? empUsername.equalsIgnoreCase(adminUsername) : flag;
							String activeFlag = tempHT.get("Active") == null || String.valueOf(tempHT.get("Active")).isEmpty() ? "true" : String.valueOf(tempHT.get("Active"));
							Hashtable<String, String> loginDetailsHT = log.getLoginDetails(empUsername, docEle.getAttribute("CustomerKey"));
							String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
							if(flag && !activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true")) {
								tempAL.add(String.valueOf(tempHT.get("empName")));
								break;
							}
						}
						tempAL.add(userName);
						mailIds = tempAL.toArray(new String[tempAL.size()]);
					}else {						
						Hashtable empHT = (Hashtable)currentemplyeeDetailsHT.get(0);
						String activeFlag = empHT.get("Active") == null || String.valueOf(empHT.get("Active")).isEmpty() ? "true" : String.valueOf(empHT.get("Active"));
						String empUsername = String.valueOf(empHT.get("empName"));
						Hashtable<String, String> loginDetailsHT = log.getLoginDetails(empUsername, docEle.getAttribute("CustomerKey"));
						String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
						if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true")) {
							ArrayList<String> tempAL = new ArrayList<>();
							
							for(int i=1;i<currentemplyeeDetailsHT.size();i++) {
								Hashtable tempHT = (Hashtable)currentemplyeeDetailsHT.get(i);
								activeFlag = tempHT.get("Active") == null || String.valueOf(tempHT.get("Active")).isEmpty() ? "true" : String.valueOf(tempHT.get("Active"));
								empUsername = String.valueOf(tempHT.get("empName"));
								loginDetailsHT = log.getLoginDetails(empUsername, docEle.getAttribute("CustomerKey"));
								disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
								if(!activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true")) {
									tempAL.add(String.valueOf(tempHT.get("empName")));
									break;
								}
							}
							tempAL.add(userName);
							mailIds = tempAL.toArray(new String[tempAL.size()]);
						}else {
							mailIds = new String[1+1];
							mailIds[0] = String.valueOf(empHT.get("empName"));
							mailIds[1] = userName;
						}
					}
					
				}else {
					if(currStageNo.equalsIgnoreCase(stageNo)) {
						ArrayList<String> tempAL = new ArrayList<>();
						for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
							Hashtable empHT = (Hashtable)currentemplyeeDetailsHT.get(i);
							String empUsername = String.valueOf(empHT.get("empName"));
							String activeFlag = empHT.get("Active") == null || String.valueOf(empHT.get("Active")).isEmpty() ? "true" : String.valueOf(empHT.get("Active"));
							Hashtable<String, String> loginDetailsHT = log.getLoginDetails(empUsername, docEle.getAttribute("CustomerKey"));
							String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
							if(!empUsername.equalsIgnoreCase(adminUsername) && !activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true")) {
								tempAL.add(String.valueOf(empHT.get("empName")));
							}
						}
						tempAL.add(userName);
						mailIds = tempAL.toArray(new String[tempAL.size()]);
					}else {
						ArrayList<String> tempAL = new ArrayList<>();
						for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
							Hashtable empHT = (Hashtable)currentemplyeeDetailsHT.get(i);
							String empUsername = String.valueOf(empHT.get("empName"));
							String activeFlag = empHT.get("Active") == null || String.valueOf(empHT.get("Active")).isEmpty() ? "true" : String.valueOf(empHT.get("Active"));
							Hashtable<String, String> loginDetailsHT = log.getLoginDetails(empUsername, docEle.getAttribute("CustomerKey"));
							String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");
							if(!activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true")) {
								tempAL.add(String.valueOf(empHT.get("empName")));
							}
						}
						tempAL.add(userName);
						mailIds = tempAL.toArray(new String[tempAL.size()]);
					}
					/*mailIds = new String[currentemplyeeDetailsHT.size()+1];
					for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
						Hashtable empHT = (Hashtable)currentemplyeeDetailsHT.get(i);
						mailIds[i] = String.valueOf(empHT.get("empName"));
					}
					mailIds[currentemplyeeDetailsHT.size()] = userName;*/
				}
				System.out.println(dueDate+"-=-=-=-=-=bobyofMail-=-=-=-=-="+bobyofMail);
				log.postMail(mailIds, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));


			}else {
				Hashtable mailDtailsHT=log.getMailDetailsFromConfig(docXmlDoc,workFlowN,currStageNo,userName, "Cancel", "Failed", false , "");
				String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
				String mailPassword=(String)mailDtailsHT.get("Mail_Password");
				String mailMessage=(String)mailDtailsHT.get("Mail_Message");
				String bobyofMail=mailMessage;
				Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currStageNo,"");

				Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
				String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
				subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
						subject : String.valueOf(mailDtailsHT.get("Subject"));
				String members = ""; 
				for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
					Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(i);
					members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
				}
				String toStage = (String)currentstageDetailsHT.get("Stage_Name");
				String dueDate = (String)currentstageDetailsHT.get("EffectiveEndDate");
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				int diffInDays = 0;
				if(dueDate != null && !dueDate.trim().isEmpty()) {
					Date due = sdf.parse(dueDate);
					long diff = new Date().getTime() - due.getTime();
					diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
				}else
					dueDate = "";
				bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members).replace("$CancelStage$", stageName);
				subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members).replace("$CancelStage$", stageName);
				String[] userMailAddress = {userName};//check Vishnu - for inactive user
				log.postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
			}
		}else {
			Hashtable mailDtailsHT=log.getMailDetailsFromConfig(docXmlDoc,workFlowN,currStageNo,userName, "Cancel", "Failed", false , "");
			String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			String bobyofMail=mailMessage;
			
			Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currStageNo,"");

			Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
			String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
			subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
					subject : String.valueOf(mailDtailsHT.get("Subject"));
			String members = ""; 
			for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
				Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(i);
				members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
			}
			String toStage = (String)currentstageDetailsHT.get("Stage_Name");
			String dueDate = (String)currentstageDetailsHT.get("EffectiveEndDate");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			int diffInDays = 0;
			if(dueDate != null && !dueDate.trim().isEmpty()) {
				Date due = sdf.parse(dueDate);
				long diff = new Date().getTime() - due.getTime();
				diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
			}else
				dueDate = "";
			bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members).replace("$CancelStage$", stageName);
			subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members).replace("$CancelStage$", stageName);
			String[] userMailAddress = {userName};//check Vishnu - for inactive user
			log.postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
		}
		currStageNo = stageNo;
		}finally {
			updateLastAccessDetails(wfID, docID, currStageNo, userName, "Cancel");
		}
		return cancelFlag;
	}
	
	public static void addHistory(Document docXmlDoc, String docID, String wfID, String actionType, String notes, String currStageNo, String userName,
			String documentPath,Node wfNd) throws Exception {
		LoginProcessManager  log=new LoginProcessManager();
		PropUtil prop = new PropUtil();
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		Element docEle = (Element)docNd;
		
		String documentname = String.valueOf(docEle.getAttribute("DocumentName"));
		String key = docEle.getAttribute("CustomerKey");
		System.out.println("documentname---"+documentname);
		
		Node workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", wfID);
		Element docEl1e = (Element)wfNd;
		Node wfDocXmlNd = null;
		if(workFlowN==null && wfNd!=null) {
			for (int i = 0; i < docNd.getChildNodes().getLength(); i++) {
				
				Node wrkNode=docNd.getChildNodes().item(i);
				if (wrkNode.getNodeType() == Node.ELEMENT_NODE && wrkNode.getNodeName().equals("Workflow")) {
					System.out.println("========Remove==&==Update====Wrkflw Node=== ");
					//docEle.removeChild(wrkNode);
					wfDocXmlNd = docXmlDoc.importNode(wfNd, true);
					docEle.replaceChild(wfDocXmlNd, wrkNode);
					workFlowN=wfDocXmlNd;
				}
			}

		}
		//docEle.appendChild(docEl1e);
		//workFlowN=wfNd;
		System.out.println(documentPath+":::::::::documentPath:::::::"+"workFlowID:: "+wfID+" -=-=-=-=-=-=workFlowN-=-=-="+workFlowN);
		Element workFlowEle = (Element) workFlowN;
		NodeList historyNdList = docEle.getElementsByTagName("History");
		Element historyEle = null;
		if(historyNdList == null || historyNdList.getLength()<=0) {
			historyEle = docXmlDoc.createElement("History");
			docEle.appendChild(historyEle);
		}else {
			historyEle = (Element)historyNdList.item(0);
		}
		
		
		Element actEle = docXmlDoc.createElement("Action");
		String	DateFormat=prop.getProperty("DATE_FORMAT");

		Date lastAccDate = new Date();
		Format formatter = new SimpleDateFormat(DateFormat);
		String accdate = formatter.format(lastAccDate);
		historyEle.appendChild(actEle);
		actEle.setAttribute("ActionType", actionType);
		actEle.setAttribute("Notes", notes);
		actEle.setAttribute("Date", accdate);
		actEle.setAttribute("FromStage", currStageNo);
		actEle.setAttribute("From", userName);
		actEle.setAttribute("ToStage", workFlowEle.getAttribute("Current_Stage_No"));
		actEle.setAttribute("DocumentPath", documentPath);
		String member = "";
		if(workFlowEle.getAttribute("Current_Stage_No").equalsIgnoreCase("Completed") || workFlowEle.getAttribute("Current_Stage_No").equalsIgnoreCase("Rules Failed") || workFlowEle.getAttribute("Current_Stage_No").equalsIgnoreCase("Cancel")) {
			member = "NA";
		}else {
			int totStage = Integer.valueOf(workFlowEle.getAttribute("Total_No_Stages"));
			int currStg = Integer.valueOf(workFlowEle.getAttribute("Current_Stage_No"));
			
			for(int i=currStg;i<=totStage;i++) {
				Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, String.valueOf(currStg),"");
				Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
				Hashtable curentPropertiesHT=(Hashtable)currentstageDetailsHT.get("PropertiesHT");
				String Properties=(String)curentPropertiesHT.get("Properties");
				Hashtable mssgeDetailsHT = (Hashtable) currentstageDetailsHT.get("MessagedetHT");
				String finalStatus = (String)mssgeDetailsHT.get("Final");
				if(Properties.equalsIgnoreCase("Serial")) {
					for(int j=0; j<currentemplyeeDetailsHT.size();j++) {
						Hashtable ApproversHT=new Hashtable();
						ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(j);
						
						String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
						Hashtable<String, String> loginDetailsHT = log.getLoginDetails(userName, key);
					
						String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(loginDetailsHT.get("Disable")).isEmpty() ? "false" : String.valueOf(loginDetailsHT.get("Disable"));
						System.out.println(userName+"-=-=-=-=-=-=-="+disableFlag);
						if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
							continue;
						String UserStatus=(String)ApproversHT.get("User_Status");
						if(!finalStatus.equalsIgnoreCase(UserStatus)) {
							member=(String)ApproversHT.get("empName");
							break;
						}
						
					}
				}else {
					for(int j=0; j<currentemplyeeDetailsHT.size();j++) {
						Hashtable ApproversHT=new Hashtable();
						ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(j);
						
						String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
						Hashtable<String, String> loginDetailsHT = log.getLoginDetails(userName, key);
						String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(loginDetailsHT.get("Disable")).isEmpty() ? "false" : String.valueOf(loginDetailsHT.get("Disable"));
						System.out.println(userName+"-=-=-=-=-=-=-="+disableFlag);
						if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
							continue;
						String UserStatus=(String)ApproversHT.get("User_Status");
						if(!finalStatus.equalsIgnoreCase(UserStatus)) {
							member=member.isEmpty() ? (String)ApproversHT.get("empName") : member+";"+(String)ApproversHT.get("empName");
//							break;
						}
						
					}
				}
				if(!member.isEmpty())
					break;
			}
		}
		
		actEle.setAttribute("To", member);
		
		 String fromstagename="";
		 String tostagename="";
		NodeList stageNdList = docEle.getElementsByTagName("Stage");
		 for (int temp = 0; temp < stageNdList.getLength(); temp++) {
		        Node nNode = stageNdList.item(temp);
		        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		            Element eElement = (Element) nNode;
		            System.out.println("Staff id : "+ eElement.getAttribute("Stage_No"));
		            if(currStageNo.equals(eElement.getAttribute("Stage_No")))
		            {
		            	fromstagename=eElement.getAttribute("Stage_Name");
		            	System.out.println("killyouman"+fromstagename);
		            }
		            if(workFlowEle.getAttribute("Current_Stage_No").equals(eElement.getAttribute("Stage_No")))
		            {
		            	tostagename=eElement.getAttribute("Stage_Name");
		            	System.out.println("secondkill"+fromstagename);
		            }
		        }
		        }
		 String workflowtype=workFlowEle.getAttribute("Workflow_Type");
		//actEle.setAttribute("To", member);
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		String to_stage=workFlowEle.getAttribute("Current_Stage_No");
		System.out.println("to stage===================="+to_stage);
		if(actionType.toLowerCase().equals("attach document"))
		{
			if(workflowtype.toLowerCase().equals("simple"))
			{
				addLog(docXmlDoc, docID, "Workflow Started", userName, accdate, notes,member,fromstagename);
			}
			else
			{
				addLog(docXmlDoc, docID, "Workflow Started", userName, accdate, notes,member+"~"+fromstagename+"~"+tostagename,fromstagename);
			}
			
		}
		else if(actionType.toLowerCase().equals("completed"))
		{
		addLog(docXmlDoc, docID, "Member Approved", userName, accdate, notes,member,fromstagename);
		}
		else if(actionType.toLowerCase().equals("resubmit"))
		{
		addLog(docXmlDoc, docID, "Document Resubmit", userName, accdate, notes,member,fromstagename);
		}
		else if(actionType.toLowerCase().equals("approved"))
		{
		addLog(docXmlDoc, docID, "Member Approved", userName, accdate, notes,member,fromstagename);
		}
		else if(actionType.toLowerCase().equals("rejected"))
		{
		addLog(docXmlDoc, docID, "Member Rejected", userName, accdate, notes,member,fromstagename);
		}
		else
		{
			if(actionType.toLowerCase().equals("workflowcancel"))
			{
				if(workflowtype.toLowerCase().equals("simple"))
				{
					addLog(docXmlDoc, docID, "Workflow Cancelled", userName, accdate, notes,member,fromstagename);
				}
				else
				{
					addLog(docXmlDoc, docID, "Workflow Cancelled", userName, accdate, notes,member+"~"+fromstagename+"~"+tostagename,fromstagename);
				}
			}
			else
			{
				if(workflowtype.toLowerCase().equals("simple"))
				{
					addLog(docXmlDoc, docID, actionType, userName, accdate, notes,member,fromstagename);
				}
				else
				{
					addLog(docXmlDoc, docID, actionType, userName, accdate, notes,member+"~"+fromstagename+"~"+tostagename,fromstagename);
				}
			}
			

		}
		 if(to_stage.toLowerCase().equals("completed"))
		{

			if(workflowtype.toLowerCase().equals("simple"))
			{
				//addLog(docXmlDoc, docID, actionType, userName, accdate, notes,member);
				addLog(docXmlDoc, docID, "Workflow Completed", userName, accdate, notes,member,fromstagename);
			}
			else
			{
				//addLog(docXmlDoc, docID, actionType, userName, accdate, notes,member);
				addLog(docXmlDoc, docID, "Workflow Completed", userName, accdate, notes,member+"~"+fromstagename+"~"+tostagename,fromstagename);
			}
		}
		System.out.println("==== completed");
	//	Globals.writeXMLFile(docXmlDoc, hierDir, docXmlFileName);
		System.out.println("sudesh222");
	}
	
	/**
	 * @param docID
	 * @param wfID
	 * @param username
	 * @param actionName
	 * @throws Exception
	 */
	/**
	 * @param docID
	 * @param wfID
	 * @param username
	 * @param actionName
	 * @throws Exception
	 */
	public static void remindMembers(String docID, String wfID, String username, String actionName) throws Exception {
		LoginProcessManager  log=new LoginProcessManager();
		PropUtil prop = new PropUtil();
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		Element docEle = (Element)docNd;
		String documentName = docEle.getAttribute("DocumentName");
		Node workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", wfID);
		Element workFlowEle = (Element) workFlowN;
		String currentStgNo = workFlowEle.getAttribute("Current_Stage_No");
		String hierarchyName = Globals.getAttrVal4AttrName(docNd, "WorkflowName");
		String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
		/*Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currentStgNo,"");
		Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
		for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
			Hashtable ApproversHT=new Hashtable();
			ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
			String emailID = (String) ApproversHT.get("empName");
			
		}*/
		Hashtable mailDtailsHT=log.getMailDetailsFromConfig(docXmlDoc,workFlowN,currentStgNo,username, actionName, "Next", false , "");
		String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
		String mailPassword=(String)mailDtailsHT.get("Mail_Password");
		String mailMessage=(String)mailDtailsHT.get("Mail_Message");
		String bobyofMail=mailMessage;
		
		Hashtable currentstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currentStgNo,"");
		
		Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
		if(actionName.equalsIgnoreCase("Escalate"))
			currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EscalateManagersHT");
		subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
				subject : String.valueOf(mailDtailsHT.get("Subject"));
		String members = ""; 
		for(int i=0;i<currentemplyeeDetailsHT.size();i++) {
			Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(i);
			members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
		}
		String toStage = (String)currentstageDetailsHT.get("Stage_Name");
		String dueDate = (String)currentstageDetailsHT.get("EffectiveEndDate");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		int diffInDays = 0;
		if(dueDate != null && !dueDate.trim().isEmpty()) {
			Date due = sdf.parse(dueDate);
			long diff = new Date().getTime() - due.getTime();
			diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
		}else
			dueDate = "";
		bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
		subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
		if(currentstageDetailsHT!=null&&currentstageDetailsHT.size()!=0)
		{
			log.sendmailFromAdmin(currentemplyeeDetailsHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
		}
		
		if(actionName.equalsIgnoreCase("Remind")) {
			mailDtailsHT=log.getMailDetailsFromConfig(docXmlDoc,workFlowN,currentStgNo,username, actionName, "Initiator", false , "");
			mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			mailPassword=(String)mailDtailsHT.get("Mail_Password");
			mailMessage=(String)mailDtailsHT.get("Mail_Message");
			subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
					subject : String.valueOf(mailDtailsHT.get("Subject"));
			bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
			subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
			String[] userMailAddress = {username};//check Vishnu - for inactive user
			log.postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
		}
	}
	
	public static String getFinalMsg(Node workFlowN,String userStatus){

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		String ishasUserStatus="false";
		try{
			LoginProcessManager  log=new LoginProcessManager();
			System.out.println("User Status  : "+userStatus);
			if(workFlowN!=null){
				Element workFlowNEL=(Element)workFlowN;
				String totstage=workFlowNEL.getAttribute("Total_No_Stages");;
				int tot=Integer.parseInt(totstage);
				int stage=0;
				for(int k=0; k<tot; k++){
					stage=k+1;

					Hashtable stageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, String.valueOf(stage),"");
					Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
					String finalMsg=(String)mssgeDetailsHT.get("Final");

					System.out.println("User Status  : "+userStatus +" finalMsg "+finalMsg+" stage "+stage);
					if(userStatus.equals(finalMsg)){
						ishasUserStatus="true";

						return ishasUserStatus;
					}						

				}




			}




		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return ishasUserStatus;

	}
	
	
	
	public static void updateLastCancelAction(String hierarchyID, String docID, String stageNo, String adminUsername, String action) throws Exception {
		
		
				
		System.out.println("hierarchyID :"+hierarchyID);
		System.out.println("docID :"+docID);
		System.out.println("stageNo :"+stageNo);
		System.out.println("adminUsername :"+adminUsername);
		System.out.println("action :"+action);
		
		PropUtil prop = new PropUtil();
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);

		Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		String documentName = Globals.getAttrVal4AttrName(docNd, "DocumentName");
		Element workFlowN = (Element)Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", hierarchyID);
		LoginProcessManager lgn=new LoginProcessManager();
		Element stageELe = (Element)Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", stageNo);
		String stageName = stageELe.getAttribute("Stage_Name");
		workFlowN.setAttribute("LastAccessStage", stageNo);
		workFlowN.setAttribute("LastAccessStageName", stageName);
		workFlowN.setAttribute("LastAccessMember", adminUsername);
		workFlowN.setAttribute("LastAccessAction", action);
		workFlowN.setAttribute("Current_Stage_No", action);
		Globals.writeXMLFile(docXmlDoc, hierLeveldir, docXmlFileName);
	}
	
	public static void updateLastAccessDetails(String hierarchyID, String docID, String stageNo, String adminUsername, String action) throws Exception {
		PropUtil prop = new PropUtil();
		//			String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);

		Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		String documentName = Globals.getAttrVal4AttrName(docNd, "DocumentName");
		Element workFlowN = (Element)Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", hierarchyID);
		LoginProcessManager lgn=new LoginProcessManager();
		Element stageELe = (Element)Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", stageNo);
		String stageName = stageELe.getAttribute("Stage_Name");
		workFlowN.setAttribute("LastAccessStage", stageNo);
		workFlowN.setAttribute("LastAccessStageName", stageName);
		workFlowN.setAttribute("LastAccessMember", adminUsername);
		workFlowN.setAttribute("LastAccessAction", action);
		Globals.writeXMLFile(docXmlDoc, hierLeveldir, docXmlFileName);
	}
	
	public static void updateLastAccessDetails(String hierarchyID, String docID, String stageNo, String adminUsername, String action, Document docXmlDoc) throws Exception {
		PropUtil prop = new PropUtil();
		//			String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");

		Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		String documentName = Globals.getAttrVal4AttrName(docNd, "DocumentName");
		Element workFlowN = (Element)Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", hierarchyID);
		LoginProcessManager lgn=new LoginProcessManager();
		Element stageELe = (Element)Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", stageNo);
		String stageName = stageELe.getAttribute("Stage_Name");
		workFlowN.setAttribute("LastAccessStage", stageNo);
		workFlowN.setAttribute("LastAccessStageName", stageName);
		workFlowN.setAttribute("LastAccessMember", adminUsername);
		workFlowN.setAttribute("LastAccessAction", action);
	}
	
	public static Hashtable setStatusMsg4EMP(String hierarchyID,String docID, String hierarchyName, String stageNo, String empID, String reasonText,String statusMsg,
			String accessUniqIDcmnFrmLogin,Boolean cmngFromLogin, String adminUsername) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."

					+ new Exception().getStackTrace()[0].getMethodName());

		Hashtable NextProcessHT=new Hashtable<>();

		boolean issendback=false;
		

		PropUtil prop = new PropUtil();
		//			String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
		String currentUserName="";

		Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		String documentName = Globals.getAttrVal4AttrName(docNd, "DocumentName");
		Node workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", hierarchyID);
		LoginProcessManager lgn=new LoginProcessManager();
		if(!cmngFromLogin){
			updateLastAccessDetails(hierarchyID, docID, stageNo, adminUsername, statusMsg, docXmlDoc);
			Node stageN = Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", stageNo);
			NodeList msgEnamePropNL = stageN.getChildNodes();
			for (int i = 0; i < msgEnamePropNL.getLength(); i++) {
				Node checkN = msgEnamePropNL.item(i);
				if (checkN.getNodeType() == Node.ELEMENT_NODE) {
					if (checkN.getNodeName().equalsIgnoreCase("Employee_Names")) {

						Node nameN = Globals.getNodeByAttrValUnderParent(docXmlDoc, checkN, "E-mail", adminUsername);
						if (nameN != null) {

							String	DateFormat=prop.getProperty("DATE_FORMAT");

							Date lastAccDate = new Date();
							Format formatter = new SimpleDateFormat(DateFormat);
							String accdate = formatter.format(lastAccDate);

							Element nameE = (Element) nameN;
							nameE.setAttribute("User_Status", statusMsg);
							nameE.setAttribute("Last_Access", accdate);
							nameE.setAttribute("Notes", reasonText);
							currentUserName=nameN.getTextContent();

							Element stageNELL=(Element)stageN;
							stageNELL.setAttribute("Stage_Status", "WIP");

							String isstatusAvaible=getFinalMsg(workFlowN,statusMsg);



							// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
							if(statusMsg.equals("Rejected")){

								issendback=true;


								///////////////////////////////

								Hashtable stageDetailsHT =lgn.retriveStageDetailsFromXML(workFlowN,stageNo,"");
								Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
								Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
								Hashtable propHT =(Hashtable)stageDetailsHT.get("PropertiesHT");
								System.out.println("empNMHT "+empNMHT);
								int addOnePerson=1;
								String triggerRejected=LoginProcessManager.IsRejectionProceed(empNMHT,propHT,addOnePerson);
								if(triggerRejected.equals("proceed")){

									lgn.doRejectionConfiguration(docXmlDoc, workFlowN, hierarchyID, stageNo, currentUserName, stageDetailsHT, messagesHT, empNMHT, propHT, empID, hierarchyName);
									//								Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);
								}


							}
							else if(statusMsg.equals("Resubmit")){

								issendback=true;

								Hashtable stageDetailsHT =lgn.retriveStageDetailsFromXML(workFlowN,stageNo,"");
								Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
								Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
								Hashtable propHT =(Hashtable)stageDetailsHT.get("PropertiesHT");
								
								lgn.doResubmitConfiguration(docXmlDoc, workFlowN, hierarchyID, stageNo, currentUserName, stageDetailsHT, messagesHT, empNMHT, propHT, empID, hierarchyName);
								



							}

							else if(isstatusAvaible.equals("true")){


								// code change pandian 28Mar2013

								//							send mail to next stage Reviewer
								//..
								/*String mailTo = statusMsg.equalsIgnoreCase("Initiate") ? "Initiator" : "Next";
								Hashtable mailDtailsHT=lgn.getMailDetailsFromConfig(docXmlDoc,workFlowN,stageNo,adminUsername, statusMsg, mailTo, false , "");
								String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
								String mailPassword=(String)mailDtailsHT.get("Mail_Password");
								String mailMessage=(String)mailDtailsHT.get("Mail_Message");
								String bobyofMail=mailMessage;
								//							String bobyofMail="Please Login to the Heiarchy Genarator to View the Progress.This is an Auto Genarated Email Don't reply to this Mail-ID";
								String subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
										"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
								Hashtable currentstageDetailsHT=lgn.retriveStageDetailsFromXML(workFlowN, String.valueOf(stageNo),"");
								Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
								Hashtable curentPropertiesHT=(Hashtable)currentstageDetailsHT.get("PropertiesHT");
								String Properties=(String)curentPropertiesHT.get("Properties");
								String externalUserFlag = currentstageDetailsHT.get("ExternalUser") == null ? "" : currentstageDetailsHT.get("ExternalUser").toString();
								String autoLoginFlag = currentstageDetailsHT.get("Auto_Login") == null ? "" : currentstageDetailsHT.get("Auto_Login").toString();
								if(Properties.equals("Parallel") && !externalUserFlag.equalsIgnoreCase("true")){  // send all the person
									String members = ""; 
									for(int t=0;t<currentemplyeeDetailsHT.size();t++) {
										Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(t);
										members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
									}
									String toStage = (String)currentstageDetailsHT.get("Stage_Name");
									String dueDate = (String)currentstageDetailsHT.get("EffectiveEndDate");
									SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
									int diffInDays = 0;
									if(dueDate != null && !dueDate.trim().isEmpty()) {
										Date due = sdf.parse(dueDate);
										long diff = new Date().getTime() - due.getTime();
										diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
									}else
										dueDate = "";
									bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
									subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
									String userMailAddress[] = new String[currentemplyeeDetailsHT.size()];
									int mailsend=0;
									Hashtable EmailHT=new Hashtable<>();
									String updateMail2DB = "";
									for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
										Hashtable mailApproversHT=new Hashtable();
										mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
										String usermailID=(String)mailApproversHT.get("E-mail");
										String username=(String)mailApproversHT.get("empName");
										String AccessUNiqID=(String)mailApproversHT.get("Access_Unique_ID");
										String UserStatus=(String)mailApproversHT.get("User_Status");
										String isstatusAvaiblee=getFinalMsg(workFlowN,UserStatus);
										String activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
										Hashtable<String, String> loginDetailsHT = lgn.getLoginDetails(username);
										String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
										if(AccessUNiqID.equals(empID) || isstatusAvaiblee.equals("true") || activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true")){


										}else{
											EmailHT.put(mailsend, usermailID);										
											mailsend++;

										}

									}

									userMailAddress = new String[EmailHT.size()];
									for (int mail = 0; mail < EmailHT.size(); mail++) {
										String usermailID=(String)EmailHT.get(mail);
										userMailAddress[mail]=usermailID;
										if(mail == 0){
											updateMail2DB = usermailID;
										}else{
											updateMail2DB = usermailID+","+updateMail2DB;
										}
									}
									if(userMailAddress.length>0){
										lgn.postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail);

									}	
									if(statusMsg.equalsIgnoreCase("Initiate")) {

										mailDtailsHT=lgn.getMailDetailsFromConfig(docXmlDoc,workFlowN,stageNo,adminUsername, statusMsg, "Next", false , "");
										mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
										mailPassword=(String)mailDtailsHT.get("Mail_Password");
										mailMessage=(String)mailDtailsHT.get("Mail_Message");
										bobyofMail=mailMessage;
										String[] mailList = {empID};	//check Vishnu - for inactive user
										subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
												"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
										bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
										subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
										lgn.postMail(mailList, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail);
									
									}
								}*/

								//							else{ // send only one person to next stage who is available on first (Serial)
								//								
								//								int mailsend=0;
								//								Hashtable EmailHT=new Hashtable<>();
								//								for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
								//									Hashtable mailApproversHT=new Hashtable();
								//									mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
								//									String usermailID=(String)mailApproversHT.get("E-mail");	
								//									String AccessUNiqID=(String)mailApproversHT.get("Access_Unique_ID");
								//									String UserStatus=(String)mailApproversHT.get("User_Status");
								//									if(AccessUNiqID.equals(empID) || UserStatus.equals("Completed") || UserStatus.equals("Approved")){
								//										
								//									}else{
								//										EmailHT.put(mailsend, usermailID);										
								//										mailsend++;
								//										break;
								//									}
								//									
								//								}
								//								
								//								 String userMailAddress[] = new String[EmailHT.size()];
								//								for (int mail = 0; mail < EmailHT.size(); mail++) {
								//									String usermailID=(String)EmailHT.get(mail);
								//									userMailAddress[mail]=usermailID;
								//								}
								//							
								//								
								//							
								//									
								//								postMail(userMailAddress, subject, bobyofMail, "accelbi@cygnussoftwares.com","acbi$2014", null, hierlink4Mail);
								//								
								//								
								//							}

							}

							System.out.println("update Last Accessed Date : " + accdate);
							Globals.writeXMLFile(docXmlDoc, hierLeveldir, docXmlFileName);


						} else{
							NextProcessHT.put("ERROR", "Employee is not available in This Stage");
							return NextProcessHT;
						}
					}

				}

			}
		}

		if(!issendback){
			docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
			docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
			workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", hierarchyID);
			NextProcessHT=lgn.workFlowNCheckAccessUser(docXmlDoc, workFlowN, hierarchyID,hierarchyName,stageNo,cmngFromLogin,accessUniqIDcmnFrmLogin,hierLeveldir, docXmlFileName, adminUsername, statusMsg);
			System.out.println("current Stage Processing  HT :"+NextProcessHT);
		}








		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return NextProcessHT;

	}
	
	public static ArrayList<String> getExternalApiDetailsFromXML(String documentID) throws Exception {
		PropUtil prop = new PropUtil();
//		String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
//		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentID);
		if(docNd == null)
			return null;
		Element docEle = (Element) docNd;
		Element workflowEle = (Element)docEle.getElementsByTagName("Workflow").item(0);
		String username = workflowEle.getAttribute("ExternalStorageDetails");
		String documentName = docEle.getAttribute("DocumentName");
		ArrayList<String> tempAL = new ArrayList<>();
		tempAL.add(documentName);
		tempAL.add(username);
		return tempAL;
	}
	
	public static void updateWorkingUserInXML(String documentID, String downloadUsername, Boolean isDocLocked) throws Exception {
		PropUtil prop = new PropUtil();
//		String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
//		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentID);
		System.out.println(docNd+"-=-=-=-=-documentID=-=-=-="+documentID+"-=-=-=-=isDocLocked-=-=-=-="+isDocLocked);
		if(docNd == null)
			return;
		Element docEle = (Element) docNd;
		docEle.setAttribute("DocumentLocked", String.valueOf(isDocLocked));
		docEle.setAttribute("DocumentLockedBy", downloadUsername);
		Globals.writeXMLFile(docXmlDoc, hierLeveldir, docXmlFileName);
	}
	
	public static Hashtable<String, String> checkDocumentLocked(String documentID) throws Exception {
		PropUtil prop = new PropUtil();
//		String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
//		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentID);
		Hashtable<String, String> detailsHT = new Hashtable<>();
		detailsHT.put("DocumentLocked", "false");
		detailsHT.put("DocumentLockedBy", "");
		System.out.println(documentID+"-=-=-=-docNd=-=-=-="+docNd);
		if(docNd == null)
			return detailsHT;
		Element docEle = (Element) docNd;
		detailsHT.put("DocumentLocked", docEle.getAttribute("DocumentLocked") == null || 
				docEle.getAttribute("DocumentLocked").isEmpty() ? "false" : docEle.getAttribute("DocumentLocked"));
		detailsHT.put("DocumentLockedBy", docEle.getAttribute("DocumentLockedBy") == null || 
				docEle.getAttribute("DocumentLockedBy").isEmpty() ? "" : docEle.getAttribute("DocumentLockedBy"));
		System.out.println("-=-=-=-detailsHT=-=-=-="+detailsHT);
		return detailsHT;
	}
	
	public static String createDocumentVersionForWFFromXML(String documentName, String documentId) throws Exception {
		PropUtil prop = new PropUtil();
//		String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
//		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		String docVersionDir = prop.getProperty("DOCUMENTVERSION_DIR");
		Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
		if(docNd == null)
			return "";
		Element docEle = (Element) docNd;
		String docId = docEle.getAttribute("Document_ID");
		String storagePlace = docEle.getAttribute("Storage");
		Element workflowEle = (Element)docEle.getElementsByTagName("Workflow").item(0);
		String folderName = documentName.substring(0, documentName.lastIndexOf("."))+"_"+docId;
		if(storagePlace != null && !storagePlace.trim().isEmpty() && storagePlace.equalsIgnoreCase("External")) {
			String folderID = workflowEle.getAttribute("FolderID");
			String username = workflowEle.getAttribute("ExternalStorageDetails");
			Drive drive = GoogleStorageMaintenace.getGoogleDriveObj(username);
			String docFolderID = GoogleStorageMaintenace.getFileIDByName(drive, folderName, "application/vnd.google-apps.folder");
			if(docFolderID == null || docFolderID.isEmpty())
				docFolderID = GoogleStorageMaintenace.createFolder(drive, folderID, folderName);
			Hashtable<String, String> folderNamesHT = GoogleStorageMaintenace.getFilesUnderFolderById(drive, docFolderID);
			Iterator<Entry<String, String>> it = folderNamesHT.entrySet().iterator();
			ArrayList<String> foldersAL = new ArrayList<>();
			ArrayList<String> foldersIDAL = new ArrayList<>();
			while (it.hasNext()) {
				Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) it
						.next();
				if(Globals.checkStringIsNumber(entry.getValue())) {
					foldersAL.add(entry.getValue());
					foldersAL.add(entry.getKey());
				}
			}
			int[] folderNames = new int[foldersAL.size()];
			int i=0;
			for(String val : foldersAL) {
				int j = 0;
				try {
					j = Integer.valueOf(val.substring(val.lastIndexOf("\\")+1));
				}catch (NumberFormatException e) {
					// TODO: handle exception
					j = 0;
				}
				folderNames[i] = j;
				i++;
			}
			int maxName = 1;
			if(folderNames.length > 0) {
				List<Integer> b = Arrays.asList(ArrayUtils.toObject(folderNames));
				maxName = Collections.max(b);
			}
//			int index = b.indexOf(maxName);
			String fileID = GoogleStorageMaintenace.createFolder(drive, docFolderID, String.valueOf(folderNames.length <= 0 ? 1 : maxName+1));
//			String 
			return fileID+"###"+"External";
		}else {
			String filePath = docVersionDir+folderName;
			File file = new File(filePath);
			if(!file.exists()) {
				file.mkdir();
			}else {

			}
			String[] filePaths = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			});
			int[] folderNames = new int[filePaths.length];
			int i=0;
			for(String val : filePaths) {
				int j = 0;
				try {
					j = Integer.valueOf(val.substring(val.lastIndexOf("\\")+1));
				}catch (NumberFormatException e) {
					// TODO: handle exception
					j = 0;
				}
				folderNames[i] = j;
				i++;
			}
			List<Integer> b = Arrays.asList(ArrayUtils.toObject(folderNames));
			int maxName = Collections.max(b);
			String path = filePaths.length <= 0 ? "1" : String.valueOf(maxName+1);
			System.out.println("-=-=-=-=-=-=>>>>"+filePath+File.separator+path);
			file = new File(filePath+File.separator+path);
			file.mkdir();
			return filePath+File.separator+path+"###"+"Server";
		}
		
	}
	
	public static String createDocumentVersionForWF(String documentName, String docId, String storagePlace, Element wfEle,String primaryFileConn) throws Exception {
		System.out.println("--------Primary_FileConn-------"+primaryFileConn);
		if(primaryFileConn.equals("Smartsheet")) {
			
			System.out.println("--------storagePlace-------"+storagePlace);
			
			String folderName = documentName+"_"+docId;
			if(storagePlace != null && !storagePlace.trim().isEmpty() && storagePlace.equalsIgnoreCase("External")) {
				String folderID = wfEle.getAttribute("FolderID");
				String username = wfEle.getAttribute("ExternalStorageDetails");
				Drive drive = GoogleStorageMaintenace.getGoogleDriveObj(username);
				String docFolderID = GoogleStorageMaintenace.getFileIDByName(drive, folderName, "application/vnd.google-apps.folder");
				if(docFolderID == null || docFolderID.isEmpty())
					docFolderID = GoogleStorageMaintenace.createFolder(drive, folderID, folderName);
				return docFolderID+"###"+"External";
			}else {
				PropUtil prop = new PropUtil();
				String docVersionDir = prop.getProperty("DOCUMENTVERSION_DIR");
				String filePath = docVersionDir+folderName;
				File file = new File(filePath);
				if(!file.exists()) {
					file.mkdir();
				}
				return filePath+"###"+"Server";
			}
			
		}
		System.out.println("documentName==>"+documentName);
		String folderName = documentName.substring(0, documentName.lastIndexOf("."))+"_"+docId;
		System.out.println(storagePlace+"^^^^^^^^^^^^^^^^folderName^^^^^^^^^^^^^^^^"+folderName);
		if(storagePlace != null && !storagePlace.trim().isEmpty() && storagePlace.equalsIgnoreCase("External")) {
			String folderID = wfEle.getAttribute("FolderID");
			String username = wfEle.getAttribute("ExternalStorageDetails");
			Drive drive = GoogleStorageMaintenace.getGoogleDriveObj(username);
			String docFolderID = GoogleStorageMaintenace.getFileIDByName(drive, folderName, "application/vnd.google-apps.folder");
			if(docFolderID == null || docFolderID.isEmpty())
				docFolderID = GoogleStorageMaintenace.createFolder(drive, folderID, folderName);
			Hashtable<String, String> folderNamesHT = GoogleStorageMaintenace.getFilesUnderFolderById(drive, docFolderID);
			Iterator<Entry<String, String>> it = folderNamesHT.entrySet().iterator();
			ArrayList<String> foldersAL = new ArrayList<>();
			ArrayList<String> foldersIDAL = new ArrayList<>();
			while (it.hasNext()) {
				Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) it
						.next();
				if(Globals.checkStringIsNumber(entry.getValue())) {
					foldersAL.add(entry.getValue());
					foldersIDAL.add(entry.getKey());
				}
			}
			int[] folderNames = new int[foldersAL.size()];
			int i=0;
			for(String val : foldersAL) {
				int j = 0;
				try {
					j = Integer.valueOf(val.substring(val.lastIndexOf("\\")+1));
				}catch (NumberFormatException e) {
					// TODO: handle exception
					j = 0;
				}
				folderNames[i] = j;
				i++;
			}
			int maxName = 1;
			if(folderNames.length > 0) {
				List<Integer> b = Arrays.asList(ArrayUtils.toObject(folderNames));
				maxName = Collections.max(b);
			}
//			int index = b.indexOf(maxName);
			
			String fileID = GoogleStorageMaintenace.createFolder(drive, docFolderID, String.valueOf(folderNames.length <= 0 ? 1 :maxName+1));
//			String 
			return fileID+"###"+"External";
		}else {
			PropUtil prop = new PropUtil();
			String docVersionDir = prop.getProperty("DOCUMENTVERSION_DIR");
			String filePath = docVersionDir+folderName;
			File file = new File(filePath);
			if(!file.exists()) {
				file.mkdir();
			}else {

			}
			String[] filePaths = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			});
			int[] folderNames = new int[filePaths.length];
			int i=0;
			for(String val : filePaths) {
				int j = 0;
				try {
					j = Integer.valueOf(val.substring(val.lastIndexOf("\\")+1));
				}catch (NumberFormatException e) {
					// TODO: handle exception
					j = 0;
				}
				folderNames[i] = j;
				i++;
			}
			int maxName = 0;
			if(filePaths.length > 0) {
				List<Integer> b = Arrays.asList(ArrayUtils.toObject(folderNames));
				maxName = Collections.max(b);
			}
			String path = filePaths.length <= 0 ? "1" : String.valueOf(maxName+1);
			System.out.println("-=-=-=-=-=-=>>>>"+filePath+File.separator+path);
			file = new File(filePath+File.separator+path);
			file.mkdir();
			return filePath+File.separator+path+"###"+"Server";
		}
		
	}
	
	private static Hashtable getStageAttributes(Node workflowNode, String from_Stage) {
		// TODO Auto-generated method stub
		Hashtable stgName=new Hashtable<>();
		try {
			NodeList workflowNodelist=workflowNode.getChildNodes();
			for(int i=0;i<workflowNodelist.getLength();i++) {
				if(workflowNodelist.item(i).getNodeType()==Node.ELEMENT_NODE && workflowNodelist.item(1).getNodeName().equals("Stage") ) {
					Element stageEle = (Element) workflowNodelist.item(i);
					String stagenam=stageEle.getAttribute("Stage_Name");
					String stagenum=stageEle.getAttribute("Stage_No");
					System.out.println(stagenum+"=------------ht2---------- "+from_Stage);
					if((!stagenam.equals("Admin")||!stagenam.equals("public"))&&stagenum.equals(from_Stage)) {
						Hashtable attrHT = Globals.getAttributeNameandValHT(workflowNodelist.item(i));
						System.out.println("=------------ht2---------- "+attrHT);
						stgName=attrHT;
						break;
					}
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return stgName;
	}
	
	public static void getHistoryDetails(Hashtable docHistryDetails,String docId) {
		// TODO Auto-generated method stub
		try {
			PropUtil prop = new PropUtil();
			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
			Node docND = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
			Element docEle = (Element) docND;
			docHistryDetails.put("DocumentName", docEle.getAttribute("DocumentName"));
			docHistryDetails.put("Document_ID", docId);
			Hashtable htstageNames= getStageNames(docND);
			Node wrk_Nd = docEle.getElementsByTagName("Workflow").item(0);
			NodeList wrk_List = wrk_Nd.getChildNodes();
			Node histNd = docEle.getElementsByTagName("History").item(0);
			NodeList hisrtList = histNd.getChildNodes();
			int len = 0; 
			for(int i=0;i<hisrtList.getLength();i++) {
				if(hisrtList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Hashtable ht = Globals.getAttributeNameandValHT(hisrtList.item(i));
					String fromSatge=(String)ht.get("FromStage");
					
					for (int j = 0; j < wrk_List.getLength(); j++) {
						if(wrk_List.item(j).getNodeType() == Node.ELEMENT_NODE && wrk_List.item(j).getNodeName().equals("Stage")) {
							Hashtable ht2=getStageAttributes(wrk_Nd, fromSatge);
							
							String startDate= ht2 == null || ht2.get("EffectiveStarDate") == null ? "" : (String)ht2.get("EffectiveStarDate");
							String endtDate= ht2 == null || ht2.get("EffectiveEndDate") == null ? "" : (String)ht2.get("EffectiveEndDate");
							System.out.println(j+" --------- "+startDate+" ---------- "+endtDate);
							ht.put("Planed_Start_Date", startDate);
							ht.put("Planed_End_Date", endtDate);
						}
					}
					
					
					String key="R"+len;
					docHistryDetails.put(key, ht);
					len++;
				}
			}
			docHistryDetails.put("TableLength", String.valueOf(docHistryDetails.size()));
			docHistryDetails.put("Get_Stages", htstageNames);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	private static Hashtable getStageNames(Node docND) {
		// TODO Auto-generated method stub
		Hashtable stageHT=new Hashtable<>();
		try {
			NodeList nList=docND.getChildNodes();
			for(int i=0;i<nList.getLength();i++) {
				Node nds=nList.item(i);
				System.out.println(" nds Name : "+nds.getNodeName());
				NodeList nList2=nds.getChildNodes();
				int len = 0; 
				for(int j=0;j<nList2.getLength();j++) {
					Node nds2=nList2.item(j);
					//System.out.println(" nds2 Name : "+nds2.getNodeName());
					
					if(nds2.getNodeType()==Node.ELEMENT_NODE && nds2.getNodeName().equals("Stage")) {
						System.out.println(" nds2 Name : "+nds2.getNodeName());
						Hashtable ht = Globals.getAttributeNameandValHT(nds2);
						String key="Stage"+len;
						stageHT.put(key, ht);
						len++;
					}
				}
				
			}
			stageHT.put("NoOfSatages", String.valueOf(stageHT.size()));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	return stageHT;	
	}
	
	public static boolean checkUserisAvailable(String username) throws Exception {
		boolean flag = false;
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String loginXMLfile=prop.getProperty("LOGIN_XML_FILE");
		Document doc1=Globals.openXMLFile(HIERARCHY_XML_DIR, loginXMLfile);
		Node nod=Globals.getNodeByAttrVal(doc1, "User", "Login_ID", username);
		if(nod == null)
			return flag;
		return true;
	}
	public static String changeUserPassword(String username, String password) throws Exception {
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String loginXMLfile=prop.getProperty("LOGIN_XML_FILE");
		Document doc1=Globals.openXMLFile(HIERARCHY_XML_DIR, loginXMLfile);
		Node nod=Globals.getNodeByAttrVal(doc1, "User", "Login_ID", username);
		if(nod == null)
			return "false";
		Element ele = (Element) nod;
		password = Inventory.store(password);
		ele.setAttribute("User_Password", password);
		Globals.writeXMLFile(doc1, HIERARCHY_XML_DIR, loginXMLfile);
		return "true";
		
	}
	public static String changePasswordMail(String userName) {
		try {
			String subject = "Change of Password";
			String mailStr = "<table><tr><td>Hi,</td></tr></table>\r\n" + 
					"<table><tr><td width=\"60px\">&nbsp;&nbsp;</td><td>You has requested password change, the new password for your account  is '$Password$'. You can change password in Home->Tools->Change Password.</td></tr></table>";
			Random rd = new Random();
			RandomString rs = new RandomString(8, rd);
			String newPassword = rs.nextString();
			mailStr = mailStr.replace("$Password$", newPassword);
			LoginProcessManager log = new LoginProcessManager();
			
			PropUtil prop = new PropUtil();
			String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String timeFormat = prop.getProperty("DATE_FORMAT");
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
			String mailSendBy="";
			String mailPassword="";
			Document configdoc=Globals.openXMLFile(hierarchyDIR, configFileName);
			NodeList ndlist=configdoc.getElementsByTagName("Mail_Configuration");
			Node nd=ndlist.item(0);
			NodeList ndlist1=nd.getChildNodes();
			String oldMessage = "";
			for(int i=0;i<ndlist1.getLength();i++){
				Node nd1=ndlist1.item(i);
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_ID_Send_by")){
					mailSendBy=nd1.getTextContent();
					//mailDtailsHT.put("Mail_ID_Send_by", mailSendBy);

				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Password")){
					mailPassword=nd1.getTextContent();
					//mailDtailsHT.put("Mail_Password", mailPassword);
				}
				
			}
			String[] recipients = new String[1];
			recipients[0] = userName;
			log.postMail(recipients, subject, mailStr, mailSendBy, mailPassword, null, hierlink4Mail, "");
			
			return changeUserPassword(userName, newPassword);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "false";
		}
	}
	
	public static Hashtable getWorkflowStatusDetails(String documentID) throws Exception {
		String len="";
		String currStageNo="";
		PropUtil prop=new PropUtil();
		String hierDir="";

		Hashtable attrHT=new Hashtable<>();

		String totalStageNo="";
		hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document doc=Globals.openXMLFile(hierDir, docXmlFileName);
		Node docNd=Globals.getNodeByAttrVal(doc, "Document", "Document_ID", documentID);
		Hashtable docDeatilsHT=Globals.getAttributeNameandValHT(docNd);
		if(docNd!=null){
			NodeList hierNdList=docNd.getChildNodes();	
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType()==Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Workflow")){
					Node workflowNode=hierNdList.item(i);
					Node stageNode=null;	
					NodeList stageNodeList=workflowNode.getChildNodes();
					attrHT = Globals.getAttributeNameandValHT(workflowNode);
					String currentStageNo=(String)attrHT.get("Current_Stage_No");
					currStageNo=currentStageNo;
					String currentStageName=(String)attrHT.get("Current_Stage_Name");
					totalStageNo=(String)attrHT.get("Total_No_Stages");
					int stgeconut=1;
					Hashtable wrokflowHashTable=new Hashtable<>();
					System.out.println("-------WORKFLOW--------------");
					for (int j = 0; j < stageNodeList.getLength(); j++) {
						if (stageNodeList.item(j).getNodeType() == Node.ELEMENT_NODE
								&& stageNodeList.item(j).getNodeName() == "Stage") {
							if (!stageNodeList.item(j).getAttributes().getNamedItem("Stage_Name")
									.getNodeValue().equals("Admin") && !stageNodeList.item(j).getAttributes().getNamedItem("Stage_Name")
									.getNodeValue().equals("public")) {

								Hashtable ecahOneStage = Globals.getAttributeNameandValHT(stageNodeList.item(j));
								//System.out.println("-------ecahOneStage--------------"+ecahOneStage);
								String stageNo = (String) ecahOneStage.get("Stage_No");

								LoginProcessManager logn1 = new LoginProcessManager();
								Hashtable currentstageDetailsHT = logn1.retriveStageDetailsFromXML(workflowNode,
										stageNo, "");

								System.out.println(stageNo+":::::::::currentstageDetailsHT::::::::: "+currentstageDetailsHT);
								Hashtable currentemplyeeDetailsHT = (Hashtable) currentstageDetailsHT
										.get("EmployeedetHT");
								Hashtable curentPropertiesHT = (Hashtable) currentstageDetailsHT.get("PropertiesHT");
								String Properties = (String) curentPropertiesHT.get("Properties");
								Hashtable mssgeDetailsHT = (Hashtable) currentstageDetailsHT.get("MessagedetHT");

								Hashtable employeeDetHt=getEmpDetails(mssgeDetailsHT,currentemplyeeDetailsHT);
								currentstageDetailsHT.put("EmpDetails", employeeDetHt);
								Hashtable history=new Hashtable<>();
								Hashtable recenthistory=new Hashtable<>();
								Hashtable historylist=new Hashtable<>();
								history.put("RecentHistory", recenthistory);
								history.put("RecentHistoryList", recenthistory);
								currentstageDetailsHT.put("History", history);
								wrokflowHashTable.put("Stage_"+stageNo, currentstageDetailsHT);
								stgeconut++;

							}


						}
					}

					attrHT.put("WrokflowStageDetails", wrokflowHashTable);


				}else if(hierNdList.item(i).getNodeType()==Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("History")) {
					Node historyNode=hierNdList.item(i);
					NodeList actionNodeList=historyNode.getChildNodes();
					Hashtable historyHt=new Hashtable<>();
					for (int j = 0; j < actionNodeList.getLength(); j++) {
						if(actionNodeList.item(j).getNodeType()==Node.ELEMENT_NODE) {
							Node nod=actionNodeList.item(j);
							Hashtable ht = Globals.getAttributeNameandValHT(nod);
							historyHt.put(historyHt.size(), ht);
						}
					}
					System.out.println("-=-=-=-=totalStageNo-=-=-=-=-=-=-=-="+totalStageNo);
//					for (int j = 1; j <= Integer.valueOf(totalStageNo); j++) {
//						String stage=String.valueOf(j);
//					}

					getLatestHistory(historyHt,attrHT);

				}
			}
		}
		System.out.println(documentID+"-=-=-=-=Before-=-=attrHT-=-=-=-=-=-=-=-="+attrHT);
		if(attrHT == null || attrHT.isEmpty()) {
			throw new Exception("This document is not attached to any workflow.");
		}
		sethashtablesize4attrHT(attrHT);
		//code change by kishor 10-07-2018
		attrHT.put("docDeatilsHT", docDeatilsHT);
		System.out.println("-=-=-=-=-=-=attrHT-=-=-=-=-=-=-=-="+attrHT);
		
		return attrHT;
	}
	
	private static void sethashtablesize4attrHT(Hashtable attrHT) {
		// TODO Auto-generated method stub
		try {
			
			Hashtable wrokflowStageDetails=(Hashtable)attrHT.get("WrokflowStageDetails");
//			if(wrokflowStageDetails == null)
//				return;
			for (int i = 1; i <= wrokflowStageDetails.size(); i++) {
				
				String keys="Stage_"+i;
				Hashtable stage=(Hashtable)wrokflowStageDetails.get(keys);
				Hashtable messagedetHT=(Hashtable)stage.get("MessagedetHT");
				Hashtable escalateManagersHT=(Hashtable)stage.get("EscalateManagersHT");
				Hashtable employeedetHT=(Hashtable)stage.get("EmployeedetHT");
				
				messagedetHT.put("MessagedetHT_SIZE", String.valueOf(messagedetHT.size()));
				escalateManagersHT.put("EscalateManagersHT_SIZE",String.valueOf(escalateManagersHT.size()));
				employeedetHT.put("EmployeedetHT_SIZE", String.valueOf(employeedetHT.size()));
				
				stage.put("MessagedetHT", messagedetHT);
				stage.put("EscalateManagersHT", escalateManagersHT);
				stage.put("EmployeedetHT", employeedetHT);
				
				wrokflowStageDetails.put(keys, stage);
				attrHT.put("WrokflowStageDetails", wrokflowStageDetails);
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void getLatestHistory(Hashtable historyHt,Hashtable attrHT) {
		// TODO Auto-generated method stub
		
		try {
			
			Hashtable nodedetails=new Hashtable<>();
			int cou=0;
			for (int i = historyHt.size()-1; i >= 0; i--) {
				Hashtable ht=(Hashtable)historyHt.get(i);
				nodedetails=checkNodes(ht,nodedetails,attrHT);
				
				}
			setKey(nodedetails);
			nodedetails.put("TableLength", nodedetails.size());
			JSONObject jobj = new JSONObject(nodedetails);
			System.out.println("Action_Node_Details: "+jobj.toJSONString());
			
			attrHT.put("Document_ActionDetails", nodedetails);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void setKey(Hashtable nodedetails) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < nodedetails.size(); i++) {
				Hashtable ht=(Hashtable)nodedetails.get("Table"+i);
				ht.put("tblLength", ht.size());
				nodedetails.put("Table"+i, ht);
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	private static Hashtable checkNodes(Hashtable ht, Hashtable nodedetails,Hashtable attrHT) {
		// TODO Auto-generated method stub
		try {
			Hashtable newNodeHt=new Hashtable<>();
			String fromstage=(String)ht.get("FromStage");
			if(nodedetails.size()!=0) {
				int keysHt=nodedetails.size()-1;
				Hashtable lastNodeHt=(Hashtable)nodedetails.get("Table"+keysHt);
				int i=lastNodeHt.size()-1;
				Hashtable laststageHt=(Hashtable)lastNodeHt.get("R"+i);	
				String laststageName=(String)laststageHt.get("FromStage");
				if(fromstage.equals(laststageName)) {
					lastNodeHt.put("R"+lastNodeHt.size(), ht);
					int keysHt1=nodedetails.size()-1;
					nodedetails.put("Table"+keysHt1, lastNodeHt);
				}else {
					newNodeHt.put("R"+newNodeHt.size(), ht);
					nodedetails.put("Table"+nodedetails.size(), newNodeHt);
				}
				
			}else {
				
				newNodeHt.put("R"+newNodeHt.size(), ht);
				getCurrntStageHistory(ht,nodedetails,attrHT);
				nodedetails.put("Table"+nodedetails.size(), newNodeHt);
				System.out.println("\"Table\"+:: "+nodedetails);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return nodedetails;
	}

	
	public static boolean getCurrntStageHistory(Hashtable newNodeHt,Hashtable nodedetails,Hashtable attrHT) {
		// TODO Auto-generated method stub
		try {
			Hashtable lastRowHt=newNodeHt;
			System.out.println("attrHT *****************"+attrHT);
			Hashtable wrokflowHashTable=(Hashtable)attrHT.get("WrokflowStageDetails");
			String current_Stage_No=(String)attrHT.get("Current_Stage_No");
			if(current_Stage_No.equalsIgnoreCase("Completed") || current_Stage_No.equalsIgnoreCase("Cancel") || current_Stage_No.equalsIgnoreCase("Rules Failed"))
				return false;
			String totalStage=(String)attrHT.get("Total_No_Stages");
			for(int j=1;j<=wrokflowHashTable.size();j++) {
				Hashtable currentstageDetailsHT=(Hashtable)wrokflowHashTable.get("Stage_"+j);
				String stageNO=(String)currentstageDetailsHT.get("Stage_No");
				if(current_Stage_No.equals(stageNO)) {
					current_Stage_No=stageNO;
				}
			}
			int currStage = Integer.valueOf(current_Stage_No);
			int totStage = Integer.valueOf(totalStage);
			for(int j=currStage;j<=totStage;j++) {
				Hashtable currentstageDetailsHT=(Hashtable)wrokflowHashTable.get("Stage_"+j);
				Hashtable mssgeDetailsHT = (Hashtable) currentstageDetailsHT.get("MessagedetHT");
				String finalMsg = (String) mssgeDetailsHT.get("Final");

				Hashtable currentemplyeeDetailsHT = (Hashtable) currentstageDetailsHT
						.get("EmployeedetHT");
				
//				Hashtable curentPropertiesHT = (Hashtable) currentstageDetailsHT.get("PropertiesHT");
//				String Properties = (String) curentPropertiesHT.get("Properties");
				
				Hashtable propHT=(Hashtable) currentstageDetailsHT.get("PropertiesHT");
				String Properties = (String) propHT.get("Properties");
				System.out.println("-----22------Properties------11------ "+Properties);
				
				LoginProcessManager log = new LoginProcessManager();
				Hashtable lastRowHt1=new Hashtable<>();
				for (int i = 0; i < currentemplyeeDetailsHT.size(); i++) {
					Hashtable ht1=(Hashtable)currentemplyeeDetailsHT.get(i);
					if(ht1!=null&&!finalMsg.equals(ht1.get("User_Status"))) {
						String userName = (String)ht1.get("E-mail");
						String activeFlag = ht1.get("Active") == null || String.valueOf(ht1.get("Active")).isEmpty() ? "true" : String.valueOf(ht1.get("Active"));
						Hashtable<String, String> loginDetailsHT = log.getLoginDetails(userName, (String)attrHT.get("CustomerKey"));
						String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
						System.out.println(userName+"-=-=-=-=-=-=-="+disableFlag);
						if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
							continue;
						lastRowHt1=new Hashtable<>();
						String date=(String)lastRowHt.get("Date");
						String to=(String)lastRowHt.get("To");
						String fromStage=current_Stage_No;
						String documentPath=(String)lastRowHt.get("DocumentPath");
						String toStage=Integer.parseInt(current_Stage_No) + 1 > Integer.valueOf(totalStage) ? "Complete" : String.valueOf(Integer.parseInt(current_Stage_No) + 1);
						String notes=(String)lastRowHt.get("Notes");

						lastRowHt1.put("From", userName);
						lastRowHt1.put("ActionType", "WIP");

						lastRowHt1.put("Date", date);
						lastRowHt1.put("To", to);
						lastRowHt1.put("FromStage", fromStage);
						lastRowHt1.put("DocumentPath", documentPath);
						lastRowHt1.put("ToStage", toStage);
						lastRowHt1.put("Notes", notes);

						Hashtable tblHT=new Hashtable<>();
						tblHT.put("R"+tblHT.size(), lastRowHt1);
						nodedetails.put("Table"+nodedetails.size(), tblHT);
						if(Properties.equals("Parallel")) {
							
						}else {
							break;	
						}
						
					}
				}
				if(!lastRowHt1.isEmpty())
					break;
			}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static Hashtable getEmpDetails(Hashtable mssgeDetailsHT, Hashtable currentemplyeeDetailsHT) {
		// TODO Auto-generated method stub
		Hashtable ht=new Hashtable<>();
		try {
			String finalMsg = (String) mssgeDetailsHT.get("	");
			for (int i = 0; i < currentemplyeeDetailsHT.size(); i++) {
				Hashtable ht1=(Hashtable)currentemplyeeDetailsHT.get(i);
				if(ht1!=null&&finalMsg.equals(ht1.get("User_Status"))) {
					ht.putAll(ht1);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ht;
	}
	
	public static String getDocumentDetails(String customerKey) throws Exception {
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Reader fileReader = new FileReader(HIERARCHY_XML_DIR+docXmlFileName);
        BufferedReader bufReader = new BufferedReader(fileReader);
        
        StringBuilder sb = new StringBuilder();
        String line = bufReader.readLine();
        while( line != null){
            sb.append(line).append("\n");
            line = bufReader.readLine();
        }
        String xml2String = sb.toString();
        System.out.println("-=-=-=-=-=TEST_XML_STRING-=-=-=-="+xml2String);
        org.json.JSONObject xmlJSONObj = XML.toJSONObject(xml2String);
        String jsonPrettyPrintString = xmlJSONObj.toString();
        org.json.JSONObject documentsObj = (org.json.JSONObject)xmlJSONObj.get("Documents");
        org.json.JSONArray docObj = (org.json.JSONArray)documentsObj.get("Document");
        System.out.println(jsonPrettyPrintString);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-="+docObj.length());
        for(int i=0;i<docObj.length();i++) {
        	org.json.JSONObject test = (org.json.JSONObject)docObj.get(i);
        	if(!customerKey.equalsIgnoreCase(test.getString("CustomerKey"))) {
        		docObj.remove(i);
        		i--;
        	}
        	System.out.println("-=-=-="+test.getString("CustomerKey"));
        }
        org.json.JSONObject json = new org.json.JSONObject();
        json.put("DocumentReport", xmlJSONObj);
        Hashtable membersFromLaXmlHT = workFlowBean.getAllUsersFromlaXML();
		Hashtable membersHT = new Hashtable<>();
		ArrayList<String> membersNameAL = new ArrayList<>();
		for(int i=0;i<membersFromLaXmlHT.size();i++){
			membersHT = (Hashtable)membersFromLaXmlHT.get(i);
			//        	String empname=membersHT.get("Employee_Name")+"("+membersHT.get("Access_Unique_ID")+")";
			String empname=(String)membersHT.get("Employee_Name");
			membersNameAL.add(empname);
		}
		org.json.JSONArray userAL = new org.json.JSONArray(membersNameAL);
		json.put("Users", userAL);
        jsonPrettyPrintString = json.toString();
        System.out.println(jsonPrettyPrintString);
        System.out.println(docObj.length());
        return jsonPrettyPrintString;
	}
	
	public static Hashtable getNextDocumentUsers(Document xmlDoc, Node workFlowN, String currentStageNO, String approvedUserName) throws Exception {
		Hashtable tempHT = new Hashtable<>();
		ArrayList<String> usersAL = new ArrayList<>();
		ArrayList<String> usersNameAL = new ArrayList<>();


		String nextStage = "";
		String nextStageNo = "";
		LoginProcessManager log = new LoginProcessManager();
		Node stageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", currentStageNO);

		Hashtable userHT = log.getLoginDetails(approvedUserName, ((Element)workFlowN).getAttribute("CustomerKey"));
		System.out.println("OOOOOOOOOOOOOOOOOOOO"+approvedUserName);
		String getNextUsername = String.valueOf(userHT.get("First_Name"))+" "+String.valueOf(userHT.get("Last_Name"));
		System.out.println(currentStageNO+"^^^^^^^^^^^name^^^^:^^^^^createdMember^^^^^^^^^^^"+approvedUserName);
		NodeList msgEnamePropNL = stageN.getChildNodes();
		for (int i = 0; i < msgEnamePropNL.getLength(); i++) {
			Node propertiesNode = msgEnamePropNL.item(i);
			if (propertiesNode.getNodeType() == Node.ELEMENT_NODE) {
				if (propertiesNode.getNodeName().equalsIgnoreCase("Properties")) {

					// get Properties
					//code change pandian 19MAR2014
					Hashtable propHT=(Hashtable)Globals.getAttributeNameandValHT(propertiesNode);
					String propertiesValue=(String)propHT.get("Concurrency");
					//						String propertiesValue=propertiesNode.getTextContent();
					System.out.println("propertiesValue  :"+propertiesValue);
					if(propertiesValue != null && propertiesValue.equals("Parallel")){
						Element propE = (Element) propertiesNode;
						String minApprovers=propE.getAttribute("Min_Approvers");
						Hashtable stageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currentStageNO,"");
						Hashtable emplyeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
						Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
						String finalMsg=(String)mssgeDetailsHT.get("Final");
						int appr=0;
						int minAprrovers=0;

						//code change pandian 19MAR2014


						for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
							Hashtable ApproversHT=new Hashtable();
							ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
							String userStatus=(String)ApproversHT.get("User_Status");								
							if(userStatus.equals(finalMsg)){

								minAprrovers=minAprrovers+1;
								appr++;
							}
						}
						minAprrovers++;
						int fromxmlminapprovers=Integer.parseInt(minApprovers);
						if(minAprrovers>=fromxmlminapprovers){
							Hashtable testHT = getNextStageUsers(xmlDoc, workFlowN, currentStageNO);
							System.out.println("-=-=-=-testHT=-=-3=-="+testHT);
							ArrayList<String> testAL = (ArrayList<String>)testHT.get("Users");
							ArrayList<String> userNametestAL = (ArrayList<String>)testHT.get("UserName");
							String stage = (String)testHT.get("Stage");
							String stageNo = (String)testHT.get("StageNo");
							/*if(!getNextUsername.isEmpty() && getNextUsername != null) {

								getNextUsername=String.valueOf(userHT.get("First_Name"))+" "+String.valueOf(userHT.get("Last_Name"));

							}else {

								getNextUsername=approvedUserName;

							}
							 */
							usersNameAL.addAll(userNametestAL);
							usersAL.addAll(testAL);
							nextStage = stage;
							nextStageNo = stageNo;
						}else {
							int nonApprovedUsersCount = 0;
							for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
								Hashtable ApproversHT=new Hashtable();
								ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
								String userStatus=(String)ApproversHT.get("User_Status");
								String usrname=(String)ApproversHT.get("empName");
								String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
								Hashtable<String, String> loginDetailsHT = log.getLoginDetails(usrname, ((Element)workFlowN).getAttribute("CustomerKey"));
								String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");
								if(!userStatus.equals(finalMsg) && !activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true")){
									String getApproverNextUsername = String.valueOf(loginDetailsHT.get("First_Name"))+" "+String.valueOf(loginDetailsHT.get("Last_Name"));
									if(!getApproverNextUsername.isEmpty() && getApproverNextUsername != null) {

										getApproverNextUsername=String.valueOf(loginDetailsHT.get("First_Name"))+" "+String.valueOf(loginDetailsHT.get("Last_Name"));

									}else {

										getApproverNextUsername=usrname;

									}
									System.out.println(getApproverNextUsername+"^^^^^^^^^^^name^^^^:^^^^^createdMember^^^^^^^^^^^"+approvedUserName);
									usersNameAL.add(getApproverNextUsername);
									usersAL.add(usrname);
									nonApprovedUsersCount++;
								}
							}
							nextStage = (String) stageDetailsHT.get("Stage_Name");
							nextStageNo = (String) stageDetailsHT.get("Stage_No");
							if(nonApprovedUsersCount <= 0) {
								Hashtable testHT = getNextStageUsers(xmlDoc, workFlowN, currentStageNO);
								System.out.println("-=-=-=-testHT=-4=-=-="+testHT);
								ArrayList<String> testAL = (ArrayList<String>)testHT.get("Users");
								ArrayList<String> userNametestAL = (ArrayList<String>)testHT.get("UserName");

								String stage = (String)testHT.get("Stage");
								String stageNo = (String)testHT.get("StageNo");
								usersNameAL.addAll(userNametestAL);
								System.out.println("^^^^^^^^^^^usersNameAL^^^^:^^^^^usersNameAL^^^^^^^^^^^"+usersNameAL.size());
								usersAL.addAll(testAL);
								nextStage = stage;
								nextStageNo = stageNo;
							}

						}
					}else {
						Hashtable stageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, currentStageNO,"");
						Hashtable emplyeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
						Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
						String finalMsg=(String)mssgeDetailsHT.get("Final");
						int lastperson=emplyeeDetailsHT.size()-1;
						Hashtable ApproversHT=new Hashtable();
						ApproversHT=(Hashtable)emplyeeDetailsHT.get(lastperson);
						String accessUniqID=(String)ApproversHT.get("Access_Unique_ID");
						String userStatus=(String)ApproversHT.get("User_Status");	
						String usrname=(String)ApproversHT.get("empName");
						if(userStatus.equals(finalMsg) || usrname.equalsIgnoreCase(approvedUserName)){
							Hashtable testHT = getNextStageUsers(xmlDoc, workFlowN, currentStageNO);
							System.out.println("-=-=-=-testHT=-=-=-="+testHT);
							ArrayList<String> testAL = (ArrayList<String>)testHT.get("Users");
							String stage = (String)testHT.get("Stage");
							String stageNo = (String)testHT.get("StageNo");
							ArrayList<String> userNametestAL = (ArrayList<String>)testHT.get("UserName");
							System.out.println("^^^^^^^^^^^usersNameAL^^^^:222222222222222^^^^^usersNameAL^^^^^^^^^^^"+usersNameAL.size());
							usersNameAL.addAll(userNametestAL);
							usersAL.addAll(testAL);
							nextStage = stage;
							nextStageNo = stageNo;
						}else {
							boolean actionMovedNextUser = false;
							boolean mailSendAlready=false;
							for (int mail = 0; mail < emplyeeDetailsHT.size(); mail++) {

								Hashtable serialApproverstempHT=(Hashtable)emplyeeDetailsHT.get(mail);
								userStatus=(String)serialApproverstempHT.get("User_Status");		
								accessUniqID=(String)serialApproverstempHT.get("Access_Unique_ID");
								usrname=(String)serialApproverstempHT.get("empName");
								if(userStatus.equals(finalMsg) || usrname.equalsIgnoreCase(approvedUserName)){
									mailSendAlready=true;
									continue;
								}
								System.out.println(mail+"-=-=-=-=-userStatus=-=-=-=-="+emplyeeDetailsHT.size()+"=--mailSendAlready--"+mailSendAlready);
								System.out.println(userStatus+"-=-=-=-=-userStatus=-=-=-=-="+finalMsg+"=--mailSendAlready--"+mailSendAlready);
								if(mailSendAlready && !userStatus.equals(finalMsg)){ // send mail to bottom level person
									mailSendAlready=false;
									int bottomlevelperson=mail;
									for (int k = bottomlevelperson; k < emplyeeDetailsHT.size(); k++) {

										Hashtable serialApproversHT=(Hashtable)emplyeeDetailsHT.get(k);

										String usermailID=(String)serialApproversHT.get("E-mail");	
										usrname=(String)serialApproversHT.get("empName");	
										String activeFlag = serialApproversHT.get("Active") == null || String.valueOf(serialApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(serialApproversHT.get("Active"));
										Hashtable<String, String> loginDetailsHT = log.getLoginDetails(usrname, ((Element)workFlowN).getAttribute("CustomerKey"));
										String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
										//fgf
										System.out.println(usrname+"-=-=-=-=-="+activeFlag+"-=-=-=-=-=-=disableFlag-=-=-=-=-=-="+disableFlag);
										if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
											continue;
										String getApproverNextUsername = String.valueOf(loginDetailsHT.get("First_Name"))+" "+String.valueOf(loginDetailsHT.get("Last_Name"));
										if(!getApproverNextUsername.isEmpty() && getApproverNextUsername != null) {

											getApproverNextUsername=String.valueOf(loginDetailsHT.get("First_Name"))+" "+String.valueOf(loginDetailsHT.get("Last_Name"));

										}else {

											getApproverNextUsername=usrname;

										}
										System.out.println(getApproverNextUsername+"^^^^^^^^^^^name^^^^:^ssssssssss^^^^createdMember^^^^^^^^^^^"+approvedUserName);
										usersNameAL.add(getApproverNextUsername);
										usersAL.add(usermailID);
										nextStage = (String) stageDetailsHT.get("Stage_Name");
										nextStageNo = (String) stageDetailsHT.get("Stage_No");
										break;
										/*FacesContext ctx1 = FacesContext.getCurrentInstance();
									ExternalContext extContext1 = ctx1.getExternalContext();
									Map sessionMap1 = extContext1.getSessionMap();
									HierarchyBean hryb = (HierarchyBean) sessionMap1.get("hierarchyBean");
									hryb.updateMailID(updateMail2DB); //code change Jayaramu 10APR14
										 */	
									}
									break;
								}
								if(!actionMovedNextUser) {
									Hashtable testHT = getNextStageUsers(xmlDoc, workFlowN, currentStageNO);
									System.out.println("-=-=-=-testHT=-=1=-="+testHT);
									ArrayList<String> testAL = (ArrayList<String>)testHT.get("Users");
									String stage = (String)testHT.get("Stage");
									String stageNo = (String)testHT.get("StageNo");
									ArrayList<String> userNametestAL = (ArrayList<String>)testHT.get("UserName");
									usersNameAL.addAll(userNametestAL);
									System.out.println("^^^^^^^^^^^name^^^^:^3333^^^^createdMember^^^^^^^^^^^"+usersNameAL);
									usersAL.addAll(testAL);
									nextStage = stage;
									nextStageNo = stageNo;
								}
							}
						}
					}
				}
			}
		}
		tempHT.put("Users", usersAL);
		tempHT.put("Stage", nextStage);
		tempHT.put("StageNo", nextStageNo);
		tempHT.put("UserName", usersNameAL);
		return tempHT;
	}
	public static Hashtable getNextStageUsers(Document xmlDoc, Node workFlowN, String currentStageNO) throws Exception {

	String userFirstName="";
	String userLastName="";
	String userGetfullName="";
		PropUtil prop=new PropUtil();
		String HierarchyDir=prop.getProperty("HIERARCHY_XML_DIR");
		String HierarchyloginXml=prop.getProperty("LOGIN_XML_FILE");
		Document doc=Globals.openXMLFile(HierarchyDir, HierarchyloginXml);
		NodeList ndList1=doc.getElementsByTagName("Obiee_Users");
		NodeList ndList=ndList1.item(0).getChildNodes();
		Hashtable tempHT = new Hashtable<>();
		
		ArrayList<String> usersAL = new ArrayList<>();
		ArrayList<String> usersNameAL = new ArrayList<>();
		
		String nextStage = "";
		String nextStageNo = "";
		LoginProcessManager log = new LoginProcessManager();
		Element WKflowNodeEl = (Element) workFlowN;
		String totalStage=WKflowNodeEl.getAttribute("Total_No_Stages");
		int nextstageno=Integer.parseInt(currentStageNO)+1;
		Hashtable<String, String> stageNoDetailsHT = RulesManager.getStageNameAndNoInHT(WKflowNodeEl, "Number");
		
		String currentStgName = stageNoDetailsHT.get(currentStageNO);
		ArrayList<String> ruleMailListAL = new ArrayList<>();
		Hashtable<String, String> detailsHT = RulesManager.applyRuleIfApplicable(workFlowN, currentStgName, String.valueOf(nextstageno), ruleMailListAL);
		String nextStageNoStr = detailsHT.get("ToStageNo") == null ? String.valueOf(nextstageno) : detailsHT.get("ToStageNo").toString();
		String result = detailsHT.get("Result") == null ? "false" : detailsHT.get("Result").toString();
		if(result.equalsIgnoreCase("false")) {
			tempHT.put("Users", usersAL);
			tempHT.put("UsersName", usersNameAL);
			tempHT.put("Stage", "Rules Failed");
			tempHT.put("StageNo", "Rules Failed");
			return tempHT;
		}
		System.out.println("-=-=-=-=-=-=nextStageNoStr-=-=-=For Mss-=-=-="+nextStageNoStr);
		nextstageno = Integer.valueOf(nextStageNoStr);
		int totstageno=Integer.parseInt(totalStage);
		if(nextstageno>totstageno){
			nextstageno=totstageno;
		}

		Element docEle = (Element)workFlowN.getParentNode();
		String documentName = docEle.getAttribute("DocumentName");
		Node nxtStageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(nextstageno));

		if(nxtStageN!=null){ // set Current status on work flow node										
			Element nxtstageEl = (Element) nxtStageN;
			String nextStageName=nxtstageEl.getAttribute("Stage_Name");
			nextStageNo=nxtstageEl.getAttribute("Stage_No");
			nextStage = nextStageName;
			Hashtable nxtstageDetailsHT=log.retriveStageDetailsFromXML(workFlowN, String.valueOf(nextstageno),"");
			Hashtable nxtemplyeeDetailsHT=(Hashtable)nxtstageDetailsHT.get("EmployeedetHT");
			Hashtable nxtPropertiesHT=(Hashtable)nxtstageDetailsHT.get("PropertiesHT");
			String Properties=(String)nxtPropertiesHT.get("Properties");
			if(Properties.equals("Parallel")){
				int mailsend=0;
				for (int mail = 0; mail < nxtemplyeeDetailsHT.size(); mail++) {
					Hashtable mailApproversHT=new Hashtable();
					mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(mail);
					String usermailID=(String)mailApproversHT.get("E-mail");	
					String usrname=(String)mailApproversHT.get("empName");	
					String activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
					Hashtable<String, String> loginDetailsHT = log.getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
					String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");
					System.out.println(usrname+"-=-=-=-=-=-=-="+disableFlag);
					if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
						continue;
					
					if(ruleMailListAL.isEmpty() || ruleMailListAL.contains(usermailID)) {
						
						for(int i=0;i<ndList.getLength();i++){
							
							if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE && ndList.item(i).getNodeName().contains("User")){
								System.out.println("^^^^^^^^^::::ndList.item(i).getTextContent():::^^^^^^^^^^^^"+ndList.item(i).getTextContent());
								
								if(ndList.item(i).getTextContent().equalsIgnoreCase(usrname)){

									Element userNameEle=(Element) ndList.item(i);
									userFirstName = userNameEle.getAttribute("First_Name");
									userLastName = userNameEle.getAttribute("Last_Name");
									System.out.println(userFirstName+"^^^^^^^^^xxxxxxxxxxxxxxxx^^^^^^^"+userLastName);
								
								if(userFirstName != null  && userLastName != null && !userFirstName.isEmpty() && !userLastName.isEmpty())
								{	
									
									
								
									userGetfullName=userFirstName+" "+ userLastName;
								
								
								}else if(userFirstName != null  &&  !userFirstName.isEmpty() || userLastName == null &&  userLastName.isEmpty()) {
									
									userGetfullName=userFirstName;
									
								}
								
								else {
								
									userGetfullName= userNameEle.getTextContent();
									
								}
								
								
								}
							}
					
						}	
						
						
						usersNameAL.add(userGetfullName);
							usersAL.add(usermailID);

					}
					
						
						
						
				}
				if(usersAL.isEmpty()) {
					return getNextStageUsers(xmlDoc, workFlowN, String.valueOf(nextstageno));
					//sendmail2Employees(nxtStageN, String.valueOf(nextstageno),xmlDoc,workFlowN,heirarchyID,hierarchyName,hierlink4Mail, adminUsername, actionName);
				}
			}else {
				Hashtable mailApproversHT=new Hashtable();
				mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(0);
				String usermailID=(String)mailApproversHT.get("E-mail");	
				String usrname=(String)mailApproversHT.get("empName");	
				String activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
				Hashtable<String, String> loginDetailsHT = log.getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
				String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");
				System.out.println("^^^^SERIAL^^^^^::::VJ::^^^^IF^^^^^^^^");
				if(!activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true") && (ruleMailListAL.isEmpty() || ruleMailListAL.contains(usermailID))){
					
					
					for(int i=0;i<ndList.getLength();i++){
						
						if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE && ndList.item(i).getNodeName().contains("User")){
						
							
							if(ndList.item(i).getTextContent().equalsIgnoreCase(usrname)){

								Element userNameEle=(Element) ndList.item(i);
								userFirstName = userNameEle.getAttribute("First_Name");
								userLastName = userNameEle.getAttribute("Last_Name");
								
							
							if(userFirstName != null  && userLastName != null && !userFirstName.isEmpty() && !userLastName.isEmpty())
							{	
								
								
								System.out.println(usermailID+"^^^^^^1^^IF^xxxxxxxxxxxxxxxx^^^^^^^"+usrname);
								userGetfullName=userFirstName+" "+ userLastName;
							
							
							}else if(userFirstName != null  &&  !userFirstName.isEmpty() || userLastName == null &&  userLastName.isEmpty()) {
								

								
								userGetfullName=userFirstName;
								
							}
							
							else {
								
								userGetfullName= userNameEle.getTextContent();
								
							}
							
							
							}
						}
				
					}	
					
						
					usersNameAL.add(userGetfullName);
					usersAL.add(usermailID);
//					String userMailAddress[] ={usermailID};
//					mailID4UpdateDB = usermailID;	
//					postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail);
//					flagTemp = true;
				}else {
					System.out.println("^^^^^^^^^::::VJ::^^^^ELSE^^^^^^^^");
					boolean flag = false;
					for (int mail = 1; mail < nxtemplyeeDetailsHT.size(); mail++) {
						mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(mail);
						usermailID=(String)mailApproversHT.get("E-mail");	
						usrname=(String)mailApproversHT.get("empName");	
						activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
						loginDetailsHT = log.getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
						disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
						if(!activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true") && (ruleMailListAL.isEmpty() || ruleMailListAL.contains(usermailID))){
							flag = true;
							
								for(int i=0;i<ndList.getLength();i++){
								
								if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE && ndList.item(i).getNodeName().contains("User")){
									System.out.println("^^^^^^^^^::::ndList.item(i).getTextContent():::^^^^^^^^^^^^"+ndList.item(i).getTextContent());
									
									if(ndList.item(i).getTextContent().equalsIgnoreCase(usermailID)){

										Element userNameEle=(Element) ndList;
										userFirstName = userNameEle.getAttribute("First_Name");
										userLastName = userNameEle.getAttribute("Last_Name");
										
									if(userFirstName != null  && userLastName != null && !userFirstName.isEmpty() && !userLastName.isEmpty())
									{	
										userGetfullName=userFirstName+" "+ userLastName;
									
									
									}else if(userFirstName != null  && userLastName == null && !userFirstName.isEmpty() && userLastName.isEmpty()) {
										
										userGetfullName=userFirstName;
										
									}
									
									else {
										
										userGetfullName= userNameEle.getTextContent();
										
									}
									
									
									}
								}
							}	
							
							
							usersNameAL.add(userGetfullName);
							usersAL.add(usermailID);
							break;
						}
					}
					if(!flag) {
						return getNextStageUsers(xmlDoc, workFlowN, String.valueOf(nextstageno));
//						sendmail2Employees(nxtStageN, String.valueOf(nextstageno),xmlDoc,workFlowN,heirarchyID,hierarchyName,hierlink4Mail, adminUsername, actionName);
					}
				}
			}
		}
		tempHT.put("Users", usersAL);
		tempHT.put("Stage", nextStage);
		tempHT.put("StageNo", nextStageNo);
		tempHT.put("UserName", usersNameAL);
		return tempHT;
	}
	
	public static Hashtable rejectNextUsers(Document xmlDoc, Node workFlowN, String stageNo, String curUserID) throws Exception {
		Hashtable tempHT = new Hashtable<>();
		ArrayList<String> usersAL = new ArrayList<>();
		ArrayList<String> rejectedusersNameAL = new ArrayList<>();
		String nextStage = "";
		String nextStgNo = "";
		LoginProcessManager lgn = new LoginProcessManager();
		Hashtable stageDetailsHT = lgn.retriveStageDetailsFromXML(workFlowN,stageNo,"");
		Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
		Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
		Hashtable propHT =(Hashtable)stageDetailsHT.get("PropertiesHT");
		String custKey = ((Element)workFlowN.getParentNode()).getAttribute("CustomerKey");
		String stagefinalStatus=(String)messagesHT.get("Final");
		String Rejected_Return_To =(String)stageDetailsHT.get("Rejected_Return_To");
		String Rejected_Set_Status_To =(String)stageDetailsHT.get("Rejected_Set_Status_To");
		String Rejected_Notification_To =(String)stageDetailsHT.get("Rejected_Notification_To");
		String concurrency =(String)propHT.get("Concurrency");			
		System.out.println("Rejected_Set_Status_To "+Rejected_Set_Status_To);
		System.out.println("Rejected_Return_To "+Rejected_Return_To);
		System.out.println("Rejected_Notification_To "+Rejected_Notification_To);			
		String returnToUser="";
		if(Rejected_Return_To==null){
			System.out.println("********ERROR****Rejection Configuration not Configured yet Now (Not in Xml)");
			Rejected_Return_To = "";
		}
		//Rejected Return To
		if(Rejected_Return_To.equalsIgnoreCase("Previous_Member")){  // member			
			Node stageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(stageNo));

			int userPos=0;
			String curUserAccUID="";
			for(int i=0;i<empNMHT.size();i++)
			{
				Hashtable indEmpHT =(Hashtable)	empNMHT.get(i);
				String accUNamexml=(String)indEmpHT.get("empName");
				if(curUserID.equalsIgnoreCase(accUNamexml))
				{	
					curUserAccUID=(String)indEmpHT.get("Access_Unique_ID");
					userPos=i;
					break;
				}
			}
			//Setting Status Based on the Index Position of the User			
			if(userPos==0)
			{
				//Setting the Status to the Previous Stage User
				int stageNO=Integer.parseInt(stageNo);
				int preStageNo=stageNO-1;
				if(preStageNo==0){
					preStageNo=1;
				}
				Node prestageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(preStageNo));
				Hashtable prestageDetailsHT = lgn.retriveStageDetailsFromXML(workFlowN,String.valueOf(preStageNo),"");
				Hashtable preStagememdetHT=(Hashtable)prestageDetailsHT.get("EmployeedetHT");
				Hashtable preStageaffMemHT=lgn.gettingLastAccOrAffectedMembers(preStagememdetHT, "lastAccess", curUserID);
				String affectedMem=(String)preStageaffMemHT.get(0);
				
				String affectedMemName= lgn.getFristLastName(affectedMem, custKey);
	
				rejectedusersNameAL.add(affectedMemName);
				usersAL.add(affectedMem);
				nextStage = (String)prestageDetailsHT.get("Stage_Name");
				nextStgNo = (String)prestageDetailsHT.get("Stage_No");
			}else
			{
				//String concurrency=(String)propHT.get("Concurrency");
				//getting Current Member Node 

				if(concurrency.equalsIgnoreCase("Serial"))
				{
					NodeList stagenodesNL=stageN.getChildNodes();
					for(int j=0;j<stagenodesNL.getLength();j++)
					{

						Node checkNode=stagenodesNL.item(j);
						if(checkNode.getNodeName().equalsIgnoreCase("Employee_Names"))
						{
							Node curempN = Globals.getNodeByAttrValUnderParent(xmlDoc, checkNode, "Access_Unique_ID", curUserAccUID);					

							//Getting Previous Member Node
							Node preempN =curempN.getPreviousSibling();
							if(preempN.getNodeType()==Node.TEXT_NODE)
							{
								preempN =curempN.getPreviousSibling().getPreviousSibling();	

							}
							Hashtable empHT=Globals.getAttributeNameandValHT(preempN);
							String role=(String)empHT.get("User_Role");
							if(role.equals("Admin")){
								preempN =preempN.getPreviousSibling();
								if(preempN.getNodeType()==Node.TEXT_NODE)
								{
									preempN =preempN.getPreviousSibling().getPreviousSibling();	

								}
							}




							Element preempE=(Element)preempN;

							String affectedMemName= lgn.getFristLastName(preempE.getTextContent(), custKey);
				
							rejectedusersNameAL.add(affectedMemName);
							usersAL.add(preempE.getTextContent());
							nextStage = (String)stageDetailsHT.get("Stage_Name");
							nextStgNo = (String)stageDetailsHT.get("Stage_No");
						}
					}
				}else //4 parallel
				{

//					System.out.println("memdetHT "+memdetHT);
					Hashtable affectedMemHT=lgn.gettingLastAccOrAffectedMembers(empNMHT, "lastAccess", curUserID);
					String affectedMem=(String)affectedMemHT.get(0);


					//getting Access Unique Id 4 affected member
					String affectedUserAccUID="";
					for(int i=0;i<empNMHT.size();i++)
					{
						Hashtable indEmpHT =(Hashtable)	empNMHT.get(i);
						String accUNamexml=(String)indEmpHT.get("empName");
						if(affectedMem.equalsIgnoreCase(accUNamexml))
						{	
							affectedUserAccUID=(String)indEmpHT.get("Access_Unique_ID");
							returnToUser=accUNamexml;
							break;
						}
					}

					String affectedMemName= lgn.getFristLastName(returnToUser, custKey);
					
					rejectedusersNameAL.add(affectedMemName);
					usersAL.add(returnToUser);
					nextStage = (String)stageDetailsHT.get("Stage_Name");
					nextStgNo = (String)stageDetailsHT.get("Stage_No");
				}

			}
		}else {
			int preStage = 1;
			String[] arrOfStr = Rejected_Return_To.split("~", 3); 
			if(Rejected_Return_To.equalsIgnoreCase("Previous_Stage"))  // previews Stage First Member
			{
				int stageNo1=Integer.parseInt(stageNo);
				preStage=stageNo1-1;
				if(preStage==0){
					preStage=1;
				}
				//returnToUser=settingStatus4StageFirstMember(levelDoc,WfNode,String.valueOf(preStage),currentStageNo, Rejected_Set_Status_To,currentuserAccUID,curUserId);
			}else if(Rejected_Return_To.equalsIgnoreCase("First_Stage"))  // First Stage First Member
			{
				//returnToUser=settingStatus4StageFirstMember(levelDoc,WfNode,"1",currentStageNo, Rejected_Set_Status_To,currentuserAccUID,curUserId);
				preStage = 1;
			}else if(arrOfStr[0].equalsIgnoreCase("Goto_Stage"))// Goto Stage  Member
			{
				preStage = Integer.parseInt(arrOfStr[2]);
			}
			rejectedusersNameAL.clear();
			usersAL.clear();
			System.out.println("-=-=-=-=dfgdf-=-=-="+preStage);
			String returnToUsr = "";
			Node stageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(preStage));
			Hashtable prestageDetailsHT = lgn.retriveStageDetailsFromXML(workFlowN,String.valueOf(preStage),"");
			Hashtable curentPropertiesHT=(Hashtable)prestageDetailsHT.get("PropertiesHT");
			String Properties=(String)curentPropertiesHT.get("Properties");
			NodeList stagenodesNL=stageN.getChildNodes();
			for(int j=0;j<stagenodesNL.getLength();j++)
			{

				Node checkNode=stagenodesNL.item(j);
				if(checkNode.getNodeName().equalsIgnoreCase("Employee_Names"))
				{
					NodeList empNL=checkNode.getChildNodes();
					for(int k=0;k<empNL.getLength();k++)
					{
						Node firstNode=empNL.item(k);
						if(firstNode.getNodeType()==Node.ELEMENT_NODE)
						{
							Hashtable empHT=Globals.getAttributeNameandValHT(firstNode);
							String role=(String)empHT.get("User_Role");
							String activeFlag = empHT.get("Active") == null || String.valueOf(empHT.get("Active")).isEmpty() ? "true" : String.valueOf(empHT.get("Active"));
							if(role.equals("Admin") || activeFlag.equalsIgnoreCase("false")){
								continue;
							}else{
								
								returnToUsr=(String)empHT.get("E-mail");
								String affectedMemName= lgn.getFristLastName(returnToUsr, custKey);
								System.out.println(Properties+"-=dsfs-=-=Properties-=-=-=-="+returnToUsr+"-=-="+(String)empHT.get("E-mail"));
								if(Properties.equalsIgnoreCase("serial") && usersAL.isEmpty()) {
									rejectedusersNameAL.add(affectedMemName);
									usersAL.add(returnToUsr);
								break;
								}else {
									rejectedusersNameAL.add(affectedMemName);
									usersAL.add(returnToUsr);
								}
							}
						}
					}
					break;
				}
			}
			System.out.println("-=-=-=-=-=usersAL-=-=-=ss-=-=-="+usersAL);
			Element stgELe = (Element) stageN;
			nextStage = stgELe.getAttribute("Stage_Name");
			nextStgNo = stgELe.getAttribute("Stage_No");
		}
		System.out.println("-=-=-=-=-=usersAL-=-=-=-=-=-="+usersAL);
		
		tempHT.put("UserName", rejectedusersNameAL);
		tempHT.put("Users", usersAL);
		tempHT.put("Stage", nextStage);
		tempHT.put("StageNo", nextStgNo);
		return tempHT;
	}
	
	public static String getSmartSheetCode(String state) throws Exception {
		PropUtil prop = new PropUtil();
		String smDir = prop.getProperty("SMARTSHEET_DIR");
		File file = new File(smDir+state+".txt");
		if(!file.exists() || !file.isFile())
			return "";
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String url = reader.readLine();
		reader.close();
		if(file.exists() && file.isFile())
			file.delete();
		return url;
	}
	
	public static String getDropboxCode(String url) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream());

		InputStreamReader isr = new InputStreamReader(connection.getInputStream());
		char[] by = new char[1024];
		int read = 0;
		StringBuilder sb = new StringBuilder();
		while((read = isr.read(by, 0, by.length)) > 0) {
			sb.append(by);
		}

		System.out.println("-=-=-=-=sddfvd-=-=-=-="+sb.toString());
		return sb.toString();
	}
	
	
	
	
	public static void saveUploadDocName(String docID,String docName,String uploadBy,String dateTime) {
		// TODO Auto-generated method stub
		try {
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			String	DateFormat=prop.getProperty("DATE_FORMAT");
			SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
			Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
			
			
			
			Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
			Element docEle = (Element)docNd;
			String xmlDocID=docEle.getAttribute("Document_ID");
			System.out.println(xmlDocID+"<========odddcd===>>"+docID);
			Node resubNd = Globals.getChildNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID,"Resubmit");
			Element resubEle=null;
			if(resubNd == null) {
				resubEle=docXmlDoc.createElement("Resubmits");
				docEle.appendChild(resubEle);
			}else {
				resubEle = (Element) resubNd;
			}
			if(xmlDocID.equals(docID)) {
				
				Element resubUserEle=docXmlDoc.createElement("Resubmit");
				resubUserEle.setAttribute("ResubmitFileID", docName);
				resubUserEle.setAttribute("ResubmitFileName", docName);
				resubUserEle.setAttribute("ResubmitBy", uploadBy);
				resubUserEle.setAttribute("ResubmitFileType", uploadBy);
				resubUserEle.setAttribute("ResubmitFileSize", dateTime);
				resubUserEle.setAttribute("ResubmitUploadTime", dateTime);
				resubEle.appendChild(resubUserEle);
			}
			
			Globals.writeXMLFile(docXmlDoc, HIERARCHY_XML_DIR, docXmlFileName);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public static void checkResubmitAttachment(Document docXmlDoc, String document_ID,String getAttachUserID,String getAttachUser,String getAttachFileName,String infile,String infilesize,String infiletype,boolean updateflag) throws Exception{
		
		ArrayList<HeirarchyDataBean> ReSubmitAttachDetailsTemAL=new ArrayList();
		System.out.println("document_ID********** :"+document_ID);
		System.out.println("attachPID************* :"+getAttachUserID);
		System.out.println("attachPUser********* :"+getAttachUser);
		
		
		String attID="";
		String attfileName="";
		String attfileType="";
		String attfileSize="";
		
		String attachStg ="";
		String attachUser ="";
		String actionName ="";
		String actionPerformedBy ="";
		String actionPerformedOn ="";
		String uploadedDate ="";
		
		
		PropUtil prop = new PropUtil();

		String	DateFormat=prop.getProperty("DATE_FORMAT");
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
		Node docND = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", document_ID);
		Element docEle = (Element) docND;
		String xmldocID = docEle.getAttribute("Document_ID");
		Node Attachments_Nd = docEle.getElementsByTagName("Attachments").item(0);
		Element copyOfAttacNodeTempEle=null;
	//	Node addAttacNodeTemp =null;
		
		if(Attachments_Nd == null) {
			return;
		}
		
		NodeList Attachments_List = Attachments_Nd.getChildNodes();
		
		Node resNode = Globals.getChildNodeByAttrVal(docXmlDoc, "Document", "Document_ID", document_ID, "Resubmits");
	
		Element resubEle=null;
		if(resNode == null) {
			
			resubEle=docXmlDoc.createElement("Resubmits");
			docEle.appendChild(resubEle);
			
		}else {
			
			resubEle = (Element) resNode;
		}
		
		
		if(updateflag) {	
			
			
			for (int j = 0; j < Attachments_List.getLength(); j++) {
				
				if(Attachments_List.item(j).getNodeType() == Node.ELEMENT_NODE && Attachments_List.item(j).getNodeName().equals("Attachment")) {
					
					Node nameN=Attachments_List.item(j).cloneNode(true);
					Element attachEle=(Element)nameN;
					attID = attachEle.getAttribute("AttachMentFile_ID");
					attachUser = attachEle.getAttribute("AttachedUser");
					if(attID.equalsIgnoreCase(getAttachUserID)&& attachUser.equalsIgnoreCase(getAttachUser))
					{
						
						// nameN.cloneNode(true);
					docXmlDoc.renameNode(nameN,null, "Resubmit"); 
					copyOfAttacNodeTempEle = (Element) nameN;
					resubEle.appendChild(copyOfAttacNodeTempEle);
					
					
					}
				
				/*	if(nameN.getNodeType()==Node.ELEMENT_NODE)
					{
						Element attachEle=(Element)nameN;
						attID = attachEle.getAttribute("AttachMentFile_ID");
						attachUser = attachEle.getAttribute("AttachedUser");
						
						System.out.println(attID+":attID********** :"+getAttachUserID);
						System.out.println(attachUser+" : attachUser*************** :"+getAttachUser);
						
						if(xmldocID.equalsIgnoreCase(document_ID))
						{
							
						if(attID.equalsIgnoreCase(getAttachUserID)&& attachUser.equalsIgnoreCase(getAttachUser))
						{
							
							attID = attachEle.getAttribute("AttachMentFile_ID");
							attachUser = attachEle.getAttribute("AttachedUser");
							attfileName = attachEle.getAttribute("FileName");
							attfileType = attachEle.getAttribute("FileType");
							attfileSize = attachEle.getAttribute("FileSize");
							attachStg = attachEle.getAttribute("AttachedStage");
							actionName = attachEle.getAttribute("ActionName");
							actionPerformedBy = attachEle.getAttribute("ActionPerformedBy");
							actionPerformedOn = attachEle.getAttribute("ActionPerformedOn");
							uploadedDate = attachEle.getAttribute("UploadedDate");
							
							System.out.println("attfileName **************:"+attfileName);
							
							
							
						}
						
						}
						
					}*/


				//	Node AttachmentsNode=Globals.getNodeByAttrVal(docXmlDoc, "Attachment", "AttachMentFile_ID",  getAttachUserID);
				
					
				}
				
				
			
				
			}
						
				
			
			}
		
			Element resubUserEle=docXmlDoc.createElement("Resubmit");
			resubUserEle.setAttribute("AttachMentFile_ID", getAttachUserID);
			resubUserEle.setAttribute("AttachedUser", getAttachUser);
			resubUserEle.setAttribute("FileName", infile);
			resubUserEle.setAttribute("FileType", infiletype);
			resubUserEle.setAttribute("FileSize", infilesize);
			resubUserEle.setAttribute("AttachedStage", "");
			resubUserEle.setAttribute("ActionName", "");
			resubUserEle.setAttribute("UploadedDate", sdf.format(new Date()));
			resubUserEle.setAttribute("ActionPerformedBy", "");
			resubUserEle.setAttribute("ActionPerformedOn", "");
			
			resubEle.appendChild(resubUserEle);
		
	
	}
	
	
	public static ArrayList<HeirarchyDataBean> loadResubmitTable(String document_ID,HeirarchyDataBean hDBean) throws Exception{

		ArrayList<HeirarchyDataBean> ResubmitDetailsTemAL=new ArrayList();

		Hashtable nameHT=new Hashtable();

		String attID="";
		String attfileName="";
		String attfileType="";
		String attfileSize="";
		String attachUser ="";
		String emptyn="";
 
		String oldATTID=hDBean.getAttID(); 
		String oldATTUserID=hDBean.getAttachUser(); 
		String oldATTfileName=hDBean.getAttfileName(); 
		
		



		PropUtil prop = new PropUtil();
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);

		Node docND = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", document_ID);
		Element docEle = (Element) docND;

		Node Attachments_Nd = docEle.getElementsByTagName("Resubmits").item(0);
		if(Attachments_Nd == null)
			return ResubmitDetailsTemAL;
		NodeList Attachments_List = Attachments_Nd.getChildNodes();

		for (int j = 0; j < Attachments_List.getLength(); j++) {
			if(Attachments_List.item(j).getNodeType() == Node.ELEMENT_NODE && Attachments_List.item(j).getNodeName().equals("Resubmit")) {



				Node nameN=Attachments_List.item(j);
				if(nameN.getNodeType()==Node.ELEMENT_NODE)
				{
					Element attachEle=(Element)nameN;
					attID = attachEle.getAttribute("AttachMentFile_ID");
					attachUser = attachEle.getAttribute("AttachedUser");
					
					if(oldATTID.equalsIgnoreCase(attID) && oldATTUserID.equalsIgnoreCase(attachUser)) {
						
					attfileName = attachEle.getAttribute("FileName")== null ? "" : attachEle.getAttribute("FileName").toString();
					attfileType = attachEle.getAttribute("FileType")== null ? "" : attachEle.getAttribute("FileType").toString();
					attfileSize = attachEle.getAttribute("FileSize")== null ? "" : attachEle.getAttribute("FileSize").toString();
					String attachStg = attachEle.getAttribute("AttachedStage")== null ? "" : attachEle.getAttribute("AttachedStage").toString();
					String actionName = attachEle.getAttribute("ActionName")== null ? "" : attachEle.getAttribute("ActionName").toString();
					String actionPerformedBy = attachEle.getAttribute("ActionPerformedBy")== null ? "" : attachEle.getAttribute("ActionPerformedBy").toString();
					String actionPerformedOn = attachEle.getAttribute("ActionPerformedOn")== null ? "" : attachEle.getAttribute("ActionPerformedOn").toString();
					String uploadedDate = attachEle.getAttribute("UploadedDate")== null ? "" : attachEle.getAttribute("UploadedDate").toString();
					
					
					HeirarchyDataBean attachData4Table = new HeirarchyDataBean(attID,attfileName,attfileSize,attfileType,
							attachStg, attachUser, actionPerformedBy, actionName, actionPerformedOn,uploadedDate);
					
					ResubmitDetailsTemAL.add(attachData4Table);
					
					}
				}


			}
		}
		return ResubmitDetailsTemAL;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static ArrayList<HeirarchyDataBean> loadAttachmentTable(String document_ID) throws Exception{

		ArrayList<HeirarchyDataBean> AttachDetailsTemAL=new ArrayList();

		Hashtable nameHT=new Hashtable();

		String attID="";
		String attfileName="";
		String attfileType="";
		String attfileSize="";
		String emptyn="";




		PropUtil prop = new PropUtil();
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);

		Node docND = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", document_ID);
		Element docEle = (Element) docND;

		Node Attachments_Nd = docEle.getElementsByTagName("Attachments").item(0);
		if(Attachments_Nd == null)
			return AttachDetailsTemAL;
		NodeList Attachments_List = Attachments_Nd.getChildNodes();

		for (int j = 0; j < Attachments_List.getLength(); j++) {
			if(Attachments_List.item(j).getNodeType() == Node.ELEMENT_NODE && Attachments_List.item(j).getNodeName().equals("Attachment")) {



				Node nameN=Attachments_List.item(j);
				if(nameN.getNodeType()==Node.ELEMENT_NODE)
				{
					Element attachEle=(Element)nameN;
					attID = attachEle.getAttribute("AttachMentFile_ID");
					attfileName = attachEle.getAttribute("FileName");
					attfileType = attachEle.getAttribute("FileType");
					attfileSize = attachEle.getAttribute("FileSize");
					String attachStg = attachEle.getAttribute("AttachedStage");
					String attachUser = attachEle.getAttribute("AttachedUser");
					String actionName = attachEle.getAttribute("ActionName");
					String actionPerformedBy = attachEle.getAttribute("ActionPerformedBy");
					String actionPerformedOn = attachEle.getAttribute("ActionPerformedOn");
					String uploadedDate = attachEle.getAttribute("UploadedDate");
					System.out.println("attfileName :"+attfileName);
//					, String actionPerformedBy, String actionName,
//					String actionPerformedOn, String uploadedOn
					HeirarchyDataBean attachData4Table = new HeirarchyDataBean(attID,attfileName,attfileType,attfileSize,
							emptyn, attachStg, attachUser, actionPerformedBy, actionName, actionPerformedOn, uploadedDate);
					AttachDetailsTemAL.add(attachData4Table);
				}


			}
		}
		return AttachDetailsTemAL;
	}
	
	public static ArrayList<HeirarchyDataBean> loadAttachmentByStageAndUser(String document_ID, String stageName, String username) throws Exception{

		ArrayList<HeirarchyDataBean> AttachDetailsTemAL=new ArrayList();

		Hashtable nameHT=new Hashtable();

		String attID="";
		String attfileName="";
		String attfileType="";
		String attfileSize="";
		String emptyn="";




		PropUtil prop = new PropUtil();
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);

		Node docND = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", document_ID);
		Element docEle = (Element) docND;

		Node Attachments_Nd = docEle.getElementsByTagName("Attachments").item(0);
		NodeList Attachments_List = Attachments_Nd.getChildNodes();

		for (int j = 0; j < Attachments_List.getLength(); j++) {
			if(Attachments_List.item(j).getNodeType() == Node.ELEMENT_NODE && Attachments_List.item(j).getNodeName().equals("Attachment")) {



				Node nameN=Attachments_List.item(j);
				if(nameN.getNodeType()==Node.ELEMENT_NODE)
				{
					Element attachEle=(Element)nameN;
					attID = attachEle.getAttribute("AttachMentFile_ID");
					attfileName = attachEle.getAttribute("FileName");
					attfileType = attachEle.getAttribute("FileType");
					attfileSize = attachEle.getAttribute("FileSize");
					String attachStg = attachEle.getAttribute("AttachedStage");
					String attachUser = attachEle.getAttribute("AttachedUser");
					String actionName = attachEle.getAttribute("ActionName");
					String actionPerformedBy = attachEle.getAttribute("ActionPerformedBy");
					String actionPerformedOn = attachEle.getAttribute("ActionPerformedOn");
					String uploadedDate = attachEle.getAttribute("UploadedDate");
					System.out.println("attfileName :"+attfileName);
					if(username.equalsIgnoreCase(attachEle.getAttribute("AttachedUser")) && stageName.equalsIgnoreCase(attachEle.getAttribute("AttachedStage"))) {
						HeirarchyDataBean attachData4Table = new HeirarchyDataBean(attID,attfileName,attfileType,attfileSize,
								emptyn, attachStg, attachUser, actionPerformedBy, actionName, actionPerformedOn, uploadedDate);
						AttachDetailsTemAL.add(attachData4Table);
					}
				}


			}
		}
		return AttachDetailsTemAL;
	}
	
	public static Hashtable<String, String> checkEnableEsignInStageLevel(String document_ID) throws Exception {
		PropUtil prop = new PropUtil();
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
		Hashtable<String, String> tempHT = new Hashtable<>();

		Node docND = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", document_ID);
		Element docEle = (Element) docND;
		Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
		NodeList stageNdList = docEle.getElementsByTagName("Stage");
		String enableEsign = "false";
		String stgNo = "";
		for (int j = 0; j < stageNdList.getLength(); j++) {
			if(stageNdList.item(j).getNodeType() == Node.ELEMENT_NODE) {
				Element stgEle = (Element)stageNdList.item(j);
				String temp = stgEle.getAttribute("Enable_Esign");
				if(temp.equalsIgnoreCase("true")) {
					enableEsign = "true";
					stgNo = stgEle.getAttribute("Stage_No");
				}
			}
		}
		tempHT.put("EsignFlag", enableEsign);
		tempHT.put("EsignStageNo", stgNo);
		return tempHT;
	}
	
	public static void saveAttachment(String docID, String infile, String infilesize, String infiletype, String userNam, String statusStageNamestr) {
		try {
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			String	DateFormat=prop.getProperty("DATE_FORMAT");
			SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
			Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
			
			Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
			Element docEle = (Element)docNd;
			Element attachmentsEle = (Element)docEle.getElementsByTagName("Attachments").item(0);
			int attachCount = 0;
			for(int i=0;i<attachmentsEle.getChildNodes().getLength();i++) {
				if(attachmentsEle.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
					attachCount++;
			}
			attachCount = attachCount+1;
			Element attachEle = docXmlDoc.createElement("Attachment");
//			AttachMentFile_ID="001" Connection_Type="null"
//					FileName="Smartsheet_Collation_Collate_04621.pdf" FilePath="null"
//					FileSize="7713431" FileType="application/pdf" File_AddDate="No"
//					Source_types="null" SrcLink="null"
			String formatted = String.format("%03d", attachCount);
			attachEle.setAttribute("AttachMentFile_ID", formatted);
			attachEle.setAttribute("Connection_Type", "LocalFile");
			attachEle.setAttribute("FileName", infile);
			attachEle.setAttribute("FilePath", "");
			attachEle.setAttribute("FileSize", infilesize);
			attachEle.setAttribute("FileType", infiletype);
			attachEle.setAttribute("File_AddDate", "No");
			attachEle.setAttribute("Source_types", "");
			attachEle.setAttribute("SrcLink", "");
			attachEle.setAttribute("AttachedUser", userNam);
			attachEle.setAttribute("AttachedStage", statusStageNamestr);
			attachEle.setAttribute("UploadedDate", sdf.format(new Date()));
			
			attachmentsEle.appendChild(attachEle);
			Globals.writeXMLFile(docXmlDoc, HIERARCHY_XML_DIR, docXmlFileName);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static Hashtable getStageDetails(String docId, String stgNo) throws Exception {
		Hashtable detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		String	DateFormat=prop.getProperty("DATE_FORMAT");
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
		Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
		Element docEle = (Element)docNd;
		Node workFlowN =  docEle.getElementsByTagName("Workflow").item(0);
		Element wfEle = (Element) workFlowN;
		detailsHT = LoginProcessManager.retriveStageDetailsFromXML(wfEle, stgNo, "");
//		detailsHT.put("Stage", stageDetailsHT);
		detailsHT.put("WorkflowNd", Globals.getAttributeNameandValHT(workFlowN));
		System.out.println("Stage Details :"+detailsHT);
		return detailsHT;
	}
	
	public static String getUserAudit(String ip) throws Exception {
		String url="http://api.ipstack.com/" + ip + "?access_key=793afbded683246a743fe26123aede7e";
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream());

		InputStreamReader isr = new InputStreamReader(connection.getInputStream());
		char[] by = new char[1024];
		int read = 0;
		StringBuilder sb = new StringBuilder();
		while((read = isr.read(by, 0, by.length)) > 0) {
			sb.append(by);
		}

		System.out.println("-=-=-=-=sddfvd-audit=-=-=-="+sb.toString());
		
		return sb.toString();
	}
	
	public static void changeFilesAFterStageApproval(String custKey, String jsonStr) throws Exception {
		System.out.println("Json Str ----- :"+jsonStr);
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		String configFileName = prop.getProperty("HIERARCHY_CONFIG_FILE"); 
		Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		JSONObject jObj = (JSONObject) new JSONParser().parse(jsonStr);
		String primary_Attach_File = (String) jObj.get("Primary_FileName");
		String documentId = (String) jObj.get("DocumentID");
		String revisionType = jObj.get("RevisionType") == null ? "Offline" : (String) jObj.get("RevisionType");
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
		Element docEle = (Element) docNd;
		if(revisionType.equalsIgnoreCase("Online")) {
			String oneDriveId = jObj.get("OneDriveId") ==null ? "" : (String) jObj.get("OneDriveId");
			String oneDriveName = jObj.get("OneDriveName") == null ? "" : (String) jObj.get("OneDriveName");
			String oneDriveUrl = jObj.get("OneDriveWebUrl") == null ? "" : (String) jObj.get("OneDriveWebUrl");
			String oneDriveDownloadUrl = jObj.get("OneDriveDownloadUrl") == null ? "" : (String) jObj.get("OneDriveDownloadUrl");
			
			
			
			docEle.setAttribute("Primary_FileName", primary_Attach_File);
			docEle.setAttribute("OneDriveId", oneDriveId);
			docEle.setAttribute("OneDriveName", oneDriveName);
			docEle.setAttribute("OneDriveWebUrl", oneDriveUrl);
			docEle.setAttribute("OneDriveDownloadUrl", oneDriveDownloadUrl);
			
			Node atthmentsNd = Globals.getChildNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId,"Attachments");
			
			Element attFile_Ele=null;
			if(atthmentsNd == null) {
				attFile_Ele=docXmlDoc.createElement("Attachments");
				docEle.appendChild(attFile_Ele);
			}else {
				attFile_Ele = (Element) atthmentsNd;
			}
			
			NodeList nList=attFile_Ele.getChildNodes();
			for (int i = nList.getLength()-1; i >0; i--) {
				attFile_Ele.removeChild( nList.item( i ) );
			}
			
			JSONObject attachFileDetails = (JSONObject) jObj.get("AttachMent_Files");
			if(!attachFileDetails.isEmpty()) {

				String attachFilesCount = (String) attachFileDetails.get("TableLength");
				int noOf_Sec_AttachFiles = attachFilesCount == null || attachFilesCount.trim().isEmpty() || !Globals.checkStringIsNumber(attachFilesCount) ? 0 : Integer.parseInt(attachFilesCount);
				for(int j=0;j<noOf_Sec_AttachFiles;j++) {
					String key = "R"+j;
					int id=j+1;
					String keyVal="";
					if(id > 9) {
						keyVal="0"+id;
					}else {
						keyVal="00"+id;
					}
					JSONObject team = (JSONObject)attachFileDetails.get(key);
					String fileName = String.valueOf(team.get("FileName"));
					String fileType = String.valueOf(team.get("FileType"));
					String fileSize = String.valueOf(team.get("FileSize"));
					String fileaddDate = String.valueOf(team.get("File_AddDate"));
					String oneDriveId1 = team.get("OneDriveId") ==null ? "" : (String) team.get("OneDriveId");
					String oneDriveName1 = team.get("OneDriveName") == null ? "" : (String) team.get("OneDriveName");
					String oneDriveUrl1 = team.get("OneDriveWebUrl") == null ? "" : (String) team.get("OneDriveWebUrl");
					String oneDriveDownloadUrl1 = team.get("OneDriveDownloadUrl") == null ? "" : (String) team.get("OneDriveDownloadUrl");
					String source_types = String.valueOf(team.get("Source_types"));
					String connection_Type = String.valueOf(team.get("Connection_Type"));
					String srcLink = String.valueOf(team.get("SrcLink"));
					String filePath = String.valueOf(team.get("FilePath"));
					System.out.println(fileName+"<===fileName===>"+filePath);
					String attachStgName = team.get("AttachedStage") == null ? "Attach" : team.get("AttachedStage").toString();
					String userCustomerName = team.get("AttachedUser") == null ? "Attach" : team.get("AttachedUser").toString();
					String uploadedDate = team.get("UploadedDate") == null ? "" : team.get("UploadedDate").toString();
					Element each_attFile_Ele=docXmlDoc.createElement("Attachment");
					each_attFile_Ele.setAttribute("FileName", fileName);
					each_attFile_Ele.setAttribute("FileType", fileType);
					each_attFile_Ele.setAttribute("FileSize", fileSize);
					each_attFile_Ele.setAttribute("File_AddDate", fileaddDate);
					each_attFile_Ele.setAttribute("AttachMentFile_ID", keyVal);

					each_attFile_Ele.setAttribute("Source_types", source_types);
					each_attFile_Ele.setAttribute("Connection_Type", connection_Type);
					each_attFile_Ele.setAttribute("SrcLink", srcLink);
					each_attFile_Ele.setAttribute("FilePath", filePath);
					each_attFile_Ele.setAttribute("AttachedStage", attachStgName);
					each_attFile_Ele.setAttribute("AttachedUser", userCustomerName);
					each_attFile_Ele.setAttribute("UploadedDate", uploadedDate);
					each_attFile_Ele.setAttribute("OneDriveId", oneDriveId1);
					each_attFile_Ele.setAttribute("OneDriveName", oneDriveName1);
					each_attFile_Ele.setAttribute("OneDriveWebUrl", oneDriveUrl1);
					each_attFile_Ele.setAttribute("OneDriveDownloadUrl", oneDriveDownloadUrl1);
					attFile_Ele.appendChild(each_attFile_Ele);

				}
			}
			Globals.writeXMLFile(docXmlDoc, HIERARCHY_XML_DIR, docXmlFileName);
		}else {
			String documentName = docEle.getAttribute("DocumentName");
			String documentPath = WorkflowManager.createDocumentVersionForWFFromXML(documentName, documentId);
			String path = documentPath.split("###")[1].equalsIgnoreCase("Server") ? documentPath.split("###")[0]+"\\"+documentName : documentPath.split("###")[0];
			System.out.println("-=-=-=-=-=-=-=-=-=-=path-=-=-=-=-=-=-="+path+"\\"+documentName);
			File srcFile = new File(primary_Attach_File);
			File destFile = new File(path);
			FileUtils.copyFile(srcFile, destFile);
		}
	}
	public static void main(String[] args) throws Exception {
		/*try {
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Reader fileReader = new FileReader(HIERARCHY_XML_DIR+docXmlFileName);
	        BufferedReader bufReader = new BufferedReader(fileReader);
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufReader.readLine();
	        while( line != null){
	            sb.append(line).append("\n");
	            line = bufReader.readLine();
	        }
	        String xml2String = sb.toString();
	        System.out.println("-=-=-=-=-=TEST_XML_STRING-=-=-=-="+xml2String);
            org.json.JSONObject xmlJSONObj = XML.toJSONObject(xml2String);
            String jsonPrettyPrintString = xmlJSONObj.toString();
            System.out.println(xmlJSONObj.get("Documents").toString());
        } catch (Exception je) {
            je.printStackTrace();
        }*/
		
//		getDocumentDetails("xbstjb1ciumjt3hu");
//		getWorkflowStatusDetails("100");
//		HttpURLConnection connection = (HttpURLConnection) new URL("https://prodentechnologies.net/dropboxtoken.php?state=settu%40cygnussoftwares.com").openConnection();
//		connection.setRequestMethod("GET");
////		connection.setDoOutput(true);
//		OutputStreamWriter out = new OutputStreamWriter(
//				connection.getOutputStream());
//
//		InputStreamReader isr = new InputStreamReader(connection.getInputStream());
//		char[] by = new char[1024];
//		int read = 0;
//		StringBuilder sb = new StringBuilder();
//		while((read = isr.read(by, 0, by.length)) > 0) {
//			sb.append(by);
//		}
//
		Hashtable test = getWorkflowStatusDetails("124");
		System.out.println("-=-=-=-=sddfvd-=-=-=-="+test);
		//Attach Document 
		/*String jsonStr = "{\"WorkflowName\":\"Test Doc WF\",\"WorkflowID\":\"105\",\"CustomerKey\":\"xbstjb1ciumjt3hu\",\"CreatedBy\":\"Raghul\",\"CreatedDate\":\"03-05-2018\",\"DocumentName\":\"Template.docx\",\"ChooseTeamName\":\"\",\"ChooseMember\":\"\",\"Sendnotes\":\"Attach Document at 03-05-2018\",\"Timeline\":\"True\",\"Showhours\":\"False\",\"ExecludeWeekEnd\":\"False\",\"Eachteam\":\"True\",\"ExecludeHoliday\":\"False\",\"SendAlertBefore\":\"True\",\"SendAlertBeforeDay\":\"1\",\"SendAlertAfter\":\"False\",\"SendAlertAfterCount\":\"\",\"SendAlertAfterDay\":\"\",\"SendAlertDayHourchooser\":\"Day\",\"Tablevalue\":{\"TableLength\":\"3\",\"R0\":{\"tblCol_Team\":\"Create\",\"tblCol_StartDay\":\"0\",\"tblCol_EndDay\":\"2\",\"tblCol_EffStartDate\":\"03-05-2018\",\"tblCol_EffEndDate\":\"05-05-2018\"},\"R1\":{\"tblCol_Team\":\"Review\",\"tblCol_StartDay\":\"1\",\"tblCol_EndDay\":\"1\",\"tblCol_EffStartDate\":\"06-05-2018\",\"tblCol_EffEndDate\":\"07-05-2018\"},\"R2\":{\"tblCol_Team\":\"Approve\",\"tblCol_StartDay\":\"1\",\"tblCol_EndDay\":\"0\",\"tblCol_EffStartDate\":\"08-05-2018\",\"tblCol_EffEndDate\":\"08-05-2018\"}}}";
		try {
			attachDocument2TheWF(jsonStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//user Tasks
		//String userName = "bharath@cygnussoftwares.com";
		//actionsWaitingForUser(userName);
		
		//document version
//		String docName = "Finance.docx";
//		createDocumentVersionForWFFromXML(docName);
		
		//action
		/*String userName = "vijay@cygnussoftwares.com";
		String notes = "canceled by vijay at 11-05-18";
		String actionName = "Cancel";
		String docName = "accel-mso-23.docx";
		Hashtable detailsHT = performingAction(userName, notes, actionName, docName);
		System.out.println("Details HT :"+detailsHT);*/
		
		
		//System.out.println("-=-=-=-=-=-=-=-="+listWorkflowForCustomer("xbstjb1ciumjt3hu"));
		
		
//		String flag = checkDocumentAttached2WF("Finance.docx", "vijay@cygnussoftwares.com");
//		System.out.println("-=-=-=-=-=-=-="+flag);
	}
}
