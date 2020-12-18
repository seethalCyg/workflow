package beans;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import managers.LoginProcessManager;
import model.RuleAttributeDataModel;
import model.RuleDataModel;
import utils.Globals;
import utils.Inventory;
import utils.PropUtil;

@ManagedBean(name = "ultradocumentBean")
@ViewScoped
public class UltradocumentBean {

	public String ultraWfName;
	public String customerkeyUWF;
	public String loginEmailUWF;
	public String actiontodo;

	public static String ultraWfcustomerKey;
	public static String ultraWfLoginName;
	public static String ultraWfhandshkeID;
	public int minApproversvalue = 1;
	public Boolean sendalertBox1value;
	public Boolean sendalertBox2value;
	public Boolean firstdisable;
	public Boolean seconddisable;
	public Boolean thirddisable;

	public String sendalertBox1Tvalue;
	public String sendalertBox2Tvalue;
	public String sendalertBox3Tvalue;
	private boolean ultradocumentischanged = false;

	public boolean isUltradocumentischanged() {
		return ultradocumentischanged;
	}

	public void setUltradocumentischanged(boolean ultradocumentischanged) {
		this.ultradocumentischanged = ultradocumentischanged;
	}

	public int numbertextBoxValue;
	public String msg4Usr;
	public String msg4Usrclose;
	public String color4msg4Usr;
	public String iframeUrl;
	String hierID = "";
	String hierName = "";
	String uniqueid = "";
	private String reviewersTypestr = "onereviewer";
	private String sendallreviewersTypestr = "sendallViewer";
	public String sendallreviewersCountTvalue;
	public String parellpanestr = "false";
	public String parellpaneCLRstr = "lightgray";
	public String minaprDisplay = "none";

	public Boolean sendallreviewersCountTdisable;
	public ArrayList<UltraDataBean> ultradocumentDetailsAL = new ArrayList<UltraDataBean>();
	private ArrayList viewMenberNameAL = new ArrayList<>();
	String HIERARCHY_XML_DIR = "";

	private String hierarchyXmlFileName = "HierachyLevel.xml";
	private String hierarchyConfigFile = "Hierarchy_Config.xml";
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public UltradocumentBean() {
		this.iframeUrl = "https://www.youtube.com/embed/JQ4iAWsALXo?rel=0"; 
		ultradocumentDetailsAL.clear();
		this.ultraWfName = "";
		this.sendalertBox1value = false;
		this.sendalertBox2value = false;
		/*
		 * this.firstdisable=true; this.seconddisable=true; this.thirddisable=true;
		 */
		this.sendalertBox2Tvalue = "";
		this.sendalertBox2Tvalue = "";
		this.sendalertBox3Tvalue = "";
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		this.ultraWfLoginName = req.getParameter("emailid");
		this.ultraWfhandshkeID = req.getParameter("hand_shake_key");
		this.ultraWfcustomerKey = getcustomerkey(this.ultraWfLoginName);

	}
	public String getIframeUrl() {
		return iframeUrl;
	}

	public void setIframeUrl(String iframeUrl) {
		this.iframeUrl = iframeUrl;
	}
	public void pageloadUWF() {
		try {

			this.msg4Usr = "";
			this.msg4Usrclose = "";
			this.color4msg4Usr = "";
			String randomnumber = wfrandomGenstring();
			ultradocumentDetailsAL.clear();
			this.ultraWfName = randomnumber;
			ArrayList<UltraDataBean> loadUltradocAL = new ArrayList();
			if (ultradocumentDetailsAL.size() == 0) {

				for (int i = 0; i < 4; i++) {

					UltraDataBean ultraTablevalue = new UltraDataBean(false, ultraWfcustomerKey, "", "", "");
					ultradocumentDetailsAL.add(ultraTablevalue);

				}
			}

		} catch (Exception e) {

		}
	}

	public void wfNameLblistener() {
		try {
			System.out.println("TTTTTTTTTTTTTTTTTTTTTT :" + this.ultradocumentischanged);
			this.ultradocumentischanged = true;
			 addtablemethod();
		} catch (Exception e) {

		}
	}

	public void addtablemethod() {
		try {

			this.msg4Usr = "";
			this.msg4Usrclose = "";
			this.color4msg4Usr = "";
			ArrayList<UltraDataBean> loadUltradocAL = new ArrayList();

			for (int i = 0; i < ultradocumentDetailsAL.size(); i++) {

				String ulwfEmail = ultradocumentDetailsAL.get(i).emailID.toString();
				if (ulwfEmail.equals("") || ulwfEmail.isEmpty()) {

					this.msg4Usr = "Enter at least one E Mail ID to share this document with.";
					this.color4msg4Usr = "white";
					return;
				}
			}

			UltraDataBean ultraTablevalue = new UltraDataBean(false, ultraWfcustomerKey, "", "", "");
			ultradocumentDetailsAL.add(ultraTablevalue);

		} catch (Exception e) {

		}
	}

	public void emptymsg() {

		try {

			this.msg4Usr = "";
			this.color4msg4Usr = "";

		} catch (Exception e) {

		}
	}

	public void chgeType() {

		System.out.println(reviewersTypestr + " :::::::::::::::::reviewersTypestr");

		if (reviewersTypestr.equals("allreviewer")) {

			this.minaprDisplay = "block";

		} else {

			this.minaprDisplay = "none";
		}
		this.ultradocumentischanged = true;

	}

	public void sendallchgeType() {

		if (sendallreviewersTypestr.equals("sendallViewer")) {
			this.parellpanestr = "true";
			this.parellpaneCLRstr = "lightgray";
			this.minaprDisplay = "block";
			this.sendallreviewersCountTvalue = "";

		} else {

			this.parellpanestr = "false";
			this.parellpaneCLRstr = "#fff";
			this.minaprDisplay = "block";
			this.sendallreviewersCountTvalue = String.valueOf(ultradocumentDetailsAL.size());
		}
		this.ultradocumentischanged = true;
	}

	public void firstcheckboxAction() {
		System.out.println("sendalertBox1value************:" + sendalertBox1value);

	}

	public void secondcheckboxAction() {

		System.out.println("sendalertBox2value************:" + sendalertBox2value);

	}

	public String wfrandomGenstring() {
		try {
			PropUtil prop = new PropUtil();
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String uniqueidVal = String
				.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "UltraWF_Unique_ID", "ID"));
		return "WF" + uniqueidVal;

	}

	public String getcustomerkey(String loginMailID) {

		String customerkey = "";

		try {

			PropUtil prop = new PropUtil();
			String adminXMLPath = prop.getProperty("IISUSERADMINFILEPATH");
			String IISUSERADMINFILENAME = prop.getProperty("IISUSERADMINFILENAME");
			Document adminXmlDoc = Globals.openXMLFile(adminXMLPath, IISUSERADMINFILENAME);
			NodeList adminChNdList = adminXmlDoc.getElementsByTagName("Admin");

			NodeList adminsubChNdList = adminXmlDoc.getElementsByTagName("User");
			for (int j = 0; j < adminsubChNdList.getLength(); j++) {
				Element adminEle1 = (Element) adminsubChNdList.item(j);
				String userId = adminEle1.getAttribute("UserID");

				if (userId.equalsIgnoreCase(loginMailID)) {
					customerkey = adminEle1.getParentNode().getAttributes().getNamedItem("CustomerKey").getNodeValue();
					break;

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerkey;

	}

	private static void checkUsersAreActive(Element wfEle) {

		NodeList stagesNdList = wfEle.getElementsByTagName("Stage");
		for (int i = 0; i < stagesNdList.getLength(); i++) {
			if (stagesNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element stageEle = (Element) stagesNdList.item(i);
				Node empNd = stageEle.getElementsByTagName("Employee_Names").item(0);
				NodeList usersNdList = empNd.getChildNodes();
				setActiveAttr2User(usersNdList);

				Node escNd = stageEle.getElementsByTagName("Escalate_Managers").item(0);
				if (escNd != null) {
					NodeList escManagersNdList = escNd.getChildNodes();
					setActiveAttr2User(escManagersNdList);
				}

			}
		}
	}

	private static void setActiveAttr2User(NodeList usersNdList) {
		LoginProcessManager log = new LoginProcessManager();
		for (int j = 0; j < usersNdList.getLength(); j++) {
			if (usersNdList.item(j).getNodeType() == Node.ELEMENT_NODE) {
				Element userEle = (Element) usersNdList.item(j);
				String userAccessId = userEle.getAttribute("Access_Unique_ID");
				String username = userEle.getAttribute("E-mail");
				Hashtable LoginDetailsHT = log.getLoginDetailsById(username, userAccessId);
				if (LoginDetailsHT != null && !LoginDetailsHT.isEmpty()) {
					String activeFlag = (String) LoginDetailsHT.get("Active");
					if (activeFlag != null && activeFlag.equalsIgnoreCase("false")) {
						userEle.setAttribute("Active", "true");
					}
				}
			}
		}
	}

	public String addStageULTRAintoXML(String heirCode, String stageName, String[] empNames, String[] statusMsg,
			String[] empEmail, String[] empUid, String propName, String minApprovers, String stageNumber,
			String saveType, String selectedStageRole, String parallelType, String selectedMember,
			Hashtable hashValueTable, Hashtable rejectHT, Hashtable pauseHT, boolean autologin, boolean allowupload,
			boolean externalUserFlag, boolean sendPrimaryFileOnlyFlag, Boolean statusCode4DocsFlag,
			boolean changeprimarydocFlag, ArrayList<RuleDataModel> rulesAL,
			ArrayList<RuleAttributeDataModel> rulesALTemp, String ultraWfName) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try {

			PropUtil prop = new PropUtil();
			String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			DashboardBean dshbn = (DashboardBean) sessionMap.get("dashboardBean");
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
			// String hierarchyID=hrybn.getHierarchy_ID();
			String dateFormat = prop.getProperty("DATE_FORMAT");
			Date HIERYDate = new Date();
			DateFormat sdf1 = new SimpleDateFormat(dateFormat);
			String StartDate = sdf1.format(HIERYDate);
			Document xmlDoc = Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node heirLevelN = Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", heirCode);
			Element hierEle = (Element) heirLevelN;

			if (hierEle.getAttribute("Hierarchy_Name").isEmpty()) {
				hierEle.setAttribute("Hierarchy_ID", heirCode);
				hierEle.setAttribute("Hierarchy_Name", ultraWfName);
				hierEle.setAttribute("Created_By", ultraWfLoginName);
				hierEle.setAttribute("Modified_By", ultraWfLoginName);
				hierEle.setAttribute("Created_Date", StartDate);
				hierEle.setAttribute("Modified_Date", StartDate);
				hierEle.setAttribute("Hand_Shake_Key", ultraWfhandshkeID);
				hierEle.setAttribute("actiontodo", actiontodo);
				// hierEle.setAttribute("Unique_ID", uniqueid);
			} else {
				hierEle = (Element) Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", heirCode);
				hierEle.setAttribute("Hierarchy_Name", ultraWfName);
				hierEle.setAttribute("Modified_By", ultraWfLoginName);
				hierEle.setAttribute("Modified_Date", StartDate);
				hierEle.setAttribute("Hand_Shake_Key", ultraWfhandshkeID);
				hierEle.setAttribute("actiontodo", actiontodo);
			}

			hierEle.setAttribute("CustomerKey", ultraWfcustomerKey);
			Node checkifWorkflowisExists = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);

			int stageNo = 0;
			if (saveType.equals("saveStage")) {
				stageNo = Integer.parseInt(stageNumber);
			} else {
				stageNo = 1;
			}

			Element stagesE = null;
			if (checkifWorkflowisExists == null) {

				stagesE = xmlDoc.createElement("Workflow");
				stagesE.setAttribute("Workflow_Level", "Default");
				stagesE.setAttribute("Workflow_Type", "Simple");
				stagesE.setAttribute("Hierarchy_ID", heirCode);
				stagesE.setAttribute("CustomerKey", ultraWfcustomerKey);
				if (saveType.equals("saveStage")) {
				} else {
					stagesE.setAttribute("Total_No_Stages", String.valueOf(stageNo));
					stagesE.setAttribute("Current_Stage_Name", stageName);
					stagesE.setAttribute("Current_Stage_No", String.valueOf(stageNo));

				}

				heirLevelN.appendChild(stagesE);
			} else {
				stagesE = (Element) checkifWorkflowisExists;
				stagesE.setAttribute("CustomerKey", ultraWfcustomerKey);
				if (saveType.equals("saveStage")) {

				} else {

					stageNo = Integer.parseInt(stagesE.getAttribute("Total_No_Stages"));
					stageNo++;
					stagesE.setAttribute("Total_No_Stages", String.valueOf(stageNo));

				}

			}

			Node isStageExists = Globals.getNodeByAttrValUnderParent(xmlDoc, (Node) stagesE, "Stage_No",
					String.valueOf(stageNo));

			if (isStageExists != null) {
				isStageExists.getParentNode().removeChild(isStageExists);
			}

			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName = prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document docs = Globals.openXMLFile(hierLeveldir, configFileName);
			Element rejectedval = (Element) docs.getElementsByTagName("Rejected_Return").item(0);
			Element pausehandl = (Element) docs.getElementsByTagName("Pause_Handling").item(0);

			String reject_Return_To = rejectedval.getAttribute("Previus_Stage").toString();
			String rejected_Set_Status_To_Type = rejectedval.getAttribute("Set_Status").toString();
			String rejected_Set_Status_To = "";
			String rejected_Notification_To = rejectedval.getAttribute("Send_Notification").toString();

			String pass_Notification_To = pausehandl.getAttribute("Send_Notificaion_Flag").toString();

			System.out.println("reject_Return_To *:" + reject_Return_To + " rejected_Set_Status_To_Type *:"
					+ rejected_Set_Status_To_Type + "rejected_Set_Status_To *:" + rejected_Set_Status_To
					+ "rejected_Notification_To *:" + rejected_Notification_To);

			Element stageE = xmlDoc.createElement("Stage");
			stageE.setAttribute("Stage_No", String.valueOf(stageNo));
			stageE.setAttribute("Stage_Name", stageName);
			stageE.setAttribute("Stage_Status", statusMsg.length > 0 ? statusMsg[0] : "YTS");

			stageE.setAttribute("aftersendremainderFlag", String.valueOf(sendalertBox1value));
			stageE.setAttribute("sendremainderwithdaysFlag", String.valueOf(sendalertBox2value));
			stageE.setAttribute("aftersendremainderCount", sendalertBox1Tvalue);
			stageE.setAttribute("sendremainderCount", sendalertBox2Tvalue);
			stageE.setAttribute("sendremainderwithdaysCount", sendalertBox3Tvalue);

			stageE.setAttribute("Auto_Login", String.valueOf(autologin));
			stageE.setAttribute("Allow_Upload", String.valueOf(allowupload));
			stageE.setAttribute("ExternalUser", String.valueOf(externalUserFlag));
			stageE.setAttribute("SendPrimaryFileOnly", String.valueOf(sendPrimaryFileOnlyFlag));
			stageE.setAttribute("StatusCode4Documents", String.valueOf(statusCode4DocsFlag));
			stageE.setAttribute("Change_PrimaryFileOnly", String.valueOf(changeprimarydocFlag));

			stageE.setAttribute("Rejected_Return_To", reject_Return_To);
			stageE.setAttribute("Rejected_Set_Status_To_Type", rejected_Set_Status_To_Type);
			stageE.setAttribute("Rejected_Set_Status_To", rejected_Set_Status_To);
			stageE.setAttribute("Rejected_Notification_To", rejected_Notification_To);
			stageE.setAttribute("Pass_Notification_To", pass_Notification_To);

			stageE.setAttribute("Select_Member", selectedMember);

			stagesE.appendChild(stageE);
			stageE.setAttribute("Stage_Role", "");

			Element statusMsgE = xmlDoc.createElement("Status_Codes");
			stageE.appendChild(statusMsgE);

			// code change pandian 21032014

			// dshbn.setUsercreateStageNumb(String.valueOf(stageNo));

			// Setting Status Message
			for (int i = 0; i < statusMsg.length; i++) {
				Element msgE = xmlDoc.createElement("Status_Code");
				if (i == statusMsg.length - 1) {
					msgE.setAttribute("level", "Final");
				} else {
					msgE.setAttribute("level", String.valueOf(i + 1));
				}
				msgE.setTextContent((String) statusMsg[i]);
				statusMsgE.appendChild(msgE);
			}

			Element empNamesE = xmlDoc.createElement("Employee_Names");
			stageE.appendChild(empNamesE);

			// code change VIJAY 2018

			String EscalateUniIDs = (String) hashValueTable.get("unidID");
			String EscalateEmailIDs = (String) hashValueTable.get("Email_ID");
			String EscalateuserTextcontents = (String) hashValueTable.get("empNameFrmlaxml");

			// String EscalateUniIDs =hashValueTable.get(3);

			Element escalateNamesE = xmlDoc.createElement("Escalate_Managers");
			stageE.appendChild(escalateNamesE);

			/*
			 * Element escalatesubN=xmlDoc.createElement("Name");
			 * 
			 * escalatesubN.setAttribute("Access_Unique_ID",EscalateUniIDs);
			 * escalatesubN.setAttribute("E-mail",EscalateEmailIDs);
			 * escalatesubN.setAttribute("UserName",selectedMember);
			 * escalatesubN.setTextContent(EscalateuserTextcontents);
			 * escalateNamesE.appendChild(escalatesubN);
			 */

			for (int i = 0; i < empNames.length; i++) {
				Element nameE = xmlDoc.createElement("Name");
				nameE.setAttribute("E-mail", (String) empEmail[i]);
				nameE.setAttribute("Access_Unique_ID", (String) empUid[i]);
				nameE.setAttribute("User_Status", "YTS");
				nameE.setAttribute("User_Role", selectedStageRole);
				nameE.setTextContent((String) empNames[i]);
				empNamesE.appendChild(nameE);

			}

			Element propE = xmlDoc.createElement("Properties");

			if (propName != null) {
				if (propName.equalsIgnoreCase("Parallel")) {
					String percent_value = "";
					if (parallelType.equals("Percent")) { // code change Jayaramu 10APR14
						percent_value = minApprovers;
						double percent = Double.parseDouble(minApprovers);
						double totalNumberOfMembers = empNames.length;
						double minAppr = (percent * totalNumberOfMembers) / 100;
						minApprovers = String.valueOf(Math.round(minAppr));
					}

					if (reviewersTypestr.equalsIgnoreCase("allreviewer")) {

						if (sendallreviewersTypestr.equalsIgnoreCase("sendallViewer")) {

							this.minApproversvalue = ultradocumentDetailsAL.size();

						} else if (sendallreviewersTypestr.equalsIgnoreCase("sendcountViewer")) {

							this.minApproversvalue = Integer.parseInt(sendallreviewersCountTvalue);

						}

					}

					propE.setAttribute("Min_Approvers", String.valueOf(minApproversvalue));
					propE.setAttribute("Concurrency", propName);
					propE.setAttribute("Parallel_Type", parallelType);
					propE.setAttribute("Min_Approvers_Percent", percent_value);
					propE.setTextContent(propName);
				} else {

					propE.setAttribute("Concurrency", propName);
					propE.setTextContent(propName);
				}
			}

			stageE.appendChild(propE);

			Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);

			// RulesManager.saveRulesInStage(heirCode, stageName, rulesAL, rulesALTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return null;

	}

	public String randomGenpwd(int length) {

		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "");
		for (int i = 0; i < length; i++)
			sb.append(chars[rnd.nextInt(chars.length)]);

		return sb.toString();

	}

	public void wfStagesaveAction() {

		try {

			System.out.println("HIIIIIIIIIII*****ultradocumentDetailsAL**********IIIIIIIIIIIIIIIIII"
					+ ultradocumentDetailsAL.size());

			PropUtil prop = new PropUtil();
			String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc = Globals.openXMLFile(heirLeveldir, heirLevelXML);
			String hierarchyID = String
					.valueOf(Globals.getMaxID(heirLeveldir, hierarchyConfigFile, "Hierarchy_ID", "ID"));
			this.hierID = hierarchyID;
			this.hierName = ultraWfName;
			String defaultStageName = "Stage_001";
			String minApprovers = "1";
			String stageNumber = "1";
			String uniqueid = String
					.valueOf(Globals.getMaxID(heirLeveldir, hierarchyConfigFile, "Row_ID4Oracle", "ID"));
			this.uniqueid = uniqueid;
			String dateFormat = prop.getProperty("DATE_FORMAT");
			Date HIERYDate = new Date();
			DateFormat sdf1 = new SimpleDateFormat(dateFormat);
			String StartDate = sdf1.format(HIERYDate);
			Node workFlowN = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			Element hierEle = (Element) Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID",
					hierarchyID);

			String propName;
			if (reviewersTypestr.equalsIgnoreCase("onereviewer")) {

				propName = "Serial";
			} else {
				propName = "Parallel";
			}

			if (hierEle == null) {
				// Hierarchy_Level Code_Combination="DontCreateCodeCombination"
				// Created_By="rbid" Created_Date="2014/07/22 14:38:26" Dim_Status="Not
				// Generated"
				// Dim_Status_Details="Not Generated" Hierarchy_Category="" Hierarchy_ID="385"
				// Hierarchy_Name="hierar22" Modified_By="rbid" Modified_Date="2014/07/22
				// 14:39:51"
				// RI_Hierarchy_Type="Independent" Unique_ID="87778" Version="Master">

				if (hierEle == null) {
					hierEle = xmlDoc.createElement("Hierarchy_Level");
					xmlDoc.getFirstChild().appendChild(hierEle);
				}

				hierEle.setAttribute("Hierarchy_ID", hierarchyID);
				hierEle.setAttribute("Hierarchy_Name", hierName);
				hierEle.setAttribute("Created_By", ultraWfLoginName);
				hierEle.setAttribute("Modified_By", ultraWfLoginName);
				hierEle.setAttribute("Created_Date", StartDate);
				hierEle.setAttribute("Modified_Date", StartDate);
				hierEle.setAttribute("Unique_ID", uniqueid);
				hierEle.setAttribute("Hand_Shake_Key", ultraWfhandshkeID);
				hierEle.setAttribute("actiontodo", actiontodo);
			} else {
				if (hierEle.getAttribute("Hierarchy_Name").isEmpty()) {
					hierEle.setAttribute("Hierarchy_ID", hierarchyID);
					hierEle.setAttribute("Hierarchy_Name", hierName);
					hierEle.setAttribute("Created_By", ultraWfLoginName);
					hierEle.setAttribute("Modified_By", ultraWfLoginName);
					hierEle.setAttribute("Created_Date", StartDate);
					hierEle.setAttribute("Modified_Date", StartDate);
					hierEle.setAttribute("Unique_ID", uniqueid);
					hierEle.setAttribute("Hand_Shake_Key", ultraWfhandshkeID);
					hierEle.setAttribute("actiontodo", actiontodo);
				} else {
					hierEle = (Element) Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID",
							hierarchyID);
					hierEle.setAttribute("Hierarchy_Name", hierName);
					hierEle.setAttribute("Modified_By", ultraWfLoginName);
					hierEle.setAttribute("Hand_Shake_Key", ultraWfhandshkeID);
					hierEle.setAttribute("Modified_Date", StartDate);
					hierEle.setAttribute("actiontodo", actiontodo);
				}
			}
			hierEle.setAttribute("CustomerKey", ultraWfcustomerKey);
			Element wfEle = (Element) workFlowN;
			if (wfEle != null)
				wfEle.setAttribute("CustomerKey", ultraWfcustomerKey);
			int bigcolCount = 0;
			int stagecount = 1;
			int UDAL = 0;
			for (int i = 0; i < ultradocumentDetailsAL.size(); i++) {
				System.out.println(ultradocumentDetailsAL.size() + "<<<<<<<<ultradocumentDetailsAL.size()");
				String ulwfEmail1 = ultradocumentDetailsAL.get(i).emailID.toString();
				if (!ulwfEmail1.equals("") || !ulwfEmail1.isEmpty()) {
					UDAL = UDAL + 1;
				}
			}
			String empNames[] = new String[UDAL];
			String statusMsg[] = { "YTS", "WIP", "Rejected", "Approved" };
			String empEmail[] = new String[UDAL];
			String empUid[] = new String[UDAL];
			int index = 0;
			for (int j = 0; j < ultradocumentDetailsAL.size(); j++) {

				// empNames[j]=viewMenberNameAL.get(j).toString();

				String ulwfEmail1 = ultradocumentDetailsAL.get(j).emailID.toString();
				if (!ulwfEmail1.equals("") || !ulwfEmail1.isEmpty()) {
					empNames[index] = ultradocumentDetailsAL.get(j).emailID.toString();
					empEmail[index] = ultradocumentDetailsAL.get(j).emailID.toString();
					index++;
				}
			}

			System.out.println("HIIIIIIIIIII*****hierarchyID**********IIIIIIIIIIIIIIIIII" + hierarchyID);
			Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);

			String configFileName = prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			Document docs = Globals.openXMLFile(hierLeveldir, configFileName);

			String usersXmlFileName = prop.getProperty("LOGIN_XML_FILE");
			Document doc = Globals.openXMLFile(hierLeveldir, usersXmlFileName);
			NodeList obiee_usersList = doc.getElementsByTagName("Obiee_Users");
			Node obieeUserparentNode = obiee_usersList.item(0);
			NodeList usersNode = obieeUserparentNode.getChildNodes();
			int j = 0;

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			// HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");

			Hashtable hashValueTable = new Hashtable();
			System.out.println("1111111111111111");

			if (ultradocumentDetailsAL != null && !ultradocumentDetailsAL.isEmpty()) {

				// setAllowedHierarchyToLaXML(viewMenberNameAL,hrybn.getHierarchy_ID());
				setAllowedHierarchyToLaXML(ultradocumentDetailsAL, hierID);

			}
			System.out.println("2222222222222222::" + ultraWfcustomerKey);

			for (int k = 0; k < empNames.length; k++) {
				for (int i = 0; i < usersNode.getLength(); i++) {

					if (usersNode.item(i).getNodeType() == Node.ELEMENT_NODE) {
						String unidID = usersNode.item(i).getAttributes().getNamedItem("Access_Unique_ID")
								.getNodeValue();
						String Email_ID = usersNode.item(i).getAttributes().getNamedItem("Email_ID").getNodeValue();
						String custKey = usersNode.item(i).getAttributes().getNamedItem("CustomerKey").getNodeValue();
						// String userTextcontent =
						// usersNode.item(i).getAttributes().getNamedItem("User").getTextContent().toString();

						String empNameFrmlaxml = usersNode.item(i).getTextContent();
						// System.out.println(custKey+"::empNameFrmlaxml***::"+empNames+"::********* :"
						// + empNameFrmlaxml);
						if (empNames[k].equalsIgnoreCase(empNameFrmlaxml)
								&& custKey.equalsIgnoreCase(ultraWfcustomerKey)) {

							empEmail[k] = usersNode.item(i).getAttributes().getNamedItem("Email_ID").getNodeValue();
							empUid[k] = usersNode.item(i).getAttributes().getNamedItem("Access_Unique_ID")
									.getNodeValue();

							// System.out.println(empUid[j]+ "empUid[j]--empEmail[j]"+empEmail[j]);

							j++;
							break;
						}
					}
					/*
					 * if(Email_ID.equals(selectedMember)) {
					 * 
					 * hashValueTable.put("Email_ID", Email_ID); hashValueTable.put("unidID",
					 * unidID); hashValueTable.put("empNameFrmlaxml", empNameFrmlaxml);
					 * 
					 * 
					 * }
					 */
				}
			}
			addStageULTRAintoXML(hierarchyID, defaultStageName, empNames, statusMsg, empEmail, empUid, propName,
					minApprovers, stageNumber, "", "", "Members", "", new Hashtable(), new Hashtable(), new Hashtable(),
					false, true, false, false, false, false, new ArrayList<RuleDataModel>(),
					new ArrayList<RuleAttributeDataModel>(), ultraWfName);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setAllowedHierarchyToLaXML(ArrayList usersFromSetStageAL, String hierID) {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {

			PropUtil prop = new PropUtil();
			String usersXmlFileName = prop.getProperty("LOGIN_XML_FILE");
			String laXmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			Document doc = Globals.openXMLFile(laXmlDir, usersXmlFileName);
			Node userNode = null;
			String[] userID = null;
			String[] allowedHierarchy = null;

			NodeList userList = doc.getElementsByTagName("Obiee_Users");
			Node firstNode = userList.item(0);

			if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

				NodeList childNodes = firstNode.getChildNodes();
				userID = new String[childNodes.getLength()];
				allowedHierarchy = new String[childNodes.getLength()];
				int k = 0;

				for (int j = 0; j < childNodes.getLength(); j++) {

					userNode = childNodes.item(j);

					if (userNode.getNodeType() == Node.ELEMENT_NODE) {

						userID[k] = userNode.getTextContent();
						String userAllowedHierarchy = hierID;
						for (int l = 0; l < usersFromSetStageAL.size(); l++) {

							if (userID[k].equals(usersFromSetStageAL.get(l))) {

								Element user = (Element) userNode;
								if (userNode.getAttributes().getNamedItem("Allowed_Hierarchies") == null) {
									user.setAttribute("Allowed_Hierarchies", userAllowedHierarchy);

								} else {

									allowedHierarchy[k] = userNode.getAttributes().getNamedItem("Allowed_Hierarchies")
											.getNodeValue();

									if (allowedHierarchy[k].contains(",")) { // 60,45,52

										String[] splitedVal = allowedHierarchy[k].split(",");

										for (int n = 0; n < splitedVal.length; n++) {

											if (!userAllowedHierarchy.contains(splitedVal[n])) {

												userAllowedHierarchy = userAllowedHierarchy.concat(",") + splitedVal[n];
											}

										}

										user.setAttribute("Allowed_Hierarchies", userAllowedHierarchy);

									} else {

										if (allowedHierarchy[k].equals("")) {

											user.setAttribute("Allowed_Hierarchies", userAllowedHierarchy);

										} else if (!userAllowedHierarchy.contains(allowedHierarchy[k])) {

											userAllowedHierarchy = userAllowedHierarchy.concat(",")
													+ allowedHierarchy[k];
											user.setAttribute("Allowed_Hierarchies", userAllowedHierarchy);
										}
									}
								}

							}

						}

						k++;
					}

				}

				Globals.writeXMLFile(doc, laXmlDir, usersXmlFileName);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	public boolean saveValidation(String wf_name) throws Exception {
		int occurance = 0;
		PropUtil prop = new PropUtil();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String hierarchyXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
		NodeList HRYNodesAL = Globals.getNodeList(HIERARCHY_XML_DIR, hierarchyXmlFileName, "Hierarchy_Level");

		for (int hry = 0; hry < HRYNodesAL.getLength(); hry++) {
			Node node = HRYNodesAL.item(hry);

			Hashtable<String, String> HRYAttrHt = Globals.getAttributeNameandValHT(node);

			String cuskey1 = HRYAttrHt.get("CustomerKey") == null ? "" : HRYAttrHt.get("CustomerKey");

			String hierarchyname = HRYAttrHt.get("Hierarchy_Name");

			if (cuskey1.equalsIgnoreCase(ultraWfcustomerKey)) {
				if (wf_name.equalsIgnoreCase(hierarchyname)) {
					occurance = occurance + 1;

				}
			}

		}

		System.out.println(ultraWfLoginName + "======" + ultraWfcustomerKey);

		if (occurance != 0) {
			return false;
		} else {
			return true;
		}

	}

	public void updateActionInHierarchyxml() {
		System.out.println("________Updating action in hierarchy xml_______");
		PropUtil prop = new PropUtil();
		try {
			String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");

			Document xmlDoc = Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node heirLevelN = Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hand_Shake_Key", ultraWfhandshkeID);
			Element hierEle = (Element) heirLevelN;

			hierEle.setAttribute("actiontodo", actiontodo);

			Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);
		} catch (Exception e) {
			System.out.println("__Exception in updating the action for startworkflow__");
			e.printStackTrace();
		}

	}

	public void ulsaveAction(String action) {

		try {
			System.out.println("________action to perform_________" + action);
			actiontodo = action;
			boolean res = saveValidation(ultraWfName);
			if (res == false) {
				this.msg4Usr = "Workflow name [" + ultraWfName + "] already exist!";
				this.color4msg4Usr = "white";
				if (action.equalsIgnoreCase("start")) {
					updateActionInHierarchyxml();
				}
				return;
			}
			System.out.println("HIIIIIIIIIIIIIIIIdsfdscxvcxvcxIIIIIIIIIIIII" + ultradocumentDetailsAL.size());

			if (ultraWfName.equals("") && ultraWfName.isEmpty()) {

				this.msg4Usr = "Please enter the workflow name";
				this.color4msg4Usr = "white";
				this.msg4Usrclose = "false";
				return;
			}

			if (ultradocumentDetailsAL.size() == 0) {
				this.msg4Usr = "Please add atlest one user and to proceed further.";
				this.color4msg4Usr = "white";
				this.msg4Usrclose = "false";
				return;

			}

			for (int i = 0; i < ultradocumentDetailsAL.size(); i++) {
				String ulwfEmail1 = ultradocumentDetailsAL.get(0).emailID.toString();
				if (ulwfEmail1.equals("") || ulwfEmail1.isEmpty()) {
					this.msg4Usr = "Enter at least one E Mail ID to share this document with.";
					this.color4msg4Usr = "white";
					this.msg4Usrclose = "false";
					return;
				}
				String ulwfEmail = ultradocumentDetailsAL.get(i).emailID.toString();

				if (!ulwfEmail.equals("") || !ulwfEmail.isEmpty()) {
					// if(ulwfEmail.equals("")||ulwfEmail.isEmpty()) {
					//
					// this.msg4Usr = "Enter at least one E Mail ID to share this document with.";
					// this.color4msg4Usr = "white";
					// return;
					// }

					boolean boolval = validate(ulwfEmail);

					System.out.println(ulwfEmail + " boolval*********" + boolval);
					if (!boolval || ulwfEmail.equals("")) {

						this.msg4Usr = "Please enter the valid Email and to proceed further.";
						this.color4msg4Usr = "white";
						this.msg4Usrclose = "false";
						return;

					}

					/*
					 * Pattern p = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
					 * Matcher m = p.matcher(ulwfEmail);
					 * 
					 * 
					 * if (!m.find()) {
					 * 
					 * this.msg4Usr = "Please enter the valid Email and to proceed further.";
					 * this.color4msg4Usr = "red"; return; }
					 * 
					 */

				}
			}

			if (sendallreviewersTypestr.equalsIgnoreCase("sendcountViewer")) {

				if (sendallreviewersCountTvalue.equals("") || sendallreviewersCountTvalue.isEmpty()) {

					this.msg4Usr = "Please enter the number of approver value and to proceed further.";
					this.color4msg4Usr = "white";
					this.msg4Usrclose = "false";
					return;
				} else {

					int sendallCountTvalue = Integer.parseInt(sendallreviewersCountTvalue);
					int tablecount = ultradocumentDetailsAL.size();

					if (sendallCountTvalue > tablecount) {

						this.msg4Usr = "Please enter the number of approver value and to proceed further.";
						this.color4msg4Usr = "white";
						this.msg4Usrclose = "false";
						return;
					}

				}
			}

			if (sendalertBox1value == true) {

				if (sendalertBox1Tvalue.equals("") || sendalertBox1Tvalue.isEmpty()) {

					this.msg4Usr = "You have checked send remainder checkbox so please enter a number of days to complete the workflow.";
					this.color4msg4Usr = "white";
					this.msg4Usrclose = "false";
					return;
				}

			}

			if (sendalertBox2value == true) {

				if (sendalertBox2Tvalue.equals("") || sendalertBox2Tvalue.isEmpty()) {

					this.msg4Usr = "Please enter a number of Email's to send after the due date.";
					this.color4msg4Usr = "white";
					this.msg4Usrclose = "false";
					return;
				}

				else if (sendalertBox3Tvalue.equals("") || sendalertBox3Tvalue.isEmpty()) {

					this.msg4Usr = "Please enter a number of days time interval to send the Email's.";
					this.color4msg4Usr = "white";
					this.msg4Usrclose = "false";
					return;
				}
			}

			PropUtil prop = new PropUtil();
			String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			boolean fileexists = Globals.isFileExists(HIERARCHY_XML_DIR, loginXmlFile);
			String hierarchyConfigFile = "Hierarchy_Config.xml";
			Document doc = null;
			FileInputStream fis = null;
			FileOutputStream fos = null;
			DocumentBuilderFactory dbf = null;
			DocumentBuilder db = null;
			String alredythrMailID = "";
			FacesContext fxContext = FacesContext.getCurrentInstance();
			ExternalContext extContext = fxContext.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
			String pwsd = randomGenpwd(8);
			System.out.println("PPPPPPPPPPPPPPPPPPPPPPP" + pwsd);
			Hashtable userNameHT = new Hashtable();
			if (!fileexists) {

				System.out.println("XML File does not exist. Creating New One.");

				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
				doc = db.newDocument();
				String root1 = "Obiee_Users";
				Element rootElement1 = doc.createElement(root1);
				doc.appendChild(rootElement1);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				fos = new FileOutputStream(HIERARCHY_XML_DIR + loginXmlFile);
				StreamResult result = new StreamResult(fos);
				transformer.transform(source, result);
				result.getOutputStream().flush();
				result.getOutputStream().close();
				System.out.println("XML File was Created.");

			} else {

			}

			doc = Globals.openXMLFile(HIERARCHY_XML_DIR, loginXmlFile);
			NodeList root = doc.getElementsByTagName("Obiee_Users");
			Node firstRootNode = root.item(0);
			NodeList childNodes = firstRootNode.getChildNodes();

			// if(!cannotSaveAL.isEmpty()) {
			// this.msg4Usr = "These users "+users+" is already exists under different
			// company. Please provide different id.";
			// this.color4msg4Usr = "red";
			// return;
			// }
			for (int i = 0; i < ultradocumentDetailsAL.size(); i++) {
				String ulwfEmail = ultradocumentDetailsAL.get(i).emailID.toString();
				if (!ulwfEmail.equals("") || !ulwfEmail.isEmpty()) {

					// int j=1;
					String accessStartDte = "";
					String accessEndDte = "";
					String dateFormat = prop.getProperty("DATE_FORMAT");
					String emailId = ultradocumentDetailsAL.get(i).emailID;
					userNameHT.put("UsernameList", ultradocumentDetailsAL.get(i).emailID.toString());
					boolean flag = true;
					for (int m = 0; m < childNodes.getLength(); m++) {

						if (childNodes.item(m).getNodeType() == Node.ELEMENT_NODE) {
							String custKey = ((Element) childNodes.item(m)).getAttribute("CustomerKey");
							alredythrMailID = childNodes.item(m).getTextContent();
							System.out.println(childNodes.getLength() + ":*********%%%%%%%%%%%%%%%*************:"
									+ alredythrMailID);

							System.out.println(alredythrMailID + ":*********alredythrMailID*************:" + emailId);

							if (alredythrMailID.equalsIgnoreCase(emailId)
									&& custKey.equalsIgnoreCase(ultraWfcustomerKey)) {
								flag = false;
								break;
							}
						}

					}
					if (flag) {
						Element childNode = doc.createElement("User");
						firstRootNode.appendChild(childNode);
						System.out.println(
								alredythrMailID + ":*********alredythrMailID*$$$$$$$$$$$$$$$$$************:" + emailId);
						childNode.setAttribute("Email_ID", emailId);
						childNode.setAttribute("Access_Start_Date", accessStartDte);
						childNode.setAttribute("Access_End_Date", accessEndDte);
						int accessMaxID = Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Acess_User_ID",
								"ID");
						childNode.setAttribute("Access_Unique_ID", String.valueOf(accessMaxID));
						childNode.setAttribute("Access_Type", "Enabled");
						childNode.setAttribute("Allowed_Hierarchies", "");
						childNode.setAttribute("CustomerKey", ultraWfcustomerKey);
						childNode.setAttribute("Super_Privilege_Admin", "false"); // code change VIJAY
						childNode.setAttribute("Login_ID", emailId);
						childNode.setAttribute("First_Name", String.valueOf(ultradocumentDetailsAL.get(i).username));
						childNode.setAttribute("Last_Name", "");
						childNode.setAttribute("Title", "");
						childNode.setAttribute("Phone_Number_1", "");
						childNode.setAttribute("Phone_Number_2", "");
						childNode.setAttribute("Phone_Number_3", "");
						childNode.setAttribute("E_Mail_ID_1", "");
						childNode.setAttribute("E_Mail_ID_2", "");
						childNode.setAttribute("Address", "");
						childNode.setAttribute("City", "");
						childNode.setAttribute("State", "");
						childNode.setAttribute("Country", "");
						childNode.setAttribute("Zip", "");
						childNode.setAttribute("User_Password", Inventory.store(pwsd));
						childNode.setAttribute("New_Customer", "");
						childNode.setAttribute("Active", "true");
						childNode.setAttribute("Disable", "false");
						childNode.setTextContent(emailId);

						System.out.println("END");
					}
				}
			}

			this.ultradocumentischanged = false;
			getUltraUSERXML(doc, ultraWfLoginName);
			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, loginXmlFile);
			this.msg4Usr = "Workflow " + ultraWfName + " saved successfully. Please click [Close] button to go back. ";
			this.color4msg4Usr = "white";
			this.msg4Usrclose = "";
			this.minApproversvalue = ultradocumentDetailsAL.size();
			wfStagesaveAction();

			// attachUltraDocument2WF();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getUltraUSERXML(Document doc, String UltraMailID) {

		String customerkey = "";
		String userUWFPassword = "";
		String userUWFfname = "";
		String userUWFlname = "";

		try {

			if (UltraMailID.equals("") || UltraMailID.isEmpty()) {

				return;

			} else {

				PropUtil prop = new PropUtil();
				String adminXMLPath = prop.getProperty("IISUSERADMINFILEPATH");
				String IISUSERADMINFILENAME = prop.getProperty("IISUSERADMINFILENAME");
				Document adminXmlDoc = Globals.openXMLFile(adminXMLPath, IISUSERADMINFILENAME);
				NodeList adminChNdList = adminXmlDoc.getElementsByTagName("Admin");

				for (int i = 0; i < adminChNdList.getLength(); i++) {
					if (adminChNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element adminEle = (Element) adminChNdList.item(i);
						NodeList adminsubChNdList = adminXmlDoc.getElementsByTagName("User");
						for (int j = 0; j < adminsubChNdList.getLength(); j++) {
							Element adminEle1 = (Element) adminsubChNdList.item(j);
							String userId = adminEle1.getAttribute("UserID");

							if (userId.equalsIgnoreCase(UltraMailID)) {

								customerkey = adminEle1.getParentNode().getAttributes().getNamedItem("CustomerKey")
										.getNodeValue();
								userUWFPassword = adminEle1.getAttribute("Key");
								userUWFfname = adminEle1.getAttribute("FirstName");
								userUWFlname = adminEle1.getAttribute("LastName");
								// break;

							}
						}
					}
				}

				System.out.println("**************customerkey************" + customerkey);
				System.out.println("**************userUWFPassword************" + userUWFPassword);
				System.out.println("**************userUWFfname************" + userUWFfname);
				System.out.println("**************userUWFlname************" + userUWFlname);

				String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
				HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
				boolean fileexists = Globals.isFileExists(HIERARCHY_XML_DIR, loginXmlFile);
				// Document doc = null;
				FileInputStream fis = null;
				FileOutputStream fos = null;
				DocumentBuilderFactory dbf = null;
				DocumentBuilder db = null;
				String alredythrMailID = "";

				if (!fileexists) {

					System.out.println("XML File does not exist. Creating New One.");

					dbf = DocumentBuilderFactory.newInstance();
					db = dbf.newDocumentBuilder();
					doc = db.newDocument();
					String root1 = "Obiee_Users";
					Element rootElement1 = doc.createElement(root1);
					doc.appendChild(rootElement1);

					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					DOMSource source = new DOMSource(doc);
					fos = new FileOutputStream(HIERARCHY_XML_DIR + loginXmlFile);
					StreamResult result = new StreamResult(fos);
					transformer.transform(source, result);
					result.getOutputStream().flush();
					result.getOutputStream().close();
					System.out.println("XML File was Created.");

				}

				// doc = Globals.openXMLFile(HIERARCHY_XML_DIR, loginXmlFile);
				NodeList root = doc.getElementsByTagName("Obiee_Users");
				Node firstRootNode = root.item(0);
				NodeList childNodes = firstRootNode.getChildNodes();

				boolean flag = true;
				for (int m = 0; m < childNodes.getLength(); m++) {

					if (childNodes.item(m).getNodeType() == Node.ELEMENT_NODE) {

						alredythrMailID = childNodes.item(m).getTextContent();
						String key = ((Element) childNodes.item(m)).getAttribute("CustomerKey");
						System.out.println(key + ":*********%%%%%%%UltraMailID%%%%%%%%*************:" + customerkey);

						System.out.println(alredythrMailID + ":*********UltraMailID*************:" + UltraMailID);

						if (alredythrMailID.equalsIgnoreCase(UltraMailID) && customerkey.equalsIgnoreCase(key)) {
							flag = false;
							break;
						}
					}

				}
				if (flag) {
					Element childNode = doc.createElement("User");
					firstRootNode.appendChild(childNode);
					System.out.println(
							alredythrMailID + ":*********UltraMailID*$$$$$$$$$$$$$$$$$************:" + UltraMailID);
					childNode.setAttribute("Email_ID", UltraMailID);
					childNode.setAttribute("Access_Start_Date", "");
					childNode.setAttribute("Access_End_Date", "");
					int accessMaxID = Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Acess_User_ID", "ID");
					childNode.setAttribute("Access_Unique_ID", String.valueOf(accessMaxID));
					childNode.setAttribute("Access_Type", "Enabled");
					childNode.setAttribute("Allowed_Hierarchies", "");
					childNode.setAttribute("CustomerKey", customerkey);
					childNode.setAttribute("Super_Privilege_Admin", "false"); // code change VIJAY
					childNode.setAttribute("Login_ID", UltraMailID);
					childNode.setAttribute("First_Name", userUWFfname);
					childNode.setAttribute("Last_Name", userUWFlname);
					childNode.setAttribute("Title", "");
					childNode.setAttribute("Phone_Number_1", "");
					childNode.setAttribute("Phone_Number_2", "");
					childNode.setAttribute("Phone_Number_3", "");
					childNode.setAttribute("E_Mail_ID_1", "");
					childNode.setAttribute("E_Mail_ID_2", "");
					childNode.setAttribute("Address", "");
					childNode.setAttribute("City", "");
					childNode.setAttribute("State", "");
					childNode.setAttribute("Country", "");
					childNode.setAttribute("Zip", "");
					childNode.setAttribute("User_Password", Inventory.store(userUWFPassword));
					childNode.setAttribute("New_Customer", "");
					childNode.setAttribute("Active", "true");
					childNode.setAttribute("Disable", "false");
					childNode.setTextContent(UltraMailID);

					System.out.println("END");
				}

				// Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, loginXmlFile);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deletetableMethod(int rowIndex) {

		try {
			System.out.println(ultradocumentDetailsAL.size() + ":::::::::::::rowIndex:::::" + rowIndex);
			if (ultradocumentDetailsAL.size() <= 1) {
				this.msg4Usr = "Connot delete the last record.";
				this.color4msg4Usr = "white";
				this.msg4Usrclose = "false";
				return;
			}
			ultradocumentDetailsAL.remove(rowIndex);

		} catch (Exception e) {

		}

	}

	/*
	 * public void numbertextBoxAction() {
	 * 
	 * try {
	 * 
	 * 
	 * System.out.println("COMUNB *****************:"+numbertextBoxValue);
	 * 
	 * ArrayList<UltraDataBean> loadUltradocAL=new ArrayList();
	 * 
	 * for(int i=0;i<numbertextBoxValue;i++) {
	 * 
	 * UltraDataBean ultraTablevalue = new UltraDataBean("","",false,"","");
	 * loadUltradocAL.add(ultraTablevalue);
	 * 
	 * }
	 * 
	 * ultradocumentDetailsAL=loadUltradocAL;
	 * viewMenberNameAL=ultradocumentDetailsAL; }catch (Exception e) {
	 * 
	 * 
	 * } }
	 */

	public String getUltraWfName() {
		return ultraWfName;
	}

	public void setUltraWfName(String ultraWfName) {
		this.ultraWfName = ultraWfName;
	}

	public String getSendalertBox1Tvalue() {
		return sendalertBox1Tvalue;
	}

	public void setSendalertBox1Tvalue(String sendalertBox1Tvalue) {
		this.sendalertBox1Tvalue = sendalertBox1Tvalue;
	}

	public String getSendalertBox2Tvalue() {
		return sendalertBox2Tvalue;
	}

	public void setSendalertBox2Tvalue(String sendalertBox2Tvalue) {
		this.sendalertBox2Tvalue = sendalertBox2Tvalue;
	}

	public String getSendalertBox3Tvalue() {
		return sendalertBox3Tvalue;
	}

	public void setSendalertBox3Tvalue(String sendalertBox3Tvalue) {
		this.sendalertBox3Tvalue = sendalertBox3Tvalue;
	}

	public ArrayList getUltradocumentDetailsAL() {
		return ultradocumentDetailsAL;
	}

	public void setUltradocumentDetailsAL(ArrayList ultradocumentDetailsAL) {
		this.ultradocumentDetailsAL = ultradocumentDetailsAL;
	}

	public int getNumbertextBoxValue() {
		return numbertextBoxValue;
	}

	public String getMsg4Usrclose() {
		return msg4Usrclose;
	}

	public void setMsg4Usrclose() {
		this.msg4Usrclose = msg4Usrclose;
	}

	public String getMsg4Usr() {
		return msg4Usr;
	}

	public void setMsg4Usr(String msg4Usr) {
		this.msg4Usr = msg4Usr;
	}

	public String getColor4msg4Usr() {
		return color4msg4Usr;
	}

	public void setColor4msg4Usr(String color4msg4Usr) {
		this.color4msg4Usr = color4msg4Usr;
	}

	public String getHIERARCHY_XML_DIR() {
		return HIERARCHY_XML_DIR;
	}

	public void setHIERARCHY_XML_DIR(String hIERARCHY_XML_DIR) {
		HIERARCHY_XML_DIR = hIERARCHY_XML_DIR;
	}

	public void setNumbertextBoxValue(int numbertextBoxValue) {
		this.numbertextBoxValue = numbertextBoxValue;
	}

	public String getHierID() {
		return hierID;
	}

	public void setHierID(String hierID) {
		this.hierID = hierID;
	}

	public String getHierName() {
		return hierName;
	}

	public void setHierName(String hierName) {
		this.hierName = hierName;
	}

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public String getReviewersTypestr() {
		return reviewersTypestr;
	}

	public void setReviewersTypestr(String reviewersTypestr) {
		this.reviewersTypestr = reviewersTypestr;
	}

	public String getHierarchyXmlFileName() {
		return hierarchyXmlFileName;
	}

	public void setHierarchyXmlFileName(String hierarchyXmlFileName) {
		this.hierarchyXmlFileName = hierarchyXmlFileName;
	}

	public String getHierarchyConfigFile() {
		return hierarchyConfigFile;
	}

	public void setHierarchyConfigFile(String hierarchyConfigFile) {
		this.hierarchyConfigFile = hierarchyConfigFile;
	}

	public ArrayList getViewMenberNameAL() {
		return viewMenberNameAL;
	}

	public void setViewMenberNameAL(ArrayList viewMenberNameAL) {
		this.viewMenberNameAL = viewMenberNameAL;
	}

	public Boolean getSendalertBox1value() {
		return sendalertBox1value;
	}

	public void setSendalertBox1value(Boolean sendalertBox1value) {
		this.sendalertBox1value = sendalertBox1value;
	}

	public Boolean getSendalertBox2value() {
		return sendalertBox2value;
	}

	public void setSendalertBox2value(Boolean sendalertBox2value) {
		this.sendalertBox2value = sendalertBox2value;
	}

	public String getUltraWfcustomerKey() {
		return ultraWfcustomerKey;
	}

	public void setUltraWfcustomerKey(String ultraWfcustomerKey) {
		this.ultraWfcustomerKey = ultraWfcustomerKey;
	}

	public String getUltraWfLoginName() {
		return ultraWfLoginName;
	}

	public void setUltraWfLoginName(String ultraWfLoginName) {
		this.ultraWfLoginName = ultraWfLoginName;
	}

	public String getCustomerkeyUWF() {
		return customerkeyUWF;
	}

	public String getLoginEmailUWF() {
		return loginEmailUWF;
	}

	public static String getUltraWfhandshkeID() {
		return ultraWfhandshkeID;
	}

	public static void setUltraWfhandshkeID(String ultraWfhandshkeID) {
		UltradocumentBean.ultraWfhandshkeID = ultraWfhandshkeID;
	}

	public void setLoginEmailUWF(String loginEmailUWF) {
		this.loginEmailUWF = loginEmailUWF;
	}

	public void setCustomerkeyUWF(String customerkeyUWF) {
		this.customerkeyUWF = customerkeyUWF;
	}

	public int getMinApproversvalue() {
		return minApproversvalue;
	}

	public void setMinApproversvalue(int minApproversvalue) {
		this.minApproversvalue = minApproversvalue;
	}

	public Boolean getFirstdisable() {
		return firstdisable;
	}

	public void setFirstdisable(Boolean firstdisable) {
		this.firstdisable = firstdisable;
	}

	public Boolean getSeconddisable() {
		return seconddisable;
	}

	public void setSeconddisable(Boolean seconddisable) {
		this.seconddisable = seconddisable;
	}

	public Boolean getThirddisable() {
		return thirddisable;
	}

	public void setThirddisable(Boolean thirddisable) {
		this.thirddisable = thirddisable;
	}

	public String getSendallreviewersTypestr() {
		return sendallreviewersTypestr;
	}

	public void setSendallreviewersTypestr(String sendallreviewersTypestr) {
		this.sendallreviewersTypestr = sendallreviewersTypestr;
	}

	public String getSendallreviewersCountTvalue() {
		return sendallreviewersCountTvalue;
	}

	public void setSendallreviewersCountTvalue(String sendallreviewersCountTvalue) {
		this.sendallreviewersCountTvalue = sendallreviewersCountTvalue;
	}

	public Boolean getSendallreviewersCountTdisable() {
		return sendallreviewersCountTdisable;
	}

	public void setSendallreviewersCountTdisable(Boolean sendallreviewersCountTdisable) {
		this.sendallreviewersCountTdisable = sendallreviewersCountTdisable;
	}

	public String getParellpanestr() {
		return parellpanestr;
	}

	public void setParellpanestr(String parellpanestr) {
		this.parellpanestr = parellpanestr;
	}

	public String getParellpaneCLRstr() {
		return parellpaneCLRstr;
	}

	public void setParellpaneCLRstr(String parellpaneCLRstr) {
		this.parellpaneCLRstr = parellpaneCLRstr;
	}

	public String getMinaprDisplay() {
		return minaprDisplay;
	}

	public void setMinaprDisplay(String minaprDisplay) {
		this.minaprDisplay = minaprDisplay;
	}
}
